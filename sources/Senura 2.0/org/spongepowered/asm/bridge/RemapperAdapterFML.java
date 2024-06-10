/*    */ package org.spongepowered.asm.bridge;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.objectweb.asm.commons.Remapper;
/*    */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
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
/*    */ public final class RemapperAdapterFML
/*    */   extends RemapperAdapter
/*    */ {
/*    */   private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
/*    */   private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
/*    */   private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
/*    */   private static final String INSTANCE_FIELD = "INSTANCE";
/*    */   private static final String UNMAP_METHOD = "unmap";
/*    */   private final Method mdUnmap;
/*    */   
/*    */   private RemapperAdapterFML(Remapper remapper, Method mdUnmap) {
/* 46 */     super(remapper);
/* 47 */     this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[] { remapper });
/* 48 */     this.mdUnmap = mdUnmap;
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmap(String typeName) {
/*    */     try {
/* 54 */       return this.mdUnmap.invoke(this.remapper, new Object[] { typeName }).toString();
/* 55 */     } catch (Exception ex) {
/* 56 */       return typeName;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IRemapper create() {
/*    */     try {
/* 65 */       Class<?> clDeobfRemapper = getFMLDeobfuscatingRemapper();
/* 66 */       Field singletonField = clDeobfRemapper.getDeclaredField("INSTANCE");
/* 67 */       Method mdUnmap = clDeobfRemapper.getDeclaredMethod("unmap", new Class[] { String.class });
/* 68 */       Remapper remapper = (Remapper)singletonField.get(null);
/* 69 */       return new RemapperAdapterFML(remapper, mdUnmap);
/* 70 */     } catch (Exception ex) {
/* 71 */       ex.printStackTrace();
/* 72 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
/*    */     try {
/* 82 */       return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
/* 83 */     } catch (ClassNotFoundException ex) {
/* 84 */       return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\bridge\RemapperAdapterFML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */