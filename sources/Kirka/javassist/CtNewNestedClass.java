/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewClass;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ClassFile;
import javassist.bytecode.InnerClassesAttribute;

class CtNewNestedClass
extends CtNewClass {
    CtNewNestedClass(String realName, ClassPool cp, boolean isInterface, CtClass superclass) {
        super(realName, cp, isInterface, superclass);
    }

    @Override
    public void setModifiers(int mod) {
        super.setModifiers(mod &= -9);
        CtNewNestedClass.updateInnerEntry(mod, this.getName(), this, true);
    }

    private static void updateInnerEntry(int mod, String name, CtClass clazz, boolean outer) {
        ClassFile cf = clazz.getClassFile2();
        InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
        if (ica == null) {
            return;
        }
        int n = ica.tableLength();
        for (int i = 0; i < n; ++i) {
            if (!name.equals(ica.innerClass(i))) continue;
            int acc = ica.accessFlags(i) & 8;
            ica.setAccessFlags(i, mod | acc);
            String outName = ica.outerClass(i);
            if (outName == null || !outer) break;
            try {
                CtClass parent = clazz.getClassPool().get(outName);
                CtNewNestedClass.updateInnerEntry(mod, name, parent, false);
                break;
            }
            catch (NotFoundException e) {
                throw new RuntimeException("cannot find the declaring class: " + outName);
            }
        }
    }
}

