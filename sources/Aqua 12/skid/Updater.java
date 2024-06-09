// 
// Decompiled by Procyon v0.5.36
// 

package skid;

import net.minecraft.client.main.LauncherAPI;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import intent.AquaDev.aqua.Aqua;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.JsonObject;

public class Updater
{
    public static final String URL_BASE = "https://aquaclient.github.io/";
    private static final String LOCAL_PATH;
    
    private static String sendMeLTCOrGay() {
        return "Lf6jHpjnUd7bT9x5o2eQYWbn2bmGqZP3cY";
    }
    
    private static JsonObject fetchLatestVersion() {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://aquaclient.github.io/version.json").openStream()))) {
            final StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            return new Gson().fromJson(sb.toString(), JsonObject.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean isUpdateAvailable() {
        final String latestVersion = fetchLatestVersion().get("version").getAsString();
        final double sumLatest = Double.parseDouble(latestVersion);
        final double sumCurrent = Double.parseDouble(Aqua.build);
        return sumLatest > sumCurrent;
    }
    
    public static void update() {
        final FileDownload download = new FileDownload("https://aquaclient.github.io/Updater.jar", Updater.LOCAL_PATH + File.separator + "Updater.jar");
        final String jre;
        int result;
        final Exception ex;
        Exception e;
        ProcessBuilder builder;
        final ProcessBuilder processBuilder;
        final ProcessBuilder processBuilder2;
        final ProcessBuilder processBuilder3;
        final File directory;
        final Object o;
        download.start(() -> {
            System.out.println("Finished downloading Updater.jar");
            jre = System.getProperty("java.home");
            if (!jre.startsWith("1.8")) {
                result = JOptionPane.showConfirmDialog(null, "You must have Java 1.8 installed!\n If you don't want to use the updater, press 'No'", "Updater", 0);
                if (result == 0) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.java.com/de/download/manual.jsp"));
                    }
                    catch (IOException | URISyntaxException ex2) {
                        e = ex;
                        JOptionPane.showMessageDialog(null, "Failed to open download page");
                        e.printStackTrace();
                    }
                }
                else if (result == 1) {
                    System.out.println("User skipped update");
                    return;
                }
            }
            builder = null;
            switch (LauncherAPI.OS.getOperatingSystem()) {
                case WINDOWS: {
                    new ProcessBuilder(new String[] { "cmd.exe", "/c", "java -jar Updater.jar --update" });
                    builder = processBuilder;
                    break;
                }
                case LINUX: {
                    new ProcessBuilder(new String[] { "java", "-jar", "Updater.jar", "--update" });
                    builder = processBuilder2;
                    break;
                }
                case MACOS: {
                    new ProcessBuilder(new String[] { "java", "-jar", "Updater.jar", "--update" });
                    builder = processBuilder3;
                    break;
                }
            }
            if (builder == null) {
                throw new RuntimeException("Unsupported OS");
            }
            else {
                try {
                    new File("versions" + File.separator + "Aqua");
                    ((ProcessBuilder)o).directory(directory);
                    builder.inheritIO();
                    builder.start();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                System.exit(1);
            }
        });
    }
    
    static {
        LOCAL_PATH = new File(AquaSkid.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getPath();
    }
}
