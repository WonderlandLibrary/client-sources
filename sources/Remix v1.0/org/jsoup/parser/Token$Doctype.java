package org.jsoup.parser;

static final class Doctype extends Token
{
    final StringBuilder name;
    String pubSysKey;
    final StringBuilder publicIdentifier;
    final StringBuilder systemIdentifier;
    boolean forceQuirks;
    
    Doctype() {
        super(null);
        this.name = new StringBuilder();
        this.pubSysKey = null;
        this.publicIdentifier = new StringBuilder();
        this.systemIdentifier = new StringBuilder();
        this.forceQuirks = false;
        this.type = TokenType.Doctype;
    }
    
    @Override
    Token reset() {
        Token.reset(this.name);
        this.pubSysKey = null;
        Token.reset(this.publicIdentifier);
        Token.reset(this.systemIdentifier);
        this.forceQuirks = false;
        return this;
    }
    
    String getName() {
        return this.name.toString();
    }
    
    String getPubSysKey() {
        return this.pubSysKey;
    }
    
    String getPublicIdentifier() {
        return this.publicIdentifier.toString();
    }
    
    public String getSystemIdentifier() {
        return this.systemIdentifier.toString();
    }
    
    public boolean isForceQuirks() {
        return this.forceQuirks;
    }
}
