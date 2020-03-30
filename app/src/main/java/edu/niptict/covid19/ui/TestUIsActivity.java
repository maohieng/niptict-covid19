package edu.niptict.covid19.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hsalf.smileyrating.SmileyRating;

import edu.niptict.covid19.R;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/30/2020
 */
public class TestUIsActivity extends AppCompatActivity {

    private static final String TAG = "TestUIsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ui);

        SmileyRating rating = findViewById(R.id.smileyRating2);
        rating.setSmileySelectedListener(type -> {
            Log.i(TAG, "onSmileySelected(): " + type);
        });

        // Customize
        rating.setMaxSmiley(3);
        rating.setReverseSmiley(true);
        rating.setRating(1);
    }
}
