package org.spongepowered.asm.mixin.transformer.debug;

import org.jetbrains.java.decompiler.main.extern.*;
import org.jetbrains.java.decompiler.util.*;
import java.io.*;

class RuntimeDecompiler$1 implements IBytecodeProvider {
    private byte[] byteCode;
    final RuntimeDecompiler this$0;
    
    RuntimeDecompiler$1(final RuntimeDecompiler this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public byte[] getBytecode(final String s, final String s2) throws IOException {
        if (this.byteCode == null) {
            this.byteCode = InterpreterUtil.getBytes(new File(s));
        }
        return this.byteCode;
    }
}