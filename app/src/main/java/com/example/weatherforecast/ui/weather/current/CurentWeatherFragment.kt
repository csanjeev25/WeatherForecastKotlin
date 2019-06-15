package com.example.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.weatherforecast.R
import com.example.weatherforecast.data.network.ApixuWeatherAPIService
import com.example.weatherforecast.data.network.ConnectivityInterceptorImpl
import com.example.weatherforecast.data.network.WeatherNetworkDataSource
import com.example.weatherforecast.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.curent_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CurentWeatherFragment()
    }

    private lateinit var viewModel: CurentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.curent_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurentWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        val apiService = ApixuWeatherAPIService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            weather_text_view.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
//            val currentWeatherResponse = apiService.getCurrentWeather("london").await()
//            weather_text_view.text = currentWeatherResponse.current.toString()
            weatherNetworkDataSource.fetchCurrentWeather("london","en")
        }
    }

}
