package nikhilg.dev.trekavenue.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nikhilg.dev.trekavenue.R;

/**
 * Created by nik on 30/1/17.
 */
public class FilterTagParamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<String> tagList;
    private Activity mContext;

    public FilterTagParamAdapter(List<String> tagList, Activity mContext) {
        this.tagList = tagList;
        this.mContext = mContext;
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
            vh.filterTagText.setText(tagList.get(position));
            vh.filterTagText.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();

    }

    public class FilterTagViewHolder extends RecyclerView.ViewHolder {
        public TextView filterTagText;

        public FilterTagViewHolder(View view) {
            super(view);
            filterTagText = (TextView) view.findViewById(R.id.filterTagText);
        }
    }
}