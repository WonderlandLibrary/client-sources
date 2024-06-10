/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.tileentity.TileEntity;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class BlockSign
/*  16:    */   extends BlockContainer
/*  17:    */ {
/*  18:    */   private Class field_149968_a;
/*  19:    */   private boolean field_149967_b;
/*  20:    */   private static final String __OBFID = "CL_00000306";
/*  21:    */   
/*  22:    */   protected BlockSign(Class p_i45426_1_, boolean p_i45426_2_)
/*  23:    */   {
/*  24: 23 */     super(Material.wood);
/*  25: 24 */     this.field_149967_b = p_i45426_2_;
/*  26: 25 */     this.field_149968_a = p_i45426_1_;
/*  27: 26 */     float var3 = 0.25F;
/*  28: 27 */     float var4 = 1.0F;
/*  29: 28 */     setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  33:    */   {
/*  34: 36 */     return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  38:    */   {
/*  39: 45 */     return null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  43:    */   {
/*  44: 53 */     setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  45: 54 */     return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  49:    */   {
/*  50: 59 */     if (!this.field_149967_b)
/*  51:    */     {
/*  52: 61 */       int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/*  53: 62 */       float var6 = 0.28125F;
/*  54: 63 */       float var7 = 0.78125F;
/*  55: 64 */       float var8 = 0.0F;
/*  56: 65 */       float var9 = 1.0F;
/*  57: 66 */       float var10 = 0.125F;
/*  58: 67 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  59: 69 */       if (var5 == 2) {
/*  60: 71 */         setBlockBounds(var8, var6, 1.0F - var10, var9, var7, 1.0F);
/*  61:    */       }
/*  62: 74 */       if (var5 == 3) {
/*  63: 76 */         setBlockBounds(var8, var6, 0.0F, var9, var7, var10);
/*  64:    */       }
/*  65: 79 */       if (var5 == 4) {
/*  66: 81 */         setBlockBounds(1.0F - var10, var6, var8, 1.0F, var7, var9);
/*  67:    */       }
/*  68: 84 */       if (var5 == 5) {
/*  69: 86 */         setBlockBounds(0.0F, var6, var8, var10, var7, var9);
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getRenderType()
/*  75:    */   {
/*  76: 96 */     return -1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean renderAsNormalBlock()
/*  80:    */   {
/*  81:101 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  85:    */   {
/*  86:106 */     return true;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isOpaqueCube()
/*  90:    */   {
/*  91:111 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:121 */       return (TileEntity)this.field_149968_a.newInstance();
/*  99:    */     }
/* 100:    */     catch (Exception var4)
/* 101:    */     {
/* 102:125 */       throw new RuntimeException(var4);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 107:    */   {
/* 108:131 */     return Items.sign;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 112:    */   {
/* 113:136 */     boolean var6 = false;
/* 114:138 */     if (this.field_149967_b)
/* 115:    */     {
/* 116:140 */       if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_).getMaterial().isSolid()) {
/* 117:142 */         var6 = true;
/* 118:    */       }
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:147 */       int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 123:148 */       var6 = true;
/* 124:150 */       if ((var7 == 2) && (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).getMaterial().isSolid())) {
/* 125:152 */         var6 = false;
/* 126:    */       }
/* 127:155 */       if ((var7 == 3) && (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).getMaterial().isSolid())) {
/* 128:157 */         var6 = false;
/* 129:    */       }
/* 130:160 */       if ((var7 == 4) && (p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).getMaterial().isSolid())) {
/* 131:162 */         var6 = false;
/* 132:    */       }
/* 133:165 */       if ((var7 == 5) && (p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).getMaterial().isSolid())) {
/* 134:167 */         var6 = false;
/* 135:    */       }
/* 136:    */     }
/* 137:171 */     if (var6)
/* 138:    */     {
/* 139:173 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 140:174 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 141:    */     }
/* 142:177 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 146:    */   {
/* 147:185 */     return Items.sign;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 151:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSign
 * JD-Core Version:    0.7.0.1
 */