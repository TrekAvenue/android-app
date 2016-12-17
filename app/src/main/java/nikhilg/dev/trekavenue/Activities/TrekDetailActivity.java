package nikhilg.dev.trekavenue.Activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import nikhilg.dev.trekavenue.Adapters.ImagesAdapter;
import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Interfaces.RandomCallback;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;
import nikhilg.dev.trekavenue.Utils.IconView;

public class TrekDetailActivity extends AppCompatActivity implements RandomCallback {

    // layout items
    private CollapsingToolbarLayout collapsingToolbar;
    private FrameLayout topImageContainer;
    private ImageView imageView;
    private RecyclerView imagesRecyclerView;
    private ImagesAdapter mImagesAdapter;

    private int width, height;

    private TrekDataDto trekObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trek_detail);

        width = getWindowManager().getDefaultDisplay().getWidth();
        height = width*2/3;

        trekObject = new Gson().fromJson(getIntent().getStringExtra(Constants.TREK_OBJECT), TrekDataDto.class);

        setupToolbar();

        initLayout();
        setOnClickListener();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    private void initLayout() {
        topImageContainer = (FrameLayout) findViewById(R.id.topImageContainer);
        topImageContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        imageView = (ImageView) findViewById(R.id.imageView);

        for (ImageDto temp : trekObject.getImages()) {
            if (temp.getIndex() == 0) {
                loadImage(temp.getUrl());
                break;
            }
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
        final Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf);
        collapsingToolbar.setTitle(trekObject.getName());

        imagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        mImagesAdapter = new ImagesAdapter(trekObject.getImages(), this, this, 0);
        imagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        imagesRecyclerView.setAdapter(mImagesAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnClickListener() {

    }

    @Override
    public void randomeMethod(Object[] data) {
        String url = (String) data[0];
        loadImage(url);
    }

    private void loadImage(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView);
    }
}
