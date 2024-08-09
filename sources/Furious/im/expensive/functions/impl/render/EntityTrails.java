package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import im.expensive.utils.player.MoveUtils;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventDisplay.Type;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;

import static net.minecraft.client.renderer.WorldRenderer.frustum;

@FunctionRegister(
        name = "ParticleTrails",
        type = Category.Render
)
public class EntityTrails extends Function {
    private final ModeSetting setting = new ModeSetting("Вид", "Орбизы", new String[]{"Орбизы", "Сердечки", "Звёзды", "Снежинки"});
    private final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList();
    private final int maxParticles = 250;

    public EntityTrails() {
    }

    private boolean isInView(Vector3d pos) {
        frustum.setCameraPosition(mc.getRenderManager().info.getProjectedView().x,
                mc.getRenderManager().info.getProjectedView().y,
                mc.getRenderManager().info.getProjectedView().z);
        return frustum.isBoundingBoxInFrustum(new AxisAlignedBB(pos.add(-0.3, -0.3, -0.3), pos.add(0.3, 0.3, 0.3)));
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.player != null && mc.world != null && e.getType() == Type.PRE) {
            if (this.particles.size() < 70) {
                this.particles.add(new Particle(this));
            }

            Iterator var2 = this.particles.iterator();

            while(true) {
                while(var2.hasNext()) {
                        Particle p = (Particle) var2.next();
                        if (System.currentTimeMillis() - p.time <= 10000L && !(mc.player.getPositionVec().distanceTo(p.pos) > 5.0) && this.isInView(p.pos) && mc.player.canEntityBeSeen(p.pos)) {
                            p.update();
                            Vector2f pos = ProjectionUtil.project(p.pos.x + 0.32, p.pos.y + 0.45, p.pos.z + 0.32);
                            float size = 3.5F - (float) (System.currentTimeMillis() - p.time) / 10000.0F;
                            switch ((String) this.setting.get()) {
                                case "Орбизы":
                                        DisplayUtils.drawCircle(pos.x + 10.0F, pos.y, 4F * size, ColorUtils.setAlpha(HUD.getColor(this.particles.indexOf(p), 1.0F), (int) (170.0F * p.alpha * size)));
                            }
                        } else {
                            this.particles.remove(p);
                        }
                }

                return;
            }
        }
    }

    public void onDisable() {
        this.particles.clear();
        super.onDisable();
    }
}
