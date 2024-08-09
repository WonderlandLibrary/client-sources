package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.*;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.render.DisplayUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.ArrayList;
import java.util.List;

@FunctionRegister(name = "DeathEffect", type = Category.Render)
public class DeathEffect extends Function {
        private Animation animate = new Animation();
        private boolean useAnimation;
        LivingEntity target;
        long time;
        public StopWatch stopWatch = new StopWatch();
        private float yaw;
        private float pitch;
        private final List<Vector3d> position = new ArrayList();
        private int current;
        private Vector3d setPosition;
        public float back;
        public Vector2f last;

        public DeathEffect() {
        }

        @Subscribe
        public void onPacket(AttackEvent e) {
            if (IMinecraft.mc.player != null && IMinecraft.mc.world != null) {
                if (e.entity instanceof PlayerEntity) {
                    this.target = (LivingEntity)e.entity;
                }

                this.time = System.currentTimeMillis();
            }
        }

        @Subscribe
        public void onPacket(EventPacket e) {
            if (IMinecraft.mc.player != null && IMinecraft.mc.world != null) {
                IPacket var3 = e.getPacket();
                if (var3 instanceof SDestroyEntitiesPacket) {
                    SDestroyEntitiesPacket p = (SDestroyEntitiesPacket)var3;
                    int[] var7 = p.getEntityIDs();
                    int var4 = var7.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        int ids = var7[var5];
                        if (this.target != null && ids != IMinecraft.mc.player.getEntityId() && this.time + 400L >= System.currentTimeMillis() && this.target.getEntityId() == ids && ((LivingEntity)IMinecraft.mc.world.getEntityByID(ids)).getHealth() < 5.0F) {
                            this.onKill(this.target);
                            this.target = null;
                        }
                    }
                }

            }
        }

        @Subscribe
        public void onUpdate(EventMotion e) {
            if (IMinecraft.mc.player != null && IMinecraft.mc.world != null) {
                if (this.useAnimation) {
                    if (IMinecraft.mc.player.ticksExisted % 5 == 0) {
                        ++this.current;
                    }

                    Vector3d player = (new Vector3d(MathUtil.interpolate(IMinecraft.mc.player.getPosX(), IMinecraft.mc.player.lastTickPosX, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosY(), IMinecraft.mc.player.lastTickPosY, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosZ(), IMinecraft.mc.player.lastTickPosZ, (double)IMinecraft.mc.getRenderPartialTicks()))).add(0.0, (double)IMinecraft.mc.player.getEyeHeight(), 0.0);
                    this.position.add(player);
                }

                if (this.target != null && this.time + 1000L >= System.currentTimeMillis() && this.target.getHealth() <= 0.0F) {
                    this.onKill(this.target);
                    this.target = null;
                }

                if (this.stopWatch.isReached(500L)) {
                    this.animate = this.animate.animate(0.0, 1.0, Easings.CIRC_OUT);
                }

                if (this.stopWatch.isReached(1500L)) {
                    this.useAnimation = false;
                    this.last = null;
                }

            }
        }

        @Subscribe
        public void onCameraController(CameraEvent e) {
            if (this.useAnimation) {
                IMinecraft.mc.getRenderManager().info.setDirection((float)((double)this.yaw + 6.0 * this.animate.getValue()), (float)((double)this.pitch + 6.0 * this.animate.getValue()));
                this.back = MathUtil.fast(this.back, this.stopWatch.isReached(1000L) ? 1.0F : 0.0F, 10.0F);
                Vector3d player = (new Vector3d(MathUtil.interpolate(IMinecraft.mc.player.getPosX(), IMinecraft.mc.player.lastTickPosX, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosY(), IMinecraft.mc.player.lastTickPosY, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosZ(), IMinecraft.mc.player.lastTickPosZ, (double)IMinecraft.mc.getRenderPartialTicks()))).add(0.0, (double)IMinecraft.mc.player.getEyeHeight(), 0.0);
                if (this.setPosition != null) {
                    IMinecraft.mc.getRenderManager().info.setDirection((float)MathUtil.interpolate((double)((float)((double)this.yaw + 6.0 * this.animate.getValue())), (double)IMinecraft.mc.player.getYaw(e.partialTicks), (double)(1.0F - this.back)), (float)MathUtil.interpolate((double)((float)((double)this.pitch + 6.0 * this.animate.getValue())), (double)IMinecraft.mc.player.getPitch(e.partialTicks), (double)(1.0F - this.back)));
                    IMinecraft.mc.getRenderManager().info.setPosition(MathUtil.interpolate(this.setPosition, player, 1.0F - this.back));
                }

                IMinecraft.mc.getRenderManager().info.moveForward(1.0 * this.animate.getValue());
            }

        }

        @Subscribe
        public void onDisplay(EventDisplay e) {
            if (IMinecraft.mc.player != null && IMinecraft.mc.world != null && e.getType() == EventDisplay.Type.POST) {
                this.animate.update();
                if (this.useAnimation && this.setPosition != null && this.position.size() > 1) {
                    this.setPosition = MathUtil.fast(this.setPosition, (Vector3d)this.position.get(this.current), 1.0F);
                    DisplayUtils.drawWhite((float)this.animate.getValue());
                }

            }
        }

        public void onKill(LivingEntity entity) {
            this.position.clear();
            this.current = 0;
            this.animate = this.animate.animate(1.0, 1.0, Easings.CIRC_OUT);
            this.useAnimation = true;
            this.stopWatch.reset();
            Vector3d player = (new Vector3d(MathUtil.interpolate(IMinecraft.mc.player.getPosX(), IMinecraft.mc.player.lastTickPosX, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosY(), IMinecraft.mc.player.lastTickPosY, (double)IMinecraft.mc.getRenderPartialTicks()), MathUtil.interpolate(IMinecraft.mc.player.getPosZ(), IMinecraft.mc.player.lastTickPosZ, (double)IMinecraft.mc.getRenderPartialTicks()))).add(0.0, (double)IMinecraft.mc.player.getEyeHeight(), 0.0);
            this.setPosition = player;
            this.yaw = IMinecraft.mc.player.getYaw(IMinecraft.mc.getRenderPartialTicks());
            this.pitch = IMinecraft.mc.player.getPitch(IMinecraft.mc.getRenderPartialTicks());
        }
    }
