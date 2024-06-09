/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.overlays;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.module.Module;
import winter.module.modules.Overlay;
import winter.notifications.Notification;
import winter.utils.render.FontUtils;
import winter.utils.render.overlays.Tab;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Hud {
    public static Notification cur;
    private Tab tab = new Tab();
    public static String watermark;
    public static double rn;
    public static FontUtils watermarkXD;
    public static FontUtils font;
    public static Minecraft mc;

    static {
        watermark = "Winter";
        rn = 1.5;
        mc = Minecraft.getMinecraft();
    }

    public Hud() {
        watermarkXD = new FontUtils("Century Gothic Bold", 0, 32);
        font = new FontUtils("Century Gothic Bold", 0, 20);
    }

    public void renderHud(Minecraft mc2, ScaledResolution sr2) {
        if (!Client.isEnabled("Overlay")) {
            return;
        }
        final FontRenderer font = mc2.fontRendererObj;
        String time = new SimpleDateFormat("hh:mm a").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }
        int lol = 2;
        Color rainbo = new Color(Color.HSBtoRGB((float)((double)mc2.thePlayer.ticksExisted / 75.0 + Math.sin(0.10471975511965977)) % 1.0f, 0.5058824f, 1.0f));
        if (Overlay.watermark.isEnabled()) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            mc2.fontRendererObj.drawStringShadowed("7.2", 2.5f + (float)mc2.fontRendererObj.getStringWidth(String.valueOf(watermark) + " "), lol, -805252865);
            mc2.fontRendererObj.drawStringShadowed(watermark, 2.5f, lol, -788529153);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            lol += 10;
        }
        if (Overlay.time.isEnabled()) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            mc2.fontRendererObj.drawStringShadowed(time, 2.5f, lol, -788529153);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            lol += 10;
        }
        if (Overlay.tabgui.isEnabled()) {
            this.tab.render(mc2, lol, Overlay.rainbow.isEnabled() ? rainbo.getRGB() : Color.getHSBColor((float)Overlay.hue.getValue() / 255.0f, 1.0f, 1.0f).getRGB());
        }
        EnumFacing yaw = EnumFacing.getHorizontal(MathHelper.floor_double((double)(mc2.thePlayer.rotationYaw * 4.0f / 360.0f) + 0.5) & 3);
        String displayString = String.valueOf(yaw.getName().substring(0, 1).toUpperCase()) + yaw.getName().substring(1);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        font.drawStringShadowed(displayString, (sr2.getScaledWidth() - font.getStringWidth(displayString)) / 2, BossStatus.statusBarTime > 0 ? 20 : 2, -788529153);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        int yXD = sr2.getScaledHeight() - 10;
        if (mc2.ingameGUI.getChatGUI().getChatOpen()) {
            yXD -= 13;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        font.drawStringShadowed("XYZ: \u00a77" + (int)mc2.thePlayer.posX + ", " + (int)mc2.thePlayer.posY + ", " + (int)mc2.thePlayer.posZ, 2.0f, yXD, -788529153);
        font.drawStringShadowed("FPS: \u00a77" + Minecraft.debugFPS, 2.0f, yXD - 10, -788529153);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        double y2 = 2.0;
        int cxd = 0;
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module mod : Client.getManager().getMods()) {
            mods.add(mod);
        }
        Collections.sort(mods, new Comparator<Module>(){

            @Override
            public int compare(Module mod1, Module mod2) {
                String mod1Name = String.valueOf(mod1.getName()) + mod1.getModeForArrayListLongNameLOL();
                String mod2Name = String.valueOf(mod2.getName()) + mod2.getModeForArrayListLongNameLOL();
                if (!Overlay.org.isEnabled()) {
                    return mod1Name.compareToIgnoreCase(mod2Name);
                }
                return font.getStringWidth(mod1Name) > font.getStringWidth(mod2Name) ? -1 : (font.getStringWidth(mod2Name) > font.getStringWidth(mod1Name) ? 1 : 0);
            }
        });
        for (Module mod : mods) {
            if (!mod.isEnabled() || !mod.visible()) continue;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            Color color = Overlay.rainbow.isEnabled() ? new Color(Color.HSBtoRGB((float)((double)mc2.thePlayer.ticksExisted / 50.0 + Math.sin((double)cxd / 50.0 * 1.5707963267948966)) % 1.0f, 0.5882353f, 1.0f)) : new Color(mod.color);
            int col2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
            font.drawStringShadowed(String.valueOf(mod.getName()) + (mod.getModeForArrayListLongNameLOL() == "" ? "" : new StringBuilder().append((Object)EnumChatFormatting.GRAY).append(mod.getModeForArrayListLongNameLOL()).toString()), sr2.getScaledWidth() - font.getStringWidth(String.valueOf(mod.getName()) + mod.getModeForArrayListLongNameLOL()) - 2, (float)y2, col2);
            y2 += (double)mc2.fontRendererObj.FONT_HEIGHT + 2.5;
            cxd = (int)((double)cxd + rn);
            if (cxd > 50) {
                cxd = 0;
            }
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        this.drawPotions(sr2);
    }

    public static void scissorBox(float x2, float y2, float xend, float yend, ScaledResolution sr2) {
        if (Hud.mc.currentScreen == null) {
            return;
        }
        int width = (int)(xend - x2);
        int height = (int)(yend - y2);
        int factor = sr2.getScaleFactor();
        int bottomY = (int)((float)Hud.mc.currentScreen.height - yend);
        GL11.glScissor((int)(x2 * (float)factor), bottomY * factor, width * factor, height * factor);
    }

    private void rainbowCircle(ScaledResolution sr2, int x2, int y2, int rad) {
        String xboy = "Thanks xboy!";
        GL11.glPushMatrix();
        int scale = Hud.mc.gameSettings.guiScale;
        Hud.mc.gameSettings.guiScale = 2;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        double tau = 6.283185307179586;
        double radius = rad;
        double fans = 55.0;
        GL11.glLineWidth(3.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glBegin(3);
        int i2 = 0;
        while ((double)i2 <= fans) {
            Color color = new Color(Color.HSBtoRGB((float)((double)Hud.mc.thePlayer.ticksExisted / fans + Math.sin((double)i2 / fans * 1.5707963267948966)) % 1.0f, 0.5058824f, 1.0f));
            GL11.glColor3f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f);
            GL11.glVertex2d((double)(x2 + rad) + radius * Math.cos((double)i2 * tau / fans), (double)(y2 + rad) + radius * Math.sin((double)i2 * tau / fans));
            ++i2;
        }
        GL11.glEnd();
        Hud.mc.gameSettings.guiScale = scale;
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    private void drawPotions(ScaledResolution scaledResolution) {
        FontRenderer font = Hud.mc.fontRendererObj;
        int x2 = scaledResolution.getScaledWidth() - 2;
        int y2 = scaledResolution.getScaledHeight() - 10;
        if (Hud.mc.ingameGUI.getChatGUI().getChatOpen()) {
            y2 -= 13;
        }
        for (Object o : Hud.mc.thePlayer.getActivePotionEffects()) {
            PotionEffect effect = (PotionEffect)o;
            String name = I18n.format(effect.getEffectName(), new Object[0]);
            if (effect.getAmplifier() == 1) {
                name = String.valueOf(name) + " " + I18n.format("enchantment.level.2", new Object[0]);
            } else if (effect.getAmplifier() == 2) {
                name = String.valueOf(name) + " " + I18n.format("enchantment.level.3", new Object[0]);
            } else if (effect.getAmplifier() == 3) {
                name = String.valueOf(name) + " " + I18n.format("enchantment.level.4", new Object[0]);
            } else if (effect.getAmplifier() > 0) {
                name = String.valueOf(name) + " " + (effect.getAmplifier() + 1);
            }
            int var1 = effect.getDuration() / 20;
            int var2 = var1 / 60;
            var1 %= 60;
            int color = 55;
            if (var2 == 0) {
                if (var1 <= 5) {
                    color = 52;
                } else if (var1 <= 10) {
                    color = 99;
                } else if (var1 <= 15) {
                    color = 54;
                } else if (var1 <= 20) {
                    color = 101;
                }
            }
            name = String.format("%s: \u00a7%s%s", name, Character.valueOf((char)color), Potion.getDurationString(effect));
            Color c2 = new Color(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            font.drawStringShadowed(name, x2 - font.getStringWidth(name), y2, new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 200).getRGB());
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            y2 -= 10;
        }
    }

    public void tabgui() {
        this.tab.render(Minecraft.getMinecraft(), 13.0f, -1);
    }

    public int color(Module mod)
    {
      switch (mod.getCategory())
      {
      case Combat: 
        return 16738657;
      case Render: 
        return -1;
      case Other: 
        return 11849708;
      case Movement: 
        return 7855479;
      case World: 
        return 7855479;
      }
      return 0;
    }

    public static void drawString(String text, float x2, float y2, int color) {
        Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(text, x2, y2, color);
    }
}

