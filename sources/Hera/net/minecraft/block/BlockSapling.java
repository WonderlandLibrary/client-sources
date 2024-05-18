/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenForest;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockSapling
/*     */   extends BlockBush implements IGrowable {
/*  29 */   public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
/*  30 */   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
/*     */ 
/*     */   
/*     */   protected BlockSapling() {
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.OAK).withProperty((IProperty)STAGE, Integer.valueOf(0)));
/*  35 */     float f = 0.4F;
/*  36 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  37 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  45 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  50 */     if (!worldIn.isRemote) {
/*     */       
/*  52 */       super.updateTick(worldIn, pos, state, rand);
/*     */       
/*  54 */       if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
/*     */       {
/*  56 */         grow(worldIn, pos, state, rand);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  63 */     if (((Integer)state.getValue((IProperty)STAGE)).intValue() == 0) {
/*     */       
/*  65 */       worldIn.setBlockState(pos, state.cycleProperty((IProperty)STAGE), 4);
/*     */     }
/*     */     else {
/*     */       
/*  69 */       generateTree(worldIn, pos, state, rand);
/*     */     }  } public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenForest worldGenForest;
/*     */     WorldGenSavannaTree worldGenSavannaTree;
/*     */     WorldGenCanopyTree worldGenCanopyTree;
/*     */     IBlockState iblockstate, iblockstate1;
/*  75 */     WorldGenerator worldgenerator = (rand.nextInt(10) == 0) ? (WorldGenerator)new WorldGenBigTree(true) : (WorldGenerator)new WorldGenTrees(true);
/*  76 */     int i = 0;
/*  77 */     int j = 0;
/*  78 */     boolean flag = false;
/*     */     
/*  80 */     switch ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)) {
/*     */ 
/*     */       
/*     */       case SPRUCE:
/*  84 */         label68: for (i = 0; i >= -1; i--) {
/*     */           
/*  86 */           for (j = 0; j >= -1; j--) {
/*     */             
/*  88 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.SPRUCE)) {
/*     */               
/*  90 */               WorldGenMegaPineTree worldGenMegaPineTree = new WorldGenMegaPineTree(false, rand.nextBoolean());
/*  91 */               flag = true;
/*     */               
/*     */               break label68;
/*     */             } 
/*     */           } 
/*     */         } 
/*  97 */         if (!flag) {
/*     */           
/*  99 */           j = 0;
/* 100 */           i = 0;
/* 101 */           WorldGenTaiga2 worldGenTaiga2 = new WorldGenTaiga2(true);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case BIRCH:
/* 107 */         worldGenForest = new WorldGenForest(true, false);
/*     */         break;
/*     */       
/*     */       case JUNGLE:
/* 111 */         iblockstate = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
/* 112 */         iblockstate1 = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */         
/* 115 */         label69: for (i = 0; i >= -1; i--) {
/*     */           
/* 117 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 119 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.JUNGLE)) {
/*     */               
/* 121 */               WorldGenMegaJungle worldGenMegaJungle = new WorldGenMegaJungle(true, 10, 20, iblockstate, iblockstate1);
/* 122 */               flag = true;
/*     */               
/*     */               break label69;
/*     */             } 
/*     */           } 
/*     */         } 
/* 128 */         if (!flag) {
/*     */           
/* 130 */           j = 0;
/* 131 */           i = 0;
/* 132 */           WorldGenTrees worldGenTrees = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case null:
/* 138 */         worldGenSavannaTree = new WorldGenSavannaTree(true);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DARK_OAK:
/* 143 */         label70: for (i = 0; i >= -1; i--) {
/*     */           
/* 145 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 147 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.DARK_OAK)) {
/*     */               
/* 149 */               worldGenCanopyTree = new WorldGenCanopyTree(true);
/* 150 */               flag = true;
/*     */               
/*     */               break label70;
/*     */             } 
/*     */           } 
/*     */         } 
/* 156 */         if (!flag) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     IBlockState iblockstate2 = Blocks.air.getDefaultState();
/*     */     
/* 166 */     if (flag) {
/*     */       
/* 168 */       worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
/* 169 */       worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
/* 170 */       worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
/* 171 */       worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
/*     */     }
/*     */     else {
/*     */       
/* 175 */       worldIn.setBlockState(pos, iblockstate2, 4);
/*     */     } 
/*     */     
/* 178 */     if (!worldGenCanopyTree.generate(worldIn, rand, pos.add(i, 0, j)))
/*     */     {
/* 180 */       if (flag) {
/*     */         
/* 182 */         worldIn.setBlockState(pos.add(i, 0, j), state, 4);
/* 183 */         worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
/* 184 */         worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
/* 185 */         worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
/*     */       }
/*     */       else {
/*     */         
/* 189 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181624_a(World p_181624_1_, BlockPos p_181624_2_, int p_181624_3_, int p_181624_4_, BlockPlanks.EnumType p_181624_5_) {
/* 196 */     return (isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_ + 1), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), p_181624_5_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTypeAt(World worldIn, BlockPos pos, BlockPlanks.EnumType type) {
/* 204 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 205 */     return (iblockstate.getBlock() == this && iblockstate.getValue((IProperty)TYPE) == type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 214 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     BlockPlanks.EnumType[] arrayOfEnumType;
/* 222 */     for (i = (arrayOfEnumType = BlockPlanks.EnumType.values()).length, b = 0; b < i; ) { BlockPlanks.EnumType blockplanks$enumtype = arrayOfEnumType[b];
/*     */       
/* 224 */       list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 238 */     return (worldIn.rand.nextFloat() < 0.45D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 243 */     grow(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 251 */     return getDefaultState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.byMetadata(meta & 0x7)).withProperty((IProperty)STAGE, Integer.valueOf((meta & 0x8) >> 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 259 */     int i = 0;
/* 260 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/* 261 */     i |= ((Integer)state.getValue((IProperty)STAGE)).intValue() << 3;
/* 262 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 267 */     return new BlockState(this, new IProperty[] { (IProperty)TYPE, (IProperty)STAGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockSapling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */