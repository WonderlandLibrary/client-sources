/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.Block.SoundType;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemHoe
/* 12:   */   extends Item
/* 13:   */ {
/* 14:   */   protected Item.ToolMaterial theToolMaterial;
/* 15:   */   private static final String __OBFID = "CL_00000039";
/* 16:   */   
/* 17:   */   public ItemHoe(Item.ToolMaterial p_i45343_1_)
/* 18:   */   {
/* 19:17 */     this.theToolMaterial = p_i45343_1_;
/* 20:18 */     this.maxStackSize = 1;
/* 21:19 */     setMaxDamage(p_i45343_1_.getMaxUses());
/* 22:20 */     setCreativeTab(CreativeTabs.tabTools);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 26:   */   {
/* 27:29 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 28:31 */       return false;
/* 29:   */     }
/* 30:35 */     Block var11 = par3World.getBlock(par4, par5, par6);
/* 31:37 */     if ((par7 != 0) && (par3World.getBlock(par4, par5 + 1, par6).getMaterial() == Material.air) && ((var11 == Blocks.grass) || (var11 == Blocks.dirt)))
/* 32:   */     {
/* 33:39 */       Block var12 = Blocks.farmland;
/* 34:40 */       par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, var12.stepSound.func_150498_e(), (var12.stepSound.func_150497_c() + 1.0F) / 2.0F, var12.stepSound.func_150494_d() * 0.8F);
/* 35:42 */       if (par3World.isClient) {
/* 36:44 */         return true;
/* 37:   */       }
/* 38:48 */       par3World.setBlock(par4, par5, par6, var12);
/* 39:49 */       par1ItemStack.damageItem(1, par2EntityPlayer);
/* 40:50 */       return true;
/* 41:   */     }
/* 42:55 */     return false;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean isFull3D()
/* 46:   */   {
/* 47:65 */     return true;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getMaterialName()
/* 51:   */   {
/* 52:74 */     return this.theToolMaterial.toString();
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemHoe
 * JD-Core Version:    0.7.0.1
 */