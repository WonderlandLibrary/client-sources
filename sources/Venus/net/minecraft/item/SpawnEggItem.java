/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;

public class SpawnEggItem
extends Item {
    private static final Map<EntityType<?>, SpawnEggItem> EGGS = Maps.newIdentityHashMap();
    private final int primaryColor;
    private final int secondaryColor;
    private final EntityType<?> typeIn;

    public SpawnEggItem(EntityType<?> entityType, int n, int n2, Item.Properties properties) {
        super(properties);
        this.typeIn = entityType;
        this.primaryColor = n;
        this.secondaryColor = n2;
        EGGS.put(entityType, this);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        Object object;
        World world = itemUseContext.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        }
        ItemStack itemStack = itemUseContext.getItem();
        BlockPos blockPos = itemUseContext.getPos();
        Direction direction = itemUseContext.getFace();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(Blocks.SPAWNER) && (object = world.getTileEntity(blockPos)) instanceof MobSpawnerTileEntity) {
            AbstractSpawner abstractSpawner = ((MobSpawnerTileEntity)object).getSpawnerBaseLogic();
            EntityType<?> entityType = this.getType(itemStack.getTag());
            abstractSpawner.setEntityType(entityType);
            ((TileEntity)object).markDirty();
            world.notifyBlockUpdate(blockPos, blockState, blockState, 3);
            itemStack.shrink(1);
            return ActionResultType.CONSUME;
        }
        object = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
        EntityType<?> entityType = this.getType(itemStack.getTag());
        if (entityType.spawn((ServerWorld)world, itemStack, itemUseContext.getPlayer(), (BlockPos)object, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, object) && direction == Direction.UP) != null) {
            itemStack.shrink(1);
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        BlockRayTraceResult blockRayTraceResult = SpawnEggItem.rayTrace(world, playerEntity, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (((RayTraceResult)blockRayTraceResult).getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(itemStack);
        }
        if (!(world instanceof ServerWorld)) {
            return ActionResult.resultSuccess(itemStack);
        }
        BlockRayTraceResult blockRayTraceResult2 = blockRayTraceResult;
        BlockPos blockPos = blockRayTraceResult2.getPos();
        if (!(world.getBlockState(blockPos).getBlock() instanceof FlowingFluidBlock)) {
            return ActionResult.resultPass(itemStack);
        }
        if (world.isBlockModifiable(playerEntity, blockPos) && playerEntity.canPlayerEdit(blockPos, blockRayTraceResult2.getFace(), itemStack)) {
            EntityType<?> entityType = this.getType(itemStack.getTag());
            if (entityType.spawn((ServerWorld)world, itemStack, playerEntity, blockPos, SpawnReason.SPAWN_EGG, false, true) == null) {
                return ActionResult.resultPass(itemStack);
            }
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            return ActionResult.resultConsume(itemStack);
        }
        return ActionResult.resultFail(itemStack);
    }

    public boolean hasType(@Nullable CompoundNBT compoundNBT, EntityType<?> entityType) {
        return Objects.equals(this.getType(compoundNBT), entityType);
    }

    public int getColor(int n) {
        return n == 0 ? this.primaryColor : this.secondaryColor;
    }

    @Nullable
    public static SpawnEggItem getEgg(@Nullable EntityType<?> entityType) {
        return EGGS.get(entityType);
    }

    public static Iterable<SpawnEggItem> getEggs() {
        return Iterables.unmodifiableIterable(EGGS.values());
    }

    public EntityType<?> getType(@Nullable CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2;
        if (compoundNBT != null && compoundNBT.contains("EntityTag", 1) && (compoundNBT2 = compoundNBT.getCompound("EntityTag")).contains("id", 1)) {
            return EntityType.byKey(compoundNBT2.getString("id")).orElse(this.typeIn);
        }
        return this.typeIn;
    }

    public Optional<MobEntity> getChildToSpawn(PlayerEntity playerEntity, MobEntity mobEntity, EntityType<? extends MobEntity> entityType, ServerWorld serverWorld, Vector3d vector3d, ItemStack itemStack) {
        if (!this.hasType(itemStack.getTag(), entityType)) {
            return Optional.empty();
        }
        MobEntity mobEntity2 = mobEntity instanceof AgeableEntity ? ((AgeableEntity)mobEntity).func_241840_a(serverWorld, (AgeableEntity)mobEntity) : entityType.create(serverWorld);
        if (mobEntity2 == null) {
            return Optional.empty();
        }
        mobEntity2.setChild(false);
        if (!mobEntity2.isChild()) {
            return Optional.empty();
        }
        mobEntity2.setLocationAndAngles(vector3d.getX(), vector3d.getY(), vector3d.getZ(), 0.0f, 0.0f);
        serverWorld.func_242417_l(mobEntity2);
        if (itemStack.hasDisplayName()) {
            mobEntity2.setCustomName(itemStack.getDisplayName());
        }
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        return Optional.of(mobEntity2);
    }
}

