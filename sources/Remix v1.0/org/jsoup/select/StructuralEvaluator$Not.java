package org.jsoup.select;

import org.jsoup.nodes.*;

static class Not extends StructuralEvaluator
{
    public Not(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element node) {
        return !this.evaluator.matches(root, node);
    }
    
    @Override
    public String toString() {
        return String.format(":not%s", this.evaluator);
    }
}
