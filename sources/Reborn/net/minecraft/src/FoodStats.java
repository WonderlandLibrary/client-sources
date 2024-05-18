package net.minecraft.src;

public class FoodStats
{
    private int foodLevel;
    private float foodSaturationLevel;
    private float foodExhaustionLevel;
    private int foodTimer;
    private int prevFoodLevel;
    
    public FoodStats() {
        this.foodLevel = 20;
        this.foodSaturationLevel = 5.0f;
        this.foodTimer = 0;
        this.prevFoodLevel = 20;
    }
    
    public void addStats(final int par1, final float par2) {
        this.foodLevel = Math.min(par1 + this.foodLevel, 20);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + par1 * par2 * 2.0f, this.foodLevel);
    }
    
    public void addStats(final ItemFood par1ItemFood) {
        this.addStats(par1ItemFood.getHealAmount(), par1ItemFood.getSaturationModifier());
    }
    
    public void onUpdate(final EntityPlayer par1EntityPlayer) {
        final int var2 = par1EntityPlayer.worldObj.difficultySetting;
        this.prevFoodLevel = this.foodLevel;
        if (this.foodExhaustionLevel > 4.0f) {
            this.foodExhaustionLevel -= 4.0f;
            if (this.foodSaturationLevel > 0.0f) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0f, 0.0f);
            }
            else if (var2 > 0) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }
        if (this.foodLevel >= 18 && par1EntityPlayer.shouldHeal()) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                par1EntityPlayer.heal(1);
                this.foodTimer = 0;
            }
        }
        else if (this.foodLevel <= 0) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                if (par1EntityPlayer.getHealth() > 10 || var2 >= 3 || (par1EntityPlayer.getHealth() > 1 && var2 >= 2)) {
                    par1EntityPlayer.attackEntityFrom(DamageSource.starve, 1);
                }
                this.foodTimer = 0;
            }
        }
        else {
            this.foodTimer = 0;
        }
    }
    
    public void readNBT(final NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.hasKey("foodLevel")) {
            this.foodLevel = par1NBTTagCompound.getInteger("foodLevel");
            this.foodTimer = par1NBTTagCompound.getInteger("foodTickTimer");
            this.foodSaturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
        }
    }
    
    public void writeNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("foodLevel", this.foodLevel);
        par1NBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
        par1NBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        par1NBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }
    
    public boolean needFood() {
        return this.foodLevel < 20;
    }
    
    public void addExhaustion(final float par1) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0f);
    }
    
    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }
    
    public void setFoodLevel(final int par1) {
        this.foodLevel = par1;
    }
    
    public void setFoodSaturationLevel(final float par1) {
        this.foodSaturationLevel = par1;
    }
}
