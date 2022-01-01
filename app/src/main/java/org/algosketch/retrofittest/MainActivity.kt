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

        getMemo()
    }

    fun getMemo() {
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

    fun <T> getRetrofitService(service: Class<T>) : T = RetrofitFactory.createRetrofitService(service)
}