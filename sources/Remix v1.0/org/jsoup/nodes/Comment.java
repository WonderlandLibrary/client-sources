package org.jsoup.nodes;

import java.io.*;

public class Comment extends Node
{
    private static final String COMMENT_KEY = "comment";
    
    public Comment(final String data, final String baseUri) {
        super(baseUri);
        this.attributes.put("comment", data);
    }
    
    @Override
    public String nodeName() {
        return "#comment";
    }
    
    public String getData() {
        return this.attributes.get("comment");
    }
    
    @Override
    void outerHtmlHead(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        if (out.prettyPrint()) {
            this.indent(accum, depth, out);
        }
        accum.append("<!--").append(this.getData()).append("-->");
    }
    
    @Override
    void outerHtmlTail(final Appendable accum, final int depth, final Document.OutputSettings out) {
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
}
