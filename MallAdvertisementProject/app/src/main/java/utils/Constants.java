package utils;

/**
 * Created by webonise on 28/8/15.
 * 23°15′N 77°25′E
 * <p/>
 * Loading...
 * 18.554871, 73.906003
 * <p/>
 * Loading...
 * 18.543866, 73.851686
 */
public class Constants {

//    public static final String URL = "http://test-geofence.weboapps.com/events.json";
//    public static final String URL = "http://api.androidhive.info/contacts";
    public static final String URL = "http://test-geofence.weboapps.com/events.json";
    public static final String BASE_URL = "http://test-geofence.weboapps.com";
    public static final String DETAIL_URL = "DETAIL_URL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DISCOUNT = "DISCOUNT";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String JSON_RESPONSE = "JSON_RESPONSE";
    public static final String GOOGLE_URL = "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f(%s)";
    public static final String EMPTY_STRING = "No description";
    public static final int REQUEST_ENABLE_BT = 1;

    public static final String EXPIRATION_DATE="2015-09-09T06:25:53.000Z";
    public static final String DATABASE_NAME="OfferDetailsDatabase";
    public static final int DATABASE_VERSION =1;
    public static final String OFFERS_TABLE="OffersTable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DISCOUNT = "name";
    public static final String COLUMN_DESCRIPTION = "age";
    public static final String COLUMN_URL = "weight";
    public static final double COLUMN_LAT = 0.0;
    public static final double COLUMN_LONG = 0.0;
    public static final String COLUMN_EXPIRATION_DATE = "height";
    public static final String COLUMN_PROVIDERS_NAME = "providersName";



    public static final String TEMP_JSON_RESPONSE = "{\n" +
            "\n" +
            "\"offers\": [\n" +
            "\n" +
            "{\n" +
            "\t\"id\": \"1\",\n" +
            "\t\"name\": \"kitkat1\",\n" +
            "\t\"url\": \"http://www.mobilesmspk.net/user/images/wallpaper_images/www.mobilesmspk.net_nature_33.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category \",\n" +
            "\t\"latitude\":\"18.5111\",\n" +

            "\t\"longitude\":\"73.7784\"\n" +
            "\t\n" +
            "\n" +
            "},{\t\"name\":\"android1\",\n" +
            "\t\"id\": \"2\",\n" +
            "\t\"url\": \"http://pati.wen.su/wallpaper/240x320/240x320_nicevilla.jpg\",\n" +
            "\t\"discountPercentage\": \"100% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\n" +
            "\t\"latitude\":\"18.5109983\",\n" +
            "\t\"longitude\":\"73.7773508\"\n" +
            "\n" +
            "},{\n" +
            "\t\"name\": \"kitkat2\",\n" +
            "\t\"id\": \"3\",\n" +
            "\t\"url\": \"http://mobile-phone.pk/images/wallpapers/animated_nature_wallpaper_nature_mobile_wallpaper.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\n" +
            "\t\"latitude\":\"18.510477\",\n" +
            "\t\"longitude\":\"73.777170\"\n" +
            "\n" +
            "\t\n" +
            "\n" +
            "},{\t\"name\":\"android2\",\n" +
            "\t\"id\": \"4\",\n" +
            "\t\"url\": \"http://www.mobilephun.com/wp-content/uploads/2009/03/nature-40.gif\",\n" +
            "\t\"discountPercentage\": \"100% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\n" +
            "\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "},{\n" +
            "\t\"name\": \"kitkat3\",\n" +
            "\t\"id\": \"5\",\n" +
            "\t\"url\": \"http://41.media.tumblr.com/a888a64b9f2248ede11db4e387af7a86/tumblr_nppd6rFiVg1uvyg9ko1_500.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\n" +
            "\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "\t\n" +
            "\n" +
            "},{\t\"name\":\"android3\",\n" +
            "\t\"id\": \"6\",\n" +
            "\t\"url\": \"http://www.myiphone5wallpaper.com/Gallery/1_Nature/My-iPhone-5-Wallpaper-HD-Nature(105).jpg\",\n" +
            "\t\"discountPercentage\": \"100% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "},{\n" +
            "\t\"name\": \"kitkat4\",\n" +
            "\t\"id\": \"7\",\n" +
            "\t\"url\": \"http://technicues.com/wp-content/uploads/2013/09/Android_Kitkat_icon.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "\t\n" +
            "\n" +
            "},{\t\"name\":\"android4\",\n" +
            "\t\"id\": \"8\",\n" +
            "\t\"url\": \"http://img5a.flixcart.com/image/ball/h/m/y/fb-221-nivia-football-revolvo-200x200-imadznjzcxefhpjr.jpeg\",\n" +
            "\t\"discountPercentage\": \"100% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "},{\n" +
            "\t\"name\": \"kitkat5\",\n" +
            "\t\"id\": \"9\",\n" +
            "\t\"url\": \"http://technicues.com/wp-content/uploads/2013/09/Android_Kitkat_icon.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "\t\n" +
            "\n" +
            "},{\t\"name\":\"android5\",\n" +
            "\t\"id\": \"10\",\n" +
            "\t\"url\": \"http://img5a.flixcart.com/image/ball/h/m/y/fb-221-nivia-football-revolvo-200x200-imadznjzcxefhpjr.jpeg\",\n" +
            "\t\"discountPercentage\": \"100% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "},{\n" +
            "\t\"name\": \"kitkat6\",\n" +
            "\t\"id\": \"11\",\n" +
            "\t\"url\": \"http://technicues.com/wp-content/uploads/2013/09/Android_Kitkat_icon.jpg\",\n" +
            "\t\"discountPercentage\": \"10% discount\",\n" +
            "\t\"description\": \"this discount is only available for some time... and only for the lower class people but not for the rich people who belongs to the Reservation category\",\t\"latitude\":\"19.562622\",\n" +
            "\t\"longitude\":\"72.808723\"\n" +
            "\n" +
            "\t\n" +
            "\n" +
            "}" +
            "]\n" +
            "}";


}
