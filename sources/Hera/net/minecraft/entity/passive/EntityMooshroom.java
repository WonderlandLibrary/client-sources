/*    */ package net.minecraft.entity.passive;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityAgeable;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMooshroom extends EntityCow {
/*    */   public EntityMooshroom(World worldIn) {
/* 16 */     super(worldIn);
/* 17 */     setSize(0.9F, 1.3F);
/* 18 */     this.spawnableBlock = (Block)Blocks.mycelium;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interact(EntityPlayer player) {
/* 26 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*    */     
/* 28 */     if (itemstack != null && itemstack.getItem() == Items.bowl && getGrowingAge() >= 0) {
/*    */       
/* 30 */       if (itemstack.stackSize == 1) {
/*    */         
/* 32 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.mushroom_stew));
/* 33 */         return true;
/*    */       } 
/*    */       
/* 36 */       if (player.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew)) && !player.capabilities.isCreativeMode) {
/*    */         
/* 38 */         player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 39 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     if (itemstack != null && itemstack.getItem() == Items.shears && getGrowingAge() >= 0) {
/*    */       
/* 45 */       setDead();
/* 46 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */       
/* 48 */       if (!this.worldObj.isRemote) {
/*    */         
/* 50 */         EntityCow entitycow = new EntityCow(this.worldObj);
/* 51 */         entitycow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 52 */         entitycow.setHealth(getHealth());
/* 53 */         entitycow.renderYawOffset = this.renderYawOffset;
/*    */         
/* 55 */         if (hasCustomName())
/*    */         {
/* 57 */           entitycow.setCustomNameTag(getCustomNameTag());
/*    */         }
/*    */         
/* 60 */         this.worldObj.spawnEntityInWorld((Entity)entitycow);
/*    */         
/* 62 */         for (int i = 0; i < 5; i++)
/*    */         {
/* 64 */           this.worldObj.spawnEntityInWorld((Entity)new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack((Block)Blocks.red_mushroom)));
/*    */         }
/*    */         
/* 67 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 68 */         playSound("mob.sheep.shear", 1.0F, 1.0F);
/*    */       } 
/*    */       
/* 71 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return super.interact(player);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityMooshroom createChild(EntityAgeable ageable) {
/* 81 */     return new EntityMooshroom(this.worldObj);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\passive\EntityMooshroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */