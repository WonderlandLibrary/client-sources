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

public class MethodParametersAttribute
extends AttributeInfo {
    public static final String tag = "MethodParameters";

    MethodParametersAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public MethodParametersAttribute(ConstPool cp, String[] names, int[] flags) {
        super(cp, tag);
        byte[] data = new byte[names.length * 4 + 1];
        data[0] = (byte)names.length;
        int i = 0;
        while (true) {
            if (i >= names.length) {
                this.set(data);
                return;
            }
            ByteArray.write16bit(cp.addUtf8Info(names[i]), data, i * 4 + 1);
            ByteArray.write16bit(flags[i], data, i * 4 + 3);
            ++i;
        }
    }

    public int size() {
        return this.info[0] & 0xFF;
    }

    public int name(int i) {
        return ByteArray.readU16bit(this.info, i * 4 + 1);
    }

    public String parameterName(int i) {
        return this.getConstPool().getUtf8Info(this.name(i));
    }

    public int accessFlags(int i) {
        return ByteArray.readU16bit(this.info, i * 4 + 3);
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        int s = this.size();
        ConstPool cp = this.getConstPool();
        String[] names = new String[s];
        int[] flags = new int[s];
        int i = 0;
        while (i < s) {
            names[i] = cp.getUtf8Info(this.name(i));
            flags[i] = this.accessFlags(i);
            ++i;
        }
        return new MethodParametersAttribute(newCp, names, flags);
    }
}

