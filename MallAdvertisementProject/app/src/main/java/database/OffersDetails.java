package database;

/**
 * Created by webonise on 21/9/15.
 */
public class OffersDetails {

    private String discountPercentage;
    private String description;
    private String imageUrl;
    private double lats;
    private double longs;
    private String providerDetail;
    private String expirationDate;

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getLats() {
        return lats;
    }

    public void setLats(double lats) {
        this.lats = lats;
    }

    public double getLongs() {
        return longs;
    }

    public void setLongs(double longs) {
        this.longs = longs;
    }

    public String getProviderDetail() {
        return providerDetail;
    }

    public void setProviderDetail(String providerDetail) {
        this.providerDetail = providerDetail;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


}
