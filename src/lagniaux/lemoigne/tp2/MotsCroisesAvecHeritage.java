package lagniaux.lemoigne.tp2;

public class MotsCroisesAvecHeritage<T> extends Grille<T> {

	Grille<Case> jeu;

	public MotsCroisesAvecHeritage(int hauteur, int largeur) {
		super(hauteur, largeur);
		jeu = new Grille<Case>(hauteur, largeur);
		for (int i = 1; i <= hauteur; i++) {
			for (int j = 1; j <= largeur; j++) {
				jeu.setCellule(i, j, new Case());
			}
		}
	}

	public int getHauteur() {
		return (jeu.getHauteur());
	}

	public int getLargeur() {
		return (jeu.getLargeur());
	}

// Validité des coordonnées
// Resultat : vrai si et seulement si (lig, col)
// désignent une cellule existante de la grille
	public boolean coordCorrectes(int lig, int col) {
		return jeu.coordCorrectes(lig, col);
	}

// Accesseurs aux cases noires
// Précondition (assert) : coordCorrectes(lig, col)
	public boolean estCaseNoire(int lig, int col) {
		assert coordCorrectes(lig, col);
		Case c = jeu.getCellule(lig, col);
		return (c.estCaseNoire);
	}

	public void setCaseNoire(int lig, int col, boolean noire) {
		assert coordCorrectes(lig, col);
		Case c = jeu.getCellule(lig, col);
		if (noire) {
			c.estCaseNoire = true;
		} else {
			c.estCaseNoire = false;
			c.solution = ' ';
			jeu.setCellule(lig, col, c);
		}

	}

// Accesseurs à la grille de solution
// Préconditions (assert) : coordCorrectes(lig, col) !estCaseNoire(lig, col)
	public char getSolution(int lig, int col) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		char soluce = jeu.getCellule(lig, col).solution;
		return soluce;
	}

	public void setSolution(int lig, int col, char sol) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		Case c = jeu.getCellule(lig, col);
		c.solution = sol;
		jeu.setCellule(lig, col, c);
	}

	public char getProposition(int lig, int col) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		char prop = jeu.getCellule(lig, col).proposition;
		return prop;
	}

	public void setProposition(int lig, int col, char prop) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		Case c = jeu.getCellule(lig, col);
		c.proposition = prop;
		jeu.setCellule(lig, col, c);
	}

	public String getDefinition(int lig, int col, boolean horiz) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		String def = "";
		if (horiz) {
			def = jeu.getCellule(lig, col).horizontal;
		} else {
			def = jeu.getCellule(lig, col).vertical;
		}
		return def;
	}

	public void setDefinition(int lig, int col, boolean horiz, String def) {
		assert !estCaseNoire(lig, col);
		Case c = jeu.getCellule(lig, col);
		if (horiz) {
			c.horizontal = def;
		} else {
			c.vertical = def;
		}
		jeu.setCellule(lig, col, c);
	}
	
}
