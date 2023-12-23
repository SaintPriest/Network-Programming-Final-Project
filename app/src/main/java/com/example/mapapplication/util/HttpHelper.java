package com.example.mapapplication.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    public static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String SERVER_URL_PREFIX = "https://qwer87511.pythonanywhere.com";

    public static String testRequest() {
        final String TEST_URL_POSTFIX = "/";
        return post(SERVER_URL_PREFIX + TEST_URL_POSTFIX, new JSONObject().toString());
    }

    public static String loginRequest(String email, String password) {
        final String LOGIN_URL_POSTFIX = "/login";

        try {
            JSONObject json = new JSONObject()
                    .put("email", email)
                    .put("password", password);

            return post(SERVER_URL_PREFIX + LOGIN_URL_POSTFIX, json.toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    static String post(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.toString());
        }
    }
}

