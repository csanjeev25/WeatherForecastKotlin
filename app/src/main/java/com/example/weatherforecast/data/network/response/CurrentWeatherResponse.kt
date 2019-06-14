package com.example.weatherforecast.data.network.response


import com.example.weatherforecast.data.db.entity.Current
import com.example.weatherforecast.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: Current,
    val location: Location
)