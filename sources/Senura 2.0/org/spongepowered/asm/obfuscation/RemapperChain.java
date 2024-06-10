/*     */ package org.spongepowered.asm.obfuscation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RemapperChain
/*     */   implements IRemapper
/*     */ {
/*  38 */   private final List<IRemapper> remappers = new ArrayList<IRemapper>();
/*     */ 
/*     */   
/*     */   public String toString() {
/*  42 */     return String.format("RemapperChain[%d]", new Object[] { Integer.valueOf(this.remappers.size()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemapperChain add(IRemapper remapper) {
/*  52 */     this.remappers.add(remapper);
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String mapMethodName(String owner, String name, String desc) {
/*  58 */     for (IRemapper remapper : this.remappers) {
/*  59 */       String newName = remapper.mapMethodName(owner, name, desc);
/*  60 */       if (newName != null && !newName.equals(name)) {
/*  61 */         name = newName;
/*     */       }
/*     */     } 
/*  64 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String mapFieldName(String owner, String name, String desc) {
/*  69 */     for (IRemapper remapper : this.remappers) {
/*  70 */       String newName = remapper.mapFieldName(owner, name, desc);
/*  71 */       if (newName != null && !newName.equals(name)) {
/*  72 */         name = newName;
/*     */       }
/*     */     } 
/*  75 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String map(String typeName) {
/*  80 */     for (IRemapper remapper : this.remappers) {
/*  81 */       String newName = remapper.map(typeName);
/*  82 */       if (newName != null && !newName.equals(typeName)) {
/*  83 */         typeName = newName;
/*     */       }
/*     */     } 
/*  86 */     return typeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String unmap(String typeName) {
/*  91 */     for (IRemapper remapper : this.remappers) {
/*  92 */       String newName = remapper.unmap(typeName);
/*  93 */       if (newName != null && !newName.equals(typeName)) {
/*  94 */         typeName = newName;
/*     */       }
/*     */     } 
/*  97 */     return typeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String mapDesc(String desc) {
/* 102 */     for (IRemapper remapper : this.remappers) {
/* 103 */       String newDesc = remapper.mapDesc(desc);
/* 104 */       if (newDesc != null && !newDesc.equals(desc)) {
/* 105 */         desc = newDesc;
/*     */       }
/*     */     } 
/* 108 */     return desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public String unmapDesc(String desc) {
/* 113 */     for (IRemapper remapper : this.remappers) {
/* 114 */       String newDesc = remapper.unmapDesc(desc);
/* 115 */       if (newDesc != null && !newDesc.equals(desc)) {
/* 116 */         desc = newDesc;
/*     */       }
/*     */     } 
/* 119 */     return desc;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\obfuscation\RemapperChain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */