/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;


public class DrawCell extends View {

    private ShapeDrawable drawable;

    //sets up DrawCell object.
    //takes context of the object
    //takes the int x and y coordinates of the object
    //takes the ints w (width) and h (height) of the object
    public DrawCell(Context context, int x, int y, int w, int h) {
        super(context);
        drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(0xff74AC23);
        drawable.setBounds(x, y, w + x, y + h);
    }

    //Takes canvas as parameter
    //draws the object onto the canvas
    protected void onDraw(Canvas canvas) {
        drawable.draw(canvas);
    }

    //should the color  of the object need be changed.
    //paint is taken as parameter.
    protected void changeColor(Paint paint) {
        drawable.getPaint().set(paint);

    }
}
