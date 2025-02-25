package ro.upt.ac.portofolii.utils;

import java.util.Random;
import java.util.stream.Collectors;

public class PasswordGenerator {
    public String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        return random.ints(8, 0, chars.length())
                .mapToObj(chars::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
