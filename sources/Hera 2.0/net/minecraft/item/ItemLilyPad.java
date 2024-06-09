/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLilyPad
/*    */   extends ItemColored {
/*    */   public ItemLilyPad(Block block) {
/* 18 */     super(block, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 26 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*    */     
/* 28 */     if (movingobjectposition == null)
/*    */     {
/* 30 */       return itemStackIn;
/*    */     }
/*    */ 
/*    */     
/* 34 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*    */       
/* 36 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 38 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*    */       {
/* 40 */         return itemStackIn;
/*    */       }
/*    */       
/* 43 */       if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn))
/*    */       {
/* 45 */         return itemStackIn;
/*    */       }
/*    */       
/* 48 */       BlockPos blockpos1 = blockpos.up();
/* 49 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*    */       
/* 51 */       if (iblockstate.getBlock().getMaterial() == Material.water && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0 && worldIn.isAirBlock(blockpos1)) {
/*    */         
/* 53 */         worldIn.setBlockState(blockpos1, Blocks.waterlily.getDefaultState());
/*    */         
/* 55 */         if (!playerIn.capabilities.isCreativeMode)
/*    */         {
/* 57 */           itemStackIn.stackSize--;
/*    */         }
/*    */         
/* 60 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return itemStackIn;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 70 */     return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemLilyPad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */