package org.jsoup.parser;

static final class Comment extends Token
{
    final StringBuilder data;
    boolean bogus;
    
    @Override
    Token reset() {
        Token.reset(this.data);
        this.bogus = false;
        return this;
    }
    
    Comment() {
        super(null);
        this.data = new StringBuilder();
        this.bogus = false;
        this.type = TokenType.Comment;
    }
    
    String getData() {
        return this.data.toString();
    }
    
    @Override
    public String toString() {
        return "<!--" + this.getData() + "-->";
    }
}
