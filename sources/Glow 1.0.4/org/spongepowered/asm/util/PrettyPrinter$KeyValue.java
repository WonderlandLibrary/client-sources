package org.spongepowered.asm.util;

class KeyValue implements IVariableWidthEntry
{
    private final String key;
    private final Object value;
    final PrettyPrinter this$0;
    
    public KeyValue(final PrettyPrinter this$0, final String key, final Object value) {
        this.this$0 = this$0;
        super();
        this.key = key;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.format(this.this$0.kvFormat, this.key, this.value);
    }
    
    @Override
    public int getWidth() {
        return this.toString().length();
    }
}
