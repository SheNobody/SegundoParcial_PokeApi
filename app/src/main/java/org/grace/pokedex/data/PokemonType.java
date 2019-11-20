package org.grace.pokedex.data;

import java.util.List;
import java.util.Map;

public class PokemonType {

    private String name;
    private Map<String, List<String>> damageRelations;
    private List<Pokemon> pokemons;

    public PokemonType(String name, Map<String, List<String>> damageRelations, List<Pokemon> pokemons) {
        this.name = name;
        this.damageRelations = damageRelations;
        this.pokemons = pokemons;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<String>> getDamageRelations() {
        return damageRelations;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public static String[] relationNames = {"double_damage_from", "double_damage_to", "half_damage_from",
            "half_damage_to", "no_damage_from", "no_damage_to"};

    public static String [] changedrelationNames = {"RECIBE DOBLE DAÑO", "HACE DOBLE DAÑO", "RECIBE MENOR DAÑO",
            "HACE MENOR DAÑO", "NO RECIBE DAÑO", "NO HACE DAÑO"};
}