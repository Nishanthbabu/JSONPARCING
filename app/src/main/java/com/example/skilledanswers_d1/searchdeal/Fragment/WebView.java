package com.example.skilledanswers_d1.searchdeal.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skilledanswers_d1.searchdeal.R;

/**
 * Created by SkilledAnswers-D1 on 07-04-2016.
 */
public class WebView extends Fragment {
    private WebView webView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview,container,false);
        return view;
    }
}
