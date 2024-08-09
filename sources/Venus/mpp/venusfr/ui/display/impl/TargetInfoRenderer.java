/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;
import mpp.venusfr.utils.animations.impl.EaseBackIn;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.AnimationMath;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class TargetInfoRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private final Animation animation = new EaseBackIn(400, 1.0, 1.0f);
    private float health = 0.0f;
    private final Animation targetHudAnimation = new EaseBackIn(300, 1.0, 1.0f);
    private LivingEntity target = null;
    private MatrixStack stack = new MatrixStack();

    @Override
    public void render(EventDisplay eventDisplay) {
        this.target = this.getTarget(this.target);
        this.targetHudAnimation.setDuration(300);
        double d = this.targetHudAnimation.getOutput();
        if (d == 0.0) {
            this.target = null;
        }
        if (this.target == null) {
            return;
        }
        String string = this.target.getName().getString();
        String string2 = string.substring(0, Math.min(string.length(), 10));
        float f = Fonts.sfui.getWidth(string2, 6.5f);
        float f2 = this.dragging.getX();
        float f3 = this.dragging.getY();
        float f4 = Math.max(f + 50.0f, 130.0f);
        float f5 = 35.0f;
        this.health = AnimationMath.fast(this.health, this.target.getHealth() / this.target.getMaxHealth(), 5.0f);
        this.health = MathHelper.clamp(this.health, 0.0f, 1.0f);
        GlStateManager.pushMatrix();
        AnimationMath.sizeAnimation(f2 + f4 / 2.0f, f3 + 17.5f, d);
        DisplayUtils.drawShadow(f2, f3, f4, 35.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f2, f3, f4, 35.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        Vector4f vector4f = new Vector4f(3.0f, 3.0f, 3.0f, 3.0f);
        this.drawTargetHead(this.target, f2 + 4.5f, f3 + 4.5f, 26.0f, 26.0f);
        DisplayUtils.drawCircle(f2 + f4 - 17.0f, f3 + 17.5f, 0.0f, 360.0f, 11.5f, 4.0f, false, ColorUtils.rgba(25, 25, 25, 255));
        DisplayUtils.drawCircle(f2 + f4 - 17.0f, f3 + 17.5f, 0.0f, this.health * 360.0f + 1.0f, 11.5f, 4.0f, false, ColorUtils.getColor(0));
        Fonts.sfui.drawText(this.stack, string2, f2 + 33.0f, f3 + 8.5f, -1, 6.5f);
        this.drawItemStack(f2 + 34.0f, f3 + 22.0f - 4.0f, 11.0f);
        String string3 = "" + (int)MathUtil.round(this.health * 20.0f + this.target.getAbsorptionAmount(), 0.5);
        Fonts.sfui.drawCenteredText(this.stack, ITextComponent.getTextComponentOrEmpty(string3), f2 + f4 - 17.0f, f3 + 17.5f - 2.2f, 6.5f);
        GlStateManager.popMatrix();
        this.dragging.setWidth(f4);
        this.dragging.setHeight(35.0f);
    }

    private void drawItemStack(float f, float f2, float f3) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(Arrays.asList(this.target.getHeldItemMainhand(), this.target.getHeldItemOffhand()));
        arrayList.addAll((Collection)this.target.getArmorInventoryList());
        AtomicReference<Float> atomicReference = new AtomicReference<Float>(Float.valueOf(f));
        arrayList.stream().filter(TargetInfoRenderer::lambda$drawItemStack$0).forEach(arg_0 -> TargetInfoRenderer.lambda$drawItemStack$1(atomicReference, f3, f2, arg_0));
    }

    public static void drawItemStack1(ItemStack itemStack, float f, float f2, boolean bl, boolean bl2, float f3) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(f, f2, 0.0f);
        if (bl2) {
            GL11.glScaled(f3, f3, f3);
        }
        mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, 0, 0);
        if (bl) {
            mc.getItemRenderer().renderItemOverlays(TargetInfoRenderer.mc.fontRenderer, itemStack, 0, 0);
        }
        RenderSystem.popMatrix();
    }

    private LivingEntity getTarget(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = livingEntity;
        if (venusfr.getInstance().getFunctionRegistry().getKillAura().getTarget() != null) {
            livingEntity2 = venusfr.getInstance().getFunctionRegistry().getKillAura().getTarget();
            this.targetHudAnimation.setDirection(Direction.FORWARDS);
        } else if (TargetInfoRenderer.mc.currentScreen instanceof ChatScreen) {
            livingEntity2 = TargetInfoRenderer.mc.player;
            this.targetHudAnimation.setDirection(Direction.FORWARDS);
        } else {
            this.targetHudAnimation.setDirection(Direction.BACKWARDS);
        }
        return livingEntity2;
    }

    public void drawTargetHead(LivingEntity livingEntity, float f, float f2, float f3, float f4) {
        if (livingEntity != null) {
            EntityRenderer<LivingEntity> entityRenderer = mc.getRenderManager().getRenderer(livingEntity);
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
        float f11 = ((float)livingEntity.hurtTime - (livingEntity.hurtTime != 0 ? TargetInfoRenderer.mc.timer.renderPartialTicks : 0.0f)) / 10.0f;
        GL11.glColor4f(1.0f, 1.0f - f11, 1.0f - f11, 1.0f);
        AbstractGui.drawScaledCustomSizeModalRect(f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5, int n) {
        Style style = venusfr.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, n));
    }

    public TargetInfoRenderer(Dragging dragging) {
        this.dragging = dragging;
    }

    private static void lambda$drawItemStack$1(AtomicReference atomicReference, float f, float f2, ItemStack itemStack) {
        TargetInfoRenderer.drawItemStack1(itemStack, atomicReference.getAndAccumulate(Float.valueOf(f), Float::sum).floatValue(), f2, true, true, 0.6f);
    }

    private static boolean lambda$drawItemStack$0(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }
}

