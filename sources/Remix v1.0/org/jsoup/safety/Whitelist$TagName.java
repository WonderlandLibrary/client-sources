package org.jsoup.safety;

static class TagName extends TypedValue
{
    TagName(final String value) {
        super(value);
    }
    
    static TagName valueOf(final String value) {
        return new TagName(value);
    }
}
