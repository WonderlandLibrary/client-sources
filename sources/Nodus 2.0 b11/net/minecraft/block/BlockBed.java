/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer.EnumStatus;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.util.ChatComponentTranslation;
/*  15:    */ import net.minecraft.util.ChunkCoordinates;
/*  16:    */ import net.minecraft.util.IIcon;
/*  17:    */ import net.minecraft.world.IBlockAccess;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import net.minecraft.world.WorldProvider;
/*  20:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  21:    */ 
/*  22:    */ public class BlockBed
/*  23:    */   extends BlockDirectional
/*  24:    */ {
/*  25: 22 */   public static final int[][] field_149981_a = { { 0, 1 }, { -1 }, { 0, -1 }, { 1 } };
/*  26:    */   private IIcon[] field_149980_b;
/*  27:    */   private IIcon[] field_149982_M;
/*  28:    */   private IIcon[] field_149983_N;
/*  29:    */   private static final String __OBFID = "CL_00000198";
/*  30:    */   
/*  31:    */   public BlockBed()
/*  32:    */   {
/*  33: 30 */     super(Material.cloth);
/*  34: 31 */     func_149978_e();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  38:    */   {
/*  39: 39 */     if (p_149727_1_.isClient) {
/*  40: 41 */       return true;
/*  41:    */     }
/*  42: 45 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/*  43: 47 */     if (!func_149975_b(var10))
/*  44:    */     {
/*  45: 49 */       int var11 = func_149895_l(var10);
/*  46: 50 */       p_149727_2_ += field_149981_a[var11][0];
/*  47: 51 */       p_149727_4_ += field_149981_a[var11][1];
/*  48: 53 */       if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) != this) {
/*  49: 55 */         return true;
/*  50:    */       }
/*  51: 58 */       var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/*  52:    */     }
/*  53: 61 */     if ((p_149727_1_.provider.canRespawnHere()) && (p_149727_1_.getBiomeGenForCoords(p_149727_2_, p_149727_4_) != BiomeGenBase.hell))
/*  54:    */     {
/*  55: 63 */       if (func_149976_c(var10))
/*  56:    */       {
/*  57: 65 */         EntityPlayer var20 = null;
/*  58: 66 */         Iterator var12 = p_149727_1_.playerEntities.iterator();
/*  59: 68 */         while (var12.hasNext())
/*  60:    */         {
/*  61: 70 */           EntityPlayer var21 = (EntityPlayer)var12.next();
/*  62: 72 */           if (var21.isPlayerSleeping())
/*  63:    */           {
/*  64: 74 */             ChunkCoordinates var14 = var21.playerLocation;
/*  65: 76 */             if ((var14.posX == p_149727_2_) && (var14.posY == p_149727_3_) && (var14.posZ == p_149727_4_)) {
/*  66: 78 */               var20 = var21;
/*  67:    */             }
/*  68:    */           }
/*  69:    */         }
/*  70: 83 */         if (var20 != null)
/*  71:    */         {
/*  72: 85 */           p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
/*  73: 86 */           return true;
/*  74:    */         }
/*  75: 89 */         func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, false);
/*  76:    */       }
/*  77: 92 */       EntityPlayer.EnumStatus var19 = p_149727_5_.sleepInBedAt(p_149727_2_, p_149727_3_, p_149727_4_);
/*  78: 94 */       if (var19 == EntityPlayer.EnumStatus.OK)
/*  79:    */       {
/*  80: 96 */         func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, true);
/*  81: 97 */         return true;
/*  82:    */       }
/*  83:101 */       if (var19 == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
/*  84:103 */         p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
/*  85:105 */       } else if (var19 == EntityPlayer.EnumStatus.NOT_SAFE) {
/*  86:107 */         p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
/*  87:    */       }
/*  88:110 */       return true;
/*  89:    */     }
/*  90:115 */     double var18 = p_149727_2_ + 0.5D;
/*  91:116 */     double var13 = p_149727_3_ + 0.5D;
/*  92:117 */     double var15 = p_149727_4_ + 0.5D;
/*  93:118 */     p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
/*  94:119 */     int var17 = func_149895_l(var10);
/*  95:120 */     p_149727_2_ += field_149981_a[var17][0];
/*  96:121 */     p_149727_4_ += field_149981_a[var17][1];
/*  97:123 */     if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) == this)
/*  98:    */     {
/*  99:125 */       p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
/* 100:126 */       var18 = (var18 + p_149727_2_ + 0.5D) / 2.0D;
/* 101:127 */       var13 = (var13 + p_149727_3_ + 0.5D) / 2.0D;
/* 102:128 */       var15 = (var15 + p_149727_4_ + 0.5D) / 2.0D;
/* 103:    */     }
/* 104:131 */     p_149727_1_.newExplosion(null, p_149727_2_ + 0.5F, p_149727_3_ + 0.5F, p_149727_4_ + 0.5F, 5.0F, true, true);
/* 105:132 */     return true;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 109:    */   {
/* 110:142 */     if (p_149691_1_ == 0) {
/* 111:144 */       return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
/* 112:    */     }
/* 113:148 */     int var3 = func_149895_l(p_149691_2_);
/* 114:149 */     int var4 = net.minecraft.util.Direction.bedDirection[var3][p_149691_1_];
/* 115:150 */     int var5 = func_149975_b(p_149691_2_) ? 1 : 0;
/* 116:151 */     return ((var5 != 1) || (var4 != 2)) && ((var5 != 0) || (var4 != 3)) ? this.field_149982_M[var5] : (var4 != 5) && (var4 != 4) ? this.field_149983_N[var5] : this.field_149980_b[var5];
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 120:    */   {
/* 121:157 */     this.field_149983_N = new IIcon[] { p_149651_1_.registerIcon(getTextureName() + "_feet_top"), p_149651_1_.registerIcon(getTextureName() + "_head_top") };
/* 122:158 */     this.field_149980_b = new IIcon[] { p_149651_1_.registerIcon(getTextureName() + "_feet_end"), p_149651_1_.registerIcon(getTextureName() + "_head_end") };
/* 123:159 */     this.field_149982_M = new IIcon[] { p_149651_1_.registerIcon(getTextureName() + "_feet_side"), p_149651_1_.registerIcon(getTextureName() + "_head_side") };
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getRenderType()
/* 127:    */   {
/* 128:167 */     return 14;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean renderAsNormalBlock()
/* 132:    */   {
/* 133:172 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isOpaqueCube()
/* 137:    */   {
/* 138:177 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 142:    */   {
/* 143:182 */     func_149978_e();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 147:    */   {
/* 148:187 */     int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 149:188 */     int var7 = func_149895_l(var6);
/* 150:190 */     if (func_149975_b(var6))
/* 151:    */     {
/* 152:192 */       if (p_149695_1_.getBlock(p_149695_2_ - field_149981_a[var7][0], p_149695_3_, p_149695_4_ - field_149981_a[var7][1]) != this) {
/* 153:194 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 154:    */       }
/* 155:    */     }
/* 156:197 */     else if (p_149695_1_.getBlock(p_149695_2_ + field_149981_a[var7][0], p_149695_3_, p_149695_4_ + field_149981_a[var7][1]) != this)
/* 157:    */     {
/* 158:199 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 159:201 */       if (!p_149695_1_.isClient) {
/* 160:203 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 166:    */   {
/* 167:210 */     return func_149975_b(p_149650_1_) ? Item.getItemById(0) : Items.bed;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private void func_149978_e()
/* 171:    */   {
/* 172:215 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static boolean func_149975_b(int p_149975_0_)
/* 176:    */   {
/* 177:220 */     return (p_149975_0_ & 0x8) != 0;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static boolean func_149976_c(int p_149976_0_)
/* 181:    */   {
/* 182:225 */     return (p_149976_0_ & 0x4) != 0;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static void func_149979_a(World p_149979_0_, int p_149979_1_, int p_149979_2_, int p_149979_3_, boolean p_149979_4_)
/* 186:    */   {
/* 187:230 */     int var5 = p_149979_0_.getBlockMetadata(p_149979_1_, p_149979_2_, p_149979_3_);
/* 188:232 */     if (p_149979_4_) {
/* 189:234 */       var5 |= 0x4;
/* 190:    */     } else {
/* 191:238 */       var5 &= 0xFFFFFFFB;
/* 192:    */     }
/* 193:241 */     p_149979_0_.setBlockMetadataWithNotify(p_149979_1_, p_149979_2_, p_149979_3_, var5, 4);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static ChunkCoordinates func_149977_a(World p_149977_0_, int p_149977_1_, int p_149977_2_, int p_149977_3_, int p_149977_4_)
/* 197:    */   {
/* 198:246 */     int var5 = p_149977_0_.getBlockMetadata(p_149977_1_, p_149977_2_, p_149977_3_);
/* 199:247 */     int var6 = BlockDirectional.func_149895_l(var5);
/* 200:249 */     for (int var7 = 0; var7 <= 1; var7++)
/* 201:    */     {
/* 202:251 */       int var8 = p_149977_1_ - field_149981_a[var6][0] * var7 - 1;
/* 203:252 */       int var9 = p_149977_3_ - field_149981_a[var6][1] * var7 - 1;
/* 204:253 */       int var10 = var8 + 2;
/* 205:254 */       int var11 = var9 + 2;
/* 206:256 */       for (int var12 = var8; var12 <= var10; var12++) {
/* 207:258 */         for (int var13 = var9; var13 <= var11; var13++) {
/* 208:260 */           if ((World.doesBlockHaveSolidTopSurface(p_149977_0_, var12, p_149977_2_ - 1, var13)) && (!p_149977_0_.getBlock(var12, p_149977_2_, var13).getMaterial().isOpaque()) && (!p_149977_0_.getBlock(var12, p_149977_2_ + 1, var13).getMaterial().isOpaque()))
/* 209:    */           {
/* 210:262 */             if (p_149977_4_ <= 0) {
/* 211:264 */               return new ChunkCoordinates(var12, p_149977_2_, var13);
/* 212:    */             }
/* 213:267 */             p_149977_4_--;
/* 214:    */           }
/* 215:    */         }
/* 216:    */       }
/* 217:    */     }
/* 218:273 */     return null;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 222:    */   {
/* 223:281 */     if (!func_149975_b(p_149690_5_)) {
/* 224:283 */       super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getMobilityFlag()
/* 229:    */   {
/* 230:289 */     return 1;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 234:    */   {
/* 235:297 */     return Items.bed;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 239:    */   {
/* 240:305 */     if ((p_149681_6_.capabilities.isCreativeMode) && (func_149975_b(p_149681_5_)))
/* 241:    */     {
/* 242:307 */       int var7 = func_149895_l(p_149681_5_);
/* 243:308 */       p_149681_2_ -= field_149981_a[var7][0];
/* 244:309 */       p_149681_4_ -= field_149981_a[var7][1];
/* 245:311 */       if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_, p_149681_4_) == this) {
/* 246:313 */         p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_, p_149681_4_);
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBed
 * JD-Core Version:    0.7.0.1
 */