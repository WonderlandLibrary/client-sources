package org.jsoup.select;

import org.jsoup.nodes.*;

static class ImmediatePreviousSibling extends StructuralEvaluator
{
    public ImmediatePreviousSibling(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        if (root == element) {
            return false;
        }
        final Element prev = element.previousElementSibling();
        return prev != null && this.evaluator.matches(root, prev);
    }
    
    @Override
    public String toString() {
        return String.format(":prev%s", this.evaluator);
    }
}
