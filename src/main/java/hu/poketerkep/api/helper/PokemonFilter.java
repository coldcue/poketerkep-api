package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;
import hu.poketerkep.api.rest.query.GameQueryJson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PokemonFilter {

    private final Predicate<Pokemon> oldPokemonPredicate;
    private final Predicate<Pokemon> sincePredicate;
    private final Predicate<Pokemon> longitudePredicate;
    private final Predicate<Pokemon> latitudePredicate;
    private final Predicate<Pokemon> typeFilterPredicate;
    private final GameQueryJson query;

    public PokemonFilter(GameQueryJson query) {
        final GameQueryJson.Bounds bounds = query.getBounds();
        final Coordinate northEast = new Coordinate(bounds.getNeLat(), bounds.getNeLng());
        final Coordinate southWest = new Coordinate(bounds.getSwLat(), bounds.getSwLng());
        Optional<Long> since = Optional.ofNullable(query.getSince());
        this.query = query;

        // Create predicates
        long now = Instant.now().toEpochMilli();


        /*
        * ---- Pokemons ----
        */

        // Filter old pokemons
        oldPokemonPredicate = pokemon -> pokemon.getDisappearTime() > now;

        // Since filter
        sincePredicate = pokemon -> !since.isPresent() || pokemon.getDisappearTime() - 15 * 60 * 1000 > since.get();

        // Sort by longitude
        longitudePredicate = pokemon -> pokemon.getLongitude() >= southWest.getLongitude()
                && pokemon.getLongitude() <= northEast.getLongitude();

        // Sort by latitude
        latitudePredicate = pokemon -> pokemon.getLatitude() >= southWest.getLatitude()
                && pokemon.getLatitude() <= northEast.getLatitude();

        // Sort by type
        if (query.getSelectedPokemons().size() == 0) {
            typeFilterPredicate = pokemon -> true;
        } else {
            typeFilterPredicate = pokemon -> query.isShowOrHide() == query.getSelectedPokemons().contains(pokemon.getPokemonId());
        }

    }

    public List<Pokemon> doFilter(List<Pokemon> pokemonList) {
        // Return empty list if pokemons are hidden
        if (!query.isPokemons())
            return new ArrayList<>();

        return pokemonList.stream()
                .filter(oldPokemonPredicate)
                .filter(sincePredicate)
                .filter(longitudePredicate)
                .filter(latitudePredicate)
                .filter(typeFilterPredicate)
                .collect(Collectors.toList());
    }
}
