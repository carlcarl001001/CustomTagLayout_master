package com.example.carl.customtaglayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TagLayout mTagLayout;
    private List<String> mItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTagLayout = findViewById(R.id.tab_layout);
        //标签 后台获取 加list<>集合
        mItems = new ArrayList<>();
        mItems.add("11111111");
        mItems.add("2222");
        mItems.add("3333333");
        mItems.add("44444444");
        mItems.add("55555");
        mItems.add("66666");
        mItems.add("777");
        mItems.add("88888");
        mItems.add("99999");
        mTagLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView tagTv = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tag,parent,false);
                tagTv.setText(mItems.get(position));
                return tagTv;
            }
        });



    }
}




















