/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenBlockBlob
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block field_150545_a;
/*    */   private final int field_150544_b;
/*    */   
/*    */   public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_) {
/* 16 */     super(false);
/* 17 */     this.field_150545_a = p_i45450_1_;
/* 18 */     this.field_150544_b = p_i45450_2_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     // Byte code:
/*    */     //   0: aload_3
/*    */     //   1: invokevirtual getY : ()I
/*    */     //   4: iconst_3
/*    */     //   5: if_icmple -> 64
/*    */     //   8: aload_1
/*    */     //   9: aload_3
/*    */     //   10: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   13: invokevirtual isAirBlock : (Lnet/minecraft/util/BlockPos;)Z
/*    */     //   16: ifeq -> 22
/*    */     //   19: goto -> 282
/*    */     //   22: aload_1
/*    */     //   23: aload_3
/*    */     //   24: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   27: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*    */     //   30: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*    */     //   35: astore #4
/*    */     //   37: aload #4
/*    */     //   39: getstatic net/minecraft/init/Blocks.grass : Lnet/minecraft/block/BlockGrass;
/*    */     //   42: if_acmpeq -> 64
/*    */     //   45: aload #4
/*    */     //   47: getstatic net/minecraft/init/Blocks.dirt : Lnet/minecraft/block/Block;
/*    */     //   50: if_acmpeq -> 64
/*    */     //   53: aload #4
/*    */     //   55: getstatic net/minecraft/init/Blocks.stone : Lnet/minecraft/block/Block;
/*    */     //   58: if_acmpeq -> 64
/*    */     //   61: goto -> 282
/*    */     //   64: aload_3
/*    */     //   65: invokevirtual getY : ()I
/*    */     //   68: iconst_3
/*    */     //   69: if_icmpgt -> 74
/*    */     //   72: iconst_0
/*    */     //   73: ireturn
/*    */     //   74: aload_0
/*    */     //   75: getfield field_150544_b : I
/*    */     //   78: istore #4
/*    */     //   80: iconst_0
/*    */     //   81: istore #5
/*    */     //   83: goto -> 269
/*    */     //   86: iload #4
/*    */     //   88: aload_2
/*    */     //   89: iconst_2
/*    */     //   90: invokevirtual nextInt : (I)I
/*    */     //   93: iadd
/*    */     //   94: istore #6
/*    */     //   96: iload #4
/*    */     //   98: aload_2
/*    */     //   99: iconst_2
/*    */     //   100: invokevirtual nextInt : (I)I
/*    */     //   103: iadd
/*    */     //   104: istore #7
/*    */     //   106: iload #4
/*    */     //   108: aload_2
/*    */     //   109: iconst_2
/*    */     //   110: invokevirtual nextInt : (I)I
/*    */     //   113: iadd
/*    */     //   114: istore #8
/*    */     //   116: iload #6
/*    */     //   118: iload #7
/*    */     //   120: iadd
/*    */     //   121: iload #8
/*    */     //   123: iadd
/*    */     //   124: i2f
/*    */     //   125: ldc 0.333
/*    */     //   127: fmul
/*    */     //   128: ldc 0.5
/*    */     //   130: fadd
/*    */     //   131: fstore #9
/*    */     //   133: aload_3
/*    */     //   134: iload #6
/*    */     //   136: ineg
/*    */     //   137: iload #7
/*    */     //   139: ineg
/*    */     //   140: iload #8
/*    */     //   142: ineg
/*    */     //   143: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   146: aload_3
/*    */     //   147: iload #6
/*    */     //   149: iload #7
/*    */     //   151: iload #8
/*    */     //   153: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   156: invokestatic getAllInBox : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
/*    */     //   159: invokeinterface iterator : ()Ljava/util/Iterator;
/*    */     //   164: astore #11
/*    */     //   166: goto -> 212
/*    */     //   169: aload #11
/*    */     //   171: invokeinterface next : ()Ljava/lang/Object;
/*    */     //   176: checkcast net/minecraft/util/BlockPos
/*    */     //   179: astore #10
/*    */     //   181: aload #10
/*    */     //   183: aload_3
/*    */     //   184: invokevirtual distanceSq : (Lnet/minecraft/util/Vec3i;)D
/*    */     //   187: fload #9
/*    */     //   189: fload #9
/*    */     //   191: fmul
/*    */     //   192: f2d
/*    */     //   193: dcmpg
/*    */     //   194: ifgt -> 212
/*    */     //   197: aload_1
/*    */     //   198: aload #10
/*    */     //   200: aload_0
/*    */     //   201: getfield field_150545_a : Lnet/minecraft/block/Block;
/*    */     //   204: invokevirtual getDefaultState : ()Lnet/minecraft/block/state/IBlockState;
/*    */     //   207: iconst_4
/*    */     //   208: invokevirtual setBlockState : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
/*    */     //   211: pop
/*    */     //   212: aload #11
/*    */     //   214: invokeinterface hasNext : ()Z
/*    */     //   219: ifne -> 169
/*    */     //   222: aload_3
/*    */     //   223: iload #4
/*    */     //   225: iconst_1
/*    */     //   226: iadd
/*    */     //   227: ineg
/*    */     //   228: aload_2
/*    */     //   229: iconst_2
/*    */     //   230: iload #4
/*    */     //   232: iconst_2
/*    */     //   233: imul
/*    */     //   234: iadd
/*    */     //   235: invokevirtual nextInt : (I)I
/*    */     //   238: iadd
/*    */     //   239: iconst_0
/*    */     //   240: aload_2
/*    */     //   241: iconst_2
/*    */     //   242: invokevirtual nextInt : (I)I
/*    */     //   245: isub
/*    */     //   246: iload #4
/*    */     //   248: iconst_1
/*    */     //   249: iadd
/*    */     //   250: ineg
/*    */     //   251: aload_2
/*    */     //   252: iconst_2
/*    */     //   253: iload #4
/*    */     //   255: iconst_2
/*    */     //   256: imul
/*    */     //   257: iadd
/*    */     //   258: invokevirtual nextInt : (I)I
/*    */     //   261: iadd
/*    */     //   262: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   265: astore_3
/*    */     //   266: iinc #5, 1
/*    */     //   269: iload #4
/*    */     //   271: iflt -> 280
/*    */     //   274: iload #5
/*    */     //   276: iconst_3
/*    */     //   277: if_icmplt -> 86
/*    */     //   280: iconst_1
/*    */     //   281: ireturn
/*    */     //   282: aload_3
/*    */     //   283: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   286: astore_3
/*    */     //   287: goto -> 0
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     //   #29	-> 8
/*    */     //   #31	-> 19
/*    */     //   #34	-> 22
/*    */     //   #36	-> 37
/*    */     //   #38	-> 61
/*    */     //   #42	-> 64
/*    */     //   #44	-> 72
/*    */     //   #47	-> 74
/*    */     //   #49	-> 80
/*    */     //   #51	-> 86
/*    */     //   #52	-> 96
/*    */     //   #53	-> 106
/*    */     //   #54	-> 116
/*    */     //   #56	-> 133
/*    */     //   #58	-> 181
/*    */     //   #60	-> 197
/*    */     //   #56	-> 212
/*    */     //   #64	-> 222
/*    */     //   #49	-> 266
/*    */     //   #67	-> 280
/*    */     //   #69	-> 282
/*    */     //   #23	-> 287
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	290	0	this	Lnet/minecraft/world/gen/feature/WorldGenBlockBlob;
/*    */     //   0	290	1	worldIn	Lnet/minecraft/world/World;
/*    */     //   0	290	2	rand	Ljava/util/Random;
/*    */     //   0	290	3	position	Lnet/minecraft/util/BlockPos;
/*    */     //   37	27	4	block	Lnet/minecraft/block/Block;
/*    */     //   80	202	4	i1	I
/*    */     //   83	197	5	i	I
/*    */     //   96	170	6	j	I
/*    */     //   106	160	7	k	I
/*    */     //   116	150	8	l	I
/*    */     //   133	133	9	f	F
/*    */     //   181	31	10	blockpos	Lnet/minecraft/util/BlockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\feature\WorldGenBlockBlob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */