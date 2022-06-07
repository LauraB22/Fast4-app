package com.laura.fast4.models.Provider;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
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

    public void removeLocation(String idDriver){
        mgeofire.removeLocation(idDriver);
    }

    public GeoQuery getActiveDrivers(LatLng latLng){
        GeoQuery geoQuery = mgeofire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 5);
                //Muestra el radio de busqueda
        geoQuery.removeAllListeners();
        return geoQuery;
    }
}
