package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IsRoot extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        final Element r = (root instanceof Document) ? root.child(0) : root;
        return element == r;
    }
    
    @Override
    public String toString() {
        return ":root";
    }
}
