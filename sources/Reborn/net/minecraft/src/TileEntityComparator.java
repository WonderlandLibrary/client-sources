package net.minecraft.src;

public class TileEntityComparator extends TileEntity
{
    private int field_96101_a;
    
    public TileEntityComparator() {
        this.field_96101_a = 0;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("OutputSignal", this.field_96101_a);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.field_96101_a = par1NBTTagCompound.getInteger("OutputSignal");
    }
    
    public int func_96100_a() {
        return this.field_96101_a;
    }
    
    public void func_96099_a(final int par1) {
        this.field_96101_a = par1;
    }
}
