package com.example.shrey.grupixupdated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void goToGallery(View view){
        Intent newActivity = new Intent(this, Gallery.class );
        startActivity(newActivity);
    }

    public void goToGroupedPictures(View view){
        Intent newActivity = new Intent(this, GroupedPictures.class);
        startActivity(newActivity);
    }
}
