package net.slimevoid.towers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    private int difficulty=1,level=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty",difficulty);
        intent.putExtra("level",level);
        startActivity(intent);

    }

    public void onCheckboxClickedDifficulty(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox1_E:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox2_N);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.checkBox3_H);
                    tmp_CB.setChecked(false);
                    difficulty=1;
                    // Easy
                } else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox1_E);
                    tmp_CB.setChecked(true);
                }
                break;
            case R.id.checkBox2_N:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox1_E);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.checkBox3_H);
                    tmp_CB.setChecked(false);
                    difficulty=2;
                    // Normal
                } else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox2_N);
                    tmp_CB.setChecked(true);
                }
                break;
            case R.id.checkBox3_H:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox1_E);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.checkBox2_N);
                    tmp_CB.setChecked(false);
                    difficulty=3;
                    // Hard
                } else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.checkBox3_H);
                    tmp_CB.setChecked(true);
                }
                break;

        }
    }

    public void onCheckBoxClickedLevel(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.CB_level1:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level2);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level3);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level4);
                    tmp_CB.setChecked(false);
                    level = 1;
                }else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level1);
                    tmp_CB.setChecked(true);
                }break;
            case R.id.CB_level2:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level1);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level3);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level4);
                    tmp_CB.setChecked(false);
                    level = 2;
                }else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level2);
                    tmp_CB.setChecked(true);
                }break;
            case R.id.CB_level3:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level2);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level1);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level4);
                    tmp_CB.setChecked(false);
                    level = 3;
                }else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level3);
                    tmp_CB.setChecked(true);
                }break;
            case R.id.CB_level4:
                if (checked) {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level2);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level3);
                    tmp_CB.setChecked(false);
                    tmp_CB = (CheckBox) findViewById(R.id.CB_level1);
                    tmp_CB.setChecked(false);
                    level = 4;
                }else {
                    CheckBox tmp_CB = (CheckBox) findViewById(R.id.CB_level4);
                    tmp_CB.setChecked(true);
                }break;

        }
    }
}
