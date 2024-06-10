/*  1:   */ package net.minecraft.entity.passive;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityAgeable;
/*  4:   */ import net.minecraft.entity.item.EntityItem;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  7:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.init.Items;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class EntityMooshroom
/* 14:   */   extends EntityCow
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00001645";
/* 17:   */   
/* 18:   */   public EntityMooshroom(World par1World)
/* 19:   */   {
/* 20:17 */     super(par1World);
/* 21:18 */     setSize(0.9F, 1.3F);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 25:   */   {
/* 26:26 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 27:28 */     if ((var2 != null) && (var2.getItem() == Items.bowl) && (getGrowingAge() >= 0))
/* 28:   */     {
/* 29:30 */       if (var2.stackSize == 1)
/* 30:   */       {
/* 31:32 */         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Items.mushroom_stew));
/* 32:33 */         return true;
/* 33:   */       }
/* 34:36 */       if ((par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))) && (!par1EntityPlayer.capabilities.isCreativeMode))
/* 35:   */       {
/* 36:38 */         par1EntityPlayer.inventory.decrStackSize(par1EntityPlayer.inventory.currentItem, 1);
/* 37:39 */         return true;
/* 38:   */       }
/* 39:   */     }
/* 40:43 */     if ((var2 != null) && (var2.getItem() == Items.shears) && (getGrowingAge() >= 0))
/* 41:   */     {
/* 42:45 */       setDead();
/* 43:46 */       this.worldObj.spawnParticle("largeexplode", this.posX, this.posY + this.height / 2.0F, this.posZ, 0.0D, 0.0D, 0.0D);
/* 44:48 */       if (!this.worldObj.isClient)
/* 45:   */       {
/* 46:50 */         EntityCow var3 = new EntityCow(this.worldObj);
/* 47:51 */         var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 48:52 */         var3.setHealth(getHealth());
/* 49:53 */         var3.renderYawOffset = this.renderYawOffset;
/* 50:54 */         this.worldObj.spawnEntityInWorld(var3);
/* 51:56 */         for (int var4 = 0; var4 < 5; var4++) {
/* 52:58 */           this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack(Blocks.red_mushroom)));
/* 53:   */         }
/* 54:61 */         var2.damageItem(1, par1EntityPlayer);
/* 55:62 */         playSound("mob.sheep.shear", 1.0F, 1.0F);
/* 56:   */       }
/* 57:65 */       return true;
/* 58:   */     }
/* 59:69 */     return super.interact(par1EntityPlayer);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public EntityMooshroom createChild(EntityAgeable par1EntityAgeable)
/* 63:   */   {
/* 64:75 */     return new EntityMooshroom(this.worldObj);
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityMooshroom
 * JD-Core Version:    0.7.0.1
 */