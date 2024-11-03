package org.jsoup.nodes;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

public class Document extends Element {
   @Nullable
   private Connection connection;
   private Document.OutputSettings outputSettings = new Document.OutputSettings();
   private Parser parser;
   private Document.QuirksMode quirksMode = Document.QuirksMode.noQuirks;
   private final String location;
   private boolean updateMetaCharset = false;
   private static final Evaluator titleEval = new Evaluator.Tag("title");

   public Document(String baseUri) {
      super(Tag.valueOf("#root", ParseSettings.htmlDefault), baseUri);
      this.location = baseUri;
      this.parser = Parser.htmlParser();
   }

   public static Document createShell(String baseUri) {
      Validate.notNull(baseUri);
      Document doc = new Document(baseUri);
      doc.parser = doc.parser();
      Element html = doc.appendElement("html");
      html.appendElement("head");
      html.appendElement("body");
      return doc;
   }

   public String location() {
      return this.location;
   }

   public Connection connection() {
      return this.connection == null ? Jsoup.newSession() : this.connection;
   }

   @Nullable
   public DocumentType documentType() {
      for (Node node : this.childNodes) {
         if (node instanceof DocumentType) {
            return (DocumentType)node;
         }

         if (!(node instanceof LeafNode)) {
            break;
         }
      }

      return null;
   }

   private Element htmlEl() {
      for (Element el : this.childElementsList()) {
         if (el.normalName().equals("html")) {
            return el;
         }
      }

      return this.appendElement("html");
   }

   public Element head() {
      Element html = this.htmlEl();

      for (Element el : html.childElementsList()) {
         if (el.normalName().equals("head")) {
            return el;
         }
      }

      return html.prependElement("head");
   }

   public Element body() {
      Element html = this.htmlEl();

      for (Element el : html.childElementsList()) {
         if ("body".equals(el.normalName()) || "frameset".equals(el.normalName())) {
            return el;
         }
      }

      return html.appendElement("body");
   }

   public String title() {
      Element titleEl = this.head().selectFirst(titleEval);
      return titleEl != null ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : "";
   }

   public void title(String title) {
      Validate.notNull(title);
      Element titleEl = this.head().selectFirst(titleEval);
      if (titleEl == null) {
         titleEl = this.head().appendElement("title");
      }

      titleEl.text(title);
   }

   public Element createElement(String tagName) {
      return new Element(Tag.valueOf(tagName, ParseSettings.preserveCase), this.baseUri());
   }

   public Document normalise() {
      Element htmlEl = this.htmlEl();
      Element head = this.head();
      this.body();
      this.normaliseTextNodes(head);
      this.normaliseTextNodes(htmlEl);
      this.normaliseTextNodes(this);
      this.normaliseStructure("head", htmlEl);
      this.normaliseStructure("body", htmlEl);
      this.ensureMetaCharsetElement();
      return this;
   }

   private void normaliseTextNodes(Element element) {
      List<Node> toMove = new ArrayList<>();

      for (Node node : element.childNodes) {
         if (node instanceof TextNode) {
            TextNode tn = (TextNode)node;
            if (!tn.isBlank()) {
               toMove.add(tn);
            }
         }
      }

      for (int i = toMove.size() - 1; i >= 0; i--) {
         Node nodex = toMove.get(i);
         element.removeChild(nodex);
         this.body().prependChild(new TextNode(" "));
         this.body().prependChild(nodex);
      }
   }

   private void normaliseStructure(String tag, Element htmlEl) {
      Elements elements = this.getElementsByTag(tag);
      Element master = elements.first();
      if (elements.size() > 1) {
         List<Node> toMove = new ArrayList<>();

         for (int i = 1; i < elements.size(); i++) {
            Node dupe = elements.get(i);
            toMove.addAll(dupe.ensureChildNodes());
            dupe.remove();
         }

         for (Node dupe : toMove) {
            master.appendChild(dupe);
         }
      }

      if (master.parent() != null && !master.parent().equals(htmlEl)) {
         htmlEl.appendChild(master);
      }
   }

   @Override
   public String outerHtml() {
      return super.html();
   }

   @Override
   public Element text(String text) {
      this.body().text(text);
      return this;
   }

   @Override
   public String nodeName() {
      return "#document";
   }

   public void charset(Charset charset) {
      this.updateMetaCharsetElement(true);
      this.outputSettings.charset(charset);
      this.ensureMetaCharsetElement();
   }

   public Charset charset() {
      return this.outputSettings.charset();
   }

   public void updateMetaCharsetElement(boolean update) {
      this.updateMetaCharset = update;
   }

   public boolean updateMetaCharsetElement() {
      return this.updateMetaCharset;
   }

   public Document clone() {
      Document clone = (Document)super.clone();
      clone.outputSettings = this.outputSettings.clone();
      return clone;
   }

   public Document shallowClone() {
      Document clone = new Document(this.baseUri());
      if (this.attributes != null) {
         clone.attributes = this.attributes.clone();
      }

      clone.outputSettings = this.outputSettings.clone();
      return clone;
   }

   private void ensureMetaCharsetElement() {
      if (this.updateMetaCharset) {
         Document.OutputSettings.Syntax syntax = this.outputSettings().syntax();
         if (syntax == Document.OutputSettings.Syntax.html) {
            Element metaCharset = this.selectFirst("meta[charset]");
            if (metaCharset != null) {
               metaCharset.attr("charset", this.charset().displayName());
            } else {
               this.head().appendElement("meta").attr("charset", this.charset().displayName());
            }

            this.select("meta[name=charset]").remove();
         } else if (syntax == Document.OutputSettings.Syntax.xml) {
            Node node = this.ensureChildNodes().get(0);
            if (node instanceof XmlDeclaration) {
               XmlDeclaration decl = (XmlDeclaration)node;
               if (decl.name().equals("xml")) {
                  decl.attr("encoding", this.charset().displayName());
                  if (decl.hasAttr("version")) {
                     decl.attr("version", "1.0");
                  }
               } else {
                  decl = new XmlDeclaration("xml", false);
                  decl.attr("version", "1.0");
                  decl.attr("encoding", this.charset().displayName());
                  this.prependChild(decl);
               }
            } else {
               XmlDeclaration decl = new XmlDeclaration("xml", false);
               decl.attr("version", "1.0");
               decl.attr("encoding", this.charset().displayName());
               this.prependChild(decl);
            }
         }
      }
   }

   public Document.OutputSettings outputSettings() {
      return this.outputSettings;
   }

   public Document outputSettings(Document.OutputSettings outputSettings) {
      Validate.notNull(outputSettings);
      this.outputSettings = outputSettings;
      return this;
   }

   public Document.QuirksMode quirksMode() {
      return this.quirksMode;
   }

   public Document quirksMode(Document.QuirksMode quirksMode) {
      this.quirksMode = quirksMode;
      return this;
   }

   public Parser parser() {
      return this.parser;
   }

   public Document parser(Parser parser) {
      this.parser = parser;
      return this;
   }

   public Document connection(Connection connection) {
      Validate.notNull(connection);
      this.connection = connection;
      return this;
   }

   public static class OutputSettings implements Cloneable {
      private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
      private Charset charset = DataUtil.UTF_8;
      private final ThreadLocal<CharsetEncoder> encoderThreadLocal = new ThreadLocal<>();
      @Nullable
      Entities.CoreCharset coreCharset;
      private boolean prettyPrint = true;
      private boolean outline = false;
      private int indentAmount = 1;
      private int maxPaddingWidth = 30;
      private Document.OutputSettings.Syntax syntax = Document.OutputSettings.Syntax.html;

      public Entities.EscapeMode escapeMode() {
         return this.escapeMode;
      }

      public Document.OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
         this.escapeMode = escapeMode;
         return this;
      }

      public Charset charset() {
         return this.charset;
      }

      public Document.OutputSettings charset(Charset charset) {
         this.charset = charset;
         return this;
      }

      public Document.OutputSettings charset(String charset) {
         this.charset(Charset.forName(charset));
         return this;
      }

      CharsetEncoder prepareEncoder() {
         CharsetEncoder encoder = this.charset.newEncoder();
         this.encoderThreadLocal.set(encoder);
         this.coreCharset = Entities.CoreCharset.byName(encoder.charset().name());
         return encoder;
      }

      CharsetEncoder encoder() {
         CharsetEncoder encoder = this.encoderThreadLocal.get();
         return encoder != null ? encoder : this.prepareEncoder();
      }

      public Document.OutputSettings.Syntax syntax() {
         return this.syntax;
      }

      public Document.OutputSettings syntax(Document.OutputSettings.Syntax syntax) {
         this.syntax = syntax;
         return this;
      }

      public boolean prettyPrint() {
         return this.prettyPrint;
      }

      public Document.OutputSettings prettyPrint(boolean pretty) {
         this.prettyPrint = pretty;
         return this;
      }

      public boolean outline() {
         return this.outline;
      }

      public Document.OutputSettings outline(boolean outlineMode) {
         this.outline = outlineMode;
         return this;
      }

      public int indentAmount() {
         return this.indentAmount;
      }

      public Document.OutputSettings indentAmount(int indentAmount) {
         Validate.isTrue(indentAmount >= 0);
         this.indentAmount = indentAmount;
         return this;
      }

      public int maxPaddingWidth() {
         return this.maxPaddingWidth;
      }

      public Document.OutputSettings maxPaddingWidth(int maxPaddingWidth) {
         Validate.isTrue(maxPaddingWidth >= -1);
         this.maxPaddingWidth = maxPaddingWidth;
         return this;
      }

      public Document.OutputSettings clone() {
         Document.OutputSettings clone;
         try {
            clone = (Document.OutputSettings)super.clone();
         } catch (CloneNotSupportedException var3) {
            throw new RuntimeException(var3);
         }

         clone.charset(this.charset.name());
         clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
         return clone;
      }

      public static enum Syntax {
         html,
         xml;
      }
   }

   public static enum QuirksMode {
      noQuirks,
      quirks,
      limitedQuirks;
   }
}
