/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.material.Material;
/*   4:    */ import net.minecraft.creativetab.CreativeTabs;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MovingObjectPosition;
/*   9:    */ import net.minecraft.util.Vec3;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockTrapDoor
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private static final String __OBFID = "CL_00000327";
/*  17:    */   
/*  18:    */   protected BlockTrapDoor(Material p_i45434_1_)
/*  19:    */   {
/*  20: 19 */     super(p_i45434_1_);
/*  21: 20 */     float var2 = 0.5F;
/*  22: 21 */     float var3 = 1.0F;
/*  23: 22 */     setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
/*  24: 23 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isOpaqueCube()
/*  28:    */   {
/*  29: 28 */     return false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean renderAsNormalBlock()
/*  33:    */   {
/*  34: 33 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  38:    */   {
/*  39: 38 */     return !func_150118_d(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getRenderType()
/*  43:    */   {
/*  44: 46 */     return 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  48:    */   {
/*  49: 54 */     setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  50: 55 */     return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  54:    */   {
/*  55: 64 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  56: 65 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  60:    */   {
/*  61: 70 */     func_150117_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setBlockBoundsForItemRender()
/*  65:    */   {
/*  66: 78 */     float var1 = 0.1875F;
/*  67: 79 */     setBlockBounds(0.0F, 0.5F - var1 / 2.0F, 0.0F, 1.0F, 0.5F + var1 / 2.0F, 1.0F);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void func_150117_b(int p_150117_1_)
/*  71:    */   {
/*  72: 84 */     float var2 = 0.1875F;
/*  73: 86 */     if ((p_150117_1_ & 0x8) != 0) {
/*  74: 88 */       setBlockBounds(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
/*  75:    */     } else {
/*  76: 92 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
/*  77:    */     }
/*  78: 95 */     if (func_150118_d(p_150117_1_))
/*  79:    */     {
/*  80: 97 */       if ((p_150117_1_ & 0x3) == 0) {
/*  81: 99 */         setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
/*  82:    */       }
/*  83:102 */       if ((p_150117_1_ & 0x3) == 1) {
/*  84:104 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
/*  85:    */       }
/*  86:107 */       if ((p_150117_1_ & 0x3) == 2) {
/*  87:109 */         setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  88:    */       }
/*  89:112 */       if ((p_150117_1_ & 0x3) == 3) {
/*  90:114 */         setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {}
/*  96:    */   
/*  97:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  98:    */   {
/*  99:129 */     if (this.blockMaterial == Material.iron) {
/* 100:131 */       return true;
/* 101:    */     }
/* 102:135 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/* 103:136 */     p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 ^ 0x4, 2);
/* 104:137 */     p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
/* 105:138 */     return true;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void func_150120_a(World p_150120_1_, int p_150120_2_, int p_150120_3_, int p_150120_4_, boolean p_150120_5_)
/* 109:    */   {
/* 110:144 */     int var6 = p_150120_1_.getBlockMetadata(p_150120_2_, p_150120_3_, p_150120_4_);
/* 111:145 */     boolean var7 = (var6 & 0x4) > 0;
/* 112:147 */     if (var7 != p_150120_5_)
/* 113:    */     {
/* 114:149 */       p_150120_1_.setBlockMetadataWithNotify(p_150120_2_, p_150120_3_, p_150120_4_, var6 ^ 0x4, 2);
/* 115:150 */       p_150120_1_.playAuxSFXAtEntity(null, 1003, p_150120_2_, p_150120_3_, p_150120_4_, 0);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 120:    */   {
/* 121:156 */     if (!p_149695_1_.isClient)
/* 122:    */     {
/* 123:158 */       int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 124:159 */       int var7 = p_149695_2_;
/* 125:160 */       int var8 = p_149695_4_;
/* 126:162 */       if ((var6 & 0x3) == 0) {
/* 127:164 */         var8 = p_149695_4_ + 1;
/* 128:    */       }
/* 129:167 */       if ((var6 & 0x3) == 1) {
/* 130:169 */         var8--;
/* 131:    */       }
/* 132:172 */       if ((var6 & 0x3) == 2) {
/* 133:174 */         var7 = p_149695_2_ + 1;
/* 134:    */       }
/* 135:177 */       if ((var6 & 0x3) == 3) {
/* 136:179 */         var7--;
/* 137:    */       }
/* 138:182 */       if (!func_150119_a(p_149695_1_.getBlock(var7, p_149695_3_, var8)))
/* 139:    */       {
/* 140:184 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 141:185 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/* 142:    */       }
/* 143:188 */       boolean var9 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
/* 144:190 */       if ((var9) || (p_149695_5_.canProvidePower())) {
/* 145:192 */         func_150120_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var9);
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/* 151:    */   {
/* 152:199 */     setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
/* 153:200 */     return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 157:    */   {
/* 158:205 */     int var10 = 0;
/* 159:207 */     if (p_149660_5_ == 2) {
/* 160:209 */       var10 = 0;
/* 161:    */     }
/* 162:212 */     if (p_149660_5_ == 3) {
/* 163:214 */       var10 = 1;
/* 164:    */     }
/* 165:217 */     if (p_149660_5_ == 4) {
/* 166:219 */       var10 = 2;
/* 167:    */     }
/* 168:222 */     if (p_149660_5_ == 5) {
/* 169:224 */       var10 = 3;
/* 170:    */     }
/* 171:227 */     if ((p_149660_5_ != 1) && (p_149660_5_ != 0) && (p_149660_7_ > 0.5F)) {
/* 172:229 */       var10 |= 0x8;
/* 173:    */     }
/* 174:232 */     return var10;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/* 178:    */   {
/* 179:240 */     if (p_149707_5_ == 0) {
/* 180:242 */       return false;
/* 181:    */     }
/* 182:244 */     if (p_149707_5_ == 1) {
/* 183:246 */       return false;
/* 184:    */     }
/* 185:250 */     if (p_149707_5_ == 2) {
/* 186:252 */       p_149707_4_++;
/* 187:    */     }
/* 188:255 */     if (p_149707_5_ == 3) {
/* 189:257 */       p_149707_4_--;
/* 190:    */     }
/* 191:260 */     if (p_149707_5_ == 4) {
/* 192:262 */       p_149707_2_++;
/* 193:    */     }
/* 194:265 */     if (p_149707_5_ == 5) {
/* 195:267 */       p_149707_2_--;
/* 196:    */     }
/* 197:270 */     return func_150119_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_));
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static boolean func_150118_d(int p_150118_0_)
/* 201:    */   {
/* 202:276 */     return (p_150118_0_ & 0x4) != 0;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static boolean func_150119_a(Block p_150119_0_)
/* 206:    */   {
/* 207:281 */     return ((p_150119_0_.blockMaterial.isOpaque()) && (p_150119_0_.renderAsNormalBlock())) || (p_150119_0_ == Blocks.glowstone) || ((p_150119_0_ instanceof BlockSlab)) || ((p_150119_0_ instanceof BlockStairs));
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTrapDoor
 * JD-Core Version:    0.7.0.1
 */