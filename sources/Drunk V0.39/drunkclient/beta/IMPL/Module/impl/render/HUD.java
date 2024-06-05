/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package drunkclient.beta.IMPL.Module.impl.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender2D;
import drunkclient.beta.Client;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.font.CFontRenderer;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.render.RenderUtil;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.Timer;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;

public class HUD
extends Module {
    public static Mode<Enum> fontMode = new Mode("Font", "Font", (Enum[])FontMode.values(), (Enum)FontMode.Minecraft);
    public static String moduleName;
    public static Option<Boolean> sorting;
    public static Option<Boolean> bg;
    public static Mode<Enum> arrayMode;
    public static Mode<Enum> WaterMarkmode;
    public static int color;
    int flykickint = 0;
    public static CFontRenderer font;
    Timer timer = new Timer();
    private int waterMarkColor;

    public HUD() {
        super("Hud", new String[0], Type.RENDER, "No");
        this.setEnabled(true);
        this.addValues(sorting, fontMode, arrayMode, WaterMarkmode);
    }

    public static double getBPS(Entity entityIn) {
        double xDist = entityIn.posX - entityIn.prevPosX;
        double zDist = entityIn.posZ - entityIn.prevPosZ;
        double bps = Math.sqrt(xDist * xDist + zDist * zDist) * 20.0;
        return (double)((int)bps) + bps - (double)((int)bps);
    }

    /*
     * WARNING - void declaration
     */
    @EventHandler
    public void onHUD(EventRender2D event) {
        float bps = (float)Math.round(Minecraft.thePlayer.getDistance(Minecraft.thePlayer.lastTickPosX, Minecraft.thePlayer.posY, Minecraft.thePlayer.lastTickPosZ) * 200.0) / 10.0f;
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        boolean bottom = false;
        int height = sr.getScaledHeight();
        if (HUD.mc.gameSettings.showDebugInfo) {
            return;
        }
        if (ModuleManager.getModuleByName("Fly").isEnabled()) {
            RenderUtil.drawRect(sr.getScaledWidth() - 100, sr.getScaledHeight() - 19, sr.getScaledWidth() - 5, sr.getScaledHeight() - 5, new Color(0, 0, 0, 97).getRGB());
            FontLoaders.arial18.drawStringWithShadowNew("Fly Kick", sr.getScaledWidth() - 45, (float)sr.getScaledHeight() - 26.5f, -1);
            RenderUtil.drawRect(sr.getScaledWidth() - 5 - this.flykickint, sr.getScaledHeight() - 19, sr.getScaledWidth() - 5, sr.getScaledHeight() - 5, color);
            if (!MovementUtil.isOnGround(0.48f) && this.timer.hasElapsed(50L, true) && this.flykickint != 98) {
                ++this.flykickint;
            } else if (this.timer.hasElapsed(100L, true)) {
                this.flykickint = 0;
            }
        } else {
            this.flykickint = 0;
        }
        switch (fontMode.getModeAsString()) {
            case "DrunkClient": {
                font = FontLoaders.arial18;
                break;
            }
        }
        ArrayList<Module> sorted = new ArrayList<Module>();
        for (Module m : Client.instance.getModuleManager().getModules()) {
            if (!m.isEnabled() || m.wasRemoved()) continue;
            sorted.add(m);
        }
        if (((Boolean)sorting.getValue()).booleanValue()) {
            if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
                sorted.sort((o1, o2) -> {
                    String string;
                    int n = HUD.mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s - %s", o2.getName(), o2.getSuffix()));
                    FontRenderer fontRenderer = HUD.mc.fontRendererObj;
                    if (o1.getSuffix().isEmpty()) {
                        string = o1.getName();
                        return n - fontRenderer.getStringWidth(string);
                    }
                    string = String.format("%s - %s", o1.getName(), o1.getSuffix());
                    return n - fontRenderer.getStringWidth(string);
                });
            } else {
                CFontRenderer finalFont = FontLoaders.arial18;
                sorted.sort((o1, o2) -> {
                    String string;
                    int n = finalFont.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s - %s", o2.getName(), o2.getSuffix()));
                    if (o1.getSuffix().isEmpty()) {
                        string = o1.getName();
                        return n - finalFont.getStringWidth(string);
                    }
                    string = String.format("%s - %s", o1.getName(), o1.getSuffix());
                    return n - finalFont.getStringWidth(string);
                });
            }
        }
        int y = 1;
        int count = 0;
        for (Module module : sorted) {
            float aOffset = (float)(System.currentTimeMillis() % 3200L) + (float)(count * 500 / 2);
            switch (arrayMode.getModeAsString()) {
                case "FadeBlue": {
                    color = this.color(y, 200);
                    break;
                }
                case "FadeGreen": {
                    color = this.colorG(y, 200);
                    break;
                }
                case "FadeRed": {
                    color = this.colorR(y, 200);
                    break;
                }
                case "Test": {
                    color = -1;
                    break;
                }
            }
            String name = module.getSuffix().isEmpty() ? module.getName() : String.format("%s - %s", module.getName(), module.getSuffix());
            double bottemLines = 0.0;
            ArrayList<Module> enabledModules = new ArrayList<Module>();
            for (Module e : Client.instance.getModuleManager().getModules()) {
                if (!e.isEnabled()) continue;
                enabledModules.add(e);
            }
            try {
                bottemLines = enabledModules.indexOf(module) != enabledModules.size() - 1 ? (double)(HUD.mc.fontRendererObj.getStringWidth(((Module)enabledModules.get((int)enabledModules.indexOf((Object)module))).name) - HUD.mc.fontRendererObj.getStringWidth(name)) : (double)(HUD.mc.fontRendererObj.getStringWidth(name) + 10);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                // empty catch block
            }
            float x = 0.0f;
            x = fontMode.getModeAsString().equalsIgnoreCase("Minecraft") ? (float)(RenderUtil.width() - HUD.mc.fontRendererObj.getStringWidth(name)) : (float)(RenderUtil.width() - font.getStringWidth(name));
            if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
                if (((Boolean)bg.getValue()).booleanValue()) {
                    Gui.drawRect((float)(sr.getScaledWidth() - HUD.mc.fontRendererObj.getStringWidth(name)) - 6.0f, (float)y + 1.2f, sr.getScaledWidth(), (float)(6 + HUD.mc.fontRendererObj.FONT_HEIGHT + y) - 3.8f, new Color(0, 0, 0, 60).getRGB());
                }
                Gui.drawRect((float)sr.getScaledWidth() - 2.0f, (float)y + 1.0f, sr.getScaledWidth(), (float)(6 + HUD.mc.fontRendererObj.FONT_HEIGHT + y) - 3.5f, color);
                HUD.mc.fontRendererObj.drawStringWithShadow(name, x - 4.0f, y + 2, color);
                ++y;
            } else {
                if (((Boolean)bg.getValue()).booleanValue()) {
                    Gui.drawRect((float)(sr.getScaledWidth() - font.getStringWidth(name)) - 6.0f, (float)y + 0.5f, sr.getScaledWidth(), (float)(6 + font.getHeight() + y) - 3.5f, new Color(0, 0, 0, 60).getRGB());
                }
                Gui.drawRect((float)sr.getScaledWidth() - 2.5f, y, sr.getScaledWidth(), 6.0f + (float)font.getHeight() + (float)y - 3.5f, color);
                FontLoaders.arial18.drawStringWithShadowNew(name, x - 4.0f, y + 2, color);
            }
            y += 9;
            ++count;
        }
        int count2 = 0;
        switch (arrayMode.getModeAsString()) {
            case "FadeBlue": {
                this.waterMarkColor = this.color(count2, 200);
                break;
            }
            case "FadeGreen": {
                this.waterMarkColor = this.colorG(count2, 200);
                break;
            }
            case "FadeRed": {
                this.waterMarkColor = this.colorR(count2, 200);
                break;
            }
            case "Astolfo": {
                this.waterMarkColor = -1;
                break;
            }
        }
        String string = "";
        String descString = Client.ClientDesc;
        if (!mc.isSingleplayer()) {
            String string2 = "D" + ChatFormatting.WHITE + "runk Client Beta | " + Minecraft.thePlayer.getName() + " | " + HUD.mc.getCurrentServerData().serverIP.toLowerCase().toString();
        } else {
            String string3 = "D" + ChatFormatting.WHITE + "runk Cient Beta | " + Minecraft.thePlayer.getName() + " | SinglePlayer";
        }
        if (WaterMarkmode.getModeAsString().equalsIgnoreCase("Drunk Client")) {
            void var12_21;
            if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
                RenderUtil.drawRoundedRect(10.0, 10.0, HUD.mc.fontRendererObj.getStringWidth((String)var12_21) + 17, 30.0, 30.0, new Color(0, 0, 0, 70).getRGB());
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderUtil.drawRoundedRect(10.0, 10.0, HUD.mc.fontRendererObj.getStringWidth((String)var12_21) + 18, 13.5, 10.0, color);
                HUD.mc.fontRendererObj.drawStringWithShadow((String)var12_21, 12.0f, 17.0f, color);
                ++count2;
                return;
            }
            RenderUtil.drawRoundedRect(10.0, 10.0, FontLoaders.arial20.getStringWidth((String)var12_21) + 17, 30.0, 30.0, new Color(0, 0, 0, 70).getRGB());
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            FontLoaders.arial20.drawStringWithShadow((String)var12_21, 12.0, 17.0, this.waterMarkColor);
            RenderUtil.drawRoundedRect(10.0, 10.0, FontLoaders.arial20.getStringWidth((String)var12_21) + 17, 13.5, 10.0, color);
            ++count2;
            return;
        }
        String string4 = ChatFormatting.WHITE + "Drunk Client" + ChatFormatting.RESET + "" + ChatFormatting.WHITE + " | " + Minecraft.thePlayer.getName() + " | " + (mc.isSingleplayer() ? "SinglePlayer" : HUD.mc.getCurrentServerData().serverIP.toString());
        String kordy = "";
        float width2 = (float)((double)FontLoaders.arial20.getStringWidth(string4) - 4.0);
        int height2 = 20;
        int posX2 = 2;
        int posY1 = 2;
        Gui.drawRect(2.0f, 2.0f, 2.0f + width2 + 2.0f, 22.0f, new Color(5, 5, 5, 255).getRGB());
        RenderUtil.drawBorderedRect(2.5, 2.5, (double)(2.0f + width2) + 1.5, 21.5, 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
        RenderUtil.drawBorderedRect(4.0, 4.0, 2.0f + width2, 20.0, 0.5, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
        Gui.drawRect(4.5, 4.5, (double)(2.0f + width2) - 0.5, 6.5, new Color(9, 9, 9, 255).getRGB());
        RenderUtil.drawGradientSideways(4.0, 5.0, 4.0f + width2 / 3.0f, 6.0, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
        RenderUtil.drawGradientSideways(4.0f + width2 / 3.0f, 5.0, 4.0f + width2 / 3.0f * 2.0f, 6.0, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
        RenderUtil.drawGradientSideways(4.0f + width2 / 3.0f * 2.0f, 5.0, width2 / 3.0f * 3.0f + 1.0f, 6.0, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
        FontLoaders.arial18.drawString(string4, 7.5f, 10.0f, Color.white.getRGB());
        StringBuilder append = new StringBuilder("FPS: ").append((Object)EnumChatFormatting.GRAY);
        HUD.mc.fontRendererObj.drawStringWithShadow(append.append(Minecraft.getDebugFPS()).toString(), 4.0f, sr.getScaledHeight() - 10 - 5, -1);
        HUD.mc.fontRendererObj.drawStringWithShadow("BPS: " + (Object)((Object)EnumChatFormatting.GRAY) + bps, 4.0f, sr.getScaledHeight() - 10 - 17, -1);
        HUD.mc.fontRendererObj.drawStringWithShadow("XYZ: " + (Object)((Object)EnumChatFormatting.GRAY) + "" + Minecraft.thePlayer.getPosition().getX() + " " + Minecraft.thePlayer.getPosition().getY() + " " + Minecraft.thePlayer.getPosition().getZ(), sr.getScaledWidth() - 115, sr.getScaledHeight() - 10 - 5, -1);
    }

    public static int darker(int color, float factor) {
        int r = (int)((float)(color >> 16 & 0xFF) * factor);
        int g = (int)((float)(color >> 8 & 0xFF) * factor);
        int b = (int)((float)(color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static void drawExhiRect(float left, float top, float right, float bottom) {
        RenderUtil.drawGradientRect((double)left - 3.5, (double)top - 3.5, right + 105.5f, bottom + 42.4f, new Color(10, 10, 10, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
        RenderUtil.drawGradientRect(left - 3.0f, (double)top - 3.2, right + 104.8f, bottom + 41.8f, new Color(40, 40, 40, 255).getRGB(), new Color(40, 40, 40, 255).getRGB());
        RenderUtil.drawGradientRect((double)left - 1.4, (double)top - 1.5, right + 103.5f, bottom + 40.5f, new Color(74, 74, 74, 255).getRGB(), new Color(74, 74, 74, 255).getRGB());
        RenderUtil.drawGradientRect(left - 1.0f, top - 1.0f, right + 103.0f, bottom + 40.0f, new Color(32, 32, 32, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
    }

    private static float getOffset() {
        return (float)(System.currentTimeMillis() % 2000L) / 1000.0f;
    }

    public static int colore(int index, int count) {
        float[] hsb = new float[3];
        Color clr = new Color(255, 255, 255);
        Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getRed(), hsb);
        float brightness = Math.abs((HUD.getOffset() + (float)index / (float)count * 4.0f) % 2.0f - 1.0f);
        brightness = 0.4f + 0.4f * brightness;
        hsb[2] = brightness % 1.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, double u, double v, int width, double height, float textureWidth, double textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = (float)(1.0 / textureHeight);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, (double)y + height, 0.0).tex(u * (double)f, (v + (double)((float)height)) * (double)f1).endVertex();
        worldrenderer.pos(x + (float)width, (double)y + height, 0.0).tex((u + (double)width) * (double)f, (v + (double)((float)height)) * (double)f1).endVertex();
        worldrenderer.pos(x + (float)width, y, 0.0).tex((u + (double)width) * (double)f, v * (double)f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * (double)f, v * (double)f1).endVertex();
        tessellator.draw();
    }

    public int color(int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(2, 53, 120, hsb);
        float brightness = Math.abs((HUD.getOffset() + (float)index / (float)count * 4.0f) % 2.0f - 1.0f);
        brightness = 0.4f + 0.4f * brightness;
        hsb[2] = brightness % 1.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public int colorG(int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(61, 235, 84, hsb);
        float brightness = Math.abs((HUD.getOffset() + (float)index / (float)count * 4.0f) % 2.0f - 1.0f);
        brightness = 0.4f + 0.4f * brightness;
        hsb[2] = brightness % 1.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public int colorR(int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(237, 53, 40, hsb);
        float brightness = Math.abs((HUD.getOffset() + (float)index / (float)count * 4.0f) % 2.0f - 1.0f);
        brightness = 0.4f + 0.4f * brightness;
        hsb[2] = brightness % 1.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1.0) {
            double left = offset % 1.0;
            int off = (int)offset;
            offset = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offset;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    static {
        sorting = new Option<Boolean>("Sorting", "Sorting", true);
        bg = new Option<Boolean>("BackGround", "BackGround", false);
        arrayMode = new Mode("Color", "Color", (Enum[])ArrayMode.values(), (Enum)ArrayMode.FadeRed);
        WaterMarkmode = new Mode("WaterMark", "WaterMark", (Enum[])WaterMarkMode.values(), (Enum)WaterMarkMode.Skeet);
        font = null;
    }

    private static enum WaterMarkMode {
        Skeet;

    }

    public static enum BorderMode {
        NONE,
        Right,
        Left,
        WrapperLeft;

    }

    public static enum ArrayMode {
        DrunkClient,
        Test,
        FadeGreen,
        FadeRed;

    }

    public static enum FontMode {
        DrunkClient,
        Minecraft;

    }
}

