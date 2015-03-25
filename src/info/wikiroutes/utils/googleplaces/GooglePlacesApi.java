package info.wikiroutes.utils.googleplaces;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Please init class on app loading
 * call init method
 */
public class GooglePlacesApi {
    private static String apiUrl = "https://maps.googleapis.com/maps/api/";
    private static String browserKey = "";

    public static void init(String key) {
        browserKey = key;
    }

    public static void getAutoCompletePlacesVariants(String query, String language, final PointSearchCallBack pointSearchCallBack) {
        call(createUrl(query, language), new GoogleApiRequestCallback() {
            @Override
            public void onDataReceived(String json) {
                final GooglePlacesAddressAutocomplete response = new Gson().fromJson(json, GooglePlacesAddressAutocomplete.class);
                pointSearchCallBack.onDataReceived(response);
            }
            @Override
            public void onError() {
                pointSearchCallBack.onError();
            }
        });

    }

    public static void getCoordinatesFromReference(String ref, final PointDetailsCallBack pointDetailsCallBack) {
        call(getReferenceSearchInput(ref), new GoogleApiRequestCallback() {
            @Override
            public void onDataReceived(String json) {
                final GooglePlacesResponse response = new Gson().fromJson(json, GooglePlacesResponse.class);
                pointDetailsCallBack.onDataReceived(response);
            }
            @Override
            public void onError() {
                pointDetailsCallBack.onError();
            }
        });
    }

    public static interface PointSearchCallBack {
        public void onDataReceived(GooglePlacesAddressAutocomplete data);
        public void onError();
    }

    public static interface PointDetailsCallBack {
        public void onDataReceived(GooglePlacesResponse data);
        public void onError();
    }

    private static String createUrl(String url, String language) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("place/autocomplete/json?input=");
        try {
            stringBuilder.append(URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException ignored) {

        }
        stringBuilder.append("&types=geocode&sensor=true&language=");
        stringBuilder.append(language);
        stringBuilder.append("&key=");
        stringBuilder.append(browserKey);
        return stringBuilder.toString();
    }

    private static String getReferenceSearchInput(String reference) {
        return "place/details/json?sensor=true&reference=" + reference + "&key=" + browserKey;
    }

    public static void call(String path, final GoogleApiRequestCallback googleApiRequestCallback) {
        final String urlPath = apiUrl + path;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
                    googleApiRequestCallback.onDataReceived(getString(in, reader));
                } catch (Exception e) {
                    googleApiRequestCallback.onError();
                }
            }
        }).start();
    }

    private static String getString(InputStream in, BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        in.close();
        return sb.toString();
    }

    public static interface GoogleApiRequestCallback {
        public void onDataReceived(String json);
        public void onError();
    }
}
