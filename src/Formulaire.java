import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Formulaire extends JFrame {

    private JTextField txtNom, txtPrenom, txtEmail, txtLogin;
    private JPasswordField txtPassword;
    private JTable tableau;
    private DefaultTableModel listeTableau;


    // Ajouter dans la base de données l'utilisateur saisi
    private void ajouterUtilisateur() {
        // Récupérer les contenus du formulaire lorsque la methode ajouterUtilisateur() est invoqué
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();
        String password = new String(txtPassword.getPassword());

        // Vider les champs lorsque les données sont récupérées
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        txtLogin.setText("");
        txtPassword.setText("");

        String sql = "INSERT INTO utilisateurs(nom, prenom, email, login, password) VALUES (?, ?, ?, ?, ?)";

        // si nom, email, login, password ne sont pas vide, ajoute les données dans la base
        if(nom.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Les champs vides ne sont pas acceptés !");
        }else{
            try (Connection conn = ConnexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ps.setString(3, email);
                ps.setString(4, login);
                ps.setString(5, password);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Utilisateur ajouté !");
                chargerUtilisateurs();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

// Charger l'utilisateur depuis la base de données
    private void chargerUtilisateurs() {
        listeTableau.setRowCount(0);
        String sql = "SELECT id, nom, prenom, email, login FROM utilisateurs";
        try (Connection conn = ConnexionDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                listeTableau.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("login")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // Formulaire permettant d'ajouter des utilisateurs dans la base de données
    public Formulaire() {

        // Les champs de texte
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        txtLogin = new JTextField();
        txtPassword = new JPasswordField();

        setTitle("Gestion des utilisateurs");
        // coordonnées et dimension de la fenetre
        setBounds(50, 50, 600, 440);

        // Arrêter l'application lorsque le bouton fermer est appuyé
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Désactive les layouts
        // => Ajouter manuellement les composant en spécifiant leurs coordonnées et tailles
        setLayout(null);

        // Définition des labels et zone de saisies

        JLabel lblNom = new JLabel("Nom:");
        lblNom.setBounds(30, 30, 100, 25);
        txtNom.setBounds(140, 30, 250, 25);
        add(lblNom);
        add(txtNom);

        JLabel lblPrenom = new JLabel("Prénom:");
        lblPrenom.setBounds(30, 60, 100, 25);
        txtPrenom.setBounds(140, 60, 250, 25);
        add(lblPrenom);
        add(txtPrenom);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(30, 90, 100, 25);
        txtEmail.setBounds(140, 90, 250, 25);
        add(lblEmail);
        add(txtEmail);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(30, 120, 100, 25);
        txtLogin.setBounds(140, 120, 200, 25);
        add(lblLogin);
        add(txtLogin);

        JLabel lblPassword = new JLabel("Mot de passe:");
        lblPassword.setBounds(30, 150, 100, 25);
        txtPassword.setBounds(140, 150, 200, 25);
        add(lblPassword);
        add(txtPassword);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(140, 180, 100, 30);
        add(btnAjouter);

        // Tableau
        listeTableau = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "E-mail", "Login"}, 0);
        tableau = new JTable(listeTableau);
        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBounds(30,220, 500, 150);
        add(scrollPane);

        // Action bouton
        btnAjouter.addActionListener(e -> ajouterUtilisateur());
        // Charger les utilisateurs au démarrage
        chargerUtilisateurs();

    }

}
