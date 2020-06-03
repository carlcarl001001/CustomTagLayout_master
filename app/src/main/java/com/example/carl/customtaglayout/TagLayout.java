package com.example.carl.customtaglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private String TAG = "chen";
    private List<List<View>> mChildViews=new ArrayList<>();
    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //1.指定宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //mChildViews.clear();
        mChildViews = new ArrayList<>();
        log("into onMeasure....................");
        int childCount = getChildCount();
        //获得宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度需要计算
        int height = getPaddingTop()+getPaddingBottom();
        //一行的宽度
        int  lineWidth = getPaddingLeft();
        //子View高度不一致
        int maxHeight =0;
        ArrayList<View> childViews = new ArrayList<>();
        boolean isOneLine = true;
        for (int i=0;i<childCount;i++) {
            //2.循环测量子View
            View childView = getChildAt(i);
            //这段话调用之后就可以获得子View的宽高，因为会i盗用子View的onMeasure方法
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            //margin值 ViewGroup.LayoutParams 没有 就用系统的MarginLayoutParams
            //想想 LinearLayout为什么会有？
            //LinearLayout有自己的LayoutParams 会复写一个非常重要的方法
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            //什么时候要换行，一行不够的情况下 考虑 margin
            if (lineWidth+(childView.getMeasuredWidth()+params.leftMargin+params.rightMargin)>width){
                log("转折处");
                log("childView.getId():"+childView.toString());
                //mChildViews.add(childViews);
                height += childView.getMeasuredHeight()+params.topMargin+params.bottomMargin;
                lineWidth=childView.getMeasuredWidth()+params.leftMargin+params.rightMargin;

                //childViews.add(childView);
                mChildViews.add(childViews);
                childViews = new ArrayList<>();
                childViews.add(childView);
                //mChildViews.add(childViews);
            }else {
                log("非转折处");
                log("childView.getId():"+childView.toString());
                lineWidth += childView.getMeasuredWidth()+params.leftMargin+params.rightMargin;
                childViews.add(childView);
                maxHeight = Math.max(childView.getMeasuredHeight()+params.topMargin+params.bottomMargin,maxHeight);
                log("maxHeight:"+maxHeight);
            }
            //childViews.add(childView);
        }
        //mChildViews.add(childViews);
        log("one line add-------------------");
        mChildViews.add(childViews);

        height +=maxHeight;

        //指定本控件的宽高
        setMeasuredDimension(width,height);
    }

    //用来摆放子view
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left,top = getPaddingTop(),right,bottom;//
        for (List<View> childViews:mChildViews){
            left = getPaddingLeft();
            for(View childView:childViews){
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                left +=params.leftMargin;
                int childTop = top+params.topMargin;
                right = left+childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();
                //摆放
                //Log.i(TAG, String.format(s,left,top,right,bottom));
                childView.layout(left,childTop,right,bottom);
                //left 叠加
                left +=childView.getMeasuredWidth()+params.rightMargin;
            }
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childViews.get(0).getLayoutParams();
            top += childViews.get(0).getMeasuredHeight()+params.topMargin+params.bottomMargin;
        }



    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    private void log(String str){
        Log.i(TAG,str);
    }
}
