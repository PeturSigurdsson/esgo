package com.example.excecutiveschedulergo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.Connection.Connection;
import com.example.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShareEventActicity extends AppCompatActivity {

    private EditText mUsername;
    private Button mButton;

    private Toolbar toolbar;
    private Connection c = Connection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_event_acticity);

        toolbar = new Toolbar(this);

        setListeners();
    }

    private void setListeners() {
        mUsername = findViewById(R.id.share_event_text);

        mUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mUsername.getText().toString().equals("Username")) {
                    mUsername.setText("");
                }
            }
        });

        mButton = findViewById(R.id.share_event_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareEvent();
            }
        });
    }

    private void shareEvent() {
        Event event = (Event) (getIntent()).getParcelableExtra("Event");
        List<String> usernames = new ArrayList<String>();

        usernames.add(mUsername.getText().toString());

        String token = TokenStore.getToken(this.getApplicationContext());

        c.shareEvent(usernames, event, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Share Event", "Failed to share event");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                if (response.isSuccessful()) {
                    Log.v("Share event success", json);
                } else {
                    Log.e("Share event fail", json);
                }
            }
        });
    }
}