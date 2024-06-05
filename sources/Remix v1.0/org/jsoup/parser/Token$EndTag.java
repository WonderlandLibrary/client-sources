package org.jsoup.parser;

static final class EndTag extends Tag
{
    EndTag() {
        this.type = TokenType.EndTag;
    }
    
    @Override
    public String toString() {
        return "</" + this.name() + ">";
    }
}
