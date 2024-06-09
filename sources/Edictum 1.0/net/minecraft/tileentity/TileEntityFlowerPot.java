package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;

public class TileEntityFlowerPot extends TileEntity
{
    private Item flowerPotItem;
    private int flowerPotData;

    public TileEntityFlowerPot()
    {
    }

    public TileEntityFlowerPot(Item potItem, int potData)
    {
        this.flowerPotItem = potItem;
        this.flowerPotData = potData;
    }

    public static void func_189699_a(DataFixer p_189699_0_)
    {
    }

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.flowerPotItem);
        p_189515_1_.setString("Item", resourcelocation == null ? "" : resourcelocation.toString());
        p_189515_1_.setInteger("Data", this.flowerPotData);
        return p_189515_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Item", 8))
        {
            this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
        }
        else
        {
            this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
        }

        this.flowerPotData = compound.getInteger("Data");
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        return new SPacketUpdateTileEntity(this.pos, 5, this.func_189517_E_());
    }

    public NBTTagCompound func_189517_E_()
    {
        return this.func_189515_b(new NBTTagCompound());
    }

    public void setFlowerPotData(Item potItem, int potData)
    {
        this.flowerPotItem = potItem;
        this.flowerPotData = potData;
    }

    @Nullable
    public ItemStack getFlowerItemStack()
    {
        return this.flowerPotItem == null ? null : new ItemStack(this.flowerPotItem, 1, this.flowerPotData);
    }

    @Nullable
    public Item getFlowerPotItem()
    {
        return this.flowerPotItem;
    }

    public int getFlowerPotData()
    {
        return this.flowerPotData;
    }
}
