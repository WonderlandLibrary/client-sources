/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.item.ItemFood;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.world.EnumDifficulty;
/*   8:    */ import net.minecraft.world.GameRules;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class FoodStats
/*  12:    */ {
/*  13: 12 */   private int foodLevel = 20;
/*  14: 15 */   private float foodSaturationLevel = 5.0F;
/*  15:    */   private float foodExhaustionLevel;
/*  16:    */   private int foodTimer;
/*  17: 22 */   private int prevFoodLevel = 20;
/*  18:    */   private static final String __OBFID = "CL_00001729";
/*  19:    */   
/*  20:    */   public void addStats(int par1, float par2)
/*  21:    */   {
/*  22: 30 */     this.foodLevel = Math.min(par1 + this.foodLevel, 20);
/*  23: 31 */     this.foodSaturationLevel = Math.min(this.foodSaturationLevel + par1 * par2 * 2.0F, this.foodLevel);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void func_151686_a(ItemFood p_151686_1_, ItemStack p_151686_2_)
/*  27:    */   {
/*  28: 36 */     addStats(p_151686_1_.func_150905_g(p_151686_2_), p_151686_1_.func_150906_h(p_151686_2_));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void onUpdate(EntityPlayer par1EntityPlayer)
/*  32:    */   {
/*  33: 44 */     EnumDifficulty var2 = par1EntityPlayer.worldObj.difficultySetting;
/*  34: 45 */     this.prevFoodLevel = this.foodLevel;
/*  35: 47 */     if (this.foodExhaustionLevel > 4.0F)
/*  36:    */     {
/*  37: 49 */       this.foodExhaustionLevel -= 4.0F;
/*  38: 51 */       if (this.foodSaturationLevel > 0.0F) {
/*  39: 53 */         this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
/*  40: 55 */       } else if (var2 != EnumDifficulty.PEACEFUL) {
/*  41: 57 */         this.foodLevel = Math.max(this.foodLevel - 1, 0);
/*  42:    */       }
/*  43:    */     }
/*  44: 61 */     if ((par1EntityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) && (this.foodLevel >= 18) && (par1EntityPlayer.shouldHeal()))
/*  45:    */     {
/*  46: 63 */       this.foodTimer += 1;
/*  47: 65 */       if (this.foodTimer >= 80)
/*  48:    */       {
/*  49: 67 */         par1EntityPlayer.heal(1.0F);
/*  50: 68 */         addExhaustion(3.0F);
/*  51: 69 */         this.foodTimer = 0;
/*  52:    */       }
/*  53:    */     }
/*  54: 72 */     else if (this.foodLevel <= 0)
/*  55:    */     {
/*  56: 74 */       this.foodTimer += 1;
/*  57: 76 */       if (this.foodTimer >= 80)
/*  58:    */       {
/*  59: 78 */         if ((par1EntityPlayer.getHealth() > 10.0F) || (var2 == EnumDifficulty.HARD) || ((par1EntityPlayer.getHealth() > 1.0F) && (var2 == EnumDifficulty.NORMAL))) {
/*  60: 80 */           par1EntityPlayer.attackEntityFrom(DamageSource.starve, 1.0F);
/*  61:    */         }
/*  62: 83 */         this.foodTimer = 0;
/*  63:    */       }
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67: 88 */       this.foodTimer = 0;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void readNBT(NBTTagCompound par1NBTTagCompound)
/*  72:    */   {
/*  73: 97 */     if (par1NBTTagCompound.func_150297_b("foodLevel", 99))
/*  74:    */     {
/*  75: 99 */       this.foodLevel = par1NBTTagCompound.getInteger("foodLevel");
/*  76:100 */       this.foodTimer = par1NBTTagCompound.getInteger("foodTickTimer");
/*  77:101 */       this.foodSaturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
/*  78:102 */       this.foodExhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void writeNBT(NBTTagCompound par1NBTTagCompound)
/*  83:    */   {
/*  84:111 */     par1NBTTagCompound.setInteger("foodLevel", this.foodLevel);
/*  85:112 */     par1NBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
/*  86:113 */     par1NBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
/*  87:114 */     par1NBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getFoodLevel()
/*  91:    */   {
/*  92:122 */     return this.foodLevel;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getPrevFoodLevel()
/*  96:    */   {
/*  97:127 */     return this.prevFoodLevel;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean needFood()
/* 101:    */   {
/* 102:135 */     return this.foodLevel < 20;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void addExhaustion(float par1)
/* 106:    */   {
/* 107:143 */     this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0F);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public float getSaturationLevel()
/* 111:    */   {
/* 112:151 */     return this.foodSaturationLevel;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setFoodLevel(int par1)
/* 116:    */   {
/* 117:156 */     this.foodLevel = par1;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFoodSaturationLevel(float par1)
/* 121:    */   {
/* 122:161 */     this.foodSaturationLevel = par1;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.FoodStats
 * JD-Core Version:    0.7.0.1
 */