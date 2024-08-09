/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FishBucketItem
extends BucketItem {
    private final EntityType<?> fishType;

    public FishBucketItem(EntityType<?> entityType, Fluid fluid, Item.Properties properties) {
        super(fluid, properties);
        this.fishType = entityType;
    }

    @Override
    public void onLiquidPlaced(World world, ItemStack itemStack, BlockPos blockPos) {
        if (world instanceof ServerWorld) {
            this.placeFish((ServerWorld)world, itemStack, blockPos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable PlayerEntity playerEntity, IWorld iWorld, BlockPos blockPos) {
        iWorld.playSound(playerEntity, blockPos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    private void placeFish(ServerWorld serverWorld, ItemStack itemStack, BlockPos blockPos) {
        Entity entity2 = this.fishType.spawn(serverWorld, itemStack, null, blockPos, SpawnReason.BUCKET, true, true);
        if (entity2 != null) {
            ((AbstractFishEntity)entity2).setFromBucket(false);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        CompoundNBT compoundNBT;
        if (this.fishType == EntityType.TROPICAL_FISH && (compoundNBT = itemStack.getTag()) != null && compoundNBT.contains("BucketVariantTag", 0)) {
            int n = compoundNBT.getInt("BucketVariantTag");
            TextFormatting[] textFormattingArray = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
            String string = "color.minecraft." + TropicalFishEntity.func_212326_d(n);
            String string2 = "color.minecraft." + TropicalFishEntity.func_212323_p(n);
            for (int i = 0; i < TropicalFishEntity.SPECIAL_VARIANTS.length; ++i) {
                if (n != TropicalFishEntity.SPECIAL_VARIANTS[i]) continue;
                list.add(new TranslationTextComponent(TropicalFishEntity.func_212324_b(i)).mergeStyle(textFormattingArray));
                return;
            }
            list.add(new TranslationTextComponent(TropicalFishEntity.func_212327_q(n)).mergeStyle(textFormattingArray));
            TranslationTextComponent translationTextComponent = new TranslationTextComponent(string);
            if (!string.equals(string2)) {
                translationTextComponent.appendString(", ").append(new TranslationTextComponent(string2));
            }
            translationTextComponent.mergeStyle(textFormattingArray);
            list.add(translationTextComponent);
        }
    }
}

