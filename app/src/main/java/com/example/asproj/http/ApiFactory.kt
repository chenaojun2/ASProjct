package com.example.asproj.http

import org.chen.cibrary.restful.ChRestful

object ApiFactory {

    private val baseUrl = "https://api.devio.org/as/"
    private val chRestful:ChRestful = ChRestful(baseUrl ,RetrofitCallFactory(baseUrl))

    init {
        chRestful.addInterceptor(BizInterceptor())
    }

    fun <T>create(service: Class<T>):T {
        return chRestful.create(service)
    }

}