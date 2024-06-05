package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IsNthChild extends CssNthEvaluator
{
    public IsNthChild(final int a, final int b) {
        super(a, b);
    }
    
    @Override
    protected int calculatePosition(final Element root, final Element element) {
        return element.elementSiblingIndex() + 1;
    }
    
    @Override
    protected String getPseudoClass() {
        return "nth-child";
    }
}
