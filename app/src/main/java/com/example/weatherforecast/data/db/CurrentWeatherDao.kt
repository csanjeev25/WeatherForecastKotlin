package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherforecast.data.db.entity.Current
import com.example.weatherforecast.data.db.unitlocalized.ImperialCurrentWeather
import com.example.weatherforecast.data.db.unitlocalized.MetricCurrentWeather


@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: Current)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeather>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeather>
}