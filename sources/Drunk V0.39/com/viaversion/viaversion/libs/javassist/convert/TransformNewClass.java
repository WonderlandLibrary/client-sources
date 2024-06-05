/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.convert.Transformer;

public final class TransformNewClass
extends Transformer {
    private int nested;
    private String classname;
    private String newClassName;
    private int newClassIndex;
    private int newMethodNTIndex;
    private int newMethodIndex;

    public TransformNewClass(Transformer next, String classname, String newClassName) {
        super(next);
        this.classname = classname;
        this.newClassName = newClassName;
    }

    @Override
    public void initialize(ConstPool cp, CodeAttribute attr) {
        this.nested = 0;
        this.newMethodIndex = 0;
        this.newMethodNTIndex = 0;
        this.newClassIndex = 0;
    }

    @Override
    public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp) throws CannotCompileException {
        int c = iterator.byteAt(pos);
        if (c == 187) {
            int index = iterator.u16bitAt(pos + 1);
            if (!cp.getClassInfo(index).equals(this.classname)) return pos;
            if (iterator.byteAt(pos + 3) != 89) {
                throw new CannotCompileException("NEW followed by no DUP was found");
            }
            if (this.newClassIndex == 0) {
                this.newClassIndex = cp.addClassInfo(this.newClassName);
            }
            iterator.write16bit(this.newClassIndex, pos + 1);
            ++this.nested;
            return pos;
        }
        if (c != 183) return pos;
        int index = iterator.u16bitAt(pos + 1);
        int typedesc = cp.isConstructor(this.classname, index);
        if (typedesc == 0) return pos;
        if (this.nested <= 0) return pos;
        int nt = cp.getMethodrefNameAndType(index);
        if (this.newMethodNTIndex != nt) {
            this.newMethodNTIndex = nt;
            this.newMethodIndex = cp.addMethodrefInfo(this.newClassIndex, nt);
        }
        iterator.write16bit(this.newMethodIndex, pos + 1);
        --this.nested;
        return pos;
    }
}

