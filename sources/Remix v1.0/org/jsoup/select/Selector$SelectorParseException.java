package org.jsoup.select;

public static class SelectorParseException extends IllegalStateException
{
    public SelectorParseException(final String msg, final Object... params) {
        super(String.format(msg, params));
    }
}
