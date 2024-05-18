package net.minecraft.src;

public class PlayerCapabilities
{
    public boolean disableDamage;
    public boolean isFlying;
    public boolean allowFlying;
    public boolean isCreativeMode;
    public boolean allowEdit;
    private float flySpeed;
    private float walkSpeed;
    
    public PlayerCapabilities() {
        this.disableDamage = false;
        this.isFlying = false;
        this.allowFlying = false;
        this.isCreativeMode = false;
        this.allowEdit = true;
        this.flySpeed = 0.05f;
        this.walkSpeed = 0.1f;
    }
    
    public void writeCapabilitiesToNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagCompound var2 = new NBTTagCompound();
        var2.setBoolean("invulnerable", this.disableDamage);
        var2.setBoolean("flying", this.isFlying);
        var2.setBoolean("mayfly", this.allowFlying);
        var2.setBoolean("instabuild", this.isCreativeMode);
        var2.setBoolean("mayBuild", this.allowEdit);
        var2.setFloat("flySpeed", this.flySpeed);
        var2.setFloat("walkSpeed", this.walkSpeed);
        par1NBTTagCompound.setTag("abilities", var2);
    }
    
    public void readCapabilitiesFromNBT(final NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.hasKey("abilities")) {
            final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("abilities");
            this.disableDamage = var2.getBoolean("invulnerable");
            this.isFlying = var2.getBoolean("flying");
            this.allowFlying = var2.getBoolean("mayfly");
            this.isCreativeMode = var2.getBoolean("instabuild");
            if (var2.hasKey("flySpeed")) {
                this.flySpeed = var2.getFloat("flySpeed");
                this.walkSpeed = var2.getFloat("walkSpeed");
            }
            if (var2.hasKey("mayBuild")) {
                this.allowEdit = var2.getBoolean("mayBuild");
            }
        }
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public void setFlySpeed(final float par1) {
        this.flySpeed = par1;
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    public void setPlayerWalkSpeed(final float par1) {
        this.walkSpeed = par1;
    }
}
