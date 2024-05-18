package net.minecraft.src;

public class TileEntitySkull extends TileEntity
{
    private int skullType;
    private int skullRotation;
    private String extraType;
    
    public TileEntitySkull() {
        this.extraType = "";
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("SkullType", (byte)(this.skullType & 0xFF));
        par1NBTTagCompound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
        par1NBTTagCompound.setString("ExtraType", this.extraType);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.skullType = par1NBTTagCompound.getByte("SkullType");
        this.skullRotation = par1NBTTagCompound.getByte("Rot");
        if (par1NBTTagCompound.hasKey("ExtraType")) {
            this.extraType = par1NBTTagCompound.getString("ExtraType");
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
    }
    
    public void setSkullType(final int par1, final String par2Str) {
        this.skullType = par1;
        this.extraType = par2Str;
    }
    
    public int getSkullType() {
        return this.skullType;
    }
    
    public int func_82119_b() {
        return this.skullRotation;
    }
    
    public void setSkullRotation(final int par1) {
        this.skullRotation = par1;
    }
    
    public String getExtraType() {
        return this.extraType;
    }
}
