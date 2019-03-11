package com.chaowen.moretext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaowen.moretextview.MoreTextView;

public class MainActivity extends AppCompatActivity {

    MoreTextView moreTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreTextView = findViewById(R.id.moreTextView);

       // moreTextView.setShowingLine(2); //只显示2行
        moreTextView.setShowingChar(30);  //只显示30个字

        moreTextView.addShowMoreText("展开");
        moreTextView.addShowLessText("收起");
    }
}
