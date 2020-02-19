package lagniaux.lemoigne.tp1;

public class Grille {

	public static void main(String[] args) {
		Grille maGrille = new Grille(3, 5);
		for (int l = 1; l <= maGrille.getHauteur(); l++) {
			String texteLigne = Integer.toString(l);
			for (int c = 1; c <= maGrille.getLargeur(); c++) {
				maGrille.setCellule(l, c, texteLigne + ',' + Integer.toString(c));
			}
		}
		System.out.println(maGrille);
	}

	// hauteur = nombre de lignes
	protected int hauteur;
	
	// largeur = nombre de colonnes
	protected int largeur;

	// tab = tableau de chaînes de caractères à deux dimensions,
	protected String[][] tab;

	// avec taille = hauteur x largeur
	protected int taille;

	// Constructeur permettant d’obtenir une grille
	// dotée d’un tableau de dimensions conformes aux valeurs
	// respectives de hauteur et de largeur, dont tous les
	// éléments contiennent la valeur null.
	// Précondition (assert) : hauteur 0 et largeur 0 ≥ ≥
	public Grille(int hauteur, int largeur) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		assert hauteur >= 0 && largeur >= 0;
		this.tab = new String[hauteur][largeur];
		this.taille = hauteur * largeur;
	}

	// Accesseurs (getters)
	public int getHauteur() {
		return hauteur;

	}

	public int getLargeur() {
		return largeur;

	}

	// Validité des coordonnées
	// Resultat : vrai si et seulement si lig (resp. col)
	// est compris entre 1 et getHauteur() (resp. getlargeur())

	public boolean coordCorrectes(int lig, int col) {
		return (lig >= 1 && lig <= getHauteur()) && (col >= 1 && col <= getLargeur());
	}

	// Valeur de la cellule ayant pour coordonnées (lig, col)
	// Précondition (assert) : coordCorrectes(lig, col)
	public String getCellule(int lig, int col) {
		assert coordCorrectes(lig, col);
		return tab[lig - 1][col - 1];

	}

	// Modification de la cellule de coordonnées (lig, col)
	// Précondition (assert) : coordCorrectes(lig, col)
	public void setCellule(int lig, int col, String ch) {
		tab[lig - 1][col - 1] = ch;

	}

	// Texte sur “hauteur�? lignes, colonnes séparées par des |
	// (voir exemple plus loin)
	@Override
	public String toString() {
		String s = "";
		for (int h = 0; h < getHauteur(); h++) {
			for (int l = 0; l < largeur; l++) {
				s = s + tab[h][l] + "|";
			}
			s = s + "\n";
		}
		return s;
	}

}
