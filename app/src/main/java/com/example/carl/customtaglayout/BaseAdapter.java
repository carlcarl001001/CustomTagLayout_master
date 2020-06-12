package com.example.carl.customtaglayout;

import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter {
    //1.有多少给条目
    public abstract int getCount();
    //2.getView通过position
    public abstract View getView(int position, ViewGroup parent);

    //3.观察者模式

}
















