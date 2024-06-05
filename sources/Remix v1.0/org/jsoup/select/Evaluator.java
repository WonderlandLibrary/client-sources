package org.jsoup.select;

import org.jsoup.helper.*;
import org.jsoup.internal.*;
import java.util.*;
import org.jsoup.nodes.*;
import java.util.regex.*;

public abstract class Evaluator
{
    protected Evaluator() {
    }
    
    public abstract boolean matches(final Element p0, final Element p1);
    
    public static final class Tag extends Evaluator
    {
        private String tagName;
        
        public Tag(final String tagName) {
            this.tagName = tagName;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.tagName().equalsIgnoreCase(this.tagName);
        }
        
        @Override
        public String toString() {
            return String.format("%s", this.tagName);
        }
    }
    
    public static final class TagEndsWith extends Evaluator
    {
        private String tagName;
        
        public TagEndsWith(final String tagName) {
            this.tagName = tagName;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.tagName().endsWith(this.tagName);
        }
        
        @Override
        public String toString() {
            return String.format("%s", this.tagName);
        }
    }
    
    public static final class Id extends Evaluator
    {
        private String id;
        
        public Id(final String id) {
            this.id = id;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return this.id.equals(element.id());
        }
        
        @Override
        public String toString() {
            return String.format("#%s", this.id);
        }
    }
    
    public static final class Class extends Evaluator
    {
        private String className;
        
        public Class(final String className) {
            this.className = className;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasClass(this.className);
        }
        
        @Override
        public String toString() {
            return String.format(".%s", this.className);
        }
    }
    
    public static final class Attribute extends Evaluator
    {
        private String key;
        
        public Attribute(final String key) {
            this.key = key;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key);
        }
        
        @Override
        public String toString() {
            return String.format("[%s]", this.key);
        }
    }
    
    public static final class AttributeStarting extends Evaluator
    {
        private String keyPrefix;
        
        public AttributeStarting(final String keyPrefix) {
            Validate.notEmpty(keyPrefix);
            this.keyPrefix = Normalizer.lowerCase(keyPrefix);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            final List<org.jsoup.nodes.Attribute> values = element.attributes().asList();
            for (final org.jsoup.nodes.Attribute attribute : values) {
                if (Normalizer.lowerCase(attribute.getKey()).startsWith(this.keyPrefix)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public String toString() {
            return String.format("[^%s]", this.keyPrefix);
        }
    }
    
    public static final class AttributeWithValue extends AttributeKeyPair
    {
        public AttributeWithValue(final String key, final String value) {
            super(key, value);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key) && this.value.equalsIgnoreCase(element.attr(this.key).trim());
        }
        
        @Override
        public String toString() {
            return String.format("[%s=%s]", this.key, this.value);
        }
    }
    
    public static final class AttributeWithValueNot extends AttributeKeyPair
    {
        public AttributeWithValueNot(final String key, final String value) {
            super(key, value);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return !this.value.equalsIgnoreCase(element.attr(this.key));
        }
        
        @Override
        public String toString() {
            return String.format("[%s!=%s]", this.key, this.value);
        }
    }
    
    public static final class AttributeWithValueStarting extends AttributeKeyPair
    {
        public AttributeWithValueStarting(final String key, final String value) {
            super(key, value);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key) && Normalizer.lowerCase(element.attr(this.key)).startsWith(this.value);
        }
        
        @Override
        public String toString() {
            return String.format("[%s^=%s]", this.key, this.value);
        }
    }
    
    public static final class AttributeWithValueEnding extends AttributeKeyPair
    {
        public AttributeWithValueEnding(final String key, final String value) {
            super(key, value);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key) && Normalizer.lowerCase(element.attr(this.key)).endsWith(this.value);
        }
        
        @Override
        public String toString() {
            return String.format("[%s$=%s]", this.key, this.value);
        }
    }
    
    public static final class AttributeWithValueContaining extends AttributeKeyPair
    {
        public AttributeWithValueContaining(final String key, final String value) {
            super(key, value);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key) && Normalizer.lowerCase(element.attr(this.key)).contains(this.value);
        }
        
        @Override
        public String toString() {
            return String.format("[%s*=%s]", this.key, this.value);
        }
    }
    
    public static final class AttributeWithValueMatching extends Evaluator
    {
        String key;
        Pattern pattern;
        
        public AttributeWithValueMatching(final String key, final Pattern pattern) {
            this.key = Normalizer.normalize(key);
            this.pattern = pattern;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.hasAttr(this.key) && this.pattern.matcher(element.attr(this.key)).find();
        }
        
        @Override
        public String toString() {
            return String.format("[%s~=%s]", this.key, this.pattern.toString());
        }
    }
    
    public abstract static class AttributeKeyPair extends Evaluator
    {
        String key;
        String value;
        
        public AttributeKeyPair(final String key, String value) {
            Validate.notEmpty(key);
            Validate.notEmpty(value);
            this.key = Normalizer.normalize(key);
            if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
                value = value.substring(1, value.length() - 1);
            }
            this.value = Normalizer.normalize(value);
        }
    }
    
    public static final class AllElements extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            return true;
        }
        
        @Override
        public String toString() {
            return "*";
        }
    }
    
    public static final class IndexLessThan extends IndexEvaluator
    {
        public IndexLessThan(final int index) {
            super(index);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.elementSiblingIndex() < this.index;
        }
        
        @Override
        public String toString() {
            return String.format(":lt(%d)", this.index);
        }
    }
    
    public static final class IndexGreaterThan extends IndexEvaluator
    {
        public IndexGreaterThan(final int index) {
            super(index);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.elementSiblingIndex() > this.index;
        }
        
        @Override
        public String toString() {
            return String.format(":gt(%d)", this.index);
        }
    }
    
    public static final class IndexEquals extends IndexEvaluator
    {
        public IndexEquals(final int index) {
            super(index);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return element.elementSiblingIndex() == this.index;
        }
        
        @Override
        public String toString() {
            return String.format(":eq(%d)", this.index);
        }
    }
    
    public static final class IsLastChild extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element p = element.parent();
            return p != null && !(p instanceof Document) && element.elementSiblingIndex() == p.children().size() - 1;
        }
        
        @Override
        public String toString() {
            return ":last-child";
        }
    }
    
    public static final class IsFirstOfType extends IsNthOfType
    {
        public IsFirstOfType() {
            super(0, 1);
        }
        
        @Override
        public String toString() {
            return ":first-of-type";
        }
    }
    
    public static final class IsLastOfType extends IsNthLastOfType
    {
        public IsLastOfType() {
            super(0, 1);
        }
        
        @Override
        public String toString() {
            return ":last-of-type";
        }
    }
    
    public abstract static class CssNthEvaluator extends Evaluator
    {
        protected final int a;
        protected final int b;
        
        public CssNthEvaluator(final int a, final int b) {
            this.a = a;
            this.b = b;
        }
        
        public CssNthEvaluator(final int b) {
            this(0, b);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element p = element.parent();
            if (p == null || p instanceof Document) {
                return false;
            }
            final int pos = this.calculatePosition(root, element);
            if (this.a == 0) {
                return pos == this.b;
            }
            return (pos - this.b) * this.a >= 0 && (pos - this.b) % this.a == 0;
        }
        
        @Override
        public String toString() {
            if (this.a == 0) {
                return String.format(":%s(%d)", this.getPseudoClass(), this.b);
            }
            if (this.b == 0) {
                return String.format(":%s(%dn)", this.getPseudoClass(), this.a);
            }
            return String.format(":%s(%dn%+d)", this.getPseudoClass(), this.a, this.b);
        }
        
        protected abstract String getPseudoClass();
        
        protected abstract int calculatePosition(final Element p0, final Element p1);
    }
    
    public static final class IsNthChild extends CssNthEvaluator
    {
        public IsNthChild(final int a, final int b) {
            super(a, b);
        }
        
        @Override
        protected int calculatePosition(final Element root, final Element element) {
            return element.elementSiblingIndex() + 1;
        }
        
        @Override
        protected String getPseudoClass() {
            return "nth-child";
        }
    }
    
    public static final class IsNthLastChild extends CssNthEvaluator
    {
        public IsNthLastChild(final int a, final int b) {
            super(a, b);
        }
        
        @Override
        protected int calculatePosition(final Element root, final Element element) {
            return element.parent().children().size() - element.elementSiblingIndex();
        }
        
        @Override
        protected String getPseudoClass() {
            return "nth-last-child";
        }
    }
    
    public static class IsNthOfType extends CssNthEvaluator
    {
        public IsNthOfType(final int a, final int b) {
            super(a, b);
        }
        
        @Override
        protected int calculatePosition(final Element root, final Element element) {
            int pos = 0;
            final Elements family = element.parent().children();
            for (final Element el : family) {
                if (el.tag().equals(element.tag())) {
                    ++pos;
                }
                if (el == element) {
                    break;
                }
            }
            return pos;
        }
        
        @Override
        protected String getPseudoClass() {
            return "nth-of-type";
        }
    }
    
    public static class IsNthLastOfType extends CssNthEvaluator
    {
        public IsNthLastOfType(final int a, final int b) {
            super(a, b);
        }
        
        @Override
        protected int calculatePosition(final Element root, final Element element) {
            int pos = 0;
            final Elements family = element.parent().children();
            for (int i = element.elementSiblingIndex(); i < family.size(); ++i) {
                if (family.get(i).tag().equals(element.tag())) {
                    ++pos;
                }
            }
            return pos;
        }
        
        @Override
        protected String getPseudoClass() {
            return "nth-last-of-type";
        }
    }
    
    public static final class IsFirstChild extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element p = element.parent();
            return p != null && !(p instanceof Document) && element.elementSiblingIndex() == 0;
        }
        
        @Override
        public String toString() {
            return ":first-child";
        }
    }
    
    public static final class IsRoot extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element r = (root instanceof Document) ? root.child(0) : root;
            return element == r;
        }
        
        @Override
        public String toString() {
            return ":root";
        }
    }
    
    public static final class IsOnlyChild extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element p = element.parent();
            return p != null && !(p instanceof Document) && element.siblingElements().size() == 0;
        }
        
        @Override
        public String toString() {
            return ":only-child";
        }
    }
    
    public static final class IsOnlyOfType extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final Element p = element.parent();
            if (p == null || p instanceof Document) {
                return false;
            }
            int pos = 0;
            final Elements family = p.children();
            for (final Element el : family) {
                if (el.tag().equals(element.tag())) {
                    ++pos;
                }
            }
            return pos == 1;
        }
        
        @Override
        public String toString() {
            return ":only-of-type";
        }
    }
    
    public static final class IsEmpty extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            final List<Node> family = element.childNodes();
            for (final Node n : family) {
                if (!(n instanceof Comment) && !(n instanceof XmlDeclaration) && !(n instanceof DocumentType)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public String toString() {
            return ":empty";
        }
    }
    
    public abstract static class IndexEvaluator extends Evaluator
    {
        int index;
        
        public IndexEvaluator(final int index) {
            this.index = index;
        }
    }
    
    public static final class ContainsText extends Evaluator
    {
        private String searchText;
        
        public ContainsText(final String searchText) {
            this.searchText = Normalizer.lowerCase(searchText);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return Normalizer.lowerCase(element.text()).contains(this.searchText);
        }
        
        @Override
        public String toString() {
            return String.format(":contains(%s)", this.searchText);
        }
    }
    
    public static final class ContainsData extends Evaluator
    {
        private String searchText;
        
        public ContainsData(final String searchText) {
            this.searchText = Normalizer.lowerCase(searchText);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return Normalizer.lowerCase(element.data()).contains(this.searchText);
        }
        
        @Override
        public String toString() {
            return String.format(":containsData(%s)", this.searchText);
        }
    }
    
    public static final class ContainsOwnText extends Evaluator
    {
        private String searchText;
        
        public ContainsOwnText(final String searchText) {
            this.searchText = Normalizer.lowerCase(searchText);
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            return Normalizer.lowerCase(element.ownText()).contains(this.searchText);
        }
        
        @Override
        public String toString() {
            return String.format(":containsOwn(%s)", this.searchText);
        }
    }
    
    public static final class Matches extends Evaluator
    {
        private Pattern pattern;
        
        public Matches(final Pattern pattern) {
            this.pattern = pattern;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            final Matcher m = this.pattern.matcher(element.text());
            return m.find();
        }
        
        @Override
        public String toString() {
            return String.format(":matches(%s)", this.pattern);
        }
    }
    
    public static final class MatchesOwn extends Evaluator
    {
        private Pattern pattern;
        
        public MatchesOwn(final Pattern pattern) {
            this.pattern = pattern;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            final Matcher m = this.pattern.matcher(element.ownText());
            return m.find();
        }
        
        @Override
        public String toString() {
            return String.format(":matchesOwn(%s)", this.pattern);
        }
    }
}
