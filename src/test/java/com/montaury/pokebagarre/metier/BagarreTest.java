package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class BagarreTest {

    @Test
    void demarrer() throws ExecutionException, InterruptedException {
        //Bagarre bagarre = new Bagarre();
        //CompletableFuture<Pokemon> pokemon = bagarre.demarrer("pikachu", "bulbizarre");
        //assertEquals("Pikachu", pokemon.get().getNom());

        //On utilise le mock pour ne pas avoir à appeler l'API
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        var bagarre = new Bagarre(fausseApi);
        var pikachu = new Pokemon("Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png", new Stats(10, 10));
        var bulbizarre = new Pokemon("Bulbizarre", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", new Stats(5, 5));
        Mockito.when(fausseApi.recupererParNom("pikachu")).thenReturn(CompletableFuture.completedFuture(pikachu));
        Mockito.when(fausseApi.recupererParNom("bulbizarre")).thenReturn(CompletableFuture.completedFuture(bulbizarre));
        var pokemon = bagarre.demarrer("pikachu", "bulbizarre");
        assertEquals("Pikachu", pokemon.get().getNom());
    }
}