package me.superskidder.lune.modules.render.Hudmode;

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

public class FluxHUD extends HUD {


    public static void renderHud() {

        int rainbowTick = 0;
        Color rainbow2 = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));


        FontLoaders.C22.drawStringWithShadow(Lune.CLIENT_NAME, 2, 5, rainbow2.getRGB());


        CFontRenderer font1;
        font1 = FontLoaders.C16;

        if (!mc.gameSettings.showDebugInfo) {
            String name;
            ArrayList<Mod> sorted = new ArrayList<Mod>();
            for (Mod m : ModuleManager.modList) {
                if(((boolean) HUD.noRender.getValue()) && m.getType() == ModCategory.Render){
                    continue;
                }
                sorted.add(m);
            }
            sorted.sort((o1, o2) -> font1.getStringWidth(o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName())) - font1.getStringWidth(o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
            int y = 8;
            rainbowTick = 0;
            for (Mod m : sorted) {
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
                name = m.getName() + (m.getDisplayName() == null ? "" : " §W" + m.getDisplayName());


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
                    m.setYAnim(m.getYAnim() + 180f / mc.getDebugFPS());
                }

                if (m.getYAnim() > y) {
                    m.setYAnim(m.getYAnim() - 180f / mc.getDebugFPS());
                }

                if(Math.abs(m.getYAnim() - y)<1){
                    m.setYAnim(y);
                }

                if (m.getState()) {
                    float x2 = RenderUtil.width() - m.getXAnim();
                    if ((Boolean) BackGround.getValue()) {
                        RenderUtil.drawRect(x2 - 8, m.getYAnim(), Lune.sr.getScaledWidth(), m.getYAnim() + 10, new Color(0, 0, 0, 150).getRGB());

                        if ((Boolean) Rainbow.getValue()) {
                            if ((Boolean) alpharainbow.getValue()) {
                                RenderUtil.drawRect(new ScaledResolution(mc).getScaledWidth_double() - 2, m.getYAnim() - 2, new ScaledResolution(mc).getScaledWidth_double(), m.getYAnim() + 10, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                            } else {
                                RenderUtil.drawRect(new ScaledResolution(mc).getScaledWidth_double() - 2, m.getYAnim() - 2, new ScaledResolution(mc).getScaledWidth_double(), m.getYAnim() + 10, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                            }
                        } else {
                            if ((Boolean) alpharainbow.getValue()) {
                                RenderUtil.drawRect(new ScaledResolution(mc).getScaledWidth_double() - 2, m.getYAnim() - 2, new ScaledResolution(mc).getScaledWidth_double(), m.getYAnim() + 10, new Color(255, 130, 100+rainbow.getRed()/2).getRGB());
                            } else {
                                RenderUtil.drawRect(new ScaledResolution(mc).getScaledWidth_double() - 2, m.getYAnim() - 2, new ScaledResolution(mc).getScaledWidth_double(), m.getYAnim() + 10, new Color(255, 130, 100).getRGB());
                            }

                        }
                    }


                    if ((Boolean) Rainbow.getValue()) {
                        if ((Boolean) alpharainbow.getValue()) {
                            font1.drawStringWithShadow(name, x2 - 5, m.getYAnim() + 2, new Color((int) rainbow.getRed(), (int) rainbow.getGreen(), (int) rainbow.getBlue(), (int) rainbow.getBlue()).getRGB());
                        }else {
                            font1.drawStringWithShadow(name, x2 - 5, m.getYAnim() + 2, rainbow.getRGB());
                        }
                    } else if (!(Boolean) Rainbow.getValue()) {
                        if ((Boolean) alpharainbow.getValue()) {
                            font1.drawStringWithShadow(name, x2 - 5, m.getYAnim() + 2, new Color(255, 130, 100+rainbow.getRed()/2).getRGB());
                        } else {
                            font1.drawStringWithShadow(name, x2 - 5, m.getYAnim() + 2, new Color(255, 130, 100).getRGB());
                        }
                    }
                    y += 10;
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

