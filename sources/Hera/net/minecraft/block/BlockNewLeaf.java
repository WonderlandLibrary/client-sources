/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNewLeaf
/*     */   extends BlockLeaves {
/*  21 */   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>()
/*     */       {
/*     */         public boolean apply(BlockPlanks.EnumType p_apply_1_)
/*     */         {
/*  25 */           return (p_apply_1_.getMetadata() >= 4);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   public BlockNewLeaf() {
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.ACACIA).withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(true)).withProperty((IProperty)DECAYABLE, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
/*  36 */     if (state.getValue((IProperty)VARIANT) == BlockPlanks.EnumType.DARK_OAK && worldIn.rand.nextInt(chance) == 0)
/*     */     {
/*  38 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.apple, 1, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  48 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  53 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  54 */     return iblockstate.getBlock().getMetaFromState(iblockstate) & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  62 */     list.add(new ItemStack(itemIn, 1, 0));
/*  63 */     list.add(new ItemStack(itemIn, 1, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/*  68 */     return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  76 */     return getDefaultState().withProperty((IProperty)VARIANT, getWoodType(meta)).withProperty((IProperty)DECAYABLE, Boolean.valueOf(((meta & 0x4) == 0))).withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  84 */     int i = 0;
/*  85 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4;
/*     */     
/*  87 */     if (!((Boolean)state.getValue((IProperty)DECAYABLE)).booleanValue())
/*     */     {
/*  89 */       i |= 0x4;
/*     */     }
/*     */     
/*  92 */     if (((Boolean)state.getValue((IProperty)CHECK_DECAY)).booleanValue())
/*     */     {
/*  94 */       i |= 0x8;
/*     */     }
/*     */     
/*  97 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPlanks.EnumType getWoodType(int meta) {
/* 102 */     return BlockPlanks.EnumType.byMetadata((meta & 0x3) + 4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 107 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT, (IProperty)CHECK_DECAY, (IProperty)DECAYABLE });
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 112 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/*     */       
/* 114 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 115 */       spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4));
/*     */     }
/*     */     else {
/*     */       
/* 119 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockNewLeaf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */