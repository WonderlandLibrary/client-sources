package Snipeware.hwid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Snipeware.Client;

public class NetworkUtil {

    public static List<String> getHWIDList(){
        List<String> HWIDList = new ArrayList<>();
        try {
            final URL url = new URL(Client.HWID_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                HWIDList.add(inputLine);
            }
        } catch(Exception e) {
           System.out.println("Load HWID Failed!");
        }
        return HWIDList;
    }
}