package org.algosketch.retrofittest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import org.algosketch.androidtemplate.data.model.Memo
import org.algosketch.retrofittest.R
import org.algosketch.retrofittest.call.CallResponseService
import org.algosketch.retrofittest.call.CallService
import org.algosketch.retrofittest.databinding.ActivityMainBinding
import org.algosketch.retrofittest.factory.OkHttp3Util
import org.algosketch.retrofittest.factory.RetrofitFactory
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initDataBinding()

//        getMemoWithCallAsync()
//        getMemoWithCallSync()
//        getMemoWithResponse()
//        CoroutineScope(Dispatchers.Main).launch {
//            getMemoWithAwait()
//        }
        CoroutineScope(Dispatchers.Main).launch {
            val result = fetchMemo()
            if(result.isSuccessful) {
                Log.d(tag, result.body().toString())
            }
        }
        //OkHttp3Util.request()
    }

    fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
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

    fun getMemoWithResponse() {
        val service = getRetrofitService(CallResponseService::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val response = service.getMemo(1)

            if(response.isSuccessful) {
                Log.d(tag, "suspend function 과 코루틴 이용")
                Log.d(tag, "${response.body()?.toString()}")
            }
            else {
                Log.e(tag, "onResponse, status : ${response.code()}, message : ${response.message()}")
            }
        }
    }

    suspend fun getMemoWithAwait() {
        val service = getRetrofitService(CallResponseService::class.java)
        val deferred = CoroutineScope(Dispatchers.IO).async {
            service.getMemo(1)
        }

        val result = deferred.await()

        Log.d(tag, result.body().toString())
    }

    suspend fun fetchMemo() = coroutineScope {
        withContext(Dispatchers.Default) {
            val service = getRetrofitService(CallResponseService::class.java)

            service.getMemo(1)
        }
    }

    fun requestWithHttp3() {

    }

    fun <T> getRetrofitService(service: Class<T>) : T = RetrofitFactory.createRetrofitService(service)
}