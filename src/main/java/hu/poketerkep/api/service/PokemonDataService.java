package hu.poketerkep.api.service;

import hu.poketerkep.api.rest.query.GameQuery;
import hu.poketerkep.shared.datasource.PokemonDataSource;
import hu.poketerkep.shared.geo.Coordinate;
import hu.poketerkep.shared.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;

@Service
public class PokemonDataService {
    private final PokemonDataSource pokemonDataSource;

    @Autowired
    public PokemonDataService(PokemonDataSource pokemonDataSource) {
        this.pokemonDataSource = pokemonDataSource;
    }

    /**
     * Returns an unmodifiable set of all the pokemons
     *
     * @return all pokemons
     */
    public Set<Pokemon> getAllPokemons(GameQuery gameQuery) throws JedisException {
        double latitude = (gameQuery.getNeLat() + gameQuery.getSwLat()) / 2;
        double longitude = (gameQuery.getNeLng() + gameQuery.getSwLng()) / 2;

        Coordinate center = Coordinate.fromDegrees(latitude, longitude);
        double radius = center.getDistance(Coordinate.fromDegrees(gameQuery.getNeLat(), gameQuery.getNeLng()));

        return pokemonDataSource.getWithinRadius(center, radius);
    }

}
