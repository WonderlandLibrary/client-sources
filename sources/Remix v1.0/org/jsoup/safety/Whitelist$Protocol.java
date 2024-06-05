package org.jsoup.safety;

static class Protocol extends TypedValue
{
    Protocol(final String value) {
        super(value);
    }
    
    static Protocol valueOf(final String value) {
        return new Protocol(value);
    }
}
