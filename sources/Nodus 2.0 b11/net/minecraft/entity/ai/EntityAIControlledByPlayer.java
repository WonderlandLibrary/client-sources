/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockSlab;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.EntityCreature;
/*   8:    */ import net.minecraft.entity.EntityLiving;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.pathfinding.PathFinder;
/*  14:    */ import net.minecraft.pathfinding.PathPoint;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class EntityAIControlledByPlayer
/*  19:    */   extends EntityAIBase
/*  20:    */ {
/*  21:    */   private final EntityLiving thisEntity;
/*  22:    */   private final float maxSpeed;
/*  23:    */   private float currentSpeed;
/*  24:    */   private boolean speedBoosted;
/*  25:    */   private int speedBoostTime;
/*  26:    */   private int maxSpeedBoostTime;
/*  27:    */   private static final String __OBFID = "CL_00001580";
/*  28:    */   
/*  29:    */   public EntityAIControlledByPlayer(EntityLiving par1EntityLiving, float par2)
/*  30:    */   {
/*  31: 35 */     this.thisEntity = par1EntityLiving;
/*  32: 36 */     this.maxSpeed = par2;
/*  33: 37 */     setMutexBits(7);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void startExecuting()
/*  37:    */   {
/*  38: 45 */     this.currentSpeed = 0.0F;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void resetTask()
/*  42:    */   {
/*  43: 53 */     this.speedBoosted = false;
/*  44: 54 */     this.currentSpeed = 0.0F;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean shouldExecute()
/*  48:    */   {
/*  49: 62 */     return (this.thisEntity.isEntityAlive()) && (this.thisEntity.riddenByEntity != null) && ((this.thisEntity.riddenByEntity instanceof EntityPlayer)) && ((this.speedBoosted) || (this.thisEntity.canBeSteered()));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void updateTask()
/*  53:    */   {
/*  54: 70 */     EntityPlayer var1 = (EntityPlayer)this.thisEntity.riddenByEntity;
/*  55: 71 */     EntityCreature var2 = (EntityCreature)this.thisEntity;
/*  56: 72 */     float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - this.thisEntity.rotationYaw) * 0.5F;
/*  57: 74 */     if (var3 > 5.0F) {
/*  58: 76 */       var3 = 5.0F;
/*  59:    */     }
/*  60: 79 */     if (var3 < -5.0F) {
/*  61: 81 */       var3 = -5.0F;
/*  62:    */     }
/*  63: 84 */     this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + var3);
/*  64: 86 */     if (this.currentSpeed < this.maxSpeed) {
/*  65: 88 */       this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01F;
/*  66:    */     }
/*  67: 91 */     if (this.currentSpeed > this.maxSpeed) {
/*  68: 93 */       this.currentSpeed = this.maxSpeed;
/*  69:    */     }
/*  70: 96 */     int var4 = MathHelper.floor_double(this.thisEntity.posX);
/*  71: 97 */     int var5 = MathHelper.floor_double(this.thisEntity.posY);
/*  72: 98 */     int var6 = MathHelper.floor_double(this.thisEntity.posZ);
/*  73: 99 */     float var7 = this.currentSpeed;
/*  74:101 */     if (this.speedBoosted)
/*  75:    */     {
/*  76:103 */       if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
/*  77:105 */         this.speedBoosted = false;
/*  78:    */       }
/*  79:108 */       var7 += var7 * 1.15F * MathHelper.sin(this.speedBoostTime / this.maxSpeedBoostTime * 3.141593F);
/*  80:    */     }
/*  81:111 */     float var8 = 0.91F;
/*  82:113 */     if (this.thisEntity.onGround) {
/*  83:115 */       var8 = this.thisEntity.worldObj.getBlock(MathHelper.floor_float(var4), MathHelper.floor_float(var5) - 1, MathHelper.floor_float(var6)).slipperiness * 0.91F;
/*  84:    */     }
/*  85:118 */     float var9 = 0.1627714F / (var8 * var8 * var8);
/*  86:119 */     float var10 = MathHelper.sin(var2.rotationYaw * 3.141593F / 180.0F);
/*  87:120 */     float var11 = MathHelper.cos(var2.rotationYaw * 3.141593F / 180.0F);
/*  88:121 */     float var12 = var2.getAIMoveSpeed() * var9;
/*  89:122 */     float var13 = Math.max(var7, 1.0F);
/*  90:123 */     var13 = var12 / var13;
/*  91:124 */     float var14 = var7 * var13;
/*  92:125 */     float var15 = -(var14 * var10);
/*  93:126 */     float var16 = var14 * var11;
/*  94:128 */     if (MathHelper.abs(var15) > MathHelper.abs(var16))
/*  95:    */     {
/*  96:130 */       if (var15 < 0.0F) {
/*  97:132 */         var15 -= this.thisEntity.width / 2.0F;
/*  98:    */       }
/*  99:135 */       if (var15 > 0.0F) {
/* 100:137 */         var15 += this.thisEntity.width / 2.0F;
/* 101:    */       }
/* 102:140 */       var16 = 0.0F;
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:144 */       var15 = 0.0F;
/* 107:146 */       if (var16 < 0.0F) {
/* 108:148 */         var16 -= this.thisEntity.width / 2.0F;
/* 109:    */       }
/* 110:151 */       if (var16 > 0.0F) {
/* 111:153 */         var16 += this.thisEntity.width / 2.0F;
/* 112:    */       }
/* 113:    */     }
/* 114:157 */     int var17 = MathHelper.floor_double(this.thisEntity.posX + var15);
/* 115:158 */     int var18 = MathHelper.floor_double(this.thisEntity.posZ + var16);
/* 116:159 */     PathPoint var19 = new PathPoint(MathHelper.floor_float(this.thisEntity.width + 1.0F), MathHelper.floor_float(this.thisEntity.height + var1.height + 1.0F), MathHelper.floor_float(this.thisEntity.width + 1.0F));
/* 117:161 */     if ((var4 != var17) || (var6 != var18))
/* 118:    */     {
/* 119:163 */       Block var20 = this.thisEntity.worldObj.getBlock(var4, var5, var6);
/* 120:164 */       boolean var21 = (!func_151498_a(var20)) && ((var20.getMaterial() != Material.air) || (!func_151498_a(this.thisEntity.worldObj.getBlock(var4, var5 - 1, var6))));
/* 121:166 */       if ((var21) && (PathFinder.func_82565_a(this.thisEntity, var17, var5, var18, var19, false, false, true) == 0) && (PathFinder.func_82565_a(this.thisEntity, var4, var5 + 1, var6, var19, false, false, true) == 1) && (PathFinder.func_82565_a(this.thisEntity, var17, var5 + 1, var18, var19, false, false, true) == 1)) {
/* 122:168 */         var2.getJumpHelper().setJumping();
/* 123:    */       }
/* 124:    */     }
/* 125:172 */     if ((!var1.capabilities.isCreativeMode) && (this.currentSpeed >= this.maxSpeed * 0.5F) && (this.thisEntity.getRNG().nextFloat() < 0.006F) && (!this.speedBoosted))
/* 126:    */     {
/* 127:174 */       ItemStack var22 = var1.getHeldItem();
/* 128:176 */       if ((var22 != null) && (var22.getItem() == Items.carrot_on_a_stick))
/* 129:    */       {
/* 130:178 */         var22.damageItem(1, var1);
/* 131:180 */         if (var22.stackSize == 0)
/* 132:    */         {
/* 133:182 */           ItemStack var23 = new ItemStack(Items.fishing_rod);
/* 134:183 */           var23.setTagCompound(var22.stackTagCompound);
/* 135:184 */           var1.inventory.mainInventory[var1.inventory.currentItem] = var23;
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:189 */     this.thisEntity.moveEntityWithHeading(0.0F, var7);
/* 140:    */   }
/* 141:    */   
/* 142:    */   private boolean func_151498_a(Block p_151498_1_)
/* 143:    */   {
/* 144:194 */     return (p_151498_1_.getRenderType() == 10) || ((p_151498_1_ instanceof BlockSlab));
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isSpeedBoosted()
/* 148:    */   {
/* 149:202 */     return this.speedBoosted;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void boostSpeed()
/* 153:    */   {
/* 154:210 */     this.speedBoosted = true;
/* 155:211 */     this.speedBoostTime = 0;
/* 156:212 */     this.maxSpeedBoostTime = (this.thisEntity.getRNG().nextInt(841) + 140);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isControlledByPlayer()
/* 160:    */   {
/* 161:220 */     return (!isSpeedBoosted()) && (this.currentSpeed > this.maxSpeed * 0.3F);
/* 162:    */   }
/* 163:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIControlledByPlayer
 * JD-Core Version:    0.7.0.1
 */