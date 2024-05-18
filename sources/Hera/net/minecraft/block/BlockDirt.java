/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDirt
/*     */   extends Block {
/*  22 */   public static final PropertyEnum<DirtType> VARIANT = PropertyEnum.create("variant", DirtType.class);
/*  23 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */ 
/*     */   
/*     */   protected BlockDirt() {
/*  27 */     super(Material.ground);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, DirtType.DIRT).withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  29 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  37 */     return ((DirtType)state.getValue((IProperty)VARIANT)).func_181066_d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  46 */     if (state.getValue((IProperty)VARIANT) == DirtType.PODZOL) {
/*     */       
/*  48 */       Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  49 */       state = state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.snow && block != Blocks.snow_layer)));
/*     */     } 
/*     */     
/*  52 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  60 */     list.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
/*  61 */     list.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
/*  62 */     list.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  67 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  68 */     return (iblockstate.getBlock() != this) ? 0 : ((DirtType)iblockstate.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  76 */     return getDefaultState().withProperty((IProperty)VARIANT, DirtType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  84 */     return ((DirtType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/*  89 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT, (IProperty)SNOWY });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  98 */     DirtType blockdirt$dirttype = (DirtType)state.getValue((IProperty)VARIANT);
/*     */     
/* 100 */     if (blockdirt$dirttype == DirtType.PODZOL)
/*     */     {
/* 102 */       blockdirt$dirttype = DirtType.DIRT;
/*     */     }
/*     */     
/* 105 */     return blockdirt$dirttype.getMetadata();
/*     */   }
/*     */   
/*     */   public enum DirtType
/*     */     implements IStringSerializable {
/* 110 */     DIRT(0, "dirt", "default", (String)MapColor.dirtColor),
/* 111 */     COARSE_DIRT(1, "coarse_dirt", "coarse", (String)MapColor.dirtColor),
/* 112 */     PODZOL(2, "podzol", MapColor.obsidianColor);
/*     */     
/* 114 */     private static final DirtType[] METADATA_LOOKUP = new DirtType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int metadata;
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
/*     */     private final String unlocalizedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final MapColor field_181067_h;
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
/*     */       DirtType[] arrayOfDirtType;
/* 169 */       for (i = (arrayOfDirtType = values()).length, b = 0; b < i; ) { DirtType blockdirt$dirttype = arrayOfDirtType[b];
/*     */         
/* 171 */         METADATA_LOOKUP[blockdirt$dirttype.getMetadata()] = blockdirt$dirttype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     DirtType(int p_i46397_3_, String p_i46397_4_, String p_i46397_5_, MapColor p_i46397_6_) {
/*     */       this.metadata = p_i46397_3_;
/*     */       this.name = p_i46397_4_;
/*     */       this.unlocalizedName = p_i46397_5_;
/*     */       this.field_181067_h = p_i46397_6_;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.metadata;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public MapColor func_181066_d() {
/*     */       return this.field_181067_h;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static DirtType byMetadata(int metadata) {
/*     */       if (metadata < 0 || metadata >= METADATA_LOOKUP.length)
/*     */         metadata = 0; 
/*     */       return METADATA_LOOKUP[metadata];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockDirt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */