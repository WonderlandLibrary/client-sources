package org.jsoup.select;

import org.jsoup.nodes.*;

public abstract static class CssNthEvaluator extends Evaluator
{
    protected final int a;
    protected final int b;
    
    public CssNthEvaluator(final int a, final int b) {
        this.a = a;
        this.b = b;
    }
    
    public CssNthEvaluator(final int b) {
        this(0, b);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        final Element p = element.parent();
        if (p == null || p instanceof Document) {
            return false;
        }
        final int pos = this.calculatePosition(root, element);
        if (this.a == 0) {
            return pos == this.b;
        }
        return (pos - this.b) * this.a >= 0 && (pos - this.b) % this.a == 0;
    }
    
    @Override
    public String toString() {
        if (this.a == 0) {
            return String.format(":%s(%d)", this.getPseudoClass(), this.b);
        }
        if (this.b == 0) {
            return String.format(":%s(%dn)", this.getPseudoClass(), this.a);
        }
        return String.format(":%s(%dn%+d)", this.getPseudoClass(), this.a, this.b);
    }
    
    protected abstract String getPseudoClass();
    
    protected abstract int calculatePosition(final Element p0, final Element p1);
}
