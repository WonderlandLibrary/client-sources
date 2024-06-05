package org.jsoup.select;

public abstract static class IndexEvaluator extends Evaluator
{
    int index;
    
    public IndexEvaluator(final int index) {
        this.index = index;
    }
}
