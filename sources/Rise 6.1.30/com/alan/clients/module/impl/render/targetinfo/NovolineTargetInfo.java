package com.alan.clients.module.impl.render.targetinfo;

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
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;
import static com.alan.clients.util.animation.Easing.*;

public final class NovolineTargetInfo extends Mode<TargetInfo> {

    private final Animation openingAnimation = new Animation(EASE_OUT_ELASTIC, 500);
    private final Animation healthAnimation = new Animation(EASE_OUT_SINE, 500);
    private final Animation armorAnimation = new Animation(EASE_OUT_SINE, 500);

    private TargetInfo targetInfo;

    public NovolineTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    @EventLink
    private final Listener<Render2DEvent> render2DEventListener = event -> {
        if (this.targetInfo == null) {
            this.targetInfo = this.getModule(TargetInfo.class);
        }

        boolean out = (!this.targetInfo.inWorld || this.targetInfo.stopwatch.finished(1000));

        openingAnimation.setDuration(out ? 400 : 850);
        openingAnimation.setEasing(out ? EASE_IN_BACK : EASE_OUT_ELASTIC);
        openingAnimation.run(out ? 0 : 1);

        if (openingAnimation.getValue() <= 0) {
            return;
        }

        final double x = this.targetInfo.position.x;
        final double y = this.targetInfo.position.y;
        final double scale = openingAnimation.getValue();

        final int width = 20 + mc.fontRendererObj.width(this.targetInfo.target.getCommandSenderName()) + mc.fontRendererObj.width("Distance: " + Math.round(this.targetInfo.target.getDistanceToEntity(mc.thePlayer)) + "m");
        final int height = 47;

        getLayer(BLUR).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2f) * (1 - scale), (y + height / 2f) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.rectangle(x, y, width, height, new Color(0, 0, 0, 255));

            GlStateManager.popMatrix();
        });

        getLayer(BLOOM).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2f) * (1 - scale), (y + height / 2f) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.rectangle(x, y, width, height, new Color(0, 0, 0, 255));

            GlStateManager.popMatrix();
        });

        getLayer(REGULAR, 1).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2f) * (1 - scale), (y + height / 2f) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.rectangle(x, y, width, height, new Color(0, 0, 0, 105));

            // reset color of the head
            GL11.glColor4f(1, 1, 1, 1);
            this.renderTargetHead((AbstractClientPlayer) this.targetInfo.target, x + 2, y + 2, 32);

            final AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) this.targetInfo.target;

            final String healthString = Double.toString(Math.round(abstractClientPlayer.getHealth()));

            mc.fontRendererObj.drawWithShadow(this.targetInfo.target.getCommandSenderName(), x + 38, y + 4, Color.WHITE.getRGB());

            mc.fontRendererObj.drawWithShadow("Health: " + healthString, x + 38, y + 15, Color.WHITE.getRGB());
            mc.fontRendererObj.drawWithShadow("Distance: " + Math.round(this.targetInfo.target.getDistanceToEntity(mc.thePlayer)) + "m", x + 38, y + 27, Color.WHITE.getRGB());

            final double nameWidth = mc.fontRendererObj.width(this.targetInfo.target.getCommandSenderName());
            final double health = Math.min(!this.targetInfo.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) this.targetInfo.target).getHealth(), 1), ((AbstractClientPlayer) this.targetInfo.target).getMaxHealth());
            final double healthTextWidth = mc.fontRendererObj.width(String.valueOf(health));
            final double healthBarWidth = Math.max(nameWidth + 35 - healthTextWidth, width - 4);

            healthAnimation.run((abstractClientPlayer.getHealth() / abstractClientPlayer.getMaxHealth()) * healthBarWidth);
            healthAnimation.setEasing(EASE_OUT_QUINT);
            healthAnimation.setDuration(250);
            double healthRemainingWidth = healthAnimation.getValue();

            final double armor = Math.min(!this.targetInfo.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) this.targetInfo.target).getTotalArmorValue() * 10, 1), width - 4);

            this.armorAnimation.run(armor);

            RenderUtil.drawRoundedGradientRect(x + 2, y + 37, width - 4, 3, 1, ColorUtil.withAlpha(Color.BLACK, 100), ColorUtil.withAlpha(Color.BLACK, 100), false);
            RenderUtil.drawRoundedGradientRect(x + 2, y + 42, width - 4, 3, 1, ColorUtil.withAlpha(Color.BLACK, 100), ColorUtil.withAlpha(Color.BLACK, 100), false);

            // health bar
            RenderUtil.drawRoundedGradientRect(x + 2, y + 37, healthRemainingWidth, 3, 1, new Color(0, 165, 0), new Color(0, 240, 0), false);
            RenderUtil.drawRoundedGradientRect(x + 2, y + 42, this.armorAnimation.getValue(), 3, 1, new Color(0, 100, 255), new Color(0, 100, 165), false);

            GlStateManager.popMatrix();
        });
    };

    private void renderTargetHead(final AbstractClientPlayer abstractClientPlayer, final double x, final double y, final double size) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        GlStateManager.enableTexture2D();

        final ResourceLocation resourceLocation = this.targetInfo.inWorld && abstractClientPlayer.getHealth() > 0
                ? abstractClientPlayer.getLocationSkin() : RenderSkeleton.getEntityTexture();

        mc.getTextureManager().bindTexture(resourceLocation);

        Gui.drawScaledCustomSizeModalRect(x, y, 4, 4, 4, 4, size, size, 32, 32);
        GlStateManager.disableBlend();
    }

}
