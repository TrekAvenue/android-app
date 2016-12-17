package nikhilg.dev.trekavenue.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;

import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Data.TrekListResponseDto;
import nikhilg.dev.trekavenue.Interfaces.NetworkRequestCallback;
import nikhilg.dev.trekavenue.Networking.NetworkCallManager;
import nikhilg.dev.trekavenue.Networking.NetworkURLs;
import nikhilg.dev.trekavenue.Networking.RequestTypes;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

public class HomeActivity extends AppCompatActivity implements NetworkRequestCallback {

    // layout items
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout progressBarLayout;

    // data variables
    private TrekListResponseDto trekListResponseDto;
    private ArrayList<TrekDataDto> trekList;

    private boolean destroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        destroyed = false;

        initLayout();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeNetworkCall();
            }
        });

        trekList = new ArrayList<>();

        makeNetworkCall();
    }

    private void initLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);

        swipeRefreshLayout.setVisibility(View.GONE);
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    private void makeNetworkCall() {
        String url = NetworkURLs.TREK_LIST_URL;
        NetworkCallManager.getInstance().MakeJsonGetRequest(RequestTypes.TREK_LIST, url, this, HomeActivity.class.getSimpleName());
    }

    @Override
    public void onSuccess(int requestType, String response) {
        if (!destroyed) {
            progressBarLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            trekListResponseDto = new Gson().fromJson(response, TrekListResponseDto.class);

            if (trekListResponseDto.getFlag().equals(Constants.NETWORK_CALL_SUCCESS_CODE)) {

            } else {

            }
        }
    }

    @Override
    public void onFailure(int requestType, String error) {

    }

    @Override
    protected void onDestroy() {
        destroyed = true;
        super.onDestroy();
    }
}
