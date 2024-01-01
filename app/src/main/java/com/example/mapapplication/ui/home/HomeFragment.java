package com.example.mapapplication.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.mapapplication.R;
import com.example.mapapplication.databinding.FragmentHomeBinding;
import com.example.mapapplication.util.HttpHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment implements GoogleMap.InfoWindowAdapter {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private Map<Marker, DiscountItem> markerItemMap = new HashMap<>();
    private HomeFragment self = this;
    private View selfView;
    private View mapInfoWindow = null;
    private GoogleMap googleMap = null;
    private Marker lastMarker = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        selfView = view;

        homeViewModel.discountItemList.observe(getViewLifecycleOwner(), new Observer<List<DiscountItem>>() {
            @Override
            public void onChanged(List<DiscountItem> discountItems) {
                if (googleMap == null) {
                    return;
                }

                googleMap.clear();
                for (DiscountItem item : homeViewModel.discountItemList.getValue())
                {
                    lastMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(item.lat, item.lng)).title(item.name));
                    markerItemMap.put(lastMarker, item);
                }
            }
        });

        return view;

        //View view = inflater.inflate(R.layout.fragment_maps, container, false);
//        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull GoogleMap googleMap) {
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(@NonNull LatLng latLng) {
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng);
//                        markerOptions.title(latLng.latitude + " KG " + latLng.longitude);
//                        googleMap.clear();
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
//            }
//        });

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // map
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            self.googleMap = googleMap;

            if (!(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                googleMap.setMyLocationEnabled(true);
            }

            googleMap.setInfoWindowAdapter(self);

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    DiscountItem item = markerItemMap.get(marker);
                    if (item != null) {
                        HttpHelper.deleteDiscount(item.name);
                        homeViewModel.load();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putString("lat", String.valueOf(marker.getPosition().latitude));
                        bundle.putString("lng", String.valueOf(marker.getPosition().longitude));
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            Address address = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1).get(0);
                            bundle.putString("address", address.getAddressLine(0));
                        } catch (IOException e) {
                        }
                        Navigation.findNavController(selfView).navigate(R.id.action_navigation_home_to_navigation_dashboard,
                                bundle,
                                new NavOptions.Builder()
                                        .setPopUpTo(R.id.navigation_home, true)
                                        .build());
                    }
                }
            });

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " KG " + latLng.longitude);
                    //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    //googleMap.clear();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.showInfoWindow();
                }
            });

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
                    marker.showInfoWindow();
                    return true;
                }
            });

            homeViewModel.load();
            Log.d(toString(), "111");

//            Marker marker = null;
//            for (DiscountItem item : homeViewModel.discountItemList)
//            {
//                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(item.lat, item.lng)).title(item.name));
//                markerItemMap.put(marker, item);
//            }
            if (lastMarker != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastMarker.getPosition(), 14));
                lastMarker.showInfoWindow();
            }
        }
    };

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        if (mapInfoWindow == null)
        {
            mapInfoWindow = getLayoutInflater().inflate(R.layout.activity_map_info_window, binding.getRoot());
        }
        View view = mapInfoWindow;

        DiscountItem item = markerItemMap.get(marker);

        Button button = view.findViewById(R.id.mapInfoButton);

        if (item != null)
        {
            ((TextView) view.findViewById(R.id.mapInfoText1)).setText(item.name);
            ((TextView) view.findViewById(R.id.mapInfoText2)).setText(item.address);
            TextView tv3 = view.findViewById(R.id.mapInfoText3);
            tv3.setText(String.valueOf(item.price));
            tv3.setPaintFlags(tv3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            try {
                ((TextView) view.findViewById(R.id.mapInfoText4)).setText(String.format("%d %% off", (((item.price - item.sp) * 100) / item.price)));
            }
            catch (Exception e) {
            }
            ((TextView) view.findViewById(R.id.mapInfoText5)).setText(String.valueOf(item.sp));

            button.setText("Delete");
        }
        else
        {
            try {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                Address address = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1).get(0);
                ((TextView) view.findViewById(R.id.mapInfoText1)).setText(address.getAddressLine(0));
            } catch (IOException e) {
                ((TextView) view.findViewById(R.id.mapInfoText1)).setText("New");
            }

            ((TextView) view.findViewById(R.id.mapInfoText2)).setText(String.format("lat: %f, lng: %f", marker.getPosition().latitude, marker.getPosition().longitude));
            ((TextView) view.findViewById(R.id.mapInfoText3)).setText("");
            ((TextView) view.findViewById(R.id.mapInfoText4)).setText("");
            ((TextView) view.findViewById(R.id.mapInfoText5)).setText("");

            button.setText("Add item");
        }

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}