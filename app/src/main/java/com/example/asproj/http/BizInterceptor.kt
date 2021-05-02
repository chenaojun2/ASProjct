package com.example.asproj.http

import org.chen.cibrary.log.ChLog
import org.chen.cibrary.restful.ChInterceptor

class BizInterceptor : ChInterceptor {

    override fun intercept(chan: ChInterceptor.Chain): Boolean {
        ChLog.dt("BizInterceptor", "start")
        if (chan.isRequestPeriod) {
            ChLog.dt("BizInterceptor", "addHeader")
            val request = chan.request()
            request.addHeader("auth-token", "MTU5Mjg1MDg3NDcwNw==")
        } else if (chan.response() != null) {
            ChLog.dt("BizInterceptor", chan.request().endPointUrl())
            ChLog.dt("BizInterceptor", chan.response()!!.rawData)
        }
        ChLog.dt("BizInterceptor", "end")
        return false
    }

}