package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class Id extends Evaluator
{
    private String id;
    
    public Id(final String id) {
        this.id = id;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return this.id.equals(element.id());
    }
    
    @Override
    public String toString() {
        return String.format("#%s", this.id);
    }
}
