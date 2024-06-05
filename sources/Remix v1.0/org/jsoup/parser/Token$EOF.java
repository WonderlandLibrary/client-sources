package org.jsoup.parser;

static final class EOF extends Token
{
    EOF() {
        super(null);
        this.type = TokenType.EOF;
    }
    
    @Override
    Token reset() {
        return this;
    }
}
