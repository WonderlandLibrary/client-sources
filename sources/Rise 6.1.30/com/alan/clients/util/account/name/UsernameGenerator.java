package com.alan.clients.util.account.name;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UsernameGenerator {
    private static final String URL = "https://raw.githubusercontent.com/jeanphorn/wordlist/master/usernames.txt";

    public static String[] retrieve() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            boolean isErrorStream = responseCode / 100 != 2 && responseCode / 100 != 3;
            InputStream stream = isErrorStream ? connection.getErrorStream() : connection.getInputStream();
            if (stream == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            for (String s; (s = reader.readLine()) != null; builder.append(s).append(System.lineSeparator())) ;
            reader.close();

            return builder.toString().split(System.lineSeparator());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Generates exactly one username.
     *
     * @return The generated username.
     */
    public static String generate() {
        String[] generated = generate(1);
        return generated == null ? null : generated[0];
    }

    /**
     * Generates a given amount of usernames.
     *
     * @param amount The amount of usernames to generate.
     * @return The generated usernames.
     */
    public static String[] generate(int amount) {
        String[] usernames = retrieve();
        if (usernames == null) {
            return null;
        }

        String[] generated = new String[amount];
        List<String> acceptableUsernames = Arrays.stream(usernames)
                .filter(username -> username.length() >= 3 && username.length() <= 6)
                .collect(Collectors.toList());

        for (int i = 0; i < amount; ++i) {
            String prefix = acceptableUsernames.get((int) (Math.random() * acceptableUsernames.size()));
            String suffix = acceptableUsernames.get((int) (Math.random() * acceptableUsernames.size()));
            String username = applyPattern(prefix, suffix);
            generated[i] = applyPattern(username);
        }

        return generated;
    }

    /**
     * Applies a pattern on the given prefix and suffix and converts it to one username.
     *
     * @param prefix The prefix (or first username).
     * @param suffix The suffix (or second username).
     * @return The converted username.
     */
    private static String applyPattern(String prefix, String suffix) {
        int pattern = (int) (Math.random() * 4);
        switch (pattern) {
            case 0: {
                return prefix + "_" + suffix;
            }
            case 1: {
                return prefix + suffix.substring(0, 2) + (int) (Math.random() * 100);
            }
            case 2: {
                int index = (int) (Math.random() * Math.min(prefix.length(), suffix.length()));
                return prefix.substring(0, index) + "_" + suffix.substring(index);
            }
            case 3: {
                StringBuilder merge = new StringBuilder(prefix).append(suffix);
                int uIndex = (int) (Math.random() * merge.length());
                int nIndex = (int) (Math.random() * merge.length());
                merge.insert(uIndex, "_");
                merge.insert(nIndex, (int) (Math.random() * 100));
                return merge.toString();
            }
            default: {
                return prefix + suffix;
            }
        }
    }

    /**
     * Applies a pattern on the given username.
     *
     * @param username The username.
     * @return The converted username.
     */
    private static String applyPattern(String username) {
        double numberChance = 0.125;
        double upperChance = 0.25;

        char[] chars = username.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if ((i == 0 || (chars[i - 1] == '_' || Character.isDigit(chars[i - 1])) && Character.isLetter(c))) {
                if (Math.random() < upperChance) {
                    chars[i] = Character.toUpperCase(c);
                    continue;
                }
            }

            char lower = Character.toLowerCase(c);
            char replacement = getReplacement(lower);
            if (replacement != lower) {
                if (Math.random() < numberChance) {
                    chars[i] = replacement;
                    numberChance *= 0.5;
                }
            }
        }

        return new String(chars);
    }

    /**
     * Gets the replacement for the given character. (e.g. 'a' -> '4')
     *
     * @param c The character.
     * @return The replacement for the given character.
     */
    private static char getReplacement(char c) {
        if (c == 'a') {
            return '4';
        } else if (c == 'e') {
            return '3';
        } else if (c == 'i') {
            return '1';
        } else if (c == 'o') {
            return '0';
        } else if (c == 't') {
            return '7';
        } else {
            return c;
        }
    }
}

