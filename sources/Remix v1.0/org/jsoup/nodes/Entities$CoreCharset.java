package org.jsoup.nodes;

private enum CoreCharset
{
    ascii, 
    utf, 
    fallback;
    
    private static CoreCharset byName(final String name) {
        if (name.equals("US-ASCII")) {
            return CoreCharset.ascii;
        }
        if (name.startsWith("UTF-")) {
            return CoreCharset.utf;
        }
        return CoreCharset.fallback;
    }
}
