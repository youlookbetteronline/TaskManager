package com.example.gav.taskmanager.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gav.taskmanager.R;


public class DaysOfWeekView extends FrameLayout {

    private String[] labels =  new String[]{"mon", "tues", "wed", "thurs", "fri", "sat", "sun"};
    private int labelTextSize;
    private int labelTextColor;
    private int width;

    public DaysOfWeekView(Context context) {
        super(context);
        initTextViews();
    }

    public DaysOfWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initTextViews();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DaysOfWeekView);
        int id;
        id = attributes.getResourceId(R.styleable.DaysOfWeekView_labels, 0);
        this.labels = (id!=0)?getResources().getStringArray(id):labels;
        this.labelTextSize = attributes.getDimensionPixelSize(R.styleable.DaysOfWeekView_label_textSize,0);
        this.labelTextColor = attributes.getColor(R.styleable.DaysOfWeekView_label_textColor, context.getResources().getColor(R.color.chart_label_text_color));

        attributes.recycle();
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
        invalidate();
    }

    public void setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize;
        invalidate();
    }

    private void initTextViews() {
        for (String label : labels) {
            TextView tv = new TextView(getContext());
            tv.setText(label);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
            tv.setTextColor(labelTextColor);
            tv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            addView(tv);
        }
    }

     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;

        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.measure(
                        MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST));
                height = Math.max(height, view.getMeasuredHeight());
            }
        }
        width = measuredWidth;
        setMeasuredDimension(measuredWidth, height);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int space = width/getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(i*space + getPaddingLeft(), getPaddingTop(), i*space + space - getPaddingRight(), view.getMeasuredHeight() - getPaddingBottom());
        }
    }
}
