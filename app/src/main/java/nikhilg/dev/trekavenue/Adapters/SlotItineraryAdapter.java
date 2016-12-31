package nikhilg.dev.trekavenue.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nikhilg.dev.trekavenue.Data.OrganizerDto;
import nikhilg.dev.trekavenue.R;

/**
 * Created by nik on 24/12/16.
 */
public class SlotItineraryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OrganizerDto organizer;

    private boolean showSlots;
    private Context mContext;

    public SlotItineraryAdapter(OrganizerDto organizer, boolean showSlots, Context mContext) {
        this.organizer = organizer;
        this.showSlots = showSlots;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        if (showSlots)
            return organizer.getSlots() == null ? 0 : organizer.getSlots().size();
        else
            return organizer.getItinerary() == null ? 0 : organizer.getItinerary().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slot_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new SlotItineraryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SlotItineraryViewHolder) {
            SlotItineraryViewHolder vh = (SlotItineraryViewHolder) holder;
            if (showSlots) {
                vh.slotLayout.setVisibility(View.VISIBLE);
                vh.dateLayout.setVisibility(View.VISIBLE);
                vh.itineraryText.setVisibility(View.GONE);
                String startDate = ((String[]) organizer.getSlots().get(position).getStartDate().split("T"))[0];
                String endDate = ((String[]) organizer.getSlots().get(position).getEndDate().split("T"))[0];
                vh.startDate.setText(" " + startDate);
                vh.endDate.setText(" " + endDate);
                vh.totalSlots.setText(" " + String.valueOf(organizer.getSlots().get(position).getTotalSlots()));
                vh.availableSlots.setText(" " + String.valueOf(organizer.getSlots().get(position).getSeatsAvailable()));

                if (organizer.getSlots().get(position).getSeatsAvailable() != null
                        && organizer.getSlots().get(position).getSeatsAvailable() > 0) {
                    vh.outerLL.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    vh.outerLL.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_color));
                }
            } else {
                vh.slotLayout.setVisibility(View.GONE);
                vh.dateLayout.setVisibility(View.GONE);
                vh.itineraryText.setVisibility(View.VISIBLE);

                vh.itineraryText.setText(organizer.getItinerary().get(position));
                vh.outerLL.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            }
        }
    }

    public class SlotItineraryViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout outerLL;
        public TextView startDate;
        private TextView endDate;
        private TextView totalSlots;
        private TextView availableSlots;
        private TextView itineraryText;
        private TextView itinerary;
        private LinearLayout dateLayout, slotLayout;

        public SlotItineraryViewHolder(View view) {
            super(view);
            outerLL = (LinearLayout) view.findViewById(R.id.outerLL);
            startDate = (TextView) view.findViewById(R.id.startDate);
            endDate = (TextView) view.findViewById(R.id.endDate);
            totalSlots = (TextView) view.findViewById(R.id.totalSlots);
            availableSlots = (TextView) view.findViewById(R.id.availableSlots);
            itineraryText = (TextView) view.findViewById(R.id.itineraryText);
            dateLayout = (LinearLayout) view.findViewById(R.id.dateLayout);
            slotLayout = (LinearLayout) view.findViewById(R.id.slotLayout);
        }
    }

}
