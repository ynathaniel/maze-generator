/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapBox {
    private TextView drawBox;
    private LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Context x;

    //sets up the MapBox object. Sets default settings to TextView drawBox.
    //takes Context as parameter.
    MapBox(Context context) {
        x = context;
        drawBox = new TextView(x);
        drawBox.setLayoutParams(p);
        drawBox.setText("");
        drawBox.setPadding(8, 15, 8, 15);
    }

    //returns drawBox, a TextView variable.
    public TextView getDrawBox() {
        return drawBox;
    }

}
