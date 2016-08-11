package hu.poketerkep.api.rest;

import hu.poketerkep.api.helper.Coordinate;
import hu.poketerkep.api.helper.PokemonFilter;
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
    public RawDataJsonDto game(
            @RequestParam(required = false) Double neLat,
            @RequestParam(required = false) Double neLng,
            @RequestParam(required = false) Double swLat,
            @RequestParam(required = false) Double swLng,
            @RequestParam(required = false) Long since) {


        // Convert coordinates
        Coordinate northEast = null;
        Coordinate southWest = null;
        if (neLat != null && neLng != null && swLat != null && swLng != null) {
            northEast = new Coordinate(neLat, neLng);
            southWest = new Coordinate(swLat, swLng);
        }

        // Get and filter pokemons
        PokemonFilter pokemonFilter = new PokemonFilter(northEast, southWest, since);
        List<Pokemon> pokemons = pokemonFilter.doFilter(pokemonDataService.getAllPokemons());

        // Map to JSON DTO
        RawDataJsonDto rawData = new RawDataJsonDto();
        List<PokemonJsonDto> pokemonJsonDtos = pokemons.stream()
                .map(PokemonMapper::mapToJsonDto)
                .collect(Collectors.toList());

        rawData.setPokemons(pokemonJsonDtos);
        rawData.setRequestTime(Instant.now().toEpochMilli());

        return rawData;
    }
}
