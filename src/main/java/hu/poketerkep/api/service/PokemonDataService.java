package hu.poketerkep.api.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class PokemonDataService {
    private static final String POKEMONS_TABLE = "pokemons";
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final AmazonDynamoDBAsync amazonDynamoDBAsync;

    // Concurrent Hash Set
    private Set<Pokemon> cachedPokemons = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Autowired
    public PokemonDataService(AmazonDynamoDBAsync amazonDynamoDBAsync) {
        this.amazonDynamoDBAsync = amazonDynamoDBAsync;
    }

    /**
     * Returns an unmodifiable set of all the pokemons
     *
     * @return all pokemons
     */
    public Set<Pokemon> getAllPokemons() {
        return Collections.unmodifiableSet(cachedPokemons);
    }

    /**
     * Refresh the cache
     */
    @Scheduled(fixedRate = 30 * 1000, initialDelay = 0)
    public void refreshCache() {
        log.info("Refreshing pokemons cache...");
        cachedPokemons.clear();
        cachedPokemons.addAll(getPokemonsFromDatabase());
        log.info("Done! " + cachedPokemons.size() + " pokemons fetched");
    }

    /**
     * Get all pokemon from the database
     *
     * @return all the pokemons
     */
    private HashSet<Pokemon> getPokemonsFromDatabase() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(POKEMONS_TABLE);

        ScanResult result = amazonDynamoDBAsync.scan(scanRequest);

        HashSet<Pokemon> pokemons = new HashSet<>();

        result.getItems().stream()
                .map(PokemonMapper::mapFromValueMap)
                .forEach(pokemons::add);

        return pokemons;
    }
}
