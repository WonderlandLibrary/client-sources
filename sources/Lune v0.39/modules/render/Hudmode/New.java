package me.superskidder.lune.modules.render.Hudmode;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.HUD;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.utils.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class New extends HUD {

    private static boolean useFont;
    private static String[] directions = new String[]{"S", "SW", "W", "NW", "N", "NE", "E", "SE"};

    public static void renderHud() {

        CFontRenderer font = FontLoaders.F18;
        if (HUD.font.getValue().booleanValue()) {
            useFont = true;
        } else if (!HUD.font.getValue().booleanValue()) {
            useFont = false;
        }
        if (!mc.gameSettings.showDebugInfo) {
            String name;
            String direction;
            Color rainbow2 = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) mc.thePlayer.ticksExisted / 150.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
            if (useFont) {
                final CFontRenderer cFontRenderer3 = font;
                final StringBuilder append5 = new StringBuilder(String.valueOf(Lune.CLIENT_NAME)).append(" ").append(EnumChatFormatting.GRAY);
                cFontRenderer3.drawStringWithShadow(append5.substring(0, 1) + append5.substring(1), 2.0, 2.0, rainbow2.getRGB());
            } else {
                final FontRenderer fontRendererObj3 = mc.fontRendererObj;
                final StringBuilder append6 = new StringBuilder(String.valueOf(Lune.CLIENT_NAME)).append(" ").append(EnumChatFormatting.GRAY);
                fontRendererObj3.drawStringWithShadow(append6.substring(0, 1) + ChatFormatting.WHITE + append6.substring(1), 2, 2, rainbow2.getRGB());

            }


            ArrayList<Mod> sorted = new ArrayList<Mod>();
            for (Mod m : Lune.moduleManager.modList) {
                if (!m.getState()) continue;
                if (((boolean) HUD.noRender.getValue()) && m.getType() == ModCategory.Render) {
                    continue;
                }
                sorted.add(m);
            }
            if (useFont) {
                sorted.sort((o1, o2) -> font.getStringWidth(o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName())) - font.getStringWidth(o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            } else {
                sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName())) - mc.fontRendererObj.getStringWidth(o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            }
            int y = 1;
            int rainbowTick = 0;


            if (useFont) {
                for (Mod m : sorted) {

                    name = m.getDisplayName() == null ? m.getName() : m.getName() + " \247W" + m.getDisplayName();
                    if (m.getXAnim() < font.getStringWidth(name) && m.getState()) {
                        if (m.getXAnim() < font.getStringWidth(name) / 3) {
                            m.setXAnim(m.getXAnim() + 1);
                        } else {
                            m.setXAnim(m.getXAnim() + 1);
                        }
                    }
                    if (m.getXAnim() > -1 && !m.getState()) {
                        m.setXAnim(m.getXAnim() - 1);
                    }

                    if (m.getXAnim() > font.getStringWidth(name) && m.getState()) {
                        m.setXAnim(font.getStringWidth(name));
                    }

                    if (m.getYAnim() < y) {
                        m.setYAnim(m.getYAnim() + 90f / mc.getDebugFPS());
                    }

                    if (m.getYAnim() > y) {
                        m.setYAnim(m.getYAnim() - 90f / mc.getDebugFPS());
                    }

                    if (Math.abs(m.getYAnim() - y) < 1) {
                        m.setYAnim(y);
                    }
                    float x = RenderUtil.width() - font.getStringWidth(name);
                    Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
                    if (((Boolean) HUD.Rainbow.getValue())) {
                        font.drawStringWithShadow(name, x - 3.0f, m.getYAnim() + 1, rainbow.getRGB());
                    } else {
                        font.drawStringWithShadow(name, x - 2.0f, m.getYAnim() + 1, new Color(107, 127 + rainbow.getRed() / 3, 255).getRGB());
                    }
                    if (++rainbowTick > 50) {
                        rainbowTick = 0;
                    }
                    y += 9;
                }
            } else {
                for (Mod m : sorted) {
                    name = m.getDisplayName() == null ? m.getName() : m.getName() + ChatFormatting.WHITE + " " + m.getDisplayName();
                    if (m.getXAnim() < font.getStringWidth(name) && m.getState()) {
                        if (m.getXAnim() < font.getStringWidth(name) / 3) {
                            m.setXAnim(m.getXAnim() + 1);
                        } else {
                            m.setXAnim(m.getXAnim() + 1);
                        }
                    }
                    if (m.getXAnim() > -1 && !m.getState()) {
                        m.setXAnim(m.getXAnim() - 1);
                    }

                    if (m.getXAnim() > font.getStringWidth(name) && m.getState()) {
                        m.setXAnim(font.getStringWidth(name));
                    }

                    if (m.getYAnim() < y) {
                        m.setYAnim(m.getYAnim() + 90f / mc.getDebugFPS());
                    }

                    if (m.getYAnim() > y) {
                        m.setYAnim(m.getYAnim() - 90f / mc.getDebugFPS());
                    }

                    if (Math.abs(m.getYAnim() - y) < 1) {
                        m.setYAnim(y);
                    }
                    float x = RenderUtil.width() - mc.fontRendererObj.getStringWidth(name);
                    Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
                    if (((Boolean) HUD.Rainbow.getValue())) {
                        mc.fontRendererObj.drawStringWithShadow(name, x - 2.0f, m.getYAnim(), rainbow.getRGB());
                    } else {
                        mc.fontRendererObj.drawStringWithShadow(name, x - 2.0f, m.getYAnim(), new Color(new Color(HUD.themeColor).getRed(), new Color(HUD.themeColor).getGreen(), new Color(HUD.themeColor).getBlue()).getRGB());
                    }
                    if (++rainbowTick > 50) {
                        rainbowTick = 0;
                    }
                    y += 9;
                }
            }


            double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            double moveSpeed = Math.sqrt(xDist * xDist + zDist * zDist) * 20;


            String text = (Object) ((Object) EnumChatFormatting.GRAY) + "X" + (Object) ((Object) EnumChatFormatting.WHITE) + ": " + MathHelper.floor_double(mc.thePlayer.posX) + " " + (Object) ((Object) EnumChatFormatting.GRAY) + "Y" + (Object) ((Object) EnumChatFormatting.WHITE) + ": " + MathHelper.floor_double(mc.thePlayer.posY) + " " + (Object) ((Object) EnumChatFormatting.GRAY) + "Z" + (Object) ((Object) EnumChatFormatting.WHITE) + ": " + MathHelper.floor_double(mc.thePlayer.posZ) + "  " + Math.round(moveSpeed) + " \2477b/s\247r";
            if (useFont) {
                int ychat;
                int n = ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 24 : 10;
                font.drawStringWithShadow(text, 4.0, new ScaledResolution(mc).getScaledHeight() - ychat, new Color(11, 12, 17).getRGB());
                drawPotionStatus(new ScaledResolution(mc));
            } else {
                int ychat;
                int n = ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 25 : 10;
                mc.fontRendererObj.drawStringWithShadow(text, 4.0f, new ScaledResolution(mc).getScaledHeight() - ychat, new Color(11, 12, 17).getRGB());
                drawPotionStatus(new ScaledResolution(mc));
            }
        }
    }

    private static void drawPotionStatus(ScaledResolution sr) {
        CFontRenderer font = FontLoaders.F18;
        int y = 0;
        for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
            int ychat;
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName(), new Object[0]);
            switch (effect.getAmplifier()) {
                case 1: {
                    PType = String.valueOf(PType) + " II";
                    break;
                }
                case 2: {
                    PType = String.valueOf(PType) + " III";
                    break;
                }
                case 3: {
                    PType = String.valueOf(PType) + " IV";
                    break;
                }
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = String.valueOf(PType) + "\u00a77:\u00a76 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = String.valueOf(PType) + "\u00a77:\u00a7c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = String.valueOf(PType) + "\u00a77:\u00a77 " + Potion.getDurationString(effect);
            }
            int n = ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 5 : -10;
            if (useFont) {
                font.drawStringWithShadow(PType, sr.getScaledWidth() - font.getStringWidth(PType) - 2, sr.getScaledHeight() - font.getHeight() + y - 12 - ychat, potion.getLiquidColor());
            } else {
                mc.fontRendererObj.drawStringWithShadow(PType, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(PType) - 2, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat, potion.getLiquidColor());
            }
            y -= 10;
        }
    }

    public static void update() {
    }
}

