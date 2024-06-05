package org.jsoup.select;

import java.util.regex.*;
import org.jsoup.internal.*;
import org.jsoup.nodes.*;

public static final class AttributeWithValueMatching extends Evaluator
{
    String key;
    Pattern pattern;
    
    public AttributeWithValueMatching(final String key, final Pattern pattern) {
        this.key = Normalizer.normalize(key);
        this.pattern = pattern;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.hasAttr(this.key) && this.pattern.matcher(element.attr(this.key)).find();
    }
    
    @Override
    public String toString() {
        return String.format("[%s~=%s]", this.key, this.pattern.toString());
    }
}
