/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.events.AttackEvent;
import mpp.venusfr.events.CameraEvent;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

@FunctionRegister(name="DeathEffect", type=Category.Visual)
public class DeathEffect
extends Function {
    private Animation animate = new Animation();
    private boolean useAnimation;
    LivingEntity target;
    long time;
    public StopWatch stopWatch = new StopWatch();
    private float yaw;
    private float pitch;
    private final List<Vector3d> position = new ArrayList<Vector3d>();
    private int current;
    private Vector3d setPosition;
    public float back;
    public Vector2f last;

    @Subscribe
    public void onPacket(AttackEvent attackEvent) {
        Minecraft minecraft = mc;
        if (DeathEffect.mc.player != null) {
            minecraft = mc;
            if (DeathEffect.mc.world != null) {
                if (attackEvent.entity instanceof PlayerEntity) {
                    this.target = (LivingEntity)attackEvent.entity;
                }
                this.time = System.currentTimeMillis();
                return;
            }
        }
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        Minecraft minecraft = mc;
        if (DeathEffect.mc.player != null) {
            minecraft = mc;
            if (DeathEffect.mc.world != null) {
                IPacket<?> iPacket = eventPacket.getPacket();
                if (iPacket instanceof SDestroyEntitiesPacket) {
                    SDestroyEntitiesPacket sDestroyEntitiesPacket = (SDestroyEntitiesPacket)iPacket;
                    for (int n : sDestroyEntitiesPacket.getEntityIDs()) {
                        if (this.target == null) continue;
                        Minecraft minecraft2 = mc;
                        if (n == DeathEffect.mc.player.getEntityId() || this.time + 400L < System.currentTimeMillis() || this.target.getEntityId() != n) continue;
                        minecraft = mc;
                        if (!(((LivingEntity)DeathEffect.mc.world.getEntityByID(n)).getHealth() < 5.0f)) continue;
                        this.onKill(this.target);
                        this.target = null;
                    }
                }
                return;
            }
        }
    }

    @Subscribe
    public void onUpdate(EventMotion eventMotion) {
        Minecraft minecraft = mc;
        if (DeathEffect.mc.player != null) {
            minecraft = mc;
            if (DeathEffect.mc.world != null) {
                if (this.useAnimation) {
                    minecraft = mc;
                    if (DeathEffect.mc.player.ticksExisted % 5 == 0) {
                        ++this.current;
                    }
                    Minecraft minecraft2 = mc;
                    double d = DeathEffect.mc.player.getPosX();
                    Minecraft minecraft3 = mc;
                    d = MathUtil.interpolate(d, DeathEffect.mc.player.lastTickPosX, (double)mc.getRenderPartialTicks());
                    minecraft3 = mc;
                    double d2 = DeathEffect.mc.player.getPosY();
                    Minecraft minecraft4 = mc;
                    d2 = MathUtil.interpolate(d2, DeathEffect.mc.player.lastTickPosY, (double)mc.getRenderPartialTicks());
                    minecraft4 = mc;
                    double d3 = DeathEffect.mc.player.getPosZ();
                    Minecraft minecraft5 = mc;
                    Vector3d vector3d = new Vector3d(d, d2, MathUtil.interpolate(d3, DeathEffect.mc.player.lastTickPosZ, (double)mc.getRenderPartialTicks()));
                    minecraft2 = mc;
                    Vector3d vector3d2 = vector3d.add(0.0, DeathEffect.mc.player.getEyeHeight(), 0.0);
                    this.position.add(vector3d2);
                }
                if (this.target != null && this.time + 1000L >= System.currentTimeMillis() && this.target.getHealth() <= 0.0f) {
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
                return;
            }
        }
    }

    @Subscribe
    public void onCameraController(CameraEvent cameraEvent) {
        if (this.useAnimation) {
            DeathEffect.mc.getRenderManager().info.setDirection((float)((double)this.yaw + 6.0 * this.animate.getValue()), (float)((double)this.pitch + 6.0 * this.animate.getValue()));
            this.back = MathUtil.fast(this.back, this.stopWatch.isReached(1000L) ? 1.0f : 0.0f, 10.0f);
            Minecraft minecraft = mc;
            double d = DeathEffect.mc.player.getPosX();
            Minecraft minecraft2 = mc;
            d = MathUtil.interpolate(d, DeathEffect.mc.player.lastTickPosX, (double)mc.getRenderPartialTicks());
            minecraft2 = mc;
            double d2 = DeathEffect.mc.player.getPosY();
            Minecraft minecraft3 = mc;
            d2 = MathUtil.interpolate(d2, DeathEffect.mc.player.lastTickPosY, (double)mc.getRenderPartialTicks());
            minecraft3 = mc;
            double d3 = DeathEffect.mc.player.getPosZ();
            Minecraft minecraft4 = mc;
            Vector3d vector3d = new Vector3d(d, d2, MathUtil.interpolate(d3, DeathEffect.mc.player.lastTickPosZ, (double)mc.getRenderPartialTicks()));
            minecraft = mc;
            Vector3d vector3d2 = vector3d.add(0.0, DeathEffect.mc.player.getEyeHeight(), 0.0);
            if (this.setPosition != null) {
                ActiveRenderInfo activeRenderInfo = DeathEffect.mc.getRenderManager().info;
                double d4 = (float)((double)this.yaw + 6.0 * this.animate.getValue());
                minecraft = mc;
                float f = (float)MathUtil.interpolate(d4, DeathEffect.mc.player.getYaw(cameraEvent.partialTicks), (double)(1.0f - this.back));
                d = (float)((double)this.pitch + 6.0 * this.animate.getValue());
                minecraft2 = mc;
                activeRenderInfo.setDirection(f, (float)MathUtil.interpolate(d, DeathEffect.mc.player.getPitch(cameraEvent.partialTicks), (double)(1.0f - this.back)));
                DeathEffect.mc.getRenderManager().info.setPosition(MathUtil.interpolate(this.setPosition, vector3d2, 1.0f - this.back));
            }
            DeathEffect.mc.getRenderManager().info.moveForward(1.0 * this.animate.getValue());
        }
    }

    @Subscribe
    public void onDisplay(EventDisplay eventDisplay) {
        Minecraft minecraft = mc;
        if (DeathEffect.mc.player != null) {
            minecraft = mc;
            if (DeathEffect.mc.world != null && eventDisplay.getType() == EventDisplay.Type.POST) {
                this.animate.update();
                if (this.useAnimation && this.setPosition != null && this.position.size() > 1) {
                    this.setPosition = MathUtil.fast(this.setPosition, this.position.get(this.current), 1.0f);
                    DisplayUtils.drawWhite((float)this.animate.getValue());
                }
                return;
            }
        }
    }

    public void onKill(LivingEntity livingEntity) {
        Vector3d vector3d;
        this.position.clear();
        this.current = 0;
        this.animate = this.animate.animate(1.0, 1.0, Easings.CIRC_OUT);
        this.useAnimation = true;
        this.stopWatch.reset();
        Minecraft minecraft = mc;
        double d = DeathEffect.mc.player.getPosX();
        Minecraft minecraft2 = mc;
        d = MathUtil.interpolate(d, DeathEffect.mc.player.lastTickPosX, (double)mc.getRenderPartialTicks());
        minecraft2 = mc;
        double d2 = DeathEffect.mc.player.getPosY();
        Minecraft minecraft3 = mc;
        d2 = MathUtil.interpolate(d2, DeathEffect.mc.player.lastTickPosY, (double)mc.getRenderPartialTicks());
        minecraft3 = mc;
        double d3 = DeathEffect.mc.player.getPosZ();
        Minecraft minecraft4 = mc;
        Vector3d vector3d2 = new Vector3d(d, d2, MathUtil.interpolate(d3, DeathEffect.mc.player.lastTickPosZ, (double)mc.getRenderPartialTicks()));
        minecraft = mc;
        this.setPosition = vector3d = vector3d2.add(0.0, DeathEffect.mc.player.getEyeHeight(), 0.0);
        Minecraft minecraft5 = mc;
        this.yaw = DeathEffect.mc.player.getYaw(mc.getRenderPartialTicks());
        minecraft5 = mc;
        this.pitch = DeathEffect.mc.player.getPitch(mc.getRenderPartialTicks());
    }
}

