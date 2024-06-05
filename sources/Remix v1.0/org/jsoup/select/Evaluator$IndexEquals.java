package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class IndexEquals extends IndexEvaluator
{
    public IndexEquals(final int index) {
        super(index);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.elementSiblingIndex() == this.index;
    }
    
    @Override
    public String toString() {
        return String.format(":eq(%d)", this.index);
    }
}
