/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class BannerItem
extends WallOrFloorItem {
    public BannerItem(Block block, Block block2, Item.Properties properties) {
        super(block, block2, properties);
        Validate.isInstanceOf(AbstractBannerBlock.class, block);
        Validate.isInstanceOf(AbstractBannerBlock.class, block2);
    }

    public static void appendHoverTextFromTileEntityTag(ItemStack itemStack, List<ITextComponent> list) {
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Patterns")) {
            ListNBT listNBT = compoundNBT.getList("Patterns", 10);
            for (int i = 0; i < listNBT.size() && i < 6; ++i) {
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                DyeColor dyeColor = DyeColor.byId(compoundNBT2.getInt("Color"));
                BannerPattern bannerPattern = BannerPattern.byHash(compoundNBT2.getString("Pattern"));
                if (bannerPattern == null) continue;
                list.add(new TranslationTextComponent("block.minecraft.banner." + bannerPattern.getFileName() + "." + dyeColor.getTranslationKey()).mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public DyeColor getColor() {
        return ((AbstractBannerBlock)this.getBlock()).getColor();
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        BannerItem.appendHoverTextFromTileEntityTag(itemStack, list);
    }
}

