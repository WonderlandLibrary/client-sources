/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Strings;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public final class MemberInfo
/*     */ {
/*     */   public final String owner;
/*     */   public final String name;
/*     */   public final String desc;
/*     */   public final boolean matchAll;
/*     */   private final boolean forceField;
/*     */   private final String unparsed;
/*     */   
/*     */   public MemberInfo(String name, boolean matchAll) {
/* 123 */     this(name, null, null, matchAll);
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
/*     */   public MemberInfo(String name, String owner, boolean matchAll) {
/* 136 */     this(name, owner, null, matchAll);
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
/*     */   public MemberInfo(String name, String owner, String desc) {
/* 148 */     this(name, owner, desc, false);
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
/*     */   public MemberInfo(String name, String owner, String desc, boolean matchAll) {
/* 161 */     this(name, owner, desc, matchAll, null);
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
/*     */   public MemberInfo(String name, String owner, String desc, boolean matchAll, String unparsed) {
/* 174 */     if (owner != null && owner.contains(".")) {
/* 175 */       throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
/*     */     }
/*     */     
/* 178 */     this.owner = owner;
/* 179 */     this.name = name;
/* 180 */     this.desc = desc;
/* 181 */     this.matchAll = matchAll;
/* 182 */     this.forceField = false;
/* 183 */     this.unparsed = unparsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo(AbstractInsnNode insn) {
/* 193 */     this.matchAll = false;
/* 194 */     this.forceField = false;
/* 195 */     this.unparsed = null;
/*     */     
/* 197 */     if (insn instanceof MethodInsnNode) {
/* 198 */       MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 199 */       this.owner = methodNode.owner;
/* 200 */       this.name = methodNode.name;
/* 201 */       this.desc = methodNode.desc;
/* 202 */     } else if (insn instanceof FieldInsnNode) {
/* 203 */       FieldInsnNode fieldNode = (FieldInsnNode)insn;
/* 204 */       this.owner = fieldNode.owner;
/* 205 */       this.name = fieldNode.name;
/* 206 */       this.desc = fieldNode.desc;
/*     */     } else {
/* 208 */       throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo(IMapping<?> mapping) {
/* 218 */     this.owner = mapping.getOwner();
/* 219 */     this.name = mapping.getSimpleName();
/* 220 */     this.desc = mapping.getDesc();
/* 221 */     this.matchAll = false;
/* 222 */     this.forceField = (mapping.getType() == IMapping.Type.FIELD);
/* 223 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemberInfo(MemberInfo remapped, MappingMethod method, boolean setOwner) {
/* 232 */     this.owner = setOwner ? method.getOwner() : remapped.owner;
/* 233 */     this.name = method.getSimpleName();
/* 234 */     this.desc = method.getDesc();
/* 235 */     this.matchAll = remapped.matchAll;
/* 236 */     this.forceField = false;
/* 237 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemberInfo(MemberInfo original, String owner) {
/* 247 */     this.owner = owner;
/* 248 */     this.name = original.name;
/* 249 */     this.desc = original.desc;
/* 250 */     this.matchAll = original.matchAll;
/* 251 */     this.forceField = original.forceField;
/* 252 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 260 */     String owner = (this.owner != null) ? ("L" + this.owner + ";") : "";
/* 261 */     String name = (this.name != null) ? this.name : "";
/* 262 */     String qualifier = this.matchAll ? "*" : "";
/* 263 */     String desc = (this.desc != null) ? this.desc : "";
/* 264 */     String separator = desc.startsWith("(") ? "" : ((this.desc != null) ? ":" : "");
/* 265 */     return owner + name + qualifier + separator + desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String toSrg() {
/* 276 */     if (!isFullyQualified()) {
/* 277 */       throw new MixinException("Cannot convert unqualified reference to SRG mapping");
/*     */     }
/*     */     
/* 280 */     if (this.desc.startsWith("(")) {
/* 281 */       return this.owner + "/" + this.name + " " + this.desc;
/*     */     }
/*     */     
/* 284 */     return this.owner + "/" + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toDescriptor() {
/* 291 */     if (this.desc == null) {
/* 292 */       return "";
/*     */     }
/*     */     
/* 295 */     return (new SignaturePrinter(this)).setFullyQualified(true).toDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCtorType() {
/* 302 */     if (this.unparsed == null) {
/* 303 */       return null;
/*     */     }
/*     */     
/* 306 */     String returnType = getReturnType();
/* 307 */     if (returnType != null) {
/* 308 */       return returnType;
/*     */     }
/*     */     
/* 311 */     if (this.owner != null) {
/* 312 */       return this.owner;
/*     */     }
/*     */     
/* 315 */     if (this.name != null && this.desc == null) {
/* 316 */       return this.name;
/*     */     }
/*     */     
/* 319 */     return (this.desc != null) ? this.desc : this.unparsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCtorDesc() {
/* 327 */     if (this.desc != null && this.desc.startsWith("(") && this.desc.indexOf(')') > -1) {
/* 328 */       return this.desc.substring(0, this.desc.indexOf(')') + 1) + "V";
/*     */     }
/*     */     
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnType() {
/* 340 */     if (this.desc == null || this.desc.indexOf(')') == -1 || this.desc.indexOf('(') != 0) {
/* 341 */       return null;
/*     */     }
/*     */     
/* 344 */     String returnType = this.desc.substring(this.desc.indexOf(')') + 1);
/* 345 */     if (returnType.startsWith("L") && returnType.endsWith(";")) {
/* 346 */       return returnType.substring(1, returnType.length() - 1);
/*     */     }
/* 348 */     return returnType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMapping<?> asMapping() {
/* 356 */     return isField() ? (IMapping<?>)asFieldMapping() : (IMapping<?>)asMethodMapping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod asMethodMapping() {
/* 363 */     if (!isFullyQualified()) {
/* 364 */       throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
/*     */     }
/*     */     
/* 367 */     if (isField()) {
/* 368 */       throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
/*     */     }
/*     */     
/* 371 */     return new MappingMethod(this.owner, this.name, this.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField asFieldMapping() {
/* 378 */     if (!isField()) {
/* 379 */       throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
/*     */     }
/*     */     
/* 382 */     return new MappingField(this.owner, this.name, this.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyQualified() {
/* 391 */     return (this.owner != null && this.name != null && this.desc != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isField() {
/* 401 */     return (this.forceField || (this.desc != null && !this.desc.startsWith("(")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstructor() {
/* 410 */     return "<init>".equals(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassInitialiser() {
/* 419 */     return "<clinit>".equals(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialiser() {
/* 429 */     return (isConstructor() || isClassInitialiser());
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
/*     */   public MemberInfo validate() throws InvalidMemberDescriptorException {
/* 442 */     if (this.owner != null) {
/* 443 */       if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
/* 444 */         throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 449 */       if (this.unparsed != null && this.unparsed.lastIndexOf('.') > 0 && this.owner.startsWith("L")) {
/* 450 */         throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 456 */     if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
/* 457 */       throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
/*     */     }
/*     */     
/* 460 */     if (this.desc != null) {
/* 461 */       if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
/* 462 */         throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
/*     */       }
/* 464 */       if (isField()) {
/* 465 */         if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
/* 466 */           throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
/*     */         }
/*     */       } else {
/*     */         try {
/* 470 */           Type.getArgumentTypes(this.desc);
/* 471 */         } catch (Exception ex) {
/* 472 */           throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
/*     */         } 
/*     */         
/* 475 */         String retString = this.desc.substring(this.desc.indexOf(')') + 1);
/*     */         try {
/* 477 */           Type retType = Type.getType(retString);
/* 478 */           if (!retString.equals(retType.getDescriptor())) {
/* 479 */             throw new InvalidMemberDescriptorException("Invalid return type \"" + retString + "\" in descriptor: " + this.desc);
/*     */           }
/* 481 */         } catch (Exception ex) {
/* 482 */           throw new InvalidMemberDescriptorException("Invalid return type \"" + retString + "\" in descriptor: " + this.desc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 487 */     return this;
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
/*     */   
/*     */   public boolean matches(String owner, String name, String desc) {
/* 501 */     return matches(owner, name, desc, 0);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(String owner, String name, String desc, int ordinal) {
/* 517 */     if (this.desc != null && desc != null && !this.desc.equals(desc)) {
/* 518 */       return false;
/*     */     }
/* 520 */     if (this.name != null && name != null && !this.name.equals(name)) {
/* 521 */       return false;
/*     */     }
/* 523 */     if (this.owner != null && owner != null && !this.owner.equals(owner)) {
/* 524 */       return false;
/*     */     }
/* 526 */     return (ordinal == 0 || this.matchAll);
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
/*     */   public boolean matches(String name, String desc) {
/* 539 */     return matches(name, desc, 0);
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
/*     */ 
/*     */   
/*     */   public boolean matches(String name, String desc, int ordinal) {
/* 554 */     return ((this.name == null || this.name.equals(name)) && (this.desc == null || (desc != null && desc
/* 555 */       .equals(this.desc))) && (ordinal == 0 || this.matchAll));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 564 */     if (obj == null || obj.getClass() != MemberInfo.class) {
/* 565 */       return false;
/*     */     }
/*     */     
/* 568 */     MemberInfo other = (MemberInfo)obj;
/* 569 */     return (this.matchAll == other.matchAll && this.forceField == other.forceField && 
/* 570 */       Objects.equal(this.owner, other.owner) && 
/* 571 */       Objects.equal(this.name, other.name) && 
/* 572 */       Objects.equal(this.desc, other.desc));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 580 */     return Objects.hashCode(new Object[] { Boolean.valueOf(this.matchAll), this.owner, this.name, this.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo move(String newOwner) {
/* 589 */     if ((newOwner == null && this.owner == null) || (newOwner != null && newOwner.equals(this.owner))) {
/* 590 */       return this;
/*     */     }
/* 592 */     return new MemberInfo(this, newOwner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo transform(String newDesc) {
/* 601 */     if ((newDesc == null && this.desc == null) || (newDesc != null && newDesc.equals(this.desc))) {
/* 602 */       return this;
/*     */     }
/* 604 */     return new MemberInfo(this.name, this.owner, newDesc, this.matchAll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo remapUsing(MappingMethod srgMethod, boolean setOwner) {
/* 615 */     return new MemberInfo(this, srgMethod, setOwner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo parseAndValidate(String string) throws InvalidMemberDescriptorException {
/* 625 */     return parse(string, null, null).validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo parseAndValidate(String string, IMixinContext context) throws InvalidMemberDescriptorException {
/* 636 */     return parse(string, context.getReferenceMapper(), context.getClassRef()).validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo parse(String string) {
/* 646 */     return parse(string, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo parse(String string, IMixinContext context) {
/* 657 */     return parse(string, context.getReferenceMapper(), context.getClassRef());
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
/*     */   private static MemberInfo parse(String input, IReferenceMapper refMapper, String mixinClass) {
/* 669 */     String desc = null;
/* 670 */     String owner = null;
/* 671 */     String name = Strings.nullToEmpty(input).replaceAll("\\s", "");
/*     */     
/* 673 */     if (refMapper != null) {
/* 674 */       name = refMapper.remap(mixinClass, name);
/*     */     }
/*     */     
/* 677 */     int lastDotPos = name.lastIndexOf('.');
/* 678 */     int semiColonPos = name.indexOf(';');
/* 679 */     if (lastDotPos > -1) {
/* 680 */       owner = name.substring(0, lastDotPos).replace('.', '/');
/* 681 */       name = name.substring(lastDotPos + 1);
/* 682 */     } else if (semiColonPos > -1 && name.startsWith("L")) {
/* 683 */       owner = name.substring(1, semiColonPos).replace('.', '/');
/* 684 */       name = name.substring(semiColonPos + 1);
/*     */     } 
/*     */     
/* 687 */     int parenPos = name.indexOf('(');
/* 688 */     int colonPos = name.indexOf(':');
/* 689 */     if (parenPos > -1) {
/* 690 */       desc = name.substring(parenPos);
/* 691 */       name = name.substring(0, parenPos);
/* 692 */     } else if (colonPos > -1) {
/* 693 */       desc = name.substring(colonPos + 1);
/* 694 */       name = name.substring(0, colonPos);
/*     */     } 
/*     */     
/* 697 */     if ((name.indexOf('/') > -1 || name.indexOf('.') > -1) && owner == null) {
/* 698 */       owner = name;
/* 699 */       name = "";
/*     */     } 
/*     */     
/* 702 */     boolean matchAll = name.endsWith("*");
/* 703 */     if (matchAll) {
/* 704 */       name = name.substring(0, name.length() - 1);
/*     */     }
/*     */     
/* 707 */     if (name.isEmpty()) {
/* 708 */       name = null;
/*     */     }
/*     */     
/* 711 */     return new MemberInfo(name, owner, desc, matchAll, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo fromMapping(IMapping<?> mapping) {
/* 721 */     return new MemberInfo(mapping);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\MemberInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */