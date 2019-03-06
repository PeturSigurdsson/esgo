package com.example.excecutiveschedulergo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Connection.Connection;
import com.example.model.Event;

public class Toolbar {

    private Activity    activity;
    public  Event       event;
    public  Button      mCreate, mEdit, mShare, mLogout;

    public Toolbar(Activity _activity){
        mCreate  = _activity.findViewById(R.id.create_event);
        mEdit    = _activity.findViewById(R.id.edit_event);
        mShare   = _activity.findViewById(R.id.share_event);
        mLogout  = _activity.findViewById(R.id.logout);
        activity = _activity;
        setListeners();
    }

    /**
     * From https://stackoverflow.com/a/3913720
     * Passing int parameter to opening Activity to determine the layout.
     */
    public void setListeners(){

        mCreate.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity.getApplicationContext(), CreateEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 0);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        mEdit.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity.getApplicationContext(), CreateEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 1);
                bundle.putParcelable("Event", event);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        mShare.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity.getApplicationContext(), CreateEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 2);
                bundle.putParcelable("Event", event);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        mLogout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TokenStore.setToken("",activity.getApplicationContext());
                activity.finish();
            }
        });

    }
}
