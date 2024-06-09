/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFlowerPot
/*     */   extends BlockContainer
/*     */ {
/*  29 */   public static final PropertyInteger LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
/*  30 */   public static final PropertyEnum<EnumFlowerType> CONTENTS = PropertyEnum.create("contents", EnumFlowerType.class);
/*     */ 
/*     */   
/*     */   public BlockFlowerPot() {
/*  34 */     super(Material.circuits);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)CONTENTS, EnumFlowerType.EMPTY).withProperty((IProperty)LEGACY_DATA, Integer.valueOf(0)));
/*  36 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  44 */     return StatCollector.translateToLocal("item.flowerPot.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  52 */     float f = 0.375F;
/*  53 */     float f1 = f / 2.0F;
/*  54 */     setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  70 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  80 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  82 */     if (tileentity instanceof TileEntityFlowerPot) {
/*     */       
/*  84 */       Item item = ((TileEntityFlowerPot)tileentity).getFlowerPotItem();
/*     */       
/*  86 */       if (item instanceof net.minecraft.item.ItemBlock)
/*     */       {
/*  88 */         return Block.getBlockFromItem(item).colorMultiplier(worldIn, pos, renderPass);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     return 16777215;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  97 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/*  99 */     if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemBlock) {
/*     */       
/* 101 */       TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */       
/* 103 */       if (tileentityflowerpot == null)
/*     */       {
/* 105 */         return false;
/*     */       }
/* 107 */       if (tileentityflowerpot.getFlowerPotItem() != null)
/*     */       {
/* 109 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 113 */       Block block = Block.getBlockFromItem(itemstack.getItem());
/*     */       
/* 115 */       if (!canNotContain(block, itemstack.getMetadata()))
/*     */       {
/* 117 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 121 */       tileentityflowerpot.setFlowerPotData(itemstack.getItem(), itemstack.getMetadata());
/* 122 */       tileentityflowerpot.markDirty();
/* 123 */       worldIn.markBlockForUpdate(pos);
/* 124 */       playerIn.triggerAchievement(StatList.field_181736_T);
/*     */       
/* 126 */       if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
/*     */       {
/* 128 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */       }
/*     */       
/* 131 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canNotContain(Block blockIn, int meta) {
/* 143 */     return (blockIn != Blocks.yellow_flower && blockIn != Blocks.red_flower && blockIn != Blocks.cactus && blockIn != Blocks.brown_mushroom && blockIn != Blocks.red_mushroom && blockIn != Blocks.sapling && blockIn != Blocks.deadbush) ? ((blockIn == Blocks.tallgrass && meta == BlockTallGrass.EnumType.FERN.getMeta())) : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 148 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/* 149 */     return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotItem() : Items.flower_pot;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 154 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/* 155 */     return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotData() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlowerPot() {
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 168 */     return (super.canPlaceBlockAt(worldIn, pos) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 176 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*     */       
/* 178 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 179 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 185 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */     
/* 187 */     if (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null)
/*     */     {
/* 189 */       spawnAsEntity(worldIn, pos, new ItemStack(tileentityflowerpot.getFlowerPotItem(), 1, tileentityflowerpot.getFlowerPotData()));
/*     */     }
/*     */     
/* 192 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 197 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */     
/* 199 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 201 */       TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */       
/* 203 */       if (tileentityflowerpot != null)
/*     */       {
/* 205 */         tileentityflowerpot.setFlowerPotData(null, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 215 */     return Items.flower_pot;
/*     */   }
/*     */ 
/*     */   
/*     */   private TileEntityFlowerPot getTileEntity(World worldIn, BlockPos pos) {
/* 220 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 221 */     return (tileentity instanceof TileEntityFlowerPot) ? (TileEntityFlowerPot)tileentity : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 229 */     Block block = null;
/* 230 */     int i = 0;
/*     */     
/* 232 */     switch (meta) {
/*     */       
/*     */       case 1:
/* 235 */         block = Blocks.red_flower;
/* 236 */         i = BlockFlower.EnumFlowerType.POPPY.getMeta();
/*     */         break;
/*     */       
/*     */       case 2:
/* 240 */         block = Blocks.yellow_flower;
/*     */         break;
/*     */       
/*     */       case 3:
/* 244 */         block = Blocks.sapling;
/* 245 */         i = BlockPlanks.EnumType.OAK.getMetadata();
/*     */         break;
/*     */       
/*     */       case 4:
/* 249 */         block = Blocks.sapling;
/* 250 */         i = BlockPlanks.EnumType.SPRUCE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 5:
/* 254 */         block = Blocks.sapling;
/* 255 */         i = BlockPlanks.EnumType.BIRCH.getMetadata();
/*     */         break;
/*     */       
/*     */       case 6:
/* 259 */         block = Blocks.sapling;
/* 260 */         i = BlockPlanks.EnumType.JUNGLE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 7:
/* 264 */         block = Blocks.red_mushroom;
/*     */         break;
/*     */       
/*     */       case 8:
/* 268 */         block = Blocks.brown_mushroom;
/*     */         break;
/*     */       
/*     */       case 9:
/* 272 */         block = Blocks.cactus;
/*     */         break;
/*     */       
/*     */       case 10:
/* 276 */         block = Blocks.deadbush;
/*     */         break;
/*     */       
/*     */       case 11:
/* 280 */         block = Blocks.tallgrass;
/* 281 */         i = BlockTallGrass.EnumType.FERN.getMeta();
/*     */         break;
/*     */       
/*     */       case 12:
/* 285 */         block = Blocks.sapling;
/* 286 */         i = BlockPlanks.EnumType.ACACIA.getMetadata();
/*     */         break;
/*     */       
/*     */       case 13:
/* 290 */         block = Blocks.sapling;
/* 291 */         i = BlockPlanks.EnumType.DARK_OAK.getMetadata();
/*     */         break;
/*     */     } 
/* 294 */     return (TileEntity)new TileEntityFlowerPot(Item.getItemFromBlock(block), i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 299 */     return new BlockState(this, new IProperty[] { (IProperty)CONTENTS, (IProperty)LEGACY_DATA });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 307 */     return ((Integer)state.getValue((IProperty)LEGACY_DATA)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 316 */     EnumFlowerType blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
/* 317 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 319 */     if (tileentity instanceof TileEntityFlowerPot)
/*     */     
/* 321 */     { TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
/* 322 */       Item item = tileentityflowerpot.getFlowerPotItem();
/*     */       
/* 324 */       if (item instanceof net.minecraft.item.ItemBlock)
/*     */       
/* 326 */       { int i = tileentityflowerpot.getFlowerPotData();
/* 327 */         Block block = Block.getBlockFromItem(item);
/*     */         
/* 329 */         if (block == Blocks.sapling)
/*     */         
/* 331 */         { switch (BlockPlanks.EnumType.byMetadata(i))
/*     */           
/*     */           { case OAK:
/* 334 */               blockflowerpot$enumflowertype = EnumFlowerType.OAK_SAPLING;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 444 */               return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case SPRUCE: blockflowerpot$enumflowertype = EnumFlowerType.SPRUCE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BIRCH: blockflowerpot$enumflowertype = EnumFlowerType.BIRCH_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case JUNGLE: blockflowerpot$enumflowertype = EnumFlowerType.JUNGLE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ACACIA_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case DARK_OAK: blockflowerpot$enumflowertype = EnumFlowerType.DARK_OAK_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.tallgrass) { switch (i) { case 0: blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case 2: blockflowerpot$enumflowertype = EnumFlowerType.FERN; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.yellow_flower) { blockflowerpot$enumflowertype = EnumFlowerType.DANDELION; } else if (block == Blocks.red_flower) { switch (BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, i)) { case POPPY: blockflowerpot$enumflowertype = EnumFlowerType.POPPY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BLUE_ORCHID: blockflowerpot$enumflowertype = EnumFlowerType.BLUE_ORCHID; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ALLIUM; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case HOUSTONIA: blockflowerpot$enumflowertype = EnumFlowerType.HOUSTONIA; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case RED_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.RED_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case ORANGE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.ORANGE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case WHITE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.WHITE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case PINK_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.PINK_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case OXEYE_DAISY: blockflowerpot$enumflowertype = EnumFlowerType.OXEYE_DAISY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.red_mushroom) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_RED; } else if (block == Blocks.brown_mushroom) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_BROWN; } else if (block == Blocks.deadbush) { blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; } else if (block == Blocks.cactus) { blockflowerpot$enumflowertype = EnumFlowerType.CACTUS; }  }  }  return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 449 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public enum EnumFlowerType
/*     */     implements IStringSerializable {
/* 454 */     EMPTY("empty"),
/* 455 */     POPPY("rose"),
/* 456 */     BLUE_ORCHID("blue_orchid"),
/* 457 */     ALLIUM("allium"),
/* 458 */     HOUSTONIA("houstonia"),
/* 459 */     RED_TULIP("red_tulip"),
/* 460 */     ORANGE_TULIP("orange_tulip"),
/* 461 */     WHITE_TULIP("white_tulip"),
/* 462 */     PINK_TULIP("pink_tulip"),
/* 463 */     OXEYE_DAISY("oxeye_daisy"),
/* 464 */     DANDELION("dandelion"),
/* 465 */     OAK_SAPLING("oak_sapling"),
/* 466 */     SPRUCE_SAPLING("spruce_sapling"),
/* 467 */     BIRCH_SAPLING("birch_sapling"),
/* 468 */     JUNGLE_SAPLING("jungle_sapling"),
/* 469 */     ACACIA_SAPLING("acacia_sapling"),
/* 470 */     DARK_OAK_SAPLING("dark_oak_sapling"),
/* 471 */     MUSHROOM_RED("mushroom_red"),
/* 472 */     MUSHROOM_BROWN("mushroom_brown"),
/* 473 */     DEAD_BUSH("dead_bush"),
/* 474 */     FERN("fern"),
/* 475 */     CACTUS("cactus");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumFlowerType(String name) {
/* 481 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 486 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 491 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockFlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */