package lagniaux.lemoigne.tp3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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

	public MotsCroisesAvecHeritage extraireGrille(int numGrille) throws SQLException {
		PreparedStatement ps = this.connexion
				.prepareStatement("SELECT hauteur,largeur FROM TP5_GRILLE WHERE num_grille = ?");
		ps.setInt(1, numGrille);
		ResultSet rs = ps.executeQuery();
		rs.next();
		MotsCroisesAvecHeritage mots = new MotsCroisesAvecHeritage(rs.getInt("hauteur"), rs.getInt("largeur"));
		ps.close();
		rs.close();
		ps = this.connexion.prepareStatement("SELECT * FROM TP5_MOT WHERE num_grille = ?");
		ps.setInt(1, numGrille);
		rs = ps.executeQuery();
		while (rs.next()) {
			if (rs.getBoolean("horizontal")) {
				int taille = rs.getString("solution").length();
				for (int i = 0; i < taille; i++) {
					mots.setSolution(rs.getInt("ligne"), rs.getInt("colonne") + i,
							new Character(rs.getString("solution").charAt(i)));
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Integer gr = 10;
		ChargerGrille chargerGrille = new ChargerGrille();

		Map<Integer, String> dispo = chargerGrille.grillesDisponibles();
		System.out.println(dispo.get(gr));

	}
}