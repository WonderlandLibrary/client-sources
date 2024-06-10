/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.util.AABBPool;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class EntityLeashKnot
/*  16:    */   extends EntityHanging
/*  17:    */ {
/*  18:    */   private static final String __OBFID = "CL_00001548";
/*  19:    */   
/*  20:    */   public EntityLeashKnot(World par1World)
/*  21:    */   {
/*  22: 18 */     super(par1World);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public EntityLeashKnot(World par1World, int par2, int par3, int par4)
/*  26:    */   {
/*  27: 23 */     super(par1World, par2, par3, par4, 0);
/*  28: 24 */     setPosition(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void entityInit()
/*  32:    */   {
/*  33: 29 */     super.entityInit();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setDirection(int par1) {}
/*  37:    */   
/*  38:    */   public int getWidthPixels()
/*  39:    */   {
/*  40: 36 */     return 9;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getHeightPixels()
/*  44:    */   {
/*  45: 41 */     return 9;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isInRangeToRenderDist(double par1)
/*  49:    */   {
/*  50: 50 */     return par1 < 1024.0D;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void onBroken(Entity par1Entity) {}
/*  54:    */   
/*  55:    */   public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound)
/*  56:    */   {
/*  57: 65 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/*  61:    */   
/*  62:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/*  63:    */   
/*  64:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/*  65:    */   {
/*  66: 83 */     ItemStack var2 = par1EntityPlayer.getHeldItem();
/*  67: 84 */     boolean var3 = false;
/*  68: 90 */     if ((var2 != null) && (var2.getItem() == Items.lead) && (!this.worldObj.isClient))
/*  69:    */     {
/*  70: 92 */       double var4 = 7.0D;
/*  71: 93 */       List var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
/*  72: 95 */       if (var6 != null)
/*  73:    */       {
/*  74: 97 */         Iterator var7 = var6.iterator();
/*  75: 99 */         while (var7.hasNext())
/*  76:    */         {
/*  77:101 */           EntityLiving var8 = (EntityLiving)var7.next();
/*  78:103 */           if ((var8.getLeashed()) && (var8.getLeashedToEntity() == par1EntityPlayer))
/*  79:    */           {
/*  80:105 */             var8.setLeashedToEntity(this, true);
/*  81:106 */             var3 = true;
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:112 */     if ((!this.worldObj.isClient) && (!var3))
/*  87:    */     {
/*  88:114 */       setDead();
/*  89:116 */       if (par1EntityPlayer.capabilities.isCreativeMode)
/*  90:    */       {
/*  91:118 */         double var4 = 7.0D;
/*  92:119 */         List var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
/*  93:121 */         if (var6 != null)
/*  94:    */         {
/*  95:123 */           Iterator var7 = var6.iterator();
/*  96:125 */           while (var7.hasNext())
/*  97:    */           {
/*  98:127 */             EntityLiving var8 = (EntityLiving)var7.next();
/*  99:129 */             if ((var8.getLeashed()) && (var8.getLeashedToEntity() == this)) {
/* 100:131 */               var8.clearLeashed(true, false);
/* 101:    */             }
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:138 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean onValidSurface()
/* 110:    */   {
/* 111:146 */     return this.worldObj.getBlock(this.field_146063_b, this.field_146064_c, this.field_146062_d).getRenderType() == 11;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static EntityLeashKnot func_110129_a(World par0World, int par1, int par2, int par3)
/* 115:    */   {
/* 116:151 */     EntityLeashKnot var4 = new EntityLeashKnot(par0World, par1, par2, par3);
/* 117:152 */     var4.forceSpawn = true;
/* 118:153 */     par0World.spawnEntityInWorld(var4);
/* 119:154 */     return var4;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static EntityLeashKnot getKnotForBlock(World par0World, int par1, int par2, int par3)
/* 123:    */   {
/* 124:159 */     List var4 = par0World.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getAABBPool().getAABB(par1 - 1.0D, par2 - 1.0D, par3 - 1.0D, par1 + 1.0D, par2 + 1.0D, par3 + 1.0D));
/* 125:161 */     if (var4 != null)
/* 126:    */     {
/* 127:163 */       Iterator var5 = var4.iterator();
/* 128:165 */       while (var5.hasNext())
/* 129:    */       {
/* 130:167 */         EntityLeashKnot var6 = (EntityLeashKnot)var5.next();
/* 131:169 */         if ((var6.field_146063_b == par1) && (var6.field_146064_c == par2) && (var6.field_146062_d == par3)) {
/* 132:171 */           return var6;
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:176 */     return null;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityLeashKnot
 * JD-Core Version:    0.7.0.1
 */