package Ascii4UwUWareClient.API.Updater;

import Ascii4UwUWareClient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;

public class Update {

    public static String publicVersion;

    public static void getVersionOnWebsite(){
        try {
            final URL url = new URL("http://venusdb.000webhostapp.com//UwUWare/UwUWareVersion.uwuprot");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            publicVersion = in.readLine().toString();
            System.out.println(publicVersion);
        }catch (Exception e){

        }
    }

    public static boolean isNewerVersionRelease(){
        return !Client.instance.version.equals(publicVersion);
    }

    public static void downloadAndReplaceJar(){
        try {
            //Get Path to download the updater
            String path = getJarPath();
            try(BufferedInputStream inputStream = new BufferedInputStream((new URL("https://venusdb.000webhostapp.com/UwUWare/UwU%20Updater.jar")).openStream());
                FileOutputStream fileOS = new FileOutputStream(path + "\\UwU_Updater(NotRat).jar")) {
                byte[] data = new byte[1024];
                int byteContent;
                while ((byteContent = inputStream.read(data, 0, 1024)) != -1)
                    fileOS.write(data, 0, byteContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Desktop desktop = Desktop.getDesktop();
            File dirToOpen = null;

            dirToOpen = new File(path + "\\UwU_Updater(NotRat).jar");
            desktop.open(dirToOpen);
            System.out.println(path);
            Minecraft.getMinecraft().shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getJarPath() throws URISyntaxException {
        CodeSource codeSource = Update.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        return jarFile.getParentFile().getPath();
    }

}