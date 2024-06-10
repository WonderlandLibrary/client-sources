/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public abstract class BlockSlab
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   protected final boolean field_150004_a;
/*  17:    */   private static final String __OBFID = "CL_00000253";
/*  18:    */   
/*  19:    */   public BlockSlab(boolean p_i45410_1_, Material p_i45410_2_)
/*  20:    */   {
/*  21: 21 */     super(p_i45410_2_);
/*  22: 22 */     this.field_150004_a = p_i45410_1_;
/*  23: 24 */     if (p_i45410_1_) {
/*  24: 26 */       this.opaque = true;
/*  25:    */     } else {
/*  26: 30 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  27:    */     }
/*  28: 33 */     setLightOpacity(255);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  32:    */   {
/*  33: 38 */     if (this.field_150004_a)
/*  34:    */     {
/*  35: 40 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  36:    */     }
/*  37:    */     else
/*  38:    */     {
/*  39: 44 */       boolean var5 = (p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x8) != 0;
/*  40: 46 */       if (var5) {
/*  41: 48 */         setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  42:    */       } else {
/*  43: 52 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setBlockBoundsForItemRender()
/*  49:    */   {
/*  50: 62 */     if (this.field_150004_a) {
/*  51: 64 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  52:    */     } else {
/*  53: 68 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  58:    */   {
/*  59: 74 */     setBlockBoundsBasedOnState(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
/*  60: 75 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isOpaqueCube()
/*  64:    */   {
/*  65: 80 */     return this.field_150004_a;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  69:    */   {
/*  70: 85 */     return (p_149660_5_ != 0) && ((p_149660_5_ == 1) || (p_149660_7_ <= 0.5D)) ? p_149660_9_ : this.field_150004_a ? p_149660_9_ : p_149660_9_ | 0x8;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int quantityDropped(Random p_149745_1_)
/*  74:    */   {
/*  75: 93 */     return this.field_150004_a ? 2 : 1;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int damageDropped(int p_149692_1_)
/*  79:    */   {
/*  80:101 */     return p_149692_1_ & 0x7;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean renderAsNormalBlock()
/*  84:    */   {
/*  85:106 */     return this.field_150004_a;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  89:    */   {
/*  90:111 */     if (this.field_150004_a) {
/*  91:113 */       return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/*  92:    */     }
/*  93:115 */     if ((p_149646_5_ != 1) && (p_149646_5_ != 0) && (!super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_))) {
/*  94:117 */       return false;
/*  95:    */     }
/*  96:121 */     int var6 = p_149646_2_ + net.minecraft.util.Facing.offsetsXForSide[net.minecraft.util.Facing.oppositeSide[p_149646_5_]];
/*  97:122 */     int var7 = p_149646_3_ + net.minecraft.util.Facing.offsetsYForSide[net.minecraft.util.Facing.oppositeSide[p_149646_5_]];
/*  98:123 */     int var8 = p_149646_4_ + net.minecraft.util.Facing.offsetsZForSide[net.minecraft.util.Facing.oppositeSide[p_149646_5_]];
/*  99:124 */     boolean var9 = (p_149646_1_.getBlockMetadata(var6, var7, var8) & 0x8) != 0;
/* 100:125 */     return p_149646_5_ == 0;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static boolean func_150003_a(Block p_150003_0_)
/* 104:    */   {
/* 105:131 */     return (p_150003_0_ == Blocks.stone_slab) || (p_150003_0_ == Blocks.wooden_slab);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public abstract String func_150002_b(int paramInt);
/* 109:    */   
/* 110:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/* 111:    */   {
/* 112:141 */     return super.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_) & 0x7;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 116:    */   {
/* 117:149 */     return this == Blocks.double_wooden_slab ? Item.getItemFromBlock(Blocks.wooden_slab) : this == Blocks.double_stone_slab ? Item.getItemFromBlock(Blocks.stone_slab) : func_150003_a(this) ? Item.getItemFromBlock(this) : Item.getItemFromBlock(Blocks.stone_slab);
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSlab
 * JD-Core Version:    0.7.0.1
 */