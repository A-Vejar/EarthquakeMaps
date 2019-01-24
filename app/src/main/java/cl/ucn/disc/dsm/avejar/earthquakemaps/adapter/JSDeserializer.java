package cl.ucn.disc.dsm.avejar.earthquakemaps.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/*
* Thanks to - https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
* USGS Json - https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
*/

public class JSDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonElement features = json.getAsJsonObject().get("features");
        return new Gson().fromJson(features, typeOfT);
    }
}
