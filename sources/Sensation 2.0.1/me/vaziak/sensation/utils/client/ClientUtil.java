package me.vaziak.sensation.utils.client;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientUtil {
    /**
     * Ripped from {@see https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java}
     */
    public static void launchWebsite(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static String getUsernameFromAPI() {
        Class api;
        try {
            api = Class.forName("net.aal.API");
        } catch (ClassNotFoundException ignored) {
            return "Unknown";
        }

        try {
            return (String) api.getMethod("getUsername").invoke(null);
        } catch (Exception e) {
            return "AAL API ERROR";
        }
    }
    private Socket socket;

    public void SocketUtil() {
       /* try {
            socket = new Socket("backend.azuma.club", 1337);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socket != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter.println("Handshake User " + Sensation.instance.username);


            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            //Minecraft.getMinecraft().shutdown();
        }*/
    }
}
