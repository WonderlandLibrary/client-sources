package org.jsoup.select;

import org.jsoup.nodes.*;

private static class Accumulator implements NodeVisitor
{
    private final Element root;
    private final Elements elements;
    private final Evaluator eval;
    
    Accumulator(final Element root, final Elements elements, final Evaluator eval) {
        this.root = root;
        this.elements = elements;
        this.eval = eval;
    }
    
    public void head(final Node node, final int depth) {
        if (node instanceof Element) {
            final Element el = (Element)node;
            if (this.eval.matches(this.root, el)) {
                this.elements.add(el);
            }
        }
    }
    
    public void tail(final Node node, final int depth) {
    }
}
