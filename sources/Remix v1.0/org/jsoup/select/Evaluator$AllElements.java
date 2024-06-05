package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class AllElements extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        return true;
    }
    
    @Override
    public String toString() {
        return "*";
    }
}
