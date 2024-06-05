package org.jsoup.parser;

import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import org.jsoup.internal.*;

abstract class Token
{
    TokenType type;
    
    private Token() {
    }
    
    String tokenType() {
        return this.getClass().getSimpleName();
    }
    
    abstract Token reset();
    
    static void reset(final StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }
    
    final boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }
    
    final Doctype asDoctype() {
        return (Doctype)this;
    }
    
    final boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }
    
    final StartTag asStartTag() {
        return (StartTag)this;
    }
    
    final boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }
    
    final EndTag asEndTag() {
        return (EndTag)this;
    }
    
    final boolean isComment() {
        return this.type == TokenType.Comment;
    }
    
    final Comment asComment() {
        return (Comment)this;
    }
    
    final boolean isCharacter() {
        return this.type == TokenType.Character;
    }
    
    final Character asCharacter() {
        return (Character)this;
    }
    
    final boolean isEOF() {
        return this.type == TokenType.EOF;
    }
    
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
    
    abstract static class Tag extends Token
    {
        protected String tagName;
        protected String normalName;
        private String pendingAttributeName;
        private StringBuilder pendingAttributeValue;
        private String pendingAttributeValueS;
        private boolean hasEmptyAttributeValue;
        private boolean hasPendingAttributeValue;
        boolean selfClosing;
        Attributes attributes;
        
        Tag() {
            super(null);
            this.pendingAttributeValue = new StringBuilder();
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            this.selfClosing = false;
        }
        
        @Override
        Tag reset() {
            this.tagName = null;
            this.normalName = null;
            this.pendingAttributeName = null;
            Token.reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            this.selfClosing = false;
            this.attributes = null;
            return this;
        }
        
        final void newAttribute() {
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.pendingAttributeName != null) {
                this.pendingAttributeName = this.pendingAttributeName.trim();
                if (this.pendingAttributeName.length() > 0) {
                    Attribute attribute;
                    if (this.hasPendingAttributeValue) {
                        attribute = new Attribute(this.pendingAttributeName, (this.pendingAttributeValue.length() > 0) ? this.pendingAttributeValue.toString() : this.pendingAttributeValueS);
                    }
                    else if (this.hasEmptyAttributeValue) {
                        attribute = new Attribute(this.pendingAttributeName, "");
                    }
                    else {
                        attribute = new BooleanAttribute(this.pendingAttributeName);
                    }
                    this.attributes.put(attribute);
                }
            }
            this.pendingAttributeName = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            Token.reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
        }
        
        final void finaliseTag() {
            if (this.pendingAttributeName != null) {
                this.newAttribute();
            }
        }
        
        final String name() {
            Validate.isFalse(this.tagName == null || this.tagName.length() == 0);
            return this.tagName;
        }
        
        final String normalName() {
            return this.normalName;
        }
        
        final Tag name(final String name) {
            this.tagName = name;
            this.normalName = Normalizer.lowerCase(name);
            return this;
        }
        
        final boolean isSelfClosing() {
            return this.selfClosing;
        }
        
        final Attributes getAttributes() {
            return this.attributes;
        }
        
        final void appendTagName(final String append) {
            this.tagName = ((this.tagName == null) ? append : this.tagName.concat(append));
            this.normalName = Normalizer.lowerCase(this.tagName);
        }
        
        final void appendTagName(final char append) {
            this.appendTagName(String.valueOf(append));
        }
        
        final void appendAttributeName(final String append) {
            this.pendingAttributeName = ((this.pendingAttributeName == null) ? append : this.pendingAttributeName.concat(append));
        }
        
        final void appendAttributeName(final char append) {
            this.appendAttributeName(String.valueOf(append));
        }
        
        final void appendAttributeValue(final String append) {
            this.ensureAttributeValue();
            if (this.pendingAttributeValue.length() == 0) {
                this.pendingAttributeValueS = append;
            }
            else {
                this.pendingAttributeValue.append(append);
            }
        }
        
        final void appendAttributeValue(final char append) {
            this.ensureAttributeValue();
            this.pendingAttributeValue.append(append);
        }
        
        final void appendAttributeValue(final char[] append) {
            this.ensureAttributeValue();
            this.pendingAttributeValue.append(append);
        }
        
        final void appendAttributeValue(final int[] appendCodepoints) {
            this.ensureAttributeValue();
            for (final int codepoint : appendCodepoints) {
                this.pendingAttributeValue.appendCodePoint(codepoint);
            }
        }
        
        final void setEmptyAttributeValue() {
            this.hasEmptyAttributeValue = true;
        }
        
        private void ensureAttributeValue() {
            this.hasPendingAttributeValue = true;
            if (this.pendingAttributeValueS != null) {
                this.pendingAttributeValue.append(this.pendingAttributeValueS);
                this.pendingAttributeValueS = null;
            }
        }
    }
    
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
    
    enum TokenType
    {
        Doctype, 
        StartTag, 
        EndTag, 
        Comment, 
        Character, 
        EOF;
    }
}
