package cl.ucn.disc.dsm.avejar.earthquakemaps;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.avejar.earthquakemaps.controller.UsgsController;
import cl.ucn.disc.dsm.avejar.earthquakemaps.models.EarthQuakeContent;

public class MainActivity extends AppCompatActivity {

    /**
     * Osmdroid
     */
    MapView map;

    /**
     * MapView Controller
     */
    IMapController mapController;

    /**
     * EarthQuake instance
     */
    List<EarthQuakeContent> content;

    /**
     * Initial point on the map - Chile
     */
    GeoPoint initialPoint = new GeoPoint(-38.48, -76.01);

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permissions first, before map is created. not depicted here
        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        // Default zoom buttons, and ability to zoom with 2 fingers (multi-touch)
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(5);
        mapController.setCenter(initialPoint);

        BackgroudTask();
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        map.onPause();
    }

    private void QuakeCoordinates(EarthQuakeContent quake){

        Marker marker;
        GeoPoint point;

        marker = new Marker(map);
        marker.setTitle(quake.properties.title);
        marker.setSnippet(quake.geometry.toString());

        point = new GeoPoint(quake.geometry.getLatitude(), quake.geometry.getLength());
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        map.getOverlays().add(marker);
    }

    private void BeginCoordinates(){

        if(content != null && !content.isEmpty()){
            for (EarthQuakeContent quake : content){
                QuakeCoordinates(quake);
            }
        }
    }

    private void BackgroudTask(){
        AsyncTask.execute(()-> {

            List<EarthQuakeContent> quakeCall = null;
            try{
                quakeCall = UsgsController.getQuake();

            }catch (IOException e) {
                e.printStackTrace();
            }

            if(quakeCall != null){

                content = quakeCall;
                BeginCoordinates();
            }
        });
    }
}
