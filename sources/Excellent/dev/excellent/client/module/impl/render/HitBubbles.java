package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.player.AttackEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.AntiBot;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.rotation.RotationUtil;
import dev.excellent.impl.util.time.PercentRunner;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Hit Bubbles", description = "Создаёт невероятно красивые порталы, которые улучшают вашу игру в кубики.", category = Category.RENDER)
public class HitBubbles extends Module {
    private final List<Particle> particles = new ArrayList<>();
    private final ResourceLocation texture = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/hitbubble.png");

    @Override
    protected void onEnable() {
        super.onEnable();
        particles.clear();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        particles.clear();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> {
        particles.clear();
    };
    private final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() instanceof LivingEntity livingEntity) {
            if (!livingEntity.isAlive() || AntiBot.contains(livingEntity)) return;
            long maxTime = 2000L;
            ClientPlayerEntity player = mc.player;
            double distance = player.getDistance(livingEntity) - 1.2F + player.getWidth() / 2.F, yawRad = Math.toRadians(RotationUtil.calculate(livingEntity).x), xOffset = -Math.sin(yawRad) * distance, zOffset = Math.cos(yawRad) * distance;
            Vector3d particlePosition = new Vector3d(player.getPosX() + xOffset, player.getPosY() + player.getEyeHeight() * .9D, player.getPosZ() + zOffset);
            particles.add(new Particle(particles.size(), particlePosition, maxTime));
        }
    };

    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        if (particles.isEmpty()) return;
        float maxScale = 1, alphaPC = 1;
        int colorSpeed = 5;
        boolean bloom = true;
        MatrixStack matrixStack = event.getMatrix();
        particles.removeIf(Particle::isToRemove);
        if (particles.isEmpty()) return;
        setRenderingTexture3D(() -> particles.stream().filter(Particle::renderRule).forEach(particle -> drawParticle(particle, matrixStack, maxScale, alphaPC, bloom)), matrixStack, texture);
    };

    private void drawParticle(Particle particle, MatrixStack matrix, float maxScale, float alphaPC, boolean bloom) {
        int[] colors = getColorsParticles(particle, alphaPC * particle.percentRunner.getPercent010());
        float extendXY = maxScale * particle.percentRunner.getPercent();
        setParticleOrientation(particle, () -> {
            for (int i = 0; i < 2; ++i)
                RectUtil.drawRect(matrix, -extendXY, -extendXY, extendXY, extendXY, colors[0], colors[1], colors[2], colors[3], bloom, true);
        }, matrix);
    }

    private void setRenderingTexture3D(Runnable render, MatrixStack matrix, ResourceLocation resourceLocation) {
        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        RenderSystem.pushMatrix();
        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light)
            RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        //RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RectUtil.bindTexture(resourceLocation);
        matrix.push();
        //matrix.rotate(mc.getRenderManager().getCameraOrientation());
        render.run();

        matrix.pop();
        //if (bloom)
        //    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light)
            RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();
    }

    private int[] getColorsParticles(Particle particle, float alphaPC) {
        int[] colors = new int[4];
        int indexAppend = particle.index * 45;
        colors[0] = getTheme().getClientColor(indexAppend, alphaPC);
        colors[1] = getTheme().getClientColor(90 + indexAppend, alphaPC);
        colors[2] = getTheme().getClientColor(180 + indexAppend, alphaPC);
        colors[3] = getTheme().getClientColor(270 + indexAppend, alphaPC);
        return colors;
    }

    private void setParticleOrientation(Particle particle, Runnable render, MatrixStack matrix) {
        matrix.push();
        RectUtil.setupOrientationMatrix(matrix, particle.pos.getX(), particle.pos.getY(), particle.pos.getZ());
        matrix.rotate(particle.quaternion);
        RectUtil.bindTexture(texture);
        matrix.push();
        final float rotatePC01 = particle.percentRunner.getPercent();
        matrix.rotate(Vector3f.ZP.rotationDegrees(rotatePC01 * -360.F));
        render.run();
        matrix.pop();
        matrix.pop();
    }

    //@RequiredArgsConstructor
    private static class Particle {
        private final int index;
        private final Vector3d pos;
        private final PercentRunner percentRunner;
        private final Quaternion quaternion = mc.getRenderManager().getCameraOrientation().copy();

        public Particle(int index, Vector3d pos, long maxTime) {
            this.index = index;
            this.pos = pos;
            this.percentRunner = PercentRunner.create(maxTime);
        }

        public boolean isToRemove() {
            return percentRunner.getPercent() == 1 || mc.player.getPositionVec().distanceTo(pos) > 100;
        }

        public boolean renderRule() {
            return !isToRemove();
        }
    }
}
