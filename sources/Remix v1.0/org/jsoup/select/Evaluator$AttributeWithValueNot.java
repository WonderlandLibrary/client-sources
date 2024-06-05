package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class AttributeWithValueNot extends AttributeKeyPair
{
    public AttributeWithValueNot(final String key, final String value) {
        super(key, value);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return !this.value.equalsIgnoreCase(element.attr(this.key));
    }
    
    @Override
    public String toString() {
        return String.format("[%s!=%s]", this.key, this.value);
    }
}
