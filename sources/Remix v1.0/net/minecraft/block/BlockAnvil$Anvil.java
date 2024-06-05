package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public static class Anvil implements IInteractionObject
{
    private final World world;
    private final BlockPos position;
    
    public Anvil(final World worldIn, final BlockPos pos) {
        this.world = worldIn;
        this.position = pos;
    }
    
    @Override
    public String getName() {
        return "anvil";
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
    }
    
    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerRepair(playerInventory, this.world, this.position, playerIn);
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:anvil";
    }
}
