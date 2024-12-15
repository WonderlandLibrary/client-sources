package com.alan.clients.module.impl.render.targetinfo;

import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.component.impl.render.ParticleComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.impl.render.TargetInfo;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.localization.Localization;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.render.StencilUtil;
import com.alan.clients.util.render.particle.Particle;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
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

public class BMSTargetInfo extends Mode<TargetInfo> {

    private final Font productSansLight = Fonts.MAIN.get(22, Weight.LIGHT);
    private final Font productSansMedium = Fonts.MAIN.get(22, Weight.LIGHT);
    //private final Font minecraft = FontManager.getMinecraft();

    private TargetInfo targetInfoModule;
    private int EDGE_OFFSET = 4, PADDING = 4, INDENT = 4;

    private Animation openingAnimation = new Animation(EASE_OUT_ELASTIC, 500);
    private Animation healthAnimation = new Animation(EASE_OUT_SINE, 500);

    public BMSTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (this.targetInfoModule == null) {
            this.targetInfoModule = this.getModule(TargetInfo.class);
        }

        Entity target = this.targetInfoModule.target;
        if (target == null) return;

        boolean out = (!this.targetInfoModule.inWorld || this.targetInfoModule.stopwatch.finished(1000));
        openingAnimation.setDuration(out ? 400 : 850);
        openingAnimation.setEasing(out ? EASE_IN_BACK : EASE_OUT_ELASTIC);
        openingAnimation.run(out ? 0 : 1);

        if (openingAnimation.getValue() <= 0) return;

        String normalName = target.getCommandSenderName();
        String name = IRCInfoComponent.formatNick(normalName, normalName);

        double x = this.targetInfoModule.position.x;
        double y = this.targetInfoModule.position.y;

        double health = Math.min(!this.targetInfoModule.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) target).getHealth(), 1), ((AbstractClientPlayer) target).getMaxHealth());
        double healthBarWidth = Math.max(0, 100);

        healthAnimation.run((health / ((AbstractClientPlayer) target).getMaxHealth()) * healthBarWidth);
        healthAnimation.setEasing(EASE_OUT_QUINT);
        healthAnimation.setDuration(250);
        double healthRemainingWidth = healthAnimation.getValue();

        double hurtTime = (((AbstractClientPlayer) target).hurtTime == 0 ? 0 :
                ((AbstractClientPlayer) target).hurtTime - mc.timer.renderPartialTicks) * 0;
        int faceScale = 32;
        double healthPercent = (health / ((AbstractClientPlayer) target).getMaxHealth() * 100);
        double finalHealth = Math.round(healthPercent);
        double faceOffset = hurtTime / 2f;
        double width = EDGE_OFFSET + faceScale + EDGE_OFFSET + healthBarWidth + INDENT + EDGE_OFFSET;
        double height = faceScale + EDGE_OFFSET * 2;
        this.targetInfoModule.positionValue.setScale(new Vector2d(width, height));

        double scale = openingAnimation.getValue();

        getLayer(REGULAR, 1).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            // Draw background
            Color background = getTheme().getBackgroundShade();
            Color accent = getTheme().getFirstColor();

            RenderUtil.drawRoundedGradientRect(x, y, width - 4, height, 6, background, background, true);

            // Render name
            productSansMedium.drawWithShadow(name, x - 28 + faceScale + PADDING + productSansLight.width(Localization.get("ui.targethud.name")) + 3, y + EDGE_OFFSET + INDENT, Color.white.getRGB());
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            // Targets face
            RenderUtil.color(ColorUtil.mixColors(Color.RED, Color.WHITE, hurtTime / 9));
            RenderUtil.dropShadow(3, x + EDGE_OFFSET + faceOffset, y + EDGE_OFFSET + faceOffset,
                    faceScale - hurtTime, faceScale - hurtTime, 20, 5);
            renderTargetHead((AbstractClientPlayer) target, x + EDGE_OFFSET + faceOffset, y + EDGE_OFFSET + faceOffset,
                    faceScale - hurtTime);

            // Health background
            RenderUtil.roundedRectangle(x + EDGE_OFFSET + faceScale + PADDING, y + EDGE_OFFSET + faceScale - INDENT - 10,
                    healthBarWidth, 12, 2, Color.darkGray.darker());

            // Health
            RenderUtil.drawRoundedGradientRect(x + EDGE_OFFSET + faceScale + PADDING, y + EDGE_OFFSET + faceScale - INDENT - 10,
                    healthRemainingWidth, 12, 2, ColorUtil.withAlpha(accent, 100), ColorUtil.withAlpha(accent, 100), false);

            productSansLight.drawCentered(finalHealth + "%",
                    x + EDGE_OFFSET + faceScale + PADDING + healthBarWidth + INDENT - 50,
                    y + EDGE_OFFSET + faceScale - INDENT - 8, Color.WHITE.getRGB());
            GlStateManager.popMatrix();
        });

        getLayer(BLUR).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);
            RenderUtil.roundedRectangle(x, y, width - 4.5, height, 6, Color.BLACK);
            GlStateManager.popMatrix();
        });

        getLayer(BLOOM).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            final boolean glow = false;
            final Color outlineColor1 = glow ? this.getTheme().getFirstColor() : getTheme().getDropShadow();
            final Color outlineColor2 = glow ? this.getTheme().getSecondColor() : getTheme().getDropShadow();
            RenderUtil.drawRoundedGradientRect(x + 0.5f, y, width - 4.5, height, 7, outlineColor1, outlineColor2, true);

            GlStateManager.popMatrix();
        });
    };

    private void renderTargetHead(final AbstractClientPlayer abstractClientPlayer, final double x, final double y, final double size) {
        StencilUtil.initStencil();
        StencilUtil.bindWriteStencilBuffer();
        RenderUtil.roundedRectangle(x, y, size, size, 3, this.getTheme().getBackgroundShade());
        StencilUtil.bindReadStencilBuffer(1);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        GlStateManager.enableTexture2D();

        final ResourceLocation resourceLocation = targetInfoModule.inWorld && abstractClientPlayer.getHealth() > 0
                ? abstractClientPlayer.getLocationSkin() : RenderSkeleton.getEntityTexture();

        mc.getTextureManager().bindTexture(resourceLocation);

        Gui.drawScaledCustomSizeModalRect(x, y, 4, 4, 4, 4, size, size, 32, 32);
        GlStateManager.disableBlend();
        StencilUtil.uninitStencilBuffer();
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (this.targetInfoModule == null) return;
        Entity target = this.targetInfoModule.target;

        if (target == null || openingAnimation.getValue() <= 0) return;

        double hurtTime = (((AbstractClientPlayer) target).hurtTime == 0 ? 0 :
                ((AbstractClientPlayer) target).hurtTime - mc.timer.renderPartialTicks) * 0;

        if (hurtTime > 0) {
            for (int i = 0; i < hurtTime * Math.random() / 2; i++) {
                ParticleComponent.add(new Particle(new Vector2f((float) (targetInfoModule.position.x + 20), (float) (targetInfoModule.position.y + 20)),
                        new Vector2f((float) (Math.random() - 0.5) * 1.7f, (float) (Math.random() - 0.5) * 1.7f)));
            }
        }
    };
}