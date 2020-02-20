package lagniaux.lemoigne.tp3.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

import lagniaux.lemoigne.tp2.Case;
import lagniaux.lemoigne.tp2.MotsCroisesAvecHeritage;
import lagniaux.lemoigne.tp3.ChargerGrille;

class ChargerGrilleTest {

	@Test
	void test() throws SQLException {
		String solution = "ARCHERDOUANEJUIN*BONT*VOID*GINN*RENDTIFOSI";
		ChargerGrille chargerGrille = new ChargerGrille();
		MotsCroisesAvecHeritage<Case> mots = chargerGrille.extraireGrille(10);
		String reponse = "";
		for (int i = 1; i < mots.getHauteur() + 1; i++) {
			for (int j = 1; j < mots.getLargeur() + 1; j++) {
				char c = mots.getSolution(i, j);
				reponse += String.valueOf(c);
			}
		}
		assertTrue(solution.equals(reponse));
	}

}
