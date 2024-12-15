package com.alan.clients.module.impl.render.targetinfo;

import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.render.TargetInfo;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.value.Mode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;
import static com.alan.clients.util.animation.Easing.*;

public final class NovolineTargetInfo extends Mode<TargetInfo> {
    private TargetInfo targetInfo;
    private final Animation healthAnimation = new Animation(EASE_OUT_SINE, 500);
    private final Animation widthAnimation = new Animation(EASE_IN_OUT_SINE, 300);

    private Entity previousTarget = null;
    private double targetWidth = 0;

    public NovolineTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    @EventLink
    private final Listener<Render2DEvent> render2DEventListener = event -> {
        if (this.targetInfo == null) {
            this.targetInfo = this.getModule(TargetInfo.class);
        }

        Entity target = this.targetInfo.target;
        boolean out = (!this.targetInfo.inWorld || this.targetInfo.stopwatch.finished(1000));

        if (target == null || out) {
            // Reset animations when there's no targets
            healthAnimation.reset();
            widthAnimation.reset();
            previousTarget = null;
            return;
        }

        double x = this.targetInfo.position.x;
        double y = this.targetInfo.position.y;

        double health = Math.min(!this.targetInfo.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) target).getHealth(), 1),
                ((AbstractClientPlayer) target).getMaxHealth());
        double maxHealth = ((AbstractClientPlayer) target).getMaxHealth();
        double healthPer = (health / maxHealth) * 100;

        String normalName = target.getCommandSenderName();
        String targetName = IRCInfoComponent.formatNick(normalName, normalName);
        double nameWidth = mc.fontRendererObj.width(targetName);

        // Calculate the target width based on name width
        double baseWidth = 74;
        double targetWidth = baseWidth + nameWidth;

        // If the target has changed, reset the width animation
        if (previousTarget != target) {
            widthAnimation.reset();
            widthAnimation.setValue(this.targetWidth); // Start from the current width
            widthAnimation.run(targetWidth); // Animate to the new target width
            previousTarget = target;
        } else {
            widthAnimation.run(targetWidth); // Continue animating to the target width
        }

        // Use the animated width value
        double animatedWidth = widthAnimation.getValue();

        double height = 42;

        // Draw background rectangle
        RenderUtil.rectangle(x, y, animatedWidth, height, new Color(40, 40, 40, 255));

        // Draw target name
        mc.fontRendererObj.drawWithShadow(targetName, x + 44, y + 10, Color.WHITE.getRGB());

        // Draw health bar background
        double barWidth = 26 + nameWidth;
        RenderUtil.rectangle(x + 44, y + 22, barWidth, 11, new Color(21, 21, 21, 150));

        // Update health animation
        double targetHealthBarWidth = barWidth * (health / maxHealth);
        healthAnimation.run(targetHealthBarWidth);

        // Draw the back health bar (animated)
        double animatedHealthBarWidth = healthAnimation.getValue();
        RenderUtil.rectangle(x + 44, y + 22, animatedHealthBarWidth, 11, ColorUtil.darker(getTheme().getSecondColor(), 0.5f));

        // Draw the front health bar (static)
        double healthBarWidth = targetHealthBarWidth;
        RenderUtil.rectangle(x + 44, y + 22, healthBarWidth, 11, getTheme().getFirstColor());

        // Draw health percentage text
        String healthText = String.format("%.1f%%", healthPer);
        double healthTextWidth = mc.fontRendererObj.width(healthText);
        mc.fontRendererObj.drawWithShadow(healthText, x + 44 + barWidth / 2 - healthTextWidth / 2, y + 24.5, Color.WHITE.getRGB());

        // Apply bloom if shaders are enabled
        if (shadersEnabled()) {
            getLayer(BLOOM).add(() -> {
                // Apply bloom to the front health bar
                RenderUtil.rectangle(x + 44, y + 22, healthBarWidth, 11, getTheme().getFirstColor());
            });
        }

        // Render player head if target is a player
        if (target instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) target;
            this.renderTargetHead(abstractClientPlayer, x + 1, y + 1, 40); // Adjust size and position as needed
        }

        // Store the current animated width for next frame
        this.targetWidth = animatedWidth;
    };

    private void renderTargetHead(final AbstractClientPlayer abstractClientPlayer, final double x, final double y, final double size) {
        if (this.targetInfo == null) {
            this.targetInfo = this.getModule(TargetInfo.class);
        }

        Entity target = this.targetInfo.target;
        boolean out = (!this.targetInfo.inWorld || this.targetInfo.stopwatch.finished(1000));

        if (target == null || out) return;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        GlStateManager.enableTexture2D();

        final ResourceLocation resourceLocation = this.targetInfo.inWorld && abstractClientPlayer.getHealth() > 0
                ? abstractClientPlayer.getLocationSkin() : RenderSkeleton.getEntityTexture();

        mc.getTextureManager().bindTexture(resourceLocation);

        // Draw the base face
        Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 8, 8, 8, 8, (int) size, (int) size, 64, 64);

        Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 40, 8, 8, 8, (int) size, (int) size, 64, 64);

        GlStateManager.disableBlend();

    }

    private Color getThemeColor() {
        return getTheme().getFirstColor();
    }

    private boolean shadersEnabled() {
        return true;
    }
}
