package org.spongepowered.asm.mixin.transformer;

class Range
{
    final int start;
    final int end;
    final int marker;
    final MixinApplicatorStandard this$0;
    
    Range(final MixinApplicatorStandard this$0, final int start, final int end, final int marker) {
        this.this$0 = this$0;
        super();
        this.start = start;
        this.end = end;
        this.marker = marker;
    }
    
    boolean isValid() {
        return this.start != 0 && this.end != 0 && this.end >= this.start;
    }
    
    boolean contains(final int n) {
        return n >= this.start && n <= this.end;
    }
    
    boolean excludes(final int n) {
        return n < this.start || n > this.end;
    }
    
    @Override
    public String toString() {
        return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
    }
}
