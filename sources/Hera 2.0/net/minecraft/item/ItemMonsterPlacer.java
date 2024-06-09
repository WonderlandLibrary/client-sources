/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemMonsterPlacer
/*     */   extends Item
/*     */ {
/*     */   public ItemMonsterPlacer() {
/*  30 */     setHasSubtypes(true);
/*  31 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  36 */     String s = StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + ".name").trim();
/*  37 */     String s1 = EntityList.getStringFromID(stack.getMetadata());
/*     */     
/*  39 */     if (s1 != null)
/*     */     {
/*  41 */       s = String.valueOf(s) + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  44 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  49 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getMetadata()));
/*  50 */     return (entitylist$entityegginfo != null) ? ((renderPass == 0) ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  58 */     if (worldIn.isRemote)
/*     */     {
/*  60 */       return true;
/*     */     }
/*  62 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  64 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  68 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  70 */     if (iblockstate.getBlock() == Blocks.mob_spawner) {
/*     */       
/*  72 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  74 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/*  76 */         MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
/*  77 */         mobspawnerbaselogic.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
/*  78 */         tileentity.markDirty();
/*  79 */         worldIn.markBlockForUpdate(pos);
/*     */         
/*  81 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/*  83 */           stack.stackSize--;
/*     */         }
/*     */         
/*  86 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     pos = pos.offset(side);
/*  91 */     double d0 = 0.0D;
/*     */     
/*  93 */     if (side == EnumFacing.UP && iblockstate instanceof net.minecraft.block.BlockFence)
/*     */     {
/*  95 */       d0 = 0.5D;
/*     */     }
/*     */     
/*  98 */     Entity entity = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);
/*     */     
/* 100 */     if (entity != null) {
/*     */       
/* 102 */       if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName())
/*     */       {
/* 104 */         entity.setCustomNameTag(stack.getDisplayName());
/*     */       }
/*     */       
/* 107 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 109 */         stack.stackSize--;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 122 */     if (worldIn.isRemote)
/*     */     {
/* 124 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 128 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*     */     
/* 130 */     if (movingobjectposition == null)
/*     */     {
/* 132 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 136 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*     */       
/* 138 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/* 140 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*     */       {
/* 142 */         return itemStackIn;
/*     */       }
/*     */       
/* 145 */       if (!playerIn.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemStackIn))
/*     */       {
/* 147 */         return itemStackIn;
/*     */       }
/*     */       
/* 150 */       if (worldIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockLiquid) {
/*     */         
/* 152 */         Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */         
/* 154 */         if (entity != null) {
/*     */           
/* 156 */           if (entity instanceof net.minecraft.entity.EntityLivingBase && itemStackIn.hasDisplayName())
/*     */           {
/* 158 */             ((EntityLiving)entity).setCustomNameTag(itemStackIn.getDisplayName());
/*     */           }
/*     */           
/* 161 */           if (!playerIn.capabilities.isCreativeMode)
/*     */           {
/* 163 */             itemStackIn.stackSize--;
/*     */           }
/*     */           
/* 166 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
/* 182 */     if (!EntityList.entityEggs.containsKey(Integer.valueOf(entityID)))
/*     */     {
/* 184 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 188 */     Entity entity = null;
/*     */     
/* 190 */     for (int i = 0; i < 1; i++) {
/*     */       
/* 192 */       entity = EntityList.createEntityByID(entityID, worldIn);
/*     */       
/* 194 */       if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/*     */         
/* 196 */         EntityLiving entityliving = (EntityLiving)entity;
/* 197 */         entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
/* 198 */         entityliving.rotationYawHead = entityliving.rotationYaw;
/* 199 */         entityliving.renderYawOffset = entityliving.rotationYaw;
/* 200 */         entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), null);
/* 201 */         worldIn.spawnEntityInWorld(entity);
/* 202 */         entityliving.playLivingSound();
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 215 */     for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values())
/*     */     {
/* 217 */       subItems.add(new ItemStack(itemIn, 1, entitylist$entityegginfo.spawnedID));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemMonsterPlacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */