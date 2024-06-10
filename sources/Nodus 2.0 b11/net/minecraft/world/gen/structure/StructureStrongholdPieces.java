/*    1:     */ package net.minecraft.world.gen.structure;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Random;
/*    7:     */ import net.minecraft.init.Blocks;
/*    8:     */ import net.minecraft.init.Items;
/*    9:     */ import net.minecraft.item.ItemEnchantedBook;
/*   10:     */ import net.minecraft.nbt.NBTTagCompound;
/*   11:     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*   12:     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*   13:     */ import net.minecraft.util.WeightedRandomChestContent;
/*   14:     */ import net.minecraft.world.ChunkPosition;
/*   15:     */ import net.minecraft.world.World;
/*   16:     */ 
/*   17:     */ public class StructureStrongholdPieces
/*   18:     */ {
/*   19:  17 */   private static final PieceWeight[] pieceWeightArray = { new PieceWeight(Straight.class, 40, 0), new PieceWeight(Prison.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StairsStraight.class, 5, 5), new PieceWeight(Stairs.class, 5, 5), new PieceWeight(Crossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2)
/*   20:     */   
/*   21:     */ 
/*   22:     */ 
/*   23:     */ 
/*   24:     */ 
/*   25:     */ 
/*   26:  24 */     new PieceWeight
/*   27:     */   {
/*   28:     */     private static final String __OBFID = "CL_00000484";
/*   29:     */     
/*   30:     */     public boolean canSpawnMoreStructuresOfType(int par1)
/*   31:     */     {
/*   32:  22 */       return (super.canSpawnMoreStructuresOfType(par1)) && (par1 > 4);
/*   33:     */     }
/*   34:  17 */   }, 
/*   35:     */   
/*   36:     */ 
/*   37:     */ 
/*   38:     */ 
/*   39:     */ 
/*   40:     */ 
/*   41:  24 */     new PieceWeight(PortalRoom.class, 20, 1)
/*   42:     */   {
/*   43:     */     private static final String __OBFID = "CL_00000485";
/*   44:     */     
/*   45:     */     public boolean canSpawnMoreStructuresOfType(int par1)
/*   46:     */     {
/*   47:  29 */       return (super.canSpawnMoreStructuresOfType(par1)) && (par1 > 5);
/*   48:     */     }
/*   49:  17 */   }
/*   50:     */   
/*   51:     */ 
/*   52:     */ 
/*   53:     */ 
/*   54:     */ 
/*   55:     */ 
/*   56:  24 */      };
/*   57:     */   private static List structurePieceList;
/*   58:     */   private static Class strongComponentType;
/*   59:     */   static int totalWeight;
/*   60:  36 */   private static final Stones strongholdStones = new Stones(null);
/*   61:     */   private static final String __OBFID = "CL_00000483";
/*   62:     */   
/*   63:     */   public static void func_143046_a()
/*   64:     */   {
/*   65:  41 */     MapGenStructureIO.func_143031_a(ChestCorridor.class, "SHCC");
/*   66:  42 */     MapGenStructureIO.func_143031_a(Corridor.class, "SHFC");
/*   67:  43 */     MapGenStructureIO.func_143031_a(Crossing.class, "SH5C");
/*   68:  44 */     MapGenStructureIO.func_143031_a(LeftTurn.class, "SHLT");
/*   69:  45 */     MapGenStructureIO.func_143031_a(Library.class, "SHLi");
/*   70:  46 */     MapGenStructureIO.func_143031_a(PortalRoom.class, "SHPR");
/*   71:  47 */     MapGenStructureIO.func_143031_a(Prison.class, "SHPH");
/*   72:  48 */     MapGenStructureIO.func_143031_a(RightTurn.class, "SHRT");
/*   73:  49 */     MapGenStructureIO.func_143031_a(RoomCrossing.class, "SHRC");
/*   74:  50 */     MapGenStructureIO.func_143031_a(Stairs.class, "SHSD");
/*   75:  51 */     MapGenStructureIO.func_143031_a(Stairs2.class, "SHStart");
/*   76:  52 */     MapGenStructureIO.func_143031_a(Straight.class, "SHS");
/*   77:  53 */     MapGenStructureIO.func_143031_a(StairsStraight.class, "SHSSD");
/*   78:     */   }
/*   79:     */   
/*   80:     */   public static void prepareStructurePieces()
/*   81:     */   {
/*   82:  61 */     structurePieceList = new ArrayList();
/*   83:  62 */     PieceWeight[] var0 = pieceWeightArray;
/*   84:  63 */     int var1 = var0.length;
/*   85:  65 */     for (int var2 = 0; var2 < var1; var2++)
/*   86:     */     {
/*   87:  67 */       PieceWeight var3 = var0[var2];
/*   88:  68 */       var3.instancesSpawned = 0;
/*   89:  69 */       structurePieceList.add(var3);
/*   90:     */     }
/*   91:  72 */     strongComponentType = null;
/*   92:     */   }
/*   93:     */   
/*   94:     */   private static boolean canAddStructurePieces()
/*   95:     */   {
/*   96:  77 */     boolean var0 = false;
/*   97:  78 */     totalWeight = 0;
/*   98:     */     PieceWeight var2;
/*   99:  81 */     for (Iterator var1 = structurePieceList.iterator(); var1.hasNext(); totalWeight += var2.pieceWeight)
/*  100:     */     {
/*  101:  83 */       var2 = (PieceWeight)var1.next();
/*  102:  85 */       if ((var2.instancesLimit > 0) && (var2.instancesSpawned < var2.instancesLimit)) {
/*  103:  87 */         var0 = true;
/*  104:     */       }
/*  105:     */     }
/*  106:  91 */     return var0;
/*  107:     */   }
/*  108:     */   
/*  109:     */   private static Stronghold getStrongholdComponentFromWeightedPiece(Class par0Class, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  110:     */   {
/*  111:  99 */     Object var8 = null;
/*  112: 101 */     if (par0Class == Straight.class) {
/*  113: 103 */       var8 = Straight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  114: 105 */     } else if (par0Class == Prison.class) {
/*  115: 107 */       var8 = Prison.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  116: 109 */     } else if (par0Class == LeftTurn.class) {
/*  117: 111 */       var8 = LeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  118: 113 */     } else if (par0Class == RightTurn.class) {
/*  119: 115 */       var8 = RightTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  120: 117 */     } else if (par0Class == RoomCrossing.class) {
/*  121: 119 */       var8 = RoomCrossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  122: 121 */     } else if (par0Class == StairsStraight.class) {
/*  123: 123 */       var8 = StairsStraight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  124: 125 */     } else if (par0Class == Stairs.class) {
/*  125: 127 */       var8 = Stairs.getStrongholdStairsComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*  126: 129 */     } else if (par0Class == Crossing.class) {
/*  127: 131 */       var8 = Crossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  128: 133 */     } else if (par0Class == ChestCorridor.class) {
/*  129: 135 */       var8 = ChestCorridor.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  130: 137 */     } else if (par0Class == Library.class) {
/*  131: 139 */       var8 = Library.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  132: 141 */     } else if (par0Class == PortalRoom.class) {
/*  133: 143 */       var8 = PortalRoom.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
/*  134:     */     }
/*  135: 146 */     return (Stronghold)var8;
/*  136:     */   }
/*  137:     */   
/*  138:     */   private static Stronghold getNextComponent(Stairs2 par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  139:     */   {
/*  140: 151 */     if (!canAddStructurePieces()) {
/*  141: 153 */       return null;
/*  142:     */     }
/*  143: 157 */     if (strongComponentType != null)
/*  144:     */     {
/*  145: 159 */       Stronghold var8 = getStrongholdComponentFromWeightedPiece(strongComponentType, par1List, par2Random, par3, par4, par5, par6, par7);
/*  146: 160 */       strongComponentType = null;
/*  147: 162 */       if (var8 != null) {
/*  148: 164 */         return var8;
/*  149:     */       }
/*  150:     */     }
/*  151: 168 */     int var13 = 0;
/*  152:     */     Iterator var10;
/*  153:     */     label200:
/*  154: 170 */     for (; var13 < 5; var10.hasNext())
/*  155:     */     {
/*  156: 172 */       var13++;
/*  157: 173 */       int var9 = par2Random.nextInt(totalWeight);
/*  158: 174 */       var10 = structurePieceList.iterator();
/*  159:     */       
/*  160: 176 */       continue;
/*  161:     */       
/*  162: 178 */       PieceWeight var11 = (PieceWeight)var10.next();
/*  163: 179 */       var9 -= var11.pieceWeight;
/*  164: 181 */       if (var9 < 0)
/*  165:     */       {
/*  166: 183 */         if ((!var11.canSpawnMoreStructuresOfType(par7)) || (var11 == par0ComponentStrongholdStairs2.strongholdPieceWeight)) {
/*  167:     */           break label200;
/*  168:     */         }
/*  169: 188 */         Stronghold var12 = getStrongholdComponentFromWeightedPiece(var11.pieceClass, par1List, par2Random, par3, par4, par5, par6, par7);
/*  170: 190 */         if (var12 != null)
/*  171:     */         {
/*  172: 192 */           var11.instancesSpawned += 1;
/*  173: 193 */           par0ComponentStrongholdStairs2.strongholdPieceWeight = var11;
/*  174: 195 */           if (!var11.canSpawnMoreStructures()) {
/*  175: 197 */             structurePieceList.remove(var11);
/*  176:     */           }
/*  177: 200 */           return var12;
/*  178:     */         }
/*  179:     */       }
/*  180:     */     }
/*  181: 206 */     StructureBoundingBox var14 = Corridor.func_74992_a(par1List, par2Random, par3, par4, par5, par6);
/*  182: 208 */     if ((var14 != null) && (var14.minY > 1)) {
/*  183: 210 */       return new Corridor(par7, par2Random, var14, par6);
/*  184:     */     }
/*  185: 214 */     return null;
/*  186:     */   }
/*  187:     */   
/*  188:     */   private static StructureComponent getNextValidComponent(Stairs2 par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*  189:     */   {
/*  190: 221 */     if (par7 > 50) {
/*  191: 223 */       return null;
/*  192:     */     }
/*  193: 225 */     if ((Math.abs(par3 - par0ComponentStrongholdStairs2.getBoundingBox().minX) <= 112) && (Math.abs(par5 - par0ComponentStrongholdStairs2.getBoundingBox().minZ) <= 112))
/*  194:     */     {
/*  195: 227 */       Stronghold var8 = getNextComponent(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
/*  196: 229 */       if (var8 != null)
/*  197:     */       {
/*  198: 231 */         par1List.add(var8);
/*  199: 232 */         par0ComponentStrongholdStairs2.field_75026_c.add(var8);
/*  200:     */       }
/*  201: 235 */       return var8;
/*  202:     */     }
/*  203: 239 */     return null;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public static class Stairs
/*  207:     */     extends StructureStrongholdPieces.Stronghold
/*  208:     */   {
/*  209:     */     private boolean field_75024_a;
/*  210:     */     private static final String __OBFID = "CL_00000498";
/*  211:     */     
/*  212:     */     public Stairs() {}
/*  213:     */     
/*  214:     */     public Stairs(int par1, Random par2Random, int par3, int par4)
/*  215:     */     {
/*  216: 252 */       super();
/*  217: 253 */       this.field_75024_a = true;
/*  218: 254 */       this.coordBaseMode = par2Random.nextInt(4);
/*  219: 255 */       this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
/*  220: 257 */       switch (this.coordBaseMode)
/*  221:     */       {
/*  222:     */       case 0: 
/*  223:     */       case 2: 
/*  224: 261 */         this.boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
/*  225: 262 */         break;
/*  226:     */       case 1: 
/*  227:     */       default: 
/*  228: 265 */         this.boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
/*  229:     */       }
/*  230:     */     }
/*  231:     */     
/*  232:     */     public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  233:     */     {
/*  234: 271 */       super();
/*  235: 272 */       this.field_75024_a = false;
/*  236: 273 */       this.coordBaseMode = par4;
/*  237: 274 */       this.field_143013_d = getRandomDoor(par2Random);
/*  238: 275 */       this.boundingBox = par3StructureBoundingBox;
/*  239:     */     }
/*  240:     */     
/*  241:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  242:     */     {
/*  243: 280 */       super.func_143012_a(par1NBTTagCompound);
/*  244: 281 */       par1NBTTagCompound.setBoolean("Source", this.field_75024_a);
/*  245:     */     }
/*  246:     */     
/*  247:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  248:     */     {
/*  249: 286 */       super.func_143011_b(par1NBTTagCompound);
/*  250: 287 */       this.field_75024_a = par1NBTTagCompound.getBoolean("Source");
/*  251:     */     }
/*  252:     */     
/*  253:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  254:     */     {
/*  255: 292 */       if (this.field_75024_a) {
/*  256: 294 */         StructureStrongholdPieces.strongComponentType = StructureStrongholdPieces.Crossing.class;
/*  257:     */       }
/*  258: 297 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  259:     */     }
/*  260:     */     
/*  261:     */     public static Stairs getStrongholdStairsComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  262:     */     {
/*  263: 302 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 5, par5);
/*  264: 303 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Stairs(par6, par1Random, var7, par5) : null;
/*  265:     */     }
/*  266:     */     
/*  267:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  268:     */     {
/*  269: 308 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  270: 310 */         return false;
/*  271:     */       }
/*  272: 314 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 4, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  273: 315 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 7, 0);
/*  274: 316 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
/*  275: 317 */       func_151550_a(par1World, Blocks.stonebrick, 0, 2, 6, 1, par3StructureBoundingBox);
/*  276: 318 */       func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5, 1, par3StructureBoundingBox);
/*  277: 319 */       func_151550_a(par1World, Blocks.stone_slab, 0, 1, 6, 1, par3StructureBoundingBox);
/*  278: 320 */       func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5, 2, par3StructureBoundingBox);
/*  279: 321 */       func_151550_a(par1World, Blocks.stonebrick, 0, 1, 4, 3, par3StructureBoundingBox);
/*  280: 322 */       func_151550_a(par1World, Blocks.stone_slab, 0, 1, 5, 3, par3StructureBoundingBox);
/*  281: 323 */       func_151550_a(par1World, Blocks.stonebrick, 0, 2, 4, 3, par3StructureBoundingBox);
/*  282: 324 */       func_151550_a(par1World, Blocks.stonebrick, 0, 3, 3, 3, par3StructureBoundingBox);
/*  283: 325 */       func_151550_a(par1World, Blocks.stone_slab, 0, 3, 4, 3, par3StructureBoundingBox);
/*  284: 326 */       func_151550_a(par1World, Blocks.stonebrick, 0, 3, 3, 2, par3StructureBoundingBox);
/*  285: 327 */       func_151550_a(par1World, Blocks.stonebrick, 0, 3, 2, 1, par3StructureBoundingBox);
/*  286: 328 */       func_151550_a(par1World, Blocks.stone_slab, 0, 3, 3, 1, par3StructureBoundingBox);
/*  287: 329 */       func_151550_a(par1World, Blocks.stonebrick, 0, 2, 2, 1, par3StructureBoundingBox);
/*  288: 330 */       func_151550_a(par1World, Blocks.stonebrick, 0, 1, 1, 1, par3StructureBoundingBox);
/*  289: 331 */       func_151550_a(par1World, Blocks.stone_slab, 0, 1, 2, 1, par3StructureBoundingBox);
/*  290: 332 */       func_151550_a(par1World, Blocks.stonebrick, 0, 1, 1, 2, par3StructureBoundingBox);
/*  291: 333 */       func_151550_a(par1World, Blocks.stone_slab, 0, 1, 1, 3, par3StructureBoundingBox);
/*  292: 334 */       return true;
/*  293:     */     }
/*  294:     */   }
/*  295:     */   
/*  296:     */   public static class Straight
/*  297:     */     extends StructureStrongholdPieces.Stronghold
/*  298:     */   {
/*  299:     */     private boolean expandsX;
/*  300:     */     private boolean expandsZ;
/*  301:     */     private static final String __OBFID = "CL_00000500";
/*  302:     */     
/*  303:     */     public Straight() {}
/*  304:     */     
/*  305:     */     public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  306:     */     {
/*  307: 349 */       super();
/*  308: 350 */       this.coordBaseMode = par4;
/*  309: 351 */       this.field_143013_d = getRandomDoor(par2Random);
/*  310: 352 */       this.boundingBox = par3StructureBoundingBox;
/*  311: 353 */       this.expandsX = (par2Random.nextInt(2) == 0);
/*  312: 354 */       this.expandsZ = (par2Random.nextInt(2) == 0);
/*  313:     */     }
/*  314:     */     
/*  315:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  316:     */     {
/*  317: 359 */       super.func_143012_a(par1NBTTagCompound);
/*  318: 360 */       par1NBTTagCompound.setBoolean("Left", this.expandsX);
/*  319: 361 */       par1NBTTagCompound.setBoolean("Right", this.expandsZ);
/*  320:     */     }
/*  321:     */     
/*  322:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  323:     */     {
/*  324: 366 */       super.func_143011_b(par1NBTTagCompound);
/*  325: 367 */       this.expandsX = par1NBTTagCompound.getBoolean("Left");
/*  326: 368 */       this.expandsZ = par1NBTTagCompound.getBoolean("Right");
/*  327:     */     }
/*  328:     */     
/*  329:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  330:     */     {
/*  331: 373 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  332: 375 */       if (this.expandsX) {
/*  333: 377 */         getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
/*  334:     */       }
/*  335: 380 */       if (this.expandsZ) {
/*  336: 382 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
/*  337:     */       }
/*  338:     */     }
/*  339:     */     
/*  340:     */     public static Straight findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  341:     */     {
/*  342: 388 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
/*  343: 389 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Straight(par6, par1Random, var7, par5) : null;
/*  344:     */     }
/*  345:     */     
/*  346:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  347:     */     {
/*  348: 394 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  349: 396 */         return false;
/*  350:     */       }
/*  351: 400 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  352: 401 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
/*  353: 402 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/*  354: 403 */       func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 1, Blocks.torch, 0);
/*  355: 404 */       func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 1, Blocks.torch, 0);
/*  356: 405 */       func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 5, Blocks.torch, 0);
/*  357: 406 */       func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 5, Blocks.torch, 0);
/*  358: 408 */       if (this.expandsX) {
/*  359: 410 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.air, Blocks.air, false);
/*  360:     */       }
/*  361: 413 */       if (this.expandsZ) {
/*  362: 415 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.air, Blocks.air, false);
/*  363:     */       }
/*  364: 418 */       return true;
/*  365:     */     }
/*  366:     */   }
/*  367:     */   
/*  368:     */   public static class Library
/*  369:     */     extends StructureStrongholdPieces.Stronghold
/*  370:     */   {
/*  371: 425 */     private static final WeightedRandomChestContent[] strongholdLibraryChestContents = { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) };
/*  372:     */     private boolean isLargeRoom;
/*  373:     */     private static final String __OBFID = "CL_00000491";
/*  374:     */     
/*  375:     */     public Library() {}
/*  376:     */     
/*  377:     */     public Library(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  378:     */     {
/*  379: 433 */       super();
/*  380: 434 */       this.coordBaseMode = par4;
/*  381: 435 */       this.field_143013_d = getRandomDoor(par2Random);
/*  382: 436 */       this.boundingBox = par3StructureBoundingBox;
/*  383: 437 */       this.isLargeRoom = (par3StructureBoundingBox.getYSize() > 6);
/*  384:     */     }
/*  385:     */     
/*  386:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  387:     */     {
/*  388: 442 */       super.func_143012_a(par1NBTTagCompound);
/*  389: 443 */       par1NBTTagCompound.setBoolean("Tall", this.isLargeRoom);
/*  390:     */     }
/*  391:     */     
/*  392:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  393:     */     {
/*  394: 448 */       super.func_143011_b(par1NBTTagCompound);
/*  395: 449 */       this.isLargeRoom = par1NBTTagCompound.getBoolean("Tall");
/*  396:     */     }
/*  397:     */     
/*  398:     */     public static Library findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  399:     */     {
/*  400: 454 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 11, 15, par5);
/*  401: 456 */       if ((!canStrongholdGoDeeper(var7)) || (StructureComponent.findIntersecting(par0List, var7) != null))
/*  402:     */       {
/*  403: 458 */         var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 6, 15, par5);
/*  404: 460 */         if ((!canStrongholdGoDeeper(var7)) || (StructureComponent.findIntersecting(par0List, var7) != null)) {
/*  405: 462 */           return null;
/*  406:     */         }
/*  407:     */       }
/*  408: 466 */       return new Library(par6, par1Random, var7, par5);
/*  409:     */     }
/*  410:     */     
/*  411:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  412:     */     {
/*  413: 471 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  414: 473 */         return false;
/*  415:     */       }
/*  416: 477 */       byte var4 = 11;
/*  417: 479 */       if (!this.isLargeRoom) {
/*  418: 481 */         var4 = 6;
/*  419:     */       }
/*  420: 484 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 13, var4 - 1, 14, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  421: 485 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 1, 0);
/*  422: 486 */       func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web, Blocks.web, false);
/*  423: 487 */       boolean var5 = true;
/*  424: 488 */       boolean var6 = true;
/*  425: 491 */       for (int var7 = 1; var7 <= 13; var7++) {
/*  426: 493 */         if ((var7 - 1) % 4 == 0)
/*  427:     */         {
/*  428: 495 */           func_151549_a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Blocks.planks, Blocks.planks, false);
/*  429: 496 */           func_151549_a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Blocks.planks, Blocks.planks, false);
/*  430: 497 */           func_151550_a(par1World, Blocks.torch, 0, 2, 3, var7, par3StructureBoundingBox);
/*  431: 498 */           func_151550_a(par1World, Blocks.torch, 0, 11, 3, var7, par3StructureBoundingBox);
/*  432: 500 */           if (this.isLargeRoom)
/*  433:     */           {
/*  434: 502 */             func_151549_a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Blocks.planks, Blocks.planks, false);
/*  435: 503 */             func_151549_a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Blocks.planks, Blocks.planks, false);
/*  436:     */           }
/*  437:     */         }
/*  438:     */         else
/*  439:     */         {
/*  440: 508 */           func_151549_a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  441: 509 */           func_151549_a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  442: 511 */           if (this.isLargeRoom)
/*  443:     */           {
/*  444: 513 */             func_151549_a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  445: 514 */             func_151549_a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  446:     */           }
/*  447:     */         }
/*  448:     */       }
/*  449: 519 */       for (var7 = 3; var7 < 12; var7 += 2)
/*  450:     */       {
/*  451: 521 */         func_151549_a(par1World, par3StructureBoundingBox, 3, 1, var7, 4, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  452: 522 */         func_151549_a(par1World, par3StructureBoundingBox, 6, 1, var7, 7, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  453: 523 */         func_151549_a(par1World, par3StructureBoundingBox, 9, 1, var7, 10, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
/*  454:     */       }
/*  455: 526 */       if (this.isLargeRoom)
/*  456:     */       {
/*  457: 528 */         func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.planks, Blocks.planks, false);
/*  458: 529 */         func_151549_a(par1World, par3StructureBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.planks, Blocks.planks, false);
/*  459: 530 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.planks, Blocks.planks, false);
/*  460: 531 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.planks, Blocks.planks, false);
/*  461: 532 */         func_151550_a(par1World, Blocks.planks, 0, 9, 5, 11, par3StructureBoundingBox);
/*  462: 533 */         func_151550_a(par1World, Blocks.planks, 0, 8, 5, 11, par3StructureBoundingBox);
/*  463: 534 */         func_151550_a(par1World, Blocks.planks, 0, 9, 5, 10, par3StructureBoundingBox);
/*  464: 535 */         func_151549_a(par1World, par3StructureBoundingBox, 3, 6, 2, 3, 6, 12, Blocks.fence, Blocks.fence, false);
/*  465: 536 */         func_151549_a(par1World, par3StructureBoundingBox, 10, 6, 2, 10, 6, 10, Blocks.fence, Blocks.fence, false);
/*  466: 537 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 6, 2, 9, 6, 2, Blocks.fence, Blocks.fence, false);
/*  467: 538 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 6, 12, 8, 6, 12, Blocks.fence, Blocks.fence, false);
/*  468: 539 */         func_151550_a(par1World, Blocks.fence, 0, 9, 6, 11, par3StructureBoundingBox);
/*  469: 540 */         func_151550_a(par1World, Blocks.fence, 0, 8, 6, 11, par3StructureBoundingBox);
/*  470: 541 */         func_151550_a(par1World, Blocks.fence, 0, 9, 6, 10, par3StructureBoundingBox);
/*  471: 542 */         var7 = func_151555_a(Blocks.ladder, 3);
/*  472: 543 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 1, 13, par3StructureBoundingBox);
/*  473: 544 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 2, 13, par3StructureBoundingBox);
/*  474: 545 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 3, 13, par3StructureBoundingBox);
/*  475: 546 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 4, 13, par3StructureBoundingBox);
/*  476: 547 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 5, 13, par3StructureBoundingBox);
/*  477: 548 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 6, 13, par3StructureBoundingBox);
/*  478: 549 */         func_151550_a(par1World, Blocks.ladder, var7, 10, 7, 13, par3StructureBoundingBox);
/*  479: 550 */         byte var8 = 7;
/*  480: 551 */         byte var9 = 7;
/*  481: 552 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 9, var9, par3StructureBoundingBox);
/*  482: 553 */         func_151550_a(par1World, Blocks.fence, 0, var8, 9, var9, par3StructureBoundingBox);
/*  483: 554 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 8, var9, par3StructureBoundingBox);
/*  484: 555 */         func_151550_a(par1World, Blocks.fence, 0, var8, 8, var9, par3StructureBoundingBox);
/*  485: 556 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9, par3StructureBoundingBox);
/*  486: 557 */         func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9, par3StructureBoundingBox);
/*  487: 558 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 2, 7, var9, par3StructureBoundingBox);
/*  488: 559 */         func_151550_a(par1World, Blocks.fence, 0, var8 + 1, 7, var9, par3StructureBoundingBox);
/*  489: 560 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9 - 1, par3StructureBoundingBox);
/*  490: 561 */         func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9 + 1, par3StructureBoundingBox);
/*  491: 562 */         func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9 - 1, par3StructureBoundingBox);
/*  492: 563 */         func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9 + 1, par3StructureBoundingBox);
/*  493: 564 */         func_151550_a(par1World, Blocks.torch, 0, var8 - 2, 8, var9, par3StructureBoundingBox);
/*  494: 565 */         func_151550_a(par1World, Blocks.torch, 0, var8 + 1, 8, var9, par3StructureBoundingBox);
/*  495: 566 */         func_151550_a(par1World, Blocks.torch, 0, var8 - 1, 8, var9 - 1, par3StructureBoundingBox);
/*  496: 567 */         func_151550_a(par1World, Blocks.torch, 0, var8 - 1, 8, var9 + 1, par3StructureBoundingBox);
/*  497: 568 */         func_151550_a(par1World, Blocks.torch, 0, var8, 8, var9 - 1, par3StructureBoundingBox);
/*  498: 569 */         func_151550_a(par1World, Blocks.torch, 0, var8, 8, var9 + 1, par3StructureBoundingBox);
/*  499:     */       }
/*  500: 572 */       generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 3, 5, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92112_a(par2Random, 1, 5, 2) }), 1 + par2Random.nextInt(4));
/*  501: 574 */       if (this.isLargeRoom)
/*  502:     */       {
/*  503: 576 */         func_151550_a(par1World, Blocks.air, 0, 12, 9, 1, par3StructureBoundingBox);
/*  504: 577 */         generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 12, 8, 1, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92112_a(par2Random, 1, 5, 2) }), 1 + par2Random.nextInt(4));
/*  505:     */       }
/*  506: 580 */       return true;
/*  507:     */     }
/*  508:     */   }
/*  509:     */   
/*  510:     */   public static class PortalRoom
/*  511:     */     extends StructureStrongholdPieces.Stronghold
/*  512:     */   {
/*  513:     */     private boolean hasSpawner;
/*  514:     */     private static final String __OBFID = "CL_00000493";
/*  515:     */     
/*  516:     */     public PortalRoom() {}
/*  517:     */     
/*  518:     */     public PortalRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  519:     */     {
/*  520: 594 */       super();
/*  521: 595 */       this.coordBaseMode = par4;
/*  522: 596 */       this.boundingBox = par3StructureBoundingBox;
/*  523:     */     }
/*  524:     */     
/*  525:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  526:     */     {
/*  527: 601 */       super.func_143012_a(par1NBTTagCompound);
/*  528: 602 */       par1NBTTagCompound.setBoolean("Mob", this.hasSpawner);
/*  529:     */     }
/*  530:     */     
/*  531:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  532:     */     {
/*  533: 607 */       super.func_143011_b(par1NBTTagCompound);
/*  534: 608 */       this.hasSpawner = par1NBTTagCompound.getBoolean("Mob");
/*  535:     */     }
/*  536:     */     
/*  537:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  538:     */     {
/*  539: 613 */       if (par1StructureComponent != null) {
/*  540: 615 */         ((StructureStrongholdPieces.Stairs2)par1StructureComponent).strongholdPortalRoom = this;
/*  541:     */       }
/*  542:     */     }
/*  543:     */     
/*  544:     */     public static PortalRoom findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  545:     */     {
/*  546: 621 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 8, 16, par5);
/*  547: 622 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new PortalRoom(par6, par1Random, var7, par5) : null;
/*  548:     */     }
/*  549:     */     
/*  550:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  551:     */     {
/*  552: 627 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 7, 15, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  553: 628 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
/*  554: 629 */       byte var4 = 6;
/*  555: 630 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, var4, 1, 1, var4, 14, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  556: 631 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, var4, 1, 9, var4, 14, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  557: 632 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, var4, 1, 8, var4, 2, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  558: 633 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, var4, 14, 8, var4, 14, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  559: 634 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 2, 1, 4, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  560: 635 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 1, 9, 1, 4, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  561: 636 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
/*  562: 637 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
/*  563: 638 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 3, 1, 8, 7, 1, 12, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  564: 639 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava, Blocks.flowing_lava, false);
/*  565: 642 */       for (int var5 = 3; var5 < 14; var5 += 2)
/*  566:     */       {
/*  567: 644 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 3, var5, 0, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
/*  568: 645 */         func_151549_a(par1World, par3StructureBoundingBox, 10, 3, var5, 10, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
/*  569:     */       }
/*  570: 648 */       for (var5 = 2; var5 < 9; var5 += 2) {
/*  571: 650 */         func_151549_a(par1World, par3StructureBoundingBox, var5, 3, 15, var5, 4, 15, Blocks.iron_bars, Blocks.iron_bars, false);
/*  572:     */       }
/*  573: 653 */       var5 = func_151555_a(Blocks.stone_brick_stairs, 3);
/*  574: 654 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 6, 1, 7, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  575: 655 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 2, 6, 6, 2, 7, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  576: 656 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 3, 7, 6, 3, 7, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  577: 658 */       for (int var6 = 4; var6 <= 6; var6++)
/*  578:     */       {
/*  579: 660 */         func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 1, 4, par3StructureBoundingBox);
/*  580: 661 */         func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 2, 5, par3StructureBoundingBox);
/*  581: 662 */         func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 3, 6, par3StructureBoundingBox);
/*  582:     */       }
/*  583: 665 */       byte var14 = 2;
/*  584: 666 */       byte var7 = 0;
/*  585: 667 */       byte var8 = 3;
/*  586: 668 */       byte var9 = 1;
/*  587: 670 */       switch (this.coordBaseMode)
/*  588:     */       {
/*  589:     */       case 0: 
/*  590: 673 */         var14 = 0;
/*  591: 674 */         var7 = 2;
/*  592: 675 */         break;
/*  593:     */       case 1: 
/*  594: 678 */         var14 = 1;
/*  595: 679 */         var7 = 3;
/*  596: 680 */         var8 = 0;
/*  597: 681 */         var9 = 2;
/*  598:     */       case 2: 
/*  599:     */       default: 
/*  600:     */         break;
/*  601:     */       case 3: 
/*  602: 688 */         var14 = 3;
/*  603: 689 */         var7 = 1;
/*  604: 690 */         var8 = 0;
/*  605: 691 */         var9 = 2;
/*  606:     */       }
/*  607: 694 */       func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 8, par3StructureBoundingBox);
/*  608: 695 */       func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 8, par3StructureBoundingBox);
/*  609: 696 */       func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 8, par3StructureBoundingBox);
/*  610: 697 */       func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 12, par3StructureBoundingBox);
/*  611: 698 */       func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 12, par3StructureBoundingBox);
/*  612: 699 */       func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 12, par3StructureBoundingBox);
/*  613: 700 */       func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 9, par3StructureBoundingBox);
/*  614: 701 */       func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 10, par3StructureBoundingBox);
/*  615: 702 */       func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 11, par3StructureBoundingBox);
/*  616: 703 */       func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 9, par3StructureBoundingBox);
/*  617: 704 */       func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 10, par3StructureBoundingBox);
/*  618: 705 */       func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 11, par3StructureBoundingBox);
/*  619: 707 */       if (!this.hasSpawner)
/*  620:     */       {
/*  621: 709 */         int var13 = getYWithOffset(3);
/*  622: 710 */         int var10 = getXWithOffset(5, 6);
/*  623: 711 */         int var11 = getZWithOffset(5, 6);
/*  624: 713 */         if (par3StructureBoundingBox.isVecInside(var10, var13, var11))
/*  625:     */         {
/*  626: 715 */           this.hasSpawner = true;
/*  627: 716 */           par1World.setBlock(var10, var13, var11, Blocks.mob_spawner, 0, 2);
/*  628: 717 */           TileEntityMobSpawner var12 = (TileEntityMobSpawner)par1World.getTileEntity(var10, var13, var11);
/*  629: 719 */           if (var12 != null) {
/*  630: 721 */             var12.func_145881_a().setMobID("Silverfish");
/*  631:     */           }
/*  632:     */         }
/*  633:     */       }
/*  634: 726 */       return true;
/*  635:     */     }
/*  636:     */   }
/*  637:     */   
/*  638:     */   public static class ChestCorridor
/*  639:     */     extends StructureStrongholdPieces.Stronghold
/*  640:     */   {
/*  641: 732 */     private static final WeightedRandomChestContent[] strongholdChestContents = { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
/*  642:     */     private boolean hasMadeChest;
/*  643:     */     private static final String __OBFID = "CL_00000487";
/*  644:     */     
/*  645:     */     public ChestCorridor() {}
/*  646:     */     
/*  647:     */     public ChestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  648:     */     {
/*  649: 740 */       super();
/*  650: 741 */       this.coordBaseMode = par4;
/*  651: 742 */       this.field_143013_d = getRandomDoor(par2Random);
/*  652: 743 */       this.boundingBox = par3StructureBoundingBox;
/*  653:     */     }
/*  654:     */     
/*  655:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  656:     */     {
/*  657: 748 */       super.func_143012_a(par1NBTTagCompound);
/*  658: 749 */       par1NBTTagCompound.setBoolean("Chest", this.hasMadeChest);
/*  659:     */     }
/*  660:     */     
/*  661:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  662:     */     {
/*  663: 754 */       super.func_143011_b(par1NBTTagCompound);
/*  664: 755 */       this.hasMadeChest = par1NBTTagCompound.getBoolean("Chest");
/*  665:     */     }
/*  666:     */     
/*  667:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  668:     */     {
/*  669: 760 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  670:     */     }
/*  671:     */     
/*  672:     */     public static ChestCorridor findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  673:     */     {
/*  674: 765 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
/*  675: 766 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new ChestCorridor(par6, par1Random, var7, par5) : null;
/*  676:     */     }
/*  677:     */     
/*  678:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  679:     */     {
/*  680: 771 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  681: 773 */         return false;
/*  682:     */       }
/*  683: 777 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  684: 778 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
/*  685: 779 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/*  686: 780 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.stonebrick, Blocks.stonebrick, false);
/*  687: 781 */       func_151550_a(par1World, Blocks.stone_slab, 5, 3, 1, 1, par3StructureBoundingBox);
/*  688: 782 */       func_151550_a(par1World, Blocks.stone_slab, 5, 3, 1, 5, par3StructureBoundingBox);
/*  689: 783 */       func_151550_a(par1World, Blocks.stone_slab, 5, 3, 2, 2, par3StructureBoundingBox);
/*  690: 784 */       func_151550_a(par1World, Blocks.stone_slab, 5, 3, 2, 4, par3StructureBoundingBox);
/*  691: 787 */       for (int var4 = 2; var4 <= 4; var4++) {
/*  692: 789 */         func_151550_a(par1World, Blocks.stone_slab, 5, 2, 1, var4, par3StructureBoundingBox);
/*  693:     */       }
/*  694: 792 */       if (!this.hasMadeChest)
/*  695:     */       {
/*  696: 794 */         var4 = getYWithOffset(2);
/*  697: 795 */         int var5 = getXWithOffset(3, 3);
/*  698: 796 */         int var6 = getZWithOffset(3, 3);
/*  699: 798 */         if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
/*  700:     */         {
/*  701: 800 */           this.hasMadeChest = true;
/*  702: 801 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, WeightedRandomChestContent.func_92080_a(strongholdChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 2 + par2Random.nextInt(2));
/*  703:     */         }
/*  704:     */       }
/*  705: 805 */       return true;
/*  706:     */     }
/*  707:     */   }
/*  708:     */   
/*  709:     */   public static class RoomCrossing
/*  710:     */     extends StructureStrongholdPieces.Stronghold
/*  711:     */   {
/*  712: 812 */     private static final WeightedRandomChestContent[] strongholdRoomCrossingChestContents = { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) };
/*  713:     */     protected int roomType;
/*  714:     */     private static final String __OBFID = "CL_00000496";
/*  715:     */     
/*  716:     */     public RoomCrossing() {}
/*  717:     */     
/*  718:     */     public RoomCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  719:     */     {
/*  720: 820 */       super();
/*  721: 821 */       this.coordBaseMode = par4;
/*  722: 822 */       this.field_143013_d = getRandomDoor(par2Random);
/*  723: 823 */       this.boundingBox = par3StructureBoundingBox;
/*  724: 824 */       this.roomType = par2Random.nextInt(5);
/*  725:     */     }
/*  726:     */     
/*  727:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  728:     */     {
/*  729: 829 */       super.func_143012_a(par1NBTTagCompound);
/*  730: 830 */       par1NBTTagCompound.setInteger("Type", this.roomType);
/*  731:     */     }
/*  732:     */     
/*  733:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  734:     */     {
/*  735: 835 */       super.func_143011_b(par1NBTTagCompound);
/*  736: 836 */       this.roomType = par1NBTTagCompound.getInteger("Type");
/*  737:     */     }
/*  738:     */     
/*  739:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  740:     */     {
/*  741: 841 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 4, 1);
/*  742: 842 */       getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
/*  743: 843 */       getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
/*  744:     */     }
/*  745:     */     
/*  746:     */     public static RoomCrossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  747:     */     {
/*  748: 848 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 7, 11, par5);
/*  749: 849 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new RoomCrossing(par6, par1Random, var7, par5) : null;
/*  750:     */     }
/*  751:     */     
/*  752:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  753:     */     {
/*  754: 854 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  755: 856 */         return false;
/*  756:     */       }
/*  757: 860 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  758: 861 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 1, 0);
/*  759: 862 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.air, Blocks.air, false);
/*  760: 863 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.air, Blocks.air, false);
/*  761: 864 */       func_151549_a(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.air, Blocks.air, false);
/*  762: 867 */       switch (this.roomType)
/*  763:     */       {
/*  764:     */       case 0: 
/*  765: 870 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 1, 5, par3StructureBoundingBox);
/*  766: 871 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 2, 5, par3StructureBoundingBox);
/*  767: 872 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 3, 5, par3StructureBoundingBox);
/*  768: 873 */         func_151550_a(par1World, Blocks.torch, 0, 4, 3, 5, par3StructureBoundingBox);
/*  769: 874 */         func_151550_a(par1World, Blocks.torch, 0, 6, 3, 5, par3StructureBoundingBox);
/*  770: 875 */         func_151550_a(par1World, Blocks.torch, 0, 5, 3, 4, par3StructureBoundingBox);
/*  771: 876 */         func_151550_a(par1World, Blocks.torch, 0, 5, 3, 6, par3StructureBoundingBox);
/*  772: 877 */         func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 4, par3StructureBoundingBox);
/*  773: 878 */         func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 5, par3StructureBoundingBox);
/*  774: 879 */         func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 6, par3StructureBoundingBox);
/*  775: 880 */         func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 4, par3StructureBoundingBox);
/*  776: 881 */         func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 5, par3StructureBoundingBox);
/*  777: 882 */         func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 6, par3StructureBoundingBox);
/*  778: 883 */         func_151550_a(par1World, Blocks.stone_slab, 0, 5, 1, 4, par3StructureBoundingBox);
/*  779: 884 */         func_151550_a(par1World, Blocks.stone_slab, 0, 5, 1, 6, par3StructureBoundingBox);
/*  780: 885 */         break;
/*  781:     */       case 1: 
/*  782: 888 */         for (int var4 = 0; var4 < 5; var4++)
/*  783:     */         {
/*  784: 890 */           func_151550_a(par1World, Blocks.stonebrick, 0, 3, 1, 3 + var4, par3StructureBoundingBox);
/*  785: 891 */           func_151550_a(par1World, Blocks.stonebrick, 0, 7, 1, 3 + var4, par3StructureBoundingBox);
/*  786: 892 */           func_151550_a(par1World, Blocks.stonebrick, 0, 3 + var4, 1, 3, par3StructureBoundingBox);
/*  787: 893 */           func_151550_a(par1World, Blocks.stonebrick, 0, 3 + var4, 1, 7, par3StructureBoundingBox);
/*  788:     */         }
/*  789: 896 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 1, 5, par3StructureBoundingBox);
/*  790: 897 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 2, 5, par3StructureBoundingBox);
/*  791: 898 */         func_151550_a(par1World, Blocks.stonebrick, 0, 5, 3, 5, par3StructureBoundingBox);
/*  792: 899 */         func_151550_a(par1World, Blocks.flowing_water, 0, 5, 4, 5, par3StructureBoundingBox);
/*  793: 900 */         break;
/*  794:     */       case 2: 
/*  795: 903 */         for (int var4 = 1; var4 <= 9; var4++)
/*  796:     */         {
/*  797: 905 */           func_151550_a(par1World, Blocks.cobblestone, 0, 1, 3, var4, par3StructureBoundingBox);
/*  798: 906 */           func_151550_a(par1World, Blocks.cobblestone, 0, 9, 3, var4, par3StructureBoundingBox);
/*  799:     */         }
/*  800: 909 */         for (var4 = 1; var4 <= 9; var4++)
/*  801:     */         {
/*  802: 911 */           func_151550_a(par1World, Blocks.cobblestone, 0, var4, 3, 1, par3StructureBoundingBox);
/*  803: 912 */           func_151550_a(par1World, Blocks.cobblestone, 0, var4, 3, 9, par3StructureBoundingBox);
/*  804:     */         }
/*  805: 915 */         func_151550_a(par1World, Blocks.cobblestone, 0, 5, 1, 4, par3StructureBoundingBox);
/*  806: 916 */         func_151550_a(par1World, Blocks.cobblestone, 0, 5, 1, 6, par3StructureBoundingBox);
/*  807: 917 */         func_151550_a(par1World, Blocks.cobblestone, 0, 5, 3, 4, par3StructureBoundingBox);
/*  808: 918 */         func_151550_a(par1World, Blocks.cobblestone, 0, 5, 3, 6, par3StructureBoundingBox);
/*  809: 919 */         func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 5, par3StructureBoundingBox);
/*  810: 920 */         func_151550_a(par1World, Blocks.cobblestone, 0, 6, 1, 5, par3StructureBoundingBox);
/*  811: 921 */         func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 5, par3StructureBoundingBox);
/*  812: 922 */         func_151550_a(par1World, Blocks.cobblestone, 0, 6, 3, 5, par3StructureBoundingBox);
/*  813: 924 */         for (var4 = 1; var4 <= 3; var4++)
/*  814:     */         {
/*  815: 926 */           func_151550_a(par1World, Blocks.cobblestone, 0, 4, var4, 4, par3StructureBoundingBox);
/*  816: 927 */           func_151550_a(par1World, Blocks.cobblestone, 0, 6, var4, 4, par3StructureBoundingBox);
/*  817: 928 */           func_151550_a(par1World, Blocks.cobblestone, 0, 4, var4, 6, par3StructureBoundingBox);
/*  818: 929 */           func_151550_a(par1World, Blocks.cobblestone, 0, 6, var4, 6, par3StructureBoundingBox);
/*  819:     */         }
/*  820: 932 */         func_151550_a(par1World, Blocks.torch, 0, 5, 3, 5, par3StructureBoundingBox);
/*  821: 934 */         for (var4 = 2; var4 <= 8; var4++)
/*  822:     */         {
/*  823: 936 */           func_151550_a(par1World, Blocks.planks, 0, 2, 3, var4, par3StructureBoundingBox);
/*  824: 937 */           func_151550_a(par1World, Blocks.planks, 0, 3, 3, var4, par3StructureBoundingBox);
/*  825: 939 */           if ((var4 <= 3) || (var4 >= 7))
/*  826:     */           {
/*  827: 941 */             func_151550_a(par1World, Blocks.planks, 0, 4, 3, var4, par3StructureBoundingBox);
/*  828: 942 */             func_151550_a(par1World, Blocks.planks, 0, 5, 3, var4, par3StructureBoundingBox);
/*  829: 943 */             func_151550_a(par1World, Blocks.planks, 0, 6, 3, var4, par3StructureBoundingBox);
/*  830:     */           }
/*  831: 946 */           func_151550_a(par1World, Blocks.planks, 0, 7, 3, var4, par3StructureBoundingBox);
/*  832: 947 */           func_151550_a(par1World, Blocks.planks, 0, 8, 3, var4, par3StructureBoundingBox);
/*  833:     */         }
/*  834: 950 */         func_151550_a(par1World, Blocks.ladder, func_151555_a(Blocks.ladder, 4), 9, 1, 3, par3StructureBoundingBox);
/*  835: 951 */         func_151550_a(par1World, Blocks.ladder, func_151555_a(Blocks.ladder, 4), 9, 2, 3, par3StructureBoundingBox);
/*  836: 952 */         func_151550_a(par1World, Blocks.ladder, func_151555_a(Blocks.ladder, 4), 9, 3, 3, par3StructureBoundingBox);
/*  837: 953 */         generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, WeightedRandomChestContent.func_92080_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) }), 1 + par2Random.nextInt(4));
/*  838:     */       }
/*  839: 956 */       return true;
/*  840:     */     }
/*  841:     */   }
/*  842:     */   
/*  843:     */   public static class StairsStraight
/*  844:     */     extends StructureStrongholdPieces.Stronghold
/*  845:     */   {
/*  846:     */     private static final String __OBFID = "CL_00000501";
/*  847:     */     
/*  848:     */     public StairsStraight() {}
/*  849:     */     
/*  850:     */     public StairsStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  851:     */     {
/*  852: 969 */       super();
/*  853: 970 */       this.coordBaseMode = par4;
/*  854: 971 */       this.field_143013_d = getRandomDoor(par2Random);
/*  855: 972 */       this.boundingBox = par3StructureBoundingBox;
/*  856:     */     }
/*  857:     */     
/*  858:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  859:     */     {
/*  860: 977 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  861:     */     }
/*  862:     */     
/*  863:     */     public static StairsStraight findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  864:     */     {
/*  865: 982 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);
/*  866: 983 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new StairsStraight(par6, par1Random, var7, par5) : null;
/*  867:     */     }
/*  868:     */     
/*  869:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  870:     */     {
/*  871: 988 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  872: 990 */         return false;
/*  873:     */       }
/*  874: 994 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  875: 995 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 7, 0);
/*  876: 996 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
/*  877: 997 */       int var4 = func_151555_a(Blocks.stone_stairs, 2);
/*  878: 999 */       for (int var5 = 0; var5 < 6; var5++)
/*  879:     */       {
/*  880:1001 */         func_151550_a(par1World, Blocks.stone_stairs, var4, 1, 6 - var5, 1 + var5, par3StructureBoundingBox);
/*  881:1002 */         func_151550_a(par1World, Blocks.stone_stairs, var4, 2, 6 - var5, 1 + var5, par3StructureBoundingBox);
/*  882:1003 */         func_151550_a(par1World, Blocks.stone_stairs, var4, 3, 6 - var5, 1 + var5, par3StructureBoundingBox);
/*  883:1005 */         if (var5 < 5)
/*  884:     */         {
/*  885:1007 */           func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5 - var5, 1 + var5, par3StructureBoundingBox);
/*  886:1008 */           func_151550_a(par1World, Blocks.stonebrick, 0, 2, 5 - var5, 1 + var5, par3StructureBoundingBox);
/*  887:1009 */           func_151550_a(par1World, Blocks.stonebrick, 0, 3, 5 - var5, 1 + var5, par3StructureBoundingBox);
/*  888:     */         }
/*  889:     */       }
/*  890:1013 */       return true;
/*  891:     */     }
/*  892:     */   }
/*  893:     */   
/*  894:     */   public static class Stairs2
/*  895:     */     extends StructureStrongholdPieces.Stairs
/*  896:     */   {
/*  897:     */     public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
/*  898:     */     public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
/*  899:1022 */     public List field_75026_c = new ArrayList();
/*  900:     */     private static final String __OBFID = "CL_00000499";
/*  901:     */     
/*  902:     */     public Stairs2() {}
/*  903:     */     
/*  904:     */     public Stairs2(int par1, Random par2Random, int par3, int par4)
/*  905:     */     {
/*  906:1029 */       super(par2Random, par3, par4);
/*  907:     */     }
/*  908:     */     
/*  909:     */     public ChunkPosition func_151553_a()
/*  910:     */     {
/*  911:1034 */       return this.strongholdPortalRoom != null ? this.strongholdPortalRoom.func_151553_a() : super.func_151553_a();
/*  912:     */     }
/*  913:     */   }
/*  914:     */   
/*  915:     */   public static class Prison
/*  916:     */     extends StructureStrongholdPieces.Stronghold
/*  917:     */   {
/*  918:     */     private static final String __OBFID = "CL_00000494";
/*  919:     */     
/*  920:     */     public Prison() {}
/*  921:     */     
/*  922:     */     public Prison(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  923:     */     {
/*  924:1046 */       super();
/*  925:1047 */       this.coordBaseMode = par4;
/*  926:1048 */       this.field_143013_d = getRandomDoor(par2Random);
/*  927:1049 */       this.boundingBox = par3StructureBoundingBox;
/*  928:     */     }
/*  929:     */     
/*  930:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  931:     */     {
/*  932:1054 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  933:     */     }
/*  934:     */     
/*  935:     */     public static Prison findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  936:     */     {
/*  937:1059 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);
/*  938:1060 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Prison(par6, par1Random, var7, par5) : null;
/*  939:     */     }
/*  940:     */     
/*  941:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  942:     */     {
/*  943:1065 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  944:1067 */         return false;
/*  945:     */       }
/*  946:1071 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, StructureStrongholdPieces.strongholdStones);
/*  947:1072 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
/*  948:1073 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.air, Blocks.air, false);
/*  949:1074 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  950:1075 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  951:1076 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  952:1077 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, StructureStrongholdPieces.strongholdStones);
/*  953:1078 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, Blocks.iron_bars, Blocks.iron_bars, false);
/*  954:1079 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, Blocks.iron_bars, Blocks.iron_bars, false);
/*  955:1080 */       func_151550_a(par1World, Blocks.iron_bars, 0, 4, 3, 2, par3StructureBoundingBox);
/*  956:1081 */       func_151550_a(par1World, Blocks.iron_bars, 0, 4, 3, 8, par3StructureBoundingBox);
/*  957:1082 */       func_151550_a(par1World, Blocks.iron_door, func_151555_a(Blocks.iron_door, 3), 4, 1, 2, par3StructureBoundingBox);
/*  958:1083 */       func_151550_a(par1World, Blocks.iron_door, func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
/*  959:1084 */       func_151550_a(par1World, Blocks.iron_door, func_151555_a(Blocks.iron_door, 3), 4, 1, 8, par3StructureBoundingBox);
/*  960:1085 */       func_151550_a(par1World, Blocks.iron_door, func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
/*  961:1086 */       return true;
/*  962:     */     }
/*  963:     */   }
/*  964:     */   
/*  965:     */   public static class LeftTurn
/*  966:     */     extends StructureStrongholdPieces.Stronghold
/*  967:     */   {
/*  968:     */     private static final String __OBFID = "CL_00000490";
/*  969:     */     
/*  970:     */     public LeftTurn() {}
/*  971:     */     
/*  972:     */     public LeftTurn(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  973:     */     {
/*  974:1099 */       super();
/*  975:1100 */       this.coordBaseMode = par4;
/*  976:1101 */       this.field_143013_d = getRandomDoor(par2Random);
/*  977:1102 */       this.boundingBox = par3StructureBoundingBox;
/*  978:     */     }
/*  979:     */     
/*  980:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  981:     */     {
/*  982:1107 */       if ((this.coordBaseMode != 2) && (this.coordBaseMode != 3)) {
/*  983:1109 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  984:     */       } else {
/*  985:1113 */         getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/*  986:     */       }
/*  987:     */     }
/*  988:     */     
/*  989:     */     public static LeftTurn findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  990:     */     {
/*  991:1119 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 5, par5);
/*  992:1120 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new LeftTurn(par6, par1Random, var7, par5) : null;
/*  993:     */     }
/*  994:     */     
/*  995:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  996:     */     {
/*  997:1125 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/*  998:1127 */         return false;
/*  999:     */       }
/* 1000:1131 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1001:1132 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
/* 1002:1134 */       if ((this.coordBaseMode != 2) && (this.coordBaseMode != 3)) {
/* 1003:1136 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
/* 1004:     */       } else {
/* 1005:1140 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
/* 1006:     */       }
/* 1007:1143 */       return true;
/* 1008:     */     }
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public static class RightTurn
/* 1012:     */     extends StructureStrongholdPieces.LeftTurn
/* 1013:     */   {
/* 1014:     */     private static final String __OBFID = "CL_00000495";
/* 1015:     */     
/* 1016:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 1017:     */     {
/* 1018:1154 */       if ((this.coordBaseMode != 2) && (this.coordBaseMode != 3)) {
/* 1019:1156 */         getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/* 1020:     */       } else {
/* 1021:1160 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
/* 1022:     */       }
/* 1023:     */     }
/* 1024:     */     
/* 1025:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1026:     */     {
/* 1027:1166 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 1028:1168 */         return false;
/* 1029:     */       }
/* 1030:1172 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1031:1173 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
/* 1032:1175 */       if ((this.coordBaseMode != 2) && (this.coordBaseMode != 3)) {
/* 1033:1177 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
/* 1034:     */       } else {
/* 1035:1181 */         func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
/* 1036:     */       }
/* 1037:1184 */       return true;
/* 1038:     */     }
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   static class Stones
/* 1042:     */     extends StructureComponent.BlockSelector
/* 1043:     */   {
/* 1044:     */     private static final String __OBFID = "CL_00000497";
/* 1045:     */     
/* 1046:     */     private Stones() {}
/* 1047:     */     
/* 1048:     */     public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
/* 1049:     */     {
/* 1050:1197 */       if (par5)
/* 1051:     */       {
/* 1052:1199 */         this.field_151562_a = Blocks.stonebrick;
/* 1053:1200 */         float var6 = par1Random.nextFloat();
/* 1054:1202 */         if (var6 < 0.2F)
/* 1055:     */         {
/* 1056:1204 */           this.selectedBlockMetaData = 2;
/* 1057:     */         }
/* 1058:1206 */         else if (var6 < 0.5F)
/* 1059:     */         {
/* 1060:1208 */           this.selectedBlockMetaData = 1;
/* 1061:     */         }
/* 1062:1210 */         else if (var6 < 0.55F)
/* 1063:     */         {
/* 1064:1212 */           this.field_151562_a = Blocks.monster_egg;
/* 1065:1213 */           this.selectedBlockMetaData = 2;
/* 1066:     */         }
/* 1067:     */         else
/* 1068:     */         {
/* 1069:1217 */           this.selectedBlockMetaData = 0;
/* 1070:     */         }
/* 1071:     */       }
/* 1072:     */       else
/* 1073:     */       {
/* 1074:1222 */         this.field_151562_a = Blocks.air;
/* 1075:1223 */         this.selectedBlockMetaData = 0;
/* 1076:     */       }
/* 1077:     */     }
/* 1078:     */     
/* 1079:     */     Stones(Object par1StructureStrongholdPieceWeight2)
/* 1080:     */     {
/* 1081:1229 */       this();
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   static abstract class Stronghold
/* 1086:     */     extends StructureComponent
/* 1087:     */   {
/* 1088:     */     protected Door field_143013_d;
/* 1089:     */     private static final String __OBFID = "CL_00000503";
/* 1090:     */     
/* 1091:     */     public Stronghold()
/* 1092:     */     {
/* 1093:1240 */       this.field_143013_d = Door.OPENING;
/* 1094:     */     }
/* 1095:     */     
/* 1096:     */     protected Stronghold(int par1)
/* 1097:     */     {
/* 1098:1245 */       super();
/* 1099:1246 */       this.field_143013_d = Door.OPENING;
/* 1100:     */     }
/* 1101:     */     
/* 1102:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1103:     */     {
/* 1104:1251 */       par1NBTTagCompound.setString("EntryDoor", this.field_143013_d.name());
/* 1105:     */     }
/* 1106:     */     
/* 1107:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1108:     */     {
/* 1109:1256 */       this.field_143013_d = Door.valueOf(par1NBTTagCompound.getString("EntryDoor"));
/* 1110:     */     }
/* 1111:     */     
/* 1112:     */     protected void placeDoor(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, Door par4EnumDoor, int par5, int par6, int par7)
/* 1113:     */     {
/* 1114:1261 */       switch (StructureStrongholdPieces.SwitchDoor.doorEnum[par4EnumDoor.ordinal()])
/* 1115:     */       {
/* 1116:     */       case 1: 
/* 1117:     */       default: 
/* 1118:1265 */         func_151549_a(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, Blocks.air, Blocks.air, false);
/* 1119:1266 */         break;
/* 1120:     */       case 2: 
/* 1121:1269 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6, par7, par3StructureBoundingBox);
/* 1122:1270 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
/* 1123:1271 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
/* 1124:1272 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
/* 1125:1273 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
/* 1126:1274 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
/* 1127:1275 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
/* 1128:1276 */         func_151550_a(par1World, Blocks.wooden_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
/* 1129:1277 */         func_151550_a(par1World, Blocks.wooden_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
/* 1130:1278 */         break;
/* 1131:     */       case 3: 
/* 1132:1281 */         func_151550_a(par1World, Blocks.air, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
/* 1133:1282 */         func_151550_a(par1World, Blocks.air, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
/* 1134:1283 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6, par7, par3StructureBoundingBox);
/* 1135:1284 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
/* 1136:1285 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
/* 1137:1286 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
/* 1138:1287 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
/* 1139:1288 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
/* 1140:1289 */         func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
/* 1141:1290 */         break;
/* 1142:     */       case 4: 
/* 1143:1293 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6, par7, par3StructureBoundingBox);
/* 1144:1294 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
/* 1145:1295 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
/* 1146:1296 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
/* 1147:1297 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
/* 1148:1298 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
/* 1149:1299 */         func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
/* 1150:1300 */         func_151550_a(par1World, Blocks.iron_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
/* 1151:1301 */         func_151550_a(par1World, Blocks.iron_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
/* 1152:1302 */         func_151550_a(par1World, Blocks.stone_button, func_151555_a(Blocks.stone_button, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
/* 1153:1303 */         func_151550_a(par1World, Blocks.stone_button, func_151555_a(Blocks.stone_button, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
/* 1154:     */       }
/* 1155:     */     }
/* 1156:     */     
/* 1157:     */     protected Door getRandomDoor(Random par1Random)
/* 1158:     */     {
/* 1159:1309 */       int var2 = par1Random.nextInt(5);
/* 1160:1311 */       switch (var2)
/* 1161:     */       {
/* 1162:     */       case 0: 
/* 1163:     */       case 1: 
/* 1164:     */       default: 
/* 1165:1316 */         return Door.OPENING;
/* 1166:     */       case 2: 
/* 1167:1319 */         return Door.WOOD_DOOR;
/* 1168:     */       case 3: 
/* 1169:1322 */         return Door.GRATES;
/* 1170:     */       }
/* 1171:1325 */       return Door.IRON_DOOR;
/* 1172:     */     }
/* 1173:     */     
/* 1174:     */     protected StructureComponent getNextComponentNormal(StructureStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
/* 1175:     */     {
/* 1176:1331 */       switch (this.coordBaseMode)
/* 1177:     */       {
/* 1178:     */       case 0: 
/* 1179:1334 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType());
/* 1180:     */       case 1: 
/* 1181:1337 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, getComponentType());
/* 1182:     */       case 2: 
/* 1183:1340 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType());
/* 1184:     */       case 3: 
/* 1185:1343 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, getComponentType());
/* 1186:     */       }
/* 1187:1346 */       return null;
/* 1188:     */     }
/* 1189:     */     
/* 1190:     */     protected StructureComponent getNextComponentX(StructureStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
/* 1191:     */     {
/* 1192:1352 */       switch (this.coordBaseMode)
/* 1193:     */       {
/* 1194:     */       case 0: 
/* 1195:1355 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType());
/* 1196:     */       case 1: 
/* 1197:1358 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType());
/* 1198:     */       case 2: 
/* 1199:1361 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType());
/* 1200:     */       case 3: 
/* 1201:1364 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType());
/* 1202:     */       }
/* 1203:1367 */       return null;
/* 1204:     */     }
/* 1205:     */     
/* 1206:     */     protected StructureComponent getNextComponentZ(StructureStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
/* 1207:     */     {
/* 1208:1373 */       switch (this.coordBaseMode)
/* 1209:     */       {
/* 1210:     */       case 0: 
/* 1211:1376 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType());
/* 1212:     */       case 1: 
/* 1213:1379 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType());
/* 1214:     */       case 2: 
/* 1215:1382 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType());
/* 1216:     */       case 3: 
/* 1217:1385 */         return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType());
/* 1218:     */       }
/* 1219:1388 */       return null;
/* 1220:     */     }
/* 1221:     */     
/* 1222:     */     protected static boolean canStrongholdGoDeeper(StructureBoundingBox par0StructureBoundingBox)
/* 1223:     */     {
/* 1224:1394 */       return (par0StructureBoundingBox != null) && (par0StructureBoundingBox.minY > 10);
/* 1225:     */     }
/* 1226:     */     
/* 1227:     */     public static enum Door
/* 1228:     */     {
/* 1229:1399 */       OPENING("OPENING", 0),  WOOD_DOOR("WOOD_DOOR", 1),  GRATES("GRATES", 2),  IRON_DOOR("IRON_DOOR", 3);
/* 1230:     */       
/* 1231:1404 */       private static final Door[] $VALUES = { OPENING, WOOD_DOOR, GRATES, IRON_DOOR };
/* 1232:     */       private static final String __OBFID = "CL_00000504";
/* 1233:     */       
/* 1234:     */       private Door(String par1Str, int par2) {}
/* 1235:     */     }
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public static class Crossing
/* 1239:     */     extends StructureStrongholdPieces.Stronghold
/* 1240:     */   {
/* 1241:     */     private boolean field_74996_b;
/* 1242:     */     private boolean field_74997_c;
/* 1243:     */     private boolean field_74995_d;
/* 1244:     */     private boolean field_74999_h;
/* 1245:     */     private static final String __OBFID = "CL_00000489";
/* 1246:     */     
/* 1247:     */     public Crossing() {}
/* 1248:     */     
/* 1249:     */     public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 1250:     */     {
/* 1251:1423 */       super();
/* 1252:1424 */       this.coordBaseMode = par4;
/* 1253:1425 */       this.field_143013_d = getRandomDoor(par2Random);
/* 1254:1426 */       this.boundingBox = par3StructureBoundingBox;
/* 1255:1427 */       this.field_74996_b = par2Random.nextBoolean();
/* 1256:1428 */       this.field_74997_c = par2Random.nextBoolean();
/* 1257:1429 */       this.field_74995_d = par2Random.nextBoolean();
/* 1258:1430 */       this.field_74999_h = (par2Random.nextInt(3) > 0);
/* 1259:     */     }
/* 1260:     */     
/* 1261:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1262:     */     {
/* 1263:1435 */       super.func_143012_a(par1NBTTagCompound);
/* 1264:1436 */       par1NBTTagCompound.setBoolean("leftLow", this.field_74996_b);
/* 1265:1437 */       par1NBTTagCompound.setBoolean("leftHigh", this.field_74997_c);
/* 1266:1438 */       par1NBTTagCompound.setBoolean("rightLow", this.field_74995_d);
/* 1267:1439 */       par1NBTTagCompound.setBoolean("rightHigh", this.field_74999_h);
/* 1268:     */     }
/* 1269:     */     
/* 1270:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1271:     */     {
/* 1272:1444 */       super.func_143011_b(par1NBTTagCompound);
/* 1273:1445 */       this.field_74996_b = par1NBTTagCompound.getBoolean("leftLow");
/* 1274:1446 */       this.field_74997_c = par1NBTTagCompound.getBoolean("leftHigh");
/* 1275:1447 */       this.field_74995_d = par1NBTTagCompound.getBoolean("rightLow");
/* 1276:1448 */       this.field_74999_h = par1NBTTagCompound.getBoolean("rightHigh");
/* 1277:     */     }
/* 1278:     */     
/* 1279:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 1280:     */     {
/* 1281:1453 */       int var4 = 3;
/* 1282:1454 */       int var5 = 5;
/* 1283:1456 */       if ((this.coordBaseMode == 1) || (this.coordBaseMode == 2))
/* 1284:     */       {
/* 1285:1458 */         var4 = 8 - var4;
/* 1286:1459 */         var5 = 8 - var5;
/* 1287:     */       }
/* 1288:1462 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 5, 1);
/* 1289:1464 */       if (this.field_74996_b) {
/* 1290:1466 */         getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, var4, 1);
/* 1291:     */       }
/* 1292:1469 */       if (this.field_74997_c) {
/* 1293:1471 */         getNextComponentX((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, var5, 7);
/* 1294:     */       }
/* 1295:1474 */       if (this.field_74995_d) {
/* 1296:1476 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, var4, 1);
/* 1297:     */       }
/* 1298:1479 */       if (this.field_74999_h) {
/* 1299:1481 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, var5, 7);
/* 1300:     */       }
/* 1301:     */     }
/* 1302:     */     
/* 1303:     */     public static Crossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/* 1304:     */     {
/* 1305:1487 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -3, 0, 10, 9, 11, par5);
/* 1306:1488 */       return (canStrongholdGoDeeper(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Crossing(par6, par1Random, var7, par5) : null;
/* 1307:     */     }
/* 1308:     */     
/* 1309:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1310:     */     {
/* 1311:1493 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 1312:1495 */         return false;
/* 1313:     */       }
/* 1314:1499 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 8, 10, true, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1315:1500 */       placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 3, 0);
/* 1316:1502 */       if (this.field_74996_b) {
/* 1317:1504 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.air, Blocks.air, false);
/* 1318:     */       }
/* 1319:1507 */       if (this.field_74995_d) {
/* 1320:1509 */         func_151549_a(par1World, par3StructureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.air, Blocks.air, false);
/* 1321:     */       }
/* 1322:1512 */       if (this.field_74997_c) {
/* 1323:1514 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.air, Blocks.air, false);
/* 1324:     */       }
/* 1325:1517 */       if (this.field_74999_h) {
/* 1326:1519 */         func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.air, Blocks.air, false);
/* 1327:     */       }
/* 1328:1522 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.air, Blocks.air, false);
/* 1329:1523 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 2, 1, 8, 2, 6, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1330:1524 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 4, 4, 9, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1331:1525 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 5, 8, 4, 9, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1332:1526 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 4, 7, 3, 4, 9, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1333:1527 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 5, 3, 3, 6, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1334:1528 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1335:1529 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1336:1530 */       fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 1, 7, 7, 1, 8, false, par2Random, StructureStrongholdPieces.strongholdStones);
/* 1337:1531 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1338:1532 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1339:1533 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1340:1534 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
/* 1341:1535 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
/* 1342:1536 */       func_151550_a(par1World, Blocks.torch, 0, 6, 5, 6, par3StructureBoundingBox);
/* 1343:1537 */       return true;
/* 1344:     */     }
/* 1345:     */   }
/* 1346:     */   
/* 1347:     */   public static class Corridor
/* 1348:     */     extends StructureStrongholdPieces.Stronghold
/* 1349:     */   {
/* 1350:     */     private int field_74993_a;
/* 1351:     */     private static final String __OBFID = "CL_00000488";
/* 1352:     */     
/* 1353:     */     public Corridor() {}
/* 1354:     */     
/* 1355:     */     public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 1356:     */     {
/* 1357:1551 */       super();
/* 1358:1552 */       this.coordBaseMode = par4;
/* 1359:1553 */       this.boundingBox = par3StructureBoundingBox;
/* 1360:1554 */       this.field_74993_a = ((par4 != 2) && (par4 != 0) ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize());
/* 1361:     */     }
/* 1362:     */     
/* 1363:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1364:     */     {
/* 1365:1559 */       super.func_143012_a(par1NBTTagCompound);
/* 1366:1560 */       par1NBTTagCompound.setInteger("Steps", this.field_74993_a);
/* 1367:     */     }
/* 1368:     */     
/* 1369:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1370:     */     {
/* 1371:1565 */       super.func_143011_b(par1NBTTagCompound);
/* 1372:1566 */       this.field_74993_a = par1NBTTagCompound.getInteger("Steps");
/* 1373:     */     }
/* 1374:     */     
/* 1375:     */     public static StructureBoundingBox func_74992_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
/* 1376:     */     {
/* 1377:1571 */       boolean var6 = true;
/* 1378:1572 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
/* 1379:1573 */       StructureComponent var8 = StructureComponent.findIntersecting(par0List, var7);
/* 1380:1575 */       if (var8 == null) {
/* 1381:1577 */         return null;
/* 1382:     */       }
/* 1383:1581 */       if (var8.getBoundingBox().minY == var7.minY) {
/* 1384:1583 */         for (int var9 = 3; var9 >= 1; var9--)
/* 1385:     */         {
/* 1386:1585 */           var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9 - 1, par5);
/* 1387:1587 */           if (!var8.getBoundingBox().intersectsWith(var7)) {
/* 1388:1589 */             return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9, par5);
/* 1389:     */           }
/* 1390:     */         }
/* 1391:     */       }
/* 1392:1594 */       return null;
/* 1393:     */     }
/* 1394:     */     
/* 1395:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1396:     */     {
/* 1397:1600 */       if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
/* 1398:1602 */         return false;
/* 1399:     */       }
/* 1400:1606 */       for (int var4 = 0; var4 < this.field_74993_a; var4++)
/* 1401:     */       {
/* 1402:1608 */         func_151550_a(par1World, Blocks.stonebrick, 0, 0, 0, var4, par3StructureBoundingBox);
/* 1403:1609 */         func_151550_a(par1World, Blocks.stonebrick, 0, 1, 0, var4, par3StructureBoundingBox);
/* 1404:1610 */         func_151550_a(par1World, Blocks.stonebrick, 0, 2, 0, var4, par3StructureBoundingBox);
/* 1405:1611 */         func_151550_a(par1World, Blocks.stonebrick, 0, 3, 0, var4, par3StructureBoundingBox);
/* 1406:1612 */         func_151550_a(par1World, Blocks.stonebrick, 0, 4, 0, var4, par3StructureBoundingBox);
/* 1407:1614 */         for (int var5 = 1; var5 <= 3; var5++)
/* 1408:     */         {
/* 1409:1616 */           func_151550_a(par1World, Blocks.stonebrick, 0, 0, var5, var4, par3StructureBoundingBox);
/* 1410:1617 */           func_151550_a(par1World, Blocks.air, 0, 1, var5, var4, par3StructureBoundingBox);
/* 1411:1618 */           func_151550_a(par1World, Blocks.air, 0, 2, var5, var4, par3StructureBoundingBox);
/* 1412:1619 */           func_151550_a(par1World, Blocks.air, 0, 3, var5, var4, par3StructureBoundingBox);
/* 1413:1620 */           func_151550_a(par1World, Blocks.stonebrick, 0, 4, var5, var4, par3StructureBoundingBox);
/* 1414:     */         }
/* 1415:1623 */         func_151550_a(par1World, Blocks.stonebrick, 0, 0, 4, var4, par3StructureBoundingBox);
/* 1416:1624 */         func_151550_a(par1World, Blocks.stonebrick, 0, 1, 4, var4, par3StructureBoundingBox);
/* 1417:1625 */         func_151550_a(par1World, Blocks.stonebrick, 0, 2, 4, var4, par3StructureBoundingBox);
/* 1418:1626 */         func_151550_a(par1World, Blocks.stonebrick, 0, 3, 4, var4, par3StructureBoundingBox);
/* 1419:1627 */         func_151550_a(par1World, Blocks.stonebrick, 0, 4, 4, var4, par3StructureBoundingBox);
/* 1420:     */       }
/* 1421:1630 */       return true;
/* 1422:     */     }
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   static final class SwitchDoor
/* 1426:     */   {
/* 1427:1637 */     static final int[] doorEnum = new int[StructureStrongholdPieces.Stronghold.Door.values().length];
/* 1428:     */     private static final String __OBFID = "CL_00000486";
/* 1429:     */     
/* 1430:     */     static
/* 1431:     */     {
/* 1432:     */       try
/* 1433:     */       {
/* 1434:1644 */         doorEnum[StructureStrongholdPieces.Stronghold.Door.OPENING.ordinal()] = 1;
/* 1435:     */       }
/* 1436:     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 1437:     */       try
/* 1438:     */       {
/* 1439:1653 */         doorEnum[StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR.ordinal()] = 2;
/* 1440:     */       }
/* 1441:     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 1442:     */       try
/* 1443:     */       {
/* 1444:1662 */         doorEnum[StructureStrongholdPieces.Stronghold.Door.GRATES.ordinal()] = 3;
/* 1445:     */       }
/* 1446:     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/* 1447:     */       try
/* 1448:     */       {
/* 1449:1671 */         doorEnum[StructureStrongholdPieces.Stronghold.Door.IRON_DOOR.ordinal()] = 4;
/* 1450:     */       }
/* 1451:     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/* 1452:     */     }
/* 1453:     */   }
/* 1454:     */   
/* 1455:     */   static class PieceWeight
/* 1456:     */   {
/* 1457:     */     public Class pieceClass;
/* 1458:     */     public final int pieceWeight;
/* 1459:     */     public int instancesSpawned;
/* 1460:     */     public int instancesLimit;
/* 1461:     */     private static final String __OBFID = "CL_00000492";
/* 1462:     */     
/* 1463:     */     public PieceWeight(Class par1Class, int par2, int par3)
/* 1464:     */     {
/* 1465:1690 */       this.pieceClass = par1Class;
/* 1466:1691 */       this.pieceWeight = par2;
/* 1467:1692 */       this.instancesLimit = par3;
/* 1468:     */     }
/* 1469:     */     
/* 1470:     */     public boolean canSpawnMoreStructuresOfType(int par1)
/* 1471:     */     {
/* 1472:1697 */       return (this.instancesLimit == 0) || (this.instancesSpawned < this.instancesLimit);
/* 1473:     */     }
/* 1474:     */     
/* 1475:     */     public boolean canSpawnMoreStructures()
/* 1476:     */     {
/* 1477:1702 */       return (this.instancesLimit == 0) || (this.instancesSpawned < this.instancesLimit);
/* 1478:     */     }
/* 1479:     */   }
/* 1480:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureStrongholdPieces
 * JD-Core Version:    0.7.0.1
 */