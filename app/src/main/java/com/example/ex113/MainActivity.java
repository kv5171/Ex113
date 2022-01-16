package com.example.ex113;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * The type Main activity.
 *
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version 1
 * @since 8/01/2022
 * short description:
 *      This activity let the user experience with internal files
 */
public class MainActivity extends AppCompatActivity {
    String stringText;
    TextView showView;
    EditText inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showView = (TextView) findViewById(R.id.showView);
        inputView = (EditText) findViewById(R.id.inputView);

        stringText = "";

        readText();
    }

    /**
     * Read the data.txt text file to get the old app run data.
     */
    public void readText()
    {
        try {
            FileInputStream fis = openFileInput("data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            stringText=sb.toString();
            if (stringText.equals(null))
                stringText = "";
            showView.setText(stringText);
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the input text to the file (and to the TextView).
     *
     * @param view the view
     */
    public void saveText(View view) {
        stringText += inputView.getText().toString() + " ";

        try {
            FileOutputStream fos = openFileOutput("data.txt",MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(stringText);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        showView.setText(stringText);
        inputView.setText("");
    }

    /**
     * Rest the TextView and EditText views.
     *
     * @param view the view
     */
    public void restText(View view) {
        inputView.setText("");
        showView.setText("");
        stringText = "";
    }

    /**
     * Exit app(save this app run data and finish).
     *
     * @param view the view
     */
    public void exitApp(View view) {
        saveText(view);
        finish();
    }

    /**
     * Create the options menu
     *
     * @param menu the menu
     * @return true if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * go to credits activity if it was clicked at the menu
     *
     * @param item the item in menu that was clicked
     * @return true if it success
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = (String) item.getTitle();

        if (title.equals("Credits"))
        {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }

        return true;
    }
}