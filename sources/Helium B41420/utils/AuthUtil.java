package rip.helium.utils;

import rip.helium.Helium;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AuthUtil {

    public static boolean check() {
        int lines = 0;
        try {
            URL url = new URL("http://vaultclient.000webhostapp.com/helium/ga8sdfg08sg87df0g78/" + Helium.license);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines += 1;
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("invaliddddd");
            return false;
        }
        //System.out.print("Has cape? " + player + ": " + lines);
        return true;
    }
}

