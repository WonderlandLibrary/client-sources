/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;

public class SerialVersionUID {
    public static void setSerialVersionUID(CtClass clazz) throws CannotCompileException, NotFoundException {
        try {
            clazz.getDeclaredField("serialVersionUID");
            return;
        }
        catch (NotFoundException e) {
            if (!SerialVersionUID.isSerializable(clazz)) {
                return;
            }
            CtField field = new CtField(CtClass.longType, "serialVersionUID", clazz);
            field.setModifiers(26);
            clazz.addField(field, SerialVersionUID.calculateDefault(clazz) + "L");
            return;
        }
    }

    private static boolean isSerializable(CtClass clazz) throws NotFoundException {
        ClassPool pool = clazz.getClassPool();
        return clazz.subtypeOf(pool.get("java.io.Serializable"));
    }

    public static long calculateDefault(CtClass clazz) throws CannotCompileException {
        try {
            int i;
            int i2;
            int mods;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bout);
            ClassFile classFile = clazz.getClassFile();
            String javaName = SerialVersionUID.javaName(clazz);
            out.writeUTF(javaName);
            CtMethod[] methods = clazz.getDeclaredMethods();
            int classMods = clazz.getModifiers();
            if ((classMods & 512) != 0) {
                classMods = methods.length > 0 ? (classMods |= 1024) : (classMods &= -1025);
            }
            out.writeInt(classMods);
            Object[] interfaces = classFile.getInterfaces();
            for (i2 = 0; i2 < interfaces.length; ++i2) {
                interfaces[i2] = SerialVersionUID.javaName((String)interfaces[i2]);
            }
            Arrays.sort(interfaces);
            for (i2 = 0; i2 < interfaces.length; ++i2) {
                out.writeUTF((String)interfaces[i2]);
            }
            CtField[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, new Comparator(){

                public int compare(Object o1, Object o2) {
                    CtField field1 = (CtField)o1;
                    CtField field2 = (CtField)o2;
                    return field1.getName().compareTo(field2.getName());
                }
            });
            for (int i3 = 0; i3 < fields.length; ++i3) {
                CtField field = fields[i3];
                int mods2 = field.getModifiers();
                if ((mods2 & 2) != 0 && (mods2 & 136) != 0) continue;
                out.writeUTF(field.getName());
                out.writeInt(mods2);
                out.writeUTF(field.getFieldInfo2().getDescriptor());
            }
            if (classFile.getStaticInitializer() != null) {
                out.writeUTF("<clinit>");
                out.writeInt(8);
                out.writeUTF("()V");
            }
            CtConstructor[] constructors = clazz.getDeclaredConstructors();
            Arrays.sort(constructors, new Comparator(){

                public int compare(Object o1, Object o2) {
                    CtConstructor c1 = (CtConstructor)o1;
                    CtConstructor c2 = (CtConstructor)o2;
                    return c1.getMethodInfo2().getDescriptor().compareTo(c2.getMethodInfo2().getDescriptor());
                }
            });
            for (i = 0; i < constructors.length; ++i) {
                CtConstructor constructor = constructors[i];
                mods = constructor.getModifiers();
                if ((mods & 2) != 0) continue;
                out.writeUTF("<init>");
                out.writeInt(mods);
                out.writeUTF(constructor.getMethodInfo2().getDescriptor().replace('/', '.'));
            }
            Arrays.sort(methods, new Comparator(){

                public int compare(Object o1, Object o2) {
                    CtMethod m1 = (CtMethod)o1;
                    CtMethod m2 = (CtMethod)o2;
                    int value = m1.getName().compareTo(m2.getName());
                    if (value == 0) {
                        value = m1.getMethodInfo2().getDescriptor().compareTo(m2.getMethodInfo2().getDescriptor());
                    }
                    return value;
                }
            });
            for (i = 0; i < methods.length; ++i) {
                CtMethod method = methods[i];
                mods = method.getModifiers() & 3391;
                if ((mods & 2) != 0) continue;
                out.writeUTF(method.getName());
                out.writeInt(mods);
                out.writeUTF(method.getMethodInfo2().getDescriptor().replace('/', '.'));
            }
            out.flush();
            MessageDigest digest = MessageDigest.getInstance("SHA");
            byte[] digested = digest.digest(bout.toByteArray());
            long hash = 0L;
            for (int i4 = java.lang.Math.min((int)digested.length, (int)8) - 1; i4 >= 0; --i4) {
                hash = hash << 8 | (long)(digested[i4] & 255);
            }
            return hash;
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new CannotCompileException(e);
        }
    }

    private static String javaName(CtClass clazz) {
        return Descriptor.toJavaName(Descriptor.toJvmName(clazz));
    }

    private static String javaName(String name) {
        return Descriptor.toJavaName(Descriptor.toJvmName(name));
    }

}

