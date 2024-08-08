package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CollectionEndEvent;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Constant;
import org.yaml.snakeyaml.util.ArrayStack;

public final class Emitter implements Emitable {
   private static final Map ESCAPE_REPLACEMENTS = new HashMap();
   public static final int MIN_INDENT = 1;
   public static final int MAX_INDENT = 10;
   private static final char[] SPACE = new char[]{' '};
   private static final Map DEFAULT_TAG_PREFIXES;
   private final Writer stream;
   private final ArrayStack states;
   private EmitterState state;
   private final Queue events;
   private Event event;
   private final ArrayStack indents;
   private Integer indent;
   private int flowLevel;
   private boolean rootContext;
   private boolean mappingContext;
   private boolean simpleKeyContext;
   private int column;
   private boolean whitespace;
   private boolean indention;
   private boolean openEnded;
   private Boolean canonical;
   private Boolean prettyFlow;
   private boolean allowUnicode;
   private int bestIndent;
   private int indicatorIndent;
   private int bestWidth;
   private char[] bestLineBreak;
   private boolean splitLines;
   private Map tagPrefixes;
   private String preparedAnchor;
   private String preparedTag;
   private ScalarAnalysis analysis;
   private Character style;
   private static final Pattern HANDLE_FORMAT;
   private static final Pattern ANCHOR_FORMAT;

   public Emitter(Writer var1, DumperOptions var2) {
      this.stream = var1;
      this.states = new ArrayStack(100);
      this.state = new Emitter.ExpectStreamStart(this);
      this.events = new ArrayBlockingQueue(100);
      this.event = null;
      this.indents = new ArrayStack(10);
      this.indent = null;
      this.flowLevel = 0;
      this.mappingContext = false;
      this.simpleKeyContext = false;
      this.column = 0;
      this.whitespace = true;
      this.indention = true;
      this.openEnded = false;
      this.canonical = var2.isCanonical();
      this.prettyFlow = var2.isPrettyFlow();
      this.allowUnicode = var2.isAllowUnicode();
      this.bestIndent = 2;
      if (var2.getIndent() > 1 && var2.getIndent() < 10) {
         this.bestIndent = var2.getIndent();
      }

      this.indicatorIndent = var2.getIndicatorIndent();
      this.bestWidth = 80;
      if (var2.getWidth() > this.bestIndent * 2) {
         this.bestWidth = var2.getWidth();
      }

      this.bestLineBreak = var2.getLineBreak().getString().toCharArray();
      this.splitLines = var2.getSplitLines();
      this.tagPrefixes = new LinkedHashMap();
      this.preparedAnchor = null;
      this.preparedTag = null;
      this.analysis = null;
      this.style = null;
   }

   public void emit(Event var1) throws IOException {
      this.events.add(var1);

      while(!this.needMoreEvents()) {
         this.event = (Event)this.events.poll();
         this.state.expect();
         this.event = null;
      }

   }

   private boolean needMoreEvents() {
      if (this.events.isEmpty()) {
         return true;
      } else {
         Event var1 = (Event)this.events.peek();
         if (var1 instanceof DocumentStartEvent) {
            return this.needEvents(1);
         } else if (var1 instanceof SequenceStartEvent) {
            return this.needEvents(2);
         } else {
            return var1 instanceof MappingStartEvent ? this.needEvents(3) : false;
         }
      }
   }

   private boolean needEvents(int var1) {
      int var2 = 0;
      Iterator var3 = this.events.iterator();
      var3.next();

      do {
         if (!var3.hasNext()) {
            return this.events.size() < var1 + 1;
         }

         Event var4 = (Event)var3.next();
         if (!(var4 instanceof DocumentStartEvent) && !(var4 instanceof CollectionStartEvent)) {
            if (!(var4 instanceof DocumentEndEvent) && !(var4 instanceof CollectionEndEvent)) {
               if (var4 instanceof StreamEndEvent) {
                  var2 = -1;
               }
            } else {
               --var2;
            }
         } else {
            ++var2;
         }
      } while(var2 >= 0);

      return false;
   }

   private void increaseIndent(boolean var1, boolean var2) {
      this.indents.push(this.indent);
      if (this.indent == null) {
         if (var1) {
            this.indent = this.bestIndent;
         } else {
            this.indent = 0;
         }
      } else if (!var2) {
         this.indent = this.indent + this.bestIndent;
      }

   }

   private void expectNode(boolean var1, boolean var2, boolean var3) throws IOException {
      this.rootContext = var1;
      this.mappingContext = var2;
      this.simpleKeyContext = var3;
      if (this.event instanceof AliasEvent) {
         this.expectAlias();
      } else {
         if (!(this.event instanceof ScalarEvent) && !(this.event instanceof CollectionStartEvent)) {
            throw new EmitterException("expected NodeEvent, but got " + this.event);
         }

         this.processAnchor("&");
         this.processTag();
         if (this.event instanceof ScalarEvent) {
            this.expectScalar();
         } else if (this.event instanceof SequenceStartEvent) {
            if (this.flowLevel == 0 && !this.canonical && !((SequenceStartEvent)this.event).getFlowStyle() && !this.checkEmptySequence()) {
               this.expectBlockSequence();
            } else {
               this.expectFlowSequence();
            }
         } else if (this.flowLevel == 0 && !this.canonical && !((MappingStartEvent)this.event).getFlowStyle() && !this.checkEmptyMapping()) {
            this.expectBlockMapping();
         } else {
            this.expectFlowMapping();
         }
      }

   }

   private void expectAlias() throws IOException {
      if (((NodeEvent)this.event).getAnchor() == null) {
         throw new EmitterException("anchor is not specified for alias");
      } else {
         this.processAnchor("*");
         this.state = (EmitterState)this.states.pop();
      }
   }

   private void expectScalar() throws IOException {
      this.increaseIndent(true, false);
      this.processScalar();
      this.indent = (Integer)this.indents.pop();
      this.state = (EmitterState)this.states.pop();
   }

   private void expectFlowSequence() throws IOException {
      this.writeIndicator("[", true, true, false);
      ++this.flowLevel;
      this.increaseIndent(true, false);
      if (this.prettyFlow) {
         this.writeIndent();
      }

      this.state = new Emitter.ExpectFirstFlowSequenceItem(this);
   }

   private void expectFlowMapping() throws IOException {
      this.writeIndicator("{", true, true, false);
      ++this.flowLevel;
      this.increaseIndent(true, false);
      if (this.prettyFlow) {
         this.writeIndent();
      }

      this.state = new Emitter.ExpectFirstFlowMappingKey(this);
   }

   private void expectBlockSequence() throws IOException {
      boolean var1 = this.mappingContext && !this.indention;
      this.increaseIndent(false, var1);
      this.state = new Emitter.ExpectFirstBlockSequenceItem(this);
   }

   private void expectBlockMapping() throws IOException {
      this.increaseIndent(false, false);
      this.state = new Emitter.ExpectFirstBlockMappingKey(this);
   }

   private boolean checkEmptySequence() {
      return this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events.peek() instanceof SequenceEndEvent;
   }

   private boolean checkEmptyMapping() {
      return this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events.peek() instanceof MappingEndEvent;
   }

   private boolean checkEmptyDocument() {
      if (this.event instanceof DocumentStartEvent && !this.events.isEmpty()) {
         Event var1 = (Event)this.events.peek();
         if (!(var1 instanceof ScalarEvent)) {
            return false;
         } else {
            ScalarEvent var2 = (ScalarEvent)var1;
            return var2.getAnchor() == null && var2.getTag() == null && var2.getImplicit() != null && var2.getValue().length() == 0;
         }
      } else {
         return false;
      }
   }

   private boolean checkSimpleKey() {
      int var1 = 0;
      if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
         if (this.preparedAnchor == null) {
            this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());
         }

         var1 += this.preparedAnchor.length();
      }

      String var2 = null;
      if (this.event instanceof ScalarEvent) {
         var2 = ((ScalarEvent)this.event).getTag();
      } else if (this.event instanceof CollectionStartEvent) {
         var2 = ((CollectionStartEvent)this.event).getTag();
      }

      if (var2 != null) {
         if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(var2);
         }

         var1 += this.preparedTag.length();
      }

      if (this.event instanceof ScalarEvent) {
         if (this.analysis == null) {
            this.analysis = this.analyzeScalar(((ScalarEvent)this.event).getValue());
         }

         var1 += this.analysis.scalar.length();
      }

      return var1 < 128 && (this.event instanceof AliasEvent || this.event instanceof ScalarEvent && !this.analysis.empty && !this.analysis.multiline || this.checkEmptySequence() || this.checkEmptyMapping());
   }

   private void processAnchor(String var1) throws IOException {
      NodeEvent var2 = (NodeEvent)this.event;
      if (var2.getAnchor() == null) {
         this.preparedAnchor = null;
      } else {
         if (this.preparedAnchor == null) {
            this.preparedAnchor = prepareAnchor(var2.getAnchor());
         }

         this.writeIndicator(var1 + this.preparedAnchor, true, false, false);
         this.preparedAnchor = null;
      }
   }

   private void processTag() throws IOException {
      String var1 = null;
      if (this.event instanceof ScalarEvent) {
         ScalarEvent var3 = (ScalarEvent)this.event;
         var1 = var3.getTag();
         if (this.style == null) {
            this.style = this.chooseScalarStyle();
         }

         if ((!this.canonical || var1 == null) && (this.style == null && var3.getImplicit().canOmitTagInPlainScalar() || this.style != null && var3.getImplicit().canOmitTagInNonPlainScalar())) {
            this.preparedTag = null;
            return;
         }

         if (var3.getImplicit().canOmitTagInPlainScalar() && var1 == null) {
            var1 = "!";
            this.preparedTag = null;
         }
      } else {
         CollectionStartEvent var2 = (CollectionStartEvent)this.event;
         var1 = var2.getTag();
         if ((!this.canonical || var1 == null) && var2.getImplicit()) {
            this.preparedTag = null;
            return;
         }
      }

      if (var1 == null) {
         throw new EmitterException("tag is not specified");
      } else {
         if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(var1);
         }

         this.writeIndicator(this.preparedTag, true, false, false);
         this.preparedTag = null;
      }
   }

   private Character chooseScalarStyle() {
      ScalarEvent var1 = (ScalarEvent)this.event;
      if (this.analysis == null) {
         this.analysis = this.analyzeScalar(var1.getValue());
      }

      if ((var1.getStyle() == null || var1.getStyle() != '"') && !this.canonical) {
         if (var1.getStyle() == null && var1.getImplicit().canOmitTagInPlainScalar() && (!this.simpleKeyContext || !this.analysis.empty && !this.analysis.multiline) && (this.flowLevel != 0 && this.analysis.allowFlowPlain || this.flowLevel == 0 && this.analysis.allowBlockPlain)) {
            return null;
         } else if (var1.getStyle() != null && (var1.getStyle() == '|' || var1.getStyle() == '>') && this.flowLevel == 0 && !this.simpleKeyContext && this.analysis.allowBlock) {
            return var1.getStyle();
         } else {
            return var1.getStyle() != null && var1.getStyle() != '\'' || !this.analysis.allowSingleQuoted || this.simpleKeyContext && this.analysis.multiline ? '"' : '\'';
         }
      } else {
         return '"';
      }
   }

   private void processScalar() throws IOException {
      ScalarEvent var1 = (ScalarEvent)this.event;
      if (this.analysis == null) {
         this.analysis = this.analyzeScalar(var1.getValue());
      }

      if (this.style == null) {
         this.style = this.chooseScalarStyle();
      }

      boolean var2 = !this.simpleKeyContext && this.splitLines;
      if (this.style == null) {
         this.writePlain(this.analysis.scalar, var2);
      } else {
         switch(this.style) {
         case '"':
            this.writeDoubleQuoted(this.analysis.scalar, var2);
            break;
         case '\'':
            this.writeSingleQuoted(this.analysis.scalar, var2);
            break;
         case '>':
            this.writeFolded(this.analysis.scalar, var2);
            break;
         case '|':
            this.writeLiteral(this.analysis.scalar);
            break;
         default:
            throw new YAMLException("Unexpected style: " + this.style);
         }
      }

      this.analysis = null;
      this.style = null;
   }

   private String prepareVersion(DumperOptions.Version var1) {
      if (var1.major() != 1) {
         throw new EmitterException("unsupported YAML version: " + var1);
      } else {
         return var1.getRepresentation();
      }
   }

   private String prepareTagHandle(String var1) {
      if (var1.length() == 0) {
         throw new EmitterException("tag handle must not be empty");
      } else if (var1.charAt(0) == '!' && var1.charAt(var1.length() - 1) == '!') {
         if (!"!".equals(var1) && !HANDLE_FORMAT.matcher(var1).matches()) {
            throw new EmitterException("invalid character in the tag handle: " + var1);
         } else {
            return var1;
         }
      } else {
         throw new EmitterException("tag handle must start and end with '!': " + var1);
      }
   }

   private String prepareTagPrefix(String var1) {
      if (var1.length() == 0) {
         throw new EmitterException("tag prefix must not be empty");
      } else {
         StringBuilder var2 = new StringBuilder();
         byte var3 = 0;
         int var4 = 0;
         if (var1.charAt(0) == '!') {
            var4 = 1;
         }

         while(var4 < var1.length()) {
            ++var4;
         }

         if (var3 < var4) {
            var2.append(var1.substring(var3, var4));
         }

         return var2.toString();
      }
   }

   private String prepareTag(String var1) {
      if (var1.length() == 0) {
         throw new EmitterException("tag must not be empty");
      } else if ("!".equals(var1)) {
         return var1;
      } else {
         String var2 = null;
         String var3 = var1;
         Iterator var4 = this.tagPrefixes.keySet().iterator();

         while(true) {
            String var5;
            do {
               do {
                  if (!var4.hasNext()) {
                     if (var2 != null) {
                        var3 = var1.substring(var2.length());
                        var2 = (String)this.tagPrefixes.get(var2);
                     }

                     int var6 = var3.length();
                     var5 = var6 > 0 ? var3.substring(0, var6) : "";
                     if (var2 != null) {
                        return var2 + var5;
                     }

                     return "!<" + var5 + ">";
                  }

                  var5 = (String)var4.next();
               } while(!var1.startsWith(var5));
            } while(!"!".equals(var5) && var5.length() >= var1.length());

            var2 = var5;
         }
      }
   }

   static String prepareAnchor(String var0) {
      if (var0.length() == 0) {
         throw new EmitterException("anchor must not be empty");
      } else if (!ANCHOR_FORMAT.matcher(var0).matches()) {
         throw new EmitterException("invalid character in the anchor: " + var0);
      } else {
         return var0;
      }
   }

   private ScalarAnalysis analyzeScalar(String var1) {
      if (var1.length() == 0) {
         return new ScalarAnalysis(var1, true, false, false, true, true, false);
      } else {
         boolean var2 = false;
         boolean var3 = false;
         boolean var4 = false;
         boolean var5 = false;
         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;
         boolean var9 = false;
         boolean var10 = false;
         boolean var11 = false;
         if (var1.startsWith("---") || var1.startsWith("...")) {
            var2 = true;
            var3 = true;
         }

         boolean var12 = true;
         boolean var13 = var1.length() == 1 || Constant.NULL_BL_T_LINEBR.has(var1.codePointAt(1));
         boolean var14 = false;
         boolean var15 = false;
         int var16 = 0;

         while(true) {
            boolean var18;
            int var19;
            do {
               do {
                  if (var16 >= var1.length()) {
                     boolean var21 = true;
                     var18 = true;
                     boolean var22 = true;
                     boolean var20 = true;
                     if (var6 || var7 || var8 || var9) {
                        var18 = false;
                        var21 = false;
                     }

                     if (var8) {
                        var20 = false;
                     }

                     if (var10) {
                        var22 = false;
                        var18 = false;
                        var21 = false;
                     }

                     if (var11 || var5) {
                        var20 = false;
                        var22 = false;
                        var18 = false;
                        var21 = false;
                     }

                     if (var4) {
                        var21 = false;
                     }

                     if (var3) {
                        var21 = false;
                     }

                     if (var2) {
                        var18 = false;
                     }

                     return new ScalarAnalysis(var1, false, var4, var21, var18, var22, var20);
                  }

                  int var17 = var1.codePointAt(var16);
                  if (var16 == 0) {
                     if ("#,[]{}&*!|>'\"%@`".indexOf(var17) != -1) {
                        var3 = true;
                        var2 = true;
                     }

                     if (var17 == 63 || var17 == 58) {
                        var3 = true;
                        if (var13) {
                           var2 = true;
                        }
                     }

                     if (var17 == 45 && var13) {
                        var3 = true;
                        var2 = true;
                     }
                  } else {
                     if (",?[]{}".indexOf(var17) != -1) {
                        var3 = true;
                     }

                     if (var17 == 58) {
                        var3 = true;
                        if (var13) {
                           var2 = true;
                        }
                     }

                     if (var17 == 35 && var12) {
                        var3 = true;
                        var2 = true;
                     }
                  }

                  var18 = Constant.LINEBR.has(var17);
                  if (var18) {
                     var4 = true;
                  }

                  if (var17 != 10 && (32 > var17 || var17 > 126)) {
                     if (var17 == 133 || var17 >= 160 && var17 <= 55295 || var17 >= 57344 && var17 <= 65533 || var17 >= 65536 && var17 <= 1114111) {
                        if (!this.allowUnicode) {
                           var5 = true;
                        }
                     } else {
                        var5 = true;
                     }
                  }

                  if (var17 == 32) {
                     if (var16 == 0) {
                        var6 = true;
                     }

                     if (var16 == var1.length() - 1) {
                        var8 = true;
                     }

                     if (var15) {
                        var10 = true;
                     }

                     var14 = true;
                     var15 = false;
                  } else if (var18) {
                     if (var16 == 0) {
                        var7 = true;
                     }

                     if (var16 == var1.length() - 1) {
                        var9 = true;
                     }

                     if (var14) {
                        var11 = true;
                     }

                     var14 = false;
                     var15 = true;
                  } else {
                     var14 = false;
                     var15 = false;
                  }

                  var16 += Character.charCount(var17);
                  var12 = Constant.NULL_BL_T.has(var17) || var18;
                  var13 = true;
               } while(var16 + 1 >= var1.length());

               var19 = var16 + Character.charCount(var1.codePointAt(var16));
            } while(var19 >= var1.length());

            var13 = Constant.NULL_BL_T.has(var1.codePointAt(var19)) || var18;
         }
      }
   }

   void flushStream() throws IOException {
      this.stream.flush();
   }

   void writeStreamStart() {
   }

   void writeStreamEnd() throws IOException {
      this.flushStream();
   }

   void writeIndicator(String var1, boolean var2, boolean var3, boolean var4) throws IOException {
      if (!this.whitespace && var2) {
         ++this.column;
         this.stream.write(SPACE);
      }

      this.whitespace = var3;
      this.indention = this.indention && var4;
      this.column += var1.length();
      this.openEnded = false;
      this.stream.write(var1);
   }

   void writeIndent() throws IOException {
      int var1;
      if (this.indent != null) {
         var1 = this.indent;
      } else {
         var1 = 0;
      }

      if (!this.indention || this.column > var1 || this.column == var1 && !this.whitespace) {
         this.writeLineBreak((String)null);
      }

      this.writeWhitespace(var1 - this.column);
   }

   private void writeWhitespace(int var1) throws IOException {
      if (var1 > 0) {
         this.whitespace = true;
         char[] var2 = new char[var1];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = ' ';
         }

         this.column += var1;
         this.stream.write(var2);
      }
   }

   private void writeLineBreak(String var1) throws IOException {
      this.whitespace = true;
      this.indention = true;
      this.column = 0;
      if (var1 == null) {
         this.stream.write(this.bestLineBreak);
      } else {
         this.stream.write(var1);
      }

   }

   void writeVersionDirective(String var1) throws IOException {
      this.stream.write("%YAML ");
      this.stream.write(var1);
      this.writeLineBreak((String)null);
   }

   void writeTagDirective(String var1, String var2) throws IOException {
      this.stream.write("%TAG ");
      this.stream.write(var1);
      this.stream.write(SPACE);
      this.stream.write(var2);
      this.writeLineBreak((String)null);
   }

   private void writeSingleQuoted(String var1, boolean var2) throws IOException {
      this.writeIndicator("'", true, false, false);
      boolean var3 = false;
      boolean var4 = false;
      int var5 = 0;

      for(int var6 = 0; var6 <= var1.length(); ++var6) {
         char var7 = 0;
         if (var6 < var1.length()) {
            var7 = var1.charAt(var6);
         }

         int var13;
         if (var3) {
            if (var7 == 0 || var7 != ' ') {
               if (var5 + 1 == var6 && this.column > this.bestWidth && var2 && var5 != 0 && var6 != var1.length()) {
                  this.writeIndent();
               } else {
                  var13 = var6 - var5;
                  this.column += var13;
                  this.stream.write(var1, var5, var13);
               }

               var5 = var6;
            }
         } else if (!var4) {
            if (Constant.LINEBR.has(var7, "\u0000 '") && var5 < var6) {
               var13 = var6 - var5;
               this.column += var13;
               this.stream.write(var1, var5, var13);
               var5 = var6;
            }
         } else if (var7 == 0 || Constant.LINEBR.hasNo(var7)) {
            if (var1.charAt(var5) == '\n') {
               this.writeLineBreak((String)null);
            }

            String var8 = var1.substring(var5, var6);
            char[] var9 = var8.toCharArray();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               char var12 = var9[var11];
               if (var12 == '\n') {
                  this.writeLineBreak((String)null);
               } else {
                  this.writeLineBreak(String.valueOf(var12));
               }
            }

            this.writeIndent();
            var5 = var6;
         }

         if (var7 == '\'') {
            this.column += 2;
            this.stream.write("''");
            var5 = var6 + 1;
         }

         if (var7 != 0) {
            var3 = var7 == ' ';
            var4 = Constant.LINEBR.has(var7);
         }
      }

      this.writeIndicator("'", false, false, false);
   }

   private void writeDoubleQuoted(String var1, boolean var2) throws IOException {
      this.writeIndicator("\"", true, false, false);
      int var3 = 0;

      for(int var4 = 0; var4 <= var1.length(); ++var4) {
         Character var5 = null;
         if (var4 < var1.length()) {
            var5 = var1.charAt(var4);
         }

         String var9;
         if (var5 == null || "\"\\\u0085\u2028\u2029\ufeff".indexOf(var5) != -1 || ' ' > var5 || var5 > '~') {
            if (var3 < var4) {
               int var6 = var4 - var3;
               this.column += var6;
               this.stream.write(var1, var3, var6);
               var3 = var4;
            }

            if (var5 != null) {
               if (ESCAPE_REPLACEMENTS.containsKey(var5)) {
                  var9 = "\\" + (String)ESCAPE_REPLACEMENTS.get(var5);
               } else if (this.allowUnicode && StreamReader.isPrintable(var5)) {
                  var9 = String.valueOf(var5);
               } else {
                  String var7;
                  if (var5 <= 255) {
                     var7 = "0" + Integer.toString(var5, 16);
                     var9 = "\\x" + var7.substring(var7.length() - 2);
                  } else if (var5 >= '\ud800' && var5 <= '\udbff') {
                     if (var4 + 1 < var1.length()) {
                        ++var4;
                        Character var10 = var1.charAt(var4);
                        String var8 = "000" + Long.toHexString((long)Character.toCodePoint(var5, var10));
                        var9 = "\\U" + var8.substring(var8.length() - 8);
                     } else {
                        var7 = "000" + Integer.toString(var5, 16);
                        var9 = "\\u" + var7.substring(var7.length() - 4);
                     }
                  } else {
                     var7 = "000" + Integer.toString(var5, 16);
                     var9 = "\\u" + var7.substring(var7.length() - 4);
                  }
               }

               this.column += var9.length();
               this.stream.write(var9);
               var3 = var4 + 1;
            }
         }

         if (0 < var4 && var4 < var1.length() - 1 && (var5 == ' ' || var3 >= var4) && this.column + (var4 - var3) > this.bestWidth && var2) {
            if (var3 >= var4) {
               var9 = "\\";
            } else {
               var9 = var1.substring(var3, var4) + "\\";
            }

            if (var3 < var4) {
               var3 = var4;
            }

            this.column += var9.length();
            this.stream.write(var9);
            this.writeIndent();
            this.whitespace = false;
            this.indention = false;
            if (var1.charAt(var3) == ' ') {
               var9 = "\\";
               this.column += var9.length();
               this.stream.write(var9);
            }
         }
      }

      this.writeIndicator("\"", false, false, false);
   }

   private String determineBlockHints(String var1) {
      StringBuilder var2 = new StringBuilder();
      if (Constant.LINEBR.has(var1.charAt(0), " ")) {
         var2.append(this.bestIndent);
      }

      char var3 = var1.charAt(var1.length() - 1);
      if (Constant.LINEBR.hasNo(var3)) {
         var2.append("-");
      } else if (var1.length() == 1 || Constant.LINEBR.has(var1.charAt(var1.length() - 2))) {
         var2.append("+");
      }

      return var2.toString();
   }

   void writeFolded(String var1, boolean var2) throws IOException {
      String var3 = this.determineBlockHints(var1);
      this.writeIndicator(">" + var3, true, false, false);
      if (var3.length() > 0 && var3.charAt(var3.length() - 1) == '+') {
         this.openEnded = true;
      }

      this.writeLineBreak((String)null);
      boolean var4 = true;
      boolean var5 = false;
      boolean var6 = true;
      int var7 = 0;

      for(int var8 = 0; var8 <= var1.length(); ++var8) {
         char var9 = 0;
         if (var8 < var1.length()) {
            var9 = var1.charAt(var8);
         }

         if (var6) {
            if (var9 == 0 || Constant.LINEBR.hasNo(var9)) {
               if (!var4 && var9 != 0 && var9 != ' ' && var1.charAt(var7) == '\n') {
                  this.writeLineBreak((String)null);
               }

               var4 = var9 == ' ';
               String var15 = var1.substring(var7, var8);
               char[] var11 = var15.toCharArray();
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  char var14 = var11[var13];
                  if (var14 == '\n') {
                     this.writeLineBreak((String)null);
                  } else {
                     this.writeLineBreak(String.valueOf(var14));
                  }
               }

               if (var9 != 0) {
                  this.writeIndent();
               }

               var7 = var8;
            }
         } else {
            int var10;
            if (var5) {
               if (var9 != ' ') {
                  if (var7 + 1 == var8 && this.column > this.bestWidth && var2) {
                     this.writeIndent();
                  } else {
                     var10 = var8 - var7;
                     this.column += var10;
                     this.stream.write(var1, var7, var10);
                  }

                  var7 = var8;
               }
            } else if (Constant.LINEBR.has(var9, "\u0000 ")) {
               var10 = var8 - var7;
               this.column += var10;
               this.stream.write(var1, var7, var10);
               if (var9 == 0) {
                  this.writeLineBreak((String)null);
               }

               var7 = var8;
            }
         }

         if (var9 != 0) {
            var6 = Constant.LINEBR.has(var9);
            var5 = var9 == ' ';
         }
      }

   }

   void writeLiteral(String var1) throws IOException {
      String var2 = this.determineBlockHints(var1);
      this.writeIndicator("|" + var2, true, false, false);
      if (var2.length() > 0 && var2.charAt(var2.length() - 1) == '+') {
         this.openEnded = true;
      }

      this.writeLineBreak((String)null);
      boolean var3 = true;
      int var4 = 0;

      for(int var5 = 0; var5 <= var1.length(); ++var5) {
         char var6 = 0;
         if (var5 < var1.length()) {
            var6 = var1.charAt(var5);
         }

         if (!var3) {
            if (var6 == 0 || Constant.LINEBR.has(var6)) {
               this.stream.write(var1, var4, var5 - var4);
               if (var6 == 0) {
                  this.writeLineBreak((String)null);
               }

               var4 = var5;
            }
         } else if (var6 == 0 || Constant.LINEBR.hasNo(var6)) {
            String var7 = var1.substring(var4, var5);
            char[] var8 = var7.toCharArray();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               char var11 = var8[var10];
               if (var11 == '\n') {
                  this.writeLineBreak((String)null);
               } else {
                  this.writeLineBreak(String.valueOf(var11));
               }
            }

            if (var6 != 0) {
               this.writeIndent();
            }

            var4 = var5;
         }

         if (var6 != 0) {
            var3 = Constant.LINEBR.has(var6);
         }
      }

   }

   void writePlain(String var1, boolean var2) throws IOException {
      if (this.rootContext) {
         this.openEnded = true;
      }

      if (var1.length() != 0) {
         if (!this.whitespace) {
            ++this.column;
            this.stream.write(SPACE);
         }

         this.whitespace = false;
         this.indention = false;
         boolean var3 = false;
         boolean var4 = false;
         int var5 = 0;

         for(int var6 = 0; var6 <= var1.length(); ++var6) {
            char var7 = 0;
            if (var6 < var1.length()) {
               var7 = var1.charAt(var6);
            }

            int var13;
            if (var3) {
               if (var7 != ' ') {
                  if (var5 + 1 == var6 && this.column > this.bestWidth && var2) {
                     this.writeIndent();
                     this.whitespace = false;
                     this.indention = false;
                  } else {
                     var13 = var6 - var5;
                     this.column += var13;
                     this.stream.write(var1, var5, var13);
                  }

                  var5 = var6;
               }
            } else if (!var4) {
               if (Constant.LINEBR.has(var7, "\u0000 ")) {
                  var13 = var6 - var5;
                  this.column += var13;
                  this.stream.write(var1, var5, var13);
                  var5 = var6;
               }
            } else if (Constant.LINEBR.hasNo(var7)) {
               if (var1.charAt(var5) == '\n') {
                  this.writeLineBreak((String)null);
               }

               String var8 = var1.substring(var5, var6);
               char[] var9 = var8.toCharArray();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  char var12 = var9[var11];
                  if (var12 == '\n') {
                     this.writeLineBreak((String)null);
                  } else {
                     this.writeLineBreak(String.valueOf(var12));
                  }
               }

               this.writeIndent();
               this.whitespace = false;
               this.indention = false;
               var5 = var6;
            }

            if (var7 != 0) {
               var3 = var7 == ' ';
               var4 = Constant.LINEBR.has(var7);
            }
         }

      }
   }

   static Event access$100(Emitter var0) {
      return var0.event;
   }

   static EmitterState access$202(Emitter var0, EmitterState var1) {
      return var0.state = var1;
   }

   static boolean access$400(Emitter var0) {
      return var0.openEnded;
   }

   static String access$500(Emitter var0, DumperOptions.Version var1) {
      return var0.prepareVersion(var1);
   }

   static Map access$602(Emitter var0, Map var1) {
      return var0.tagPrefixes = var1;
   }

   static Map access$700() {
      return DEFAULT_TAG_PREFIXES;
   }

   static Map access$600(Emitter var0) {
      return var0.tagPrefixes;
   }

   static String access$800(Emitter var0, String var1) {
      return var0.prepareTagHandle(var1);
   }

   static String access$900(Emitter var0, String var1) {
      return var0.prepareTagPrefix(var1);
   }

   static Boolean access$1000(Emitter var0) {
      return var0.canonical;
   }

   static boolean access$1100(Emitter var0) {
      return var0.checkEmptyDocument();
   }

   static ArrayStack access$1500(Emitter var0) {
      return var0.states;
   }

   static void access$1600(Emitter var0, boolean var1, boolean var2, boolean var3) throws IOException {
      var0.expectNode(var1, var2, var3);
   }

   static Integer access$1802(Emitter var0, Integer var1) {
      return var0.indent = var1;
   }

   static ArrayStack access$1900(Emitter var0) {
      return var0.indents;
   }

   static int access$2010(Emitter var0) {
      return var0.flowLevel--;
   }

   static int access$2100(Emitter var0) {
      return var0.column;
   }

   static int access$2200(Emitter var0) {
      return var0.bestWidth;
   }

   static boolean access$2300(Emitter var0) {
      return var0.splitLines;
   }

   static Boolean access$2400(Emitter var0) {
      return var0.prettyFlow;
   }

   static boolean access$2700(Emitter var0) {
      return var0.checkSimpleKey();
   }

   static int access$3200(Emitter var0) {
      return var0.indicatorIndent;
   }

   static void access$3300(Emitter var0, int var1) throws IOException {
      var0.writeWhitespace(var1);
   }

   static {
      ESCAPE_REPLACEMENTS.put('\u0000', "0");
      ESCAPE_REPLACEMENTS.put('\u0007', "a");
      ESCAPE_REPLACEMENTS.put('\b', "b");
      ESCAPE_REPLACEMENTS.put('\t', "t");
      ESCAPE_REPLACEMENTS.put('\n', "n");
      ESCAPE_REPLACEMENTS.put('\u000b', "v");
      ESCAPE_REPLACEMENTS.put('\f', "f");
      ESCAPE_REPLACEMENTS.put('\r', "r");
      ESCAPE_REPLACEMENTS.put('\u001b', "e");
      ESCAPE_REPLACEMENTS.put('"', "\"");
      ESCAPE_REPLACEMENTS.put('\\', "\\");
      ESCAPE_REPLACEMENTS.put('\u0085', "N");
      ESCAPE_REPLACEMENTS.put(' ', "_");
      ESCAPE_REPLACEMENTS.put('\u2028', "L");
      ESCAPE_REPLACEMENTS.put('\u2029', "P");
      DEFAULT_TAG_PREFIXES = new LinkedHashMap();
      DEFAULT_TAG_PREFIXES.put("!", "!");
      DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
      HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
      ANCHOR_FORMAT = Pattern.compile("^[-_\\w]*$");
   }

   private class ExpectBlockMappingValue implements EmitterState {
      final Emitter this$0;

      private ExpectBlockMappingValue(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         this.this$0.writeIndent();
         this.this$0.writeIndicator(":", true, false, true);
         this.this$0.states.push(this.this$0.new ExpectBlockMappingKey(this.this$0, false));
         this.this$0.expectNode(false, true, false);
      }

      ExpectBlockMappingValue(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectBlockMappingSimpleValue implements EmitterState {
      final Emitter this$0;

      private ExpectBlockMappingSimpleValue(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         this.this$0.writeIndicator(":", false, false, false);
         this.this$0.states.push(this.this$0.new ExpectBlockMappingKey(this.this$0, false));
         this.this$0.expectNode(false, true, false);
      }

      ExpectBlockMappingSimpleValue(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectBlockMappingKey implements EmitterState {
      private boolean first;
      final Emitter this$0;

      public ExpectBlockMappingKey(Emitter var1, boolean var2) {
         this.this$0 = var1;
         this.first = var2;
      }

      public void expect() throws IOException {
         if (!this.first && this.this$0.event instanceof MappingEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            this.this$0.writeIndent();
            if (this.this$0.checkSimpleKey()) {
               this.this$0.states.push(this.this$0.new ExpectBlockMappingSimpleValue(this.this$0));
               this.this$0.expectNode(false, true, true);
            } else {
               this.this$0.writeIndicator("?", true, false, true);
               this.this$0.states.push(this.this$0.new ExpectBlockMappingValue(this.this$0));
               this.this$0.expectNode(false, true, false);
            }
         }

      }
   }

   private class ExpectFirstBlockMappingKey implements EmitterState {
      final Emitter this$0;

      private ExpectFirstBlockMappingKey(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         (this.this$0.new ExpectBlockMappingKey(this.this$0, true)).expect();
      }

      ExpectFirstBlockMappingKey(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectBlockSequenceItem implements EmitterState {
      private boolean first;
      final Emitter this$0;

      public ExpectBlockSequenceItem(Emitter var1, boolean var2) {
         this.this$0 = var1;
         this.first = var2;
      }

      public void expect() throws IOException {
         if (!this.first && this.this$0.event instanceof SequenceEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            this.this$0.writeIndent();
            this.this$0.writeWhitespace(this.this$0.indicatorIndent);
            this.this$0.writeIndicator("-", true, false, true);
            this.this$0.states.push(this.this$0.new ExpectBlockSequenceItem(this.this$0, false));
            this.this$0.expectNode(false, false, false);
         }

      }
   }

   private class ExpectFirstBlockSequenceItem implements EmitterState {
      final Emitter this$0;

      private ExpectFirstBlockSequenceItem(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         (this.this$0.new ExpectBlockSequenceItem(this.this$0, true)).expect();
      }

      ExpectFirstBlockSequenceItem(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFlowMappingValue implements EmitterState {
      final Emitter this$0;

      private ExpectFlowMappingValue(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth || this.this$0.prettyFlow) {
            this.this$0.writeIndent();
         }

         this.this$0.writeIndicator(":", true, false, false);
         this.this$0.states.push(this.this$0.new ExpectFlowMappingKey(this.this$0));
         this.this$0.expectNode(false, true, false);
      }

      ExpectFlowMappingValue(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFlowMappingSimpleValue implements EmitterState {
      final Emitter this$0;

      private ExpectFlowMappingSimpleValue(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         this.this$0.writeIndicator(":", false, false, false);
         this.this$0.states.push(this.this$0.new ExpectFlowMappingKey(this.this$0));
         this.this$0.expectNode(false, true, false);
      }

      ExpectFlowMappingSimpleValue(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFlowMappingKey implements EmitterState {
      final Emitter this$0;

      private ExpectFlowMappingKey(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof MappingEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.flowLevel--;
            if (this.this$0.canonical) {
               this.this$0.writeIndicator(",", false, false, false);
               this.this$0.writeIndent();
            }

            if (this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            this.this$0.writeIndicator("}", false, false, false);
            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            this.this$0.writeIndicator(",", false, false, false);
            if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines || this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            if (!this.this$0.canonical && this.this$0.checkSimpleKey()) {
               this.this$0.states.push(this.this$0.new ExpectFlowMappingSimpleValue(this.this$0));
               this.this$0.expectNode(false, true, true);
            } else {
               this.this$0.writeIndicator("?", true, false, false);
               this.this$0.states.push(this.this$0.new ExpectFlowMappingValue(this.this$0));
               this.this$0.expectNode(false, true, false);
            }
         }

      }

      ExpectFlowMappingKey(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFirstFlowMappingKey implements EmitterState {
      final Emitter this$0;

      private ExpectFirstFlowMappingKey(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof MappingEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.flowLevel--;
            this.this$0.writeIndicator("}", false, false, false);
            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines || this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            if (!this.this$0.canonical && this.this$0.checkSimpleKey()) {
               this.this$0.states.push(this.this$0.new ExpectFlowMappingSimpleValue(this.this$0));
               this.this$0.expectNode(false, true, true);
            } else {
               this.this$0.writeIndicator("?", true, false, false);
               this.this$0.states.push(this.this$0.new ExpectFlowMappingValue(this.this$0));
               this.this$0.expectNode(false, true, false);
            }
         }

      }

      ExpectFirstFlowMappingKey(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFlowSequenceItem implements EmitterState {
      final Emitter this$0;

      private ExpectFlowSequenceItem(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof SequenceEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.flowLevel--;
            if (this.this$0.canonical) {
               this.this$0.writeIndicator(",", false, false, false);
               this.this$0.writeIndent();
            }

            this.this$0.writeIndicator("]", false, false, false);
            if (this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            this.this$0.writeIndicator(",", false, false, false);
            if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines || this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            this.this$0.states.push(this.this$0.new ExpectFlowSequenceItem(this.this$0));
            this.this$0.expectNode(false, false, false);
         }

      }

      ExpectFlowSequenceItem(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectFirstFlowSequenceItem implements EmitterState {
      final Emitter this$0;

      private ExpectFirstFlowSequenceItem(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof SequenceEndEvent) {
            this.this$0.indent = (Integer)this.this$0.indents.pop();
            this.this$0.flowLevel--;
            this.this$0.writeIndicator("]", false, false, false);
            this.this$0.state = (EmitterState)this.this$0.states.pop();
         } else {
            if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines || this.this$0.prettyFlow) {
               this.this$0.writeIndent();
            }

            this.this$0.states.push(this.this$0.new ExpectFlowSequenceItem(this.this$0));
            this.this$0.expectNode(false, false, false);
         }

      }

      ExpectFirstFlowSequenceItem(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectDocumentRoot implements EmitterState {
      final Emitter this$0;

      private ExpectDocumentRoot(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         this.this$0.states.push(this.this$0.new ExpectDocumentEnd(this.this$0));
         this.this$0.expectNode(true, false, false);
      }

      ExpectDocumentRoot(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectDocumentEnd implements EmitterState {
      final Emitter this$0;

      private ExpectDocumentEnd(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof DocumentEndEvent) {
            this.this$0.writeIndent();
            if (((DocumentEndEvent)this.this$0.event).getExplicit()) {
               this.this$0.writeIndicator("...", true, false, false);
               this.this$0.writeIndent();
            }

            this.this$0.flushStream();
            this.this$0.state = this.this$0.new ExpectDocumentStart(this.this$0, false);
         } else {
            throw new EmitterException("expected DocumentEndEvent, but got " + this.this$0.event);
         }
      }

      ExpectDocumentEnd(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectDocumentStart implements EmitterState {
      private boolean first;
      final Emitter this$0;

      public ExpectDocumentStart(Emitter var1, boolean var2) {
         this.this$0 = var1;
         this.first = var2;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof DocumentStartEvent) {
            DocumentStartEvent var1 = (DocumentStartEvent)this.this$0.event;
            if ((var1.getVersion() != null || var1.getTags() != null) && this.this$0.openEnded) {
               this.this$0.writeIndicator("...", true, false, false);
               this.this$0.writeIndent();
            }

            if (var1.getVersion() != null) {
               String var2 = this.this$0.prepareVersion(var1.getVersion());
               this.this$0.writeVersionDirective(var2);
            }

            this.this$0.tagPrefixes = new LinkedHashMap(Emitter.DEFAULT_TAG_PREFIXES);
            if (var1.getTags() != null) {
               TreeSet var8 = new TreeSet(var1.getTags().keySet());
               Iterator var3 = var8.iterator();

               while(var3.hasNext()) {
                  String var4 = (String)var3.next();
                  String var5 = (String)var1.getTags().get(var4);
                  this.this$0.tagPrefixes.put(var5, var4);
                  String var6 = this.this$0.prepareTagHandle(var4);
                  String var7 = this.this$0.prepareTagPrefix(var5);
                  this.this$0.writeTagDirective(var6, var7);
               }
            }

            boolean var9 = this.first && !var1.getExplicit() && !this.this$0.canonical && var1.getVersion() == null && (var1.getTags() == null || var1.getTags().isEmpty()) && !this.this$0.checkEmptyDocument();
            if (!var9) {
               this.this$0.writeIndent();
               this.this$0.writeIndicator("---", true, false, false);
               if (this.this$0.canonical) {
                  this.this$0.writeIndent();
               }
            }

            this.this$0.state = this.this$0.new ExpectDocumentRoot(this.this$0);
         } else {
            if (!(this.this$0.event instanceof StreamEndEvent)) {
               throw new EmitterException("expected DocumentStartEvent, but got " + this.this$0.event);
            }

            this.this$0.writeStreamEnd();
            this.this$0.state = this.this$0.new ExpectNothing(this.this$0);
         }

      }
   }

   private class ExpectFirstDocumentStart implements EmitterState {
      final Emitter this$0;

      private ExpectFirstDocumentStart(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         (this.this$0.new ExpectDocumentStart(this.this$0, true)).expect();
      }

      ExpectFirstDocumentStart(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectNothing implements EmitterState {
      final Emitter this$0;

      private ExpectNothing(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         throw new EmitterException("expecting nothing, but got " + this.this$0.event);
      }

      ExpectNothing(Emitter var1, Object var2) {
         this(var1);
      }
   }

   private class ExpectStreamStart implements EmitterState {
      final Emitter this$0;

      private ExpectStreamStart(Emitter var1) {
         this.this$0 = var1;
      }

      public void expect() throws IOException {
         if (this.this$0.event instanceof StreamStartEvent) {
            this.this$0.writeStreamStart();
            this.this$0.state = this.this$0.new ExpectFirstDocumentStart(this.this$0);
         } else {
            throw new EmitterException("expected StreamStartEvent, but got " + this.this$0.event);
         }
      }

      ExpectStreamStart(Emitter var1, Object var2) {
         this(var1);
      }
   }
}
