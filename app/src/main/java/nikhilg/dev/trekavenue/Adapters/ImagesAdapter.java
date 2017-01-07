package nikhilg.dev.trekavenue.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Interfaces.RandomCallback;
import nikhilg.dev.trekavenue.R;

/**
 * Created by nik on 17/12/16.
 */
public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ImageDto> imageURLList;
    private RandomCallback randomCallback;

    // variables for image width, height
    private int width, height;
    private int selectedPos;

    private Activity mContext;

    public ImagesAdapter(List<ImageDto> imageURLList, RandomCallback randomCallback, Activity mContext, int selectedPos) {
        this.imageURLList = imageURLList;
        this.randomCallback = randomCallback;
        this.mContext = mContext;
        this.selectedPos = selectedPos;

        width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        width = width/5;
        height = width;
    }

    @Override
    public int getItemCount() {
        return imageURLList == null ? 0 : imageURLList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_item, parent, false);
        viewHolder = new ImageItemViewHolder(view);
        ((ImageItemViewHolder) viewHolder).frameLayout.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageItemViewHolder) {
            ImageItemViewHolder vh = (ImageItemViewHolder) holder;
            Picasso.with(mContext)
                    .load(imageURLList.get(position).getUrl())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(vh.imageView);

            if (position == selectedPos) {
                vh.frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                vh.frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_light_color));
            }

            vh.frameLayout.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        if (pos != selectedPos) {
            int oldPos = selectedPos;
            selectedPos = pos;
            notifyItemChanged(oldPos);
            notifyItemChanged(selectedPos);
            Object[] data = new Object[1];
            data[0] = imageURLList.get(pos).getUrl();
            randomCallback.randomeMethod(data);
        }
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout frameLayout;
        public ImageView imageView;

        public ImageItemViewHolder(View view) {
            super(view);
            frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
            lp.setMargins(0, 0, 0, 16);
            frameLayout.setLayoutParams(lp);
        }
    }
}
