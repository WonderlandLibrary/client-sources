package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.other.TickEvent;
import dev.excellent.api.event.impl.player.AttackEvent;
import dev.excellent.api.event.impl.render.Render3DLastEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@ModuleInfo(name = "Back Track", description = "Замедляет пакеты таргета тем самым позволяя бить за 6 блоков", category = Category.COMBAT)
public class BackTrack extends Module {
    private final NumberValue range = new NumberValue("Дистанция", this, 3f, 3f, 6f, 0.1f);
    private final NumberValue delay = new NumberValue("Задержка", this, 500f, 100f, 1_000f, 50f);
    private final List<PacketData> queue = new LinkedList<>();
    private Entity target;
    private Vector3d realPos;
    private Vector3d irealPos;
    private final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() == target || event.getTarget().isInvulnerable()) return;

        target = event.getTarget();
        realPos = target.getPositionVec();
        irealPos = realPos;
    };

    private final Listener<PacketEvent> onPacket = event -> {

        if (event.isSent() || !shouldLagging() || mc.isSingleplayer()) {
            return;
        }

        IPacket<?> packet = event.getPacket();

        if (packet instanceof SPlaySoundEffectPacket || packet instanceof SEntityStatusPacket || packet instanceof SUpdateTimePacket || packet instanceof SSpawnParticlePacket || packet instanceof SAnimateHandPacket) {
            return;
        }

        if (packet instanceof SPlayerPositionLookPacket || packet instanceof SDisconnectPacket || (packet instanceof SEntityVelocityPacket velocityPacket && (velocityPacket.getEntityID() == mc.player.getEntityId()))) {
            reset();
            return;
        }

        if (packet instanceof SEntityTeleportPacket wrapper && wrapper.getEntityId() == target.getEntityId()) {
            realPos = new Vector3d(wrapper.getX(), wrapper.getY(), wrapper.getZ());
        }

        if (packet instanceof SEntityPacket wrapper) {
            if (wrapper.entityId == target.getEntityId()) {
                realPos = realPos.add(new Vector3d(
                        wrapper.posX / 4096.0D,
                        wrapper.posY / 4096.0D,
                        wrapper.posZ / 4096.0D
                ));
            }
        }

        event.cancel();

        synchronized (queue) {
            if (event.isReceive())
                queue.add(new PacketData(packet, System.currentTimeMillis()));
        }
    };

    private final Listener<TickEvent> onTick = event -> {
        if (mc.player == null) return;
        if (queue.isEmpty() && isTargetNull() || mc.isSingleplayer()) {
            return;
        }

        if (shouldLagging()) {
            handle(false);
        } else {
            reset();
        }
    };

    private final Listener<Render3DLastEvent> onRender3D = event -> {
        if (realPos == null || mc.isSingleplayer()) {
            return;
        }
        if (target instanceof LivingEntity wrapper) {
            double half = target.getWidth() / 2;

            if (irealPos == null || realPos.distanceTo(irealPos) >= 1F) {
                irealPos = realPos;
            }
            irealPos = fast(irealPos, realPos, 15);
            int hurtTime = wrapper.hurtTime;
            RenderUtil.Render3D.drawBox(event.getMatrix(), new AxisAlignedBB(
                            irealPos.getX() - half, irealPos.getY(), irealPos.getZ() - half,
                            irealPos.getX() + half, irealPos.getY() + target.getHeight(), irealPos.getZ() + half),
                    ColorUtil.interpolate(ColorUtil.RED, getTheme().getClientColor().hashCode(), 1 - (hurtTime / 10F)));
        }
    };

    public Vector3d fast(Vector3d end, Vector3d start, float multiple) {
        return new Vector3d(
                fast((float) end.getX(), (float) start.getX(), multiple),
                fast((float) end.getY(), (float) start.getY(), multiple),
                fast((float) end.getZ(), (float) start.getZ(), multiple));
    }

    public float fast(float end, float start, float multiple) {
        return (1 - MathHelper.clamp((float) (deltaTime() * multiple), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }

    public double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    private void handle(boolean all) {
        synchronized (queue) {
            Iterator<PacketData> iterator = queue.iterator();

            while (iterator.hasNext()) {
                PacketData packetData = iterator.next();

                if (realPos == null) {
                    try {
                        NetworkManager.processPacket(packetData.packet(), mc.player.connection);
                    } catch (ThreadQuickExitException ignored) {
                    }
                    return;
                } else {
                    double factor = mc.player.getPositionVec().distanceTo(realPos) / range.getValue().doubleValue();

                    if (!all && packetData.timestamp() + delay.getValue().longValue() * factor > System.currentTimeMillis())
                        break;

                    try {
                        NetworkManager.processPacket(packetData.packet(), mc.player.connection);
                    } catch (ThreadQuickExitException ignored) {
                    }
                }
                iterator.remove();
            }
        }
    }

    private boolean isTargetNull() {
        return target == null && realPos == null;
    }

    private boolean shouldLagging() {
        if (isTargetNull() || mc.player == null) return false;
        return target != null && target.isAlive() && !target.isInvulnerable() && mc.player.getPositionVec().distanceTo(realPos) <= range.getValue().doubleValue();
    }

    private void reset() {
        handle(true);
        target = null;
        realPos = null;
        irealPos = null;
    }

    private record PacketData(IPacket<?> packet, long timestamp) {
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        if (mc.isSingleplayer()) {
            return;
        }
        reset();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        if (mc.isSingleplayer()) {
            return;
        }
        reset();
    }
}