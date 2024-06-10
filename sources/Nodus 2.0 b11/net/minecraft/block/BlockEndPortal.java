/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.MapColor;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.tileentity.TileEntity;
/*  11:    */ import net.minecraft.tileentity.TileEntityEndPortal;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ 
/*  17:    */ public class BlockEndPortal
/*  18:    */   extends BlockContainer
/*  19:    */ {
/*  20:    */   public static boolean field_149948_a;
/*  21:    */   private static final String __OBFID = "CL_00000236";
/*  22:    */   
/*  23:    */   protected BlockEndPortal(Material p_i45404_1_)
/*  24:    */   {
/*  25: 23 */     super(p_i45404_1_);
/*  26: 24 */     setLightLevel(1.0F);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  30:    */   {
/*  31: 32 */     return new TileEntityEndPortal();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  35:    */   {
/*  36: 37 */     float var5 = 0.0625F;
/*  37: 38 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  41:    */   {
/*  42: 43 */     return p_149646_5_ != 0 ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {}
/*  46:    */   
/*  47:    */   public boolean isOpaqueCube()
/*  48:    */   {
/*  49: 50 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean renderAsNormalBlock()
/*  53:    */   {
/*  54: 55 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int quantityDropped(Random p_149745_1_)
/*  58:    */   {
/*  59: 63 */     return 0;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/*  63:    */   {
/*  64: 68 */     if ((p_149670_5_.ridingEntity == null) && (p_149670_5_.riddenByEntity == null) && (!p_149670_1_.isClient)) {
/*  65: 70 */       p_149670_5_.travelToDimension(1);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  70:    */   {
/*  71: 79 */     double var6 = p_149734_2_ + p_149734_5_.nextFloat();
/*  72: 80 */     double var8 = p_149734_3_ + 0.8F;
/*  73: 81 */     double var10 = p_149734_4_ + p_149734_5_.nextFloat();
/*  74: 82 */     double var12 = 0.0D;
/*  75: 83 */     double var14 = 0.0D;
/*  76: 84 */     double var16 = 0.0D;
/*  77: 85 */     p_149734_1_.spawnParticle("smoke", var6, var8, var10, var12, var14, var16);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getRenderType()
/*  81:    */   {
/*  82: 93 */     return -1;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  86:    */   {
/*  87: 98 */     if (!field_149948_a) {
/*  88:100 */       if (p_149726_1_.provider.dimensionId != 0) {
/*  89:102 */         p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  95:    */   {
/*  96:112 */     return Item.getItemById(0);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 100:    */   {
/* 101:117 */     this.blockIcon = p_149651_1_.registerIcon("portal");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public MapColor getMapColor(int p_149728_1_)
/* 105:    */   {
/* 106:122 */     return MapColor.field_151654_J;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockEndPortal
 * JD-Core Version:    0.7.0.1
 */