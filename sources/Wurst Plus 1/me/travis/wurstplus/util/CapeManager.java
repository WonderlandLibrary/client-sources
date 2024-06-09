package me.travis.wurstplus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CapeManager {
    private static final String users = "https://pastebin.com/raw/9RRDWmDa";
    private static final String chad = "https://pastebin.com/raw/r4mKMJ5A";
    private static final String travis = "https://pastebin.com/raw/rduvfFtq";
    private static final String tabbott = "https://pastebin.com/raw/HPyRpgWg";
    private static final List<String> staticUsers = Arrays.asList("dbc45ea7-e8bd-4a3e-8660-ac064ce58216", "2a951e5b-ad2e-4183-a031-883889614042", "2dcf1a61-23c9-48df-849f-20b13eb2f268", "f9b270e9-7fd6-4537-bfd8-877552e677e8", "83d24303-8050-4280-a59f-d7f5a525e009", "ed89acba-3b86-4a2a-9dc0-960639b58032");
    private static HashMap<String, Boolean> capeUsers;

    public CapeManager() {
        capeUsers = new HashMap();
    }

    public void initializeCapes() {
        staticUsers.forEach(uuid -> capeUsers.put((String)uuid, true));
        // this.getFromPastebin(usersPastebin).forEach(uuid -> capeUsers.put((String)uuid, false));
        this.getFromPastebin(users).forEach(uuid -> capeUsers.put((String)uuid, true));
    }

    private List<String> getFromPastebin(String urlString) {
        URL url;
        BufferedReader bufferedReader;
        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        ArrayList<String> uuidList = new ArrayList<String>();
        do {
            String line;
            try {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }
            uuidList.add(line);
        } while (true);
        try {
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        return uuidList;
    }

    public static boolean hasCape(UUID uuid) {
        return capeUsers.containsKey(uuid.toString());
    }

    public static boolean isOg(UUID uuid) {
        if (capeUsers.containsKey(uuid.toString())) {
            return capeUsers.get(uuid.toString());
        }
        return false;
    }
}

