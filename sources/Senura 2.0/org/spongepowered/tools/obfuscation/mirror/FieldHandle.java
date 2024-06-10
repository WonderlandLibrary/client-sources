/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldHandle
/*     */   extends MemberHandle<MappingField>
/*     */ {
/*     */   private final VariableElement element;
/*     */   private final boolean rawType;
/*     */   
/*     */   public FieldHandle(TypeElement owner, VariableElement element) {
/*  51 */     this(TypeUtils.getInternalName(owner), element);
/*     */   }
/*     */   
/*     */   public FieldHandle(String owner, VariableElement element) {
/*  55 */     this(owner, element, false);
/*     */   }
/*     */   
/*     */   public FieldHandle(TypeElement owner, VariableElement element, boolean rawType) {
/*  59 */     this(TypeUtils.getInternalName(owner), element, rawType);
/*     */   }
/*     */   
/*     */   public FieldHandle(String owner, VariableElement element, boolean rawType) {
/*  63 */     this(owner, element, rawType, TypeUtils.getName(element), TypeUtils.getInternalName(element));
/*     */   }
/*     */   
/*     */   public FieldHandle(String owner, String name, String desc) {
/*  67 */     this(owner, (VariableElement)null, false, name, desc);
/*     */   }
/*     */   
/*     */   private FieldHandle(String owner, VariableElement element, boolean rawType, String name, String desc) {
/*  71 */     super(owner, name, desc);
/*  72 */     this.element = element;
/*  73 */     this.rawType = rawType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImaginary() {
/*  80 */     return (this.element == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VariableElement getElement() {
/*  87 */     return this.element;
/*     */   }
/*     */ 
/*     */   
/*     */   public Visibility getVisibility() {
/*  92 */     return TypeUtils.getVisibility(this.element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRawType() {
/* 100 */     return this.rawType;
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField asMapping(boolean includeOwner) {
/* 105 */     return new MappingField(includeOwner ? getOwner() : null, getName(), getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     String owner = (getOwner() != null) ? ("L" + getOwner() + ";") : "";
/* 111 */     String name = Strings.nullToEmpty(getName());
/* 112 */     String desc = Strings.nullToEmpty(getDesc());
/* 113 */     return String.format("%s%s:%s", new Object[] { owner, name, desc });
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\FieldHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */