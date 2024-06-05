package org.jsoup.select;

import org.jsoup.nodes.*;

public class Collector
{
    private Collector() {
    }
    
    public static Elements collect(final Evaluator eval, final Element root) {
        final Elements elements = new Elements();
        new NodeTraversor(new Accumulator(root, elements, eval)).traverse(root);
        return elements;
    }
    
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
}
