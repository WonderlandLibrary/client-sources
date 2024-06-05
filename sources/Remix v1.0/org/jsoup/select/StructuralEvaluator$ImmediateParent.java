package org.jsoup.select;

import org.jsoup.nodes.*;

static class ImmediateParent extends StructuralEvaluator
{
    public ImmediateParent(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        if (root == element) {
            return false;
        }
        final Element parent = element.parent();
        return parent != null && this.evaluator.matches(root, parent);
    }
    
    @Override
    public String toString() {
        return String.format(":ImmediateParent%s", this.evaluator);
    }
}
