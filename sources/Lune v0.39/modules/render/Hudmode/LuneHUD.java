package me.superskidder.lune.modules.render.Hudmode;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.HUD;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class LuneHUD extends HUD {

    public static void renderHud() {

        int rainbowTick = 0;
        Color rainbow2 = new Color(Color.HSBtoRGB(
                (float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6))
                        % 1.0f,
                0.5f, 1.0f));
        String text = " \247w" + Lune.CLIENT_NAME + ChatFormatting.GREEN + "sense" + "\247w"
                + " | " + Lune.username + " | " + (mc.isSingleplayer() ? "singleplayer" : mc.getCurrentServerData().serverIP) + " | " + mc.debugFPS + "fps" + " | " + (mc.isSingleplayer() ? "non" : mc.getCurrentServerData().pingToServer + "ms");

        RenderUtil.drawBordered(5, 5, 5 + FontLoaders.F14.getStringWidth(text), 15, 1, new Color(50, 50, 50).getRGB(), new Color(100, 100, 100).getRGB());
        RenderUtil.drawBordered(7, 7, 5 + FontLoaders.F14.getStringWidth(text) - 4, 11, 1, new Color(50, 50, 50).getRGB(), new Color(100, 100, 100).getRGB());
        Color sense1 = new Color(Color.HSBtoRGB(
                (float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6))
                        % 1.0f,
                0.5f, 1.0f));
        Color sense2 = new Color(Color.HSBtoRGB(
                (float) ((double) mc.thePlayer.ticksExisted / 80.0 + Math.sin((double) rainbowTick / 80.0 * 1.6))
                        % 1.0f,
                0.5f, 1.0f));
        RenderUtil.drawGradientSideways(7, 17, 8 + FontLoaders.F14.getStringWidth(text), 18, sense1.getRGB(), sense2.getRGB());
        FontLoaders.F14.drawString(text, 7, 9, -1);


        CFontRenderer font1;
        font1 = FontLoaders.C16;

        if (!mc.gameSettings.showDebugInfo) {
            String name;
            ArrayList<Mod> sorted = new ArrayList<Mod>();
            for (Mod m : ModuleManager.modList) {
                if (((boolean) HUD.noRender.getValue()) && m.getType() == ModCategory.Render) {
                    continue;
                }
                sorted.add(m);
            }
            if (font.getValue()) {
                sorted.sort((o1,
                             o2) -> font1.getStringWidth(
                        o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName()))
                        - font1.getStringWidth(
                        o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            } else {
                sorted.sort((o1,
                             o2) -> mc.fontRendererObj.getStringWidth(
                        o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName()))
                        - mc.fontRendererObj.getStringWidth(
                        o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            }
            int y = 0;
            rainbowTick = 0;
            for (Mod m : sorted) {
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0
                        + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
                name = m.getName() + (m.getDisplayName() == null ? "" : " " + ChatFormatting.GRAY + m.getDisplayName());

                if (font.getValue()) {
                    if (m.getXAnim() < font1.getStringWidth(name) && m.getState()) {
                        if (m.getXAnim() < font1.getStringWidth(name) / 3) {
                            m.setXAnim(m.getXAnim() + 1);
                        } else {
                            m.setXAnim(m.getXAnim() + 1);
                        }
                    }
                    if (m.getXAnim() > -1 && !m.getState()) {
                        m.setXAnim(m.getXAnim() - 1);
                    }

                    if (m.getXAnim() > font1.getStringWidth(name) && m.getState()) {
                        m.setXAnim(font1.getStringWidth(name));
                    }
                } else {
                    if (m.getXAnim() < mc.fontRendererObj.getStringWidth(name) && m.getState()) {
                        if (m.getXAnim() < mc.fontRendererObj.getStringWidth(name) / 3) {
                            m.setXAnim(m.getXAnim() + 1);
                        } else {
                            m.setXAnim(m.getXAnim() + 1);
                        }
                    }
                    if (m.getXAnim() > -1 && !m.getState()) {
                        m.setXAnim(m.getXAnim() - 1);
                    }

                    if (m.getXAnim() > mc.fontRendererObj.getStringWidth(name) && m.getState()) {
                        m.setXAnim(mc.fontRendererObj.getStringWidth(name));
                    }
                }


                if (m.getState()) {
                    float x2 = RenderUtil.width() - m.getXAnim();
                    if (font.getValue()) {
                        if ((Boolean) Rainbow.getValue()) {
                            if ((Boolean) alpharainbow.getValue()) {
                                font1.drawStringWithShadow(name, x2 - 4, y,
                                        new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(),
                                                (int) rainbow.getBlue()).getRGB());
                            } else {
                                font1.drawStringWithShadow(name, x2 - 4, y, rainbow.getRGB());
                            }
                        } else if (!(Boolean) Rainbow.getValue()) {
                            if ((Boolean) alpharainbow.getValue()) {
                                font1.drawStringWithShadow(name, x2 - 4, y,
                                        new Color(107, 127, 255, rainbow.getBlue()).getRGB());
                            } else {
                                font1.drawStringWithShadow(name, x2 - 4, y,
                                        new Color(107, 127, 255).getRGB());
                            }
                        }
                    } else {
                        if ((boolean) BackGround.getValue()) {
                            RenderUtil.drawRect(x2 - 4, y, new ScaledResolution(mc).getScaledWidth_double(), y + 15, new Color(0, 0, 0, 150).getRGB());
                            RenderUtil.drawRect(new ScaledResolution(mc).getScaledWidth_double() - 1, y, new ScaledResolution(mc).getScaledWidth_double(), y + 15, rainbow.getRGB());
                        }
                        mc.fontRendererObj.drawString(name, (int) x2 - 2, y + 2.5f, rainbow.getRGB(), true);
                    }
                    y += 15;
                }
                if (++rainbowTick > 50) {
                    rainbowTick = 0;
                } else {
                    rainbowTick += 0.1;
                }
            }

        }
    }

    public static void update() {
    }
}
