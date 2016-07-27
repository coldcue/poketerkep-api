package hu.poketerkep.api.mapper;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.api.model.Pokemon;

import java.util.Map;

public class PokemonMapper {
    public static Pokemon mapFromValueMap(Map<String, AttributeValue> valueMap) {
        Pokemon pokemon = new Pokemon();

        pokemon.setEncounterId(valueMap.get("encounterId").getS());
        pokemon.setDisappearTime(Long.valueOf(valueMap.get("disappearTime").getN()));
        pokemon.setLatitude(Double.valueOf(valueMap.get("latitude").getN()));
        pokemon.setLongitude(Double.valueOf(valueMap.get("longitude").getN()));
        pokemon.setPokemonId(Integer.parseInt(valueMap.get("pokemonId").getN()));
        pokemon.setPokemonName(valueMap.get("pokemonName").getS());
        pokemon.setSpawnpointId(valueMap.get("spawnpointId").getS());

        return pokemon;
    }

    public static PokemonJsonDto mapToJsonDto(Pokemon pokemon) {
        PokemonJsonDto pokemonJsonDto = new PokemonJsonDto();

        pokemonJsonDto.setEncounter_id(pokemon.getEncounterId());
        pokemonJsonDto.setDisappear_time(pokemon.getDisappearTime());
        pokemonJsonDto.setLatitude(pokemon.getLatitude());
        pokemonJsonDto.setLongitude(pokemon.getLongitude());
        pokemonJsonDto.setPokemon_id(pokemon.getPokemonId());
        pokemonJsonDto.setPokemon_name(pokemon.getPokemonName());
        pokemonJsonDto.setSpawnpoint_id(pokemon.getSpawnpointId());

        return pokemonJsonDto;
    }
}
