/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTNT
/*     */   extends Block {
/*  23 */   public static final PropertyBool EXPLODE = PropertyBool.create("explode");
/*     */ 
/*     */   
/*     */   public BlockTNT() {
/*  27 */     super(Material.tnt);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)EXPLODE, Boolean.valueOf(false)));
/*  29 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  34 */     super.onBlockAdded(worldIn, pos, state);
/*     */     
/*  36 */     if (worldIn.isBlockPowered(pos)) {
/*     */       
/*  38 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  39 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  48 */     if (worldIn.isBlockPowered(pos)) {
/*     */       
/*  50 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  51 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/*  60 */     if (!worldIn.isRemote) {
/*     */       
/*  62 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
/*  63 */       entitytntprimed.fuse = worldIn.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
/*  64 */       worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  73 */     explode(worldIn, pos, state, (EntityLivingBase)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
/*  78 */     if (!worldIn.isRemote)
/*     */     {
/*  80 */       if (((Boolean)state.getValue((IProperty)EXPLODE)).booleanValue()) {
/*     */         
/*  82 */         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), igniter);
/*  83 */         worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*  84 */         worldIn.playSoundAtEntity((Entity)entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  91 */     if (playerIn.getCurrentEquippedItem() != null) {
/*     */       
/*  93 */       Item item = playerIn.getCurrentEquippedItem().getItem();
/*     */       
/*  95 */       if (item == Items.flint_and_steel || item == Items.fire_charge) {
/*     */         
/*  97 */         explode(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (EntityLivingBase)playerIn);
/*  98 */         worldIn.setBlockToAir(pos);
/*     */         
/* 100 */         if (item == Items.flint_and_steel) {
/*     */           
/* 102 */           playerIn.getCurrentEquippedItem().damageItem(1, (EntityLivingBase)playerIn);
/*     */         }
/* 104 */         else if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 106 */           (playerIn.getCurrentEquippedItem()).stackSize--;
/*     */         } 
/*     */         
/* 109 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 121 */     if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
/*     */       
/* 123 */       EntityArrow entityarrow = (EntityArrow)entityIn;
/*     */       
/* 125 */       if (entityarrow.isBurning()) {
/*     */         
/* 127 */         explode(worldIn, pos, worldIn.getBlockState(pos).withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (entityarrow.shootingEntity instanceof EntityLivingBase) ? (EntityLivingBase)entityarrow.shootingEntity : null);
/* 128 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 146 */     return getDefaultState().withProperty((IProperty)EXPLODE, Boolean.valueOf(((meta & 0x1) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 154 */     return ((Boolean)state.getValue((IProperty)EXPLODE)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 159 */     return new BlockState(this, new IProperty[] { (IProperty)EXPLODE });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */