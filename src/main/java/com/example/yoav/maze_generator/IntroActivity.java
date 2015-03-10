/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

//Launcher activity
//default dimension is 11, which is 6x6
public class IntroActivity extends Activity implements AdapterView.OnItemSelectedListener {
    int dimens;
    Spinner spinner;
    String[] sizes = {"6x6", "7x7", "8x8", "9x9", "10x10", "11x11", "12x12", "13x13", "14x14"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
        dimens = 11;

        Spinner s = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(IntroActivity.this,
                android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(IntroActivity.this);
    }

    //Takes view
    //if the user selected to view the maze with the bitmap visuals, it comes here
    //sends the user to MainActivity with the dimensions and version the user selected
    //ver = 1 means the user chose the complete maze mode
    //ver = 2 means the user chose the random explore mode
    public void bitmap(View view) {
        Intent intent;
        int ver = 0;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("dimens", dimens);
        switch (view.getId()) {
            case R.id.button3:
                ver = 1;
                break;
            case R.id.button4:
                ver = 2;
                break;
        }
        intent.putExtra("version", ver);
        startActivity(intent);
    }

    //Takes view
    //if the user selected to view the maze with the LinearLayout visuals, it comes here
    //sends the user to CompleteActivity with the dimensions and version the user selected
    //ver = 1 means the user chose the complete maze mode
    //ver = 2 means the user chose the random explore mode
    public void choice(View view) {
        Intent intent;
        intent = new Intent(this, CompleteActivity.class);
        intent.putExtra("dimens", dimens);
        int ver = 0;
        switch (view.getId()) {
            case R.id.button1:
                ver = 1;
                break;
            case R.id.button2:
                ver = 2;
                break;
        }
        intent.putExtra("version", ver);
        startActivity(intent);
    }


    //Takes View
    //an older function to read clicks on Radio Buttons.
    //This is no longer implemented because a spinner is now being used.
    public void radioClick(View view) {
        /*boolean selected = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.rb6:
                if (selected){
                    dimens = 11;
                }
                break;
            case R.id.rb7:
                if (selected){
                    dimens = 13;
                }
                break;
            case R.id.rb8:
                if (selected){
                    dimens = 15;
                }
                break;
            case R.id.rb9:
                if (selected){
                    dimens = 17;
                }
                break;
            case R.id.rb10:
                if (selected){
                    dimens = 19;
                }
                break;
        }*/
    }


    //Takes AdapterView, View, int position, and long id.
    //Takes the position of the choice selected on the spinner, and correlates the choice to a
    // certain dimension for the grid.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos = position;
        switch (pos) {
            case 0:
                dimens = 11;
                break;
            case 1:
                dimens = 13;
                break;
            case 2:
                dimens = 15;
                break;
            case 3:
                dimens = 17;
                break;
            case 4:
                dimens = 19;
                break;
            case 5:
                dimens = 21;
                break;
            case 6:
                dimens = 23;
                break;
            case 7:
                dimens = 25;
                break;
            case 8:
                dimens = 27;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
