package com.group8.myandroid.global;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <h1>OpenHTML</h1>
 * This class is used to retrieve HTML content from a specified URL.
 * This class uses HTTP GET requests to retrieve content.
 * <p>
 * Example of use.
 * <pre>
 *   URL url = new URL("https://www.example.com");
 *   String content = OpenHTML.getPageContent(url);
 * </pre>
 * </p>
 *
 * @author rxxuzi
 */
public final class OpenHTML {

    private ExecutorService executor;
    public static final String page = "https://www.java.com/ja/";
    public static boolean isRunning = true;
    private String htmlContent;

    /**
     * OpenHTMLコンストラクター
     *
     * @param useAsync trueの場合非同期モードを使用。それ以外の場合は同期モードを使用。
     */
    public OpenHTML(boolean useAsync) {
        if (useAsync) {
            executor = Executors.newSingleThreadExecutor();
        }
    }

    /**
     * 非同期で指定されたURLからHTMLコンテンツを取得する。
     *
     * @param url HTMLコンテンツを取得するURL。
     * @return 非同期タスクの結果を取得するために使用するFutureオブジェクト。
     * @throws IllegalArgumentException urlがnullの場合。
     */
    public Future<String> getPageContentAsync(URL url) {
        return executor.submit(() -> getPageContent(url));
    }

    /**
     * ExecutorServiceをシャットダウンするメソッド。
     * OpenHTMLインスタンスがもはや必要なくなったときに呼び出すべきメソッド。
     */
    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    /**
     * 指定されたURLからHTMLコンテンツを取得する。
     * このメソッドはHTTP GETリクエストを実行し、レスポンスボディを文字列として返す。
     *
     * @param url HTMLコンテンツを取得するURL。
     * @return URLから取得したHTMLコンテンツ。
     * @throws IllegalArgumentException urlがnullの場合。
     * @throws IOException              ネットワークエラーやリクエスト失敗など、I/Oエラーが発生した場合。
     */
    public static String getPageContent(URL url) throws IOException {
        StringBuilder htmlContent;
        try {
            // HTTP URL Connection
            HttpURLConnection openConnection =
                    (HttpURLConnection) url.openConnection();
            openConnection.setAllowUserInteraction(false);
            openConnection.setInstanceFollowRedirects(true);
            // GET Request
            openConnection.setRequestMethod("GET");
            // Connect
            openConnection.connect();

            int httpStatusCode = openConnection.getResponseCode();
            if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("HTTP Status " + httpStatusCode);
            }
            // Input Stream
            DataInputStream dataInStream = new DataInputStream(openConnection.getInputStream());

            // Read HTML content
            BufferedReader reader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
            htmlContent = new StringBuilder();
            String line;
            //Read html one line at a time
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return htmlContent.toString();
    }



}
