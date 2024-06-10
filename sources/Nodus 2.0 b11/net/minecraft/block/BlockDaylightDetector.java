/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.tileentity.TileEntity;
/*   8:    */ import net.minecraft.tileentity.TileEntityDaylightDetector;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.world.EnumSkyBlock;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraft.world.WorldProvider;
/*  15:    */ 
/*  16:    */ public class BlockDaylightDetector
/*  17:    */   extends BlockContainer
/*  18:    */ {
/*  19: 17 */   private IIcon[] field_149958_a = new IIcon[2];
/*  20:    */   private static final String __OBFID = "CL_00000223";
/*  21:    */   
/*  22:    */   public BlockDaylightDetector()
/*  23:    */   {
/*  24: 22 */     super(Material.wood);
/*  25: 23 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*  26: 24 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  30:    */   {
/*  31: 29 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/*  35:    */   {
/*  36: 34 */     return p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {}
/*  40:    */   
/*  41:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {}
/*  42:    */   
/*  43:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {}
/*  44:    */   
/*  45:    */   public void func_149957_e(World p_149957_1_, int p_149957_2_, int p_149957_3_, int p_149957_4_)
/*  46:    */   {
/*  47: 48 */     if (!p_149957_1_.provider.hasNoSky)
/*  48:    */     {
/*  49: 50 */       int var5 = p_149957_1_.getBlockMetadata(p_149957_2_, p_149957_3_, p_149957_4_);
/*  50: 51 */       int var6 = p_149957_1_.getSavedLightValue(EnumSkyBlock.Sky, p_149957_2_, p_149957_3_, p_149957_4_) - p_149957_1_.skylightSubtracted;
/*  51: 52 */       float var7 = p_149957_1_.getCelestialAngleRadians(1.0F);
/*  52: 54 */       if (var7 < 3.141593F) {
/*  53: 56 */         var7 += (0.0F - var7) * 0.2F;
/*  54:    */       } else {
/*  55: 60 */         var7 += (6.283186F - var7) * 0.2F;
/*  56:    */       }
/*  57: 63 */       var6 = Math.round(var6 * MathHelper.cos(var7));
/*  58: 65 */       if (var6 < 0) {
/*  59: 67 */         var6 = 0;
/*  60:    */       }
/*  61: 70 */       if (var6 > 15) {
/*  62: 72 */         var6 = 15;
/*  63:    */       }
/*  64: 75 */       if (var5 != var6) {
/*  65: 77 */         p_149957_1_.setBlockMetadataWithNotify(p_149957_2_, p_149957_3_, p_149957_4_, var6, 3);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean renderAsNormalBlock()
/*  71:    */   {
/*  72: 84 */     return false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isOpaqueCube()
/*  76:    */   {
/*  77: 89 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canProvidePower()
/*  81:    */   {
/*  82: 97 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  86:    */   {
/*  87:105 */     return new TileEntityDaylightDetector();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  91:    */   {
/*  92:113 */     return p_149691_1_ == 1 ? this.field_149958_a[0] : this.field_149958_a[1];
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  96:    */   {
/*  97:118 */     this.field_149958_a[0] = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  98:119 */     this.field_149958_a[1] = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDaylightDetector
 * JD-Core Version:    0.7.0.1
 */