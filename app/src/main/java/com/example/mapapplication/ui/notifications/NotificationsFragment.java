package com.example.mapapplication.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mapapplication.databinding.FragmentNotificationsBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private List<SwitchMaterial> switches = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        switches.add(binding.materialSwitch0);
        switches.add(binding.materialSwitch1);
        switches.add(binding.materialSwitch2);
        switches.add(binding.materialSwitch3);
        switches.add(binding.materialSwitch4);
        switches.add(binding.materialSwitch5);
        switches.add(binding.materialSwitch6);
        switches.add(binding.materialSwitch7);
        switches.add(binding.materialSwitch8);
        switches.add(binding.materialSwitch9);

        for (int i = 0; i < 10; i++)
        {
            SwitchMaterial mySwitch = switches.get(i);
            mySwitch.setChecked(true);
            mySwitch.setText(String.valueOf(i));
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    NotificationsViewModel.groupEn[Integer.parseInt(buttonView.getText().toString())] = isChecked;
                }
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}