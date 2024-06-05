package de.tired.module.impl;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.shader.renderapi.ColorUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.Render2DEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import de.tired.module.impl.list.visual.Shader;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.list.ColorCorrectionShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@ModuleAnnotation(name = "Hud", category = ModuleCategory.RENDER)
public class HudModule extends Module {

    private final List<Double> motionList = new ArrayList<>();

    public HudModule() {

    }

    public void renderGraphYaxisBlack(int x, int y, float lineWidth) {
        final float height = 0.35F;

        double math = Math.round(Math.abs(MC.thePlayer.motionY) * 10.0) / 10.0;
        this.motionList.add((double) (1.0F - MC.timer.renderPartialTicks) * this.motionList.get(this.motionList.size() - 1) + math * (double) MC.timer.renderPartialTicks);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(3);
        float interpret = 120.0F / (float) this.motionList.size();
        for (int i = 0; i < this.motionList.size(); ++i) {
            double motionList = this.motionList.get(i);
            if (motionList > (double) height) {
                motionList = height;
            }

            RenderProcessor.color(Color.BLACK.getRGB());

            GL11.glVertex2d(x + (double) ((float) i * interpret), y - motionList * 120.0D);
            GL11.glVertex2d(x + (double) ((float) i * interpret), y - motionList * 120.0D);
        }
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        while (this.motionList.size() > 100) {
            this.motionList.remove(0);
        }

        GL11.glEnable(GL11.GL_BLEND);

    }


    public void renderGraphYaxis(int x, int y, float lineWidth) {
        final float height = 0.35F;

        GL11.glLineWidth(lineWidth);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(3);
        float interpret = 120.0F / (float) this.motionList.size();
        for (int i = 0; i < this.motionList.size(); ++i) {
            double motionList = this.motionList.get(i);
            if (motionList > (double) height) {
                motionList = height;
            }

            Color color = ColorUtil.rainbow(System.nanoTime(), i * interpret / 4, 1);
            RenderProcessor.color(color.getRGB());


            GL11.glVertex2d(x + (double) ((float) i * interpret), y - motionList * 70);
        }
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        while (this.motionList.size() > 2200) {
            this.motionList.remove(0);
        }

        GL11.glEnable(GL11.GL_BLEND);

    }

    public static HudModule getInstance() {
        return ModuleManager.getInstance(HudModule.class);
    }

    @EventTarget
    public void onRender(Render2DEvent e) {

        double math = Math.round(Math.abs(MC.thePlayer.motionY) * 10.0) / 10.0;
        this.motionList.add(math);


        if (Shader.getInstance().isState() && Shader.getInstance().Exposure.getValue()) {
            ShaderManager.shaderBy(ColorCorrectionShader.class).doRender();
        }
        final ScaledResolution sr = new ScaledResolution(MC);
        FontManager.light.drawOutlinedString("FPS: " + Minecraft.getDebugFPS(), 3, sr.getScaledHeight() - 7, -1);


        ModuleManager.sortedModList.sort((m1, m2) -> FontManager.light.getStringWidth(m2.getNameWithSuffix()) - FontManager.light.getStringWidth(m1.getNameWithSuffix()));

        int yAxis3 = -2;
        for (Module module : ModuleManager.sortedModList) {
            if (module.state) {
                FontManager.light.drawStringWithShadow(module.name, 1, 2 + yAxis3, -1);
                yAxis3 += 10;
            }
        }

        int x = sr.getScaledWidth() / 2 - 60;

        int y = sr.getScaledHeight() - 100;


        renderGraphYaxis(x, y, 1);

        GlStateManager.resetColor();
        FontManager.light.drawString("FPS: " + Minecraft.getDebugFPS(), 3, sr.getScaledHeight() - 7, -1);


        renderGraphYaxis(x, y, 2);
        //      Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlineRect2(x, sr.getScaledHeight() - 40 - 50, x + 120, sr.getScaledHeight() - 40, Integer.MAX_VALUE);

    }


    @Override
    public void onState() {
        motionList.clear();
        motionList.add(Math.round(Math.abs(MC.thePlayer.motionY) * 10.0) / 10.0);
    }

    @Override
    public void onUndo() {
        motionList.clear();
        motionList.add(Math.round(Math.abs(MC.thePlayer.motionY) * 10.0) / 10.0);
    }
}
