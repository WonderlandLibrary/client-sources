package org.jsoup.select;

import org.jsoup.nodes.*;

public static final class AttributeWithValue extends AttributeKeyPair
{
    public AttributeWithValue(final String key, final String value) {
        super(key, value);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return element.hasAttr(this.key) && this.value.equalsIgnoreCase(element.attr(this.key).trim());
    }
    
    @Override
    public String toString() {
        return String.format("[%s=%s]", this.key, this.value);
    }
}
