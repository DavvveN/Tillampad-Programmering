package com.example.uppgift1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private TextView zero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView)findViewById(R.id.show_count);
        zero = (TextView)findViewById(R.id.button_Zero);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCount++;
        if(mShowCount != null){
            mShowCount.setText(Integer.toString(mCount));
            zero.setBackgroundColor(173);
        }
    }
    public void reset(View view){
        mCount = 0;
        mShowCount.setText(0);
        mShowCount.setBackgroundColor(808080);
    }
}