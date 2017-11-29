package ude.edu.uy.controldeplagas.connection;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by goliath on 21/11/2017.
 */

public class HttpUrlConnection {

    public static String sendGet(String url, String authorization) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", "Basic " + authorization)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String r = response.toString();
            if (r.contains("200")) {
                result = response.body().string();
            } else {
                result = "Error: " + r;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String verificarServer(String url) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code()==200){
                result = "Verificacion exitosa";
            } else {
                result = "Datos Incorrectos";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String verificarLogin(String url, String authorization) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", "Basic " + authorization)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code()==200){
                result = response.body().string();
            } else {
                result = "Datos Incorrectos";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String sendPost(String url, String authorization, String json) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("authorization", "Basic " + authorization)
                .addHeader("content-type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String r = response.toString();
            if (r.contains("201")) {
                result = response.body().string();
            } else {
                result = "Error: " + r;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPatch(String url, String authorization, String json) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("authorization", "Basic " + authorization)
                .addHeader("content-type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (code == 204) {
                result = "Se han modificado los datos correctamente";
            } else {
                result = "Error: " + response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPut(String url, String authorization, String json) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("authorization", "Basic " + authorization)
                .addHeader("content-type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String r = response.toString();
            if (r.contains("200")) {
                result = response.body().string();
            } else {
                result = "Error: " + r;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendDelete(String url, String authorization) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .delete(null)
                .addHeader("authorization", "Basic " + authorization)
                .build();

        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (code == 204) {
                result = "Se han borrado los datos correctamente";
            } else {
                result = "Error: " + response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
