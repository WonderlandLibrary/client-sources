package org.jsoup.nodes;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Nullable;
import org.jsoup.helper.ChangeNotifyingArrayList;
import org.jsoup.helper.Consumer;
import org.jsoup.helper.Validate;
import org.jsoup.internal.NonnullByDefault;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.Tag;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;

@NonnullByDefault
public class Element extends Node {
   private static final List<Element> EmptyChildren = Collections.emptyList();
   private static final Pattern ClassSplit = Pattern.compile("\\s+");
   private static final String BaseUriKey = Attributes.internalKey("baseUri");
   private Tag tag;
   @Nullable
   private WeakReference<List<Element>> shadowChildrenRef;
   List<Node> childNodes;
   @Nullable
   Attributes attributes;

   public Element(String tag) {
      this(Tag.valueOf(tag), "", null);
   }

   public Element(Tag tag, @Nullable String baseUri, @Nullable Attributes attributes) {
      Validate.notNull(tag);
      this.childNodes = EmptyNodes;
      this.attributes = attributes;
      this.tag = tag;
      if (baseUri != null) {
         this.setBaseUri(baseUri);
      }
   }

   public Element(Tag tag, @Nullable String baseUri) {
      this(tag, baseUri, null);
   }

   protected boolean hasChildNodes() {
      return this.childNodes != EmptyNodes;
   }

   @Override
   protected List<Node> ensureChildNodes() {
      if (this.childNodes == EmptyNodes) {
         this.childNodes = new Element.NodeList(this, 4);
      }

      return this.childNodes;
   }

   @Override
   protected boolean hasAttributes() {
      return this.attributes != null;
   }

   @Override
   public Attributes attributes() {
      if (this.attributes == null) {
         this.attributes = new Attributes();
      }

      return this.attributes;
   }

   @Override
   public String baseUri() {
      return searchUpForAttribute(this, BaseUriKey);
   }

   private static String searchUpForAttribute(Element start, String key) {
      for (Element el = start; el != null; el = el.parent()) {
         if (el.attributes != null && el.attributes.hasKey(key)) {
            return el.attributes.get(key);
         }
      }

      return "";
   }

   @Override
   protected void doSetBaseUri(String baseUri) {
      this.attributes().put(BaseUriKey, baseUri);
   }

   @Override
   public int childNodeSize() {
      return this.childNodes.size();
   }

   @Override
   public String nodeName() {
      return this.tag.getName();
   }

   public String tagName() {
      return this.tag.getName();
   }

   public String normalName() {
      return this.tag.normalName();
   }

   public Element tagName(String tagName) {
      Validate.notEmptyParam(tagName, "tagName");
      this.tag = Tag.valueOf(tagName, NodeUtils.parser(this).settings());
      return this;
   }

   public Tag tag() {
      return this.tag;
   }

   public boolean isBlock() {
      return this.tag.isBlock();
   }

   public String id() {
      return this.attributes != null ? this.attributes.getIgnoreCase("id") : "";
   }

   public Element id(String id) {
      Validate.notNull(id);
      this.attr("id", id);
      return this;
   }

   public Element attr(String attributeKey, String attributeValue) {
      super.attr(attributeKey, attributeValue);
      return this;
   }

   public Element attr(String attributeKey, boolean attributeValue) {
      this.attributes().put(attributeKey, attributeValue);
      return this;
   }

   public Map<String, String> dataset() {
      return this.attributes().dataset();
   }

   @Nullable
   public final Element parent() {
      return (Element)this.parentNode;
   }

   public Elements parents() {
      Elements parents = new Elements();
      accumulateParents(this, parents);
      return parents;
   }

   private static void accumulateParents(Element el, Elements parents) {
      Element parent = el.parent();
      if (parent != null && !parent.tagName().equals("#root")) {
         parents.add(parent);
         accumulateParents(parent, parents);
      }
   }

   public Element child(int index) {
      return this.childElementsList().get(index);
   }

   public int childrenSize() {
      return this.childElementsList().size();
   }

   public Elements children() {
      return new Elements(this.childElementsList());
   }

   List<Element> childElementsList() {
      if (this.childNodeSize() == 0) {
         return EmptyChildren;
      } else {
         List<Element> children;
         if (this.shadowChildrenRef == null || (children = this.shadowChildrenRef.get()) == null) {
            int size = this.childNodes.size();
            children = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
               Node node = this.childNodes.get(i);
               if (node instanceof Element) {
                  children.add((Element)node);
               }
            }

            this.shadowChildrenRef = new WeakReference<>(children);
         }

         return children;
      }
   }

   @Override
   void nodelistChanged() {
      super.nodelistChanged();
      this.shadowChildrenRef = null;
   }

   public List<TextNode> textNodes() {
      List<TextNode> textNodes = new ArrayList<>();

      for (Node node : this.childNodes) {
         if (node instanceof TextNode) {
            textNodes.add((TextNode)node);
         }
      }

      return Collections.unmodifiableList(textNodes);
   }

   public List<DataNode> dataNodes() {
      List<DataNode> dataNodes = new ArrayList<>();

      for (Node node : this.childNodes) {
         if (node instanceof DataNode) {
            dataNodes.add((DataNode)node);
         }
      }

      return Collections.unmodifiableList(dataNodes);
   }

   public Elements select(String cssQuery) {
      return Selector.select(cssQuery, this);
   }

   public Elements select(Evaluator evaluator) {
      return Selector.select(evaluator, this);
   }

   @Nullable
   public Element selectFirst(String cssQuery) {
      return Selector.selectFirst(cssQuery, this);
   }

   @Nullable
   public Element selectFirst(Evaluator evaluator) {
      return Collector.findFirst(evaluator, this);
   }

   public Element expectFirst(String cssQuery) {
      return (Element)Validate.ensureNotNull(
         Selector.selectFirst(cssQuery, this),
         this.parent() != null ? "No elements matched the query '%s' on element '%s'." : "No elements matched the query '%s' in the document.",
         cssQuery,
         this.tagName()
      );
   }

   public boolean is(String cssQuery) {
      return this.is(QueryParser.parse(cssQuery));
   }

   public boolean is(Evaluator evaluator) {
      return evaluator.matches(this.root(), this);
   }

   @Nullable
   public Element closest(String cssQuery) {
      return this.closest(QueryParser.parse(cssQuery));
   }

   @Nullable
   public Element closest(Evaluator evaluator) {
      Validate.notNull(evaluator);
      Element el = this;
      Element root = this.root();

      while (!evaluator.matches(root, el)) {
         el = el.parent();
         if (el == null) {
            return null;
         }
      }

      return el;
   }

   public Elements selectXpath(String xpath) {
      return new Elements(NodeUtils.selectXpath(xpath, this, Element.class));
   }

   public <T extends Node> List<T> selectXpath(String xpath, Class<T> nodeType) {
      return NodeUtils.selectXpath(xpath, this, nodeType);
   }

   public Element appendChild(Node child) {
      Validate.notNull(child);
      this.reparentChild(child);
      this.ensureChildNodes();
      this.childNodes.add(child);
      child.setSiblingIndex(this.childNodes.size() - 1);
      return this;
   }

   public Element appendChildren(Collection<? extends Node> children) {
      this.insertChildren(-1, children);
      return this;
   }

   public Element appendTo(Element parent) {
      Validate.notNull(parent);
      parent.appendChild(this);
      return this;
   }

   public Element prependChild(Node child) {
      Validate.notNull(child);
      this.addChildren(0, new Node[]{child});
      return this;
   }

   public Element prependChildren(Collection<? extends Node> children) {
      this.insertChildren(0, children);
      return this;
   }

   public Element insertChildren(int index, Collection<? extends Node> children) {
      Validate.notNull(children, "Children collection to be inserted must not be null.");
      int currentSize = this.childNodeSize();
      if (index < 0) {
         index += currentSize + 1;
      }

      Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");
      ArrayList<Node> nodes = new ArrayList<>(children);
      Node[] nodeArray = nodes.toArray(new Node[0]);
      this.addChildren(index, nodeArray);
      return this;
   }

   public Element insertChildren(int index, Node... children) {
      Validate.notNull(children, "Children collection to be inserted must not be null.");
      int currentSize = this.childNodeSize();
      if (index < 0) {
         index += currentSize + 1;
      }

      Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");
      this.addChildren(index, children);
      return this;
   }

   public Element appendElement(String tagName) {
      Element child = new Element(Tag.valueOf(tagName, NodeUtils.parser(this).settings()), this.baseUri());
      this.appendChild(child);
      return child;
   }

   public Element prependElement(String tagName) {
      Element child = new Element(Tag.valueOf(tagName, NodeUtils.parser(this).settings()), this.baseUri());
      this.prependChild(child);
      return child;
   }

   public Element appendText(String text) {
      Validate.notNull(text);
      TextNode node = new TextNode(text);
      this.appendChild(node);
      return this;
   }

   public Element prependText(String text) {
      Validate.notNull(text);
      TextNode node = new TextNode(text);
      this.prependChild(node);
      return this;
   }

   public Element append(String html) {
      Validate.notNull(html);
      List<Node> nodes = NodeUtils.parser(this).parseFragmentInput(html, this, this.baseUri());
      this.addChildren(nodes.toArray(new Node[0]));
      return this;
   }

   public Element prepend(String html) {
      Validate.notNull(html);
      List<Node> nodes = NodeUtils.parser(this).parseFragmentInput(html, this, this.baseUri());
      this.addChildren(0, nodes.toArray(new Node[0]));
      return this;
   }

   public Element before(String html) {
      return (Element)super.before(html);
   }

   public Element before(Node node) {
      return (Element)super.before(node);
   }

   public Element after(String html) {
      return (Element)super.after(html);
   }

   public Element after(Node node) {
      return (Element)super.after(node);
   }

   public Element empty() {
      this.childNodes.clear();
      return this;
   }

   public Element wrap(String html) {
      return (Element)super.wrap(html);
   }

   public String cssSelector() {
      if (this.id().length() > 0) {
         String idSel = "#" + this.id();
         Document doc = this.ownerDocument();
         if (doc == null) {
            return idSel;
         }

         Elements els = doc.select(idSel);
         if (els.size() == 1 && els.get(0) == this) {
            return idSel;
         }
      }

      String tagName = this.tagName().replace(':', '|');
      StringBuilder selector = new StringBuilder(tagName);
      String classes = StringUtil.join(this.classNames(), ".");
      if (classes.length() > 0) {
         selector.append('.').append(classes);
      }

      if (this.parent() != null && !(this.parent() instanceof Document)) {
         selector.insert(0, " > ");
         if (this.parent().select(selector.toString()).size() > 1) {
            selector.append(String.format(":nth-child(%d)", this.elementSiblingIndex() + 1));
         }

         return this.parent().cssSelector() + selector.toString();
      } else {
         return selector.toString();
      }
   }

   public Elements siblingElements() {
      if (this.parentNode == null) {
         return new Elements(0);
      } else {
         List<Element> elements = this.parent().childElementsList();
         Elements siblings = new Elements(elements.size() - 1);

         for (Element el : elements) {
            if (el != this) {
               siblings.add(el);
            }
         }

         return siblings;
      }
   }

   @Nullable
   public Element nextElementSibling() {
      if (this.parentNode == null) {
         return null;
      } else {
         List<Element> siblings = this.parent().childElementsList();
         int index = indexInList(this, siblings);
         return siblings.size() > index + 1 ? siblings.get(index + 1) : null;
      }
   }

   public Elements nextElementSiblings() {
      return this.nextElementSiblings(true);
   }

   @Nullable
   public Element previousElementSibling() {
      if (this.parentNode == null) {
         return null;
      } else {
         List<Element> siblings = this.parent().childElementsList();
         int index = indexInList(this, siblings);
         return index > 0 ? siblings.get(index - 1) : null;
      }
   }

   public Elements previousElementSiblings() {
      return this.nextElementSiblings(false);
   }

   private Elements nextElementSiblings(boolean next) {
      Elements els = new Elements();
      if (this.parentNode == null) {
         return els;
      } else {
         els.add(this);
         return next ? els.nextAll() : els.prevAll();
      }
   }

   public Element firstElementSibling() {
      if (this.parent() != null) {
         List<Element> siblings = this.parent().childElementsList();
         return siblings.size() > 1 ? siblings.get(0) : this;
      } else {
         return this;
      }
   }

   public int elementSiblingIndex() {
      return this.parent() == null ? 0 : indexInList(this, this.parent().childElementsList());
   }

   public Element lastElementSibling() {
      if (this.parent() != null) {
         List<Element> siblings = this.parent().childElementsList();
         return siblings.size() > 1 ? siblings.get(siblings.size() - 1) : this;
      } else {
         return this;
      }
   }

   private static <E extends Element> int indexInList(Element search, List<E> elements) {
      int size = elements.size();

      for (int i = 0; i < size; i++) {
         if (elements.get(i) == search) {
            return i;
         }
      }

      return 0;
   }

   @Nullable
   public Element firstElementChild() {
      int size = this.childNodeSize();
      if (size == 0) {
         return null;
      } else {
         List<Node> children = this.ensureChildNodes();

         for (int i = 0; i < size; i++) {
            Node node = children.get(i);
            if (node instanceof Element) {
               return (Element)node;
            }
         }

         return null;
      }
   }

   @Nullable
   public Element lastElementChild() {
      int size = this.childNodeSize();
      if (size == 0) {
         return null;
      } else {
         List<Node> children = this.ensureChildNodes();

         for (int i = size - 1; i >= 0; i--) {
            Node node = children.get(i);
            if (node instanceof Element) {
               return (Element)node;
            }
         }

         return null;
      }
   }

   public Elements getElementsByTag(String tagName) {
      Validate.notEmpty(tagName);
      tagName = Normalizer.normalize(tagName);
      return Collector.collect(new Evaluator.Tag(tagName), this);
   }

   @Nullable
   public Element getElementById(String id) {
      Validate.notEmpty(id);
      Elements elements = Collector.collect(new Evaluator.Id(id), this);
      return elements.size() > 0 ? elements.get(0) : null;
   }

   public Elements getElementsByClass(String className) {
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

   public Elements getElementsByAttributeValue(String key, String value) {
      return Collector.collect(new Evaluator.AttributeWithValue(key, value), this);
   }

   public Elements getElementsByAttributeValueNot(String key, String value) {
      return Collector.collect(new Evaluator.AttributeWithValueNot(key, value), this);
   }

   public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
      return Collector.collect(new Evaluator.AttributeWithValueStarting(key, valuePrefix), this);
   }

   public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
      return Collector.collect(new Evaluator.AttributeWithValueEnding(key, valueSuffix), this);
   }

   public Elements getElementsByAttributeValueContaining(String key, String match) {
      return Collector.collect(new Evaluator.AttributeWithValueContaining(key, match), this);
   }

   public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
      return Collector.collect(new Evaluator.AttributeWithValueMatching(key, pattern), this);
   }

   public Elements getElementsByAttributeValueMatching(String key, String regex) {
      Pattern pattern;
      try {
         pattern = Pattern.compile(regex);
      } catch (PatternSyntaxException var5) {
         throw new IllegalArgumentException("Pattern syntax error: " + regex, var5);
      }

      return this.getElementsByAttributeValueMatching(key, pattern);
   }

   public Elements getElementsByIndexLessThan(int index) {
      return Collector.collect(new Evaluator.IndexLessThan(index), this);
   }

   public Elements getElementsByIndexGreaterThan(int index) {
      return Collector.collect(new Evaluator.IndexGreaterThan(index), this);
   }

   public Elements getElementsByIndexEquals(int index) {
      return Collector.collect(new Evaluator.IndexEquals(index), this);
   }

   public Elements getElementsContainingText(String searchText) {
      return Collector.collect(new Evaluator.ContainsText(searchText), this);
   }

   public Elements getElementsContainingOwnText(String searchText) {
      return Collector.collect(new Evaluator.ContainsOwnText(searchText), this);
   }

   public Elements getElementsMatchingText(Pattern pattern) {
      return Collector.collect(new Evaluator.Matches(pattern), this);
   }

   public Elements getElementsMatchingText(String regex) {
      Pattern pattern;
      try {
         pattern = Pattern.compile(regex);
      } catch (PatternSyntaxException var4) {
         throw new IllegalArgumentException("Pattern syntax error: " + regex, var4);
      }

      return this.getElementsMatchingText(pattern);
   }

   public Elements getElementsMatchingOwnText(Pattern pattern) {
      return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
   }

   public Elements getElementsMatchingOwnText(String regex) {
      Pattern pattern;
      try {
         pattern = Pattern.compile(regex);
      } catch (PatternSyntaxException var4) {
         throw new IllegalArgumentException("Pattern syntax error: " + regex, var4);
      }

      return this.getElementsMatchingOwnText(pattern);
   }

   public Elements getAllElements() {
      return Collector.collect(new Evaluator.AllElements(), this);
   }

   public String text() {
      final StringBuilder accum = StringUtil.borrowBuilder();
      NodeTraversor.traverse(new NodeVisitor() {
         @Override
         public void head(Node node, int depth) {
            if (node instanceof TextNode) {
               TextNode textNode = (TextNode)node;
               Element.appendNormalisedText(accum, textNode);
            } else if (node instanceof Element) {
               Element element = (Element)node;
               if (accum.length() > 0 && (element.isBlock() || element.tag.normalName().equals("br")) && !TextNode.lastCharIsWhitespace(accum)) {
                  accum.append(' ');
               }
            }
         }

         @Override
         public void tail(Node node, int depth) {
            if (node instanceof Element) {
               Element element = (Element)node;
               if (element.isBlock() && node.nextSibling() instanceof TextNode && !TextNode.lastCharIsWhitespace(accum)) {
                  accum.append(' ');
               }
            }
         }
      }, this);
      return StringUtil.releaseBuilder(accum).trim();
   }

   public String wholeText() {
      StringBuilder accum = StringUtil.borrowBuilder();
      NodeTraversor.traverse((node, depth) -> appendWholeText(node, accum), this);
      return StringUtil.releaseBuilder(accum);
   }

   private static void appendWholeText(Node node, StringBuilder accum) {
      if (node instanceof TextNode) {
         accum.append(((TextNode)node).getWholeText());
      } else if (node instanceof Element) {
         appendNewlineIfBr((Element)node, accum);
      }
   }

   public String wholeOwnText() {
      StringBuilder accum = StringUtil.borrowBuilder();
      int size = this.childNodeSize();

      for (int i = 0; i < size; i++) {
         Node node = this.childNodes.get(i);
         appendWholeText(node, accum);
      }

      return StringUtil.releaseBuilder(accum);
   }

   public String ownText() {
      StringBuilder sb = StringUtil.borrowBuilder();
      this.ownText(sb);
      return StringUtil.releaseBuilder(sb).trim();
   }

   private void ownText(StringBuilder accum) {
      for (int i = 0; i < this.childNodeSize(); i++) {
         Node child = this.childNodes.get(i);
         if (child instanceof TextNode) {
            TextNode textNode = (TextNode)child;
            appendNormalisedText(accum, textNode);
         } else if (child instanceof Element) {
            appendWhitespaceIfBr((Element)child, accum);
         }
      }
   }

   private static void appendNormalisedText(StringBuilder accum, TextNode textNode) {
      String text = textNode.getWholeText();
      if (!preserveWhitespace(textNode.parentNode) && !(textNode instanceof CDataNode)) {
         StringUtil.appendNormalisedWhitespace(accum, text, TextNode.lastCharIsWhitespace(accum));
      } else {
         accum.append(text);
      }
   }

   private static void appendWhitespaceIfBr(Element element, StringBuilder accum) {
      if (element.tag.normalName().equals("br") && !TextNode.lastCharIsWhitespace(accum)) {
         accum.append(" ");
      }
   }

   private static void appendNewlineIfBr(Element element, StringBuilder accum) {
      if (element.tag.normalName().equals("br")) {
         accum.append("\n");
      }
   }

   static boolean preserveWhitespace(@Nullable Node node) {
      if (node instanceof Element) {
         Element el = (Element)node;
         int i = 0;

         do {
            if (el.tag.preserveWhitespace()) {
               return true;
            }

            el = el.parent();
         } while (++i < 6 && el != null);
      }

      return false;
   }

   public Element text(String text) {
      Validate.notNull(text);
      this.empty();
      Document owner = this.ownerDocument();
      if (owner != null && owner.parser().isContentForTagData(this.normalName())) {
         this.appendChild(new DataNode(text));
      } else {
         this.appendChild(new TextNode(text));
      }

      return this;
   }

   public boolean hasText() {
      for (Node child : this.childNodes) {
         if (child instanceof TextNode) {
            TextNode textNode = (TextNode)child;
            if (!textNode.isBlank()) {
               return true;
            }
         } else if (child instanceof Element) {
            Element el = (Element)child;
            if (el.hasText()) {
               return true;
            }
         }
      }

      return false;
   }

   public String data() {
      StringBuilder sb = StringUtil.borrowBuilder();

      for (Node childNode : this.childNodes) {
         if (childNode instanceof DataNode) {
            DataNode data = (DataNode)childNode;
            sb.append(data.getWholeData());
         } else if (childNode instanceof Comment) {
            Comment comment = (Comment)childNode;
            sb.append(comment.getData());
         } else if (childNode instanceof Element) {
            Element element = (Element)childNode;
            String elementData = element.data();
            sb.append(elementData);
         } else if (childNode instanceof CDataNode) {
            CDataNode cDataNode = (CDataNode)childNode;
            sb.append(cDataNode.getWholeText());
         }
      }

      return StringUtil.releaseBuilder(sb);
   }

   public String className() {
      return this.attr("class").trim();
   }

   public Set<String> classNames() {
      String[] names = ClassSplit.split(this.className());
      Set<String> classNames = new LinkedHashSet<>(Arrays.asList(names));
      classNames.remove("");
      return classNames;
   }

   public Element classNames(Set<String> classNames) {
      Validate.notNull(classNames);
      if (classNames.isEmpty()) {
         this.attributes().remove("class");
      } else {
         this.attributes().put("class", StringUtil.join(classNames, " "));
      }

      return this;
   }

   public boolean hasClass(String className) {
      if (this.attributes == null) {
         return false;
      } else {
         String classAttr = this.attributes.getIgnoreCase("class");
         int len = classAttr.length();
         int wantLen = className.length();
         if (len != 0 && len >= wantLen) {
            if (len == wantLen) {
               return className.equalsIgnoreCase(classAttr);
            } else {
               boolean inClass = false;
               int start = 0;

               for (int i = 0; i < len; i++) {
                  if (Character.isWhitespace(classAttr.charAt(i))) {
                     if (inClass) {
                        if (i - start == wantLen && classAttr.regionMatches(true, start, className, 0, wantLen)) {
                           return true;
                        }

                        inClass = false;
                     }
                  } else if (!inClass) {
                     inClass = true;
                     start = i;
                  }
               }

               return inClass && len - start == wantLen ? classAttr.regionMatches(true, start, className, 0, wantLen) : false;
            }
         } else {
            return false;
         }
      }
   }

   public Element addClass(String className) {
      Validate.notNull(className);
      Set<String> classes = this.classNames();
      classes.add(className);
      this.classNames(classes);
      return this;
   }

   public Element removeClass(String className) {
      Validate.notNull(className);
      Set<String> classes = this.classNames();
      classes.remove(className);
      this.classNames(classes);
      return this;
   }

   public Element toggleClass(String className) {
      Validate.notNull(className);
      Set<String> classes = this.classNames();
      if (classes.contains(className)) {
         classes.remove(className);
      } else {
         classes.add(className);
      }

      this.classNames(classes);
      return this;
   }

   public String val() {
      return this.normalName().equals("textarea") ? this.text() : this.attr("value");
   }

   public Element val(String value) {
      if (this.normalName().equals("textarea")) {
         this.text(value);
      } else {
         this.attr("value", value);
      }

      return this;
   }

   public Range endSourceRange() {
      return Range.of(this, false);
   }

   boolean shouldIndent(Document.OutputSettings out) {
      return out.prettyPrint() && this.isFormatAsBlock(out) && !this.isInlineable(out);
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      if (this.shouldIndent(out)) {
         if (accum instanceof StringBuilder) {
            if (((StringBuilder)accum).length() > 0) {
               this.indent(accum, depth, out);
            }
         } else {
            this.indent(accum, depth, out);
         }
      }

      accum.append('<').append(this.tagName());
      if (this.attributes != null) {
         this.attributes.html(accum, out);
      }

      if (this.childNodes.isEmpty() && this.tag.isSelfClosing()) {
         if (out.syntax() == Document.OutputSettings.Syntax.html && this.tag.isEmpty()) {
            accum.append('>');
         } else {
            accum.append(" />");
         }
      } else {
         accum.append('>');
      }
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
         if (out.prettyPrint()
            && !this.childNodes.isEmpty()
            && (
               this.tag.formatAsBlock()
                  || out.outline() && (this.childNodes.size() > 1 || this.childNodes.size() == 1 && this.childNodes.get(0) instanceof Element)
            )) {
            this.indent(accum, depth, out);
         }

         accum.append("</").append(this.tagName()).append('>');
      }
   }

   public String html() {
      StringBuilder accum = StringUtil.borrowBuilder();
      this.html(accum);
      String html = StringUtil.releaseBuilder(accum);
      return NodeUtils.outputSettings(this).prettyPrint() ? html.trim() : html;
   }

   @Override
   public <T extends Appendable> T html(T appendable) {
      int size = this.childNodes.size();

      for (int i = 0; i < size; i++) {
         this.childNodes.get(i).outerHtml(appendable);
      }

      return appendable;
   }

   public Element html(String html) {
      this.empty();
      this.append(html);
      return this;
   }

   public Element clone() {
      return (Element)super.clone();
   }

   public Element shallowClone() {
      return new Element(this.tag, this.baseUri(), this.attributes == null ? null : this.attributes.clone());
   }

   protected Element doClone(@Nullable Node parent) {
      Element clone = (Element)super.doClone(parent);
      clone.attributes = this.attributes != null ? this.attributes.clone() : null;
      clone.childNodes = new Element.NodeList(clone, this.childNodes.size());
      clone.childNodes.addAll(this.childNodes);
      return clone;
   }

   public Element clearAttributes() {
      if (this.attributes != null) {
         super.clearAttributes();
         this.attributes = null;
      }

      return this;
   }

   public Element removeAttr(String attributeKey) {
      return (Element)super.removeAttr(attributeKey);
   }

   public Element root() {
      return (Element)super.root();
   }

   public Element traverse(NodeVisitor nodeVisitor) {
      return (Element)super.traverse(nodeVisitor);
   }

   public Element forEachNode(Consumer<? super Node> action) {
      return (Element)super.forEachNode(action);
   }

   public Element forEach(Consumer<? super Element> action) {
      Validate.notNull(action);
      NodeTraversor.traverse((node, depth) -> {
         if (node instanceof Element) {
            action.accept((Element)node);
         }
      }, this);
      return this;
   }

   public Element filter(NodeFilter nodeFilter) {
      return (Element)super.filter(nodeFilter);
   }

   private boolean isFormatAsBlock(Document.OutputSettings out) {
      return this.tag.formatAsBlock() || this.parent() != null && this.parent().tag().formatAsBlock() || out.outline();
   }

   private boolean isInlineable(Document.OutputSettings out) {
      return this.tag().isInline() && (this.parent() == null || this.parent().isBlock()) && this.previousSibling() != null && !out.outline();
   }

   private static final class NodeList extends ChangeNotifyingArrayList<Node> {
      private final Element owner;

      NodeList(Element owner, int initialCapacity) {
         super(initialCapacity);
         this.owner = owner;
      }

      @Override
      public void onContentsChanged() {
         this.owner.nodelistChanged();
      }
   }
}
