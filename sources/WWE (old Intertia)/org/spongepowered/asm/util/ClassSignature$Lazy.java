package org.spongepowered.asm.util;

static class Lazy extends ClassSignature
{
    private final String sig;
    private ClassSignature generated;
    
    Lazy(final String sig) {
        super();
        this.sig = sig;
    }
    
    @Override
    public ClassSignature wake() {
        if (this.generated == null) {
            this.generated = ClassSignature.of(this.sig);
        }
        return this.generated;
    }
}
