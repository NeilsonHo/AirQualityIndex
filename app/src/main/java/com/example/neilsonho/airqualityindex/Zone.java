package com.example.neilsonho.airqualityindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neilsonho on 15-04-07.
 */
public class Zone implements Parcelable{
    private String _name;
    final private List<Region> regionList;

    public Zone(String name){
        _name = name;
        regionList = new ArrayList<Region>();
    }
    //Zone Setters & Getters
    public String get_name() {
        return _name;
    }

    public List<Region> getRegionList() {
        return regionList;
    }
    @Override
    public String toString() {
        return this._name;
    }


    //Parcelable Methods
    public Zone(Parcel in){
        _name = in.readString();
        regionList = new ArrayList<Region>();
        in.readTypedList(regionList,Region.CREATOR);
    }
    @Override
    public int describeContents(){
        return 0;
    }
    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_name);
        out.writeTypedList(regionList);
    }
    public static final Parcelable.Creator<Zone> CREATOR = new Parcelable.Creator<Zone>() {
        @Override
        public Zone createFromParcel(Parcel in) {
            return new Zone(in);
        }
        @Override
        public Zone[] newArray(int size) {
            return new Zone[size];
        }
    };

}
