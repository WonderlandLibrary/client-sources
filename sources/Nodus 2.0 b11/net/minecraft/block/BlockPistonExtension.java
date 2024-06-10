/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockPistonExtension
/*  18:    */   extends Block
/*  19:    */ {
/*  20:    */   private IIcon field_150088_a;
/*  21:    */   private static final String __OBFID = "CL_00000367";
/*  22:    */   
/*  23:    */   public BlockPistonExtension()
/*  24:    */   {
/*  25: 24 */     super(Material.piston);
/*  26: 25 */     setStepSound(soundTypePiston);
/*  27: 26 */     setHardness(0.5F);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void func_150086_a(IIcon p_150086_1_)
/*  31:    */   {
/*  32: 31 */     this.field_150088_a = p_150086_1_;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void func_150087_e()
/*  36:    */   {
/*  37: 36 */     this.field_150088_a = null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/*  41:    */   {
/*  42: 44 */     if (p_149681_6_.capabilities.isCreativeMode)
/*  43:    */     {
/*  44: 46 */       int var7 = func_150085_b(p_149681_5_);
/*  45: 47 */       Block var8 = p_149681_1_.getBlock(p_149681_2_ - net.minecraft.util.Facing.offsetsXForSide[var7], p_149681_3_ - net.minecraft.util.Facing.offsetsYForSide[var7], p_149681_4_ - net.minecraft.util.Facing.offsetsZForSide[var7]);
/*  46: 49 */       if ((var8 == Blocks.piston) || (var8 == Blocks.sticky_piston)) {
/*  47: 51 */         p_149681_1_.setBlockToAir(p_149681_2_ - net.minecraft.util.Facing.offsetsXForSide[var7], p_149681_3_ - net.minecraft.util.Facing.offsetsYForSide[var7], p_149681_4_ - net.minecraft.util.Facing.offsetsZForSide[var7]);
/*  48:    */       }
/*  49:    */     }
/*  50: 55 */     super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  54:    */   {
/*  55: 60 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/*  56: 61 */     int var7 = net.minecraft.util.Facing.oppositeSide[func_150085_b(p_149749_6_)];
/*  57: 62 */     p_149749_2_ += net.minecraft.util.Facing.offsetsXForSide[var7];
/*  58: 63 */     p_149749_3_ += net.minecraft.util.Facing.offsetsYForSide[var7];
/*  59: 64 */     p_149749_4_ += net.minecraft.util.Facing.offsetsZForSide[var7];
/*  60: 65 */     Block var8 = p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_);
/*  61: 67 */     if ((var8 == Blocks.piston) || (var8 == Blocks.sticky_piston))
/*  62:    */     {
/*  63: 69 */       p_149749_6_ = p_149749_1_.getBlockMetadata(p_149749_2_, p_149749_3_, p_149749_4_);
/*  64: 71 */       if (BlockPistonBase.func_150075_c(p_149749_6_))
/*  65:    */       {
/*  66: 73 */         var8.dropBlockAsItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_, 0);
/*  67: 74 */         p_149749_1_.setBlockToAir(p_149749_2_, p_149749_3_, p_149749_4_);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  73:    */   {
/*  74: 84 */     int var3 = func_150085_b(p_149691_2_);
/*  75: 85 */     return (var3 < 6) && (p_149691_1_ == net.minecraft.util.Facing.oppositeSide[var3]) ? BlockPistonBase.func_150074_e("piston_top_normal") : p_149691_1_ == var3 ? BlockPistonBase.func_150074_e("piston_top_normal") : (p_149691_2_ & 0x8) != 0 ? BlockPistonBase.func_150074_e("piston_top_sticky") : this.field_150088_a != null ? this.field_150088_a : BlockPistonBase.func_150074_e("piston_side");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/*  79:    */   
/*  80:    */   public int getRenderType()
/*  81:    */   {
/*  82: 95 */     return 17;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isOpaqueCube()
/*  86:    */   {
/*  87:100 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean renderAsNormalBlock()
/*  91:    */   {
/*  92:105 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  96:    */   {
/*  97:110 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/* 101:    */   {
/* 102:118 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int quantityDropped(Random p_149745_1_)
/* 106:    */   {
/* 107:126 */     return 0;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/* 111:    */   {
/* 112:131 */     int var8 = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
/* 113:132 */     float var9 = 0.25F;
/* 114:133 */     float var10 = 0.375F;
/* 115:134 */     float var11 = 0.625F;
/* 116:135 */     float var12 = 0.25F;
/* 117:136 */     float var13 = 0.75F;
/* 118:138 */     switch (func_150085_b(var8))
/* 119:    */     {
/* 120:    */     case 0: 
/* 121:141 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/* 122:142 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 123:143 */       setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/* 124:144 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 125:145 */       break;
/* 126:    */     case 1: 
/* 127:148 */       setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 128:149 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 129:150 */       setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/* 130:151 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 131:152 */       break;
/* 132:    */     case 2: 
/* 133:155 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/* 134:156 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 135:157 */       setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/* 136:158 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 137:159 */       break;
/* 138:    */     case 3: 
/* 139:162 */       setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/* 140:163 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 141:164 */       setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/* 142:165 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 143:166 */       break;
/* 144:    */     case 4: 
/* 145:169 */       setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/* 146:170 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 147:171 */       setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/* 148:172 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 149:173 */       break;
/* 150:    */     case 5: 
/* 151:176 */       setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 152:177 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 153:178 */       setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/* 154:179 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 155:    */     }
/* 156:182 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 160:    */   {
/* 161:187 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/* 162:188 */     float var6 = 0.25F;
/* 163:190 */     switch (func_150085_b(var5))
/* 164:    */     {
/* 165:    */     case 0: 
/* 166:193 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/* 167:194 */       break;
/* 168:    */     case 1: 
/* 169:197 */       setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 170:198 */       break;
/* 171:    */     case 2: 
/* 172:201 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/* 173:202 */       break;
/* 174:    */     case 3: 
/* 175:205 */       setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/* 176:206 */       break;
/* 177:    */     case 4: 
/* 178:209 */       setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/* 179:210 */       break;
/* 180:    */     case 5: 
/* 181:213 */       setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 186:    */   {
/* 187:219 */     int var6 = func_150085_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
/* 188:220 */     Block var7 = p_149695_1_.getBlock(p_149695_2_ - net.minecraft.util.Facing.offsetsXForSide[var6], p_149695_3_ - net.minecraft.util.Facing.offsetsYForSide[var6], p_149695_4_ - net.minecraft.util.Facing.offsetsZForSide[var6]);
/* 189:222 */     if ((var7 != Blocks.piston) && (var7 != Blocks.sticky_piston)) {
/* 190:224 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 191:    */     } else {
/* 192:228 */       var7.onNeighborBlockChange(p_149695_1_, p_149695_2_ - net.minecraft.util.Facing.offsetsXForSide[var6], p_149695_3_ - net.minecraft.util.Facing.offsetsYForSide[var6], p_149695_4_ - net.minecraft.util.Facing.offsetsZForSide[var6], p_149695_5_);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static int func_150085_b(int p_150085_0_)
/* 197:    */   {
/* 198:234 */     return p_150085_0_ & 0x7;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 202:    */   {
/* 203:242 */     int var5 = p_149694_1_.getBlockMetadata(p_149694_2_, p_149694_3_, p_149694_4_);
/* 204:243 */     return (var5 & 0x8) != 0 ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPistonExtension
 * JD-Core Version:    0.7.0.1
 */