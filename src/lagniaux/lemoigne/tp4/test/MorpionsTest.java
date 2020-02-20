package lagniaux.lemoigne.tp4.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import lagniaux.lemoigne.tp4.ModeleMorpions;
import lagniaux.lemoigne.tp4.SpecifModeleMorpions;
import morpions.kit.test.Bogue1;
import morpions.kit.test.MorpionsReference;
import morpions.kit.test.Bogue2;
import morpions.kit.test.Bogue3;
import morpions.kit.test.Bogue4;
import morpions.kit.test.Bogue5;


public class MorpionsTest
{
	ModeleMorpions morpions;
	public static final int TAILLE = 3;
	public static final int NB_CASES = 9;

	@Before
	public void setUp() throws Exception {
		//morpions = new MorpionsReference();
		//morpions = new Bogue1();
		//morpions = new Bogue2();
		//morpions = new Bogue3();
		//morpions = new Bogue4();
		//morpions = new Bogue5();
		morpions =  new ModeleMorpions() ;
	}
	
	// Scénario(s) vérifiant l'état du jeu avant le premier coup :
	// * non-fin de partie
	// * cases accessibles
	@Test
	public void testInit() {
		assertFalse("Partie pas finie au départ",morpions.estFinie());
		assertTrue("Coup non autorisé au départ",morpions.estCoupAutorise(1,1));
		

		// Test de l'invariant de la classe
		testInvariant();
	}
	
	// Scénario vérifiant le premier coup joué, notamment :
	// * position correcte ou non
	// * non-fin de partie
	// * identité du premier joueur

	@Test
	public void testPremierCoup() {
		assertEquals("Le joueur n'est pas le bon",1,morpions.getJoueur());
		assertTrue("Coup non autorisé au départ",morpions.estCoupAutorise(1,1));
		assertTrue("Partie pas finie après premier coup", !morpions.estFinie());
		morpions.jouerCoup(1, 1);	
		assertEquals("Le joueur n'est pas le bon",2,morpions.getJoueur());
				
				
		
		// On reteste l'invariant
		testInvariant();
	}

	private void testInvariant() {
		// Le nombre de coups est positif ou nul, et inférieur ou égal au nombre de
		// cases
		assertTrue("Nombre de coups >= 0", morpions.getNombreCoups() >= 0);
		assertTrue("Nombre de coups <= " + NB_CASES, morpions.getNombreCoups() <= NB_CASES);
	}

	@Test
	public void testCoupDejaJoue() {
		morpions.jouerCoup(1, 1);
		assertTrue("La case (1,1) ne peut être jouée", !morpions.estCoupAutorise(1, 1));
		
	}

	@Test
	public void testPartiePasFinie() {
		morpions.jouerCoup(1, 1);
		morpions.jouerCoup(2, 2);
		assertFalse("La partie n'est pas finie",morpions.estFinie());
	}
	


	@Test
	public void testJoueur1gagnant() {
		morpions.jouerCoup(1, 1);
		morpions.jouerCoup(2, 2);
		morpions.jouerCoup(1, 2);
		morpions.jouerCoup(2, 3);
		morpions.jouerCoup(1, 3);
		
		assertEquals("Le joueur 1 doit être gagnant",1,morpions.getVainqueur());
	}
	
	
	@Test
	public void testJoueur2gagnant() {
		morpions.jouerCoup(2, 1);
		morpions.jouerCoup(1, 1);
		morpions.jouerCoup(2, 2);
		morpions.jouerCoup(1, 2);
		morpions.jouerCoup(3, 2);
		morpions.jouerCoup(1, 3);
		assertEquals("Le joueur 2 doit être gagnant",2,morpions.getVainqueur());
	}
	
	@Test
	public void testMatchNul() {
		morpions.jouerCoup(1, 1);
		morpions.jouerCoup(2, 2);
		morpions.jouerCoup(3, 3);
		morpions.jouerCoup(3, 1);
		morpions.jouerCoup(1, 3);
		morpions.jouerCoup(2, 3);
		morpions.jouerCoup(2, 1);
		morpions.jouerCoup(1, 2);
		morpions.jouerCoup(3, 2);
		assertTrue("La partie n'est pas terminée",morpions.estFinie());
		assertEquals("Il n' y a pas de vainqueur",0,morpions.getVainqueur());
	}
}

