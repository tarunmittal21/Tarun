package com.perraulthealth.perraulthealth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.perraulthealth.perraulthealth.R;


public class SignoutFragment extends Fragment {

public SignoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_signout, container, false);
        return rootview;
    }


}
