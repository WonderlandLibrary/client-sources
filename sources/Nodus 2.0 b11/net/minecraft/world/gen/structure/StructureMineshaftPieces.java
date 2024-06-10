/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.entity.item.EntityMinecartChest;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemEnchantedBook;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.nbt.NBTTagList;
/*  16:    */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  17:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  18:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class StructureMineshaftPieces
/*  22:    */ {
/*  23: 22 */   private static final WeightedRandomChestContent[] mineshaftChestContents = { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, 4, 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) };
/*  24:    */   private static final String __OBFID = "CL_00000444";
/*  25:    */   
/*  26:    */   public static void func_143048_a()
/*  27:    */   {
/*  28: 27 */     MapGenStructureIO.func_143031_a(Corridor.class, "MSCorridor");
/*  29: 28 */     MapGenStructureIO.func_143031_a(Cross.class, "MSCrossing");
/*  30: 29 */     MapGenStructureIO.func_143031_a(Room.class, "MSRoom");
/*  31: 30 */     MapGenStructureIO.func_143031_a(Stairs.class, "MSStairs");
/*  32:    */   }
/*  33:    */   
/*  34:    */   private static StructureComponent getRandomComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  35:    */   {
/*  36: 35 */     int var7 = par1Random.nextInt(100);
/*  37: 38 */     if (var7 >= 80)
/*  38:    */     {
/*  39: 40 */       StructureBoundingBox var8 = Cross.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
/*  40: 42 */       if (var8 != null) {
/*  41: 44 */         return new Cross(par6, par1Random, var8, par5);
/*  42:    */       }
/*  43:    */     }
/*  44: 47 */     else if (var7 >= 70)
/*  45:    */     {
/*  46: 49 */       StructureBoundingBox var8 = Stairs.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
/*  47: 51 */       if (var8 != null) {
/*  48: 53 */         return new Stairs(par6, par1Random, var8, par5);
/*  49:    */       }
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53: 58 */       StructureBoundingBox var8 = Corridor.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
/*  54: 60 */       if (var8 != null) {
/*  55: 62 */         return new Corridor(par6, par1Random, var8, par5);
/*  56:    */       }
/*  57:    */     }
/*  58: 66 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static StructureComponent getNextMineShaftComponent(StructureComponent par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  62:    */   {
/*  63: 71 */     if (par7 > 8) {
/*  64: 73 */       return null;
/*  65:    */     }
/*  66: 75 */     if ((Math.abs(par3 - par0StructureComponent.getBoundingBox().minX) <= 80) && (Math.abs(par5 - par0StructureComponent.getBoundingBox().minZ) <= 80))
/*  67:    */     {
/*  68: 77 */       StructureComponent var8 = getRandomComponent(par1List, par2Random, par3, par4, par5, par6, par7 + 1);
/*  69: 79 */       if (var8 != null)
/*  70:    */       {
/*  71: 81 */         par1List.add(var8);
/*  72: 82 */         var8.buildComponent(par0StructureComponent, par1List, par2Random);
/*  73:    */       }
/*  74: 85 */       return var8;
/*  75:    */     }
/*  76: 89 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static class Cross
/*  80:    */     extends StructureComponent
/*  81:    */   {
/*  82:    */     private int corridorDirection;
/*  83:    */     private boolean isMultipleFloors;
/*  84:    */     private static final String __OBFID = "CL_00000446";
/*  85:    */     
/*  86:    */     public Cross() {}
/*  87:    */     
/*  88:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  89:    */     {
/*  90:103 */       par1NBTTagCompound.setBoolean("tf", this.isMultipleFloors);
/*  91:104 */       par1NBTTagCompound.setInteger("D", this.corridorDirection);
/*  92:    */     }
/*  93:    */     
/*  94:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  95:    */     {
/*  96:109 */       this.isMultipleFloors = par1NBTTagCompound.getBoolean("tf");
/*  97:110 */       this.corridorDirection = par1NBTTagCompound.getInteger("D");
/*  98:    */     }
/*  99:    */     
/* 100:    */     public Cross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 101:    */     {
/* 102:115 */       super();
/* 103:116 */       this.corridorDirection = par4;
/* 104:117 */       this.boundingBox = par3StructureBoundingBox;
/* 105:118 */       this.isMultipleFloors = (par3StructureBoundingBox.getYSize() > 3);
/* 106:    */     }
/* 107:    */     
/* 108:    */     public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
/* 109:    */     {
/* 110:123 */       StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
/* 111:125 */       if (par1Random.nextInt(4) == 0) {
/* 112:127 */         var6.maxY += 4;
/* 113:    */       }
/* 114:130 */       switch (par5)
/* 115:    */       {
/* 116:    */       case 0: 
/* 117:133 */         var6.minX = (par2 - 1);
/* 118:134 */         var6.maxX = (par2 + 3);
/* 119:135 */         var6.maxZ = (par4 + 4);
/* 120:136 */         break;
/* 121:    */       case 1: 
/* 122:139 */         var6.minX = (par2 - 4);
/* 123:140 */         var6.minZ = (par4 - 1);
/* 124:141 */         var6.maxZ = (par4 + 3);
/* 125:142 */         break;
/* 126:    */       case 2: 
/* 127:145 */         var6.minX = (par2 - 1);
/* 128:146 */         var6.maxX = (par2 + 3);
/* 129:147 */         var6.minZ = (par4 - 4);
/* 130:148 */         break;
/* 131:    */       case 3: 
/* 132:151 */         var6.maxX = (par2 + 4);
/* 133:152 */         var6.minZ = (par4 - 1);
/* 134:153 */         var6.maxZ = (par4 + 3);
/* 135:    */       }
/* 136:156 */       return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 140:    */     {
/* 141:161 */       int var4 = getComponentType();
/* 142:163 */       switch (this.corridorDirection)
/* 143:    */       {
/* 144:    */       case 0: 
/* 145:166 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
/* 146:167 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
/* 147:168 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
/* 148:169 */         break;
/* 149:    */       case 1: 
/* 150:172 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
/* 151:173 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
/* 152:174 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
/* 153:175 */         break;
/* 154:    */       case 2: 
/* 155:178 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
/* 156:179 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
/* 157:180 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
/* 158:181 */         break;
/* 159:    */       case 3: 
/* 160:184 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
/* 161:185 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
/* 162:186 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
/* 163:    */       }
/* 164:189 */       if (this.isMultipleFloors)
/* 165:    */       {
/* 166:191 */         if (par3Random.nextBoolean()) {
/* 167:193 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, 2, var4);
/* 168:    */         }
/* 169:196 */         if (par3Random.nextBoolean()) {
/* 170:198 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 1, var4);
/* 171:    */         }
/* 172:201 */         if (par3Random.nextBoolean()) {
/* 173:203 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 3, var4);
/* 174:    */         }
/* 175:206 */         if (par3Random.nextBoolean()) {
/* 176:208 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, 0, var4);
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:    */     
/* 181:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 182:    */     {
/* 183:215 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 184:217 */         return false;
/* 185:    */       }
/* 186:221 */       if (this.isMultipleFloors)
/* 187:    */       {
/* 188:223 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
/* 189:224 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
/* 190:225 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
/* 191:226 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
/* 192:227 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
/* 193:    */       }
/* 194:    */       else
/* 195:    */       {
/* 196:231 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
/* 197:232 */         func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
/* 198:    */       }
/* 199:235 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
/* 200:236 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);
/* 201:237 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
/* 202:238 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);
/* 203:240 */       for (int var4 = this.boundingBox.minX; var4 <= this.boundingBox.maxX; var4++) {
/* 204:242 */         for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; var5++) {
/* 205:244 */           if (func_151548_a(par1World, var4, this.boundingBox.minY - 1, var5, par3StructureBoundingBox).getMaterial() == Material.air) {
/* 206:246 */             func_151550_a(par1World, Blocks.planks, 0, var4, this.boundingBox.minY - 1, var5, par3StructureBoundingBox);
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:251 */       return true;
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static class Room
/* 215:    */     extends StructureComponent
/* 216:    */   {
/* 217:258 */     private List roomsLinkedToTheRoom = new LinkedList();
/* 218:    */     private static final String __OBFID = "CL_00000447";
/* 219:    */     
/* 220:    */     public Room() {}
/* 221:    */     
/* 222:    */     public Room(int par1, Random par2Random, int par3, int par4)
/* 223:    */     {
/* 224:265 */       super();
/* 225:266 */       this.boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
/* 226:    */     }
/* 227:    */     
/* 228:    */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 229:    */     {
/* 230:271 */       int var4 = getComponentType();
/* 231:272 */       int var6 = this.boundingBox.getYSize() - 3 - 1;
/* 232:274 */       if (var6 <= 0) {
/* 233:276 */         var6 = 1;
/* 234:    */       }
/* 235:283 */       for (int var5 = 0; var5 < this.boundingBox.getXSize(); var5 += 4)
/* 236:    */       {
/* 237:285 */         var5 += par3Random.nextInt(this.boundingBox.getXSize());
/* 238:287 */         if (var5 + 3 > this.boundingBox.getXSize()) {
/* 239:    */           break;
/* 240:    */         }
/* 241:292 */         StructureComponent var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var5, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ - 1, 2, var4);
/* 242:294 */         if (var7 != null)
/* 243:    */         {
/* 244:296 */           StructureBoundingBox var8 = var7.getBoundingBox();
/* 245:297 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
/* 246:    */         }
/* 247:    */       }
/* 248:301 */       for (var5 = 0; var5 < this.boundingBox.getXSize(); var5 += 4)
/* 249:    */       {
/* 250:303 */         var5 += par3Random.nextInt(this.boundingBox.getXSize());
/* 251:305 */         if (var5 + 3 > this.boundingBox.getXSize()) {
/* 252:    */           break;
/* 253:    */         }
/* 254:310 */         StructureComponent var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var5, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.maxZ + 1, 0, var4);
/* 255:312 */         if (var7 != null)
/* 256:    */         {
/* 257:314 */           StructureBoundingBox var8 = var7.getBoundingBox();
/* 258:315 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
/* 259:    */         }
/* 260:    */       }
/* 261:319 */       for (var5 = 0; var5 < this.boundingBox.getZSize(); var5 += 4)
/* 262:    */       {
/* 263:321 */         var5 += par3Random.nextInt(this.boundingBox.getZSize());
/* 264:323 */         if (var5 + 3 > this.boundingBox.getZSize()) {
/* 265:    */           break;
/* 266:    */         }
/* 267:328 */         StructureComponent var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ + var5, 1, var4);
/* 268:330 */         if (var7 != null)
/* 269:    */         {
/* 270:332 */           StructureBoundingBox var8 = var7.getBoundingBox();
/* 271:333 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
/* 272:    */         }
/* 273:    */       }
/* 274:337 */       for (var5 = 0; var5 < this.boundingBox.getZSize(); var5 += 4)
/* 275:    */       {
/* 276:339 */         var5 += par3Random.nextInt(this.boundingBox.getZSize());
/* 277:341 */         if (var5 + 3 > this.boundingBox.getZSize()) {
/* 278:    */           break;
/* 279:    */         }
/* 280:346 */         StructureComponent var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ + var5, 3, var4);
/* 281:348 */         if (var7 != null)
/* 282:    */         {
/* 283:350 */           StructureBoundingBox var8 = var7.getBoundingBox();
/* 284:351 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
/* 285:    */         }
/* 286:    */       }
/* 287:    */     }
/* 288:    */     
/* 289:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 290:    */     {
/* 291:358 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 292:360 */         return false;
/* 293:    */       }
/* 294:364 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt, Blocks.air, true);
/* 295:365 */       func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
/* 296:366 */       Iterator var4 = this.roomsLinkedToTheRoom.iterator();
/* 297:368 */       while (var4.hasNext())
/* 298:    */       {
/* 299:370 */         StructureBoundingBox var5 = (StructureBoundingBox)var4.next();
/* 300:371 */         func_151549_a(par1World, par3StructureBoundingBox, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, Blocks.air, Blocks.air, false);
/* 301:    */       }
/* 302:374 */       func_151547_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, false);
/* 303:375 */       return true;
/* 304:    */     }
/* 305:    */     
/* 306:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 307:    */     {
/* 308:381 */       NBTTagList var2 = new NBTTagList();
/* 309:382 */       Iterator var3 = this.roomsLinkedToTheRoom.iterator();
/* 310:384 */       while (var3.hasNext())
/* 311:    */       {
/* 312:386 */         StructureBoundingBox var4 = (StructureBoundingBox)var3.next();
/* 313:387 */         var2.appendTag(var4.func_151535_h());
/* 314:    */       }
/* 315:390 */       par1NBTTagCompound.setTag("Entrances", var2);
/* 316:    */     }
/* 317:    */     
/* 318:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 319:    */     {
/* 320:395 */       NBTTagList var2 = par1NBTTagCompound.getTagList("Entrances", 11);
/* 321:397 */       for (int var3 = 0; var3 < var2.tagCount(); var3++) {
/* 322:399 */         this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.func_150306_c(var3)));
/* 323:    */       }
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public static class Corridor
/* 328:    */     extends StructureComponent
/* 329:    */   {
/* 330:    */     private boolean hasRails;
/* 331:    */     private boolean hasSpiders;
/* 332:    */     private boolean spawnerPlaced;
/* 333:    */     private int sectionCount;
/* 334:    */     private static final String __OBFID = "CL_00000445";
/* 335:    */     
/* 336:    */     public Corridor() {}
/* 337:    */     
/* 338:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 339:    */     {
/* 340:416 */       par1NBTTagCompound.setBoolean("hr", this.hasRails);
/* 341:417 */       par1NBTTagCompound.setBoolean("sc", this.hasSpiders);
/* 342:418 */       par1NBTTagCompound.setBoolean("hps", this.spawnerPlaced);
/* 343:419 */       par1NBTTagCompound.setInteger("Num", this.sectionCount);
/* 344:    */     }
/* 345:    */     
/* 346:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 347:    */     {
/* 348:424 */       this.hasRails = par1NBTTagCompound.getBoolean("hr");
/* 349:425 */       this.hasSpiders = par1NBTTagCompound.getBoolean("sc");
/* 350:426 */       this.spawnerPlaced = par1NBTTagCompound.getBoolean("hps");
/* 351:427 */       this.sectionCount = par1NBTTagCompound.getInteger("Num");
/* 352:    */     }
/* 353:    */     
/* 354:    */     public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 355:    */     {
/* 356:432 */       super();
/* 357:433 */       this.coordBaseMode = par4;
/* 358:434 */       this.boundingBox = par3StructureBoundingBox;
/* 359:435 */       this.hasRails = (par2Random.nextInt(3) == 0);
/* 360:436 */       this.hasSpiders = ((!this.hasRails) && (par2Random.nextInt(23) == 0));
/* 361:438 */       if ((this.coordBaseMode != 2) && (this.coordBaseMode != 0)) {
/* 362:440 */         this.sectionCount = (par3StructureBoundingBox.getXSize() / 5);
/* 363:    */       } else {
/* 364:444 */         this.sectionCount = (par3StructureBoundingBox.getZSize() / 5);
/* 365:    */       }
/* 366:    */     }
/* 367:    */     
/* 368:    */     public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
/* 369:    */     {
/* 370:450 */       StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
/* 371:453 */       for (int var7 = par1Random.nextInt(3) + 2; var7 > 0; var7--)
/* 372:    */       {
/* 373:455 */         int var8 = var7 * 5;
/* 374:457 */         switch (par5)
/* 375:    */         {
/* 376:    */         case 0: 
/* 377:460 */           var6.maxX = (par2 + 2);
/* 378:461 */           var6.maxZ = (par4 + (var8 - 1));
/* 379:462 */           break;
/* 380:    */         case 1: 
/* 381:465 */           var6.minX = (par2 - (var8 - 1));
/* 382:466 */           var6.maxZ = (par4 + 2);
/* 383:467 */           break;
/* 384:    */         case 2: 
/* 385:470 */           var6.maxX = (par2 + 2);
/* 386:471 */           var6.minZ = (par4 - (var8 - 1));
/* 387:472 */           break;
/* 388:    */         case 3: 
/* 389:475 */           var6.maxX = (par2 + (var8 - 1));
/* 390:476 */           var6.maxZ = (par4 + 2);
/* 391:    */         }
/* 392:479 */         if (StructureComponent.findIntersecting(par0List, var6) == null) {
/* 393:    */           break;
/* 394:    */         }
/* 395:    */       }
/* 396:485 */       return var7 > 0 ? var6 : null;
/* 397:    */     }
/* 398:    */     
/* 399:    */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 400:    */     {
/* 401:490 */       int var4 = getComponentType();
/* 402:491 */       int var5 = par3Random.nextInt(4);
/* 403:493 */       switch (this.coordBaseMode)
/* 404:    */       {
/* 405:    */       case 0: 
/* 406:496 */         if (var5 <= 1) {
/* 407:498 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
/* 408:500 */         } else if (var5 == 2) {
/* 409:502 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ - 3, 1, var4);
/* 410:    */         } else {
/* 411:506 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ - 3, 3, var4);
/* 412:    */         }
/* 413:509 */         break;
/* 414:    */       case 1: 
/* 415:512 */         if (var5 <= 1) {
/* 416:514 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
/* 417:516 */         } else if (var5 == 2) {
/* 418:518 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
/* 419:    */         } else {
/* 420:522 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
/* 421:    */         }
/* 422:525 */         break;
/* 423:    */       case 2: 
/* 424:528 */         if (var5 <= 1) {
/* 425:530 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
/* 426:532 */         } else if (var5 == 2) {
/* 427:534 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, 1, var4);
/* 428:    */         } else {
/* 429:538 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, 3, var4);
/* 430:    */         }
/* 431:541 */         break;
/* 432:    */       case 3: 
/* 433:544 */         if (var5 <= 1) {
/* 434:546 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
/* 435:548 */         } else if (var5 == 2) {
/* 436:550 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
/* 437:    */         } else {
/* 438:554 */           StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
/* 439:    */         }
/* 440:    */         break;
/* 441:    */       }
/* 442:558 */       if (var4 < 8) {
/* 443:563 */         if ((this.coordBaseMode != 2) && (this.coordBaseMode != 0)) {
/* 444:565 */           for (int var6 = this.boundingBox.minX + 3; var6 + 3 <= this.boundingBox.maxX; var6 += 5)
/* 445:    */           {
/* 446:567 */             int var7 = par3Random.nextInt(5);
/* 447:569 */             if (var7 == 0) {
/* 448:571 */               StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, var6, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4 + 1);
/* 449:573 */             } else if (var7 == 1) {
/* 450:575 */               StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, var6, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4 + 1);
/* 451:    */             }
/* 452:    */           }
/* 453:    */         } else {
/* 454:581 */           for (int var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5)
/* 455:    */           {
/* 456:583 */             int var7 = par3Random.nextInt(5);
/* 457:585 */             if (var7 == 0) {
/* 458:587 */               StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, var6, 1, var4 + 1);
/* 459:589 */             } else if (var7 == 1) {
/* 460:591 */               StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, var6, 3, var4 + 1);
/* 461:    */             }
/* 462:    */           }
/* 463:    */         }
/* 464:    */       }
/* 465:    */     }
/* 466:    */     
/* 467:    */     protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8)
/* 468:    */     {
/* 469:600 */       int var9 = getXWithOffset(par4, par6);
/* 470:601 */       int var10 = getYWithOffset(par5);
/* 471:602 */       int var11 = getZWithOffset(par4, par6);
/* 472:604 */       if ((par2StructureBoundingBox.isVecInside(var9, var10, var11)) && (par1World.getBlock(var9, var10, var11).getMaterial() == Material.air))
/* 473:    */       {
/* 474:606 */         int var12 = par3Random.nextBoolean() ? 1 : 0;
/* 475:607 */         par1World.setBlock(var9, var10, var11, Blocks.rail, func_151555_a(Blocks.rail, var12), 2);
/* 476:608 */         EntityMinecartChest var13 = new EntityMinecartChest(par1World, var9 + 0.5F, var10 + 0.5F, var11 + 0.5F);
/* 477:609 */         WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var13, par8);
/* 478:610 */         par1World.spawnEntityInWorld(var13);
/* 479:611 */         return true;
/* 480:    */       }
/* 481:615 */       return false;
/* 482:    */     }
/* 483:    */     
/* 484:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 485:    */     {
/* 486:621 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 487:623 */         return false;
/* 488:    */       }
/* 489:627 */       boolean var4 = false;
/* 490:628 */       boolean var5 = true;
/* 491:629 */       boolean var6 = false;
/* 492:630 */       boolean var7 = true;
/* 493:631 */       int var8 = this.sectionCount * 5 - 1;
/* 494:632 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, var8, Blocks.air, Blocks.air, false);
/* 495:633 */       func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.air, Blocks.air, false);
/* 496:635 */       if (this.hasSpiders) {
/* 497:637 */         func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.6F, 0, 0, 0, 2, 1, var8, Blocks.web, Blocks.air, false);
/* 498:    */       }
/* 499:643 */       for (int var9 = 0; var9 < this.sectionCount; var9++)
/* 500:    */       {
/* 501:645 */         int var10 = 2 + var9 * 5;
/* 502:646 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 0, var10, 0, 1, var10, Blocks.fence, Blocks.air, false);
/* 503:647 */         func_151549_a(par1World, par3StructureBoundingBox, 2, 0, var10, 2, 1, var10, Blocks.fence, Blocks.air, false);
/* 504:649 */         if (par2Random.nextInt(4) == 0)
/* 505:    */         {
/* 506:651 */           func_151549_a(par1World, par3StructureBoundingBox, 0, 2, var10, 0, 2, var10, Blocks.planks, Blocks.air, false);
/* 507:652 */           func_151549_a(par1World, par3StructureBoundingBox, 2, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
/* 508:    */         }
/* 509:    */         else
/* 510:    */         {
/* 511:656 */           func_151549_a(par1World, par3StructureBoundingBox, 0, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
/* 512:    */         }
/* 513:659 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 - 1, Blocks.web, 0);
/* 514:660 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 - 1, Blocks.web, 0);
/* 515:661 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 + 1, Blocks.web, 0);
/* 516:662 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 + 1, Blocks.web, 0);
/* 517:663 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 - 2, Blocks.web, 0);
/* 518:664 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 - 2, Blocks.web, 0);
/* 519:665 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 + 2, Blocks.web, 0);
/* 520:666 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 + 2, Blocks.web, 0);
/* 521:667 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 - 1, Blocks.torch, 0);
/* 522:668 */         func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 + 1, Blocks.torch, 0);
/* 523:670 */         if (par2Random.nextInt(100) == 0) {
/* 524:672 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 3 + par2Random.nextInt(4));
/* 525:    */         }
/* 526:675 */         if (par2Random.nextInt(100) == 0) {
/* 527:677 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 3 + par2Random.nextInt(4));
/* 528:    */         }
/* 529:680 */         if ((this.hasSpiders) && (!this.spawnerPlaced))
/* 530:    */         {
/* 531:682 */           int var11 = getYWithOffset(0);
/* 532:683 */           int var12 = var10 - 1 + par2Random.nextInt(3);
/* 533:684 */           int var13 = getXWithOffset(1, var12);
/* 534:685 */           var12 = getZWithOffset(1, var12);
/* 535:687 */           if (par3StructureBoundingBox.isVecInside(var13, var11, var12))
/* 536:    */           {
/* 537:689 */             this.spawnerPlaced = true;
/* 538:690 */             par1World.setBlock(var13, var11, var12, Blocks.mob_spawner, 0, 2);
/* 539:691 */             TileEntityMobSpawner var14 = (TileEntityMobSpawner)par1World.getTileEntity(var13, var11, var12);
/* 540:693 */             if (var14 != null) {
/* 541:695 */               var14.func_145881_a().setMobID("CaveSpider");
/* 542:    */             }
/* 543:    */           }
/* 544:    */         }
/* 545:    */       }
/* 546:701 */       for (var9 = 0; var9 <= 2; var9++) {
/* 547:703 */         for (int var10 = 0; var10 <= var8; var10++)
/* 548:    */         {
/* 549:705 */           byte var16 = -1;
/* 550:706 */           Block var17 = func_151548_a(par1World, var9, var16, var10, par3StructureBoundingBox);
/* 551:708 */           if (var17.getMaterial() == Material.air)
/* 552:    */           {
/* 553:710 */             byte var18 = -1;
/* 554:711 */             func_151550_a(par1World, Blocks.planks, 0, var9, var18, var10, par3StructureBoundingBox);
/* 555:    */           }
/* 556:    */         }
/* 557:    */       }
/* 558:716 */       if (this.hasRails) {
/* 559:718 */         for (var9 = 0; var9 <= var8; var9++)
/* 560:    */         {
/* 561:720 */           Block var15 = func_151548_a(par1World, 1, -1, var9, par3StructureBoundingBox);
/* 562:722 */           if ((var15.getMaterial() != Material.air) && (var15.func_149730_j())) {
/* 563:724 */             func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, var9, Blocks.rail, func_151555_a(Blocks.rail, 0));
/* 564:    */           }
/* 565:    */         }
/* 566:    */       }
/* 567:729 */       return true;
/* 568:    */     }
/* 569:    */   }
/* 570:    */   
/* 571:    */   public static class Stairs
/* 572:    */     extends StructureComponent
/* 573:    */   {
/* 574:    */     private static final String __OBFID = "CL_00000449";
/* 575:    */     
/* 576:    */     public Stairs() {}
/* 577:    */     
/* 578:    */     public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 579:    */     {
/* 580:742 */       super();
/* 581:743 */       this.coordBaseMode = par4;
/* 582:744 */       this.boundingBox = par3StructureBoundingBox;
/* 583:    */     }
/* 584:    */     
/* 585:    */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {}
/* 586:    */     
/* 587:    */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {}
/* 588:    */     
/* 589:    */     public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
/* 590:    */     {
/* 591:753 */       StructureBoundingBox var6 = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);
/* 592:755 */       switch (par5)
/* 593:    */       {
/* 594:    */       case 0: 
/* 595:758 */         var6.maxX = (par2 + 2);
/* 596:759 */         var6.maxZ = (par4 + 8);
/* 597:760 */         break;
/* 598:    */       case 1: 
/* 599:763 */         var6.minX = (par2 - 8);
/* 600:764 */         var6.maxZ = (par4 + 2);
/* 601:765 */         break;
/* 602:    */       case 2: 
/* 603:768 */         var6.maxX = (par2 + 2);
/* 604:769 */         var6.minZ = (par4 - 8);
/* 605:770 */         break;
/* 606:    */       case 3: 
/* 607:773 */         var6.maxX = (par2 + 8);
/* 608:774 */         var6.maxZ = (par4 + 2);
/* 609:    */       }
/* 610:777 */       return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
/* 611:    */     }
/* 612:    */     
/* 613:    */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 614:    */     {
/* 615:782 */       int var4 = getComponentType();
/* 616:784 */       switch (this.coordBaseMode)
/* 617:    */       {
/* 618:    */       case 0: 
/* 619:787 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
/* 620:788 */         break;
/* 621:    */       case 1: 
/* 622:791 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, var4);
/* 623:792 */         break;
/* 624:    */       case 2: 
/* 625:795 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
/* 626:796 */         break;
/* 627:    */       case 3: 
/* 628:799 */         StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, var4);
/* 629:    */       }
/* 630:    */     }
/* 631:    */     
/* 632:    */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 633:    */     {
/* 634:805 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 635:807 */         return false;
/* 636:    */       }
/* 637:811 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air, Blocks.air, false);
/* 638:812 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air, Blocks.air, false);
/* 639:814 */       for (int var4 = 0; var4 < 5; var4++) {
/* 640:816 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air, Blocks.air, false);
/* 641:    */       }
/* 642:819 */       return true;
/* 643:    */     }
/* 644:    */   }
/* 645:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureMineshaftPieces
 * JD-Core Version:    0.7.0.1
 */