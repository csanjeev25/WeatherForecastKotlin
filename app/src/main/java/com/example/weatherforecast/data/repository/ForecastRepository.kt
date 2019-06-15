package com.example.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeather

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeather>
}