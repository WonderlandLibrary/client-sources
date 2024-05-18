/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import java.util.List;
import javassist.CannotCompileException;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import org.reflections.Configuration;
import org.reflections.ReflectionsException;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;
import org.reflections.util.ClasspathHelper;

public class MemberUsageScanner
extends AbstractScanner {
    private ClassPool classPool;

    public void scan(Object cls) {
        try {
            CtClass ctClass = this.getClassPool().get(this.getMetadataAdapter().getClassName(cls));
            for (CtConstructor member : ctClass.getDeclaredConstructors()) {
                this.scanMember(member);
            }
            for (CtBehavior member : ctClass.getDeclaredMethods()) {
                this.scanMember(member);
            }
            ctClass.detach();
        }
        catch (Exception e) {
            throw new ReflectionsException("Could not scan method usage for " + this.getMetadataAdapter().getClassName(cls), e);
        }
    }

    void scanMember(CtBehavior member) throws CannotCompileException {
        final String key = member.getDeclaringClass().getName() + "." + member.getMethodInfo().getName() + "(" + this.parameterNames(member.getMethodInfo()) + ")";
        member.instrument(new ExprEditor(){

            public void edit(NewExpr e) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(e.getConstructor().getDeclaringClass().getName() + "." + "<init>" + "(" + MemberUsageScanner.this.parameterNames(e.getConstructor().getMethodInfo()) + ")", e.getLineNumber(), key);
                }
                catch (NotFoundException e1) {
                    throw new ReflectionsException("Could not find new instance usage in " + key, e1);
                }
            }

            public void edit(MethodCall m) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(m.getMethod().getDeclaringClass().getName() + "." + m.getMethodName() + "(" + MemberUsageScanner.this.parameterNames(m.getMethod().getMethodInfo()) + ")", m.getLineNumber(), key);
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + m.getClassName() + " in " + key, e);
                }
            }

            public void edit(ConstructorCall c) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(c.getConstructor().getDeclaringClass().getName() + "." + "<init>" + "(" + MemberUsageScanner.this.parameterNames(c.getConstructor().getMethodInfo()) + ")", c.getLineNumber(), key);
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + c.getClassName() + " in " + key, e);
                }
            }

            public void edit(FieldAccess f) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(f.getField().getDeclaringClass().getName() + "." + f.getFieldName(), f.getLineNumber(), key);
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + f.getFieldName() + " in " + key, e);
                }
            }
        });
    }

    private void put(String key, int lineNumber, String value) {
        if (this.acceptResult(key)) {
            this.getStore().put((Object)key, (Object)(value + " #" + lineNumber));
        }
    }

    String parameterNames(MethodInfo info) {
        return Joiner.on((String)", ").join(this.getMetadataAdapter().getParameterNames(info));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassPool getClassPool() {
        if (this.classPool == null) {
            MemberUsageScanner memberUsageScanner = this;
            synchronized (memberUsageScanner) {
                this.classPool = new ClassPool();
                ClassLoader[] classLoaders = this.getConfiguration().getClassLoaders();
                if (classLoaders == null) {
                    classLoaders = ClasspathHelper.classLoaders(new ClassLoader[0]);
                }
                for (ClassLoader classLoader : classLoaders) {
                    this.classPool.appendClassPath(new LoaderClassPath(classLoader));
                }
            }
        }
        return this.classPool;
    }

}

