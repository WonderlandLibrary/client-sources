/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.net.URI;
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
/*    */ public class MixinPlatformAgentDefault
/*    */   extends MixinPlatformAgentAbstract
/*    */ {
/*    */   public MixinPlatformAgentDefault(MixinPlatformManager manager, URI uri) {
/* 42 */     super(manager, uri);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void prepare() {
/* 48 */     String compatibilityLevel = this.attributes.get("MixinCompatibilityLevel");
/* 49 */     if (compatibilityLevel != null) {
/* 50 */       this.manager.setCompatibilityLevel(compatibilityLevel);
/*    */     }
/*    */     
/* 53 */     String mixinConfigs = this.attributes.get("MixinConfigs");
/* 54 */     if (mixinConfigs != null) {
/* 55 */       for (String config : mixinConfigs.split(",")) {
/* 56 */         this.manager.addConfig(config.trim());
/*    */       }
/*    */     }
/*    */     
/* 60 */     String tokenProviders = this.attributes.get("MixinTokenProviders");
/* 61 */     if (tokenProviders != null) {
/* 62 */       for (String provider : tokenProviders.split(",")) {
/* 63 */         this.manager.addTokenProvider(provider.trim());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPrimaryContainer() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void inject() {}
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 78 */     return this.attributes.get("Main-Class");
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */