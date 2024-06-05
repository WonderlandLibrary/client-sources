/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.StackMap;
import com.viaversion.viaversion.libs.javassist.bytecode.StackMapTable;
import com.viaversion.viaversion.libs.javassist.convert.Transformer;

public final class TransformNew
extends Transformer {
    private int nested;
    private String classname;
    private String trapClass;
    private String trapMethod;

    public TransformNew(Transformer next, String classname, String trapClass, String trapMethod) {
        super(next);
        this.classname = classname;
        this.trapClass = trapClass;
        this.trapMethod = trapMethod;
    }

    @Override
    public void initialize(ConstPool cp, CodeAttribute attr) {
        this.nested = 0;
    }

    @Override
    public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp) throws CannotCompileException {
        StackMap sm;
        int c = iterator.byteAt(pos);
        if (c != 187) {
            if (c != 183) return pos;
            int index = iterator.u16bitAt(pos + 1);
            int typedesc = cp.isConstructor(this.classname, index);
            if (typedesc == 0) return pos;
            if (this.nested <= 0) return pos;
            int methodref = this.computeMethodref(typedesc, cp);
            iterator.writeByte(184, pos);
            iterator.write16bit(methodref, pos + 1);
            --this.nested;
            return pos;
        }
        int index = iterator.u16bitAt(pos + 1);
        if (!cp.getClassInfo(index).equals(this.classname)) return pos;
        if (iterator.byteAt(pos + 3) != 89) {
            throw new CannotCompileException("NEW followed by no DUP was found");
        }
        iterator.writeByte(0, pos);
        iterator.writeByte(0, pos + 1);
        iterator.writeByte(0, pos + 2);
        iterator.writeByte(0, pos + 3);
        ++this.nested;
        StackMapTable smt = (StackMapTable)iterator.get().getAttribute("StackMapTable");
        if (smt != null) {
            smt.removeNew(pos);
        }
        if ((sm = (StackMap)iterator.get().getAttribute("StackMap")) == null) return pos;
        sm.removeNew(pos);
        return pos;
    }

    private int computeMethodref(int typedesc, ConstPool cp) {
        int classIndex = cp.addClassInfo(this.trapClass);
        int mnameIndex = cp.addUtf8Info(this.trapMethod);
        typedesc = cp.addUtf8Info(Descriptor.changeReturnType(this.classname, cp.getUtf8Info(typedesc)));
        return cp.addMethodrefInfo(classIndex, cp.addNameAndTypeInfo(mnameIndex, typedesc));
    }
}

