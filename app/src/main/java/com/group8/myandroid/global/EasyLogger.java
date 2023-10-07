package com.group8.myandroid.global;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * <h1>EasyLogger</h1>
 * EasyLogger is a utility class that simplifies logging in Android applications.
 * It wraps around the Android Log utility to provide additional features such
 * as automatic tag generation and log message formatting with stack trace information.
 *
 * <p>EasyLogger can log messages with different log levels (e.g., DEBUG, ERROR, etc.)
 * and can include class name, method name, and line number from the call
 * stack in the log message, which can be especially useful for debugging.</p>
 *
 * <p><strong>Usage example:</strong></p>
 * <pre>
 * EasyLogger logger = new EasyLogger("MyTag");
 * logger.setGetTrace(true);
 * logger.debug("This is a debug message");
 * </pre>
 *
 * <p>This will produce a log message like the following in Logcat:</p>
 * <pre>
 * D/MyTag: Class: com.example.MyClass
 * Method: myMethod
 * Line: 123
 * Message: This is a debug message
 * </pre>
 *
 * <p>The actual format and content of the log message may vary depending on how
 * the {@link #msg(String)} method is implemented and whether the trace flag is set.</p>
 *
 * <p>Note: Ensure that logging is disabled or limited in production releases of
 * your app for security and performance reasons.</p>
 *
 * @author rxxuzi
 */
public final class EasyLogger {
    private final String tag;
    private boolean getTrace = false;


    private static int totalCalls = 0;

    //デフォルトタグ"EasyLogger"を採用
    public EasyLogger() {
        this.tag = "EasyLogger";
    }
    /**
     * タグが指定されたオブジェクトのクラスの単純な名前に設定された EasyLogger インスタンスを作成する。
     *
     * @param object An object to determine the tag from its class name.
     */
    public EasyLogger(Object object) {
        this.tag = object.getClass().getSimpleName();
    }

    public EasyLogger(Object object, boolean getTrace) {
        this.tag = object.getClass().getSimpleName();
        this.getTrace = getTrace;
    }

    public EasyLogger(boolean getTrace) {
        this.getTrace = getTrace;
        this.tag = "EasyLogger"; // デフォルトタグ"EasyLogger"を採用
    }

    public EasyLogger(String tag) {
        this.tag = tag;
    }

    public EasyLogger(String tag, boolean getTrace) {
        this.tag = tag;
        this.getTrace = getTrace;
    }

    @SuppressLint("DefaultLocale")
    private String msg(String message) {
        totalCalls ++ ;
        if (getTrace){
            int index = 2;
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

            // EasyLoggerクラスの外の呼び出し元を見つける
            while (index < stackTraceElements.length &&
                    stackTraceElements[index].getClassName().equals(this.getClass().getName())) {
                index++;
            }

            // データを取得する有効なスタック要素があるかどうかをチェックする。
            if (index < stackTraceElements.length) {
                StackTraceElement stackTraceElement = stackTraceElements[index];
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                int lineNumber = stackTraceElement.getLineNumber();
                // Get thread info
                String threadName = Thread.currentThread().getName();

                return String.format("Class: %s\nMethod: %s\nLine: %d\nThread: %s\nMessage: %s",
                        className, methodName, lineNumber, threadName, message);

            }
        }
        return message;
    }

    // エラーログを出力
    public void error(String message) {
        Log.e(tag, msg(message));
    }

    // エラーログを出力（例外情報付き）
    public void error(String message, Throwable throwable) {
        Log.e(tag, msg(message), throwable);
    }

    // エラーログを出力（例外情報付き）
    public void error(Exception e) {
        Log.e(tag, msg(e.getMessage()), e);
    }

    // デバッグログを出力
    public void debug(String message) {
        Log.d(tag, msg(message));
    }

    // 情報ログを出力
    public void info(String message) {
        Log.i(tag, msg(message));
    }

    // 警告ログを出力
    public void warn(String message) {
        Log.w(tag, msg(message));
    }

    // トレース情報の切り替え
    public void setGetTrace(boolean getTrace) {
        this.getTrace = getTrace;
    }

    // 合計呼び出し回数
    public static int getTotalCalls() {
        Log.i("EasyLogger", "totalCalls: " + totalCalls);
        return totalCalls;
    }
}
