package nikhilg.dev.trekavenue.Data;

import java.util.ArrayList;

/**
 * Created by nik on 4/12/16.
 */
public class TrekDataDto extends BaseDto {
    private Long trekId;
    private String name;
    private String description;
    private String region;
    private String country;
    private Long highestAltitudeInFeet;
    private Integer averageTemperatureDayMaxCelcius;
    private Integer averageTemperatureDayMinCelcius;
    private Integer averageTemperatureNightMaxCelcius;
    private Integer averageTemperatureNightMinCelcius;
    private Boolean isActive;
    private String difficulty;
    private SummaryInfo summaryInfo;

    private ArrayList<OrganizerDto> organizers;
    private ArrayList<ImageDto> images;

    public Long getTrekId() {
        return trekId;
    }

    public void setTrekId(Long trekId) {
        this.trekId = trekId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAverageTemperatureDayMaxCelcius() {
        return averageTemperatureDayMaxCelcius;
    }

    public void setAverageTemperatureDayMaxCelcius(Integer averageTemperatureDayMaxCelcius) {
        this.averageTemperatureDayMaxCelcius = averageTemperatureDayMaxCelcius;
    }

    public Integer getAverageTemperatureDayMinCelcius() {
        return averageTemperatureDayMinCelcius;
    }

    public void setAverageTemperatureDayMinCelcius(Integer averageTemperatureDayMinCelcius) {
        this.averageTemperatureDayMinCelcius = averageTemperatureDayMinCelcius;
    }

    public Integer getAverageTemperatureNightMaxCelcius() {
        return averageTemperatureNightMaxCelcius;
    }

    public void setAverageTemperatureNightMaxCelcius(Integer averageTemperatureNightMaxCelcius) {
        this.averageTemperatureNightMaxCelcius = averageTemperatureNightMaxCelcius;
    }

    public Integer getAverageTemperatureNightMinCelcius() {
        return averageTemperatureNightMinCelcius;
    }

    public void setAverageTemperatureNightMinCelcius(Integer averageTemperatureNightMinCelcius) {
        this.averageTemperatureNightMinCelcius = averageTemperatureNightMinCelcius;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Long getHighestAltitudeInFeet() {
        return highestAltitudeInFeet;
    }

    public void setHighestAltitudeInFeet(Long highestAltitudeInFeet) {
        this.highestAltitudeInFeet = highestAltitudeInFeet;
    }

    public ArrayList<ImageDto> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageDto> images) {
        this.images = images;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ArrayList<OrganizerDto> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(ArrayList<OrganizerDto> organizers) {
        this.organizers = organizers;
    }

    public SummaryInfo getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(SummaryInfo summaryInfo) {
        this.summaryInfo = summaryInfo;
    }
}
