import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nVeuillez choisir une action :");
            System.out.println("1 - Mode terminal (CMD)");
            System.out.println("2 - Mode graphique");
            System.out.println("3 - Quitter");
            System.out.print("> ");

            switch (sc.nextLine().trim()) {
                case "1" -> ConsoleApp.run();
                case "2" -> lancerGUI();
                case "3" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void lancerGUI() {
        Formulaire appsForm = new Formulaire();
        appsForm.setVisible(true);
        //new Formulaire().setVisible(true);
    }
}
