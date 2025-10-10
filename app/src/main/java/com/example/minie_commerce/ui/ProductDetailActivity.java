package com.example.minie_commerce.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.minie_commerce.R;

public class ProductDetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailPrice;
    ImageView detailImage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        toolbar = findViewById(R.id.actionBar);
        setSupportActionBar(toolbar);

        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        detailPrice = findViewById(R.id.detailPrice);
        detailDesc = findViewById(R.id.detailDesc);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            detailTitle.setText(bundle.getString("name"));
            detailPrice.setText(String.valueOf(bundle.getFloat("price")));
            detailDesc.setText(bundle.getString("description"));

            String imageUrl = bundle.getString("image");
            Glide.with(this)
                    .load(imageUrl)
                    .into(detailImage);

        }
    }
}
