package com.example.asproj.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.chen.cibrary.restful.ChConvert
import org.chen.cibrary.restful.ChResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

class GsonConvert : ChConvert {

    private var gson: Gson

    init {
        gson = Gson()
    }

    override fun <T> convert(rawData: String, dataType: Type): ChResponse<T> {
        var response: ChResponse<T> = ChResponse<T>()
        try {
            var jsonObject = JSONObject(rawData)
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")
            val data = jsonObject.optString("data")

            if (response.code == ChResponse.SUCCESS) {
                response.data = gson.fromJson(data, dataType)
            } else {
                response.errorData = gson.fromJson<MutableMap<String, String>>(
                    data, object : TypeToken<MutableMap<String, String>>() {}.type
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            response.code = -1
            response.msg = e.message
        }
        return response
    }

}