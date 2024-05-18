package net.minecraft.src;

public class PotionEffect
{
    private int potionID;
    private int duration;
    private int amplifier;
    private boolean isSplashPotion;
    private boolean isAmbient;
    private boolean isPotionDurationMax;
    
    public PotionEffect(final int par1, final int par2) {
        this(par1, par2, 0);
    }
    
    public PotionEffect(final int par1, final int par2, final int par3) {
        this(par1, par2, par3, false);
    }
    
    public PotionEffect(final int par1, final int par2, final int par3, final boolean par4) {
        this.potionID = par1;
        this.duration = par2;
        this.amplifier = par3;
        this.isAmbient = par4;
    }
    
    public PotionEffect(final PotionEffect par1PotionEffect) {
        this.potionID = par1PotionEffect.potionID;
        this.duration = par1PotionEffect.duration;
        this.amplifier = par1PotionEffect.amplifier;
    }
    
    public void combine(final PotionEffect par1PotionEffect) {
        if (this.potionID != par1PotionEffect.potionID) {
            System.err.println("This method should only be called for matching effects!");
        }
        if (par1PotionEffect.amplifier > this.amplifier) {
            this.amplifier = par1PotionEffect.amplifier;
            this.duration = par1PotionEffect.duration;
        }
        else if (par1PotionEffect.amplifier == this.amplifier && this.duration < par1PotionEffect.duration) {
            this.duration = par1PotionEffect.duration;
        }
        else if (!par1PotionEffect.isAmbient && this.isAmbient) {
            this.isAmbient = par1PotionEffect.isAmbient;
        }
    }
    
    public int getPotionID() {
        return this.potionID;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getAmplifier() {
        return this.amplifier;
    }
    
    public boolean isSplashPotionEffect() {
        return this.isSplashPotion;
    }
    
    public void setSplashPotion(final boolean par1) {
        this.isSplashPotion = par1;
    }
    
    public boolean getIsAmbient() {
        return this.isAmbient;
    }
    
    public boolean onUpdate(final EntityLiving par1EntityLiving) {
        if (this.duration > 0) {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
                this.performEffect(par1EntityLiving);
            }
            this.deincrementDuration();
        }
        return this.duration > 0;
    }
    
    private int deincrementDuration() {
        return --this.duration;
    }
    
    public void performEffect(final EntityLiving par1EntityLiving) {
        if (this.duration > 0) {
            Potion.potionTypes[this.potionID].performEffect(par1EntityLiving, this.amplifier);
        }
    }
    
    public String getEffectName() {
        return Potion.potionTypes[this.potionID].getName();
    }
    
    @Override
    public int hashCode() {
        return this.potionID;
    }
    
    @Override
    public String toString() {
        String var1 = "";
        if (this.getAmplifier() > 0) {
            var1 = String.valueOf(this.getEffectName()) + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else {
            var1 = String.valueOf(this.getEffectName()) + ", Duration: " + this.getDuration();
        }
        if (this.isSplashPotion) {
            var1 = String.valueOf(var1) + ", Splash: true";
        }
        return Potion.potionTypes[this.potionID].isUsable() ? ("(" + var1 + ")") : var1;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof PotionEffect)) {
            return false;
        }
        final PotionEffect var2 = (PotionEffect)par1Obj;
        return this.potionID == var2.potionID && this.amplifier == var2.amplifier && this.duration == var2.duration && this.isSplashPotion == var2.isSplashPotion && this.isAmbient == var2.isAmbient;
    }
    
    public NBTTagCompound writeCustomPotionEffectToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Id", (byte)this.getPotionID());
        par1NBTTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        par1NBTTagCompound.setInteger("Duration", this.getDuration());
        par1NBTTagCompound.setBoolean("Ambient", this.getIsAmbient());
        return par1NBTTagCompound;
    }
    
    public static PotionEffect readCustomPotionEffectFromNBT(final NBTTagCompound par0NBTTagCompound) {
        final byte var1 = par0NBTTagCompound.getByte("Id");
        final byte var2 = par0NBTTagCompound.getByte("Amplifier");
        final int var3 = par0NBTTagCompound.getInteger("Duration");
        final boolean var4 = par0NBTTagCompound.getBoolean("Ambient");
        return new PotionEffect(var1, var3, var2, var4);
    }
    
    public void setPotionDurationMax(final boolean par1) {
        this.isPotionDurationMax = par1;
    }
    
    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }
}
