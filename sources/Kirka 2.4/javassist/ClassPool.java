/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javassist.CannotCompileException;
import javassist.ClassPath;
import javassist.ClassPoolTail;
import javassist.CtArray;
import javassist.CtClass;
import javassist.CtClassType;
import javassist.CtMethod;
import javassist.CtNewClass;
import javassist.CtNewNestedClass;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;

public class ClassPool {
    private static Method defineClass1;
    private static Method defineClass2;
    private static Method definePackage;
    public boolean childFirstLookup = false;
    public static boolean doPruning;
    private int compressCount;
    private static final int COMPRESS_THRESHOLD = 100;
    public static boolean releaseUnmodifiedClassFile;
    protected ClassPoolTail source = new ClassPoolTail();
    protected ClassPool parent;
    protected Hashtable classes = new Hashtable(191);
    private Hashtable cflow = null;
    private static final int INIT_HASH_SIZE = 191;
    private ArrayList importedPackages;
    private static ClassPool defaultPool;

    public ClassPool() {
        this(null);
    }

    public ClassPool(boolean useDefaultPath) {
        this(null);
        if (useDefaultPath) {
            this.appendSystemPath();
        }
    }

    public ClassPool(ClassPool parent) {
        this.parent = parent;
        if (parent == null) {
            CtClass[] pt = CtClass.primitiveTypes;
            for (int i = 0; i < pt.length; ++i) {
                this.classes.put(pt[i].getName(), pt[i]);
            }
        }
        this.cflow = null;
        this.compressCount = 0;
        this.clearImportedPackages();
    }

    public static synchronized ClassPool getDefault() {
        if (defaultPool == null) {
            defaultPool = new ClassPool(null);
            defaultPool.appendSystemPath();
        }
        return defaultPool;
    }

    protected CtClass getCached(String classname) {
        return (CtClass)this.classes.get(classname);
    }

    protected void cacheCtClass(String classname, CtClass c, boolean dynamic) {
        this.classes.put(classname, c);
    }

    protected CtClass removeCached(String classname) {
        return (CtClass)this.classes.remove(classname);
    }

    public String toString() {
        return this.source.toString();
    }

    void compress() {
        if (this.compressCount++ > 100) {
            this.compressCount = 0;
            Enumeration e = this.classes.elements();
            while (e.hasMoreElements()) {
                ((CtClass)e.nextElement()).compress();
            }
        }
    }

    public void importPackage(String packageName) {
        this.importedPackages.add(packageName);
    }

    public void clearImportedPackages() {
        this.importedPackages = new ArrayList();
        this.importedPackages.add("java.lang");
    }

    public Iterator getImportedPackages() {
        return this.importedPackages.iterator();
    }

    public void recordInvalidClassName(String name) {
    }

    void recordCflow(String name, String cname, String fname) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        this.cflow.put(name, new Object[]{cname, fname});
    }

    public Object[] lookupCflow(String name) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        return (Object[])this.cflow.get(name);
    }

    public CtClass getAndRename(String orgName, String newName) throws NotFoundException {
        CtClass clazz = this.get0(orgName, false);
        if (clazz == null) {
            throw new NotFoundException(orgName);
        }
        if (clazz instanceof CtClassType) {
            ((CtClassType)clazz).setClassPool(this);
        }
        clazz.setName(newName);
        return clazz;
    }

    synchronized void classNameChanged(String oldname, CtClass clazz) {
        CtClass c = this.getCached(oldname);
        if (c == clazz) {
            this.removeCached(oldname);
        }
        String newName = clazz.getName();
        this.checkNotFrozen(newName);
        this.cacheCtClass(newName, clazz, false);
    }

    public CtClass get(String classname) throws NotFoundException {
        CtClass clazz = classname == null ? null : this.get0(classname, true);
        if (clazz == null) {
            throw new NotFoundException(classname);
        }
        clazz.incGetCounter();
        return clazz;
    }

    public CtClass getOrNull(String classname) {
        CtClass clazz = null;
        if (classname == null) {
            clazz = null;
        } else {
            try {
                clazz = this.get0(classname, true);
            }
            catch (NotFoundException e) {
                // empty catch block
            }
        }
        if (clazz != null) {
            clazz.incGetCounter();
        }
        return clazz;
    }

    public CtClass getCtClass(String classname) throws NotFoundException {
        if (classname.charAt(0) == '[') {
            return Descriptor.toCtClass(classname, this);
        }
        return this.get(classname);
    }

    protected synchronized CtClass get0(String classname, boolean useCache) throws NotFoundException {
        CtClass clazz = null;
        if (useCache && (clazz = this.getCached(classname)) != null) {
            return clazz;
        }
        if (!this.childFirstLookup && this.parent != null && (clazz = this.parent.get0(classname, useCache)) != null) {
            return clazz;
        }
        clazz = this.createCtClass(classname, useCache);
        if (clazz != null) {
            if (useCache) {
                this.cacheCtClass(clazz.getName(), clazz, false);
            }
            return clazz;
        }
        if (this.childFirstLookup && this.parent != null) {
            clazz = this.parent.get0(classname, useCache);
        }
        return clazz;
    }

    protected CtClass createCtClass(String classname, boolean useCache) {
        if (classname.charAt(0) == '[') {
            classname = Descriptor.toClassName(classname);
        }
        if (classname.endsWith("[]")) {
            String base = classname.substring(0, classname.indexOf(91));
            if (!(useCache && this.getCached(base) != null || this.find(base) != null)) {
                return null;
            }
            return new CtArray(classname, this);
        }
        if (this.find(classname) == null) {
            return null;
        }
        return new CtClassType(classname, this);
    }

    public URL find(String classname) {
        return this.source.find(classname);
    }

    void checkNotFrozen(String classname) throws RuntimeException {
        CtClass clazz = this.getCached(classname);
        if (clazz == null) {
            if (!this.childFirstLookup && this.parent != null) {
                try {
                    clazz = this.parent.get0(classname, true);
                }
                catch (NotFoundException e) {
                    // empty catch block
                }
                if (clazz != null) {
                    throw new RuntimeException(classname + " is in a parent ClassPool.  Use the parent.");
                }
            }
        } else if (clazz.isFrozen()) {
            throw new RuntimeException(classname + ": frozen class (cannot edit)");
        }
    }

    CtClass checkNotExists(String classname) {
        CtClass clazz = this.getCached(classname);
        if (clazz == null && !this.childFirstLookup && this.parent != null) {
            try {
                clazz = this.parent.get0(classname, true);
            }
            catch (NotFoundException e) {
                // empty catch block
            }
        }
        return clazz;
    }

    InputStream openClassfile(String classname) throws NotFoundException {
        return this.source.openClassfile(classname);
    }

    void writeClassfile(String classname, OutputStream out) throws NotFoundException, IOException, CannotCompileException {
        this.source.writeClassfile(classname, out);
    }

    public CtClass[] get(String[] classnames) throws NotFoundException {
        if (classnames == null) {
            return new CtClass[0];
        }
        int num = classnames.length;
        CtClass[] result = new CtClass[num];
        for (int i = 0; i < num; ++i) {
            result[i] = this.get(classnames[i]);
        }
        return result;
    }

    public CtMethod getMethod(String classname, String methodname) throws NotFoundException {
        CtClass c = this.get(classname);
        return c.getDeclaredMethod(methodname);
    }

    public CtClass makeClass(InputStream classfile) throws IOException, RuntimeException {
        return this.makeClass(classfile, true);
    }

    public CtClass makeClass(InputStream classfile, boolean ifNotFrozen) throws IOException, RuntimeException {
        this.compress();
        classfile = new BufferedInputStream(classfile);
        CtClassType clazz = new CtClassType(classfile, this);
        ((CtClass)clazz).checkModify();
        String classname = clazz.getName();
        if (ifNotFrozen) {
            this.checkNotFrozen(classname);
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }

    public CtClass makeClass(ClassFile classfile) throws RuntimeException {
        return this.makeClass(classfile, true);
    }

    public CtClass makeClass(ClassFile classfile, boolean ifNotFrozen) throws RuntimeException {
        this.compress();
        CtClassType clazz = new CtClassType(classfile, this);
        ((CtClass)clazz).checkModify();
        String classname = clazz.getName();
        if (ifNotFrozen) {
            this.checkNotFrozen(classname);
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }

    public CtClass makeClassIfNew(InputStream classfile) throws IOException, RuntimeException {
        this.compress();
        classfile = new BufferedInputStream(classfile);
        CtClassType clazz = new CtClassType(classfile, this);
        ((CtClass)clazz).checkModify();
        String classname = clazz.getName();
        CtClass found = this.checkNotExists(classname);
        if (found != null) {
            return found;
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }

    public CtClass makeClass(String classname) throws RuntimeException {
        return this.makeClass(classname, null);
    }

    public synchronized CtClass makeClass(String classname, CtClass superclass) throws RuntimeException {
        this.checkNotFrozen(classname);
        CtNewClass clazz = new CtNewClass(classname, this, false, superclass);
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }

    synchronized CtClass makeNestedClass(String classname) {
        this.checkNotFrozen(classname);
        CtNewNestedClass clazz = new CtNewNestedClass(classname, this, false, null);
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }

    public CtClass makeInterface(String name) throws RuntimeException {
        return this.makeInterface(name, null);
    }

    public synchronized CtClass makeInterface(String name, CtClass superclass) throws RuntimeException {
        this.checkNotFrozen(name);
        CtNewClass clazz = new CtNewClass(name, this, true, superclass);
        this.cacheCtClass(name, clazz, true);
        return clazz;
    }

    public CtClass makeAnnotation(String name) throws RuntimeException {
        try {
            CtClass cc = this.makeInterface(name, this.get("java.lang.annotation.Annotation"));
            cc.setModifiers(cc.getModifiers() | 8192);
            return cc;
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ClassPath appendSystemPath() {
        return this.source.appendSystemPath();
    }

    public ClassPath insertClassPath(ClassPath cp) {
        return this.source.insertClassPath(cp);
    }

    public ClassPath appendClassPath(ClassPath cp) {
        return this.source.appendClassPath(cp);
    }

    public ClassPath insertClassPath(String pathname) throws NotFoundException {
        return this.source.insertClassPath(pathname);
    }

    public ClassPath appendClassPath(String pathname) throws NotFoundException {
        return this.source.appendClassPath(pathname);
    }

    public void removeClassPath(ClassPath cp) {
        this.source.removeClassPath(cp);
    }

    public void appendPathList(String pathlist) throws NotFoundException {
        char sep = File.pathSeparatorChar;
        int i = 0;
        do {
            int j;
            if ((j = pathlist.indexOf(sep, i)) < 0) break;
            this.appendClassPath(pathlist.substring(i, j));
            i = j + 1;
        } while (true);
        this.appendClassPath(pathlist.substring(i));
    }

    public Class toClass(CtClass clazz) throws CannotCompileException {
        return this.toClass(clazz, this.getClassLoader());
    }

    public ClassLoader getClassLoader() {
        return ClassPool.getContextClassLoader();
    }

    static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public Class toClass(CtClass ct, ClassLoader loader) throws CannotCompileException {
        return this.toClass(ct, loader, null);
    }

    public Class toClass(CtClass ct, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException {
        try {
            Object[] args;
            Method method;
            byte[] b = ct.toBytecode();
            if (domain == null) {
                method = defineClass1;
                args = new Object[]{ct.getName(), b, new Integer(0), new Integer(b.length)};
            } else {
                method = defineClass2;
                args = new Object[]{ct.getName(), b, new Integer(0), new Integer(b.length), domain};
            }
            return (Class)ClassPool.toClass2(method, loader, args);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (InvocationTargetException e) {
            throw new CannotCompileException(e.getTargetException());
        }
        catch (Exception e) {
            throw new CannotCompileException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static synchronized Object toClass2(Method method, ClassLoader loader, Object[] args) throws Exception {
        method.setAccessible(true);
        try {
            Object object = method.invoke(loader, args);
            return object;
        }
        finally {
            method.setAccessible(false);
        }
    }

    public void makePackage(ClassLoader loader, String name) throws CannotCompileException {
        Throwable t;
        Object[] args = new Object[]{name, null, null, null, null, null, null, null};
        try {
            ClassPool.toClass2(definePackage, loader, args);
            return;
        }
        catch (InvocationTargetException e) {
            t = e.getTargetException();
            if (t == null) {
                t = e;
            } else if (t instanceof IllegalArgumentException) {
                return;
            }
        }
        catch (Exception e) {
            t = e;
        }
        throw new CannotCompileException(t);
    }

    static {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction(){

                public Object run() throws Exception {
                    Class<?> cl = Class.forName("java.lang.ClassLoader");
                    defineClass1 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                    defineClass2 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    definePackage = cl.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class);
                    return null;
                }
            });
        }
        catch (PrivilegedActionException pae) {
            throw new RuntimeException("cannot initialize ClassPool", pae.getException());
        }
        doPruning = false;
        releaseUnmodifiedClassFile = true;
        defaultPool = null;
    }

}

