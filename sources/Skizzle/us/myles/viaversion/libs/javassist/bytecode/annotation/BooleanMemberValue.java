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

public class BooleanMemberValue
extends MemberValue {
    int valueIndex;

    public BooleanMemberValue(int index, ConstPool cp) {
        super('Z', cp);
        this.valueIndex = index;
    }

    public BooleanMemberValue(boolean b, ConstPool cp) {
        super('Z', cp);
        this.setValue(b);
    }

    public BooleanMemberValue(ConstPool cp) {
        super('Z', cp);
        this.setValue(false);
    }

    @Override
    Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return this.getValue();
    }

    @Override
    Class<?> getType(ClassLoader cl) {
        return Boolean.TYPE;
    }

    public boolean getValue() {
        return this.cp.getIntegerInfo(this.valueIndex) != 0;
    }

    public void setValue(boolean newValue) {
        this.valueIndex = this.cp.addIntegerInfo(newValue ? 1 : 0);
    }

    public String toString() {
        return this.getValue() ? "true" : "false";
    }

    @Override
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(this.getValue());
    }

    @Override
    public void accept(MemberValueVisitor visitor) {
        visitor.visitBooleanMemberValue(this);
    }
}

