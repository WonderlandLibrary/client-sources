/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;

public class SkullItem
extends WallOrFloorItem {
    public SkullItem(Block block, Block block2, Item.Properties properties) {
        super(block, block2, properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack itemStack) {
        if (itemStack.getItem() == Items.PLAYER_HEAD && itemStack.hasTag()) {
            CompoundNBT compoundNBT;
            String string = null;
            CompoundNBT compoundNBT2 = itemStack.getTag();
            if (compoundNBT2.contains("SkullOwner", 1)) {
                string = compoundNBT2.getString("SkullOwner");
            } else if (compoundNBT2.contains("SkullOwner", 1) && (compoundNBT = compoundNBT2.getCompound("SkullOwner")).contains("Name", 1)) {
                string = compoundNBT.getString("Name");
            }
            if (string != null) {
                return new TranslationTextComponent(this.getTranslationKey() + ".named", string);
            }
        }
        return super.getDisplayName(itemStack);
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT compoundNBT) {
        super.updateItemStackNBT(compoundNBT);
        if (compoundNBT.contains("SkullOwner", 1) && !StringUtils.isBlank(compoundNBT.getString("SkullOwner"))) {
            GameProfile gameProfile = new GameProfile(null, compoundNBT.getString("SkullOwner"));
            gameProfile = SkullTileEntity.updateGameProfile(gameProfile);
            compoundNBT.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile));
            return false;
        }
        return true;
    }
}

