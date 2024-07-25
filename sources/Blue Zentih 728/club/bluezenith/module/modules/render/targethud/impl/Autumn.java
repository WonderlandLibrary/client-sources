package club.bluezenith.module.modules.render.targethud.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.targethud.TargetHUD;
import club.bluezenith.module.modules.render.targethud.TargetHudMode;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

import static club.bluezenith.util.math.MathUtil.round;
import static club.bluezenith.util.render.RenderUtil.*;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

public class Autumn implements TargetHudMode {

    private float animationProgress = 0.5F;
    private boolean doDisappear; //false - animation played forwards, true - backwards

    private float healthbarWidth, prevHealthbarWidth, health;
    private EntityPlayer lastHitPlayer;

    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        glPushMatrix();

        if(doDisappear) {
            animationProgress = RenderUtil.animate(0.2F, animationProgress, 0.12F);
        } else animationProgress = RenderUtil.animate(1F, animationProgress, 0.07F);

        final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());

        final boolean reset = lastHitPlayer != target;

        if(reset) {
            lastHitPlayer = target;
            health = target.getHealth();
        }

        final FontRenderer font = mc.fontRendererObj;

        if(info == null) {
            glPopMatrix();
            return;
        }

        final String ign = info.getGameProfile().getName();

        final float ignWidth = font.getStringWidthF(ign);
        final float rectWidth = max(140, 10 + ignWidth);
        final float rectWidthHalf = rectWidth/2F;
        final float healthbarHeight = 1.5F;
        float maxAbsorptionHearts = 0;
        for (PotionEffect potionEffect : target.getActivePotionEffects()) {
            if(potionEffect.getEffectName().equals("potion.absorption") && target.getAbsorptionAmount() > 0) {
                maxAbsorptionHearts += (potionEffect.getAmplifier() + 1.0F) * 4.0F;
            }
        }
        final float progress = 1 - ((target.getMaxHealth() + maxAbsorptionHearts) - (target.getHealth() + target.getAbsorptionAmount())) / (target.getMaxHealth() + maxAbsorptionHearts);

        //if switched targets, skip animation to make the targethud more responsive
        final float animTarget = (rectWidth - 1.5F) * progress;
        healthbarWidth = reset ? animTarget : max(animate(animTarget, healthbarWidth, 0.2f), 1.5F);

        final float diff = abs(healthbarWidth - animTarget);

        if(diff < 0.1 || reset || (target.getHealth() <= 0 && diff <= 1.5F)) {
            prevHealthbarWidth = reset ? healthbarWidth : max(animate(healthbarWidth, prevHealthbarWidth, 0.3F), 1.5F);
        }

        health = (target.getMaxHealth() + maxAbsorptionHearts) * (healthbarWidth/(rectWidth - 1.5F));
        targetHUD.setWidth(rectWidth);
        final float height = 32.5F;
        targetHUD.setHeight(height);

        scaleFromCenter(rectWidth, height, animationProgress);

        rect(0, 0, rectWidth, height, new Color(0, 0, 0, 160));
        rect(1.5F, height - 2 - healthbarHeight, rectWidth - 1.5F, height - 2, new Color(0, 0, 0, 170));

        final int color = ColorUtil.getColorForHealth(target.getMaxHealth(), target.getHealth());

        glEnable(GL_SCISSOR_TEST);
        crop(targetHUD.getX() - 2, targetHUD.getY() - 2, targetHUD.getX() + rectWidth - 2, targetHUD.getY() + height + 2);
        rect(1.5F, height - 2 - healthbarHeight, prevHealthbarWidth, height - 2, darken(new Color(color), 90));
        rect(1.5F, height - 2 - healthbarHeight, healthbarWidth, height - 2, color);
        glDisable(GL_SCISSOR_TEST);

        font.drawString(ign, rectWidthHalf - ignWidth/2f, 2.5F, -1, true);

        final double roundedHealth = round(health, 1);
        final float healthStringWidth = font.getStringWidthF("❤ " + roundedHealth)/2f;
        final float heartWidth = font.getStringWidthF("❤");
        font.drawString("❤", (rectWidthHalf - healthStringWidth) - 1f, 4.5F + font.FONT_HEIGHT, new Color(255,87,93).getRGB(), true);
        font.drawString(valueOf(roundedHealth), rectWidthHalf - healthStringWidth + heartWidth + 1f, 5F + font.FONT_HEIGHT, -1, true);
        glPopMatrix();
    }

    @Override
    public boolean translateToPos() {
        return true;
    }

    @Override
    public void stopDisappearing() {
        doDisappear = false;
    }

    @Override
    public void startDisappearing() {
        doDisappear = true;
    }

    @Override
    public boolean doneDisappearing() {
        return animationProgress <= 0.21F;
    }

    @Override
    public Value<?>[] addSettings() {
        return new Value<?>[] {
            new BooleanValue("test", false).setIndex(1)
        };
    }
}
