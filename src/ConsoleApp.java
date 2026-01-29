import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ConsoleApp {

    public static void run() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nVeuillez choisir une action :");
            System.out.println("1 - Ajouter un utilisateur");
            System.out.println("2 - Afficher les utilisateurs");
            System.out.println("3 - Retour");
            System.out.print("> ");

            switch (sc.nextLine().trim()) {
                case "1" -> ajouterUtilisateur(sc);
                case "2" -> afficherUtilisateurs();
                case "3" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void ajouterUtilisateur(Scanner sc) {
        try (Connection c = ConnexionDB.getConnection()) {

            System.out.print("Nom : ");
            String nom = sc.nextLine().trim();

            System.out.print("Prénom : ");
            String prenom = sc.nextLine().trim();

            System.out.print("Email : ");
            String email = sc.nextLine().trim();

            System.out.print("Login (laisser vide = NULL) : ");
            String login = sc.nextLine().trim();
            if (login.isEmpty()) login = null;

            System.out.print("Password (laisser vide = NULL) : ");
            String password = sc.nextLine().trim();
            if (password.isEmpty()) password = null;

            String sql = "INSERT INTO utilisateurs(nom, prenom, email, login, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ps.setString(3, email);

                if (login == null) ps.setNull(4, java.sql.Types.VARCHAR);
                else ps.setString(4, login);

                if (password == null) ps.setNull(5, java.sql.Types.VARCHAR);
                else ps.setString(5, password);

                ps.executeUpdate();
            }

            System.out.println("Utilisateur ajouté.");

        } catch (Exception e) {
            System.out.println("Erreur lors de l’ajout : " + e.getMessage());
        }
    }

    private static void afficherUtilisateurs() {

        try (Connection c = ConnexionDB.getConnection()) {

            String sql = "SELECT id, nom, prenom, email, login, password FROM utilisateurs";
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                System.out.println("\n--- Utilisateurs ---");
                System.out.println("id | Nom | Prenom | email | login | password");
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + " | " +
                                    rs.getString("nom") + " " +
                                    rs.getString("prenom") + " | " +
                                    rs.getString("email") + " | " +
                                    rs.getString("login") + " | " +
                                    rs.getString("password")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur affichage : " + e.getMessage());
        }
    }
}
