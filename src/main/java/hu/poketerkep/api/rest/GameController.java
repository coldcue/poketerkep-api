package hu.poketerkep.api.rest;

import hu.poketerkep.api.helper.Coordinate;
import hu.poketerkep.api.helper.GeospatialPokemonFilter;
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
import java.util.stream.Stream;


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

        RawDataJsonDto rawData = new RawDataJsonDto();

        Stream<Pokemon> pokemonStream = pokemonDataService.getAllPokemons().stream();

        long now = Instant.now().toEpochMilli();

        // Filter old pokemons
        pokemonStream.filter(pokemon -> pokemon.getDisappearTime() > now);

        // Time filter
        if (since != null) {
            pokemonStream
                    //If the pokemon's disappear time minus 15 mins is bigger then since
                    .filter(pokemon -> pokemon.getDisappearTime() - 15 * 60 * 1000 > since)
                    .collect(Collectors.toList());
        }

        // Geospatial filter
        if (neLat != null && neLng != null && swLat != null && swLng != null) {
            GeospatialPokemonFilter geospatialPokemonFilter = new GeospatialPokemonFilter(pokemonStream);

            Coordinate northEast = new Coordinate(neLat, neLng);
            Coordinate southWest = new Coordinate(swLat, swLng);

            pokemonStream = geospatialPokemonFilter.filter(northEast, southWest);
        }

        // Map to JSON DTO
        List<PokemonJsonDto> pokemonJsonDtos = pokemonStream
                .map(PokemonMapper::mapToJsonDto)
                .collect(Collectors.toList());

        rawData.setPokemons(pokemonJsonDtos);
        rawData.setRequestTime(Instant.now().toEpochMilli());

        return rawData;
    }
}
