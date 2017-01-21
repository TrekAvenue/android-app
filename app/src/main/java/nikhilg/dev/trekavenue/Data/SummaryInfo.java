package nikhilg.dev.trekavenue.Data;

/**
 * Created by nik on 21/1/17.
 */
public class SummaryInfo extends BaseDto {
    private Double startingPriceInr;
    private String bannerImageUrl;

    public Double getStartingPriceInr() {
        return startingPriceInr;
    }

    public void setStartingPriceInr(Double startingPriceInr) {
        this.startingPriceInr = startingPriceInr;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }
}
