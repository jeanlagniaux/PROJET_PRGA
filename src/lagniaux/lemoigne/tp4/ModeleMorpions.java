package lagniaux.lemoigne.tp4;

public class ModeleMorpions implements SpecifModeleMorpions {
	private int[][] grille;

	public ModeleMorpions() {
		this.grille = new int[3][3];
	}

	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {
		this.grille = grille;
	}

	@Override
	public Etat getEtatJeu() {
		Etat etat = Etat.J1_JOUE;
		int statut = getVainqueur();
		int nbCoups = getNombreCoups();
		if (statut == 1) {
			etat = Etat.J1_VAINQUEUR;
		} else if (statut == 2) {
			etat = Etat.J1_VAINQUEUR;
		} else if (statut == 0) {
			if (nbCoups == 0) {
				etat = Etat.MATCH_NUL;
			} else if (nbCoups % 2 == 0) {
				etat = Etat.J2_JOUE;
			}
		}
		return etat;
	}

	@Override
	public int getJoueur() {
		if (getEtatJeu() == Etat.J1_JOUE) {
			return 1;
		}
		if (getEtatJeu() == Etat.J2_JOUE) {
			return 2;
		}
		return 0;

	}

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
		return 0;
	}

	@Override
	public int getNombreCoups() {
		int cpt = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.grille[i][j] == 0) {
					cpt++;
				}
			}
		}
		return cpt;
	}

	@Override
	public boolean estFinie() {
		return getEtatJeu() == Etat.MATCH_NUL || getEtatJeu() == Etat.J1_VAINQUEUR || getEtatJeu() == Etat.J2_VAINQUEUR;
	}

	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {
		return grille[ligne - 1][colonne - 1] == 0;
	}

	@Override
	public void jouerCoup(int ligne, int colonne) {
		int etat = getJoueur();
		if (etat == 1) {
			grille[ligne - 1][colonne - 1] = 1;
		} else if (etat == 2) {
			grille[ligne - 1][colonne - 1] = 2;
		}

	}

}
