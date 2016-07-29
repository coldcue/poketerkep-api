package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;

import java.util.List;
import java.util.stream.Collectors;

public class GeospatialPokemonFilter {
    private final List<Pokemon> pokemons;

    public GeospatialPokemonFilter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> filter(Coordinate northEast, Coordinate southWest) {
        return pokemons.parallelStream()
                // Sort by longitude
                .filter(pokemon -> pokemon.getLongitude() >= southWest.getLongitude()
                        && pokemon.getLongitude() <= northEast.getLongitude())
                // Sort by latitude
                .filter(pokemon -> pokemon.getLatitude() >= southWest.getLatitude()
                        && pokemon.getLatitude() <= northEast.getLatitude())
                .collect(Collectors.toList());
    }
}
