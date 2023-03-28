package com.montaury.pokebagarre.ui;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
@ExtendWith(ApplicationExtension.class)
class PokeBagarreAppTest {
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1 = "#nomPokemon1";
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2 = "#nomPokemon2";
    private static final String IDENTIFIANT_BOUTON_BAGARRE = ".button";

    @Start
    private void start(Stage stage) {
        new PokeBagarreApp().start(stage);
    }

    @Test
    void on_ne_donne_pas_le_nom_du_premier_pokemon(FxRobot robot) {
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        //On a pas besoin d'attendre car on appelle pas l'API
        assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le premier pokemon n'est pas renseigne");
    }

    @Test
    void on_ne_donne_pas_le_nom_du_deuxieme_pokemon(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        //await().atMost(1, TimeUnit.SECONDS).untilAsserted(() ->
        //On a pas besoin d'attendre car on appelle pas l'API
        assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le second pokemon n'est pas renseigne");

    }

    @Test
    void on_donne_un_nom_de_pokemon_qui_nexiste_pas(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("MissingNo.");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de recuperer les details sur 'MissingNo.'"));
    }

    @Test
    void les_deux_pokemons_sont_identiques(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);
        assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    void le_pokemon_1_gagne(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Raichu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getResultatBagarre(robot)).isEqualTo("Le vainqueur est: Raichu"));
    }



    @Test
    void le_pokemon_2_gagne(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Giratina");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(getResultatBagarre(robot)).isEqualTo("Le vainqueur est: Giratina"));
    }


    private static String getResultatBagarre(FxRobot robot) {
        return robot.lookup("#resultatBagarre").queryText().getText();
    }

    private static String getMessageErreur(FxRobot robot) {
        return robot.lookup("#resultatErreur").queryLabeled().getText();

    }
}