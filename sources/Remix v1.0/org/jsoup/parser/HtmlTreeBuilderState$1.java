package org.jsoup.parser;

import org.jsoup.nodes.*;

enum HtmlTreeBuilderState$1
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            return true;
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (!t.isDoctype()) {
                tb.transition(HtmlTreeBuilderState$1.BeforeHtml);
                return tb.process(t);
            }
            final Token.Doctype d = t.asDoctype();
            final DocumentType doctype = new DocumentType(tb.settings.normalizeTag(d.getName()), d.getPubSysKey(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri());
            tb.getDocument().appendChild(doctype);
            if (d.isForceQuirks()) {
                tb.getDocument().quirksMode(Document.QuirksMode.quirks);
            }
            tb.transition(HtmlTreeBuilderState$1.BeforeHtml);
        }
        return true;
    }
}