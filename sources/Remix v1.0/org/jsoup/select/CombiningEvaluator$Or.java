package org.jsoup.select;

import java.util.*;
import org.jsoup.nodes.*;

static final class Or extends CombiningEvaluator
{
    Or(final Collection<Evaluator> evaluators) {
        if (this.num > 1) {
            this.evaluators.add(new And(evaluators));
        }
        else {
            this.evaluators.addAll(evaluators);
        }
        this.updateNumEvaluators();
    }
    
    Or(final Evaluator... evaluators) {
        this(Arrays.asList(evaluators));
    }
    
    Or() {
    }
    
    public void add(final Evaluator e) {
        this.evaluators.add(e);
        this.updateNumEvaluators();
    }
    
    @Override
    public boolean matches(final Element root, final Element node) {
        for (int i = 0; i < this.num; ++i) {
            final Evaluator s = this.evaluators.get(i);
            if (s.matches(root, node)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format(":or%s", this.evaluators);
    }
}
