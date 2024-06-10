/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.spongepowered.asm.service.ILegacyClassTransformer;
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
/*    */ public final class Proxy
/*    */   implements IClassTransformer, ILegacyClassTransformer
/*    */ {
/* 47 */   private static List<Proxy> proxies = new ArrayList<Proxy>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   private static MixinTransformer transformer = new MixinTransformer();
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isActive = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public Proxy() {
/* 61 */     for (Proxy hook : proxies) {
/* 62 */       hook.isActive = false;
/*    */     }
/*    */     
/* 65 */     proxies.add(this);
/* 66 */     LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[] { Integer.valueOf(proxies.size()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 71 */     if (this.isActive) {
/* 72 */       return transformer.transformClassBytes(name, transformedName, basicClass);
/*    */     }
/*    */     
/* 75 */     return basicClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 80 */     return getClass().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDelegationExcluded() {
/* 85 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 90 */     if (this.isActive) {
/* 91 */       return transformer.transformClassBytes(name, transformedName, basicClass);
/*    */     }
/*    */     
/* 94 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\Proxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */