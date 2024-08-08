package org.yaml.snakeyaml.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.ImplicitTuple;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.TagTuple;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.util.ArrayStack;

public class ParserImpl implements Parser {
   private static final Map DEFAULT_TAGS = new HashMap();
   protected final Scanner scanner;
   private Event currentEvent;
   private final ArrayStack states;
   private final ArrayStack marks;
   private Production state;
   private VersionTagsTuple directives;

   public ParserImpl(StreamReader var1) {
      this((Scanner)(new ScannerImpl(var1)));
   }

   public ParserImpl(Scanner var1) {
      this.scanner = var1;
      this.currentEvent = null;
      this.directives = new VersionTagsTuple((DumperOptions.Version)null, new HashMap(DEFAULT_TAGS));
      this.states = new ArrayStack(100);
      this.marks = new ArrayStack(10);
      this.state = new ParserImpl.ParseStreamStart(this);
   }

   public boolean checkEvent(Event.ID var1) {
      this.peekEvent();
      return this.currentEvent != null && this.currentEvent.is(var1);
   }

   public Event peekEvent() {
      if (this.currentEvent == null && this.state != null) {
         this.currentEvent = this.state.produce();
      }

      return this.currentEvent;
   }

   public Event getEvent() {
      this.peekEvent();
      Event var1 = this.currentEvent;
      this.currentEvent = null;
      return var1;
   }

   private VersionTagsTuple processDirectives() {
      DumperOptions.Version var1 = null;
      HashMap var2 = new HashMap();

      while(this.scanner.checkToken(Token.ID.Directive)) {
         DirectiveToken var3 = (DirectiveToken)this.scanner.getToken();
         List var4;
         if (var3.getName().equals("YAML")) {
            if (var1 != null) {
               throw new ParserException((String)null, (Mark)null, "found duplicate YAML directive", var3.getStartMark());
            }

            var4 = var3.getValue();
            Integer var5 = (Integer)var4.get(0);
            if (var5 != 1) {
               throw new ParserException((String)null, (Mark)null, "found incompatible YAML document (version 1.* is required)", var3.getStartMark());
            }

            Integer var6 = (Integer)var4.get(1);
            switch(var6) {
            case 0:
               var1 = DumperOptions.Version.V1_0;
               break;
            default:
               var1 = DumperOptions.Version.V1_1;
            }
         } else if (var3.getName().equals("TAG")) {
            var4 = var3.getValue();
            String var9 = (String)var4.get(0);
            String var10 = (String)var4.get(1);
            if (var2.containsKey(var9)) {
               throw new ParserException((String)null, (Mark)null, "duplicate tag handle " + var9, var3.getStartMark());
            }

            var2.put(var9, var10);
         }
      }

      if (var1 != null || !var2.isEmpty()) {
         Iterator var7 = DEFAULT_TAGS.keySet().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            if (!var2.containsKey(var8)) {
               var2.put(var8, DEFAULT_TAGS.get(var8));
            }
         }

         this.directives = new VersionTagsTuple(var1, var2);
      }

      return this.directives;
   }

   private Event parseFlowNode() {
      return this.parseNode(false, false);
   }

   private Event parseBlockNodeOrIndentlessSequence() {
      return this.parseNode(true, true);
   }

   private Event parseNode(boolean var1, boolean var2) {
      Mark var4 = null;
      Mark var5 = null;
      Mark var6 = null;
      Object var3;
      if (this.scanner.checkToken(Token.ID.Alias)) {
         AliasToken var7 = (AliasToken)this.scanner.getToken();
         var3 = new AliasEvent(var7.getValue(), var7.getStartMark(), var7.getEndMark());
         this.state = (Production)this.states.pop();
      } else {
         String var13 = null;
         TagTuple var8 = null;
         if (this.scanner.checkToken(Token.ID.Anchor)) {
            AnchorToken var9 = (AnchorToken)this.scanner.getToken();
            var4 = var9.getStartMark();
            var5 = var9.getEndMark();
            var13 = var9.getValue();
            if (this.scanner.checkToken(Token.ID.Tag)) {
               TagToken var10 = (TagToken)this.scanner.getToken();
               var6 = var10.getStartMark();
               var5 = var10.getEndMark();
               var8 = var10.getValue();
            }
         } else if (this.scanner.checkToken(Token.ID.Tag)) {
            TagToken var14 = (TagToken)this.scanner.getToken();
            var4 = var14.getStartMark();
            var6 = var4;
            var5 = var14.getEndMark();
            var8 = var14.getValue();
            if (this.scanner.checkToken(Token.ID.Anchor)) {
               AnchorToken var16 = (AnchorToken)this.scanner.getToken();
               var5 = var16.getEndMark();
               var13 = var16.getValue();
            }
         }

         String var15 = null;
         String var11;
         if (var8 != null) {
            String var17 = var8.getHandle();
            var11 = var8.getSuffix();
            if (var17 != null) {
               if (!this.directives.getTags().containsKey(var17)) {
                  throw new ParserException("while parsing a node", var4, "found undefined tag handle " + var17, var6);
               }

               var15 = (String)this.directives.getTags().get(var17) + var11;
            } else {
               var15 = var11;
            }
         }

         if (var4 == null) {
            var4 = this.scanner.peekToken().getStartMark();
            var5 = var4;
         }

         var3 = null;
         boolean var18 = var15 == null || var15.equals("!");
         if (var2 && this.scanner.checkToken(Token.ID.BlockEntry)) {
            var5 = this.scanner.peekToken().getEndMark();
            var3 = new SequenceStartEvent(var13, var15, var18, var4, var5, Boolean.FALSE);
            this.state = new ParserImpl.ParseIndentlessSequenceEntry(this);
         } else if (this.scanner.checkToken(Token.ID.Scalar)) {
            ScalarToken var19 = (ScalarToken)this.scanner.getToken();
            var5 = var19.getEndMark();
            ImplicitTuple var12;
            if ((!var19.getPlain() || var15 != null) && !"!".equals(var15)) {
               if (var15 == null) {
                  var12 = new ImplicitTuple(false, true);
               } else {
                  var12 = new ImplicitTuple(false, false);
               }
            } else {
               var12 = new ImplicitTuple(true, false);
            }

            var3 = new ScalarEvent(var13, var15, var12, var19.getValue(), var4, var5, var19.getStyle());
            this.state = (Production)this.states.pop();
         } else if (this.scanner.checkToken(Token.ID.FlowSequenceStart)) {
            var5 = this.scanner.peekToken().getEndMark();
            var3 = new SequenceStartEvent(var13, var15, var18, var4, var5, Boolean.TRUE);
            this.state = new ParserImpl.ParseFlowSequenceFirstEntry(this);
         } else if (this.scanner.checkToken(Token.ID.FlowMappingStart)) {
            var5 = this.scanner.peekToken().getEndMark();
            var3 = new MappingStartEvent(var13, var15, var18, var4, var5, Boolean.TRUE);
            this.state = new ParserImpl.ParseFlowMappingFirstKey(this);
         } else if (var1 && this.scanner.checkToken(Token.ID.BlockSequenceStart)) {
            var5 = this.scanner.peekToken().getStartMark();
            var3 = new SequenceStartEvent(var13, var15, var18, var4, var5, Boolean.FALSE);
            this.state = new ParserImpl.ParseBlockSequenceFirstEntry(this);
         } else if (var1 && this.scanner.checkToken(Token.ID.BlockMappingStart)) {
            var5 = this.scanner.peekToken().getStartMark();
            var3 = new MappingStartEvent(var13, var15, var18, var4, var5, Boolean.FALSE);
            this.state = new ParserImpl.ParseBlockMappingFirstKey(this);
         } else {
            if (var13 == null && var15 == null) {
               if (var1) {
                  var11 = "block";
               } else {
                  var11 = "flow";
               }

               Token var20 = this.scanner.peekToken();
               throw new ParserException("while parsing a " + var11 + " node", var4, "expected the node content, but found " + var20.getTokenId(), var20.getStartMark());
            }

            var3 = new ScalarEvent(var13, var15, new ImplicitTuple(var18, false), "", var4, var5, '\u0000');
            this.state = (Production)this.states.pop();
         }
      }

      return (Event)var3;
   }

   private Event processEmptyScalar(Mark var1) {
      return new ScalarEvent((String)null, (String)null, new ImplicitTuple(true, false), "", var1, var1, '\u0000');
   }

   static Production access$102(ParserImpl var0, Production var1) {
      return var0.state = var1;
   }

   static VersionTagsTuple access$302(ParserImpl var0, VersionTagsTuple var1) {
      return var0.directives = var1;
   }

   static Map access$400() {
      return DEFAULT_TAGS;
   }

   static ArrayStack access$600(ParserImpl var0) {
      return var0.states;
   }

   static VersionTagsTuple access$900(ParserImpl var0) {
      return var0.processDirectives();
   }

   static ArrayStack access$1100(ParserImpl var0) {
      return var0.marks;
   }

   static Event access$1200(ParserImpl var0, Mark var1) {
      return var0.processEmptyScalar(var1);
   }

   static Event access$1300(ParserImpl var0, boolean var1, boolean var2) {
      return var0.parseNode(var1, var2);
   }

   static Event access$2200(ParserImpl var0) {
      return var0.parseBlockNodeOrIndentlessSequence();
   }

   static Event access$2400(ParserImpl var0) {
      return var0.parseFlowNode();
   }

   static {
      DEFAULT_TAGS.put("!", "!");
      DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
   }

   private class ParseFlowMappingEmptyValue implements Production {
      final ParserImpl this$0;

      private ParseFlowMappingEmptyValue(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         this.this$0.state = this.this$0.new ParseFlowMappingKey(this.this$0, false);
         return this.this$0.processEmptyScalar(this.this$0.scanner.peekToken().getStartMark());
      }

      ParseFlowMappingEmptyValue(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowMappingValue implements Production {
      final ParserImpl this$0;

      private ParseFlowMappingValue(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1;
         if (this.this$0.scanner.checkToken(Token.ID.Value)) {
            var1 = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
               this.this$0.states.push(this.this$0.new ParseFlowMappingKey(this.this$0, false));
               return this.this$0.parseFlowNode();
            } else {
               this.this$0.state = this.this$0.new ParseFlowMappingKey(this.this$0, false);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }
         } else {
            this.this$0.state = this.this$0.new ParseFlowMappingKey(this.this$0, false);
            var1 = this.this$0.scanner.peekToken();
            return this.this$0.processEmptyScalar(var1.getStartMark());
         }
      }

      ParseFlowMappingValue(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowMappingKey implements Production {
      private boolean first;
      final ParserImpl this$0;

      public ParseFlowMappingKey(ParserImpl var1, boolean var2) {
         this.this$0 = var1;
         this.first = false;
         this.first = var2;
      }

      public Event produce() {
         Token var1;
         if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
            if (!this.first) {
               if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                  var1 = this.this$0.scanner.peekToken();
                  throw new ParserException("while parsing a flow mapping", (Mark)this.this$0.marks.pop(), "expected ',' or '}', but got " + var1.getTokenId(), var1.getStartMark());
               }

               this.this$0.scanner.getToken();
            }

            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
               var1 = this.this$0.scanner.getToken();
               if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                  this.this$0.states.push(this.this$0.new ParseFlowMappingValue(this.this$0));
                  return this.this$0.parseFlowNode();
               }

               this.this$0.state = this.this$0.new ParseFlowMappingValue(this.this$0);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }

            if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
               this.this$0.states.push(this.this$0.new ParseFlowMappingEmptyValue(this.this$0));
               return this.this$0.parseFlowNode();
            }
         }

         var1 = this.this$0.scanner.getToken();
         MappingEndEvent var2 = new MappingEndEvent(var1.getStartMark(), var1.getEndMark());
         this.this$0.state = (Production)this.this$0.states.pop();
         this.this$0.marks.pop();
         return var2;
      }
   }

   private class ParseFlowMappingFirstKey implements Production {
      final ParserImpl this$0;

      private ParseFlowMappingFirstKey(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.getToken();
         this.this$0.marks.push(var1.getStartMark());
         return (this.this$0.new ParseFlowMappingKey(this.this$0, true)).produce();
      }

      ParseFlowMappingFirstKey(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowSequenceEntryMappingEnd implements Production {
      final ParserImpl this$0;

      private ParseFlowSequenceEntryMappingEnd(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         this.this$0.state = this.this$0.new ParseFlowSequenceEntry(this.this$0, false);
         Token var1 = this.this$0.scanner.peekToken();
         return new MappingEndEvent(var1.getStartMark(), var1.getEndMark());
      }

      ParseFlowSequenceEntryMappingEnd(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowSequenceEntryMappingValue implements Production {
      final ParserImpl this$0;

      private ParseFlowSequenceEntryMappingValue(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1;
         if (this.this$0.scanner.checkToken(Token.ID.Value)) {
            var1 = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
               this.this$0.states.push(this.this$0.new ParseFlowSequenceEntryMappingEnd(this.this$0));
               return this.this$0.parseFlowNode();
            } else {
               this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingEnd(this.this$0);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }
         } else {
            this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingEnd(this.this$0);
            var1 = this.this$0.scanner.peekToken();
            return this.this$0.processEmptyScalar(var1.getStartMark());
         }
      }

      ParseFlowSequenceEntryMappingValue(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowSequenceEntryMappingKey implements Production {
      final ParserImpl this$0;

      private ParseFlowSequenceEntryMappingKey(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.getToken();
         if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
            this.this$0.states.push(this.this$0.new ParseFlowSequenceEntryMappingValue(this.this$0));
            return this.this$0.parseFlowNode();
         } else {
            this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingValue(this.this$0);
            return this.this$0.processEmptyScalar(var1.getEndMark());
         }
      }

      ParseFlowSequenceEntryMappingKey(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseFlowSequenceEntry implements Production {
      private boolean first;
      final ParserImpl this$0;

      public ParseFlowSequenceEntry(ParserImpl var1, boolean var2) {
         this.this$0 = var1;
         this.first = false;
         this.first = var2;
      }

      public Event produce() {
         Token var1;
         if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
            if (!this.first) {
               if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                  var1 = this.this$0.scanner.peekToken();
                  throw new ParserException("while parsing a flow sequence", (Mark)this.this$0.marks.pop(), "expected ',' or ']', but got " + var1.getTokenId(), var1.getStartMark());
               }

               this.this$0.scanner.getToken();
            }

            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
               var1 = this.this$0.scanner.peekToken();
               MappingStartEvent var3 = new MappingStartEvent((String)null, (String)null, true, var1.getStartMark(), var1.getEndMark(), Boolean.TRUE);
               this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingKey(this.this$0);
               return var3;
            }

            if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
               this.this$0.states.push(this.this$0.new ParseFlowSequenceEntry(this.this$0, false));
               return this.this$0.parseFlowNode();
            }
         }

         var1 = this.this$0.scanner.getToken();
         SequenceEndEvent var2 = new SequenceEndEvent(var1.getStartMark(), var1.getEndMark());
         this.this$0.state = (Production)this.this$0.states.pop();
         this.this$0.marks.pop();
         return var2;
      }
   }

   private class ParseFlowSequenceFirstEntry implements Production {
      final ParserImpl this$0;

      private ParseFlowSequenceFirstEntry(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.getToken();
         this.this$0.marks.push(var1.getStartMark());
         return (this.this$0.new ParseFlowSequenceEntry(this.this$0, true)).produce();
      }

      ParseFlowSequenceFirstEntry(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockMappingValue implements Production {
      final ParserImpl this$0;

      private ParseBlockMappingValue(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1;
         if (this.this$0.scanner.checkToken(Token.ID.Value)) {
            var1 = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
               this.this$0.states.push(this.this$0.new ParseBlockMappingKey(this.this$0));
               return this.this$0.parseBlockNodeOrIndentlessSequence();
            } else {
               this.this$0.state = this.this$0.new ParseBlockMappingKey(this.this$0);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }
         } else {
            this.this$0.state = this.this$0.new ParseBlockMappingKey(this.this$0);
            var1 = this.this$0.scanner.peekToken();
            return this.this$0.processEmptyScalar(var1.getStartMark());
         }
      }

      ParseBlockMappingValue(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockMappingKey implements Production {
      final ParserImpl this$0;

      private ParseBlockMappingKey(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1;
         if (this.this$0.scanner.checkToken(Token.ID.Key)) {
            var1 = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
               this.this$0.states.push(this.this$0.new ParseBlockMappingValue(this.this$0));
               return this.this$0.parseBlockNodeOrIndentlessSequence();
            } else {
               this.this$0.state = this.this$0.new ParseBlockMappingValue(this.this$0);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }
         } else if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
            var1 = this.this$0.scanner.peekToken();
            throw new ParserException("while parsing a block mapping", (Mark)this.this$0.marks.pop(), "expected <block end>, but found " + var1.getTokenId(), var1.getStartMark());
         } else {
            var1 = this.this$0.scanner.getToken();
            MappingEndEvent var2 = new MappingEndEvent(var1.getStartMark(), var1.getEndMark());
            this.this$0.state = (Production)this.this$0.states.pop();
            this.this$0.marks.pop();
            return var2;
         }
      }

      ParseBlockMappingKey(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockMappingFirstKey implements Production {
      final ParserImpl this$0;

      private ParseBlockMappingFirstKey(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.getToken();
         this.this$0.marks.push(var1.getStartMark());
         return (this.this$0.new ParseBlockMappingKey(this.this$0)).produce();
      }

      ParseBlockMappingFirstKey(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseIndentlessSequenceEntry implements Production {
      final ParserImpl this$0;

      private ParseIndentlessSequenceEntry(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1;
         if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
            var1 = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
               this.this$0.states.push(this.this$0.new ParseIndentlessSequenceEntry(this.this$0));
               return (this.this$0.new ParseBlockNode(this.this$0)).produce();
            } else {
               this.this$0.state = this.this$0.new ParseIndentlessSequenceEntry(this.this$0);
               return this.this$0.processEmptyScalar(var1.getEndMark());
            }
         } else {
            var1 = this.this$0.scanner.peekToken();
            SequenceEndEvent var2 = new SequenceEndEvent(var1.getStartMark(), var1.getEndMark());
            this.this$0.state = (Production)this.this$0.states.pop();
            return var2;
         }
      }

      ParseIndentlessSequenceEntry(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockSequenceEntry implements Production {
      final ParserImpl this$0;

      private ParseBlockSequenceEntry(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
            BlockEntryToken var3 = (BlockEntryToken)this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
               this.this$0.states.push(this.this$0.new ParseBlockSequenceEntry(this.this$0));
               return (this.this$0.new ParseBlockNode(this.this$0)).produce();
            } else {
               this.this$0.state = this.this$0.new ParseBlockSequenceEntry(this.this$0);
               return this.this$0.processEmptyScalar(var3.getEndMark());
            }
         } else {
            Token var1;
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
               var1 = this.this$0.scanner.peekToken();
               throw new ParserException("while parsing a block collection", (Mark)this.this$0.marks.pop(), "expected <block end>, but found " + var1.getTokenId(), var1.getStartMark());
            } else {
               var1 = this.this$0.scanner.getToken();
               SequenceEndEvent var2 = new SequenceEndEvent(var1.getStartMark(), var1.getEndMark());
               this.this$0.state = (Production)this.this$0.states.pop();
               this.this$0.marks.pop();
               return var2;
            }
         }
      }

      ParseBlockSequenceEntry(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockSequenceFirstEntry implements Production {
      final ParserImpl this$0;

      private ParseBlockSequenceFirstEntry(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.getToken();
         this.this$0.marks.push(var1.getStartMark());
         return (this.this$0.new ParseBlockSequenceEntry(this.this$0)).produce();
      }

      ParseBlockSequenceFirstEntry(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseBlockNode implements Production {
      final ParserImpl this$0;

      private ParseBlockNode(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         return this.this$0.parseNode(true, false);
      }

      ParseBlockNode(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseDocumentContent implements Production {
      final ParserImpl this$0;

      private ParseDocumentContent(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         if (this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
            Event var1 = this.this$0.processEmptyScalar(this.this$0.scanner.peekToken().getStartMark());
            this.this$0.state = (Production)this.this$0.states.pop();
            return var1;
         } else {
            ParserImpl.ParseBlockNode var2 = this.this$0.new ParseBlockNode(this.this$0);
            return var2.produce();
         }
      }

      ParseDocumentContent(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseDocumentEnd implements Production {
      final ParserImpl this$0;

      private ParseDocumentEnd(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         Token var1 = this.this$0.scanner.peekToken();
         Mark var2 = var1.getStartMark();
         Mark var3 = var2;
         boolean var4 = false;
         if (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
            var1 = this.this$0.scanner.getToken();
            var3 = var1.getEndMark();
            var4 = true;
         }

         DocumentEndEvent var5 = new DocumentEndEvent(var2, var3, var4);
         this.this$0.state = this.this$0.new ParseDocumentStart(this.this$0);
         return var5;
      }

      ParseDocumentEnd(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseDocumentStart implements Production {
      final ParserImpl this$0;

      private ParseDocumentStart(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         while(this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
            this.this$0.scanner.getToken();
         }

         Object var1;
         if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
            Token var2 = this.this$0.scanner.peekToken();
            Mark var3 = var2.getStartMark();
            VersionTagsTuple var4 = this.this$0.processDirectives();
            if (!this.this$0.scanner.checkToken(Token.ID.DocumentStart)) {
               throw new ParserException((String)null, (Mark)null, "expected '<document start>', but found " + this.this$0.scanner.peekToken().getTokenId(), this.this$0.scanner.peekToken().getStartMark());
            }

            var2 = this.this$0.scanner.getToken();
            Mark var5 = var2.getEndMark();
            var1 = new DocumentStartEvent(var3, var5, true, var4.getVersion(), var4.getTags());
            this.this$0.states.push(this.this$0.new ParseDocumentEnd(this.this$0));
            this.this$0.state = this.this$0.new ParseDocumentContent(this.this$0);
         } else {
            StreamEndToken var6 = (StreamEndToken)this.this$0.scanner.getToken();
            var1 = new StreamEndEvent(var6.getStartMark(), var6.getEndMark());
            if (!this.this$0.states.isEmpty()) {
               throw new YAMLException("Unexpected end of stream. States left: " + this.this$0.states);
            }

            if (!this.this$0.marks.isEmpty()) {
               throw new YAMLException("Unexpected end of stream. Marks left: " + this.this$0.marks);
            }

            this.this$0.state = null;
         }

         return (Event)var1;
      }

      ParseDocumentStart(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseImplicitDocumentStart implements Production {
      final ParserImpl this$0;

      private ParseImplicitDocumentStart(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         if (!this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
            this.this$0.directives = new VersionTagsTuple((DumperOptions.Version)null, ParserImpl.DEFAULT_TAGS);
            Token var5 = this.this$0.scanner.peekToken();
            Mark var2 = var5.getStartMark();
            DocumentStartEvent var4 = new DocumentStartEvent(var2, var2, false, (DumperOptions.Version)null, (Map)null);
            this.this$0.states.push(this.this$0.new ParseDocumentEnd(this.this$0));
            this.this$0.state = this.this$0.new ParseBlockNode(this.this$0);
            return var4;
         } else {
            ParserImpl.ParseDocumentStart var1 = this.this$0.new ParseDocumentStart(this.this$0);
            return var1.produce();
         }
      }

      ParseImplicitDocumentStart(ParserImpl var1, Object var2) {
         this(var1);
      }
   }

   private class ParseStreamStart implements Production {
      final ParserImpl this$0;

      private ParseStreamStart(ParserImpl var1) {
         this.this$0 = var1;
      }

      public Event produce() {
         StreamStartToken var1 = (StreamStartToken)this.this$0.scanner.getToken();
         StreamStartEvent var2 = new StreamStartEvent(var1.getStartMark(), var1.getEndMark());
         this.this$0.state = this.this$0.new ParseImplicitDocumentStart(this.this$0);
         return var2;
      }

      ParseStreamStart(ParserImpl var1, Object var2) {
         this(var1);
      }
   }
}
