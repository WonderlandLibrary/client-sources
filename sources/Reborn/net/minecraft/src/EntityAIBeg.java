package net.minecraft.src;

public class EntityAIBeg extends EntityAIBase
{
    private EntityWolf theWolf;
    private EntityPlayer thePlayer;
    private World worldObject;
    private float minPlayerDistance;
    private int field_75384_e;
    
    public EntityAIBeg(final EntityWolf par1EntityWolf, final float par2) {
        this.theWolf = par1EntityWolf;
        this.worldObject = par1EntityWolf.worldObj;
        this.minPlayerDistance = par2;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
        return this.thePlayer != null && this.hasPlayerGotBoneInHand(this.thePlayer);
    }
    
    @Override
    public boolean continueExecuting() {
        return this.thePlayer.isEntityAlive() && this.theWolf.getDistanceSqToEntity(this.thePlayer) <= this.minPlayerDistance * this.minPlayerDistance && (this.field_75384_e > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
    }
    
    @Override
    public void startExecuting() {
        this.theWolf.func_70918_i(true);
        this.field_75384_e = 40 + this.theWolf.getRNG().nextInt(40);
    }
    
    @Override
    public void resetTask() {
        this.theWolf.func_70918_i(false);
        this.thePlayer = null;
    }
    
    @Override
    public void updateTask() {
        this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0f, this.theWolf.getVerticalFaceSpeed());
        --this.field_75384_e;
    }
    
    private boolean hasPlayerGotBoneInHand(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        return var2 != null && ((!this.theWolf.isTamed() && var2.itemID == Item.bone.itemID) || this.theWolf.isBreedingItem(var2));
    }
}
