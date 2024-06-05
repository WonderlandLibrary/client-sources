package org.jsoup.select;

import org.jsoup.nodes.*;

public static class IsNthLastOfType extends CssNthEvaluator
{
    public IsNthLastOfType(final int a, final int b) {
        super(a, b);
    }
    
    @Override
    protected int calculatePosition(final Element root, final Element element) {
        int pos = 0;
        final Elements family = element.parent().children();
        for (int i = element.elementSiblingIndex(); i < family.size(); ++i) {
            if (family.get(i).tag().equals(element.tag())) {
                ++pos;
            }
        }
        return pos;
    }
    
    @Override
    protected String getPseudoClass() {
        return "nth-last-of-type";
    }
}
