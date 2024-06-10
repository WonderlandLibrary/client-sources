/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class ItemSeeds
/*  9:   */   extends Item
/* 10:   */ {
/* 11:   */   private Block field_150925_a;
/* 12:   */   private Block soilBlockID;
/* 13:   */   private static final String __OBFID = "CL_00000061";
/* 14:   */   
/* 15:   */   public ItemSeeds(Block p_i45352_1_, Block p_i45352_2_)
/* 16:   */   {
/* 17:18 */     this.field_150925_a = p_i45352_1_;
/* 18:19 */     this.soilBlockID = p_i45352_2_;
/* 19:20 */     setCreativeTab(CreativeTabs.tabMaterials);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 23:   */   {
/* 24:29 */     if (par7 != 1) {
/* 25:31 */       return false;
/* 26:   */     }
/* 27:33 */     if ((par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) && (par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)))
/* 28:   */     {
/* 29:35 */       if ((par3World.getBlock(par4, par5, par6) == this.soilBlockID) && (par3World.isAirBlock(par4, par5 + 1, par6)))
/* 30:   */       {
/* 31:37 */         par3World.setBlock(par4, par5 + 1, par6, this.field_150925_a);
/* 32:38 */         par1ItemStack.stackSize -= 1;
/* 33:39 */         return true;
/* 34:   */       }
/* 35:43 */       return false;
/* 36:   */     }
/* 37:48 */     return false;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSeeds
 * JD-Core Version:    0.7.0.1
 */