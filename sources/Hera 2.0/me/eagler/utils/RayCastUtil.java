/*    */ package me.eagler.utils;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import optfine.Reflector;
/*    */ 
/*    */ public class RayCastUtil {
/* 12 */   private static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static MovingObjectPosition getMouseOver(float yaw, float pitch, float range) {
/* 15 */     float partialTicks = 1.0F;
/* 16 */     Entity var2 = mc.getRenderViewEntity();
/* 17 */     if (var2 != null && mc.theWorld != null) {
/* 18 */       double var3 = mc.playerController.getBlockReachDistance();
/* 19 */       MovingObjectPosition objectMouseOver = var2.rayTrace(var3, partialTicks);
/* 20 */       double var5 = var3;
/* 21 */       Vec3 var7 = var2.getPositionEyes(partialTicks);
/* 22 */       var3 = range;
/* 23 */       var5 = range;
/* 24 */       if (objectMouseOver != null)
/* 25 */         var5 = objectMouseOver.hitVec.distanceTo(var7); 
/* 26 */       Vec3 var8 = var2.getLook(partialTicks);
/* 27 */       Vec3 var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
/* 28 */       Entity pointedEntity = null;
/* 29 */       Vec3 var10 = null;
/* 30 */       float var11 = 1.0F;
/* 31 */       List<Entity> var12 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox()
/* 32 */           .addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand(var11, var11, var11));
/* 33 */       double var13 = var5;
/* 34 */       for (int var15 = 0; var15 < var12.size(); var15++) {
/* 35 */         Entity var16 = var12.get(var15);
/* 36 */         if (var16.canBeCollidedWith()) {
/* 37 */           float var17 = var16.getCollisionBorderSize();
/* 38 */           AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
/* 39 */           MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
/* 40 */           if (var18.isVecInside(var7)) {
/* 41 */             if (0.0D < var13 || var13 == 0.0D) {
/* 42 */               pointedEntity = var16;
/* 43 */               var10 = (var19 == null) ? var7 : var19.hitVec;
/* 44 */               var13 = 0.0D;
/*    */             } 
/* 46 */           } else if (var19 != null) {
/* 47 */             double var20 = var7.distanceTo(var19.hitVec);
/* 48 */             if (var20 < var13 || var13 == 0.0D) {
/* 49 */               boolean canRiderInteract = false;
/* 50 */               if (Reflector.ForgeEntity_canRiderInteract.exists())
/* 51 */                 canRiderInteract = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract, 
/* 52 */                     new Object[0]); 
/* 53 */               if (var16 == var2.ridingEntity && !canRiderInteract) {
/* 54 */                 if (var13 == 0.0D) {
/* 55 */                   pointedEntity = var16;
/* 56 */                   var10 = var19.hitVec;
/*    */                 } 
/*    */               } else {
/* 59 */                 pointedEntity = var16;
/* 60 */                 var10 = var19.hitVec;
/* 61 */                 var13 = var20;
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/* 67 */       if (pointedEntity != null && (var13 < var5 || mc.objectMouseOver == null)) {
/* 68 */         objectMouseOver = new MovingObjectPosition(pointedEntity, var10);
/* 69 */         if (pointedEntity instanceof net.minecraft.entity.EntityLivingBase || 
/* 70 */           pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame)
/* 71 */           return objectMouseOver; 
/*    */       } 
/* 73 */       return objectMouseOver;
/*    */     } 
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagle\\utils\RayCastUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */