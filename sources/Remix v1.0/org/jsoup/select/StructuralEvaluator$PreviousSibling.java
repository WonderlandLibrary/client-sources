package org.jsoup.select;

import org.jsoup.nodes.*;

static class PreviousSibling extends StructuralEvaluator
{
    public PreviousSibling(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        if (root == element) {
            return false;
        }
        for (Element prev = element.previousElementSibling(); prev != null; prev = prev.previousElementSibling()) {
            if (this.evaluator.matches(root, prev)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format(":prev*%s", this.evaluator);
    }
}
