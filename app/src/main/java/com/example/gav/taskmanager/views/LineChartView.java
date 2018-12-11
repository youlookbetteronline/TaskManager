package com.example.gav.taskmanager.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.gav.taskmanager.R;

public class LineChartView extends View {
    private int[] coordinates = new int[]{};
    private int chartBackgroundColor;
    private int lineColor;
    private int lineVerticalColor;
    private int dotBorderColor;
    private int dotColor;
    private int dotRadius;
    private Paint paintPath;
    private Paint paintVerticalLines;
    private Paint paintPathLine;
    private Paint paintExternalDot;
    private Paint paintInternalDot;
    private Path path;
    private Path pathLine;
    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;

    public LineChartView(Context context) {
        super(context);
        initDefaultAttrs(context);
        initVariables(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null)
            initAttrs(context, attrs);
        initVariables(context);

    }

    private void initVariables(Context context) {
        paintPath = new Paint();
        paintPathLine = new Paint();
        paintVerticalLines = new Paint();
        paintExternalDot = new Paint();
        paintInternalDot = new Paint();

        paintPath.setColor(chartBackgroundColor);

        paintPathLine.setColor(lineColor);
        paintPathLine.setStyle(Paint.Style.STROKE);
        paintPathLine.setStrokeWidth(4);

        paintVerticalLines.setColor(lineVerticalColor);
        paintVerticalLines.setStyle(Paint.Style.STROKE);
        paintVerticalLines.setStrokeWidth(3);

        paintInternalDot.setColor(dotColor);
        paintInternalDot.setStyle(Paint.Style.FILL);
        paintInternalDot.setAntiAlias(true);

        paintExternalDot.setColor(dotBorderColor);
        paintExternalDot.setStyle(Paint.Style.STROKE);
        paintExternalDot.setStrokeWidth(2);
        paintExternalDot.setAntiAlias(true);


        path = new Path();
        pathLine = new Path();

        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());

    }

    private void initDefaultAttrs(Context context) {
        coordinates = new int[]{0, 0, 0, 0, 0, 0, 0};
        chartBackgroundColor = context.getResources().getColor(R.color.chart_background_color);
        lineColor = context.getResources().getColor(R.color.chart_line_color);
        lineVerticalColor = context.getResources().getColor(R.color.chart_line_vertical_color);
        dotBorderColor = context.getResources().getColor(R.color.chart_line_color);
        dotColor = context.getResources().getColor(R.color.dot_color);
        dotRadius = 8;


    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        int id;
        id = attributes.getResourceId(R.styleable.LineChartView_coordinates, 0);
        this.coordinates = (id!=0)?getResources().getIntArray(id):coordinates;
        this.chartBackgroundColor = attributes.getColor(R.styleable.LineChartView_chart_background_color, getResources().getColor(R.color.chart_background_color));
        this.lineColor = attributes.getColor(R.styleable.LineChartView_line_color, getResources().getColor(R.color.chart_line_color));
        this.lineVerticalColor = attributes.getColor(R.styleable.LineChartView_line_vertical_color, getResources().getColor(R.color.chart_line_vertical_color));
        this.dotBorderColor = attributes.getColor(R.styleable.LineChartView_dot_border_color, getResources().getColor(R.color.chart_line_color));
        this.dotColor = attributes.getColor(R.styleable.LineChartView_dot_color, getResources().getColor(R.color.dot_color));
        this.dotRadius = attributes.getDimensionPixelSize(R.styleable.LineChartView_dot_radius, 8);

        attributes.recycle();
    }

    public void setChartData(int[] coordinates) {
        this.coordinates = coordinates.clone();
        invalidate();
    }
    public void chartBackgroundColor(int chartBackgroundColor) {
        this.chartBackgroundColor = chartBackgroundColor;
        invalidate();
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        invalidate();
    }

    public void setLineVerticalColor(int lineVerticalColor) {
        this.lineVerticalColor = lineVerticalColor;
        invalidate();
    }

    public void setDotBorderColor(int dotBorderColor) {
        this.dotBorderColor = dotBorderColor;
        invalidate();
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        invalidate();
    }

    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
        invalidate();
    }

    private float getYPos(float value) {
        float result;
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        int maxValue = getMax(coordinates);

        value = (value / maxValue) * height;

        result = height - value;

        result += getPaddingTop();

        return result;
    }

    private float getXPos(float value) {
        float result;
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = coordinates.length - 1;

        result = (value / maxValue) * width;

        result += getPaddingLeft();

        return result;
    }

    private int getMax(int[] array) {
        int result = 0;
        for (int i : array) {
            if (i > result) result = i;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.moveTo(getXPos(0), getYPos(coordinates[0]));
        pathLine.moveTo(getXPos(0), getYPos(coordinates[0]));
        for (int i = 1; i < coordinates.length; i++) {
            path.lineTo(getXPos(i), getYPos(coordinates[i]));
            pathLine.lineTo(getXPos(i), getYPos(coordinates[i]));
        }
        path.lineTo(getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        path.lineTo(getPaddingLeft(), getHeight() - getPaddingBottom());

        canvas.drawPath(path, paintPath);

        for (int p = 0; p < coordinates.length; p++) {
            float startX = getXPos(p);
            float startY = getYPos(coordinates[p]);
            float finishX = getXPos(p);
            int finishY = getHeight() - getPaddingBottom();
            canvas.drawLine(startX, startY, finishX, finishY, paintVerticalLines);

            canvas.drawCircle(startX, startY - dotRadius, dotRadius + 1, paintExternalDot);
            canvas.drawCircle(startX, startY - dotRadius, dotRadius, paintInternalDot);
        }

        canvas.drawPath(pathLine, paintPathLine);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
}
