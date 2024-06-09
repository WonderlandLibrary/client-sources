package org.spongepowered.asm.mixin.injection.invoke;

class Meta
{
    public static final String KEY = "redirector";
    final int priority;
    final boolean isFinal;
    final String name;
    final String desc;
    final RedirectInjector this$0;
    
    public Meta(final RedirectInjector this$0, final int priority, final boolean isFinal, final String name, final String desc) {
        this.this$0 = this$0;
        super();
        this.priority = priority;
        this.isFinal = isFinal;
        this.name = name;
        this.desc = desc;
    }
    
    RedirectInjector getOwner() {
        return this.this$0;
    }
}
