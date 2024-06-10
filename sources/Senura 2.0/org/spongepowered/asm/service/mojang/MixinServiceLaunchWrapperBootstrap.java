/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import org.spongepowered.asm.service.IMixinServiceBootstrap;
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
/*    */ public class MixinServiceLaunchWrapperBootstrap
/*    */   implements IMixinServiceBootstrap
/*    */ {
/*    */   private static final String SERVICE_PACKAGE = "org.spongepowered.asm.service.";
/*    */   private static final String MIXIN_UTIL_PACKAGE = "org.spongepowered.asm.util.";
/*    */   private static final String ASM_PACKAGE = "org.spongepowered.asm.lib.";
/*    */   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
/*    */   
/*    */   public String getName() {
/* 44 */     return "LaunchWrapper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServiceClassName() {
/* 49 */     return "org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void bootstrap() {
/* 55 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.service.");
/*    */ 
/*    */     
/* 58 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.lib.");
/* 59 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.mixin.");
/* 60 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.util.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\service\mojang\MixinServiceLaunchWrapperBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */