package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.utils.objects.Dragging;
import dev.event.EventListener;
import dev.event.impl.render.Render2DEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.impl.render.ShaderEvent;
import dev.settings.impl.*;
import dev.utils.misc.MathUtils;
import dev.utils.objects.Drag;
import dev.client.tenacity.utils.render.*;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.utils.render.GLUtil;
import dev.utils.render.StencilUtil;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Radar extends Module {

    public final NumberSetting size = new NumberSetting("Size", 90, 125, 75, 1);
    public final ModeSetting colorMode = new ModeSetting("Color", "Sync", "Sync", "Analogous", "Tenacity", "Gradient", "Modern");
    public final ModeSetting degree = new ModeSetting("Degree", "30", "30", "-30");
    private final ColorSetting color1 = new ColorSetting("Color 1", Tenacity.INSTANCE.getClientColor());
    private final ColorSetting color2 = new ColorSetting("Color 2", Tenacity.INSTANCE.getAlternateClientColor());
    public final MultipleBoolSetting targets = new MultipleBoolSetting("Entities", new BooleanSetting("Players", true));



    public final Dragging drag = Tenacity.INSTANCE.createDrag(this, "radar", 5, 40);
    private final List<EntityLivingBase> entities = new ArrayList<>();

    public Radar() {
        super("Radar", Category.RENDER, "Shows entites on a gui");
        color1.addParent(colorMode, modeSetting -> modeSetting.is("Gradient") || modeSetting.is("Analogous"));
        color2.addParent(colorMode, modeSetting -> modeSetting.is("Gradient") && !modeSetting.is("Analogous"));
        degree.addParent(colorMode, modeSetting -> modeSetting.is("Analogous"));
        addSettings(colorMode, color1, color2, degree, size);
    }

    private final EventListener<ShaderEvent> blurEvent = e -> {
        float x = drag.getX(), y = drag.getY(), size = this.size.getValue().floatValue(), middleX = x + size / 2f, middleY = y + size / 2f;
        RoundedUtil.drawRound(x, y, size, size, 6, false, Color.WHITE);

    };

    private Color gradientColor1 = Color.WHITE, gradientColor2 = Color.WHITE, gradientColor3 = Color.WHITE, gradientColor4 = Color.WHITE;
    @SuppressWarnings("unused")
    private final EventListener<Render2DEvent> render2DEvent = e -> {
        getEntities();
        float x = drag.getX(), y = drag.getY(), size = this.size.getValue().floatValue(), middleX = x + size / 2f, middleY = y + size / 2f;

        drag.setWidth(size);
        drag.setHeight(size);

        Color lineColor = new Color(255, 255, 255, 180);


        switch (colorMode.getMode()) {
            case "Sync":
                HudMod hudMod = (HudMod) Tenacity.INSTANCE.getModuleCollection().get(HudMod.class);
                Color[] colors = hudMod.getClientColors();
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                break;
            case "Tenacity":
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                break;
            case "Gradient":
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, color1.getColor(), color2.getColor(), HudMod.hueInterpolation.isEnabled());
                break;
            case "Analogous":
                int val = degree.is("30") ? 0 : 1;
                Color analogous = ColorUtil.getAnalogousColor(color1.getColor())[val];
                gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 0, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 90, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor3 = ColorUtil.interpolateColorsBackAndForth(15, 180, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                gradientColor4 = ColorUtil.interpolateColorsBackAndForth(15, 270, color1.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                break;
            case "Modern":
                RoundedUtil.drawRoundOutline(x, y, size, size, 6, .5f, new Color(10, 10, 10, 80), new Color(-2));
                break;
            case "Dark":
                lineColor = new Color(120, 120, 120);
                RoundedUtil.drawRoundOutline(x, y, size, size, 6, .5f, new Color(10, 10, 10, 80), new Color(90, 90, 90, 180));
                break;
        }
        boolean outlinedRadar = !(colorMode.is("Modern"));
        if(outlinedRadar) {
            RoundedUtil.drawGradientRound(x,y,size,size, 6, ColorUtil.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2);
        }


        if(outlinedRadar) {
            Gui.drawRect2(x - 1, y + (size / 2f - .5), size + 2, 1, lineColor.getRGB());
            Gui.drawRect2(x + (size / 2f - .5), y - 1, 1, size + 2, lineColor.getRGB());
        }else {
            Gui.drawRect2(x + 1, y + (size / 2f - .5), size - 2, 1, lineColor.getRGB());
            Gui.drawRect2(x + (size / 2f - .5), y + 1, 1, size - 2, lineColor.getRGB());
        }

        StencilUtil.initStencilToWrite();
        RenderUtil.renderRoundedRect(x, y, size, size, 6, -1);
        StencilUtil.readStencilBuffer(1);
        GLUtil.rotate(middleX, middleY, mc.thePlayer.rotationYaw, () -> {
            for (EntityLivingBase entity : entities) {
                double xDiff = MathUtils.interpolate(entity.prevPosX, entity.posX, mc.timer.renderPartialTicks) - MathUtils.interpolate(mc.thePlayer.prevPosX, mc.thePlayer.posX, mc.timer.renderPartialTicks);
                double zDiff = MathUtils.interpolate(entity.prevPosZ, entity.posZ, mc.timer.renderPartialTicks) - MathUtils.interpolate(mc.thePlayer.prevPosZ, mc.thePlayer.posZ, mc.timer.renderPartialTicks);
                if ((xDiff + zDiff) < (size / 2f)) {
                    float translatedX = (float) (middleX - xDiff);
                    float translatedY = (float) (middleY - zDiff);
                    RoundedUtil.drawRound(translatedX, translatedY, 3, 3, 1f, new Color(255, 255, 255, 255));
                    //Gui.drawRect2(translatedX, translatedY, 3, 3, -1);
                }
            }
        });
        StencilUtil.uninitStencilBuffer();
    };


    public void getEntities() {
        entities.clear();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity instanceof EntityPlayer && entity != mc.thePlayer && !entity.isInvisible()) {
                    entities.add((EntityLivingBase) entity);
                }
            }
        }
    }


    @Override
    public void onEnable() {
        super.onEnable();
    }
}
