/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.lang;

import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.UTF16;

@Deprecated
public final class UScriptRun {
    private char[] emptyCharArray = new char[0];
    private char[] text;
    private int textIndex;
    private int textStart;
    private int textLimit;
    private int scriptStart;
    private int scriptLimit;
    private int scriptCode;
    private static int PAREN_STACK_DEPTH = 32;
    private static ParenStackEntry[] parenStack = new ParenStackEntry[PAREN_STACK_DEPTH];
    private int parenSP = -1;
    private int pushCount = 0;
    private int fixupCount = 0;
    private static int[] pairedChars = new int[]{40, 41, 60, 62, 91, 93, 123, 125, 171, 187, 8216, 8217, 8220, 8221, 8249, 8250, 12296, 12297, 12298, 12299, 12300, 12301, 12302, 12303, 12304, 12305, 12308, 12309, 12310, 12311, 12312, 12313, 12314, 12315};
    private static int pairedCharPower = 1 << UScriptRun.highBit(pairedChars.length);
    private static int pairedCharExtra = pairedChars.length - pairedCharPower;

    @Deprecated
    public UScriptRun() {
        char[] cArray = null;
        this.reset(cArray, 0, 0);
    }

    @Deprecated
    public UScriptRun(String string) {
        this.reset(string);
    }

    @Deprecated
    public UScriptRun(String string, int n, int n2) {
        this.reset(string, n, n2);
    }

    @Deprecated
    public UScriptRun(char[] cArray) {
        this.reset(cArray);
    }

    @Deprecated
    public UScriptRun(char[] cArray, int n, int n2) {
        this.reset(cArray, n, n2);
    }

    @Deprecated
    public final void reset() {
        while (this.stackIsNotEmpty()) {
            this.pop();
        }
        this.scriptStart = this.textStart;
        this.scriptLimit = this.textStart;
        this.scriptCode = -1;
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.textIndex = this.textStart;
    }

    @Deprecated
    public final void reset(int n, int n2) throws IllegalArgumentException {
        int n3 = 0;
        if (this.text != null) {
            n3 = this.text.length;
        }
        if (n < 0 || n2 < 0 || n > n3 - n2) {
            throw new IllegalArgumentException();
        }
        this.textStart = n;
        this.textLimit = n + n2;
        this.reset();
    }

    @Deprecated
    public final void reset(char[] cArray, int n, int n2) {
        if (cArray == null) {
            cArray = this.emptyCharArray;
        }
        this.text = cArray;
        this.reset(n, n2);
    }

    @Deprecated
    public final void reset(char[] cArray) {
        int n = 0;
        if (cArray != null) {
            n = cArray.length;
        }
        this.reset(cArray, 0, n);
    }

    @Deprecated
    public final void reset(String string, int n, int n2) {
        char[] cArray = null;
        if (string != null) {
            cArray = string.toCharArray();
        }
        this.reset(cArray, n, n2);
    }

    @Deprecated
    public final void reset(String string) {
        int n = 0;
        if (string != null) {
            n = string.length();
        }
        this.reset(string, 0, n);
    }

    @Deprecated
    public final int getScriptStart() {
        return this.scriptStart;
    }

    @Deprecated
    public final int getScriptLimit() {
        return this.scriptLimit;
    }

    @Deprecated
    public final int getScriptCode() {
        return this.scriptCode;
    }

    @Deprecated
    public final boolean next() {
        if (this.scriptLimit >= this.textLimit) {
            return true;
        }
        this.scriptCode = 0;
        this.scriptStart = this.scriptLimit;
        this.syncFixup();
        while (this.textIndex < this.textLimit) {
            int n = UTF16.charAt(this.text, this.textStart, this.textLimit, this.textIndex - this.textStart);
            int n2 = UTF16.getCharCount(n);
            int n3 = UScript.getScript(n);
            int n4 = UScriptRun.getPairIndex(n);
            this.textIndex += n2;
            if (n4 >= 0) {
                if ((n4 & 1) == 0) {
                    this.push(n4, this.scriptCode);
                } else {
                    int n5 = n4 & 0xFFFFFFFE;
                    while (this.stackIsNotEmpty() && this.top().pairIndex != n5) {
                        this.pop();
                    }
                    if (this.stackIsNotEmpty()) {
                        n3 = this.top().scriptCode;
                    }
                }
            }
            if (UScriptRun.sameScript(this.scriptCode, n3)) {
                if (this.scriptCode <= 1 && n3 > 1) {
                    this.scriptCode = n3;
                    this.fixup(this.scriptCode);
                }
                if (n4 < 0 || (n4 & 1) == 0) continue;
                this.pop();
                continue;
            }
            this.textIndex -= n2;
            break;
        }
        this.scriptLimit = this.textIndex;
        return false;
    }

    private static boolean sameScript(int n, int n2) {
        return n <= 1 || n2 <= 1 || n == n2;
    }

    private static final int mod(int n) {
        return n % PAREN_STACK_DEPTH;
    }

    private static final int inc(int n, int n2) {
        return UScriptRun.mod(n + n2);
    }

    private static final int inc(int n) {
        return UScriptRun.inc(n, 1);
    }

    private static final int dec(int n, int n2) {
        return UScriptRun.mod(n + PAREN_STACK_DEPTH - n2);
    }

    private static final int dec(int n) {
        return UScriptRun.dec(n, 1);
    }

    private static final int limitInc(int n) {
        if (n < PAREN_STACK_DEPTH) {
            ++n;
        }
        return n;
    }

    private final boolean stackIsEmpty() {
        return this.pushCount <= 0;
    }

    private final boolean stackIsNotEmpty() {
        return !this.stackIsEmpty();
    }

    private final void push(int n, int n2) {
        this.pushCount = UScriptRun.limitInc(this.pushCount);
        this.fixupCount = UScriptRun.limitInc(this.fixupCount);
        this.parenSP = UScriptRun.inc(this.parenSP);
        UScriptRun.parenStack[this.parenSP] = new ParenStackEntry(n, n2);
    }

    private final void pop() {
        if (this.stackIsEmpty()) {
            return;
        }
        UScriptRun.parenStack[this.parenSP] = null;
        if (this.fixupCount > 0) {
            --this.fixupCount;
        }
        --this.pushCount;
        this.parenSP = UScriptRun.dec(this.parenSP);
        if (this.stackIsEmpty()) {
            this.parenSP = -1;
        }
    }

    private final ParenStackEntry top() {
        return parenStack[this.parenSP];
    }

    private final void syncFixup() {
        this.fixupCount = 0;
    }

    private final void fixup(int n) {
        int n2 = UScriptRun.dec(this.parenSP, this.fixupCount);
        while (this.fixupCount-- > 0) {
            n2 = UScriptRun.inc(n2);
            UScriptRun.parenStack[n2].scriptCode = n;
        }
    }

    private static final byte highBit(int n) {
        if (n <= 0) {
            return 1;
        }
        byte by = 0;
        if (n >= 65536) {
            n >>= 16;
            by = (byte)(by + 16);
        }
        if (n >= 256) {
            n >>= 8;
            by = (byte)(by + 8);
        }
        if (n >= 16) {
            n >>= 4;
            by = (byte)(by + 4);
        }
        if (n >= 4) {
            n >>= 2;
            by = (byte)(by + 2);
        }
        if (n >= 2) {
            n >>= 1;
            by = (byte)(by + 1);
        }
        return by;
    }

    private static int getPairIndex(int n) {
        int n2 = pairedCharPower;
        int n3 = 0;
        if (n >= pairedChars[pairedCharExtra]) {
            n3 = pairedCharExtra;
        }
        while (n2 > 1) {
            if (n < pairedChars[n3 + (n2 >>= 1)]) continue;
            n3 += n2;
        }
        if (pairedChars[n3] != n) {
            n3 = -1;
        }
        return n3;
    }

    private static final class ParenStackEntry {
        int pairIndex;
        int scriptCode;

        public ParenStackEntry(int n, int n2) {
            this.pairIndex = n;
            this.scriptCode = n2;
        }
    }
}

