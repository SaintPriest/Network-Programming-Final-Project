package com.example.mapapplication.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mapapplication.ui.notifications.NotificationsViewModel;
import com.example.mapapplication.util.HttpHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<List<DiscountItem>> discountItemList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        discountItemList = new MutableLiveData<>();
        discountItemList.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void load() {
        String response = HttpHelper.getDiscounts(NotificationsViewModel.getGroupEnCode());
        Log.d("HomeViewModel", response);

        List<DiscountItem> list = new Gson().fromJson(response, new TypeToken<ArrayList<DiscountItem>>(){}.getType());

        if (list.size() != discountItemList.getValue().size())
        {
            discountItemList.setValue(list);
            for (DiscountItem item : discountItemList.getValue())
            {
                Log.d("HomeViewModel", item.name + " " + item.lat + " " + item.lng);
            }
        }
    }
}