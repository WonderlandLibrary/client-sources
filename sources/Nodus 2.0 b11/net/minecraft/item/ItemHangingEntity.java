/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.EntityHanging;
/*  5:   */ import net.minecraft.entity.item.EntityItemFrame;
/*  6:   */ import net.minecraft.entity.item.EntityPainting;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemHangingEntity
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private final Class hangingEntityClass;
/* 14:   */   private static final String __OBFID = "CL_00000038";
/* 15:   */   
/* 16:   */   public ItemHangingEntity(Class p_i45342_1_)
/* 17:   */   {
/* 18:18 */     this.hangingEntityClass = p_i45342_1_;
/* 19:19 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 23:   */   {
/* 24:28 */     if (par7 == 0) {
/* 25:30 */       return false;
/* 26:   */     }
/* 27:32 */     if (par7 == 1) {
/* 28:34 */       return false;
/* 29:   */     }
/* 30:38 */     int var11 = net.minecraft.util.Direction.facingToDirection[par7];
/* 31:39 */     EntityHanging var12 = createHangingEntity(par3World, par4, par5, par6, var11);
/* 32:41 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 33:43 */       return false;
/* 34:   */     }
/* 35:47 */     if ((var12 != null) && (var12.onValidSurface()))
/* 36:   */     {
/* 37:49 */       if (!par3World.isClient) {
/* 38:51 */         par3World.spawnEntityInWorld(var12);
/* 39:   */       }
/* 40:54 */       par1ItemStack.stackSize -= 1;
/* 41:   */     }
/* 42:57 */     return true;
/* 43:   */   }
/* 44:   */   
/* 45:   */   private EntityHanging createHangingEntity(World par1World, int par2, int par3, int par4, int par5)
/* 46:   */   {
/* 47:67 */     return this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(par1World, par2, par3, par4, par5) : this.hangingEntityClass == EntityPainting.class ? new EntityPainting(par1World, par2, par3, par4, par5) : null;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemHangingEntity
 * JD-Core Version:    0.7.0.1
 */