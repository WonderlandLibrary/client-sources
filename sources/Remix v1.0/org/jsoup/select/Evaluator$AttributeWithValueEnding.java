package org.jsoup.select;

import org.jsoup.nodes.*;
import org.jsoup.internal.*;

public static final class AttributeWithValueEnding extends AttributeKeyPair
{
    public AttributeWithValueEnding(final String key, final String value) {
        super(key, value);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.hasAttr(this.key) && Normalizer.lowerCase(element.attr(this.key)).endsWith(this.value);
    }
    
    @Override
    public String toString() {
        return String.format("[%s$=%s]", this.key, this.value);
    }
}
