/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassMap;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Javac;

public final class CtConstructor
extends CtBehavior {
    protected CtConstructor(MethodInfo minfo, CtClass declaring) {
        super(declaring, minfo);
    }

    public CtConstructor(CtClass[] parameters, CtClass declaring) {
        this((MethodInfo)null, declaring);
        ConstPool cp = declaring.getClassFile2().getConstPool();
        String desc = Descriptor.ofConstructor(parameters);
        this.methodInfo = new MethodInfo(cp, "<init>", desc);
        this.setModifiers(1);
    }

    public CtConstructor(CtConstructor src, CtClass declaring, ClassMap map) throws CannotCompileException {
        this((MethodInfo)null, declaring);
        this.copy(src, true, map);
    }

    public boolean isConstructor() {
        return this.methodInfo.isConstructor();
    }

    public boolean isClassInitializer() {
        return this.methodInfo.isStaticInitializer();
    }

    @Override
    public String getLongName() {
        String string;
        StringBuilder stringBuilder = new StringBuilder().append(this.getDeclaringClass().getName());
        if (this.isConstructor()) {
            string = Descriptor.toString(this.getSignature());
            return stringBuilder.append(string).toString();
        }
        string = ".<clinit>()";
        return stringBuilder.append(string).toString();
    }

    @Override
    public String getName() {
        if (!this.methodInfo.isStaticInitializer()) return this.declaringClass.getSimpleName();
        return "<clinit>";
    }

    @Override
    public boolean isEmpty() {
        CodeAttribute ca = this.getMethodInfo2().getCodeAttribute();
        if (ca == null) {
            return false;
        }
        ConstPool cp = ca.getConstPool();
        CodeIterator it = ca.iterator();
        try {
            int op0 = it.byteAt(it.next());
            if (op0 == 177) return true;
            if (op0 != 42) return false;
            int pos = it.next();
            if (it.byteAt(pos) != 183) return false;
            int desc = cp.isConstructor(this.getSuperclassName(), it.u16bitAt(pos + 1));
            if (desc == 0) return false;
            if (!"()V".equals(cp.getUtf8Info(desc))) return false;
            if (it.byteAt(it.next()) != 177) return false;
            if (it.hasNext()) return false;
            return true;
        }
        catch (BadBytecode badBytecode) {
            return false;
        }
    }

    private String getSuperclassName() {
        ClassFile cf = this.declaringClass.getClassFile2();
        return cf.getSuperclass();
    }

    public boolean callsSuper() throws CannotCompileException {
        CodeAttribute codeAttr = this.methodInfo.getCodeAttribute();
        if (codeAttr == null) return false;
        CodeIterator it = codeAttr.iterator();
        try {
            int index = it.skipSuperConstructor();
            if (index < 0) return false;
            return true;
        }
        catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    @Override
    public void setBody(String src) throws CannotCompileException {
        if (src == null) {
            src = this.isClassInitializer() ? ";" : "super();";
        }
        super.setBody(src);
    }

    public void setBody(CtConstructor src, ClassMap map) throws CannotCompileException {
        CtConstructor.setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
    }

    public void insertBeforeBody(String src) throws CannotCompileException {
        CtClass cc = this.declaringClass;
        cc.checkModify();
        if (this.isClassInitializer()) {
            throw new CannotCompileException("class initializer");
        }
        CodeAttribute ca = this.methodInfo.getCodeAttribute();
        CodeIterator iterator = ca.iterator();
        Bytecode b = new Bytecode(this.methodInfo.getConstPool(), ca.getMaxStack(), ca.getMaxLocals());
        b.setStackDepth(ca.getMaxStack());
        Javac jv = new Javac(b, cc);
        try {
            jv.recordParams(this.getParameterTypes(), false);
            jv.compileStmnt(src);
            ca.setMaxStack(b.getMaxStack());
            ca.setMaxLocals(b.getMaxLocals());
            iterator.skipConstructor();
            int pos = iterator.insertEx(b.get());
            iterator.insert(b.getExceptionTable(), pos);
            this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
            return;
        }
        catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
        catch (CompileError e) {
            throw new CannotCompileException(e);
        }
        catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    @Override
    int getStartPosOfBody(CodeAttribute ca) throws CannotCompileException {
        CodeIterator ci = ca.iterator();
        try {
            ci.skipConstructor();
            return ci.next();
        }
        catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    public CtMethod toMethod(String name, CtClass declaring) throws CannotCompileException {
        return this.toMethod(name, declaring, null);
    }

    public CtMethod toMethod(String name, CtClass declaring, ClassMap map) throws CannotCompileException {
        MethodInfo minfo;
        CodeAttribute ca;
        CtMethod method = new CtMethod(null, declaring);
        method.copy(this, false, map);
        if (this.isConstructor() && (ca = (minfo = method.getMethodInfo2()).getCodeAttribute()) != null) {
            CtConstructor.removeConsCall(ca);
            try {
                this.methodInfo.rebuildStackMapIf6(declaring.getClassPool(), declaring.getClassFile2());
            }
            catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        method.setName(name);
        return method;
    }

    private static void removeConsCall(CodeAttribute ca) throws CannotCompileException {
        CodeIterator iterator = ca.iterator();
        try {
            int pos = iterator.skipConstructor();
            if (pos < 0) return;
            int mref = iterator.u16bitAt(pos + 1);
            String desc = ca.getConstPool().getMethodrefType(mref);
            int num = Descriptor.numOfParameters(desc) + 1;
            if (num > 3) {
                pos = iterator.insertGapAt((int)pos, (int)(num - 3), (boolean)false).position;
            }
            iterator.writeByte(87, pos++);
            iterator.writeByte(0, pos);
            iterator.writeByte(0, pos + 1);
            Descriptor.Iterator it = new Descriptor.Iterator(desc);
            while (true) {
                it.next();
                if (!it.isParameter()) return;
                iterator.writeByte(it.is2byte() ? 88 : 87, pos++);
            }
        }
        catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }
}

