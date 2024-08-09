/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OnAStickItem<T extends Entity>
extends Item {
    private final EntityType<T> temptedEntity;
    private final int damageAmount;

    public OnAStickItem(Item.Properties properties, EntityType<T> entityType, int n) {
        super(properties);
        this.temptedEntity = entityType;
        this.damageAmount = n;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        IRideable iRideable;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (world.isRemote) {
            return ActionResult.resultPass(itemStack);
        }
        Entity entity2 = playerEntity.getRidingEntity();
        if (playerEntity.isPassenger() && entity2 instanceof IRideable && entity2.getType() == this.temptedEntity && (iRideable = (IRideable)((Object)entity2)).boost()) {
            itemStack.damageItem(this.damageAmount, playerEntity, arg_0 -> OnAStickItem.lambda$onItemRightClick$0(hand, arg_0));
            if (itemStack.isEmpty()) {
                ItemStack itemStack2 = new ItemStack(Items.FISHING_ROD);
                itemStack2.setTag(itemStack.getTag());
                return ActionResult.resultSuccess(itemStack2);
            }
            return ActionResult.resultSuccess(itemStack);
        }
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        return ActionResult.resultPass(itemStack);
    }

    private static void lambda$onItemRightClick$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }
}

