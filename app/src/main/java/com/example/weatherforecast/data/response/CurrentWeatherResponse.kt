package com.example.weatherforecast.data.response


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: Current,
    val location: Location
)