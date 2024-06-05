/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.ByteArray;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class BootstrapMethodsAttribute
extends AttributeInfo {
    public static final String tag = "BootstrapMethods";

    BootstrapMethodsAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public BootstrapMethodsAttribute(ConstPool cp, BootstrapMethod[] methods) {
        super(cp, tag);
        int size = 2;
        for (int i = 0; i < methods.length; size += 4 + methods[i].arguments.length * 2, ++i) {
        }
        byte[] data = new byte[size];
        ByteArray.write16bit(methods.length, data, 0);
        int pos = 2;
        int i = 0;
        while (true) {
            if (i >= methods.length) {
                this.set(data);
                return;
            }
            ByteArray.write16bit(methods[i].methodRef, data, pos);
            ByteArray.write16bit(methods[i].arguments.length, data, pos + 2);
            int[] args = methods[i].arguments;
            pos += 4;
            for (int k = 0; k < args.length; pos += 2, ++k) {
                ByteArray.write16bit(args[k], data, pos);
            }
            ++i;
        }
    }

    public BootstrapMethod[] getMethods() {
        byte[] data = this.get();
        int num = ByteArray.readU16bit(data, 0);
        BootstrapMethod[] methods = new BootstrapMethod[num];
        int pos = 2;
        int i = 0;
        while (i < num) {
            int ref = ByteArray.readU16bit(data, pos);
            int len = ByteArray.readU16bit(data, pos + 2);
            int[] args = new int[len];
            pos += 4;
            for (int k = 0; k < len; pos += 2, ++k) {
                args[k] = ByteArray.readU16bit(data, pos);
            }
            methods[i] = new BootstrapMethod(ref, args);
            ++i;
        }
        return methods;
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        BootstrapMethod[] methods = this.getMethods();
        ConstPool thisCp = this.getConstPool();
        int i = 0;
        while (i < methods.length) {
            BootstrapMethod m = methods[i];
            m.methodRef = thisCp.copy(m.methodRef, newCp, classnames);
            for (int k = 0; k < m.arguments.length; ++k) {
                m.arguments[k] = thisCp.copy(m.arguments[k], newCp, classnames);
            }
            ++i;
        }
        return new BootstrapMethodsAttribute(newCp, methods);
    }

    public static class BootstrapMethod {
        public int methodRef;
        public int[] arguments;

        public BootstrapMethod(int method, int[] args) {
            this.methodRef = method;
            this.arguments = args;
        }
    }
}

