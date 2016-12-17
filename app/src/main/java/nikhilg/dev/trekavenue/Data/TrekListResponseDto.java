package nikhilg.dev.trekavenue.Data;

import java.util.ArrayList;

/**
 * Created by nik on 4/12/16.
 */
public class TrekListResponseDto extends BaseDto {
    private Integer flag;
    private String message;
    private ArrayList<TrekDataDto> trekData;
    private Integer status;
    private Long nextTrekId;

    // Remove below variables when becomes camelCase
    private ArrayList<TrekDataDto> trek_data;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TrekDataDto> getTrekData() {
        return trekData;
    }

    public void setTrekData(ArrayList<TrekDataDto> trekData) {
        this.trekData = trekData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getNextTrekId() {
        return nextTrekId;
    }

    public void setNextTrekId(Long nextTrekId) {
        this.nextTrekId = nextTrekId;
    }

    public ArrayList<TrekDataDto> getTrek_data() {
        return trek_data;
    }

    public void setTrek_data(ArrayList<TrekDataDto> trek_data) {
        this.trek_data = trek_data;
    }
}