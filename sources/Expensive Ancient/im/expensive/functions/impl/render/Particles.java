package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.AttackEvent;
import im.expensive.events.EventDisplay;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.client.renderer.WorldRenderer.frustum;

@FunctionRegister(name = "Particles", type = Category.Render)
public class Particles extends Function {

    private final ModeSetting setting = new ModeSetting("Вид", "Сердечки", "Сердечки", "Орбизы", "Молния", "Снежинки");
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

    @Subscribe
    private void onUpdate(AttackEvent e) {
        if (e.entity == mc.player) return;
        if (e.entity instanceof LivingEntity livingEntity) {
            for (int i = 0; i < value.get(); i++) {
                particles.add(new Particle(livingEntity.getPositon(mc.getRenderPartialTicks()).add(0, livingEntity.getHeight() / 2f, 0)));
            }
        }
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }

        for (Particle p : particles) {
            if (System.currentTimeMillis() - p.time > 5000) {
                particles.remove(p);
            }
            if (mc.player.getPositionVec().distanceTo(p.pos) > 30) {
                particles.remove(p);
            }
            if (isInView(p.pos)) {
                if (!mc.player.canEntityBeSeen(p.pos)) {
                    particles.remove(p);

                }
                p.update();
                Vector2f pos = ProjectionUtil.project(p.pos.x, p.pos.y, p.pos.z);

                float size = 1 - ((System.currentTimeMillis() - p.time) / 5000f);
                DisplayUtils.drawShadowCircle(pos.x, pos.y, 10, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((64 * p.alpha) * size)));

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
                }
            } else {
                particles.remove(p);
            }
        }

    }

    private class Particle {
        private Vector3d pos;
        private final Vector3d end;
        private final long time;

        private float alpha;


        public Particle(Vector3d pos) {
            this.pos = pos;
            end = pos.add(-ThreadLocalRandom.current().nextFloat(-3, 3), -ThreadLocalRandom.current().nextFloat(-3, 3), -ThreadLocalRandom.current().nextFloat(-3, 3));
            time = System.currentTimeMillis();
        }

        public void update() {
            alpha = MathUtil.fast(alpha, 1, 10);
            pos = MathUtil.fast(pos, end, 0.5f);

        }


    }

}
