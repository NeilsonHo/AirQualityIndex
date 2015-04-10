package com.example.neilsonho.airqualityindex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neilsonho on 15-04-09.
 */
public class AirForeIndex {
    private String _textSummary;
    private String _updateDate;
    private List<Forecast> _forecastIndexes;

    public AirForeIndex(){
        _forecastIndexes = new ArrayList<Forecast>();

    }
    //RegionAirIndex setters & getters methods

    public void set_textSummary(String _textSummary) {this._textSummary = _textSummary;}
    public void set_updateDate(String _updateDate) {this._updateDate = _updateDate;}

    public List<Forecast> get_forecastIndexes() {return _forecastIndexes;}
    public String get_textSummary() {return _textSummary;}
    public String get_updateDate() {return _updateDate;}
}
