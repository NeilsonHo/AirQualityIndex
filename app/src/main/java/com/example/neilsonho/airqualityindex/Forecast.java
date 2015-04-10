package com.example.neilsonho.airqualityindex;

/**
 * Created by neilsonho on 15-04-09.
 */
public class Forecast {
    private String _name;
    private String _airIndex;

    public Forecast(String name){

        this._name = name;
        _airIndex = "";
    }

    //Forecast Setters & Getters
    public void set_name(String _name) {this._name = _name;}
    public void set_airIndex(String _airIndex) {this._airIndex = _airIndex;}
    public String get_name(){return _name;}
    public String get_airIndex() {return _airIndex;}
}
