package hu.poketerkep.api.rest;

import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.api.json.RawDataJsonDto;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.model.Pokemon;
import hu.poketerkep.api.service.PokemonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class GameController {
    private final PokemonDataService pokemonDataService;

    @Autowired
    public GameController(PokemonDataService pokemonDataService) {
        this.pokemonDataService = pokemonDataService;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public RawDataJsonDto game(@RequestParam(required = false) Long since) {

        RawDataJsonDto rawData = new RawDataJsonDto();

        List<Pokemon> pokemons = pokemonDataService.getAllPokemons();

        if (since != null) {
            pokemons = pokemons.parallelStream()
                    //If the pokemon's disappear time minus 15 mins is bigger then since
                    .filter(pokemon -> pokemon.getDisappearTime() - 15 * 60 * 1000 > since)
                    .collect(Collectors.toList());
        }


        List<PokemonJsonDto> pokemonJsonDtos = pokemons.parallelStream()
                .map(PokemonMapper::mapToJsonDto)
                .collect(Collectors.toList());

        rawData.setPokemons(pokemonJsonDtos);
        rawData.setRequestTime(Instant.now().toEpochMilli());

        return rawData;
    }
}
