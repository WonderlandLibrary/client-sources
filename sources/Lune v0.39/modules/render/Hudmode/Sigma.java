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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Sigma extends HUD {


    public static void update() {

    }


    public static void renderHud() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontLoaders.F30.drawStringWithShadow(Lune.CLIENT_NAME, 7, 2, -1);
        FontLoaders.C18.drawStringWithShadow(Lune.CLIENT_Ver, FontLoaders.F30.getStringWidth(Lune.CLIENT_NAME), FontLoaders.F30.getStringHeight(Lune.CLIENT_NAME) + 2, -1);

        CFontRenderer font1;
        font1 = FontLoaders.C18;

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
            int y = 2;
            int rainbowTick = 0;


            for (Mod m : sorted) {
                name = m.getName() + (m.getDisplayName() == null ? "" : " " + ChatFormatting.GRAY + m.getDisplayName());


                if (m.getXAnim() < 255 && m.getState()) {
                    m.setXAnim(m.getXAnim() + 180f / mc.getDebugFPS());
                }

                if (m.getXAnim() > 0 && !m.getState()) {
                    m.setXAnim(0);
                }

//                if (m.getXAnim() > font1.getStringWidth(name) && m.getState()) {
//                    m.setXAnim(font1.getStringWidth(name));
//                }
                if(Math.abs(m.getYAnim() - y)<1){
                    m.setYAnim(y);
                }





                if (m.getState()) {
                    float x2 = RenderUtil.width() - font1.getStringWidth(name);

                    if (m.getYAnim() < y) {
                        m.setYAnim(m.getYAnim() + 120F / mc.getDebugFPS());
                    }

                    if (m.getYAnim() > y) {
                        m.setYAnim(m.getYAnim() - 120F / mc.getDebugFPS());
                    }

                    //Shadow -- Debug

                    final float width = (float) font1.getStringWidth(name);
                    GL11.glPushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableAlpha();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    mc.getTextureManager().bindTexture(new ResourceLocation("client/arraylistshadow.png"));
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 0.7f);
                    Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() - width - 10, m.getYAnim() - 18, 0.0f, 0.0f, font1.getStringWidth(name) + 20 + 10, 38.5f, font1.getStringWidth(name) * 1 + 20 + 10, 38.5F);
                    GlStateManager.disableBlend();
                    GlStateManager.enableAlpha();
                    GL11.glPopMatrix();


                    //Shadow

                    font1.drawString(name, x2 - 3, m.getYAnim() + 2, new Color(255, 255, 255, (int) m.getXAnim() > 255 ? 255 : (int) m.getXAnim()).getRGB());
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

}

