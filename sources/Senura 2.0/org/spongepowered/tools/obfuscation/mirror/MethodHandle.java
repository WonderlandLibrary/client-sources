/*    */ package org.spongepowered.tools.obfuscation.mirror;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import javax.lang.model.element.ExecutableElement;
/*    */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;
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
/*    */ 
/*    */ public class MethodHandle
/*    */   extends MemberHandle<MappingMethod>
/*    */ {
/*    */   private final ExecutableElement element;
/*    */   private final TypeHandle ownerHandle;
/*    */   
/*    */   public MethodHandle(TypeHandle owner, ExecutableElement element) {
/* 50 */     this(owner, element, TypeUtils.getName(element), TypeUtils.getDescriptor(element));
/*    */   }
/*    */   
/*    */   public MethodHandle(TypeHandle owner, String name, String desc) {
/* 54 */     this(owner, (ExecutableElement)null, name, desc);
/*    */   }
/*    */   
/*    */   private MethodHandle(TypeHandle owner, ExecutableElement element, String name, String desc) {
/* 58 */     super((owner != null) ? owner.getName() : null, name, desc);
/* 59 */     this.element = element;
/* 60 */     this.ownerHandle = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isImaginary() {
/* 67 */     return (this.element == null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExecutableElement getElement() {
/* 74 */     return this.element;
/*    */   }
/*    */ 
/*    */   
/*    */   public Visibility getVisibility() {
/* 79 */     return TypeUtils.getVisibility(this.element);
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingMethod asMapping(boolean includeOwner) {
/* 84 */     if (includeOwner) {
/* 85 */       if (this.ownerHandle != null) {
/* 86 */         return (MappingMethod)new ResolvableMappingMethod(this.ownerHandle, getName(), getDesc());
/*    */       }
/* 88 */       return new MappingMethod(getOwner(), getName(), getDesc());
/*    */     } 
/* 90 */     return new MappingMethod(null, getName(), getDesc());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     String owner = (getOwner() != null) ? ("L" + getOwner() + ";") : "";
/* 96 */     String name = Strings.nullToEmpty(getName());
/* 97 */     String desc = Strings.nullToEmpty(getDesc());
/* 98 */     return String.format("%s%s%s", new Object[] { owner, name, desc });
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\MethodHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */