/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class HitBubble
extends Module {
    public static HitBubble get;
    static final ArrayList<Bubble> bubbles;
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();
    private final ResourceLocation BUBBLE_TEXTURE = new ResourceLocation("vegaline/modules/hitbubble/bubble.png");

    public HitBubble() {
        super("HitBubble", 0, Module.Category.RENDER);
        get = this;
    }

    @Override
    public void onToggled(boolean actived) {
        this.stateAnim.to = actived ? 1.0f : 0.0f;
        super.onToggled(actived);
    }

    @Override
    public void onUpdate() {
        this.stateAnim.to = 1.0f;
    }

    private float getAlphaPC() {
        return this.stateAnim.getAnim();
    }

    private static float getMaxTime() {
        return (double)Minecraft.player.getCooledAttackStrength(0.0f) > 0.8 ? 2500.0f : 1000.0f;
    }

    public static void onAttack(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)entity;
            if (base == null || !base.isEntityAlive()) {
                return;
            }
            Vec3d to = base.getPositionVector().addVector(0.0, base.height / 1.6f, 0.0);
            HitBubble.addBubble(to);
        }
    }

    private static void addBubble(Vec3d addToCoord) {
        RenderManager manager = mc.getRenderManager();
        bubbles.add(new Bubble(manager.playerViewX, -manager.playerViewY, addToCoord));
    }

    private void setupDrawsBubbles3D(Runnable render) {
        RenderManager manager = mc.getRenderManager();
        Vec3d conpense = new Vec3d(manager.getRenderPosX(), manager.getRenderPosY(), manager.getRenderPosZ());
        HitBubble.mc.entityRenderer.disableLightmap();
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glTranslated(-conpense.xCoord, -conpense.yCoord, -conpense.zCoord);
        mc.getTextureManager().bindTexture(this.BUBBLE_TEXTURE);
        render.run();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glTranslated(conpense.xCoord, conpense.yCoord, conpense.zCoord);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        HitBubble.mc.entityRenderer.enableLightmap();
        GL11.glEnable(2896);
    }

    private void drawBubble(Bubble bubble, float alphaPC) {
        GL11.glPushMatrix();
        Vec3d bXYZ = bubble.pos;
        GL11.glTranslated(bubble.pos.xCoord, bubble.pos.yCoord, bubble.pos.zCoord);
        RenderManager manager = mc.getRenderManager();
        float extS = bubble.getDeltaTime();
        GlStateManager.translate(-Math.sin(Math.toRadians(bubble.viewPitch)) * (double)extS / 3.0, Math.sin(Math.toRadians(bubble.viewYaw)) * (double)extS / 2.0, -Math.cos(Math.toRadians(bubble.viewPitch)) * (double)extS / 3.0);
        GL11.glNormal3d(1.0, 1.0, 1.0);
        GL11.glRotated(bubble.viewPitch, 0.0, 1.0, 0.0);
        GL11.glRotated(bubble.viewYaw, HitBubble.mc.gameSettings.thirdPersonView == 2 ? -1.0 : 1.0, 0.0, 0.0);
        GL11.glScaled(-0.1, -0.1, 0.1);
        this.drawBeginsNullCoord(bubble, alphaPC);
        GL11.glPopMatrix();
    }

    private void drawBeginsNullCoord(Bubble bubble, float alphaPC) {
        float aPC = MathUtils.clamp(bubble.getDeltaTime(), 0.0f, 1.0f) * alphaPC;
        aPC = (double)aPC > 0.5 ? 1.0f - aPC : aPC;
        aPC = aPC > 1.0f ? 1.0f : (aPC *= 4.0f);
        float r = 50.0f * bubble.getDeltaTime() * (1.0f - bubble.getDeltaTime());
        float width = aPC *= alphaPC;
        int c = ColorUtils.getColor(100, 20, 255, 255.0f * aPC);
        int speedRotate = 3;
        float III = (float)(System.currentTimeMillis() % (long)(3600 / speedRotate)) / 10.0f * (float)speedRotate;
        RenderUtils.customRotatedObject2D(-1.0f, -1.0f, 2.0f, 2.0f, -III);
        this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(ClientColors.getColor1(0, aPC)).endVertex();
        this.buffer.pos(0.0, r, 0.0).tex(0.0, 1.0).color(ClientColors.getColor1(90, aPC)).endVertex();
        this.buffer.pos(r, r, 0.0).tex(1.0, 1.0).color(ClientColors.getColor1(180, aPC)).endVertex();
        this.buffer.pos(r, 0.0, 0.0).tex(1.0, 0.0).color(ClientColors.getColor1(270, aPC)).endVertex();
        GlStateManager.blendFunc(770, 772);
        GlStateManager.translate(-r / 2.0f, -r / 2.0f, 0.0f);
        GlStateManager.shadeModel(7425);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        this.tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.translate(r / 2.0f, r / 2.0f, 0.0f);
        GlStateManager.blendFunc(770, 771);
    }

    @Override
    public void alwaysRender3D() {
        float aPC = this.getAlphaPC();
        if ((double)aPC < 0.05) {
            return;
        }
        if (bubbles.isEmpty()) {
            return;
        }
        this.removeAuto();
        this.setupDrawsBubbles3D(() -> bubbles.forEach(bubble -> {
            if (bubble != null && bubble.getDeltaTime() <= 1.0f) {
                this.drawBubble((Bubble)bubble, aPC);
            }
        }));
    }

    private void removeAuto() {
        bubbles.removeIf(bubble -> bubble.getDeltaTime() >= 1.0f);
    }

    static {
        bubbles = new ArrayList();
    }

    private static final class Bubble {
        Vec3d pos;
        long time = System.currentTimeMillis();
        float maxTime = HitBubble.getMaxTime();
        float viewYaw;
        float viewPitch;

        public Bubble(float viewYaw, float viewPitch, Vec3d pos) {
            this.viewYaw = viewYaw;
            this.viewPitch = viewPitch;
            this.pos = pos;
        }

        private float getDeltaTime() {
            return (float)(System.currentTimeMillis() - this.time) / this.maxTime;
        }
    }
}

