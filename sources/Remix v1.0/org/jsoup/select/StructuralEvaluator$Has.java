package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.*;

static class Has extends StructuralEvaluator
{
    public Has(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        for (final Element e : element.getAllElements()) {
            if (e != element && this.evaluator.matches(root, e)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format(":has(%s)", this.evaluator);
    }
}
