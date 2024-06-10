/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ 
/*  6:   */ public class EntityUtils
/*  7:   */ {
/*  8: 8 */   private static ReflectorClass ForgeEntityLivingBase = new ReflectorClass(EntityLivingBase.class);
/*  9: 9 */   private static ReflectorField ForgeEntityLivingBase_entityAge = new ReflectorField(ForgeEntityLivingBase, "entityAge");
/* 10:10 */   private static boolean directEntityAge = true;
/* 11:11 */   private static ReflectorClass ForgeEntityLiving = new ReflectorClass(EntityLiving.class);
/* 12:12 */   private static ReflectorMethod ForgeEntityLiving_despawnEntity = new ReflectorMethod(ForgeEntityLiving, "despawnEntity");
/* 13:13 */   private static boolean directDespawnEntity = true;
/* 14:   */   
/* 15:   */   public static int getEntityAge(EntityLivingBase elb)
/* 16:   */   {
/* 17:17 */     if (directEntityAge) {
/* 18:   */       try
/* 19:   */       {
/* 20:21 */         return elb.entityAge;
/* 21:   */       }
/* 22:   */       catch (IllegalAccessError var2)
/* 23:   */       {
/* 24:25 */         directEntityAge = false;
/* 25:27 */         if (!ForgeEntityLivingBase_entityAge.exists()) {
/* 26:29 */           throw var2;
/* 27:   */         }
/* 28:   */       }
/* 29:   */     }
/* 30:34 */     return ((Integer)Reflector.getFieldValue(elb, ForgeEntityLivingBase_entityAge)).intValue();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void setEntityAge(EntityLivingBase elb, int age)
/* 34:   */   {
/* 35:39 */     if (directEntityAge) {
/* 36:   */       try
/* 37:   */       {
/* 38:43 */         elb.entityAge = age;
/* 39:44 */         return;
/* 40:   */       }
/* 41:   */       catch (IllegalAccessError var3)
/* 42:   */       {
/* 43:48 */         directEntityAge = false;
/* 44:50 */         if (!ForgeEntityLivingBase_entityAge.exists()) {
/* 45:52 */           throw var3;
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:57 */     Reflector.setFieldValue(elb, ForgeEntityLivingBase_entityAge, Integer.valueOf(age));
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static void despawnEntity(EntityLiving el)
/* 53:   */   {
/* 54:62 */     if (directDespawnEntity) {
/* 55:   */       try
/* 56:   */       {
/* 57:66 */         el.despawnEntity();
/* 58:67 */         return;
/* 59:   */       }
/* 60:   */       catch (IllegalAccessError var2)
/* 61:   */       {
/* 62:71 */         directDespawnEntity = false;
/* 63:73 */         if (!ForgeEntityLiving_despawnEntity.exists()) {
/* 64:75 */           throw var2;
/* 65:   */         }
/* 66:   */       }
/* 67:   */     }
/* 68:80 */     Reflector.callVoid(el, ForgeEntityLiving_despawnEntity, new Object[0]);
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.EntityUtils
 * JD-Core Version:    0.7.0.1
 */