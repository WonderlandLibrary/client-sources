package me.zeroeightsix.kami.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created 23 November 2019 by hub
 * Updated 16 December 2019 by hub
 */
public class CapeManager {

    private static final String usersPastebin = "https://pastebin.com/raw/za3hEYdn";
    private static final String ogUsersPastebin = "https://pastebin.com/raw/9H0WwSkw";

    private static HashMap<String, Boolean> capeUsers;

    public CapeManager() {
        capeUsers = new HashMap<>();
    }

    public static boolean hasCape(final UUID uuid) {
        return capeUsers.containsKey(sanitizeUuid(uuid));
    }

    public static boolean isOg(final UUID uuid) {
        if (hasCape(uuid)) {
            return capeUsers.get(sanitizeUuid(uuid));
        }
        return false;
    }

    private static String sanitizeUuid(UUID uuid) {
        return sanitizeUuidString(uuid.toString());
    }

    private static String sanitizeUuidString(String uuidString) {
        return uuidString.replaceAll("-", "").toLowerCase();
    }

    public void initializeCapes() {

        getFromPastebin(usersPastebin).forEach(uuid -> {
            capeUsers.put(uuid, false);
        });

        getFromPastebin(ogUsersPastebin).forEach(uuid -> {
            capeUsers.put(uuid, true);
        });

    }

    private List<String> getFromPastebin(String urlString) {

        URL url;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        ArrayList<String> uuidList = new ArrayList<>();
        String line;

        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            uuidList.add(sanitizeUuidString(line));
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return uuidList;

    }

}
