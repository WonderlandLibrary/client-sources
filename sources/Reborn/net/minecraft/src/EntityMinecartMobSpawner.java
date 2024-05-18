package net.minecraft.src;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    
    public EntityMinecartMobSpawner(final World par1World) {
        super(par1World);
        this.mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);
    }
    
    public EntityMinecartMobSpawner(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);
    }
    
    @Override
    public int getMinecartType() {
        return 4;
    }
    
    @Override
    public Block getDefaultDisplayTile() {
        return Block.mobSpawner;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        this.mobSpawnerLogic.setDelayToMin(par1);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
    
    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }
}
