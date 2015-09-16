package com.webonise.malladvertisementproject;

import java.util.List;

/**
 * Created by webonise on 16/9/15.
 */
public class JsonData  {
    /**
     * data : [{"eventDescription":"10% off on cappuccino at CCD","geofenceData":{"radius":100,"lng":73.7916,"lat":18.5581},"eventId":1,"imageUrl":"/uploads/event/image/1/freds_coupon.jpg","date":"2015-09-09T06:25:53.000Z"},{"eventDescription":"betway bingo $15 Bingo Bonus Free , betway bingo $15 Bingo Bonus Free, betway bingo $15 Bingo Bonus Free.","geofenceData":{"radius":200,"lng":73.7816,"lat":18.5151},"eventId":2,"imageUrl":"/uploads/event/image/2/offer1.jpeg","date":"2015-09-10T06:41:27.000Z"},{"eventDescription":"Introducing our new winter menu, 2nd main course 70 % off. Introducing our new winter menu, 2nd main course 70 % off","geofenceData":{"radius":300,"lng":73.751,"lat":18.5376},"eventId":3,"imageUrl":"/uploads/event/image/3/offer2.jpg","date":"2015-09-10T06:44:48.000Z"},{"eventDescription":"My Saloon Success, Rude client cost millians, Dont' be blinded, Two minutes for $2000 in salon sales. How old Fashioned marketing still works . BE THE MCDONALDS OF THE SALON INDUSTRY!","geofenceData":{"radius":200,"lng":73.8067,"lat":18.5649},"eventId":4,"imageUrl":"/uploads/event/image/4/offer3.jpeg","date":"2015-09-10T06:49:15.000Z"},{"eventDescription":"Star wars Ger the first - ever star wars: The Force awakens TM Cards","geofenceData":{"radius":20,"lng":73.7773,"lat":18.5109},"eventId":5,"imageUrl":"/uploads/event/image/5/offer5.jpg","date":"2015-09-10T07:04:31.000Z"},{"eventDescription":"By Teddy Beer on Occasion of Teddy Day to gift beloved someone.","geofenceData":{"radius":400,"lng":73.8061,"lat":18.5595},"eventId":6,"imageUrl":"/uploads/event/image/6/offer6.jpg","date":"2015-09-10T07:06:29.000Z"},{"eventDescription":"Hey lets do Pizza party with your friends , we are giving special discount offers this weekend  ","geofenceData":{"radius":500,"lng":73.8051,"lat":18.5581},"eventId":7,"imageUrl":"/uploads/event/image/7/offer7.jpeg","date":"2015-09-10T07:08:36.000Z"},{"eventDescription":"Shinde patrol pump is near by ","geofenceData":{"radius":200,"lng":73.7278,"lat":18.6675},"eventId":8,"imageUrl":"/uploads/event/image/8/offer8.jpeg","date":"2015-09-11T06:26:11.000Z"},{"eventDescription":"Come by and get a free Doughnut!","geofenceData":{"radius":100,"lng":-73.9884,"lat":40.7197},"eventId":9,"imageUrl":"/uploads/event/image/9/doughnut.jpg.CROP.promo-mediumlarge.jpg","date":"2015-09-11T14:08:49.000Z"},{"eventDescription":"You're at the NYC office, nice job!","geofenceData":{"radius":300,"lng":-73.9897,"lat":40.7187},"eventId":10,"imageUrl":"/uploads/event/image/10/scat.jpg","date":"2015-09-11T14:37:19.000Z"},{"eventDescription":"Test Notification","geofenceData":{"radius":100,"lng":73.7781,"lat":18.5092},"eventId":12,"imageUrl":"/uploads/event/image/12/11855707_10153100472490669_5521416291663989279_n.jpg","date":"2015-09-14T12:31:05.000Z"},{"eventDescription":"Test KB","geofenceData":{"radius":20,"lng":73.7785,"lat":18.5111},"eventId":13,"imageUrl":"/uploads/event/image/13/1794757_493033144173309_4768711593085979364_n.jpg","date":"2015-09-15T07:23:11.000Z"},{"eventDescription":"test kunal belleza","geofenceData":{"radius":10,"lng":73.7781,"lat":18.5092},"eventId":14,"imageUrl":"/uploads/event/image/14/1794757_493033144173309_4768711593085979364_n.jpg","date":"2015-09-15T09:50:20.000Z"}]
     */
    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * eventDescription : 10% off on cappuccino at CCD
         * geofenceData : {"radius":100,"lng":73.7916,"lat":18.5581}
         * eventId : 1
         * imageUrl : /uploads/event/image/1/freds_coupon.jpg
         * date : 2015-09-09T06:25:53.000Z
         */
        private String eventDescription;
        private GeofenceDataEntity geofenceData;
        private int eventId;
        private String imageUrl;
        private String date;

        public void setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
        }

        public void setGeofenceData(GeofenceDataEntity geofenceData) {
            this.geofenceData = geofenceData;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public GeofenceDataEntity getGeofenceData() {
            return geofenceData;
        }

        public int getEventId() {
            return eventId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getDate() {
            return date;
        }

        public static class GeofenceDataEntity {
            /**
             * radius : 100
             * lng : 73.7916
             * lat : 18.5581
             */
            private int radius;
            private double lng;
            private double lat;

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public int getRadius() {
                return radius;
            }

            public double getLng() {
                return lng;
            }

            public double getLat() {
                return lat;
            }
        }
    }
}
