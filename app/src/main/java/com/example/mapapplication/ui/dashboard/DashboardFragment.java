package com.example.mapapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mapapplication.R;
import com.example.mapapplication.databinding.FragmentDashboardBinding;
import com.example.mapapplication.util.HttpHelper;
import com.google.android.material.snackbar.Snackbar;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        ((TextView) view.findViewById(R.id.contributeLatEditText)).setText(bundle.getString("lat"));
        ((TextView) view.findViewById(R.id.contributeLngEditText)).setText(bundle.getString("lng"));
        ((TextView) view.findViewById(R.id.contributeAddressEditText)).setText(bundle.getString("address"));
        ((Button) view.findViewById(R.id.contributeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String response = HttpHelper.addDiscountItem(((TextView) view.findViewById(R.id.contributeNameEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributeLatEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributeLngEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributeAddressEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributePriceEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributeSpEditText)).getText().toString(),
                            ((TextView) view.findViewById(R.id.contributeGroupEditText)).getText().toString());
                    Log.d(toString(), response);
                    Snackbar snackbar = Snackbar.make(view, response, 1000);
                    snackbar.show();
                }
                catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(view, "Failed", 1000);
                    snackbar.show();
                }
            }
        });
    }
}