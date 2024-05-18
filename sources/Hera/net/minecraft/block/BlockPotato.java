/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockPotato
/*    */   extends BlockCrops {
/*    */   protected Item getSeed() {
/* 14 */     return Items.potato;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getCrop() {
/* 19 */     return Items.potato;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 27 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*    */     
/* 29 */     if (!worldIn.isRemote)
/*    */     {
/* 31 */       if (((Integer)state.getValue((IProperty)AGE)).intValue() >= 7 && worldIn.rand.nextInt(50) == 0)
/*    */       {
/* 33 */         spawnAsEntity(worldIn, pos, new ItemStack(Items.poisonous_potato));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockPotato.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */