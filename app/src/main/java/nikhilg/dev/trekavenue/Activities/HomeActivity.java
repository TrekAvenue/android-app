package nikhilg.dev.trekavenue.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import nikhilg.dev.trekavenue.Adapters.HomeRecyclerAdapter;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Data.TrekListResponseDto;
import nikhilg.dev.trekavenue.Interfaces.NetworkRequestCallback;
import nikhilg.dev.trekavenue.Interfaces.OnLoadMoreListener;
import nikhilg.dev.trekavenue.Networking.NetworkCallManager;
import nikhilg.dev.trekavenue.Networking.NetworkURLs;
import nikhilg.dev.trekavenue.Networking.RequestTypes;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.CommonLib;
import nikhilg.dev.trekavenue.Utils.Constants;

public class HomeActivity extends AppCompatActivity implements NetworkRequestCallback {

    // layout items
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout progressBarLayout;

    // toolbar items
    private ImageView titleImageView;
    private TextView titleText;

    // data variables
    private TrekListResponseDto trekListResponseDto;
    private ArrayList<TrekDataDto> trekList;

    // adapter
    private HomeRecyclerAdapter mAdapter;

    private boolean destroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        destroyed = false;

        initLayout();

        trekList = new ArrayList<>();
        mAdapter = new HomeRecyclerAdapter(trekList, recyclerView, this);
        recyclerView.setAdapter(mAdapter);

        refreshView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (trekListResponseDto.getNextTrekId() != null && trekListResponseDto.getNextTrekId() > 0) {
                    trekList.add(null);
                    mAdapter.notifyItemInserted(trekList.size() - 1);
                    String url = NetworkURLs.TREK_LIST_URL + "?nextTrekId=" + trekListResponseDto.getNextTrekId();
                    NetworkCallManager.getInstance().MakeJsonGetRequest(RequestTypes.TREK_LIST_MORE, url, HomeActivity.this, HomeActivity.class.getSimpleName());
                }
            }
        });
    }

    private void initLayout() {
        titleImageView = (ImageView) findViewById(R.id.titleImageView);
        titleText = (TextView) findViewById(R.id.titleText);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setVisibility(View.GONE);
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    private void refreshView() {
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
            trekList.clear();
            if (trekListResponseDto.getFlag().equals(Constants.NETWORK_CALL_SUCCESS_CODE)) {
                trekList.addAll(trekListResponseDto.getTrekData());
                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
            } else {
                Toast.makeText(HomeActivity.this, trekListResponseDto.getMessage(), Toast.LENGTH_SHORT).show();
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
