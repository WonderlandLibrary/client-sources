package org.jsoup.parser;

import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import org.jsoup.internal.*;

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
