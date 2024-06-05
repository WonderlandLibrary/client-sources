package net.minecraft.src;

public class TileEntitySign extends TileEntity
{
    public String[] signText;
    public int lineBeingEdited;
    private boolean isEditable;
    
    public TileEntitySign() {
        this.signText = new String[] { "", "", "", "" };
        this.lineBeingEdited = -1;
        this.isEditable = true;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("Text1", this.signText[0]);
        par1NBTTagCompound.setString("Text2", this.signText[1]);
        par1NBTTagCompound.setString("Text3", this.signText[2]);
        par1NBTTagCompound.setString("Text4", this.signText[3]);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.isEditable = false;
        super.readFromNBT(par1NBTTagCompound);
        for (int var2 = 0; var2 < 4; ++var2) {
            this.signText[var2] = par1NBTTagCompound.getString("Text" + (var2 + 1));
            if (this.signText[var2].length() > 15) {
                this.signText[var2] = this.signText[var2].substring(0, 15);
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final String[] var1 = new String[4];
        System.arraycopy(this.signText, 0, var1, 0, 4);
        return new Packet130UpdateSign(this.xCoord, this.yCoord, this.zCoord, var1);
    }
    
    public boolean isEditable() {
        return this.isEditable;
    }
    
    public void setEditable(final boolean par1) {
        this.isEditable = par1;
    }
}
