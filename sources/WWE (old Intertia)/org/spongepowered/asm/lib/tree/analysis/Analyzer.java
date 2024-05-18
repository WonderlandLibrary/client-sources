package org.spongepowered.asm.lib.tree.analysis;

import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

public class Analyzer<V extends Value> implements Opcodes
{
    private final Interpreter<V> interpreter;
    private int n;
    private InsnList insns;
    private List<TryCatchBlockNode>[] handlers;
    private Frame<V>[] frames;
    private Subroutine[] subroutines;
    private boolean[] queued;
    private int[] queue;
    private int top;
    
    public Analyzer(final Interpreter<V> interpreter) {
        super();
        this.interpreter = interpreter;
    }
    
    public Frame<V>[] analyze(final String s, final MethodNode methodNode) throws AnalyzerException {
        if ((methodNode.access & 0x500) != 0x0) {
            return this.frames = (Frame<V>[])new Frame[0];
        }
        this.n = methodNode.instructions.size();
        this.insns = methodNode.instructions;
        this.handlers = (List<TryCatchBlockNode>[])new List[this.n];
        this.frames = (Frame<V>[])new Frame[this.n];
        this.subroutines = new Subroutine[this.n];
        this.queued = new boolean[this.n];
        this.queue = new int[this.n];
        this.top = 0;
        for (int i = 0; i < methodNode.tryCatchBlocks.size(); ++i) {
            final TryCatchBlockNode tryCatchBlockNode = methodNode.tryCatchBlocks.get(i);
            final int index = this.insns.indexOf(tryCatchBlockNode.start);
            for (int index2 = this.insns.indexOf(tryCatchBlockNode.end), j = index; j < index2; ++j) {
                List<TryCatchBlockNode> list = this.handlers[j];
                if (list == null) {
                    list = new ArrayList<TryCatchBlockNode>();
                    this.handlers[j] = list;
                }
                list.add(tryCatchBlockNode);
            }
        }
        final Subroutine subroutine = new Subroutine(null, methodNode.maxLocals, null);
        final ArrayList<Object> list2 = new ArrayList<Object>();
        final HashMap<Object, Subroutine> hashMap = new HashMap<Object, Subroutine>();
        this.findSubroutine(0, subroutine, (List<AbstractInsnNode>)list2);
        while (!list2.isEmpty()) {
            final JumpInsnNode jumpInsnNode = list2.remove(0);
            final Subroutine subroutine2 = hashMap.get(jumpInsnNode.label);
            if (subroutine2 == null) {
                final Subroutine subroutine3 = new Subroutine(jumpInsnNode.label, methodNode.maxLocals, jumpInsnNode);
                hashMap.put(jumpInsnNode.label, subroutine3);
                this.findSubroutine(this.insns.indexOf(jumpInsnNode.label), subroutine3, (List<AbstractInsnNode>)list2);
            }
            else {
                subroutine2.callers.add(jumpInsnNode);
            }
        }
        for (int k = 0; k < this.n; ++k) {
            if (this.subroutines[k] != null && this.subroutines[k].start == null) {
                this.subroutines[k] = null;
            }
        }
        final Frame<V> frame = this.newFrame(methodNode.maxLocals, methodNode.maxStack);
        final Frame<V> frame2 = this.newFrame(methodNode.maxLocals, methodNode.maxStack);
        frame.setReturn(this.interpreter.newValue(Type.getReturnType(methodNode.desc)));
        final Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
        int l = 0;
        if ((methodNode.access & 0x8) == 0x0) {
            frame.setLocal(l++, this.interpreter.newValue(Type.getObjectType(s)));
        }
        for (int n = 0; n < argumentTypes.length; ++n) {
            frame.setLocal(l++, this.interpreter.newValue(argumentTypes[n]));
            if (argumentTypes[n].getSize() == 2) {
                frame.setLocal(l++, this.interpreter.newValue(null));
            }
        }
        while (l < methodNode.maxLocals) {
            frame.setLocal(l++, this.interpreter.newValue(null));
        }
        this.merge(0, frame, null);
        this.init(s, methodNode);
        while (this.top > 0) {
            final int[] queue = this.queue;
            final int top = this.top - 1;
            this.top = top;
            final int n2 = queue[top];
            final Frame<V> frame3 = this.frames[n2];
            Subroutine subroutine4 = this.subroutines[n2];
            this.queued[n2] = false;
            AbstractInsnNode value = null;
            try {
                value = methodNode.instructions.get(n2);
                final int opcode = value.getOpcode();
                final int type = value.getType();
                if (type == 8 || type == 15 || type == 14) {
                    this.merge(n2 + 1, frame3, subroutine4);
                    this.newControlFlowEdge(n2, n2 + 1);
                }
                else {
                    frame.init((Frame<? extends V>)frame3).execute(value, this.interpreter);
                    subroutine4 = ((subroutine4 == null) ? null : subroutine4.copy());
                    if (value instanceof JumpInsnNode) {
                        final JumpInsnNode jumpInsnNode2 = (JumpInsnNode)value;
                        if (opcode != 167 && opcode != 168) {
                            this.merge(n2 + 1, frame, subroutine4);
                            this.newControlFlowEdge(n2, n2 + 1);
                        }
                        final int index3 = this.insns.indexOf(jumpInsnNode2.label);
                        if (opcode == 168) {
                            this.merge(index3, frame, new Subroutine(jumpInsnNode2.label, methodNode.maxLocals, jumpInsnNode2));
                        }
                        else {
                            this.merge(index3, frame, subroutine4);
                        }
                        this.newControlFlowEdge(n2, index3);
                    }
                    else if (value instanceof LookupSwitchInsnNode) {
                        final LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)value;
                        final int index4 = this.insns.indexOf(lookupSwitchInsnNode.dflt);
                        this.merge(index4, frame, subroutine4);
                        this.newControlFlowEdge(n2, index4);
                        for (int n3 = 0; n3 < lookupSwitchInsnNode.labels.size(); ++n3) {
                            final int index5 = this.insns.indexOf(lookupSwitchInsnNode.labels.get(n3));
                            this.merge(index5, frame, subroutine4);
                            this.newControlFlowEdge(n2, index5);
                        }
                    }
                    else if (value instanceof TableSwitchInsnNode) {
                        final TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)value;
                        final int index6 = this.insns.indexOf(tableSwitchInsnNode.dflt);
                        this.merge(index6, frame, subroutine4);
                        this.newControlFlowEdge(n2, index6);
                        for (int n4 = 0; n4 < tableSwitchInsnNode.labels.size(); ++n4) {
                            final int index7 = this.insns.indexOf(tableSwitchInsnNode.labels.get(n4));
                            this.merge(index7, frame, subroutine4);
                            this.newControlFlowEdge(n2, index7);
                        }
                    }
                    else if (opcode == 169) {
                        if (subroutine4 == null) {
                            throw new AnalyzerException(value, "RET instruction outside of a sub routine");
                        }
                        for (int n5 = 0; n5 < subroutine4.callers.size(); ++n5) {
                            final int index8 = this.insns.indexOf(subroutine4.callers.get(n5));
                            if (this.frames[index8] != null) {
                                this.merge(index8 + 1, this.frames[index8], frame, this.subroutines[index8], subroutine4.access);
                                this.newControlFlowEdge(n2, index8 + 1);
                            }
                        }
                    }
                    else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                        if (subroutine4 != null) {
                            if (value instanceof VarInsnNode) {
                                final int var = ((VarInsnNode)value).var;
                                subroutine4.access[var] = true;
                                if (opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57) {
                                    subroutine4.access[var + 1] = true;
                                }
                            }
                            else if (value instanceof IincInsnNode) {
                                subroutine4.access[((IincInsnNode)value).var] = true;
                            }
                        }
                        this.merge(n2 + 1, frame, subroutine4);
                        this.newControlFlowEdge(n2, n2 + 1);
                    }
                }
                final List<TryCatchBlockNode> list3 = this.handlers[n2];
                if (list3 == null) {
                    continue;
                }
                for (int n6 = 0; n6 < list3.size(); ++n6) {
                    final TryCatchBlockNode tryCatchBlockNode2 = list3.get(n6);
                    Type type2;
                    if (tryCatchBlockNode2.type == null) {
                        type2 = Type.getObjectType("java/lang/Throwable");
                    }
                    else {
                        type2 = Type.getObjectType(tryCatchBlockNode2.type);
                    }
                    final int index9 = this.insns.indexOf(tryCatchBlockNode2.handler);
                    if (this.newControlFlowExceptionEdge(n2, tryCatchBlockNode2)) {
                        frame2.init((Frame<? extends V>)frame3);
                        frame2.clearStack();
                        frame2.push(this.interpreter.newValue(type2));
                        this.merge(index9, frame2, subroutine4);
                    }
                }
            }
            catch (AnalyzerException ex) {
                throw new AnalyzerException(ex.node, "Error at instruction " + n2 + ": " + ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                throw new AnalyzerException(value, "Error at instruction " + n2 + ": " + ex2.getMessage(), ex2);
            }
        }
        return this.frames;
    }
    
    private void findSubroutine(int n, final Subroutine subroutine, final List<AbstractInsnNode> list) throws AnalyzerException {
        while (n >= 0 && n < this.n) {
            if (this.subroutines[n] != null) {
                return;
            }
            this.subroutines[n] = subroutine.copy();
            final AbstractInsnNode value = this.insns.get(n);
            if (value instanceof JumpInsnNode) {
                if (value.getOpcode() == 168) {
                    list.add(value);
                }
                else {
                    this.findSubroutine(this.insns.indexOf(((JumpInsnNode)value).label), subroutine, list);
                }
            }
            else if (value instanceof TableSwitchInsnNode) {
                final TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)value;
                this.findSubroutine(this.insns.indexOf(tableSwitchInsnNode.dflt), subroutine, list);
                for (int i = tableSwitchInsnNode.labels.size() - 1; i >= 0; --i) {
                    this.findSubroutine(this.insns.indexOf(tableSwitchInsnNode.labels.get(i)), subroutine, list);
                }
            }
            else if (value instanceof LookupSwitchInsnNode) {
                final LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)value;
                this.findSubroutine(this.insns.indexOf(lookupSwitchInsnNode.dflt), subroutine, list);
                for (int j = lookupSwitchInsnNode.labels.size() - 1; j >= 0; --j) {
                    this.findSubroutine(this.insns.indexOf(lookupSwitchInsnNode.labels.get(j)), subroutine, list);
                }
            }
            final List<TryCatchBlockNode> list2 = this.handlers[n];
            if (list2 != null) {
                for (int k = 0; k < list2.size(); ++k) {
                    this.findSubroutine(this.insns.indexOf(list2.get(k).handler), subroutine, list);
                }
            }
            switch (value.getOpcode()) {
                case 167:
                case 169:
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 191: {
                    return;
                }
                default: {
                    ++n;
                    continue;
                }
            }
        }
        throw new AnalyzerException(null, "Execution can fall off end of the code");
    }
    
    public Frame<V>[] getFrames() {
        return this.frames;
    }
    
    public List<TryCatchBlockNode> getHandlers(final int n) {
        return this.handlers[n];
    }
    
    protected void init(final String s, final MethodNode methodNode) throws AnalyzerException {
    }
    
    protected Frame<V> newFrame(final int n, final int n2) {
        return new Frame<V>(n, n2);
    }
    
    protected Frame<V> newFrame(final Frame<? extends V> frame) {
        return new Frame<V>(frame);
    }
    
    protected void newControlFlowEdge(final int n, final int n2) {
    }
    
    protected boolean newControlFlowExceptionEdge(final int n, final int n2) {
        return true;
    }
    
    protected boolean newControlFlowExceptionEdge(final int n, final TryCatchBlockNode tryCatchBlockNode) {
        return this.newControlFlowExceptionEdge(n, this.insns.indexOf(tryCatchBlockNode.handler));
    }
    
    private void merge(final int n, final Frame<V> frame, final Subroutine subroutine) throws AnalyzerException {
        final Frame<V> frame2 = this.frames[n];
        final Subroutine subroutine2 = this.subroutines[n];
        boolean merge;
        if (frame2 == null) {
            this.frames[n] = this.newFrame((Frame<? extends V>)frame);
            merge = true;
        }
        else {
            merge = frame2.merge((Frame<? extends V>)frame, this.interpreter);
        }
        if (subroutine2 == null) {
            if (subroutine != null) {
                this.subroutines[n] = subroutine.copy();
                merge = true;
            }
        }
        else if (subroutine != null) {
            merge |= subroutine2.merge(subroutine);
        }
        if (merge && !this.queued[n]) {
            this.queued[n] = true;
            this.queue[this.top++] = n;
        }
    }
    
    private void merge(final int n, final Frame<V> frame, final Frame<V> frame2, final Subroutine subroutine, final boolean[] array) throws AnalyzerException {
        final Frame<V> frame3 = this.frames[n];
        final Subroutine subroutine2 = this.subroutines[n];
        frame2.merge((Frame<? extends V>)frame, array);
        boolean merge;
        if (frame3 == null) {
            this.frames[n] = this.newFrame((Frame<? extends V>)frame2);
            merge = true;
        }
        else {
            merge = frame3.merge((Frame<? extends V>)frame2, this.interpreter);
        }
        if (subroutine2 != null && subroutine != null) {
            merge |= subroutine2.merge(subroutine);
        }
        if (merge && !this.queued[n]) {
            this.queued[n] = true;
            this.queue[this.top++] = n;
        }
    }
}
