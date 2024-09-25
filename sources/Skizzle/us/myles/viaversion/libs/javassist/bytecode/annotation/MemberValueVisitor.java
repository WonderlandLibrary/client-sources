/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.bytecode.annotation;

import us.myles.viaversion.libs.javassist.bytecode.annotation.AnnotationMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.ArrayMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.BooleanMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.ByteMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.CharMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.ClassMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.DoubleMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.EnumMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.FloatMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.IntegerMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.LongMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.ShortMemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.StringMemberValue;

public interface MemberValueVisitor {
    public void visitAnnotationMemberValue(AnnotationMemberValue var1);

    public void visitArrayMemberValue(ArrayMemberValue var1);

    public void visitBooleanMemberValue(BooleanMemberValue var1);

    public void visitByteMemberValue(ByteMemberValue var1);

    public void visitCharMemberValue(CharMemberValue var1);

    public void visitDoubleMemberValue(DoubleMemberValue var1);

    public void visitEnumMemberValue(EnumMemberValue var1);

    public void visitFloatMemberValue(FloatMemberValue var1);

    public void visitIntegerMemberValue(IntegerMemberValue var1);

    public void visitLongMemberValue(LongMemberValue var1);

    public void visitShortMemberValue(ShortMemberValue var1);

    public void visitStringMemberValue(StringMemberValue var1);

    public void visitClassMemberValue(ClassMemberValue var1);
}

