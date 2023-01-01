package com.icia.friend.store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.icia.friend.R;
import com.icia.friend.vo.StoreVO;

public class InfoFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    TextView s_name, s_location, s_tel;

    StoreVO vo = new StoreVO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StoreVO vo = (StoreVO) this.getArguments().getSerializable("StoreVO");
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        s_name = view.findViewById(R.id.s_name);
        s_tel = view.findViewById(R.id.s_tel);
        s_location = view.findViewById(R.id.s_location);

        s_name.setText(vo.getS_name());
        s_tel.setText(vo.getS_tel());
        s_location.setText(vo.getS_location());

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        StoreVO vo = (StoreVO) this.getArguments().getSerializable("StoreVO");
        mMap = googleMap;

        System.out.println("InfoFragment - onMapReady :: 좌표 x : " + vo.getX() + ", 좌표 y : " + vo.getY());

        LatLng latLng = new LatLng(vo.getX(), vo.getY());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(vo.getS_name())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_marker)));
    }

}