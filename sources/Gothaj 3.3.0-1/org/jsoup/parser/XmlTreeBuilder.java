package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;

public class XmlTreeBuilder extends TreeBuilder {
   private static final int maxQueueDepth = 256;

   @Override
   ParseSettings defaultSettings() {
      return ParseSettings.preserveCase;
   }

   @ParametersAreNonnullByDefault
   @Override
   protected void initialiseParse(Reader input, String baseUri, Parser parser) {
      super.initialiseParse(input, baseUri, parser);
      this.stack.add(this.doc);
      this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml).prettyPrint(false);
   }

   Document parse(Reader input, String baseUri) {
      return this.parse(input, baseUri, new Parser(this));
   }

   Document parse(String input, String baseUri) {
      return this.parse(new StringReader(input), baseUri, new Parser(this));
   }

   XmlTreeBuilder newInstance() {
      return new XmlTreeBuilder();
   }

   @Override
   protected boolean process(Token token) {
      switch (token.type) {
         case StartTag:
            this.insert(token.asStartTag());
            break;
         case EndTag:
            this.popStackToClose(token.asEndTag());
            break;
         case Comment:
            this.insert(token.asComment());
            break;
         case Character:
            this.insert(token.asCharacter());
            break;
         case Doctype:
            this.insert(token.asDoctype());
         case EOF:
            break;
         default:
            Validate.fail("Unexpected token type: " + token.type);
      }

      return true;
   }

   protected void insertNode(Node node) {
      this.currentElement().appendChild(node);
      this.onNodeInserted(node, null);
   }

   protected void insertNode(Node node, Token token) {
      this.currentElement().appendChild(node);
      this.onNodeInserted(node, token);
   }

   Element insert(Token.StartTag startTag) {
      Tag tag = this.tagFor(startTag.name(), this.settings);
      if (startTag.hasAttributes()) {
         startTag.attributes.deduplicate(this.settings);
      }

      Element el = new Element(tag, null, this.settings.normalizeAttributes(startTag.attributes));
      this.insertNode(el, startTag);
      if (startTag.isSelfClosing()) {
         if (!tag.isKnownTag()) {
            tag.setSelfClosing();
         }
      } else {
         this.stack.add(el);
      }

      return el;
   }

   void insert(Token.Comment commentToken) {
      Comment comment = new Comment(commentToken.getData());
      Node insert = comment;
      if (commentToken.bogus && comment.isXmlDeclaration()) {
         XmlDeclaration decl = comment.asXmlDeclaration();
         if (decl != null) {
            insert = decl;
         }
      }

      this.insertNode(insert, commentToken);
   }

   void insert(Token.Character token) {
      String data = token.getData();
      this.insertNode((Node)(token.isCData() ? new CDataNode(data) : new TextNode(data)), token);
   }

   void insert(Token.Doctype d) {
      DocumentType doctypeNode = new DocumentType(this.settings.normalizeTag(d.getName()), d.getPublicIdentifier(), d.getSystemIdentifier());
      doctypeNode.setPubSysKey(d.getPubSysKey());
      this.insertNode(doctypeNode, d);
   }

   protected void popStackToClose(Token.EndTag endTag) {
      String elName = this.settings.normalizeTag(endTag.tagName);
      Element firstFound = null;
      int bottom = this.stack.size() - 1;
      int upper = bottom >= 256 ? bottom - 256 : 0;

      for (int pos = this.stack.size() - 1; pos >= upper; pos--) {
         Element next = this.stack.get(pos);
         if (next.nodeName().equals(elName)) {
            firstFound = next;
            break;
         }
      }

      if (firstFound != null) {
         for (int posx = this.stack.size() - 1; posx >= 0; posx--) {
            Element next = this.stack.get(posx);
            this.stack.remove(posx);
            if (next == firstFound) {
               this.onNodeClosed(next, endTag);
               break;
            }
         }
      }
   }

   List<Node> parseFragment(String inputFragment, String baseUri, Parser parser) {
      this.initialiseParse(new StringReader(inputFragment), baseUri, parser);
      this.runParser();
      return this.doc.childNodes();
   }

   @Override
   List<Node> parseFragment(String inputFragment, Element context, String baseUri, Parser parser) {
      return this.parseFragment(inputFragment, baseUri, parser);
   }
}
