 package me.vene.skilled.utilities;
 
 import java.util.List;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.util.AxisAlignedBB;
 import net.minecraft.util.MovingObjectPosition;
 import net.minecraft.util.Vec3;

 public class ClientUtil
 {
   public static MovingObjectPosition getMouseOver(double distance, double expand, float partialTicks) {
     if (Minecraft.getMinecraft().getRenderViewEntity() != null && (Minecraft.getMinecraft()).theWorld != null) {
       Entity entity = null;
       Vec3 var6 = Minecraft.getMinecraft().getRenderViewEntity().getPositionEyes(partialTicks);
       Vec3 var7 = Minecraft.getMinecraft().getRenderViewEntity().getLook(partialTicks);
       Vec3 var8 = var6.addVector(var7.xCoord * distance, var7.yCoord * distance, var7.zCoord * distance);
       Vec3 var9 = null;
      float var10 = 1.0F;
      List<Entity> var11 = (Minecraft.getMinecraft()).theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().getRenderViewEntity(), Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().addCoord(var7.xCoord * distance, var7.yCoord * distance, var7.zCoord * distance).expand(var10, var10, var10));
       double var12 = distance;
       for (int var14 = 0; var14 < var11.size(); var14++) {
         
         Entity var15 = var11.get(var14);
         if (var15.canBeCollidedWith()) {
           float var16 = var15.getCollisionBorderSize();
           AxisAlignedBB var17 = var15.getEntityBoundingBox().expand(var16, var16, var16);
           var17 = var17.expand(expand, expand, expand);
           MovingObjectPosition var18 = var17.calculateIntercept(var6, var8);
           if (var17.isVecInside(var6))
           { if (0.0D < var12 || var12 == 0.0D) {
               entity = var15;
               var9 = (var18 == null) ? var6 : var18.hitVec;
               var12 = 0.0D;
              } 
         }
           else
           { double var19; if (var18 != null && ((var19 = var6.distanceTo(var18.hitVec)) < var12 || var12 == 0.0D))
              if (var15 == (Minecraft.getMinecraft().getRenderViewEntity()).ridingEntity && !entity.canRiderInteract())
              { if (var12 == 0.0D) {
                   entity = var15;
                   var9 = var18.hitVec;
                 }  
            }
              else
              { entity = var15;
                 var9 = var18.hitVec;
                var12 = var19; 
                }  
            } 
         } 
       }  
       if (var12 < distance && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).canEntityBeSeen((Entity)(Minecraft.getMinecraft()).thePlayer)) {
         return new MovingObjectPosition(entity, var9);
       }
     } 
     return null;
   }
 }

