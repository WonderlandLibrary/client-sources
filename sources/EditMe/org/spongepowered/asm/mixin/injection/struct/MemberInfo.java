package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public final class MemberInfo {
   public final String owner;
   public final String name;
   public final String desc;
   public final boolean matchAll;
   private final boolean forceField;
   private final String unparsed;

   public MemberInfo(String var1, boolean var2) {
      this(var1, (String)null, (String)null, var2);
   }

   public MemberInfo(String var1, String var2, boolean var3) {
      this(var1, var2, (String)null, var3);
   }

   public MemberInfo(String var1, String var2, String var3) {
      this(var1, var2, var3, false);
   }

   public MemberInfo(String var1, String var2, String var3, boolean var4) {
      this(var1, var2, var3, var4, (String)null);
   }

   public MemberInfo(String var1, String var2, String var3, boolean var4, String var5) {
      if (var2 != null && var2.contains(".")) {
         throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
      } else {
         this.owner = var2;
         this.name = var1;
         this.desc = var3;
         this.matchAll = var4;
         this.forceField = false;
         this.unparsed = var5;
      }
   }

   public MemberInfo(AbstractInsnNode var1) {
      this.matchAll = false;
      this.forceField = false;
      this.unparsed = null;
      if (var1 instanceof MethodInsnNode) {
         MethodInsnNode var2 = (MethodInsnNode)var1;
         this.owner = var2.owner;
         this.name = var2.name;
         this.desc = var2.desc;
      } else {
         if (!(var1 instanceof FieldInsnNode)) {
            throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
         }

         FieldInsnNode var3 = (FieldInsnNode)var1;
         this.owner = var3.owner;
         this.name = var3.name;
         this.desc = var3.desc;
      }

   }

   public MemberInfo(IMapping var1) {
      this.owner = var1.getOwner();
      this.name = var1.getSimpleName();
      this.desc = var1.getDesc();
      this.matchAll = false;
      this.forceField = var1.getType() == IMapping.Type.FIELD;
      this.unparsed = null;
   }

   private MemberInfo(MemberInfo var1, MappingMethod var2, boolean var3) {
      this.owner = var3 ? var2.getOwner() : var1.owner;
      this.name = var2.getSimpleName();
      this.desc = var2.getDesc();
      this.matchAll = var1.matchAll;
      this.forceField = false;
      this.unparsed = null;
   }

   private MemberInfo(MemberInfo var1, String var2) {
      this.owner = var2;
      this.name = var1.name;
      this.desc = var1.desc;
      this.matchAll = var1.matchAll;
      this.forceField = var1.forceField;
      this.unparsed = null;
   }

   public String toString() {
      String var1 = this.owner != null ? "L" + this.owner + ";" : "";
      String var2 = this.name != null ? this.name : "";
      String var3 = this.matchAll ? "*" : "";
      String var4 = this.desc != null ? this.desc : "";
      String var5 = var4.startsWith("(") ? "" : (this.desc != null ? ":" : "");
      return var1 + var2 + var3 + var5 + var4;
   }

   /** @deprecated */
   @Deprecated
   public String toSrg() {
      if (!this.isFullyQualified()) {
         throw new MixinException("Cannot convert unqualified reference to SRG mapping");
      } else {
         return this.desc.startsWith("(") ? this.owner + "/" + this.name + " " + this.desc : this.owner + "/" + this.name;
      }
   }

   public String toDescriptor() {
      return this.desc == null ? "" : (new SignaturePrinter(this)).setFullyQualified(true).toDescriptor();
   }

   public String toCtorType() {
      if (this.unparsed == null) {
         return null;
      } else {
         String var1 = this.getReturnType();
         if (var1 != null) {
            return var1;
         } else if (this.owner != null) {
            return this.owner;
         } else if (this.name != null && this.desc == null) {
            return this.name;
         } else {
            return this.desc != null ? this.desc : this.unparsed;
         }
      }
   }

   public String toCtorDesc() {
      return this.desc != null && this.desc.startsWith("(") && this.desc.indexOf(41) > -1 ? this.desc.substring(0, this.desc.indexOf(41) + 1) + "V" : null;
   }

   public String getReturnType() {
      if (this.desc != null && this.desc.indexOf(41) != -1 && this.desc.indexOf(40) == 0) {
         String var1 = this.desc.substring(this.desc.indexOf(41) + 1);
         return var1.startsWith("L") && var1.endsWith(";") ? var1.substring(1, var1.length() - 1) : var1;
      } else {
         return null;
      }
   }

   public IMapping asMapping() {
      return (IMapping)(this.isField() ? this.asFieldMapping() : this.asMethodMapping());
   }

   public MappingMethod asMethodMapping() {
      if (!this.isFullyQualified()) {
         throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
      } else if (this.isField()) {
         throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
      } else {
         return new MappingMethod(this.owner, this.name, this.desc);
      }
   }

   public MappingField asFieldMapping() {
      if (!this.isField()) {
         throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
      } else {
         return new MappingField(this.owner, this.name, this.desc);
      }
   }

   public boolean isFullyQualified() {
      return this.owner != null && this.name != null && this.desc != null;
   }

   public boolean isField() {
      return this.forceField || this.desc != null && !this.desc.startsWith("(");
   }

   public boolean isConstructor() {
      return "<init>".equals(this.name);
   }

   public boolean isClassInitialiser() {
      return "<clinit>".equals(this.name);
   }

   public boolean isInitialiser() {
      return this.isConstructor() || this.isClassInitialiser();
   }

   public MemberInfo validate() throws InvalidMemberDescriptorException {
      if (this.owner != null) {
         if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
            throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner);
         }

         try {
            if (!this.owner.equals(Type.getType(this.owner).getDescriptor())) {
               throw new InvalidMemberDescriptorException("Invalid owner type specified: " + this.owner);
            }
         } catch (Exception var5) {
            throw new InvalidMemberDescriptorException("Invalid owner type specified: " + this.owner);
         }
      }

      if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
         throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
      } else {
         if (this.desc != null) {
            if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
               throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
            }

            if (this.isField()) {
               if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
                  throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
               }
            } else {
               try {
                  Type.getArgumentTypes(this.desc);
               } catch (Exception var4) {
                  throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
               }

               String var1 = this.desc.substring(this.desc.indexOf(41) + 1);

               try {
                  Type var2 = Type.getType(var1);
                  if (!var1.equals(var2.getDescriptor())) {
                     throw new InvalidMemberDescriptorException("Invalid return type \"" + var1 + "\" in descriptor: " + this.desc);
                  }
               } catch (Exception var3) {
                  throw new InvalidMemberDescriptorException("Invalid return type \"" + var1 + "\" in descriptor: " + this.desc);
               }
            }
         }

         return this;
      }
   }

   public boolean matches(String var1, String var2, String var3) {
      return this.matches(var1, var2, var3, 0);
   }

   public boolean matches(String var1, String var2, String var3, int var4) {
      if (this.desc != null && var3 != null && !this.desc.equals(var3)) {
         return false;
      } else if (this.name != null && var2 != null && !this.name.equals(var2)) {
         return false;
      } else if (this.owner != null && var1 != null && !this.owner.equals(var1)) {
         return false;
      } else {
         return var4 == 0 || this.matchAll;
      }
   }

   public boolean matches(String var1, String var2) {
      return this.matches(var1, var2, 0);
   }

   public boolean matches(String var1, String var2, int var3) {
      return (this.name == null || this.name.equals(var1)) && (this.desc == null || var2 != null && var2.equals(this.desc)) && (var3 == 0 || this.matchAll);
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == MemberInfo.class) {
         MemberInfo var2 = (MemberInfo)var1;
         return this.matchAll == var2.matchAll && this.forceField == var2.forceField && Objects.equal(this.owner, var2.owner) && Objects.equal(this.name, var2.name) && Objects.equal(this.desc, var2.desc);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.matchAll, this.owner, this.name, this.desc});
   }

   public MemberInfo move(String var1) {
      return (var1 != null || this.owner != null) && (var1 == null || !var1.equals(this.owner)) ? new MemberInfo(this, var1) : this;
   }

   public MemberInfo transform(String var1) {
      return (var1 != null || this.desc != null) && (var1 == null || !var1.equals(this.desc)) ? new MemberInfo(this.name, this.owner, var1, this.matchAll) : this;
   }

   public MemberInfo remapUsing(MappingMethod var1, boolean var2) {
      return new MemberInfo(this, var1, var2);
   }

   public static MemberInfo parseAndValidate(String var0) throws InvalidMemberDescriptorException {
      return parse(var0, (IReferenceMapper)null, (String)null).validate();
   }

   public static MemberInfo parseAndValidate(String var0, IMixinContext var1) throws InvalidMemberDescriptorException {
      return parse(var0, var1.getReferenceMapper(), var1.getClassRef()).validate();
   }

   public static MemberInfo parse(String var0) {
      return parse(var0, (IReferenceMapper)null, (String)null);
   }

   public static MemberInfo parse(String var0, IMixinContext var1) {
      return parse(var0, var1.getReferenceMapper(), var1.getClassRef());
   }

   private static MemberInfo parse(String var0, IReferenceMapper var1, String var2) {
      String var3 = null;
      String var4 = null;
      String var5 = Strings.nullToEmpty(var0).replaceAll("\\s", "");
      if (var1 != null) {
         var5 = var1.remap(var2, var5);
      }

      int var6 = var5.lastIndexOf(46);
      int var7 = var5.indexOf(59);
      if (var6 > -1) {
         var4 = var5.substring(0, var6).replace('.', '/');
         var5 = var5.substring(var6 + 1);
      } else if (var7 > -1 && var5.startsWith("L")) {
         var4 = var5.substring(1, var7).replace('.', '/');
         var5 = var5.substring(var7 + 1);
      }

      int var8 = var5.indexOf(40);
      int var9 = var5.indexOf(58);
      if (var8 > -1) {
         var3 = var5.substring(var8);
         var5 = var5.substring(0, var8);
      } else if (var9 > -1) {
         var3 = var5.substring(var9 + 1);
         var5 = var5.substring(0, var9);
      }

      if ((var5.indexOf(47) > -1 || var5.indexOf(46) > -1) && var4 == null) {
         var4 = var5;
         var5 = "";
      }

      boolean var10 = var5.endsWith("*");
      if (var10) {
         var5 = var5.substring(0, var5.length() - 1);
      }

      if (var5.isEmpty()) {
         var5 = null;
      }

      return new MemberInfo(var5, var4, var3, var10, var0);
   }

   public static MemberInfo fromMapping(IMapping var0) {
      return new MemberInfo(var0);
   }
}
