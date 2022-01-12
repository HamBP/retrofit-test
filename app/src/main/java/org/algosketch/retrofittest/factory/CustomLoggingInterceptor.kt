package org.algosketch.retrofittest.factory

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class CustomLoggingInterceptor : Interceptor {
    private val http3LoggingInterceptor = HttpLoggingInterceptor()

    init {
        http3LoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return http3LoggingInterceptor.intercept(chain)
    }
}