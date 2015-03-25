package com.example.jgreenb2.mymondrian;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private static Context mContext;
    private static int mSliderMax;

    final int numOfTiles=5;

    private View[] coloredTiles = new View[numOfTiles];
    private int[] tileColors = new int[numOfTiles];

    // velocity of the white tile is 0.0 so it never changes
    //private float[] colorVelocity = {1.0f, 2.0f, 1.5f, 0.0f, 3.0f};
    private float colorVelocity[] = new float[numOfTiles];

    public static final String TAG = "Mondrian";

    private static InfoDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SeekBar changeColorSlider;
        Random rnd = new Random();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mDialog = new InfoDialog();

        // assemble references to each tile
        // place the white tile in the first position
        coloredTiles[0] = findViewById(R.id.myRectangleView11);
        coloredTiles[1] = findViewById(R.id.myRectangleView12);
        coloredTiles[2] = findViewById(R.id.myRectangleView13);
        coloredTiles[3] = findViewById(R.id.myRectangleView21);
        coloredTiles[4] = findViewById(R.id.myRectangleView22);

        // retrieve the initial view colors
        // and initialize the colorVelocities to random values
        // in the interval [0.5, 3)
        for (int i=0;i<numOfTiles;i++) {
            ColorDrawable drawable = (ColorDrawable) coloredTiles[i].getBackground();
            tileColors[i] = drawable.getColor();
            colorVelocity[i] = (rnd.nextFloat()*2.5f)+0.5f;
        }

        // set up the seek bar
        changeColorSlider = (SeekBar) findViewById(R.id.colorSlider);
        mSliderMax = changeColorSlider.getMax();

        changeColorSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTileColors(coloredTiles, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

        // open a dialog
        if (id == R.id.info) {
            mDialog.show(getFragmentManager(), "infoDialog");

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

    // change the color of each tile except for the white one
    // at array pos 0

    void updateTileColors(View[] tiles, int val) {
        float hsv[] = new float[3];
        int rgba[] = new int[4];


        // use the slider to scale the hue and saturation
        // of each tile
        for (int i=0;i<numOfTiles;i++) {
            int2rgba(tileColors[i],rgba);   // decompose into rgb, alpha
            Color.RGBToHSV(rgba[0],rgba[1],rgba[2],hsv);    // convert to HSV

            // rotate hue through max of slider degrees
            hsv[0] = (hsv[0] + val*colorVelocity[i]) % 360.0f;

            // reduce the saturation by 25% (max of slider)
            hsv[1] = hsv[1] * (1.0f-(0.25f*val*colorVelocity[i]/((float) mSliderMax)));

            // convert back to a color-int
            int newColor = Color.HSVToColor(rgba[3],hsv);

            tiles[i].setBackgroundColor(newColor);
        }
    }

    // why this isn't built-in is a mystery to me
    void int2rgba(int c, int rgba[]) {

        rgba[0] = Color.red(c);
        rgba[1] = Color.green(c);
        rgba[2] = Color.blue(c);
        rgba[3] = Color.alpha(c);

        return;
    }
}
