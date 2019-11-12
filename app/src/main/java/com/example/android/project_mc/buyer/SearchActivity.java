package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.project_mc.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button search_text=findViewById(R.id.text_search);
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchActivity.this,searchText.class);
                startActivity(i);
            }
        });
        Button search_voice=findViewById(R.id.voice_search);
        search_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchActivity.this,searchVoice.class);
                startActivity(i);
            }
        });
    }
}
