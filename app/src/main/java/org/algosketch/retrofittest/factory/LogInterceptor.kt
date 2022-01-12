package org.algosketch.retrofittest.factory

import okhttp3.logging.HttpLoggingInterceptor

object LogInterceptor {
    fun createLogInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return interceptor
    }
}