package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.regex.*;

public static final class Matches extends Evaluator
{
    private Pattern pattern;
    
    public Matches(final Pattern pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        final Matcher m = this.pattern.matcher(element.text());
        return m.find();
    }
    
    @Override
    public String toString() {
        return String.format(":matches(%s)", this.pattern);
    }
}
