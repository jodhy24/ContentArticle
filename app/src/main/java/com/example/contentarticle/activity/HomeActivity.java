package com.example.contentarticle.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.contentarticle.R;
import com.example.contentarticle.adapter.ContentAdapter;
import com.example.contentarticle.helper.AppDatabase;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, AddActivity.class));
                // Destroy Aplikasi
                finish();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        getMyContent();


    }

    private void getMyContent() {


        class GetContent extends AsyncTask<Void, Void, List<Content>> {

            @Override
            protected List<Content> doInBackground(Void... voids) {

                 List<Content> contentList =  DatabaseClient.getInstance(
                        getApplicationContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAll();



                return contentList;
            }

            @Override
            protected void onPostExecute(List<Content> contents) {
                super.onPostExecute(contents);

                ContentAdapter contentAdapter = new
                        ContentAdapter(HomeActivity.this, contents);
                recyclerView.setAdapter(contentAdapter);
            }
        }

        GetContent getContent = new GetContent();
        getContent.execute();

    }




}
