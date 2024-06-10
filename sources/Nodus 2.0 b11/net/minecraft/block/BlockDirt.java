/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class BlockDirt
/*  15:    */   extends Block
/*  16:    */ {
/*  17: 16 */   public static final String[] field_150009_a = { "default", "default", "podzol" };
/*  18:    */   private IIcon field_150008_b;
/*  19:    */   private IIcon field_150010_M;
/*  20:    */   private static final String __OBFID = "CL_00000228";
/*  21:    */   
/*  22:    */   protected BlockDirt()
/*  23:    */   {
/*  24: 23 */     super(Material.ground);
/*  25: 24 */     setCreativeTab(CreativeTabs.tabBlock);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  29:    */   {
/*  30: 32 */     if (p_149691_2_ == 2)
/*  31:    */     {
/*  32: 34 */       if (p_149691_1_ == 1) {
/*  33: 36 */         return this.field_150008_b;
/*  34:    */       }
/*  35: 39 */       if (p_149691_1_ != 0) {
/*  36: 41 */         return this.field_150010_M;
/*  37:    */       }
/*  38:    */     }
/*  39: 45 */     return this.blockIcon;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
/*  43:    */   {
/*  44: 50 */     int var6 = p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_);
/*  45: 52 */     if (var6 == 2)
/*  46:    */     {
/*  47: 54 */       if (p_149673_5_ == 1) {
/*  48: 56 */         return this.field_150008_b;
/*  49:    */       }
/*  50: 59 */       if (p_149673_5_ != 0)
/*  51:    */       {
/*  52: 61 */         Material var7 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
/*  53: 63 */         if ((var7 == Material.field_151597_y) || (var7 == Material.craftedSnow)) {
/*  54: 65 */           return Blocks.grass.getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
/*  55:    */         }
/*  56: 68 */         Block var8 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_);
/*  57: 70 */         if ((var8 != Blocks.dirt) && (var8 != Blocks.grass)) {
/*  58: 72 */           return this.field_150010_M;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62: 77 */     return this.blockIcon;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int damageDropped(int p_149692_1_)
/*  66:    */   {
/*  67: 85 */     return 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/*  71:    */   {
/*  72: 94 */     if (p_149644_1_ == 1) {
/*  73: 96 */       p_149644_1_ = 0;
/*  74:    */     }
/*  75: 99 */     return super.createStackedBlock(p_149644_1_);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  79:    */   {
/*  80:104 */     p_149666_3_.add(new ItemStack(this, 1, 0));
/*  81:105 */     p_149666_3_.add(new ItemStack(this, 1, 2));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  85:    */   {
/*  86:110 */     super.registerBlockIcons(p_149651_1_);
/*  87:111 */     this.field_150008_b = p_149651_1_.registerIcon(getTextureName() + "_" + "podzol_top");
/*  88:112 */     this.field_150010_M = p_149651_1_.registerIcon(getTextureName() + "_" + "podzol_side");
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/*  92:    */   {
/*  93:120 */     int var5 = p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
/*  94:122 */     if (var5 == 1) {
/*  95:124 */       var5 = 0;
/*  96:    */     }
/*  97:127 */     return var5;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDirt
 * JD-Core Version:    0.7.0.1
 */