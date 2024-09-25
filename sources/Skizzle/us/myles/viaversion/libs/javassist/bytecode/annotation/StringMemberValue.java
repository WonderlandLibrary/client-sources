/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;
import us.myles.viaversion.libs.javassist.bytecode.annotation.AnnotationsWriter;
import us.myles.viaversion.libs.javassist.bytecode.annotation.MemberValue;
import us.myles.viaversion.libs.javassist.bytecode.annotation.MemberValueVisitor;

public class StringMemberValue
extends MemberValue {
    int valueIndex;

    public StringMemberValue(int index, ConstPool cp) {
        super('s', cp);
        this.valueIndex = index;
    }

    public StringMemberValue(String str, ConstPool cp) {
        super('s', cp);
        this.setValue(str);
    }

    public StringMemberValue(ConstPool cp) {
        super('s', cp);
        this.setValue("");
    }

    @Override
    Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return this.getValue();
    }

    @Override
    Class<?> getType(ClassLoader cl) {
        return String.class;
    }

    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String newValue) {
        this.valueIndex = this.cp.addUtf8Info(newValue);
    }

    public String toString() {
        return "\"" + this.getValue() + "\"";
    }

    @Override
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(this.getValue());
    }

    @Override
    public void accept(MemberValueVisitor visitor) {
        visitor.visitStringMemberValue(this);
    }
}

