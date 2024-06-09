/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSilverfish extends Block {
/*  21 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */ 
/*     */   
/*     */   public BlockSilverfish() {
/*  25 */     super(Material.clay);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumType.STONE));
/*  27 */     setHardness(0.0F);
/*  28 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  36 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canContainSilverfish(IBlockState blockState) {
/*  41 */     Block block = blockState.getBlock();
/*  42 */     return !(blockState != Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, BlockStone.EnumType.STONE) && block != Blocks.cobblestone && block != Blocks.stonebrick);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/*  47 */     switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */       
/*     */       case COBBLESTONE:
/*  50 */         return new ItemStack(Blocks.cobblestone);
/*     */       
/*     */       case STONEBRICK:
/*  53 */         return new ItemStack(Blocks.stonebrick);
/*     */       
/*     */       case MOSSY_STONEBRICK:
/*  56 */         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetadata());
/*     */       
/*     */       case CRACKED_STONEBRICK:
/*  59 */         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetadata());
/*     */       
/*     */       case null:
/*  62 */         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetadata());
/*     */     } 
/*     */     
/*  65 */     return new ItemStack(Blocks.stone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  74 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops")) {
/*     */       
/*  76 */       EntitySilverfish entitysilverfish = new EntitySilverfish(worldIn);
/*  77 */       entitysilverfish.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/*  78 */       worldIn.spawnEntityInWorld((Entity)entitysilverfish);
/*  79 */       entitysilverfish.spawnExplosionParticle();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  85 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  86 */     return iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumType[] arrayOfEnumType;
/*  94 */     for (i = (arrayOfEnumType = EnumType.values()).length, b = 0; b < i; ) { EnumType blocksilverfish$enumtype = arrayOfEnumType[b];
/*     */       
/*  96 */       list.add(new ItemStack(itemIn, 1, blocksilverfish$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 105 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 113 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 118 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 123 */     STONE(0, "stone")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 127 */         return Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, BlockStone.EnumType.STONE);
/*     */       }
/*     */     },
/* 130 */     COBBLESTONE(1, "cobblestone", "cobble")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 134 */         return Blocks.cobblestone.getDefaultState();
/*     */       }
/*     */     },
/* 137 */     STONEBRICK(2, "stone_brick", "brick")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 141 */         return Blocks.stonebrick.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
/*     */       }
/*     */     },
/* 144 */     MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 148 */         return Blocks.stonebrick.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
/*     */       }
/*     */     },
/* 151 */     CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 155 */         return Blocks.stonebrick.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
/*     */       }
/*     */     },
/* 158 */     CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick")
/*     */     {
/*     */       public IBlockState getModelBlock()
/*     */       {
/* 162 */         return Blocks.stonebrick.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
/*     */       }
/*     */     };
/*     */     
/* 166 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 229 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blocksilverfish$enumtype = arrayOfEnumType[b];
/*     */         
/* 231 */         META_LOOKUP[blocksilverfish$enumtype.getMetadata()] = blocksilverfish$enumtype;
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
/*     */     
/*     */     public static EnumType forModelBlock(IBlockState model) {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/*     */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) {
/*     */         EnumType blocksilverfish$enumtype = arrayOfEnumType[b];
/*     */         if (model == blocksilverfish$enumtype.getModelBlock())
/*     */           return blocksilverfish$enumtype; 
/*     */         b++;
/*     */       } 
/*     */       return STONE;
/*     */     }
/*     */     
/*     */     public abstract IBlockState getModelBlock();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockSilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */