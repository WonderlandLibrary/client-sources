package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class Frame<V extends Value>
{
    private V returnValue;
    private V[] values;
    private int locals;
    private int top;
    
    public Frame(final int locals, final int n) {
        super();
        this.values = (V[])new Value[locals + n];
        this.locals = locals;
    }
    
    public Frame(final Frame<? extends V> frame) {
        this(frame.locals, frame.values.length - frame.locals);
        this.init(frame);
    }
    
    public Frame<V> init(final Frame<? extends V> frame) {
        this.returnValue = (V)frame.returnValue;
        System.arraycopy(frame.values, 0, this.values, 0, this.values.length);
        this.top = frame.top;
        return this;
    }
    
    public void setReturn(final V returnValue) {
        this.returnValue = returnValue;
    }
    
    public int getLocals() {
        return this.locals;
    }
    
    public int getMaxStackSize() {
        return this.values.length - this.locals;
    }
    
    public V getLocal(final int n) throws IndexOutOfBoundsException {
        if (n >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
        }
        return this.values[n];
    }
    
    public void setLocal(final int n, final V v) throws IndexOutOfBoundsException {
        if (n >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + n);
        }
        this.values[n] = v;
    }
    
    public int getStackSize() {
        return this.top;
    }
    
    public V getStack(final int n) throws IndexOutOfBoundsException {
        return this.values[n + this.locals];
    }
    
    public void clearStack() {
        this.top = 0;
    }
    
    public V pop() throws IndexOutOfBoundsException {
        if (this.top == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        final V[] values = this.values;
        final int top = this.top - 1;
        this.top = top;
        return values[top + this.locals];
    }
    
    public void push(final V v) throws IndexOutOfBoundsException {
        if (this.top + this.locals >= this.values.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
        }
        this.values[this.top++ + this.locals] = v;
    }
    
    public void execute(final AbstractInsnNode abstractInsnNode, final Interpreter<V> interpreter) throws AnalyzerException {
        switch (abstractInsnNode.getOpcode()) {
            case 0: {
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18: {
                this.push(interpreter.newOperation(abstractInsnNode));
                break;
            }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25: {
                this.push(interpreter.copyOperation(abstractInsnNode, this.getLocal(((VarInsnNode)abstractInsnNode).var)));
                break;
            }
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53: {
                this.push(interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop()));
                break;
            }
            case 54:
            case 55:
            case 56:
            case 57:
            case 58: {
                final Value copyOperation = interpreter.copyOperation(abstractInsnNode, this.pop());
                final int var = ((VarInsnNode)abstractInsnNode).var;
                this.setLocal(var, (V)copyOperation);
                if (copyOperation.getSize() == 2) {
                    this.setLocal(var + 1, interpreter.newValue(null));
                }
                if (var > 0) {
                    final Value local = this.getLocal(var - 1);
                    if (local != null && local.getSize() == 2) {
                        this.setLocal(var - 1, interpreter.newValue(null));
                    }
                    break;
                }
                break;
            }
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86: {
                interpreter.ternaryOperation(abstractInsnNode, this.pop(), this.pop(), this.pop());
                break;
            }
            case 87: {
                if (this.pop().getSize() == 2) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of POP");
                }
                break;
            }
            case 88: {
                if (this.pop().getSize() == 1 && this.pop().getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of POP2");
                }
                break;
            }
            case 89: {
                final Value pop = this.pop();
                if (pop.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP");
                }
                this.push((V)pop);
                this.push(interpreter.copyOperation(abstractInsnNode, (V)pop));
                break;
            }
            case 90: {
                final Value pop2 = this.pop();
                final Value pop3 = this.pop();
                if (pop2.getSize() != 1 || pop3.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X1");
                }
                this.push(interpreter.copyOperation(abstractInsnNode, (V)pop2));
                this.push((V)pop3);
                this.push((V)pop2);
                break;
            }
            case 91: {
                final Value pop4 = this.pop();
                if (pop4.getSize() == 1) {
                    final Value pop5 = this.pop();
                    if (pop5.getSize() != 1) {
                        this.push(interpreter.copyOperation(abstractInsnNode, (V)pop4));
                        this.push((V)pop5);
                        this.push((V)pop4);
                        break;
                    }
                    final Value pop6 = this.pop();
                    if (pop6.getSize() == 1) {
                        this.push(interpreter.copyOperation(abstractInsnNode, (V)pop4));
                        this.push((V)pop6);
                        this.push((V)pop5);
                        this.push((V)pop4);
                        break;
                    }
                }
                throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X2");
            }
            case 92: {
                final Value pop7 = this.pop();
                if (pop7.getSize() != 1) {
                    this.push((V)pop7);
                    this.push(interpreter.copyOperation(abstractInsnNode, (V)pop7));
                    break;
                }
                final Value pop8 = this.pop();
                if (pop8.getSize() == 1) {
                    this.push((V)pop8);
                    this.push((V)pop7);
                    this.push(interpreter.copyOperation(abstractInsnNode, (V)pop8));
                    this.push(interpreter.copyOperation(abstractInsnNode, (V)pop7));
                    break;
                }
                throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2");
            }
            case 93: {
                final Value pop9 = this.pop();
                if (pop9.getSize() == 1) {
                    final Value pop10 = this.pop();
                    if (pop10.getSize() == 1) {
                        final Value pop11 = this.pop();
                        if (pop11.getSize() == 1) {
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop10));
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop9));
                            this.push((V)pop11);
                            this.push((V)pop10);
                            this.push((V)pop9);
                            break;
                        }
                    }
                }
                else {
                    final Value pop12 = this.pop();
                    if (pop12.getSize() == 1) {
                        this.push(interpreter.copyOperation(abstractInsnNode, (V)pop9));
                        this.push((V)pop12);
                        this.push((V)pop9);
                        break;
                    }
                }
                throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X1");
            }
            case 94: {
                final Value pop13 = this.pop();
                if (pop13.getSize() == 1) {
                    final Value pop14 = this.pop();
                    if (pop14.getSize() == 1) {
                        final Value pop15 = this.pop();
                        if (pop15.getSize() != 1) {
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop14));
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop13));
                            this.push((V)pop15);
                            this.push((V)pop14);
                            this.push((V)pop13);
                            break;
                        }
                        final Value pop16 = this.pop();
                        if (pop16.getSize() == 1) {
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop14));
                            this.push(interpreter.copyOperation(abstractInsnNode, (V)pop13));
                            this.push((V)pop16);
                            this.push((V)pop15);
                            this.push((V)pop14);
                            this.push((V)pop13);
                            break;
                        }
                    }
                }
                else {
                    final Value pop17 = this.pop();
                    if (pop17.getSize() != 1) {
                        this.push(interpreter.copyOperation(abstractInsnNode, (V)pop13));
                        this.push((V)pop17);
                        this.push((V)pop13);
                        break;
                    }
                    final Value pop18 = this.pop();
                    if (pop18.getSize() == 1) {
                        this.push(interpreter.copyOperation(abstractInsnNode, (V)pop13));
                        this.push((V)pop18);
                        this.push((V)pop17);
                        this.push((V)pop13);
                        break;
                    }
                }
                throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X2");
            }
            case 95: {
                final Value pop19 = this.pop();
                final Value pop20 = this.pop();
                if (pop20.getSize() != 1 || pop19.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of SWAP");
                }
                this.push(interpreter.copyOperation(abstractInsnNode, (V)pop19));
                this.push(interpreter.copyOperation(abstractInsnNode, (V)pop20));
                break;
            }
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115: {
                this.push(interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop()));
                break;
            }
            case 116:
            case 117:
            case 118:
            case 119: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                break;
            }
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131: {
                this.push(interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop()));
                break;
            }
            case 132: {
                final int var2 = ((IincInsnNode)abstractInsnNode).var;
                this.setLocal(var2, interpreter.unaryOperation(abstractInsnNode, this.getLocal(var2)));
                break;
            }
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                break;
            }
            case 148:
            case 149:
            case 150:
            case 151:
            case 152: {
                this.push(interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop()));
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166: {
                interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop());
                break;
            }
            case 167: {
                break;
            }
            case 168: {
                this.push(interpreter.newOperation(abstractInsnNode));
                break;
            }
            case 169: {
                break;
            }
            case 170:
            case 171: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            case 172:
            case 173:
            case 174:
            case 175:
            case 176: {
                final Value pop21 = this.pop();
                interpreter.unaryOperation(abstractInsnNode, (V)pop21);
                interpreter.returnOperation(abstractInsnNode, (V)pop21, this.returnValue);
                break;
            }
            case 177: {
                if (this.returnValue != null) {
                    throw new AnalyzerException(abstractInsnNode, "Incompatible return type");
                }
                break;
            }
            case 178: {
                this.push(interpreter.newOperation(abstractInsnNode));
                break;
            }
            case 179: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            case 180: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                break;
            }
            case 181: {
                interpreter.binaryOperation(abstractInsnNode, this.pop(), this.pop());
                break;
            }
            case 182:
            case 183:
            case 184:
            case 185: {
                final ArrayList<Object> list = new ArrayList<Object>();
                final String desc = ((MethodInsnNode)abstractInsnNode).desc;
                for (int i = Type.getArgumentTypes(desc).length; i > 0; --i) {
                    list.add(0, this.pop());
                }
                if (abstractInsnNode.getOpcode() != 184) {
                    list.add(0, this.pop());
                }
                if (Type.getReturnType(desc) == Type.VOID_TYPE) {
                    interpreter.naryOperation(abstractInsnNode, (List<? extends V>)list);
                    break;
                }
                this.push(interpreter.naryOperation(abstractInsnNode, (List<? extends V>)list));
                break;
            }
            case 186: {
                final ArrayList<Object> list2 = new ArrayList<Object>();
                final String desc2 = ((InvokeDynamicInsnNode)abstractInsnNode).desc;
                for (int j = Type.getArgumentTypes(desc2).length; j > 0; --j) {
                    list2.add(0, this.pop());
                }
                if (Type.getReturnType(desc2) == Type.VOID_TYPE) {
                    interpreter.naryOperation(abstractInsnNode, (List<? extends V>)list2);
                    break;
                }
                this.push(interpreter.naryOperation(abstractInsnNode, (List<? extends V>)list2));
                break;
            }
            case 187: {
                this.push(interpreter.newOperation(abstractInsnNode));
                break;
            }
            case 188:
            case 189:
            case 190: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                break;
            }
            case 191: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            case 192:
            case 193: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                break;
            }
            case 194:
            case 195: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            case 197: {
                final ArrayList<Object> list3 = new ArrayList<Object>();
                for (int k = ((MultiANewArrayInsnNode)abstractInsnNode).dims; k > 0; --k) {
                    list3.add(0, this.pop());
                }
                this.push(interpreter.naryOperation(abstractInsnNode, (List<? extends V>)list3));
                break;
            }
            case 198:
            case 199: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                break;
            }
            default: {
                throw new RuntimeException("Illegal opcode " + abstractInsnNode.getOpcode());
            }
        }
    }
    
    public boolean merge(final Frame<? extends V> frame, final Interpreter<V> interpreter) throws AnalyzerException {
        if (this.top != frame.top) {
            throw new AnalyzerException(null, "Incompatible stack heights");
        }
        boolean b = false;
        for (int i = 0; i < this.locals + this.top; ++i) {
            final Value merge = interpreter.merge(this.values[i], (V)frame.values[i]);
            if (!merge.equals(this.values[i])) {
                this.values[i] = (V)merge;
                b = true;
            }
        }
        return b;
    }
    
    public boolean merge(final Frame<? extends V> frame, final boolean[] array) {
        boolean b = false;
        for (int i = 0; i < this.locals; ++i) {
            if (!array[i] && !this.values[i].equals(frame.values[i])) {
                this.values[i] = (V)frame.values[i];
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getLocals(); ++i) {
            sb.append(this.getLocal(i));
        }
        sb.append(' ');
        for (int j = 0; j < this.getStackSize(); ++j) {
            sb.append(this.getStack(j).toString());
        }
        return sb.toString();
    }
}
