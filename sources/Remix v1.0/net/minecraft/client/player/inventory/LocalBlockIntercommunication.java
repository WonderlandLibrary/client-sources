package net.minecraft.client.player.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class LocalBlockIntercommunication implements IInteractionObject
{
    private String field_175126_a;
    private IChatComponent field_175125_b;
    
    public LocalBlockIntercommunication(final String p_i46277_1_, final IChatComponent p_i46277_2_) {
        this.field_175126_a = p_i46277_1_;
        this.field_175125_b = p_i46277_2_;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String getName() {
        return this.field_175125_b.getUnformattedText();
    }
    
    @Override
    public boolean hasCustomName() {
        return true;
    }
    
    @Override
    public String getGuiID() {
        return this.field_175126_a;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.field_175125_b;
    }
}
