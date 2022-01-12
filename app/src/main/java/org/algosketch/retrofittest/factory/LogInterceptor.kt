package org.algosketch.retrofittest.factory

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

object LogInterceptor {
    fun createLogInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    fun createCustomLogInterceptor() : Interceptor {
        return CustomLoggingInterceptor()
    }
}