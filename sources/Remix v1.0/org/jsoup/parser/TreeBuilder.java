package org.jsoup.parser;

import java.util.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;

abstract class TreeBuilder
{
    CharacterReader reader;
    Tokeniser tokeniser;
    protected Document doc;
    protected ArrayList<Element> stack;
    protected String baseUri;
    protected Token currentToken;
    protected ParseErrorList errors;
    protected ParseSettings settings;
    private Token.StartTag start;
    private Token.EndTag end;
    
    TreeBuilder() {
        this.start = new Token.StartTag();
        this.end = new Token.EndTag();
    }
    
    abstract ParseSettings defaultSettings();
    
    protected void initialiseParse(final String input, final String baseUri, final ParseErrorList errors, final ParseSettings settings) {
        Validate.notNull(input, "String input must not be null");
        Validate.notNull(baseUri, "BaseURI must not be null");
        this.doc = new Document(baseUri);
        this.settings = settings;
        this.reader = new CharacterReader(input);
        this.errors = errors;
        this.tokeniser = new Tokeniser(this.reader, errors);
        this.stack = new ArrayList<Element>(32);
        this.baseUri = baseUri;
    }
    
    Document parse(final String input, final String baseUri, final ParseErrorList errors, final ParseSettings settings) {
        this.initialiseParse(input, baseUri, errors, settings);
        this.runParser();
        return this.doc;
    }
    
    protected void runParser() {
        Token token;
        do {
            token = this.tokeniser.read();
            this.process(token);
            token.reset();
        } while (token.type != Token.TokenType.EOF);
    }
    
    protected abstract boolean process(final Token p0);
    
    protected boolean processStartTag(final String name) {
        if (this.currentToken == this.start) {
            return this.process(new Token.StartTag().name(name));
        }
        return this.process(this.start.reset().name(name));
    }
    
    public boolean processStartTag(final String name, final Attributes attrs) {
        if (this.currentToken == this.start) {
            return this.process(new Token.StartTag().nameAttr(name, attrs));
        }
        this.start.reset();
        this.start.nameAttr(name, attrs);
        return this.process(this.start);
    }
    
    protected boolean processEndTag(final String name) {
        if (this.currentToken == this.end) {
            return this.process(new Token.EndTag().name(name));
        }
        return this.process(this.end.reset().name(name));
    }
    
    protected Element currentElement() {
        final int size = this.stack.size();
        return (size > 0) ? this.stack.get(size - 1) : null;
    }
}
