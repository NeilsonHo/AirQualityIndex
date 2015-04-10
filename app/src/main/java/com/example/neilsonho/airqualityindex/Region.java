package com.example.neilsonho.airqualityindex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by neilsonho on 15-04-07.
 */
public class Region implements Parcelable {
    private String _name;
    private String _currentURL;
    private String _forecastURL;

    public Region() {
        _name=null;
    }

    public Region (String name){
        this._name = name;
        this._currentURL="";
        this._forecastURL="";
    }

    //Region Setters & Getters
    public void set_name(String name) {
        this._name = name;
    }

    public String getName(){
        return this._name;
    }

    public void set_currentURL(String currentURL){
        this._currentURL = currentURL;
    }

    public String get_currentURLl(){
        return this._currentURL;
    }

    public void set_forecastURL(String forecastURL){ this._forecastURL=forecastURL; }

    public String get_forecastURL(){
        return this._forecastURL;
    }

    @Override
    public String toString() {
        return this._name;
    }


    //Parcelable Methods
    Region(Parcel in){
        _name = in.readString();
        _currentURL = in.readString();
        _forecastURL = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_currentURL);
        dest.writeString(_forecastURL);
    }
    public static final Parcelable.Creator<Region> CREATOR = new Parcelable.Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }
        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };
    @Override
    public int describeContents(){
        return 0;
    }


}
