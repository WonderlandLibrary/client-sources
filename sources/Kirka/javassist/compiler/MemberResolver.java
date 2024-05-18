/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.compiler.CompileError;
import javassist.compiler.NoFieldException;
import javassist.compiler.TokenId;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Symbol;

public class MemberResolver
implements TokenId {
    private ClassPool classPool;
    private static final int YES = 0;
    private static final int NO = -1;
    private static final String INVALID = "<invalid>";
    private static WeakHashMap invalidNamesMap = new WeakHashMap();
    private Hashtable invalidNames = null;

    public MemberResolver(ClassPool cp) {
        this.classPool = cp;
    }

    public ClassPool getClassPool() {
        return this.classPool;
    }

    private static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }

    public Method lookupMethod(CtClass clazz, CtClass currentClass, MethodInfo current, String methodName, int[] argTypes, int[] argDims, String[] argClassNames) throws CompileError {
        int res;
        Method m;
        Method maybe = null;
        if (current != null && clazz == currentClass && current.getName().equals(methodName) && (res = this.compareSignature(current.getDescriptor(), argTypes, argDims, argClassNames)) != -1) {
            Method r = new Method(clazz, current, res);
            if (res == 0) {
                return r;
            }
            maybe = r;
        }
        if ((m = this.lookupMethod(clazz, methodName, argTypes, argDims, argClassNames, maybe != null)) != null) {
            return m;
        }
        return maybe;
    }

    private Method lookupMethod(CtClass clazz, String methodName, int[] argTypes, int[] argDims, String[] argClassNames, boolean onlyExact) throws CompileError {
        Method r;
        Method maybe = null;
        ClassFile cf = clazz.getClassFile2();
        if (cf != null) {
            List list = cf.getMethods();
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                int res;
                MethodInfo minfo = (MethodInfo)list.get(i);
                if (!minfo.getName().equals(methodName) || (res = this.compareSignature(minfo.getDescriptor(), argTypes, argDims, argClassNames)) == -1) continue;
                r = new Method(clazz, minfo, res);
                if (res == 0) {
                    return r;
                }
                if (maybe != null && maybe.notmatch <= res) continue;
                maybe = r;
            }
        }
        if (onlyExact) {
            maybe = null;
        } else {
            onlyExact = maybe != null;
        }
        int mod = clazz.getModifiers();
        boolean isIntf = Modifier.isInterface(mod);
        try {
            CtClass pclazz;
            Method r2;
            if (!isIntf && (pclazz = clazz.getSuperclass()) != null && (r2 = this.lookupMethod(pclazz, methodName, argTypes, argDims, argClassNames, onlyExact)) != null) {
                return r2;
            }
        }
        catch (NotFoundException e) {
            // empty catch block
        }
        try {
            CtClass pclazz;
            CtClass[] ifs = clazz.getInterfaces();
            int size = ifs.length;
            for (int i = 0; i < size; ++i) {
                r = this.lookupMethod(ifs[i], methodName, argTypes, argDims, argClassNames, onlyExact);
                if (r == null) continue;
                return r;
            }
            if (isIntf && (pclazz = clazz.getSuperclass()) != null && (r = this.lookupMethod(pclazz, methodName, argTypes, argDims, argClassNames, onlyExact)) != null) {
                return r;
            }
        }
        catch (NotFoundException e) {
            // empty catch block
        }
        return maybe;
    }

    private int compareSignature(String desc, int[] argTypes, int[] argDims, String[] argClassNames) throws CompileError {
        int result = 0;
        int i = 1;
        int nArgs = argTypes.length;
        if (nArgs != Descriptor.numOfParameters(desc)) {
            return -1;
        }
        int len = desc.length();
        int n = 0;
        while (i < len) {
            char c;
            if ((c = desc.charAt(i++)) == ')') {
                return n == nArgs ? result : -1;
            }
            if (n >= nArgs) {
                return -1;
            }
            int dim = 0;
            while (c == '[') {
                ++dim;
                c = desc.charAt(i++);
            }
            if (argTypes[n] == 412) {
                if (dim == 0 && c != 'L') {
                    return -1;
                }
                if (c == 'L') {
                    i = desc.indexOf(59, i) + 1;
                }
            } else if (argDims[n] != dim) {
                if (dim != 0 || c != 'L' || !desc.startsWith("java/lang/Object;", i)) {
                    return -1;
                }
                i = desc.indexOf(59, i) + 1;
                ++result;
                if (i <= 0) {
                    return -1;
                }
            } else if (c == 'L') {
                int j;
                block23 : {
                    j = desc.indexOf(59, i);
                    if (j < 0 || argTypes[n] != 307) {
                        return -1;
                    }
                    String cname = desc.substring(i, j);
                    if (!cname.equals(argClassNames[n])) {
                        CtClass clazz = this.lookupClassByJvmName(argClassNames[n]);
                        try {
                            if (clazz.subtypeOf(this.lookupClassByJvmName(cname))) {
                                ++result;
                                break block23;
                            }
                            return -1;
                        }
                        catch (NotFoundException e) {
                            ++result;
                        }
                    }
                }
                i = j + 1;
            } else {
                int at;
                int t = MemberResolver.descToType(c);
                if (t != (at = argTypes[n])) {
                    if (t == 324 && (at == 334 || at == 303 || at == 306)) {
                        ++result;
                    } else {
                        return -1;
                    }
                }
            }
            ++n;
        }
        return -1;
    }

    public CtField lookupFieldByJvmName2(String jvmClassName, Symbol fieldSym, ASTree expr) throws NoFieldException {
        String field = fieldSym.get();
        CtClass cc = null;
        try {
            cc = this.lookupClass(MemberResolver.jvmToJavaName(jvmClassName), true);
        }
        catch (CompileError e) {
            throw new NoFieldException(jvmClassName + "/" + field, expr);
        }
        try {
            return cc.getField(field);
        }
        catch (NotFoundException e) {
            jvmClassName = MemberResolver.javaToJvmName(cc.getName());
            throw new NoFieldException(jvmClassName + "$" + field, expr);
        }
    }

    public CtField lookupFieldByJvmName(String jvmClassName, Symbol fieldName) throws CompileError {
        return this.lookupField(MemberResolver.jvmToJavaName(jvmClassName), fieldName);
    }

    public CtField lookupField(String className, Symbol fieldName) throws CompileError {
        CtClass cc = this.lookupClass(className, false);
        try {
            return cc.getField(fieldName.get());
        }
        catch (NotFoundException e) {
            throw new CompileError("no such field: " + fieldName.get());
        }
    }

    public CtClass lookupClassByName(ASTList name) throws CompileError {
        return this.lookupClass(Declarator.astToClassName(name, '.'), false);
    }

    public CtClass lookupClassByJvmName(String jvmName) throws CompileError {
        return this.lookupClass(MemberResolver.jvmToJavaName(jvmName), false);
    }

    public CtClass lookupClass(Declarator decl) throws CompileError {
        return this.lookupClass(decl.getType(), decl.getArrayDim(), decl.getClassName());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CtClass lookupClass(int type, int dim, String classname) throws CompileError {
        String cname = "";
        if (type == 307) {
            CtClass clazz = this.lookupClassByJvmName(classname);
            if (dim <= 0) return clazz;
            cname = clazz.getName();
        } else {
            cname = MemberResolver.getTypeName(type);
        }
        while (dim-- > 0) {
            cname = cname + "[]";
        }
        return this.lookupClass(cname, false);
    }

    static String getTypeName(int type) throws CompileError {
        String cname = "";
        switch (type) {
            case 301: {
                cname = "boolean";
                break;
            }
            case 306: {
                cname = "char";
                break;
            }
            case 303: {
                cname = "byte";
                break;
            }
            case 334: {
                cname = "short";
                break;
            }
            case 324: {
                cname = "int";
                break;
            }
            case 326: {
                cname = "long";
                break;
            }
            case 317: {
                cname = "float";
                break;
            }
            case 312: {
                cname = "double";
                break;
            }
            case 344: {
                cname = "void";
                break;
            }
            default: {
                MemberResolver.fatal();
            }
        }
        return cname;
    }

    public CtClass lookupClass(String name, boolean notCheckInner) throws CompileError {
        Hashtable cache = this.getInvalidNames();
        Object found = cache.get(name);
        if (found == INVALID) {
            throw new CompileError("no such class: " + name);
        }
        if (found != null) {
            try {
                return this.classPool.get((String)found);
            }
            catch (NotFoundException e) {
                // empty catch block
            }
        }
        CtClass cc = null;
        try {
            cc = this.lookupClass0(name, notCheckInner);
        }
        catch (NotFoundException e) {
            cc = this.searchImports(name);
        }
        cache.put(name, cc.getName());
        return cc;
    }

    public static int getInvalidMapSize() {
        return invalidNamesMap.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Hashtable getInvalidNames() {
        Hashtable ht = this.invalidNames;
        if (ht != null) return ht;
        Class<MemberResolver> class_ = MemberResolver.class;
        synchronized (MemberResolver.class) {
            WeakReference ref = (WeakReference)invalidNamesMap.get(this.classPool);
            if (ref != null) {
                ht = (Hashtable)ref.get();
            }
            if (ht == null) {
                ht = new Hashtable();
                invalidNamesMap.put(this.classPool, new WeakReference(ht));
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            this.invalidNames = ht;
            return ht;
        }
    }

    private CtClass searchImports(String orgName) throws CompileError {
        if (orgName.indexOf(46) < 0) {
            Iterator it = this.classPool.getImportedPackages();
            while (it.hasNext()) {
                String pac = (String)it.next();
                String fqName = pac + '.' + orgName;
                try {
                    return this.classPool.get(fqName);
                }
                catch (NotFoundException e) {
                    try {
                        if (!pac.endsWith("." + orgName)) continue;
                        return this.classPool.get(pac);
                    }
                    catch (NotFoundException e2) {
                    }
                }
            }
        }
        this.getInvalidNames().put(orgName, INVALID);
        throw new CompileError("no such class: " + orgName);
    }

    private CtClass lookupClass0(String classname, boolean notCheckInner) throws NotFoundException {
        CtClass cc = null;
        do {
            try {
                cc = this.classPool.get(classname);
            }
            catch (NotFoundException e) {
                int i = classname.lastIndexOf(46);
                if (notCheckInner || i < 0) {
                    throw e;
                }
                StringBuffer sbuf = new StringBuffer(classname);
                sbuf.setCharAt(i, '$');
                classname = sbuf.toString();
            }
        } while (cc == null);
        return cc;
    }

    public String resolveClassName(ASTList name) throws CompileError {
        if (name == null) {
            return null;
        }
        return MemberResolver.javaToJvmName(this.lookupClassByName(name).getName());
    }

    public String resolveJvmClassName(String jvmName) throws CompileError {
        if (jvmName == null) {
            return null;
        }
        return MemberResolver.javaToJvmName(this.lookupClassByJvmName(jvmName).getName());
    }

    public static CtClass getSuperclass(CtClass c) throws CompileError {
        try {
            CtClass sc = c.getSuperclass();
            if (sc != null) {
                return sc;
            }
        }
        catch (NotFoundException e) {
            // empty catch block
        }
        throw new CompileError("cannot find the super class of " + c.getName());
    }

    public static CtClass getSuperInterface(CtClass c, String interfaceName) throws CompileError {
        try {
            CtClass[] intfs = c.getInterfaces();
            for (int i = 0; i < intfs.length; ++i) {
                if (!intfs[i].getName().equals(interfaceName)) continue;
                return intfs[i];
            }
        }
        catch (NotFoundException e) {
            // empty catch block
        }
        throw new CompileError("cannot find the super inetrface " + interfaceName + " of " + c.getName());
    }

    public static String javaToJvmName(String classname) {
        return classname.replace('.', '/');
    }

    public static String jvmToJavaName(String classname) {
        return classname.replace('/', '.');
    }

    public static int descToType(char c) throws CompileError {
        switch (c) {
            case 'Z': {
                return 301;
            }
            case 'C': {
                return 306;
            }
            case 'B': {
                return 303;
            }
            case 'S': {
                return 334;
            }
            case 'I': {
                return 324;
            }
            case 'J': {
                return 326;
            }
            case 'F': {
                return 317;
            }
            case 'D': {
                return 312;
            }
            case 'V': {
                return 344;
            }
            case 'L': 
            case '[': {
                return 307;
            }
        }
        MemberResolver.fatal();
        return 344;
    }

    public static int getModifiers(ASTList mods) {
        int m = 0;
        while (mods != null) {
            Keyword k = (Keyword)mods.head();
            mods = mods.tail();
            switch (k.get()) {
                case 335: {
                    m |= 8;
                    break;
                }
                case 315: {
                    m |= 16;
                    break;
                }
                case 338: {
                    m |= 32;
                    break;
                }
                case 300: {
                    m |= 1024;
                    break;
                }
                case 332: {
                    m |= 1;
                    break;
                }
                case 331: {
                    m |= 4;
                    break;
                }
                case 330: {
                    m |= 2;
                    break;
                }
                case 345: {
                    m |= 64;
                    break;
                }
                case 342: {
                    m |= 128;
                    break;
                }
                case 347: {
                    m |= 2048;
                }
            }
        }
        return m;
    }

    public static class Method {
        public CtClass declaring;
        public MethodInfo info;
        public int notmatch;

        public Method(CtClass c, MethodInfo i, int n) {
            this.declaring = c;
            this.info = i;
            this.notmatch = n;
        }

        public boolean isStatic() {
            int acc = this.info.getAccessFlags();
            return (acc & 8) != 0;
        }
    }

}

