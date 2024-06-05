package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IsNthLastChild extends CssNthEvaluator
{
    public IsNthLastChild(final int a, final int b) {
        super(a, b);
    }
    
    @Override
    protected int calculatePosition(final Element root, final Element element) {
        return element.parent().children().size() - element.elementSiblingIndex();
    }
    
    @Override
    protected String getPseudoClass() {
        return "nth-last-child";
    }
}
