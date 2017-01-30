package nikhilg.dev.trekavenue.Activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import nikhilg.dev.trekavenue.Adapters.FilterTagParamAdapter;
import nikhilg.dev.trekavenue.Data.FilterObject;
import nikhilg.dev.trekavenue.Data.FilterParams;
import nikhilg.dev.trekavenue.Data.KeyValueObject;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.TApplication;

public class FilterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout filterParamsContainer;

    private FilterParams filterParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterParams = ((TApplication) getApplication()).getFilterParams();

        if (filterParams == null || filterParams.getFilters() == null || filterParams.getFilters().size() <= 0)
            finish();

        initLayout();
    }

    private void initLayout() {
        setUpToolbar();

        filterParamsContainer = (LinearLayout) findViewById(R.id.filterParamsContainer);

        for (int i=0; i<filterParams.getFilters().size(); i++) {
            FilterObject filter = filterParams.getFilters().get(i);
            switch (filter.getFilterType()) {
                case "SLIDER":
                    addSliderFilter(i, filter);
                    break;
                case "TAGS":
                    addTagFilter(i, filter);
                    break;
            }
        }

    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.filter_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addSliderFilter(int pos, FilterObject filterObject) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.filter_seekbar_layout, null);

        TextView title = (TextView) v.findViewById(R.id.filterParamTitle);
        title.setText(filterObject.getCriteriaName());

        int min, max;

        TextView minText, maxText;
        minText = (TextView) v.findViewById(R.id.min);
        maxText = (TextView) v.findViewById(R.id.max);

        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        try {
            min = filterObject.getFilterValues().getJSONObject("sliderValues").getInt("min");
            max = filterObject.getFilterValues().getJSONObject("sliderValues").getInt("max");
            minText.setText(String.valueOf(min));
            maxText.setText(String.valueOf(max));
        } catch (Exception e) {
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("nikhilseek", "progress : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        filterParamsContainer.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addTagFilter(int pos, FilterObject filterObject) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.filter_tag_layout, null);

        TextView title = (TextView) v.findViewById(R.id.filterParamTitle);
        title.setText(filterObject.getCriteriaName());

        // create list
        ArrayList<KeyValueObject> tagsList = new ArrayList<>();
        try {
            JSONArray tagArray = filterObject.getFilterValues().getJSONArray("tagValues");
            for (int j=0; j<tagArray.length(); j++) {
                KeyValueObject keyValueObject = new KeyValueObject();
                keyValueObject.setKey(tagArray.getJSONObject(j).getString("tagKey"));
                keyValueObject.setValue(tagArray.getJSONObject(j).getString("tagValue"));
                tagsList.add(keyValueObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        FilterTagParamAdapter mAdapter = new FilterTagParamAdapter(tagsList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);

        filterParamsContainer.addView(v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
