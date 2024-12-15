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
import com.alan.clients.ui.theme.Themes;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.chat.ChatUtil;
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
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;
import static com.alan.clients.util.animation.Easing.*;

public class ModernTargetInfo extends Mode<TargetInfo> {

    private final BooleanValue particles = new BooleanValue("Particles", this, true);

    private final Font productSansLight = Fonts.MAIN.get(22, Weight.LIGHT);
    private final Font productSansMedium = Fonts.MAIN.get(22, Weight.MEDIUM);

    private final ModeValue backgroundMode = new ModeValue("Background Mode", this) {{
        add(new SubMode("Glass"));
        add(new SubMode("Tint"));
        add(new SubMode("Solid"));
        setDefault("Glass");
    }};

    private TargetInfo targetInfoModule;
    private final int EDGE_OFFSET = 8;
    private final int PADDING = 7;
    private final int INDENT = 4;

    private final Animation openingAnimation = new Animation(EASE_OUT_ELASTIC, 500);
    private final Animation healthAnimation = new Animation(EASE_OUT_SINE, 500);

    public ModernTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (this.targetInfoModule == null) {
            this.targetInfoModule = this.getModule(TargetInfo.class);
        }

        getLayer(BLOOM).add(ParticleComponent::bloom);
        getLayer(REGULAR, 1).add(ParticleComponent::render);

        Entity target = this.targetInfoModule.target;
        if (target == null) return;

        boolean out = (!this.targetInfoModule.inWorld || this.targetInfoModule.stopwatch.finished(1000));
        openingAnimation.setDuration(out ? 400 : 850);
        openingAnimation.setEasing(out ? EASE_IN_BACK : EASE_OUT_ELASTIC);
        openingAnimation.run(out ? 0 : 1);

        if (openingAnimation.getValue() <= 0) return;

        // Get the target's name
        String normalName = target.getCommandSenderName();
        String ircName = IRCInfoComponent.formatNick(normalName,normalName);

        // Calculate the winning value and determine the label
        float winningValue = calculateWinning();
        String label = winningValue > 0 ? "Winning:" : "Losing:";

        double x = this.targetInfoModule.position.x;
        double y = this.targetInfoModule.position.y;

        double nameWidth = productSansMedium.width(ircName);
        double labelWidth = productSansLight.width(label);

        double health = Math.min(!this.targetInfoModule.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) target).getHealth(), 1), ((AbstractClientPlayer) target).getMaxHealth());
        double healthTextWidth = productSansMedium.width(String.valueOf(health));
        double healthBarWidth = Math.max(labelWidth + nameWidth + 35 - healthTextWidth, 65);

        healthAnimation.run((health / ((AbstractClientPlayer) target).getMaxHealth()) * healthBarWidth);
        healthAnimation.setEasing(EASE_OUT_QUINT);
        healthAnimation.setDuration(250);
        double healthRemainingWidth = healthAnimation.getValue();

        double hurtTime = (((AbstractClientPlayer) target).hurtTime == 0 ? 0 :
                ((AbstractClientPlayer) target).hurtTime - mc.timer.renderPartialTicks) * 0.5;
        int faceScale = 32;
        double faceOffset = hurtTime / 2f;
        double width = EDGE_OFFSET + faceScale + EDGE_OFFSET + healthBarWidth + INDENT + healthTextWidth + EDGE_OFFSET;
        double height = faceScale + EDGE_OFFSET * 2;
        this.targetInfoModule.positionValue.setScale(new Vector2d(width, height));

        double scale = openingAnimation.getValue();

        getLayer(REGULAR).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            // Draw background
            Color background1 = Themes.getBackgroundShade();
            Color background2 = Themes.getBackgroundShade();
            Color accent1 = getTheme().getFirstColor();
            Color accent2 = getTheme().getSecondColor();

            if (this.backgroundMode.getValue().getName().equals("Tint")) {
                Color theme1 = this.getTheme().getAccentColor(new Vector2d(x, y)), theme2 = this.getTheme().getAccentColor(new Vector2d(x, y + height));
                background1 = new Color(theme1.getRed() / 5, theme1.getGreen() / 5, theme1.getBlue() / 5, 128);
                background2 = new Color(theme2.getRed() / 5, theme2.getGreen() / 5, theme2.getBlue() / 5, 128);
            } else if (this.backgroundMode.getValue().getName().equals("Solid")) {
                Color theme1 = this.getTheme().getFirstColor(), theme2 = this.getTheme().getSecondColor();
                background1 = new Color(theme1.getRed(), theme1.getGreen(), theme1.getBlue(), 128);
                background2 = new Color(theme2.getRed(), theme2.getGreen(), theme2.getBlue(), 128);
                accent1 = new Color(255, 255, 255);
                accent2 = new Color(164, 164, 164);
            }

            RenderUtil.drawRoundedGradientRect(x, y, width - 1, height, 19, background1, background2, true);

            // Render Winning or Losing label
            productSansLight.drawWithShadow(label, x + EDGE_OFFSET + faceScale + PADDING, y + EDGE_OFFSET + INDENT + 2, Color.WHITE.hashCode());

            // Render the target's name next to the label
            productSansMedium.drawWithShadow(ircName, x + EDGE_OFFSET + faceScale + PADDING + labelWidth + 3, y + EDGE_OFFSET + INDENT + 2.5, accent1.hashCode());

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            // Health background
            RenderUtil.drawRoundedGradientRect(x + EDGE_OFFSET + faceScale + PADDING, y + EDGE_OFFSET + faceScale - INDENT - 7,
                    healthBarWidth, 6, 3, ColorUtil.withAlpha(Themes.getBackgroundShade(), (int) (Themes.getBackgroundShade().getAlpha() / 1.7f)), getTheme().getBackgroundShade(), true);

            // Health
            RenderUtil.drawRoundedGradientRect(x + EDGE_OFFSET + faceScale + PADDING, y + EDGE_OFFSET + faceScale - INDENT - 7,
                    healthRemainingWidth, 6, 3, accent2, accent1, true);

            productSansMedium.drawWithShadow(String.valueOf(health),
                    x + EDGE_OFFSET + faceScale + PADDING + healthBarWidth + INDENT,
                    y + EDGE_OFFSET + faceScale - INDENT - 8, accent1.hashCode());

            GlStateManager.popMatrix();
        });

        getLayer(REGULAR, 1).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            // Target's face
            RenderUtil.color(ColorUtil.mixColors(Color.RED, Color.WHITE, hurtTime / 9));
            RenderUtil.dropShadow(3, x + EDGE_OFFSET + faceOffset, y + EDGE_OFFSET + faceOffset,
                    faceScale - hurtTime, faceScale - hurtTime, 20, this.getTheme().getRound() * 2);
            renderTargetHead((AbstractClientPlayer) target, x + EDGE_OFFSET + faceOffset, y + EDGE_OFFSET + faceOffset,
                    faceScale - hurtTime);

            GlStateManager.popMatrix();
        });

        getLayer(BLUR).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);
            RenderUtil.roundedRectangle(x, y, width - 1, height, 19, Color.BLACK);
            GlStateManager.popMatrix();
        });

        getLayer(BLOOM).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(x + 0.5F, y + 0.5F, width - 2, height - 1, 20, getTheme().getDropShadow());

            GlStateManager.popMatrix();
        });
    };

    private float damage(final ItemStack stack, final Entity attacker, final Entity target) {
        float baseDamage = 1.0f;
        if (stack != null) {
            final Item item = stack.getItem();
            int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);

            if (item instanceof ItemSword) {
                baseDamage = ((ItemSword) item).attackDamage;
            } else if (item instanceof ItemTool) {
                baseDamage = ((ItemTool) item).damageVsEntity;
            }
            baseDamage += sharpnessLevel * 1.25f;
        }

        if (attacker instanceof EntityLivingBase livingAttacker) {
            int strengthLevel = livingAttacker.getActivePotionEffect(Potion.damageBoost) != null ? livingAttacker.getActivePotionEffect(Potion.damageBoost).getAmplifier() + 1 : 0;
            baseDamage += strengthLevel * 3.0f;
        }

        float totalDamage = baseDamage;

        if (target instanceof EntityLivingBase livingTarget) {
            int totalProtectionLevel = 0;
            for (int i = 0; i < 4; i++) {
                totalProtectionLevel += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, livingTarget.getCurrentArmor(i));
            }
            totalDamage *= (1 - livingTarget.getTotalArmorValue() * 0.04f);
            totalDamage *= (1 - totalProtectionLevel * 0.04f);

            int resistanceLevel = livingTarget.getActivePotionEffect(Potion.resistance) != null ? livingTarget.getActivePotionEffect(Potion.resistance).getAmplifier() + 1 : 0;
            totalDamage *= (1 - resistanceLevel * 0.2f);
        }
        return totalDamage;
    }

    private float calculateWinning() {
        Entity target = this.targetInfoModule.target;

        if (!(target instanceof AbstractClientPlayer)) {
            return 0;
        }

        float playerHealth = mc.thePlayer.getHealth();
        float targetHealth = ((AbstractClientPlayer) target).getHealth();

        while (playerHealth > 0 && targetHealth > 0) {
            float playerDamage = damage(mc.thePlayer.getHeldItem(), mc.thePlayer, target);
            float targetDamage = damage(((AbstractClientPlayer) target).getHeldItem(), target, mc.thePlayer);

            targetHealth -= playerDamage;
            playerHealth -= targetDamage;
        }

        return playerHealth - targetHealth;
    }

    private void renderTargetHead(final AbstractClientPlayer abstractClientPlayer, final double x, final double y, final double size) {
        StencilUtil.initStencil();
        StencilUtil.bindWriteStencilBuffer();
        RenderUtil.roundedRectangle(x, y, size, size, this.getTheme().getRound() * 2, Themes.getBackgroundShade());
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

        float expand = 0.5f;
        RenderUtil.roundedOutlineRectangle(x - expand, y - expand, size + expand * 2, size + expand * 2, this.getTheme().getRound() * 2, 0.5, ColorUtil.withAlpha(Color.BLACK, 40));
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if (this.targetInfoModule == null) return;
        Entity target = this.targetInfoModule.target;

        if (target == null || openingAnimation.getValue() <= 0 || !this.particles.getValue()) return;

        double hurtTime = (((AbstractClientPlayer) target).hurtTime == 0 ? 0 :
                ((AbstractClientPlayer) target).hurtTime - mc.timer.renderPartialTicks) * 0.5;

        if (hurtTime > 0) {
            for (int i = 0; i < hurtTime * Math.random() / 2; i++) {
                ParticleComponent.add(new Particle(new Vector2f((float) (targetInfoModule.position.x + 20), (float) (targetInfoModule.position.y + 20)),
                        new Vector2f((float) (Math.random() - 0.5) * 1.7f, (float) (Math.random() - 0.5) * 1.7f)));
            }
        }
    };
}
