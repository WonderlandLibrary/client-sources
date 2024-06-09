package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.tree.analysis.*;

class PopFrame extends Frame<BasicValue>
{
    private AbstractInsnNode current;
    private AnalyzerState state;
    private int depth;
    final PopAnalyzer this$0;
    
    public PopFrame(final PopAnalyzer this$0, final int n, final int n2) {
        this.this$0 = this$0;
        super(n, n2);
        this.state = AnalyzerState.SEARCH;
        this.depth = 0;
    }
    
    @Override
    public void execute(final AbstractInsnNode current, final Interpreter<BasicValue> interpreter) throws AnalyzerException {
        super.execute(this.current = current, interpreter);
    }
    
    @Override
    public void push(final BasicValue basicValue) throws IndexOutOfBoundsException {
        if (this.current == this.this$0.node && this.state == AnalyzerState.SEARCH) {
            this.state = AnalyzerState.ANALYSE;
            ++this.depth;
        }
        else if (this.state == AnalyzerState.ANALYSE) {
            ++this.depth;
        }
        super.push(basicValue);
    }
    
    @Override
    public BasicValue pop() throws IndexOutOfBoundsException {
        if (this.state == AnalyzerState.ANALYSE && --this.depth == 0) {
            this.state = AnalyzerState.COMPLETE;
            throw new AnalysisResultException(this.current);
        }
        return super.pop();
    }
    
    @Override
    public void push(final Value value) throws IndexOutOfBoundsException {
        this.push((BasicValue)value);
    }
    
    @Override
    public Value pop() throws IndexOutOfBoundsException {
        return this.pop();
    }
}
