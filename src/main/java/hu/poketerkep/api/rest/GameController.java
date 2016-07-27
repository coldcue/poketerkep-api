package hu.poketerkep.api.rest;

import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.api.json.RawDataJsonDto;
import hu.poketerkep.api.mapper.PokemonMapper;
import hu.poketerkep.api.service.PokemonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @Cacheable("api")
    public RawDataJsonDto game() {

        RawDataJsonDto rawData = new RawDataJsonDto();

        List<PokemonJsonDto> pokemonJsonDtos = pokemonDataService.getAllPokemons().parallelStream()
                .map(PokemonMapper::mapToJsonDto)
                .collect(Collectors.toList());

        rawData.setPokemons(pokemonJsonDtos);

        return rawData;
    }
}
