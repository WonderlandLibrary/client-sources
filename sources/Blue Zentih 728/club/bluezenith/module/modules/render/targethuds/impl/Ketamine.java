package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.render.RenderUtil;
import fr.lavache.anime.AnimateTarget;
import fr.lavache.anime.Easing;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.Color;

import static club.bluezenith.util.math.MathUtil.round;
import static club.bluezenith.util.font.FontUtil.fluxIcons;
import static club.bluezenith.util.render.RenderUtil.hollowRect;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.glColor4f;

public class Ketamine implements ITargetHUD {

    private float targetPrevHealth, targetPrevArmor = 0;
    private final AnimateTarget armorAnim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final AnimateTarget anim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);

    private final static Color HEALTH_BAR_PRIMARY = new Color(177,39,99,255);
    private final static Color HEALTH_BAR_BACKGROUND = RenderUtil.darken(HEALTH_BAR_PRIMARY, 35);
    private final static Color ARMOR_BAR_PRIMARY = new Color(20,157,236,255);
    private final static Color ARMOR_BAR_BACKGROUND = RenderUtil.darken(ARMOR_BAR_PRIMARY, 35);
    private final static Color BACKGROUND_PRIMARY = new Color(38, 38, 38);
    private final static Color BACKGROUND_DARKENED = RenderUtil.darken(BACKGROUND_PRIMARY, 10);

    private final static int BACKGROUND_OUTLINE = new Color(20, 20, 20).getRGB();
    private final static int HEALTH_TEXT = new Color(181,38,82,255).getRGB();

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        pushMatrix();
        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
        if (info == null) {
            popMatrix();
            return;
        }
        final float rectWidth = max(120, 45 + mc.fontRendererObj.getStringWidthF(info.getGameProfile().getName()));
        targetHUD.width = rectWidth - 3;
        targetHUD.height = 36.5F;
        rect(0, 0, rectWidth - 2, 36.5F, BACKGROUND_PRIMARY);
        hollowRect(0, 0, rectWidth - 2, 36.5F, 2.5f, BACKGROUND_OUTLINE);
        glColor4f(1F, 1F - (target.hurtTime / 15f), 1F - (target.hurtTime / 15f), 1F);
        mc.getTextureManager().bindTexture(info.getLocationSkin());
        drawScaledCustomSizeModalRect(2.5F, 2.5F, 8F, 8F, 8, 8, 30, 31, 64F, 64F);

        mc.fontRendererObj.drawString(info.getGameProfile().getName(), 37F, 3F, -1);

        final int fontHeight = mc.fontRendererObj.FONT_HEIGHT;
        final float barWidthStart = 36.5F, barEnd = rectWidth - 8;
        final float barHeight = 5, HPBarHeightStart = 3.5F + fontHeight, HPBarHeightEnd = HPBarHeightStart + barHeight;
        final float barWidth = barEnd - barWidthStart;
        healthBar:
        {
            final float progress = 1 - (target.getMaxHealth() - target.getHealth()) / target.getMaxHealth();
            final float translation = barWidth * progress;
            final float difference = max(abs(targetPrevHealth - translation), 1);
            final boolean back = targetPrevHealth > translation;
            anim.setSpeed((back ? 10 : 20) / difference);
            final float animWidth = anim.setMax(barWidth).setEase(Easing.QUAD_OUT).setTarget(translation).update().getValue();
            rect(barWidthStart, HPBarHeightStart, barEnd, HPBarHeightEnd, HEALTH_BAR_BACKGROUND);
            rect(barWidthStart, HPBarHeightStart, barWidthStart + animWidth, HPBarHeightEnd, HEALTH_BAR_PRIMARY);
            targetPrevHealth = translation;
        }
        final float outlineWidth = 1.5F;
        final float barSpacing = 0.5F;
        final float armorBarStart = HPBarHeightEnd + barSpacing, armorBarEnd = armorBarStart + barHeight;
        rect(barWidthStart, armorBarStart - barSpacing, barEnd, armorBarStart +barSpacing, BACKGROUND_DARKENED);
        armorBar:
        {
            final float progress = 1 - (20 - targetHUD.getArmorPointsForPlayer(target)) / 20F;
            final float translation = barWidth * progress;
            final float difference = max(abs(targetPrevArmor - translation), 1);
            final boolean back = targetPrevArmor > translation;
            armorAnim.setSpeed((back ? 10 : 20) / difference);
            final float animWidth = armorAnim.setMax(barWidth).setEase(Easing.QUAD_OUT).setTarget(translation).update().getValue();
            rect(barWidthStart - 0.1F, armorBarStart, barEnd, armorBarEnd, ARMOR_BAR_BACKGROUND);
            rect(barWidthStart - 0.1F, armorBarStart, barWidthStart - 0.1F + animWidth, armorBarEnd, ARMOR_BAR_PRIMARY);
            targetPrevArmor = translation;
        }
        hollowRect(barWidthStart, HPBarHeightStart, barEnd, armorBarEnd, outlineWidth, BACKGROUND_DARKENED.getRGB());
        pushMatrix();
        final float downscale = 1F;
        scale(downscale, downscale, downscale);
        int the = mc.fontRendererObj.drawString(valueOf(round(target.getHealth(), 1)), 37F/downscale, (armorBarEnd + 3F)/downscale, -1);
        mc.fontRendererObj.drawString("❤", (the + 2F)/downscale, (armorBarEnd + 2.5F)/downscale, HEALTH_TEXT);
        final float upscale = 1.22F;
        pushMatrix();
        scale(upscale, upscale, upscale);
        final float shield = fluxIcons.getStringWidthF("s");
        final String points = valueOf(targetHUD.getArmorPointsForPlayer(target));
        final float end = rectWidth - shield*2 - 2.5F;
        fluxIcons.drawString("s", end/upscale, (armorBarEnd + 3.5f)/upscale, ARMOR_BAR_PRIMARY.getRGB());
        popMatrix();
        mc.fontRendererObj.drawString(points, (end - mc.fontRendererObj.getStringWidthF(points)),  (armorBarEnd + 3), -1);
        popMatrix();
        popMatrix();
    }


    @Override
    public ITargetHUD createInstance() {
        return new Ketamine();
    }

    private Ketamine() {}
    private final static Ketamine ketamineMode = new Ketamine();
    public static ITargetHUD getInstance() {
        return ketamineMode;
    }
}
