package org.jsoup.parser;

import org.jsoup.nodes.*;
import org.jsoup.internal.*;

static final class StartTag extends Tag
{
    StartTag() {
        this.attributes = new Attributes();
        this.type = TokenType.StartTag;
    }
    
    @Override
    Tag reset() {
        super.reset();
        this.attributes = new Attributes();
        return this;
    }
    
    StartTag nameAttr(final String name, final Attributes attributes) {
        this.tagName = name;
        this.attributes = attributes;
        this.normalName = Normalizer.lowerCase(this.tagName);
        return this;
    }
    
    @Override
    public String toString() {
        if (this.attributes != null && this.attributes.size() > 0) {
            return "<" + this.name() + " " + this.attributes.toString() + ">";
        }
        return "<" + this.name() + ">";
    }
}
