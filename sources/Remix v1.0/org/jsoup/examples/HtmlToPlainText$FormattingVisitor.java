package org.jsoup.examples;

import org.jsoup.select.*;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;

private class FormattingVisitor implements NodeVisitor
{
    private static final int maxWidth = 80;
    private int width;
    private StringBuilder accum;
    
    private FormattingVisitor() {
        this.width = 0;
        this.accum = new StringBuilder();
    }
    
    public void head(final Node node, final int depth) {
        final String name = node.nodeName();
        if (node instanceof TextNode) {
            this.append(((TextNode)node).text());
        }
        else if (name.equals("li")) {
            this.append("\n * ");
        }
        else if (name.equals("dt")) {
            this.append("  ");
        }
        else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr")) {
            this.append("\n");
        }
    }
    
    public void tail(final Node node, final int depth) {
        final String name = node.nodeName();
        if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5")) {
            this.append("\n");
        }
        else if (name.equals("a")) {
            this.append(String.format(" <%s>", node.absUrl("href")));
        }
    }
    
    private void append(final String text) {
        if (text.startsWith("\n")) {
            this.width = 0;
        }
        if (text.equals(" ") && (this.accum.length() == 0 || StringUtil.in(this.accum.substring(this.accum.length() - 1), " ", "\n"))) {
            return;
        }
        if (text.length() + this.width > 80) {
            final String[] words = text.split("\\s+");
            for (int i = 0; i < words.length; ++i) {
                String word = words[i];
                final boolean last = i == words.length - 1;
                if (!last) {
                    word += " ";
                }
                if (word.length() + this.width > 80) {
                    this.accum.append("\n").append(word);
                    this.width = word.length();
                }
                else {
                    this.accum.append(word);
                    this.width += word.length();
                }
            }
        }
        else {
            this.accum.append(text);
            this.width += text.length();
        }
    }
    
    @Override
    public String toString() {
        return this.accum.toString();
    }
}
