/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

public class Room extends Cell {
    private int builtFrom;
    //avoid repetition
    private boolean visited;


    public Room() {
        visited = false;
        builtFrom = 0;
    }

    //return int builtFrom to see from which direction the room has been built.
    public int getBuiltFrom() {
        return builtFrom;
    }

    //takes an int to set builtFrom variable. sets the direction of which the cell was built from.
    //a builtFrom = 0 should mean it's the starting point or an error.
    //1 = left, 2 = up, 3 = right, 4 = down.
    public void setBuiltFrom(int f) {
        builtFrom = f;
    }

    //takes a boolean to set visited variable. true if the cell is being visited,
    // false if the cell is being unvisited.
    public void setVisited(boolean v) {
        visited = v;
    }

    //returns the boolean visited. true if the cell has benn visited,
    // false if the cell has yet to be visited.
    public boolean getVisited() {
        return visited;
    }
}
