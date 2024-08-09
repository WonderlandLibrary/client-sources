/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.impl.Particle;
import fun.ellant.ui.display.impl.ParticleManager;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.Stencil;
import fun.ellant.utils.render.font.Fonts;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

public class TargetInfoRenderer1488
        implements ElementRenderer {
    private final StopWatch stopWatch = new StopWatch();
    private final Dragging drag;
    private LivingEntity entity = null;
    private final ParticleManager particleManager = new ParticleManager();
    private boolean allow;
    private final Animation animation = new EaseBackIn(400, 1.0, 1.0f);
    private float healthAnimation = 0.0f;
    private float absorptionAnimation = 0.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        this.entity = this.getTarget(this.entity);
        float f = 6.0f;
        boolean bl = !this.allow || this.stopWatch.isReached(1000L);
        this.animation.setDuration(bl ? 400 : 300);
        this.animation.setDirection(bl ? Direction.BACKWARDS : Direction.FORWARDS);
        if (this.animation.getOutput() == 0.0) {
            this.entity = null;
        }
        if (this.entity != null) {
            String string;
            String string2 = this.entity.getName().getString();
            float f2 = this.drag.getX();
            float f3 = this.drag.getY();
            float f4 = 34.0f;
            float f5 = 5.0f;
            float f6 = 114.666664f;
            float f7 = 39.333332f;
            this.drag.setWidth(f6);
            this.drag.setHeight(f7);
            float f8 = 1.5f;
            Score score = TargetInfoRenderer.mc.world.getScoreboard().getOrCreateScore(this.entity.getScoreboardName(), TargetInfoRenderer.mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
            float hp = entity.getHealth();
            float maxHp = entity.getMaxHealth();
            String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();

            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                    && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20;
            }
            healthAnimation = MathUtil.fast(healthAnimation, MathHelper.clamp(hp / maxHp, 0, 1), 10);
            absorptionAnimation = MathUtil.fast(absorptionAnimation, MathHelper.clamp(entity.getAbsorptionAmount() / maxHp, 0, 1), 10);


            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                    && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20;
            }
            float f11 = (float)this.animation.getOutput();
            float f12 = (1.0f - f11) / 2.0f;
            float f13 = f2 + f6 * f12;
            float f14 = f3 + f7 * f12;
            float f15 = f6 * f11;
            float f16 = f7 * f11;
            int n = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
            GlStateManager.pushMatrix();
            Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
            TargetInfoRenderer.sizeAnimation(f2 + f6 / 2.0f, f3 + f7 / 2.0f, this.animation.getOutput());
            DisplayUtils.drawShadow(f2, f3, 1.0f, 1.0f, 11, Color.BLACK.getRGB());
            this.drawStyledRect(f2, f3, f6, f7, f, 255);
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(f2 + 2.5f, f3 + 2.5f, f4, f4, 6.0f, style.getSecondColor().getRGB());
            Stencil.readStencilBuffer(1);
            this.drawTargetHead(this.entity, f2 + 2.5f, f3 + 2.5f, f4, f4);
            Stencil.uninitStencilBuffer();
            Scissor.push();
            Scissor.setFromComponentCoordinates(f13, f14, f15 - 6.0f, f16);
            Fonts.sfui.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), f2 + 28.0f + f5 + f5, f3 + f5 + 1.0f, ColorUtils.rgb(255, 255, 255), 8.0f);
            Fonts.sfbold.drawText(eventDisplay.getMatrixStack(), "HP: " + (int) hp, f2 + f4 + f5 + f5,
                    f3 - 5f + f5 + 7 + f5 + f5, HUD.getColor(0), 7);            Scissor.unset();
            Scissor.pop();
            Vector4i vector4i = new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB());
            DisplayUtils.drawRoundedRect(f2 + 30.0f + f5 + f5, f3 + f7 - f5 * 2.0f - 3.0f, f6 - 46.0f, 8.0f, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), ColorUtils.rgb(32, 32, 32));
            this.drawItemStack(f2 + 38.9f, f3 - 16.3f, 15.0f);
            DisplayUtils.drawRoundedRect(f2 + 30.0f + f5 + f5, f3 + f7 - f5 * 2.0f - 3.0f, (f6 - 46.0f) * this.healthAnimation, 8.0f, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), vector4i);
            GlStateManager.popMatrix();
        }
    }

    private LivingEntity getTarget(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = Ellant.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity livingEntity3 = livingEntity;
        if (livingEntity2 != null) {
            this.stopWatch.reset();
            this.allow = true;
            livingEntity3 = livingEntity2;
        } else if (TargetInfoRenderer.mc.currentScreen instanceof ChatScreen) {
            this.stopWatch.reset();
            this.allow = true;
            livingEntity3 = TargetInfoRenderer.mc.player;
        } else {
            this.allow = false;
        }
        return livingEntity3;
    }

    public void drawTargetHead(LivingEntity livingEntity, float f, float f2, float f3, float f4) {
        if (livingEntity != null) {
            EntityRenderer<LivingEntity> entityRenderer = (EntityRenderer<LivingEntity>) mc.getRenderManager().getRenderer(livingEntity);
            this.drawFace(entityRenderer.getEntityTexture(livingEntity), f, f2, 8.0f, 8.0f, 8.0f, 8.0f, f3, f4, 64.0f, 64.0f, livingEntity);
        }
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }

    public void drawFace(ResourceLocation resourceLocation, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, LivingEntity livingEntity) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        mc.getTextureManager().bindTexture(resourceLocation);
        this.particleManager.render();
        float f11 = ((float)livingEntity.hurtTime - (livingEntity.hurtTime != 0 ? TargetInfoRenderer.mc.timer.renderPartialTicks : 0.0f)) / 10.0f;
        GL11.glColor4f(1.0f, 1.0f - f11, 1.0f - f11, 1.0f);
        AbstractGui.drawScaledCustomSizeModalRect(f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5, int n) {
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(0, 0, 0, n));
    }

    private void spawnParticles(LivingEntity livingEntity, float f, float f2, float f3) {
        int n = 0;
        while ((float)n < 1.0E-25f) {
            float f4 = (float)(Math.random() * 2.0 - 1.0) * 1.5f;
            float f5 = (float)(Math.random() * 2.0 - 1.0) * 1.5f;
            float f6 = 0.5f;
            int n2 = ColorUtils.getColor(90);
            ResourceLocation star = new ResourceLocation("expensive/images/star.png");
            float f7 = (float)(Math.random() - 0.5) * f3;
            float f8 = (float)(Math.random() - 0.5) * f3;
            Particle particle = new Particle(f + f3 / 2.0f + f7, f2 + f3 / 2.0f + f8, f4, f5, f6, star, 60, n2);
            particle.setFadeColor(n2);
            particle.setSliding(true);
            this.particleManager.addParticle(particle);
            n -= 4963;
            n += 4964;
        }
    }

    private void drawItemStack(float f, float f2, float f3) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(Arrays.asList(this.entity.getHeldItemOffhand()));
        this.entity.getArmorInventoryList().forEach(arrayList::add);
        Objects.requireNonNull(arrayList);
        arrayList.removeIf(itemStack -> itemStack.getItem() instanceof AirItem);
        Collections.reverse(arrayList);
        AtomicReference<Float> atomicReference = new AtomicReference<Float>(Float.valueOf(f));
        arrayList.stream().filter(itemStack -> {
            boolean bl = !itemStack.isEmpty();
            return bl;
        }).forEach(itemStack -> TargetInfoRenderer1488.drawItemStack(itemStack, atomicReference.getAndAccumulate(Float.valueOf(f3), Float::sum).floatValue(), f2, true, true, 0.9f));
    }

    public static void drawItemStack(ItemStack itemStack, float f, float f2, boolean bl, boolean bl2, float f3) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(f, f2, 0.0f);
        if (bl2) {
            GL11.glScaled(f3, f3, f3);
        }
        mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, 0, 0);
        if (bl) {
            mc.getItemRenderer().renderItemOverlays(TargetInfoRenderer1488.mc.fontRenderer, itemStack, 0, 0);
        }
        RenderSystem.popMatrix();
    }

    public TargetInfoRenderer1488(Dragging dragging) {
        this.drag = dragging;
    }


}

