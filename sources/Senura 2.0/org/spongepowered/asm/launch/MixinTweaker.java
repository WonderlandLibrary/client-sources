/*    */ package org.spongepowered.asm.launch;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.ITweaker;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
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
/*    */ public class MixinTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   public MixinTweaker() {
/* 44 */     MixinBootstrap.start();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 53 */     MixinBootstrap.doInit(args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
/* 62 */     MixinBootstrap.inject();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 70 */     return MixinBootstrap.getPlatform().getLaunchTarget();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 78 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\MixinTweaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */