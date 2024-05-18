package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

class Subroutine
{
    LabelNode start;
    boolean[] access;
    List<JumpInsnNode> callers;
    
    private Subroutine() {
        super();
    }
    
    Subroutine(final LabelNode start, final int n, final JumpInsnNode jumpInsnNode) {
        super();
        this.start = start;
        this.access = new boolean[n];
        (this.callers = new ArrayList<JumpInsnNode>()).add(jumpInsnNode);
    }
    
    public Subroutine copy() {
        final Subroutine subroutine = new Subroutine();
        subroutine.start = this.start;
        subroutine.access = new boolean[this.access.length];
        System.arraycopy(this.access, 0, subroutine.access, 0, this.access.length);
        subroutine.callers = new ArrayList<JumpInsnNode>(this.callers);
        return subroutine;
    }
    
    public boolean merge(final Subroutine subroutine) throws AnalyzerException {
        boolean b = false;
        for (int i = 0; i < this.access.length; ++i) {
            if (subroutine.access[i] && !this.access[i]) {
                this.access[i] = true;
                b = true;
            }
        }
        if (subroutine.start == this.start) {
            for (int j = 0; j < subroutine.callers.size(); ++j) {
                final JumpInsnNode jumpInsnNode = subroutine.callers.get(j);
                if (!this.callers.contains(jumpInsnNode)) {
                    this.callers.add(jumpInsnNode);
                    b = true;
                }
            }
        }
        return b;
    }
}
