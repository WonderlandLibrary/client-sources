/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockMycelium
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private IIcon field_150200_a;
/*  17:    */   private IIcon field_150199_b;
/*  18:    */   private static final String __OBFID = "CL_00000273";
/*  19:    */   
/*  20:    */   protected BlockMycelium()
/*  21:    */   {
/*  22: 21 */     super(Material.grass);
/*  23: 22 */     setTickRandomly(true);
/*  24: 23 */     setCreativeTab(CreativeTabs.tabBlock);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  28:    */   {
/*  29: 31 */     return p_149691_1_ == 0 ? Blocks.dirt.getBlockTextureFromSide(p_149691_1_) : p_149691_1_ == 1 ? this.field_150200_a : this.blockIcon;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
/*  33:    */   {
/*  34: 36 */     if (p_149673_5_ == 1) {
/*  35: 38 */       return this.field_150200_a;
/*  36:    */     }
/*  37: 40 */     if (p_149673_5_ == 0) {
/*  38: 42 */       return Blocks.dirt.getBlockTextureFromSide(p_149673_5_);
/*  39:    */     }
/*  40: 46 */     Material var6 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
/*  41: 47 */     return (var6 != Material.field_151597_y) && (var6 != Material.craftedSnow) ? this.blockIcon : this.field_150199_b;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  45:    */   {
/*  46: 53 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  47: 54 */     this.field_150200_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  48: 55 */     this.field_150199_b = p_149651_1_.registerIcon("grass_side_snowed");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  52:    */   {
/*  53: 63 */     if (!p_149674_1_.isClient) {
/*  54: 65 */       if ((p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) < 4) && (p_149674_1_.getBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_).getLightOpacity() > 2)) {
/*  55: 67 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.dirt);
/*  56: 69 */       } else if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) {
/*  57: 71 */         for (int var6 = 0; var6 < 4; var6++)
/*  58:    */         {
/*  59: 73 */           int var7 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
/*  60: 74 */           int var8 = p_149674_3_ + p_149674_5_.nextInt(5) - 3;
/*  61: 75 */           int var9 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
/*  62: 76 */           Block var10 = p_149674_1_.getBlock(var7, var8 + 1, var9);
/*  63: 78 */           if ((p_149674_1_.getBlock(var7, var8, var9) == Blocks.dirt) && (p_149674_1_.getBlockMetadata(var7, var8, var9) == 0) && (p_149674_1_.getBlockLightValue(var7, var8 + 1, var9) >= 4) && (var10.getLightOpacity() <= 2)) {
/*  64: 80 */             p_149674_1_.setBlock(var7, var8, var9, this);
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  72:    */   {
/*  73: 92 */     super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
/*  74: 94 */     if (p_149734_5_.nextInt(10) == 0) {
/*  75: 96 */       p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1F, p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  80:    */   {
/*  81:102 */     return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockMycelium
 * JD-Core Version:    0.7.0.1
 */