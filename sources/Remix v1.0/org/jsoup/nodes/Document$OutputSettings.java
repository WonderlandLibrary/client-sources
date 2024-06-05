package org.jsoup.nodes;

import java.nio.charset.*;
import org.jsoup.helper.*;

public static class OutputSettings implements Cloneable
{
    private Entities.EscapeMode escapeMode;
    private Charset charset;
    private boolean prettyPrint;
    private boolean outline;
    private int indentAmount;
    private Syntax syntax;
    
    public OutputSettings() {
        this.escapeMode = Entities.EscapeMode.base;
        this.charset = Charset.forName("UTF-8");
        this.prettyPrint = true;
        this.outline = false;
        this.indentAmount = 1;
        this.syntax = Syntax.html;
    }
    
    public Entities.EscapeMode escapeMode() {
        return this.escapeMode;
    }
    
    public OutputSettings escapeMode(final Entities.EscapeMode escapeMode) {
        this.escapeMode = escapeMode;
        return this;
    }
    
    public Charset charset() {
        return this.charset;
    }
    
    public OutputSettings charset(final Charset charset) {
        this.charset = charset;
        return this;
    }
    
    public OutputSettings charset(final String charset) {
        this.charset(Charset.forName(charset));
        return this;
    }
    
    CharsetEncoder encoder() {
        return this.charset.newEncoder();
    }
    
    public Syntax syntax() {
        return this.syntax;
    }
    
    public OutputSettings syntax(final Syntax syntax) {
        this.syntax = syntax;
        return this;
    }
    
    public boolean prettyPrint() {
        return this.prettyPrint;
    }
    
    public OutputSettings prettyPrint(final boolean pretty) {
        this.prettyPrint = pretty;
        return this;
    }
    
    public boolean outline() {
        return this.outline;
    }
    
    public OutputSettings outline(final boolean outlineMode) {
        this.outline = outlineMode;
        return this;
    }
    
    public int indentAmount() {
        return this.indentAmount;
    }
    
    public OutputSettings indentAmount(final int indentAmount) {
        Validate.isTrue(indentAmount >= 0);
        this.indentAmount = indentAmount;
        return this;
    }
    
    public OutputSettings clone() {
        OutputSettings clone;
        try {
            clone = (OutputSettings)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.charset(this.charset.name());
        clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
        return clone;
    }
    
    public enum Syntax
    {
        html, 
        xml;
    }
}
