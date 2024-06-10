/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class RotationUtils
/*    */ {
/* 11 */   private static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public static boolean isVisibleFOV(Entity e, float fov) {
/* 14 */     return (((Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F > 180.0F) ? (360.0F - Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F) : (Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F)) <= fov);
/*    */   }
/*    */   private static float[] getRotations(Entity entity) {
/*    */     double diffY;
/* 18 */     if (entity == null) {
/* 19 */       return null;
/*    */     }
/* 21 */     double diffX = entity.field_70165_t - mc.field_71439_g.field_70165_t;
/* 22 */     double diffZ = entity.field_70161_v - mc.field_71439_g.field_70161_v;
/*    */     
/* 24 */     if (entity instanceof EntityLivingBase) {
/* 25 */       EntityLivingBase elb = (EntityLivingBase)entity;
/* 26 */       diffY = elb.field_70163_u + elb.func_70047_e() - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/*    */     } else {
/* 28 */       diffY = ((entity.func_174813_aQ()).field_72338_b + (entity.func_174813_aQ()).field_72337_e) / 2.0D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/*    */     } 
/* 30 */     double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
/* 31 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 32 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 33 */     return new float[] { yaw, pitch };
/*    */   }
/*    */   
/*    */   public static float[] getAngles(Entity entity) {
/* 37 */     double x = entity.field_70165_t - mc.field_71439_g.field_70165_t;
/* 38 */     double z = entity.field_70161_v - mc.field_71439_g.field_70161_v;
/*    */ 
/*    */     
/* 41 */     double y = (entity instanceof net.minecraft.entity.monster.EntityEnderman) ? (entity.field_70163_u - mc.field_71439_g.field_70163_u) : (entity.field_70163_u + entity.func_70047_e() - 1.9D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e() - 1.9D);
/* 42 */     double helper = MathHelper.func_76133_a(x * x + z * z);
/* 43 */     float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
/* 44 */     float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));
/* 45 */     if (z < 0.0D && x < 0.0D) {
/* 46 */       newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
/* 47 */     } else if (z < 0.0D && x > 0.0D) {
/* 48 */       newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
/*    */     } 
/* 50 */     return new float[] { newPitch, newYaw };
/*    */   }
/*    */   
/*    */   public static float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
/* 54 */     float deltaAngle = MathHelper.func_76142_g(targetRotation - currentRotation);
/* 55 */     if (deltaAngle > maxIncrement) {
/* 56 */       deltaAngle = maxIncrement;
/*    */     }
/* 58 */     if (deltaAngle < -maxIncrement) {
/* 59 */       deltaAngle = -maxIncrement;
/*    */     }
/* 61 */     return currentRotation + deltaAngle / 2.0F;
/*    */   }
/*    */   
/*    */   public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
/* 65 */     float g = 0.006F;
/* 66 */     float sqrt = velocity * velocity * velocity * velocity - g * (g * d3 * d3 + 2.0F * d1 * velocity * velocity);
/* 67 */     return (float)Math.toDegrees(Math.atan(((velocity * velocity) - Math.sqrt(sqrt)) / (g * d3)));
/*    */   }
/*    */   
/*    */   public static float getYawToEntity(Entity e) {
/* 71 */     return (Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F > 180.0F) ? (360.0F - Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F) : (Math.abs(getRotations(e)[0] - mc.field_71439_g.field_70177_z) % 360.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\RotationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */