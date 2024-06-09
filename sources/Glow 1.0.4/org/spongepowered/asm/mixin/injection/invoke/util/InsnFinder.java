package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.lib.tree.analysis.*;

public class InsnFinder
{
    private static final Logger logger;
    
    public InsnFinder() {
        super();
    }
    
    public AbstractInsnNode findPopInsn(final Target target, final AbstractInsnNode abstractInsnNode) {
        try {
            new PopAnalyzer(abstractInsnNode).analyze(target.classNode.name, target.method);
        }
        catch (AnalyzerException ex) {
            if (ex.getCause() instanceof AnalysisResultException) {
                return ((AnalysisResultException)ex.getCause()).getResult();
            }
            InsnFinder.logger.catching((Throwable)ex);
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static class AnalysisResultException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private AbstractInsnNode result;
        
        public AnalysisResultException(final AbstractInsnNode result) {
            super();
            this.result = result;
        }
        
        public AbstractInsnNode getResult() {
            return this.result;
        }
    }
    
    enum AnalyzerState
    {
        SEARCH, 
        ANALYSE, 
        COMPLETE;
        
        private static final AnalyzerState[] $VALUES;
        
        public static AnalyzerState[] values() {
            return AnalyzerState.$VALUES.clone();
        }
        
        public static AnalyzerState valueOf(final String s) {
            return Enum.valueOf(AnalyzerState.class, s);
        }
        
        static {
            $VALUES = new AnalyzerState[] { AnalyzerState.SEARCH, AnalyzerState.ANALYSE, AnalyzerState.COMPLETE };
        }
    }
    
    static class PopAnalyzer extends Analyzer<BasicValue>
    {
        protected final AbstractInsnNode node;
        
        public PopAnalyzer(final AbstractInsnNode node) {
            super(new BasicInterpreter());
            this.node = node;
        }
        
        @Override
        protected Frame<BasicValue> newFrame(final int n, final int n2) {
            return new PopFrame(n, n2);
        }
        
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
    }
}
