//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventDisplay.Type;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.projections.ProjectionUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(
        name = "EntityTrails",
        type = Category.Render
)
public class Trails2 extends Function {
    private final ModeSetting setting = new ModeSetting("Вид", "Орбизы", new String[]{"Орбизы", "Сердечки", "Звёзды", "Снежинки"});
    private final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList();
    private final int maxParticles = 70;

    public Trails2() {
        this.addSettings(new Setting[]{this.setting});
    }

    private boolean isInView(Vector3d pos) {
        WorldRenderer.frustum.setCameraPosition(IMinecraft.mc.getRenderManager().info.getProjectedView().x, IMinecraft.mc.getRenderManager().info.getProjectedView().y, IMinecraft.mc.getRenderManager().info.getProjectedView().z);
        return WorldRenderer.frustum.isBoundingBoxInFrustum(new AxisAlignedBB(pos.add(0.2, 0.1, 0.1), pos.add(0.2, 0.2, 0.2)));
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.player != null && mc.world != null && e.getType() == Type.PRE) {
            if (this.particles.size() < 70) {
                this.particles.add(new Particle());
            }

            Iterator var2 = this.particles.iterator();

            while(true) {
                while(var2.hasNext()) {
                    Particle p = (Particle)var2.next();
                    if (System.currentTimeMillis() - p.time <= 12200L && !(mc.player.getPositionVec().distanceTo(p.pos) > 5.0) && this.isInView(p.pos) && mc.player.canEntityBeSeen(p.pos)) {
                        p.update();
                        Vector2f pos = ProjectionUtil.project(p.pos.x + 0.5, p.pos.y, p.pos.z + 0.5);
                        float size = 1.0F - (float)(System.currentTimeMillis() - p.time) / 12200.0F;
                        final ResourceLocation star = new ResourceLocation("Wiksi/images/star.png");
                        switch ((String)this.setting.get()) {
                            case "Сердечки":
                                Fonts.damage.drawText(e.getMatrixStack(), "B", pos.x - 1.0F * size, pos.y - 3.0F * size, ColorUtils.setAlpha(HUD.getColor(this.particles.indexOf(p), 1.0F), (int)(155.0F * p.alpha * size)), 8.0F * size, 0.05F);
                                break;
                            case "Снежинки":
                                Fonts.damage.drawText(e.getMatrixStack(), "A", pos.x - 1.0F * size, pos.y - 3.0F * size, ColorUtils.setAlpha(HUD.getColor(this.particles.indexOf(p), 1.0F), (int)(155.0F * p.alpha * size)), 8.0F * size, 0.05F);
                                break;
                            case "Звёзды":
                                DisplayUtils.drawImage(star, pos.x, pos.y,  10,  10, ColorUtils.setAlpha(HUD.getColor(particles.indexOf(p), 1), (int) ((255 * p.alpha) * size)));
                                break;
                            case "Орбизы":
                                DisplayUtils.drawCircle(pos.x + 10.0F, pos.y, 5.0F * size, ColorUtils.setAlpha(HUD.getColor(this.particles.indexOf(p), 1.0F), (int)(155.0F * p.alpha * size)));
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

    private class Particle {
        private Vector3d pos;
        private Vector3d end;
        private long time;
        private long collisionTime = -1;
        private Vector3d velocity;
        private float alpha;

        public Particle() {
            this.pos = IMinecraft.mc.player.getPositionVec().add((double)(-ThreadLocalRandom.current().nextFloat()), (double)ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F), (double)(-ThreadLocalRandom.current().nextFloat()));
            this.end = this.pos.add((double)(-ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F)), (double)(-ThreadLocalRandom.current().nextFloat()), (double)(-ThreadLocalRandom.current().nextFloat()));
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
                this.alpha = MathUtil.fast(this.alpha, 1.0F, 2.0F);
                this.pos = MathUtil.fast(this.pos, this.end, 0.2F);
                long timeSinceCollision = System.currentTimeMillis() - collisionTime;
                alpha = Math.max(0, 1 - (timeSinceCollision / 7000f));
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
