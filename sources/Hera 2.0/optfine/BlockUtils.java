/*    */ package optfine;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class BlockUtils
/*    */ {
/*  9 */   private static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/* 10 */   private static ReflectorMethod ForgeBlock_setLightOpacity = new ReflectorMethod(ForgeBlock, "setLightOpacity");
/*    */   private static boolean directAccessValid = true;
/* 12 */   private static Map mapOriginalOpacity = new IdentityHashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public static void setLightOpacity(Block p_setLightOpacity_0_, int p_setLightOpacity_1_) {
/* 16 */     if (!mapOriginalOpacity.containsKey(p_setLightOpacity_0_))
/*    */     {
/* 18 */       mapOriginalOpacity.put(p_setLightOpacity_0_, Integer.valueOf(p_setLightOpacity_0_.getLightOpacity()));
/*    */     }
/*    */     
/* 21 */     if (directAccessValid) {
/*    */       
/*    */       try {
/*    */         
/* 25 */         p_setLightOpacity_0_.setLightOpacity(p_setLightOpacity_1_);
/*    */         
/*    */         return;
/* 28 */       } catch (IllegalAccessError illegalaccesserror) {
/*    */         
/* 30 */         directAccessValid = false;
/*    */         
/* 32 */         if (!ForgeBlock_setLightOpacity.exists())
/*    */         {
/* 34 */           throw illegalaccesserror;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 39 */     Reflector.callVoid(p_setLightOpacity_0_, ForgeBlock_setLightOpacity, new Object[] { Integer.valueOf(p_setLightOpacity_1_) });
/*    */   }
/*    */ 
/*    */   
/*    */   public static void restoreLightOpacity(Block p_restoreLightOpacity_0_) {
/* 44 */     if (mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
/*    */       
/* 46 */       int i = ((Integer)mapOriginalOpacity.get(p_restoreLightOpacity_0_)).intValue();
/* 47 */       setLightOpacity(p_restoreLightOpacity_0_, i);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\BlockUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */