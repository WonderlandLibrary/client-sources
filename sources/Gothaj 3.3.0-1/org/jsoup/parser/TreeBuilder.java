package org.jsoup.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Range;

abstract class TreeBuilder {
   protected Parser parser;
   CharacterReader reader;
   Tokeniser tokeniser;
   protected Document doc;
   protected ArrayList<Element> stack;
   protected String baseUri;
   protected Token currentToken;
   protected ParseSettings settings;
   protected Map<String, Tag> seenTags;
   private Token.StartTag start = new Token.StartTag();
   private Token.EndTag end = new Token.EndTag();
   private boolean trackSourceRange;

   abstract ParseSettings defaultSettings();

   @ParametersAreNonnullByDefault
   protected void initialiseParse(Reader input, String baseUri, Parser parser) {
      Validate.notNullParam(input, "input");
      Validate.notNullParam(baseUri, "baseUri");
      Validate.notNull(parser);
      this.doc = new Document(baseUri);
      this.doc.parser(parser);
      this.parser = parser;
      this.settings = parser.settings();
      this.reader = new CharacterReader(input);
      this.trackSourceRange = parser.isTrackPosition();
      this.reader.trackNewlines(parser.isTrackErrors() || this.trackSourceRange);
      this.currentToken = null;
      this.tokeniser = new Tokeniser(this.reader, parser.getErrors());
      this.stack = new ArrayList<>(32);
      this.seenTags = new HashMap<>();
      this.baseUri = baseUri;
   }

   @ParametersAreNonnullByDefault
   Document parse(Reader input, String baseUri, Parser parser) {
      this.initialiseParse(input, baseUri, parser);
      this.runParser();
      this.reader.close();
      this.reader = null;
      this.tokeniser = null;
      this.stack = null;
      this.seenTags = null;
      return this.doc;
   }

   abstract TreeBuilder newInstance();

   abstract List<Node> parseFragment(String var1, Element var2, String var3, Parser var4);

   protected void runParser() {
      Tokeniser tokeniser = this.tokeniser;
      Token.TokenType eof = Token.TokenType.EOF;

      Token token;
      do {
         token = tokeniser.read();
         this.process(token);
         token.reset();
      } while (token.type != eof);
   }

   protected abstract boolean process(Token var1);

   protected boolean processStartTag(String name) {
      Token.StartTag start = this.start;
      return this.currentToken == start ? this.process(new Token.StartTag().name(name)) : this.process(start.reset().name(name));
   }

   public boolean processStartTag(String name, Attributes attrs) {
      Token.StartTag start = this.start;
      if (this.currentToken == start) {
         return this.process(new Token.StartTag().nameAttr(name, attrs));
      } else {
         start.reset();
         start.nameAttr(name, attrs);
         return this.process(start);
      }
   }

   protected boolean processEndTag(String name) {
      return this.currentToken == this.end ? this.process(new Token.EndTag().name(name)) : this.process(this.end.reset().name(name));
   }

   protected Element currentElement() {
      int size = this.stack.size();
      return (Element)(size > 0 ? this.stack.get(size - 1) : this.doc);
   }

   protected boolean currentElementIs(String normalName) {
      if (this.stack.size() == 0) {
         return false;
      } else {
         Element current = this.currentElement();
         return current != null && current.normalName().equals(normalName);
      }
   }

   protected void error(String msg) {
      this.error(msg, (Object[])null);
   }

   protected void error(String msg, Object... args) {
      ParseErrorList errors = this.parser.getErrors();
      if (errors.canAddError()) {
         errors.add(new ParseError(this.reader, msg, args));
      }
   }

   protected boolean isContentForTagData(String normalName) {
      return false;
   }

   protected Tag tagFor(String tagName, ParseSettings settings) {
      Tag tag = this.seenTags.get(tagName);
      if (tag == null) {
         tag = Tag.valueOf(tagName, settings);
         this.seenTags.put(tagName, tag);
      }

      return tag;
   }

   protected void onNodeInserted(Node node, @Nullable Token token) {
      this.trackNodePosition(node, token, true);
   }

   protected void onNodeClosed(Node node, Token token) {
      this.trackNodePosition(node, token, false);
   }

   private void trackNodePosition(Node node, @Nullable Token token, boolean start) {
      if (this.trackSourceRange && token != null) {
         int startPos = token.startPos();
         if (startPos == -1) {
            return;
         }

         Range.Position startRange = new Range.Position(startPos, this.reader.lineNumber(startPos), this.reader.columnNumber(startPos));
         int endPos = token.endPos();
         Range.Position endRange = new Range.Position(endPos, this.reader.lineNumber(endPos), this.reader.columnNumber(endPos));
         Range range = new Range(startRange, endRange);
         range.track(node, start);
      }
   }
}
