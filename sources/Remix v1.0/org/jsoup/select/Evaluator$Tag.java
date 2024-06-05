package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class Tag extends Evaluator
{
    private String tagName;
    
    public Tag(final String tagName) {
        this.tagName = tagName;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.tagName().equalsIgnoreCase(this.tagName);
    }
    
    @Override
    public String toString() {
        return String.format("%s", this.tagName);
    }
}
