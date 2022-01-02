package org.algosketch.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.algosketch.androidtemplate.data.model.Memo
import org.algosketch.retrofittest.call.CallService
import org.algosketch.retrofittest.factory.RetrofitFactory
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getMemoWithCallAsync()
//        getMemoWithCallSync()
    }

    fun getMemoWithCallAsync() {
        val service = getRetrofitService(CallService::class.java)
        val call = service.getMemo(1)
        call.enqueue(object : retrofit2.Callback<Memo> {
            override fun onResponse(call: Call<Memo>, response: Response<Memo>) {
                if(response.isSuccessful) {
                    Log.d(tag, "가장 기본적인 Retrofit 사용 방법")
                    Log.d(tag, "${response.body()?.toString()}")
                }
                else {
                    Log.e(tag, "onResponse, status : ${response.code()}, message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Memo>, t: Throwable) {
                Log.e(tag, "onFailure")
                t.printStackTrace()
            }
        })
    }

    fun getMemoWithCallSync() {
        val service = getRetrofitService(CallService::class.java)
        val call = service.getMemo(1)

        /**
         * Main Thread 에서 사용하면 Exception 발생.
         */
        Thread {
            val result = call.execute()

            if(result.isSuccessful) {
                Log.d(tag, "스레드 + 동기를 이용한 Retrofit 사용 방법")
                Log.d(tag, "${result.body()?.toString()}")
            }
            else {
                Log.e(tag, "onResponse, status : ${result.code()}, message : ${result.message()}")
            }
        }.start()
    }

    fun <T> getRetrofitService(service: Class<T>) : T = RetrofitFactory.createRetrofitService(service)
}