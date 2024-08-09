package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.BlockUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Kagune", description = "Генерация хвоста гулёныша. (я ебанутый)", category = Category.RENDER)
public class Kagune extends Module {
    private final NumberValue delay = new NumberValue("Длина", this, 1500, 500, 3000, 100);
    private final List<Particle3D> particles = new ArrayList<>();
    private final ResourceLocation triangleTexture = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/triangle.png");
    private final ResourceLocation bloom = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/firefly.png");

    @Override
    protected void onEnable() {
        super.onEnable();
        particles.clear();
    }

    private final Listener<MotionEvent> onMotion = event -> {
        ClientPlayerEntity player = mc.player;
        if ((player.lastTickPosX != player.getPosX() || player.lastTickPosY != player.getPosY() || player.lastTickPosZ != player.getPosZ())) {

            double distance = -(player.getWidth() / 2F), yawRad = Math.toRadians(mc.player.renderYawOffset), xOffset = -Math.sin(yawRad) * distance, zOffset = Math.cos(yawRad) * distance;
            particles.add(new Particle3D(new Vector3d(player.getPosX() + xOffset, player.getPosY() + (player.getHeight() * 0.4F), player.getPosZ() + zOffset), new Vector3d(player.motion.x, 0, player.motion.z)
                    .mul(1.5F + Math.random()), particles.size()));

        }
        particles.removeIf(particle -> particle.getTimerUtil().hasReached(delay.getValue().intValue()));
        particles.removeIf(particle -> particle.position.distance(player.getPosX(), player.getPosY(), player.getPosZ()) >= 100);

    };
    private final Listener<WorldChangeEvent> onWorldChange = event -> {
        particles.clear();
    };
    private final Listener<WorldLoadEvent> onWorldLoad = event -> {
        particles.clear();
    };
    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        MatrixStack matrix = event.getMatrix();

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
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        float pos = 0.15F;
        matrix.push();
        if (!particles.isEmpty()) {
            for (int i = 0; i < mc.getRenderPartialTicks(); i++) {
                particles.forEach(Particle3D::update);
            }
            int index = 0;
            for (final Particle3D particle : particles) {
                if ((int) particle.getAnimation().getValue() != 128 && !particle.getTimerUtil().hasReached(250)) {
                    particle.getAnimation().run(128);
                }
                if ((int) particle.getAnimation().getValue() != 0 && particle.getTimerUtil().hasReached(delay.getValue().intValue() - 250)) {
                    particle.getAnimation().run(0);
                }
                int color = ColorUtil.replAlpha(getTheme().getClientColor(particle.getIndex() * 30), (int) (particle.getAnimation().getValue()));

                Particle3D prevParticle = particles.get((int) Mathf.clamp(0, particles.size(), index - 1));
                Vector3d prevPosition = prevParticle.getPosition();

                float smooth = 0.1F;
                prevParticle.position.set(
                        Interpolator.lerp(prevPosition.x, particle.position.x, smooth),
                        Interpolator.lerp(prevPosition.y, particle.position.y, smooth),
                        Interpolator.lerp(prevPosition.z, particle.position.z, smooth)
                );

                final Vector3d vec = particle.getPosition();

                final float x = (float) vec.x;
                final float y = (float) vec.y;
                final float z = (float) vec.z;

                matrix.push();
                RectUtil.setupOrientationMatrix(matrix, x, y, z);
                matrix.rotate(mc.getRenderManager().getCameraOrientation());
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                matrix.translate(0, pos / 2F, 0);
                float bloomSize = (float) Mathf.clamp(0.25, 1, particle.position.distance(prevPosition) * 4);
                RectUtil.bindTexture(bloom);
                RectUtil.drawRect(matrix, -bloomSize, -bloomSize, bloomSize, bloomSize, color, color, color, color, true, true);
                RectUtil.bindTexture(triangleTexture);
                RectUtil.drawRect(matrix, -pos, -pos, pos, pos, color, color, color, color, true, true);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                matrix.pop();
                index++;
            }
        }
        matrix.pop();

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light)
            RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();

    };

    @Getter
    private static class Particle3D implements IAccess {
        private final int index;
        private final TimerUtil timerUtil = TimerUtil.create();
        private final Animation animation = new Animation(Easing.LINEAR, 250);

        public final Vector3d position;
        private final Vector3d delta;

        public Particle3D(final Vector3d position, final int index) {
            this.position = position;
            this.delta = new Vector3d((Math.random() * 0.5 - 0.25) * 0.01, (Math.random() * 0.25) * 0.01, (Math.random() * 0.5 - 0.25) * 0.01);
            this.index = index;
            this.timerUtil.reset();
        }

        public Particle3D(final Vector3d position, final Vector3d velocity, final int index) {
            this.position = position;
            this.delta = new Vector3d(velocity.x * 0.01, velocity.y * 0.01, velocity.z * 0.01);
            this.index = index;
            this.timerUtil.reset();
        }

        public void update() {
            final Block block1 = BlockUtils.getBlock(this.position.x, this.position.y, this.position.z + this.delta.z);
            if (isValidBlock(block1))
                this.delta.z *= -0.8;

            final Block block2 = BlockUtils.getBlock(this.position.x, this.position.y + this.delta.y, this.position.z);
            if (isValidBlock(block2)) {
                this.delta.x *= 0.999F;
                this.delta.z *= 0.999F;

                this.delta.y *= -0.7;
            }

            final Block block3 = BlockUtils.getBlock(this.position.x + this.delta.x, this.position.y, this.position.z);
            if (isValidBlock(block3))
                this.delta.x *= -0.8;

            this.updateWithoutPhysics();
        }

        private boolean isValidBlock(Block block) {
            return !(block instanceof AirBlock)
                    && !(block instanceof BushBlock)
                    && !(block instanceof AbstractButtonBlock)
                    && !(block instanceof TorchBlock)
                    && !(block instanceof LeverBlock)
                    && !(block instanceof AbstractPressurePlateBlock)
                    && !(block instanceof CarpetBlock)
                    && !(block instanceof FlowingFluidBlock);
        }

        public void updateWithoutPhysics() {
            this.position.x += this.delta.x;
            this.position.y += this.delta.y;
            this.position.z += this.delta.z;
            this.delta.x /= 0.999999F;
            this.delta.y = 0;
            this.delta.z /= 0.999999F;
        }
    }
}