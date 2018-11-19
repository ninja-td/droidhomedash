package com.ninjagames.droidhomedash;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SwitchSettingsFragment extends Fragment {

    EditText eventNameText;
    EditText apiKeyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.switch_settings, container, false);

        eventNameText = rootView.findViewById(R.id.switch_name);
        eventNameText.setText(PreferenceHelper.getString(getActivity(), "switch_name"));
        eventNameText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                PreferenceHelper.setString(getActivity(),"switch_name", eventNameText.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        apiKeyText = rootView.findViewById(R.id.api_key);
        apiKeyText.setText(PreferenceHelper.getString(getActivity(), "api_key"));
        apiKeyText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                PreferenceHelper.setString(getActivity(),"api_key", eventNameText.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        return rootView;
    }
}
