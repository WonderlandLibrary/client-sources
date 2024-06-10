/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class EntityDamageSource
/*  9:   */   extends DamageSource
/* 10:   */ {
/* 11:   */   protected Entity damageSourceEntity;
/* 12:   */   private static final String __OBFID = "CL_00001522";
/* 13:   */   
/* 14:   */   public EntityDamageSource(String par1Str, Entity par2Entity)
/* 15:   */   {
/* 16:15 */     super(par1Str);
/* 17:16 */     this.damageSourceEntity = par2Entity;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Entity getEntity()
/* 21:   */   {
/* 22:21 */     return this.damageSourceEntity;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
/* 26:   */   {
/* 27:26 */     ItemStack var2 = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
/* 28:27 */     String var3 = "death.attack." + this.damageType;
/* 29:28 */     String var4 = var3 + ".item";
/* 30:29 */     return (var2 != null) && (var2.hasDisplayName()) && (StatCollector.canTranslate(var4)) ? new ChatComponentTranslation(var4, new Object[] { p_151519_1_.func_145748_c_(), this.damageSourceEntity.func_145748_c_(), var2.func_151000_E() }) : new ChatComponentTranslation(var3, new Object[] { p_151519_1_.func_145748_c_(), this.damageSourceEntity.func_145748_c_() });
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean isDifficultyScaled()
/* 34:   */   {
/* 35:37 */     return (this.damageSourceEntity != null) && ((this.damageSourceEntity instanceof EntityLivingBase)) && (!(this.damageSourceEntity instanceof EntityPlayer));
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EntityDamageSource
 * JD-Core Version:    0.7.0.1
 */