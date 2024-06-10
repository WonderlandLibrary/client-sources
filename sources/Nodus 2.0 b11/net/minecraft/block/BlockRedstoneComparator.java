/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.tileentity.TileEntity;
/*   9:    */ import net.minecraft.tileentity.TileEntityComparator;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class BlockRedstoneComparator
/*  15:    */   extends BlockRedstoneDiode
/*  16:    */   implements ITileEntityProvider
/*  17:    */ {
/*  18:    */   private static final String __OBFID = "CL_00000220";
/*  19:    */   
/*  20:    */   public BlockRedstoneComparator(boolean p_i45399_1_)
/*  21:    */   {
/*  22: 21 */     super(p_i45399_1_);
/*  23: 22 */     this.isBlockContainer = true;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  27:    */   {
/*  28: 27 */     return Items.comparator;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  32:    */   {
/*  33: 35 */     return Items.comparator;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int func_149901_b(int p_149901_1_)
/*  37:    */   {
/*  38: 40 */     return 2;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected BlockRedstoneDiode func_149906_e()
/*  42:    */   {
/*  43: 45 */     return Blocks.powered_comparator;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected BlockRedstoneDiode func_149898_i()
/*  47:    */   {
/*  48: 50 */     return Blocks.unpowered_comparator;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getRenderType()
/*  52:    */   {
/*  53: 58 */     return 37;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  57:    */   {
/*  58: 66 */     boolean var3 = (this.field_149914_a) || ((p_149691_2_ & 0x8) != 0);
/*  59: 67 */     return p_149691_1_ == 1 ? this.blockIcon : var3 ? Blocks.powered_comparator.blockIcon : p_149691_1_ == 0 ? Blocks.unlit_redstone_torch.getBlockTextureFromSide(p_149691_1_) : var3 ? Blocks.redstone_torch.getBlockTextureFromSide(p_149691_1_) : Blocks.double_stone_slab.getBlockTextureFromSide(1);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected boolean func_149905_c(int p_149905_1_)
/*  63:    */   {
/*  64: 72 */     return (this.field_149914_a) || ((p_149905_1_ & 0x8) != 0);
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected int func_149904_f(IBlockAccess p_149904_1_, int p_149904_2_, int p_149904_3_, int p_149904_4_, int p_149904_5_)
/*  68:    */   {
/*  69: 77 */     return func_149971_e(p_149904_1_, p_149904_2_, p_149904_3_, p_149904_4_).func_145996_a();
/*  70:    */   }
/*  71:    */   
/*  72:    */   private int func_149970_j(World p_149970_1_, int p_149970_2_, int p_149970_3_, int p_149970_4_, int p_149970_5_)
/*  73:    */   {
/*  74: 82 */     return !func_149969_d(p_149970_5_) ? func_149903_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_) : Math.max(func_149903_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_) - func_149902_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_), 0);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean func_149969_d(int p_149969_1_)
/*  78:    */   {
/*  79: 87 */     return (p_149969_1_ & 0x4) == 4;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected boolean func_149900_a(World p_149900_1_, int p_149900_2_, int p_149900_3_, int p_149900_4_, int p_149900_5_)
/*  83:    */   {
/*  84: 92 */     int var6 = func_149903_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_);
/*  85: 94 */     if (var6 >= 15) {
/*  86: 96 */       return true;
/*  87:    */     }
/*  88: 98 */     if (var6 == 0) {
/*  89:100 */       return false;
/*  90:    */     }
/*  91:104 */     int var7 = func_149902_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_);
/*  92:105 */     return var7 == 0;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected int func_149903_h(World p_149903_1_, int p_149903_2_, int p_149903_3_, int p_149903_4_, int p_149903_5_)
/*  96:    */   {
/*  97:111 */     int var6 = super.func_149903_h(p_149903_1_, p_149903_2_, p_149903_3_, p_149903_4_, p_149903_5_);
/*  98:112 */     int var7 = func_149895_l(p_149903_5_);
/*  99:113 */     int var8 = p_149903_2_ + net.minecraft.util.Direction.offsetX[var7];
/* 100:114 */     int var9 = p_149903_4_ + net.minecraft.util.Direction.offsetZ[var7];
/* 101:115 */     Block var10 = p_149903_1_.getBlock(var8, p_149903_3_, var9);
/* 102:117 */     if (var10.hasComparatorInputOverride())
/* 103:    */     {
/* 104:119 */       var6 = var10.getComparatorInputOverride(p_149903_1_, var8, p_149903_3_, var9, net.minecraft.util.Direction.rotateOpposite[var7]);
/* 105:    */     }
/* 106:121 */     else if ((var6 < 15) && (var10.isNormalCube()))
/* 107:    */     {
/* 108:123 */       var8 += net.minecraft.util.Direction.offsetX[var7];
/* 109:124 */       var9 += net.minecraft.util.Direction.offsetZ[var7];
/* 110:125 */       var10 = p_149903_1_.getBlock(var8, p_149903_3_, var9);
/* 111:127 */       if (var10.hasComparatorInputOverride()) {
/* 112:129 */         var6 = var10.getComparatorInputOverride(p_149903_1_, var8, p_149903_3_, var9, net.minecraft.util.Direction.rotateOpposite[var7]);
/* 113:    */       }
/* 114:    */     }
/* 115:133 */     return var6;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public TileEntityComparator func_149971_e(IBlockAccess p_149971_1_, int p_149971_2_, int p_149971_3_, int p_149971_4_)
/* 119:    */   {
/* 120:138 */     return (TileEntityComparator)p_149971_1_.getTileEntity(p_149971_2_, p_149971_3_, p_149971_4_);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 124:    */   {
/* 125:146 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/* 126:147 */     boolean var11 = this.field_149914_a | (var10 & 0x8) != 0;
/* 127:148 */     boolean var12 = !func_149969_d(var10);
/* 128:149 */     int var13 = var12 ? 4 : 0;
/* 129:150 */     var13 |= (var11 ? 8 : 0);
/* 130:151 */     p_149727_1_.playSoundEffect(p_149727_2_ + 0.5D, p_149727_3_ + 0.5D, p_149727_4_ + 0.5D, "random.click", 0.3F, var12 ? 0.55F : 0.5F);
/* 131:152 */     p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var13 | var10 & 0x3, 2);
/* 132:153 */     func_149972_c(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_1_.rand);
/* 133:154 */     return true;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected void func_149897_b(World p_149897_1_, int p_149897_2_, int p_149897_3_, int p_149897_4_, Block p_149897_5_)
/* 137:    */   {
/* 138:159 */     if (!p_149897_1_.func_147477_a(p_149897_2_, p_149897_3_, p_149897_4_, this))
/* 139:    */     {
/* 140:161 */       int var6 = p_149897_1_.getBlockMetadata(p_149897_2_, p_149897_3_, p_149897_4_);
/* 141:162 */       int var7 = func_149970_j(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6);
/* 142:163 */       int var8 = func_149971_e(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_).func_145996_a();
/* 143:165 */       if ((var7 != var8) || (func_149905_c(var6) != func_149900_a(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6))) {
/* 144:167 */         if (func_149912_i(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
/* 145:169 */           p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, func_149901_b(0), -1);
/* 146:    */         } else {
/* 147:173 */           p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, func_149901_b(0), 0);
/* 148:    */         }
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void func_149972_c(World p_149972_1_, int p_149972_2_, int p_149972_3_, int p_149972_4_, Random p_149972_5_)
/* 154:    */   {
/* 155:181 */     int var6 = p_149972_1_.getBlockMetadata(p_149972_2_, p_149972_3_, p_149972_4_);
/* 156:182 */     int var7 = func_149970_j(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_, var6);
/* 157:183 */     int var8 = func_149971_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_).func_145996_a();
/* 158:184 */     func_149971_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_).func_145995_a(var7);
/* 159:186 */     if ((var8 != var7) || (!func_149969_d(var6)))
/* 160:    */     {
/* 161:188 */       boolean var9 = func_149900_a(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_, var6);
/* 162:189 */       boolean var10 = (this.field_149914_a) || ((var6 & 0x8) != 0);
/* 163:191 */       if ((var10) && (!var9)) {
/* 164:193 */         p_149972_1_.setBlockMetadataWithNotify(p_149972_2_, p_149972_3_, p_149972_4_, var6 & 0xFFFFFFF7, 2);
/* 165:195 */       } else if ((!var10) && (var9)) {
/* 166:197 */         p_149972_1_.setBlockMetadataWithNotify(p_149972_2_, p_149972_3_, p_149972_4_, var6 | 0x8, 2);
/* 167:    */       }
/* 168:200 */       func_149911_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 173:    */   {
/* 174:209 */     if (this.field_149914_a)
/* 175:    */     {
/* 176:211 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/* 177:212 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, func_149898_i(), var6 | 0x8, 4);
/* 178:    */     }
/* 179:215 */     func_149972_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 183:    */   {
/* 184:220 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 185:221 */     p_149726_1_.setTileEntity(p_149726_2_, p_149726_3_, p_149726_4_, createNewTileEntity(p_149726_1_, 0));
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 189:    */   {
/* 190:226 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 191:227 */     p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 192:228 */     func_149911_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/* 196:    */   {
/* 197:233 */     super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
/* 198:234 */     TileEntity var7 = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
/* 199:235 */     return var7 != null ? var7.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 203:    */   {
/* 204:243 */     return new TileEntityComparator();
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneComparator
 * JD-Core Version:    0.7.0.1
 */