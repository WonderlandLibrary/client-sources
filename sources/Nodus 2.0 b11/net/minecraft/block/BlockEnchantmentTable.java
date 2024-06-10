/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockEnchantmentTable
/*  17:    */   extends BlockContainer
/*  18:    */ {
/*  19:    */   private IIcon field_149950_a;
/*  20:    */   private IIcon field_149949_b;
/*  21:    */   private static final String __OBFID = "CL_00000235";
/*  22:    */   
/*  23:    */   protected BlockEnchantmentTable()
/*  24:    */   {
/*  25: 24 */     super(Material.rock);
/*  26: 25 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*  27: 26 */     setLightOpacity(0);
/*  28: 27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean renderAsNormalBlock()
/*  32:    */   {
/*  33: 32 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  37:    */   {
/*  38: 40 */     super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
/*  39: 42 */     for (int var6 = p_149734_2_ - 2; var6 <= p_149734_2_ + 2; var6++) {
/*  40: 44 */       for (int var7 = p_149734_4_ - 2; var7 <= p_149734_4_ + 2; var7++)
/*  41:    */       {
/*  42: 46 */         if ((var6 > p_149734_2_ - 2) && (var6 < p_149734_2_ + 2) && (var7 == p_149734_4_ - 1)) {
/*  43: 48 */           var7 = p_149734_4_ + 2;
/*  44:    */         }
/*  45: 51 */         if (p_149734_5_.nextInt(16) == 0) {
/*  46: 53 */           for (int var8 = p_149734_3_; var8 <= p_149734_3_ + 1; var8++) {
/*  47: 55 */             if (p_149734_1_.getBlock(var6, var8, var7) == Blocks.bookshelf)
/*  48:    */             {
/*  49: 57 */               if (!p_149734_1_.isAirBlock((var6 - p_149734_2_) / 2 + p_149734_2_, var8, (var7 - p_149734_4_) / 2 + p_149734_4_)) {
/*  50:    */                 break;
/*  51:    */               }
/*  52: 62 */               p_149734_1_.spawnParticle("enchantmenttable", p_149734_2_ + 0.5D, p_149734_3_ + 2.0D, p_149734_4_ + 0.5D, var6 - p_149734_2_ + p_149734_5_.nextFloat() - 0.5D, var8 - p_149734_3_ - p_149734_5_.nextFloat() - 1.0F, var7 - p_149734_4_ + p_149734_5_.nextFloat() - 0.5D);
/*  53:    */             }
/*  54:    */           }
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isOpaqueCube()
/*  61:    */   {
/*  62: 72 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  66:    */   {
/*  67: 80 */     return p_149691_1_ == 1 ? this.field_149950_a : p_149691_1_ == 0 ? this.field_149949_b : this.blockIcon;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  71:    */   {
/*  72: 88 */     return new TileEntityEnchantmentTable();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  76:    */   {
/*  77: 96 */     if (p_149727_1_.isClient) {
/*  78: 98 */       return true;
/*  79:    */     }
/*  80:102 */     TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  81:103 */     p_149727_5_.displayGUIEnchantment(p_149727_2_, p_149727_3_, p_149727_4_, var10.func_145921_b() ? var10.func_145919_a() : null);
/*  82:104 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  86:    */   {
/*  87:113 */     super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
/*  88:115 */     if (p_149689_6_.hasDisplayName()) {
/*  89:117 */       ((TileEntityEnchantmentTable)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145920_a(p_149689_6_.getDisplayName());
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  94:    */   {
/*  95:123 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_" + "side");
/*  96:124 */     this.field_149950_a = p_149651_1_.registerIcon(getTextureName() + "_" + "top");
/*  97:125 */     this.field_149949_b = p_149651_1_.registerIcon(getTextureName() + "_" + "bottom");
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockEnchantmentTable
 * JD-Core Version:    0.7.0.1
 */