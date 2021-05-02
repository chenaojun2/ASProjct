package com.example.asproj.http.api

import com.google.gson.JsonObject
import org.chen.cibrary.restful.ChCall
import org.chen.cibrary.restful.annotation.Filed
import org.devio.hi.library.restful.annotation.GET

interface TestApi {
    @GET("cities")
    fun listCities(@Filed("name") name: String): ChCall<JsonObject>
}