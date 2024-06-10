/*    1:     */ package net.minecraft.world.gen.structure;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Random;
/*    7:     */ import net.minecraft.block.Block;
/*    8:     */ import net.minecraft.block.material.Material;
/*    9:     */ import net.minecraft.entity.passive.EntityVillager;
/*   10:     */ import net.minecraft.init.Blocks;
/*   11:     */ import net.minecraft.init.Items;
/*   12:     */ import net.minecraft.item.Item;
/*   13:     */ import net.minecraft.nbt.NBTTagCompound;
/*   14:     */ import net.minecraft.util.MathHelper;
/*   15:     */ import net.minecraft.util.RegistryNamespaced;
/*   16:     */ import net.minecraft.util.WeightedRandomChestContent;
/*   17:     */ import net.minecraft.world.World;
/*   18:     */ import net.minecraft.world.WorldProvider;
/*   19:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   20:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   21:     */ 
/*   22:     */ public class StructureVillagePieces
/*   23:     */ {
/*   24:     */   private static final String __OBFID = "CL_00000516";
/*   25:     */   
/*   26:     */   public static void func_143016_a()
/*   27:     */   {
/*   28:  26 */     MapGenStructureIO.func_143031_a(House1.class, "ViBH");
/*   29:  27 */     MapGenStructureIO.func_143031_a(Field1.class, "ViDF");
/*   30:  28 */     MapGenStructureIO.func_143031_a(Field2.class, "ViF");
/*   31:  29 */     MapGenStructureIO.func_143031_a(Torch.class, "ViL");
/*   32:  30 */     MapGenStructureIO.func_143031_a(Hall.class, "ViPH");
/*   33:  31 */     MapGenStructureIO.func_143031_a(House4Garden.class, "ViSH");
/*   34:  32 */     MapGenStructureIO.func_143031_a(WoodHut.class, "ViSmH");
/*   35:  33 */     MapGenStructureIO.func_143031_a(Church.class, "ViST");
/*   36:  34 */     MapGenStructureIO.func_143031_a(House2.class, "ViS");
/*   37:  35 */     MapGenStructureIO.func_143031_a(Start.class, "ViStart");
/*   38:  36 */     MapGenStructureIO.func_143031_a(Path.class, "ViSR");
/*   39:  37 */     MapGenStructureIO.func_143031_a(House3.class, "ViTRH");
/*   40:  38 */     MapGenStructureIO.func_143031_a(Well.class, "ViW");
/*   41:     */   }
/*   42:     */   
/*   43:     */   public static List getStructureVillageWeightedPieceList(Random par0Random, int par1)
/*   44:     */   {
/*   45:  43 */     ArrayList var2 = new ArrayList();
/*   46:  44 */     var2.add(new PieceWeight(House4Garden.class, 4, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
/*   47:  45 */     var2.add(new PieceWeight(Church.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 1 + par1)));
/*   48:  46 */     var2.add(new PieceWeight(House1.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
/*   49:  47 */     var2.add(new PieceWeight(WoodHut.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 5 + par1 * 3)));
/*   50:  48 */     var2.add(new PieceWeight(Hall.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
/*   51:  49 */     var2.add(new PieceWeight(Field1.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 4 + par1)));
/*   52:  50 */     var2.add(new PieceWeight(Field2.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
/*   53:  51 */     var2.add(new PieceWeight(House2.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0, 1 + par1)));
/*   54:  52 */     var2.add(new PieceWeight(House3.class, 8, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 3 + par1 * 2)));
/*   55:  53 */     Iterator var3 = var2.iterator();
/*   56:  55 */     while (var3.hasNext()) {
/*   57:  57 */       if (((PieceWeight)var3.next()).villagePiecesLimit == 0) {
/*   58:  59 */         var3.remove();
/*   59:     */       }
/*   60:     */     }
/*   61:  63 */     return var2;
/*   62:     */   }
/*   63:     */   
/*   64:     */   private static int func_75079_a(List par0List)
/*   65:     */   {
/*   66:  68 */     boolean var1 = false;
/*   67:  69 */     int var2 = 0;
/*   68:     */     PieceWeight var4;
/*   69:  72 */     for (Iterator var3 = par0List.iterator(); var3.hasNext(); var2 += var4.villagePieceWeight)
/*   70:     */     {
/*   71:  74 */       var4 = (PieceWeight)var3.next();
/*   72:  76 */       if ((var4.villagePiecesLimit > 0) && (var4.villagePiecesSpawned < var4.villagePiecesLimit)) {
/*   73:  78 */         var1 = true;
/*   74:     */       }
/*   75:     */     }
/*   76:  82 */     return var1 ? var2 : -1;
/*   77:     */   }
/*   78:     */   
/*   79:     */   private static Village func_75083_a(Start par0ComponentVillageStartPiece, PieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8)
/*   80:     */   {
/*   81:  87 */     Class var9 = par1StructureVillagePieceWeight.villagePieceClass;
/*   82:  88 */     Object var10 = null;
/*   83:  90 */     if (var9 == House4Garden.class) {
/*   84:  92 */       var10 = House4Garden.func_74912_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   85:  94 */     } else if (var9 == Church.class) {
/*   86:  96 */       var10 = Church.func_74919_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   87:  98 */     } else if (var9 == House1.class) {
/*   88: 100 */       var10 = House1.func_74898_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   89: 102 */     } else if (var9 == WoodHut.class) {
/*   90: 104 */       var10 = WoodHut.func_74908_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   91: 106 */     } else if (var9 == Hall.class) {
/*   92: 108 */       var10 = Hall.func_74906_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   93: 110 */     } else if (var9 == Field1.class) {
/*   94: 112 */       var10 = Field1.func_74900_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   95: 114 */     } else if (var9 == Field2.class) {
/*   96: 116 */       var10 = Field2.func_74902_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   97: 118 */     } else if (var9 == House2.class) {
/*   98: 120 */       var10 = House2.func_74915_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*   99: 122 */     } else if (var9 == House3.class) {
/*  100: 124 */       var10 = House3.func_74921_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
/*  101:     */     }
/*  102: 127 */     return (Village)var10;
/*  103:     */   }
/*  104:     */   
/*  105:     */   private static Village getNextVillageComponent(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  106:     */   {
/*  107: 135 */     int var8 = func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);
/*  108: 137 */     if (var8 <= 0) {
/*  109: 139 */       return null;
/*  110:     */     }
/*  111: 143 */     int var9 = 0;
/*  112:     */     Iterator var11;
/*  113:     */     label183:
/*  114: 145 */     for (; var9 < 5; var11.hasNext())
/*  115:     */     {
/*  116: 147 */       var9++;
/*  117: 148 */       int var10 = par2Random.nextInt(var8);
/*  118: 149 */       var11 = par0ComponentVillageStartPiece.structureVillageWeightedPieceList.iterator();
/*  119:     */       
/*  120: 151 */       continue;
/*  121:     */       
/*  122: 153 */       PieceWeight var12 = (PieceWeight)var11.next();
/*  123: 154 */       var10 -= var12.villagePieceWeight;
/*  124: 156 */       if (var10 < 0)
/*  125:     */       {
/*  126: 158 */         if ((!var12.canSpawnMoreVillagePiecesOfType(par7)) || ((var12 == par0ComponentVillageStartPiece.structVillagePieceWeight) && (par0ComponentVillageStartPiece.structureVillageWeightedPieceList.size() > 1))) {
/*  127:     */           break label183;
/*  128:     */         }
/*  129: 163 */         Village var13 = func_75083_a(par0ComponentVillageStartPiece, var12, par1List, par2Random, par3, par4, par5, par6, par7);
/*  130: 165 */         if (var13 != null)
/*  131:     */         {
/*  132: 167 */           var12.villagePiecesSpawned += 1;
/*  133: 168 */           par0ComponentVillageStartPiece.structVillagePieceWeight = var12;
/*  134: 170 */           if (!var12.canSpawnMoreVillagePieces()) {
/*  135: 172 */             par0ComponentVillageStartPiece.structureVillageWeightedPieceList.remove(var12);
/*  136:     */           }
/*  137: 175 */           return var13;
/*  138:     */         }
/*  139:     */       }
/*  140:     */     }
/*  141: 181 */     StructureBoundingBox var14 = Torch.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
/*  142: 183 */     if (var14 != null) {
/*  143: 185 */       return new Torch(par0ComponentVillageStartPiece, par7, par2Random, var14, par6);
/*  144:     */     }
/*  145: 189 */     return null;
/*  146:     */   }
/*  147:     */   
/*  148:     */   private static StructureComponent getNextVillageStructureComponent(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  149:     */   {
/*  150: 199 */     if (par7 > 50) {
/*  151: 201 */       return null;
/*  152:     */     }
/*  153: 203 */     if ((Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112) && (Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112))
/*  154:     */     {
/*  155: 205 */       Village var8 = getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
/*  156: 207 */       if (var8 != null)
/*  157:     */       {
/*  158: 209 */         int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
/*  159: 210 */         int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
/*  160: 211 */         int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
/*  161: 212 */         int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
/*  162: 213 */         int var13 = var11 > var12 ? var11 : var12;
/*  163: 215 */         if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes))
/*  164:     */         {
/*  165: 217 */           par1List.add(var8);
/*  166: 218 */           par0ComponentVillageStartPiece.field_74932_i.add(var8);
/*  167: 219 */           return var8;
/*  168:     */         }
/*  169:     */       }
/*  170: 223 */       return null;
/*  171:     */     }
/*  172: 227 */     return null;
/*  173:     */   }
/*  174:     */   
/*  175:     */   private static StructureComponent getNextComponentVillagePath(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  176:     */   {
/*  177: 233 */     if (par7 > 3 + par0ComponentVillageStartPiece.terrainType) {
/*  178: 235 */       return null;
/*  179:     */     }
/*  180: 237 */     if ((Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112) && (Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112))
/*  181:     */     {
/*  182: 239 */       StructureBoundingBox var8 = Path.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
/*  183: 241 */       if ((var8 != null) && (var8.minY > 10))
/*  184:     */       {
/*  185: 243 */         Path var9 = new Path(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
/*  186: 244 */         int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
/*  187: 245 */         int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
/*  188: 246 */         int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
/*  189: 247 */         int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
/*  190: 248 */         int var14 = var12 > var13 ? var12 : var13;
/*  191: 250 */         if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes))
/*  192:     */         {
/*  193: 252 */           par1List.add(var9);
/*  194: 253 */           par0ComponentVillageStartPiece.field_74930_j.add(var9);
/*  195: 254 */           return var9;
/*  196:     */         }
/*  197:     */       }
/*  198: 258 */       return null;
/*  199:     */     }
/*  200: 262 */     return null;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public static class Well
/*  204:     */     extends StructureVillagePieces.Village
/*  205:     */   {
/*  206:     */     private static final String __OBFID = "CL_00000533";
/*  207:     */     
/*  208:     */     public Well() {}
/*  209:     */     
/*  210:     */     public Well(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, int par4, int par5)
/*  211:     */     {
/*  212: 274 */       super(par2);
/*  213: 275 */       this.coordBaseMode = par3Random.nextInt(4);
/*  214: 277 */       switch (this.coordBaseMode)
/*  215:     */       {
/*  216:     */       case 0: 
/*  217:     */       case 2: 
/*  218: 281 */         this.boundingBox = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
/*  219: 282 */         break;
/*  220:     */       case 1: 
/*  221:     */       default: 
/*  222: 285 */         this.boundingBox = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
/*  223:     */       }
/*  224:     */     }
/*  225:     */     
/*  226:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  227:     */     {
/*  228: 291 */       StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, getComponentType());
/*  229: 292 */       StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, getComponentType());
/*  230: 293 */       StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, getComponentType());
/*  231: 294 */       StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, getComponentType());
/*  232:     */     }
/*  233:     */     
/*  234:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  235:     */     {
/*  236: 299 */       if (this.field_143015_k < 0)
/*  237:     */       {
/*  238: 301 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/*  239: 303 */         if (this.field_143015_k < 0) {
/*  240: 305 */           return true;
/*  241:     */         }
/*  242: 308 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
/*  243:     */       }
/*  244: 311 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.cobblestone, Blocks.flowing_water, false);
/*  245: 312 */       func_151550_a(par1World, Blocks.air, 0, 2, 12, 2, par3StructureBoundingBox);
/*  246: 313 */       func_151550_a(par1World, Blocks.air, 0, 3, 12, 2, par3StructureBoundingBox);
/*  247: 314 */       func_151550_a(par1World, Blocks.air, 0, 2, 12, 3, par3StructureBoundingBox);
/*  248: 315 */       func_151550_a(par1World, Blocks.air, 0, 3, 12, 3, par3StructureBoundingBox);
/*  249: 316 */       func_151550_a(par1World, Blocks.fence, 0, 1, 13, 1, par3StructureBoundingBox);
/*  250: 317 */       func_151550_a(par1World, Blocks.fence, 0, 1, 14, 1, par3StructureBoundingBox);
/*  251: 318 */       func_151550_a(par1World, Blocks.fence, 0, 4, 13, 1, par3StructureBoundingBox);
/*  252: 319 */       func_151550_a(par1World, Blocks.fence, 0, 4, 14, 1, par3StructureBoundingBox);
/*  253: 320 */       func_151550_a(par1World, Blocks.fence, 0, 1, 13, 4, par3StructureBoundingBox);
/*  254: 321 */       func_151550_a(par1World, Blocks.fence, 0, 1, 14, 4, par3StructureBoundingBox);
/*  255: 322 */       func_151550_a(par1World, Blocks.fence, 0, 4, 13, 4, par3StructureBoundingBox);
/*  256: 323 */       func_151550_a(par1World, Blocks.fence, 0, 4, 14, 4, par3StructureBoundingBox);
/*  257: 324 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  258: 326 */       for (int var4 = 0; var4 <= 5; var4++) {
/*  259: 328 */         for (int var5 = 0; var5 <= 5; var5++) {
/*  260: 330 */           if ((var5 == 0) || (var5 == 5) || (var4 == 0) || (var4 == 5))
/*  261:     */           {
/*  262: 332 */             func_151550_a(par1World, Blocks.gravel, 0, var5, 11, var4, par3StructureBoundingBox);
/*  263: 333 */             clearCurrentPositionBlocksUpwards(par1World, var5, 12, var4, par3StructureBoundingBox);
/*  264:     */           }
/*  265:     */         }
/*  266:     */       }
/*  267: 338 */       return true;
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   static abstract class Village
/*  272:     */     extends StructureComponent
/*  273:     */   {
/*  274: 344 */     protected int field_143015_k = -1;
/*  275:     */     private int villagersSpawned;
/*  276:     */     private boolean field_143014_b;
/*  277:     */     private static final String __OBFID = "CL_00000531";
/*  278:     */     
/*  279:     */     public Village() {}
/*  280:     */     
/*  281:     */     protected Village(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2)
/*  282:     */     {
/*  283: 353 */       super();
/*  284: 355 */       if (par1ComponentVillageStartPiece != null) {
/*  285: 357 */         this.field_143014_b = par1ComponentVillageStartPiece.inDesert;
/*  286:     */       }
/*  287:     */     }
/*  288:     */     
/*  289:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  290:     */     {
/*  291: 363 */       par1NBTTagCompound.setInteger("HPos", this.field_143015_k);
/*  292: 364 */       par1NBTTagCompound.setInteger("VCount", this.villagersSpawned);
/*  293: 365 */       par1NBTTagCompound.setBoolean("Desert", this.field_143014_b);
/*  294:     */     }
/*  295:     */     
/*  296:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  297:     */     {
/*  298: 370 */       this.field_143015_k = par1NBTTagCompound.getInteger("HPos");
/*  299: 371 */       this.villagersSpawned = par1NBTTagCompound.getInteger("VCount");
/*  300: 372 */       this.field_143014_b = par1NBTTagCompound.getBoolean("Desert");
/*  301:     */     }
/*  302:     */     
/*  303:     */     protected StructureComponent getNextComponentNN(StructureVillagePieces.Start par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5)
/*  304:     */     {
/*  305: 377 */       switch (this.coordBaseMode)
/*  306:     */       {
/*  307:     */       case 0: 
/*  308: 380 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType());
/*  309:     */       case 1: 
/*  310: 383 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType());
/*  311:     */       case 2: 
/*  312: 386 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType());
/*  313:     */       case 3: 
/*  314: 389 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType());
/*  315:     */       }
/*  316: 392 */       return null;
/*  317:     */     }
/*  318:     */     
/*  319:     */     protected StructureComponent getNextComponentPP(StructureVillagePieces.Start par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5)
/*  320:     */     {
/*  321: 398 */       switch (this.coordBaseMode)
/*  322:     */       {
/*  323:     */       case 0: 
/*  324: 401 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType());
/*  325:     */       case 1: 
/*  326: 404 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType());
/*  327:     */       case 2: 
/*  328: 407 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType());
/*  329:     */       case 3: 
/*  330: 410 */         return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType());
/*  331:     */       }
/*  332: 413 */       return null;
/*  333:     */     }
/*  334:     */     
/*  335:     */     protected int getAverageGroundLevel(World par1World, StructureBoundingBox par2StructureBoundingBox)
/*  336:     */     {
/*  337: 419 */       int var3 = 0;
/*  338: 420 */       int var4 = 0;
/*  339: 422 */       for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; var5++) {
/*  340: 424 */         for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; var6++) {
/*  341: 426 */           if (par2StructureBoundingBox.isVecInside(var6, 64, var5))
/*  342:     */           {
/*  343: 428 */             var3 += Math.max(par1World.getTopSolidOrLiquidBlock(var6, var5), par1World.provider.getAverageGroundLevel());
/*  344: 429 */             var4++;
/*  345:     */           }
/*  346:     */         }
/*  347:     */       }
/*  348: 434 */       if (var4 == 0) {
/*  349: 436 */         return -1;
/*  350:     */       }
/*  351: 440 */       return var3 / var4;
/*  352:     */     }
/*  353:     */     
/*  354:     */     protected static boolean canVillageGoDeeper(StructureBoundingBox par0StructureBoundingBox)
/*  355:     */     {
/*  356: 446 */       return (par0StructureBoundingBox != null) && (par0StructureBoundingBox.minY > 10);
/*  357:     */     }
/*  358:     */     
/*  359:     */     protected void spawnVillagers(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6)
/*  360:     */     {
/*  361: 451 */       if (this.villagersSpawned < par6) {
/*  362: 453 */         for (int var7 = this.villagersSpawned; var7 < par6; var7++)
/*  363:     */         {
/*  364: 455 */           int var8 = getXWithOffset(par3 + var7, par5);
/*  365: 456 */           int var9 = getYWithOffset(par4);
/*  366: 457 */           int var10 = getZWithOffset(par3 + var7, par5);
/*  367: 459 */           if (!par2StructureBoundingBox.isVecInside(var8, var9, var10)) {
/*  368:     */             break;
/*  369:     */           }
/*  370: 464 */           this.villagersSpawned += 1;
/*  371: 465 */           EntityVillager var11 = new EntityVillager(par1World, getVillagerType(var7));
/*  372: 466 */           var11.setLocationAndAngles(var8 + 0.5D, var9, var10 + 0.5D, 0.0F, 0.0F);
/*  373: 467 */           par1World.spawnEntityInWorld(var11);
/*  374:     */         }
/*  375:     */       }
/*  376:     */     }
/*  377:     */     
/*  378:     */     protected int getVillagerType(int par1)
/*  379:     */     {
/*  380: 474 */       return 0;
/*  381:     */     }
/*  382:     */     
/*  383:     */     protected Block func_151558_b(Block p_151558_1_, int p_151558_2_)
/*  384:     */     {
/*  385: 479 */       if (this.field_143014_b)
/*  386:     */       {
/*  387: 481 */         if ((p_151558_1_ == Blocks.log) || (p_151558_1_ == Blocks.log2)) {
/*  388: 483 */           return Blocks.sandstone;
/*  389:     */         }
/*  390: 486 */         if (p_151558_1_ == Blocks.cobblestone) {
/*  391: 488 */           return Blocks.sandstone;
/*  392:     */         }
/*  393: 491 */         if (p_151558_1_ == Blocks.planks) {
/*  394: 493 */           return Blocks.sandstone;
/*  395:     */         }
/*  396: 496 */         if (p_151558_1_ == Blocks.oak_stairs) {
/*  397: 498 */           return Blocks.sandstone_stairs;
/*  398:     */         }
/*  399: 501 */         if (p_151558_1_ == Blocks.stone_stairs) {
/*  400: 503 */           return Blocks.sandstone_stairs;
/*  401:     */         }
/*  402: 506 */         if (p_151558_1_ == Blocks.gravel) {
/*  403: 508 */           return Blocks.sandstone;
/*  404:     */         }
/*  405:     */       }
/*  406: 512 */       return p_151558_1_;
/*  407:     */     }
/*  408:     */     
/*  409:     */     protected int func_151557_c(Block p_151557_1_, int p_151557_2_)
/*  410:     */     {
/*  411: 517 */       if (this.field_143014_b)
/*  412:     */       {
/*  413: 519 */         if ((p_151557_1_ == Blocks.log) || (p_151557_1_ == Blocks.log2)) {
/*  414: 521 */           return 0;
/*  415:     */         }
/*  416: 524 */         if (p_151557_1_ == Blocks.cobblestone) {
/*  417: 526 */           return 0;
/*  418:     */         }
/*  419: 529 */         if (p_151557_1_ == Blocks.planks) {
/*  420: 531 */           return 2;
/*  421:     */         }
/*  422:     */       }
/*  423: 535 */       return p_151557_2_;
/*  424:     */     }
/*  425:     */     
/*  426:     */     protected void func_151550_a(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_)
/*  427:     */     {
/*  428: 540 */       Block var8 = func_151558_b(p_151550_2_, p_151550_3_);
/*  429: 541 */       int var9 = func_151557_c(p_151550_2_, p_151550_3_);
/*  430: 542 */       super.func_151550_a(p_151550_1_, var8, var9, p_151550_4_, p_151550_5_, p_151550_6_, p_151550_7_);
/*  431:     */     }
/*  432:     */     
/*  433:     */     protected void func_151549_a(World p_151549_1_, StructureBoundingBox p_151549_2_, int p_151549_3_, int p_151549_4_, int p_151549_5_, int p_151549_6_, int p_151549_7_, int p_151549_8_, Block p_151549_9_, Block p_151549_10_, boolean p_151549_11_)
/*  434:     */     {
/*  435: 547 */       Block var12 = func_151558_b(p_151549_9_, 0);
/*  436: 548 */       int var13 = func_151557_c(p_151549_9_, 0);
/*  437: 549 */       Block var14 = func_151558_b(p_151549_10_, 0);
/*  438: 550 */       int var15 = func_151557_c(p_151549_10_, 0);
/*  439: 551 */       super.func_151556_a(p_151549_1_, p_151549_2_, p_151549_3_, p_151549_4_, p_151549_5_, p_151549_6_, p_151549_7_, p_151549_8_, var12, var13, var14, var15, p_151549_11_);
/*  440:     */     }
/*  441:     */     
/*  442:     */     protected void func_151554_b(World p_151554_1_, Block p_151554_2_, int p_151554_3_, int p_151554_4_, int p_151554_5_, int p_151554_6_, StructureBoundingBox p_151554_7_)
/*  443:     */     {
/*  444: 556 */       Block var8 = func_151558_b(p_151554_2_, p_151554_3_);
/*  445: 557 */       int var9 = func_151557_c(p_151554_2_, p_151554_3_);
/*  446: 558 */       super.func_151554_b(p_151554_1_, var8, var9, p_151554_4_, p_151554_5_, p_151554_6_, p_151554_7_);
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public static class Hall
/*  451:     */     extends StructureVillagePieces.Village
/*  452:     */   {
/*  453:     */     private static final String __OBFID = "CL_00000522";
/*  454:     */     
/*  455:     */     public Hall() {}
/*  456:     */     
/*  457:     */     public Hall(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/*  458:     */     {
/*  459: 570 */       super(par2);
/*  460: 571 */       this.coordBaseMode = par5;
/*  461: 572 */       this.boundingBox = par4StructureBoundingBox;
/*  462:     */     }
/*  463:     */     
/*  464:     */     public static Hall func_74906_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  465:     */     {
/*  466: 577 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 11, par6);
/*  467: 578 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new Hall(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/*  468:     */     }
/*  469:     */     
/*  470:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  471:     */     {
/*  472: 583 */       if (this.field_143015_k < 0)
/*  473:     */       {
/*  474: 585 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/*  475: 587 */         if (this.field_143015_k < 0) {
/*  476: 589 */           return true;
/*  477:     */         }
/*  478: 592 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/*  479:     */       }
/*  480: 595 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
/*  481: 596 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
/*  482: 597 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 6, 8, 0, 10, Blocks.dirt, Blocks.dirt, false);
/*  483: 598 */       func_151550_a(par1World, Blocks.cobblestone, 0, 6, 0, 6, par3StructureBoundingBox);
/*  484: 599 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 2, 1, 10, Blocks.fence, Blocks.fence, false);
/*  485: 600 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 1, 6, 8, 1, 10, Blocks.fence, Blocks.fence, false);
/*  486: 601 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 10, 7, 1, 10, Blocks.fence, Blocks.fence, false);
/*  487: 602 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
/*  488: 603 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  489: 604 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  490: 605 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/*  491: 606 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 5, 7, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  492: 607 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
/*  493: 608 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 3, 5, Blocks.planks, Blocks.planks, false);
/*  494: 609 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
/*  495: 610 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 4, 8, 4, 4, Blocks.planks, Blocks.planks, false);
/*  496: 611 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
/*  497: 612 */       func_151550_a(par1World, Blocks.planks, 0, 0, 4, 2, par3StructureBoundingBox);
/*  498: 613 */       func_151550_a(par1World, Blocks.planks, 0, 0, 4, 3, par3StructureBoundingBox);
/*  499: 614 */       func_151550_a(par1World, Blocks.planks, 0, 8, 4, 2, par3StructureBoundingBox);
/*  500: 615 */       func_151550_a(par1World, Blocks.planks, 0, 8, 4, 3, par3StructureBoundingBox);
/*  501: 616 */       int var4 = func_151555_a(Blocks.oak_stairs, 3);
/*  502: 617 */       int var5 = func_151555_a(Blocks.oak_stairs, 2);
/*  503: 621 */       for (int var6 = -1; var6 <= 2; var6++) {
/*  504: 623 */         for (int var7 = 0; var7 <= 8; var7++)
/*  505:     */         {
/*  506: 625 */           func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 4 + var6, var6, par3StructureBoundingBox);
/*  507: 626 */           func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
/*  508:     */         }
/*  509:     */       }
/*  510: 630 */       func_151550_a(par1World, Blocks.log, 0, 0, 2, 1, par3StructureBoundingBox);
/*  511: 631 */       func_151550_a(par1World, Blocks.log, 0, 0, 2, 4, par3StructureBoundingBox);
/*  512: 632 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 1, par3StructureBoundingBox);
/*  513: 633 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 4, par3StructureBoundingBox);
/*  514: 634 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/*  515: 635 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
/*  516: 636 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
/*  517: 637 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
/*  518: 638 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 5, par3StructureBoundingBox);
/*  519: 639 */       func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 5, par3StructureBoundingBox);
/*  520: 640 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
/*  521: 641 */       func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 5, par3StructureBoundingBox);
/*  522: 642 */       func_151550_a(par1World, Blocks.fence, 0, 2, 1, 3, par3StructureBoundingBox);
/*  523: 643 */       func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 2, 2, 3, par3StructureBoundingBox);
/*  524: 644 */       func_151550_a(par1World, Blocks.planks, 0, 1, 1, 4, par3StructureBoundingBox);
/*  525: 645 */       func_151550_a(par1World, Blocks.oak_stairs, func_151555_a(Blocks.oak_stairs, 3), 2, 1, 4, par3StructureBoundingBox);
/*  526: 646 */       func_151550_a(par1World, Blocks.oak_stairs, func_151555_a(Blocks.oak_stairs, 1), 1, 1, 3, par3StructureBoundingBox);
/*  527: 647 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
/*  528: 648 */       func_151550_a(par1World, Blocks.double_stone_slab, 0, 6, 1, 1, par3StructureBoundingBox);
/*  529: 649 */       func_151550_a(par1World, Blocks.double_stone_slab, 0, 6, 1, 2, par3StructureBoundingBox);
/*  530: 650 */       func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
/*  531: 651 */       func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
/*  532: 652 */       func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
/*  533: 653 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, func_151555_a(Blocks.wooden_door, 1));
/*  534: 655 */       if ((func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/*  535: 657 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
/*  536:     */       }
/*  537: 660 */       func_151550_a(par1World, Blocks.air, 0, 6, 1, 5, par3StructureBoundingBox);
/*  538: 661 */       func_151550_a(par1World, Blocks.air, 0, 6, 2, 5, par3StructureBoundingBox);
/*  539: 662 */       func_151550_a(par1World, Blocks.torch, 0, 6, 3, 4, par3StructureBoundingBox);
/*  540: 663 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 6, 1, 5, func_151555_a(Blocks.wooden_door, 1));
/*  541: 665 */       for (var6 = 0; var6 < 5; var6++) {
/*  542: 667 */         for (int var7 = 0; var7 < 9; var7++)
/*  543:     */         {
/*  544: 669 */           clearCurrentPositionBlocksUpwards(par1World, var7, 7, var6, par3StructureBoundingBox);
/*  545: 670 */           func_151554_b(par1World, Blocks.cobblestone, 0, var7, -1, var6, par3StructureBoundingBox);
/*  546:     */         }
/*  547:     */       }
/*  548: 674 */       spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
/*  549: 675 */       return true;
/*  550:     */     }
/*  551:     */     
/*  552:     */     protected int getVillagerType(int par1)
/*  553:     */     {
/*  554: 680 */       return par1 == 0 ? 4 : 0;
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   public static class House1
/*  559:     */     extends StructureVillagePieces.Village
/*  560:     */   {
/*  561:     */     private static final String __OBFID = "CL_00000517";
/*  562:     */     
/*  563:     */     public House1() {}
/*  564:     */     
/*  565:     */     public House1(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/*  566:     */     {
/*  567: 692 */       super(par2);
/*  568: 693 */       this.coordBaseMode = par5;
/*  569: 694 */       this.boundingBox = par4StructureBoundingBox;
/*  570:     */     }
/*  571:     */     
/*  572:     */     public static House1 func_74898_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  573:     */     {
/*  574: 699 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 6, par6);
/*  575: 700 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new House1(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/*  576:     */     }
/*  577:     */     
/*  578:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  579:     */     {
/*  580: 705 */       if (this.field_143015_k < 0)
/*  581:     */       {
/*  582: 707 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/*  583: 709 */         if (this.field_143015_k < 0) {
/*  584: 711 */           return true;
/*  585:     */         }
/*  586: 714 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
/*  587:     */       }
/*  588: 717 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.air, Blocks.air, false);
/*  589: 718 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  590: 719 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  591: 720 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  592: 721 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.cobblestone, Blocks.cobblestone, false);
/*  593: 722 */       int var4 = func_151555_a(Blocks.oak_stairs, 3);
/*  594: 723 */       int var5 = func_151555_a(Blocks.oak_stairs, 2);
/*  595: 727 */       for (int var6 = -1; var6 <= 2; var6++) {
/*  596: 729 */         for (int var7 = 0; var7 <= 8; var7++)
/*  597:     */         {
/*  598: 731 */           func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 6 + var6, var6, par3StructureBoundingBox);
/*  599: 732 */           func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 6 + var6, 5 - var6, par3StructureBoundingBox);
/*  600:     */         }
/*  601:     */       }
/*  602: 736 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  603: 737 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 5, 8, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  604: 738 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 1, 0, 8, 1, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  605: 739 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/*  606: 740 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/*  607: 741 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  608: 742 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 5, 8, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/*  609: 743 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 0, 8, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/*  610: 744 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 4, 4, Blocks.planks, Blocks.planks, false);
/*  611: 745 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 4, 5, Blocks.planks, Blocks.planks, false);
/*  612: 746 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 1, 8, 4, 4, Blocks.planks, Blocks.planks, false);
/*  613: 747 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 4, 0, Blocks.planks, Blocks.planks, false);
/*  614: 748 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 0, par3StructureBoundingBox);
/*  615: 749 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
/*  616: 750 */       func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 0, par3StructureBoundingBox);
/*  617: 751 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 0, par3StructureBoundingBox);
/*  618: 752 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 3, 0, par3StructureBoundingBox);
/*  619: 753 */       func_151550_a(par1World, Blocks.glass_pane, 0, 6, 3, 0, par3StructureBoundingBox);
/*  620: 754 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/*  621: 755 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
/*  622: 756 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 2, par3StructureBoundingBox);
/*  623: 757 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 3, par3StructureBoundingBox);
/*  624: 758 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
/*  625: 759 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
/*  626: 760 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 3, 2, par3StructureBoundingBox);
/*  627: 761 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 3, 3, par3StructureBoundingBox);
/*  628: 762 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 5, par3StructureBoundingBox);
/*  629: 763 */       func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 5, par3StructureBoundingBox);
/*  630: 764 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 5, par3StructureBoundingBox);
/*  631: 765 */       func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 5, par3StructureBoundingBox);
/*  632: 766 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 7, 4, 1, Blocks.planks, Blocks.planks, false);
/*  633: 767 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 4, 7, 4, 4, Blocks.planks, Blocks.planks, false);
/*  634: 768 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 7, 3, 4, Blocks.bookshelf, Blocks.bookshelf, false);
/*  635: 769 */       func_151550_a(par1World, Blocks.planks, 0, 7, 1, 4, par3StructureBoundingBox);
/*  636: 770 */       func_151550_a(par1World, Blocks.oak_stairs, func_151555_a(Blocks.oak_stairs, 0), 7, 1, 3, par3StructureBoundingBox);
/*  637: 771 */       var6 = func_151555_a(Blocks.oak_stairs, 3);
/*  638: 772 */       func_151550_a(par1World, Blocks.oak_stairs, var6, 6, 1, 4, par3StructureBoundingBox);
/*  639: 773 */       func_151550_a(par1World, Blocks.oak_stairs, var6, 5, 1, 4, par3StructureBoundingBox);
/*  640: 774 */       func_151550_a(par1World, Blocks.oak_stairs, var6, 4, 1, 4, par3StructureBoundingBox);
/*  641: 775 */       func_151550_a(par1World, Blocks.oak_stairs, var6, 3, 1, 4, par3StructureBoundingBox);
/*  642: 776 */       func_151550_a(par1World, Blocks.fence, 0, 6, 1, 3, par3StructureBoundingBox);
/*  643: 777 */       func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 6, 2, 3, par3StructureBoundingBox);
/*  644: 778 */       func_151550_a(par1World, Blocks.fence, 0, 4, 1, 3, par3StructureBoundingBox);
/*  645: 779 */       func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 4, 2, 3, par3StructureBoundingBox);
/*  646: 780 */       func_151550_a(par1World, Blocks.crafting_table, 0, 7, 1, 1, par3StructureBoundingBox);
/*  647: 781 */       func_151550_a(par1World, Blocks.air, 0, 1, 1, 0, par3StructureBoundingBox);
/*  648: 782 */       func_151550_a(par1World, Blocks.air, 0, 1, 2, 0, par3StructureBoundingBox);
/*  649: 783 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, func_151555_a(Blocks.wooden_door, 1));
/*  650: 785 */       if ((func_151548_a(par1World, 1, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 1, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/*  651: 787 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, par3StructureBoundingBox);
/*  652:     */       }
/*  653: 790 */       for (int var7 = 0; var7 < 6; var7++) {
/*  654: 792 */         for (int var8 = 0; var8 < 9; var8++)
/*  655:     */         {
/*  656: 794 */           clearCurrentPositionBlocksUpwards(par1World, var8, 9, var7, par3StructureBoundingBox);
/*  657: 795 */           func_151554_b(par1World, Blocks.cobblestone, 0, var8, -1, var7, par3StructureBoundingBox);
/*  658:     */         }
/*  659:     */       }
/*  660: 799 */       spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
/*  661: 800 */       return true;
/*  662:     */     }
/*  663:     */     
/*  664:     */     protected int getVillagerType(int par1)
/*  665:     */     {
/*  666: 805 */       return 1;
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   public static class Church
/*  671:     */     extends StructureVillagePieces.Village
/*  672:     */   {
/*  673:     */     private static final String __OBFID = "CL_00000525";
/*  674:     */     
/*  675:     */     public Church() {}
/*  676:     */     
/*  677:     */     public Church(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/*  678:     */     {
/*  679: 817 */       super(par2);
/*  680: 818 */       this.coordBaseMode = par5;
/*  681: 819 */       this.boundingBox = par4StructureBoundingBox;
/*  682:     */     }
/*  683:     */     
/*  684:     */     public static Church func_74919_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  685:     */     {
/*  686: 824 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 12, 9, par6);
/*  687: 825 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new Church(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/*  688:     */     }
/*  689:     */     
/*  690:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  691:     */     {
/*  692: 830 */       if (this.field_143015_k < 0)
/*  693:     */       {
/*  694: 832 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/*  695: 834 */         if (this.field_143015_k < 0) {
/*  696: 836 */           return true;
/*  697:     */         }
/*  698: 839 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
/*  699:     */       }
/*  700: 842 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.air, Blocks.air, false);
/*  701: 843 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.air, Blocks.air, false);
/*  702: 844 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.cobblestone, Blocks.cobblestone, false);
/*  703: 845 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/*  704: 846 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
/*  705: 847 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
/*  706: 848 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
/*  707: 849 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
/*  708: 850 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.cobblestone, Blocks.cobblestone, false);
/*  709: 851 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  710: 852 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.cobblestone, Blocks.cobblestone, false);
/*  711: 853 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  712: 854 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  713: 855 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 11, 2, par3StructureBoundingBox);
/*  714: 856 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 11, 2, par3StructureBoundingBox);
/*  715: 857 */       func_151550_a(par1World, Blocks.cobblestone, 0, 2, 11, 0, par3StructureBoundingBox);
/*  716: 858 */       func_151550_a(par1World, Blocks.cobblestone, 0, 2, 11, 4, par3StructureBoundingBox);
/*  717: 859 */       func_151550_a(par1World, Blocks.cobblestone, 0, 1, 1, 6, par3StructureBoundingBox);
/*  718: 860 */       func_151550_a(par1World, Blocks.cobblestone, 0, 1, 1, 7, par3StructureBoundingBox);
/*  719: 861 */       func_151550_a(par1World, Blocks.cobblestone, 0, 2, 1, 7, par3StructureBoundingBox);
/*  720: 862 */       func_151550_a(par1World, Blocks.cobblestone, 0, 3, 1, 6, par3StructureBoundingBox);
/*  721: 863 */       func_151550_a(par1World, Blocks.cobblestone, 0, 3, 1, 7, par3StructureBoundingBox);
/*  722: 864 */       func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 1, 1, 5, par3StructureBoundingBox);
/*  723: 865 */       func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 2, 1, 6, par3StructureBoundingBox);
/*  724: 866 */       func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 3, 1, 5, par3StructureBoundingBox);
/*  725: 867 */       func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 1), 1, 2, 7, par3StructureBoundingBox);
/*  726: 868 */       func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 0), 3, 2, 7, par3StructureBoundingBox);
/*  727: 869 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/*  728: 870 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 2, par3StructureBoundingBox);
/*  729: 871 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 2, par3StructureBoundingBox);
/*  730: 872 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 2, par3StructureBoundingBox);
/*  731: 873 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 6, 2, par3StructureBoundingBox);
/*  732: 874 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 7, 2, par3StructureBoundingBox);
/*  733: 875 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 6, 2, par3StructureBoundingBox);
/*  734: 876 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 7, 2, par3StructureBoundingBox);
/*  735: 877 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 6, 0, par3StructureBoundingBox);
/*  736: 878 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 7, 0, par3StructureBoundingBox);
/*  737: 879 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 6, 4, par3StructureBoundingBox);
/*  738: 880 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 7, 4, par3StructureBoundingBox);
/*  739: 881 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 6, par3StructureBoundingBox);
/*  740: 882 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 6, par3StructureBoundingBox);
/*  741: 883 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 3, 8, par3StructureBoundingBox);
/*  742: 884 */       func_151550_a(par1World, Blocks.torch, 0, 2, 4, 7, par3StructureBoundingBox);
/*  743: 885 */       func_151550_a(par1World, Blocks.torch, 0, 1, 4, 6, par3StructureBoundingBox);
/*  744: 886 */       func_151550_a(par1World, Blocks.torch, 0, 3, 4, 6, par3StructureBoundingBox);
/*  745: 887 */       func_151550_a(par1World, Blocks.torch, 0, 2, 4, 5, par3StructureBoundingBox);
/*  746: 888 */       int var4 = func_151555_a(Blocks.ladder, 4);
/*  747: 891 */       for (int var5 = 1; var5 <= 9; var5++) {
/*  748: 893 */         func_151550_a(par1World, Blocks.ladder, var4, 3, var5, 3, par3StructureBoundingBox);
/*  749:     */       }
/*  750: 896 */       func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
/*  751: 897 */       func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
/*  752: 898 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, func_151555_a(Blocks.wooden_door, 1));
/*  753: 900 */       if ((func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/*  754: 902 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
/*  755:     */       }
/*  756: 905 */       for (var5 = 0; var5 < 9; var5++) {
/*  757: 907 */         for (int var6 = 0; var6 < 5; var6++)
/*  758:     */         {
/*  759: 909 */           clearCurrentPositionBlocksUpwards(par1World, var6, 12, var5, par3StructureBoundingBox);
/*  760: 910 */           func_151554_b(par1World, Blocks.cobblestone, 0, var6, -1, var5, par3StructureBoundingBox);
/*  761:     */         }
/*  762:     */       }
/*  763: 914 */       spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
/*  764: 915 */       return true;
/*  765:     */     }
/*  766:     */     
/*  767:     */     protected int getVillagerType(int par1)
/*  768:     */     {
/*  769: 920 */       return 2;
/*  770:     */     }
/*  771:     */   }
/*  772:     */   
/*  773:     */   public static class House4Garden
/*  774:     */     extends StructureVillagePieces.Village
/*  775:     */   {
/*  776:     */     private boolean isRoofAccessible;
/*  777:     */     private static final String __OBFID = "CL_00000523";
/*  778:     */     
/*  779:     */     public House4Garden() {}
/*  780:     */     
/*  781:     */     public House4Garden(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/*  782:     */     {
/*  783: 933 */       super(par2);
/*  784: 934 */       this.coordBaseMode = par5;
/*  785: 935 */       this.boundingBox = par4StructureBoundingBox;
/*  786: 936 */       this.isRoofAccessible = par3Random.nextBoolean();
/*  787:     */     }
/*  788:     */     
/*  789:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  790:     */     {
/*  791: 941 */       super.func_143012_a(par1NBTTagCompound);
/*  792: 942 */       par1NBTTagCompound.setBoolean("Terrace", this.isRoofAccessible);
/*  793:     */     }
/*  794:     */     
/*  795:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  796:     */     {
/*  797: 947 */       super.func_143011_b(par1NBTTagCompound);
/*  798: 948 */       this.isRoofAccessible = par1NBTTagCompound.getBoolean("Terrace");
/*  799:     */     }
/*  800:     */     
/*  801:     */     public static House4Garden func_74912_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  802:     */     {
/*  803: 953 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 6, 5, par6);
/*  804: 954 */       return StructureComponent.findIntersecting(par1List, var8) != null ? null : new House4Garden(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
/*  805:     */     }
/*  806:     */     
/*  807:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  808:     */     {
/*  809: 959 */       if (this.field_143015_k < 0)
/*  810:     */       {
/*  811: 961 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/*  812: 963 */         if (this.field_143015_k < 0) {
/*  813: 965 */           return true;
/*  814:     */         }
/*  815: 968 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*  816:     */       }
/*  817: 971 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/*  818: 972 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.log, Blocks.log, false);
/*  819: 973 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.planks, Blocks.planks, false);
/*  820: 974 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 1, 0, par3StructureBoundingBox);
/*  821: 975 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 2, 0, par3StructureBoundingBox);
/*  822: 976 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 3, 0, par3StructureBoundingBox);
/*  823: 977 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 0, par3StructureBoundingBox);
/*  824: 978 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 2, 0, par3StructureBoundingBox);
/*  825: 979 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 0, par3StructureBoundingBox);
/*  826: 980 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 1, 4, par3StructureBoundingBox);
/*  827: 981 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 2, 4, par3StructureBoundingBox);
/*  828: 982 */       func_151550_a(par1World, Blocks.cobblestone, 0, 0, 3, 4, par3StructureBoundingBox);
/*  829: 983 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 4, par3StructureBoundingBox);
/*  830: 984 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 2, 4, par3StructureBoundingBox);
/*  831: 985 */       func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 4, par3StructureBoundingBox);
/*  832: 986 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
/*  833: 987 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.planks, Blocks.planks, false);
/*  834: 988 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.planks, Blocks.planks, false);
/*  835: 989 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/*  836: 990 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 4, par3StructureBoundingBox);
/*  837: 991 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 2, par3StructureBoundingBox);
/*  838: 992 */       func_151550_a(par1World, Blocks.planks, 0, 1, 1, 0, par3StructureBoundingBox);
/*  839: 993 */       func_151550_a(par1World, Blocks.planks, 0, 1, 2, 0, par3StructureBoundingBox);
/*  840: 994 */       func_151550_a(par1World, Blocks.planks, 0, 1, 3, 0, par3StructureBoundingBox);
/*  841: 995 */       func_151550_a(par1World, Blocks.planks, 0, 2, 3, 0, par3StructureBoundingBox);
/*  842: 996 */       func_151550_a(par1World, Blocks.planks, 0, 3, 3, 0, par3StructureBoundingBox);
/*  843: 997 */       func_151550_a(par1World, Blocks.planks, 0, 3, 2, 0, par3StructureBoundingBox);
/*  844: 998 */       func_151550_a(par1World, Blocks.planks, 0, 3, 1, 0, par3StructureBoundingBox);
/*  845:1000 */       if ((func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/*  846:1002 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
/*  847:     */       }
/*  848:1005 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);
/*  849:1007 */       if (this.isRoofAccessible)
/*  850:     */       {
/*  851:1009 */         func_151550_a(par1World, Blocks.fence, 0, 0, 5, 0, par3StructureBoundingBox);
/*  852:1010 */         func_151550_a(par1World, Blocks.fence, 0, 1, 5, 0, par3StructureBoundingBox);
/*  853:1011 */         func_151550_a(par1World, Blocks.fence, 0, 2, 5, 0, par3StructureBoundingBox);
/*  854:1012 */         func_151550_a(par1World, Blocks.fence, 0, 3, 5, 0, par3StructureBoundingBox);
/*  855:1013 */         func_151550_a(par1World, Blocks.fence, 0, 4, 5, 0, par3StructureBoundingBox);
/*  856:1014 */         func_151550_a(par1World, Blocks.fence, 0, 0, 5, 4, par3StructureBoundingBox);
/*  857:1015 */         func_151550_a(par1World, Blocks.fence, 0, 1, 5, 4, par3StructureBoundingBox);
/*  858:1016 */         func_151550_a(par1World, Blocks.fence, 0, 2, 5, 4, par3StructureBoundingBox);
/*  859:1017 */         func_151550_a(par1World, Blocks.fence, 0, 3, 5, 4, par3StructureBoundingBox);
/*  860:1018 */         func_151550_a(par1World, Blocks.fence, 0, 4, 5, 4, par3StructureBoundingBox);
/*  861:1019 */         func_151550_a(par1World, Blocks.fence, 0, 4, 5, 1, par3StructureBoundingBox);
/*  862:1020 */         func_151550_a(par1World, Blocks.fence, 0, 4, 5, 2, par3StructureBoundingBox);
/*  863:1021 */         func_151550_a(par1World, Blocks.fence, 0, 4, 5, 3, par3StructureBoundingBox);
/*  864:1022 */         func_151550_a(par1World, Blocks.fence, 0, 0, 5, 1, par3StructureBoundingBox);
/*  865:1023 */         func_151550_a(par1World, Blocks.fence, 0, 0, 5, 2, par3StructureBoundingBox);
/*  866:1024 */         func_151550_a(par1World, Blocks.fence, 0, 0, 5, 3, par3StructureBoundingBox);
/*  867:     */       }
/*  868:1029 */       if (this.isRoofAccessible)
/*  869:     */       {
/*  870:1031 */         int var4 = func_151555_a(Blocks.ladder, 3);
/*  871:1032 */         func_151550_a(par1World, Blocks.ladder, var4, 3, 1, 3, par3StructureBoundingBox);
/*  872:1033 */         func_151550_a(par1World, Blocks.ladder, var4, 3, 2, 3, par3StructureBoundingBox);
/*  873:1034 */         func_151550_a(par1World, Blocks.ladder, var4, 3, 3, 3, par3StructureBoundingBox);
/*  874:1035 */         func_151550_a(par1World, Blocks.ladder, var4, 3, 4, 3, par3StructureBoundingBox);
/*  875:     */       }
/*  876:1038 */       func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
/*  877:1040 */       for (int var4 = 0; var4 < 5; var4++) {
/*  878:1042 */         for (int var5 = 0; var5 < 5; var5++)
/*  879:     */         {
/*  880:1044 */           clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
/*  881:1045 */           func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
/*  882:     */         }
/*  883:     */       }
/*  884:1049 */       spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
/*  885:1050 */       return true;
/*  886:     */     }
/*  887:     */   }
/*  888:     */   
/*  889:     */   public static class Path
/*  890:     */     extends StructureVillagePieces.Road
/*  891:     */   {
/*  892:     */     private int averageGroundLevel;
/*  893:     */     private static final String __OBFID = "CL_00000528";
/*  894:     */     
/*  895:     */     public Path() {}
/*  896:     */     
/*  897:     */     public Path(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/*  898:     */     {
/*  899:1063 */       super(par2);
/*  900:1064 */       this.coordBaseMode = par5;
/*  901:1065 */       this.boundingBox = par4StructureBoundingBox;
/*  902:1066 */       this.averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
/*  903:     */     }
/*  904:     */     
/*  905:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  906:     */     {
/*  907:1071 */       super.func_143012_a(par1NBTTagCompound);
/*  908:1072 */       par1NBTTagCompound.setInteger("Length", this.averageGroundLevel);
/*  909:     */     }
/*  910:     */     
/*  911:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  912:     */     {
/*  913:1077 */       super.func_143011_b(par1NBTTagCompound);
/*  914:1078 */       this.averageGroundLevel = par1NBTTagCompound.getInteger("Length");
/*  915:     */     }
/*  916:     */     
/*  917:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  918:     */     {
/*  919:1083 */       boolean var4 = false;
/*  920:1087 */       for (int var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
/*  921:     */       {
/*  922:1089 */         StructureComponent var6 = getNextComponentNN((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, 0, var5);
/*  923:1091 */         if (var6 != null)
/*  924:     */         {
/*  925:1093 */           var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
/*  926:1094 */           var4 = true;
/*  927:     */         }
/*  928:     */       }
/*  929:1098 */       for (var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
/*  930:     */       {
/*  931:1100 */         StructureComponent var6 = getNextComponentPP((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, 0, var5);
/*  932:1102 */         if (var6 != null)
/*  933:     */         {
/*  934:1104 */           var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
/*  935:1105 */           var4 = true;
/*  936:     */         }
/*  937:     */       }
/*  938:1109 */       if ((var4) && (par3Random.nextInt(3) > 0)) {
/*  939:1111 */         switch (this.coordBaseMode)
/*  940:     */         {
/*  941:     */         case 0: 
/*  942:1114 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, getComponentType());
/*  943:1115 */           break;
/*  944:     */         case 1: 
/*  945:1118 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, getComponentType());
/*  946:1119 */           break;
/*  947:     */         case 2: 
/*  948:1122 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, getComponentType());
/*  949:1123 */           break;
/*  950:     */         case 3: 
/*  951:1126 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, getComponentType());
/*  952:     */         }
/*  953:     */       }
/*  954:1130 */       if ((var4) && (par3Random.nextInt(3) > 0)) {
/*  955:1132 */         switch (this.coordBaseMode)
/*  956:     */         {
/*  957:     */         case 0: 
/*  958:1135 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, getComponentType());
/*  959:1136 */           break;
/*  960:     */         case 1: 
/*  961:1139 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, getComponentType());
/*  962:1140 */           break;
/*  963:     */         case 2: 
/*  964:1143 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, getComponentType());
/*  965:1144 */           break;
/*  966:     */         case 3: 
/*  967:1147 */           StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, getComponentType());
/*  968:     */         }
/*  969:     */       }
/*  970:     */     }
/*  971:     */     
/*  972:     */     public static StructureBoundingBox func_74933_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
/*  973:     */     {
/*  974:1154 */       for (int var7 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5); var7 >= 7; var7 -= 7)
/*  975:     */       {
/*  976:1156 */         StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);
/*  977:1158 */         if (StructureComponent.findIntersecting(par1List, var8) == null) {
/*  978:1160 */           return var8;
/*  979:     */         }
/*  980:     */       }
/*  981:1164 */       return null;
/*  982:     */     }
/*  983:     */     
/*  984:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  985:     */     {
/*  986:1169 */       Block var4 = func_151558_b(Blocks.gravel, 0);
/*  987:1171 */       for (int var5 = this.boundingBox.minX; var5 <= this.boundingBox.maxX; var5++) {
/*  988:1173 */         for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; var6++) {
/*  989:1175 */           if (par3StructureBoundingBox.isVecInside(var5, 64, var6))
/*  990:     */           {
/*  991:1177 */             int var7 = par1World.getTopSolidOrLiquidBlock(var5, var6) - 1;
/*  992:1178 */             par1World.setBlock(var5, var7, var6, var4, 0, 2);
/*  993:     */           }
/*  994:     */         }
/*  995:     */       }
/*  996:1183 */       return true;
/*  997:     */     }
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public static class House2
/* 1001:     */     extends StructureVillagePieces.Village
/* 1002:     */   {
/* 1003:1189 */     private static final WeightedRandomChestContent[] villageBlacksmithChestContents = { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
/* 1004:     */     private boolean hasMadeChest;
/* 1005:     */     private static final String __OBFID = "CL_00000526";
/* 1006:     */     
/* 1007:     */     public House2() {}
/* 1008:     */     
/* 1009:     */     public House2(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1010:     */     {
/* 1011:1197 */       super(par2);
/* 1012:1198 */       this.coordBaseMode = par5;
/* 1013:1199 */       this.boundingBox = par4StructureBoundingBox;
/* 1014:     */     }
/* 1015:     */     
/* 1016:     */     public static House2 func_74915_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/* 1017:     */     {
/* 1018:1204 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 10, 6, 7, par6);
/* 1019:1205 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new House2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/* 1020:     */     }
/* 1021:     */     
/* 1022:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1023:     */     {
/* 1024:1210 */       super.func_143012_a(par1NBTTagCompound);
/* 1025:1211 */       par1NBTTagCompound.setBoolean("Chest", this.hasMadeChest);
/* 1026:     */     }
/* 1027:     */     
/* 1028:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1029:     */     {
/* 1030:1216 */       super.func_143011_b(par1NBTTagCompound);
/* 1031:1217 */       this.hasMadeChest = par1NBTTagCompound.getBoolean("Chest");
/* 1032:     */     }
/* 1033:     */     
/* 1034:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1035:     */     {
/* 1036:1222 */       if (this.field_143015_k < 0)
/* 1037:     */       {
/* 1038:1224 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1039:1226 */         if (this.field_143015_k < 0) {
/* 1040:1228 */           return true;
/* 1041:     */         }
/* 1042:1231 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/* 1043:     */       }
/* 1044:1234 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.air, Blocks.air, false);
/* 1045:1235 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1046:1236 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1047:1237 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1048:1238 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.air, Blocks.air, false);
/* 1049:1239 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
/* 1050:1240 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.log, Blocks.log, false);
/* 1051:1241 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.log, Blocks.log, false);
/* 1052:1242 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.log, Blocks.log, false);
/* 1053:1243 */       func_151550_a(par1World, Blocks.planks, 0, 3, 3, 1, par3StructureBoundingBox);
/* 1054:1244 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.planks, Blocks.planks, false);
/* 1055:1245 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.planks, Blocks.planks, false);
/* 1056:1246 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.planks, Blocks.planks, false);
/* 1057:1247 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.planks, Blocks.planks, false);
/* 1058:1248 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.fence, Blocks.fence, false);
/* 1059:1249 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.fence, Blocks.fence, false);
/* 1060:1250 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1061:1251 */       func_151550_a(par1World, Blocks.flowing_lava, 0, 7, 1, 5, par3StructureBoundingBox);
/* 1062:1252 */       func_151550_a(par1World, Blocks.flowing_lava, 0, 8, 1, 5, par3StructureBoundingBox);
/* 1063:1253 */       func_151550_a(par1World, Blocks.iron_bars, 0, 9, 2, 5, par3StructureBoundingBox);
/* 1064:1254 */       func_151550_a(par1World, Blocks.iron_bars, 0, 9, 2, 4, par3StructureBoundingBox);
/* 1065:1255 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.air, Blocks.air, false);
/* 1066:1256 */       func_151550_a(par1World, Blocks.cobblestone, 0, 6, 1, 3, par3StructureBoundingBox);
/* 1067:1257 */       func_151550_a(par1World, Blocks.furnace, 0, 6, 2, 3, par3StructureBoundingBox);
/* 1068:1258 */       func_151550_a(par1World, Blocks.furnace, 0, 6, 3, 3, par3StructureBoundingBox);
/* 1069:1259 */       func_151550_a(par1World, Blocks.double_stone_slab, 0, 8, 1, 1, par3StructureBoundingBox);
/* 1070:1260 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/* 1071:1261 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 4, par3StructureBoundingBox);
/* 1072:1262 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 6, par3StructureBoundingBox);
/* 1073:1263 */       func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 6, par3StructureBoundingBox);
/* 1074:1264 */       func_151550_a(par1World, Blocks.fence, 0, 2, 1, 4, par3StructureBoundingBox);
/* 1075:1265 */       func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 2, 2, 4, par3StructureBoundingBox);
/* 1076:1266 */       func_151550_a(par1World, Blocks.planks, 0, 1, 1, 5, par3StructureBoundingBox);
/* 1077:1267 */       func_151550_a(par1World, Blocks.oak_stairs, func_151555_a(Blocks.oak_stairs, 3), 2, 1, 5, par3StructureBoundingBox);
/* 1078:1268 */       func_151550_a(par1World, Blocks.oak_stairs, func_151555_a(Blocks.oak_stairs, 1), 1, 1, 4, par3StructureBoundingBox);
/* 1079:1272 */       if (!this.hasMadeChest)
/* 1080:     */       {
/* 1081:1274 */         int var4 = getYWithOffset(1);
/* 1082:1275 */         int var5 = getXWithOffset(5, 5);
/* 1083:1276 */         int var6 = getZWithOffset(5, 5);
/* 1084:1278 */         if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
/* 1085:     */         {
/* 1086:1280 */           this.hasMadeChest = true;
/* 1087:1281 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 5, 1, 5, villageBlacksmithChestContents, 3 + par2Random.nextInt(6));
/* 1088:     */         }
/* 1089:     */       }
/* 1090:1285 */       for (int var4 = 6; var4 <= 8; var4++) {
/* 1091:1287 */         if ((func_151548_a(par1World, var4, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, var4, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/* 1092:1289 */           func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), var4, 0, -1, par3StructureBoundingBox);
/* 1093:     */         }
/* 1094:     */       }
/* 1095:1293 */       for (var4 = 0; var4 < 7; var4++) {
/* 1096:1295 */         for (int var5 = 0; var5 < 10; var5++)
/* 1097:     */         {
/* 1098:1297 */           clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
/* 1099:1298 */           func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
/* 1100:     */         }
/* 1101:     */       }
/* 1102:1302 */       spawnVillagers(par1World, par3StructureBoundingBox, 7, 1, 1, 1);
/* 1103:1303 */       return true;
/* 1104:     */     }
/* 1105:     */     
/* 1106:     */     protected int getVillagerType(int par1)
/* 1107:     */     {
/* 1108:1308 */       return 3;
/* 1109:     */     }
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public static class Start
/* 1113:     */     extends StructureVillagePieces.Well
/* 1114:     */   {
/* 1115:     */     public WorldChunkManager worldChunkMngr;
/* 1116:     */     public boolean inDesert;
/* 1117:     */     public int terrainType;
/* 1118:     */     public StructureVillagePieces.PieceWeight structVillagePieceWeight;
/* 1119:     */     public List structureVillageWeightedPieceList;
/* 1120:1319 */     public List field_74932_i = new ArrayList();
/* 1121:1320 */     public List field_74930_j = new ArrayList();
/* 1122:     */     private static final String __OBFID = "CL_00000527";
/* 1123:     */     
/* 1124:     */     public Start() {}
/* 1125:     */     
/* 1126:     */     public Start(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, List par6List, int par7)
/* 1127:     */     {
/* 1128:1327 */       super(0, par3Random, par4, par5);
/* 1129:1328 */       this.worldChunkMngr = par1WorldChunkManager;
/* 1130:1329 */       this.structureVillageWeightedPieceList = par6List;
/* 1131:1330 */       this.terrainType = par7;
/* 1132:1331 */       BiomeGenBase var8 = par1WorldChunkManager.getBiomeGenAt(par4, par5);
/* 1133:1332 */       this.inDesert = ((var8 == BiomeGenBase.desert) || (var8 == BiomeGenBase.desertHills));
/* 1134:     */     }
/* 1135:     */     
/* 1136:     */     public WorldChunkManager getWorldChunkManager()
/* 1137:     */     {
/* 1138:1337 */       return this.worldChunkMngr;
/* 1139:     */     }
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public static class House3
/* 1143:     */     extends StructureVillagePieces.Village
/* 1144:     */   {
/* 1145:     */     private static final String __OBFID = "CL_00000530";
/* 1146:     */     
/* 1147:     */     public House3() {}
/* 1148:     */     
/* 1149:     */     public House3(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1150:     */     {
/* 1151:1349 */       super(par2);
/* 1152:1350 */       this.coordBaseMode = par5;
/* 1153:1351 */       this.boundingBox = par4StructureBoundingBox;
/* 1154:     */     }
/* 1155:     */     
/* 1156:     */     public static House3 func_74921_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/* 1157:     */     {
/* 1158:1356 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 12, par6);
/* 1159:1357 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new House3(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/* 1160:     */     }
/* 1161:     */     
/* 1162:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1163:     */     {
/* 1164:1362 */       if (this.field_143015_k < 0)
/* 1165:     */       {
/* 1166:1364 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1167:1366 */         if (this.field_143015_k < 0) {
/* 1168:1368 */           return true;
/* 1169:     */         }
/* 1170:1371 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/* 1171:     */       }
/* 1172:1374 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
/* 1173:1375 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
/* 1174:1376 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 5, 8, 0, 10, Blocks.planks, Blocks.planks, false);
/* 1175:1377 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
/* 1176:1378 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1177:1379 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1178:1380 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 2, 0, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1179:1381 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 5, 2, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1180:1382 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 6, 2, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1181:1383 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 10, 7, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1182:1384 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
/* 1183:1385 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 2, 3, 5, Blocks.planks, Blocks.planks, false);
/* 1184:1386 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
/* 1185:1387 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 4, 3, 4, 4, Blocks.planks, Blocks.planks, false);
/* 1186:1388 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
/* 1187:1389 */       func_151550_a(par1World, Blocks.planks, 0, 0, 4, 2, par3StructureBoundingBox);
/* 1188:1390 */       func_151550_a(par1World, Blocks.planks, 0, 0, 4, 3, par3StructureBoundingBox);
/* 1189:1391 */       func_151550_a(par1World, Blocks.planks, 0, 8, 4, 2, par3StructureBoundingBox);
/* 1190:1392 */       func_151550_a(par1World, Blocks.planks, 0, 8, 4, 3, par3StructureBoundingBox);
/* 1191:1393 */       func_151550_a(par1World, Blocks.planks, 0, 8, 4, 4, par3StructureBoundingBox);
/* 1192:1394 */       int var4 = func_151555_a(Blocks.oak_stairs, 3);
/* 1193:1395 */       int var5 = func_151555_a(Blocks.oak_stairs, 2);
/* 1194:1399 */       for (int var6 = -1; var6 <= 2; var6++) {
/* 1195:1401 */         for (int var7 = 0; var7 <= 8; var7++)
/* 1196:     */         {
/* 1197:1403 */           func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 4 + var6, var6, par3StructureBoundingBox);
/* 1198:1405 */           if (((var6 > -1) || (var7 <= 1)) && ((var6 > 0) || (var7 <= 3)) && ((var6 > 1) || (var7 <= 4) || (var7 >= 6))) {
/* 1199:1407 */             func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
/* 1200:     */           }
/* 1201:     */         }
/* 1202:     */       }
/* 1203:1412 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 4, 5, 3, 4, 10, Blocks.planks, Blocks.planks, false);
/* 1204:1413 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 4, 2, 7, 4, 10, Blocks.planks, Blocks.planks, false);
/* 1205:1414 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 4, 4, 5, 10, Blocks.planks, Blocks.planks, false);
/* 1206:1415 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 4, 6, 5, 10, Blocks.planks, Blocks.planks, false);
/* 1207:1416 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 6, 3, 5, 6, 10, Blocks.planks, Blocks.planks, false);
/* 1208:1417 */       var6 = func_151555_a(Blocks.oak_stairs, 0);
/* 1209:1420 */       for (int var7 = 4; var7 >= 1; var7--)
/* 1210:     */       {
/* 1211:1422 */         func_151550_a(par1World, Blocks.planks, 0, var7, 2 + var7, 7 - var7, par3StructureBoundingBox);
/* 1212:1424 */         for (int var8 = 8 - var7; var8 <= 10; var8++) {
/* 1213:1426 */           func_151550_a(par1World, Blocks.oak_stairs, var6, var7, 2 + var7, var8, par3StructureBoundingBox);
/* 1214:     */         }
/* 1215:     */       }
/* 1216:1430 */       var7 = func_151555_a(Blocks.oak_stairs, 1);
/* 1217:1431 */       func_151550_a(par1World, Blocks.planks, 0, 6, 6, 3, par3StructureBoundingBox);
/* 1218:1432 */       func_151550_a(par1World, Blocks.planks, 0, 7, 5, 4, par3StructureBoundingBox);
/* 1219:1433 */       func_151550_a(par1World, Blocks.oak_stairs, var7, 6, 6, 4, par3StructureBoundingBox);
/* 1220:1436 */       for (int var8 = 6; var8 <= 8; var8++) {
/* 1221:1438 */         for (int var9 = 5; var9 <= 10; var9++) {
/* 1222:1440 */           func_151550_a(par1World, Blocks.oak_stairs, var7, var8, 12 - var8, var9, par3StructureBoundingBox);
/* 1223:     */         }
/* 1224:     */       }
/* 1225:1444 */       func_151550_a(par1World, Blocks.log, 0, 0, 2, 1, par3StructureBoundingBox);
/* 1226:1445 */       func_151550_a(par1World, Blocks.log, 0, 0, 2, 4, par3StructureBoundingBox);
/* 1227:1446 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/* 1228:1447 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
/* 1229:1448 */       func_151550_a(par1World, Blocks.log, 0, 4, 2, 0, par3StructureBoundingBox);
/* 1230:1449 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
/* 1231:1450 */       func_151550_a(par1World, Blocks.log, 0, 6, 2, 0, par3StructureBoundingBox);
/* 1232:1451 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 1, par3StructureBoundingBox);
/* 1233:1452 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
/* 1234:1453 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
/* 1235:1454 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 4, par3StructureBoundingBox);
/* 1236:1455 */       func_151550_a(par1World, Blocks.planks, 0, 8, 2, 5, par3StructureBoundingBox);
/* 1237:1456 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 6, par3StructureBoundingBox);
/* 1238:1457 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 7, par3StructureBoundingBox);
/* 1239:1458 */       func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 8, par3StructureBoundingBox);
/* 1240:1459 */       func_151550_a(par1World, Blocks.log, 0, 8, 2, 9, par3StructureBoundingBox);
/* 1241:1460 */       func_151550_a(par1World, Blocks.log, 0, 2, 2, 6, par3StructureBoundingBox);
/* 1242:1461 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 7, par3StructureBoundingBox);
/* 1243:1462 */       func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 8, par3StructureBoundingBox);
/* 1244:1463 */       func_151550_a(par1World, Blocks.log, 0, 2, 2, 9, par3StructureBoundingBox);
/* 1245:1464 */       func_151550_a(par1World, Blocks.log, 0, 4, 4, 10, par3StructureBoundingBox);
/* 1246:1465 */       func_151550_a(par1World, Blocks.glass_pane, 0, 5, 4, 10, par3StructureBoundingBox);
/* 1247:1466 */       func_151550_a(par1World, Blocks.log, 0, 6, 4, 10, par3StructureBoundingBox);
/* 1248:1467 */       func_151550_a(par1World, Blocks.planks, 0, 5, 5, 10, par3StructureBoundingBox);
/* 1249:1468 */       func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
/* 1250:1469 */       func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
/* 1251:1470 */       func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
/* 1252:1471 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, func_151555_a(Blocks.wooden_door, 1));
/* 1253:1472 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, -1, 3, 2, -1, Blocks.air, Blocks.air, false);
/* 1254:1474 */       if ((func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/* 1255:1476 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
/* 1256:     */       }
/* 1257:1479 */       for (var8 = 0; var8 < 5; var8++) {
/* 1258:1481 */         for (int var9 = 0; var9 < 9; var9++)
/* 1259:     */         {
/* 1260:1483 */           clearCurrentPositionBlocksUpwards(par1World, var9, 7, var8, par3StructureBoundingBox);
/* 1261:1484 */           func_151554_b(par1World, Blocks.cobblestone, 0, var9, -1, var8, par3StructureBoundingBox);
/* 1262:     */         }
/* 1263:     */       }
/* 1264:1488 */       for (var8 = 5; var8 < 11; var8++) {
/* 1265:1490 */         for (int var9 = 2; var9 < 9; var9++)
/* 1266:     */         {
/* 1267:1492 */           clearCurrentPositionBlocksUpwards(par1World, var9, 7, var8, par3StructureBoundingBox);
/* 1268:1493 */           func_151554_b(par1World, Blocks.cobblestone, 0, var9, -1, var8, par3StructureBoundingBox);
/* 1269:     */         }
/* 1270:     */       }
/* 1271:1497 */       spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
/* 1272:1498 */       return true;
/* 1273:     */     }
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   public static class WoodHut
/* 1277:     */     extends StructureVillagePieces.Village
/* 1278:     */   {
/* 1279:     */     private boolean isTallHouse;
/* 1280:     */     private int tablePosition;
/* 1281:     */     private static final String __OBFID = "CL_00000524";
/* 1282:     */     
/* 1283:     */     public WoodHut() {}
/* 1284:     */     
/* 1285:     */     public WoodHut(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1286:     */     {
/* 1287:1512 */       super(par2);
/* 1288:1513 */       this.coordBaseMode = par5;
/* 1289:1514 */       this.boundingBox = par4StructureBoundingBox;
/* 1290:1515 */       this.isTallHouse = par3Random.nextBoolean();
/* 1291:1516 */       this.tablePosition = par3Random.nextInt(3);
/* 1292:     */     }
/* 1293:     */     
/* 1294:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1295:     */     {
/* 1296:1521 */       super.func_143012_a(par1NBTTagCompound);
/* 1297:1522 */       par1NBTTagCompound.setInteger("T", this.tablePosition);
/* 1298:1523 */       par1NBTTagCompound.setBoolean("C", this.isTallHouse);
/* 1299:     */     }
/* 1300:     */     
/* 1301:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1302:     */     {
/* 1303:1528 */       super.func_143011_b(par1NBTTagCompound);
/* 1304:1529 */       this.tablePosition = par1NBTTagCompound.getInteger("T");
/* 1305:1530 */       this.isTallHouse = par1NBTTagCompound.getBoolean("C");
/* 1306:     */     }
/* 1307:     */     
/* 1308:     */     public static WoodHut func_74908_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/* 1309:     */     {
/* 1310:1535 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 4, 6, 5, par6);
/* 1311:1536 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new WoodHut(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/* 1312:     */     }
/* 1313:     */     
/* 1314:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1315:     */     {
/* 1316:1541 */       if (this.field_143015_k < 0)
/* 1317:     */       {
/* 1318:1543 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1319:1545 */         if (this.field_143015_k < 0) {
/* 1320:1547 */           return true;
/* 1321:     */         }
/* 1322:1550 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/* 1323:     */       }
/* 1324:1553 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 5, 4, Blocks.air, Blocks.air, false);
/* 1325:1554 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 3, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
/* 1326:1555 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 3, Blocks.dirt, Blocks.dirt, false);
/* 1327:1557 */       if (this.isTallHouse) {
/* 1328:1559 */         func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 2, 4, 3, Blocks.log, Blocks.log, false);
/* 1329:     */       } else {
/* 1330:1563 */         func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 2, 5, 3, Blocks.log, Blocks.log, false);
/* 1331:     */       }
/* 1332:1566 */       func_151550_a(par1World, Blocks.log, 0, 1, 4, 0, par3StructureBoundingBox);
/* 1333:1567 */       func_151550_a(par1World, Blocks.log, 0, 2, 4, 0, par3StructureBoundingBox);
/* 1334:1568 */       func_151550_a(par1World, Blocks.log, 0, 1, 4, 4, par3StructureBoundingBox);
/* 1335:1569 */       func_151550_a(par1World, Blocks.log, 0, 2, 4, 4, par3StructureBoundingBox);
/* 1336:1570 */       func_151550_a(par1World, Blocks.log, 0, 0, 4, 1, par3StructureBoundingBox);
/* 1337:1571 */       func_151550_a(par1World, Blocks.log, 0, 0, 4, 2, par3StructureBoundingBox);
/* 1338:1572 */       func_151550_a(par1World, Blocks.log, 0, 0, 4, 3, par3StructureBoundingBox);
/* 1339:1573 */       func_151550_a(par1World, Blocks.log, 0, 3, 4, 1, par3StructureBoundingBox);
/* 1340:1574 */       func_151550_a(par1World, Blocks.log, 0, 3, 4, 2, par3StructureBoundingBox);
/* 1341:1575 */       func_151550_a(par1World, Blocks.log, 0, 3, 4, 3, par3StructureBoundingBox);
/* 1342:1576 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 3, 0, Blocks.log, Blocks.log, false);
/* 1343:1577 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 3, 0, Blocks.log, Blocks.log, false);
/* 1344:1578 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 4, Blocks.log, Blocks.log, false);
/* 1345:1579 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 4, 3, 3, 4, Blocks.log, Blocks.log, false);
/* 1346:1580 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
/* 1347:1581 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 1, 3, 3, 3, Blocks.planks, Blocks.planks, false);
/* 1348:1582 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
/* 1349:1583 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 3, 4, Blocks.planks, Blocks.planks, false);
/* 1350:1584 */       func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
/* 1351:1585 */       func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 2, par3StructureBoundingBox);
/* 1352:1587 */       if (this.tablePosition > 0)
/* 1353:     */       {
/* 1354:1589 */         func_151550_a(par1World, Blocks.fence, 0, this.tablePosition, 1, 3, par3StructureBoundingBox);
/* 1355:1590 */         func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, this.tablePosition, 2, 3, par3StructureBoundingBox);
/* 1356:     */       }
/* 1357:1593 */       func_151550_a(par1World, Blocks.air, 0, 1, 1, 0, par3StructureBoundingBox);
/* 1358:1594 */       func_151550_a(par1World, Blocks.air, 0, 1, 2, 0, par3StructureBoundingBox);
/* 1359:1595 */       placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, func_151555_a(Blocks.wooden_door, 1));
/* 1360:1597 */       if ((func_151548_a(par1World, 1, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air) && (func_151548_a(par1World, 1, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air)) {
/* 1361:1599 */         func_151550_a(par1World, Blocks.stone_stairs, func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, par3StructureBoundingBox);
/* 1362:     */       }
/* 1363:1602 */       for (int var4 = 0; var4 < 5; var4++) {
/* 1364:1604 */         for (int var5 = 0; var5 < 4; var5++)
/* 1365:     */         {
/* 1366:1606 */           clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
/* 1367:1607 */           func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
/* 1368:     */         }
/* 1369:     */       }
/* 1370:1611 */       spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
/* 1371:1612 */       return true;
/* 1372:     */     }
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public static class Field1
/* 1376:     */     extends StructureVillagePieces.Village
/* 1377:     */   {
/* 1378:     */     private Block cropTypeA;
/* 1379:     */     private Block cropTypeB;
/* 1380:     */     private Block cropTypeC;
/* 1381:     */     private Block cropTypeD;
/* 1382:     */     private static final String __OBFID = "CL_00000518";
/* 1383:     */     
/* 1384:     */     public Field1() {}
/* 1385:     */     
/* 1386:     */     public Field1(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1387:     */     {
/* 1388:1628 */       super(par2);
/* 1389:1629 */       this.coordBaseMode = par5;
/* 1390:1630 */       this.boundingBox = par4StructureBoundingBox;
/* 1391:1631 */       this.cropTypeA = func_151559_a(par3Random);
/* 1392:1632 */       this.cropTypeB = func_151559_a(par3Random);
/* 1393:1633 */       this.cropTypeC = func_151559_a(par3Random);
/* 1394:1634 */       this.cropTypeD = func_151559_a(par3Random);
/* 1395:     */     }
/* 1396:     */     
/* 1397:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1398:     */     {
/* 1399:1639 */       super.func_143012_a(par1NBTTagCompound);
/* 1400:1640 */       par1NBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/* 1401:1641 */       par1NBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/* 1402:1642 */       par1NBTTagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
/* 1403:1643 */       par1NBTTagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
/* 1404:     */     }
/* 1405:     */     
/* 1406:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1407:     */     {
/* 1408:1648 */       super.func_143011_b(par1NBTTagCompound);
/* 1409:1649 */       this.cropTypeA = Block.getBlockById(par1NBTTagCompound.getInteger("CA"));
/* 1410:1650 */       this.cropTypeB = Block.getBlockById(par1NBTTagCompound.getInteger("CB"));
/* 1411:1651 */       this.cropTypeC = Block.getBlockById(par1NBTTagCompound.getInteger("CC"));
/* 1412:1652 */       this.cropTypeD = Block.getBlockById(par1NBTTagCompound.getInteger("CD"));
/* 1413:     */     }
/* 1414:     */     
/* 1415:     */     private Block func_151559_a(Random p_151559_1_)
/* 1416:     */     {
/* 1417:1657 */       switch (p_151559_1_.nextInt(5))
/* 1418:     */       {
/* 1419:     */       case 0: 
/* 1420:1660 */         return Blocks.carrots;
/* 1421:     */       case 1: 
/* 1422:1663 */         return Blocks.potatoes;
/* 1423:     */       }
/* 1424:1666 */       return Blocks.wheat;
/* 1425:     */     }
/* 1426:     */     
/* 1427:     */     public static Field1 func_74900_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/* 1428:     */     {
/* 1429:1672 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 13, 4, 9, par6);
/* 1430:1673 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new Field1(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/* 1431:     */     }
/* 1432:     */     
/* 1433:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1434:     */     {
/* 1435:1678 */       if (this.field_143015_k < 0)
/* 1436:     */       {
/* 1437:1680 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1438:1682 */         if (this.field_143015_k < 0) {
/* 1439:1684 */           return true;
/* 1440:     */         }
/* 1441:1687 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/* 1442:     */       }
/* 1443:1690 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 12, 4, 8, Blocks.air, Blocks.air, false);
/* 1444:1691 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1445:1692 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1446:1693 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 1, 8, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1447:1694 */       func_151549_a(par1World, par3StructureBoundingBox, 10, 0, 1, 11, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1448:1695 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
/* 1449:1696 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
/* 1450:1697 */       func_151549_a(par1World, par3StructureBoundingBox, 12, 0, 0, 12, 0, 8, Blocks.log, Blocks.log, false);
/* 1451:1698 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 11, 0, 0, Blocks.log, Blocks.log, false);
/* 1452:1699 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 8, 11, 0, 8, Blocks.log, Blocks.log, false);
/* 1453:1700 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
/* 1454:1701 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 1, 9, 0, 7, Blocks.water, Blocks.water, false);
/* 1455:1704 */       for (int var4 = 1; var4 <= 7; var4++)
/* 1456:     */       {
/* 1457:1706 */         func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
/* 1458:1707 */         func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
/* 1459:1708 */         func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
/* 1460:1709 */         func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
/* 1461:1710 */         func_151550_a(par1World, this.cropTypeC, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 7, 1, var4, par3StructureBoundingBox);
/* 1462:1711 */         func_151550_a(par1World, this.cropTypeC, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 8, 1, var4, par3StructureBoundingBox);
/* 1463:1712 */         func_151550_a(par1World, this.cropTypeD, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 10, 1, var4, par3StructureBoundingBox);
/* 1464:1713 */         func_151550_a(par1World, this.cropTypeD, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 11, 1, var4, par3StructureBoundingBox);
/* 1465:     */       }
/* 1466:1716 */       for (var4 = 0; var4 < 9; var4++) {
/* 1467:1718 */         for (int var5 = 0; var5 < 13; var5++)
/* 1468:     */         {
/* 1469:1720 */           clearCurrentPositionBlocksUpwards(par1World, var5, 4, var4, par3StructureBoundingBox);
/* 1470:1721 */           func_151554_b(par1World, Blocks.dirt, 0, var5, -1, var4, par3StructureBoundingBox);
/* 1471:     */         }
/* 1472:     */       }
/* 1473:1725 */       return true;
/* 1474:     */     }
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public static class Field2
/* 1478:     */     extends StructureVillagePieces.Village
/* 1479:     */   {
/* 1480:     */     private Block cropTypeA;
/* 1481:     */     private Block cropTypeB;
/* 1482:     */     private static final String __OBFID = "CL_00000519";
/* 1483:     */     
/* 1484:     */     public Field2() {}
/* 1485:     */     
/* 1486:     */     public Field2(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1487:     */     {
/* 1488:1739 */       super(par2);
/* 1489:1740 */       this.coordBaseMode = par5;
/* 1490:1741 */       this.boundingBox = par4StructureBoundingBox;
/* 1491:1742 */       this.cropTypeA = func_151560_a(par3Random);
/* 1492:1743 */       this.cropTypeB = func_151560_a(par3Random);
/* 1493:     */     }
/* 1494:     */     
/* 1495:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1496:     */     {
/* 1497:1748 */       super.func_143012_a(par1NBTTagCompound);
/* 1498:1749 */       par1NBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/* 1499:1750 */       par1NBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/* 1500:     */     }
/* 1501:     */     
/* 1502:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1503:     */     {
/* 1504:1755 */       super.func_143011_b(par1NBTTagCompound);
/* 1505:1756 */       this.cropTypeA = Block.getBlockById(par1NBTTagCompound.getInteger("CA"));
/* 1506:1757 */       this.cropTypeB = Block.getBlockById(par1NBTTagCompound.getInteger("CB"));
/* 1507:     */     }
/* 1508:     */     
/* 1509:     */     private Block func_151560_a(Random p_151560_1_)
/* 1510:     */     {
/* 1511:1762 */       switch (p_151560_1_.nextInt(5))
/* 1512:     */       {
/* 1513:     */       case 0: 
/* 1514:1765 */         return Blocks.carrots;
/* 1515:     */       case 1: 
/* 1516:1768 */         return Blocks.potatoes;
/* 1517:     */       }
/* 1518:1771 */       return Blocks.wheat;
/* 1519:     */     }
/* 1520:     */     
/* 1521:     */     public static Field2 func_74902_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/* 1522:     */     {
/* 1523:1777 */       StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 7, 4, 9, par6);
/* 1524:1778 */       return (canVillageGoDeeper(var8)) && (StructureComponent.findIntersecting(par1List, var8) == null) ? new Field2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
/* 1525:     */     }
/* 1526:     */     
/* 1527:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1528:     */     {
/* 1529:1783 */       if (this.field_143015_k < 0)
/* 1530:     */       {
/* 1531:1785 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1532:1787 */         if (this.field_143015_k < 0) {
/* 1533:1789 */           return true;
/* 1534:     */         }
/* 1535:1792 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/* 1536:     */       }
/* 1537:1795 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 6, 4, 8, Blocks.air, Blocks.air, false);
/* 1538:1796 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1539:1797 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
/* 1540:1798 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
/* 1541:1799 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
/* 1542:1800 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 0, 0, Blocks.log, Blocks.log, false);
/* 1543:1801 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 8, 5, 0, 8, Blocks.log, Blocks.log, false);
/* 1544:1802 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
/* 1545:1805 */       for (int var4 = 1; var4 <= 7; var4++)
/* 1546:     */       {
/* 1547:1807 */         func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
/* 1548:1808 */         func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
/* 1549:1809 */         func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
/* 1550:1810 */         func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
/* 1551:     */       }
/* 1552:1813 */       for (var4 = 0; var4 < 9; var4++) {
/* 1553:1815 */         for (int var5 = 0; var5 < 7; var5++)
/* 1554:     */         {
/* 1555:1817 */           clearCurrentPositionBlocksUpwards(par1World, var5, 4, var4, par3StructureBoundingBox);
/* 1556:1818 */           func_151554_b(par1World, Blocks.dirt, 0, var5, -1, var4, par3StructureBoundingBox);
/* 1557:     */         }
/* 1558:     */       }
/* 1559:1822 */       return true;
/* 1560:     */     }
/* 1561:     */   }
/* 1562:     */   
/* 1563:     */   public static abstract class Road
/* 1564:     */     extends StructureVillagePieces.Village
/* 1565:     */   {
/* 1566:     */     private static final String __OBFID = "CL_00000532";
/* 1567:     */     
/* 1568:     */     public Road() {}
/* 1569:     */     
/* 1570:     */     protected Road(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2)
/* 1571:     */     {
/* 1572:1834 */       super(par2);
/* 1573:     */     }
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public static class Torch
/* 1577:     */     extends StructureVillagePieces.Village
/* 1578:     */   {
/* 1579:     */     private static final String __OBFID = "CL_00000520";
/* 1580:     */     
/* 1581:     */     public Torch() {}
/* 1582:     */     
/* 1583:     */     public Torch(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
/* 1584:     */     {
/* 1585:1846 */       super(par2);
/* 1586:1847 */       this.coordBaseMode = par5;
/* 1587:1848 */       this.boundingBox = par4StructureBoundingBox;
/* 1588:     */     }
/* 1589:     */     
/* 1590:     */     public static StructureBoundingBox func_74904_a(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
/* 1591:     */     {
/* 1592:1853 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 4, 2, par6);
/* 1593:1854 */       return StructureComponent.findIntersecting(par1List, var7) != null ? null : var7;
/* 1594:     */     }
/* 1595:     */     
/* 1596:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1597:     */     {
/* 1598:1859 */       if (this.field_143015_k < 0)
/* 1599:     */       {
/* 1600:1861 */         this.field_143015_k = getAverageGroundLevel(par1World, par3StructureBoundingBox);
/* 1601:1863 */         if (this.field_143015_k < 0) {
/* 1602:1865 */           return true;
/* 1603:     */         }
/* 1604:1868 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/* 1605:     */       }
/* 1606:1871 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
/* 1607:1872 */       func_151550_a(par1World, Blocks.fence, 0, 1, 0, 0, par3StructureBoundingBox);
/* 1608:1873 */       func_151550_a(par1World, Blocks.fence, 0, 1, 1, 0, par3StructureBoundingBox);
/* 1609:1874 */       func_151550_a(par1World, Blocks.fence, 0, 1, 2, 0, par3StructureBoundingBox);
/* 1610:1875 */       func_151550_a(par1World, Blocks.wool, 15, 1, 3, 0, par3StructureBoundingBox);
/* 1611:1876 */       func_151550_a(par1World, Blocks.torch, 0, 0, 3, 0, par3StructureBoundingBox);
/* 1612:1877 */       func_151550_a(par1World, Blocks.torch, 0, 1, 3, 1, par3StructureBoundingBox);
/* 1613:1878 */       func_151550_a(par1World, Blocks.torch, 0, 2, 3, 0, par3StructureBoundingBox);
/* 1614:1879 */       func_151550_a(par1World, Blocks.torch, 0, 1, 3, -1, par3StructureBoundingBox);
/* 1615:1880 */       return true;
/* 1616:     */     }
/* 1617:     */   }
/* 1618:     */   
/* 1619:     */   public static class PieceWeight
/* 1620:     */   {
/* 1621:     */     public Class villagePieceClass;
/* 1622:     */     public final int villagePieceWeight;
/* 1623:     */     public int villagePiecesSpawned;
/* 1624:     */     public int villagePiecesLimit;
/* 1625:     */     private static final String __OBFID = "CL_00000521";
/* 1626:     */     
/* 1627:     */     public PieceWeight(Class par1Class, int par2, int par3)
/* 1628:     */     {
/* 1629:1894 */       this.villagePieceClass = par1Class;
/* 1630:1895 */       this.villagePieceWeight = par2;
/* 1631:1896 */       this.villagePiecesLimit = par3;
/* 1632:     */     }
/* 1633:     */     
/* 1634:     */     public boolean canSpawnMoreVillagePiecesOfType(int par1)
/* 1635:     */     {
/* 1636:1901 */       return (this.villagePiecesLimit == 0) || (this.villagePiecesSpawned < this.villagePiecesLimit);
/* 1637:     */     }
/* 1638:     */     
/* 1639:     */     public boolean canSpawnMoreVillagePieces()
/* 1640:     */     {
/* 1641:1906 */       return (this.villagePiecesLimit == 0) || (this.villagePiecesSpawned < this.villagePiecesLimit);
/* 1642:     */     }
/* 1643:     */   }
/* 1644:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureVillagePieces
 * JD-Core Version:    0.7.0.1
 */