package hu.poketerkep.api.rest;

import hu.poketerkep.api.helper.PokemonFilter;
import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.api.json.RawDataJsonDto;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.rest.query.GameQuery;
import hu.poketerkep.api.service.PokemonDataService;
import hu.poketerkep.shared.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.exceptions.JedisException;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/game")
public class GameController {
    private final PokemonDataService pokemonDataService;

    @Autowired
    public GameController(PokemonDataService pokemonDataService) {
        this.pokemonDataService = pokemonDataService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RawDataJsonDto> game(GameQuery query) {

        // Get and filter pokemons
        Set<Pokemon> allPokemons;
        try {
            allPokemons = pokemonDataService.getAllPokemons(query);
        } catch (JedisException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        PokemonFilter pokemonFilter = new PokemonFilter(query);
        Collection<Pokemon> pokemons = pokemonFilter.doFilter(allPokemons);

        // Map to JSON DTO
        RawDataJsonDto rawData = new RawDataJsonDto();

        // Convert pokemons to JSON dto
        HashSet<PokemonJsonDto> pokemonJsonDtos = new HashSet<>(pokemons.size());
        pokemons.stream()
                .map(PokemonMapper::mapToJsonDto)
                .forEach(pokemonJsonDtos::add);

        rawData.setPokemons(pokemonJsonDtos);
        rawData.setRequestTime(Instant.now().toEpochMilli());

        return ResponseEntity.ok(rawData);
    }
}
