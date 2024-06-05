package org.jsoup.select;

import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;

abstract class CombiningEvaluator extends Evaluator
{
    final ArrayList<Evaluator> evaluators;
    int num;
    
    CombiningEvaluator() {
        this.num = 0;
        this.evaluators = new ArrayList<Evaluator>();
    }
    
    CombiningEvaluator(final Collection<Evaluator> evaluators) {
        this();
        this.evaluators.addAll(evaluators);
        this.updateNumEvaluators();
    }
    
    Evaluator rightMostEvaluator() {
        return (this.num > 0) ? this.evaluators.get(this.num - 1) : null;
    }
    
    void replaceRightMostEvaluator(final Evaluator replacement) {
        this.evaluators.set(this.num - 1, replacement);
    }
    
    void updateNumEvaluators() {
        this.num = this.evaluators.size();
    }
    
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
}
