package org.jsoup.parser;

static final class Character extends Token
{
    private String data;
    
    Character() {
        super(null);
        this.type = TokenType.Character;
    }
    
    @Override
    Token reset() {
        this.data = null;
        return this;
    }
    
    Character data(final String data) {
        this.data = data;
        return this;
    }
    
    String getData() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return this.getData();
    }
}
