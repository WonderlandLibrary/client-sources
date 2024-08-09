/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ArmorStandItem
extends Item {
    public ArmorStandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        Direction direction = itemUseContext.getFace();
        if (direction == Direction.DOWN) {
            return ActionResultType.FAIL;
        }
        World world = itemUseContext.getWorld();
        BlockItemUseContext blockItemUseContext = new BlockItemUseContext(itemUseContext);
        BlockPos blockPos = blockItemUseContext.getPos();
        ItemStack itemStack = itemUseContext.getItem();
        Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockPos);
        AxisAlignedBB axisAlignedBB = EntityType.ARMOR_STAND.getSize().func_242285_a(vector3d.getX(), vector3d.getY(), vector3d.getZ());
        if (world.hasNoCollisions(null, axisAlignedBB, ArmorStandItem::lambda$onItemUse$0) && world.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB).isEmpty()) {
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                ArmorStandEntity armorStandEntity = EntityType.ARMOR_STAND.create(serverWorld, itemStack.getTag(), null, itemUseContext.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, false);
                if (armorStandEntity == null) {
                    return ActionResultType.FAIL;
                }
                serverWorld.func_242417_l(armorStandEntity);
                float f = (float)MathHelper.floor((MathHelper.wrapDegrees(itemUseContext.getPlacementYaw() - 180.0f) + 22.5f) / 45.0f) * 45.0f;
                armorStandEntity.setLocationAndAngles(armorStandEntity.getPosX(), armorStandEntity.getPosY(), armorStandEntity.getPosZ(), f, 0.0f);
                this.applyRandomRotations(armorStandEntity, world.rand);
                world.addEntity(armorStandEntity);
                world.playSound(null, armorStandEntity.getPosX(), armorStandEntity.getPosY(), armorStandEntity.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75f, 0.8f);
            }
            itemStack.shrink(1);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.FAIL;
    }

    private void applyRandomRotations(ArmorStandEntity armorStandEntity, Random random2) {
        Rotations rotations = armorStandEntity.getHeadRotation();
        float f = random2.nextFloat() * 5.0f;
        float f2 = random2.nextFloat() * 20.0f - 10.0f;
        Rotations rotations2 = new Rotations(rotations.getX() + f, rotations.getY() + f2, rotations.getZ());
        armorStandEntity.setHeadRotation(rotations2);
        rotations = armorStandEntity.getBodyRotation();
        f = random2.nextFloat() * 10.0f - 5.0f;
        rotations2 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
        armorStandEntity.setBodyRotation(rotations2);
    }

    private static boolean lambda$onItemUse$0(Entity entity2) {
        return false;
    }
}

