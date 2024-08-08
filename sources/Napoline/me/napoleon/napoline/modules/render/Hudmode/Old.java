package me.napoleon.napoline.modules.render.Hudmode;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.font.CFontRenderer;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.modules.render.HUD;
import me.napoleon.napoline.utils.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Old extends HUD {


    public static void renderHud() {

        Color rainbow2 = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) 1 / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));

        FontLoaders.C18.drawString((Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver).toUpperCase().substring(1), 7, 4, -1);
        FontLoaders.C18.drawString((Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver).toUpperCase().substring(0, 1), 2, 4, rainbow2.getRGB());

        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");

        FontLoaders.C18.drawString("[" + df.format(new Date()) + "]", 3 + FontLoaders.C18.getStringWidth((Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver).toUpperCase()), 4, new Color(180, 180, 180).getRGB());
        CFontRenderer font1;
        font1 = FontLoaders.C18;

        if (!mc.gameSettings.showDebugInfo) {
            FontLoaders.C18.drawString("Development - SuperSkidder & Marshall", Napoline.sr.getScaledWidth() - 175, Napoline.sr.getScaledHeight() - 15, new Color(255, 255, 255, 150).getRGB());
            String name;
            ArrayList<Mod> sorted = new ArrayList<Mod>();
            for (Mod m : ModuleManager.modList) {
                if(((boolean) HUD.noRender.getValue()) && m.getType() == ModCategory.Render){
                    continue;
                }
                sorted.add(m);
            }
            sorted.sort((o1, o2) -> font1.getStringWidth(o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName())) - font1.getStringWidth(o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            int y = 2;
            int rainbowTick = 0;
            for (Mod m : sorted) {
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
                name = m.getName() + (m.getDisplayName() == null ? "" : " " + ChatFormatting.WHITE + m.getDisplayName());


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

                if (m.getYAnim() < y) {
                    m.setYAnim(m.getYAnim() + 1);
                }

                if (m.getYAnim() > y) {
                    m.setYAnim(m.getYAnim() - 1);
                }
                if (Math.abs(m.getYAnim() - y) < 1) {
                    m.setYAnim(y);
                }
                if (m.getState()) {
                    float x2 = RenderUtil.width() - m.getXAnim();
                    if ((Boolean) BackGround.getValue()) {
                        RenderUtil.drawRect(x2 - 8, m.getYAnim() - 2, Napoline.sr.getScaledWidth(), m.getYAnim() + 10, new Color(0, 0, 0, 100).getRGB());

                        if ((Boolean) Rainbow.getValue()) {
                            if ((Boolean) alpharainbow.getValue()) {
                                RenderUtil.drawRect(x2 - 8, m.getYAnim() - 2, x2 - 7, m.getYAnim() + 10, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                            } else {
                                RenderUtil.drawRect(x2 - 8, m.getYAnim() - 2, x2 - 7, m.getYAnim() + 10, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                            }
                        } else {
                            if ((Boolean) alpharainbow.getValue()) {
                                RenderUtil.drawRect(x2 - 8, m.getYAnim() - 2, x2 - 7, m.getYAnim() + 10, new Color(107, 127, 255, rainbow.getBlue()).getRGB());
                            } else {
                                RenderUtil.drawRect(x2 - 8, m.getYAnim() - 2, x2 - 7, m.getYAnim() + 10, new Color(107, 127, 255).getRGB());
                            }

                        }
                    }


                    if ((Boolean) Rainbow.getValue()) {
                        if ((Boolean) alpharainbow.getValue()) {
                            font1.drawStringWithShadow(name, x2 - 3, m.getYAnim() + 2, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                        }
                        font1.drawStringWithShadow(name, x2 - 3, m.getYAnim() + 2, rainbow.getRGB());
                    } else if (!(Boolean) Rainbow.getValue()) {
                        if ((Boolean) alpharainbow.getValue()) {
                            font1.drawStringWithShadow(name, x2 - 3, m.getYAnim() + 2, new Color(107, 127, 255, rainbow.getBlue()).getRGB());
                        } else {
                            font1.drawStringWithShadow(name, x2 - 3, m.getYAnim() + 2, new Color(107, 127, 255).getRGB());
                        }
                    }
                    y += 12;
                }
                if (++rainbowTick > 50) {
                    rainbowTick = 0;
                } else {
                    rainbowTick += 1;
                }
            }

        }
    }

    public static void update() {
    }
}

