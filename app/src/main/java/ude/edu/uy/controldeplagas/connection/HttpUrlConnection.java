package ude.edu.uy.controldeplagas.connection;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by goliath on 21/11/2017.
 */

public class HttpUrlConnection {
    public static String sendGet (String url, String authorization){
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", "Basic " + authorization)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "38134af4-1801-bbb3-1462-53a066b7d4fb")
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
