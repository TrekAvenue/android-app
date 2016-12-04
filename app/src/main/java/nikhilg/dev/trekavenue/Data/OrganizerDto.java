package nikhilg.dev.trekavenue.Data;

import java.util.ArrayList;

/**
 * Created by nik on 4/12/16.
 */
public class OrganizerDto extends BaseDto {
    private String organizerName;
    private Double priceInr;
    private Integer durationDays;
    private String bookingLink;
    private String Id;
    private ArrayList<SlotDto> slots;
    private ArrayList<String> itinerary;

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public Double getPriceInr() {
        return priceInr;
    }

    public void setPriceInr(Double priceInr) {
        this.priceInr = priceInr;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getBookingLink() {
        return bookingLink;
    }

    public void setBookingLink(String bookingLink) {
        this.bookingLink = bookingLink;
    }

    public ArrayList<SlotDto> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<SlotDto> slots) {
        this.slots = slots;
    }

    public ArrayList<String> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<String> itinerary) {
        this.itinerary = itinerary;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
