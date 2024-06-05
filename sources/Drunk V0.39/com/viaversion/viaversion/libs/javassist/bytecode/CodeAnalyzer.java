/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.ExceptionTable;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;

class CodeAnalyzer
implements Opcode {
    private ConstPool constPool;
    private CodeAttribute codeAttr;

    public CodeAnalyzer(CodeAttribute ca) {
        this.codeAttr = ca;
        this.constPool = ca.getConstPool();
    }

    public int computeMaxStack() throws BadBytecode {
        boolean repeat;
        CodeIterator ci = this.codeAttr.iterator();
        int length = ci.getCodeLength();
        int[] stack = new int[length];
        this.constPool = this.codeAttr.getConstPool();
        this.initStack(stack, this.codeAttr);
        do {
            repeat = false;
            for (int i = 0; i < length; ++i) {
                if (stack[i] >= 0) continue;
                repeat = true;
                this.visitBytecode(ci, stack, i);
            }
        } while (repeat);
        int maxStack = 1;
        int i = 0;
        while (i < length) {
            if (stack[i] > maxStack) {
                maxStack = stack[i];
            }
            ++i;
        }
        return maxStack - 1;
    }

    private void initStack(int[] stack, CodeAttribute ca) {
        stack[0] = -1;
        ExceptionTable et = ca.getExceptionTable();
        if (et == null) return;
        int size = et.size();
        int i = 0;
        while (i < size) {
            stack[et.handlerPc((int)i)] = -2;
            ++i;
        }
    }

    private void visitBytecode(CodeIterator ci, int[] stack, int index) throws BadBytecode {
        int codeLength = stack.length;
        ci.move(index);
        int stackDepth = -stack[index];
        int[] jsrDepth = new int[]{-1};
        while (ci.hasNext()) {
            index = ci.next();
            stack[index] = stackDepth;
            int op = ci.byteAt(index);
            stackDepth = this.visitInst(op, ci, index, stackDepth);
            if (stackDepth < 1) {
                throw new BadBytecode("stack underflow at " + index);
            }
            if (this.processBranch(op, ci, index, codeLength, stack, stackDepth, jsrDepth)) {
                return;
            }
            if (CodeAnalyzer.isEnd(op)) {
                return;
            }
            if (op != 168 && op != 201) continue;
            --stackDepth;
        }
    }

    private boolean processBranch(int opcode, CodeIterator ci, int index, int codeLength, int[] stack, int stackDepth, int[] jsrDepth) throws BadBytecode {
        if (153 <= opcode && opcode <= 166 || opcode == 198 || opcode == 199) {
            int target = index + ci.s16bitAt(index + 1);
            this.checkTarget(index, target, codeLength, stack, stackDepth);
            return false;
        }
        switch (opcode) {
            case 167: {
                int target = index + ci.s16bitAt(index + 1);
                this.checkTarget(index, target, codeLength, stack, stackDepth);
                return true;
            }
            case 200: {
                int target = index + ci.s32bitAt(index + 1);
                this.checkTarget(index, target, codeLength, stack, stackDepth);
                return true;
            }
            case 168: 
            case 201: {
                int target = opcode == 168 ? index + ci.s16bitAt(index + 1) : index + ci.s32bitAt(index + 1);
                this.checkTarget(index, target, codeLength, stack, stackDepth);
                if (jsrDepth[0] < 0) {
                    jsrDepth[0] = stackDepth;
                    return false;
                }
                if (stackDepth != jsrDepth[0]) throw new BadBytecode("sorry, cannot compute this data flow due to JSR: " + stackDepth + "," + jsrDepth[0]);
                return false;
            }
            case 169: {
                if (jsrDepth[0] < 0) {
                    jsrDepth[0] = stackDepth + 1;
                    return false;
                }
                if (stackDepth + 1 != jsrDepth[0]) throw new BadBytecode("sorry, cannot compute this data flow due to RET: " + stackDepth + "," + jsrDepth[0]);
                return true;
            }
            case 170: 
            case 171: {
                int index2 = (index & 0xFFFFFFFC) + 4;
                int target = index + ci.s32bitAt(index2);
                this.checkTarget(index, target, codeLength, stack, stackDepth);
                if (opcode == 171) {
                    int npairs = ci.s32bitAt(index2 + 4);
                    index2 += 12;
                    int i = 0;
                    while (i < npairs) {
                        target = index + ci.s32bitAt(index2);
                        this.checkTarget(index, target, codeLength, stack, stackDepth);
                        index2 += 8;
                        ++i;
                    }
                    return true;
                }
                int low = ci.s32bitAt(index2 + 4);
                int high = ci.s32bitAt(index2 + 8);
                int n = high - low + 1;
                index2 += 12;
                int i = 0;
                while (i < n) {
                    target = index + ci.s32bitAt(index2);
                    this.checkTarget(index, target, codeLength, stack, stackDepth);
                    index2 += 4;
                    ++i;
                }
                return true;
            }
        }
        return false;
    }

    private void checkTarget(int opIndex, int target, int codeLength, int[] stack, int stackDepth) throws BadBytecode {
        if (target < 0) throw new BadBytecode("bad branch offset at " + opIndex);
        if (codeLength <= target) {
            throw new BadBytecode("bad branch offset at " + opIndex);
        }
        int d = stack[target];
        if (d == 0) {
            stack[target] = -stackDepth;
            return;
        }
        if (d == stackDepth) return;
        if (d == -stackDepth) return;
        throw new BadBytecode("verification error (" + stackDepth + "," + d + ") at " + opIndex);
    }

    private static boolean isEnd(int opcode) {
        if (172 <= opcode) {
            if (opcode <= 177) return true;
        }
        if (opcode == 191) return true;
        return false;
    }

    private int visitInst(int op, CodeIterator ci, int index, int stack) throws BadBytecode {
        switch (op) {
            case 180: {
                return stack += this.getFieldSize(ci, index) - 1;
            }
            case 181: {
                return stack -= this.getFieldSize(ci, index) + 1;
            }
            case 178: {
                return stack += this.getFieldSize(ci, index);
            }
            case 179: {
                return stack -= this.getFieldSize(ci, index);
            }
            case 182: 
            case 183: {
                String desc = this.constPool.getMethodrefType(ci.u16bitAt(index + 1));
                return stack += Descriptor.dataSize(desc) - 1;
            }
            case 184: {
                String desc = this.constPool.getMethodrefType(ci.u16bitAt(index + 1));
                return stack += Descriptor.dataSize(desc);
            }
            case 185: {
                String desc = this.constPool.getInterfaceMethodrefType(ci.u16bitAt(index + 1));
                return stack += Descriptor.dataSize(desc) - 1;
            }
            case 186: {
                String desc = this.constPool.getInvokeDynamicType(ci.u16bitAt(index + 1));
                return stack += Descriptor.dataSize(desc);
            }
            case 191: {
                return 1;
            }
            case 197: {
                return stack += 1 - ci.byteAt(index + 3);
            }
            case 196: {
                op = ci.byteAt(index + 1);
            }
        }
        stack += STACK_GROW[op];
        return stack;
    }

    private int getFieldSize(CodeIterator ci, int index) {
        String desc = this.constPool.getFieldrefType(ci.u16bitAt(index + 1));
        return Descriptor.dataSize(desc);
    }
}

