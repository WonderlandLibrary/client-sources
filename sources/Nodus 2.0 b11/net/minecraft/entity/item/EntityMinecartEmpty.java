/*  1:   */ package net.minecraft.entity.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityMinecartEmpty
/*  7:   */   extends EntityMinecart
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001677";
/* 10:   */   
/* 11:   */   public EntityMinecartEmpty(World par1World)
/* 12:   */   {
/* 13:12 */     super(par1World);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public EntityMinecartEmpty(World par1World, double par2, double par4, double par6)
/* 17:   */   {
/* 18:17 */     super(par1World, par2, par4, par6);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 22:   */   {
/* 23:25 */     if ((this.riddenByEntity != null) && ((this.riddenByEntity instanceof EntityPlayer)) && (this.riddenByEntity != par1EntityPlayer)) {
/* 24:27 */       return true;
/* 25:   */     }
/* 26:29 */     if ((this.riddenByEntity != null) && (this.riddenByEntity != par1EntityPlayer)) {
/* 27:31 */       return false;
/* 28:   */     }
/* 29:35 */     if (!this.worldObj.isClient) {
/* 30:37 */       par1EntityPlayer.mountEntity(this);
/* 31:   */     }
/* 32:40 */     return true;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getMinecartType()
/* 36:   */   {
/* 37:46 */     return 0;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartEmpty
 * JD-Core Version:    0.7.0.1
 */