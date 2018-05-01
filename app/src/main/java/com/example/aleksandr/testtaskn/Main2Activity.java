package com.example.aleksandr.testtaskn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    DBHelper dbHelper;
    private TextView textView, textView2,textView3,textView10;

    List<Place> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DBHelper(this);

        setInitialData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, places);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

    }


    private void setInitialData(){


    }

}
