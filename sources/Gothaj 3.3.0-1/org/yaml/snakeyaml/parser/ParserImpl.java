package org.yaml.snakeyaml.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CommentEvent;
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
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.TagTuple;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.util.ArrayStack;

public class ParserImpl implements Parser {
   private static final Map<String, String> DEFAULT_TAGS = new HashMap<>();
   protected final Scanner scanner;
   private Event currentEvent;
   private final ArrayStack<Production> states;
   private final ArrayStack<Mark> marks;
   private Production state;
   private VersionTagsTuple directives;

   public ParserImpl(StreamReader reader, LoaderOptions options) {
      this(new ScannerImpl(reader, options));
   }

   public ParserImpl(Scanner scanner) {
      this.scanner = scanner;
      this.currentEvent = null;
      this.directives = new VersionTagsTuple(null, new HashMap<>(DEFAULT_TAGS));
      this.states = new ArrayStack<>(100);
      this.marks = new ArrayStack<>(10);
      this.state = new ParserImpl.ParseStreamStart();
   }

   @Override
   public boolean checkEvent(Event.ID choice) {
      this.peekEvent();
      return this.currentEvent != null && this.currentEvent.is(choice);
   }

   @Override
   public Event peekEvent() {
      if (this.currentEvent == null && this.state != null) {
         this.currentEvent = this.state.produce();
      }

      return this.currentEvent;
   }

   @Override
   public Event getEvent() {
      this.peekEvent();
      Event value = this.currentEvent;
      this.currentEvent = null;
      return value;
   }

   private CommentEvent produceCommentEvent(CommentToken token) {
      Mark startMark = token.getStartMark();
      Mark endMark = token.getEndMark();
      String value = token.getValue();
      CommentType type = token.getCommentType();
      return new CommentEvent(type, value, startMark, endMark);
   }

   private VersionTagsTuple processDirectives() {
      HashMap<String, String> tagHandles = new HashMap<>(this.directives.getTags());

      for (String key : DEFAULT_TAGS.keySet()) {
         tagHandles.remove(key);
      }

      this.directives = new VersionTagsTuple(null, tagHandles);

      while (this.scanner.checkToken(Token.ID.Directive)) {
         DirectiveToken token = (DirectiveToken)this.scanner.getToken();
         if (token.getName().equals("YAML")) {
            if (this.directives.getVersion() != null) {
               throw new ParserException(null, null, "found duplicate YAML directive", token.getStartMark());
            }

            List<Integer> value = token.getValue();
            Integer major = value.get(0);
            if (major != 1) {
               throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token.getStartMark());
            }

            Integer minor = value.get(1);
            if (minor == 0) {
               this.directives = new VersionTagsTuple(DumperOptions.Version.V1_0, tagHandles);
            } else {
               this.directives = new VersionTagsTuple(DumperOptions.Version.V1_1, tagHandles);
            }
         } else if (token.getName().equals("TAG")) {
            List<String> valuex = token.getValue();
            String handle = valuex.get(0);
            String prefix = valuex.get(1);
            if (tagHandles.containsKey(handle)) {
               throw new ParserException(null, null, "duplicate tag handle " + handle, token.getStartMark());
            }

            tagHandles.put(handle, prefix);
         }
      }

      HashMap<String, String> detectedTagHandles = new HashMap<>();
      if (!tagHandles.isEmpty()) {
         detectedTagHandles = new HashMap<>(tagHandles);
      }

      for (String key : DEFAULT_TAGS.keySet()) {
         if (!tagHandles.containsKey(key)) {
            tagHandles.put(key, DEFAULT_TAGS.get(key));
         }
      }

      return new VersionTagsTuple(this.directives.getVersion(), detectedTagHandles);
   }

   private Event parseFlowNode() {
      return this.parseNode(false, false);
   }

   private Event parseBlockNodeOrIndentlessSequence() {
      return this.parseNode(true, true);
   }

   private Event parseNode(boolean block, boolean indentlessSequence) {
      Mark startMark = null;
      Mark endMark = null;
      Mark tagMark = null;
      Event event;
      if (this.scanner.checkToken(Token.ID.Alias)) {
         AliasToken token = (AliasToken)this.scanner.getToken();
         event = new AliasEvent(token.getValue(), token.getStartMark(), token.getEndMark());
         this.state = this.states.pop();
      } else {
         String anchor = null;
         TagTuple tagTokenTag = null;
         if (this.scanner.checkToken(Token.ID.Anchor)) {
            AnchorToken token = (AnchorToken)this.scanner.getToken();
            startMark = token.getStartMark();
            endMark = token.getEndMark();
            anchor = token.getValue();
            if (this.scanner.checkToken(Token.ID.Tag)) {
               TagToken tagToken = (TagToken)this.scanner.getToken();
               tagMark = tagToken.getStartMark();
               endMark = tagToken.getEndMark();
               tagTokenTag = tagToken.getValue();
            }
         } else if (this.scanner.checkToken(Token.ID.Tag)) {
            TagToken tagToken = (TagToken)this.scanner.getToken();
            startMark = tagToken.getStartMark();
            tagMark = startMark;
            endMark = tagToken.getEndMark();
            tagTokenTag = tagToken.getValue();
            if (this.scanner.checkToken(Token.ID.Anchor)) {
               AnchorToken token = (AnchorToken)this.scanner.getToken();
               endMark = token.getEndMark();
               anchor = token.getValue();
            }
         }

         String tag = null;
         if (tagTokenTag != null) {
            String handle = tagTokenTag.getHandle();
            String suffix = tagTokenTag.getSuffix();
            if (handle != null) {
               if (!this.directives.getTags().containsKey(handle)) {
                  throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
               }

               tag = this.directives.getTags().get(handle) + suffix;
            } else {
               tag = suffix;
            }
         }

         if (startMark == null) {
            startMark = this.scanner.peekToken().getStartMark();
            endMark = startMark;
         }

         event = null;
         boolean implicit = tag == null || tag.equals("!");
         if (indentlessSequence && this.scanner.checkToken(Token.ID.BlockEntry)) {
            endMark = this.scanner.peekToken().getEndMark();
            event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
            this.state = new ParserImpl.ParseIndentlessSequenceEntryKey();
         } else if (this.scanner.checkToken(Token.ID.Scalar)) {
            ScalarToken token = (ScalarToken)this.scanner.getToken();
            endMark = token.getEndMark();
            ImplicitTuple implicitValues;
            if ((!token.getPlain() || tag != null) && !"!".equals(tag)) {
               if (tag == null) {
                  implicitValues = new ImplicitTuple(false, true);
               } else {
                  implicitValues = new ImplicitTuple(false, false);
               }
            } else {
               implicitValues = new ImplicitTuple(true, false);
            }

            event = new ScalarEvent(anchor, tag, implicitValues, token.getValue(), startMark, endMark, token.getStyle());
            this.state = this.states.pop();
         } else if (this.scanner.checkToken(Token.ID.FlowSequenceStart)) {
            endMark = this.scanner.peekToken().getEndMark();
            event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.FLOW);
            this.state = new ParserImpl.ParseFlowSequenceFirstEntry();
         } else if (this.scanner.checkToken(Token.ID.FlowMappingStart)) {
            endMark = this.scanner.peekToken().getEndMark();
            event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.FLOW);
            this.state = new ParserImpl.ParseFlowMappingFirstKey();
         } else if (block && this.scanner.checkToken(Token.ID.BlockSequenceStart)) {
            endMark = this.scanner.peekToken().getStartMark();
            event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
            this.state = new ParserImpl.ParseBlockSequenceFirstEntry();
         } else if (block && this.scanner.checkToken(Token.ID.BlockMappingStart)) {
            endMark = this.scanner.peekToken().getStartMark();
            event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
            this.state = new ParserImpl.ParseBlockMappingFirstKey();
         } else {
            if (anchor == null && tag == null) {
               Token token = this.scanner.peekToken();
               throw new ParserException(
                  "while parsing a " + (block ? "block" : "flow") + " node",
                  startMark,
                  "expected the node content, but found '" + token.getTokenId() + "'",
                  token.getStartMark()
               );
            }

            event = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, DumperOptions.ScalarStyle.PLAIN);
            this.state = this.states.pop();
         }
      }

      return event;
   }

   private Event processEmptyScalar(Mark mark) {
      return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, DumperOptions.ScalarStyle.PLAIN);
   }

   static {
      DEFAULT_TAGS.put("!", "!");
      DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
   }

   private class ParseBlockMappingFirstKey implements Production {
      private ParseBlockMappingFirstKey() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.getToken();
         ParserImpl.this.marks.push(token.getStartMark());
         return ParserImpl.this.new ParseBlockMappingKey().produce();
      }
   }

   private class ParseBlockMappingKey implements Production {
      private ParseBlockMappingKey() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
               ParserImpl.this.states.push(ParserImpl.this.new ParseBlockMappingValue());
               return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingValue();
               return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEnd)) {
            Token token = ParserImpl.this.scanner.peekToken();
            throw new ParserException(
               "while parsing a block mapping",
               ParserImpl.this.marks.pop(),
               "expected <block end>, but found '" + token.getTokenId() + "'",
               token.getStartMark()
            );
         } else {
            Token token = ParserImpl.this.scanner.getToken();
            Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.state = ParserImpl.this.states.pop();
            ParserImpl.this.marks.pop();
            return event;
         }
      }
   }

   private class ParseBlockMappingValue implements Production {
      private ParseBlockMappingValue() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
               ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingValueComment();
               return ParserImpl.this.state.produce();
            } else if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
               ParserImpl.this.states.push(ParserImpl.this.new ParseBlockMappingKey());
               return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingKey();
               return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
         } else if (ParserImpl.this.scanner.checkToken(Token.ID.Scalar)) {
            ParserImpl.this.states.push(ParserImpl.this.new ParseBlockMappingKey());
            return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingKey();
            Token token = ParserImpl.this.scanner.peekToken();
            return ParserImpl.this.processEmptyScalar(token.getStartMark());
         }
      }
   }

   private class ParseBlockMappingValueComment implements Production {
      List<CommentToken> tokens = new LinkedList<>();

      private ParseBlockMappingValueComment() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            this.tokens.add((CommentToken)ParserImpl.this.scanner.getToken());
            return this.produce();
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            if (!this.tokens.isEmpty()) {
               return ParserImpl.this.produceCommentEvent(this.tokens.remove(0));
            } else {
               ParserImpl.this.states.push(ParserImpl.this.new ParseBlockMappingKey());
               return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockMappingValueCommentList(this.tokens);
            return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
         }
      }
   }

   private class ParseBlockMappingValueCommentList implements Production {
      List<CommentToken> tokens;

      public ParseBlockMappingValueCommentList(List<CommentToken> tokens) {
         this.tokens = tokens;
      }

      @Override
      public Event produce() {
         return (Event)(!this.tokens.isEmpty()
            ? ParserImpl.this.produceCommentEvent(this.tokens.remove(0))
            : ParserImpl.this.new ParseBlockMappingKey().produce());
      }
   }

   private class ParseBlockNode implements Production {
      private ParseBlockNode() {
      }

      @Override
      public Event produce() {
         return ParserImpl.this.parseNode(true, false);
      }
   }

   private class ParseBlockSequenceEntryKey implements Production {
      private ParseBlockSequenceEntryKey() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockSequenceEntryKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry)) {
            BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
            return ParserImpl.this.new ParseBlockSequenceEntryValue(token).produce();
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEnd)) {
            Token token = ParserImpl.this.scanner.peekToken();
            throw new ParserException(
               "while parsing a block collection",
               ParserImpl.this.marks.pop(),
               "expected <block end>, but found '" + token.getTokenId() + "'",
               token.getStartMark()
            );
         } else {
            Token token = ParserImpl.this.scanner.getToken();
            Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.state = ParserImpl.this.states.pop();
            ParserImpl.this.marks.pop();
            return event;
         }
      }
   }

   private class ParseBlockSequenceEntryValue implements Production {
      BlockEntryToken token;

      public ParseBlockSequenceEntryValue(BlockEntryToken token) {
         this.token = token;
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockSequenceEntryValue(this.token);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
            ParserImpl.this.states.push(ParserImpl.this.new ParseBlockSequenceEntryKey());
            return ParserImpl.this.new ParseBlockNode().produce();
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseBlockSequenceEntryKey();
            return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
         }
      }
   }

   private class ParseBlockSequenceFirstEntry implements Production {
      private ParseBlockSequenceFirstEntry() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.getToken();
         ParserImpl.this.marks.push(token.getStartMark());
         return ParserImpl.this.new ParseBlockSequenceEntryKey().produce();
      }
   }

   private class ParseDocumentContent implements Production {
      private ParseDocumentContent() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseDocumentContent();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (ParserImpl.this.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
            Event event = ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
            ParserImpl.this.state = ParserImpl.this.states.pop();
            return event;
         } else {
            return ParserImpl.this.new ParseBlockNode().produce();
         }
      }
   }

   private class ParseDocumentEnd implements Production {
      private ParseDocumentEnd() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.peekToken();
         Mark startMark = token.getStartMark();
         Mark endMark = startMark;
         boolean explicit = false;
         if (ParserImpl.this.scanner.checkToken(Token.ID.DocumentEnd)) {
            token = ParserImpl.this.scanner.getToken();
            endMark = token.getEndMark();
            explicit = true;
         }

         Event event = new DocumentEndEvent(startMark, endMark, explicit);
         ParserImpl.this.state = ParserImpl.this.new ParseDocumentStart();
         return event;
      }
   }

   private class ParseDocumentStart implements Production {
      private ParseDocumentStart() {
      }

      @Override
      public Event produce() {
         while (ParserImpl.this.scanner.checkToken(Token.ID.DocumentEnd)) {
            ParserImpl.this.scanner.getToken();
         }

         if (!ParserImpl.this.scanner.checkToken(Token.ID.StreamEnd)) {
            ParserImpl.this.scanner.resetDocumentIndex();
            Token token = ParserImpl.this.scanner.peekToken();
            Mark startMark = token.getStartMark();
            VersionTagsTuple tuple = ParserImpl.this.processDirectives();

            while (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
               ParserImpl.this.scanner.getToken();
            }

            if (!ParserImpl.this.scanner.checkToken(Token.ID.StreamEnd)) {
               if (!ParserImpl.this.scanner.checkToken(Token.ID.DocumentStart)) {
                  throw new ParserException(
                     null,
                     null,
                     "expected '<document start>', but found '" + ParserImpl.this.scanner.peekToken().getTokenId() + "'",
                     ParserImpl.this.scanner.peekToken().getStartMark()
                  );
               }

               token = ParserImpl.this.scanner.getToken();
               Mark endMark = token.getEndMark();
               Event event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
               ParserImpl.this.states.push(ParserImpl.this.new ParseDocumentEnd());
               ParserImpl.this.state = ParserImpl.this.new ParseDocumentContent();
               return event;
            }
         }

         StreamEndToken token = (StreamEndToken)ParserImpl.this.scanner.getToken();
         Event event = new StreamEndEvent(token.getStartMark(), token.getEndMark());
         if (!ParserImpl.this.states.isEmpty()) {
            throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
         } else if (!ParserImpl.this.marks.isEmpty()) {
            throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
         } else {
            ParserImpl.this.state = null;
            return event;
         }
      }
   }

   private class ParseFlowEndComment implements Production {
      private ParseFlowEndComment() {
      }

      @Override
      public Event produce() {
         Event event = ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.states.pop();
         }

         return event;
      }
   }

   private class ParseFlowMappingEmptyValue implements Production {
      private ParseFlowMappingEmptyValue() {
      }

      @Override
      public Event produce() {
         ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingKey(false);
         return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
      }
   }

   private class ParseFlowMappingFirstKey implements Production {
      private ParseFlowMappingFirstKey() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.getToken();
         ParserImpl.this.marks.push(token.getStartMark());
         return ParserImpl.this.new ParseFlowMappingKey(true).produce();
      }
   }

   private class ParseFlowMappingKey implements Production {
      private final boolean first;

      public ParseFlowMappingKey(boolean first) {
         this.first = first;
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingKey(this.first);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else {
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowMappingEnd)) {
               if (!this.first) {
                  if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry)) {
                     Token token = ParserImpl.this.scanner.peekToken();
                     throw new ParserException(
                        "while parsing a flow mapping", ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark()
                     );
                  }

                  ParserImpl.this.scanner.getToken();
                  if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                     ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingKey(true);
                     return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
                  }
               }

               if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
                  Token token = ParserImpl.this.scanner.getToken();
                  if (!ParserImpl.this.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                     ParserImpl.this.states.push(ParserImpl.this.new ParseFlowMappingValue());
                     return ParserImpl.this.parseFlowNode();
                  }

                  ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingValue();
                  return ParserImpl.this.processEmptyScalar(token.getEndMark());
               }

               if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                  ParserImpl.this.states.push(ParserImpl.this.new ParseFlowMappingEmptyValue());
                  return ParserImpl.this.parseFlowNode();
               }
            }

            Token token = ParserImpl.this.scanner.getToken();
            Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.marks.pop();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
               ParserImpl.this.state = ParserImpl.this.states.pop();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseFlowEndComment();
            }

            return event;
         }
      }
   }

   private class ParseFlowMappingValue implements Production {
      private ParseFlowMappingValue() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
               ParserImpl.this.states.push(ParserImpl.this.new ParseFlowMappingKey(false));
               return ParserImpl.this.parseFlowNode();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingKey(false);
               return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseFlowMappingKey(false);
            Token token = ParserImpl.this.scanner.peekToken();
            return ParserImpl.this.processEmptyScalar(token.getStartMark());
         }
      }
   }

   private class ParseFlowSequenceEntry implements Production {
      private final boolean first;

      public ParseFlowSequenceEntry(boolean first) {
         this.first = first;
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntry(this.first);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else {
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
               if (!this.first) {
                  if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry)) {
                     Token token = ParserImpl.this.scanner.peekToken();
                     throw new ParserException(
                        "while parsing a flow sequence",
                        ParserImpl.this.marks.pop(),
                        "expected ',' or ']', but got " + token.getTokenId(),
                        token.getStartMark()
                     );
                  }

                  ParserImpl.this.scanner.getToken();
                  if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                     ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntry(true);
                     return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
                  }
               }

               if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
                  Token token = ParserImpl.this.scanner.peekToken();
                  Event event = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), DumperOptions.FlowStyle.FLOW);
                  ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntryMappingKey();
                  return event;
               }

               if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                  ParserImpl.this.states.push(ParserImpl.this.new ParseFlowSequenceEntry(false));
                  return ParserImpl.this.parseFlowNode();
               }
            }

            Token token = ParserImpl.this.scanner.getToken();
            Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
               ParserImpl.this.state = ParserImpl.this.states.pop();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseFlowEndComment();
            }

            ParserImpl.this.marks.pop();
            return event;
         }
      }
   }

   private class ParseFlowSequenceEntryMappingEnd implements Production {
      private ParseFlowSequenceEntryMappingEnd() {
      }

      @Override
      public Event produce() {
         ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntry(false);
         Token token = ParserImpl.this.scanner.peekToken();
         return new MappingEndEvent(token.getStartMark(), token.getEndMark());
      }
   }

   private class ParseFlowSequenceEntryMappingKey implements Production {
      private ParseFlowSequenceEntryMappingKey() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.getToken();
         if (!ParserImpl.this.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
            ParserImpl.this.states.push(ParserImpl.this.new ParseFlowSequenceEntryMappingValue());
            return ParserImpl.this.parseFlowNode();
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntryMappingValue();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
         }
      }
   }

   private class ParseFlowSequenceEntryMappingValue implements Production {
      private ParseFlowSequenceEntryMappingValue() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
               ParserImpl.this.states.push(ParserImpl.this.new ParseFlowSequenceEntryMappingEnd());
               return ParserImpl.this.parseFlowNode();
            } else {
               ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntryMappingEnd();
               return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseFlowSequenceEntryMappingEnd();
            Token token = ParserImpl.this.scanner.peekToken();
            return ParserImpl.this.processEmptyScalar(token.getStartMark());
         }
      }
   }

   private class ParseFlowSequenceFirstEntry implements Production {
      private ParseFlowSequenceFirstEntry() {
      }

      @Override
      public Event produce() {
         Token token = ParserImpl.this.scanner.getToken();
         ParserImpl.this.marks.push(token.getStartMark());
         return ParserImpl.this.new ParseFlowSequenceEntry(true).produce();
      }
   }

   private class ParseImplicitDocumentStart implements Production {
      private ParseImplicitDocumentStart() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseImplicitDocumentStart();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
            Token token = ParserImpl.this.scanner.peekToken();
            Mark startMark = token.getStartMark();
            Event event = new DocumentStartEvent(startMark, startMark, false, null, null);
            ParserImpl.this.states.push(ParserImpl.this.new ParseDocumentEnd());
            ParserImpl.this.state = ParserImpl.this.new ParseBlockNode();
            return event;
         } else {
            return ParserImpl.this.new ParseDocumentStart().produce();
         }
      }
   }

   private class ParseIndentlessSequenceEntryKey implements Production {
      private ParseIndentlessSequenceEntryKey() {
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseIndentlessSequenceEntryKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry)) {
            BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
            return ParserImpl.this.new ParseIndentlessSequenceEntryValue(token).produce();
         } else {
            Token token = ParserImpl.this.scanner.peekToken();
            Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.state = ParserImpl.this.states.pop();
            return event;
         }
      }
   }

   private class ParseIndentlessSequenceEntryValue implements Production {
      BlockEntryToken token;

      public ParseIndentlessSequenceEntryValue(BlockEntryToken token) {
         this.token = token;
      }

      @Override
      public Event produce() {
         if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = ParserImpl.this.new ParseIndentlessSequenceEntryValue(this.token);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
         } else if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            ParserImpl.this.states.push(ParserImpl.this.new ParseIndentlessSequenceEntryKey());
            return ParserImpl.this.new ParseBlockNode().produce();
         } else {
            ParserImpl.this.state = ParserImpl.this.new ParseIndentlessSequenceEntryKey();
            return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
         }
      }
   }

   private class ParseStreamStart implements Production {
      private ParseStreamStart() {
      }

      @Override
      public Event produce() {
         StreamStartToken token = (StreamStartToken)ParserImpl.this.scanner.getToken();
         Event event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
         ParserImpl.this.state = ParserImpl.this.new ParseImplicitDocumentStart();
         return event;
      }
   }
}
