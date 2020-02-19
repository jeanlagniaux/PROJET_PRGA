package lagniaux.lemoigne.tp2;

public class MotsCroises {

	Grille<String> solution;
	Grille<String> proposition;
	Grille<String> horizontal;
	Grille<String> vertical;

	public MotsCroises(int h, int l) {

		solution = new Grille<String>(h, l);
		proposition = new Grille<String>(h, l);
		horizontal = new Grille<String>(h, l);
		vertical = new Grille<String>(h, l);
	}

	public int getHauteur() {
		return (solution.getHauteur());
	}

	public int getLargeur() {
		return (solution.getLargeur());
	}

// Validité des coordonnées
// Resultat : vrai si et seulement si (lig, col)
// désignent une cellule existante de la grille
	public boolean coordCorrectes(int lig, int col) {
		return solution.coordCorrectes(lig, col);
	}

// Accesseurs aux cases noires
// Précondition (assert) : coordCorrectes(lig, col)
	public boolean estCaseNoire(int lig, int col) {
		assert coordCorrectes(lig, col);
		return (solution.getCellule(lig, col) == null);
	}

	public void setCaseNoire(int lig, int col, boolean noire) {
		assert coordCorrectes(lig, col);
		if (noire) {
			solution.setCellule(lig, col, null);
		} else {
			solution.setCellule(lig, col, (" "));
		}

	}

// Accesseurs à la grille de solution
// Préconditions (assert) : coordCorrectes(lig, col) !estCaseNoire(lig, col)
	public char getSolution(int lig, int col) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		return (solution.getCellule(lig, col).charAt(0));
	}

	public void setSolution(int lig, int col, char sol) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		solution.setCellule(lig, col, Character.toString(sol));
	}

	public char getProposition(int lig, int col) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		return (proposition.getCellule(lig, col).charAt(0));
	}

	public void setProposition(int lig, int col, char sol) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		proposition.setCellule(lig, col, Character.toString(sol));
	}

	public String getDefinition(int lig, int col, boolean horiz) {
		assert coordCorrectes(lig, col) && !estCaseNoire(lig, col);
		if (horiz) {
			return horizontal.getCellule(lig, col);
		} else {
			return vertical.getCellule(lig, col);
		}
	}

	public void setDefinition(int lig, int col, boolean horiz, String def) {
		assert !estCaseNoire(lig, col);
		if (horiz) {
			horizontal.setCellule(lig, col, def);
		} else {
			vertical.setCellule(lig, col, def);
		}
	}
	
	public String solutions() {
		
		return null;
		}
}
