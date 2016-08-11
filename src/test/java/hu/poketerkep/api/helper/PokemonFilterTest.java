package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PokemonFilterTest {


    @Test
    public void geoSpatialTest() throws Exception {

        List<Pokemon> pokemons = new ArrayList<>();

        long disappearTime = Long.MAX_VALUE;

        Pokemon pokemon0_0 = new Pokemon();
        pokemon0_0.setLongitude(0.0);
        pokemon0_0.setLatitude(0.0);
        pokemon0_0.setDisappearTime(disappearTime);
        pokemons.add(pokemon0_0);

        Pokemon pokemon2_2 = new Pokemon();
        pokemon2_2.setLongitude(2.0);
        pokemon2_2.setLatitude(2.0);
        pokemon2_2.setDisappearTime(disappearTime);
        pokemons.add(pokemon2_2);

        Pokemon pokemon3_3 = new Pokemon();
        pokemon3_3.setLongitude(3.0);
        pokemon3_3.setLatitude(3.0);
        pokemon3_3.setDisappearTime(disappearTime);
        pokemons.add(pokemon3_3);

        Pokemon pokemon4_4 = new Pokemon();
        pokemon4_4.setLongitude(4.0);
        pokemon4_4.setLatitude(4.0);
        pokemon4_4.setDisappearTime(disappearTime);
        pokemons.add(pokemon4_4);

        Pokemon pokemon5_5 = new Pokemon();
        pokemon5_5.setLongitude(5.0);
        pokemon5_5.setLatitude(5.0);
        pokemon5_5.setDisappearTime(disappearTime);
        pokemons.add(pokemon5_5);

        Pokemon pokemon1_5 = new Pokemon();
        pokemon1_5.setLongitude(1.0);
        pokemon1_5.setLatitude(5.0);
        pokemon1_5.setDisappearTime(disappearTime);
        pokemons.add(pokemon1_5);

        Pokemon pokemon5_1 = new Pokemon();
        pokemon5_1.setLongitude(1.0);
        pokemon5_1.setLatitude(5.0);
        pokemon5_1.setDisappearTime(disappearTime);
        pokemons.add(pokemon1_5);

        PokemonFilter pokemonFilter = new PokemonFilter(
                new Coordinate(4.0, 4.0),
                new Coordinate(1.0, 1.0),
                0L);

        List<Pokemon> filter = pokemonFilter.doFilter(pokemons);

        Assert.assertTrue(filter.contains(pokemon2_2));
        Assert.assertTrue(filter.contains(pokemon3_3));
        Assert.assertTrue(filter.contains(pokemon4_4));

        Assert.assertFalse(filter.contains(pokemon0_0));
        Assert.assertFalse(filter.contains(pokemon5_5));
        Assert.assertFalse(filter.contains(pokemon1_5));
        Assert.assertFalse(filter.contains(pokemon5_1));

    }

}