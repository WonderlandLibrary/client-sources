package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityFlowerPot extends TileEntity
{
    private Item flowerPotItem;
    private int flowerPotData;
    
    public TileEntityFlowerPot() {
    }
    
    public TileEntityFlowerPot(final Item p_i45442_1_, final int p_i45442_2_) {
        this.flowerPotItem = p_i45442_1_;
        this.flowerPotData = p_i45442_2_;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        final ResourceLocation var2 = (ResourceLocation)Item.itemRegistry.getNameForObject(this.flowerPotItem);
        compound.setString("Item", (var2 == null) ? "" : var2.toString());
        compound.setInteger("Data", this.flowerPotData);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("Item", 8)) {
            this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
        }
        else {
            this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
        }
        this.flowerPotData = compound.getInteger("Data");
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        var1.removeTag("Item");
        var1.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
        return new S35PacketUpdateTileEntity(this.pos, 5, var1);
    }
    
    public void func_145964_a(final Item p_145964_1_, final int p_145964_2_) {
        this.flowerPotItem = p_145964_1_;
        this.flowerPotData = p_145964_2_;
    }
    
    public Item getFlowerPotItem() {
        return this.flowerPotItem;
    }
    
    public int getFlowerPotData() {
        return this.flowerPotData;
    }
}
