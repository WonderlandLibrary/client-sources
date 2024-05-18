package org.spongepowered.asm.util;

class CentredText
{
    private final Object centred;
    final PrettyPrinter this$0;
    
    public CentredText(final PrettyPrinter this$0, final Object centred) {
        this.this$0 = this$0;
        super();
        this.centred = centred;
    }
    
    @Override
    public String toString() {
        final String string = this.centred.toString();
        return String.format("%" + ((this.this$0.width - string.length()) / 2 + string.length()) + "s", string);
    }
}
