/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.IIcon;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import net.minecraft.world.IBlockAccess;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  20:    */ 
/*  21:    */ public class BlockDoublePlant
/*  22:    */   extends BlockBush
/*  23:    */   implements IGrowable
/*  24:    */ {
/*  25: 22 */   public static final String[] field_149892_a = { "sunflower", "syringa", "grass", "fern", "rose", "paeonia" };
/*  26:    */   private IIcon[] field_149893_M;
/*  27:    */   private IIcon[] field_149894_N;
/*  28:    */   public IIcon[] field_149891_b;
/*  29:    */   private static final String __OBFID = "CL_00000231";
/*  30:    */   
/*  31:    */   public BlockDoublePlant()
/*  32:    */   {
/*  33: 30 */     super(Material.plants);
/*  34: 31 */     setHardness(0.0F);
/*  35: 32 */     setStepSound(soundTypeGrass);
/*  36: 33 */     setBlockName("doublePlant");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getRenderType()
/*  40:    */   {
/*  41: 41 */     return 40;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  45:    */   {
/*  46: 46 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int func_149885_e(IBlockAccess p_149885_1_, int p_149885_2_, int p_149885_3_, int p_149885_4_)
/*  50:    */   {
/*  51: 51 */     int var5 = p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_, p_149885_4_);
/*  52: 52 */     return !func_149887_c(var5) ? var5 & 0x7 : p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_ - 1, p_149885_4_) & 0x7;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  56:    */   {
/*  57: 57 */     return (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) && (p_149742_1_.isAirBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_));
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void func_149855_e(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
/*  61:    */   {
/*  62: 62 */     if (!canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
/*  63:    */     {
/*  64: 64 */       int var5 = p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_);
/*  65: 66 */       if (!func_149887_c(var5))
/*  66:    */       {
/*  67: 68 */         dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, var5, 0);
/*  68: 70 */         if (p_149855_1_.getBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_) == this) {
/*  69: 72 */           p_149855_1_.setBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_, Blocks.air, 0, 2);
/*  70:    */         }
/*  71:    */       }
/*  72: 76 */       p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, Blocks.air, 0, 2);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  77:    */   {
/*  78: 85 */     int var5 = p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_);
/*  79: 86 */     return p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == this;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  83:    */   {
/*  84: 91 */     if (func_149887_c(p_149650_1_)) {
/*  85: 93 */       return null;
/*  86:    */     }
/*  87: 97 */     int var4 = func_149890_d(p_149650_1_);
/*  88: 98 */     return (var4 != 3) && (var4 != 2) ? Item.getItemFromBlock(this) : null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int damageDropped(int p_149692_1_)
/*  92:    */   {
/*  93:107 */     return func_149887_c(p_149692_1_) ? 0 : p_149692_1_ & 0x7;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static boolean func_149887_c(int p_149887_0_)
/*  97:    */   {
/*  98:112 */     return (p_149887_0_ & 0x8) != 0;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static int func_149890_d(int p_149890_0_)
/* 102:    */   {
/* 103:117 */     return p_149890_0_ & 0x7;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 107:    */   {
/* 108:125 */     return func_149887_c(p_149691_2_) ? this.field_149893_M[0] : this.field_149893_M[(p_149691_2_ & 0x7)];
/* 109:    */   }
/* 110:    */   
/* 111:    */   public IIcon func_149888_a(boolean p_149888_1_, int p_149888_2_)
/* 112:    */   {
/* 113:130 */     return p_149888_1_ ? this.field_149894_N[p_149888_2_] : this.field_149893_M[p_149888_2_];
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 117:    */   {
/* 118:139 */     int var5 = func_149885_e(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
/* 119:140 */     return (var5 != 2) && (var5 != 3) ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void func_149889_c(World p_149889_1_, int p_149889_2_, int p_149889_3_, int p_149889_4_, int p_149889_5_, int p_149889_6_)
/* 123:    */   {
/* 124:145 */     p_149889_1_.setBlock(p_149889_2_, p_149889_3_, p_149889_4_, this, p_149889_5_, p_149889_6_);
/* 125:146 */     p_149889_1_.setBlock(p_149889_2_, p_149889_3_ + 1, p_149889_4_, this, 8, p_149889_6_);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 129:    */   {
/* 130:154 */     int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
/* 131:155 */     p_149689_1_.setBlock(p_149689_2_, p_149689_3_ + 1, p_149689_4_, this, 0x8 | var7, 2);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/* 135:    */   {
/* 136:160 */     if ((p_149636_1_.isClient) || (p_149636_2_.getCurrentEquippedItem() == null) || (p_149636_2_.getCurrentEquippedItem().getItem() != Items.shears) || (func_149887_c(p_149636_6_)) || (!func_149886_b(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, p_149636_2_))) {
/* 137:162 */       super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 142:    */   {
/* 143:171 */     if (func_149887_c(p_149681_5_))
/* 144:    */     {
/* 145:173 */       if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this) {
/* 146:175 */         if (!p_149681_6_.capabilities.isCreativeMode)
/* 147:    */         {
/* 148:177 */           int var7 = p_149681_1_.getBlockMetadata(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
/* 149:178 */           int var8 = func_149890_d(var7);
/* 150:180 */           if ((var8 != 3) && (var8 != 2))
/* 151:    */           {
/* 152:182 */             p_149681_1_.func_147480_a(p_149681_2_, p_149681_3_ - 1, p_149681_4_, true);
/* 153:    */           }
/* 154:    */           else
/* 155:    */           {
/* 156:186 */             if ((!p_149681_1_.isClient) && (p_149681_6_.getCurrentEquippedItem() != null) && (p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears)) {
/* 157:188 */               func_149886_b(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, var7, p_149681_6_);
/* 158:    */             }
/* 159:191 */             p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
/* 160:    */           }
/* 161:    */         }
/* 162:    */         else
/* 163:    */         {
/* 164:196 */           p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:200 */     else if ((p_149681_6_.capabilities.isCreativeMode) && (p_149681_1_.getBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_) == this)) {
/* 169:202 */       p_149681_1_.setBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_, Blocks.air, 0, 2);
/* 170:    */     }
/* 171:205 */     super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
/* 172:    */   }
/* 173:    */   
/* 174:    */   private boolean func_149886_b(World p_149886_1_, int p_149886_2_, int p_149886_3_, int p_149886_4_, int p_149886_5_, EntityPlayer p_149886_6_)
/* 175:    */   {
/* 176:210 */     int var7 = func_149890_d(p_149886_5_);
/* 177:212 */     if ((var7 != 3) && (var7 != 2)) {
/* 178:214 */       return false;
/* 179:    */     }
/* 180:218 */     p_149886_6_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/* 181:219 */     byte var8 = 1;
/* 182:221 */     if (var7 == 3) {
/* 183:223 */       var8 = 2;
/* 184:    */     }
/* 185:226 */     dropBlockAsItem_do(p_149886_1_, p_149886_2_, p_149886_3_, p_149886_4_, new ItemStack(Blocks.tallgrass, 2, var8));
/* 186:227 */     return true;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 190:    */   {
/* 191:233 */     this.field_149893_M = new IIcon[field_149892_a.length];
/* 192:234 */     this.field_149894_N = new IIcon[field_149892_a.length];
/* 193:236 */     for (int var2 = 0; var2 < this.field_149893_M.length; var2++)
/* 194:    */     {
/* 195:238 */       this.field_149893_M[var2] = p_149651_1_.registerIcon("double_plant_" + field_149892_a[var2] + "_bottom");
/* 196:239 */       this.field_149894_N[var2] = p_149651_1_.registerIcon("double_plant_" + field_149892_a[var2] + "_top");
/* 197:    */     }
/* 198:242 */     this.field_149891_b = new IIcon[2];
/* 199:243 */     this.field_149891_b[0] = p_149651_1_.registerIcon("double_plant_sunflower_front");
/* 200:244 */     this.field_149891_b[1] = p_149651_1_.registerIcon("double_plant_sunflower_back");
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 204:    */   {
/* 205:249 */     for (int var4 = 0; var4 < this.field_149893_M.length; var4++) {
/* 206:251 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/* 211:    */   {
/* 212:260 */     int var5 = p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
/* 213:261 */     return func_149887_c(var5) ? func_149890_d(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_ - 1, p_149643_4_)) : func_149890_d(var5);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 217:    */   {
/* 218:266 */     int var6 = func_149885_e(p_149851_1_, p_149851_2_, p_149851_3_, p_149851_4_);
/* 219:267 */     return (var6 != 2) && (var6 != 3);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 223:    */   {
/* 224:272 */     return true;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 228:    */   {
/* 229:277 */     int var6 = func_149885_e(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
/* 230:278 */     dropBlockAsItem_do(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, new ItemStack(this, 1, var6));
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDoublePlant
 * JD-Core Version:    0.7.0.1
 */