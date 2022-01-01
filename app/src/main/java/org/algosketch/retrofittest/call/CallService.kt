package org.algosketch.retrofittest.call

import org.algosketch.androidtemplate.data.model.Memo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CallService {
    @GET("memo/{id}")
    fun getMemo(@Path(value = "id") id: Long): Call<Memo>

    @POST(".")
    fun writeMemo(
        @Body requestBody: Memo
    ): Call<Memo>
}