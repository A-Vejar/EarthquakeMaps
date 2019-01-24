package cl.ucn.disc.dsm.avejar.earthquakemaps.models;

import java.util.List;

public class Geometry {

    public List<Double> coordinates;

    @Override
    public String toString(){
        if(coordinates != null && !coordinates.isEmpty()){
            return "Length " + getLength() +
                    " | Latitude " + getLatitude() +
                    " | Depth - " + getDepth() + " [km]";
        }else {
            return "Non Coordinates";
        }
    }

    public Double getLength(){
        if(coordinates != null && !coordinates.isEmpty()){
            return coordinates.get(0);
        }else {
            return 0.0;
        }
    }

    public Double getLatitude(){
        if(coordinates != null && !coordinates.isEmpty()){
            return coordinates.get(1);
        }else {
            return 0.0;
        }
    }

    public Double getDepth(){
        if(coordinates != null && !coordinates.isEmpty()){
            return coordinates.get(2);
        }else {
            return 0.0;
        }
    }
}
