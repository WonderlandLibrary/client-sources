package org.jsoup.safety;

static class AttributeValue extends TypedValue
{
    AttributeValue(final String value) {
        super(value);
    }
    
    static AttributeValue valueOf(final String value) {
        return new AttributeValue(value);
    }
}
