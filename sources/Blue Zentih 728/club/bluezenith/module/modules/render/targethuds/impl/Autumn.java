package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

import static club.bluezenith.util.math.MathUtil.round;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static net.minecraft.client.renderer.GlStateManager.popMatrix;
import static net.minecraft.client.renderer.GlStateManager.pushMatrix;
import static org.lwjgl.opengl.GL11.*;

public class Autumn implements ITargetHUD {

    float healthbarWidth;
    float prevHealthbarWidth;
    float health;
    EntityPlayer lastHitPlayer;

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        pushMatrix();
        final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());

        final boolean reset = lastHitPlayer != target;

        if(reset) {
            health = target.getHealth();
            lastHitPlayer = target;
        }

        final FontRenderer font = mc.fontRendererObj;
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
        targetHUD.width = rectWidth;
        final float height = targetHUD.height = 32.5F;
        rect(0, 0, rectWidth, height, new Color(0, 0, 0, 160));
        rect(1.5F, height - 2 - healthbarHeight, rectWidth - 1.5F, height - 2, new Color(0, 0, 0, 170));

        final int color = targetHUD.getColorForHealth(target.getMaxHealth(), target.getHealth());

        glEnable(GL_SCISSOR_TEST);
        crop(targetHUD.translateX - 2, targetHUD.translateY - 2, targetHUD.translateX + rectWidth - 2, targetHUD.translateY + height + 2);
        rect(1.5F, height - 2 - healthbarHeight, prevHealthbarWidth, height - 2, darken(new Color(color), 90));
        rect(1.5F, height - 2 - healthbarHeight, healthbarWidth, height - 2, color);
        glDisable(GL_SCISSOR_TEST);

        font.drawString(ign, rectWidthHalf - ignWidth/2f, 2.5F, -1, true);

        final double roundedHealth = round(health, 1);
        final float healthStringWidth = font.getStringWidthF("❤ " + roundedHealth)/2f;
        final float heartWidth = font.getStringWidthF("❤");
        font.drawString("❤", (rectWidthHalf - healthStringWidth) - 1f, 4.5F + font.FONT_HEIGHT, new Color(255,87,93).getRGB(), true);
        font.drawString(valueOf(roundedHealth), rectWidthHalf - healthStringWidth + heartWidth + 1f, 5F + font.FONT_HEIGHT, -1, true);
        popMatrix();
    }

    @Override
    public ITargetHUD createInstance() {
        return new Autumn();
    }

    private static final ITargetHUD autumn = new Autumn();

    public static ITargetHUD getInstance() {
        return autumn;
    }
}
