package com.example.mapapplication.ui.notifications;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    public static boolean[] groupEn = new boolean[10];
    private static boolean isInit = false;

    public static String getGroupEnCode() {

        if (!isInit) {
            for (int i = 0; i < 10; i++) {
                groupEn[i] = true;
            }
            isInit = true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (groupEn[i]) {
                stringBuilder.append("1");
            }
            else {
                stringBuilder.append("0");
            }
        }
        String s = stringBuilder.toString();
        return s;
    }

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}