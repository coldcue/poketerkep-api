package hu.poketerkep.api.helper;

import org.apache.commons.codec.binary.Base64;

import java.util.HashSet;


class Base64PokemonIdDecoder {
    static HashSet<Integer> decodePokemonIdsFromBase64(String base64) {
        // Null check
        if (base64 == null) {
            return new HashSet<>();
        }

        int offset = 0;
        HashSet<Integer> pokemonIds = new HashSet<>();

        byte[] bytes = Base64.decodeBase64(base64);

        // Iterate over 91 bytes. We need 91 bytes, so we have 91 * 8 = 728 maximum pokemon id, which is more than 721
        for (byte byte_value : bytes) {

            for (int i = 0; i < 8; i++) {
                // Mask out other bits
                if (((byte_value >> i) & 1) == 1) {
                    // If its set
                    pokemonIds.add(offset + i);
                }
            }

            offset += 8;
        }

        return pokemonIds;
    }
}
