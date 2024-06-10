/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.util.Annotations;
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
/*     */ public final class InterfaceInfo
/*     */ {
/*     */   private final MixinInfo mixin;
/*     */   private final String prefix;
/*     */   private final Type iface;
/*     */   private final boolean unique;
/*     */   private Set<String> methods;
/*     */   
/*     */   private InterfaceInfo(MixinInfo mixin, String prefix, Type iface, boolean unique) {
/*  82 */     if (prefix == null || prefix.length() < 2 || !prefix.endsWith("$")) {
/*  83 */       throw new InvalidMixinException(mixin, String.format("Prefix %s for iface %s is not valid", new Object[] { prefix, iface.toString() }));
/*     */     }
/*     */     
/*  86 */     this.mixin = mixin;
/*  87 */     this.prefix = prefix;
/*  88 */     this.iface = iface;
/*  89 */     this.unique = unique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initMethods() {
/*  96 */     this.methods = new HashSet<String>();
/*  97 */     readInterface(this.iface.getInternalName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readInterface(String ifaceName) {
/* 107 */     ClassInfo interfaceInfo = ClassInfo.forName(ifaceName);
/*     */     
/* 109 */     for (ClassInfo.Method ifaceMethod : interfaceInfo.getMethods()) {
/* 110 */       this.methods.add(ifaceMethod.toString());
/*     */     }
/*     */     
/* 113 */     for (String superIface : interfaceInfo.getInterfaces()) {
/* 114 */       readInterface(superIface);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 124 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getIface() {
/* 133 */     return this.iface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 142 */     return this.iface.getClassName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInternalName() {
/* 151 */     return this.iface.getInternalName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnique() {
/* 160 */     return this.unique;
/*     */   }
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
/*     */   public boolean renameMethod(MethodNode method) {
/* 173 */     if (this.methods == null) {
/* 174 */       initMethods();
/*     */     }
/*     */     
/* 177 */     if (!method.name.startsWith(this.prefix)) {
/* 178 */       if (this.methods.contains(method.name + method.desc)) {
/* 179 */         decorateUniqueMethod(method);
/*     */       }
/* 181 */       return false;
/*     */     } 
/*     */     
/* 184 */     String realName = method.name.substring(this.prefix.length());
/* 185 */     String signature = realName + method.desc;
/*     */     
/* 187 */     if (!this.methods.contains(signature)) {
/* 188 */       throw new InvalidMixinException(this.mixin, String.format("%s does not exist in target interface %s", new Object[] { realName, getName() }));
/*     */     }
/*     */     
/* 191 */     if ((method.access & 0x1) == 0) {
/* 192 */       throw new InvalidMixinException(this.mixin, String.format("%s cannot implement %s because it is not visible", new Object[] { realName, getName() }));
/*     */     }
/*     */     
/* 195 */     Annotations.setVisible(method, MixinRenamed.class, new Object[] { "originalName", method.name, "isInterfaceMember", Boolean.valueOf(true) });
/* 196 */     decorateUniqueMethod(method);
/* 197 */     method.name = realName;
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decorateUniqueMethod(MethodNode method) {
/* 208 */     if (!this.unique) {
/*     */       return;
/*     */     }
/*     */     
/* 212 */     if (Annotations.getVisible(method, Unique.class) == null) {
/* 213 */       Annotations.setVisible(method, Unique.class, new Object[0]);
/* 214 */       this.mixin.getClassInfo().findMethod(method).setUnique(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static InterfaceInfo fromAnnotation(MixinInfo mixin, AnnotationNode node) {
/* 227 */     String prefix = (String)Annotations.getValue(node, "prefix");
/* 228 */     Type iface = (Type)Annotations.getValue(node, "iface");
/* 229 */     Boolean unique = (Boolean)Annotations.getValue(node, "unique");
/*     */     
/* 231 */     if (prefix == null || iface == null) {
/* 232 */       throw new InvalidMixinException(mixin, String.format("@Interface annotation on %s is missing a required parameter", new Object[] { mixin }));
/*     */     }
/*     */     
/* 235 */     return new InterfaceInfo(mixin, prefix, iface, (unique != null && unique.booleanValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 240 */     if (this == o) {
/* 241 */       return true;
/*     */     }
/* 243 */     if (o == null || getClass() != o.getClass()) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     InterfaceInfo that = (InterfaceInfo)o;
/*     */     
/* 249 */     return (this.mixin.equals(that.mixin) && this.prefix.equals(that.prefix) && this.iface.equals(that.iface));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 254 */     int result = this.mixin.hashCode();
/* 255 */     result = 31 * result + this.prefix.hashCode();
/* 256 */     result = 31 * result + this.iface.hashCode();
/* 257 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\InterfaceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */