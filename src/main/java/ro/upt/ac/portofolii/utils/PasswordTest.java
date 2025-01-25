package ro.upt.ac.portofolii.utils;

public class PasswordTest {
    public static void main(String[] args) {
        try {
            System.out.println("Încep testarea...");


            int numberOfPasswords = 5;

            int passwordLength = 8;

            String outputPath = "src/main/java/ro/upt/ac/portofolii/utils/passwords_test.csv";

            PasswordUtils.generateAndExportPasswords(numberOfPasswords, passwordLength, outputPath);

            System.out.println("Test complet! Fișierul a fost generat la: " + outputPath);

        } catch (Exception e) {
            System.err.println("Eroare în timpul testării: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
