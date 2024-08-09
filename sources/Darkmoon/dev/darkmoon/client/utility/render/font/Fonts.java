package dev.darkmoon.client.utility.render.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Fonts
{
    public static FontRenderer icons21 = new FontRenderer(getFontFromTTF("icons", 21, 0), true, true);
    public static FontRenderer icon21 = new FontRenderer(getFontFromTTF("icon", 21, 0), true, true);
    public static FontRenderer iconStaff = new FontRenderer(getFontFromTTF("iconss", 18, 0), true, true);
    public static FontRenderer iconGui = new FontRenderer(getFontFromTTF("icons1", 21.0F, 0), true, true);
    public static FontRenderer iconNotification = new FontRenderer(getFontFromTTF("icons2", 23.0F, 0), true, true);
    public static FontRenderer icons41 = new FontRenderer(getFontFromTTF("icons", 41, 0), true, true);
    public static FontRenderer nunitoBold14 = new FontRenderer(getFontFromTTF("nunito-bold", 14, 0), true, true);
    public static FontRenderer nunitoBold12 = new FontRenderer(getFontFromTTF("nunito-bold", 12, 0), true, true);
    public static FontRenderer nunitoBold15 = new FontRenderer(getFontFromTTF("nunito-bold", 15, 0), true, true);
    public static FontRenderer poppin14 = new FontRenderer(getFontFromTTF("poppin", 14, 0), true, true);
    public static FontRenderer poppin16 = new FontRenderer(getFontFromTTF("poppin", 16, 0), true, true);
    public static FontRenderer poppin12 = new FontRenderer(getFontFromTTF("poppin", 12, 0), true, true);
    public static FontRenderer iconTargetHud = new FontRenderer(getFontFromTTF("pon", 18, 0), true, true);
    public static FontRenderer poppin15 = new FontRenderer(getFontFromTTF("poppin", 15, 0), true, true);
    public static FontRenderer zelek37 = new FontRenderer(getFontFromTTF("zelek", 27, 0), true, true);
    public static FontRenderer nunitoBold20 = new FontRenderer(getFontFromTTF("nunito-bold", 20, 0), true, true);
    public static FontRenderer nunitoBold16 = new FontRenderer(getFontFromTTF("nunito-bold", 16, 0), true, true);
    public static FontRenderer tahomaBold16 = new FontRenderer(getFontFromTTF("tahoma-bold", 16, 0), true, true);
    public static FontRenderer sfpro14 = new FontRenderer(getFontFromTTF("sfpro", 14, 0), true, true);
    public static FontRenderer gcl14 = new FontRenderer(getFontFromTTF("greycliff", 14, 0), true, true);
    public static FontRenderer rubik14 = new FontRenderer(getFontFromTTF("rub", 14, 0), true, true);
    public static FontRenderer sfpro16 = new FontRenderer(getFontFromTTF("sfpro", 16, 0), true, true);
    public static FontRenderer tahomaBold20 = new FontRenderer(getFontFromTTF("tahoma-bold", 20, 0), true, true);
    public static FontRenderer tahomaBold12 = new FontRenderer(getFontFromTTF("tahoma-bold", 12, 0), true, true);
    public static FontRenderer verdanaBold22 = new FontRenderer(getFontFromTTF("verdanabold", 22, 0), true, true);
    public static FontRenderer verdanaBold20 = new FontRenderer(getFontFromTTF("verdanabold", 20, 0), true, true);
    public static FontRenderer verdanaBold12 = new FontRenderer(getFontFromTTF("verdanabold", 12, 0), true, true);
    public static FontRenderer nunitoBold18 = new FontRenderer(getFontFromTTF("nunito-bold", 18, 0), true, true);
    public static FontRenderer mntsb16 = new FontRenderer(getFontFromTTF("mntsb", 16, 0), true, true);
    public static FontRenderer mntsb18 = new FontRenderer(getFontFromTTF("mntsb", 18, 0), true, true);
    public static FontRenderer mntsb20 = new FontRenderer(getFontFromTTF("mntsb", 20, 0), true, true);
    public static FontRenderer mntsb12 = new FontRenderer(getFontFromTTF("mntsb", 12, 0), true, true);
    public static FontRenderer mntssb10 = new FontRenderer(getFontFromTTF("mntssb", 20, 0), true, true);
    public static FontRenderer mntsb30 = new FontRenderer(getFontFromTTF("mntsb", 30, 0), true, true);
    public static FontRenderer mntsb14 = new FontRenderer(getFontFromTTF("mntsb", 14, 0), true, true);
    public static FontRenderer mntssb16 = new FontRenderer(getFontFromTTF("mntssb", 16, 0), true, true);
    public static FontRenderer mntssb12 = new FontRenderer(getFontFromTTF("mntssb", 12, 0), true, true);
    public static FontRenderer mntssb20 = new FontRenderer(getFontFromTTF("mntssb", 20, 0), true, true);
    public static FontRenderer mntssb15 = new FontRenderer(getFontFromTTF("mntssb", 15, 0), true, true);
    public static FontRenderer mntssb14 = new FontRenderer(getFontFromTTF("mntssb", 14, 0), true, true);
    public static FontRenderer mntsb13 = new FontRenderer(getFontFromTTF("mntsb", 13, 0), true, true);
    public static FontRenderer mntsb15 = new FontRenderer(getFontFromTTF("mntsb", 15, 0), true, true);
    public static FontRenderer tenacityBold25 = new FontRenderer(getFontFromTTF("tenacity-bold", 25, 0), true, true);
    public static FontRenderer tenacityBold28 = new FontRenderer(getFontFromTTF("tenacity-bold", 28, 0), true, true);
    public static FontRenderer tenacityBold35 = new FontRenderer(getFontFromTTF("tenacity-bold", 35, 0), true, true);
    public static FontRenderer tenacityBold18 = new FontRenderer(getFontFromTTF("tenacity-bold", 18, 0), true, true);
    public static FontRenderer tenacityBold22 = new FontRenderer(getFontFromTTF("tenacity-bold", 22, 0), true, true);
    public static FontRenderer tenacityBold15 = new FontRenderer(getFontFromTTF("tenacity-bold", 15, 0), true, true);
    public static FontRenderer tenacityBold16 = new FontRenderer(getFontFromTTF("tenacity-bold", 16, 0), true, true);
    public static FontRenderer tenacityBold13 = new FontRenderer(getFontFromTTF("tenacity-bold", 13, 0), true, true);
    public static FontRenderer tenacityBold14 = new FontRenderer(getFontFromTTF("tenacity-bold", 14, 0), true, true);
    public static FontRenderer verdanaBold14 = new FontRenderer(getFontFromTTF("verdanaBold", 14, 0), true, true);
    public static FontRenderer tenacityBold20 = new FontRenderer(getFontFromTTF("tenacity-bold", 20, 0), true, true);
    public static FontRenderer tenacityBold12 = new FontRenderer(getFontFromTTF("tenacity-bold", 12, 0), true, true);
    public static FontRenderer comfortaa28 = new FontRenderer(getFontFromTTF("comfortaa", 28, 0), true, true);
    public static FontRenderer comfortaa35 = new FontRenderer(getFontFromTTF("comfortaa", 35, 0), true, true);
    public static FontRenderer comfortaa18 = new FontRenderer(getFontFromTTF("comfortaa", 18, 0), true, true);
    public static FontRenderer comfortaa16 = new FontRenderer(getFontFromTTF("comfortaa", 16, 0), true, true);
    public static FontRenderer comfortaa15 = new FontRenderer(getFontFromTTF("comfortaa", 15, 0), true, true);
    public static FontRenderer comfortaa14 = new FontRenderer(getFontFromTTF("comfortaa", 14, 0), true, true);
    public static FontRenderer comfortaa20 = new FontRenderer(getFontFromTTF("comfortaa", 20, 0), true, true);
    public static FontRenderer comfortaa12 = new FontRenderer(getFontFromTTF("comfortaa", 12, 0), true, true);
    public static FontRenderer ace16 = new FontRenderer(getFontFromTTF("ace", 16, 0), true, true);
    public static FontRenderer ace14 = new FontRenderer(getFontFromTTF("ace", 14, 0), true, true);
    public static FontRenderer hack16 = new FontRenderer(getFontFromTTF("hack", 16, 0), true, true);
    public static FontRenderer hack14 = new FontRenderer(getFontFromTTF("hack", 14, 0), true, true);
    public static FontRenderer minecraft13 = new FontRenderer(getFontFromTTF("minecraft", 13, 0), true, true);
    public static FontRenderer mntsb17 = new FontRenderer(getFontFromTTF("mntsb", 17, 0), true, true);
    public static FontRenderer nunito14 = new FontRenderer(getFontFromTTF("nunito", 14, 0), true, true);
    public static FontRenderer nunito12 = new FontRenderer(getFontFromTTF("nunito", 12, 0), true, true);

    public static Font getFontFromTTF(String name, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("darkmoon/fonts/" + name + ".ttf")).getInputStream());
            output = output.deriveFont(fontSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
//
//    public static void bootstrap() {
//        Fonts.nunitoBold16 = new FontRenderer(getFontFromTTF("nunito-bold", 16, 0), true, true);
//        Fonts.nunitoBold14 = new FontRenderer(getFontFromTTF("nunito-bold", 14, 0), true, true);
//        Fonts.tenacity16 = new FontRenderer(getFontFromTTF("tenacity", 16, 0), true, true);
//        Fonts.tenacityBold20 = new FontRenderer(getFontFromTTF("tenacity-bold", 20, 0), true, true);
//        Fonts.tenacityBold28 = new FontRenderer(getFontFromTTF("tenacity-bold", 28, 0), true, true);
//        Fonts.nunitoBold15 = new FontRenderer(getFontFromTTF("nunito-bold", 16, 0), true, true);
//        Fonts.ace16 = new FontRenderer(getFontFromTTF("ace", 16, 0), true, true);
//        Fonts.nunito14 = new FontRenderer(getFontFromTTF("nunito", 14, 0), true, true);
//    }
}
