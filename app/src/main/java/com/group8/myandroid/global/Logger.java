package com.group8.myandroid.global;
import android.annotation.SuppressLint;
import android.util.Log;

import java.io.File;

public class Logger {

    @SuppressLint("DefaultLocale")
    public static void err(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        String msg = String.format("Class: %s, Method: %s, Line: %d, Message: %s",
                className, methodName, lineNumber, message);

        Log.e("Exception", msg);
    }

    public static void err(Exception e){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        @SuppressLint("DefaultLocale")
        String msg = String.format("Class: %s, Method: %s, Line: %d, Message: %s",
                className, methodName, lineNumber, e.getMessage());

        Log.e("Exception", msg);
    }


}
