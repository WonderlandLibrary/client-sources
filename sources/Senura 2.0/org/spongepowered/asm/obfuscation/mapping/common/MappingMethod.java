/*     */ package org.spongepowered.asm.obfuscation.mapping.common;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
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
/*     */ public class MappingMethod
/*     */   implements IMapping<MappingMethod>
/*     */ {
/*     */   private final String owner;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   
/*     */   public MappingMethod(String fullyQualifiedName, String desc) {
/*  42 */     this(getOwnerFromName(fullyQualifiedName), getBaseName(fullyQualifiedName), desc);
/*     */   }
/*     */   
/*     */   public MappingMethod(String owner, String simpleName, String desc) {
/*  46 */     this.owner = owner;
/*  47 */     this.name = simpleName;
/*  48 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMapping.Type getType() {
/*  53 */     return IMapping.Type.METHOD;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  58 */     if (this.name == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     return ((this.owner != null) ? (this.owner + "/") : "") + this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSimpleName() {
/*  66 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwner() {
/*  71 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDesc() {
/*  76 */     return this.desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod getSuper() {
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isConstructor() {
/*  85 */     return "<init>".equals(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod move(String newOwner) {
/*  90 */     return new MappingMethod(newOwner, getSimpleName(), getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod remap(String newName) {
/*  95 */     return new MappingMethod(getOwner(), newName, getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod transform(String newDesc) {
/* 100 */     return new MappingMethod(getOwner(), getSimpleName(), newDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod copy() {
/* 105 */     return new MappingMethod(getOwner(), getSimpleName(), getDesc());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod addPrefix(String prefix) {
/* 116 */     String simpleName = getSimpleName();
/* 117 */     if (simpleName == null || simpleName.startsWith(prefix)) {
/* 118 */       return this;
/*     */     }
/* 120 */     return new MappingMethod(getOwner(), prefix + simpleName, getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Objects.hashCode(new Object[] { getName(), getDesc() });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 130 */     if (this == obj) {
/* 131 */       return true;
/*     */     }
/* 133 */     if (obj instanceof MappingMethod) {
/* 134 */       return (Objects.equal(this.name, ((MappingMethod)obj).name) && Objects.equal(this.desc, ((MappingMethod)obj).desc));
/*     */     }
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String serialise() {
/* 141 */     return toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     String desc = getDesc();
/* 147 */     return String.format("%s%s%s", new Object[] { getName(), (desc != null) ? " " : "", (desc != null) ? desc : "" });
/*     */   }
/*     */   
/*     */   private static String getBaseName(String name) {
/* 151 */     if (name == null) {
/* 152 */       return null;
/*     */     }
/* 154 */     int pos = name.lastIndexOf('/');
/* 155 */     return (pos > -1) ? name.substring(pos + 1) : name;
/*     */   }
/*     */   
/*     */   private static String getOwnerFromName(String name) {
/* 159 */     if (name == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     int pos = name.lastIndexOf('/');
/* 163 */     return (pos > -1) ? name.substring(0, pos) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\obfuscation\mapping\common\MappingMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */