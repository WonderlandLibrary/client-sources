package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.*;

public static class IsNthOfType extends CssNthEvaluator
{
    public IsNthOfType(final int a, final int b) {
        super(a, b);
    }
    
    @Override
    protected int calculatePosition(final Element root, final Element element) {
        int pos = 0;
        final Elements family = element.parent().children();
        for (final Element el : family) {
            if (el.tag().equals(element.tag())) {
                ++pos;
            }
            if (el == element) {
                break;
            }
        }
        return pos;
    }
    
    @Override
    protected String getPseudoClass() {
        return "nth-of-type";
    }
}
