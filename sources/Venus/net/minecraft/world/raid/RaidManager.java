/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class RaidManager
extends WorldSavedData {
    private final Map<Integer, Raid> byId = Maps.newHashMap();
    private final ServerWorld world;
    private int nextAvailableId;
    private int tick;

    public RaidManager(ServerWorld serverWorld) {
        super(RaidManager.func_234620_a_(serverWorld.getDimensionType()));
        this.world = serverWorld;
        this.nextAvailableId = 1;
        this.markDirty();
    }

    public Raid get(int n) {
        return this.byId.get(n);
    }

    public void tick() {
        ++this.tick;
        Iterator<Raid> iterator2 = this.byId.values().iterator();
        while (iterator2.hasNext()) {
            Raid raid = iterator2.next();
            if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                raid.stop();
            }
            if (raid.isStopped()) {
                iterator2.remove();
                this.markDirty();
                continue;
            }
            raid.tick();
        }
        if (this.tick % 200 == 0) {
            this.markDirty();
        }
        DebugPacketSender.sendRaids(this.world, this.byId.values());
    }

    public static boolean canJoinRaid(AbstractRaiderEntity abstractRaiderEntity, Raid raid) {
        if (abstractRaiderEntity != null && raid != null && raid.getWorld() != null) {
            return abstractRaiderEntity.isAlive() && abstractRaiderEntity.canJoinRaid() && abstractRaiderEntity.getIdleTime() <= 2400 && abstractRaiderEntity.world.getDimensionType() == raid.getWorld().getDimensionType();
        }
        return true;
    }

    @Nullable
    public Raid badOmenTick(ServerPlayerEntity serverPlayerEntity) {
        Object object;
        Object object22;
        if (serverPlayerEntity.isSpectator()) {
            return null;
        }
        if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
            return null;
        }
        DimensionType dimensionType = serverPlayerEntity.world.getDimensionType();
        if (!dimensionType.isHasRaids()) {
            return null;
        }
        BlockPos blockPos = serverPlayerEntity.getPosition();
        List list = this.world.getPointOfInterestManager().func_219146_b(PointOfInterestType.MATCH_ANY, blockPos, 64, PointOfInterestManager.Status.IS_OCCUPIED).collect(Collectors.toList());
        int n = 0;
        Vector3d vector3d = Vector3d.ZERO;
        for (Object object22 : list) {
            BlockPos blockPos2 = ((PointOfInterest)object22).getPos();
            vector3d = vector3d.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            ++n;
        }
        if (n > 0) {
            vector3d = vector3d.scale(1.0 / (double)n);
            object = new BlockPos(vector3d);
        } else {
            object = blockPos;
        }
        object22 = this.findOrCreateRaid(serverPlayerEntity.getServerWorld(), (BlockPos)object);
        boolean bl = false;
        if (!((Raid)object22).isStarted()) {
            if (!this.byId.containsKey(((Raid)object22).getId())) {
                this.byId.put(((Raid)object22).getId(), (Raid)object22);
            }
            bl = true;
        } else if (((Raid)object22).getBadOmenLevel() < ((Raid)object22).getMaxLevel()) {
            bl = true;
        } else {
            serverPlayerEntity.removePotionEffect(Effects.BAD_OMEN);
            serverPlayerEntity.connection.sendPacket(new SEntityStatusPacket(serverPlayerEntity, 43));
        }
        if (bl) {
            ((Raid)object22).increaseLevel(serverPlayerEntity);
            serverPlayerEntity.connection.sendPacket(new SEntityStatusPacket(serverPlayerEntity, 43));
            if (!((Raid)object22).func_221297_c()) {
                serverPlayerEntity.addStat(Stats.RAID_TRIGGER);
                CriteriaTriggers.VOLUNTARY_EXILE.trigger(serverPlayerEntity);
            }
        }
        this.markDirty();
        return object22;
    }

    private Raid findOrCreateRaid(ServerWorld serverWorld, BlockPos blockPos) {
        Raid raid = serverWorld.findRaid(blockPos);
        return raid != null ? raid : new Raid(this.incrementNextId(), serverWorld, blockPos);
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        this.nextAvailableId = compoundNBT.getInt("NextAvailableID");
        this.tick = compoundNBT.getInt("Tick");
        ListNBT listNBT = compoundNBT.getList("Raids", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT2 = listNBT.getCompound(i);
            Raid raid = new Raid(this.world, compoundNBT2);
            this.byId.put(raid.getId(), raid);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putInt("NextAvailableID", this.nextAvailableId);
        compoundNBT.putInt("Tick", this.tick);
        ListNBT listNBT = new ListNBT();
        for (Raid raid : this.byId.values()) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            raid.write(compoundNBT2);
            listNBT.add(compoundNBT2);
        }
        compoundNBT.put("Raids", listNBT);
        return compoundNBT;
    }

    public static String func_234620_a_(DimensionType dimensionType) {
        return "raids" + dimensionType.getSuffix();
    }

    private int incrementNextId() {
        return ++this.nextAvailableId;
    }

    @Nullable
    public Raid findRaid(BlockPos blockPos, int n) {
        Raid raid = null;
        double d = n;
        for (Raid raid2 : this.byId.values()) {
            double d2 = raid2.getCenter().distanceSq(blockPos);
            if (!raid2.isActive() || !(d2 < d)) continue;
            raid = raid2;
            d = d2;
        }
        return raid;
    }
}

