package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class Class extends Evaluator
{
    private String className;
    
    public Class(final String className) {
        this.className = className;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.hasClass(this.className);
    }
    
    @Override
    public String toString() {
        return String.format(".%s", this.className);
    }
}
