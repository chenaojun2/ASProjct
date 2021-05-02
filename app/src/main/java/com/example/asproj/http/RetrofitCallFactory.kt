package com.example.asproj.http

import android.util.Log
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.chen.cibrary.restful.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.IllegalStateException

class RetrofitCallFactory(val baseUrl: String) : ChCall.Factory {

    private var apiService: ApiService
    private var chConvert: ChConvert

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
        apiService = retrofit.create(ApiService::class.java)
        chConvert = GsonConvert()
    }

    override fun newCall(request: ChRequest): ChCall<Any> {
        return RetrofitCall(request)
    }

    internal inner class RetrofitCall<T>(val request: ChRequest) : ChCall<T> {


        override fun execute(): ChResponse<T> {
            val realCall = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)
        }


        override fun enqueue(callback: ChCallBack<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response: ChResponse<T> = parseResponse(response)
                    Log.d("MainActivity", "onResponse: ")
                    callback.onSuccess(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("MainActivity", "onFailure: ")
                    callback.onFailed(t)
                }

            })
        }

        private fun parseResponse(response: Response<ResponseBody>): ChResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return chConvert.convert(rawData!!, request.returnType!!)
        }


        private fun createRealCall(request: ChRequest): Call<ResponseBody> {
            if (request.httpMethod == ChRequest.METHOD.GET) {
                return apiService.get(request.headers, request.endPointUrl(), request.parameters)
            } else if (request.httpMethod == ChRequest.METHOD.POST) {
                val parameters = request.parameters
                var builder = FormBody.Builder()
                var requestBody: RequestBody?
                var jsonObject = JSONObject()
                for ((key, value) in parameters!!) {
                    if (request.fromPost) {
                        builder.add(key, value)
                    } else {
                        jsonObject.put(key, value)
                    }
                }

                if (request.fromPost) {
                    requestBody = builder.build()
                } else {
                    requestBody = RequestBody.create(
                        MediaType.parse("application/json;utf-8"),
                        jsonObject.toString()
                    )
                }
                return apiService.post(request.headers, request.endPointUrl(), requestBody)
            } else {
                throw IllegalStateException("hirestful only support GET POST for now , url = " + request.endPointUrl())
            }
        }

    }

    interface ApiService {

        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>
    }

}