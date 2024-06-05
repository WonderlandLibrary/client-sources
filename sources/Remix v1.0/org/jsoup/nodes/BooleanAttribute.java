package org.jsoup.nodes;

public class BooleanAttribute extends Attribute
{
    public BooleanAttribute(final String key) {
        super(key, "");
    }
    
    @Override
    protected boolean isBooleanAttribute() {
        return true;
    }
}
