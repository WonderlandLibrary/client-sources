package com.polarware.util.backend;

import by.radioegor146.nativeobfuscator.Native;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class BackendUtil {
    final static String BACKEND_URL = "https://raw.githubusercontent.com/Nyghtfullfr/skidibidi-toilet-polar/main/main";

    public static String readFromUrl(String url) throws IOException {
        StringBuilder content = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.append(inputLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
        }

        return content.toString();
    }

    public static boolean shouldCrash() {
        boolean crash = false;

        try {
            if(!readFromUrl(BACKEND_URL).contains("true")) {
                crash = true;
            }
        } catch (Exception e) {
            System.exit((int) (Minecraft.getSystemTime() % 1000L));
        }

        return crash;
    }
}
