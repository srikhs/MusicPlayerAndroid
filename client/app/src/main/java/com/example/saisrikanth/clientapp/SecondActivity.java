package com.example.saisrikanth.clientapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by srikh on 4/9/2016.
 */
public class SecondActivity extends Activity {
    ListView listView;
    //For populating transaction
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        listView = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        String[] array = i.getExtras().getStringArray("Project4Saisrikanth");
        if (array != null) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
            listView.setAdapter(adapter1);
        }

    }
}
