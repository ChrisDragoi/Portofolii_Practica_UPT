package ro.upt.ac.portofolii.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtils {

    public static void generateAndExportPasswords(int count, int length, String filePath)
            throws IOException, NoSuchAlgorithmException {
        FileWriter writer = new FileWriter(filePath);
        writer.append("parola_originala,hash_md5\n"); // header pentru CSV

        for (int i = 0; i < count; i++) {
            String password = generateRandomPassword(length);
            String md5Hash = getMD5Hash(password);
            writer.append(password).append(",").append(md5Hash).append("\n");
        }

        writer.flush();
        writer.close();
    }

    public static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public static String getMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder hash = new StringBuilder();

        for (byte b : hashBytes) {
            hash.append(String.format("%02x", b));
        }

        return hash.toString();
    }
}
