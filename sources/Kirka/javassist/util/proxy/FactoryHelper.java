/*
 * Decompiled with CFR 0.143.
 */
package javassist.util.proxy;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import javassist.CannotCompileException;
import javassist.bytecode.ClassFile;
import javassist.util.proxy.SecurityActions;

public class FactoryHelper {
    private static Method defineClass1;
    private static Method defineClass2;
    public static final Class[] primitiveTypes;
    public static final String[] wrapperTypes;
    public static final String[] wrapperDesc;
    public static final String[] unwarpMethods;
    public static final String[] unwrapDesc;
    public static final int[] dataSize;

    public static final int typeIndex(Class type) {
        Class[] list = primitiveTypes;
        int n = list.length;
        for (int i = 0; i < n; ++i) {
            if (list[i] != type) continue;
            return i;
        }
        throw new RuntimeException("bad type:" + type.getName());
    }

    public static Class toClass(ClassFile cf, ClassLoader loader) throws CannotCompileException {
        return FactoryHelper.toClass(cf, loader, null);
    }

    public static Class toClass(ClassFile cf, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException {
        try {
            Method method;
            Object[] args;
            byte[] b = FactoryHelper.toBytecode(cf);
            if (domain == null) {
                method = defineClass1;
                args = new Object[]{cf.getName(), b, new Integer(0), new Integer(b.length)};
            } else {
                method = defineClass2;
                args = new Object[]{cf.getName(), b, new Integer(0), new Integer(b.length), domain};
            }
            return FactoryHelper.toClass2(method, loader, args);
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

    private static synchronized Class toClass2(Method method, ClassLoader loader, Object[] args) throws Exception {
        SecurityActions.setAccessible(method, true);
        Class clazz = (Class)method.invoke(loader, args);
        SecurityActions.setAccessible(method, false);
        return clazz;
    }

    private static byte[] toBytecode(ClassFile cf) throws IOException {
        ByteArrayOutputStream barray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(barray);
        try {
            cf.write(out);
        }
        finally {
            out.close();
        }
        return barray.toByteArray();
    }

    public static void writeFile(ClassFile cf, String directoryName) throws CannotCompileException {
        try {
            FactoryHelper.writeFile0(cf, directoryName);
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }

    private static void writeFile0(ClassFile cf, String directoryName) throws CannotCompileException, IOException {
        String dir;
        String classname = cf.getName();
        String filename = directoryName + File.separatorChar + classname.replace('.', File.separatorChar) + ".class";
        int pos = filename.lastIndexOf(File.separatorChar);
        if (pos > 0 && !(dir = filename.substring(0, pos)).equals(".")) {
            new File(dir).mkdirs();
        }
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        try {
            cf.write(out);
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            out.close();
        }
    }

    static {
        try {
            Class<?> cl = Class.forName("java.lang.ClassLoader");
            defineClass1 = SecurityActions.getDeclaredMethod(cl, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE});
            defineClass2 = SecurityActions.getDeclaredMethod(cl, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class});
        }
        catch (Exception e) {
            throw new RuntimeException("cannot initialize");
        }
        primitiveTypes = new Class[]{Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
        wrapperTypes = new String[]{"java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Void"};
        wrapperDesc = new String[]{"(Z)V", "(B)V", "(C)V", "(S)V", "(I)V", "(J)V", "(F)V", "(D)V"};
        unwarpMethods = new String[]{"booleanValue", "byteValue", "charValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue"};
        unwrapDesc = new String[]{"()Z", "()B", "()C", "()S", "()I", "()J", "()F", "()D"};
        dataSize = new int[]{1, 1, 1, 1, 1, 2, 1, 2};
    }
}

