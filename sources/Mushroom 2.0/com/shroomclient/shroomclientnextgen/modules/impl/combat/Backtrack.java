package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.mixin.TrackedPositionAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.Nullable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Backtrack",
    category = ModuleCategory.Combat,
    description = "He's 6 blocking",
    uniqueId = "backtrack"
)
public class Backtrack extends Module {

    private final ConcurrentHashMap<Integer, BacktrackState> states =
        new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<TimedPacket> blinkedPackets =
        new ConcurrentLinkedQueue<>();

    @ConfigOption(
        name = "Backtrack Time",
        description = "Delay Packet Time (In MS)",
        min = 0,
        max = 10000,
        order = 1
    )
    public Integer backtrackTime = 300;

    @ConfigParentId("combatBTONLY")
    @ConfigOption(
        name = "Only In Combat",
        description = "Only BackTracks In Combat",
        min = 1,
        precision = 1,
        order = 2
    )
    public Boolean onlyInCombat = true;

    @ConfigChild("combatBTONLY")
    @ConfigOption(
        name = "Min Distance",
        description = "Don't Backtrack If The Real Pos Is Closer Than This",
        min = 1,
        max = 9,
        precision = 1,
        order = 3
    )
    public Double minDistance = 2.9d;

    @ConfigChild("combatBTONLY")
    @ConfigOption(
        name = "Max reach",
        description = "Don't Backtrack If The Real Pos Is Farther Than This",
        min = 1,
        max = 9,
        precision = 1,
        order = 4
    )
    public Double maxReach = 5d;

    long HitTime = 0;
    private @Nullable Entity lastHitEntity = null;

    @Override
    protected void onEnable() {
        //if (!C.isInGame()) {
        //    ModuleManager.setEnabled(Backtrack.class, false, false);
        //}
    }

    private void flushBlinkedPackets() {
        while (!blinkedPackets.isEmpty()) {
            try {
                blinkedPackets.poll().packet.apply(C.mc.getNetworkHandler());
            } catch (Exception ignored) {}
        }
    }

    @Override
    protected void onDisable() {
        disable();
    }

    private void disable() {
        for (BacktrackState state : states.values()) {
            state.flush();
        }
        states.clear();

        flushBlinkedPackets();
    }

    // Returns anyBlinking
    private boolean updateAll() {
        boolean anyBlinking = false;
        for (BacktrackState state : states.values()) {
            state.update();
            if (state.isBlinking()) anyBlinking = true;
        }
        return anyBlinking;
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive.Pre e) {
        if (lastHitEntity != null && onlyInCombat) {
            if (System.currentTimeMillis() - HitTime > backtrackTime) {
                disable();
            }

            if (System.currentTimeMillis() - HitTime > 5000) {
                lastHitEntity = null;
            }
        }

        if (
            !C.mc.isInSingleplayer() && (lastHitEntity != null || !onlyInCombat)
        ) {
            if (e.getPacket() instanceof EntityPositionS2CPacket p) {
                if (
                    !(C.w().getEntityById(p.getId()) instanceof PlayerEntity)
                ) return;

                if (!states.contains(p.getId())) states.put(
                    p.getId(),
                    new BacktrackState(p.getId())
                );
                states.get(p.getId()).add(p);
            } else if (e.getPacket() instanceof EntityS2CPacket p) {
                if (C.w() != null && C.isInGame() && C.p() != null) {
                    int entityId = p.getEntity(C.w()).getId();
                    if (
                        !(C.w().getEntityById(entityId) instanceof PlayerEntity)
                    ) return;

                    if (!states.contains(entityId)) states.put(
                        entityId,
                        new BacktrackState(entityId)
                    );
                    states.get(entityId).add(p);
                }
            }

            if (true || updateAll()) {
                e.setCancelled(true);
                blinkedPackets.add(
                    new TimedPacket(
                        (Packet<ClientPlayPacketListener>) e.getPacket()
                    )
                );
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (!C.isInGame()) return;
        //if (false && !updateAll()) flushBlinkedPackets();
        while (
            !blinkedPackets.isEmpty() &&
            blinkedPackets.peek().msPassed() > backtrackTime
        ) {
            try {
                blinkedPackets.poll().packet.apply(C.mc.getNetworkHandler());
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        for (BacktrackState state : states.values()) {
            if (
                !TargetUtil.isBot(state.ent) &&
                ((lastHitEntity != null &&
                        state.ent.getId() == lastHitEntity.getId()) ||
                    !onlyInCombat)
            ) {
                if (onlyInCombat) {
                    if (
                        MovementUtil.distanceTo(
                            C.p().getPos(),
                            state.getTrackedPos(state.realPosition)
                        ) <
                        minDistance
                    ) {
                        disable();
                        return;
                    }
                    if (
                        MovementUtil.distanceTo(
                            C.p().getPos(),
                            state.getTrackedPos(state.realPosition)
                        ) >
                        maxReach
                    ) {
                        disable();
                        return;
                    }
                }

                state.drawRealPos(e.matrixStack, e.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = C.w().getEntityById(eId);
                HitTime = System.currentTimeMillis();
            }
        }
    }

    private class BacktrackState {

        private final ConcurrentLinkedQueue<
            TimedPacket<ClientPlayPacketListener>
        > packets = new ConcurrentLinkedQueue<>();
        private Entity ent;
        private TrackedPosition lastFlushedPosition;
        private TrackedPosition realPosition;

        public BacktrackState(int entityId) {
            this.ent = C.w().getEntityById(entityId);
            Vec3d p = C.w().getEntityById(entityId).getPos();
            lastFlushedPosition = new TrackedPosition();
            lastFlushedPosition.setPos(p);
            realPosition = new TrackedPosition();
            realPosition.setPos(p);
        }

        public void flush() {
            while (!packets.isEmpty()) {
                try {
                    Packet<ClientPlayPacketListener> p = packets.poll().packet;
                    if (p instanceof EntityPositionS2CPacket pp) {
                        lastFlushedPosition.setPos(
                            new Vec3d(pp.getX(), pp.getY(), pp.getZ())
                        );
                    } else if (p instanceof EntityS2CPacket pp) {
                        lastFlushedPosition.setPos(
                            lastFlushedPosition.withDelta(
                                pp.getDeltaX(),
                                pp.getDeltaY(),
                                pp.getDeltaZ()
                            )
                        );
                    }
                } catch (Exception ignored) {}
            }
        }

        private Vec3d getTrackedPos(TrackedPosition p) {
            return ((TrackedPositionAccessor) p).getPos();
        }

        private Vec3d asHitPos(Vec3d p) {
            return p.add(new Vec3d(0d, 1d, 0d));
        }

        public void update() {
            Vec3d ourP = C.p().getEyePos();
            if (
                asHitPos(getTrackedPos(lastFlushedPosition)).distanceTo(ourP) >
                    maxReach &&
                onlyInCombat
            ) {
                disable();
            }
        }

        private Vec3d getPositionAfterPacket(
            Packet<ClientPlayPacketListener> p
        ) {
            if (p instanceof EntityPositionS2CPacket pp) {
                return new Vec3d(pp.getX(), pp.getY(), pp.getZ());
            } else if (p instanceof EntityS2CPacket pp) {
                return realPosition.withDelta(
                    pp.getDeltaX(),
                    pp.getDeltaY(),
                    pp.getDeltaZ()
                );
            }
            throw new RuntimeException("Shouldn't happen");
        }

        public void add(Packet<ClientPlayPacketListener> p) {
            packets.add(new TimedPacket(p));

            realPosition.setPos(getPositionAfterPacket(p));
        }

        public boolean isBlinking() {
            return !packets.isEmpty();
        }

        public void drawRealPos(MatrixStack matrixStack, float partialTicks) {
            if (packets.isEmpty()) return;

            Vec3d p = getTrackedPos(realPosition);
            RenderUtil.drawBox2(
                p.getX() - 0.5d,
                p.getY(),
                p.getZ() - 0.5d,
                1d,
                2d,
                1d,
                partialTicks,
                matrixStack,
                ThemeUtil.themeColors()[0],
                50
            );
        }
    }
}
