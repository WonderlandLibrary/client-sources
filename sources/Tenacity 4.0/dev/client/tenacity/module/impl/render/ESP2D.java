package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.render.*;
import dev.event.EventListener;
import dev.event.impl.render.NametagRenderEvent;
import dev.event.impl.render.Render2DEvent;
import dev.event.impl.render.Render3DEvent;
import dev.settings.ParentAttribute;
import dev.settings.impl.*;
import dev.utils.font.FontUtil;
import dev.utils.misc.MathUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class ESP2D extends Module {

    private final MultipleBoolSetting validEntities = new MultipleBoolSetting("Valid Entities",
            new BooleanSetting("Players", true),
            new BooleanSetting("Animals", true),
            new BooleanSetting("Mobs", true));


    private final BooleanSetting mcfont = new BooleanSetting("Minecraft Font", true);
    private final BooleanSetting itemHeld = new BooleanSetting("Item Held", true);
    private final BooleanSetting boxEsp = new BooleanSetting("Box", true);
    private final ModeSetting boxColorMode = new ModeSetting("Box Mode", "Sync", "Sync", "Tenacity", "Light Rainbow", "Rainbow", "Static", "Fade", "Double Color", "Analogous");
    private final ColorSetting boxColor = new ColorSetting("Box Color", Color.PINK);
    private final ModeSetting degree = new ModeSetting("Degree", "30", "30", "-30");
    private final ColorSetting boxColorAlt = new ColorSetting("Box Color Alt", Color.BLUE);

    private final BooleanSetting healthBar = new BooleanSetting("Health Bar", true);
    private final ModeSetting healthBarMode = new ModeSetting("Health Bar Mode", "Color", "Health", "Color");
    private final BooleanSetting healthBarText = new BooleanSetting("Health Bar Text", true);


    private final BooleanSetting nametags = new BooleanSetting("Tags", true);
    private final BooleanSetting redTags = new BooleanSetting("Red Tags", true);
    private final NumberSetting scale = new NumberSetting("Tag Scale", .75, 1, .35, .05);
    private final MultipleBoolSetting nametagSettings = new MultipleBoolSetting("Nametag Settings",
            new BooleanSetting("Health Text", true),
            new BooleanSetting("Background", true));

    private final ModeSetting backgroundMode = new ModeSetting("Background Mode", "Rect", "Rect", "Round");

    public ESP2D() {
        super("2D ESP", Category.RENDER, "Draws a box in 2D space around entitys");
        boxColorMode.addParent(boxEsp, ParentAttribute.BOOLEAN_CONDITION);
        boxColor.addParent(boxColorMode, modeSetting -> modeSetting.is("Fade") || modeSetting.is("Double Color") || modeSetting.is("Analogous") || modeSetting.is("Static"));
        boxColorAlt.addParent(boxColorMode, modeSetting -> modeSetting.is("Double Color"));
        degree.addParent(boxColorMode, modeSetting -> modeSetting.is("Analogous"));

        scale.addParent(nametags, ParentAttribute.BOOLEAN_CONDITION);
        nametagSettings.addParent(nametags, ParentAttribute.BOOLEAN_CONDITION);
        redTags.addParent(nametags, ParentAttribute.BOOLEAN_CONDITION);
        backgroundMode.addParent(nametagSettings, multipleBoolSetting -> multipleBoolSetting.getSetting("Background").isEnabled());

        healthBarMode.addParent(healthBar, ParentAttribute.BOOLEAN_CONDITION);
        healthBarText.addParent(healthBar, ParentAttribute.BOOLEAN_CONDITION);


        addSettings(validEntities, mcfont, itemHeld, boxEsp, boxColorMode, boxColor, degree, boxColorAlt, healthBar, healthBarMode, healthBarText, nametags, scale, redTags, nametagSettings, backgroundMode);
    }


    private final Map<Entity, Vector4f> entityPosition = new HashMap<>();

    private final EventListener<NametagRenderEvent> onNametagRender = e -> {
        if (nametags.isEnabled()) e.cancel();
    };


    private final EventListener<Render3DEvent> event3DCall = e -> {
        entityPosition.clear();
        for (final Entity entity : mc.theWorld.loadedEntityList) {
            if (shouldRender(entity) && ESPUtil.isInView(entity)) {
                entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
            }
        }

    };

    private final NumberFormat df = new DecimalFormat("0.#");
    private final Color backgroundColor = new Color(10, 10, 10, 130);

    private Color firstColor = Color.BLACK, secondColor = Color.BLACK, thirdColor = Color.BLACK, fourthColor = Color.BLACK;
    private final EventListener<Render2DEvent> eventCall = e -> {
        if (boxEsp.isEnabled()) {
            switch (boxColorMode.getMode()) {
                case "Sync":
                    HudMod hudMod = (HudMod) Tenacity.INSTANCE.getModuleCollection().get(HudMod.class);
                    Color[] colors = hudMod.getClientColors();
                    firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                    secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                    thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                    fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, colors[0], colors[1], HudMod.hueInterpolation.isEnabled());
                    break;
                case "Tenacity":
                    firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                    secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                    thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                    fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), HudMod.hueInterpolation.isEnabled());
                    break;
                case "Light Rainbow":
                    firstColor = ColorUtil.rainbow(15, 0, .6f, 1, 1);
                    secondColor = ColorUtil.rainbow(15, 90, .6f, 1, 1);
                    thirdColor = ColorUtil.rainbow(15, 180, .6f, 1, 1);
                    fourthColor = ColorUtil.rainbow(15, 270, .6f, 1, 1);
                    break;
                case "Rainbow":
                    firstColor = ColorUtil.rainbow(15, 0, 1, 1, 1);
                    secondColor = ColorUtil.rainbow(15, 90, 1, 1, 1);
                    thirdColor = ColorUtil.rainbow(15, 180, 1, 1, 1);
                    fourthColor = ColorUtil.rainbow(15, 270, 1, 1, 1);
                    break;
                case "Static":
                    firstColor = boxColor.getColor();
                    secondColor = firstColor;
                    thirdColor = firstColor;
                    fourthColor = firstColor;
                    break;
                case "Fade":
                    firstColor = ColorUtil.fade(15, 0, boxColor.getColor(), 1);
                    secondColor = ColorUtil.fade(15, 90, boxColor.getColor(), 1);
                    thirdColor = ColorUtil.fade(15, 180, boxColor.getColor(), 1);
                    fourthColor = ColorUtil.fade(15, 270, boxColor.getColor(), 1);
                    break;
                case "Double Color":
                    firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, boxColor.getColor(), boxColorAlt.getColor(), HudMod.hueInterpolation.isEnabled());
                    secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, boxColor.getColor(), boxColorAlt.getColor(), HudMod.hueInterpolation.isEnabled());
                    thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, boxColor.getColor(), boxColorAlt.getColor(), HudMod.hueInterpolation.isEnabled());
                    fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, boxColor.getColor(), boxColorAlt.getColor(), HudMod.hueInterpolation.isEnabled());
                    break;
                case "Analogous":
                    int val = degree.is("30") ? 0 : 1;
                    Color analogous = ColorUtil.getAnalogousColor(boxColor.getColor())[val];
                    firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, boxColor.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                    secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, boxColor.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                    thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, boxColor.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                    fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, boxColor.getColor(), analogous, HudMod.hueInterpolation.isEnabled());
                    break;
            }
        }

        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (Entity entity : entityPosition.keySet()) {
            Vector4f pos = entityPosition.get(entity);
            float x = pos.getX(),
                    y = pos.getY(),
                    right = pos.getZ(),
                    bottom = pos.getW();

            if (nametags.isEnabled() && entity instanceof EntityLivingBase) {
                EntityLivingBase renderingEntity = (EntityLivingBase) entity;
                float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);
                StringBuilder text = new StringBuilder((redTags.isEnabled() ? "§c" : "§f") + StringUtils.stripControlCodes(renderingEntity.getDisplayName().getUnformattedText()));
                if (nametagSettings.getSetting("Health Text").isEnabled()) {
                    text.append(String.format(" §7[§r%s HP§7]", df.format(renderingEntity.getHealth())));
                }
                double fontScale = scale.getValue();
                float middle = x + ((right - x) / 2);
                float textWidth = 0;
                double fontHeight;
                if (mcfont.isEnabled()) {
                    textWidth = mc.fontRendererObj.getStringWidth(text.toString());
                    middle -= (textWidth * fontScale) / 2f;
                    fontHeight = mc.fontRendererObj.FONT_HEIGHT * fontScale;
                } else {
                    textWidth = (float) FontUtil.tenacityBoldFont20.getStringWidth(text.toString());
                    middle -= (textWidth * fontScale) / 2f;
                    fontHeight = FontUtil.tenacityBoldFont20.getHeight() * fontScale;
                }

                glPushMatrix();
                glTranslated(middle, y - (fontHeight + 2), 0);
                glScaled(fontScale, fontScale, 1);
                glTranslated(-middle, -(y - (fontHeight + 2)), 0);

                if (nametagSettings.getSetting("Background").isEnabled()) {
                    if (backgroundMode.is("Rect")) {
                        Gui.drawRect2(middle - 3, (float) (y - (fontHeight + 7)), textWidth + 6,
                                (fontHeight / fontScale) + 4, backgroundColor.getRGB());
                    } else {
                        RoundedUtil.drawRound(middle - 3, (float) (y - (fontHeight + 7)), (float) (textWidth + 6),
                                (float) ((fontHeight / fontScale) + 4), 4, backgroundColor);
                    }
                }

                GlStateManager.bindTexture(0);
                RenderUtil.resetColor();
                if (mcfont.isEnabled()) {
                    mc.fontRendererObj.drawStringWithShadow(text.toString(), middle, (float) (y - (fontHeight + 4)), healthColor.getRGB());
                } else {
                    FontUtil.tenacityBoldFont20.drawSmoothString(text.toString(), middle, (float) (y - (fontHeight + 5)), healthColor.getRGB());
                }

                glPopMatrix();

            }
            if (itemHeld.isEnabled() && entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (entityLivingBase.getHeldItem() != null) {

                    double fontScale = .5f;
                    float middle = x + ((right - x) / 2);
                    float textWidth = 0;
                    double fontHeight;
                    String text = entityLivingBase.getHeldItem().getDisplayName();
                    if (mcfont.isEnabled()) {
                        textWidth = mc.fontRendererObj.getStringWidth(text);
                        middle -= (textWidth * fontScale) / 2f;
                        fontHeight = mc.fontRendererObj.FONT_HEIGHT * fontScale;
                    } else {
                        textWidth = (float) FontUtil.tenacityBoldFont20.getStringWidth(text);
                        middle -= (textWidth * fontScale) / 2f;
                        fontHeight = FontUtil.tenacityBoldFont20.getHeight() * fontScale;
                    }

                    glPushMatrix();
                    glTranslated(middle, (bottom + 4), 0);
                    glScaled(fontScale, fontScale, 1);
                    glTranslated(-middle, -(bottom + 4), 0);
                    GlStateManager.bindTexture(0);
                    RenderUtil.resetColor();
                    if (mcfont.isEnabled()) {
                        mc.fontRendererObj.drawStringWithShadow(text, middle, bottom + 4, -1);
                    } else {
                        FontUtil.tenacityBoldFont20.drawSmoothStringWithShadow(text.toString(), middle, bottom + 4, -1);
                    }
                    glPopMatrix();
                }
            }


            if (healthBar.isEnabled() && entity instanceof EntityLivingBase) {
                EntityLivingBase renderingEntity = (EntityLivingBase) entity;
                float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);

                float height = (bottom - y) + 1;
                Gui.drawRect2(right + 2.5f, y - .5f, 2, height + 1, new Color(0, 0, 0, 180).getRGB());
                if (healthBarMode.is("Color")) {
                    GradientUtil.drawGradientTB(right + 3, y + (height - (height * healthValue)), 1, height * healthValue, 1, secondColor, thirdColor);
                } else {
                    Gui.drawRect2(right + 3, y + (height - (height * healthValue)), 1, height * healthValue, healthColor.getRGB());
                }

                if (healthBarText.isEnabled()) {
                    healthValue *= 100;
                    String health = String.valueOf(MathUtils.round(healthValue, 1)).substring(0, healthValue == 100 ? 3 : 2);
                    String text = health + "%";
                    double fontScale = .5;
                    float textX = right + 8;
                    float fontHeight = mcfont.isEnabled() ? (float) (mc.fontRendererObj.FONT_HEIGHT * fontScale) : (float) (FontUtil.tenacityBoldFont20.getHeight() * fontScale);
                    float newHeight = height - fontHeight;
                    float textY = y + (newHeight - (newHeight * (healthValue / 100)));

                    glPushMatrix();
                    glTranslated(textX - 5, textY, 1);
                    glScaled(fontScale, fontScale, 1);
                    glTranslated(-(textX - 5), -textY, 1);
                    if (mcfont.isEnabled()) {
                        mc.fontRendererObj.drawStringWithShadow(text, textX, textY, -1);
                    } else {
                        FontUtil.tenacityBoldFont20.drawSmoothStringWithShadow(text, textX, textY, -1);
                    }
                    glPopMatrix();
                }


            }


            if (boxEsp.isEnabled()) {
                float outlineThickness = .5f;
                RenderUtil.resetColor();
                //top
                GradientUtil.drawGradientLR(x, y, (right - x), 1, 1, firstColor, secondColor);
                //left
                GradientUtil.drawGradientTB(x, y, 1, bottom - y, 1, firstColor, fourthColor);
                //bottom
                GradientUtil.drawGradientLR(x, bottom, right - x, 1, 1, fourthColor, thirdColor);
                //right
                GradientUtil.drawGradientTB(right, y, 1, (bottom - y) + 1, 1, secondColor, thirdColor);

                //Outline

                //top
                Gui.drawRect2(x - .5f, y - outlineThickness, (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x - outlineThickness, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x - .5f, (bottom + 1), (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right + 1, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());


                //top
                Gui.drawRect2(x + 1, y + 1, (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x + 1, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x + 1, (bottom - outlineThickness), (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right - outlineThickness, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());

            }

        }
    };

    private boolean shouldRender(Entity entity) {
        if (entity.isDead || entity.isInvisible()) {
            return false;
        }
        if (validEntities.getSetting("Players").isEnabled() && entity instanceof EntityPlayer) {
            if (entity == mc.thePlayer) {
                return mc.gameSettings.thirdPersonView != 0;
            }
            return true;
        }
        if (validEntities.getSetting("Animals").isEnabled() && entity instanceof EntityAnimal) {
            return true;
        }

        return validEntities.getSetting("mobs").isEnabled() && entity instanceof EntityMob;
    }


}
