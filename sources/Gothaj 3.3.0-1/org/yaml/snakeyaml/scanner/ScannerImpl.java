package org.yaml.snakeyaml.scanner;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.BlockEndToken;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.BlockMappingStartToken;
import org.yaml.snakeyaml.tokens.BlockSequenceStartToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.DocumentEndToken;
import org.yaml.snakeyaml.tokens.DocumentStartToken;
import org.yaml.snakeyaml.tokens.FlowEntryToken;
import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
import org.yaml.snakeyaml.tokens.FlowMappingStartToken;
import org.yaml.snakeyaml.tokens.FlowSequenceEndToken;
import org.yaml.snakeyaml.tokens.FlowSequenceStartToken;
import org.yaml.snakeyaml.tokens.KeyToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.TagTuple;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.ValueToken;
import org.yaml.snakeyaml.util.ArrayStack;
import org.yaml.snakeyaml.util.UriEncoder;

public final class ScannerImpl implements Scanner {
   private static final Pattern NOT_HEXA = Pattern.compile("[^0-9A-Fa-f]");
   public static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap<>();
   public static final Map<Character, Integer> ESCAPE_CODES = new HashMap<>();
   private final StreamReader reader;
   private boolean done = false;
   private int flowLevel = 0;
   private final List<Token> tokens;
   private Token lastToken;
   private int tokensTaken = 0;
   private int indent = -1;
   private final ArrayStack<Integer> indents;
   private final boolean parseComments;
   private final LoaderOptions loaderOptions;
   private boolean allowSimpleKey = true;
   private final Map<Integer, SimpleKey> possibleSimpleKeys;

   public ScannerImpl(StreamReader reader, LoaderOptions options) {
      if (options == null) {
         throw new NullPointerException("LoaderOptions must be provided.");
      } else {
         this.parseComments = options.isProcessComments();
         this.reader = reader;
         this.tokens = new ArrayList<>(100);
         this.indents = new ArrayStack<>(10);
         this.possibleSimpleKeys = new LinkedHashMap<>();
         this.loaderOptions = options;
         this.fetchStreamStart();
      }
   }

   @Override
   public boolean checkToken(Token.ID... choices) {
      while (this.needMoreTokens()) {
         this.fetchMoreTokens();
      }

      if (!this.tokens.isEmpty()) {
         if (choices.length == 0) {
            return true;
         }

         Token.ID first = this.tokens.get(0).getTokenId();

         for (int i = 0; i < choices.length; i++) {
            if (first == choices[i]) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public Token peekToken() {
      while (this.needMoreTokens()) {
         this.fetchMoreTokens();
      }

      return this.tokens.get(0);
   }

   @Override
   public Token getToken() {
      this.tokensTaken++;
      return this.tokens.remove(0);
   }

   private void addToken(Token token) {
      this.lastToken = token;
      this.tokens.add(token);
   }

   private void addToken(int index, Token token) {
      if (index == this.tokens.size()) {
         this.lastToken = token;
      }

      this.tokens.add(index, token);
   }

   private void addAllTokens(List<Token> tokens) {
      this.lastToken = tokens.get(tokens.size() - 1);
      this.tokens.addAll(tokens);
   }

   private boolean needMoreTokens() {
      if (this.done) {
         return false;
      } else if (this.tokens.isEmpty()) {
         return true;
      } else {
         this.stalePossibleSimpleKeys();
         return this.nextPossibleSimpleKey() == this.tokensTaken;
      }
   }

   private void fetchMoreTokens() {
      if (this.reader.getDocumentIndex() > this.loaderOptions.getCodePointLimit()) {
         throw new YAMLException("The incoming YAML document exceeds the limit: " + this.loaderOptions.getCodePointLimit() + " code points.");
      } else {
         this.scanToNextToken();
         this.stalePossibleSimpleKeys();
         this.unwindIndent(this.reader.getColumn());
         int c = this.reader.peek();
         switch (c) {
            case 0:
               this.fetchStreamEnd();
               return;
            case 33:
               this.fetchTag();
               return;
            case 34:
               this.fetchDouble();
               return;
            case 37:
               if (this.checkDirective()) {
                  this.fetchDirective();
                  return;
               }
               break;
            case 38:
               this.fetchAnchor();
               return;
            case 39:
               this.fetchSingle();
               return;
            case 42:
               this.fetchAlias();
               return;
            case 44:
               this.fetchFlowEntry();
               return;
            case 45:
               if (this.checkDocumentStart()) {
                  this.fetchDocumentStart();
                  return;
               }

               if (this.checkBlockEntry()) {
                  this.fetchBlockEntry();
                  return;
               }
               break;
            case 46:
               if (this.checkDocumentEnd()) {
                  this.fetchDocumentEnd();
                  return;
               }
               break;
            case 58:
               if (this.checkValue()) {
                  this.fetchValue();
                  return;
               }
               break;
            case 62:
               if (this.flowLevel == 0) {
                  this.fetchFolded();
                  return;
               }
               break;
            case 63:
               if (this.checkKey()) {
                  this.fetchKey();
                  return;
               }
               break;
            case 91:
               this.fetchFlowSequenceStart();
               return;
            case 93:
               this.fetchFlowSequenceEnd();
               return;
            case 123:
               this.fetchFlowMappingStart();
               return;
            case 124:
               if (this.flowLevel == 0) {
                  this.fetchLiteral();
                  return;
               }
               break;
            case 125:
               this.fetchFlowMappingEnd();
               return;
         }

         if (this.checkPlain()) {
            this.fetchPlain();
         } else {
            String chRepresentation = this.escapeChar(String.valueOf(Character.toChars(c)));
            if (c == 9) {
               chRepresentation = chRepresentation + "(TAB)";
            }

            String text = String.format("found character '%s' that cannot start any token. (Do not use %s for indentation)", chRepresentation, chRepresentation);
            throw new ScannerException("while scanning for the next token", null, text, this.reader.getMark());
         }
      }
   }

   private String escapeChar(String chRepresentation) {
      for (Character s : ESCAPE_REPLACEMENTS.keySet()) {
         String v = ESCAPE_REPLACEMENTS.get(s);
         if (v.equals(chRepresentation)) {
            return "\\" + s;
         }
      }

      return chRepresentation;
   }

   private int nextPossibleSimpleKey() {
      return !this.possibleSimpleKeys.isEmpty() ? this.possibleSimpleKeys.values().iterator().next().getTokenNumber() : -1;
   }

   private void stalePossibleSimpleKeys() {
      if (!this.possibleSimpleKeys.isEmpty()) {
         Iterator<SimpleKey> iterator = this.possibleSimpleKeys.values().iterator();

         while (iterator.hasNext()) {
            SimpleKey key = iterator.next();
            if (key.getLine() != this.reader.getLine() || this.reader.getIndex() - key.getIndex() > 1024) {
               if (key.isRequired()) {
                  throw new ScannerException("while scanning a simple key", key.getMark(), "could not find expected ':'", this.reader.getMark());
               }

               iterator.remove();
            }
         }
      }
   }

   private void savePossibleSimpleKey() {
      boolean required = this.flowLevel == 0 && this.indent == this.reader.getColumn();
      if (!this.allowSimpleKey && required) {
         throw new YAMLException("A simple key is required only if it is the first token in the current line");
      } else {
         if (this.allowSimpleKey) {
            this.removePossibleSimpleKey();
            int tokenNumber = this.tokensTaken + this.tokens.size();
            SimpleKey key = new SimpleKey(tokenNumber, required, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark());
            this.possibleSimpleKeys.put(this.flowLevel, key);
         }
      }
   }

   private void removePossibleSimpleKey() {
      SimpleKey key = this.possibleSimpleKeys.remove(this.flowLevel);
      if (key != null && key.isRequired()) {
         throw new ScannerException("while scanning a simple key", key.getMark(), "could not find expected ':'", this.reader.getMark());
      }
   }

   private void unwindIndent(int col) {
      if (this.flowLevel == 0) {
         while (this.indent > col) {
            Mark mark = this.reader.getMark();
            this.indent = this.indents.pop();
            this.addToken(new BlockEndToken(mark, mark));
         }
      }
   }

   private boolean addIndent(int column) {
      if (this.indent < column) {
         this.indents.push(this.indent);
         this.indent = column;
         return true;
      } else {
         return false;
      }
   }

   private void fetchStreamStart() {
      Mark mark = this.reader.getMark();
      Token token = new StreamStartToken(mark, mark);
      this.addToken(token);
   }

   private void fetchStreamEnd() {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      this.possibleSimpleKeys.clear();
      Mark mark = this.reader.getMark();
      Token token = new StreamEndToken(mark, mark);
      this.addToken(token);
      this.done = true;
   }

   private void fetchDirective() {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      List<Token> tok = this.scanDirective();
      this.addAllTokens(tok);
   }

   private void fetchDocumentStart() {
      this.fetchDocumentIndicator(true);
   }

   private void fetchDocumentEnd() {
      this.fetchDocumentIndicator(false);
   }

   private void fetchDocumentIndicator(boolean isDocumentStart) {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      Mark startMark = this.reader.getMark();
      this.reader.forward(3);
      Mark endMark = this.reader.getMark();
      Token token;
      if (isDocumentStart) {
         token = new DocumentStartToken(startMark, endMark);
      } else {
         token = new DocumentEndToken(startMark, endMark);
      }

      this.addToken(token);
   }

   private void fetchFlowSequenceStart() {
      this.fetchFlowCollectionStart(false);
   }

   private void fetchFlowMappingStart() {
      this.fetchFlowCollectionStart(true);
   }

   private void fetchFlowCollectionStart(boolean isMappingStart) {
      this.savePossibleSimpleKey();
      this.flowLevel++;
      this.allowSimpleKey = true;
      Mark startMark = this.reader.getMark();
      this.reader.forward(1);
      Mark endMark = this.reader.getMark();
      Token token;
      if (isMappingStart) {
         token = new FlowMappingStartToken(startMark, endMark);
      } else {
         token = new FlowSequenceStartToken(startMark, endMark);
      }

      this.addToken(token);
   }

   private void fetchFlowSequenceEnd() {
      this.fetchFlowCollectionEnd(false);
   }

   private void fetchFlowMappingEnd() {
      this.fetchFlowCollectionEnd(true);
   }

   private void fetchFlowCollectionEnd(boolean isMappingEnd) {
      this.removePossibleSimpleKey();
      this.flowLevel--;
      this.allowSimpleKey = false;
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      Mark endMark = this.reader.getMark();
      Token token;
      if (isMappingEnd) {
         token = new FlowMappingEndToken(startMark, endMark);
      } else {
         token = new FlowSequenceEndToken(startMark, endMark);
      }

      this.addToken(token);
   }

   private void fetchFlowEntry() {
      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      Mark endMark = this.reader.getMark();
      Token token = new FlowEntryToken(startMark, endMark);
      this.addToken(token);
   }

   private void fetchBlockEntry() {
      if (this.flowLevel == 0) {
         if (!this.allowSimpleKey) {
            throw new ScannerException(null, null, "sequence entries are not allowed here", this.reader.getMark());
         }

         if (this.addIndent(this.reader.getColumn())) {
            Mark mark = this.reader.getMark();
            this.addToken(new BlockSequenceStartToken(mark, mark));
         }
      }

      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      Mark endMark = this.reader.getMark();
      Token token = new BlockEntryToken(startMark, endMark);
      this.addToken(token);
   }

   private void fetchKey() {
      if (this.flowLevel == 0) {
         if (!this.allowSimpleKey) {
            throw new ScannerException(null, null, "mapping keys are not allowed here", this.reader.getMark());
         }

         if (this.addIndent(this.reader.getColumn())) {
            Mark mark = this.reader.getMark();
            this.addToken(new BlockMappingStartToken(mark, mark));
         }
      }

      this.allowSimpleKey = this.flowLevel == 0;
      this.removePossibleSimpleKey();
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      Mark endMark = this.reader.getMark();
      Token token = new KeyToken(startMark, endMark);
      this.addToken(token);
   }

   private void fetchValue() {
      SimpleKey key = this.possibleSimpleKeys.remove(this.flowLevel);
      if (key != null) {
         this.addToken(key.getTokenNumber() - this.tokensTaken, new KeyToken(key.getMark(), key.getMark()));
         if (this.flowLevel == 0 && this.addIndent(key.getColumn())) {
            this.addToken(key.getTokenNumber() - this.tokensTaken, new BlockMappingStartToken(key.getMark(), key.getMark()));
         }

         this.allowSimpleKey = false;
      } else {
         if (this.flowLevel == 0 && !this.allowSimpleKey) {
            throw new ScannerException(null, null, "mapping values are not allowed here", this.reader.getMark());
         }

         if (this.flowLevel == 0 && this.addIndent(this.reader.getColumn())) {
            Mark mark = this.reader.getMark();
            this.addToken(new BlockMappingStartToken(mark, mark));
         }

         this.allowSimpleKey = this.flowLevel == 0;
         this.removePossibleSimpleKey();
      }

      Mark startMark = this.reader.getMark();
      this.reader.forward();
      Mark endMark = this.reader.getMark();
      Token token = new ValueToken(startMark, endMark);
      this.addToken(token);
   }

   private void fetchAlias() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token tok = this.scanAnchor(false);
      this.addToken(tok);
   }

   private void fetchAnchor() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token tok = this.scanAnchor(true);
      this.addToken(tok);
   }

   private void fetchTag() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token tok = this.scanTag();
      this.addToken(tok);
   }

   private void fetchLiteral() {
      this.fetchBlockScalar('|');
   }

   private void fetchFolded() {
      this.fetchBlockScalar('>');
   }

   private void fetchBlockScalar(char style) {
      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      List<Token> tok = this.scanBlockScalar(style);
      this.addAllTokens(tok);
   }

   private void fetchSingle() {
      this.fetchFlowScalar('\'');
   }

   private void fetchDouble() {
      this.fetchFlowScalar('"');
   }

   private void fetchFlowScalar(char style) {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token tok = this.scanFlowScalar(style);
      this.addToken(tok);
   }

   private void fetchPlain() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token tok = this.scanPlain();
      this.addToken(tok);
   }

   private boolean checkDirective() {
      return this.reader.getColumn() == 0;
   }

   private boolean checkDocumentStart() {
      return this.reader.getColumn() != 0 ? false : "---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
   }

   private boolean checkDocumentEnd() {
      return this.reader.getColumn() != 0 ? false : "...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
   }

   private boolean checkBlockEntry() {
      return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
   }

   private boolean checkKey() {
      return this.flowLevel != 0 ? true : Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
   }

   private boolean checkValue() {
      return this.flowLevel != 0 ? true : Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
   }

   private boolean checkPlain() {
      int c = this.reader.peek();
      return Constant.NULL_BL_T_LINEBR.hasNo(c, "-?:,[]{}#&*!|>'\"%@`")
         || Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(1)) && (c == 45 || this.flowLevel == 0 && "?:".indexOf(c) != -1);
   }

   private void scanToNextToken() {
      if (this.reader.getIndex() == 0 && this.reader.peek() == 65279) {
         this.reader.forward();
      }

      boolean found = false;
      int inlineStartColumn = -1;

      while (!found) {
         Mark startMark = this.reader.getMark();
         int columnBeforeComment = this.reader.getColumn();
         boolean commentSeen = false;
         int ff = 0;

         while (this.reader.peek(ff) == 32) {
            ff++;
         }

         if (ff > 0) {
            this.reader.forward(ff);
         }

         if (this.reader.peek() == 35) {
            commentSeen = true;
            CommentType type;
            if (columnBeforeComment == 0 || this.lastToken != null && this.lastToken.getTokenId() == Token.ID.BlockEntry) {
               if (inlineStartColumn == this.reader.getColumn()) {
                  type = CommentType.IN_LINE;
               } else {
                  inlineStartColumn = -1;
                  type = CommentType.BLOCK;
               }
            } else {
               type = CommentType.IN_LINE;
               inlineStartColumn = this.reader.getColumn();
            }

            CommentToken token = this.scanComment(type);
            if (this.parseComments) {
               this.addToken(token);
            }
         }

         String breaks = this.scanLineBreak();
         if (breaks.length() != 0) {
            if (this.parseComments && !commentSeen && columnBeforeComment == 0) {
               Mark endMark = this.reader.getMark();
               this.addToken(new CommentToken(CommentType.BLANK_LINE, breaks, startMark, endMark));
            }

            if (this.flowLevel == 0) {
               this.allowSimpleKey = true;
            }
         } else {
            found = true;
         }
      }
   }

   private CommentToken scanComment(CommentType type) {
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      int length = 0;

      while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(length))) {
         length++;
      }

      String value = this.reader.prefixForward(length);
      Mark endMark = this.reader.getMark();
      return new CommentToken(type, value, startMark, endMark);
   }

   private List<Token> scanDirective() {
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      String name = this.scanDirectiveName(startMark);
      List<?> value = null;
      Mark endMark;
      if ("YAML".equals(name)) {
         value = this.scanYamlDirectiveValue(startMark);
         endMark = this.reader.getMark();
      } else if ("TAG".equals(name)) {
         value = this.scanTagDirectiveValue(startMark);
         endMark = this.reader.getMark();
      } else {
         endMark = this.reader.getMark();
         int ff = 0;

         while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(ff))) {
            ff++;
         }

         if (ff > 0) {
            this.reader.forward(ff);
         }
      }

      CommentToken commentToken = this.scanDirectiveIgnoredLine(startMark);
      DirectiveToken token = new DirectiveToken<>(name, value, startMark, endMark);
      return this.makeTokenList(token, commentToken);
   }

   private String scanDirectiveName(Mark startMark) {
      int length = 0;
      int c = this.reader.peek(length);

      while (Constant.ALPHA.has(c)) {
         c = this.reader.peek(++length);
      }

      if (length == 0) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException(
            "while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + s + "(" + c + ")", this.reader.getMark()
         );
      } else {
         String value = this.reader.prefixForward(length);
         c = this.reader.peek();
         if (Constant.NULL_BL_LINEBR.hasNo(c)) {
            String s = String.valueOf(Character.toChars(c));
            throw new ScannerException(
               "while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + s + "(" + c + ")", this.reader.getMark()
            );
         } else {
            return value;
         }
      }
   }

   private List<Integer> scanYamlDirectiveValue(Mark startMark) {
      while (this.reader.peek() == 32) {
         this.reader.forward();
      }

      Integer major = this.scanYamlDirectiveNumber(startMark);
      int c = this.reader.peek();
      if (c != 46) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a directive", startMark, "expected a digit or '.', but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         this.reader.forward();
         Integer minor = this.scanYamlDirectiveNumber(startMark);
         c = this.reader.peek();
         if (Constant.NULL_BL_LINEBR.hasNo(c)) {
            String s = String.valueOf(Character.toChars(c));
            throw new ScannerException(
               "while scanning a directive", startMark, "expected a digit or ' ', but found " + s + "(" + c + ")", this.reader.getMark()
            );
         } else {
            List<Integer> result = new ArrayList<>(2);
            result.add(major);
            result.add(minor);
            return result;
         }
      }
   }

   private Integer scanYamlDirectiveNumber(Mark startMark) {
      int c = this.reader.peek();
      if (!Character.isDigit(c)) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a directive", startMark, "expected a digit, but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         int length = 0;

         while (Character.isDigit(this.reader.peek(length))) {
            length++;
         }

         String number = this.reader.prefixForward(length);
         if (length > 3) {
            throw new ScannerException(
               "while scanning a YAML directive", startMark, "found a number which cannot represent a valid version: " + number, this.reader.getMark()
            );
         } else {
            return Integer.parseInt(number);
         }
      }
   }

   private List<String> scanTagDirectiveValue(Mark startMark) {
      while (this.reader.peek() == 32) {
         this.reader.forward();
      }

      String handle = this.scanTagDirectiveHandle(startMark);

      while (this.reader.peek() == 32) {
         this.reader.forward();
      }

      String prefix = this.scanTagDirectivePrefix(startMark);
      List<String> result = new ArrayList<>(2);
      result.add(handle);
      result.add(prefix);
      return result;
   }

   private String scanTagDirectiveHandle(Mark startMark) {
      String value = this.scanTagHandle("directive", startMark);
      int c = this.reader.peek();
      if (c != 32) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         return value;
      }
   }

   private String scanTagDirectivePrefix(Mark startMark) {
      String value = this.scanTagUri("directive", startMark);
      int c = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(c)) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         return value;
      }
   }

   private CommentToken scanDirectiveIgnoredLine(Mark startMark) {
      while (this.reader.peek() == 32) {
         this.reader.forward();
      }

      CommentToken commentToken = null;
      if (this.reader.peek() == 35) {
         CommentToken comment = this.scanComment(CommentType.IN_LINE);
         if (this.parseComments) {
            commentToken = comment;
         }
      }

      int c = this.reader.peek();
      String lineBreak = this.scanLineBreak();
      if (lineBreak.length() == 0 && c != 0) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException(
            "while scanning a directive", startMark, "expected a comment or a line break, but found " + s + "(" + c + ")", this.reader.getMark()
         );
      } else {
         return commentToken;
      }
   }

   private Token scanAnchor(boolean isAnchor) {
      Mark startMark = this.reader.getMark();
      int indicator = this.reader.peek();
      String name = indicator == 42 ? "alias" : "anchor";
      this.reader.forward();
      int length = 0;
      int c = this.reader.peek(length);

      while (Constant.NULL_BL_T_LINEBR.hasNo(c, ":,[]{}/.*&")) {
         c = this.reader.peek(++length);
      }

      if (length == 0) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning an " + name, startMark, "unexpected character found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         String value = this.reader.prefixForward(length);
         c = this.reader.peek();
         if (Constant.NULL_BL_T_LINEBR.hasNo(c, "?:,]}%@`")) {
            String s = String.valueOf(Character.toChars(c));
            throw new ScannerException("while scanning an " + name, startMark, "unexpected character found " + s + "(" + c + ")", this.reader.getMark());
         } else {
            Mark endMark = this.reader.getMark();
            Token tok;
            if (isAnchor) {
               tok = new AnchorToken(value, startMark, endMark);
            } else {
               tok = new AliasToken(value, startMark, endMark);
            }

            return tok;
         }
      }
   }

   private Token scanTag() {
      Mark startMark = this.reader.getMark();
      int c = this.reader.peek(1);
      String handle = null;
      String suffix = null;
      if (c == 60) {
         this.reader.forward(2);
         suffix = this.scanTagUri("tag", startMark);
         c = this.reader.peek();
         if (c != 62) {
            String s = String.valueOf(Character.toChars(c));
            throw new ScannerException("while scanning a tag", startMark, "expected '>', but found '" + s + "' (" + c + ")", this.reader.getMark());
         }

         this.reader.forward();
      } else if (Constant.NULL_BL_T_LINEBR.has(c)) {
         suffix = "!";
         this.reader.forward();
      } else {
         int length = 1;

         boolean useHandle;
         for (useHandle = false; Constant.NULL_BL_LINEBR.hasNo(c); c = this.reader.peek(++length)) {
            if (c == 33) {
               useHandle = true;
               break;
            }
         }

         if (useHandle) {
            handle = this.scanTagHandle("tag", startMark);
         } else {
            handle = "!";
            this.reader.forward();
         }

         suffix = this.scanTagUri("tag", startMark);
      }

      c = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(c)) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a tag", startMark, "expected ' ', but found '" + s + "' (" + c + ")", this.reader.getMark());
      } else {
         TagTuple value = new TagTuple(handle, suffix);
         Mark endMark = this.reader.getMark();
         return new TagToken(value, startMark, endMark);
      }
   }

   private List<Token> scanBlockScalar(char style) {
      boolean folded = style == '>';
      StringBuilder chunks = new StringBuilder();
      Mark startMark = this.reader.getMark();
      this.reader.forward();
      ScannerImpl.Chomping chompi = this.scanBlockScalarIndicators(startMark);
      int increment = chompi.getIncrement();
      CommentToken commentToken = this.scanBlockScalarIgnoredLine(startMark);
      int minIndent = this.indent + 1;
      if (minIndent < 1) {
         minIndent = 1;
      }

      String breaks;
      int indent;
      Mark endMark;
      if (increment == -1) {
         Object[] brme = this.scanBlockScalarIndentation();
         breaks = (String)brme[0];
         int maxIndent = (Integer)brme[1];
         endMark = (Mark)brme[2];
         indent = Math.max(minIndent, maxIndent);
      } else {
         indent = minIndent + increment - 1;
         Object[] brme = this.scanBlockScalarBreaks(indent);
         breaks = (String)brme[0];
         endMark = (Mark)brme[1];
      }

      String lineBreak = "";

      while (this.reader.getColumn() == indent && this.reader.peek() != 0) {
         chunks.append(breaks);
         boolean leadingNonSpace = " \t".indexOf(this.reader.peek()) == -1;
         int length = 0;

         while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(length))) {
            length++;
         }

         chunks.append(this.reader.prefixForward(length));
         lineBreak = this.scanLineBreak();
         Object[] brme = this.scanBlockScalarBreaks(indent);
         breaks = (String)brme[0];
         endMark = (Mark)brme[1];
         if (this.reader.getColumn() != indent || this.reader.peek() == 0) {
            break;
         }

         if (!folded || !"\n".equals(lineBreak) || !leadingNonSpace || " \t".indexOf(this.reader.peek()) != -1) {
            chunks.append(lineBreak);
         } else if (breaks.length() == 0) {
            chunks.append(" ");
         }
      }

      if (chompi.chompTailIsNotFalse()) {
         chunks.append(lineBreak);
      }

      if (chompi.chompTailIsTrue()) {
         chunks.append(breaks);
      }

      ScalarToken scalarToken = new ScalarToken(chunks.toString(), false, startMark, endMark, DumperOptions.ScalarStyle.createStyle(style));
      return this.makeTokenList(commentToken, scalarToken);
   }

   private ScannerImpl.Chomping scanBlockScalarIndicators(Mark startMark) {
      Boolean chomping = null;
      int increment = -1;
      int c = this.reader.peek();
      if (c != 45 && c != 43) {
         if (Character.isDigit(c)) {
            String s = String.valueOf(Character.toChars(c));
            increment = Integer.parseInt(s);
            if (increment == 0) {
               throw new ScannerException(
                  "while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark()
               );
            }

            this.reader.forward();
            c = this.reader.peek();
            if (c == 45 || c == 43) {
               if (c == 43) {
                  chomping = Boolean.TRUE;
               } else {
                  chomping = Boolean.FALSE;
               }

               this.reader.forward();
            }
         }
      } else {
         if (c == 43) {
            chomping = Boolean.TRUE;
         } else {
            chomping = Boolean.FALSE;
         }

         this.reader.forward();
         c = this.reader.peek();
         if (Character.isDigit(c)) {
            String sx = String.valueOf(Character.toChars(c));
            increment = Integer.parseInt(sx);
            if (increment == 0) {
               throw new ScannerException(
                  "while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark()
               );
            }

            this.reader.forward();
         }
      }

      c = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(c)) {
         String sx = String.valueOf(Character.toChars(c));
         throw new ScannerException(
            "while scanning a block scalar", startMark, "expected chomping or indentation indicators, but found " + sx + "(" + c + ")", this.reader.getMark()
         );
      } else {
         return new ScannerImpl.Chomping(chomping, increment);
      }
   }

   private CommentToken scanBlockScalarIgnoredLine(Mark startMark) {
      while (this.reader.peek() == 32) {
         this.reader.forward();
      }

      CommentToken commentToken = null;
      if (this.reader.peek() == 35) {
         commentToken = this.scanComment(CommentType.IN_LINE);
      }

      int c = this.reader.peek();
      String lineBreak = this.scanLineBreak();
      if (lineBreak.length() == 0 && c != 0) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException(
            "while scanning a block scalar", startMark, "expected a comment or a line break, but found " + s + "(" + c + ")", this.reader.getMark()
         );
      } else {
         return commentToken;
      }
   }

   private Object[] scanBlockScalarIndentation() {
      StringBuilder chunks = new StringBuilder();
      int maxIndent = 0;
      Mark endMark = this.reader.getMark();

      while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
         if (this.reader.peek() != 32) {
            chunks.append(this.scanLineBreak());
            endMark = this.reader.getMark();
         } else {
            this.reader.forward();
            if (this.reader.getColumn() > maxIndent) {
               maxIndent = this.reader.getColumn();
            }
         }
      }

      return new Object[]{chunks.toString(), maxIndent, endMark};
   }

   private Object[] scanBlockScalarBreaks(int indent) {
      StringBuilder chunks = new StringBuilder();
      Mark endMark = this.reader.getMark();

      for (int col = this.reader.getColumn(); col < indent && this.reader.peek() == 32; col++) {
         this.reader.forward();
      }

      String lineBreak = null;

      while ((lineBreak = this.scanLineBreak()).length() != 0) {
         chunks.append(lineBreak);
         endMark = this.reader.getMark();

         for (int var6 = this.reader.getColumn(); var6 < indent && this.reader.peek() == 32; var6++) {
            this.reader.forward();
         }
      }

      return new Object[]{chunks.toString(), endMark};
   }

   private Token scanFlowScalar(char style) {
      boolean _double = style == '"';
      StringBuilder chunks = new StringBuilder();
      Mark startMark = this.reader.getMark();
      int quote = this.reader.peek();
      this.reader.forward();
      chunks.append(this.scanFlowScalarNonSpaces(_double, startMark));

      while (this.reader.peek() != quote) {
         chunks.append(this.scanFlowScalarSpaces(startMark));
         chunks.append(this.scanFlowScalarNonSpaces(_double, startMark));
      }

      this.reader.forward();
      Mark endMark = this.reader.getMark();
      return new ScalarToken(chunks.toString(), false, startMark, endMark, DumperOptions.ScalarStyle.createStyle(style));
   }

   private String scanFlowScalarNonSpaces(boolean doubleQuoted, Mark startMark) {
      StringBuilder chunks = new StringBuilder();

      while (true) {
         int length = 0;

         while (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(length), "'\"\\")) {
            length++;
         }

         if (length != 0) {
            chunks.append(this.reader.prefixForward(length));
         }

         int c = this.reader.peek();
         if (!doubleQuoted && c == 39 && this.reader.peek(1) == 39) {
            chunks.append("'");
            this.reader.forward(2);
         } else if ((!doubleQuoted || c != 39) && (doubleQuoted || "\"\\".indexOf(c) == -1)) {
            if (!doubleQuoted || c != 92) {
               return chunks.toString();
            }

            this.reader.forward();
            c = this.reader.peek();
            if (!Character.isSupplementaryCodePoint(c) && ESCAPE_REPLACEMENTS.containsKey((char)c)) {
               chunks.append(ESCAPE_REPLACEMENTS.get((char)c));
               this.reader.forward();
            } else if (!Character.isSupplementaryCodePoint(c) && ESCAPE_CODES.containsKey((char)c)) {
               length = ESCAPE_CODES.get((char)c);
               this.reader.forward();
               String hex = this.reader.prefix(length);
               if (NOT_HEXA.matcher(hex).find()) {
                  throw new ScannerException(
                     "while scanning a double-quoted scalar",
                     startMark,
                     "expected escape sequence of " + length + " hexadecimal numbers, but found: " + hex,
                     this.reader.getMark()
                  );
               }

               int decimal = Integer.parseInt(hex, 16);

               try {
                  String unicode = new String(Character.toChars(decimal));
                  chunks.append(unicode);
                  this.reader.forward(length);
               } catch (IllegalArgumentException var9) {
                  throw new ScannerException("while scanning a double-quoted scalar", startMark, "found unknown escape character " + hex, this.reader.getMark());
               }
            } else {
               if (this.scanLineBreak().length() == 0) {
                  String s = String.valueOf(Character.toChars(c));
                  throw new ScannerException(
                     "while scanning a double-quoted scalar", startMark, "found unknown escape character " + s + "(" + c + ")", this.reader.getMark()
                  );
               }

               chunks.append(this.scanFlowScalarBreaks(startMark));
            }
         } else {
            chunks.appendCodePoint(c);
            this.reader.forward();
         }
      }
   }

   private String scanFlowScalarSpaces(Mark startMark) {
      StringBuilder chunks = new StringBuilder();
      int length = 0;

      while (" \t".indexOf(this.reader.peek(length)) != -1) {
         length++;
      }

      String whitespaces = this.reader.prefixForward(length);
      int c = this.reader.peek();
      if (c == 0) {
         throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected end of stream", this.reader.getMark());
      } else {
         String lineBreak = this.scanLineBreak();
         if (lineBreak.length() != 0) {
            String breaks = this.scanFlowScalarBreaks(startMark);
            if (!"\n".equals(lineBreak)) {
               chunks.append(lineBreak);
            } else if (breaks.length() == 0) {
               chunks.append(" ");
            }

            chunks.append(breaks);
         } else {
            chunks.append(whitespaces);
         }

         return chunks.toString();
      }
   }

   private String scanFlowScalarBreaks(Mark startMark) {
      StringBuilder chunks = new StringBuilder();

      while (true) {
         String prefix = this.reader.prefix(3);
         if (("---".equals(prefix) || "...".equals(prefix)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
            throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected document separator", this.reader.getMark());
         }

         while (" \t".indexOf(this.reader.peek()) != -1) {
            this.reader.forward();
         }

         String lineBreak = this.scanLineBreak();
         if (lineBreak.length() == 0) {
            return chunks.toString();
         }

         chunks.append(lineBreak);
      }
   }

   private Token scanPlain() {
      StringBuilder chunks = new StringBuilder();
      Mark startMark = this.reader.getMark();
      Mark endMark = startMark;
      int indent = this.indent + 1;
      String spaces = "";

      while (true) {
         int length = 0;
         if (this.reader.peek() == 35) {
            return new ScalarToken(chunks.toString(), startMark, endMark, true);
         }

         while (true) {
            int c = this.reader.peek(length);
            if (Constant.NULL_BL_T_LINEBR.has(c)
               || c == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(length + 1), this.flowLevel != 0 ? ",[]{}" : "")
               || this.flowLevel != 0 && ",?[]{}".indexOf(c) != -1) {
               if (length == 0) {
                  return new ScalarToken(chunks.toString(), startMark, endMark, true);
               }

               this.allowSimpleKey = false;
               chunks.append(spaces);
               chunks.append(this.reader.prefixForward(length));
               endMark = this.reader.getMark();
               spaces = this.scanPlainSpaces();
               if (spaces.length() == 0 || this.reader.peek() == 35 || this.flowLevel == 0 && this.reader.getColumn() < indent) {
                  return new ScalarToken(chunks.toString(), startMark, endMark, true);
               }
               break;
            }

            length++;
         }
      }
   }

   private boolean atEndOfPlain() {
      int wsLength = 0;
      int wsColumn = this.reader.getColumn();

      int c;
      while ((c = this.reader.peek(wsLength)) != 0 && Constant.NULL_BL_T_LINEBR.has(c)) {
         wsLength++;
         if (!Constant.LINEBR.has(c) && (c != 13 || this.reader.peek(wsLength + 1) != 10) && c != 65279) {
            wsColumn++;
         } else {
            wsColumn = 0;
         }
      }

      if (this.reader.peek(wsLength) != 35 && this.reader.peek(wsLength + 1) != 0 && (this.flowLevel != 0 || wsColumn >= this.indent)) {
         if (this.flowLevel == 0) {
            for (int extra = 1; (c = this.reader.peek(wsLength + extra)) != 0 && !Constant.NULL_BL_T_LINEBR.has(c); extra++) {
               if (c == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(wsLength + extra + 1))) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private String scanPlainSpaces() {
      int length = 0;

      while (this.reader.peek(length) == 32 || this.reader.peek(length) == 9) {
         length++;
      }

      String whitespaces = this.reader.prefixForward(length);
      String lineBreak = this.scanLineBreak();
      if (lineBreak.length() == 0) {
         return whitespaces;
      } else {
         this.allowSimpleKey = true;
         String prefix = this.reader.prefix(3);
         if ("---".equals(prefix) || "...".equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
            return "";
         } else if (this.parseComments && this.atEndOfPlain()) {
            return "";
         } else {
            StringBuilder breaks = new StringBuilder();

            do {
               while (this.reader.peek() == 32) {
                  this.reader.forward();
               }

               String lb = this.scanLineBreak();
               if (lb.length() == 0) {
                  if (!"\n".equals(lineBreak)) {
                     return lineBreak + breaks;
                  }

                  if (breaks.length() == 0) {
                     return " ";
                  }

                  return breaks.toString();
               }

               breaks.append(lb);
               prefix = this.reader.prefix(3);
            } while (!"---".equals(prefix) && (!"...".equals(prefix) || !Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))));

            return "";
         }
      }
   }

   private String scanTagHandle(String name, Mark startMark) {
      int c = this.reader.peek();
      if (c != 33) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         int length = 1;
         c = this.reader.peek(length);
         if (c != 32) {
            while (Constant.ALPHA.has(c)) {
               c = this.reader.peek(++length);
            }

            if (c != 33) {
               this.reader.forward(length);
               String s = String.valueOf(Character.toChars(c));
               throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + s + "(" + c + ")", this.reader.getMark());
            }

            length++;
         }

         return this.reader.prefixForward(length);
      }
   }

   private String scanTagUri(String name, Mark startMark) {
      StringBuilder chunks = new StringBuilder();
      int length = 0;

      int c;
      for (c = this.reader.peek(length); Constant.URI_CHARS.has(c); c = this.reader.peek(length)) {
         if (c == 37) {
            chunks.append(this.reader.prefixForward(length));
            length = 0;
            chunks.append(this.scanUriEscapes(name, startMark));
         } else {
            length++;
         }
      }

      if (length != 0) {
         chunks.append(this.reader.prefixForward(length));
      }

      if (chunks.length() == 0) {
         String s = String.valueOf(Character.toChars(c));
         throw new ScannerException("while scanning a " + name, startMark, "expected URI, but found " + s + "(" + c + ")", this.reader.getMark());
      } else {
         return chunks.toString();
      }
   }

   private String scanUriEscapes(String name, Mark startMark) {
      int length = 1;

      while (this.reader.peek(length * 3) == 37) {
         length++;
      }

      Mark beginningMark = this.reader.getMark();

      ByteBuffer buff;
      for (buff = ByteBuffer.allocate(length); this.reader.peek() == 37; this.reader.forward(2)) {
         this.reader.forward();

         try {
            byte code = (byte)Integer.parseInt(this.reader.prefix(2), 16);
            buff.put(code);
         } catch (NumberFormatException var12) {
            int c1 = this.reader.peek();
            String s1 = String.valueOf(Character.toChars(c1));
            int c2 = this.reader.peek(1);
            String s2 = String.valueOf(Character.toChars(c2));
            throw new ScannerException(
               "while scanning a " + name,
               startMark,
               "expected URI escape sequence of 2 hexadecimal numbers, but found " + s1 + "(" + c1 + ") and " + s2 + "(" + c2 + ")",
               this.reader.getMark()
            );
         }
      }

      ((Buffer)buff).flip();

      try {
         return UriEncoder.decode(buff);
      } catch (CharacterCodingException var11) {
         throw new ScannerException("while scanning a " + name, startMark, "expected URI in UTF-8: " + var11.getMessage(), beginningMark);
      }
   }

   private String scanLineBreak() {
      int c = this.reader.peek();
      if (c != 13 && c != 10 && c != 133) {
         if (c != 8232 && c != 8233) {
            return "";
         } else {
            this.reader.forward();
            return String.valueOf(Character.toChars(c));
         }
      } else {
         if (c == 13 && 10 == this.reader.peek(1)) {
            this.reader.forward(2);
         } else {
            this.reader.forward();
         }

         return "\n";
      }
   }

   private List<Token> makeTokenList(Token... tokens) {
      List<Token> tokenList = new ArrayList<>();

      for (int ix = 0; ix < tokens.length; ix++) {
         if (tokens[ix] != null && (this.parseComments || !(tokens[ix] instanceof CommentToken))) {
            tokenList.add(tokens[ix]);
         }
      }

      return tokenList;
   }

   @Override
   public void resetDocumentIndex() {
      this.reader.resetDocumentIndex();
   }

   static {
      ESCAPE_REPLACEMENTS.put('0', "\u0000");
      ESCAPE_REPLACEMENTS.put('a', "\u0007");
      ESCAPE_REPLACEMENTS.put('b', "\b");
      ESCAPE_REPLACEMENTS.put('t', "\t");
      ESCAPE_REPLACEMENTS.put('n', "\n");
      ESCAPE_REPLACEMENTS.put('v', "\u000b");
      ESCAPE_REPLACEMENTS.put('f', "\f");
      ESCAPE_REPLACEMENTS.put('r', "\r");
      ESCAPE_REPLACEMENTS.put('e', "\u001b");
      ESCAPE_REPLACEMENTS.put(' ', " ");
      ESCAPE_REPLACEMENTS.put('"', "\"");
      ESCAPE_REPLACEMENTS.put('\\', "\\");
      ESCAPE_REPLACEMENTS.put('N', "\u0085");
      ESCAPE_REPLACEMENTS.put('_', " ");
      ESCAPE_REPLACEMENTS.put('L', "\u2028");
      ESCAPE_REPLACEMENTS.put('P', "\u2029");
      ESCAPE_CODES.put('x', 2);
      ESCAPE_CODES.put('u', 4);
      ESCAPE_CODES.put('U', 8);
   }

   private static class Chomping {
      private final Boolean value;
      private final int increment;

      public Chomping(Boolean value, int increment) {
         this.value = value;
         this.increment = increment;
      }

      public boolean chompTailIsNotFalse() {
         return this.value == null || this.value;
      }

      public boolean chompTailIsTrue() {
         return this.value != null && this.value;
      }

      public int getIncrement() {
         return this.increment;
      }
   }
}
