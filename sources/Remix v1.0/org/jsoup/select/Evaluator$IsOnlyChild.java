package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IsOnlyChild extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        final Element p = element.parent();
        return p != null && !(p instanceof Document) && element.siblingElements().size() == 0;
    }
    
    @Override
    public String toString() {
        return ":only-child";
    }
}
