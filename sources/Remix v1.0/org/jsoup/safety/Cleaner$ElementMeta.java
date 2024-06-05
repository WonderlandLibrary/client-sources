package org.jsoup.safety;

import org.jsoup.nodes.*;

private static class ElementMeta
{
    Element el;
    int numAttribsDiscarded;
    
    ElementMeta(final Element el, final int numAttribsDiscarded) {
        this.el = el;
        this.numAttribsDiscarded = numAttribsDiscarded;
    }
}
