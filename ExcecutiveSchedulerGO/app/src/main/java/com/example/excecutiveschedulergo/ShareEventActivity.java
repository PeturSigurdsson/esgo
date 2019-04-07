package com.example.excecutiveschedulergo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.User;

import com.example.Connection.Connection;
import com.example.model.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShareEventActivity extends AppCompatActivity {

    private EditText mUsername;
    private Button mButton;
    private ListView mUserlist, mShareduserlist;
    private ArrayList<String> sharedusers;

    private Toolbar toolbar;
    private Connection c = Connection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_event_acticity);
        sharedusers = new ArrayList<>();
        toolbar = new Toolbar(this);
        mUserlist = findViewById(R.id.userlist);

        String token = TokenStore.getToken(this.getApplicationContext());
        c.getAllUsers(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Get Users", "Failed to get users");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<User>>(){}.getType();
                    List users = gson.fromJson(json, type);
                    Log.e("Users", "" + users);
                    String s = users.toString();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            ShareEventActivity.this,
                            android.R.layout.simple_list_item_1,
                            users
                    );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mUserlist.setAdapter(adapter);
                        }
                    });
                } else {
                    Log.e("Share event fail", json);
                }
            }

        });
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

        mUserlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sharedusers.add(parent.getItemAtPosition(position).toString());
                displayShared();

            }
        });

        mShareduserlist = findViewById(R.id.shareduserlist);
        mShareduserlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                sharedusers.remove(s);
                displayShared();
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

    private void displayShared(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ShareEventActivity.this,
                android.R.layout.simple_list_item_1,
                sharedusers
        );
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShareduserlist.setAdapter(adapter);
            }
        });
    }

    private void shareEvent() {
        String token = TokenStore.getToken(this.getApplicationContext());
        Event event = (Event) (getIntent()).getParcelableExtra("Event");

        c.shareEvent(sharedusers, event, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Share Event", "Failed to share event");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                if (response.isSuccessful()) {
                    Log.v("Share event success", json);
                    // Redirectum aftur i calendar view eftir ad vid shareum event
                    Intent calendar = new Intent(getApplicationContext(), CalendarActivity.class);
                    startActivity(calendar);

                } else {
                    Log.e("Share event fail", json);
                }
            }
        });
    }
}