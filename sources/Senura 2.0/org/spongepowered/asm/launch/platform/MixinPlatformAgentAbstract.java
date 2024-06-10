/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URI;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ public abstract class MixinPlatformAgentAbstract
/*    */   implements IMixinPlatformAgent
/*    */ {
/* 41 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MixinPlatformManager manager;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final URI uri;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final File container;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MainAttributes attributes;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MixinPlatformAgentAbstract(MixinPlatformManager manager, URI uri) {
/* 67 */     this.manager = manager;
/* 68 */     this.uri = uri;
/* 69 */     this.container = (this.uri != null) ? new File(this.uri) : null;
/* 70 */     this.attributes = MainAttributes.of(uri);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return String.format("PlatformAgent[%s:%s]", new Object[] { getClass().getSimpleName(), this.uri });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPhaseProvider() {
/* 80 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */