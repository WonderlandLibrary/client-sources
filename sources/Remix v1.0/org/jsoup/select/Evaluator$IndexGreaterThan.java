package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IndexGreaterThan extends IndexEvaluator
{
    public IndexGreaterThan(final int index) {
        super(index);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.elementSiblingIndex() > this.index;
    }
    
    @Override
    public String toString() {
        return String.format(":gt(%d)", this.index);
    }
}
