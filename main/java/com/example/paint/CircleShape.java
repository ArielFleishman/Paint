package com.example.paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class  CircleShape extends ClosedShape {

    private int xEnd;
    private int yEnd;
    private float cx;
    private float cy;
    private double rad;

    public CircleShape(int x, int y, String color,int width, boolean fill) {
        super(x, y, color,width,fill);
        xEnd = x;
        yEnd = y;
    }

    @Override
    public void updatePoint(int xe, int ye) {
        xEnd = xe;
        yEnd = ye;
        cx=(x+xEnd)/2;
        cy=(y+yEnd)/2;
        rad=Math.sqrt(Math.pow(xEnd-x,2)+Math.pow(yEnd-y,2));
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        if(fill==true)
            paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, (float) rad, paint);
    }
    @Override
    public double GetSurface(){
        return Math.pow(rad,2)*Math.PI;
    }

}