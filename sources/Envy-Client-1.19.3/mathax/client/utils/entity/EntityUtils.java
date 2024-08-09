package mathax.client.utils.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import mathax.client.mixin.EntityTrackingSectionAccessor;
import mathax.client.mixin.SectionedEntityCacheAccessor;
import mathax.client.mixin.SimpleEntityLookupAccessor;
import mathax.client.mixin.WorldAccessor;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.*;
import net.minecraft.world.GameMode;
import net.minecraft.world.entity.EntityLookup;
import net.minecraft.world.entity.EntityTrackingSection;
import net.minecraft.world.entity.SectionedEntityCache;
import net.minecraft.world.entity.SimpleEntityLookup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static mathax.client.MatHax.mc;

public class EntityUtils {
    public static boolean isAttackable(EntityType<?> type) {
        return type != EntityType.AREA_EFFECT_CLOUD && type != EntityType.ARROW && type != EntityType.FALLING_BLOCK && type != EntityType.FIREWORK_ROCKET && type != EntityType.ITEM && type != EntityType.LLAMA_SPIT && type != EntityType.SPECTRAL_ARROW && type != EntityType.ENDER_PEARL && type != EntityType.EXPERIENCE_BOTTLE && type != EntityType.POTION && type != EntityType.TRIDENT && type != EntityType.LIGHTNING_BOLT && type != EntityType.FISHING_BOBBER && type != EntityType.EXPERIENCE_ORB && type != EntityType.EGG;
    }

    public static float getTotalHealth(LivingEntity target) {
        return target.getHealth() + target.getAbsorptionAmount();
    }

    public static float getTotalPlayerHealth(PlayerEntity target) {
        return target.getHealth() + target.getAbsorptionAmount();
    }

    public static <T extends Entity> T findClosest(Class<T> entityClass, float range) {
        for (Entity entity : mc.world.getEntities()) {
            if (entityClass.isAssignableFrom(entity.getClass()) && !entity.equals(mc.player) && entity.distanceTo(mc.player) <= range) {
                return (T) entity;
            }
        }
        return null;
    }

    public static int getPing(PlayerEntity player) {
        if (mc.getNetworkHandler() == null) return 0;
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }

    public static GameMode getGameMode(PlayerEntity player) {
        if (player == null) return null;
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null) return null;
        return playerListEntry.getGameMode();
    }

    public static boolean isAboveWater(Entity entity) {
        BlockPos.Mutable blockPos = entity.getBlockPos().mutableCopy();

        for (int i = 0; i < 64; i++) {
            BlockState state = mc.world.getBlockState(blockPos);

            if (state.getMaterial().blocksMovement()) break;

            Fluid fluid = state.getFluidState().getFluid();
            if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) return true;

            blockPos.move(0, -1, 0);
        }

        return false;
    }

    public static boolean isInRenderDistance(Entity entity) {
        if (entity == null) return false;
        return isInRenderDistance(entity.getX(), entity.getZ());
    }

    public static boolean isInRenderDistance(BlockEntity entity) {
        if (entity == null) return false;
        return isInRenderDistance(entity.getPos().getX(), entity.getPos().getZ());
    }

    public static boolean isInRenderDistance(BlockPos pos) {
        if (pos == null) return false;
        return isInRenderDistance(pos.getX(), pos.getZ());
    }

    public static boolean isInRenderDistance(double posX, double posZ) {
        double x = Math.abs(mc.gameRenderer.getCamera().getPos().x - posX);
        double z = Math.abs(mc.gameRenderer.getCamera().getPos().z - posZ);
        double d = (mc.options.getViewDistance().getValue() + 1) * 16;

        return x < d && z < d;
    }

    public static List<BlockPos> getSurroundBlocks(PlayerEntity player) {
        if (player == null) return null;

        List<BlockPos> positions = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) continue;

            BlockPos pos = player.getBlockPos().offset(direction);

            if (mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN) positions.add(pos);
        }

        return positions;
    }

    public static BlockPos getCityBlock(PlayerEntity player) {
        List<BlockPos> posList = getSurroundBlocks(player);
        posList.sort(Comparator.comparingDouble(PlayerUtils::distanceTo));
        return posList.isEmpty() ? null : posList.get(0);
    }

    public static String getName(Entity entity) {
        if (entity == null) return null;
        if (entity instanceof PlayerEntity) return entity.getEntityName();
        return entity.getType().getName().getString();
    }

    public static boolean intersectsWithEntity(Box box, Predicate<Entity> predicate) {
        EntityLookup<Entity> entityLookup = ((WorldAccessor) mc.world).getEntityLookup();

        // Fast implementation using SimpleEntityLookup that returns on the first intersecting entity
        if (entityLookup instanceof SimpleEntityLookup<Entity> simpleEntityLookup) {
            SectionedEntityCache<Entity> cache = ((SimpleEntityLookupAccessor) simpleEntityLookup).getCache();
            LongSortedSet trackedPositions = ((SectionedEntityCacheAccessor) cache).getTrackedPositions();
            Long2ObjectMap<EntityTrackingSection<Entity>> trackingSections = ((SectionedEntityCacheAccessor) cache).getTrackingSections();

            int i = ChunkSectionPos.getSectionCoord(box.minX - 2);
            int j = ChunkSectionPos.getSectionCoord(box.minY - 2);
            int k = ChunkSectionPos.getSectionCoord(box.minZ - 2);
            int l = ChunkSectionPos.getSectionCoord(box.maxX + 2);
            int m = ChunkSectionPos.getSectionCoord(box.maxY + 2);
            int n = ChunkSectionPos.getSectionCoord(box.maxZ + 2);

            for (int o = i; o <= l; o++) {
                long p = ChunkSectionPos.asLong(o, 0, 0);
                long q = ChunkSectionPos.asLong(o, -1, -1);
                LongBidirectionalIterator longIterator = trackedPositions.subSet(p, q + 1).iterator();

                while (longIterator.hasNext()) {
                    long r = longIterator.nextLong();
                    int s = ChunkSectionPos.unpackY(r);
                    int t = ChunkSectionPos.unpackZ(r);

                    if (s >= j && s <= m && t >= k && t <= n) {
                        EntityTrackingSection<Entity> entityTrackingSection = trackingSections.get(r);

                        if (entityTrackingSection != null && entityTrackingSection.getStatus().shouldTrack()) {
                            for (Entity entity : ((EntityTrackingSectionAccessor) entityTrackingSection).<Entity>getCollection()) {
                                if (entity.getBoundingBox().intersects(box) && predicate.test(entity)) return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        // Slow implementation that loops every entity if for some reason the EntityLookup implementation is changed
        AtomicBoolean found = new AtomicBoolean(false);

        entityLookup.forEachIntersects(box, entity -> {
            if (!found.get() && predicate.test(entity)) found.set(true);
        });

        return found.get();
    }

    public static boolean isSurrounded(LivingEntity targetEntity) {
        assert mc.world != null;
        return BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(-1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, 1)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, -1));
    }

    public static boolean isSurroundBroken(LivingEntity targetEntity) {
        assert mc.world != null;
        return (!BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(-1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, 1)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, -1))) || (BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(1, 0, 0)) && !BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(-1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, 1)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, -1))) || (BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(-1, 0, 0)) && !BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, 1)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, -1))) || (BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(-1, 0, 0)) && BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, 1)) && !BlockUtils.isBlastResistant2(targetEntity.getBlockPos().add(0, 0, -1)));
    }

    public static boolean isBurrowed(LivingEntity targetEntity) {
        assert mc.world != null;
        return BlockUtils.isBlastResistant2(targetEntity.getBlockPos());
    }

    public static boolean isTopTrapped(LivingEntity target) {
        assert mc.world != null;
        return BlockUtils.isBlastResistant2(target.getBlockPos().add(0, 2, 0));
    }

    public static boolean isFaceSurrounded(LivingEntity target) {
        assert mc.world != null;
        return BlockUtils.isBlastResistant2(target.getBlockPos().add(1, 1, 0)) && BlockUtils.isBlastResistant2(target.getBlockPos().add(-1, 1, 0)) && BlockUtils.isBlastResistant2(target.getBlockPos().add(0, 1, 1)) && BlockUtils.isBlastResistant2(target.getBlockPos().add(0, 1, -1));
    }

    public static boolean isGreenHole(LivingEntity target) {
        assert mc.world != null;
        return mc.world.getBlockState(target.getBlockPos().add(1, 0, 0)).isOf(Blocks.BEDROCK) && mc.world.getBlockState(target.getBlockPos().add(-1, 0, 0)).isOf(Blocks.BEDROCK) && mc.world.getBlockState(target.getBlockPos().add(0, 0, 1)).isOf(Blocks.BEDROCK) && mc.world.getBlockState(target.getBlockPos().add(0, 0, -1)).isOf(Blocks.BEDROCK);
    }

    public static boolean isBedrock(BlockPos pos) {
        return mc.world.getBlockState(pos).isOf(Blocks.BEDROCK);
    }
}
