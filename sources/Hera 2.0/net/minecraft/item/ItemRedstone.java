/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/*    */   public ItemRedstone() {
/* 16 */     setCreativeTab(CreativeTabs.tabRedstone);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 24 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/* 25 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*    */     
/* 27 */     if (!playerIn.canPlayerEdit(blockpos, side, stack))
/*    */     {
/* 29 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 33 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */     
/* 35 */     if (!worldIn.canBlockBePlaced(block, blockpos, false, side, null, stack))
/*    */     {
/* 37 */       return false;
/*    */     }
/* 39 */     if (Blocks.redstone_wire.canPlaceBlockAt(worldIn, blockpos)) {
/*    */       
/* 41 */       stack.stackSize--;
/* 42 */       worldIn.setBlockState(blockpos, Blocks.redstone_wire.getDefaultState());
/* 43 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemRedstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */