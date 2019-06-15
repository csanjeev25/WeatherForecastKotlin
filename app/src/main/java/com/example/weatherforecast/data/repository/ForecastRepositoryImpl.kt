package com.example.weatherforecast.data.repository

import android.os.Build
import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.CurrentWeatherDao
import com.example.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.example.weatherforecast.data.network.WeatherNetworkDataSource
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
  private val currentWeatherDao: CurrentWeatherDao,
  private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{
            newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
        initWeatherData()
        return withContext(Dispatchers.IO){
            return@withContext if(!metric) currentWeatherDao.getWeatherImperial()
            else currentWeatherDao.getWeatherMetric()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ZonedDateTime.now().minusMinutes(30)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("Los Angeles", Locale.getDefault().language)
    }
}