package nikhilg.dev.trekavenue.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nikhilg.dev.trekavenue.Data.KeyValueObject;
import nikhilg.dev.trekavenue.Interfaces.RandomCallback;
import nikhilg.dev.trekavenue.R;

/**
 * Created by nik on 30/1/17.
 */
public class FilterTagParamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<KeyValueObject> tagList;
    private Activity mContext;
    private String paramKey;
    private RandomCallback randomCallback;

    int selectedPosition = -1;

    public FilterTagParamAdapter(List<KeyValueObject> tagList, String paramKey, Activity mContext, RandomCallback randomCallback) {
        this.tagList = tagList;
        this.mContext = mContext;
        this.paramKey = paramKey;
        this.randomCallback = randomCallback;
    }

    @Override
    public int getItemCount() {
        return tagList == null ? 0 : tagList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_tag_layout, parent, false);
        RecyclerView.ViewHolder viewHolder = new FilterTagViewHolder(view);
        ((FilterTagViewHolder) viewHolder).filterTagText.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FilterTagViewHolder) {
            FilterTagViewHolder vh = (FilterTagViewHolder) holder;
            vh.filterTagText.setText(tagList.get(position).getValue());
            vh.filterTagText.setTag(position);

            if (selectedPosition == position) {
                vh.filterTagText.setBackgroundResource(R.drawable.recent_search_item_selected);
                vh.filterTagText.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                vh.filterTagText.setBackgroundResource(R.drawable.recent_search_item_background);
                vh.filterTagText.setTextColor(mContext.getResources().getColor(R.color.text_medium_gray));
            }
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        int oldPos = selectedPosition;
        selectedPosition = position;
        if (oldPos == selectedPosition) {
            selectedPosition = -1;
            notifyItemChanged(oldPos);
            randomCallback.randomMethod(new Object[]{paramKey, null});
            return;
        }
        if (oldPos >= 0) {
            notifyItemChanged(oldPos);
        }
        notifyItemChanged(selectedPosition);
        randomCallback.randomMethod(new Object[]{paramKey, tagList.get(selectedPosition).getKey()});
    }

    public class FilterTagViewHolder extends RecyclerView.ViewHolder {
        public TextView filterTagText;

        public FilterTagViewHolder(View view) {
            super(view);
            filterTagText = (TextView) view.findViewById(R.id.filterTagText);
        }
    }
}