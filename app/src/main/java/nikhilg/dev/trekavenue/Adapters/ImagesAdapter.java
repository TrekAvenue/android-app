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

import java.util.List;

import nikhilg.dev.trekavenue.Activities.TrekDetailActivity;
import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.Interfaces.OnLoadMoreListener;
import nikhilg.dev.trekavenue.Interfaces.RandomCallback;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

/**
 * Created by nik on 17/12/16.
 */
public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ImageDto> imageURLList;
    private RandomCallback randomCallback;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_MORE = 1;

    // variables for image width, height
    private int width, height;
    private boolean showAll = false;
    private int selectedIndex;

    private Activity mContext;

    public ImagesAdapter(List<ImageDto> imageURLList, RandomCallback randomCallback, Activity mContext, int selectedIndex) {
        this.imageURLList = imageURLList;
        this.randomCallback = randomCallback;
        this.mContext = mContext;
        this.selectedIndex = selectedIndex;

        width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        width = width/5;
        height = width;
    }

    @Override
    public int getItemCount() {
        if (imageURLList == null) {
            return 0;
        } else if (imageURLList.size() < 5) {
            return imageURLList.size();
        } else if (showAll) {
            return imageURLList.size();
        } else {
            return 4;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 3 && imageURLList.size() > 4 && !showAll) {
            return VIEW_TYPE_MORE;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_item, parent, false);
            viewHolder = new ImageItemViewHolder(view);
            ((ImageItemViewHolder) viewHolder).frameLayout.setOnClickListener(this);
        } else if (viewType == VIEW_TYPE_MORE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_all_item, parent, false);
            viewHolder = new ViewAllViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewAllViewHolder) {
            ViewAllViewHolder vh = (ViewAllViewHolder) holder;
            vh.viewAllLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAll = true;
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof ImageItemViewHolder) {
            ImageItemViewHolder vh = (ImageItemViewHolder) holder;
            Picasso.with(mContext)
                    .load(imageURLList.get(position).getUrl())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(vh.imageView);

            if (imageURLList.get(position).getIndex() == selectedIndex) {
                vh.frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                vh.frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.text_light_gray));
            }

            vh.frameLayout.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        if (selectedIndex != imageURLList.get(pos).getIndex()) {
            selectedIndex = imageURLList.get(pos).getIndex();
            Object[] data = new Object[1];
            data[0] = imageURLList.get(pos).getUrl();
            randomCallback.randomeMethod(data);
            notifyDataSetChanged();
        }
    }

    public class ViewAllViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout viewAllLayout;

        public ViewAllViewHolder(View view) {
            super(view);
            viewAllLayout = (FrameLayout) view.findViewById(R.id.viewAllLayout);
            viewAllLayout.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        }
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout frameLayout;
        public ImageView imageView;

        public ImageItemViewHolder(View view) {
            super(view);
            frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        }
    }
}
