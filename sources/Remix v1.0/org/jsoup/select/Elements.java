package org.jsoup.select;

import org.jsoup.helper.*;
import java.util.*;
import org.jsoup.nodes.*;

public class Elements extends ArrayList<Element>
{
    public Elements() {
    }
    
    public Elements(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public Elements(final Collection<Element> elements) {
        super(elements);
    }
    
    public Elements(final List<Element> elements) {
        super(elements);
    }
    
    public Elements(final Element... elements) {
        super(Arrays.asList(elements));
    }
    
    @Override
    public Elements clone() {
        final Elements clone = new Elements(this.size());
        for (final Element e : this) {
            clone.add(e.clone());
        }
        return clone;
    }
    
    public String attr(final String attributeKey) {
        for (final Element element : this) {
            if (element.hasAttr(attributeKey)) {
                return element.attr(attributeKey);
            }
        }
        return "";
    }
    
    public boolean hasAttr(final String attributeKey) {
        for (final Element element : this) {
            if (element.hasAttr(attributeKey)) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> eachAttr(final String attributeKey) {
        final List<String> attrs = new ArrayList<String>(this.size());
        for (final Element element : this) {
            if (element.hasAttr(attributeKey)) {
                attrs.add(element.attr(attributeKey));
            }
        }
        return attrs;
    }
    
    public Elements attr(final String attributeKey, final String attributeValue) {
        for (final Element element : this) {
            element.attr(attributeKey, attributeValue);
        }
        return this;
    }
    
    public Elements removeAttr(final String attributeKey) {
        for (final Element element : this) {
            element.removeAttr(attributeKey);
        }
        return this;
    }
    
    public Elements addClass(final String className) {
        for (final Element element : this) {
            element.addClass(className);
        }
        return this;
    }
    
    public Elements removeClass(final String className) {
        for (final Element element : this) {
            element.removeClass(className);
        }
        return this;
    }
    
    public Elements toggleClass(final String className) {
        for (final Element element : this) {
            element.toggleClass(className);
        }
        return this;
    }
    
    public boolean hasClass(final String className) {
        for (final Element element : this) {
            if (element.hasClass(className)) {
                return true;
            }
        }
        return false;
    }
    
    public String val() {
        if (this.size() > 0) {
            return this.first().val();
        }
        return "";
    }
    
    public Elements val(final String value) {
        for (final Element element : this) {
            element.val(value);
        }
        return this;
    }
    
    public String text() {
        final StringBuilder sb = new StringBuilder();
        for (final Element element : this) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(element.text());
        }
        return sb.toString();
    }
    
    public boolean hasText() {
        for (final Element element : this) {
            if (element.hasText()) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> eachText() {
        final ArrayList<String> texts = new ArrayList<String>(this.size());
        for (final Element el : this) {
            if (el.hasText()) {
                texts.add(el.text());
            }
        }
        return texts;
    }
    
    public String html() {
        final StringBuilder sb = new StringBuilder();
        for (final Element element : this) {
            if (sb.length() != 0) {
                sb.append("\n");
            }
            sb.append(element.html());
        }
        return sb.toString();
    }
    
    public String outerHtml() {
        final StringBuilder sb = new StringBuilder();
        for (final Element element : this) {
            if (sb.length() != 0) {
                sb.append("\n");
            }
            sb.append(element.outerHtml());
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
    
    public Elements tagName(final String tagName) {
        for (final Element element : this) {
            element.tagName(tagName);
        }
        return this;
    }
    
    public Elements html(final String html) {
        for (final Element element : this) {
            element.html(html);
        }
        return this;
    }
    
    public Elements prepend(final String html) {
        for (final Element element : this) {
            element.prepend(html);
        }
        return this;
    }
    
    public Elements append(final String html) {
        for (final Element element : this) {
            element.append(html);
        }
        return this;
    }
    
    public Elements before(final String html) {
        for (final Element element : this) {
            element.before(html);
        }
        return this;
    }
    
    public Elements after(final String html) {
        for (final Element element : this) {
            element.after(html);
        }
        return this;
    }
    
    public Elements wrap(final String html) {
        Validate.notEmpty(html);
        for (final Element element : this) {
            element.wrap(html);
        }
        return this;
    }
    
    public Elements unwrap() {
        for (final Element element : this) {
            element.unwrap();
        }
        return this;
    }
    
    public Elements empty() {
        for (final Element element : this) {
            element.empty();
        }
        return this;
    }
    
    public Elements remove() {
        for (final Element element : this) {
            element.remove();
        }
        return this;
    }
    
    public Elements select(final String query) {
        return Selector.select(query, this);
    }
    
    public Elements not(final String query) {
        final Elements out = Selector.select(query, this);
        return Selector.filterOut(this, out);
    }
    
    public Elements eq(final int index) {
        return (this.size() > index) ? new Elements(new Element[] { this.get(index) }) : new Elements();
    }
    
    public boolean is(final String query) {
        final Evaluator eval = QueryParser.parse(query);
        for (final Element e : this) {
            if (e.is(eval)) {
                return true;
            }
        }
        return false;
    }
    
    public Elements next() {
        return this.siblings(null, true, false);
    }
    
    public Elements next(final String query) {
        return this.siblings(query, true, false);
    }
    
    public Elements nextAll() {
        return this.siblings(null, true, true);
    }
    
    public Elements nextAll(final String query) {
        return this.siblings(query, true, true);
    }
    
    public Elements prev() {
        return this.siblings(null, false, false);
    }
    
    public Elements prev(final String query) {
        return this.siblings(query, false, false);
    }
    
    public Elements prevAll() {
        return this.siblings(null, false, true);
    }
    
    public Elements prevAll(final String query) {
        return this.siblings(query, false, true);
    }
    
    private Elements siblings(final String query, final boolean next, final boolean all) {
        final Elements els = new Elements();
        final Evaluator eval = (query != null) ? QueryParser.parse(query) : null;
        for (Element e : this) {
            do {
                final Element sib = next ? e.nextElementSibling() : e.previousElementSibling();
                if (sib == null) {
                    break;
                }
                if (eval == null) {
                    els.add(sib);
                }
                else if (sib.is(eval)) {
                    els.add(sib);
                }
                e = sib;
            } while (all);
        }
        return els;
    }
    
    public Elements parents() {
        final HashSet<Element> combo = new LinkedHashSet<Element>();
        for (final Element e : this) {
            combo.addAll((Collection<?>)e.parents());
        }
        return new Elements(combo);
    }
    
    public Element first() {
        return this.isEmpty() ? null : this.get(0);
    }
    
    public Element last() {
        return this.isEmpty() ? null : this.get(this.size() - 1);
    }
    
    public Elements traverse(final NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        final NodeTraversor traversor = new NodeTraversor(nodeVisitor);
        for (final Element el : this) {
            traversor.traverse(el);
        }
        return this;
    }
    
    public List<FormElement> forms() {
        final ArrayList<FormElement> forms = new ArrayList<FormElement>();
        for (final Element el : this) {
            if (el instanceof FormElement) {
                forms.add((FormElement)el);
            }
        }
        return forms;
    }
}
