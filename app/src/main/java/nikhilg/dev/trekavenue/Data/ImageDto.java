package nikhilg.dev.trekavenue.Data;

/**
 * Created by nik on 4/12/16.
 */
public class ImageDto {
    private Integer index;
    private String url;
    private String thumbnailUrl;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
