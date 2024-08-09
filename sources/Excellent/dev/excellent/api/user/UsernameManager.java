package dev.excellent.api.user;


import i.gishreloaded.deadcode.api.DeadCodeConstants;

import java.io.*;

public class UsernameManager {
    private static final String FILE_NAME = DeadCodeConstants.MAIN_DIRECTORY.getAbsolutePath() + "\\username";

    public static String loadUsername() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.readLine();
            } catch (IOException e) {
                System.err.println("Error reading username from file: " + e.getMessage());
            }
        }
        return generateRandomUsername();
    }

    public static void saveUsername(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(username);
        } catch (IOException e) {
            System.err.println("Error saving username to file: " + e.getMessage());
        }
    }

    private static String generateRandomUsername() {
        return DeadCodeConstants.CHEAT_NAME + System.currentTimeMillis() % 1000L;
    }
}
