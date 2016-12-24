package nikhilg.dev.trekavenue.Data;

/**
 * Created by nik on 4/12/16.
 */
public class SlotDto extends BaseDto {
//    private Long startDate;
//    private Long endDate;
    private String startDate;
    private String endDate;
    private Integer totalSlots;
    private Integer slotsAvailable;
    private String id;

//    public Long getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Long startDate) {
//        this.startDate = startDate;
//    }
//
//    public Long getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Long endDate) {
//        this.endDate = endDate;
//    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Integer totalSlots) {
        this.totalSlots = totalSlots;
    }

    public Integer getSeatsAvailable() {
        return slotsAvailable;
    }

    public void setSeatsAvailable(Integer slotsAvailable) {
        this.slotsAvailable = slotsAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
