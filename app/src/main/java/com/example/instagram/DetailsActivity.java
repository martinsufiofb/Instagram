package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    Post post;
    TextView tvTitle;
    TextView tvDescription;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = (TextView) findViewById(R.id.tvTitleDetails);
        tvDescription = (TextView) findViewById(R.id.tvDescriptionDetails);
        ivImage = (ImageView) findViewById(R.id.ivImageDetails);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvTitle.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        ParseFile image = post.getImage();
        if (image != null){
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
    }
}