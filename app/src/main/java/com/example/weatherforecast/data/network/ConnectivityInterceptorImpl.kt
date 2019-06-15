package com.example.weatherforecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherforecast.Internal.NoConnectvityClass
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//context to get system service
class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectvityClass()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}