package com.example.nare.paint;

import android.graphics.Path;

/**
 * Created by Nare on 28.03.2017.
 */

public class MyPath extends Path {
    private int color;
    private int strokeWidth;
    public Path path;

    public MyPath(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

}
