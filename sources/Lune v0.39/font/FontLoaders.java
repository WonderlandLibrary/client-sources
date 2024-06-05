/*
 * Decompiled with CFR 0_132.
 */
package me.superskidder.lune.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class FontLoaders {

    public static CFontRenderer F14 = new CFontRenderer(FontLoaders.getFont(14), true, true);
    public static CFontRenderer F16 = new CFontRenderer(FontLoaders.getFont(16), true, true);
    public static CFontRenderer F18 = new CFontRenderer(FontLoaders.getFont(18), true, true);
    public static CFontRenderer F20 = new CFontRenderer(FontLoaders.getFont(20), true, true);
    public static CFontRenderer F22 = new CFontRenderer(FontLoaders.getFont(22), true, true);
    public static CFontRenderer F23 = new CFontRenderer(FontLoaders.getFont(23), true, true);
    public static CFontRenderer F24 = new CFontRenderer(FontLoaders.getFont(24), true, true);
    public static CFontRenderer F30 = new CFontRenderer(FontLoaders.getFont(30), true, true);
    public static CFontRenderer F40 = new CFontRenderer(FontLoaders.getFont(40), true, true);

    public static CFontRenderer C12 = new CFontRenderer(FontLoaders.getComfortaa(12), true, true);
    public static CFontRenderer C14 = new CFontRenderer(FontLoaders.getComfortaa(14), true, true);
    public static CFontRenderer C16 = new CFontRenderer(FontLoaders.getComfortaa(16), true, true);
    public static CFontRenderer C18 = new CFontRenderer(FontLoaders.getComfortaa(18), true, true);
    public static CFontRenderer C20 = new CFontRenderer(FontLoaders.getComfortaa(20), true, true);
    public static CFontRenderer C22 = new CFontRenderer(FontLoaders.getComfortaa(22), true, true);

    public static CFontRenderer Logo = new CFontRenderer(FontLoaders.getNovo(40), true, true);

    public static ArrayList<CFontRenderer> fonts = new ArrayList();


    public static CFontRenderer getFontRender(int size) {
        return fonts.get(size - 10);
    }

    public static Font getFont(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/ali.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getComfortaa(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("client/fonts/ali.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getNovo(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("client/fonts/ali.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

}

