/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.creativetab.CreativeTabs;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.item.EntityBoat;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.util.MovingObjectPosition;
/*  13:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  14:    */ import net.minecraft.util.Vec3;
/*  15:    */ import net.minecraft.util.Vec3Pool;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class ItemBoat
/*  19:    */   extends Item
/*  20:    */ {
/*  21:    */   private static final String __OBFID = "CL_00001774";
/*  22:    */   
/*  23:    */   public ItemBoat()
/*  24:    */   {
/*  25: 21 */     this.maxStackSize = 1;
/*  26: 22 */     setCreativeTab(CreativeTabs.tabTransport);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  30:    */   {
/*  31: 30 */     float var4 = 1.0F;
/*  32: 31 */     float var5 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * var4;
/*  33: 32 */     float var6 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * var4;
/*  34: 33 */     double var7 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
/*  35: 34 */     double var9 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62D - par3EntityPlayer.yOffset;
/*  36: 35 */     double var11 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
/*  37: 36 */     Vec3 var13 = par2World.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
/*  38: 37 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/*  39: 38 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/*  40: 39 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/*  41: 40 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/*  42: 41 */     float var18 = var15 * var16;
/*  43: 42 */     float var20 = var14 * var16;
/*  44: 43 */     double var21 = 5.0D;
/*  45: 44 */     Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
/*  46: 45 */     MovingObjectPosition var24 = par2World.rayTraceBlocks(var13, var23, true);
/*  47: 47 */     if (var24 == null) {
/*  48: 49 */       return par1ItemStack;
/*  49:    */     }
/*  50: 53 */     Vec3 var25 = par3EntityPlayer.getLook(var4);
/*  51: 54 */     boolean var26 = false;
/*  52: 55 */     float var27 = 1.0F;
/*  53: 56 */     List var28 = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand(var27, var27, var27));
/*  54: 59 */     for (int var29 = 0; var29 < var28.size(); var29++)
/*  55:    */     {
/*  56: 61 */       Entity var30 = (Entity)var28.get(var29);
/*  57: 63 */       if (var30.canBeCollidedWith())
/*  58:    */       {
/*  59: 65 */         float var31 = var30.getCollisionBorderSize();
/*  60: 66 */         AxisAlignedBB var32 = var30.boundingBox.expand(var31, var31, var31);
/*  61: 68 */         if (var32.isVecInside(var13)) {
/*  62: 70 */           var26 = true;
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66: 75 */     if (var26) {
/*  67: 77 */       return par1ItemStack;
/*  68:    */     }
/*  69: 81 */     if (var24.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/*  70:    */     {
/*  71: 83 */       var29 = var24.blockX;
/*  72: 84 */       int var33 = var24.blockY;
/*  73: 85 */       int var34 = var24.blockZ;
/*  74: 87 */       if (par2World.getBlock(var29, var33, var34) == Blocks.snow_layer) {
/*  75: 89 */         var33--;
/*  76:    */       }
/*  77: 92 */       EntityBoat var35 = new EntityBoat(par2World, var29 + 0.5F, var33 + 1.0F, var34 + 0.5F);
/*  78: 93 */       var35.rotationYaw = (((MathHelper.floor_double(par3EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) - 1) * 90);
/*  79: 95 */       if (!par2World.getCollidingBoundingBoxes(var35, var35.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/*  80: 97 */         return par1ItemStack;
/*  81:    */       }
/*  82:100 */       if (!par2World.isClient) {
/*  83:102 */         par2World.spawnEntityInWorld(var35);
/*  84:    */       }
/*  85:105 */       if (!par3EntityPlayer.capabilities.isCreativeMode) {
/*  86:107 */         par1ItemStack.stackSize -= 1;
/*  87:    */       }
/*  88:    */     }
/*  89:111 */     return par1ItemStack;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBoat
 * JD-Core Version:    0.7.0.1
 */