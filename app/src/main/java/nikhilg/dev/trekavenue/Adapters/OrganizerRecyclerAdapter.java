package nikhilg.dev.trekavenue.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import nikhilg.dev.trekavenue.Activities.SlotItineraryActivity;
import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.OrganizerDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

/**
 * Created by nik on 24/12/16.
 */
public class OrganizerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<OrganizerDto> organizerList;
    private Activity mContext;

    public OrganizerRecyclerAdapter(List<OrganizerDto> organizerList, Activity mContext) {
        this.organizerList = organizerList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return organizerList == null ? 0 : organizerList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_organizer_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new OrganizerViewHolder(view);
        ((OrganizerViewHolder) viewHolder).bookButton.setOnClickListener(this);
        ((OrganizerViewHolder) viewHolder).availableSlots.setOnClickListener(this);
        ((OrganizerViewHolder) viewHolder).itinerary.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrganizerViewHolder) {
            OrganizerViewHolder vh = (OrganizerViewHolder) holder;
            vh.organizerName.setText(organizerList.get(position).getOrganizerName());
            vh.durationText.setText("Duration - " + organizerList.get(position).getDurationDays() + " days");
            vh.priceText.setText("Price - \u20B9" + organizerList.get(position).getPriceInr());

            vh.bookButton.setTag(position);
            vh.availableSlots.setTag(position);
            vh.itinerary.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.bookButton:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(organizerList.get(position).getBookingLink()));
                mContext.startActivity(browserIntent);
                break;
            case R.id.availableSlots:
                Intent slotIntent = new Intent(mContext, SlotItineraryActivity.class);
                slotIntent.putExtra(Constants.TOOLBAR_TITLE, organizerList.get(position).getOrganizerName() + " - Slots");
                slotIntent.putExtra(Constants.SHOW_SLOTS, true);
                slotIntent.putExtra(Constants.ORGANIZER_OBJECT, new Gson().toJson(organizerList.get(position)));
                mContext.startActivity(slotIntent);
                break;
            case R.id.itinerary:
                Intent itineraryIntent = new Intent(mContext, SlotItineraryActivity.class);
                itineraryIntent.putExtra(Constants.TOOLBAR_TITLE, organizerList.get(position).getOrganizerName() + " - Itinerary");
                itineraryIntent.putExtra(Constants.SHOW_SLOTS, false);
                itineraryIntent.putExtra(Constants.ORGANIZER_OBJECT, new Gson().toJson(organizerList.get(position)));
                mContext.startActivity(itineraryIntent);
                break;
        }
    }

    public class OrganizerViewHolder extends RecyclerView.ViewHolder {
        public TextView organizerName;
        private TextView priceText;
        private TextView durationText;
        private TextView bookButton;
        private TextView availableSlots;
        private TextView itinerary;

        public OrganizerViewHolder(View view) {
            super(view);
            organizerName = (TextView) view.findViewById(R.id.organizerName);
            priceText = (TextView) view.findViewById(R.id.priceText);
            durationText = (TextView) view.findViewById(R.id.durationText);
            bookButton = (TextView) view.findViewById(R.id.bookButton);
            availableSlots = (TextView) view.findViewById(R.id.availableSlots);
            itinerary = (TextView) view.findViewById(R.id.itinerary);
        }
    }
}
