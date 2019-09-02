package com.mayank.atg_internship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    RelativeLayout relativeLayout;

    RecyclerView recyclerView;
    TvShowAdapter tvShowAdapter;
    ArrayList<TvShow> tvShows = new ArrayList<>();

    public static final String[] TvShows =
            {       "Breaking Bad",
                    "Rick and Morty",
                    "FRIENDS",
                    "Sherlock",
                    "Stranger Things"};

    public static final String[] TvShowImgs =
            {   "https://m.media-amazon.com/images/M/MV5BMjhiMzgxZTctNDc1Ni00OTIxLTlhMTYtZTA3ZWFkODRkNmE2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg" ,
                    "https://cdn.shopify.com/s/files/1/0191/7850/products/RICKMORTY_50_-_COVER_A_SOLICIT_WEB_1024x1024.jpg?v=1559080783",
                    "https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_UY268_CR0,0,182,268_AL_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BMWY3NTljMjEtYzRiMi00NWM2LTkzNjItZTVmZjE0MTdjMjJhL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTQ4NTc5OTU@._V1_UX182_CR0,0,182,268_AL_.jpg",
                   "https://m.media-amazon.com/images/M/MV5BZGExYjQzNTQtNGNhMi00YmY1LTlhY2MtMTRjODg3MjU4YTAyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX182_CR0,0,182,268_AL_.jpg"
            };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPopupBtn = findViewById(R.id.showPopupBtn);
        linearLayout1 = findViewById(R.id.linearLayout1);
        closePopupBtn = findViewById(R.id.closePopupBtn);
        relativeLayout = findViewById(R.id.rel_layout1);

        showPopupBtn.setOnClickListener(v -> {
            closePopupBtn.setVisibility(View.VISIBLE);
            relativeLayout.getBackground().setAlpha(200);
//            Blurry.with(getApplicationContext())
//                    .radius(2)
//                    .sampling(2)
//                    .color(Color.argb(0, 255, 255, 255))
//                    .async()
//                    .onto(relativeLayout);
            LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            @SuppressLint("InflateParams")
            View customView = layoutInflater.inflate(R.layout.location_popup, null);

            popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, -0, -650);

        });

        for (int i = 0; i < TvShows.length; i++) {
            TvShow tvShow = new TvShow();
            tvShow.setTvshow(TvShows[i]);
            tvShow.setImgTvshow(TvShowImgs[i]);

            tvShows.add(tvShow);
        }


        tvShowAdapter = new TvShowAdapter(tvShows);

        recyclerView = findViewById(R.id.TvShows);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tvShowAdapter);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);
    }

    public void ClosePopup(View view) {
        popupWindow.dismiss();
        closePopupBtn.setVisibility(View.INVISIBLE);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.Transparent));
    }
}