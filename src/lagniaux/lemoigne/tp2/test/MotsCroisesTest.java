package lagniaux.lemoigne.tp2.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lagniaux.lemoigne.tp2.MotsCroises;

public class MotsCroisesTest
{
	private MotsCroises mc ;
	private int hauteur, largeur ;
	private boolean[][] noire ;
	private Character[][] solution, proposition ;
	private String[][] horizontal, vertical ;

	@BeforeEach
	public void setUp() throws Exception
	{
		/* hauteur = 321 ;
		largeur = 654 ;
		*/
		hauteur = 7 ;
		largeur = 5 ;
		mc = new MotsCroises(hauteur, largeur) ;
		noire = new boolean[hauteur][largeur] ;
		solution = new Character[hauteur][largeur] ;
		proposition = new Character[hauteur][largeur] ;
		horizontal = new String[hauteur][largeur] ;
		vertical = new String[hauteur][largeur] ;

		System.out.println("Test de " + mc.getClass().getName()) ;

		// Mise en place des cases noires
		// Première étape : "mise à blanc" de toutes les cases
		for (int lig=1; lig <=mc.getHauteur(); lig++ )
		{
			for (int col=1; col <=mc.getLargeur(); col++ )
			{
				noire[lig-1][col-1] = false ;
				mc.setCaseNoire(lig, col, false);
				horizontal[lig-1][col-1] = "" ;
				mc.setDefinition(lig, col, true, "");
				vertical[lig-1][col-1] = "" ;
				mc.setDefinition(lig, col, false, "");
			}
		}

		// Deuxième étape : noircir toutes les cases d'une diagonale (i,i)
		// exceptée pour (2,2)
		int borneMin = Math.min(mc.getHauteur(), mc.getLargeur()) ;
		for (int i=1; i <= borneMin; i++ )
		{
			if (i != 2)
			{
				noire[i-1][i-1] = true ;
				mc.setCaseNoire(i, i, true);
			}
		}

		// Mise en place des solutions, des propositions et des définitions
		char lettre = 'A' ;
		for (int lig=1; lig<=mc.getHauteur(); lig++)
		{
			for (int col=1; col<=mc.getLargeur(); col++)
			{
				if (! mc.estCaseNoire(lig, col))
				{
					solution[lig-1][col-1] = lettre ;
					mc.setSolution(lig, col, lettre) ;
					String def = "(" + lig + "," + col + ") " + lettre ;
					if((lig+col)%2 == 0)
					{
						horizontal[lig-1][col-1] = def ;
						mc.setDefinition(lig, col, true, def) ;
					}
					if((lig+col)%3 == 0)
					{
						vertical[lig-1][col-1] = def ;
						mc.setDefinition(lig, col, false, def) ;
					}
					lettre++ ;
					proposition[lig-1][col-1] = lettre ;
					mc.setProposition(lig, col, lettre) ;
					lettre++ ;
				}
			}
		}

		// Création d'une 2ème instance pour tester d'éventuels effets de bord
		MotsCroises mc2 = new MotsCroises(2, 4);
		for (int lig=1; lig <=mc2.getHauteur(); lig++ )
		{
			for (int col=1; col <=mc2.getLargeur(); col++ )
			{
				mc2.setCaseNoire(lig, col, false);
			}
		}

		mc2.setCaseNoire(1, 1, true);
		mc2.setSolution(2, 3, 'Z');
		mc2.setProposition(2, 3, 'Y');
		mc2.setDefinition(2, 1, true, "WWWW") ;
		mc2.setDefinition(1, 3, false, "XXXX") ;
	}

	public void afficherReference(Object[][] tab, String libelle)
	{
		System.out.println("Référence " + libelle + " :") ;
		for (int lig=1; lig<=hauteur; lig++)
		{
			for (int col=1; col<=largeur; col++)
			{
				System.out.print("" + tab[lig-1][col-1] + "-") ;
			}
			System.out.println("") ;
		}
		System.out.println(" ");
	}

	@Test
	public void testCasesNoires()
	{
		for (int lig=1; lig<=mc.getHauteur(); lig++)
			for (int col=1; col<=mc.getLargeur(); col++)
			{
				if (noire[lig-1][col-1])
				{
					assertTrue(mc.estCaseNoire(lig, col),
									"La case (" + lig + "," + col + ") doit être noire") ;
				}
				else
				{
					assertFalse(mc.estCaseNoire(lig, col),
									"La case (" + lig + "," + col + ") ne doit pas être noire") ;
				}
			}
	}

	@Test
	public void testSolutions()
	{
		testGrille(solution, "solution") ;
	}

	@Test
	public void testPropositions()
	{
		testGrille(proposition, "proposition") ;
	}

	@Test
	public void testHorizontal()
	{
		testGrille(horizontal, "définition horizontale") ;
	}

	@Test
	public void testVertical()
	{
		testGrille(vertical, "définition verticale") ;
	}

	@Test
	public void testToutesAssertions()
	{
		if (testMethode(1).equals("ArrayIndexOutOfBoundsException"))
		{
			fail("Erreur d'indice : vérifiez que VM Arguments contient bien -enableassertions") ;
		}

		// StringBuffer probleme = new StringBuffer() ;
		String probleme = "" ;
		for (int meth=1; meth<=NB_METHODES; meth++)
		{
			probleme = testMethode(meth) ;
			// probleme.append(testMethode(meth));
			if (! probleme.isEmpty())
			{
				fail("Cet appel devrait déclencher une AssertionError : " + probleme) ;
			}
		}
	}

	private static final int NB_METHODES = 18 ;

	private String testMethode(int meth)
	{
		String[] scenario =
			{"estCaseNoire(0, largeur)",
			 "estCaseNoire(hauteur, -1)",
			 "estCaseNoire(hauteur+1, largeur)",
			 "estCaseNoire(hauteur, 9999)",
			 "setCaseNoire(hauteur, 0, true)",
			 "setCaseNoire(hauteur, largeur+1, true)",
			 "getSolution(0, 1)",
			 "setSolution(1, 0, 'A')",
			 "getProposition(hauteur+1, 1)",
			 "setProposition(1, largeur+1, 'A')",
			 "getDefinition(hauteur+1, 1, true)",
			 "setDefinition(1, largeur+1, false, \"bla bla\")",
			 "case noire : getSolution(1, 1)",
			 "case noire : setSolution(3, 3, 'B')",
			 "case noire : getProposition(4, 4)",
			 "case noire : setProposition(1, 1, 'C')",
			 "case noire : getDefinition(3, 3, true",
			 "case noire : setDefinition(4, 4, false, \"bla bla\")"
			} ;
		try
		{
			switch (meth)
			{
				case 1 : mc.estCaseNoire(0, largeur) ; break;
				case 2 : mc.estCaseNoire(hauteur, -1) ; break;
				case 3 : mc.estCaseNoire(hauteur+1, largeur); break;
				case 4 : mc.estCaseNoire(hauteur, 9999); break;
				case 5 : mc.setCaseNoire(hauteur, 0, true); break;
				case 6 : mc.setCaseNoire(hauteur, largeur+1, true); break;
				case 7 : mc.getSolution(0, 1); break;
				case 8 : mc.setSolution(1, 0, 'A'); break;
				case 9 : mc.getProposition(hauteur+1, 1); break;
				case 10 : mc.setProposition(1, largeur+1, 'A'); break;
				case 11 : mc.getDefinition(hauteur+1, 1, true); break;
				case 12 : mc.setDefinition(1, largeur+1, false, "bla bla"); break;
				case 13 : mc.getSolution(1, 1); break;
				case 14 : mc.setSolution(3, 3, 'B'); break;
				case 15 : mc.getProposition(4, 4); break;
				case 16 : mc.setProposition(1, 1, 'C'); break;
				case 17 : mc.getDefinition(3, 3, true); break;
				case 18 : mc.setDefinition(4, 4, false, "bla bla"); break;
			}
		}
		catch (AssertionError e)
		{
			return "" ;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return "ArrayIndexOutOfBoundsException" ;
		}
		return scenario[meth-1] + " / " ;
	}

	private void testGrille(Object[][] grille, String libelle)
	{
		// afficherReference(grille, libelle) ;
		Object attendu, observe = null ;
		for (int lig=1; lig<=mc.getHauteur(); lig++)
			for (int col=1; col<=mc.getLargeur(); col++)
			{
				if (! noire[lig-1][col-1])
				{
					attendu = grille[lig-1][col-1] ;
					switch (libelle)
					{
						case "solution" : observe = mc.getSolution(lig, col); break ;
						case "proposition" : observe = mc.getProposition(lig, col); break ;
						case "définition horizontale" : observe = mc.getDefinition(lig, col, true); break ;
/* !!! */				case "définition verticale" : observe = mc.getDefinition(lig, col, false); break ;
					}
					assertEquals(attendu, observe, "La case (" + lig + "," + col + ") de " + libelle
									+ " doit contenir \"" + attendu + "\".") ;
				}
			}
	}

}
