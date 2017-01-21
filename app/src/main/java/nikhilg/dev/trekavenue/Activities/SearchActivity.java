package nikhilg.dev.trekavenue.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import nikhilg.dev.trekavenue.Utils.FlowLayout;
import nikhilg.dev.trekavenue.Utils.IconView;
import nikhilg.dev.trekavenue.Utils.SharedPrefHandler;

public class SearchActivity extends AppCompatActivity implements NetworkRequestCallback {

    // Toolbar items
    private IconView backIcon, searchIcon, removeSearch;
    private EditText searchEditText;
    private TextView searchText;

    // recent searches items
    private LinearLayout recentSearchLayout;
    private TextView recentSearchesPlaceholder;
    private FlowLayout flowLayout;

    // result items
    private FrameLayout resultLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout progressBarLayout;
    private TextView noResultText;

    // data variables
    private TrekListResponseDto trekListResponseDto;
    private ArrayList<TrekDataDto> trekList;

    // adapter
    private HomeRecyclerAdapter mAdapter;

    private boolean destroyed;

    private String BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        destroyed = false;

        initLayout();

        setRecentSearches();

        setOnClickListeners();
    }

    private void initLayout() {
        backIcon = (IconView) findViewById(R.id.backIcon);
        searchIcon = (IconView) findViewById(R.id.searchIcon);
        removeSearch = (IconView) findViewById(R.id.removeSearch);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchText = (TextView) findViewById(R.id.searchText);

        recentSearchLayout = (LinearLayout) findViewById(R.id.recentSearchLayout);
        recentSearchesPlaceholder = (TextView) findViewById(R.id.recentSearchesPlaceholder);
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);

        resultLayout = (FrameLayout) findViewById(R.id.resultLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);
        noResultText = (TextView) findViewById(R.id.noResultText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        recentSearchLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.GONE);
        searchEditText.setVisibility(View.VISIBLE);
        searchText.setVisibility(View.GONE);
        searchIcon.setVisibility(View.VISIBLE);
        removeSearch.setVisibility(View.GONE);
    }

    private void setRecentSearches() {
        if (SharedPrefHandler.getInstance(this).getvalue(Constants.RECENT_SEARCHES) != null
                && !TextUtils.isEmpty(SharedPrefHandler.getInstance(this).getvalue(Constants.RECENT_SEARCHES))) {
            recentSearchesPlaceholder.setText(getString(R.string.recent_searches));
            addRecentSearches();
        } else {
            recentSearchesPlaceholder.setText(getString(R.string.no_recent_searches));
        }
    }

    private void setOnClickListeners() {
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = null;
                if (searchEditText.getText() != null) {
                    searchQuery = searchEditText.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(searchQuery)) {
                    String recentSearches = SharedPrefHandler.getInstance(SearchActivity.this).getvalue(Constants.RECENT_SEARCHES);
                    if (TextUtils.isEmpty(recentSearches))
                        recentSearches = searchQuery;
                    else {
                        List<String> recentSearchesArray = new ArrayList<String>();
                        recentSearchesArray.addAll(Arrays.asList(recentSearches.split(",")));
                        for (String s : recentSearchesArray) {
                            if (s.equalsIgnoreCase(searchQuery)) {
                                setupResultLayout(searchQuery, false);
                                return;
                            }
                        }
                        if (recentSearchesArray.size() >= 10) {
                            recentSearchesArray.remove(0);
                        }
                        recentSearchesArray.add(searchQuery);
                        recentSearches = TextUtils.join(",", recentSearchesArray);
                    }
                    SharedPrefHandler.getInstance(SearchActivity.this).addvalue(Constants.RECENT_SEARCHES, recentSearches);
                    setupResultLayout(searchQuery, true);
                } else {
                    Toast.makeText(SearchActivity.this, getString(R.string.enter_search_string), Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentSearchLayout.setVisibility(View.VISIBLE);
                resultLayout.setVisibility(View.GONE);
                searchEditText.setVisibility(View.VISIBLE);
                searchText.setVisibility(View.GONE);
                searchIcon.setVisibility(View.VISIBLE);
                removeSearch.setVisibility(View.GONE);
            }
        });
    }

    private void addRecentSearches() {
        String recentSearches = SharedPrefHandler.getInstance(this).getvalue(Constants.RECENT_SEARCHES);
        String[] recentSearchesArray = recentSearches.split(",");
        for (String search : recentSearchesArray) {
            addSearchQueryToFlowLayout(search);
        }
    }

    private void addSearchQueryToFlowLayout(String s, int... pos) {
        View v = LayoutInflater.from(this).inflate(R.layout.recent_search_item, null);

        TextView searchText = (TextView) v.findViewById(R.id.searchText);
        searchText.setText(s);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupResultLayout(((TextView) v).getText().toString(), false);
            }
        });

        if (pos.length > 0)
            flowLayout.addView(v, pos[0]);
        else
            flowLayout.addView(v);
    }

    private void setupResultLayout(String result, boolean addToFlowLayout) {
        if (addToFlowLayout)
            addSearchQueryToFlowLayout(result, 0);

        CommonLib.hideKeyboard(this);

        recentSearchLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        searchEditText.setVisibility(View.GONE);
        searchText.setVisibility(View.VISIBLE);
        searchIcon.setVisibility(View.GONE);
        removeSearch.setVisibility(View.VISIBLE);

        searchText.setText(result);

        BASE_URL = NetworkURLs.TREK_LIST_URL;

        trekList = new ArrayList<>();
        mAdapter = new HomeRecyclerAdapter(trekList, recyclerView, this);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int skipNumber = trekList.size();
                trekList.add(null);
                mAdapter.notifyItemInserted(trekList.size() - 1);
                String url = BASE_URL + "&skip=" + skipNumber;
                NetworkCallManager.getInstance().MakeJsonGetRequest(RequestTypes.TREK_LIST_MORE, url, SearchActivity.this, SearchActivity.class.getSimpleName());
            }
        });

        refreshView();
    }

    private void refreshView() {
        noResultText.setVisibility(View.GONE);
        NetworkCallManager.getInstance().MakeJsonGetRequest(RequestTypes.TREK_LIST, BASE_URL, this, HomeActivity.class.getSimpleName());
    }

    @Override
    public void onSuccess(int requestType, String response) {
        if (!destroyed) {
            if (requestType == RequestTypes.TREK_LIST) {
                progressBarLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                trekListResponseDto = new Gson().fromJson(response, TrekListResponseDto.class);
                trekList.clear();
                if (trekListResponseDto.getFlag() == Constants.NETWORK_CALL_SUCCESS_CODE) {
                    if (trekListResponseDto.getTrekData() != null && trekListResponseDto.getTrekData().size() > 0)
                        trekList.addAll(trekListResponseDto.getTrekData());
                    else
                        noResultText.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SearchActivity.this, trekListResponseDto.getMessage(), Toast.LENGTH_SHORT).show();
                    noResultText.setVisibility(View.VISIBLE);
                }
                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
            } else if (requestType == RequestTypes.TREK_LIST_MORE) {
                trekListResponseDto = new Gson().fromJson(response, TrekListResponseDto.class);
                if (trekList != null && trekList.size() > 0 && trekList.get(trekList.size() - 1) == null) {
                    trekList.remove(trekList.size() - 1);
                    mAdapter.notifyItemRemoved(trekList.size());
                }
                if (trekListResponseDto.getFlag() == Constants.NETWORK_CALL_SUCCESS_CODE
                        && trekListResponseDto.getTrekData() != null
                        && trekListResponseDto.getTrekData().size() > 0) {
                    trekList.addAll(trekListResponseDto.getTrekData());
                    mAdapter.setLoaded();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onFailure(int requestType, String error) {
        if (!destroyed && requestType == RequestTypes.TREK_LIST) {
            progressBarLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            noResultText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
