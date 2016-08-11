package hu.poketerkep.api.helper;

import hu.poketerkep.api.model.Pokemon;
import hu.poketerkep.api.rest.query.GameQueryJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        GameQueryJson gameQueryJson = new GameQueryJson();
        gameQueryJson.setGyms(false);
        gameQueryJson.setPokestops(false);
        gameQueryJson.setPokemons(true);
        gameQueryJson.setSince(null);
        gameQueryJson.setSelectedPokemons(Collections.emptyList());
        gameQueryJson.setShowOrHide(true);

        GameQueryJson.Bounds bounds = new GameQueryJson.Bounds();
        bounds.setNeLng(4.0);
        bounds.setNeLat(4.0);
        bounds.setSwLng(1.0);
        bounds.setSwLat(1.0);
        gameQueryJson.setBounds(bounds);

        PokemonFilter pokemonFilter = new PokemonFilter(gameQueryJson);

        List<Pokemon> filter = pokemonFilter.doFilter(pokemons);

        Assert.assertTrue(filter.contains(pokemon2_2));
        Assert.assertTrue(filter.contains(pokemon3_3));
        Assert.assertTrue(filter.contains(pokemon4_4));

        Assert.assertFalse(filter.contains(pokemon0_0));
        Assert.assertFalse(filter.contains(pokemon5_5));
        Assert.assertFalse(filter.contains(pokemon1_5));
        Assert.assertFalse(filter.contains(pokemon5_1));

    }

    @Test
    public void selectedPokemonsTest() {

        List<Pokemon> allPokemons = new ArrayList<>();
        List<Pokemon> pokemonsWithId1 = new ArrayList<>();
        List<Pokemon> pokemonsWithOtherIds = new ArrayList<>();

        long disappearTime = Long.MAX_VALUE;


        // Pokemons with ID 1
        Pokemon pokemon1_1 = new Pokemon("asd", disappearTime, 1.0, 4.0, 1, "test1", "asdsad");
        Pokemon pokemon1_2 = new Pokemon("asd", disappearTime, 2.0, 3.0, 1, "test1", "asdsad");
        Pokemon pokemon1_3 = new Pokemon("asd", disappearTime, 3.0, 2.0, 1, "test1", "asdsad");
        Pokemon pokemon1_4 = new Pokemon("asd", disappearTime, 4.0, 1.0, 1, "test1", "asdsad");

        pokemonsWithId1.addAll(Arrays.asList(pokemon1_1, pokemon1_2, pokemon1_3, pokemon1_4));

        // Pokemons with other IDs

        Pokemon pokemono_1 = new Pokemon("asd", disappearTime, 1.0, 4.0, 4, "test1", "asdsad");
        Pokemon pokemono_2 = new Pokemon("asd", disappearTime, 2.0, 3.0, 6, "test1", "asdsad");
        Pokemon pokemono_3 = new Pokemon("asd", disappearTime, 3.0, 2.0, 8, "test1", "asdsad");
        Pokemon pokemono_4 = new Pokemon("asd", disappearTime, 4.0, 1.0, 14, "test1", "asdsad");

        pokemonsWithOtherIds.addAll(Arrays.asList(pokemono_1, pokemono_2, pokemono_3, pokemono_4));

        allPokemons.addAll(pokemonsWithId1);
        allPokemons.addAll(pokemonsWithOtherIds);


        // Show only pokemons with ID 1
        {
            GameQueryJson gameQueryJson = new GameQueryJson();
            gameQueryJson.setGyms(false);
            gameQueryJson.setPokestops(false);
            gameQueryJson.setPokemons(true);
            gameQueryJson.setSince(null);
            gameQueryJson.setSelectedPokemons(Collections.singletonList(1));
            gameQueryJson.setShowOrHide(true);

            GameQueryJson.Bounds bounds = new GameQueryJson.Bounds();
            bounds.setNeLng(4.0);
            bounds.setNeLat(4.0);
            bounds.setSwLng(1.0);
            bounds.setSwLat(1.0);
            gameQueryJson.setBounds(bounds);

            PokemonFilter showOnly1 = new PokemonFilter(gameQueryJson);
            List<Pokemon> showOnly1Result = showOnly1.doFilter(allPokemons);

            Assert.assertTrue("It should only show pokemons with ID 1",
                    showOnly1Result.containsAll(pokemonsWithId1) && showOnly1Result.size() == 4);
        }

        // Show only pokemons with not ID 1
        {
            GameQueryJson gameQueryJson = new GameQueryJson();
            gameQueryJson.setGyms(false);
            gameQueryJson.setPokestops(false);
            gameQueryJson.setPokemons(true);
            gameQueryJson.setSince(null);
            gameQueryJson.setSelectedPokemons(Collections.singletonList(1));
            gameQueryJson.setShowOrHide(false);

            GameQueryJson.Bounds bounds = new GameQueryJson.Bounds();
            bounds.setNeLng(4.0);
            bounds.setNeLat(4.0);
            bounds.setSwLng(1.0);
            bounds.setSwLat(1.0);
            gameQueryJson.setBounds(bounds);

            PokemonFilter showOnly1 = new PokemonFilter(gameQueryJson);
            List<Pokemon> showOnly1Result = showOnly1.doFilter(allPokemons);

            Assert.assertTrue("It should only show pokemons with not ID 1",
                    showOnly1Result.containsAll(pokemonsWithOtherIds) && showOnly1Result.size() == 4);
        }

        // It should not filter any pokemons
        {
            GameQueryJson gameQueryJson = new GameQueryJson();
            gameQueryJson.setGyms(false);
            gameQueryJson.setPokestops(false);
            gameQueryJson.setPokemons(true);
            gameQueryJson.setSince(null);
            gameQueryJson.setSelectedPokemons(Collections.emptyList());
            gameQueryJson.setShowOrHide(true);

            GameQueryJson.Bounds bounds = new GameQueryJson.Bounds();
            bounds.setNeLng(4.0);
            bounds.setNeLat(4.0);
            bounds.setSwLng(1.0);
            bounds.setSwLat(1.0);
            gameQueryJson.setBounds(bounds);

            PokemonFilter showOnly1 = new PokemonFilter(gameQueryJson);
            List<Pokemon> showOnly1Result = showOnly1.doFilter(allPokemons);

            Assert.assertTrue("It should show all pokemons",
                    showOnly1Result.containsAll(allPokemons) && showOnly1Result.size() == 8);
        }
    }

}