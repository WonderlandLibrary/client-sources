/*     */ package org.spongepowered.asm.obfuscation.mapping.common;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Strings;
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
/*     */ 
/*     */ public class MappingField
/*     */   implements IMapping<MappingField>
/*     */ {
/*     */   private final String owner;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   
/*     */   public MappingField(String owner, String name) {
/*  44 */     this(owner, name, null);
/*     */   }
/*     */   
/*     */   public MappingField(String owner, String name, String desc) {
/*  48 */     this.owner = owner;
/*  49 */     this.name = name;
/*  50 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMapping.Type getType() {
/*  55 */     return IMapping.Type.FIELD;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  60 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getSimpleName() {
/*  65 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getOwner() {
/*  70 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getDesc() {
/*  75 */     return this.desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField getSuper() {
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField move(String newOwner) {
/*  85 */     return new MappingField(newOwner, getName(), getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField remap(String newName) {
/*  90 */     return new MappingField(getOwner(), newName, getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField transform(String newDesc) {
/*  95 */     return new MappingField(getOwner(), getName(), newDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField copy() {
/* 100 */     return new MappingField(getOwner(), getName(), getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Objects.hashCode(new Object[] { toString() });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (this == obj) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (obj instanceof MappingField) {
/* 114 */       return Objects.equal(toString(), ((MappingField)obj).toString());
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String serialise() {
/* 121 */     return toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return String.format("L%s;%s:%s", new Object[] { getOwner(), getName(), Strings.nullToEmpty(getDesc()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\obfuscation\mapping\common\MappingField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */