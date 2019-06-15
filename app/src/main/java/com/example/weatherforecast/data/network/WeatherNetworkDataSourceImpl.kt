package com.example.weatherforecast.data.network

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.Internal.NoConnectvityClass
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherAPIService: ApixuWeatherAPIService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, lnaguageCode: String) {

        try {
            val fetchCurrentWeather = apixuWeatherAPIService
                .getCurrentWeather(location, lnaguageCode )
                .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }catch (e: NoConnectvityClass){
            //
        }
    }
}