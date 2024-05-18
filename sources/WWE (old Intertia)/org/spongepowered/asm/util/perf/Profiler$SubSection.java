package org.spongepowered.asm.util.perf;

class SubSection extends LiveSection
{
    private final String baseName;
    private final Section root;
    final Profiler this$0;
    
    SubSection(final Profiler this$0, final String s, final int n, final String baseName, final Section root) {
        this.this$0 = this$0.super(s, n);
        this.baseName = baseName;
        this.root = root;
    }
    
    @Override
    Section invalidate() {
        this.root.invalidate();
        return super.invalidate();
    }
    
    @Override
    public String getBaseName() {
        return this.baseName;
    }
    
    @Override
    public void setInfo(final String s) {
        this.root.setInfo(s);
        super.setInfo(s);
    }
    
    @Override
    Section getDelegate() {
        return this.root;
    }
    
    @Override
    Section start() {
        this.root.start();
        return super.start();
    }
    
    @Override
    public Section end() {
        this.root.stop();
        return super.end();
    }
    
    @Override
    public Section next(final String s) {
        super.stop();
        return this.root.next(s);
    }
}
