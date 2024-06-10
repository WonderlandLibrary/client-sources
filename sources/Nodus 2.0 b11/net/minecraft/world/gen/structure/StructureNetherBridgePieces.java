/*    1:     */ package net.minecraft.world.gen.structure;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Random;
/*    7:     */ import net.minecraft.block.BlockLiquid;
/*    8:     */ import net.minecraft.init.Blocks;
/*    9:     */ import net.minecraft.init.Items;
/*   10:     */ import net.minecraft.nbt.NBTTagCompound;
/*   11:     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*   12:     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*   13:     */ import net.minecraft.util.WeightedRandomChestContent;
/*   14:     */ import net.minecraft.world.World;
/*   15:     */ 
/*   16:     */ public class StructureNetherBridgePieces
/*   17:     */ {
/*   18:  16 */   private static final PieceWeight[] primaryComponents = { new PieceWeight(Straight.class, 30, 0, true), new PieceWeight(Crossing3.class, 10, 4), new PieceWeight(Crossing.class, 10, 4), new PieceWeight(Stairs.class, 10, 3), new PieceWeight(Throne.class, 5, 2), new PieceWeight(Entrance.class, 5, 1) };
/*   19:  17 */   private static final PieceWeight[] secondaryComponents = { new PieceWeight(Corridor5.class, 25, 0, true), new PieceWeight(Crossing2.class, 15, 5), new PieceWeight(Corridor2.class, 5, 10), new PieceWeight(Corridor.class, 5, 10), new PieceWeight(Corridor3.class, 10, 3, true), new PieceWeight(Corridor4.class, 7, 2), new PieceWeight(NetherStalkRoom.class, 5, 2) };
/*   20:     */   private static final String __OBFID = "CL_00000453";
/*   21:     */   
/*   22:     */   public static void func_143049_a()
/*   23:     */   {
/*   24:  22 */     MapGenStructureIO.func_143031_a(Crossing3.class, "NeBCr");
/*   25:  23 */     MapGenStructureIO.func_143031_a(End.class, "NeBEF");
/*   26:  24 */     MapGenStructureIO.func_143031_a(Straight.class, "NeBS");
/*   27:  25 */     MapGenStructureIO.func_143031_a(Corridor3.class, "NeCCS");
/*   28:  26 */     MapGenStructureIO.func_143031_a(Corridor4.class, "NeCTB");
/*   29:  27 */     MapGenStructureIO.func_143031_a(Entrance.class, "NeCE");
/*   30:  28 */     MapGenStructureIO.func_143031_a(Crossing2.class, "NeSCSC");
/*   31:  29 */     MapGenStructureIO.func_143031_a(Corridor.class, "NeSCLT");
/*   32:  30 */     MapGenStructureIO.func_143031_a(Corridor5.class, "NeSC");
/*   33:  31 */     MapGenStructureIO.func_143031_a(Corridor2.class, "NeSCRT");
/*   34:  32 */     MapGenStructureIO.func_143031_a(NetherStalkRoom.class, "NeCSR");
/*   35:  33 */     MapGenStructureIO.func_143031_a(Throne.class, "NeMT");
/*   36:  34 */     MapGenStructureIO.func_143031_a(Crossing.class, "NeRC");
/*   37:  35 */     MapGenStructureIO.func_143031_a(Stairs.class, "NeSR");
/*   38:  36 */     MapGenStructureIO.func_143031_a(Start.class, "NeStart");
/*   39:     */   }
/*   40:     */   
/*   41:     */   private static Piece createNextComponentRandom(PieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
/*   42:     */   {
/*   43:  41 */     Class var8 = par0StructureNetherBridgePieceWeight.weightClass;
/*   44:  42 */     Object var9 = null;
/*   45:  44 */     if (var8 == Straight.class) {
/*   46:  46 */       var9 = Straight.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   47:  48 */     } else if (var8 == Crossing3.class) {
/*   48:  50 */       var9 = Crossing3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   49:  52 */     } else if (var8 == Crossing.class) {
/*   50:  54 */       var9 = Crossing.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   51:  56 */     } else if (var8 == Stairs.class) {
/*   52:  58 */       var9 = Stairs.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   53:  60 */     } else if (var8 == Throne.class) {
/*   54:  62 */       var9 = Throne.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   55:  64 */     } else if (var8 == Entrance.class) {
/*   56:  66 */       var9 = Entrance.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   57:  68 */     } else if (var8 == Corridor5.class) {
/*   58:  70 */       var9 = Corridor5.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   59:  72 */     } else if (var8 == Corridor2.class) {
/*   60:  74 */       var9 = Corridor2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   61:  76 */     } else if (var8 == Corridor.class) {
/*   62:  78 */       var9 = Corridor.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   63:  80 */     } else if (var8 == Corridor3.class) {
/*   64:  82 */       var9 = Corridor3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   65:  84 */     } else if (var8 == Corridor4.class) {
/*   66:  86 */       var9 = Corridor4.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   67:  88 */     } else if (var8 == Crossing2.class) {
/*   68:  90 */       var9 = Crossing2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   69:  92 */     } else if (var8 == NetherStalkRoom.class) {
/*   70:  94 */       var9 = NetherStalkRoom.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
/*   71:     */     }
/*   72:  97 */     return (Piece)var9;
/*   73:     */   }
/*   74:     */   
/*   75:     */   public static class Crossing3
/*   76:     */     extends StructureNetherBridgePieces.Piece
/*   77:     */   {
/*   78:     */     private static final String __OBFID = "CL_00000454";
/*   79:     */     
/*   80:     */     public Crossing3() {}
/*   81:     */     
/*   82:     */     public Crossing3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*   83:     */     {
/*   84: 108 */       super();
/*   85: 109 */       this.coordBaseMode = par4;
/*   86: 110 */       this.boundingBox = par3StructureBoundingBox;
/*   87:     */     }
/*   88:     */     
/*   89:     */     protected Crossing3(Random par1Random, int par2, int par3)
/*   90:     */     {
/*   91: 115 */       super();
/*   92: 116 */       this.coordBaseMode = par1Random.nextInt(4);
/*   93: 118 */       switch (this.coordBaseMode)
/*   94:     */       {
/*   95:     */       case 0: 
/*   96:     */       case 2: 
/*   97: 122 */         this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
/*   98: 123 */         break;
/*   99:     */       case 1: 
/*  100:     */       default: 
/*  101: 126 */         this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
/*  102:     */       }
/*  103:     */     }
/*  104:     */     
/*  105:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  106:     */     {
/*  107: 132 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 8, 3, false);
/*  108: 133 */       getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
/*  109: 134 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
/*  110:     */     }
/*  111:     */     
/*  112:     */     public static Crossing3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  113:     */     {
/*  114: 139 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -8, -3, 0, 19, 10, 19, par5);
/*  115: 140 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Crossing3(par6, par1Random, var7, par5) : null;
/*  116:     */     }
/*  117:     */     
/*  118:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  119:     */     {
/*  120: 145 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  121: 146 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  122: 147 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.air, Blocks.air, false);
/*  123: 148 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.air, Blocks.air, false);
/*  124: 149 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  125: 150 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  126: 151 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  127: 152 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  128: 153 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  129: 154 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  130: 155 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  131: 156 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  132: 157 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  133: 158 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  134: 159 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/*  135: 160 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  136: 164 */       for (int var4 = 7; var4 <= 11; var4++) {
/*  137: 166 */         for (int var5 = 0; var5 <= 2; var5++)
/*  138:     */         {
/*  139: 168 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  140: 169 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
/*  141:     */         }
/*  142:     */       }
/*  143: 173 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  144: 174 */       func_151549_a(par1World, par3StructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  145: 175 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  146: 176 */       func_151549_a(par1World, par3StructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);
/*  147: 178 */       for (var4 = 0; var4 <= 2; var4++) {
/*  148: 180 */         for (int var5 = 7; var5 <= 11; var5++)
/*  149:     */         {
/*  150: 182 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  151: 183 */           func_151554_b(par1World, Blocks.nether_brick, 0, 18 - var4, -1, var5, par3StructureBoundingBox);
/*  152:     */         }
/*  153:     */       }
/*  154: 187 */       return true;
/*  155:     */     }
/*  156:     */   }
/*  157:     */   
/*  158:     */   public static class Straight
/*  159:     */     extends StructureNetherBridgePieces.Piece
/*  160:     */   {
/*  161:     */     private static final String __OBFID = "CL_00000456";
/*  162:     */     
/*  163:     */     public Straight() {}
/*  164:     */     
/*  165:     */     public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  166:     */     {
/*  167: 199 */       super();
/*  168: 200 */       this.coordBaseMode = par4;
/*  169: 201 */       this.boundingBox = par3StructureBoundingBox;
/*  170:     */     }
/*  171:     */     
/*  172:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  173:     */     {
/*  174: 206 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 3, false);
/*  175:     */     }
/*  176:     */     
/*  177:     */     public static Straight createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  178:     */     {
/*  179: 211 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);
/*  180: 212 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Straight(par6, par1Random, var7, par5) : null;
/*  181:     */     }
/*  182:     */     
/*  183:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  184:     */     {
/*  185: 217 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  186: 218 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.air, Blocks.air, false);
/*  187: 219 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  188: 220 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  189: 221 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  190: 222 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  191: 223 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/*  192: 224 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);
/*  193: 226 */       for (int var4 = 0; var4 <= 4; var4++) {
/*  194: 228 */         for (int var5 = 0; var5 <= 2; var5++)
/*  195:     */         {
/*  196: 230 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  197: 231 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
/*  198:     */         }
/*  199:     */       }
/*  200: 235 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  201: 236 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  202: 237 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  203: 238 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  204: 239 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  205: 240 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  206: 241 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  207: 242 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  208: 243 */       return true;
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   public static class Crossing2
/*  213:     */     extends StructureNetherBridgePieces.Piece
/*  214:     */   {
/*  215:     */     private static final String __OBFID = "CL_00000460";
/*  216:     */     
/*  217:     */     public Crossing2() {}
/*  218:     */     
/*  219:     */     public Crossing2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  220:     */     {
/*  221: 255 */       super();
/*  222: 256 */       this.coordBaseMode = par4;
/*  223: 257 */       this.boundingBox = par3StructureBoundingBox;
/*  224:     */     }
/*  225:     */     
/*  226:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  227:     */     {
/*  228: 262 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
/*  229: 263 */       getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
/*  230: 264 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
/*  231:     */     }
/*  232:     */     
/*  233:     */     public static Crossing2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  234:     */     {
/*  235: 269 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
/*  236: 270 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Crossing2(par6, par1Random, var7, par5) : null;
/*  237:     */     }
/*  238:     */     
/*  239:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  240:     */     {
/*  241: 275 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  242: 276 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
/*  243: 277 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  244: 278 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  245: 279 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  246: 280 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  247: 281 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  248: 283 */       for (int var4 = 0; var4 <= 4; var4++) {
/*  249: 285 */         for (int var5 = 0; var5 <= 4; var5++) {
/*  250: 287 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  251:     */         }
/*  252:     */       }
/*  253: 291 */       return true;
/*  254:     */     }
/*  255:     */   }
/*  256:     */   
/*  257:     */   public static class Entrance
/*  258:     */     extends StructureNetherBridgePieces.Piece
/*  259:     */   {
/*  260:     */     private static final String __OBFID = "CL_00000459";
/*  261:     */     
/*  262:     */     public Entrance() {}
/*  263:     */     
/*  264:     */     public Entrance(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  265:     */     {
/*  266: 303 */       super();
/*  267: 304 */       this.coordBaseMode = par4;
/*  268: 305 */       this.boundingBox = par3StructureBoundingBox;
/*  269:     */     }
/*  270:     */     
/*  271:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  272:     */     {
/*  273: 310 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
/*  274:     */     }
/*  275:     */     
/*  276:     */     public static Entrance createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  277:     */     {
/*  278: 315 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
/*  279: 316 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Entrance(par6, par1Random, var7, par5) : null;
/*  280:     */     }
/*  281:     */     
/*  282:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  283:     */     {
/*  284: 321 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  285: 322 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
/*  286: 323 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  287: 324 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  288: 325 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  289: 326 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  290: 327 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  291: 328 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/*  292: 329 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/*  293: 330 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/*  294: 331 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
/*  295: 332 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  296: 335 */       for (int var4 = 1; var4 <= 11; var4 += 2)
/*  297:     */       {
/*  298: 337 */         func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  299: 338 */         func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  300: 339 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  301: 340 */         func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  302: 341 */         func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
/*  303: 342 */         func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
/*  304: 343 */         func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
/*  305: 344 */         func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
/*  306: 345 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
/*  307: 346 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
/*  308: 347 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
/*  309: 348 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
/*  310:     */       }
/*  311: 351 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
/*  312: 352 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
/*  313: 353 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
/*  314: 354 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);
/*  315: 356 */       for (var4 = 3; var4 <= 9; var4 += 2)
/*  316:     */       {
/*  317: 358 */         func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  318: 359 */         func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  319:     */       }
/*  320: 362 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  321: 363 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  322: 364 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/*  323: 365 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/*  324: 366 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  325: 367 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  326: 370 */       for (var4 = 4; var4 <= 8; var4++) {
/*  327: 372 */         for (int var5 = 0; var5 <= 2; var5++)
/*  328:     */         {
/*  329: 374 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  330: 375 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 12 - var5, par3StructureBoundingBox);
/*  331:     */         }
/*  332:     */       }
/*  333: 379 */       for (var4 = 0; var4 <= 2; var4++) {
/*  334: 381 */         for (int var5 = 4; var5 <= 8; var5++)
/*  335:     */         {
/*  336: 383 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  337: 384 */           func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var4, -1, var5, par3StructureBoundingBox);
/*  338:     */         }
/*  339:     */       }
/*  340: 388 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  341: 389 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.air, Blocks.air, false);
/*  342: 390 */       func_151550_a(par1World, Blocks.nether_brick, 0, 6, 0, 6, par3StructureBoundingBox);
/*  343: 391 */       func_151550_a(par1World, Blocks.flowing_lava, 0, 6, 5, 6, par3StructureBoundingBox);
/*  344: 392 */       var4 = getXWithOffset(6, 6);
/*  345: 393 */       int var5 = getYWithOffset(5);
/*  346: 394 */       int var6 = getZWithOffset(6, 6);
/*  347: 396 */       if (par3StructureBoundingBox.isVecInside(var4, var5, var6))
/*  348:     */       {
/*  349: 398 */         par1World.scheduledUpdatesAreImmediate = true;
/*  350: 399 */         Blocks.flowing_lava.updateTick(par1World, var4, var5, var6, par2Random);
/*  351: 400 */         par1World.scheduledUpdatesAreImmediate = false;
/*  352:     */       }
/*  353: 403 */       return true;
/*  354:     */     }
/*  355:     */   }
/*  356:     */   
/*  357:     */   static class PieceWeight
/*  358:     */   {
/*  359:     */     public Class weightClass;
/*  360:     */     public final int field_78826_b;
/*  361:     */     public int field_78827_c;
/*  362:     */     public int field_78824_d;
/*  363:     */     public boolean field_78825_e;
/*  364:     */     private static final String __OBFID = "CL_00000467";
/*  365:     */     
/*  366:     */     public PieceWeight(Class par1Class, int par2, int par3, boolean par4)
/*  367:     */     {
/*  368: 418 */       this.weightClass = par1Class;
/*  369: 419 */       this.field_78826_b = par2;
/*  370: 420 */       this.field_78824_d = par3;
/*  371: 421 */       this.field_78825_e = par4;
/*  372:     */     }
/*  373:     */     
/*  374:     */     public PieceWeight(Class par1Class, int par2, int par3)
/*  375:     */     {
/*  376: 426 */       this(par1Class, par2, par3, false);
/*  377:     */     }
/*  378:     */     
/*  379:     */     public boolean func_78822_a(int par1)
/*  380:     */     {
/*  381: 431 */       return (this.field_78824_d == 0) || (this.field_78827_c < this.field_78824_d);
/*  382:     */     }
/*  383:     */     
/*  384:     */     public boolean func_78823_a()
/*  385:     */     {
/*  386: 436 */       return (this.field_78824_d == 0) || (this.field_78827_c < this.field_78824_d);
/*  387:     */     }
/*  388:     */   }
/*  389:     */   
/*  390:     */   public static class Throne
/*  391:     */     extends StructureNetherBridgePieces.Piece
/*  392:     */   {
/*  393:     */     private boolean hasSpawner;
/*  394:     */     private static final String __OBFID = "CL_00000465";
/*  395:     */     
/*  396:     */     public Throne() {}
/*  397:     */     
/*  398:     */     public Throne(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  399:     */     {
/*  400: 449 */       super();
/*  401: 450 */       this.coordBaseMode = par4;
/*  402: 451 */       this.boundingBox = par3StructureBoundingBox;
/*  403:     */     }
/*  404:     */     
/*  405:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  406:     */     {
/*  407: 456 */       super.func_143011_b(par1NBTTagCompound);
/*  408: 457 */       this.hasSpawner = par1NBTTagCompound.getBoolean("Mob");
/*  409:     */     }
/*  410:     */     
/*  411:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  412:     */     {
/*  413: 462 */       super.func_143012_a(par1NBTTagCompound);
/*  414: 463 */       par1NBTTagCompound.setBoolean("Mob", this.hasSpawner);
/*  415:     */     }
/*  416:     */     
/*  417:     */     public static Throne createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  418:     */     {
/*  419: 468 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 8, 9, par5);
/*  420: 469 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Throne(par6, par1Random, var7, par5) : null;
/*  421:     */     }
/*  422:     */     
/*  423:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  424:     */     {
/*  425: 474 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.air, Blocks.air, false);
/*  426: 475 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  427: 476 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  428: 477 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  429: 478 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  430: 479 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
/*  431: 480 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
/*  432: 481 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/*  433: 482 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/*  434: 483 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  435: 484 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  436: 485 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  437: 486 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 1, 6, 3, par3StructureBoundingBox);
/*  438: 487 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 5, 6, 3, par3StructureBoundingBox);
/*  439: 488 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  440: 489 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  441: 490 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  442: 491 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  443: 495 */       if (!this.hasSpawner)
/*  444:     */       {
/*  445: 497 */         int var4 = getYWithOffset(5);
/*  446: 498 */         int var5 = getXWithOffset(3, 5);
/*  447: 499 */         int var6 = getZWithOffset(3, 5);
/*  448: 501 */         if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
/*  449:     */         {
/*  450: 503 */           this.hasSpawner = true;
/*  451: 504 */           par1World.setBlock(var5, var4, var6, Blocks.mob_spawner, 0, 2);
/*  452: 505 */           TileEntityMobSpawner var7 = (TileEntityMobSpawner)par1World.getTileEntity(var5, var4, var6);
/*  453: 507 */           if (var7 != null) {
/*  454: 509 */             var7.func_145881_a().setMobID("Blaze");
/*  455:     */           }
/*  456:     */         }
/*  457:     */       }
/*  458: 514 */       for (int var4 = 0; var4 <= 6; var4++) {
/*  459: 516 */         for (int var5 = 0; var5 <= 6; var5++) {
/*  460: 518 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  461:     */         }
/*  462:     */       }
/*  463: 522 */       return true;
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   public static class Corridor5
/*  468:     */     extends StructureNetherBridgePieces.Piece
/*  469:     */   {
/*  470:     */     private static final String __OBFID = "CL_00000462";
/*  471:     */     
/*  472:     */     public Corridor5() {}
/*  473:     */     
/*  474:     */     public Corridor5(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  475:     */     {
/*  476: 534 */       super();
/*  477: 535 */       this.coordBaseMode = par4;
/*  478: 536 */       this.boundingBox = par3StructureBoundingBox;
/*  479:     */     }
/*  480:     */     
/*  481:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  482:     */     {
/*  483: 541 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
/*  484:     */     }
/*  485:     */     
/*  486:     */     public static Corridor5 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  487:     */     {
/*  488: 546 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
/*  489: 547 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Corridor5(par6, par1Random, var7, par5) : null;
/*  490:     */     }
/*  491:     */     
/*  492:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  493:     */     {
/*  494: 552 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  495: 553 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
/*  496: 554 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  497: 555 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  498: 556 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  499: 557 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  500: 558 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  501: 559 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  502: 560 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  503: 562 */       for (int var4 = 0; var4 <= 4; var4++) {
/*  504: 564 */         for (int var5 = 0; var5 <= 4; var5++) {
/*  505: 566 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  506:     */         }
/*  507:     */       }
/*  508: 570 */       return true;
/*  509:     */     }
/*  510:     */   }
/*  511:     */   
/*  512:     */   static abstract class Piece
/*  513:     */     extends StructureComponent
/*  514:     */   {
/*  515: 576 */     protected static final WeightedRandomChestContent[] field_111019_a = { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3) };
/*  516:     */     private static final String __OBFID = "CL_00000466";
/*  517:     */     
/*  518:     */     public Piece() {}
/*  519:     */     
/*  520:     */     protected Piece(int par1)
/*  521:     */     {
/*  522: 583 */       super();
/*  523:     */     }
/*  524:     */     
/*  525:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {}
/*  526:     */     
/*  527:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {}
/*  528:     */     
/*  529:     */     private int getTotalWeight(List par1List)
/*  530:     */     {
/*  531: 592 */       boolean var2 = false;
/*  532: 593 */       int var3 = 0;
/*  533:     */       StructureNetherBridgePieces.PieceWeight var5;
/*  534: 596 */       for (Iterator var4 = par1List.iterator(); var4.hasNext(); var3 += var5.field_78826_b)
/*  535:     */       {
/*  536: 598 */         var5 = (StructureNetherBridgePieces.PieceWeight)var4.next();
/*  537: 600 */         if ((var5.field_78824_d > 0) && (var5.field_78827_c < var5.field_78824_d)) {
/*  538: 602 */           var2 = true;
/*  539:     */         }
/*  540:     */       }
/*  541: 606 */       return var2 ? var3 : -1;
/*  542:     */     }
/*  543:     */     
/*  544:     */     private Piece getNextComponent(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, List par3List, Random par4Random, int par5, int par6, int par7, int par8, int par9)
/*  545:     */     {
/*  546: 611 */       int var10 = getTotalWeight(par2List);
/*  547: 612 */       boolean var11 = (var10 > 0) && (par9 <= 30);
/*  548: 613 */       int var12 = 0;
/*  549:     */       Iterator var14;
/*  550:     */       label184:
/*  551: 615 */       for (; (var12 < 5) && (var11); var14.hasNext())
/*  552:     */       {
/*  553: 617 */         var12++;
/*  554: 618 */         int var13 = par4Random.nextInt(var10);
/*  555: 619 */         var14 = par2List.iterator();
/*  556:     */         
/*  557: 621 */         continue;
/*  558:     */         
/*  559: 623 */         StructureNetherBridgePieces.PieceWeight var15 = (StructureNetherBridgePieces.PieceWeight)var14.next();
/*  560: 624 */         var13 -= var15.field_78826_b;
/*  561: 626 */         if (var13 < 0)
/*  562:     */         {
/*  563: 628 */           if ((!var15.func_78822_a(par9)) || ((var15 == par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight) && (!var15.field_78825_e))) {
/*  564:     */             break label184;
/*  565:     */           }
/*  566: 633 */           Piece var16 = StructureNetherBridgePieces.createNextComponentRandom(var15, par3List, par4Random, par5, par6, par7, par8, par9);
/*  567: 635 */           if (var16 != null)
/*  568:     */           {
/*  569: 637 */             var15.field_78827_c += 1;
/*  570: 638 */             par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight = var15;
/*  571: 640 */             if (!var15.func_78823_a()) {
/*  572: 642 */               par2List.remove(var15);
/*  573:     */             }
/*  574: 645 */             return var16;
/*  575:     */           }
/*  576:     */         }
/*  577:     */       }
/*  578: 651 */       return StructureNetherBridgePieces.End.func_74971_a(par3List, par4Random, par5, par6, par7, par8, par9);
/*  579:     */     }
/*  580:     */     
/*  581:     */     private StructureComponent getNextComponent(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8, boolean par9)
/*  582:     */     {
/*  583: 656 */       if ((Math.abs(par4 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minX) <= 112) && (Math.abs(par6 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minZ) <= 112))
/*  584:     */       {
/*  585: 658 */         List var10 = par1ComponentNetherBridgeStartPiece.primaryWeights;
/*  586: 660 */         if (par9) {
/*  587: 662 */           var10 = par1ComponentNetherBridgeStartPiece.secondaryWeights;
/*  588:     */         }
/*  589: 665 */         Piece var11 = getNextComponent(par1ComponentNetherBridgeStartPiece, var10, par2List, par3Random, par4, par5, par6, par7, par8 + 1);
/*  590: 667 */         if (var11 != null)
/*  591:     */         {
/*  592: 669 */           par2List.add(var11);
/*  593: 670 */           par1ComponentNetherBridgeStartPiece.field_74967_d.add(var11);
/*  594:     */         }
/*  595: 673 */         return var11;
/*  596:     */       }
/*  597: 677 */       return StructureNetherBridgePieces.End.func_74971_a(par2List, par3Random, par4, par5, par6, par7, par8);
/*  598:     */     }
/*  599:     */     
/*  600:     */     protected StructureComponent getNextComponentNormal(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
/*  601:     */     {
/*  602: 683 */       switch (this.coordBaseMode)
/*  603:     */       {
/*  604:     */       case 0: 
/*  605: 686 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType(), par6);
/*  606:     */       case 1: 
/*  607: 689 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, getComponentType(), par6);
/*  608:     */       case 2: 
/*  609: 692 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType(), par6);
/*  610:     */       case 3: 
/*  611: 695 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, getComponentType(), par6);
/*  612:     */       }
/*  613: 698 */       return null;
/*  614:     */     }
/*  615:     */     
/*  616:     */     protected StructureComponent getNextComponentX(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
/*  617:     */     {
/*  618: 704 */       switch (this.coordBaseMode)
/*  619:     */       {
/*  620:     */       case 0: 
/*  621: 707 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType(), par6);
/*  622:     */       case 1: 
/*  623: 710 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType(), par6);
/*  624:     */       case 2: 
/*  625: 713 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, getComponentType(), par6);
/*  626:     */       case 3: 
/*  627: 716 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, getComponentType(), par6);
/*  628:     */       }
/*  629: 719 */       return null;
/*  630:     */     }
/*  631:     */     
/*  632:     */     protected StructureComponent getNextComponentZ(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
/*  633:     */     {
/*  634: 725 */       switch (this.coordBaseMode)
/*  635:     */       {
/*  636:     */       case 0: 
/*  637: 728 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType(), par6);
/*  638:     */       case 1: 
/*  639: 731 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType(), par6);
/*  640:     */       case 2: 
/*  641: 734 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, getComponentType(), par6);
/*  642:     */       case 3: 
/*  643: 737 */         return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, getComponentType(), par6);
/*  644:     */       }
/*  645: 740 */       return null;
/*  646:     */     }
/*  647:     */     
/*  648:     */     protected static boolean isAboveGround(StructureBoundingBox par0StructureBoundingBox)
/*  649:     */     {
/*  650: 746 */       return (par0StructureBoundingBox != null) && (par0StructureBoundingBox.minY > 10);
/*  651:     */     }
/*  652:     */   }
/*  653:     */   
/*  654:     */   public static class Start
/*  655:     */     extends StructureNetherBridgePieces.Crossing3
/*  656:     */   {
/*  657:     */     public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
/*  658:     */     public List primaryWeights;
/*  659:     */     public List secondaryWeights;
/*  660: 755 */     public ArrayList field_74967_d = new ArrayList();
/*  661:     */     private static final String __OBFID = "CL_00000470";
/*  662:     */     
/*  663:     */     public Start() {}
/*  664:     */     
/*  665:     */     public Start(Random par1Random, int par2, int par3)
/*  666:     */     {
/*  667: 762 */       super(par2, par3);
/*  668: 763 */       this.primaryWeights = new ArrayList();
/*  669: 764 */       StructureNetherBridgePieces.PieceWeight[] var4 = StructureNetherBridgePieces.primaryComponents;
/*  670: 765 */       int var5 = var4.length;
/*  671: 769 */       for (int var6 = 0; var6 < var5; var6++)
/*  672:     */       {
/*  673: 771 */         StructureNetherBridgePieces.PieceWeight var7 = var4[var6];
/*  674: 772 */         var7.field_78827_c = 0;
/*  675: 773 */         this.primaryWeights.add(var7);
/*  676:     */       }
/*  677: 776 */       this.secondaryWeights = new ArrayList();
/*  678: 777 */       var4 = StructureNetherBridgePieces.secondaryComponents;
/*  679: 778 */       var5 = var4.length;
/*  680: 780 */       for (var6 = 0; var6 < var5; var6++)
/*  681:     */       {
/*  682: 782 */         StructureNetherBridgePieces.PieceWeight var7 = var4[var6];
/*  683: 783 */         var7.field_78827_c = 0;
/*  684: 784 */         this.secondaryWeights.add(var7);
/*  685:     */       }
/*  686:     */     }
/*  687:     */     
/*  688:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  689:     */     {
/*  690: 790 */       super.func_143011_b(par1NBTTagCompound);
/*  691:     */     }
/*  692:     */     
/*  693:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  694:     */     {
/*  695: 795 */       super.func_143012_a(par1NBTTagCompound);
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public static class Stairs
/*  700:     */     extends StructureNetherBridgePieces.Piece
/*  701:     */   {
/*  702:     */     private static final String __OBFID = "CL_00000469";
/*  703:     */     
/*  704:     */     public Stairs() {}
/*  705:     */     
/*  706:     */     public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  707:     */     {
/*  708: 807 */       super();
/*  709: 808 */       this.coordBaseMode = par4;
/*  710: 809 */       this.boundingBox = par3StructureBoundingBox;
/*  711:     */     }
/*  712:     */     
/*  713:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  714:     */     {
/*  715: 814 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 6, 2, false);
/*  716:     */     }
/*  717:     */     
/*  718:     */     public static Stairs createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  719:     */     {
/*  720: 819 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 11, 7, par5);
/*  721: 820 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Stairs(par6, par1Random, var7, par5) : null;
/*  722:     */     }
/*  723:     */     
/*  724:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  725:     */     {
/*  726: 825 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/*  727: 826 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.air, Blocks.air, false);
/*  728: 827 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  729: 828 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  730: 829 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/*  731: 830 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/*  732: 831 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/*  733: 832 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  734: 833 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  735: 834 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  736: 835 */       func_151550_a(par1World, Blocks.nether_brick, 0, 5, 2, 5, par3StructureBoundingBox);
/*  737: 836 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  738: 837 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  739: 838 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  740: 839 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  741: 840 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  742: 841 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.air, Blocks.air, false);
/*  743: 842 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  744: 843 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  745: 845 */       for (int var4 = 0; var4 <= 6; var4++) {
/*  746: 847 */         for (int var5 = 0; var5 <= 6; var5++) {
/*  747: 849 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  748:     */         }
/*  749:     */       }
/*  750: 853 */       return true;
/*  751:     */     }
/*  752:     */   }
/*  753:     */   
/*  754:     */   public static class Corridor2
/*  755:     */     extends StructureNetherBridgePieces.Piece
/*  756:     */   {
/*  757:     */     private boolean field_111020_b;
/*  758:     */     private static final String __OBFID = "CL_00000463";
/*  759:     */     
/*  760:     */     public Corridor2() {}
/*  761:     */     
/*  762:     */     public Corridor2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  763:     */     {
/*  764: 866 */       super();
/*  765: 867 */       this.coordBaseMode = par4;
/*  766: 868 */       this.boundingBox = par3StructureBoundingBox;
/*  767: 869 */       this.field_111020_b = (par2Random.nextInt(3) == 0);
/*  768:     */     }
/*  769:     */     
/*  770:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  771:     */     {
/*  772: 874 */       super.func_143011_b(par1NBTTagCompound);
/*  773: 875 */       this.field_111020_b = par1NBTTagCompound.getBoolean("Chest");
/*  774:     */     }
/*  775:     */     
/*  776:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  777:     */     {
/*  778: 880 */       super.func_143012_a(par1NBTTagCompound);
/*  779: 881 */       par1NBTTagCompound.setBoolean("Chest", this.field_111020_b);
/*  780:     */     }
/*  781:     */     
/*  782:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  783:     */     {
/*  784: 886 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
/*  785:     */     }
/*  786:     */     
/*  787:     */     public static Corridor2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  788:     */     {
/*  789: 891 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
/*  790: 892 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Corridor2(par6, par1Random, var7, par5) : null;
/*  791:     */     }
/*  792:     */     
/*  793:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  794:     */     {
/*  795: 897 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  796: 898 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
/*  797: 899 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  798: 900 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  799: 901 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  800: 902 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  801: 903 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  802: 904 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
/*  803: 905 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
/*  804: 909 */       if (this.field_111020_b)
/*  805:     */       {
/*  806: 911 */         int var4 = getYWithOffset(2);
/*  807: 912 */         int var5 = getXWithOffset(1, 3);
/*  808: 913 */         int var6 = getZWithOffset(1, 3);
/*  809: 915 */         if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
/*  810:     */         {
/*  811: 917 */           this.field_111020_b = false;
/*  812: 918 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 1, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
/*  813:     */         }
/*  814:     */       }
/*  815: 922 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/*  816: 924 */       for (int var4 = 0; var4 <= 4; var4++) {
/*  817: 926 */         for (int var5 = 0; var5 <= 4; var5++) {
/*  818: 928 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/*  819:     */         }
/*  820:     */       }
/*  821: 932 */       return true;
/*  822:     */     }
/*  823:     */   }
/*  824:     */   
/*  825:     */   public static class Corridor3
/*  826:     */     extends StructureNetherBridgePieces.Piece
/*  827:     */   {
/*  828:     */     private static final String __OBFID = "CL_00000457";
/*  829:     */     
/*  830:     */     public Corridor3() {}
/*  831:     */     
/*  832:     */     public Corridor3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  833:     */     {
/*  834: 944 */       super();
/*  835: 945 */       this.coordBaseMode = par4;
/*  836: 946 */       this.boundingBox = par3StructureBoundingBox;
/*  837:     */     }
/*  838:     */     
/*  839:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  840:     */     {
/*  841: 951 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
/*  842:     */     }
/*  843:     */     
/*  844:     */     public static Corridor3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  845:     */     {
/*  846: 956 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 14, 10, par5);
/*  847: 957 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Corridor3(par6, par1Random, var7, par5) : null;
/*  848:     */     }
/*  849:     */     
/*  850:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  851:     */     {
/*  852: 962 */       int var4 = func_151555_a(Blocks.nether_brick_stairs, 2);
/*  853: 964 */       for (int var5 = 0; var5 <= 9; var5++)
/*  854:     */       {
/*  855: 966 */         int var6 = Math.max(1, 7 - var5);
/*  856: 967 */         int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
/*  857: 968 */         int var8 = var5;
/*  858: 969 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 0, var5, 4, var6, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  859: 970 */         func_151549_a(par1World, par3StructureBoundingBox, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air, Blocks.air, false);
/*  860: 972 */         if (var5 <= 6)
/*  861:     */         {
/*  862: 974 */           func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 1, var6 + 1, var5, par3StructureBoundingBox);
/*  863: 975 */           func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 2, var6 + 1, var5, par3StructureBoundingBox);
/*  864: 976 */           func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 3, var6 + 1, var5, par3StructureBoundingBox);
/*  865:     */         }
/*  866: 979 */         func_151549_a(par1World, par3StructureBoundingBox, 0, var7, var5, 4, var7, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  867: 980 */         func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  868: 981 */         func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  869: 983 */         if ((var5 & 0x1) == 0)
/*  870:     */         {
/*  871: 985 */           func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  872: 986 */           func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  873:     */         }
/*  874: 989 */         for (int var9 = 0; var9 <= 4; var9++) {
/*  875: 991 */           func_151554_b(par1World, Blocks.nether_brick, 0, var9, -1, var8, par3StructureBoundingBox);
/*  876:     */         }
/*  877:     */       }
/*  878: 995 */       return true;
/*  879:     */     }
/*  880:     */   }
/*  881:     */   
/*  882:     */   public static class Corridor4
/*  883:     */     extends StructureNetherBridgePieces.Piece
/*  884:     */   {
/*  885:     */     private static final String __OBFID = "CL_00000458";
/*  886:     */     
/*  887:     */     public Corridor4() {}
/*  888:     */     
/*  889:     */     public Corridor4(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  890:     */     {
/*  891:1007 */       super();
/*  892:1008 */       this.coordBaseMode = par4;
/*  893:1009 */       this.boundingBox = par3StructureBoundingBox;
/*  894:     */     }
/*  895:     */     
/*  896:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/*  897:     */     {
/*  898:1014 */       byte var4 = 1;
/*  899:1016 */       if ((this.coordBaseMode == 1) || (this.coordBaseMode == 2)) {
/*  900:1018 */         var4 = 5;
/*  901:     */       }
/*  902:1021 */       getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
/*  903:1022 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
/*  904:     */     }
/*  905:     */     
/*  906:     */     public static Corridor4 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  907:     */     {
/*  908:1027 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -3, 0, 0, 9, 7, 9, par5);
/*  909:1028 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Corridor4(par6, par1Random, var7, par5) : null;
/*  910:     */     }
/*  911:     */     
/*  912:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  913:     */     {
/*  914:1033 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  915:1034 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.air, Blocks.air, false);
/*  916:1035 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  917:1036 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  918:1037 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/*  919:1038 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  920:1039 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  921:1040 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/*  922:1041 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.air, Blocks.air, false);
/*  923:1042 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.air, Blocks.air, false);
/*  924:1043 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  925:1044 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  926:1045 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  927:1046 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  928:1047 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  929:1048 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  930:1049 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  931:1050 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  932:1051 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/*  933:1053 */       for (int var4 = 0; var4 <= 5; var4++) {
/*  934:1055 */         for (int var5 = 0; var5 <= 8; var5++) {
/*  935:1057 */           func_151554_b(par1World, Blocks.nether_brick, 0, var5, -1, var4, par3StructureBoundingBox);
/*  936:     */         }
/*  937:     */       }
/*  938:1061 */       return true;
/*  939:     */     }
/*  940:     */   }
/*  941:     */   
/*  942:     */   public static class End
/*  943:     */     extends StructureNetherBridgePieces.Piece
/*  944:     */   {
/*  945:     */     private int fillSeed;
/*  946:     */     private static final String __OBFID = "CL_00000455";
/*  947:     */     
/*  948:     */     public End() {}
/*  949:     */     
/*  950:     */     public End(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/*  951:     */     {
/*  952:1074 */       super();
/*  953:1075 */       this.coordBaseMode = par4;
/*  954:1076 */       this.boundingBox = par3StructureBoundingBox;
/*  955:1077 */       this.fillSeed = par2Random.nextInt();
/*  956:     */     }
/*  957:     */     
/*  958:     */     public static End func_74971_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/*  959:     */     {
/*  960:1082 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 8, par5);
/*  961:1083 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new End(par6, par1Random, var7, par5) : null;
/*  962:     */     }
/*  963:     */     
/*  964:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/*  965:     */     {
/*  966:1088 */       super.func_143011_b(par1NBTTagCompound);
/*  967:1089 */       this.fillSeed = par1NBTTagCompound.getInteger("Seed");
/*  968:     */     }
/*  969:     */     
/*  970:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/*  971:     */     {
/*  972:1094 */       super.func_143012_a(par1NBTTagCompound);
/*  973:1095 */       par1NBTTagCompound.setInteger("Seed", this.fillSeed);
/*  974:     */     }
/*  975:     */     
/*  976:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  977:     */     {
/*  978:1100 */       Random var4 = new Random(this.fillSeed);
/*  979:1105 */       for (int var5 = 0; var5 <= 4; var5++) {
/*  980:1107 */         for (int var6 = 3; var6 <= 4; var6++)
/*  981:     */         {
/*  982:1109 */           int var7 = var4.nextInt(8);
/*  983:1110 */           func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
/*  984:     */         }
/*  985:     */       }
/*  986:1114 */       var5 = var4.nextInt(8);
/*  987:1115 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  988:1116 */       var5 = var4.nextInt(8);
/*  989:1117 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);
/*  990:1119 */       for (var5 = 0; var5 <= 4; var5++)
/*  991:     */       {
/*  992:1121 */         int var6 = var4.nextInt(5);
/*  993:1122 */         func_151549_a(par1World, par3StructureBoundingBox, var5, 2, 0, var5, 2, var6, Blocks.nether_brick, Blocks.nether_brick, false);
/*  994:     */       }
/*  995:1125 */       for (var5 = 0; var5 <= 4; var5++) {
/*  996:1127 */         for (int var6 = 0; var6 <= 1; var6++)
/*  997:     */         {
/*  998:1129 */           int var7 = var4.nextInt(3);
/*  999:1130 */           func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1000:     */         }
/* 1001:     */       }
/* 1002:1134 */       return true;
/* 1003:     */     }
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public static class NetherStalkRoom
/* 1007:     */     extends StructureNetherBridgePieces.Piece
/* 1008:     */   {
/* 1009:     */     private static final String __OBFID = "CL_00000464";
/* 1010:     */     
/* 1011:     */     public NetherStalkRoom() {}
/* 1012:     */     
/* 1013:     */     public NetherStalkRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 1014:     */     {
/* 1015:1146 */       super();
/* 1016:1147 */       this.coordBaseMode = par4;
/* 1017:1148 */       this.boundingBox = par3StructureBoundingBox;
/* 1018:     */     }
/* 1019:     */     
/* 1020:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 1021:     */     {
/* 1022:1153 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
/* 1023:1154 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 11, true);
/* 1024:     */     }
/* 1025:     */     
/* 1026:     */     public static NetherStalkRoom createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/* 1027:     */     {
/* 1028:1159 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
/* 1029:1160 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new NetherStalkRoom(par6, par1Random, var7, par5) : null;
/* 1030:     */     }
/* 1031:     */     
/* 1032:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1033:     */     {
/* 1034:1165 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1035:1166 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
/* 1036:1167 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1037:1168 */       func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1038:1169 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1039:1170 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1040:1171 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1041:1172 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1042:1173 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1043:1174 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1044:1175 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1045:1178 */       for (int var4 = 1; var4 <= 11; var4 += 2)
/* 1046:     */       {
/* 1047:1180 */         func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1048:1181 */         func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1049:1182 */         func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1050:1183 */         func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1051:1184 */         func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
/* 1052:1185 */         func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
/* 1053:1186 */         func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
/* 1054:1187 */         func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
/* 1055:1188 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
/* 1056:1189 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
/* 1057:1190 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
/* 1058:1191 */         func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
/* 1059:     */       }
/* 1060:1194 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
/* 1061:1195 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
/* 1062:1196 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
/* 1063:1197 */       func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);
/* 1064:1199 */       for (var4 = 3; var4 <= 9; var4 += 2)
/* 1065:     */       {
/* 1066:1201 */         func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1067:1202 */         func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1068:     */       }
/* 1069:1205 */       var4 = func_151555_a(Blocks.nether_brick_stairs, 3);
/* 1070:1210 */       for (int var5 = 0; var5 <= 6; var5++)
/* 1071:     */       {
/* 1072:1212 */         int var6 = var5 + 4;
/* 1073:1214 */         for (int var7 = 5; var7 <= 7; var7++) {
/* 1074:1216 */           func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var7, 5 + var5, var6, par3StructureBoundingBox);
/* 1075:     */         }
/* 1076:1219 */         if ((var6 >= 5) && (var6 <= 8)) {
/* 1077:1221 */           func_151549_a(par1World, par3StructureBoundingBox, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1078:1223 */         } else if ((var6 >= 9) && (var6 <= 10)) {
/* 1079:1225 */           func_151549_a(par1World, par3StructureBoundingBox, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1080:     */         }
/* 1081:1228 */         if (var5 >= 1) {
/* 1082:1230 */           func_151549_a(par1World, par3StructureBoundingBox, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air, Blocks.air, false);
/* 1083:     */         }
/* 1084:     */       }
/* 1085:1234 */       for (var5 = 5; var5 <= 7; var5++) {
/* 1086:1236 */         func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var5, 12, 11, par3StructureBoundingBox);
/* 1087:     */       }
/* 1088:1239 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1089:1240 */       func_151549_a(par1World, par3StructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1090:1241 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.air, Blocks.air, false);
/* 1091:1242 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1092:1243 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1093:1244 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1094:1245 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1095:1246 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1096:1247 */       func_151549_a(par1World, par3StructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1097:1248 */       var5 = func_151555_a(Blocks.nether_brick_stairs, 0);
/* 1098:1249 */       int var6 = func_151555_a(Blocks.nether_brick_stairs, 1);
/* 1099:1250 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 2, par3StructureBoundingBox);
/* 1100:1251 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 3, par3StructureBoundingBox);
/* 1101:1252 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 9, par3StructureBoundingBox);
/* 1102:1253 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 10, par3StructureBoundingBox);
/* 1103:1254 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 2, par3StructureBoundingBox);
/* 1104:1255 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 3, par3StructureBoundingBox);
/* 1105:1256 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 9, par3StructureBoundingBox);
/* 1106:1257 */       func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 10, par3StructureBoundingBox);
/* 1107:1258 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
/* 1108:1259 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
/* 1109:1260 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
/* 1110:1261 */       func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
/* 1111:1262 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1112:1263 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1113:1264 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1114:1265 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1115:1266 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1116:1267 */       func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1117:1270 */       for (int var7 = 4; var7 <= 8; var7++) {
/* 1118:1272 */         for (int var8 = 0; var8 <= 2; var8++)
/* 1119:     */         {
/* 1120:1274 */           func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
/* 1121:1275 */           func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, 12 - var8, par3StructureBoundingBox);
/* 1122:     */         }
/* 1123:     */       }
/* 1124:1279 */       for (var7 = 0; var7 <= 2; var7++) {
/* 1125:1281 */         for (int var8 = 4; var8 <= 8; var8++)
/* 1126:     */         {
/* 1127:1283 */           func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
/* 1128:1284 */           func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var7, -1, var8, par3StructureBoundingBox);
/* 1129:     */         }
/* 1130:     */       }
/* 1131:1288 */       return true;
/* 1132:     */     }
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public static class Crossing
/* 1136:     */     extends StructureNetherBridgePieces.Piece
/* 1137:     */   {
/* 1138:     */     private static final String __OBFID = "CL_00000468";
/* 1139:     */     
/* 1140:     */     public Crossing() {}
/* 1141:     */     
/* 1142:     */     public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 1143:     */     {
/* 1144:1300 */       super();
/* 1145:1301 */       this.coordBaseMode = par4;
/* 1146:1302 */       this.boundingBox = par3StructureBoundingBox;
/* 1147:     */     }
/* 1148:     */     
/* 1149:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 1150:     */     {
/* 1151:1307 */       getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 2, 0, false);
/* 1152:1308 */       getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
/* 1153:1309 */       getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
/* 1154:     */     }
/* 1155:     */     
/* 1156:     */     public static Crossing createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/* 1157:     */     {
/* 1158:1314 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 9, 7, par5);
/* 1159:1315 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Crossing(par6, par1Random, var7, par5) : null;
/* 1160:     */     }
/* 1161:     */     
/* 1162:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1163:     */     {
/* 1164:1320 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1165:1321 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.air, Blocks.air, false);
/* 1166:1322 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1167:1323 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1168:1324 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1169:1325 */       func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1170:1326 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1171:1327 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1172:1328 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1173:1329 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1174:1330 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1175:1331 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1176:1332 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1177:1333 */       func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1178:1334 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1179:1335 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1180:1336 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1181:1337 */       func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1182:1339 */       for (int var4 = 0; var4 <= 6; var4++) {
/* 1183:1341 */         for (int var5 = 0; var5 <= 6; var5++) {
/* 1184:1343 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/* 1185:     */         }
/* 1186:     */       }
/* 1187:1347 */       return true;
/* 1188:     */     }
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public static class Corridor
/* 1192:     */     extends StructureNetherBridgePieces.Piece
/* 1193:     */   {
/* 1194:     */     private boolean field_111021_b;
/* 1195:     */     private static final String __OBFID = "CL_00000461";
/* 1196:     */     
/* 1197:     */     public Corridor() {}
/* 1198:     */     
/* 1199:     */     public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
/* 1200:     */     {
/* 1201:1360 */       super();
/* 1202:1361 */       this.coordBaseMode = par4;
/* 1203:1362 */       this.boundingBox = par3StructureBoundingBox;
/* 1204:1363 */       this.field_111021_b = (par2Random.nextInt(3) == 0);
/* 1205:     */     }
/* 1206:     */     
/* 1207:     */     protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
/* 1208:     */     {
/* 1209:1368 */       super.func_143011_b(par1NBTTagCompound);
/* 1210:1369 */       this.field_111021_b = par1NBTTagCompound.getBoolean("Chest");
/* 1211:     */     }
/* 1212:     */     
/* 1213:     */     protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
/* 1214:     */     {
/* 1215:1374 */       super.func_143012_a(par1NBTTagCompound);
/* 1216:1375 */       par1NBTTagCompound.setBoolean("Chest", this.field_111021_b);
/* 1217:     */     }
/* 1218:     */     
/* 1219:     */     public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
/* 1220:     */     {
/* 1221:1380 */       getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
/* 1222:     */     }
/* 1223:     */     
/* 1224:     */     public static Corridor createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
/* 1225:     */     {
/* 1226:1385 */       StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
/* 1227:1386 */       return (isAboveGround(var7)) && (StructureComponent.findIntersecting(par0List, var7) == null) ? new Corridor(par6, par1Random, var7, par5) : null;
/* 1228:     */     }
/* 1229:     */     
/* 1230:     */     public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/* 1231:     */     {
/* 1232:1391 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1233:1392 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
/* 1234:1393 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1235:1394 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1236:1395 */       func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
/* 1237:1396 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1238:1397 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1239:1398 */       func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
/* 1240:1399 */       func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
/* 1241:1403 */       if (this.field_111021_b)
/* 1242:     */       {
/* 1243:1405 */         int var4 = getYWithOffset(2);
/* 1244:1406 */         int var5 = getXWithOffset(3, 3);
/* 1245:1407 */         int var6 = getZWithOffset(3, 3);
/* 1246:1409 */         if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
/* 1247:     */         {
/* 1248:1411 */           this.field_111021_b = false;
/* 1249:1412 */           generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
/* 1250:     */         }
/* 1251:     */       }
/* 1252:1416 */       func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
/* 1253:1418 */       for (int var4 = 0; var4 <= 4; var4++) {
/* 1254:1420 */         for (int var5 = 0; var5 <= 4; var5++) {
/* 1255:1422 */           func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
/* 1256:     */         }
/* 1257:     */       }
/* 1258:1426 */       return true;
/* 1259:     */     }
/* 1260:     */   }
/* 1261:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureNetherBridgePieces
 * JD-Core Version:    0.7.0.1
 */