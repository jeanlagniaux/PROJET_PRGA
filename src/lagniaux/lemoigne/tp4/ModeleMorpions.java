package lagniaux.lemoigne.tp4;

import javafx.beans.property.ReadOnlyIntegerWrapper;

public class ModeleMorpions implements SpecifModeleMorpions {
	private int[][] grille;
	Etat etat;
	int taille = SpecifModeleMorpions.TAILLE;
	private int nbCoups;

	public ModeleMorpions() {
		this.grille = new int[3][3];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				this.grille[i][j] = 0;
			}
		}
		nbCoups = 0;
		etat = Etat.J2_JOUE;
	}

	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {
		this.grille = grille;
	}

	/*
	 * getEtatJeu() : état actuel du jeu
	 * 
	 * @return une des valeurs du type énuméré SpecifMorpions.Etat
	 */
	@Override
	public Etat getEtatJeu() {
		return etat;
	}

	/*
	 * getJoueur() : numéro du joueur courant
	 * 
	 * @return numéro du prochain joueur (1 ou 2), ou 0 si le jeu est fini
	 */
	@Override
	public int getJoueur() {
		if (getEtatJeu() == Etat.J1_JOUE) {
			return 2;
		}
		if (getEtatJeu() == Etat.J2_JOUE) {
			return 1;
		}
		return 0;

	}

	/*
	 * getVainqueur() : numéro du vainqueur
	 * 
	 * @return numéro du vainqueur (1 ou 2), ou 0 s'il n'y a pas, ou pas encore, de
	 * vainqueur
	 */
	@Override
	public int getVainqueur() {
		for (int i = 0; i < 3; i++) {
			if (grille[i][0] * grille[i][1] * grille[i][2] == 1) {
				return 1;
			}
			if (grille[0][i] * grille[1][i] * grille[2][i] == 1) {
				return 1;
			}
			if (grille[i][0] * grille[i][1] * grille[i][2] == 8) {
				return 2;
			}
			if (grille[0][i] * grille[1][i] * grille[2][i] == 8) {
				return 2;
			}
		}
		if (grille[0][0] * grille[1][1] * grille[2][2] == 1) {
			return 1;
		}
		if (grille[0][0] * grille[1][1] * grille[2][2] == 8) {
			return 2;
		}
		if (grille[0][2] * grille[1][1] * grille[2][0] == 1) {
			return 1;
		}
		if (grille[0][2] * grille[1][1] * grille[2][0] == 8) {
			return 2;
		}
		return 0;
	}

	/*
	 * getNombreCoups() : nombre de coups joués
	 * 
	 * @return nombre de coups joués
	 */
	@Override
	public int getNombreCoups() {
		return nbCoups;
	}

	/*
	 * estFinie() : détermine si la partie est terminée ou non
	 * 
	 * @return vrai si et seulement si getEtatJeu() est dans {MATCH_NUL,
	 * J1_VAINQUEUR, J2_VAINQUEUR}
	 */
	@Override
	public boolean estFinie() {
		return getEtatJeu() == Etat.MATCH_NUL || getEtatJeu() == Etat.J1_VAINQUEUR || getEtatJeu() == Etat.J2_VAINQUEUR;
	}

	/*
	 * estCoupAutorise() : valide le coup (ligne, colonne) sans le jouer
	 * 
	 * @param ligne : numéro de ligne
	 * 
	 * @param colonne : numéro de colonne
	 * 
	 * @return true si le coup est autorisé, false sinon
	 */
	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {
		return grille[ligne - 1][colonne - 1] == 0;
	}

	/*
	 * jouerCoup() : joue le coup (ligne, colonne)
	 * 
	 * @param ligne : numéro de ligne
	 * 
	 * @param colonne : numéro de colonne
	 * 
	 * @pre ! this.estFinie() ;
	 * 
	 * @pre this.estCoupAutorise(ligne, colonne)
	 */
	@Override
	public void jouerCoup(int ligne, int colonne) {
		Etat et = getEtatJeu();

		grille[ligne - 1][colonne - 1] = getJoueur();

		if (etat == Etat.J1_JOUE) {
			this.etat = Etat.J2_JOUE;
		} else if (etat == Etat.J2_JOUE) {
			this.etat = Etat.J1_JOUE;
		}

		nbCoups++;

		if (nbCoups == 9) {
			this.etat = Etat.MATCH_NUL;
		}

		int statut = getVainqueur();
		if (statut == 1) {
			etat = Etat.J1_VAINQUEUR;
		} else if (statut == 2) {
			this.etat = Etat.J2_VAINQUEUR;
		}

	}

	public void printGame() {
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				System.out.print(this.grille[i][j] + "\t");
				;
			}
			System.out.println();
		}
	}

}
