package com.example.nare.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyView extends View {

    private static final
    float Touch_tolerance = 4;
    private float mX,mY;
    List<MyPath> myPaths;
    Paint paint;
    Path mpath;
    int curentcolor;
    int curentsize;
    Bitmap backbit,bitmap1;


    public void setBackbit(Bitmap backbit) {
        this.backbit = backbit;

        invalidate();
    }

    public void setCurentcolor(int curentcolor) {
        this.curentcolor = curentcolor;
    }

    public void setCurentsize(int curentsize) {
        this.curentsize = curentsize;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        myPaths = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);


        curentcolor= Color.BLACK;
        curentsize=10;

    }
    public void cleanBac(){
        myPaths.clear();
        invalidate();
    }
    public void undo(){

        try {
            myPaths.remove(myPaths.size()-1);
        }
        catch (Exception e){

        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        try {
            int a = backbit.getHeight()/getHeight();
            bitmap1 = Bitmap.createScaledBitmap(backbit,backbit.getWidth()/a,getHeight(),true);
            canvas.drawBitmap(bitmap1,(getWidth()-bitmap1.getWidth())/2,(getHeight()-bitmap1.getHeight())/2,paint);

        }
        catch (Exception e){

        }
        for (MyPath myPath:myPaths) {
            paint.setColor(myPath.getColor());
            paint.setStrokeWidth(myPath.getStrokeWidth());
            paint.setMaskFilter(null);
            canvas.drawPath(myPath.path,paint);
        }
    }

    private void touchStart(float x, float y) {
        mpath = new Path();
        MyPath myPath = new MyPath(curentcolor, curentsize,mpath);
        myPaths.add(myPath);

        mpath.reset();
        mpath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x,float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);


            if (dx >= Touch_tolerance || dy >= Touch_tolerance) {
                mpath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }

    }
    public void drawbit(Bitmap b){
        Canvas canvas = new Canvas();
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(b,10,10,paint);
        b.recycle();
    }

    private void touchUp(){
        mpath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    public Bitmap asBitmap(){
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        Bitmap drawingCache = Bitmap.createBitmap(getDrawingCache());
        setDrawingCacheEnabled(false);
        return drawingCache;
    }
}
