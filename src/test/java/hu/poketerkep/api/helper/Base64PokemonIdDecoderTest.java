package hu.poketerkep.api.helper;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class Base64PokemonIdDecoderTest {


    @Test
    public void decodePokemonIdsFromBase64() throws Exception {
        HashSet<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 3, 5, 23, 31, 32, 33, 55, 66, 67, 87, 88, 98, 161, 400, 543, 654, 721, 722));
        String input = "KwCAgAMAgAAMAIABBAAAAAAAAAACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAIAAAAAAAAAAAAAAAAAAQAAAAAAAAAAABg==";
        HashSet<Integer> output = Base64PokemonIdDecoder.decodePokemonIdsFromBase64(input);

        Assert.assertArrayEquals(expected.toArray(), output.toArray());
    }

}