package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;

import java.util.stream.Stream;

public class GeospatialPokemonFilter {
    private final Stream<Pokemon> pokemons;

    public GeospatialPokemonFilter(Stream<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public Stream<Pokemon> filter(Coordinate northEast, Coordinate southWest) {
        return pokemons
                // Sort by longitude
                .filter(pokemon -> pokemon.getLongitude() >= southWest.getLongitude()
                        && pokemon.getLongitude() <= northEast.getLongitude())
                // Sort by latitude
                .filter(pokemon -> pokemon.getLatitude() >= southWest.getLatitude()
                        && pokemon.getLatitude() <= northEast.getLatitude());
    }
}
