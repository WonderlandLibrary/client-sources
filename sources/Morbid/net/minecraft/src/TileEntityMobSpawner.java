package net.minecraft.src;

public class TileEntityMobSpawner extends TileEntity
{
    private final MobSpawnerBaseLogic field_98050_a;
    
    public TileEntityMobSpawner() {
        this.field_98050_a = new TileEntityMobSpawnerLogic(this);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.field_98050_a.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        this.field_98050_a.writeToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void updateEntity() {
        this.field_98050_a.updateSpawner();
        super.updateEntity();
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        var1.removeTag("SpawnPotentials");
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
    
    @Override
    public boolean receiveClientEvent(final int par1, final int par2) {
        return this.field_98050_a.setDelayToMin(par1) || super.receiveClientEvent(par1, par2);
    }
    
    public MobSpawnerBaseLogic func_98049_a() {
        return this.field_98050_a;
    }
}
