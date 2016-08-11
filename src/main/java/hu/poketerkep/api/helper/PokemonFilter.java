package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PokemonFilter {

    private final Coordinate northEast;
    private final Coordinate southWest;
    private final Long since;
    private final Predicate<Pokemon> oldPokemonPredicate;
    private final Predicate<Pokemon> sincePredicate;
    private final Predicate<Pokemon> longitudePredicate;
    private final Predicate<Pokemon> latitudePredicate;

    public PokemonFilter(Coordinate northEast, Coordinate southWest, Long since) {
        this.northEast = northEast;
        this.southWest = southWest;
        this.since = since;

        // Create predicates
        long now = Instant.now().toEpochMilli();

        // Filter old pokemons
        oldPokemonPredicate = pokemon -> pokemon.getDisappearTime() > now;

        // Since filter
        sincePredicate = pokemon -> !hasSinceFilter() || pokemon.getDisappearTime() - 15 * 60 * 1000 > since;

        // Sort by longitude
        longitudePredicate = pokemon -> !hasGeoFilter() || pokemon.getLongitude() >= southWest.getLongitude()
                && pokemon.getLongitude() <= northEast.getLongitude();

        // Sort by latitude
        latitudePredicate = pokemon -> !hasGeoFilter() || pokemon.getLatitude() >= southWest.getLatitude()
                && pokemon.getLatitude() <= northEast.getLatitude();
    }

    public List<Pokemon> doFilter(List<Pokemon> pokemonList) {
        return pokemonList.stream()
                .filter(oldPokemonPredicate)
                .filter(sincePredicate)
                .filter(longitudePredicate)
                .filter(latitudePredicate)
                .collect(Collectors.toList());
    }

    private boolean hasSinceFilter() {
        return since != null;
    }

    private boolean hasGeoFilter() {
        return northEast != null && southWest != null;
    }
}
