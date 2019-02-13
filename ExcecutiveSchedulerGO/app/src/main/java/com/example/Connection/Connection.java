package com.example.Connection;

import com.example.model.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Connection {

    private static Connection instance = new Connection();
    private String URL = "https://apis.is/cars";
    private User user;

    private Connection() {
        user = new User();
    }

    public static Connection getInstance() {
        return instance;
    }

    /*
    public String post(String endPoint, String data) {
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .url()
    }


    public String get(String endPoint, String data) {
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder().url(URL + endPoint).build();

        Call call = client.newCall(req);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return null;
    }
    */
    public void setUser(String username, String password){
        user.setUsername(username);
        user.setPassword(password);

        // TODO: Connect to backend and fill in the rest of the info.
    }

}
