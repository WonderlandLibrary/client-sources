package com.boomer.client.utils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Shit {
    private static Map<String, String> gay = new HashMap<>();

    public static void getGoodies() {
        try {
            final URL oracle = new URL("https://pastebin.com/raw/v4FUYRca");
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                gay.put(inputLine.split(":")[0], inputLine.split(":")[1]);
            in.close();
            if (urMom()) {
                System.out.println("Doesn't contain!");
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, -1);
            } else {
                System.out.println("Contains name is " + gay.get(getHWID()));
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static boolean urMom() {
        try {
            return !gay.containsKey(getHWID());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getHWID() throws NoSuchAlgorithmException {

        String s = "";
        final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
        final byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        final byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        for (final byte b : md5) {
            s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
            if (i != md5.length - 1) {
                s += "-";
            }
            i++;
        }
        return s;
    }
}
