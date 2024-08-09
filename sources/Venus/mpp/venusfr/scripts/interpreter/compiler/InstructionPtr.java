/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

class InstructionPtr {
    final int[] code;
    final int idx;

    InstructionPtr(int[] nArray, int n) {
        this.code = nArray;
        this.idx = n;
    }

    int get() {
        return this.code[this.idx];
    }

    void set(int n) {
        this.code[this.idx] = n;
    }
}

