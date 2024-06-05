package zamorozka.ui;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

public abstract class Command2 {
    public static String prefix = "-";
    public static boolean MsgWaterMark = true;
    public static ChatFormatting cf = ChatFormatting.GRAY;
    static Minecraft mc = Minecraft.getMinecraft();

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String newPrefix) {
        prefix = newPrefix;
    }

    public abstract String[] getAlias();

    public abstract String getSyntax();

    public abstract void onCommand(String command, String[] args) throws Exception;
}
