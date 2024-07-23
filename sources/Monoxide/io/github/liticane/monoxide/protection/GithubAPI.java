package io.github.liticane.monoxide.protection;

import io.github.liticane.monoxide.protection.antitamper.impl.AntiVM;
import io.github.liticane.monoxide.util.system.HWIDUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubAPI {

    public static String username, uid;

    public static int login(String uid) {
        AntiVM.run();
        if (uid == null) {
            return 4;
        }

        try {
            URL url = new URL("");
            HttpURLConnection uc = (HttpURLConnection ) url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0");
            uc.setRequestMethod("GET");
            int responseCode = uc.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return 3;
            }
        } catch (Exception e) {
            return 3;
        }

        if(!isWhitelisted(HWIDUtil.getNewHashedHWID())){
            return 2;
        }

        if(!getUUID(HWIDUtil.getNewHashedHWID()).equals(uid)) {
            return 1;
        }

        GithubAPI.username = getUsername(uid, HWIDUtil.getNewHashedHWID());
        GithubAPI.uid = getUUID(HWIDUtil.getNewHashedHWID());

        return 0;
    }

    private static String getDocument() {
        String documentUrl = "https://raw.githubusercontent.com/Atani-Client/Stuff/main/UwEymE4I8TNBvG2YAZq";

        try {
            URL url = new URL(documentUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0");
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Failed to access the document. Response code: " + responseCode;
            }

        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }

    private static boolean isWhitelisted(String hwid) {
        String documentContent = getDocument();
        String[] lines = documentContent.split(",");

        for (String line : lines) {
            String[] parts = line.split(":");

            if (parts.length >= 3) {
                String documentHWIDs = parts[2].trim();

                String[] hwidArray = documentHWIDs.split(",");

                for (String docHwid : hwidArray) {

                    if (docHwid.equals(hwid)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static String getUUID(String hwid) {
        String documentContent = getDocument();
        String[] lines = documentContent.split(",");

        for (String line : lines) {
            String[] parts = line.split(":");

            if (parts.length >= 3) {
                String documentUID = parts[1].trim();
                String documentHWIDs = parts[2].trim();

                String[] hwidArray = documentHWIDs.split(",");

                for (String docHwid : hwidArray) {
                    if (docHwid.equals(hwid)) {
                        if (documentUID.length() >= 4) {
                            return documentUID.substring(0, 4);
                        }
                    }
                }
            }
        }

        return "UUID not found for HWID: " + hwid;
    }

    private static String getUsername(String uuid, String hwid) {
        String documentContent = getDocument();
        String[] lines = documentContent.split(",");

        for (String line : lines) {
            String[] parts = line.split(":");

            if (parts.length == 3) {
                String documentUsername = parts[0].trim();
                String documentUID = parts[1].trim();
                String documentHWID = parts[2].trim();

                if (documentUID.equals(uuid) && documentHWID.equals(hwid)) {
                    return documentUsername;
                }
            }
        }

        return "null";
    }

}
