/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLiving;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.inventory.IInventory;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public abstract interface IEntitySelector
/* 11:   */ {
/* 12:12 */   public static final IEntitySelector selectAnything = new IEntitySelector()
/* 13:   */   {
/* 14:   */     private static final String __OBFID = "CL_00001541";
/* 15:   */     
/* 16:   */     public boolean isEntityApplicable(Entity par1Entity)
/* 17:   */     {
/* 18:17 */       return par1Entity.isEntityAlive();
/* 19:   */     }
/* 20:   */   };
/* 21:20 */   public static final IEntitySelector selectInventories = new IEntitySelector()
/* 22:   */   {
/* 23:   */     private static final String __OBFID = "CL_00001542";
/* 24:   */     
/* 25:   */     public boolean isEntityApplicable(Entity par1Entity)
/* 26:   */     {
/* 27:25 */       return ((par1Entity instanceof IInventory)) && (par1Entity.isEntityAlive());
/* 28:   */     }
/* 29:   */   };
/* 30:   */   
/* 31:   */   public abstract boolean isEntityApplicable(Entity paramEntity);
/* 32:   */   
/* 33:   */   public static class ArmoredMob
/* 34:   */     implements IEntitySelector
/* 35:   */   {
/* 36:   */     private final ItemStack field_96567_c;
/* 37:   */     private static final String __OBFID = "CL_00001543";
/* 38:   */     
/* 39:   */     public ArmoredMob(ItemStack par1ItemStack)
/* 40:   */     {
/* 41:41 */       this.field_96567_c = par1ItemStack;
/* 42:   */     }
/* 43:   */     
/* 44:   */     public boolean isEntityApplicable(Entity par1Entity)
/* 45:   */     {
/* 46:46 */       if (!par1Entity.isEntityAlive()) {
/* 47:48 */         return false;
/* 48:   */       }
/* 49:50 */       if (!(par1Entity instanceof EntityLivingBase)) {
/* 50:52 */         return false;
/* 51:   */       }
/* 52:56 */       EntityLivingBase var2 = (EntityLivingBase)par1Entity;
/* 53:57 */       return (var2 instanceof EntityLiving) ? ((EntityLiving)var2).canPickUpLoot() : var2.getEquipmentInSlot(EntityLiving.getArmorPosition(this.field_96567_c)) != null ? false : var2 instanceof EntityPlayer;
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.IEntitySelector
 * JD-Core Version:    0.7.0.1
 */