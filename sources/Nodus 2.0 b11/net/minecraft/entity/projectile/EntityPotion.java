/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.ItemPotion;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.potion.Potion;
/*  11:    */ import net.minecraft.potion.PotionEffect;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.MovingObjectPosition;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class EntityPotion
/*  17:    */   extends EntityThrowable
/*  18:    */ {
/*  19:    */   private ItemStack potionDamage;
/*  20:    */   private static final String __OBFID = "CL_00001727";
/*  21:    */   
/*  22:    */   public EntityPotion(World par1World)
/*  23:    */   {
/*  24: 25 */     super(par1World);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, int par3)
/*  28:    */   {
/*  29: 30 */     this(par1World, par2EntityLivingBase, new ItemStack(Items.potionitem, 1, par3));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, ItemStack par3ItemStack)
/*  33:    */   {
/*  34: 35 */     super(par1World, par2EntityLivingBase);
/*  35: 36 */     this.potionDamage = par3ItemStack;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public EntityPotion(World par1World, double par2, double par4, double par6, int par8)
/*  39:    */   {
/*  40: 41 */     this(par1World, par2, par4, par6, new ItemStack(Items.potionitem, 1, par8));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public EntityPotion(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*  44:    */   {
/*  45: 46 */     super(par1World, par2, par4, par6);
/*  46: 47 */     this.potionDamage = par8ItemStack;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected float getGravityVelocity()
/*  50:    */   {
/*  51: 55 */     return 0.05F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected float func_70182_d()
/*  55:    */   {
/*  56: 60 */     return 0.5F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected float func_70183_g()
/*  60:    */   {
/*  61: 65 */     return -20.0F;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setPotionDamage(int par1)
/*  65:    */   {
/*  66: 70 */     if (this.potionDamage == null) {
/*  67: 72 */       this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
/*  68:    */     }
/*  69: 75 */     this.potionDamage.setItemDamage(par1);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getPotionDamage()
/*  73:    */   {
/*  74: 83 */     if (this.potionDamage == null) {
/*  75: 85 */       this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
/*  76:    */     }
/*  77: 88 */     return this.potionDamage.getItemDamage();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/*  81:    */   {
/*  82: 96 */     if (!this.worldObj.isClient)
/*  83:    */     {
/*  84: 98 */       List var2 = Items.potionitem.getEffects(this.potionDamage);
/*  85:100 */       if ((var2 != null) && (!var2.isEmpty()))
/*  86:    */       {
/*  87:102 */         AxisAlignedBB var3 = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
/*  88:103 */         List var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
/*  89:105 */         if ((var4 != null) && (!var4.isEmpty()))
/*  90:    */         {
/*  91:107 */           Iterator var5 = var4.iterator();
/*  92:109 */           while (var5.hasNext())
/*  93:    */           {
/*  94:111 */             EntityLivingBase var6 = (EntityLivingBase)var5.next();
/*  95:112 */             double var7 = getDistanceSqToEntity(var6);
/*  96:114 */             if (var7 < 16.0D)
/*  97:    */             {
/*  98:116 */               double var9 = 1.0D - Math.sqrt(var7) / 4.0D;
/*  99:118 */               if (var6 == par1MovingObjectPosition.entityHit) {
/* 100:120 */                 var9 = 1.0D;
/* 101:    */               }
/* 102:123 */               Iterator var11 = var2.iterator();
/* 103:125 */               while (var11.hasNext())
/* 104:    */               {
/* 105:127 */                 PotionEffect var12 = (PotionEffect)var11.next();
/* 106:128 */                 int var13 = var12.getPotionID();
/* 107:130 */                 if (Potion.potionTypes[var13].isInstant())
/* 108:    */                 {
/* 109:132 */                   Potion.potionTypes[var13].affectEntity(getThrower(), var6, var12.getAmplifier(), var9);
/* 110:    */                 }
/* 111:    */                 else
/* 112:    */                 {
/* 113:136 */                   int var14 = (int)(var9 * var12.getDuration() + 0.5D);
/* 114:138 */                   if (var14 > 20) {
/* 115:140 */                     var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
/* 116:    */                   }
/* 117:    */                 }
/* 118:    */               }
/* 119:    */             }
/* 120:    */           }
/* 121:    */         }
/* 122:    */       }
/* 123:149 */       this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), getPotionDamage());
/* 124:150 */       setDead();
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 129:    */   {
/* 130:159 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 131:161 */     if (par1NBTTagCompound.func_150297_b("Potion", 10)) {
/* 132:163 */       this.potionDamage = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("Potion"));
/* 133:    */     } else {
/* 134:167 */       setPotionDamage(par1NBTTagCompound.getInteger("potionValue"));
/* 135:    */     }
/* 136:170 */     if (this.potionDamage == null) {
/* 137:172 */       setDead();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 142:    */   {
/* 143:181 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 144:183 */     if (this.potionDamage != null) {
/* 145:185 */       par1NBTTagCompound.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityPotion
 * JD-Core Version:    0.7.0.1
 */