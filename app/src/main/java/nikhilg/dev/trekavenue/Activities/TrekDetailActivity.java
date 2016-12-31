package nikhilg.dev.trekavenue.Activities;

import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import nikhilg.dev.trekavenue.Adapters.ImagesAdapter;
import nikhilg.dev.trekavenue.Adapters.OrganizerRecyclerAdapter;
import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Interfaces.RandomCallback;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

public class TrekDetailActivity extends AppCompatActivity implements RandomCallback {

    // layout items

    // top images and toolbar items
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private FrameLayout topImageContainer;
    private ImageView imageView;
    private RecyclerView imagesRecyclerView;

    // location and difficulty items
    private TextView locationText, difficultyText, altitudeText, temperatureText;
    private LinearLayout locationLayout, difficultyLayout, altitudeLayout, temperatureLayout;

    // top images and toolbar helper items
    private ImagesAdapter mImagesAdapter;
    private int width, height;

    // Organizer recyclerview items
    private RecyclerView organizerRecyclerView;
    private OrganizerRecyclerAdapter mOrganizerAdapter;

    // data variables
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
        // handle toolbar title
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
        final Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf);
        collapsingToolbar.setTitle(trekObject.getName());

        // Setup top images and toolbar items and recyclerview
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        topImageContainer = (FrameLayout) findViewById(R.id.topImageContainer);
        topImageContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        imageView = (ImageView) findViewById(R.id.imageView);

        if (trekObject.getImages() != null && trekObject.getImages().size() > 0) {
            for (ImageDto temp : trekObject.getImages()) {
                if (temp.getIndex() == 0) {
                    loadImage(temp.getUrl());
                    break;
                }
            }
        } else {
            imageView.setImageResource(R.drawable.placeholder_5_2);
        }

        // image recyclerview below the collapsing toolbar
        imagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        int selectedPos = 0;
        if (trekObject.getImages() != null && trekObject.getImages().size() > 0) {
            for (ImageDto image : trekObject.getImages()) {
                if ( image.getIndex() == 0) {
                    selectedPos = trekObject.getImages().indexOf(image);
                    break;
                }
            }
            mImagesAdapter = new ImagesAdapter(trekObject.getImages(), this, this, selectedPos);
            imagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            imagesRecyclerView.setAdapter(mImagesAdapter);
            imagesRecyclerView.setNestedScrollingEnabled(false);
        }

        // setup location and difficulty items
        locationText = (TextView) findViewById(R.id.locationText);
        difficultyText = (TextView) findViewById(R.id.difficultyText);
        altitudeText = (TextView) findViewById(R.id.altitudeText);
        temperatureText = (TextView) findViewById(R.id.temperatureText);
        locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
        difficultyLayout = (LinearLayout) findViewById(R.id.difficultLayout);
        altitudeLayout = (LinearLayout) findViewById(R.id.altitudeLayout);
        temperatureLayout = (LinearLayout) findViewById(R.id.temperatureLayout);

        if (!TextUtils.isEmpty(trekObject.getRegion())) {
            locationText.setText(trekObject.getRegion());
        } else {
            locationLayout.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(trekObject.getDifficulty())) {
            difficultyText.setText(trekObject.getDifficulty());
        } else {
            difficultyLayout.setVisibility(View.GONE);
        }

        if (trekObject.getHighestAltitudeInFeet() != null && trekObject.getHighestAltitudeInFeet() > 0) {
            altitudeText.setText(trekObject.getHighestAltitudeInFeet() + " ft");
        } else {
            altitudeLayout.setVisibility(View.GONE);
        }

        if ((trekObject.getAverageTemperatureDayMaxCelcius() != null || trekObject.getAverageTemperatureNightMaxCelcius() != null)
                && (trekObject.getAverageTemperatureDayMinCelcius() != null || trekObject.getAverageTemperatureNightMinCelcius() != null)) {
            int highTemp = 0;
            if (trekObject.getAverageTemperatureDayMaxCelcius() != null && trekObject.getAverageTemperatureNightMaxCelcius() != null) {
                highTemp = trekObject.getAverageTemperatureDayMaxCelcius() > trekObject.getAverageTemperatureNightMaxCelcius()
                       ? trekObject.getAverageTemperatureDayMaxCelcius() : trekObject.getAverageTemperatureNightMaxCelcius();
            } else if (trekObject.getAverageTemperatureDayMaxCelcius() != null) {
                highTemp = trekObject.getAverageTemperatureDayMaxCelcius();
            } else if (trekObject.getAverageTemperatureNightMaxCelcius() != null) {
                highTemp = trekObject.getAverageTemperatureNightMaxCelcius();
            }

            int lowTemp = 0;
            if (trekObject.getAverageTemperatureDayMinCelcius() != null && trekObject.getAverageTemperatureNightMinCelcius() != null) {
                lowTemp = trekObject.getAverageTemperatureDayMinCelcius() < trekObject.getAverageTemperatureNightMinCelcius()
                        ? trekObject.getAverageTemperatureDayMinCelcius() : trekObject.getAverageTemperatureNightMinCelcius();
            } else if (trekObject.getAverageTemperatureDayMinCelcius() != null) {
                lowTemp = trekObject.getAverageTemperatureDayMinCelcius();
            } else if (trekObject.getAverageTemperatureNightMinCelcius() != null) {
                lowTemp = trekObject.getAverageTemperatureNightMinCelcius();
            }

            temperatureText.setText(lowTemp + " - " + highTemp + " \u00b0" + "C");
        } else {
            temperatureLayout.setVisibility(View.GONE);
        }

        // setup organizer recycler view
        if (trekObject.getOrganizers() != null && trekObject.getOrganizers().size() > 0) {
            organizerRecyclerView = (RecyclerView) findViewById(R.id.organizerRecyclerView);
            organizerRecyclerView.setNestedScrollingEnabled(false);
            organizerRecyclerView.setHasFixedSize(true);
            mOrganizerAdapter = new OrganizerRecyclerAdapter(trekObject.getOrganizers(), this);
            organizerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            organizerRecyclerView.setAdapter(mOrganizerAdapter);
        }
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
        appBarLayout.setExpanded(true);
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder_5_2)
                .error(R.drawable.placeholder_5_2)
                .into(imageView);
    }
}
