package com.shoo.launchedfrom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String launchedFromPkgName = LaunchedFromManager.getInstance().getLaunchedFromPkgName();
        String notice = "App Launched from: " + launchedFromPkgName;

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(notice);

        Toast.makeText(this, notice, Toast.LENGTH_LONG).show();
    }
}
