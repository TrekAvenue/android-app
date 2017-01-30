package nikhilg.dev.trekavenue.Data;

/**
 * Created by nik on 30/1/17.
 */
public class KeyValueObject extends BaseDto {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
