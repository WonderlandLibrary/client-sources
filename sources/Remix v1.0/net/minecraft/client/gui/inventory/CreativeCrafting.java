package net.minecraft.client.gui.inventory;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft mc;
    
    public CreativeCrafting(final Minecraft mc) {
        this.mc = mc;
    }
    
    @Override
    public void updateCraftingInventory(final Container p_71110_1_, final List p_71110_2_) {
    }
    
    @Override
    public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        this.mc.playerController.sendSlotPacket(p_71111_3_, p_71111_2_);
    }
    
    @Override
    public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
    }
    
    @Override
    public void func_175173_a(final Container p_175173_1_, final IInventory p_175173_2_) {
    }
}
