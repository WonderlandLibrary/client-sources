package xyz.mathax.mathaxclient.utils.window;

import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import net.minecraft.client.MinecraftClient;

public class Title {
    private static String currentTitle = "Minecraft " + Versions.getMinecraft();

    public static String getCurrentTitle() {
        return currentTitle;
    }

    public static void setTitle(String title, boolean update) {
        currentTitle = title;
        if (update) {
            MinecraftClient.getInstance().getWindow().setTitle(title);
        }
    }
}
