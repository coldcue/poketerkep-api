package hu.poketerkep.api.rest.query;

public class GameQuery {

    private double neLat;
    private double neLng;
    private double swLat;
    private double swLng;

    private boolean gyms;
    private boolean pokemons;
    private boolean pokestops;
    private boolean showOrHide;

    private String selectedPokemons;

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

    public String getSelectedPokemons() {
        return selectedPokemons;
    }

    public void setSelectedPokemons(String selectedPokemons) {
        this.selectedPokemons = selectedPokemons;
    }

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }
}
