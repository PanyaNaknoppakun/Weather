package com.panya.weather.data.api

import com.panya.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    companion object {
        private const val APP_ID = "APPID"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url.newBuilder().addQueryParameter(APP_ID, BuildConfig.APP_ID).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}
