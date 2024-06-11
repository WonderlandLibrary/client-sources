package Hydro;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;

public class Wrapper {
    public static boolean authorized;
    public static double getFuckedPrinceKin;
    public static Minecraft mc;

    public static EntityPlayerSP getPlayer() {
        return Wrapper.getMinecraft().thePlayer;
    }

    
    public static FontRenderer getFontRenderer() {
        return Wrapper.getMinecraft().fontRendererObj;
    }

    public static GameSettings getGameSettings() {
        return Wrapper.getMinecraft().gameSettings;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(mc);
    }

    public static float getTimerSpeed() {
        return Wrapper.mc.timer.timerSpeed;
    }

    public void jump() {
        Wrapper.getPlayer().jump();
    }

    public static void tellPlayer(String string) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
    }

    public static String generateRandomString() {
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (true) {
            if (stringBuilder.length() >= 5) {
                String string2 = "Hydro_" + String.valueOf(stringBuilder);
                return string2;
            }
            int n = (int)(random.nextFloat() * (float)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".length());
            stringBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".charAt(n));
        }
    }

    public static NetHandlerPlayClient getSendQueue() {
        return Wrapper.getPlayer().sendQueue;
    }

    public static WorldClient getWorld() {
        return Minecraft.theWorld;
    }

    static {
        mc = Minecraft.getMinecraft();
    }

    public static void setTimerSpeed(float f) {
        Wrapper.mc.timer.timerSpeed = f;
    }

    public static EntityRenderer getRenderer() {
        return Wrapper.getMinecraft().entityRenderer;
    }

    
}
 