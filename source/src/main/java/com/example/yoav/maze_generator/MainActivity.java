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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//MainActivity uses a Bitmap and a Canvas to draw the maze.

public class MainActivity extends ActionBarActivity {


    //version = 1 means the user chose the complete maze mode
    //version = 2 means the user chose the random explore mode
    //int d stands for the dimensions. for understanding, actual dimensions are (d+1)/2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze_grid);
        Intent intent = getIntent();
        int d = intent.getIntExtra("dimens", 0);
        final int version = intent.getIntExtra("version", 0);
        if (d == 0 || version == 0) {
            Intent intent1 = new Intent(this, IntroActivity.class);
            startActivity(intent1);
        }
        final ImageScreen imageScreen = new ImageScreen(this);
        final Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final LinearLayout page = drawInterface(imageScreen, canvas, bitmap);
        final Context context = this;
        final Grid grid = new Grid(d);

        Button nextButton = (Button) findViewById(R.id.nextMoveButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        final TextView stepsText = (TextView) findViewById(R.id.stepCount);

        //resets the grid and visuals
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.resetGrid();
                canvas.drawColor(0xFFFFFFFF);
                bitmapSurround(canvas);
                stepsText.setText("Steps = " + grid.getSteps());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (version == 2) {

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
                                canvas.drawColor(0xFFFFFFFF);
                                bitmapSurround(canvas);
                                stepsText.setText("Steps = " + grid.getSteps());
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
                    clickExperiment(grid, canvas, context, imageScreen);
                    //removes imageScreen and adds it again with an updated version.
                    page.removeView(imageScreen);
                    page.addView(imageScreen);
                }

                if (version == 1) {
                    //find complete maze
                    nextClick(grid, canvas, context, imageScreen);
                }
                stepsText.setText("Steps = " + grid.getSteps());
            }
        });

    }

    //Takes Grid, Canvas, Context, and ImageScreen as parameters
    //Calls explore() in Grid. Draws the walls around the current cell.
    public void clickExperiment(Grid grid, Canvas canvas, Context context, ImageScreen imageView) {
        grid.explore();
        int x = grid.getCurrent().getxCoord();
        int y = grid.getCurrent().getyCoord();
        int actualGrid = (grid.dimens + 1) / 2;
        int sizeUse = 1000 / actualGrid;

        if (x != 0) {
            if (grid.cells[x - 1][y].checkWall()) {
                DrawCell drawCell = new DrawCell(context, (x / 2) * sizeUse, (y / 2) * sizeUse, 10, sizeUse);
                drawCell.onDraw(canvas);
            }
        }
        if (x != grid.dimens - 1) {
            if (grid.cells[x + 1][y].checkWall()) {
                DrawCell drawCell = new DrawCell(context, ((x + 2) / 2) * sizeUse, (y / 2) * sizeUse, 10,
                        sizeUse);
                drawCell.onDraw(canvas);
            }
        }
        if (y != 0) {
            if (grid.cells[x][y - 1].checkWall()) {
                DrawCell drawCell = new DrawCell(context, (x / 2) * sizeUse, (y / 2) * sizeUse, sizeUse, 10);
                drawCell.onDraw(canvas);
            }
        }

        if (y != grid.dimens - 1) {
            if (grid.cells[x][y + 1].checkWall()) {
                DrawCell drawCell = new DrawCell(context, (x / 2) * sizeUse, ((y + 2) / 2) * sizeUse,
                        sizeUse, 10);
                drawCell.onDraw(canvas);
            }
        }
    }

    //Takes Grid, Canvas, Context, and ImageScreen as parameters
    //Repeats the following:
    //      Calls clickExperiment until maze cannot build anymore
    //      if maze is not complete, restart grid and visuals and make a recursive call.
    //      Mark the beginning and ending cells to make them distinct
    public void nextClick(Grid grid, Canvas canvas, Context context, ImageScreen imageView) {
        while (!grid.getCellStack().isEmpty()) {
            clickExperiment(grid, canvas, context, imageView);
        }
        if (!grid.getGoalCell().getVisited()) {
            grid.resetGrid();
            canvas.drawColor(0xFFFFFFFF);
            bitmapSurround(canvas);
            nextClick(grid, canvas, context, imageView);
            return;
        }
        int actualGrid = (grid.dimens + 1) / 2;
        int sizeUse = 1000 / actualGrid;
        int startX = grid.getStartCell().getxCoord();
        int startY = grid.getStartCell().getyCoord();
        DrawCell drawStartCell = new DrawCell(context, ((startX / 2) * sizeUse) + (sizeUse / 4),
                ((startY / 2) * sizeUse) + (sizeUse / 4), (sizeUse / 2), (sizeUse / 2));
        drawStartCell.onDraw(canvas);
        int endX = grid.getGoalCell().getxCoord();
        int endY = grid.getGoalCell().getyCoord();
        DrawCell drawGoalCell = new DrawCell(context, ((endX / 2) * sizeUse) + (sizeUse / 4),
                ((endY / 2) * sizeUse) + (sizeUse / 4), (sizeUse / 2), (sizeUse / 2));
        drawGoalCell.onDraw(canvas);


    }

    //Takes Canvas, Bitmap, and ImageScreen as parameters
    //Returns LinearLayout where ImageScreen was added to.
    //Sets up the bitmap ready on the screen.
    //calls bitmapSurround
    private LinearLayout drawInterface(ImageScreen imageView, Canvas canvas, Bitmap bitmap) {
        final LinearLayout page = (LinearLayout) findViewById(R.id.pageLayout);
        imageView = new ImageScreen(this);
        canvas.drawColor(Color.WHITE);
        imageView.setImageBitmap(bitmap);
        imageView.setPadding(200, 0, 20, 20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        imageView.setLayoutParams(params);
        page.addView(imageView);
        bitmapSurround(canvas);

        return page;
    }

    //Takes Canvas as parameter
    //Draws a black line on the edges of the canvas to enclose it.
    private void bitmapSurround(Canvas canvas) {
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 1000, 5, black);
        canvas.drawRect(0, 0, 5, 1000, black);
        canvas.drawRect(0, 995, 1000, 1000, black);
        canvas.drawRect(995, 0, 1000, 1000, black);
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
