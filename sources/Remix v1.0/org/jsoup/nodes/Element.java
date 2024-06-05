package org.jsoup.nodes;

import java.lang.ref.*;
import org.jsoup.parser.*;
import org.jsoup.helper.*;
import org.jsoup.internal.*;
import java.util.regex.*;
import org.jsoup.select.*;
import java.util.*;
import java.io.*;

public class Element extends Node
{
    private Tag tag;
    private WeakReference<List<Element>> shadowChildrenRef;
    private static final Pattern classSplit;
    
    public Element(final String tag) {
        this(Tag.valueOf(tag), "", new Attributes());
    }
    
    public Element(final Tag tag, final String baseUri, final Attributes attributes) {
        super(baseUri, attributes);
        Validate.notNull(tag);
        this.tag = tag;
    }
    
    public Element(final Tag tag, final String baseUri) {
        this(tag, baseUri, new Attributes());
    }
    
    @Override
    public String nodeName() {
        return this.tag.getName();
    }
    
    public String tagName() {
        return this.tag.getName();
    }
    
    public Element tagName(final String tagName) {
        Validate.notEmpty(tagName, "Tag name must not be empty.");
        this.tag = Tag.valueOf(tagName, ParseSettings.preserveCase);
        return this;
    }
    
    public Tag tag() {
        return this.tag;
    }
    
    public boolean isBlock() {
        return this.tag.isBlock();
    }
    
    public String id() {
        return this.attributes.getIgnoreCase("id");
    }
    
    @Override
    public Element attr(final String attributeKey, final String attributeValue) {
        super.attr(attributeKey, attributeValue);
        return this;
    }
    
    public Element attr(final String attributeKey, final boolean attributeValue) {
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }
    
    public Map<String, String> dataset() {
        return this.attributes.dataset();
    }
    
    @Override
    public final Element parent() {
        return (Element)this.parentNode;
    }
    
    public Elements parents() {
        final Elements parents = new Elements();
        accumulateParents(this, parents);
        return parents;
    }
    
    private static void accumulateParents(final Element el, final Elements parents) {
        final Element parent = el.parent();
        if (parent != null && !parent.tagName().equals("#root")) {
            parents.add(parent);
            accumulateParents(parent, parents);
        }
    }
    
    public Element child(final int index) {
        return this.childElementsList().get(index);
    }
    
    public Elements children() {
        return new Elements(this.childElementsList());
    }
    
    private List<Element> childElementsList() {
        List<Element> children;
        if (this.shadowChildrenRef == null || (children = this.shadowChildrenRef.get()) == null) {
            final int size = this.childNodes.size();
            children = new ArrayList<Element>(size);
            for (int i = 0; i < size; ++i) {
                final Node node = this.childNodes.get(i);
                if (node instanceof Element) {
                    children.add((Element)node);
                }
            }
            this.shadowChildrenRef = new WeakReference<List<Element>>(children);
        }
        return children;
    }
    
    @Override
    void nodelistChanged() {
        super.nodelistChanged();
        this.shadowChildrenRef = null;
    }
    
    public List<TextNode> textNodes() {
        final List<TextNode> textNodes = new ArrayList<TextNode>();
        for (final Node node : this.childNodes) {
            if (node instanceof TextNode) {
                textNodes.add((TextNode)node);
            }
        }
        return Collections.unmodifiableList((List<? extends TextNode>)textNodes);
    }
    
    public List<DataNode> dataNodes() {
        final List<DataNode> dataNodes = new ArrayList<DataNode>();
        for (final Node node : this.childNodes) {
            if (node instanceof DataNode) {
                dataNodes.add((DataNode)node);
            }
        }
        return Collections.unmodifiableList((List<? extends DataNode>)dataNodes);
    }
    
    public Elements select(final String cssQuery) {
        return Selector.select(cssQuery, this);
    }
    
    public boolean is(final String cssQuery) {
        return this.is(QueryParser.parse(cssQuery));
    }
    
    public boolean is(final Evaluator evaluator) {
        return evaluator.matches((Element)this.root(), this);
    }
    
    public Element appendChild(final Node child) {
        Validate.notNull(child);
        this.reparentChild(child);
        this.ensureChildNodes();
        this.childNodes.add(child);
        child.setSiblingIndex(this.childNodes.size() - 1);
        return this;
    }
    
    public Element prependChild(final Node child) {
        Validate.notNull(child);
        this.addChildren(0, child);
        return this;
    }
    
    public Element insertChildren(int index, final Collection<? extends Node> children) {
        Validate.notNull(children, "Children collection to be inserted must not be null.");
        final int currentSize = this.childNodeSize();
        if (index < 0) {
            index += currentSize + 1;
        }
        Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");
        final ArrayList<Node> nodes = new ArrayList<Node>(children);
        final Node[] nodeArray = nodes.toArray(new Node[nodes.size()]);
        this.addChildren(index, nodeArray);
        return this;
    }
    
    public Element insertChildren(int index, final Node... children) {
        Validate.notNull(children, "Children collection to be inserted must not be null.");
        final int currentSize = this.childNodeSize();
        if (index < 0) {
            index += currentSize + 1;
        }
        Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");
        this.addChildren(index, children);
        return this;
    }
    
    public Element appendElement(final String tagName) {
        final Element child = new Element(Tag.valueOf(tagName), this.baseUri());
        this.appendChild(child);
        return child;
    }
    
    public Element prependElement(final String tagName) {
        final Element child = new Element(Tag.valueOf(tagName), this.baseUri());
        this.prependChild(child);
        return child;
    }
    
    public Element appendText(final String text) {
        Validate.notNull(text);
        final TextNode node = new TextNode(text, this.baseUri());
        this.appendChild(node);
        return this;
    }
    
    public Element prependText(final String text) {
        Validate.notNull(text);
        final TextNode node = new TextNode(text, this.baseUri());
        this.prependChild(node);
        return this;
    }
    
    public Element append(final String html) {
        Validate.notNull(html);
        final List<Node> nodes = Parser.parseFragment(html, this, this.baseUri());
        this.addChildren((Node[])nodes.toArray(new Node[nodes.size()]));
        return this;
    }
    
    public Element prepend(final String html) {
        Validate.notNull(html);
        final List<Node> nodes = Parser.parseFragment(html, this, this.baseUri());
        this.addChildren(0, (Node[])nodes.toArray(new Node[nodes.size()]));
        return this;
    }
    
    @Override
    public Element before(final String html) {
        return (Element)super.before(html);
    }
    
    @Override
    public Element before(final Node node) {
        return (Element)super.before(node);
    }
    
    @Override
    public Element after(final String html) {
        return (Element)super.after(html);
    }
    
    @Override
    public Element after(final Node node) {
        return (Element)super.after(node);
    }
    
    public Element empty() {
        this.childNodes.clear();
        return this;
    }
    
    @Override
    public Element wrap(final String html) {
        return (Element)super.wrap(html);
    }
    
    public String cssSelector() {
        if (this.id().length() > 0) {
            return "#" + this.id();
        }
        final String tagName = this.tagName().replace(':', '|');
        final StringBuilder selector = new StringBuilder(tagName);
        final String classes = StringUtil.join(this.classNames(), ".");
        if (classes.length() > 0) {
            selector.append('.').append(classes);
        }
        if (this.parent() == null || this.parent() instanceof Document) {
            return selector.toString();
        }
        selector.insert(0, " > ");
        if (this.parent().select(selector.toString()).size() > 1) {
            selector.append(String.format(":nth-child(%d)", this.elementSiblingIndex() + 1));
        }
        return this.parent().cssSelector() + selector.toString();
    }
    
    public Elements siblingElements() {
        if (this.parentNode == null) {
            return new Elements(0);
        }
        final List<Element> elements = this.parent().childElementsList();
        final Elements siblings = new Elements(elements.size() - 1);
        for (final Element el : elements) {
            if (el != this) {
                siblings.add(el);
            }
        }
        return siblings;
    }
    
    public Element nextElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        final List<Element> siblings = this.parent().childElementsList();
        final Integer index = indexInList(this, siblings);
        Validate.notNull(index);
        if (siblings.size() > index + 1) {
            return siblings.get(index + 1);
        }
        return null;
    }
    
    public Element previousElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        final List<Element> siblings = this.parent().childElementsList();
        final Integer index = indexInList(this, siblings);
        Validate.notNull(index);
        if (index > 0) {
            return siblings.get(index - 1);
        }
        return null;
    }
    
    public Element firstElementSibling() {
        final List<Element> siblings = this.parent().childElementsList();
        return (siblings.size() > 1) ? siblings.get(0) : null;
    }
    
    public int elementSiblingIndex() {
        if (this.parent() == null) {
            return 0;
        }
        return indexInList(this, this.parent().childElementsList());
    }
    
    public Element lastElementSibling() {
        final List<Element> siblings = this.parent().childElementsList();
        return (siblings.size() > 1) ? siblings.get(siblings.size() - 1) : null;
    }
    
    private static <E extends Element> int indexInList(final Element search, final List<E> elements) {
        for (int i = 0; i < elements.size(); ++i) {
            if (elements.get(i) == search) {
                return i;
            }
        }
        return 0;
    }
    
    public Elements getElementsByTag(String tagName) {
        Validate.notEmpty(tagName);
        tagName = Normalizer.normalize(tagName);
        return Collector.collect(new Evaluator.Tag(tagName), this);
    }
    
    public Element getElementById(final String id) {
        Validate.notEmpty(id);
        final Elements elements = Collector.collect(new Evaluator.Id(id), this);
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }
    
    public Elements getElementsByClass(final String className) {
        Validate.notEmpty(className);
        return Collector.collect(new Evaluator.Class(className), this);
    }
    
    public Elements getElementsByAttribute(String key) {
        Validate.notEmpty(key);
        key = key.trim();
        return Collector.collect(new Evaluator.Attribute(key), this);
    }
    
    public Elements getElementsByAttributeStarting(String keyPrefix) {
        Validate.notEmpty(keyPrefix);
        keyPrefix = keyPrefix.trim();
        return Collector.collect(new Evaluator.AttributeStarting(keyPrefix), this);
    }
    
    public Elements getElementsByAttributeValue(final String key, final String value) {
        return Collector.collect(new Evaluator.AttributeWithValue(key, value), this);
    }
    
    public Elements getElementsByAttributeValueNot(final String key, final String value) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(key, value), this);
    }
    
    public Elements getElementsByAttributeValueStarting(final String key, final String valuePrefix) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(key, valuePrefix), this);
    }
    
    public Elements getElementsByAttributeValueEnding(final String key, final String valueSuffix) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(key, valueSuffix), this);
    }
    
    public Elements getElementsByAttributeValueContaining(final String key, final String match) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(key, match), this);
    }
    
    public Elements getElementsByAttributeValueMatching(final String key, final Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(key, pattern), this);
    }
    
    public Elements getElementsByAttributeValueMatching(final String key, final String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }
        catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return this.getElementsByAttributeValueMatching(key, pattern);
    }
    
    public Elements getElementsByIndexLessThan(final int index) {
        return Collector.collect(new Evaluator.IndexLessThan(index), this);
    }
    
    public Elements getElementsByIndexGreaterThan(final int index) {
        return Collector.collect(new Evaluator.IndexGreaterThan(index), this);
    }
    
    public Elements getElementsByIndexEquals(final int index) {
        return Collector.collect(new Evaluator.IndexEquals(index), this);
    }
    
    public Elements getElementsContainingText(final String searchText) {
        return Collector.collect(new Evaluator.ContainsText(searchText), this);
    }
    
    public Elements getElementsContainingOwnText(final String searchText) {
        return Collector.collect(new Evaluator.ContainsOwnText(searchText), this);
    }
    
    public Elements getElementsMatchingText(final Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }
    
    public Elements getElementsMatchingText(final String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }
        catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return this.getElementsMatchingText(pattern);
    }
    
    public Elements getElementsMatchingOwnText(final Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }
    
    public Elements getElementsMatchingOwnText(final String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }
        catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return this.getElementsMatchingOwnText(pattern);
    }
    
    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }
    
    public String text() {
        final StringBuilder accum = new StringBuilder();
        new NodeTraversor(new NodeVisitor() {
            public void head(final Node node, final int depth) {
                if (node instanceof TextNode) {
                    final TextNode textNode = (TextNode)node;
                    appendNormalisedText(accum, textNode);
                }
                else if (node instanceof Element) {
                    final Element element = (Element)node;
                    if (accum.length() > 0 && (element.isBlock() || element.tag.getName().equals("br")) && !TextNode.lastCharIsWhitespace(accum)) {
                        accum.append(" ");
                    }
                }
            }
            
            public void tail(final Node node, final int depth) {
            }
        }).traverse(this);
        return accum.toString().trim();
    }
    
    public String ownText() {
        final StringBuilder sb = new StringBuilder();
        this.ownText(sb);
        return sb.toString().trim();
    }
    
    private void ownText(final StringBuilder accum) {
        for (final Node child : this.childNodes) {
            if (child instanceof TextNode) {
                final TextNode textNode = (TextNode)child;
                appendNormalisedText(accum, textNode);
            }
            else {
                if (!(child instanceof Element)) {
                    continue;
                }
                appendWhitespaceIfBr((Element)child, accum);
            }
        }
    }
    
    private static void appendNormalisedText(final StringBuilder accum, final TextNode textNode) {
        final String text = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode)) {
            accum.append(text);
        }
        else {
            StringUtil.appendNormalisedWhitespace(accum, text, TextNode.lastCharIsWhitespace(accum));
        }
    }
    
    private static void appendWhitespaceIfBr(final Element element, final StringBuilder accum) {
        if (element.tag.getName().equals("br") && !TextNode.lastCharIsWhitespace(accum)) {
            accum.append(" ");
        }
    }
    
    static boolean preserveWhitespace(final Node node) {
        if (node != null && node instanceof Element) {
            final Element element = (Element)node;
            return element.tag.preserveWhitespace() || (element.parent() != null && element.parent().tag.preserveWhitespace());
        }
        return false;
    }
    
    public Element text(final String text) {
        Validate.notNull(text);
        this.empty();
        final TextNode textNode = new TextNode(text, this.baseUri);
        this.appendChild(textNode);
        return this;
    }
    
    public boolean hasText() {
        for (final Node child : this.childNodes) {
            if (child instanceof TextNode) {
                final TextNode textNode = (TextNode)child;
                if (!textNode.isBlank()) {
                    return true;
                }
                continue;
            }
            else {
                if (!(child instanceof Element)) {
                    continue;
                }
                final Element el = (Element)child;
                if (el.hasText()) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public String data() {
        final StringBuilder sb = new StringBuilder();
        for (final Node childNode : this.childNodes) {
            if (childNode instanceof DataNode) {
                final DataNode data = (DataNode)childNode;
                sb.append(data.getWholeData());
            }
            else if (childNode instanceof Comment) {
                final Comment comment = (Comment)childNode;
                sb.append(comment.getData());
            }
            else {
                if (!(childNode instanceof Element)) {
                    continue;
                }
                final Element element = (Element)childNode;
                final String elementData = element.data();
                sb.append(elementData);
            }
        }
        return sb.toString();
    }
    
    public String className() {
        return this.attr("class").trim();
    }
    
    public Set<String> classNames() {
        final String[] names = Element.classSplit.split(this.className());
        final Set<String> classNames = new LinkedHashSet<String>(Arrays.asList(names));
        classNames.remove("");
        return classNames;
    }
    
    public Element classNames(final Set<String> classNames) {
        Validate.notNull(classNames);
        this.attributes.put("class", StringUtil.join(classNames, " "));
        return this;
    }
    
    public boolean hasClass(final String className) {
        final String classAttr = this.attributes.getIgnoreCase("class");
        final int len = classAttr.length();
        final int wantLen = className.length();
        if (len == 0 || len < wantLen) {
            return false;
        }
        if (len == wantLen) {
            return className.equalsIgnoreCase(classAttr);
        }
        boolean inClass = false;
        int start = 0;
        for (int i = 0; i < len; ++i) {
            if (Character.isWhitespace(classAttr.charAt(i))) {
                if (inClass) {
                    if (i - start == wantLen && classAttr.regionMatches(true, start, className, 0, wantLen)) {
                        return true;
                    }
                    inClass = false;
                }
            }
            else if (!inClass) {
                inClass = true;
                start = i;
            }
        }
        return inClass && len - start == wantLen && classAttr.regionMatches(true, start, className, 0, wantLen);
    }
    
    public Element addClass(final String className) {
        Validate.notNull(className);
        final Set<String> classes = this.classNames();
        classes.add(className);
        this.classNames(classes);
        return this;
    }
    
    public Element removeClass(final String className) {
        Validate.notNull(className);
        final Set<String> classes = this.classNames();
        classes.remove(className);
        this.classNames(classes);
        return this;
    }
    
    public Element toggleClass(final String className) {
        Validate.notNull(className);
        final Set<String> classes = this.classNames();
        if (classes.contains(className)) {
            classes.remove(className);
        }
        else {
            classes.add(className);
        }
        this.classNames(classes);
        return this;
    }
    
    public String val() {
        if (this.tagName().equals("textarea")) {
            return this.text();
        }
        return this.attr("value");
    }
    
    public Element val(final String value) {
        if (this.tagName().equals("textarea")) {
            this.text(value);
        }
        else {
            this.attr("value", value);
        }
        return this;
    }
    
    @Override
    void outerHtmlHead(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        if (out.prettyPrint() && (this.tag.formatAsBlock() || (this.parent() != null && this.parent().tag().formatAsBlock()) || out.outline())) {
            if (accum instanceof StringBuilder) {
                if (((StringBuilder)accum).length() > 0) {
                    this.indent(accum, depth, out);
                }
            }
            else {
                this.indent(accum, depth, out);
            }
        }
        accum.append("<").append(this.tagName());
        this.attributes.html(accum, out);
        if (this.childNodes.isEmpty() && this.tag.isSelfClosing()) {
            if (out.syntax() == Document.OutputSettings.Syntax.html && this.tag.isEmpty()) {
                accum.append('>');
            }
            else {
                accum.append(" />");
            }
        }
        else {
            accum.append(">");
        }
    }
    
    @Override
    void outerHtmlTail(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            if (out.prettyPrint() && !this.childNodes.isEmpty() && (this.tag.formatAsBlock() || (out.outline() && (this.childNodes.size() > 1 || (this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode)))))) {
                this.indent(accum, depth, out);
            }
            accum.append("</").append(this.tagName()).append(">");
        }
    }
    
    public String html() {
        final StringBuilder accum = new StringBuilder();
        this.html(accum);
        return this.getOutputSettings().prettyPrint() ? accum.toString().trim() : accum.toString();
    }
    
    private void html(final StringBuilder accum) {
        for (final Node node : this.childNodes) {
            node.outerHtml(accum);
        }
    }
    
    @Override
    public <T extends Appendable> T html(final T appendable) {
        for (final Node node : this.childNodes) {
            node.outerHtml(appendable);
        }
        return appendable;
    }
    
    public Element html(final String html) {
        this.empty();
        this.append(html);
        return this;
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
    
    @Override
    public Element clone() {
        return (Element)super.clone();
    }
    
    static {
        classSplit = Pattern.compile("\\s+");
    }
}
