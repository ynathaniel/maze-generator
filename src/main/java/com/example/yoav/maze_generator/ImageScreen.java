/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import android.content.Context;
import android.widget.ImageView;


public class ImageScreen extends ImageView {
    private Context context;

    //sets up ImageScreen
    //takes Context as parameter.
    ImageScreen(Context c) {
        super(c);
        context = c;
    }

    //update the object using invalidate() method.
    public void onDraw() {
        this.invalidate();
    }

}
