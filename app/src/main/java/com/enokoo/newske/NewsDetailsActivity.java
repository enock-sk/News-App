package com.enokoo.newske;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
private TextView titleTV,subDescTV,contentTV;
private ImageView newsIV;
private Button readNews;

    String title,desc,image,url,context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
  title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        context=getIntent().getStringExtra("context");
        image=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");
titleTV=findViewById(R.id.idTVTitle);
subDescTV=findViewById(R.id.heading);
contentTV=findViewById(R.id.contentt);
newsIV=findViewById(R.id.idIVNews);
readNews=findViewById(R.id.btn);
titleTV.setText(title);
subDescTV.setText(desc);
contentTV.setText(context);
        Picasso.get().load(image).into(newsIV);
        readNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i=new Intent(Intent.ACTION_VIEW);
             i.setData(Uri.parse(url));
startActivity(i);
            }
        });
    }
}