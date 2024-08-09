/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BoatItem
extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final BoatEntity.Type type;

    public BoatItem(BoatEntity.Type type, Item.Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        Object object;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        BlockRayTraceResult blockRayTraceResult = BoatItem.rayTrace(world, playerEntity, RayTraceContext.FluidMode.ANY);
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemStack);
        }
        Vector3d vector3d = playerEntity.getLook(1.0f);
        double d = 5.0;
        List<Entity> list = world.getEntitiesInAABBexcluding(playerEntity, playerEntity.getBoundingBox().expand(vector3d.scale(5.0)).grow(1.0), field_219989_a);
        if (!list.isEmpty()) {
            object = playerEntity.getEyePosition(1.0f);
            for (Entity entity2 : list) {
                AxisAlignedBB axisAlignedBB = entity2.getBoundingBox().grow(entity2.getCollisionBorderSize());
                if (!axisAlignedBB.contains((Vector3d)object)) continue;
                return ActionResult.resultPass(itemStack);
            }
        }
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.BLOCK) {
            object = new BoatEntity(world, blockRayTraceResult.getHitVec().x, blockRayTraceResult.getHitVec().y, blockRayTraceResult.getHitVec().z);
            ((BoatEntity)object).setBoatType(this.type);
            ((BoatEntity)object).rotationYaw = playerEntity.rotationYaw;
            if (!world.hasNoCollisions((Entity)object, ((Entity)object).getBoundingBox().grow(-0.1))) {
                return ActionResult.resultFail(itemStack);
            }
            if (!world.isRemote) {
                world.addEntity((Entity)object);
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            }
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            return ActionResult.func_233538_a_(itemStack, world.isRemote());
        }
        return ActionResult.resultPass(itemStack);
    }
}

