package nikhilg.dev.trekavenue.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nikhilg.dev.trekavenue.Activities.TrekDetailActivity;
import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Interfaces.OnLoadMoreListener;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

/**
 * Created by nik on 17/12/16.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<TrekDataDto> trekList;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // load more items
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    // variables for image width, height
    private int width, height;

    private Activity mContext;

    public HomeRecyclerAdapter(List<TrekDataDto> trekList, RecyclerView mRecyclerView, Activity mContext) {
        this.trekList = trekList;
        this.mContext = mContext;

        width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        height = width*2/5;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    isLoading = true;
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return trekList == null ? 0 : trekList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return trekList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trek_list_item, parent, false);
            viewHolder = new TrekListItemViewHolder(view);
            ((TrekListItemViewHolder) viewHolder).trekItem.setOnClickListener(this);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_layout, parent, false);
            viewHolder = new LoadingViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder vh = (LoadingViewHolder) holder;
            vh.progressBar.setIndeterminate(true);
        } else if (holder instanceof TrekListItemViewHolder) {
            TrekListItemViewHolder vh = (TrekListItemViewHolder) holder;
            TrekDataDto trekObject = trekList.get(position);

            for (ImageDto temp : trekObject.getImages()) {
                if (temp.getIndex() == 0) {
                    Picasso.with(mContext)
                            .load(temp.getUrl())
                            .placeholder(R.drawable.placeholder_5_2)
                            .error(R.drawable.placeholder_5_2)
                            .into(vh.imageView);
                    break;
                }
            }

            // set trek name and location
            vh.trekName.setText(trekObject.getName() + ", ");
            vh.trekLocation.setText(trekObject.getRegion());

            vh.trekItem.setTag(position);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        Intent intent = new Intent(mContext, TrekDetailActivity.class);
        intent.putExtra(Constants.TREK_OBJECT, new Gson().toJson(trekList.get(pos)));

        Pair<View, String> p1 = Pair.create((View) v.findViewById(R.id.imageView), "listImageTransition");
        Pair<View, String> p2 = Pair.create((View) v.findViewById(R.id.trekName), "trekNameTransition");
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(mContext, p1, p2);
        ActivityCompat.startActivity(mContext, intent, options.toBundle());

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    public class TrekListItemViewHolder extends RecyclerView.ViewHolder {
        public CardView trekItem;
        private TextView trekName, trekLocation;
        public ImageView imageView;

        public TrekListItemViewHolder(View view) {
            super(view);
            trekItem = (CardView) view.findViewById(R.id.trekItem);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            trekName = (TextView) view.findViewById(R.id.trekName);
            trekLocation = (TextView) view.findViewById(R.id.trekLocation);
        }
    }
}
