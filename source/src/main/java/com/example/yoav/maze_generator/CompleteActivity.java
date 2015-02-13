/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//CompleteActivity uses a LinearLayouts and TextViews to draw the maze.
public class CompleteActivity extends ActionBarActivity {

    Context context = this;
    private int mCount = 0;

    //version = 1 means the user chose the complete maze mode
    //version = 2 means the user chose the random explore mode
    //int d stands for the dimensions. for understanding, actual dimensions are (d+1)/2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button counterButton = (Button) findViewById(R.id.buttonCount);
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        final TextView counterText = (TextView) findViewById(R.id.stepCount);
        final LinearLayout page = (LinearLayout) findViewById(R.id.pageLayout);

        Intent intent = getIntent();
        int d = intent.getIntExtra("dimens", 0);
        final int version = intent.getIntExtra("version", 0);
        if (d == 0 || version == 0) {
            Intent intent1 = new Intent(this, IntroActivity.class);
            startActivity(intent1);
        }
        final Grid grid = new Grid(d);
        final MapDrawer mapDrawer = new MapDrawer(grid.dimens, this);
        LinearLayout maze = mapDrawer.getMazeLayout();

        page.addView(maze);
        drawItAll(grid, mapDrawer);

        //resets the grid and visuals
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.resetGrid();
                drawItAll(grid, mapDrawer);
                counterText.setText("Steps = " + grid.getSteps());
            }
        });

        counterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (version == 1) {
                    //prepares the pop up alert message
                    if (grid.getCellStack().isEmpty() && !grid.getGoalCell().getVisited()) {
                        AlertDialog.Builder alertBox = new AlertDialog.Builder(context);
                        alertBox.setTitle("Alert! No More Moves!");
                        alertBox.setMessage("Maze finished building but the end point has not " +
                                "been reached.");
                        alertBox.setCancelable(false);
                        alertBox.setPositiveButton("Try Maze Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                grid.resetGrid();
                                drawItAll(grid, mapDrawer);
                                counterText.setText("Steps = " + grid.getSteps());
                            }
                        });
                        alertBox.setNegativeButton("I Don't Care", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertBox.create();
                        alertBox.show();
                    }
                    gridExplore(grid, mapDrawer);
                }
                if (version == 2) {
                    //reset grid, reset screen, find complete maze
                    grid.resetGrid();
                    drawItAll(grid, mapDrawer);
                    nextStep(grid, mapDrawer);
                }
                counterText.setText("Steps = " + grid.getSteps());
            }
        });
    }

    //Takes Grid and MapDrawer as parameters. Sets up the visuals ready for message generation.
    public void drawItAll(Grid grid, MapDrawer mapDrawer) {
        int curX = grid.getCurrent().getxCoord();
        int curY = grid.getCurrent().getyCoord();

        for (int i = 0; i < grid.dimens; i++) {
            MapRow mapRow = mapDrawer.getMapRowAt(i);

            for (int o = 0; o < grid.dimens; o++) {
                MapBox mapBox = mapRow.getMapBoxAt(o);
                TextView tBox = mapBox.getDrawBox();

                if (((o % 2) == 0) && ((i % 2) == 0)) {
                    //tBox.setText("(" + o/2 + "," + i/2 + ")");
                    //if (curX == o && curY == i)
                    //{
                    //tBox.setBackgroundColor(0xFF32A2D8);
                    //}
                    //else {
                    tBox.setPadding(36, 15, 36, 15);
                    tBox.setBackgroundColor(0xFFFFFFFF);
                    //}
                } else {
                    //vertical wall
                    tBox.setBackgroundColor(0xFFFFFFFF);
                    if ((o % 2 != 0) && (i % 2 == 0)) {
                        //tBox.setBackgroundColor(Color.LTGRAY);
                        tBox.setPadding(5, 15, 5, 15);
                    } else {
                        //adjust row sizing
                        mapRow.getRowLayout().setPadding(0, -13, 0, -13);
                        //horizontal wall
                        if ((o % 2 == 0) && (i % 2 != 0)) {
                            //tBox.setBackgroundColor(Color.LTGRAY);
                            tBox.setPadding(36, 0, 36, 0);
                        }
                        //small areas between walls
                        else {
                            //tBox.setBackgroundColor(Color.WHITE);
                            tBox.setPadding(5, 0, 5, 0);
                        }
                    }
                }
            }
        }
    }

    //Takes Grid and MapDrawer as parameters.
    //Calls explore() in Grid. Draws the walls around the current cell.
    public void gridExplore(Grid grid, MapDrawer mapDrawer) {
        grid.explore();
        Cell current = grid.getCurrent();
        int curX = current.getxCoord();
        int curY = current.getyCoord();
        //mapDrawer.getMapRowAt(curY).getMapBoxAt(curX).drawBox.setBackgroundColor(0xFF6AA2F0);
        if (curX != 0) {
            if (grid.cells[curX - 1][curY].checkWall() == true) {
                mapDrawer.getMapRowAt(curY).getMapBoxAt(curX - 1).getDrawBox().setBackgroundColor
                        (Color.BLACK);
            }
        }
        if (curY != 0) {
            if (grid.cells[curX][curY - 1].checkWall() == true) {
                mapDrawer.getMapRowAt(curY - 1).getMapBoxAt(curX).getDrawBox().setBackgroundColor
                        (Color.BLACK);
            }
        }
        if (curX != grid.dimens - 1) {
            if (grid.cells[curX + 1][curY].checkWall() == true) {
                mapDrawer.getMapRowAt(curY).getMapBoxAt(curX + 1).getDrawBox().setBackgroundColor
                        (Color.BLACK);
            }
        }
        if (curY != grid.dimens - 1) {
            if (grid.cells[curX][curY + 1].checkWall() == true) {
                mapDrawer.getMapRowAt(curY + 1).getMapBoxAt(curX).getDrawBox().setBackgroundColor
                        (Color.BLACK);
            }
        }
    }

    //Takes Grid and MapDrawer as parameters.
    //Repeats the following:
    //      Calls gridExplore until maze cannot build anymore
    //      Mark the beginning and ending cells to make them distinct
    //      if maze is not complete, restart grid and visuals and make a recursive call.
    public void nextStep(Grid grid, MapDrawer mapDrawer) {
        int areaHas = 0;
        while (!grid.getCellStack().isEmpty()) {
            gridExplore(grid, mapDrawer);
            areaHas++;
        }
        int startY = grid.getStartCell().getyCoord();
        int startX = grid.getStartCell().getxCoord();
        mapDrawer.getMapRowAt(startY).getMapBoxAt(startX).getDrawBox().setBackgroundColor(0xFF06C709);
        int endY = grid.getGoalCell().getyCoord();
        int endX = grid.getGoalCell().getxCoord();
        mapDrawer.getMapRowAt(endY).getMapBoxAt(endX).getDrawBox().setBackgroundColor(0xFFDFE12C);
        int area = grid.dimens / 2;
        area = area * area;
        int areaNeeded = area;
        if (!grid.getGoalCell().getVisited() || areaHas <= areaNeeded) {
            grid.resetGrid();
            drawItAll(grid, mapDrawer);
            nextStep(grid, mapDrawer);
        }
        //paintTheRest(grid, mapDrawer);
    }

    //Takes Grid and MapDrawer as parameters.
    //A working optional feature currently not implemented.
    //Paints black areas completely sealed off by walls.
    public void paintTheRest(Grid grid, MapDrawer mapDrawer) {
        for (int i = 0; i < grid.dimens; i++) {
            for (int o = 0; o < grid.dimens; o++) {
                if ((o % 2 == 0) && (i % 2 == 0)) {
                    if (!grid.cells[o][i].getVisited()) {
                        mapDrawer.getMapRowAt(i).getMapBoxAt(o).getDrawBox().setBackgroundColor(Color
                                .BLACK);
                    }
                } else if ((o % 2 == 0) && (i % 2 != 0)) {
                    if (!grid.cells[o][i + 1].getVisited() && !grid.cells[o][i - 1].getVisited()) {
                        mapDrawer.getMapRowAt(i).getMapBoxAt(o).getDrawBox().setBackgroundColor(Color
                                .BLACK);
                    }
                } else if ((o % 2 != 0) && (i % 2 == 0)) {
                    if (!grid.cells[o + 1][i].getVisited() && !grid.cells[o - 1][i].getVisited()) {
                        mapDrawer.getMapRowAt(i).getMapBoxAt(o).getDrawBox().setBackgroundColor(Color
                                .BLACK);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
