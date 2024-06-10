/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.ItemLead;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class BlockFence
/*  16:    */   extends Block
/*  17:    */ {
/*  18:    */   private final String field_149827_a;
/*  19:    */   private static final String __OBFID = "CL_00000242";
/*  20:    */   
/*  21:    */   public BlockFence(String p_i45406_1_, Material p_i45406_2_)
/*  22:    */   {
/*  23: 22 */     super(p_i45406_2_);
/*  24: 23 */     this.field_149827_a = p_i45406_1_;
/*  25: 24 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  29:    */   {
/*  30: 29 */     boolean var8 = func_149826_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_ - 1);
/*  31: 30 */     boolean var9 = func_149826_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_ + 1);
/*  32: 31 */     boolean var10 = func_149826_e(p_149743_1_, p_149743_2_ - 1, p_149743_3_, p_149743_4_);
/*  33: 32 */     boolean var11 = func_149826_e(p_149743_1_, p_149743_2_ + 1, p_149743_3_, p_149743_4_);
/*  34: 33 */     float var12 = 0.375F;
/*  35: 34 */     float var13 = 0.625F;
/*  36: 35 */     float var14 = 0.375F;
/*  37: 36 */     float var15 = 0.625F;
/*  38: 38 */     if (var8) {
/*  39: 40 */       var14 = 0.0F;
/*  40:    */     }
/*  41: 43 */     if (var9) {
/*  42: 45 */       var15 = 1.0F;
/*  43:    */     }
/*  44: 48 */     if ((var8) || (var9))
/*  45:    */     {
/*  46: 50 */       setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
/*  47: 51 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  48:    */     }
/*  49: 54 */     var14 = 0.375F;
/*  50: 55 */     var15 = 0.625F;
/*  51: 57 */     if (var10) {
/*  52: 59 */       var12 = 0.0F;
/*  53:    */     }
/*  54: 62 */     if (var11) {
/*  55: 64 */       var13 = 1.0F;
/*  56:    */     }
/*  57: 67 */     if ((var10) || (var11) || ((!var8) && (!var9)))
/*  58:    */     {
/*  59: 69 */       setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
/*  60: 70 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  61:    */     }
/*  62: 73 */     if (var8) {
/*  63: 75 */       var14 = 0.0F;
/*  64:    */     }
/*  65: 78 */     if (var9) {
/*  66: 80 */       var15 = 1.0F;
/*  67:    */     }
/*  68: 83 */     setBlockBounds(var12, 0.0F, var14, var13, 1.0F, var15);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  72:    */   {
/*  73: 88 */     boolean var5 = func_149826_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ - 1);
/*  74: 89 */     boolean var6 = func_149826_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ + 1);
/*  75: 90 */     boolean var7 = func_149826_e(p_149719_1_, p_149719_2_ - 1, p_149719_3_, p_149719_4_);
/*  76: 91 */     boolean var8 = func_149826_e(p_149719_1_, p_149719_2_ + 1, p_149719_3_, p_149719_4_);
/*  77: 92 */     float var9 = 0.375F;
/*  78: 93 */     float var10 = 0.625F;
/*  79: 94 */     float var11 = 0.375F;
/*  80: 95 */     float var12 = 0.625F;
/*  81: 97 */     if (var5) {
/*  82: 99 */       var11 = 0.0F;
/*  83:    */     }
/*  84:102 */     if (var6) {
/*  85:104 */       var12 = 1.0F;
/*  86:    */     }
/*  87:107 */     if (var7) {
/*  88:109 */       var9 = 0.0F;
/*  89:    */     }
/*  90:112 */     if (var8) {
/*  91:114 */       var10 = 1.0F;
/*  92:    */     }
/*  93:117 */     setBlockBounds(var9, 0.0F, var11, var10, 1.0F, var12);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isOpaqueCube()
/*  97:    */   {
/*  98:122 */     return false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean renderAsNormalBlock()
/* 102:    */   {
/* 103:127 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/* 107:    */   {
/* 108:132 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getRenderType()
/* 112:    */   {
/* 113:140 */     return 11;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean func_149826_e(IBlockAccess p_149826_1_, int p_149826_2_, int p_149826_3_, int p_149826_4_)
/* 117:    */   {
/* 118:145 */     Block var5 = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
/* 119:146 */     return var5.blockMaterial != Material.field_151572_C;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static boolean func_149825_a(Block p_149825_0_)
/* 123:    */   {
/* 124:151 */     return (p_149825_0_ == Blocks.fence) || (p_149825_0_ == Blocks.nether_brick_fence);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 128:    */   {
/* 129:156 */     return true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 133:    */   {
/* 134:161 */     this.blockIcon = p_149651_1_.registerIcon(this.field_149827_a);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 138:    */   {
/* 139:169 */     return p_149727_1_.isClient ? true : ItemLead.func_150909_a(p_149727_5_, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFence
 * JD-Core Version:    0.7.0.1
 */