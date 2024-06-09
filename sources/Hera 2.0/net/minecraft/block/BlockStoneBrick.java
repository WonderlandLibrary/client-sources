/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ 
/*     */ public class BlockStoneBrick
/*     */   extends Block {
/*  16 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*  17 */   public static final int DEFAULT_META = EnumType.DEFAULT.getMetadata();
/*  18 */   public static final int MOSSY_META = EnumType.MOSSY.getMetadata();
/*  19 */   public static final int CRACKED_META = EnumType.CRACKED.getMetadata();
/*  20 */   public static final int CHISELED_META = EnumType.CHISELED.getMetadata();
/*     */ 
/*     */   
/*     */   public BlockStoneBrick() {
/*  24 */     super(Material.rock);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumType.DEFAULT));
/*  26 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  35 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumType[] arrayOfEnumType;
/*  43 */     for (i = (arrayOfEnumType = EnumType.values()).length, b = 0; b < i; ) { EnumType blockstonebrick$enumtype = arrayOfEnumType[b];
/*     */       
/*  45 */       list.add(new ItemStack(itemIn, 1, blockstonebrick$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  54 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  62 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/*  67 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/*  72 */     DEFAULT(0, "stonebrick", "default"),
/*  73 */     MOSSY(1, "mossy_stonebrick", "mossy"),
/*  74 */     CRACKED(2, "cracked_stonebrick", "cracked"),
/*  75 */     CHISELED(3, "chiseled_stonebrick", "chiseled");
/*     */     
/*  77 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
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
/*     */     private final String name;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 120 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockstonebrick$enumtype = arrayOfEnumType[b];
/*     */         
/* 122 */         META_LOOKUP[blockstonebrick$enumtype.getMetadata()] = blockstonebrick$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
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
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockStoneBrick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */