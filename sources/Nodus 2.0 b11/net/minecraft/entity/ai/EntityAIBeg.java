/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.passive.EntityWolf;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityAIBeg
/* 12:   */   extends EntityAIBase
/* 13:   */ {
/* 14:   */   private EntityWolf theWolf;
/* 15:   */   private EntityPlayer thePlayer;
/* 16:   */   private World worldObject;
/* 17:   */   private float minPlayerDistance;
/* 18:   */   private int field_75384_e;
/* 19:   */   private static final String __OBFID = "CL_00001576";
/* 20:   */   
/* 21:   */   public EntityAIBeg(EntityWolf par1EntityWolf, float par2)
/* 22:   */   {
/* 23:20 */     this.theWolf = par1EntityWolf;
/* 24:21 */     this.worldObject = par1EntityWolf.worldObj;
/* 25:22 */     this.minPlayerDistance = par2;
/* 26:23 */     setMutexBits(2);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean shouldExecute()
/* 30:   */   {
/* 31:31 */     this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
/* 32:32 */     return this.thePlayer == null ? false : hasPlayerGotBoneInHand(this.thePlayer);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean continueExecuting()
/* 36:   */   {
/* 37:40 */     return this.thePlayer.isEntityAlive();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void startExecuting()
/* 41:   */   {
/* 42:48 */     this.theWolf.func_70918_i(true);
/* 43:49 */     this.field_75384_e = (40 + this.theWolf.getRNG().nextInt(40));
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void resetTask()
/* 47:   */   {
/* 48:57 */     this.theWolf.func_70918_i(false);
/* 49:58 */     this.thePlayer = null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void updateTask()
/* 53:   */   {
/* 54:66 */     this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, this.theWolf.getVerticalFaceSpeed());
/* 55:67 */     this.field_75384_e -= 1;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private boolean hasPlayerGotBoneInHand(EntityPlayer par1EntityPlayer)
/* 59:   */   {
/* 60:75 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 61:76 */     return (!this.theWolf.isTamed()) && (var2.getItem() == Items.bone) ? true : var2 == null ? false : this.theWolf.isBreedingItem(var2);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIBeg
 * JD-Core Version:    0.7.0.1
 */