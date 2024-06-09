package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.refmap.*;
import com.google.common.base.*;

public final class MemberInfo
{
    public final String owner;
    public final String name;
    public final String desc;
    public final boolean matchAll;
    private final boolean forceField;
    private final String unparsed;
    
    public MemberInfo(final String s, final boolean b) {
        this(s, null, null, b);
    }
    
    public MemberInfo(final String s, final String s2, final boolean b) {
        this(s, s2, null, b);
    }
    
    public MemberInfo(final String s, final String s2, final String s3) {
        this(s, s2, s3, false);
    }
    
    public MemberInfo(final String s, final String s2, final String s3, final boolean b) {
        this(s, s2, s3, b, null);
    }
    
    public MemberInfo(final String name, final String owner, final String desc, final boolean matchAll, final String unparsed) {
        super();
        if (owner != null && owner.contains(".")) {
            throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
        }
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.matchAll = matchAll;
        this.forceField = false;
        this.unparsed = unparsed;
    }
    
    public MemberInfo(final AbstractInsnNode abstractInsnNode) {
        super();
        this.matchAll = false;
        this.forceField = false;
        this.unparsed = null;
        if (abstractInsnNode instanceof MethodInsnNode) {
            final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
            this.owner = methodInsnNode.owner;
            this.name = methodInsnNode.name;
            this.desc = methodInsnNode.desc;
        }
        else {
            if (!(abstractInsnNode instanceof FieldInsnNode)) {
                throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
            }
            final FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
            this.owner = fieldInsnNode.owner;
            this.name = fieldInsnNode.name;
            this.desc = fieldInsnNode.desc;
        }
    }
    
    public MemberInfo(final IMapping<?> mapping) {
        super();
        this.owner = mapping.getOwner();
        this.name = mapping.getSimpleName();
        this.desc = mapping.getDesc();
        this.matchAll = false;
        this.forceField = (mapping.getType() == IMapping.Type.FIELD);
        this.unparsed = null;
    }
    
    private MemberInfo(final MemberInfo memberInfo, final MappingMethod mappingMethod, final boolean b) {
        super();
        this.owner = (b ? mappingMethod.getOwner() : memberInfo.owner);
        this.name = mappingMethod.getSimpleName();
        this.desc = mappingMethod.getDesc();
        this.matchAll = memberInfo.matchAll;
        this.forceField = false;
        this.unparsed = null;
    }
    
    private MemberInfo(final MemberInfo memberInfo, final String owner) {
        super();
        this.owner = owner;
        this.name = memberInfo.name;
        this.desc = memberInfo.desc;
        this.matchAll = memberInfo.matchAll;
        this.forceField = memberInfo.forceField;
        this.unparsed = null;
    }
    
    @Override
    public String toString() {
        final String s = (this.owner != null) ? ("L" + this.owner + ";") : "";
        final String s2 = (this.name != null) ? this.name : "";
        final String s3 = this.matchAll ? "*" : "";
        final String s4 = (this.desc != null) ? this.desc : "";
        return s + s2 + s3 + (s4.startsWith("(") ? "" : ((this.desc != null) ? ":" : "")) + s4;
    }
    
    @Deprecated
    public String toSrg() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference to SRG mapping");
        }
        if (this.desc.startsWith("(")) {
            return this.owner + "/" + this.name + " " + this.desc;
        }
        return this.owner + "/" + this.name;
    }
    
    public String toDescriptor() {
        if (this.desc == null) {
            return "";
        }
        return new SignaturePrinter(this).setFullyQualified(true).toDescriptor();
    }
    
    public String toCtorType() {
        if (this.unparsed == null) {
            return null;
        }
        final String returnType = this.getReturnType();
        if (returnType != null) {
            return returnType;
        }
        if (this.owner != null) {
            return this.owner;
        }
        if (this.name != null && this.desc == null) {
            return this.name;
        }
        return (this.desc != null) ? this.desc : this.unparsed;
    }
    
    public String toCtorDesc() {
        if (this.desc != null && this.desc.startsWith("(") && this.desc.indexOf(41) > -1) {
            return this.desc.substring(0, this.desc.indexOf(41) + 1) + "V";
        }
        return null;
    }
    
    public String getReturnType() {
        if (this.desc == null || this.desc.indexOf(41) == -1 || this.desc.indexOf(40) != 0) {
            return null;
        }
        final String substring = this.desc.substring(this.desc.indexOf(41) + 1);
        if (substring.startsWith("L") && substring.endsWith(";")) {
            return substring.substring(1, substring.length() - 1);
        }
        return substring;
    }
    
    public IMapping<?> asMapping() {
        return (IMapping<?>)(this.isField() ? this.asFieldMapping() : this.asMethodMapping());
    }
    
    public MappingMethod asMethodMapping() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
        }
        if (this.isField()) {
            throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
        }
        return new MappingMethod(this.owner, this.name, this.desc);
    }
    
    public MappingField asFieldMapping() {
        if (!this.isField()) {
            throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
        }
        return new MappingField(this.owner, this.name, this.desc);
    }
    
    public boolean isFullyQualified() {
        return this.owner != null && this.name != null && this.desc != null;
    }
    
    public boolean isField() {
        return this.forceField || (this.desc != null && !this.desc.startsWith("("));
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
            if (this.unparsed != null && this.unparsed.lastIndexOf(46) > 0 && this.owner.startsWith("L")) {
                throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error");
            }
        }
        if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
            throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
        }
        if (this.desc != null) {
            if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
                throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
            }
            if (this.isField()) {
                if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
                    throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
                }
            }
            else {
                try {
                    Type.getArgumentTypes(this.desc);
                }
                catch (Exception ex) {
                    throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
                }
                final String substring = this.desc.substring(this.desc.indexOf(41) + 1);
                try {
                    if (!substring.equals(Type.getType(substring).getDescriptor())) {
                        throw new InvalidMemberDescriptorException("Invalid return type \"" + substring + "\" in descriptor: " + this.desc);
                    }
                }
                catch (Exception ex2) {
                    throw new InvalidMemberDescriptorException("Invalid return type \"" + substring + "\" in descriptor: " + this.desc);
                }
            }
        }
        return this;
    }
    
    public boolean matches(final String s, final String s2, final String s3) {
        return this.matches(s, s2, s3, 0);
    }
    
    public boolean matches(final String s, final String s2, final String s3, final int n) {
        return (this.desc == null || s3 == null || this.desc.equals(s3)) && (this.name == null || s2 == null || this.name.equals(s2)) && (this.owner == null || s == null || this.owner.equals(s)) && (n == 0 || this.matchAll);
    }
    
    public boolean matches(final String s, final String s2) {
        return this.matches(s, s2, 0);
    }
    
    public boolean matches(final String s, final String s2, final int n) {
        return (this.name == null || this.name.equals(s)) && (this.desc == null || (s2 != null && s2.equals(this.desc))) && (n == 0 || this.matchAll);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || o.getClass() != MemberInfo.class) {
            return false;
        }
        final MemberInfo memberInfo = (MemberInfo)o;
        return this.matchAll == memberInfo.matchAll && this.forceField == memberInfo.forceField && Objects.equal(this.owner, memberInfo.owner) && Objects.equal(this.name, memberInfo.name) && Objects.equal(this.desc, memberInfo.desc);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.matchAll, this.owner, this.name, this.desc);
    }
    
    public MemberInfo move(final String s) {
        if ((s == null && this.owner == null) || (s != null && s.equals(this.owner))) {
            return this;
        }
        return new MemberInfo(this, s);
    }
    
    public MemberInfo transform(final String s) {
        if ((s == null && this.desc == null) || (s != null && s.equals(this.desc))) {
            return this;
        }
        return new MemberInfo(this.name, this.owner, s, this.matchAll);
    }
    
    public MemberInfo remapUsing(final MappingMethod mappingMethod, final boolean b) {
        return new MemberInfo(this, mappingMethod, b);
    }
    
    public static MemberInfo parseAndValidate(final String s) throws InvalidMemberDescriptorException {
        return parse(s, null, null).validate();
    }
    
    public static MemberInfo parseAndValidate(final String s, final IMixinContext mixinContext) throws InvalidMemberDescriptorException {
        return parse(s, mixinContext.getReferenceMapper(), mixinContext.getClassRef()).validate();
    }
    
    public static MemberInfo parse(final String s) {
        return parse(s, null, null);
    }
    
    public static MemberInfo parse(final String s, final IMixinContext mixinContext) {
        return parse(s, mixinContext.getReferenceMapper(), mixinContext.getClassRef());
    }
    
    private static MemberInfo parse(final String s, final IReferenceMapper referenceMapper, final String s2) {
        String s3 = null;
        String s4 = null;
        String s5 = Strings.nullToEmpty(s).replaceAll("\\s", "");
        if (referenceMapper != null) {
            s5 = referenceMapper.remap(s2, s5);
        }
        final int lastIndex = s5.lastIndexOf(46);
        final int index = s5.indexOf(59);
        if (lastIndex > -1) {
            s4 = s5.substring(0, lastIndex).replace('.', '/');
            s5 = s5.substring(lastIndex + 1);
        }
        else if (index > -1 && s5.startsWith("L")) {
            s4 = s5.substring(1, index).replace('.', '/');
            s5 = s5.substring(index + 1);
        }
        final int index2 = s5.indexOf(40);
        final int index3 = s5.indexOf(58);
        if (index2 > -1) {
            s3 = s5.substring(index2);
            s5 = s5.substring(0, index2);
        }
        else if (index3 > -1) {
            s3 = s5.substring(index3 + 1);
            s5 = s5.substring(0, index3);
        }
        if ((s5.indexOf(47) > -1 || s5.indexOf(46) > -1) && s4 == null) {
            s4 = s5;
            s5 = "";
        }
        final boolean endsWith = s5.endsWith("*");
        if (endsWith) {
            s5 = s5.substring(0, s5.length() - 1);
        }
        if (s5.isEmpty()) {
            s5 = null;
        }
        return new MemberInfo(s5, s4, s3, endsWith, s);
    }
    
    public static MemberInfo fromMapping(final IMapping<?> mapping) {
        return new MemberInfo(mapping);
    }
}
