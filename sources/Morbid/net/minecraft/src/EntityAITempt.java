package net.minecraft.src;

public class EntityAITempt extends EntityAIBase
{
    private EntityCreature temptedEntity;
    private float field_75282_b;
    private double field_75283_c;
    private double field_75280_d;
    private double field_75281_e;
    private double field_75278_f;
    private double field_75279_g;
    private EntityPlayer temptingPlayer;
    private int delayTemptCounter;
    private boolean field_75287_j;
    private int breedingFood;
    private boolean scaredByPlayerMovement;
    private boolean field_75286_m;
    
    public EntityAITempt(final EntityCreature par1EntityCreature, final float par2, final int par3, final boolean par4) {
        this.delayTemptCounter = 0;
        this.temptedEntity = par1EntityCreature;
        this.field_75282_b = par2;
        this.breedingFood = par3;
        this.scaredByPlayerMovement = par4;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        }
        this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return false;
        }
        final ItemStack var1 = this.temptingPlayer.getCurrentEquippedItem();
        return var1 != null && var1.itemID == this.breedingFood;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.field_75283_c, this.field_75280_d, this.field_75281_e) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs(this.temptingPlayer.rotationPitch - this.field_75278_f) > 5.0 || Math.abs(this.temptingPlayer.rotationYaw - this.field_75279_g) > 5.0) {
                    return false;
                }
            }
            else {
                this.field_75283_c = this.temptingPlayer.posX;
                this.field_75280_d = this.temptingPlayer.posY;
                this.field_75281_e = this.temptingPlayer.posZ;
            }
            this.field_75278_f = this.temptingPlayer.rotationPitch;
            this.field_75279_g = this.temptingPlayer.rotationYaw;
        }
        return this.shouldExecute();
    }
    
    @Override
    public void startExecuting() {
        this.field_75283_c = this.temptingPlayer.posX;
        this.field_75280_d = this.temptingPlayer.posY;
        this.field_75281_e = this.temptingPlayer.posZ;
        this.field_75287_j = true;
        this.field_75286_m = this.temptedEntity.getNavigator().getAvoidsWater();
        this.temptedEntity.getNavigator().setAvoidsWater(false);
    }
    
    @Override
    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
        this.field_75287_j = false;
        this.temptedEntity.getNavigator().setAvoidsWater(this.field_75286_m);
    }
    
    @Override
    public void updateTask() {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0f, this.temptedEntity.getVerticalFaceSpeed());
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator().clearPathEntity();
        }
        else {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.field_75282_b);
        }
    }
    
    public boolean func_75277_f() {
        return this.field_75287_j;
    }
}
