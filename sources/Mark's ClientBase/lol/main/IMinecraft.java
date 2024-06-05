package lol.main;

import lol.base.BaseClient;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public interface IMinecraft {

    Minecraft mc = Minecraft.getMinecraft();

    default void chat(String message) {
        chat(message, true);
    }

    default void chat(String message, boolean prefix) {
        message = prefix ? "§d" + BaseClient.get().clientManager.name + " : §f" + message : message;
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

}
