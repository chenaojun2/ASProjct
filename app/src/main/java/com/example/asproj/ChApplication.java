package com.example.asproj;

import org.chen.cibrary.log.ChConsolePrinter;
import org.chen.cibrary.log.ChLogConfig;
import org.chen.cibrary.log.ChLogManger;
import org.chen.common.ui.component.ChBaseApplication;

public class ChApplication extends ChBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ChLogManger.init(new ChLogConfig() {
            @Override
            public String getGlobalTag() {
                return super.getGlobalTag();
            }

        },new ChConsolePrinter());
    }
}
