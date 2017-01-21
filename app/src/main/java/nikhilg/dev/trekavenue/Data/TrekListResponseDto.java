package nikhilg.dev.trekavenue.Data;

import java.util.ArrayList;

/**
 * Created by nik on 4/12/16.
 */
public class TrekListResponseDto extends BaseDto {
    private int flag;
    private String message;
    private ArrayList<TrekDataDto> trekData;
    private Integer status;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
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
}
