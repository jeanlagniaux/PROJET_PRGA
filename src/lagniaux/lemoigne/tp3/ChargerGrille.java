package lagniaux.lemoigne.tp3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import lagniaux.lemoigne.tp2.Case;
import lagniaux.lemoigne.tp2.MotsCroisesAvecHeritage;

public class ChargerGrille {

	private Connection connexion;

	public Connection getConnexion() {
		return connexion;
	}

	public ChargerGrille() {
		try {
			connexion = conneterBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection conneterBD() throws SQLException {
		Connection connect;
		connect = DriverManager.getConnection("jdbc:mysql://mysql.istic.univ-rennes1.fr:3306/base_bousse",
				"user_jlagniaux", "rootroot");
		return connect;
	}

	public Map<Integer, String> grillesDisponibles() {
		Map<Integer, String> disponible = new HashMap<>();
		String sql = "SELECT * FROM TP5_GRILLE";
		try {
			Statement req = getConnexion().createStatement();
			ResultSet resultat = req.executeQuery(sql);
			while (resultat.next()) {
				disponible.put(new Integer(resultat.getInt("num_grille")), resultat.getString("nom_grille") + " "
						+ resultat.getString("hauteur") + "x" + resultat.getString("largeur"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disponible;
	}

	public MotsCroisesAvecHeritage<Case> extraireGrille(int numGrille) throws SQLException {
		PreparedStatement ps = this.connexion
				.prepareStatement("SELECT hauteur,largeur FROM TP5_GRILLE WHERE num_grille = ?");
		ps.setInt(1, numGrille);
		ResultSet rs = ps.executeQuery();
		rs.next();
		MotsCroisesAvecHeritage<Case> mots = new MotsCroisesAvecHeritage<Case>(rs.getInt("hauteur"),
				rs.getInt("largeur"));
		for (int i = 1; i < rs.getInt("hauteur"); i++) {
			for (int j = 1; j < rs.getInt("largeur"); j++) {
				mots.setSolution(i, j, '*');
			}
		}
		ps.close();
		rs.close();
		ps = this.connexion.prepareStatement("SELECT * FROM TP5_MOT WHERE num_grille = ?");
		ps.setInt(1, numGrille);
		rs = ps.executeQuery();
		while (rs.next()) {
			if (rs.getBoolean("horizontal")) {
				int taille = rs.getString("solution").length();
				for (int i = 0; i < taille; i++) {
					Character car = new Character(rs.getString("solution").charAt(i));
					mots.setSolution(rs.getInt("ligne"), rs.getInt("colonne") + i, car = Character.toUpperCase(car));
				}
			} else {
				int taille = rs.getString("solution").length();
				for (int i = 0; i < taille; i++) {
					Character car = new Character(rs.getString("solution").charAt(i));
					mots.setSolution(rs.getInt("ligne") + i, rs.getInt("colonne"), car = Character.toUpperCase(car));
				}
			}
		}
		return mots;
	}

	public static void main(String[] args) throws SQLException {

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
		if (reponse.equals(solution)) {
			System.out.println("true");
		}

	}
}