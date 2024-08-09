package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.BlockUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Fire Fly", description = "Отображает красивые светлячки в мире.", category = Category.RENDER)
public class FireFly extends Module {
    private final List<FireFlyEntity> particles = new ArrayList<>();

    private final NumberValue count = new NumberValue("Кол-во", this, 100, 10, 300, 10);
    public final BooleanValue randomColor = new BooleanValue("Рандомный цвет", this, false);
    private final ResourceLocation texture = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/firefly.png");

    @Override
    protected void onEnable() {
        super.onEnable();
        particles.clear();
        spawnParticle(mc.player);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        particles.clear();
    }

    private void spawnParticle(LivingEntity entity) {
        double distance = Mathf.getRandom(5, 50), yawRad = Math.toRadians(Mathf.getRandom(0, 360)), xOffset = -Math.sin(yawRad) * distance, zOffset = Math.cos(yawRad) * distance;

        particles.add(new FireFlyEntity(new Vector3d(entity.getPosX() + xOffset, entity.getPosY() + Mathf.getRandom(10, 20), entity.getPosZ() + zOffset),
                new Vector3d(), particles.size(), ColorUtil.random().hashCode()));
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> particles.clear();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> particles.clear();
    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        ClientPlayerEntity player = mc.player;
        particles.removeIf(particle ->
                (particle.time.hasReached(5000))
                        || (particle.position.distance(player.getPosX(), player.getPosY(), player.getPosZ()) >= 60)
        );
        if (particles.size() <= count.getValue().intValue()) spawnParticle(player);
        MatrixStack matrix = event.getMatrix();
        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        RenderSystem.pushMatrix();
        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light) RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        matrix.push();

        RectUtil.bindTexture(texture);
        if (!particles.isEmpty()) {
            particles.forEach(fireFly -> fireFly.update(true));
            float pos = 0.2F;
            for (final FireFlyEntity particle : particles) {
                if ((int) particle.getAlpha().getValue() != 255 && !particle.time.hasReached(particle.alpha.getDuration()))
                    particle.getAlpha().run(255);
                if ((int) particle.getAlpha().getValue() != 0 && particle.time.hasReached(5000 - particle.alpha.getDuration()))
                    particle.getAlpha().run(0);
                int alpha = (int) Mathf.clamp(0, (int) (particle.getAlpha().getValue()), particle.getAngle());
                int color;
                int colorGlow = ColorUtil.replAlpha(getTheme().getClientColor(particle.index * 99), alpha);
                if (randomColor.getValue()) colorGlow = ColorUtil.replAlpha(particle.getColor(), alpha);
                final Vector3d vec = particle.getPosition();
                final float x = (float) vec.x;
                final float y = (float) vec.y;
                final float z = (float) vec.z;
                matrix.push();
                RectUtil.setupOrientationMatrix(matrix, x, y, z);
                matrix.rotate(mc.getRenderManager().getCameraOrientation());
                matrix.translate(0, pos / 2F, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                RectUtil.drawRect(matrix, -pos, -pos, pos, pos, colorGlow, colorGlow, colorGlow, colorGlow, true, true);
                float size = pos / 2F;
                color = ColorUtil.replAlpha(-1, alpha);
                RectUtil.drawRect(matrix, -size, -size, size, size, color, color, color, color, true, true);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                matrix.pop();
            }
        }
        matrix.pop();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light) RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();
    };


    @Getter
    private static class FireFlyEntity implements IAccess {
        private final int index;
        private final TimerUtil time = TimerUtil.create();
        private final Animation alpha = new Animation(Easing.LINEAR, 150);
        private final int color;

        public final Vector3d position;
        private final Vector3d delta;

        public FireFlyEntity(final Vector3d position, final Vector3d velocity, final int index, int color) {
            this.position = position;
            this.delta = new Vector3d(velocity.x, velocity.y, velocity.z);
            this.index = index;
            this.color = color;
            this.time.reset();
        }

        public void update(boolean physics) {
            if (physics) {
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
            }

            this.updateMotion();
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

        public int getAngle() {
            return (int) Mathf.clamp(0, 255, ((Math.sin(time.elapsedTime() / 250D) + 1F) / 2F) * 255);
        }

        public void updateMotion() {
            double motion = 0.005;
            this.delta.x += (Math.random() - 0.5) * motion;
            this.delta.y += (Math.random() - 0.5) * motion;
            this.delta.z += (Math.random() - 0.5) * motion;

            double maxSpeed = 0.5;
            this.delta.x = MathHelper.clamp(this.delta.x, -maxSpeed, maxSpeed);
            this.delta.y = MathHelper.clamp(this.delta.y, -maxSpeed, maxSpeed);
            this.delta.z = MathHelper.clamp(this.delta.z, -maxSpeed, maxSpeed);

            this.position.x += this.delta.x;
            this.position.y += this.delta.y;
            this.position.z += this.delta.z;

        }
    }

}
