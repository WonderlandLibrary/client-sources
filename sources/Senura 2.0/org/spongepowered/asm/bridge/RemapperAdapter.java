/*    */ package org.spongepowered.asm.bridge;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.objectweb.asm.commons.Remapper;
/*    */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
/*    */ import org.spongepowered.asm.util.ObfuscationUtil;
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
/*    */ public abstract class RemapperAdapter
/*    */   implements IRemapper, ObfuscationUtil.IClassRemapper
/*    */ {
/* 38 */   protected final Logger logger = LogManager.getLogger("mixin");
/*    */   protected final Remapper remapper;
/*    */   
/*    */   public RemapperAdapter(Remapper remapper) {
/* 42 */     this.remapper = remapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return getClass().getSimpleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapMethodName(String owner, String name, String desc) {
/* 52 */     this.logger.debug("{} is remapping method {}{} for {}", new Object[] { this, name, desc, owner });
/* 53 */     String newName = this.remapper.mapMethodName(owner, name, desc);
/* 54 */     if (!newName.equals(name)) {
/* 55 */       return newName;
/*    */     }
/* 57 */     String obfOwner = unmap(owner);
/* 58 */     String obfDesc = unmapDesc(desc);
/* 59 */     this.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[] { this, name, obfDesc, obfOwner });
/* 60 */     return this.remapper.mapMethodName(obfOwner, name, obfDesc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapFieldName(String owner, String name, String desc) {
/* 65 */     this.logger.debug("{} is remapping field {}{} for {}", new Object[] { this, name, desc, owner });
/* 66 */     String newName = this.remapper.mapFieldName(owner, name, desc);
/* 67 */     if (!newName.equals(name)) {
/* 68 */       return newName;
/*    */     }
/* 70 */     String obfOwner = unmap(owner);
/* 71 */     String obfDesc = unmapDesc(desc);
/* 72 */     this.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[] { this, name, obfDesc, obfOwner });
/* 73 */     return this.remapper.mapFieldName(obfOwner, name, obfDesc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String map(String typeName) {
/* 78 */     this.logger.debug("{} is remapping class {}", new Object[] { this, typeName });
/* 79 */     return this.remapper.map(typeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmap(String typeName) {
/* 84 */     return typeName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapDesc(String desc) {
/* 89 */     return this.remapper.mapDesc(desc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmapDesc(String desc) {
/* 94 */     return ObfuscationUtil.unmapDescriptor(desc, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\bridge\RemapperAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */