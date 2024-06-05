package org.jsoup.nodes;

import org.jsoup.helper.*;
import java.io.*;

public class XmlDeclaration extends Node
{
    private final String name;
    private final boolean isProcessingInstruction;
    
    public XmlDeclaration(final String name, final String baseUri, final boolean isProcessingInstruction) {
        super(baseUri);
        Validate.notNull(name);
        this.name = name;
        this.isProcessingInstruction = isProcessingInstruction;
    }
    
    @Override
    public String nodeName() {
        return "#declaration";
    }
    
    public String name() {
        return this.name;
    }
    
    public String getWholeDeclaration() {
        return this.attributes.html().trim();
    }
    
    @Override
    void outerHtmlHead(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        accum.append("<").append(this.isProcessingInstruction ? "!" : "?").append(this.name);
        this.attributes.html(accum, out);
        accum.append(this.isProcessingInstruction ? "!" : "?").append(">");
    }
    
    @Override
    void outerHtmlTail(final Appendable accum, final int depth, final Document.OutputSettings out) {
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
}
