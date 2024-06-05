package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IndexLessThan extends IndexEvaluator
{
    public IndexLessThan(final int index) {
        super(index);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.elementSiblingIndex() < this.index;
    }
    
    @Override
    public String toString() {
        return String.format(":lt(%d)", this.index);
    }
}
