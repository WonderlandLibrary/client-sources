package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IsLastChild extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        final Element p = element.parent();
        return p != null && !(p instanceof Document) && element.elementSiblingIndex() == p.children().size() - 1;
    }
    
    @Override
    public String toString() {
        return ":last-child";
    }
}
