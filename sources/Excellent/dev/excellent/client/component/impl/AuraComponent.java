package dev.excellent.client.component.impl;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.Component;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.rotation.AuraUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import net.optifine.shaders.Shaders;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class AuraComponent extends Component implements IAccess {
    private final ResourceLocation markerLocation = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/marker.png");
    private final ResourceLocation glowCircle = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/glow_circle.png");
    private final Vector2d markerPosition = new Vector2d();
    public final Animation markerAnimation = new Animation(Easing.LINEAR, 500);
    public final Animation markerA = new Animation(Easing.EASE_OUT_CUBIC, 500);
    public LivingEntity target = null;
    private final long startTime = System.currentTimeMillis();
    private final Listener<Render2DEvent> onRender2D = event -> {
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null) {
            target = aura.getTarget();
        }
        if (markerAnimation.isFinished() && markerAnimation.getValue() == 0 && aura.getTarget() == null) {
            target = null;
        }
        if (mc.player == null || mc.world == null || target == null) return;
        auraProcess(event);
    };

    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null) {
            target = aura.getTarget();
        }
        if (markerA.isFinished() && markerA.getValue() == 0 && aura.getTarget() == null) {
            target = null;
        }
        if (mc.player == null || mc.world == null || target == null) return;
        auraProcess(event);
    };

    private void auraProcess(Render2DEvent event) {
        KillAura aura = KillAura.singleton.get();
        markerAnimation.run(aura.getTarget() == null ? 0 : 255);
        if (aura.getChecks().get("Таргет есп").getValue()) {
            if (aura.targetEspMode.is("Старый")) {
                drawMarker(event.getMatrix());
            }
        }
    }

    private void auraProcess(Render3DPosedEvent event) {
        KillAura aura = KillAura.singleton.get();
        markerA.run(aura.getTarget() == null ? 0 : 1);
        if (aura.getChecks().get("Таргет есп").getValue()) {
            if (aura.targetEspMode.is("Новый")) {
                drawNewMarker(event.getMatrix());
            }
            if (aura.targetEspMode.is("Кругляшок")) {
                drawNewMarker(event);
            }
        }
    }

    public void drawNewMarker(MatrixStack stack) {
        double markerX = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosX, target.getPosX());
        double markerY = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosY, target.getPosY()) + target.getHeight() / 1.6f;
        double markerZ = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosZ, target.getPosZ());
        float time = (float) ((((System.currentTimeMillis() - startTime) / 1500F)) + (Math.sin((((System.currentTimeMillis() - startTime) / 1500F))) / 10f));
        float alpha = (float) ((Shaders.shaderPackLoaded ? 1 : 0.5f) * markerA.getValue());
        float pl = 0;
        boolean fa = false;
        for (int iteration = 0; iteration < 3; iteration++) {
            for (float i = time * 360; i < time * 360 + 90; i += 2) {
                float max = time * 360 + 90;
                float dc = Mathf.normalize(i, time * 360 - 45, max);
                float rf = 0.6f;
                double radians = Math.toRadians(i);
                double plY = pl + Math.sin(radians * 1.2f) * 0.1f;
                int firstColor = ColorUtil.replAlpha(getTheme().getClientColor(0), (int) (markerA.getValue() * 255));
                int secondColor = ColorUtil.replAlpha(getTheme().getClientColor(90), (int) (markerA.getValue() * 255));
                stack.push();
                RectUtil.setupOrientationMatrix(stack, markerX, markerY, markerZ);
                stack.rotate(mc.getRenderManager().info.getRotation());
                GlStateManager.depthMask(false);
                float q = (!fa ? 0.25f : 0.15f) * (Math.max(fa ? 0.25f : 0.15f, fa ? dc : (1f - -(0.4f - dc)) / 2f) + 0.45f);
                float size = q * (2f + ((0.5f - alpha) * 2));
                RenderUtil.drawImage(stack,
                        glowCircle,
                        Math.cos(radians) * rf - size / 2f,
                        plY - 0.7,
                        Math.sin(radians) * rf - size / 2f, size, size,
                        firstColor,
                        secondColor,
                        secondColor,
                        firstColor);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GlStateManager.depthMask(true);
                stack.pop();
            }
            time *= -1.025f;
            fa = !fa;
            pl += 0.45f;
        }
    }

    public void drawMarker(MatrixStack matrix) {
        KillAura aura = KillAura.singleton.get();

        int alpha = (int) markerAnimation.getValue();
        float alphaPC = (alpha / 255F);
        if (target != null) {
            org.joml.Vector3d interpolatedPosition = RenderUtil.interpolate(target, mc.getRenderPartialTicks());
            double x = interpolatedPosition.x;
            double y = interpolatedPosition.y;
            double z = interpolatedPosition.z;

            Vector2d marker = RenderUtil.project2D(x, y + ((target.getEyeHeight() + 0.4F) * 0.5F), z);
            if (marker == null) return;

            markerPosition.lerp(marker, target.isAlive() ? 0.5F : 0);

            float size = 100;

            double angle = (float) Mathf.clamp(0, 90, ((Math.sin(System.currentTimeMillis() / 500D) + 1F) / 2F) * 90);
            double scale = (float) Mathf.clamp(0.9, 1.1, ((Math.sin(System.currentTimeMillis() / 500D) + 1F) / 2F) * 1.1);
            double rotate = (float) Mathf.clamp(0, 360, ((Math.sin(System.currentTimeMillis() / 1000D) + 1F) / 2F) * 360);

            GlStateManager.pushMatrix();
            GL11.glTranslatef((float) markerPosition.x, (float) markerPosition.y, 0.0F);
            GL11.glScaled(scale, scale, 1F);

            double sc = Mathf.clamp(0.75F, 1F, (1F - AuraUtil.getVector(target).length() / aura.getAttackRange().getValue().doubleValue()));
            sc = Interpolator.lerp(scale, sc, 0.5F);
            GL11.glScaled(sc, sc, sc);

            GL11.glTranslatef((float) (-markerPosition.x) - (size / 2F), (float) (-markerPosition.y), 0.0F);

            float colorPC = (float) Math.sin(target.hurtTime * (18F * Math.PI / 180F));

            int color1 = ColorUtil.overCol(getTheme().getClientColor(0, alphaPC), ColorUtil.getColor(255, 0, 0, alpha), colorPC);
            int color2 = ColorUtil.overCol(getTheme().getClientColor(90, alphaPC), ColorUtil.getColor(255, 0, 0, alpha), colorPC);
            int color3 = ColorUtil.overCol(getTheme().getClientColor(180, alphaPC), ColorUtil.getColor(255, 0, 0, alpha), colorPC);
            int color4 = ColorUtil.overCol(getTheme().getClientColor(270, alphaPC), ColorUtil.getColor(255, 0, 0, alpha), colorPC);

            GLUtils.startRotate((float) markerPosition.x + (size / 2F), (float) markerPosition.y, (float) (45F - (angle - 45) + rotate));

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
            mc.getTextureManager().bindTexture(markerLocation);
            if (alpha != 0) {
                RectUtil.drawRect(matrix, (float) (markerPosition.x), (float) (markerPosition.y - (size / 2F)), (float) (markerPosition.x + size), (float) (markerPosition.y + (size / 2F)), color1, color2, color3, color4, true, true);
            }
            GlStateManager.disableBlend();

            GLUtils.endRotate();
            GlStateManager.popMatrix();
        }
    }

    public void drawNewMarker(Render3DPosedEvent event) {
        MatrixStack matrix = event.getMatrix();
        double markerX = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosX, target.getPosX());
        double markerY = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosY, target.getPosY());
        double markerZ = Mathf.interporate(mc.getRenderPartialTicks(), target.lastTickPosZ, target.getPosZ());

        float size = 0.5F;
        final float radius = 0.75F;

        final boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);

        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light) RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderUtil.bindTexture(glowCircle);

        double angleY = ((Math.sin(System.currentTimeMillis() / 500D) + 1F) / 2F) * (target.getHeight());
        final float iterations = 10;

        for (int i = 0; i < 360; i += 10) {
            matrix.push();
            RectUtil.setupOrientationMatrix(matrix, markerX, markerY, markerZ);

            final double radians = Math.toRadians(i);
            final double cosPos = ((Math.cos(radians) * radius) - (size / 2F));
            final double sinPos = ((Math.sin(radians) * radius) - (size / 2F));

            matrix.push();

            matrix.translate((cosPos + (size / 2F)), 0, (sinPos + (size / 2F)));
            matrix.rotate(Vector3f.YN.rotationDegrees(mc.getRenderManager().info.getYaw()));
            matrix.translate(-(cosPos + (size / 2F)), -0, -(sinPos + (size / 2F)));

            matrix.translate(cosPos, angleY, sinPos);
            matrix.rotate(Vector3f.XP.rotationDegrees(mc.getRenderManager().info.getPitch()));
            matrix.translate(-cosPos, -angleY, -sinPos);

            for (int j = 1; j < iterations; j++) {
                final int color = getTheme().getClientColor(i * 5, (float) (markerA.getValue() * ((j / iterations) * 0.25F)));
                RenderUtil.drawImage(matrix, cosPos, angleY + (j * 0.1) * (-0.5 + Math.cos(angleY)), sinPos, size, size, color, color, color, color);
            }
            matrix.pop();
            matrix.pop();
        }
//       angleY + (j * 0.1) * (-0.5 + Math.cos(angleY))

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light) RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
    }

}
