package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public static class InterfaceCraftingTable implements IInteractionObject
{
    private final World world;
    private final BlockPos position;
    
    public InterfaceCraftingTable(final World worldIn, final BlockPos p_i45730_2_) {
        this.world = worldIn;
        this.position = p_i45730_2_;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
    }
    
    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerWorkbench(playerInventory, this.world, this.position);
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:crafting_table";
    }
}
