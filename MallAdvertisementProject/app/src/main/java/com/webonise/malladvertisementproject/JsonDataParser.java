package com.webonise.malladvertisementproject;

import java.util.List;

import utils.CoOdrinates;

/**
 * Created by webonise on 10/9/15.
 */
public class JsonDataParser {
    private List<OffersEntity> offers;

    public void setOffers(List<OffersEntity> offers) {
        this.offers = offers;
    }

    public List<OffersEntity> getOffers() {
        return offers;
    }

    public static class OffersEntity {

        private String name;
        private String description;
        private String longitude;
        private String latitude;
        private String discountPercentage;
        private String url;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setDiscountPercentage(String discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getDiscountPercentage() {
            return discountPercentage;
        }

        public String getUrl() {
            return url;
        }

        public CoOdrinates getCoOrdinates() {
            return new CoOdrinates(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }
    }
}

