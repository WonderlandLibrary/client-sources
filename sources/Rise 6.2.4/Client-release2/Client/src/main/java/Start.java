import com.alan.clients.Client;
import net.minecraft.client.main.Main;

import java.util.Arrays;
import java.io.File;

public class Start {
    public static void main(final String[] args) {
        String userHome = System.getProperty("user.home", ".");
        String appdata = System.getenv("APPDATA");
        String desiredFolder = (appdata != null) ? appdata : userHome;
        File minecraftDir = new File(desiredFolder, ".minecraft/");
        boolean applyFix = !System.getProperty("os.name").toLowerCase().contains("mac");

        Main.main(concat(new String[]{
                "--version", Client.NAME,
                "--accessToken", "0",
                applyFix ? "--gameDir" : "", applyFix ? new File(minecraftDir, ".").getAbsolutePath() : "",
                "--assetsDir", applyFix ? new File(minecraftDir, "assets/").getAbsolutePath() : "assets",
                "--assetIndex", "1.8",
                "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
