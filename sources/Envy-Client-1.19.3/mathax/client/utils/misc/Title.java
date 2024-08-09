package mathax.client.utils.misc;

import net.minecraft.client.MinecraftClient;

public class Title {
    private static String currentTitle = "Minecraft " + "1.19.3";

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
