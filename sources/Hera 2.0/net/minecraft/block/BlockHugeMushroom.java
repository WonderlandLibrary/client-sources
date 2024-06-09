/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockHugeMushroom
/*     */   extends Block {
/*  19 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */   
/*     */   private final Block smallBlock;
/*     */   
/*     */   public BlockHugeMushroom(Material p_i46392_1_, MapColor p_i46392_2_, Block p_i46392_3_) {
/*  24 */     super(p_i46392_1_, p_i46392_2_);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumType.ALL_OUTSIDE));
/*  26 */     this.smallBlock = p_i46392_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  34 */     return Math.max(0, random.nextInt(10) - 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  42 */     switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */       
/*     */       case ALL_STEM:
/*  45 */         return MapColor.clothColor;
/*     */       
/*     */       case null:
/*  48 */         return MapColor.sandColor;
/*     */       
/*     */       case STEM:
/*  51 */         return MapColor.sandColor;
/*     */     } 
/*     */     
/*  54 */     return super.getMapColor(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  63 */     return Item.getItemFromBlock(this.smallBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  68 */     return Item.getItemFromBlock(this.smallBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  77 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  85 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  93 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/*  98 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 103 */     NORTH_WEST(1, "north_west"),
/* 104 */     NORTH(2, "north"),
/* 105 */     NORTH_EAST(3, "north_east"),
/* 106 */     WEST(4, "west"),
/* 107 */     CENTER(5, "center"),
/* 108 */     EAST(6, "east"),
/* 109 */     SOUTH_WEST(7, "south_west"),
/* 110 */     SOUTH(8, "south"),
/* 111 */     SOUTH_EAST(9, "south_east"),
/* 112 */     STEM(10, "stem"),
/* 113 */     ALL_INSIDE(0, "all_inside"),
/* 114 */     ALL_OUTSIDE(14, "all_outside"),
/* 115 */     ALL_STEM(15, "all_stem");
/*     */     
/* 117 */     private static final EnumType[] META_LOOKUP = new EnumType[16];
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
/*     */     private final int meta;
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
/*     */     private final String name;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 154 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockhugemushroom$enumtype = arrayOfEnumType[b];
/*     */         
/* 156 */         META_LOOKUP[blockhugemushroom$enumtype.getMetadata()] = blockhugemushroom$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       EnumType blockhugemushroom$enumtype = META_LOOKUP[meta];
/*     */       return (blockhugemushroom$enumtype == null) ? META_LOOKUP[0] : blockhugemushroom$enumtype;
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockHugeMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */