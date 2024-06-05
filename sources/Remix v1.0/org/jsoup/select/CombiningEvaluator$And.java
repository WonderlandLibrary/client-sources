package org.jsoup.select;

import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;

static final class And extends CombiningEvaluator
{
    And(final Collection<Evaluator> evaluators) {
        super(evaluators);
    }
    
    And(final Evaluator... evaluators) {
        this(Arrays.asList(evaluators));
    }
    
    @Override
    public boolean matches(final Element root, final Element node) {
        for (int i = 0; i < this.num; ++i) {
            final Evaluator s = this.evaluators.get(i);
            if (!s.matches(root, node)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return StringUtil.join(this.evaluators, " ");
    }
}
