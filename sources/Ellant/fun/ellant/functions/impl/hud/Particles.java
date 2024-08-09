package fun.ellant.functions.impl.hud;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.AttackEvent;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.projections.ProjectionUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.client.renderer.WorldRenderer.frustum;

@FunctionRegister(name = "Particles", type = Category.RENDER, desc = "Эффект при ударе")
public class Particles extends Function {

    private final ModeSetting setting = new ModeSetting("Вид", "Доллары", "Доллары", "Снежинки", "Орбизы", "Звездочки");
    private final SliderSetting value = new SliderSetting("Кол-во за удар", 20.0f, 1.0f, 50.0f, 1.0f);
    private final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();

    public Particles() {
        addSettings(setting, value);
    }


    private boolean isInView(Vector3d pos) {
        frustum.setCameraPosition(mc.getRenderManager().info.getProjectedView().x,
                mc.getRenderManager().info.getProjectedView().y,
                mc.getRenderManager().info.getProjectedView().z);
        return frustum.isBoundingBoxInFrustum(new AxisAlignedBB(pos.add(-0.2, -0.2, -0.2), pos.add(0.2, 0.2, 0.2)));
    }

    private boolean isVisible(Vector3d pos) {
        Vector3d cameraPos = mc.getRenderManager().info.getProjectedView();
        RayTraceContext context = new RayTraceContext(cameraPos, pos, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, mc.player);
        BlockRayTraceResult result = mc.world.rayTraceBlocks(context);
        return result.getType() == RayTraceResult.Type.MISS;
    }

    @Subscribe
    private void onUpdate(AttackEvent e) {
        if (e.entity == mc.player) return;
        if (e.entity instanceof LivingEntity livingEntity) {
            Vector3d center = livingEntity.getPositionVec().add(0, livingEntity.getHeight() / 2f, 0);
            for (int i = 0; i < value.get(); i++) {
                particles.add(new Particle(center));
            }
        }
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }

        for (Particle p : particles) {
            if (System.currentTimeMillis() - p.time > 7000 || p.alpha <= 0) {
                particles.remove(p);
            } else if (mc.player.getPositionVec().distanceTo(p.pos) > 30) {
                particles.remove(p);
            } else if (isInView(p.pos) && isVisible(p.pos)) {
                p.update();
                Vector2f pos = ProjectionUtil.project(p.pos.x, p.pos.y, p.pos.z);

                float size = 1 - ((System.currentTimeMillis() - p.time) / 7000f);

                final ResourceLocation star = new ResourceLocation("expensive/images/star.png");
                final ResourceLocation snow = new ResourceLocation("expensive/images/snow.png");
                final ResourceLocation orbiz = new ResourceLocation("expensive/images/orbiz.png");
                final ResourceLocation dollar = new ResourceLocation("expensive/images/target3.png");

                switch (setting.get()) {
                    case "Сердечки" -> {
                        Fonts.damage.drawText(e.getMatrixStack(), "B", pos.x - 3 * size, pos.y - 3 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((200 * p.alpha) * size)), 15 * size, 0.05f);
                    }
                    case "Снежинки" -> {
                        Fonts.damage.drawText(e.getMatrixStack(), "A", pos.x - 3 * size, pos.y - 3 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((200 * p.alpha) * size)), 15 * size, 0.05f);
                    }
                    case "Молния" -> {
                        Fonts.damage.drawText(e.getMatrixStack(), "C", pos.x - 3 * size, pos.y - 3 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((200 * p.alpha) * size)), 15 * size, 0.05f);
                    }
                    case "Орбизы" -> {
                        DisplayUtils.drawCircle(pos.x, pos.y, 5 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((200 * p.alpha) * size)));
                    }
                    case "Доллары" -> {
                        DisplayUtils.drawImage(dollar, pos.x, pos.y, 30 * size, 30 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((255 * p.alpha) * size)));                    }
                    case "Звездочки" -> {
                        DisplayUtils.drawImage(star, pos.x, pos.y, 30 * size, 30 * size, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((255 * p.alpha) * size)));
                    }
                }
            } else {
                particles.remove(p);
            }
        }
    }

    private class Particle {
        private Vector3d pos;
        private Vector3d end;
        private long time;
        private long collisionTime = -1;
        private Vector3d velocity;
        private float alpha;

        public Particle(Vector3d pos) {
            this.pos = pos;
            this.end = pos.add(-ThreadLocalRandom.current().nextFloat(-1, 1), -ThreadLocalRandom.current().nextFloat(-1, 1), -ThreadLocalRandom.current().nextFloat(-1, 1));
            this.time = System.currentTimeMillis();
            this.velocity = new Vector3d(
                    ThreadLocalRandom.current().nextDouble(-0.01, 0.01),
                    ThreadLocalRandom.current().nextDouble(0.01, 0.02),
                    ThreadLocalRandom.current().nextDouble(-0.01, 0.01)
            );
            this.alpha = 1.0f;
        }

        public void update() {

            if (collisionTime != -1) {
                long timeSinceCollision = System.currentTimeMillis() - collisionTime;
                alpha = Math.max(0, 1 - (timeSinceCollision / 3000f));
            }

            velocity = velocity.add(0, -0.0001, 0);

            Vector3d newPos = pos.add(velocity);

            BlockPos particlePos = new BlockPos(newPos);
            BlockState blockState = mc.world.getBlockState(particlePos);
            if (!blockState.isAir()) {
                if (collisionTime == -1) {
                    collisionTime = System.currentTimeMillis();
                }

                if (!mc.world.getBlockState(new BlockPos(pos.x + velocity.x, pos.y, pos.z)).isAir()) {
                    velocity = new Vector3d(0, velocity.y, velocity.z);
                }
                if (!mc.world.getBlockState(new BlockPos(pos.x, pos.y + velocity.y, pos.z)).isAir()) {
                    velocity = new Vector3d(velocity.x, -velocity.y * 0.5f, velocity.z); // Отскок с уменьшением скорости
                }
                if (!mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z + velocity.z)).isAir()) {
                    velocity = new Vector3d(velocity.x, velocity.y, 0);
                }

                pos = pos.add(velocity);
            } else {
                pos = newPos;
            }
        }
    }

}