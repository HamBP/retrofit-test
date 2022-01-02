package org.algosketch.retrofittest.call

import org.algosketch.androidtemplate.data.model.Memo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CallResponseService {
    @GET("memo/{id}")
    suspend fun getMemo(@Path(value = "id") id: Long): Response<Memo>

    @POST(".")
    suspend fun writeMemo(
        @Body requestBody: Memo
    ): Response<Memo>
}