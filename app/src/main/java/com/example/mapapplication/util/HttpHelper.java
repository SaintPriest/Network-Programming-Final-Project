package com.example.mapapplication.util;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SERVER_URL_PREFIX = "https://qwer87511.pythonanywhere.com";

    public static String getAllDiscounts() {
        final String URL_POSTFIX = "/explore/discounts/all";
        try {
            JSONObject jsonObject = new JSONObject();

            return post(SERVER_URL_PREFIX + URL_POSTFIX, jsonObject.toString());
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public static String getDiscounts(String value) {
        OkHttpClient client = new OkHttpClient();
        final String URL_POSTFIX = "/explore/discounts/get";
        String url = SERVER_URL_PREFIX + URL_POSTFIX;
        try {
            RequestBody body = new FormBody.Builder()
                    .add("value", value)
                    .build();
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
        catch (Exception e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    public static String deleteDiscount(String name) {
        OkHttpClient client = new OkHttpClient();
        final String URL_POSTFIX = "/explore/discounts/delete";
        String url = SERVER_URL_PREFIX + URL_POSTFIX;
        try {
            RequestBody body = new FormBody.Builder()
                    .add("name", name)
                    .build();
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
        catch (Exception e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    public static String addDiscountItem(String name, String lat, String lng, String address, String price, String sp, String group) {
        OkHttpClient client = new OkHttpClient();
        final String URL_POSTFIX = "/explore/discounts/add";
        String url = SERVER_URL_PREFIX + URL_POSTFIX;
        try {
            RequestBody body = new FormBody.Builder()
                    .add("name", name)
                    .add("lat", lat)
                    .add("lng", lng)
                    .add("address", address)
                    .add("price", price)
                    .add("sp", sp)
                    .add("group", group)
                    .build();
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
        catch (Exception e)
        {
            throw new RuntimeException(e.toString());
        }
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
        OkHttpClient client = new OkHttpClient();
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
