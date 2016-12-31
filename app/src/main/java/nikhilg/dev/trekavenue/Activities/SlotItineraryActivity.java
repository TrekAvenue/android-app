package nikhilg.dev.trekavenue.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import nikhilg.dev.trekavenue.Adapters.OrganizerRecyclerAdapter;
import nikhilg.dev.trekavenue.Adapters.SlotItineraryAdapter;
import nikhilg.dev.trekavenue.Data.OrganizerDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

public class SlotItineraryActivity extends AppCompatActivity implements Constants {

    // Layout items
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private SlotItineraryAdapter mAdapter;

    private boolean showSlots;
    private String title;
    private OrganizerDto organizerDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_itinerary);

        if (getIntent().getExtras() == null)
            finish();

        title = getIntent().getStringExtra(TOOLBAR_TITLE);
        showSlots = getIntent().getBooleanExtra(SHOW_SLOTS, false);
        organizerDto = new Gson().fromJson(getIntent().getStringExtra(ORGANIZER_OBJECT), OrganizerDto.class);

        setUpActionBar(title);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mAdapter = new SlotItineraryAdapter(organizerDto, showSlots, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void setUpActionBar(String titleText) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(titleText);

        toolbar.setNavigationIcon(R.drawable.back_icon_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
