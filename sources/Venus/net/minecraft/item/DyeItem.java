/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class DyeItem
extends Item {
    private static final Map<DyeColor, DyeItem> COLOR_DYE_ITEM_MAP = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;

    public DyeItem(DyeColor dyeColor, Item.Properties properties) {
        super(properties);
        this.dyeColor = dyeColor;
        COLOR_DYE_ITEM_MAP.put(dyeColor, this);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
        SheepEntity sheepEntity;
        if (livingEntity instanceof SheepEntity && (sheepEntity = (SheepEntity)livingEntity).isAlive() && !sheepEntity.getSheared() && sheepEntity.getFleeceColor() != this.dyeColor) {
            if (!playerEntity.world.isRemote) {
                sheepEntity.setFleeceColor(this.dyeColor);
                itemStack.shrink(1);
            }
            return ActionResultType.func_233537_a_(playerEntity.world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }

    public static DyeItem getItem(DyeColor dyeColor) {
        return COLOR_DYE_ITEM_MAP.get(dyeColor);
    }
}

