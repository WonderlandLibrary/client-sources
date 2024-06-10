/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.BlockLever;
/*   5:    */ import net.minecraft.entity.monster.EntityWitch;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.ItemEnchantedBook;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.WorldProvider;
/*  13:    */ 
/*  14:    */ public class ComponentScatteredFeaturePieces
/*  15:    */ {
/*  16:    */   private static final String __OBFID = "CL_00000473";
/*  17:    */   
/*  18:    */   public static void func_143045_a()
/*  19:    */   {
/*  20: 20 */     MapGenStructureIO.func_143031_a(DesertPyramid.class, "TeDP");
/*  21: 21 */     MapGenStructureIO.func_143031_a(JunglePyramid.class, "TeJP");
/*  22: 22 */     MapGenStructureIO.func_143031_a(SwampHut.class, "TeSH");
/*  23:    */   }
/*  24:    */   
/*  25:    */   static abstract class Feature
/*  26:    */     extends StructureComponent
/*  27:    */   {
/*  28:    */     protected int scatteredFeatureSizeX;
/*  29:    */     protected int scatteredFeatureSizeY;
/*  30:    */     protected int scatteredFeatureSizeZ;
/*  31: 30 */     protected int field_74936_d = -1;
/*  32:    */     private static final String __OBFID = "CL_00000479";
/*  33:    */     
/*  34:    */     public Feature() {}
/*  35:    */     
/*  36:    */     protected Feature(Random par1Random, int par2, int par3, int par4, int par5, int par6, int par7)
/*  37:    */     {
/*  38: 37 */       super();
/*  39: 38 */       this.scatteredFeatureSizeX = par5;
/*  40: 39 */       this.scatteredFeatureSizeY = par6;
/*  41: 40 */       this.scatteredFeatureSizeZ = par7;
/*  42: 41 */       this.coordBaseMode = par1Random.nextInt(4);
/*  43: 43 */       switch (this.coordBaseMode)
/*  44:    */       {
/*  45:    */       case 0: 
/*  46:    */       case 2: 
/*  47: 47 */         this.boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par5 - 1, par3 + par6 - 1, par4 + par7 - 1);
/*  48: 48 */         break;
/*  49:    */       case 1: 
/*  50:    */       default: 
/*  51: 51 */         this.boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par7 - 1, par3 + par6 - 1, par4 + par5 - 1);
/*  52:    */       }
/*  53:    */     }
/*  54:    */     
/*  55:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  56:    */     {
/*  57: 57 */       par1NBTTagCompound.setInteger("Width", this.scatteredFeatureSizeX);
/*  58: 58 */       par1NBTTagCompound.setInteger("Height", this.scatteredFeatureSizeY);
/*  59: 59 */       par1NBTTagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
/*  60: 60 */       par1NBTTagCompound.setInteger("HPos", this.field_74936_d);
/*  61:    */     }
/*  62:    */     
/*  63:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  64:    */     {
/*  65: 65 */       this.scatteredFeatureSizeX = par1NBTTagCompound.getInteger("Width");
/*  66: 66 */       this.scatteredFeatureSizeY = par1NBTTagCompound.getInteger("Height");
/*  67: 67 */       this.scatteredFeatureSizeZ = par1NBTTagCompound.getInteger("Depth");
/*  68: 68 */       this.field_74936_d = par1NBTTagCompound.getInteger("HPos");
/*  69:    */     }
/*  70:    */     
/*  71:    */     protected boolean func_74935_a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3)
/*  72:    */     {
/*  73: 73 */       if (this.field_74936_d >= 0) {
/*  74: 75 */         return true;
/*  75:    */       }
/*  76: 79 */       int var4 = 0;
/*  77: 80 */       int var5 = 0;
/*  78: 82 */       for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; var6++) {
/*  79: 84 */         for (int var7 = this.boundingBox.minX; var7 <= this.boundingBox.maxX; var7++) {
/*  80: 86 */           if (par2StructureBoundingBox.isVecInside(var7, 64, var6))
/*  81:    */           {
/*  82: 88 */             var4 += Math.max(par1World.getTopSolidOrLiquidBlock(var7, var6), par1World.provider.getAverageGroundLevel());
/*  83: 89 */             var5++;
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87: 94 */       if (var5 == 0) {
/*  88: 96 */         return false;
/*  89:    */       }
/*  90:100 */       this.field_74936_d = (var4 / var5);
/*  91:101 */       this.boundingBox.offset(0, this.field_74936_d - this.boundingBox.minY + par3, 0);
/*  92:102 */       return true;
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static class JunglePyramid
/*  97:    */     extends ComponentScatteredFeaturePieces.Feature
/*  98:    */   {
/*  99:    */     private boolean field_74947_h;
/* 100:    */     private boolean field_74948_i;
/* 101:    */     private boolean field_74945_j;
/* 102:    */     private boolean field_74946_k;
/* 103:114 */     private static final WeightedRandomChestContent[] junglePyramidsChestContents = { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
/* 104:115 */     private static final WeightedRandomChestContent[] junglePyramidsDispenserContents = { new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30) };
/* 105:116 */     private static Stones junglePyramidsRandomScatteredStones = new Stones(null);
/* 106:    */     private static final String __OBFID = "CL_00000477";
/* 107:    */     
/* 108:    */     public JunglePyramid() {}
/* 109:    */     
/* 110:    */     public JunglePyramid(Random par1Random, int par2, int par3)
/* 111:    */     {
/* 112:123 */       super(par2, 64, par3, 12, 10, 15);
/* 113:    */     }
/* 114:    */     
/* 115:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 116:    */     {
/* 117:128 */       super.func_143012_a(par1NBTTagCompound);
/* 118:129 */       par1NBTTagCompound.setBoolean("placedMainChest", this.field_74947_h);
/* 119:130 */       par1NBTTagCompound.setBoolean("placedHiddenChest", this.field_74948_i);
/* 120:131 */       par1NBTTagCompound.setBoolean("placedTrap1", this.field_74945_j);
/* 121:132 */       par1NBTTagCompound.setBoolean("placedTrap2", this.field_74946_k);
/* 122:    */     }
/* 123:    */     
/* 124:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 125:    */     {
/* 126:137 */       super.func_143011_b(par1NBTTagCompound);
/* 127:138 */       this.field_74947_h = par1NBTTagCompound.getBoolean("placedMainChest");
/* 128:139 */       this.field_74948_i = par1NBTTagCompound.getBoolean("placedHiddenChest");
/* 129:140 */       this.field_74945_j = par1NBTTagCompound.getBoolean("placedTrap1");
/* 130:141 */       this.field_74946_k = par1NBTTagCompound.getBoolean("placedTrap2");
/* 131:    */     }
/* 132:    */     
/* 133:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 134:    */     {
/* 135:146 */       if (!func_74935_a(par1World, par3StructureBoundingBox, 0)) {
/* 136:148 */         return false;
/* 137:    */       }
/* 138:152 */       int var4 = func_151555_a(Blocks.stone_stairs, 3);
/* 139:153 */       int var5 = func_151555_a(Blocks.stone_stairs, 2);
/* 140:154 */       int var6 = func_151555_a(Blocks.stone_stairs, 0);
/* 141:155 */       int var7 = func_151555_a(Blocks.stone_stairs, 1);
/* 142:156 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 143:157 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 1, 2, 9, 2, 2, false, par2Random, junglePyramidsRandomScatteredStones);
/* 144:158 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 1, 12, 9, 2, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 145:159 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 1, 3, 2, 2, 11, false, par2Random, junglePyramidsRandomScatteredStones);
/* 146:160 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, 1, 3, 9, 2, 11, false, par2Random, junglePyramidsRandomScatteredStones);
/* 147:161 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 1, 10, 6, 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 148:162 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 13, 10, 6, 13, false, par2Random, junglePyramidsRandomScatteredStones);
/* 149:163 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 2, 1, 6, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 150:164 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 10, 3, 2, 10, 6, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 151:165 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 3, 2, 9, 3, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 152:166 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 6, 2, 9, 6, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 153:167 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 3, 7, 3, 8, 7, 11, false, par2Random, junglePyramidsRandomScatteredStones);
/* 154:168 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 8, 4, 7, 8, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 155:169 */       fillWithAir(par1World, par3StructureBoundingBox, 3, 1, 3, 8, 2, 11);
/* 156:170 */       fillWithAir(par1World, par3StructureBoundingBox, 4, 3, 6, 7, 3, 9);
/* 157:171 */       fillWithAir(par1World, par3StructureBoundingBox, 2, 4, 2, 9, 5, 12);
/* 158:172 */       fillWithAir(par1World, par3StructureBoundingBox, 4, 6, 5, 7, 6, 9);
/* 159:173 */       fillWithAir(par1World, par3StructureBoundingBox, 5, 7, 6, 6, 7, 8);
/* 160:174 */       fillWithAir(par1World, par3StructureBoundingBox, 5, 1, 2, 6, 2, 2);
/* 161:175 */       fillWithAir(par1World, par3StructureBoundingBox, 5, 2, 12, 6, 2, 12);
/* 162:176 */       fillWithAir(par1World, par3StructureBoundingBox, 5, 5, 1, 6, 5, 1);
/* 163:177 */       fillWithAir(par1World, par3StructureBoundingBox, 5, 5, 13, 6, 5, 13);
/* 164:178 */       func_151550_a(par1World, Blocks.air, 0, 1, 5, 5, par3StructureBoundingBox);
/* 165:179 */       func_151550_a(par1World, Blocks.air, 0, 10, 5, 5, par3StructureBoundingBox);
/* 166:180 */       func_151550_a(par1World, Blocks.air, 0, 1, 5, 9, par3StructureBoundingBox);
/* 167:181 */       func_151550_a(par1World, Blocks.air, 0, 10, 5, 9, par3StructureBoundingBox);
/* 168:184 */       for (int var8 = 0; var8 <= 14; var8 += 14)
/* 169:    */       {
/* 170:186 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 4, var8, 2, 5, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 171:187 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 4, var8, 4, 5, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 172:188 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 7, 4, var8, 7, 5, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 173:189 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, 4, var8, 9, 5, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 174:    */       }
/* 175:192 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 6, 0, 6, 6, 0, false, par2Random, junglePyramidsRandomScatteredStones);
/* 176:194 */       for (var8 = 0; var8 <= 11; var8 += 11)
/* 177:    */       {
/* 178:196 */         for (int var9 = 2; var9 <= 12; var9 += 2) {
/* 179:198 */           fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, var8, 4, var9, var8, 5, var9, false, par2Random, junglePyramidsRandomScatteredStones);
/* 180:    */         }
/* 181:201 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, var8, 6, 5, var8, 6, 5, false, par2Random, junglePyramidsRandomScatteredStones);
/* 182:202 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, var8, 6, 9, var8, 6, 9, false, par2Random, junglePyramidsRandomScatteredStones);
/* 183:    */       }
/* 184:205 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 7, 2, 2, 9, 2, false, par2Random, junglePyramidsRandomScatteredStones);
/* 185:206 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, 7, 2, 9, 9, 2, false, par2Random, junglePyramidsRandomScatteredStones);
/* 186:207 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, 7, 12, 2, 9, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 187:208 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, 7, 12, 9, 9, 12, false, par2Random, junglePyramidsRandomScatteredStones);
/* 188:209 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 9, 4, 4, 9, 4, false, par2Random, junglePyramidsRandomScatteredStones);
/* 189:210 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 7, 9, 4, 7, 9, 4, false, par2Random, junglePyramidsRandomScatteredStones);
/* 190:211 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 9, 10, 4, 9, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 191:212 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 7, 9, 10, 7, 9, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 192:213 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 9, 7, 6, 9, 7, false, par2Random, junglePyramidsRandomScatteredStones);
/* 193:214 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 5, 9, 6, par3StructureBoundingBox);
/* 194:215 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 6, 9, 6, par3StructureBoundingBox);
/* 195:216 */       func_151550_a(par1World, Blocks.stone_stairs, var5, 5, 9, 8, par3StructureBoundingBox);
/* 196:217 */       func_151550_a(par1World, Blocks.stone_stairs, var5, 6, 9, 8, par3StructureBoundingBox);
/* 197:218 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 4, 0, 0, par3StructureBoundingBox);
/* 198:219 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 5, 0, 0, par3StructureBoundingBox);
/* 199:220 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 6, 0, 0, par3StructureBoundingBox);
/* 200:221 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 7, 0, 0, par3StructureBoundingBox);
/* 201:222 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 4, 1, 8, par3StructureBoundingBox);
/* 202:223 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 4, 2, 9, par3StructureBoundingBox);
/* 203:224 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 4, 3, 10, par3StructureBoundingBox);
/* 204:225 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 7, 1, 8, par3StructureBoundingBox);
/* 205:226 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 7, 2, 9, par3StructureBoundingBox);
/* 206:227 */       func_151550_a(par1World, Blocks.stone_stairs, var4, 7, 3, 10, par3StructureBoundingBox);
/* 207:228 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 1, 9, false, par2Random, junglePyramidsRandomScatteredStones);
/* 208:229 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 7, 1, 9, 7, 1, 9, false, par2Random, junglePyramidsRandomScatteredStones);
/* 209:230 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 10, 7, 2, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 210:231 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 4, 5, 6, 4, 5, false, par2Random, junglePyramidsRandomScatteredStones);
/* 211:232 */       func_151550_a(par1World, Blocks.stone_stairs, var6, 4, 4, 5, par3StructureBoundingBox);
/* 212:233 */       func_151550_a(par1World, Blocks.stone_stairs, var7, 7, 4, 5, par3StructureBoundingBox);
/* 213:235 */       for (var8 = 0; var8 < 4; var8++)
/* 214:    */       {
/* 215:237 */         func_151550_a(par1World, Blocks.stone_stairs, var5, 5, 0 - var8, 6 + var8, par3StructureBoundingBox);
/* 216:238 */         func_151550_a(par1World, Blocks.stone_stairs, var5, 6, 0 - var8, 6 + var8, par3StructureBoundingBox);
/* 217:239 */         fillWithAir(par1World, par3StructureBoundingBox, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
/* 218:    */       }
/* 219:242 */       fillWithAir(par1World, par3StructureBoundingBox, 1, -3, 12, 10, -1, 13);
/* 220:243 */       fillWithAir(par1World, par3StructureBoundingBox, 1, -3, 1, 3, -1, 13);
/* 221:244 */       fillWithAir(par1World, par3StructureBoundingBox, 1, -3, 1, 9, -1, 5);
/* 222:246 */       for (var8 = 1; var8 <= 13; var8 += 2) {
/* 223:248 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, -3, var8, 1, -2, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 224:    */       }
/* 225:251 */       for (var8 = 2; var8 <= 12; var8 += 2) {
/* 226:253 */         fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, -1, var8, 3, -1, var8, false, par2Random, junglePyramidsRandomScatteredStones);
/* 227:    */       }
/* 228:256 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, -2, 1, 5, -2, 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 229:257 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 7, -2, 1, 9, -2, 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 230:258 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 6, -3, 1, 6, -3, 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 231:259 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 6, -1, 1, 6, -1, 1, false, par2Random, junglePyramidsRandomScatteredStones);
/* 232:260 */       func_151550_a(par1World, Blocks.tripwire_hook, func_151555_a(Blocks.tripwire_hook, 3) | 0x4, 1, -3, 8, par3StructureBoundingBox);
/* 233:261 */       func_151550_a(par1World, Blocks.tripwire_hook, func_151555_a(Blocks.tripwire_hook, 1) | 0x4, 4, -3, 8, par3StructureBoundingBox);
/* 234:262 */       func_151550_a(par1World, Blocks.tripwire, 4, 2, -3, 8, par3StructureBoundingBox);
/* 235:263 */       func_151550_a(par1World, Blocks.tripwire, 4, 3, -3, 8, par3StructureBoundingBox);
/* 236:264 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 7, par3StructureBoundingBox);
/* 237:265 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 6, par3StructureBoundingBox);
/* 238:266 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 5, par3StructureBoundingBox);
/* 239:267 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 4, par3StructureBoundingBox);
/* 240:268 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 3, par3StructureBoundingBox);
/* 241:269 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 2, par3StructureBoundingBox);
/* 242:270 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 5, -3, 1, par3StructureBoundingBox);
/* 243:271 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 4, -3, 1, par3StructureBoundingBox);
/* 244:272 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 3, -3, 1, par3StructureBoundingBox);
/* 245:274 */       if (!this.field_74945_j) {
/* 246:276 */         this.field_74945_j = generateStructureDispenserContents(par1World, par3StructureBoundingBox, par2Random, 3, -2, 1, 2, junglePyramidsDispenserContents, 2);
/* 247:    */       }
/* 248:279 */       func_151550_a(par1World, Blocks.vine, 15, 3, -2, 2, par3StructureBoundingBox);
/* 249:280 */       func_151550_a(par1World, Blocks.tripwire_hook, func_151555_a(Blocks.tripwire_hook, 2) | 0x4, 7, -3, 1, par3StructureBoundingBox);
/* 250:281 */       func_151550_a(par1World, Blocks.tripwire_hook, func_151555_a(Blocks.tripwire_hook, 0) | 0x4, 7, -3, 5, par3StructureBoundingBox);
/* 251:282 */       func_151550_a(par1World, Blocks.tripwire, 4, 7, -3, 2, par3StructureBoundingBox);
/* 252:283 */       func_151550_a(par1World, Blocks.tripwire, 4, 7, -3, 3, par3StructureBoundingBox);
/* 253:284 */       func_151550_a(par1World, Blocks.tripwire, 4, 7, -3, 4, par3StructureBoundingBox);
/* 254:285 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 8, -3, 6, par3StructureBoundingBox);
/* 255:286 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 9, -3, 6, par3StructureBoundingBox);
/* 256:287 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 9, -3, 5, par3StructureBoundingBox);
/* 257:288 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 9, -3, 4, par3StructureBoundingBox);
/* 258:289 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 9, -2, 4, par3StructureBoundingBox);
/* 259:291 */       if (!this.field_74946_k) {
/* 260:293 */         this.field_74946_k = generateStructureDispenserContents(par1World, par3StructureBoundingBox, par2Random, 9, -2, 3, 4, junglePyramidsDispenserContents, 2);
/* 261:    */       }
/* 262:296 */       func_151550_a(par1World, Blocks.vine, 15, 8, -1, 3, par3StructureBoundingBox);
/* 263:297 */       func_151550_a(par1World, Blocks.vine, 15, 8, -2, 3, par3StructureBoundingBox);
/* 264:299 */       if (!this.field_74947_h) {
/* 265:301 */         this.field_74947_h = generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 8, -3, 3, WeightedRandomChestContent.func_92080_a(junglePyramidsChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 2 + par2Random.nextInt(5));
/* 266:    */       }
/* 267:304 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 9, -3, 2, par3StructureBoundingBox);
/* 268:305 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 8, -3, 1, par3StructureBoundingBox);
/* 269:306 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 4, -3, 5, par3StructureBoundingBox);
/* 270:307 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 5, -2, 5, par3StructureBoundingBox);
/* 271:308 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 5, -1, 5, par3StructureBoundingBox);
/* 272:309 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 6, -3, 5, par3StructureBoundingBox);
/* 273:310 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 7, -2, 5, par3StructureBoundingBox);
/* 274:311 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 7, -1, 5, par3StructureBoundingBox);
/* 275:312 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 8, -3, 5, par3StructureBoundingBox);
/* 276:313 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, -1, 1, 9, -1, 5, false, par2Random, junglePyramidsRandomScatteredStones);
/* 277:314 */       fillWithAir(par1World, par3StructureBoundingBox, 8, -3, 8, 10, -1, 10);
/* 278:315 */       func_151550_a(par1World, Blocks.stonebrick, 3, 8, -2, 11, par3StructureBoundingBox);
/* 279:316 */       func_151550_a(par1World, Blocks.stonebrick, 3, 9, -2, 11, par3StructureBoundingBox);
/* 280:317 */       func_151550_a(par1World, Blocks.stonebrick, 3, 10, -2, 11, par3StructureBoundingBox);
/* 281:318 */       func_151550_a(par1World, Blocks.lever, BlockLever.func_149819_b(func_151555_a(Blocks.lever, 2)), 8, -2, 12, par3StructureBoundingBox);
/* 282:319 */       func_151550_a(par1World, Blocks.lever, BlockLever.func_149819_b(func_151555_a(Blocks.lever, 2)), 9, -2, 12, par3StructureBoundingBox);
/* 283:320 */       func_151550_a(par1World, Blocks.lever, BlockLever.func_149819_b(func_151555_a(Blocks.lever, 2)), 10, -2, 12, par3StructureBoundingBox);
/* 284:321 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, -3, 8, 8, -3, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 285:322 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 10, -3, 8, 10, -3, 10, false, par2Random, junglePyramidsRandomScatteredStones);
/* 286:323 */       func_151550_a(par1World, Blocks.mossy_cobblestone, 0, 10, -2, 9, par3StructureBoundingBox);
/* 287:324 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 8, -2, 9, par3StructureBoundingBox);
/* 288:325 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 8, -2, 10, par3StructureBoundingBox);
/* 289:326 */       func_151550_a(par1World, Blocks.redstone_wire, 0, 10, -1, 9, par3StructureBoundingBox);
/* 290:327 */       func_151550_a(par1World, Blocks.sticky_piston, 1, 9, -2, 8, par3StructureBoundingBox);
/* 291:328 */       func_151550_a(par1World, Blocks.sticky_piston, func_151555_a(Blocks.sticky_piston, 4), 10, -2, 8, par3StructureBoundingBox);
/* 292:329 */       func_151550_a(par1World, Blocks.sticky_piston, func_151555_a(Blocks.sticky_piston, 4), 10, -1, 8, par3StructureBoundingBox);
/* 293:330 */       func_151550_a(par1World, Blocks.unpowered_repeater, func_151555_a(Blocks.unpowered_repeater, 2), 10, -2, 10, par3StructureBoundingBox);
/* 294:332 */       if (!this.field_74948_i) {
/* 295:334 */         this.field_74948_i = generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 9, -3, 10, WeightedRandomChestContent.func_92080_a(junglePyramidsChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 2 + par2Random.nextInt(5));
/* 296:    */       }
/* 297:337 */       return true;
/* 298:    */     }
/* 299:    */     
/* 300:    */     static class Stones
/* 301:    */       extends StructureComponent.BlockSelector
/* 302:    */     {
/* 303:    */       private static final String __OBFID = "CL_00000478";
/* 304:    */       
/* 305:    */       private Stones() {}
/* 306:    */       
/* 307:    */       public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
/* 308:    */       {
/* 309:349 */         if (par1Random.nextFloat() < 0.4F) {
/* 310:351 */           this.field_151562_a = Blocks.cobblestone;
/* 311:    */         } else {
/* 312:355 */           this.field_151562_a = Blocks.mossy_cobblestone;
/* 313:    */         }
/* 314:    */       }
/* 315:    */       
/* 316:    */       Stones(Object par1ComponentScatteredFeaturePieces2)
/* 317:    */       {
/* 318:361 */         this();
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static class SwampHut
/* 324:    */     extends ComponentScatteredFeaturePieces.Feature
/* 325:    */   {
/* 326:    */     private boolean hasWitch;
/* 327:    */     private static final String __OBFID = "CL_00000480";
/* 328:    */     
/* 329:    */     public SwampHut() {}
/* 330:    */     
/* 331:    */     public SwampHut(Random par1Random, int par2, int par3)
/* 332:    */     {
/* 333:375 */       super(par2, 64, par3, 7, 5, 9);
/* 334:    */     }
/* 335:    */     
/* 336:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 337:    */     {
/* 338:380 */       super.func_143012_a(par1NBTTagCompound);
/* 339:381 */       par1NBTTagCompound.setBoolean("Witch", this.hasWitch);
/* 340:    */     }
/* 341:    */     
/* 342:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 343:    */     {
/* 344:386 */       super.func_143011_b(par1NBTTagCompound);
/* 345:387 */       this.hasWitch = par1NBTTagCompound.getBoolean("Witch");
/* 346:    */     }
/* 347:    */     
/* 348:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 349:    */     {
/* 350:392 */       if (!func_74935_a(par1World, par3StructureBoundingBox, 0)) {
/* 351:394 */         return false;
/* 352:    */       }
/* 353:398 */       func_151556_a(par1World, par3StructureBoundingBox, 1, 1, 1, 5, 1, 7, Blocks.planks, 1, Blocks.planks, 1, false);
/* 354:399 */       func_151556_a(par1World, par3StructureBoundingBox, 1, 4, 2, 5, 4, 7, Blocks.planks, 1, Blocks.planks, 1, false);
/* 355:400 */       func_151556_a(par1World, par3StructureBoundingBox, 2, 1, 0, 4, 1, 0, Blocks.planks, 1, Blocks.planks, 1, false);
/* 356:401 */       func_151556_a(par1World, par3StructureBoundingBox, 2, 2, 2, 3, 3, 2, Blocks.planks, 1, Blocks.planks, 1, false);
/* 357:402 */       func_151556_a(par1World, par3StructureBoundingBox, 1, 2, 3, 1, 3, 6, Blocks.planks, 1, Blocks.planks, 1, false);
/* 358:403 */       func_151556_a(par1World, par3StructureBoundingBox, 5, 2, 3, 5, 3, 6, Blocks.planks, 1, Blocks.planks, 1, false);
/* 359:404 */       func_151556_a(par1World, par3StructureBoundingBox, 2, 2, 7, 4, 3, 7, Blocks.planks, 1, Blocks.planks, 1, false);
/* 360:405 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 2, 1, 3, 2, Blocks.log, Blocks.log, false);
/* 361:406 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 0, 2, 5, 3, 2, Blocks.log, Blocks.log, false);
/* 362:407 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 7, 1, 3, 7, Blocks.log, Blocks.log, false);
/* 363:408 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 0, 7, 5, 3, 7, Blocks.log, Blocks.log, false);
/* 364:409 */       func_151550_a(par1World, Blocks.fence, 0, 2, 3, 2, par3StructureBoundingBox);
/* 365:410 */       func_151550_a(par1World, Blocks.fence, 0, 3, 3, 7, par3StructureBoundingBox);
/* 366:411 */       func_151550_a(par1World, Blocks.air, 0, 1, 3, 4, par3StructureBoundingBox);
/* 367:412 */       func_151550_a(par1World, Blocks.air, 0, 5, 3, 4, par3StructureBoundingBox);
/* 368:413 */       func_151550_a(par1World, Blocks.air, 0, 5, 3, 5, par3StructureBoundingBox);
/* 369:414 */       func_151550_a(par1World, Blocks.flower_pot, 7, 1, 3, 5, par3StructureBoundingBox);
/* 370:415 */       func_151550_a(par1World, Blocks.crafting_table, 0, 3, 2, 6, par3StructureBoundingBox);
/* 371:416 */       func_151550_a(par1World, Blocks.cauldron, 0, 4, 2, 6, par3StructureBoundingBox);
/* 372:417 */       func_151550_a(par1World, Blocks.fence, 0, 1, 2, 1, par3StructureBoundingBox);
/* 373:418 */       func_151550_a(par1World, Blocks.fence, 0, 5, 2, 1, par3StructureBoundingBox);
/* 374:419 */       int var4 = func_151555_a(Blocks.oak_stairs, 3);
/* 375:420 */       int var5 = func_151555_a(Blocks.oak_stairs, 1);
/* 376:421 */       int var6 = func_151555_a(Blocks.oak_stairs, 0);
/* 377:422 */       int var7 = func_151555_a(Blocks.oak_stairs, 2);
/* 378:423 */       func_151556_a(par1World, par3StructureBoundingBox, 0, 4, 1, 6, 4, 1, Blocks.spruce_stairs, var4, Blocks.spruce_stairs, var4, false);
/* 379:424 */       func_151556_a(par1World, par3StructureBoundingBox, 0, 4, 2, 0, 4, 7, Blocks.spruce_stairs, var6, Blocks.spruce_stairs, var6, false);
/* 380:425 */       func_151556_a(par1World, par3StructureBoundingBox, 6, 4, 2, 6, 4, 7, Blocks.spruce_stairs, var5, Blocks.spruce_stairs, var5, false);
/* 381:426 */       func_151556_a(par1World, par3StructureBoundingBox, 0, 4, 8, 6, 4, 8, Blocks.spruce_stairs, var7, Blocks.spruce_stairs, var7, false);
/* 382:430 */       for (int var8 = 2; var8 <= 7; var8 += 5) {
/* 383:432 */         for (int var9 = 1; var9 <= 5; var9 += 4) {
/* 384:434 */           func_151554_b(par1World, Blocks.log, 0, var9, -1, var8, par3StructureBoundingBox);
/* 385:    */         }
/* 386:    */       }
/* 387:438 */       if (!this.hasWitch)
/* 388:    */       {
/* 389:440 */         var8 = getXWithOffset(2, 5);
/* 390:441 */         int var9 = getYWithOffset(2);
/* 391:442 */         int var10 = getZWithOffset(2, 5);
/* 392:444 */         if (par3StructureBoundingBox.isVecInside(var8, var9, var10))
/* 393:    */         {
/* 394:446 */           this.hasWitch = true;
/* 395:447 */           EntityWitch var11 = new EntityWitch(par1World);
/* 396:448 */           var11.setLocationAndAngles(var8 + 0.5D, var9, var10 + 0.5D, 0.0F, 0.0F);
/* 397:449 */           var11.onSpawnWithEgg(null);
/* 398:450 */           par1World.spawnEntityInWorld(var11);
/* 399:    */         }
/* 400:    */       }
/* 401:454 */       return true;
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public static class DesertPyramid
/* 406:    */     extends ComponentScatteredFeaturePieces.Feature
/* 407:    */   {
/* 408:461 */     private boolean[] field_74940_h = new boolean[4];
/* 409:462 */     private static final WeightedRandomChestContent[] itemsToGenerateInTemple = { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
/* 410:    */     private static final String __OBFID = "CL_00000476";
/* 411:    */     
/* 412:    */     public DesertPyramid() {}
/* 413:    */     
/* 414:    */     public DesertPyramid(Random par1Random, int par2, int par3)
/* 415:    */     {
/* 416:469 */       super(par2, 64, par3, 21, 15, 21);
/* 417:    */     }
/* 418:    */     
/* 419:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 420:    */     {
/* 421:474 */       super.func_143012_a(par1NBTTagCompound);
/* 422:475 */       par1NBTTagCompound.setBoolean("hasPlacedChest0", this.field_74940_h[0]);
/* 423:476 */       par1NBTTagCompound.setBoolean("hasPlacedChest1", this.field_74940_h[1]);
/* 424:477 */       par1NBTTagCompound.setBoolean("hasPlacedChest2", this.field_74940_h[2]);
/* 425:478 */       par1NBTTagCompound.setBoolean("hasPlacedChest3", this.field_74940_h[3]);
/* 426:    */     }
/* 427:    */     
/* 428:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 429:    */     {
/* 430:483 */       super.func_143011_b(par1NBTTagCompound);
/* 431:484 */       this.field_74940_h[0] = par1NBTTagCompound.getBoolean("hasPlacedChest0");
/* 432:485 */       this.field_74940_h[1] = par1NBTTagCompound.getBoolean("hasPlacedChest1");
/* 433:486 */       this.field_74940_h[2] = par1NBTTagCompound.getBoolean("hasPlacedChest2");
/* 434:487 */       this.field_74940_h[3] = par1NBTTagCompound.getBoolean("hasPlacedChest3");
/* 435:    */     }
/* 436:    */     
/* 437:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 438:    */     {
/* 439:492 */       func_151549_a(par1World, par3StructureBoundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Blocks.sandstone, Blocks.sandstone, false);
/* 440:495 */       for (int var4 = 1; var4 <= 9; var4++)
/* 441:    */       {
/* 442:497 */         func_151549_a(par1World, par3StructureBoundingBox, var4, var4, var4, this.scatteredFeatureSizeX - 1 - var4, var4, this.scatteredFeatureSizeZ - 1 - var4, Blocks.sandstone, Blocks.sandstone, false);
/* 443:498 */         func_151549_a(par1World, par3StructureBoundingBox, var4 + 1, var4, var4 + 1, this.scatteredFeatureSizeX - 2 - var4, var4, this.scatteredFeatureSizeZ - 2 - var4, Blocks.air, Blocks.air, false);
/* 444:    */       }
/* 445:503 */       for (var4 = 0; var4 < this.scatteredFeatureSizeX; var4++) {
/* 446:505 */         for (int var5 = 0; var5 < this.scatteredFeatureSizeZ; var5++)
/* 447:    */         {
/* 448:507 */           byte var6 = -5;
/* 449:508 */           func_151554_b(par1World, Blocks.sandstone, 0, var4, var6, var5, par3StructureBoundingBox);
/* 450:    */         }
/* 451:    */       }
/* 452:512 */       var4 = func_151555_a(Blocks.sandstone_stairs, 3);
/* 453:513 */       int var5 = func_151555_a(Blocks.sandstone_stairs, 2);
/* 454:514 */       int var13 = func_151555_a(Blocks.sandstone_stairs, 0);
/* 455:515 */       int var7 = func_151555_a(Blocks.sandstone_stairs, 1);
/* 456:516 */       byte var8 = 1;
/* 457:517 */       byte var9 = 11;
/* 458:518 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 9, 4, Blocks.sandstone, Blocks.air, false);
/* 459:519 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 10, 1, 3, 10, 3, Blocks.sandstone, Blocks.sandstone, false);
/* 460:520 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, 2, 10, 0, par3StructureBoundingBox);
/* 461:521 */       func_151550_a(par1World, Blocks.sandstone_stairs, var5, 2, 10, 4, par3StructureBoundingBox);
/* 462:522 */       func_151550_a(par1World, Blocks.sandstone_stairs, var13, 0, 10, 2, par3StructureBoundingBox);
/* 463:523 */       func_151550_a(par1World, Blocks.sandstone_stairs, var7, 4, 10, 2, par3StructureBoundingBox);
/* 464:524 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 0, 0, this.scatteredFeatureSizeX - 1, 9, 4, Blocks.sandstone, Blocks.air, false);
/* 465:525 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 4, 10, 1, this.scatteredFeatureSizeX - 2, 10, 3, Blocks.sandstone, Blocks.sandstone, false);
/* 466:526 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, this.scatteredFeatureSizeX - 3, 10, 0, par3StructureBoundingBox);
/* 467:527 */       func_151550_a(par1World, Blocks.sandstone_stairs, var5, this.scatteredFeatureSizeX - 3, 10, 4, par3StructureBoundingBox);
/* 468:528 */       func_151550_a(par1World, Blocks.sandstone_stairs, var13, this.scatteredFeatureSizeX - 5, 10, 2, par3StructureBoundingBox);
/* 469:529 */       func_151550_a(par1World, Blocks.sandstone_stairs, var7, this.scatteredFeatureSizeX - 1, 10, 2, par3StructureBoundingBox);
/* 470:530 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 0, 0, 12, 4, 4, Blocks.sandstone, Blocks.air, false);
/* 471:531 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 1, 0, 11, 3, 4, Blocks.air, Blocks.air, false);
/* 472:532 */       func_151550_a(par1World, Blocks.sandstone, 2, 9, 1, 1, par3StructureBoundingBox);
/* 473:533 */       func_151550_a(par1World, Blocks.sandstone, 2, 9, 2, 1, par3StructureBoundingBox);
/* 474:534 */       func_151550_a(par1World, Blocks.sandstone, 2, 9, 3, 1, par3StructureBoundingBox);
/* 475:535 */       func_151550_a(par1World, Blocks.sandstone, 2, 10, 3, 1, par3StructureBoundingBox);
/* 476:536 */       func_151550_a(par1World, Blocks.sandstone, 2, 11, 3, 1, par3StructureBoundingBox);
/* 477:537 */       func_151550_a(par1World, Blocks.sandstone, 2, 11, 2, 1, par3StructureBoundingBox);
/* 478:538 */       func_151550_a(par1World, Blocks.sandstone, 2, 11, 1, 1, par3StructureBoundingBox);
/* 479:539 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 8, 3, 3, Blocks.sandstone, Blocks.air, false);
/* 480:540 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 2, 8, 2, 2, Blocks.air, Blocks.air, false);
/* 481:541 */       func_151549_a(par1World, par3StructureBoundingBox, 12, 1, 1, 16, 3, 3, Blocks.sandstone, Blocks.air, false);
/* 482:542 */       func_151549_a(par1World, par3StructureBoundingBox, 12, 1, 2, 16, 2, 2, Blocks.air, Blocks.air, false);
/* 483:543 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 4, 5, this.scatteredFeatureSizeX - 6, 4, this.scatteredFeatureSizeZ - 6, Blocks.sandstone, Blocks.sandstone, false);
/* 484:544 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 4, 9, 11, 4, 11, Blocks.air, Blocks.air, false);
/* 485:545 */       func_151556_a(par1World, par3StructureBoundingBox, 8, 1, 8, 8, 3, 8, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 486:546 */       func_151556_a(par1World, par3StructureBoundingBox, 12, 1, 8, 12, 3, 8, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 487:547 */       func_151556_a(par1World, par3StructureBoundingBox, 8, 1, 12, 8, 3, 12, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 488:548 */       func_151556_a(par1World, par3StructureBoundingBox, 12, 1, 12, 12, 3, 12, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 489:549 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 5, 4, 4, 11, Blocks.sandstone, Blocks.sandstone, false);
/* 490:550 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 1, 5, this.scatteredFeatureSizeX - 2, 4, 11, Blocks.sandstone, Blocks.sandstone, false);
/* 491:551 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 7, 9, 6, 7, 11, Blocks.sandstone, Blocks.sandstone, false);
/* 492:552 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 7, 7, 9, this.scatteredFeatureSizeX - 7, 7, 11, Blocks.sandstone, Blocks.sandstone, false);
/* 493:553 */       func_151556_a(par1World, par3StructureBoundingBox, 5, 5, 9, 5, 7, 11, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 494:554 */       func_151556_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 6, 5, 9, this.scatteredFeatureSizeX - 6, 7, 11, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 495:555 */       func_151550_a(par1World, Blocks.air, 0, 5, 5, 10, par3StructureBoundingBox);
/* 496:556 */       func_151550_a(par1World, Blocks.air, 0, 5, 6, 10, par3StructureBoundingBox);
/* 497:557 */       func_151550_a(par1World, Blocks.air, 0, 6, 6, 10, par3StructureBoundingBox);
/* 498:558 */       func_151550_a(par1World, Blocks.air, 0, this.scatteredFeatureSizeX - 6, 5, 10, par3StructureBoundingBox);
/* 499:559 */       func_151550_a(par1World, Blocks.air, 0, this.scatteredFeatureSizeX - 6, 6, 10, par3StructureBoundingBox);
/* 500:560 */       func_151550_a(par1World, Blocks.air, 0, this.scatteredFeatureSizeX - 7, 6, 10, par3StructureBoundingBox);
/* 501:561 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 4, 4, 2, 6, 4, Blocks.air, Blocks.air, false);
/* 502:562 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 3, 4, 4, this.scatteredFeatureSizeX - 3, 6, 4, Blocks.air, Blocks.air, false);
/* 503:563 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, 2, 4, 5, par3StructureBoundingBox);
/* 504:564 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, 2, 3, 4, par3StructureBoundingBox);
/* 505:565 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, this.scatteredFeatureSizeX - 3, 4, 5, par3StructureBoundingBox);
/* 506:566 */       func_151550_a(par1World, Blocks.sandstone_stairs, var4, this.scatteredFeatureSizeX - 3, 3, 4, par3StructureBoundingBox);
/* 507:567 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 3, 2, 2, 3, Blocks.sandstone, Blocks.sandstone, false);
/* 508:568 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 3, 1, 3, this.scatteredFeatureSizeX - 2, 2, 3, Blocks.sandstone, Blocks.sandstone, false);
/* 509:569 */       func_151550_a(par1World, Blocks.sandstone_stairs, 0, 1, 1, 2, par3StructureBoundingBox);
/* 510:570 */       func_151550_a(par1World, Blocks.sandstone_stairs, 0, this.scatteredFeatureSizeX - 2, 1, 2, par3StructureBoundingBox);
/* 511:571 */       func_151550_a(par1World, Blocks.stone_slab, 1, 1, 2, 2, par3StructureBoundingBox);
/* 512:572 */       func_151550_a(par1World, Blocks.stone_slab, 1, this.scatteredFeatureSizeX - 2, 2, 2, par3StructureBoundingBox);
/* 513:573 */       func_151550_a(par1World, Blocks.sandstone_stairs, var7, 2, 1, 2, par3StructureBoundingBox);
/* 514:574 */       func_151550_a(par1World, Blocks.sandstone_stairs, var13, this.scatteredFeatureSizeX - 3, 1, 2, par3StructureBoundingBox);
/* 515:575 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 5, 4, 3, 18, Blocks.sandstone, Blocks.sandstone, false);
/* 516:576 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 3, 5, this.scatteredFeatureSizeX - 5, 3, 17, Blocks.sandstone, Blocks.sandstone, false);
/* 517:577 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 5, 4, 2, 16, Blocks.air, Blocks.air, false);
/* 518:578 */       func_151549_a(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 6, 1, 5, this.scatteredFeatureSizeX - 5, 2, 16, Blocks.air, Blocks.air, false);
/* 519:581 */       for (int var10 = 5; var10 <= 17; var10 += 2)
/* 520:    */       {
/* 521:583 */         func_151550_a(par1World, Blocks.sandstone, 2, 4, 1, var10, par3StructureBoundingBox);
/* 522:584 */         func_151550_a(par1World, Blocks.sandstone, 1, 4, 2, var10, par3StructureBoundingBox);
/* 523:585 */         func_151550_a(par1World, Blocks.sandstone, 2, this.scatteredFeatureSizeX - 5, 1, var10, par3StructureBoundingBox);
/* 524:586 */         func_151550_a(par1World, Blocks.sandstone, 1, this.scatteredFeatureSizeX - 5, 2, var10, par3StructureBoundingBox);
/* 525:    */       }
/* 526:589 */       func_151550_a(par1World, Blocks.wool, var8, 10, 0, 7, par3StructureBoundingBox);
/* 527:590 */       func_151550_a(par1World, Blocks.wool, var8, 10, 0, 8, par3StructureBoundingBox);
/* 528:591 */       func_151550_a(par1World, Blocks.wool, var8, 9, 0, 9, par3StructureBoundingBox);
/* 529:592 */       func_151550_a(par1World, Blocks.wool, var8, 11, 0, 9, par3StructureBoundingBox);
/* 530:593 */       func_151550_a(par1World, Blocks.wool, var8, 8, 0, 10, par3StructureBoundingBox);
/* 531:594 */       func_151550_a(par1World, Blocks.wool, var8, 12, 0, 10, par3StructureBoundingBox);
/* 532:595 */       func_151550_a(par1World, Blocks.wool, var8, 7, 0, 10, par3StructureBoundingBox);
/* 533:596 */       func_151550_a(par1World, Blocks.wool, var8, 13, 0, 10, par3StructureBoundingBox);
/* 534:597 */       func_151550_a(par1World, Blocks.wool, var8, 9, 0, 11, par3StructureBoundingBox);
/* 535:598 */       func_151550_a(par1World, Blocks.wool, var8, 11, 0, 11, par3StructureBoundingBox);
/* 536:599 */       func_151550_a(par1World, Blocks.wool, var8, 10, 0, 12, par3StructureBoundingBox);
/* 537:600 */       func_151550_a(par1World, Blocks.wool, var8, 10, 0, 13, par3StructureBoundingBox);
/* 538:601 */       func_151550_a(par1World, Blocks.wool, var9, 10, 0, 10, par3StructureBoundingBox);
/* 539:603 */       for (var10 = 0; var10 <= this.scatteredFeatureSizeX - 1; var10 += this.scatteredFeatureSizeX - 1)
/* 540:    */       {
/* 541:605 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 2, 1, par3StructureBoundingBox);
/* 542:606 */         func_151550_a(par1World, Blocks.wool, var8, var10, 2, 2, par3StructureBoundingBox);
/* 543:607 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 2, 3, par3StructureBoundingBox);
/* 544:608 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 3, 1, par3StructureBoundingBox);
/* 545:609 */         func_151550_a(par1World, Blocks.wool, var8, var10, 3, 2, par3StructureBoundingBox);
/* 546:610 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 3, 3, par3StructureBoundingBox);
/* 547:611 */         func_151550_a(par1World, Blocks.wool, var8, var10, 4, 1, par3StructureBoundingBox);
/* 548:612 */         func_151550_a(par1World, Blocks.sandstone, 1, var10, 4, 2, par3StructureBoundingBox);
/* 549:613 */         func_151550_a(par1World, Blocks.wool, var8, var10, 4, 3, par3StructureBoundingBox);
/* 550:614 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 5, 1, par3StructureBoundingBox);
/* 551:615 */         func_151550_a(par1World, Blocks.wool, var8, var10, 5, 2, par3StructureBoundingBox);
/* 552:616 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 5, 3, par3StructureBoundingBox);
/* 553:617 */         func_151550_a(par1World, Blocks.wool, var8, var10, 6, 1, par3StructureBoundingBox);
/* 554:618 */         func_151550_a(par1World, Blocks.sandstone, 1, var10, 6, 2, par3StructureBoundingBox);
/* 555:619 */         func_151550_a(par1World, Blocks.wool, var8, var10, 6, 3, par3StructureBoundingBox);
/* 556:620 */         func_151550_a(par1World, Blocks.wool, var8, var10, 7, 1, par3StructureBoundingBox);
/* 557:621 */         func_151550_a(par1World, Blocks.wool, var8, var10, 7, 2, par3StructureBoundingBox);
/* 558:622 */         func_151550_a(par1World, Blocks.wool, var8, var10, 7, 3, par3StructureBoundingBox);
/* 559:623 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 8, 1, par3StructureBoundingBox);
/* 560:624 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 8, 2, par3StructureBoundingBox);
/* 561:625 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 8, 3, par3StructureBoundingBox);
/* 562:    */       }
/* 563:628 */       for (var10 = 2; var10 <= this.scatteredFeatureSizeX - 3; var10 += this.scatteredFeatureSizeX - 3 - 2)
/* 564:    */       {
/* 565:630 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 - 1, 2, 0, par3StructureBoundingBox);
/* 566:631 */         func_151550_a(par1World, Blocks.wool, var8, var10, 2, 0, par3StructureBoundingBox);
/* 567:632 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 + 1, 2, 0, par3StructureBoundingBox);
/* 568:633 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 - 1, 3, 0, par3StructureBoundingBox);
/* 569:634 */         func_151550_a(par1World, Blocks.wool, var8, var10, 3, 0, par3StructureBoundingBox);
/* 570:635 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 + 1, 3, 0, par3StructureBoundingBox);
/* 571:636 */         func_151550_a(par1World, Blocks.wool, var8, var10 - 1, 4, 0, par3StructureBoundingBox);
/* 572:637 */         func_151550_a(par1World, Blocks.sandstone, 1, var10, 4, 0, par3StructureBoundingBox);
/* 573:638 */         func_151550_a(par1World, Blocks.wool, var8, var10 + 1, 4, 0, par3StructureBoundingBox);
/* 574:639 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 - 1, 5, 0, par3StructureBoundingBox);
/* 575:640 */         func_151550_a(par1World, Blocks.wool, var8, var10, 5, 0, par3StructureBoundingBox);
/* 576:641 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 + 1, 5, 0, par3StructureBoundingBox);
/* 577:642 */         func_151550_a(par1World, Blocks.wool, var8, var10 - 1, 6, 0, par3StructureBoundingBox);
/* 578:643 */         func_151550_a(par1World, Blocks.sandstone, 1, var10, 6, 0, par3StructureBoundingBox);
/* 579:644 */         func_151550_a(par1World, Blocks.wool, var8, var10 + 1, 6, 0, par3StructureBoundingBox);
/* 580:645 */         func_151550_a(par1World, Blocks.wool, var8, var10 - 1, 7, 0, par3StructureBoundingBox);
/* 581:646 */         func_151550_a(par1World, Blocks.wool, var8, var10, 7, 0, par3StructureBoundingBox);
/* 582:647 */         func_151550_a(par1World, Blocks.wool, var8, var10 + 1, 7, 0, par3StructureBoundingBox);
/* 583:648 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 - 1, 8, 0, par3StructureBoundingBox);
/* 584:649 */         func_151550_a(par1World, Blocks.sandstone, 2, var10, 8, 0, par3StructureBoundingBox);
/* 585:650 */         func_151550_a(par1World, Blocks.sandstone, 2, var10 + 1, 8, 0, par3StructureBoundingBox);
/* 586:    */       }
/* 587:653 */       func_151556_a(par1World, par3StructureBoundingBox, 8, 4, 0, 12, 6, 0, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 588:654 */       func_151550_a(par1World, Blocks.air, 0, 8, 6, 0, par3StructureBoundingBox);
/* 589:655 */       func_151550_a(par1World, Blocks.air, 0, 12, 6, 0, par3StructureBoundingBox);
/* 590:656 */       func_151550_a(par1World, Blocks.wool, var8, 9, 5, 0, par3StructureBoundingBox);
/* 591:657 */       func_151550_a(par1World, Blocks.sandstone, 1, 10, 5, 0, par3StructureBoundingBox);
/* 592:658 */       func_151550_a(par1World, Blocks.wool, var8, 11, 5, 0, par3StructureBoundingBox);
/* 593:659 */       func_151556_a(par1World, par3StructureBoundingBox, 8, -14, 8, 12, -11, 12, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 594:660 */       func_151556_a(par1World, par3StructureBoundingBox, 8, -10, 8, 12, -10, 12, Blocks.sandstone, 1, Blocks.sandstone, 1, false);
/* 595:661 */       func_151556_a(par1World, par3StructureBoundingBox, 8, -9, 8, 12, -9, 12, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
/* 596:662 */       func_151549_a(par1World, par3StructureBoundingBox, 8, -8, 8, 12, -1, 12, Blocks.sandstone, Blocks.sandstone, false);
/* 597:663 */       func_151549_a(par1World, par3StructureBoundingBox, 9, -11, 9, 11, -1, 11, Blocks.air, Blocks.air, false);
/* 598:664 */       func_151550_a(par1World, Blocks.stone_pressure_plate, 0, 10, -11, 10, par3StructureBoundingBox);
/* 599:665 */       func_151549_a(par1World, par3StructureBoundingBox, 9, -13, 9, 11, -13, 11, Blocks.tnt, Blocks.air, false);
/* 600:666 */       func_151550_a(par1World, Blocks.air, 0, 8, -11, 10, par3StructureBoundingBox);
/* 601:667 */       func_151550_a(par1World, Blocks.air, 0, 8, -10, 10, par3StructureBoundingBox);
/* 602:668 */       func_151550_a(par1World, Blocks.sandstone, 1, 7, -10, 10, par3StructureBoundingBox);
/* 603:669 */       func_151550_a(par1World, Blocks.sandstone, 2, 7, -11, 10, par3StructureBoundingBox);
/* 604:670 */       func_151550_a(par1World, Blocks.air, 0, 12, -11, 10, par3StructureBoundingBox);
/* 605:671 */       func_151550_a(par1World, Blocks.air, 0, 12, -10, 10, par3StructureBoundingBox);
/* 606:672 */       func_151550_a(par1World, Blocks.sandstone, 1, 13, -10, 10, par3StructureBoundingBox);
/* 607:673 */       func_151550_a(par1World, Blocks.sandstone, 2, 13, -11, 10, par3StructureBoundingBox);
/* 608:674 */       func_151550_a(par1World, Blocks.air, 0, 10, -11, 8, par3StructureBoundingBox);
/* 609:675 */       func_151550_a(par1World, Blocks.air, 0, 10, -10, 8, par3StructureBoundingBox);
/* 610:676 */       func_151550_a(par1World, Blocks.sandstone, 1, 10, -10, 7, par3StructureBoundingBox);
/* 611:677 */       func_151550_a(par1World, Blocks.sandstone, 2, 10, -11, 7, par3StructureBoundingBox);
/* 612:678 */       func_151550_a(par1World, Blocks.air, 0, 10, -11, 12, par3StructureBoundingBox);
/* 613:679 */       func_151550_a(par1World, Blocks.air, 0, 10, -10, 12, par3StructureBoundingBox);
/* 614:680 */       func_151550_a(par1World, Blocks.sandstone, 1, 10, -10, 13, par3StructureBoundingBox);
/* 615:681 */       func_151550_a(par1World, Blocks.sandstone, 2, 10, -11, 13, par3StructureBoundingBox);
/* 616:683 */       for (var10 = 0; var10 < 4; var10++) {
/* 617:685 */         if (this.field_74940_h[var10] == 0)
/* 618:    */         {
/* 619:687 */           int var11 = net.minecraft.util.Direction.offsetX[var10] * 2;
/* 620:688 */           int var12 = net.minecraft.util.Direction.offsetZ[var10] * 2;
/* 621:689 */           this.field_74940_h[var10] = generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 10 + var11, -11, 10 + var12, WeightedRandomChestContent.func_92080_a(itemsToGenerateInTemple, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 2 + par2Random.nextInt(5));
/* 622:    */         }
/* 623:    */       }
/* 624:693 */       return true;
/* 625:    */     }
/* 626:    */   }
/* 627:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces
 * JD-Core Version:    0.7.0.1
 */