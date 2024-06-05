package me.vaziak.sensation.utils.anthony.basicfont;

import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * @antja03
 **/
public class Fonts {

    public static FontRenderer f8;
    public static FontRenderer f10;
    public static FontRenderer f12;
    public static FontRenderer f14;
    public static FontRenderer f16;
    public static FontRenderer f18;
    public static FontRenderer f20;
    public static FontRenderer f22;
    public static FontRenderer f24;
    public static FontRenderer f26;
    public static FontRenderer f28;

    public static FontRenderer bf18;
    public static FontRenderer bf20;
    public static FontRenderer bf22;
    public static FontRenderer bf24;
    public static FontRenderer bf26;
    public static FontRenderer bf28;
	public static FontRenderer verdana3;
	public static FontRenderer verdana;
	public static FontRenderer verdanaN;
	public static FontRenderer verdana2;
	public static FontRenderer verdanaChat;
	public static FontRenderer arial;

	public static CFont clickfont;
    public static void createFonts() { 
        f8 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 8, Font.PLAIN), true, true);
        f10 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 10, Font.PLAIN), true, true);
        f12 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 12, Font.PLAIN), true, true);
        f14 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 14, Font.PLAIN), true, true);
        f16 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 16, Font.PLAIN), true, true);
        f18 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 18, Font.PLAIN), true, true);
        f20 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 20, Font.PLAIN), true, true);
        f22 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 22, Font.PLAIN), true, true);
        f24 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 24, Font.PLAIN), true, true);
        f26 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 26, Font.PLAIN), true, true);
        f28 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 28, Font.PLAIN), true, true);
    	verdana = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/verdanab.ttf"), 14, Font.PLAIN), true, true);
        verdanaN = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/verdanab.ttf"), 19, Font.PLAIN), true, true);
        verdana2 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/verdana.ttf"), 18, Font.PLAIN), true, true);
        verdana3 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/verdana.ttf"), 14, Font.PLAIN), true, true);
        verdanaChat = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/verdana.ttf"), 20, Font.PLAIN), true, true);
        bf18 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 18, Font.PLAIN), true, true);
        bf20 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 20, Font.PLAIN), true, true);
        bf22 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 22, Font.PLAIN), true, true);
        bf24 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 24, Font.PLAIN), true, true);
        bf26 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 26, Font.PLAIN), true, true);
        bf28 = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), 28, Font.PLAIN), true, true);

        arial = new FontRenderer(
                fontFromTTF(new ResourceLocation("client/arial.ttf"), 18, Font.PLAIN), true, true);
    }

    public static FontRenderer createFontRenderer(int fontSize) {
        return new FontRenderer(
                fontFromTTF(new ResourceLocation("client/ElliotSans-Medium.ttf"), fontSize, Font.PLAIN), true, true);
    }

    public static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

}
