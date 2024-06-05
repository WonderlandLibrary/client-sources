package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.PostUpdateListener;
import de.dietrichpaul.clientbase.event.TargetPickListener;
import de.dietrichpaul.clientbase.event.lag.DelayIncomingPacketListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class BacktrackHack extends Hack implements DelayIncomingPacketListener, PostUpdateListener {

    private BooleanProperty vanillaDistanceLimit = new BooleanProperty("VanillaDistanceLimit", true);

    private boolean forced;
    private boolean trackingBack;
    private Entity target;
    private TrackedPosition trackedPosition;

    public BacktrackHack() {
        super("Backtrack", HackCategory.COMBAT);
        addProperty(vanillaDistanceLimit);
    }

    @Override
    protected void onEnable() {
        trackingBack = false;
        forced = false;
        ClientBase.INSTANCE.getEventDispatcher().subscribe(DelayIncomingPacketListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(PostUpdateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(DelayIncomingPacketListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(PostUpdateListener.class, this);
        if (trackingBack || forced) {
            ClientBase.INSTANCE.getLagEngine().release();
        }
    }

    private boolean isForceKeyPressed() {
        return GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
    }

    public void stopBacktracking() {
        if (trackingBack) {
            ClientBase.INSTANCE.getLagEngine().release();
            trackingBack = false;
        }
    }

    @Override
    public void onDelayIncomingPacket(DelayIncomingPacketEvent event) {
        if (isForceKeyPressed()) forced = true;
        if (forced) {
            event.stopTraffic();
            return;
        }

        Entity prevTarget = target;
        target = ClientBase.INSTANCE.getEventDispatcher().post(new TargetPickListener.TargetPickEvent(null)).getTarget();
        if (prevTarget != target)
            trackedPosition = null;
        if (!(target instanceof LivingEntity)) {
            stopBacktracking();
            return;
        }

        if (trackedPosition == null) {
            trackedPosition = new TrackedPosition();
            trackedPosition.setPos(target.getTrackedPosition().pos);
        }
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        if (event.getPacket() instanceof EntityS2CPacket packet) {
            Entity entity = packet.getEntity(mc.world);
            if (entity == target && !entity.isLogicalSideForUpdatingMovement()) {
                if (packet.isPositionChanged()) {
                    Vec3d vec3d = trackedPosition.withDelta(packet.getDeltaX(), packet.getDeltaY(), packet.getDeltaZ());
                    trackedPosition.setPos(vec3d);
                }
            }
        } else if (event.getPacket() instanceof EntityPositionS2CPacket packet) {
            Entity entity = mc.world.getEntityById(packet.getId());
            if (entity == target) {
                double d = packet.getX();
                double e = packet.getY();
                double f = packet.getZ();
                trackedPosition.setPos(new Vec3d(d, e, f));
            }
        } else if (trackingBack && event.getPacket() instanceof EntitiesDestroyS2CPacket packet) {
            if (packet.getEntityIds().contains(target.getId())) {
                stopBacktracking();
                return;
            }
        } else if (trackingBack && event.getPacket() instanceof DeathMessageS2CPacket) {
            stopBacktracking();
            return;
        }

        boolean vanillaHit = !this.vanillaDistanceLimit.getState() || camera.squaredDistanceTo(trackedPosition.pos) < MathHelper.square(6.0);
        Vec3d bestVec = MathUtil.clamp(camera, target.dimensions.getBoxAt(trackedPosition.pos));
        if (camera.distanceTo(bestVec) >= 3 && vanillaHit) {
            event.stopTraffic();
            trackingBack = true;
        } else {
            stopBacktracking();
        }
    }

    @Override
    public void onPostUpdate() {
        if (forced && !isForceKeyPressed()) {
            ClientBase.INSTANCE.getLagEngine().release();
            forced = false;
        } else if (trackingBack && target != null) {
            Vec3d camera = mc.player.getCameraPosVec(1.0F);
            boolean vanillaHit = !this.vanillaDistanceLimit.getState() || camera.squaredDistanceTo(trackedPosition.pos) < MathHelper.square(6.0);
            Vec3d bestVec = MathUtil.clamp(camera, target.dimensions.getBoxAt(trackedPosition.pos));
            if (camera.distanceTo(bestVec) < 3 && vanillaHit)
                stopBacktracking();
        }
    }
}
