package com.example.android.newsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class expand_news extends AppCompatActivity {

    TextView expand_head,expand_source,expand_description;
    ImageView expand_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_news);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        expand_head=findViewById(R.id.expand_head);
        expand_source=findViewById(R.id.expand_source);
        expand_description=findViewById(R.id.expand_description);
        expand_pic=findViewById(R.id.expand_pic);
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();

        expand_head.setText(String.valueOf(bundle.get("title")));
        expand_source.setText(String.valueOf(bundle.get("source")));
        expand_description.setText(String.valueOf(bundle.get("content")));
        Picasso.with(expand_news.this).load(String.valueOf(bundle.get("image")))
                .into(expand_pic);
    }
}
