package me.travis.wurstplus.util;

import me.travis.wurstplus.gui.wurstplus.wurstplusGUI;
import me.travis.wurstplus.gui.font.CFontRenderer;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

/**
 * Created by 086 on 11/11/2017.
 */
public class Wrapper {
    private static FontRenderer fontRenderer;

    public static void init() {
        // fontRenderer = new CFontRenderer(new Font("Segoe UI", Font.PLAIN, 19), true, false);
        fontRenderer = wurstplusGUI.fontRenderer;
    }
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().player;
    }
    public static World getWorld() {
        return getMinecraft().world;
    }
    public static int getKey(String keyname){
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }

    public static FontRenderer getFontRenderer() {
        return fontRenderer;
    }
}
