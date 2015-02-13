/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/


package com.example.yoav.maze_generator;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MapDrawer {
    private int dimens;
    private LinearLayout mazeLayout;
    private LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public MapRow mapRow[];
    Context x;

    //set up MapDrawer and create the correct number of MapRow objects in mapRow array and
    // adds each MapRow's LinearLayout to mazeLayout variable.
    //takes int num for dimensions of the grid.
    //takes context of the view.
    MapDrawer(int num, Context context) {
        dimens = num;
        x = context;
        mazeLayout = new LinearLayout(x);
        mazeLayout.setLayoutParams(p);
        mazeLayout.setOrientation(LinearLayout.VERTICAL);
        mazeLayout.setBackgroundColor(0xFF6AA2F0);
        mazeLayout.setGravity(Gravity.CENTER);


        mapRow = new MapRow[num];
        for (int i = 0; i < dimens; i++) {
            mapRow[i] = new MapRow(dimens, x);
            LinearLayout row = mapRow[i].getRowLayout();
            mazeLayout.addView(row);
        }
    }

    //takes int of location MapBox in array mapBox
    public MapRow getMapRowAt(int rowNum) {
        return mapRow[rowNum];
    }

    //returns mazeLayout, a LinearLayout variable.
    public LinearLayout getMazeLayout() {
        return mazeLayout;
    }

}
