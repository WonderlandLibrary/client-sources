package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
   private double otherPlayerMPPitch;
   private double otherPlayerMPZ;
   private double otherPlayerMPX;
   private boolean isItemInUse;
   private double otherPlayerMPY;
   private int otherPlayerMPPosRotationIncrements;
   private double otherPlayerMPYaw;
   private static final String __OBFID = "CL_00000939";

   public void onLivingUpdate() {
      if (this.otherPlayerMPPosRotationIncrements > 0) {
         double var1 = this.posX + (this.otherPlayerMPX - this.posX) / (double)this.otherPlayerMPPosRotationIncrements;
         double var3 = this.posY + (this.otherPlayerMPY - this.posY) / (double)this.otherPlayerMPPosRotationIncrements;
         double var5 = this.posZ + (this.otherPlayerMPZ - this.posZ) / (double)this.otherPlayerMPPosRotationIncrements;

         double var7;
         for(var7 = this.otherPlayerMPYaw - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
         }

         while(var7 >= 180.0D) {
            var7 -= 360.0D;
         }

         this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.otherPlayerMPPosRotationIncrements);
         this.rotationPitch = (float)((double)this.rotationPitch + (this.otherPlayerMPPitch - (double)this.rotationPitch) / (double)this.otherPlayerMPPosRotationIncrements);
         --this.otherPlayerMPPosRotationIncrements;
         this.setPosition(var1, var3, var5);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      }

      this.prevCameraYaw = this.cameraYaw;
      this.updateArmSwingProgress();
      float var9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      float var2 = (float)Math.atan(-this.motionY * 0.20000000298023224D) * 15.0F;
      if (var9 > 0.1F) {
         var9 = 0.1F;
      }

      if (!this.onGround || this.getHealth() <= 0.0F) {
         var9 = 0.0F;
      }

      if (this.onGround || this.getHealth() <= 0.0F) {
         var2 = 0.0F;
      }

      this.cameraYaw += (var9 - this.cameraYaw) * 0.4F;
      this.cameraPitch += (var2 - this.cameraPitch) * 0.8F;
   }

   public void onUpdate() {
      this.field_71082_cx = 0.0F;
      super.onUpdate();
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double var1 = this.posX - this.prevPosX;
      double var3 = this.posZ - this.prevPosZ;
      float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;
      if (var5 > 1.0F) {
         var5 = 1.0F;
      }

      this.limbSwingAmount += (var5 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
      if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
         ItemStack var6 = this.inventory.mainInventory[this.inventory.currentItem];
         this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], var6.getItem().getMaxItemUseDuration(var6));
         this.isItemInUse = true;
      } else if (this.isItemInUse && !this.isEating()) {
         this.clearItemInUse();
         this.isItemInUse = false;
      }

   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return false;
   }

   public EntityOtherPlayerMP(World var1, GameProfile var2) {
      super(var1, var2);
      this.stepHeight = 0.0F;
      this.noClip = true;
      this.field_71082_cx = 0.25F;
      this.renderDistanceWeight = 10.0D;
   }

   public void addChatMessage(IChatComponent var1) {
      Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(var1);
   }

   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.otherPlayerMPX = var1;
      this.otherPlayerMPY = var3;
      this.otherPlayerMPZ = var5;
      this.otherPlayerMPYaw = (double)var7;
      this.otherPlayerMPPitch = (double)var8;
      this.otherPlayerMPPosRotationIncrements = var9;
   }

   public BlockPos getPosition() {
      return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
   }

   public void setCurrentItemOrArmor(int var1, ItemStack var2) {
      if (var1 == 0) {
         this.inventory.mainInventory[this.inventory.currentItem] = var2;
      } else {
         this.inventory.armorInventory[var1 - 1] = var2;
      }

   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return true;
   }
}
