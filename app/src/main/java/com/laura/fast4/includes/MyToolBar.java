package com.laura.fast4.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.laura.fast4.R;

public class MyToolBar {
    public static void show(AppCompatActivity activity, String title, boolean upbutton){
        Toolbar toolbar = activity.findViewById(R.id.ToolBar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upbutton);
    }
}
