package net.shoreline.client.api.manager.player.rotation;

import net.minecraft.client.input.KeyboardInput;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.impl.event.entity.UpdateVelocityEvent;
import net.shoreline.client.impl.event.entity.player.PlayerJumpEvent;
import net.shoreline.client.impl.event.keyboard.KeyboardInputEvent;
import net.shoreline.client.impl.event.keyboard.KeyboardTickEvent;
import net.shoreline.client.impl.event.network.MovementPacketsEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.event.render.entity.RenderPlayerEvent;
import net.shoreline.client.init.Modules;
import net.shoreline.client.mixin.accessor.AccessorPlayerMoveC2SPacket;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author linus, bon55
 * @since 1.0
 */
public class RotationManager implements Globals
{
    private static final Map<String, Integer> ROTATE_PRIORITY = new HashMap<>();
    //
    private float yaw, pitch;
    //
    private RotationRequest rotation;
    private RotationModule rotateModule;
    private final List<RotationRequest> requests = new ArrayList<>();
    private final Timer rotateTimer = new CacheTimer();

    /**
     *
     */
    public RotationManager()
    {
        Shoreline.EVENT_HANDLER.subscribe(this);
        //
        ROTATE_PRIORITY.put("Surround", 1000);
        ROTATE_PRIORITY.put("Speedmine", 950);
        ROTATE_PRIORITY.put("AutoCrystal", 900);
        ROTATE_PRIORITY.put("Aura", 800);
        // AntiAim should always have lowest prio?
        ROTATE_PRIORITY.put("AntiAim", 50);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        if (mc.player == null || mc.world == null)
        {
            return;
        }
        if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook())
        {
            yaw = packet.getYaw(0.0f);
            pitch = packet.getPitch(0.0f);
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onMovementPackets(MovementPacketsEvent event)
    {
        requests.removeIf(r -> System.currentTimeMillis() - r.getTime() > 500);
        float vanillaYaw = mc.player.getYaw();
        float vanillaPitch = mc.player.getPitch();
        if (requests.isEmpty())
        {
            rotation = null;
            rotateModule = null;
            return;
        }
        RotationRequest request = getRotationRequest();
        if (request == null)
        {
            if (isDoneRotating())
            {
                rotation = null;
                rotateModule = null;
                return;
            }
        }
        else
        {
            rotation = request;
            rotateModule = rotation.getModule();
        }
        // fixes flags for aim % 360
        float serverYawChange = rotation.getYaw() - getWrappedYaw();
        float serverPitchChange = rotation.getPitch() - getPitch();
        float yaw1 = yaw + serverYawChange;
        float pitch1 = pitch + serverPitchChange;
        rotateTimer.reset();
        event.cancel();
        event.setYaw(yaw1);
        event.setPitch(pitch1);
    }

    @Nullable
    private RotationRequest getRotationRequest()
    {
        if (requests.isEmpty())
        {
            return null;
        }
        RotationRequest rotationRequest = null;
        int priority = 0;
        long time = 0;
        for (RotationRequest request : requests)
        {
            if (!request.getModule().isEnabled())
            {
                continue;
            }
            if (request.getPriority() > priority ||
                    request.getPriority() == priority && request.getTime() > time)
            {
                rotationRequest = request;
                priority = request.getPriority();
                time = request.getTime();
            }
        }
        return rotationRequest;
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onKeyboardTick(KeyboardTickEvent event)
    {
        if (rotation != null && mc.player != null
                && Modules.ROTATIONS.getMovementFix())
        {
            event.cancel();
            float forward = mc.player.input.movementForward;
            float strafe = mc.player.input.movementSideways;
            float offset = (getWrappedYaw() - rotation.getYaw()) * MathHelper.RADIANS_PER_DEGREE;
            double cosValue = MathHelper.cos(offset);
            double sinValue = MathHelper.sin(offset);
            mc.player.input.movementForward = Math.round(forward * cosValue + strafe * sinValue);
            mc.player.input.movementSideways = Math.round(strafe * cosValue - forward * sinValue);
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onUpdateVelocity(UpdateVelocityEvent event)
    {
        if (rotation != null && Modules.ROTATIONS.getMovementFix())
        {
            event.cancel();
            event.setYaw(rotation.getYaw());
        }
    }

    private float prevYaw;

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerJump(PlayerJumpEvent event)
    {
        if (rotation != null && Modules.ROTATIONS.getMovementFix())
        {
            if (event.getStage() == EventStage.PRE)
            {
                prevYaw = mc.player.getYaw();
                mc.player.setYaw(rotation.getYaw());
            }
            else
            {
                mc.player.setYaw(prevYaw);
            }
        }
    }

    // public void onJump

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onRenderPlayer(RenderPlayerEvent event)
    {
        if (event.getEntity() == mc.player)
        {
            // Match packet server rotations
            event.setYaw(getWrappedYaw());
            event.setPitch(getPitch());
            event.cancel();
        }
    }

    public void setRotation(float yaw, float pitch)
    {

    }

    /**
     *
     * @param requester
     * @param yaw
     * @param pitch
     */
    public void setRotation(RotationModule requester, float yaw, float pitch)
    {
        for (RotationRequest r : requests)
        {
            if (requester == r.getModule())
            {
                // r.setPriority();
                r.setTime(System.currentTimeMillis());
                r.setYaw(yaw);
                r.setPitch(pitch);
                return;
            }
        }
        requests.add(new RotationRequest(requester,
                ROTATE_PRIORITY.getOrDefault(requester.getName(), 100), yaw, pitch));
    }

    /**
     *
     * @param request
     */
    public boolean removeRotation(RotationRequest request)
    {
        return requests.remove(request);
    }

    /**
     *
     * @param requester
     */
    public void removeRotation(RotationModule requester)
    {
        requests.removeIf(r -> requester == r.getModule());
    }

    /**
     *
     * @param yaw
     * @param pitch
     */
    public void setRotationClient(float yaw, float pitch)
    {
        if (mc.player == null)
        {
            return;
        }
        mc.player.setYaw(yaw);
        mc.player.setHeadYaw(yaw);
        mc.player.setBodyYaw(yaw);
        mc.player.setPitch(pitch);
    }

    /**
     *
     * @return
     */
    public boolean isDoneRotating()
    {
        return rotateTimer.passed(Modules.ROTATIONS.getPreserveTicks() * 50.0f);
    }

    /**
     *
     * @return
     */
    public RotationModule getRotatingModule()
    {
        return rotateModule;
    }

    /**
     *
     * @return
     */
    public float getYaw()
    {
        return yaw;
    }

    /**
     *
     * @return
     */
    public float getWrappedYaw()
    {
        return MathHelper.wrapDegrees(yaw);
    }

    /**
     *
     * @return
     */
    public float getPitch()
    {
        return pitch;
    }
}
