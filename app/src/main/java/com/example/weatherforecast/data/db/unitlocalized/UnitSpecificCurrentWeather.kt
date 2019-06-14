package com.example.weatherforecast.data.db.unitlocalized

interface UnitSpecificCurrentWeather {
    val temperature : Double
    val conditionText: String
    val conditionUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitation: Double
    val feelsLikeTemperature: Double
    val visibiltyDistance: Double
}