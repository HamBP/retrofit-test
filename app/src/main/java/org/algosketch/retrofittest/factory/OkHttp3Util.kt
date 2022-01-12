package org.algosketch.retrofittest.factory

import android.util.Log
import okhttp3.*
import org.algosketch.retrofittest.configs.ServerConfig
import java.io.IOException

object OkHttp3Util {
    private val client = OkHttpClient()

    fun createClientWithLogInterceptor() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(LogInterceptor.createLogInterceptor()).build()
    }

    fun request() {
        Log.e("request", "is called")

        val request = Request.Builder()
            .url(ServerConfig.baseUrl + "/memo/1")
            .post(createBody())
            .build()

        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("err", "err")
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
//                        val res = response.body.toString()
//                        Log.d("res", res)
                        Log.e("res", "res")
                    } else {
                        Log.e("err", response.message)
                    }
                }
            }
        )
    }

    fun createBody() : RequestBody {
        return FormBody.Builder()
            .build()
    }
}