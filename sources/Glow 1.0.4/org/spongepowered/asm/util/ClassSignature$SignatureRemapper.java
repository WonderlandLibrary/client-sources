package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;
import java.util.*;

class SignatureRemapper extends SignatureWriter
{
    private final Set<String> localTypeVars;
    final ClassSignature this$0;
    
    SignatureRemapper(final ClassSignature this$0) {
        this.this$0 = this$0;
        super();
        this.localTypeVars = new HashSet<String>();
    }
    
    @Override
    public void visitFormalTypeParameter(final String s) {
        this.localTypeVars.add(s);
        super.visitFormalTypeParameter(s);
    }
    
    @Override
    public void visitTypeVariable(final String s) {
        if (!this.localTypeVars.contains(s)) {
            final TypeVar typeVar = this.this$0.getTypeVar(s);
            if (typeVar != null) {
                super.visitTypeVariable(typeVar.toString());
                return;
            }
        }
        super.visitTypeVariable(s);
    }
}
