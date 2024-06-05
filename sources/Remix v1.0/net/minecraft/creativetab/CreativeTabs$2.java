package net.minecraft.creativetab;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

static final class CreativeTabs$2 extends CreativeTabs {
    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.double_plant);
    }
    
    @Override
    public int getIconItemDamage() {
        return BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a();
    }
}