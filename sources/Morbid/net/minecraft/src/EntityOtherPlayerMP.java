package net.minecraft.src;

import net.minecraft.client.*;

public class EntityOtherPlayerMP extends EntityPlayer
{
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;
    
    public EntityOtherPlayerMP(final World par1World, final String par2Str) {
        super(par1World);
        this.isItemInUse = false;
        this.username = par2Str;
        this.yOffset = 0.0f;
        this.stepHeight = 0.0f;
        if (par2Str != null && par2Str.length() > 0) {
            this.skinUrl = "http://mcdata0.craftlandia.com.br/mcskins/" + StringUtils.stripControlCodes(par2Str) + ".png";
        }
        this.noClip = true;
        this.field_71082_cx = 0.25f;
        this.renderDistanceWeight = 10.0;
    }
    
    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return true;
    }
    
    @Override
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.otherPlayerMPX = par1;
        this.otherPlayerMPY = par3;
        this.otherPlayerMPZ = par5;
        this.otherPlayerMPYaw = par7;
        this.otherPlayerMPPitch = par8;
        this.otherPlayerMPPosRotationIncrements = par9;
    }
    
    @Override
    public void updateCloak() {
        this.cloakUrl = "http://mcdata0.craftlandia.com.br/mccloaks/" + StringUtils.stripControlCodes(this.username) + ".png";
    }
    
    @Override
    public void onUpdate() {
        this.field_71082_cx = 0.0f;
        super.onUpdate();
        this.prevLimbYaw = this.limbYaw;
        final double var1 = this.posX - this.prevPosX;
        final double var2 = this.posZ - this.prevPosZ;
        float var3 = MathHelper.sqrt_double(var1 * var1 + var2 * var2) * 4.0f;
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        this.limbYaw += (var3 - this.limbYaw) * 0.4f;
        this.limbSwing += this.limbYaw;
        if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
            final ItemStack var4 = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], Item.itemsList[var4.itemID].getMaxItemUseDuration(var4));
            this.isItemInUse = true;
        }
        else if (this.isItemInUse && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        super.updateEntityActionState();
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            final double var1 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
            final double var2 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
            final double var3 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            double var4;
            for (var4 = this.otherPlayerMPYaw - this.rotationYaw; var4 < -180.0; var4 += 360.0) {}
            while (var4 >= 180.0) {
                var4 -= 360.0;
            }
            this.rotationYaw += (float)(var4 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch += (float)((this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(var1, var2, var3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        float var5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var6 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (var5 > 0.1f) {
            var5 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0) {
            var5 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0) {
            var6 = 0.0f;
        }
        this.cameraYaw += (var5 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var6 - this.cameraPitch) * 0.8f;
    }
    
    @Override
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
        if (par1 == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = par2ItemStack;
        }
        else {
            this.inventory.armorInventory[par1 - 1] = par2ItemStack;
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 1.82f;
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(par1Str);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return false;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }
}
