/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class ItemDoor
/*  12:    */   extends Item
/*  13:    */ {
/*  14:    */   private Material doorMaterial;
/*  15:    */   private static final String __OBFID = "CL_00000020";
/*  16:    */   
/*  17:    */   public ItemDoor(Material p_i45334_1_)
/*  18:    */   {
/*  19: 18 */     this.doorMaterial = p_i45334_1_;
/*  20: 19 */     this.maxStackSize = 1;
/*  21: 20 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  25:    */   {
/*  26: 29 */     if (par7 != 1) {
/*  27: 31 */       return false;
/*  28:    */     }
/*  29: 35 */     par5++;
/*  30:    */     Block var11;
/*  31:    */     Block var11;
/*  32: 38 */     if (this.doorMaterial == Material.wood) {
/*  33: 40 */       var11 = Blocks.wooden_door;
/*  34:    */     } else {
/*  35: 44 */       var11 = Blocks.iron_door;
/*  36:    */     }
/*  37: 47 */     if ((par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) && (par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)))
/*  38:    */     {
/*  39: 49 */       if (!var11.canPlaceBlockAt(par3World, par4, par5, par6)) {
/*  40: 51 */         return false;
/*  41:    */       }
/*  42: 55 */       int var12 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3;
/*  43: 56 */       func_150924_a(par3World, par4, par5, par6, var12, var11);
/*  44: 57 */       par1ItemStack.stackSize -= 1;
/*  45: 58 */       return true;
/*  46:    */     }
/*  47: 63 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void func_150924_a(World p_150924_0_, int p_150924_1_, int p_150924_2_, int p_150924_3_, int p_150924_4_, Block p_150924_5_)
/*  51:    */   {
/*  52: 70 */     byte var6 = 0;
/*  53: 71 */     byte var7 = 0;
/*  54: 73 */     if (p_150924_4_ == 0) {
/*  55: 75 */       var7 = 1;
/*  56:    */     }
/*  57: 78 */     if (p_150924_4_ == 1) {
/*  58: 80 */       var6 = -1;
/*  59:    */     }
/*  60: 83 */     if (p_150924_4_ == 2) {
/*  61: 85 */       var7 = -1;
/*  62:    */     }
/*  63: 88 */     if (p_150924_4_ == 3) {
/*  64: 90 */       var6 = 1;
/*  65:    */     }
/*  66: 93 */     int var8 = (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7).isNormalCube() ? 1 : 0) + (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7).isNormalCube() ? 1 : 0);
/*  67: 94 */     int var9 = (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7).isNormalCube() ? 1 : 0) + (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7).isNormalCube() ? 1 : 0);
/*  68: 95 */     boolean var10 = (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7) == p_150924_5_) || (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7) == p_150924_5_);
/*  69: 96 */     boolean var11 = (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7) == p_150924_5_) || (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7) == p_150924_5_);
/*  70: 97 */     boolean var12 = false;
/*  71: 99 */     if ((var10) && (!var11)) {
/*  72:101 */       var12 = true;
/*  73:103 */     } else if (var9 > var8) {
/*  74:105 */       var12 = true;
/*  75:    */     }
/*  76:108 */     p_150924_0_.setBlock(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_, p_150924_4_, 2);
/*  77:109 */     p_150924_0_.setBlock(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_, 0x8 | (var12 ? 1 : 0), 2);
/*  78:110 */     p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_);
/*  79:111 */     p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_);
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemDoor
 * JD-Core Version:    0.7.0.1
 */