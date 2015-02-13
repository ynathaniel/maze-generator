/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

public class Wall extends Cell {
    private boolean state = false;

    //sets the status of the wall. true means the wall should be set,
    // false means it should be turned off.
    public void setWall(boolean b) {
        state = b;
    }

    //returns the status of the wall. true means the wall has been set.
    public boolean checkWall() {
        return state;
    }
}