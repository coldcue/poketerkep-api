package hu.poketerkep.api.mapper;

import hu.poketerkep.api.json.PokemonJsonDto;
import hu.poketerkep.shared.model.Pokemon;

public class PokemonMapper {

    public static PokemonJsonDto mapToJsonDto(Pokemon pokemon) {
        PokemonJsonDto pokemonJsonDto = new PokemonJsonDto();

        pokemonJsonDto.setEncounter_id(pokemon.getEncounterId());
        pokemonJsonDto.setDisappear_time(pokemon.getDisappearTime());
        pokemonJsonDto.setLatitude(pokemon.getLatitude());
        pokemonJsonDto.setLongitude(pokemon.getLongitude());
        pokemonJsonDto.setPokemon_id(pokemon.getPokemonId());

        return pokemonJsonDto;
    }
}
