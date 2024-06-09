/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class BlockBeacon
/*     */   extends BlockContainer {
/*     */   public BlockBeacon() {
/*  26 */     super(Material.glass, MapColor.diamondColor);
/*  27 */     setHardness(3.0F);
/*  28 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  36 */     return (TileEntity)new TileEntityBeacon();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  41 */     if (worldIn.isRemote)
/*     */     {
/*  43 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  47 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  49 */     if (tileentity instanceof TileEntityBeacon) {
/*     */       
/*  51 */       playerIn.displayGUIChest((IInventory)tileentity);
/*  52 */       playerIn.triggerAchievement(StatList.field_181730_N);
/*     */     } 
/*     */     
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  77 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  85 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/*  87 */     if (stack.hasDisplayName()) {
/*     */       
/*  89 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  91 */       if (tileentity instanceof TileEntityBeacon)
/*     */       {
/*  93 */         ((TileEntityBeacon)tileentity).setName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 103 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 105 */     if (tileentity instanceof TileEntityBeacon) {
/*     */       
/* 107 */       ((TileEntityBeacon)tileentity).updateBeacon();
/* 108 */       worldIn.addBlockEvent(pos, this, 1, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 114 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateColorAsync(final World worldIn, final BlockPos glassPos) {
/* 119 */     HttpUtil.field_180193_a.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 123 */             Chunk chunk = worldIn.getChunkFromBlockCoords(glassPos);
/*     */             
/* 125 */             for (int i = glassPos.getY() - 1; i >= 0; i--) {
/*     */               
/* 127 */               final BlockPos blockpos = new BlockPos(glassPos.getX(), i, glassPos.getZ());
/*     */               
/* 129 */               if (!chunk.canSeeSky(blockpos)) {
/*     */                 break;
/*     */               }
/*     */ 
/*     */               
/* 134 */               IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */               
/* 136 */               if (iblockstate.getBlock() == Blocks.beacon)
/*     */               {
/* 138 */                 ((WorldServer)worldIn).addScheduledTask(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 142 */                         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */                         
/* 144 */                         if (tileentity instanceof TileEntityBeacon) {
/*     */                           
/* 146 */                           ((TileEntityBeacon)tileentity).updateBeacon();
/* 147 */                           worldIn.addBlockEvent(blockpos, Blocks.beacon, 1, 0);
/*     */                         } 
/*     */                       }
/*     */                     });
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */