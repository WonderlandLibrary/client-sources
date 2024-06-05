package org.jsoup.safety;

static class AttributeKey extends TypedValue
{
    AttributeKey(final String value) {
        super(value);
    }
    
    static AttributeKey valueOf(final String value) {
        return new AttributeKey(value);
    }
}
