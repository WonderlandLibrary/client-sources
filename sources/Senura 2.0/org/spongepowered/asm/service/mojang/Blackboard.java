/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import org.spongepowered.asm.service.IGlobalPropertyService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Blackboard
/*    */   implements IGlobalPropertyService
/*    */ {
/*    */   public final <T> T getProperty(String key) {
/* 46 */     return (T)Launch.blackboard.get(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void setProperty(String key, Object value) {
/* 57 */     Launch.blackboard.put(key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final <T> T getProperty(String key, T defaultValue) {
/* 72 */     Object value = Launch.blackboard.get(key);
/* 73 */     return (value != null) ? (T)value : defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getPropertyString(String key, String defaultValue) {
/* 87 */     Object value = Launch.blackboard.get(key);
/* 88 */     return (value != null) ? value.toString() : defaultValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\service\mojang\Blackboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */