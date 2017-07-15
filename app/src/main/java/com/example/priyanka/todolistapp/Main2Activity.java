package com.example.priyanka.todolistapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.szugyi.circlemenu.view.CircleLayout;

import static com.example.priyanka.todolistapp.R.id.imageView;
import static com.squareup.picasso.Picasso.with;

public class Main2Activity extends AppCompatActivity {
    CircleLayout circle;
    FloatingActionButton GYM;
    FloatingActionButton BIRHTDAY;
    FloatingActionButton ANNIVERSARY;
    FloatingActionButton MEETING;
    FloatingActionButton LASTDATE;
    FloatingActionButton EXTRAS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        circle=(CircleLayout) findViewById(R.id.circle);
        GYM=(FloatingActionButton) findViewById(R.id.GYM) ;
        BIRHTDAY=(FloatingActionButton) findViewById(R.id.BIRHTDAY) ;
        ANNIVERSARY=(FloatingActionButton) findViewById(R.id.ANNIVERSARY) ;
        MEETING=(FloatingActionButton) findViewById(R.id.MEETING) ;
        LASTDATE=(FloatingActionButton) findViewById(R.id.LASTDATE) ;
        EXTRAS=(FloatingActionButton) findViewById(R.id.EXTRAS) ;
        circle.setFirstChildPosition(CircleLayout.FirstChildPosition.EAST);
        circle.isRotating();
        GYM=(FloatingActionButton) findViewById(R.id.GYM);
        circle.setOnCenterClickListener(new CircleLayout.OnCenterClickListener() {
            @Override
            public void onCenterClick() {
                Intent i=new Intent(Main2Activity.this,MainActivity.class);
                i.putExtra("start",AllCategories.SCHEDULE_EXTRAS);
                startActivity(i);
            }
        });
    }

    public void clickDone(View view) {
        FloatingActionButton fab=(FloatingActionButton) view;
        Intent i=new Intent(Main2Activity.this,MainActivity.class);
        if(fab.getId()==GYM.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_GYM);
            startActivity(i);
        }
        if(fab.getId()==LASTDATE.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_LASTDATE);
            startActivity(i);
        }
        if(fab.getId()==ANNIVERSARY.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_ANNIVERSARY);
            startActivity(i);
        }
        if(fab.getId()==MEETING.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_MEETING);
            startActivity(i);
        }
        if(fab.getId()==BIRHTDAY.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_BIRTHDAY);
            startActivity(i);
        }
        if(fab.getId()==EXTRAS.getId()){
            i.putExtra("start",AllCategories.SCHEDULE_EXTRAS);
            startActivity(i);
        }
    }
}

