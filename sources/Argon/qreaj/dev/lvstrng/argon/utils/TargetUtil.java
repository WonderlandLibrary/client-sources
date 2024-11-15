// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("DataFlowIssue")
public final class TargetUtil {
    public static boolean isDeadNearby() {
        return Argon.mc.world.getPlayers().parallelStream().filter(TargetUtil::isPlayer).filter(TargetUtil::inRange).anyMatch(LivingEntity::isDead);
    }

    public static Entity findClosest(final PlayerEntity toPlayer, final float radius, final boolean seeOnly) {
        for (final Entity entity : Argon.mc.world.getEntities()) {
            final float distanceTo = entity.distanceTo(toPlayer);
            if (entity != toPlayer && distanceTo <= radius && Argon.mc.player.canSee(entity) == seeOnly && distanceTo < Float.MAX_VALUE)
                return entity;
        }

        return null;
    }

    public static PlayerEntity getNearestPlayer(final PlayerEntity toPlayer, final float range, final boolean seeOnly) {
        PlayerEntity plyr = null;
        float closestDistance = Float.MAX_VALUE;

        for (AbstractClientPlayerEntity player : Argon.mc.world.getPlayers()) {
            if (player == toPlayer) continue;
            float distanceTo = player.distanceTo(toPlayer);
            if (distanceTo > range || seeOnly && !player.canSee(toPlayer)) continue;
            if (distanceTo < closestDistance) {
                closestDistance = distanceTo;
                plyr = player;
            }
        }

        return plyr;
    }

    public static void placeBlock(final BlockHitResult blockHit, final boolean swingHand) {
        final ActionResult interactBlock = Argon.mc.interactionManager.interactBlock(Argon.mc.player, Hand.MAIN_HAND, blockHit);
        if (!interactBlock.isAccepted()) return;
        if (interactBlock.shouldSwingHand() && swingHand) Argon.mc.player.swingHand(Hand.MAIN_HAND);
    }

    public static Stream<WorldChunk> chunks() {
        final int n = Math.max(2, Argon.mc.options.getClampedViewDistance()) + 3;
        final int n2 = n * 2 + 1;
        ChunkPos chunkPos = Argon.mc.player.getChunkPos();
        ChunkPos chunkPos2 = new ChunkPos(chunkPos.x + n, chunkPos.z + n);
        ChunkPos chunkPos3 = new ChunkPos(chunkPos.x - n, chunkPos.z - n);
        return Stream.iterate(chunkPos2, arg_0 -> TargetUtil.isInRange(chunkPos3, chunkPos2, arg_0)).limit((long) n2 * (long) n2).filter(TargetUtil::isLoaded).map(TargetUtil::getChunk).filter(Objects::nonNull);
    }

    public static boolean canHit(final PlayerEntity player) {
        if (Argon.mc.player != null && player != null) {
            final Vec3d normalize = Argon.mc.player.getPos().subtract(player.getPos()).normalize();
            final float getYaw = player.getYaw();
            final float getPitch = player.getPitch();
            return new Vec3d(-Math.sin(Math.toRadians(getYaw)) * Math.cos(Math.toRadians(getPitch)), -Math.sin(Math.toRadians(getPitch)), Math.cos(Math.toRadians(getYaw)) * Math.cos(Math.toRadians(getPitch))).normalize().dotProduct(normalize) < 0.0;
        }
        return false;
    }

    public static boolean canAttack(final PlayerEntity player, final Entity target) {
        return player.getAttackCooldownProgress(0.5f) > 0.9f && player.fallDistance > 0.0f && !player.isOnGround() && !player.isClimbing() && !player.isSubmergedInWater() && !player.hasStatusEffect(StatusEffects.BLINDNESS) && target instanceof LivingEntity;
    }

    public static void attack(final Entity entity, final boolean swingHand) {
        Argon.mc.interactionManager.attackEntity(Argon.mc.player, entity);
        if (swingHand) Argon.mc.player.swingHand(Hand.MAIN_HAND);
    }

    private static WorldChunk getChunk(final ChunkPos chunkPos) {
        return Argon.mc.world.getChunk(chunkPos.x, chunkPos.z);
    }

    private static boolean isLoaded(final ChunkPos chunkPos) {
        return Argon.mc.world.isChunkLoaded(chunkPos.x, chunkPos.z);
    }

    private static ChunkPos isInRange(final ChunkPos chunk1, final ChunkPos chunk2, final ChunkPos chunk3) {
        int z = chunk3.z;
        if ((chunk3.x + 1) > chunk1.x) {
            return new ChunkPos(chunk2.x, z + 1);
        }

        return new ChunkPos(chunk3.x + 1, z);
    }

    private static boolean inRange(final AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return abstractClientPlayerEntity.squaredDistanceTo(Argon.mc.player) <= 36.0;
    }

    private static boolean isPlayer(final AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return abstractClientPlayerEntity != Argon.mc.player;
    }
}