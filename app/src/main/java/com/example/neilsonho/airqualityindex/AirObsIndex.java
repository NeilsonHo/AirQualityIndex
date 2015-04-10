package com.example.neilsonho.airqualityindex;

/**
 * Created by neilsonho on 15-04-09.
 */
public class AirObsIndex {
    private String _airIndex;
    private String _textSummary;
    private String _updateDate;


    public AirObsIndex(){
        _airIndex="";
        _textSummary="";
        _updateDate="";
    }
    //RegionAirIndex setters & getters methods
    public void set_foreAirIndex(String _foreAirIndex) {this._airIndex = _foreAirIndex;}
    public void set_textSummary(String _textSummary) {this._textSummary = _textSummary;}
    public void set_updateDate(String _updateDate) {this._updateDate = _updateDate;}

    public String get_foreAirIndex() {return _airIndex;}
    public String get_textSummary() {return _textSummary;}
    public String get_updateDate() {return _updateDate;}
}
