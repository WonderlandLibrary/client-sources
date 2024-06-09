/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.block.BlockEndPortalFrame;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEnderEye extends Item {
/*     */   public ItemEnderEye() {
/*  20 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  28 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  30 */     if (playerIn.canPlayerEdit(pos.offset(side), side, stack) && iblockstate.getBlock() == Blocks.end_portal_frame && !((Boolean)iblockstate.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */       
/*  32 */       if (worldIn.isRemote)
/*     */       {
/*  34 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  38 */       worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
/*  39 */       worldIn.updateComparatorOutputLevel(pos, Blocks.end_portal_frame);
/*  40 */       stack.stackSize--;
/*     */       
/*  42 */       for (int i = 0; i < 16; i++) {
/*     */         
/*  44 */         double d0 = (pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  45 */         double d1 = (pos.getY() + 0.8125F);
/*  46 */         double d2 = (pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  47 */         double d3 = 0.0D;
/*  48 */         double d4 = 0.0D;
/*  49 */         double d5 = 0.0D;
/*  50 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */       } 
/*     */       
/*  53 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockEndPortalFrame.FACING);
/*  54 */       int l = 0;
/*  55 */       int j = 0;
/*  56 */       boolean flag1 = false;
/*  57 */       boolean flag = true;
/*  58 */       EnumFacing enumfacing1 = enumfacing.rotateY();
/*     */       
/*  60 */       for (int k = -2; k <= 2; k++) {
/*     */         
/*  62 */         BlockPos blockpos1 = pos.offset(enumfacing1, k);
/*  63 */         IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */         
/*  65 */         if (iblockstate1.getBlock() == Blocks.end_portal_frame) {
/*     */           
/*  67 */           if (!((Boolean)iblockstate1.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */             
/*  69 */             flag = false;
/*     */             
/*     */             break;
/*     */           } 
/*  73 */           j = k;
/*     */           
/*  75 */           if (!flag1) {
/*     */             
/*  77 */             l = k;
/*  78 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  83 */       if (flag && j == l + 2) {
/*     */         
/*  85 */         BlockPos blockpos = pos.offset(enumfacing, 4);
/*     */         
/*  87 */         for (int i1 = l; i1 <= j; i1++) {
/*     */           
/*  89 */           BlockPos blockpos2 = blockpos.offset(enumfacing1, i1);
/*  90 */           IBlockState iblockstate3 = worldIn.getBlockState(blockpos2);
/*     */           
/*  92 */           if (iblockstate3.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate3.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */             
/*  94 */             flag = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  99 */         for (int j1 = l - 1; j1 <= j + 1; j1 += 4) {
/*     */           
/* 101 */           blockpos = pos.offset(enumfacing1, j1);
/*     */           
/* 103 */           for (int l1 = 1; l1 <= 3; l1++) {
/*     */             
/* 105 */             BlockPos blockpos3 = blockpos.offset(enumfacing, l1);
/* 106 */             IBlockState iblockstate2 = worldIn.getBlockState(blockpos3);
/*     */             
/* 108 */             if (iblockstate2.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate2.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */               
/* 110 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 116 */         if (flag)
/*     */         {
/* 118 */           for (int k1 = l; k1 <= j; k1++) {
/*     */             
/* 120 */             blockpos = pos.offset(enumfacing1, k1);
/*     */             
/* 122 */             for (int i2 = 1; i2 <= 3; i2++) {
/*     */               
/* 124 */               BlockPos blockpos4 = blockpos.offset(enumfacing, i2);
/* 125 */               worldIn.setBlockState(blockpos4, Blocks.end_portal.getDefaultState(), 2);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 145 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, false);
/*     */     
/* 147 */     if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldIn.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.end_portal_frame)
/*     */     {
/* 149 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 153 */     if (!worldIn.isRemote) {
/*     */       
/* 155 */       BlockPos blockpos = worldIn.getStrongholdPos("Stronghold", new BlockPos((Entity)playerIn));
/*     */       
/* 157 */       if (blockpos != null) {
/*     */         
/* 159 */         EntityEnderEye entityendereye = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
/* 160 */         entityendereye.moveTowards(blockpos);
/* 161 */         worldIn.spawnEntityInWorld((Entity)entityendereye);
/* 162 */         worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 163 */         worldIn.playAuxSFXAtEntity(null, 1002, new BlockPos((Entity)playerIn), 0);
/*     */         
/* 165 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 167 */           itemStackIn.stackSize--;
/*     */         }
/*     */         
/* 170 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return itemStackIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemEnderEye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */