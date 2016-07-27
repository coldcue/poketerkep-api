package hu.poketerkep.api.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonDataService {
    private static final String POKEMONS_TABLE = "pokemons";
    private static final String POKEMON_TABLE_KEY = "encounterId";


    private final AmazonDynamoDBAsync amazonDynamoDBAsync;

    @Autowired
    public PokemonDataService(AmazonDynamoDBAsync amazonDynamoDBAsync) {
        this.amazonDynamoDBAsync = amazonDynamoDBAsync;
    }

    @Cacheable("pokemons")
    public List<Pokemon> getAllPokemons() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(POKEMONS_TABLE);

        ScanResult result = amazonDynamoDBAsync.scan(scanRequest);

        return result.getItems().parallelStream()
                .map(PokemonMapper::mapFromValueMap)
                .collect(Collectors.toList());
    }
}
