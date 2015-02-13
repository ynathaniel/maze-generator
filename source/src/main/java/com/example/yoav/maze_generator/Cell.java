/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/


package com.example.yoav.maze_generator;

//A typical cell in the grid
public class Cell {

    //coords variables
    private int xCoord;
    private int yCoord;

    //type = 1 means it's a Room, type = 2 means it's a wall
    //0 is default. 0 means it's neither and should not be used.
    public int type;

    Cell() {
        type = 0;
    }

    //return coords
    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    //set coords for the cell, two ints (one for x and one for y)
    public void setCoords(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    //These functions are overwritten by Room and Wall classes.
    //gets a boolean, true means the room has been visited, false means it hasn't.
    public void setVisited(boolean v) {
    }

    //returns the status of the visited, because Cell contains a copy that can be sent to Wall,
    // it's set to false.
    public boolean getVisited() {
        return false;
    }

    //gets a boolean, true means the wall is set, false means it's turned off.
    public void setWall(boolean b) {
    }

    //returns the status of the wall, because Cell contains a copy that can be sent to Room,
    // it's set to false.
    public boolean checkWall() {
        return false;
    }

    //returns the int of where the Room was build from, because Cell contains a copy that can be
    // sent to Wall, it's set to -1.
    public int getBuiltFrom() {
        return -1;
    }

    //a function in Room that takes an int to set from what direction the room has been built from.
    public void setBuiltFrom(int f) {
    }

}
