package nikhilg.dev.trekavenue.Data;

import java.util.ArrayList;

/**
 * Created by nik on 30/1/17.
 */
public class FilterParams extends BaseDto {
    private int flag;
    private String message;
    private ArrayList<FilterObject> filters;
    private Integer status;

    public ArrayList<FilterObject> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<FilterObject> filters) {
        this.filters = filters;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
