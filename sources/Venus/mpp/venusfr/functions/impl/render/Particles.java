/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import mpp.venusfr.events.AttackEvent;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.PlayerPositionTracker;
import mpp.venusfr.utils.projections.ProjectionUtil;
import mpp.venusfr.utils.render.AnimationMath;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.world.WorldUtil;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Particles", type=Category.Visual)
public class Particles
extends Function {
    private final SliderSetting value = new SliderSetting("\u041a\u043e\u043b-\u0432\u043e \u0437\u0430 \u0443\u0434\u0430\u0440", 20.0f, 1.0f, 50.0f, 1.0f);
    private final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList();
    private final ResourceLocation firefly = new ResourceLocation("venusfr/images/firefly.png");

    public Particles() {
        this.addSettings(this.value);
    }

    private boolean isInView(Vector3d vector3d) {
        WorldRenderer.frustum.setCameraPosition(Particles.mc.getRenderManager().info.getProjectedView().x, Particles.mc.getRenderManager().info.getProjectedView().y, Particles.mc.getRenderManager().info.getProjectedView().z);
        return WorldRenderer.frustum.isBoundingBoxInFrustum(new AxisAlignedBB(vector3d.add(-0.2, -0.2, -0.2), vector3d.add(0.2, 0.2, 0.2)));
    }

    private boolean isVisible(Vector3d vector3d) {
        Vector3d vector3d2 = Particles.mc.getRenderManager().info.getProjectedView();
        RayTraceContext rayTraceContext = new RayTraceContext(vector3d2, vector3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, Particles.mc.player);
        BlockRayTraceResult blockRayTraceResult = Particles.mc.world.rayTraceBlocks(rayTraceContext);
        return blockRayTraceResult.getType() == RayTraceResult.Type.MISS;
    }

    @Subscribe
    private void onUpdate(AttackEvent attackEvent) {
        if (attackEvent.entity == Particles.mc.player) {
            return;
        }
        Object object = attackEvent.entity;
        if (object instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)object;
            object = livingEntity.getPositionVec().add(0.0, livingEntity.getHeight() / 2.0f, 0.0);
            int n = 0;
            while ((float)n < ((Float)this.value.get()).floatValue()) {
                this.particles.add(new Particle(this, (Vector3d)object));
                ++n;
            }
        }
    }

    @Subscribe
    private void onUpdate(EventMotion eventMotion) {
        for (Entity entity2 : Particles.mc.world.getAllEntities()) {
            if (!(entity2 instanceof EnderPearlEntity)) continue;
            EnderPearlEntity enderPearlEntity = (EnderPearlEntity)entity2;
            this.particles.add(new Particle(this, enderPearlEntity.getPositionVec()));
        }
    }

    @Subscribe
    private void onDisplay(EventDisplay eventDisplay) {
        if (Particles.mc.player == null || Particles.mc.world == null || eventDisplay.getType() != EventDisplay.Type.PRE) {
            return;
        }
        for (Particle particle : this.particles) {
            long l = System.currentTimeMillis() - particle.time;
            if (l > particle.aliveTime || !Objects.requireNonNull(Particles.mc.player).canVectorBeSeenFixed(particle.pos) || !PlayerPositionTracker.isInView(particle.pos)) {
                this.particles.remove(particle);
                continue;
            }
            if (this.isInView(particle.pos) && this.isVisible(particle.pos)) {
                particle.update();
                Vector2f vector2f = ProjectionUtil.project(particle.pos.x, particle.pos.y, particle.pos.z);
                float f = 1.0f - (float)l / (float)particle.aliveTime;
                int n = ColorUtils.setAlpha(ColorUtils.getColor(0), 165);
                int n2 = ColorUtils.setAlpha(ColorUtils.getColor(90), 165);
                DisplayUtils.drawImage(this.firefly, vector2f.x - 15.0f, vector2f.y - 15.0f, 30.0f * f, 30.0f * f, n, n, n2, n2);
                continue;
            }
            this.particles.remove(particle);
        }
    }

    private class Particle {
        private Vector3d pos;
        public Vector3d motion;
        public Vector3d animatedMotion;
        public long aliveTime;
        private long time;
        private long collisionTime;
        public float size;
        private float alpha;
        final Particles this$0;

        public Particle(Particles particles, Vector3d vector3d) {
            this.this$0 = particles;
            this.collisionTime = -1L;
            this.pos = vector3d;
            this.motion = new Vector3d(MathUtil.randomizeFloat(-0.01f, 0.01f), 0.0, MathUtil.randomizeFloat(-0.01f, 0.01f));
            this.animatedMotion = new Vector3d(0.0, 0.0, 0.0);
            this.time = System.currentTimeMillis();
            this.size = MathUtil.randomizeFloat(4.0f, 7.0f);
            this.aliveTime = ThreadLocalRandom.current().nextLong(3000L, 10000L);
            this.alpha = 1.0f;
        }

        public void update() {
            if (this.isGround()) {
                this.motion.y = 1.0;
                this.motion.x *= 1.05;
                this.motion.z *= 1.05;
            } else {
                this.motion.y = -0.01;
                this.motion.y *= 2.0;
            }
            this.animatedMotion.x = AnimationMath.fast((float)this.animatedMotion.x, (float)this.motion.x, 3.0f);
            this.animatedMotion.y = AnimationMath.fast((float)this.animatedMotion.y, (float)this.motion.y, 3.0f);
            this.animatedMotion.z = AnimationMath.fast((float)this.animatedMotion.z, (float)this.motion.z, 3.0f);
            this.pos = this.pos.add(this.animatedMotion);
        }

        boolean isGround() {
            Vector3d vector3d = this.pos.add(this.animatedMotion);
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(vector3d.x - 0.1, vector3d.y - 0.1, vector3d.z - 0.1, vector3d.x + 0.1, vector3d.y + 0.1, vector3d.z + 0.1);
            return WorldUtil.TotemUtil.getSphere(new BlockPos(vector3d), 2.0f, 4, false, true, 0).stream().anyMatch(arg_0 -> Particle.lambda$isGround$0(axisAlignedBB, vector3d, arg_0));
        }

        private static boolean lambda$isGround$0(AxisAlignedBB axisAlignedBB, Vector3d vector3d, BlockPos blockPos) {
            return !IMinecraft.mc.world.getBlockState(blockPos).isAir() && axisAlignedBB.intersects(new AxisAlignedBB(blockPos)) && AxisAlignedBB.calcSideHit(new AxisAlignedBB(blockPos.add(0, 1, 0)), vector3d, new double[]{2.0}, null, 0.1f, 0.1f, 0.1f) == Direction.DOWN;
        }
    }
}

