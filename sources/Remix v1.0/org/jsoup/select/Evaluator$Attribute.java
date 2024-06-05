package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class Attribute extends Evaluator
{
    private String key;
    
    public Attribute(final String key) {
        this.key = key;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.hasAttr(this.key);
    }
    
    @Override
    public String toString() {
        return String.format("[%s]", this.key);
    }
}
