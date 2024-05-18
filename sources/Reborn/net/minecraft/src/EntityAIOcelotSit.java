package net.minecraft.src;

public class EntityAIOcelotSit extends EntityAIBase
{
    private final EntityOcelot theOcelot;
    private final float field_75404_b;
    private int currentTick;
    private int field_75402_d;
    private int maxSittingTicks;
    private int sitableBlockX;
    private int sitableBlockY;
    private int sitableBlockZ;
    
    public EntityAIOcelotSit(final EntityOcelot par1EntityOcelot, final float par2) {
        this.currentTick = 0;
        this.field_75402_d = 0;
        this.maxSittingTicks = 0;
        this.sitableBlockX = 0;
        this.sitableBlockY = 0;
        this.sitableBlockZ = 0;
        this.theOcelot = par1EntityOcelot;
        this.field_75404_b = par2;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theOcelot.isTamed() && !this.theOcelot.isSitting() && this.theOcelot.getRNG().nextDouble() <= 0.006500000134110451 && this.getNearbySitableBlockDistance();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.currentTick <= this.maxSittingTicks && this.field_75402_d <= 60 && this.isSittableBlock(this.theOcelot.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
    }
    
    @Override
    public void startExecuting() {
        this.theOcelot.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5, this.sitableBlockY + 1, this.sitableBlockZ + 0.5, this.field_75404_b);
        this.currentTick = 0;
        this.field_75402_d = 0;
        this.maxSittingTicks = this.theOcelot.getRNG().nextInt(this.theOcelot.getRNG().nextInt(1200) + 1200) + 1200;
        this.theOcelot.func_70907_r().setSitting(false);
    }
    
    @Override
    public void resetTask() {
        this.theOcelot.setSitting(false);
    }
    
    @Override
    public void updateTask() {
        ++this.currentTick;
        this.theOcelot.func_70907_r().setSitting(false);
        if (this.theOcelot.getDistanceSq(this.sitableBlockX, this.sitableBlockY + 1, this.sitableBlockZ) > 1.0) {
            this.theOcelot.setSitting(false);
            this.theOcelot.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5, this.sitableBlockY + 1, this.sitableBlockZ + 0.5, this.field_75404_b);
            ++this.field_75402_d;
        }
        else if (!this.theOcelot.isSitting()) {
            this.theOcelot.setSitting(true);
        }
        else {
            --this.field_75402_d;
        }
    }
    
    private boolean getNearbySitableBlockDistance() {
        final int var1 = (int)this.theOcelot.posY;
        double var2 = 2.147483647E9;
        for (int var3 = (int)this.theOcelot.posX - 8; var3 < this.theOcelot.posX + 8.0; ++var3) {
            for (int var4 = (int)this.theOcelot.posZ - 8; var4 < this.theOcelot.posZ + 8.0; ++var4) {
                if (this.isSittableBlock(this.theOcelot.worldObj, var3, var1, var4) && this.theOcelot.worldObj.isAirBlock(var3, var1 + 1, var4)) {
                    final double var5 = this.theOcelot.getDistanceSq(var3, var1, var4);
                    if (var5 < var2) {
                        this.sitableBlockX = var3;
                        this.sitableBlockY = var1;
                        this.sitableBlockZ = var4;
                        var2 = var5;
                    }
                }
            }
        }
        return var2 < 2.147483647E9;
    }
    
    private boolean isSittableBlock(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3, par4);
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if (var5 == Block.chest.blockID) {
            final TileEntityChest var7 = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);
            if (var7.numUsingPlayers < 1) {
                return true;
            }
        }
        else {
            if (var5 == Block.furnaceBurning.blockID) {
                return true;
            }
            if (var5 == Block.bed.blockID && !BlockBed.isBlockHeadOfBed(var6)) {
                return true;
            }
        }
        return false;
    }
}
