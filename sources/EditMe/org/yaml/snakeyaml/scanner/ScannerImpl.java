package org.yaml.snakeyaml.scanner;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.BlockEndToken;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.BlockMappingStartToken;
import org.yaml.snakeyaml.tokens.BlockSequenceStartToken;
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
   public static final Map ESCAPE_REPLACEMENTS = new HashMap();
   public static final Map ESCAPE_CODES = new HashMap();
   private final StreamReader reader;
   private boolean done = false;
   private int flowLevel = 0;
   private List tokens;
   private int tokensTaken = 0;
   private int indent = -1;
   private ArrayStack indents;
   private boolean allowSimpleKey = true;
   private Map possibleSimpleKeys;

   public ScannerImpl(StreamReader var1) {
      this.reader = var1;
      this.tokens = new ArrayList(100);
      this.indents = new ArrayStack(10);
      this.possibleSimpleKeys = new LinkedHashMap();
      this.fetchStreamStart();
   }

   public boolean checkToken(Token.ID... var1) {
      while(this.needMoreTokens()) {
         this.fetchMoreTokens();
      }

      if (!this.tokens.isEmpty()) {
         if (var1.length == 0) {
            return true;
         }

         Token.ID var2 = ((Token)this.tokens.get(0)).getTokenId();

         for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var2 == var1[var3]) {
               return true;
            }
         }
      }

      return false;
   }

   public Token peekToken() {
      while(this.needMoreTokens()) {
         this.fetchMoreTokens();
      }

      return (Token)this.tokens.get(0);
   }

   public Token getToken() {
      if (!this.tokens.isEmpty()) {
         ++this.tokensTaken;
         return (Token)this.tokens.remove(0);
      } else {
         return null;
      }
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
      this.scanToNextToken();
      this.stalePossibleSimpleKeys();
      this.unwindIndent(this.reader.getColumn());
      int var1 = this.reader.peek();
      switch(var1) {
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
         String var2 = String.valueOf(Character.toChars(var1));
         Iterator var3 = ESCAPE_REPLACEMENTS.keySet().iterator();

         while(var3.hasNext()) {
            Character var4 = (Character)var3.next();
            String var5 = (String)ESCAPE_REPLACEMENTS.get(var4);
            if (var5.equals(var2)) {
               var2 = "\\" + var4;
               break;
            }
         }

         if (var1 == 9) {
            var2 = var2 + "(TAB)";
         }

         String var6 = String.format("found character '%s' that cannot start any token. (Do not use %s for indentation)", var2, var2);
         throw new ScannerException("while scanning for the next token", (Mark)null, var6, this.reader.getMark());
      }
   }

   private int nextPossibleSimpleKey() {
      return !this.possibleSimpleKeys.isEmpty() ? ((SimpleKey)this.possibleSimpleKeys.values().iterator().next()).getTokenNumber() : -1;
   }

   private void stalePossibleSimpleKeys() {
      if (!this.possibleSimpleKeys.isEmpty()) {
         Iterator var1 = this.possibleSimpleKeys.values().iterator();

         while(true) {
            SimpleKey var2;
            do {
               if (!var1.hasNext()) {
                  return;
               }

               var2 = (SimpleKey)var1.next();
            } while(var2.getLine() == this.reader.getLine() && this.reader.getIndex() - var2.getIndex() <= 1024);

            if (var2.isRequired()) {
               throw new ScannerException("while scanning a simple key", var2.getMark(), "could not find expected ':'", this.reader.getMark());
            }

            var1.remove();
         }
      }
   }

   private void savePossibleSimpleKey() {
      boolean var1 = this.flowLevel == 0 && this.indent == this.reader.getColumn();
      if (!this.allowSimpleKey && var1) {
         throw new YAMLException("A simple key is required only if it is the first token in the current line");
      } else {
         if (this.allowSimpleKey) {
            this.removePossibleSimpleKey();
            int var2 = this.tokensTaken + this.tokens.size();
            SimpleKey var3 = new SimpleKey(var2, var1, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark());
            this.possibleSimpleKeys.put(this.flowLevel, var3);
         }

      }
   }

   private void removePossibleSimpleKey() {
      SimpleKey var1 = (SimpleKey)this.possibleSimpleKeys.remove(this.flowLevel);
      if (var1 != null && var1.isRequired()) {
         throw new ScannerException("while scanning a simple key", var1.getMark(), "could not find expected ':'", this.reader.getMark());
      }
   }

   private void unwindIndent(int var1) {
      if (this.flowLevel == 0) {
         while(this.indent > var1) {
            Mark var2 = this.reader.getMark();
            this.indent = (Integer)this.indents.pop();
            this.tokens.add(new BlockEndToken(var2, var2));
         }

      }
   }

   private boolean addIndent(int var1) {
      if (this.indent < var1) {
         this.indents.push(this.indent);
         this.indent = var1;
         return true;
      } else {
         return false;
      }
   }

   private void fetchStreamStart() {
      Mark var1 = this.reader.getMark();
      StreamStartToken var2 = new StreamStartToken(var1, var1);
      this.tokens.add(var2);
   }

   private void fetchStreamEnd() {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      this.possibleSimpleKeys.clear();
      Mark var1 = this.reader.getMark();
      StreamEndToken var2 = new StreamEndToken(var1, var1);
      this.tokens.add(var2);
      this.done = true;
   }

   private void fetchDirective() {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var1 = this.scanDirective();
      this.tokens.add(var1);
   }

   private void fetchDocumentStart() {
      this.fetchDocumentIndicator(true);
   }

   private void fetchDocumentEnd() {
      this.fetchDocumentIndicator(false);
   }

   private void fetchDocumentIndicator(boolean var1) {
      this.unwindIndent(-1);
      this.removePossibleSimpleKey();
      this.allowSimpleKey = false;
      Mark var2 = this.reader.getMark();
      this.reader.forward(3);
      Mark var3 = this.reader.getMark();
      Object var4;
      if (var1) {
         var4 = new DocumentStartToken(var2, var3);
      } else {
         var4 = new DocumentEndToken(var2, var3);
      }

      this.tokens.add(var4);
   }

   private void fetchFlowSequenceStart() {
      this.fetchFlowCollectionStart(false);
   }

   private void fetchFlowMappingStart() {
      this.fetchFlowCollectionStart(true);
   }

   private void fetchFlowCollectionStart(boolean var1) {
      this.savePossibleSimpleKey();
      ++this.flowLevel;
      this.allowSimpleKey = true;
      Mark var2 = this.reader.getMark();
      this.reader.forward(1);
      Mark var3 = this.reader.getMark();
      Object var4;
      if (var1) {
         var4 = new FlowMappingStartToken(var2, var3);
      } else {
         var4 = new FlowSequenceStartToken(var2, var3);
      }

      this.tokens.add(var4);
   }

   private void fetchFlowSequenceEnd() {
      this.fetchFlowCollectionEnd(false);
   }

   private void fetchFlowMappingEnd() {
      this.fetchFlowCollectionEnd(true);
   }

   private void fetchFlowCollectionEnd(boolean var1) {
      this.removePossibleSimpleKey();
      --this.flowLevel;
      this.allowSimpleKey = false;
      Mark var2 = this.reader.getMark();
      this.reader.forward();
      Mark var3 = this.reader.getMark();
      Object var4;
      if (var1) {
         var4 = new FlowMappingEndToken(var2, var3);
      } else {
         var4 = new FlowSequenceEndToken(var2, var3);
      }

      this.tokens.add(var4);
   }

   private void fetchFlowEntry() {
      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      Mark var1 = this.reader.getMark();
      this.reader.forward();
      Mark var2 = this.reader.getMark();
      FlowEntryToken var3 = new FlowEntryToken(var1, var2);
      this.tokens.add(var3);
   }

   private void fetchBlockEntry() {
      Mark var1;
      if (this.flowLevel == 0) {
         if (!this.allowSimpleKey) {
            throw new ScannerException((String)null, (Mark)null, "sequence entries are not allowed here", this.reader.getMark());
         }

         if (this.addIndent(this.reader.getColumn())) {
            var1 = this.reader.getMark();
            this.tokens.add(new BlockSequenceStartToken(var1, var1));
         }
      }

      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      var1 = this.reader.getMark();
      this.reader.forward();
      Mark var2 = this.reader.getMark();
      BlockEntryToken var3 = new BlockEntryToken(var1, var2);
      this.tokens.add(var3);
   }

   private void fetchKey() {
      Mark var1;
      if (this.flowLevel == 0) {
         if (!this.allowSimpleKey) {
            throw new ScannerException((String)null, (Mark)null, "mapping keys are not allowed here", this.reader.getMark());
         }

         if (this.addIndent(this.reader.getColumn())) {
            var1 = this.reader.getMark();
            this.tokens.add(new BlockMappingStartToken(var1, var1));
         }
      }

      this.allowSimpleKey = this.flowLevel == 0;
      this.removePossibleSimpleKey();
      var1 = this.reader.getMark();
      this.reader.forward();
      Mark var2 = this.reader.getMark();
      KeyToken var3 = new KeyToken(var1, var2);
      this.tokens.add(var3);
   }

   private void fetchValue() {
      SimpleKey var1 = (SimpleKey)this.possibleSimpleKeys.remove(this.flowLevel);
      Mark var2;
      if (var1 != null) {
         this.tokens.add(var1.getTokenNumber() - this.tokensTaken, new KeyToken(var1.getMark(), var1.getMark()));
         if (this.flowLevel == 0 && this.addIndent(var1.getColumn())) {
            this.tokens.add(var1.getTokenNumber() - this.tokensTaken, new BlockMappingStartToken(var1.getMark(), var1.getMark()));
         }

         this.allowSimpleKey = false;
      } else {
         if (this.flowLevel == 0 && !this.allowSimpleKey) {
            throw new ScannerException((String)null, (Mark)null, "mapping values are not allowed here", this.reader.getMark());
         }

         if (this.flowLevel == 0 && this.addIndent(this.reader.getColumn())) {
            var2 = this.reader.getMark();
            this.tokens.add(new BlockMappingStartToken(var2, var2));
         }

         this.allowSimpleKey = this.flowLevel == 0;
         this.removePossibleSimpleKey();
      }

      var2 = this.reader.getMark();
      this.reader.forward();
      Mark var3 = this.reader.getMark();
      ValueToken var4 = new ValueToken(var2, var3);
      this.tokens.add(var4);
   }

   private void fetchAlias() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var1 = this.scanAnchor(false);
      this.tokens.add(var1);
   }

   private void fetchAnchor() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var1 = this.scanAnchor(true);
      this.tokens.add(var1);
   }

   private void fetchTag() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var1 = this.scanTag();
      this.tokens.add(var1);
   }

   private void fetchLiteral() {
      this.fetchBlockScalar('|');
   }

   private void fetchFolded() {
      this.fetchBlockScalar('>');
   }

   private void fetchBlockScalar(char var1) {
      this.allowSimpleKey = true;
      this.removePossibleSimpleKey();
      Token var2 = this.scanBlockScalar(var1);
      this.tokens.add(var2);
   }

   private void fetchSingle() {
      this.fetchFlowScalar('\'');
   }

   private void fetchDouble() {
      this.fetchFlowScalar('"');
   }

   private void fetchFlowScalar(char var1) {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var2 = this.scanFlowScalar(var1);
      this.tokens.add(var2);
   }

   private void fetchPlain() {
      this.savePossibleSimpleKey();
      this.allowSimpleKey = false;
      Token var1 = this.scanPlain();
      this.tokens.add(var1);
   }

   private boolean checkDirective() {
      return this.reader.getColumn() == 0;
   }

   private boolean checkDocumentStart() {
      return this.reader.getColumn() == 0 && "---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
   }

   private boolean checkDocumentEnd() {
      return this.reader.getColumn() == 0 && "...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
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
      int var1 = this.reader.peek();
      return Constant.NULL_BL_T_LINEBR.hasNo(var1, "-?:,[]{}#&*!|>'\"%@`") || Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(1)) && (var1 == 45 || this.flowLevel == 0 && "?:".indexOf(var1) != -1);
   }

   private void scanToNextToken() {
      if (this.reader.getIndex() == 0 && this.reader.peek() == 65279) {
         this.reader.forward();
      }

      boolean var1 = false;

      while(!var1) {
         int var2;
         for(var2 = 0; this.reader.peek(var2) == 32; ++var2) {
         }

         if (var2 > 0) {
            this.reader.forward(var2);
         }

         if (this.reader.peek() == 35) {
            for(var2 = 0; Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(var2)); ++var2) {
            }

            if (var2 > 0) {
               this.reader.forward(var2);
            }
         }

         if (this.scanLineBreak().length() != 0) {
            if (this.flowLevel == 0) {
               this.allowSimpleKey = true;
            }
         } else {
            var1 = true;
         }
      }

   }

   private Token scanDirective() {
      Mark var1 = this.reader.getMark();
      this.reader.forward();
      String var3 = this.scanDirectiveName(var1);
      List var4 = null;
      Mark var2;
      if ("YAML".equals(var3)) {
         var4 = this.scanYamlDirectiveValue(var1);
         var2 = this.reader.getMark();
      } else if ("TAG".equals(var3)) {
         var4 = this.scanTagDirectiveValue(var1);
         var2 = this.reader.getMark();
      } else {
         var2 = this.reader.getMark();

         int var5;
         for(var5 = 0; Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(var5)); ++var5) {
         }

         if (var5 > 0) {
            this.reader.forward(var5);
         }
      }

      this.scanDirectiveIgnoredLine(var1);
      return new DirectiveToken(var3, var4, var1, var2);
   }

   private String scanDirectiveName(Mark var1) {
      int var2 = 0;

      int var3;
      for(var3 = this.reader.peek(var2); Constant.ALPHA.has(var3); var3 = this.reader.peek(var2)) {
         ++var2;
      }

      String var4;
      if (var2 == 0) {
         var4 = String.valueOf(Character.toChars(var3));
         throw new ScannerException("while scanning a directive", var1, "expected alphabetic or numeric character, but found " + var4 + "(" + var3 + ")", this.reader.getMark());
      } else {
         var4 = this.reader.prefixForward(var2);
         var3 = this.reader.peek();
         if (Constant.NULL_BL_LINEBR.hasNo(var3)) {
            String var5 = String.valueOf(Character.toChars(var3));
            throw new ScannerException("while scanning a directive", var1, "expected alphabetic or numeric character, but found " + var5 + "(" + var3 + ")", this.reader.getMark());
         } else {
            return var4;
         }
      }
   }

   private List scanYamlDirectiveValue(Mark var1) {
      while(this.reader.peek() == 32) {
         this.reader.forward();
      }

      Integer var2 = this.scanYamlDirectiveNumber(var1);
      int var3 = this.reader.peek();
      if (var3 != 46) {
         String var6 = String.valueOf(Character.toChars(var3));
         throw new ScannerException("while scanning a directive", var1, "expected a digit or '.', but found " + var6 + "(" + var3 + ")", this.reader.getMark());
      } else {
         this.reader.forward();
         Integer var4 = this.scanYamlDirectiveNumber(var1);
         var3 = this.reader.peek();
         if (Constant.NULL_BL_LINEBR.hasNo(var3)) {
            String var7 = String.valueOf(Character.toChars(var3));
            throw new ScannerException("while scanning a directive", var1, "expected a digit or ' ', but found " + var7 + "(" + var3 + ")", this.reader.getMark());
         } else {
            ArrayList var5 = new ArrayList(2);
            var5.add(var2);
            var5.add(var4);
            return var5;
         }
      }
   }

   private Integer scanYamlDirectiveNumber(Mark var1) {
      int var2 = this.reader.peek();
      if (!Character.isDigit(var2)) {
         String var5 = String.valueOf(Character.toChars(var2));
         throw new ScannerException("while scanning a directive", var1, "expected a digit, but found " + var5 + "(" + var2 + ")", this.reader.getMark());
      } else {
         int var3;
         for(var3 = 0; Character.isDigit(this.reader.peek(var3)); ++var3) {
         }

         Integer var4 = Integer.parseInt(this.reader.prefixForward(var3));
         return var4;
      }
   }

   private List scanTagDirectiveValue(Mark var1) {
      while(this.reader.peek() == 32) {
         this.reader.forward();
      }

      String var2 = this.scanTagDirectiveHandle(var1);

      while(this.reader.peek() == 32) {
         this.reader.forward();
      }

      String var3 = this.scanTagDirectivePrefix(var1);
      ArrayList var4 = new ArrayList(2);
      var4.add(var2);
      var4.add(var3);
      return var4;
   }

   private String scanTagDirectiveHandle(Mark var1) {
      String var2 = this.scanTagHandle("directive", var1);
      int var3 = this.reader.peek();
      if (var3 != 32) {
         String var4 = String.valueOf(Character.toChars(var3));
         throw new ScannerException("while scanning a directive", var1, "expected ' ', but found " + var4 + "(" + var3 + ")", this.reader.getMark());
      } else {
         return var2;
      }
   }

   private String scanTagDirectivePrefix(Mark var1) {
      String var2 = this.scanTagUri("directive", var1);
      int var3 = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(var3)) {
         String var4 = String.valueOf(Character.toChars(var3));
         throw new ScannerException("while scanning a directive", var1, "expected ' ', but found " + var4 + "(" + var3 + ")", this.reader.getMark());
      } else {
         return var2;
      }
   }

   private String scanDirectiveIgnoredLine(Mark var1) {
      while(this.reader.peek() == 32) {
         this.reader.forward();
      }

      if (this.reader.peek() == 35) {
         while(Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
            this.reader.forward();
         }
      }

      int var2 = this.reader.peek();
      String var3 = this.scanLineBreak();
      if (var3.length() == 0 && var2 != 0) {
         String var4 = String.valueOf(Character.toChars(var2));
         throw new ScannerException("while scanning a directive", var1, "expected a comment or a line break, but found " + var4 + "(" + var2 + ")", this.reader.getMark());
      } else {
         return var3;
      }
   }

   private Token scanAnchor(boolean var1) {
      Mark var2 = this.reader.getMark();
      int var3 = this.reader.peek();
      String var4 = var3 == 42 ? "alias" : "anchor";
      this.reader.forward();
      int var5 = 0;

      int var6;
      for(var6 = this.reader.peek(var5); Constant.ALPHA.has(var6); var6 = this.reader.peek(var5)) {
         ++var5;
      }

      String var7;
      if (var5 == 0) {
         var7 = String.valueOf(Character.toChars(var6));
         throw new ScannerException("while scanning an " + var4, var2, "expected alphabetic or numeric character, but found " + var7 + "(" + var6 + ")", this.reader.getMark());
      } else {
         var7 = this.reader.prefixForward(var5);
         var6 = this.reader.peek();
         if (Constant.NULL_BL_T_LINEBR.hasNo(var6, "?:,]}%@`")) {
            String var10 = String.valueOf(Character.toChars(var6));
            throw new ScannerException("while scanning an " + var4, var2, "expected alphabetic or numeric character, but found " + var10 + "(" + var6 + ")", this.reader.getMark());
         } else {
            Mark var8 = this.reader.getMark();
            Object var9;
            if (var1) {
               var9 = new AnchorToken(var7, var2, var8);
            } else {
               var9 = new AliasToken(var7, var2, var8);
            }

            return (Token)var9;
         }
      }
   }

   private Token scanTag() {
      Mark var1 = this.reader.getMark();
      int var2 = this.reader.peek(1);
      String var3 = null;
      String var4 = null;
      String var5;
      if (var2 == 60) {
         this.reader.forward(2);
         var4 = this.scanTagUri("tag", var1);
         var2 = this.reader.peek();
         if (var2 != 62) {
            var5 = String.valueOf(Character.toChars(var2));
            throw new ScannerException("while scanning a tag", var1, "expected '>', but found '" + var5 + "' (" + var2 + ")", this.reader.getMark());
         }

         this.reader.forward();
      } else if (Constant.NULL_BL_T_LINEBR.has(var2)) {
         var4 = "!";
         this.reader.forward();
      } else {
         int var7 = 1;

         boolean var6;
         for(var6 = false; Constant.NULL_BL_LINEBR.hasNo(var2); var2 = this.reader.peek(var7)) {
            if (var2 == 33) {
               var6 = true;
               break;
            }

            ++var7;
         }

         var3 = "!";
         if (var6) {
            var3 = this.scanTagHandle("tag", var1);
         } else {
            var3 = "!";
            this.reader.forward();
         }

         var4 = this.scanTagUri("tag", var1);
      }

      var2 = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(var2)) {
         var5 = String.valueOf(Character.toChars(var2));
         throw new ScannerException("while scanning a tag", var1, "expected ' ', but found '" + var5 + "' (" + var2 + ")", this.reader.getMark());
      } else {
         TagTuple var8 = new TagTuple(var3, var4);
         Mark var9 = this.reader.getMark();
         return new TagToken(var8, var1, var9);
      }
   }

   private Token scanBlockScalar(char var1) {
      boolean var2;
      if (var1 == '>') {
         var2 = true;
      } else {
         var2 = false;
      }

      StringBuilder var3 = new StringBuilder();
      Mark var4 = this.reader.getMark();
      this.reader.forward();
      ScannerImpl.Chomping var5 = this.scanBlockScalarIndicators(var4);
      int var6 = var5.getIncrement();
      this.scanBlockScalarIgnoredLine(var4);
      int var7 = this.indent + 1;
      if (var7 < 1) {
         var7 = 1;
      }

      String var8 = null;
      boolean var9 = false;
      boolean var10 = false;
      Mark var11;
      Object[] var12;
      int var17;
      if (var6 == -1) {
         var12 = this.scanBlockScalarIndentation();
         var8 = (String)var12[0];
         int var16 = (Integer)var12[1];
         var11 = (Mark)var12[2];
         var17 = Math.max(var7, var16);
      } else {
         var17 = var7 + var6 - 1;
         var12 = this.scanBlockScalarBreaks(var17);
         var8 = (String)var12[0];
         var11 = (Mark)var12[1];
      }

      String var18 = "";

      while(this.reader.getColumn() == var17 && this.reader.peek() != 0) {
         var3.append(var8);
         boolean var13 = " \t".indexOf(this.reader.peek()) == -1;

         int var14;
         for(var14 = 0; Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(var14)); ++var14) {
         }

         var3.append(this.reader.prefixForward(var14));
         var18 = this.scanLineBreak();
         Object[] var15 = this.scanBlockScalarBreaks(var17);
         var8 = (String)var15[0];
         var11 = (Mark)var15[1];
         if (this.reader.getColumn() != var17 || this.reader.peek() == 0) {
            break;
         }

         if (var2 && "\n".equals(var18) && var13 && " \t".indexOf(this.reader.peek()) == -1) {
            if (var8.length() == 0) {
               var3.append(" ");
            }
         } else {
            var3.append(var18);
         }
      }

      if (var5.chompTailIsNotFalse()) {
         var3.append(var18);
      }

      if (var5.chompTailIsTrue()) {
         var3.append(var8);
      }

      return new ScalarToken(var3.toString(), false, var4, var11, var1);
   }

   private ScannerImpl.Chomping scanBlockScalarIndicators(Mark var1) {
      Boolean var2 = null;
      int var3 = -1;
      int var4 = this.reader.peek();
      String var5;
      if (var4 != 45 && var4 != 43) {
         if (Character.isDigit(var4)) {
            var5 = String.valueOf(Character.toChars(var4));
            var3 = Integer.parseInt(var5);
            if (var3 == 0) {
               throw new ScannerException("while scanning a block scalar", var1, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
            }

            this.reader.forward();
            var4 = this.reader.peek();
            if (var4 == 45 || var4 == 43) {
               if (var4 == 43) {
                  var2 = Boolean.TRUE;
               } else {
                  var2 = Boolean.FALSE;
               }

               this.reader.forward();
            }
         }
      } else {
         if (var4 == 43) {
            var2 = Boolean.TRUE;
         } else {
            var2 = Boolean.FALSE;
         }

         this.reader.forward();
         var4 = this.reader.peek();
         if (Character.isDigit(var4)) {
            var5 = String.valueOf(Character.toChars(var4));
            var3 = Integer.parseInt(var5);
            if (var3 == 0) {
               throw new ScannerException("while scanning a block scalar", var1, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
            }

            this.reader.forward();
         }
      }

      var4 = this.reader.peek();
      if (Constant.NULL_BL_LINEBR.hasNo(var4)) {
         var5 = String.valueOf(Character.toChars(var4));
         throw new ScannerException("while scanning a block scalar", var1, "expected chomping or indentation indicators, but found " + var5 + "(" + var4 + ")", this.reader.getMark());
      } else {
         return new ScannerImpl.Chomping(var2, var3);
      }
   }

   private String scanBlockScalarIgnoredLine(Mark var1) {
      while(this.reader.peek() == 32) {
         this.reader.forward();
      }

      if (this.reader.peek() == 35) {
         while(Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
            this.reader.forward();
         }
      }

      int var2 = this.reader.peek();
      String var3 = this.scanLineBreak();
      if (var3.length() == 0 && var2 != 0) {
         String var4 = String.valueOf(Character.toChars(var2));
         throw new ScannerException("while scanning a block scalar", var1, "expected a comment or a line break, but found " + var4 + "(" + var2 + ")", this.reader.getMark());
      } else {
         return var3;
      }
   }

   private Object[] scanBlockScalarIndentation() {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;
      Mark var3 = this.reader.getMark();

      while(Constant.LINEBR.has(this.reader.peek(), " \r")) {
         if (this.reader.peek() != 32) {
            var1.append(this.scanLineBreak());
            var3 = this.reader.getMark();
         } else {
            this.reader.forward();
            if (this.reader.getColumn() > var2) {
               var2 = this.reader.getColumn();
            }
         }
      }

      return new Object[]{var1.toString(), var2, var3};
   }

   private Object[] scanBlockScalarBreaks(int var1) {
      StringBuilder var2 = new StringBuilder();
      Mark var3 = this.reader.getMark();

      int var4;
      for(var4 = this.reader.getColumn(); var4 < var1 && this.reader.peek() == 32; ++var4) {
         this.reader.forward();
      }

      String var5 = null;

      while((var5 = this.scanLineBreak()).length() != 0) {
         var2.append(var5);
         var3 = this.reader.getMark();

         for(var4 = this.reader.getColumn(); var4 < var1 && this.reader.peek() == 32; ++var4) {
            this.reader.forward();
         }
      }

      return new Object[]{var2.toString(), var3};
   }

   private Token scanFlowScalar(char var1) {
      boolean var2;
      if (var1 == '"') {
         var2 = true;
      } else {
         var2 = false;
      }

      StringBuilder var3 = new StringBuilder();
      Mark var4 = this.reader.getMark();
      int var5 = this.reader.peek();
      this.reader.forward();
      var3.append(this.scanFlowScalarNonSpaces(var2, var4));

      while(this.reader.peek() != var5) {
         var3.append(this.scanFlowScalarSpaces(var4));
         var3.append(this.scanFlowScalarNonSpaces(var2, var4));
      }

      this.reader.forward();
      Mark var6 = this.reader.getMark();
      return new ScalarToken(var3.toString(), false, var4, var6, var1);
   }

   private String scanFlowScalarNonSpaces(boolean var1, Mark var2) {
      StringBuilder var3 = new StringBuilder();

      while(true) {
         while(true) {
            while(true) {
               int var4;
               for(var4 = 0; Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(var4), "'\"\\"); ++var4) {
               }

               if (var4 != 0) {
                  var3.append(this.reader.prefixForward(var4));
               }

               int var5 = this.reader.peek();
               if (var1 || var5 != 39 || this.reader.peek(1) != 39) {
                  if ((!var1 || var5 != 39) && (var1 || "\"\\".indexOf(var5) == -1)) {
                     if (!var1 || var5 != 92) {
                        return var3.toString();
                     }

                     this.reader.forward();
                     var5 = this.reader.peek();
                     if (!Character.isSupplementaryCodePoint(var5) && ESCAPE_REPLACEMENTS.containsKey((char)var5)) {
                        var3.append((String)ESCAPE_REPLACEMENTS.get((char)var5));
                        this.reader.forward();
                     } else {
                        String var6;
                        if (!Character.isSupplementaryCodePoint(var5) && ESCAPE_CODES.containsKey((char)var5)) {
                           var4 = (Integer)ESCAPE_CODES.get((char)var5);
                           this.reader.forward();
                           var6 = this.reader.prefix(var4);
                           if (NOT_HEXA.matcher(var6).find()) {
                              throw new ScannerException("while scanning a double-quoted scalar", var2, "expected escape sequence of " + var4 + " hexadecimal numbers, but found: " + var6, this.reader.getMark());
                           }

                           int var7 = Integer.parseInt(var6, 16);
                           String var8 = new String(Character.toChars(var7));
                           var3.append(var8);
                           this.reader.forward(var4);
                        } else {
                           if (this.scanLineBreak().length() == 0) {
                              var6 = String.valueOf(Character.toChars(var5));
                              throw new ScannerException("while scanning a double-quoted scalar", var2, "found unknown escape character " + var6 + "(" + var5 + ")", this.reader.getMark());
                           }

                           var3.append(this.scanFlowScalarBreaks(var2));
                        }
                     }
                  } else {
                     var3.appendCodePoint(var5);
                     this.reader.forward();
                  }
               } else {
                  var3.append("'");
                  this.reader.forward(2);
               }
            }
         }
      }
   }

   private String scanFlowScalarSpaces(Mark var1) {
      StringBuilder var2 = new StringBuilder();

      int var3;
      for(var3 = 0; " \t".indexOf(this.reader.peek(var3)) != -1; ++var3) {
      }

      String var4 = this.reader.prefixForward(var3);
      int var5 = this.reader.peek();
      if (var5 == 0) {
         throw new ScannerException("while scanning a quoted scalar", var1, "found unexpected end of stream", this.reader.getMark());
      } else {
         String var6 = this.scanLineBreak();
         if (var6.length() != 0) {
            String var7 = this.scanFlowScalarBreaks(var1);
            if (!"\n".equals(var6)) {
               var2.append(var6);
            } else if (var7.length() == 0) {
               var2.append(" ");
            }

            var2.append(var7);
         } else {
            var2.append(var4);
         }

         return var2.toString();
      }
   }

   private String scanFlowScalarBreaks(Mark var1) {
      StringBuilder var2 = new StringBuilder();

      while(true) {
         String var3 = this.reader.prefix(3);
         if (("---".equals(var3) || "...".equals(var3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
            throw new ScannerException("while scanning a quoted scalar", var1, "found unexpected document separator", this.reader.getMark());
         }

         while(" \t".indexOf(this.reader.peek()) != -1) {
            this.reader.forward();
         }

         String var4 = this.scanLineBreak();
         if (var4.length() == 0) {
            return var2.toString();
         }

         var2.append(var4);
      }
   }

   private Token scanPlain() {
      StringBuilder var1 = new StringBuilder();
      Mark var2 = this.reader.getMark();
      Mark var3 = var2;
      int var4 = this.indent + 1;
      String var5 = "";

      do {
         int var7 = 0;
         if (this.reader.peek() == 35) {
            break;
         }

         while(true) {
            int var6 = this.reader.peek(var7);
            if (Constant.NULL_BL_T_LINEBR.has(var6) || this.flowLevel == 0 && var6 == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(var7 + 1)) || this.flowLevel != 0 && ",:?[]{}".indexOf(var6) != -1) {
               if (this.flowLevel != 0 && var6 == 58 && Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(var7 + 1), ",[]{}")) {
                  this.reader.forward(var7);
                  throw new ScannerException("while scanning a plain scalar", var2, "found unexpected ':'", this.reader.getMark(), "Please check http://pyyaml.org/wiki/YAMLColonInFlowContext for details.");
               }

               if (var7 == 0) {
                  return new ScalarToken(var1.toString(), var2, var3, true);
               }

               this.allowSimpleKey = false;
               var1.append(var5);
               var1.append(this.reader.prefixForward(var7));
               var3 = this.reader.getMark();
               var5 = this.scanPlainSpaces();
               break;
            }

            ++var7;
         }
      } while(var5.length() != 0 && this.reader.peek() != 35 && (this.flowLevel != 0 || this.reader.getColumn() >= var4));

      return new ScalarToken(var1.toString(), var2, var3, true);
   }

   private String scanPlainSpaces() {
      int var1;
      for(var1 = 0; this.reader.peek(var1) == 32 || this.reader.peek(var1) == 9; ++var1) {
      }

      String var2 = this.reader.prefixForward(var1);
      String var3 = this.scanLineBreak();
      if (var3.length() == 0) {
         return var2;
      } else {
         this.allowSimpleKey = true;
         String var4 = this.reader.prefix(3);
         if (!"---".equals(var4) && (!"...".equals(var4) || !Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))) {
            StringBuilder var5 = new StringBuilder();

            do {
               while(this.reader.peek() == 32) {
                  this.reader.forward();
               }

               String var6 = this.scanLineBreak();
               if (var6.length() == 0) {
                  if (!"\n".equals(var3)) {
                     return var3 + var5;
                  }

                  if (var5.length() == 0) {
                     return " ";
                  }

                  return var5.toString();
               }

               var5.append(var6);
               var4 = this.reader.prefix(3);
            } while(!"---".equals(var4) && (!"...".equals(var4) || !Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))));

            return "";
         } else {
            return "";
         }
      }
   }

   private String scanTagHandle(String var1, Mark var2) {
      int var3 = this.reader.peek();
      if (var3 != 33) {
         String var6 = String.valueOf(Character.toChars(var3));
         throw new ScannerException("while scanning a " + var1, var2, "expected '!', but found " + var6 + "(" + var3 + ")", this.reader.getMark());
      } else {
         int var4 = 1;
         var3 = this.reader.peek(var4);
         String var5;
         if (var3 != 32) {
            while(Constant.ALPHA.has(var3)) {
               ++var4;
               var3 = this.reader.peek(var4);
            }

            if (var3 != 33) {
               this.reader.forward(var4);
               var5 = String.valueOf(Character.toChars(var3));
               throw new ScannerException("while scanning a " + var1, var2, "expected '!', but found " + var5 + "(" + var3 + ")", this.reader.getMark());
            }

            ++var4;
         }

         var5 = this.reader.prefixForward(var4);
         return var5;
      }
   }

   private String scanTagUri(String var1, Mark var2) {
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;

      int var5;
      for(var5 = this.reader.peek(var4); Constant.URI_CHARS.has(var5); var5 = this.reader.peek(var4)) {
         if (var5 == 37) {
            var3.append(this.reader.prefixForward(var4));
            var4 = 0;
            var3.append(this.scanUriEscapes(var1, var2));
         } else {
            ++var4;
         }
      }

      if (var4 != 0) {
         var3.append(this.reader.prefixForward(var4));
         boolean var7 = false;
      }

      if (var3.length() == 0) {
         String var6 = String.valueOf(Character.toChars(var5));
         throw new ScannerException("while scanning a " + var1, var2, "expected URI, but found " + var6 + "(" + var5 + ")", this.reader.getMark());
      } else {
         return var3.toString();
      }
   }

   private String scanUriEscapes(String var1, Mark var2) {
      int var3;
      for(var3 = 1; this.reader.peek(var3 * 3) == 37; ++var3) {
      }

      Mark var4 = this.reader.getMark();

      ByteBuffer var5;
      for(var5 = ByteBuffer.allocate(var3); this.reader.peek() == 37; this.reader.forward(2)) {
         this.reader.forward();

         try {
            byte var6 = (byte)Integer.parseInt(this.reader.prefix(2), 16);
            var5.put(var6);
         } catch (NumberFormatException var12) {
            int var7 = this.reader.peek();
            String var8 = String.valueOf(Character.toChars(var7));
            int var9 = this.reader.peek(1);
            String var10 = String.valueOf(Character.toChars(var9));
            throw new ScannerException("while scanning a " + var1, var2, "expected URI escape sequence of 2 hexadecimal numbers, but found " + var8 + "(" + var7 + ") and " + var10 + "(" + var9 + ")", this.reader.getMark());
         }
      }

      var5.flip();

      try {
         return UriEncoder.decode(var5);
      } catch (CharacterCodingException var11) {
         throw new ScannerException("while scanning a " + var1, var2, "expected URI in UTF-8: " + var11.getMessage(), var4);
      }
   }

   private String scanLineBreak() {
      int var1 = this.reader.peek();
      if (var1 != 13 && var1 != 10 && var1 != 133) {
         if (var1 != 8232 && var1 != 8233) {
            return "";
         } else {
            this.reader.forward();
            return String.valueOf(Character.toChars(var1));
         }
      } else {
         if (var1 == 13 && 10 == this.reader.peek(1)) {
            this.reader.forward(2);
         } else {
            this.reader.forward();
         }

         return "\n";
      }
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

      public Chomping(Boolean var1, int var2) {
         this.value = var1;
         this.increment = var2;
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
