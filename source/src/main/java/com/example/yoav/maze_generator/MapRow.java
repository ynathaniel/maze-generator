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

public class MapRow {
    private int dimens;
    private LinearLayout rowLayout;
    private LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
    public MapBox mapBox[];
    Context x;

    //set up MapRow and create the correct number of MapBox objects in mapBox array and
    // adds each MapBox's TextView to rowLayout variable.
    //takes int num for dimensions of the grid.
    //takes context of the view.
    MapRow(int num, Context context) {
        dimens = num;
        x = context;
        rowLayout = new LinearLayout(x);
        rowLayout.setPadding(0, 0, 0, 0);
        rowLayout.setLayoutParams(p);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        mapBox = new MapBox[dimens];
        for (int i = 0; i < dimens; i++) {
            mapBox[i] = new MapBox(x);
            rowLayout.addView(mapBox[i].getDrawBox());
        }
    }

    //takes int of location MapBox in array mapBox
    public MapBox getMapBoxAt(int b) {
        return mapBox[b];
    }

    //returns rowLayout, a LinearLayout variable.
    public LinearLayout getRowLayout() {
        return rowLayout;
    }
}