package wtf.shiyeno.modules.impl.combat;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.IMinecraft;
import wtf.shiyeno.util.math.PlayerPositionTracker;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.ProjectionUtils;
import wtf.shiyeno.util.render.RenderUtil.Render2D;
import wtf.shiyeno.util.render.animation.AnimationMath;
import wtf.shiyeno.util.world.WorldUtil.TotemUtil;
import org.joml.Vector2d;

@FunctionAnnotation(
        name = "Particles",
        type = Type.Combat
)
public class Particles extends Function {
    private final ModeSetting rotationMode = new ModeSetting("Мод", "Dollar", new String[]{"Dollar", "Snowflake", "Heart", "Star"});
    private final SliderSetting size = new SliderSetting("Размер партиклов", 4.5F, 0.1F, 20.0F, 0.1F);
    CopyOnWriteArrayList<Point> points = new CopyOnWriteArrayList();
    public Particles() {
        this.addSettings(new Setting[]{this.rotationMode, this.size});
    }

    public void onEvent(Event event) {
        Iterator var3;
        if (event instanceof EventMotion e) {
            var3 = mc.world.getAllEntities().iterator();

            while(var3.hasNext()) {
                Entity entity = (Entity)var3.next();
                if (entity instanceof LivingEntity l) {
                    if (l.hurtTime == 9) {
                        this.createPoints(l.getPositionVec().add(0.0, 1.0, 0.0));
                    }
                }
            }
        }

        if (event instanceof EventRender e) {
            if (e.isRender2D()) {
                if (this.points.size() > 100) {
                    this.points.remove(0);
                }

                var3 = this.points.iterator();

                while(true) {
                    while(var3.hasNext()) {
                        Point point = (Point)var3.next();
                        long alive = System.currentTimeMillis() - point.createdTime;
                        if (alive <= point.aliveTime) {
                            Minecraft var10000 = mc;
                            if (Minecraft.player.canVectorBeSeenFixed(point.position) && PlayerPositionTracker.isInView(point.position)) {
                                Vector2d pos = ProjectionUtils.project(point.position.x, point.position.y, point.position.z);
                                if (pos != null) {
                                    point.update();
                                    BloomHelper.registerRenderCall(() -> {
                                        Render2D.drawImage(new ResourceLocation("shiyeno/images/" + this.rotationMode.get() + ".png"), (float)pos.x, (float)pos.y, this.size.getValue().floatValue() + 0.0F, this.size.getValue().floatValue() + 0.0F, ColorUtil.getColorStyle((float)this.points.indexOf(point)));
                                    });
                                    Render2D.drawImage(new ResourceLocation("shiyeno/images/" + this.rotationMode.get() + ".png"), (float)pos.x, (float)pos.y, this.size.getValue().floatValue() + 0.0F, this.size.getValue().floatValue() + 0.0F, ColorUtil.getColorStyle((float)this.points.indexOf(point)));
                                }
                                continue;
                            }
                        }

                        this.points.remove(point);
                    }

                    return;
                }
            }
        }

    }

    private void createPoints(Vector3d position) {
        for(int i = 0; i < ThreadLocalRandom.current().nextInt(8, 20); ++i) {
            this.points.add(new Point(position));
        }

    }

    private final class Point {
        public Vector3d position;
        public Vector3d motion;
        public Vector3d animatedMotion;
        public long aliveTime;
        public float size;
        public long createdTime = System.currentTimeMillis();

        public Point(Vector3d position) {
            this.position = new Vector3d(position.x, position.y, position.z);
            this.motion = new Vector3d((double)ThreadLocalRandom.current().nextFloat(-0.01F, 0.01F), 0.0, (double)ThreadLocalRandom.current().nextFloat(-0.01F, 0.01F));
            this.animatedMotion = new Vector3d(0.0, 0.0, 0.0);
            this.size = ThreadLocalRandom.current().nextFloat(4.0F, 6.0F);
            this.aliveTime = ThreadLocalRandom.current().nextLong(3000L, 10000L);
        }

        public void update() {
            Vector3d var10000;
            if (this.isGround()) {
                this.motion.y = 1.0;
                var10000 = this.motion;
                var10000.x *= 1.05;
                var10000 = this.motion;
                var10000.z *= 1.05;
            } else {
                this.motion.y = -0.01;
                var10000 = this.motion;
                var10000.y *= 2.0;
            }

            this.animatedMotion.x = (double)AnimationMath.fast((float)this.animatedMotion.x, (float)this.motion.x, 3.0F);
            this.animatedMotion.y = (double)AnimationMath.fast((float)this.animatedMotion.y, (float)this.motion.y, 3.0F);
            this.animatedMotion.z = (double)AnimationMath.fast((float)this.animatedMotion.z, (float)this.motion.z, 3.0F);
            this.position = this.position.add(this.animatedMotion);
        }

        boolean isGround() {
            Vector3d position = this.position.add(this.animatedMotion);
            AxisAlignedBB bb = new AxisAlignedBB(position.x - 0.1, position.y - 0.1, position.z - 0.1, position.x + 0.1, position.y + 0.1, position.z + 0.1);
            return TotemUtil.getSphere(new BlockPos(position), 2.0F, 4, false, true, 0).stream().anyMatch((blockPos) -> {
                return !IMinecraft.mc.world.getBlockState(blockPos).isAir() && bb.intersects(new AxisAlignedBB(blockPos)) && AxisAlignedBB.calcSideHit(new AxisAlignedBB(blockPos.add(0, 1, 0)), position, new double[]{2.0}, (Direction)null, 0.10000000149011612, 0.10000000149011612, 0.10000000149011612) == Direction.DOWN;
            });
        }
    }
}