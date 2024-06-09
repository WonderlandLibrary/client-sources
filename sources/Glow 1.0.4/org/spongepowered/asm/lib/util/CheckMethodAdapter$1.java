package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.io.*;

class CheckMethodAdapter$1 extends MethodNode {
    final MethodVisitor val$cmv;
    
    CheckMethodAdapter$1(final int n, final int n2, final String s, final String s2, final String s3, final String[] array, final MethodVisitor val$cmv) {
        this.val$cmv = val$cmv;
        super(n, n2, s, s2, s3, array);
    }
    
    @Override
    public void visitEnd() {
        final Analyzer<BasicValue> analyzer = new Analyzer<BasicValue>(new BasicVerifier());
        try {
            analyzer.analyze("dummy", this);
        }
        catch (Exception ex) {
            if (ex instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
            }
            ex.printStackTrace();
            final StringWriter stringWriter = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(stringWriter, true);
            CheckClassAdapter.printAnalyzerResult(this, analyzer, printWriter);
            printWriter.close();
            throw new RuntimeException(ex.getMessage() + ' ' + stringWriter.toString());
        }
        this.accept(this.val$cmv);
    }
}