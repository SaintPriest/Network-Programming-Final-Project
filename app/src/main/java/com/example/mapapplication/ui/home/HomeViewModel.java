package com.example.mapapplication.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mapapplication.util.HttpHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public List<DiscountItem> discountItemList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void load() {
        String s = HttpHelper.testRequest();
        Log.d("HomeViewModel", s);

        discountItemList = new Gson().fromJson(s, new TypeToken<ArrayList<DiscountItem>>(){}.getType());
        for (DiscountItem item : discountItemList)
        {
            Log.d("HomeViewModel", item.name + " " + item.lat + " " + item.lng);
        }
    }
}