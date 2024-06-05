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

public class LineNumberAttribute
extends AttributeInfo {
    public static final String tag = "LineNumberTable";

    LineNumberAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    private LineNumberAttribute(ConstPool cp, byte[] i) {
        super(cp, tag, i);
    }

    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }

    public int startPc(int i) {
        return ByteArray.readU16bit(this.info, i * 4 + 2);
    }

    public int lineNumber(int i) {
        return ByteArray.readU16bit(this.info, i * 4 + 4);
    }

    public int toLineNumber(int pc) {
        int n = this.tableLength();
        int i = 0;
        while (i < n) {
            if (pc < this.startPc(i)) {
                if (i != 0) return this.lineNumber(i - 1);
                return this.lineNumber(0);
            }
            ++i;
        }
        return this.lineNumber(i - 1);
    }

    public int toStartPc(int line) {
        int n = this.tableLength();
        int i = 0;
        while (i < n) {
            if (line == this.lineNumber(i)) {
                return this.startPc(i);
            }
            ++i;
        }
        return -1;
    }

    public Pc toNearPc(int line) {
        int n = this.tableLength();
        int nearPc = 0;
        int distance = 0;
        if (n > 0) {
            distance = this.lineNumber(0) - line;
            nearPc = this.startPc(0);
        }
        int i = 1;
        while (true) {
            if (i >= n) {
                Pc res = new Pc();
                res.index = nearPc;
                res.line = line + distance;
                return res;
            }
            int d = this.lineNumber(i) - line;
            if (d < 0 && d > distance || d >= 0 && (d < distance || distance < 0)) {
                distance = d;
                nearPc = this.startPc(i);
            }
            ++i;
        }
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        byte[] src = this.info;
        int num = src.length;
        byte[] dest = new byte[num];
        int i = 0;
        while (i < num) {
            dest[i] = src[i];
            ++i;
        }
        return new LineNumberAttribute(newCp, dest);
    }

    void shiftPc(int where, int gapLength, boolean exclusive) {
        int n = this.tableLength();
        int i = 0;
        while (i < n) {
            int pos = i * 4 + 2;
            int pc = ByteArray.readU16bit(this.info, pos);
            if (pc > where || exclusive && pc == where) {
                ByteArray.write16bit(pc + gapLength, this.info, pos);
            }
            ++i;
        }
    }

    public static class Pc {
        public int index;
        public int line;
    }
}

