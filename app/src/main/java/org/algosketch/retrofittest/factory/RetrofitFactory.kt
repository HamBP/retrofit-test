package org.algosketch.retrofittest.factory

import okhttp3.OkHttpClient
import org.algosketch.retrofittest.configs.ServerConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitFactory {
    fun <T> createRetrofitService(service: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(ServerConfig.baseUrl)
            .client(OkHttp3Util.createClientWithLogInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}