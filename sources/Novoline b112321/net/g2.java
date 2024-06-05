package net;

import java.util.UUID;
import net.aIj;
import net.aSv;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class g2 {
   Minecraft a = Minecraft.getInstance();

   @aSv
   public void jump() {
      this.a.player.jump();
   }

   @aSv
   public boolean isOnGround() {
      return this.a.player.onGround;
   }

   @aSv
   public void setPosition(double var1, double var3, double var5) {
      this.a.player.setPosition(var1, var3, var5);
   }

   @aSv
   public double getBaseMoveSpeed() {
      return this.a.player.getBaseMoveSpeed();
   }

   @aSv
   public double getSpeed() {
      return this.a.player.getSpeed();
   }

   @aSv
   public void setSpeed(double var1) {
      this.a.player.setSpeed(var1);
   }

   @aSv
   public String getName() {
      return this.a.player.getName();
   }

   @aSv
   public String getHeldItemName() {
      return this.a.player.getHeldItem().getItem().getUnlocalizedName();
   }

   @aSv
   public String getHeldItemDisplayName() {
      return this.a.player.getHeldItem().getDisplayName();
   }

   @aSv
   public String getTagCompound() {
      return this.a.player.getHeldItem().getTagCompound().toString();
   }

   @aSv
   public double getLastTickDistance() {
      boolean var1 = aIj.c();
      double var10000 = Math.hypot(this.a.player.posX - this.a.player.prevPosX, this.a.player.posZ - this.a.player.prevPosZ);
      if(acE.b() == null) {
         aIj.b(false);
      }

      return var10000;
   }

   @aSv
   public double setMotionY(double var1) {
      this.a.player.motionY = var1;
      return var1;
   }

   @aSv
   public double setMotionX(double var1) {
      this.a.player.motionX = var1;
      return this.a.player.motionY = var1;
   }

   @aSv
   public double setMotionZ(double var1) {
      this.a.player.motionZ = var1;
      return this.a.player.motionY = var1;
   }

   @aSv
   public double getX() {
      return this.a.player.posX;
   }

   @aSv
   public double getY() {
      return this.a.player.posY;
   }

   @aSv
   public double getZ() {
      return this.a.player.posZ;
   }

   @aSv
   public double setZ(double var1) {
      this.a.player.posZ = var1;
      return var1;
   }

   @aSv
   public double setY(double var1) {
      this.a.player.posY = var1;
      return var1;
   }

   @aSv
   public double setX(double var1) {
      this.a.player.posX = var1;
      return var1;
   }

   @aSv
   public void attackEntity(Entity var1) {
      this.a.at.b((EntityPlayer)this.a.player, (Entity)var1);
   }

   @aSv
   public double getDistanceToEntity(Entity var1) {
      return (double)this.a.player.getDistanceToEntity(var1);
   }

   @aSv
   public double getDistanceToEntity2D(Entity var1) {
      return this.a.player.getDistance2D(var1);
   }

   @aSv
   public void swingItem() {
      this.a.player.swingItem();
   }

   @aSv
   public double getFallDistance() {
      return (double)this.a.player.fallDistance;
   }

   @aSv
   public ItemStack getHeldItem() {
      return this.a.player.getHeldItem();
   }

   @aSv
   public boolean isMoving() {
      return this.a.player.isMoving();
   }

   @aSv
   public double getHealth() {
      return (double)this.a.player.getHealth();
   }

   @aSv
   public void sendMessage(String var1) {
      this.a.player.c(var1);
   }

   @aSv
   public String getDisplayName() {
      return this.a.player.getDisplayName().getFormattedText();
   }

   @aSv
   public void setSlot(int var1) {
      this.a.player.inventory.currentItem = var1;
   }

   @aSv
   public int getHurtTime() {
      return this.a.player.hurtTime;
   }

   @aSv
   public int hurtResistantTime() {
      return this.a.player.hurtResistantTime;
   }

   @aSv
   public int getFoodLevel() {
      return this.a.player.getFoodStats().getFoodLevel();
   }

   @aSv
   public boolean isBurning() {
      return this.a.player.isBurning();
   }

   @aSv
   public void leftClick() {
      this.a.clickMouse();
   }

   @aSv
   public void rightClick() {
      this.a.rightClickMouse();
   }

   @aSv
   public boolean isInWater() {
      return this.a.player.isInWater();
   }

   @aSv
   public boolean isInLava() {
      return this.a.player.isInLava();
   }

   @aSv
   public boolean isSneaking() {
      return this.a.player.isSneaking();
   }

   @aSv
   public void setSneaking(boolean var1) {
      this.a.player.setSneaking(var1);
   }

   @aSv
   public boolean isDead() {
      return this.a.player.isDead;
   }

   @aSv
   public boolean isSprinting() {
      return this.a.player.isSneaking();
   }

   @aSv
   public void setSprinting(boolean var1) {
      this.a.player.setSprinting(var1);
   }

   @aSv
   public boolean isRiding() {
      return this.a.player.isRiding();
   }

   @aSv
   public boolean isCollidedVertically() {
      return this.a.player.isCollidedVertically;
   }

   @aSv
   public boolean isCollidedHorizontally() {
      return this.a.player.isCollidedHorizontally;
   }

   @aSv
   public int ticksExisted() {
      return this.a.player.ticksExisted;
   }

   @aSv
   public void setYaw(float var1) {
      this.a.player.rotationYaw = var1;
   }

   @aSv
   public void setPitch(float var1) {
      this.a.player.rotationPitch = var1;
   }

   @aSv
   public UUID getUUID() {
      return this.a.player.getUniqueID();
   }

   @aSv
   public double getPrevPosX() {
      return this.a.player.prevPosX;
   }

   @aSv
   public double getPrevPosY() {
      return this.a.player.prevPosZ;
   }

   @aSv
   public double getPrevPosZ() {
      return this.a.player.prevPosZ;
   }

   @aSv
   public boolean isUsingItem() {
      return this.a.player.isUsingItem();
   }
}
