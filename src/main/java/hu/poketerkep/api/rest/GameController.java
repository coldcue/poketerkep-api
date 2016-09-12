package hu.poketerkep.api.rest;

import hu.poketerkep.api.helper.PokemonFilter;
import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.api.json.RawDataJsonDto;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.rest.query.GameQuery;
import hu.poketerkep.api.service.PokemonDataService;
import hu.poketerkep.shared.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;


@RestController
@RequestMapping("/game")
public class GameController {
    private final PokemonDataService pokemonDataService;
    private final ExecutorService executorService;

    @Autowired
    public GameController(PokemonDataService pokemonDataService, ExecutorService executorService) {
        this.pokemonDataService = pokemonDataService;
        this.executorService = executorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<RawDataJsonDto> game(GameQuery query) {
        DeferredResult<RawDataJsonDto> deferredResult = new DeferredResult<>();

        executorService.submit(() -> {
            // Get and filter pokemons
            PokemonFilter pokemonFilter = new PokemonFilter(query);
            Collection<Pokemon> pokemons = pokemonFilter.doFilter(pokemonDataService.getAllPokemons(query));

            // Map to JSON DTO
            RawDataJsonDto rawData = new RawDataJsonDto();

            // Convert pokemons to JSON dto
            HashSet<PokemonJsonDto> pokemonJsonDtos = new HashSet<>(pokemons.size());
            pokemons.stream()
                    .map(PokemonMapper::mapToJsonDto)
                    .forEach(pokemonJsonDtos::add);

            rawData.setPokemons(pokemonJsonDtos);
            rawData.setRequestTime(Instant.now().toEpochMilli());

            deferredResult.setResult(rawData);
        });

        return deferredResult;
    }
}
