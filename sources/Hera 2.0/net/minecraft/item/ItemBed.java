/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBed extends Item {
/*    */   public ItemBed() {
/* 18 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 26 */     if (worldIn.isRemote)
/*    */     {
/* 28 */       return true;
/*    */     }
/* 30 */     if (side != EnumFacing.UP)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 37 */     Block block = iblockstate.getBlock();
/* 38 */     boolean flag = block.isReplaceable(worldIn, pos);
/*    */     
/* 40 */     if (!flag)
/*    */     {
/* 42 */       pos = pos.up();
/*    */     }
/*    */     
/* 45 */     int i = MathHelper.floor_double((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 46 */     EnumFacing enumfacing = EnumFacing.getHorizontal(i);
/* 47 */     BlockPos blockpos = pos.offset(enumfacing);
/*    */     
/* 49 */     if (playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(blockpos, side, stack)) {
/*    */       
/* 51 */       boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
/* 52 */       boolean flag2 = !(!flag && !worldIn.isAirBlock(pos));
/* 53 */       boolean flag3 = !(!flag1 && !worldIn.isAirBlock(blockpos));
/*    */       
/* 55 */       if (flag2 && flag3 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos.down())) {
/*    */         
/* 57 */         IBlockState iblockstate1 = Blocks.bed.getDefaultState().withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty((IProperty)BlockBed.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.FOOT);
/*    */         
/* 59 */         if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/*    */           
/* 61 */           IBlockState iblockstate2 = iblockstate1.withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.HEAD);
/* 62 */           worldIn.setBlockState(blockpos, iblockstate2, 3);
/*    */         } 
/*    */         
/* 65 */         stack.stackSize--;
/* 66 */         return true;
/*    */       } 
/*    */ 
/*    */       
/* 70 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */