package com.laura.fast4.models.Provider;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {
    private DatabaseReference  mdatabase;
    private GeoFire mgeofire;

    public GeofireProvider() {
        mdatabase = FirebaseDatabase.getInstance().getReference().child("active_drivers");
        mgeofire = new GeoFire(mdatabase);
    }

    public void saveLocation(String idDriver, LatLng latLng){
        mgeofire.setLocation(idDriver, new GeoLocation(latLng.latitude, latLng.longitude));
        //Guardar la localizacion
    }

    public void removeLocation(String idDriver, LatLng latLng){
        mgeofire.removeLocation(idDriver);
    }
}
