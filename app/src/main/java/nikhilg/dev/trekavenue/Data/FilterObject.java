package nikhilg.dev.trekavenue.Data;

import org.json.JSONObject;

/**
 * Created by nik on 30/1/17.
 */
public class FilterObject extends BaseDto {
    private String criteriaName;
    private String criteriakey;
    private String filterType;
    private JSONObject filterValues;

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getCriteriakey() {
        return criteriakey;
    }

    public void setCriteriakey(String criteriakey) {
        this.criteriakey = criteriakey;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public JSONObject getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(JSONObject filterValues) {
        this.filterValues = filterValues;
    }
}
