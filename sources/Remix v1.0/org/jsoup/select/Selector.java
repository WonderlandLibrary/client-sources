package org.jsoup.select;

import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import java.util.*;

public class Selector
{
    private final Evaluator evaluator;
    private final Element root;
    
    private Selector(String query, final Element root) {
        Validate.notNull(query);
        query = query.trim();
        Validate.notEmpty(query);
        Validate.notNull(root);
        this.evaluator = QueryParser.parse(query);
        this.root = root;
    }
    
    private Selector(final Evaluator evaluator, final Element root) {
        Validate.notNull(evaluator);
        Validate.notNull(root);
        this.evaluator = evaluator;
        this.root = root;
    }
    
    public static Elements select(final String query, final Element root) {
        return new Selector(query, root).select();
    }
    
    public static Elements select(final Evaluator evaluator, final Element root) {
        return new Selector(evaluator, root).select();
    }
    
    public static Elements select(final String query, final Iterable<Element> roots) {
        Validate.notEmpty(query);
        Validate.notNull(roots);
        final Evaluator evaluator = QueryParser.parse(query);
        final ArrayList<Element> elements = new ArrayList<Element>();
        final IdentityHashMap<Element, Boolean> seenElements = new IdentityHashMap<Element, Boolean>();
        for (final Element root : roots) {
            final Elements found = select(evaluator, root);
            for (final Element el : found) {
                if (!seenElements.containsKey(el)) {
                    elements.add(el);
                    seenElements.put(el, Boolean.TRUE);
                }
            }
        }
        return new Elements(elements);
    }
    
    private Elements select() {
        return Collector.collect(this.evaluator, this.root);
    }
    
    static Elements filterOut(final Collection<Element> elements, final Collection<Element> outs) {
        final Elements output = new Elements();
        for (final Element el : elements) {
            boolean found = false;
            for (final Element out : outs) {
                if (el.equals(out)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                output.add(el);
            }
        }
        return output;
    }
    
    public static class SelectorParseException extends IllegalStateException
    {
        public SelectorParseException(final String msg, final Object... params) {
            super(String.format(msg, params));
        }
    }
}
