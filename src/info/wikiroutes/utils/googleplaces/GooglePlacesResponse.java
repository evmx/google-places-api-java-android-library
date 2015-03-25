package info.wikiroutes.utils.googleplaces;

import info.wikiroutes.android.server.entity.EntityCoordinate;

/**
 * Created by Intellij IDEA.
 * User: Ironz
 * Date: 04.10.2014, 15:49.
 * E-mail: implimentz@gmail.com
 * vk: iamironz
 */
public class GooglePlacesResponse {
    private Result result;
    private String status;

    public EntityCoordinate getCoordinate() {
        return new EntityCoordinate(result.getGeometry().location.getLat(), result.getGeometry().location.getLng());
    }

    public boolean success() {
        return "OK".equals(status) && result.getGeometry().getLocation() != null;
    }
}


class Result {

    private Geometry geometry;

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}

class Geometry {

    public AddressLocation location;

    public void setLocation(AddressLocation location) {
        this.location = location;
    }

    public AddressLocation getLocation() {
        return location;
    }
}

class AddressLocation {

    private double lat;
    private double lng;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
