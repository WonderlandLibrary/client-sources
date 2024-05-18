package net.minecraft.src;

public class ItemMapBase extends Item
{
    protected ItemMapBase(final int par1) {
        super(par1);
    }
    
    @Override
    public boolean isMap() {
        return true;
    }
    
    public Packet createMapDataPacket(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return null;
    }
}
