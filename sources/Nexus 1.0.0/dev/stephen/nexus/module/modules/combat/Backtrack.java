package dev.stephen.nexus.module.modules.combat;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.impl.player.EventAttack;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.event.impl.world.EventWorldChange;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.CombatUtils;
import dev.stephen.nexus.utils.mc.DelayData;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.render.RenderUtils;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Backtrack extends Module {
    public static final RangeSetting range = new RangeSetting("Range", 0, 6, 2.9, 4.5, 0.1);
    public static final RangeSetting delay = new RangeSetting("Delay", 0, 1000, 100, 150, 1);

    private final ConcurrentLinkedQueue<DelayData> packetQueue = new ConcurrentLinkedQueue<>();

    private TrackedPosition position = null;
    private Entity target = null;
    private long currentDelay = 0L;

    public Backtrack() {
        super("Backtrack", "", 0, ModuleCategory.COMBAT);
        this.addSettings(range, delay);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        synchronized (packetQueue) {
            if (!event.isCancelled() && event.getOrder() == TransferOrder.RECEIVE) {
                if (packetQueue.isEmpty() && !shouldCancelPackets()) {
                    return;
                }

                Packet<?> packet = event.getPacket();

                if (packet instanceof ChatMessageC2SPacket || packet instanceof GameMessageS2CPacket || packet instanceof CommandExecutionC2SPacket) {
                    return;
                }

                if (packet instanceof PlayerPositionLookS2CPacket || packet instanceof DisconnectS2CPacket) {
                    clear(true);
                    return;
                }

                if (packet instanceof PlaySoundS2CPacket && ((PlaySoundS2CPacket) packet).getSound().value() == SoundEvents.ENTITY_PLAYER_HURT) {
                    return;
                }

                if (packet instanceof HealthUpdateS2CPacket && ((HealthUpdateS2CPacket) packet).getHealth() <= 0) {
                    clear(true);
                    return;
                }

                boolean entityPacket = packet instanceof EntityS2CPacket && ((EntityS2CPacket) packet).getEntity(MinecraftClient.getInstance().world) == target;
                boolean positionPacket = packet instanceof EntityPositionS2CPacket && ((EntityPositionS2CPacket) packet).getId() == target.getId();

                if (entityPacket || positionPacket) {
                    Vec3d pos = packet instanceof EntityS2CPacket ? position.withDelta(((EntityS2CPacket) packet).getDeltaX(), ((EntityS2CPacket) packet).getDeltaY(), ((EntityS2CPacket) packet).getDeltaZ()) : new Vec3d(((EntityPositionS2CPacket) packet).getX(), ((EntityPositionS2CPacket) packet).getY(), ((EntityPositionS2CPacket) packet).getZ());

                    position.setPos(pos);

                    if (CombatUtils.getDistanceToPos(pos) < CombatUtils.getDistanceToEntity(target)) {
                        processPackets(true);
                        return;
                    }
                }

                event.cancel();
                packetQueue.add(new DelayData(packet, System.currentTimeMillis()));
            }
        }
    };

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (isNull()) {
            return;
        }
        if (target == null) {
            return;
        }
        if (position == null) {
            return;
        }

        EntityDimensions dimensions = target.getDimensions(target.getPose());
        Box box = dimensions.getBoxAt(position.pos);

        RenderUtils.draw3DBox(event.getMatrixStack().peek().getPositionMatrix(), box, ThemeUtils.getMainColor());
    };


    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(String.valueOf(currentDelay));

        if (isNull()) {
            clear(false);
            return;
        }

        if (shouldCancelPackets()) {
            processPackets(false);
        } else {
            clear(true);
        }
    };

    @EventLink
    public final Listener<EventWorldChange> eventWorldChangeListener = event -> {
        if (event.getWorld() == null) {
            clear(false);
        }
    };

    @EventLink
    public final Listener<EventAttack> attackEventListener = event -> {
        Entity enemy = event.getTarget();

        if (!shouldConsiderAsEnemy(enemy)) {
            return;
        }

        if (enemy != target) {
            clear(true);

            position = new TrackedPosition();
            position.setPos(enemy.getTrackedPosition().pos);
        }

        target = enemy;
    };

    @Override
    public void onEnable() {
        clear(false);

        currentDelay = (long) RandomUtil.randomBetween(delay.getValueMin(), delay.getValueMax());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        clear(true);
        super.onDisable();
    }

    private void processPackets(boolean clear) {
        synchronized (packetQueue) {
            if (!packetQueue.isEmpty()) {
                packetQueue.removeIf(data -> {
                    if (clear || data.getDelay() <= System.currentTimeMillis() - currentDelay) {
                        if (!isNull()) {
                            PacketUtils.handlePacket(data.getPacket());
                            currentDelay = (long) RandomUtil.randomBetween(delay.getValueMin(), delay.getValueMax());
                        }
                        return true;
                    }
                    return false;
                });
            }
        }
    }

    private void clear(boolean handlePackets) {
        if (handlePackets) {
            processPackets(true);
        } else {
            synchronized (packetQueue) {
                packetQueue.clear();
            }
        }

        target = null;
        position = null;
    }

    private boolean shouldConsiderAsEnemy(Entity target) {
        return target instanceof PlayerEntity && CombatUtils.getDistanceToEntity(target) <= range.getValueMax() && CombatUtils.getDistanceToEntity(target) >= range.getValueMin() && mc.player.age > 10;
    }

    private boolean shouldCancelPackets() {
        return target != null && target.isAlive() && shouldConsiderAsEnemy(target);
    }
}
