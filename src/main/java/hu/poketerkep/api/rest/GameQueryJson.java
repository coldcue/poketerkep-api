package hu.poketerkep.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GameQueryJson {
    @JsonProperty(required = true)
    private double neLat;
    @JsonProperty(required = true)
    private double neLng;
    @JsonProperty(required = true)
    private double swLat;
    @JsonProperty(required = true)
    private double swLng;

    @JsonProperty(required = true)
    private boolean gyms;
    @JsonProperty(required = true)
    private boolean pokemons;
    @JsonProperty(required = true)
    private boolean pokestops;
    @JsonProperty(required = true)
    private boolean showOrHide;

    @JsonProperty(required = true)
    private List<Integer> selectedPokemons;

    private Long since;

    public double getNeLat() {
        return neLat;
    }

    public void setNeLat(double neLat) {
        this.neLat = neLat;
    }

    public double getNeLng() {
        return neLng;
    }

    public void setNeLng(double neLng) {
        this.neLng = neLng;
    }

    public double getSwLat() {
        return swLat;
    }

    public void setSwLat(double swLat) {
        this.swLat = swLat;
    }

    public double getSwLng() {
        return swLng;
    }

    public void setSwLng(double swLng) {
        this.swLng = swLng;
    }

    public boolean isGyms() {
        return gyms;
    }

    public void setGyms(boolean gyms) {
        this.gyms = gyms;
    }

    public boolean isPokemons() {
        return pokemons;
    }

    public void setPokemons(boolean pokemons) {
        this.pokemons = pokemons;
    }

    public boolean isPokestops() {
        return pokestops;
    }

    public void setPokestops(boolean pokestops) {
        this.pokestops = pokestops;
    }

    public boolean isShowOrHide() {
        return showOrHide;
    }

    public void setShowOrHide(boolean showOrHide) {
        this.showOrHide = showOrHide;
    }

    public List<Integer> getSelectedPokemons() {
        return selectedPokemons;
    }

    public void setSelectedPokemons(List<Integer> selectedPokemons) {
        this.selectedPokemons = selectedPokemons;
    }

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }
}
