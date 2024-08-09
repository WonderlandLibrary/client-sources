/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
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
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.parser.VersionTagsTuple;
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

public class ParserImpl
implements Parser {
    private static final Map<String, String> DEFAULT_TAGS = new HashMap<String, String>();
    protected final Scanner scanner;
    private Event currentEvent;
    private final ArrayStack<Production> states;
    private final ArrayStack<Mark> marks;
    private Production state;
    private VersionTagsTuple directives;

    public ParserImpl(StreamReader streamReader, LoaderOptions loaderOptions) {
        this(new ScannerImpl(streamReader, loaderOptions));
    }

    public ParserImpl(Scanner scanner) {
        this.scanner = scanner;
        this.currentEvent = null;
        this.directives = new VersionTagsTuple(null, new HashMap<String, String>(DEFAULT_TAGS));
        this.states = new ArrayStack(100);
        this.marks = new ArrayStack(10);
        this.state = new ParseStreamStart(this, null);
    }

    @Override
    public boolean checkEvent(Event.ID iD) {
        this.peekEvent();
        return this.currentEvent != null && this.currentEvent.is(iD);
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
        Event event = this.currentEvent;
        this.currentEvent = null;
        return event;
    }

    private CommentEvent produceCommentEvent(CommentToken commentToken) {
        Mark mark = commentToken.getStartMark();
        Mark mark2 = commentToken.getEndMark();
        String string = commentToken.getValue();
        CommentType commentType = commentToken.getCommentType();
        return new CommentEvent(commentType, string, mark, mark2);
    }

    private VersionTagsTuple processDirectives() {
        Object object;
        HashMap<String, String> hashMap = new HashMap<String, String>(this.directives.getTags());
        for (String list : DEFAULT_TAGS.keySet()) {
            hashMap.remove(list);
        }
        this.directives = new VersionTagsTuple(null, hashMap);
        while (this.scanner.checkToken(Token.ID.Directive)) {
            Object object2;
            object = (DirectiveToken)this.scanner.getToken();
            if (((DirectiveToken)object).getName().equals("YAML")) {
                if (this.directives.getVersion() != null) {
                    throw new ParserException(null, null, "found duplicate YAML directive", ((Token)object).getStartMark());
                }
                List list = ((DirectiveToken)object).getValue();
                Integer n = (Integer)list.get(0);
                if (n != 1) {
                    throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", ((Token)object).getStartMark());
                }
                object2 = (Integer)list.get(1);
                if ((Integer)object2 == 0) {
                    this.directives = new VersionTagsTuple(DumperOptions.Version.V1_0, hashMap);
                    continue;
                }
                this.directives = new VersionTagsTuple(DumperOptions.Version.V1_1, hashMap);
                continue;
            }
            if (!((DirectiveToken)object).getName().equals("TAG")) continue;
            List list = ((DirectiveToken)object).getValue();
            String string = (String)list.get(0);
            object2 = (String)list.get(1);
            if (hashMap.containsKey(string)) {
                throw new ParserException(null, null, "duplicate tag handle " + string, ((Token)object).getStartMark());
            }
            hashMap.put(string, (String)object2);
        }
        object = new HashMap();
        if (!hashMap.isEmpty()) {
            object = new HashMap<String, String>(hashMap);
        }
        for (String string : DEFAULT_TAGS.keySet()) {
            if (hashMap.containsKey(string)) continue;
            hashMap.put(string, DEFAULT_TAGS.get(string));
        }
        return new VersionTagsTuple(this.directives.getVersion(), (Map<String, String>)object);
    }

    private Event parseFlowNode() {
        return this.parseNode(false, false);
    }

    private Event parseBlockNodeOrIndentlessSequence() {
        return this.parseNode(true, true);
    }

    private Event parseNode(boolean bl, boolean bl2) {
        NodeEvent nodeEvent;
        Mark mark = null;
        Mark mark2 = null;
        Mark mark3 = null;
        if (this.scanner.checkToken(Token.ID.Alias)) {
            AliasToken aliasToken = (AliasToken)this.scanner.getToken();
            nodeEvent = new AliasEvent(aliasToken.getValue(), aliasToken.getStartMark(), aliasToken.getEndMark());
            this.state = this.states.pop();
        } else {
            boolean bl3;
            Object object;
            Object object2;
            Object object3;
            String string = null;
            TagTuple tagTuple = null;
            if (this.scanner.checkToken(Token.ID.Anchor)) {
                object3 = (AnchorToken)this.scanner.getToken();
                mark = ((Token)object3).getStartMark();
                mark2 = ((Token)object3).getEndMark();
                string = ((AnchorToken)object3).getValue();
                if (this.scanner.checkToken(Token.ID.Tag)) {
                    object2 = (TagToken)this.scanner.getToken();
                    mark3 = ((Token)object2).getStartMark();
                    mark2 = ((Token)object2).getEndMark();
                    tagTuple = ((TagToken)object2).getValue();
                }
            } else if (this.scanner.checkToken(Token.ID.Tag)) {
                object3 = (TagToken)this.scanner.getToken();
                mark3 = mark = ((Token)object3).getStartMark();
                mark2 = ((Token)object3).getEndMark();
                tagTuple = ((TagToken)object3).getValue();
                if (this.scanner.checkToken(Token.ID.Anchor)) {
                    object2 = (AnchorToken)this.scanner.getToken();
                    mark2 = ((Token)object2).getEndMark();
                    string = ((AnchorToken)object2).getValue();
                }
            }
            object3 = null;
            if (tagTuple != null) {
                object2 = tagTuple.getHandle();
                object = tagTuple.getSuffix();
                if (object2 != null) {
                    if (!this.directives.getTags().containsKey(object2)) {
                        throw new ParserException("while parsing a node", mark, "found undefined tag handle " + (String)object2, mark3);
                    }
                    object3 = this.directives.getTags().get(object2) + (String)object;
                } else {
                    object3 = object;
                }
            }
            if (mark == null) {
                mark2 = mark = this.scanner.peekToken().getStartMark();
            }
            nodeEvent = null;
            boolean bl4 = bl3 = object3 == null || ((String)object3).equals("!");
            if (bl2 && this.scanner.checkToken(Token.ID.BlockEntry)) {
                mark2 = this.scanner.peekToken().getEndMark();
                nodeEvent = new SequenceStartEvent(string, (String)object3, bl3, mark, mark2, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseIndentlessSequenceEntryKey(this, null);
            } else if (this.scanner.checkToken(Token.ID.Scalar)) {
                object = (ScalarToken)this.scanner.getToken();
                mark2 = ((Token)object).getEndMark();
                ImplicitTuple implicitTuple = ((ScalarToken)object).getPlain() && object3 == null || "!".equals(object3) ? new ImplicitTuple(true, false) : (object3 == null ? new ImplicitTuple(false, true) : new ImplicitTuple(false, false));
                nodeEvent = new ScalarEvent(string, (String)object3, implicitTuple, ((ScalarToken)object).getValue(), mark, mark2, ((ScalarToken)object).getStyle());
                this.state = this.states.pop();
            } else if (this.scanner.checkToken(Token.ID.FlowSequenceStart)) {
                mark2 = this.scanner.peekToken().getEndMark();
                nodeEvent = new SequenceStartEvent(string, (String)object3, bl3, mark, mark2, DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowSequenceFirstEntry(this, null);
            } else if (this.scanner.checkToken(Token.ID.FlowMappingStart)) {
                mark2 = this.scanner.peekToken().getEndMark();
                nodeEvent = new MappingStartEvent(string, (String)object3, bl3, mark, mark2, DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowMappingFirstKey(this, null);
            } else if (bl && this.scanner.checkToken(Token.ID.BlockSequenceStart)) {
                mark2 = this.scanner.peekToken().getStartMark();
                nodeEvent = new SequenceStartEvent(string, (String)object3, bl3, mark, mark2, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockSequenceFirstEntry(this, null);
            } else if (bl && this.scanner.checkToken(Token.ID.BlockMappingStart)) {
                mark2 = this.scanner.peekToken().getStartMark();
                nodeEvent = new MappingStartEvent(string, (String)object3, bl3, mark, mark2, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockMappingFirstKey(this, null);
            } else if (string != null || object3 != null) {
                nodeEvent = new ScalarEvent(string, (String)object3, new ImplicitTuple(bl3, false), "", mark, mark2, DumperOptions.ScalarStyle.PLAIN);
                this.state = this.states.pop();
            } else {
                object = this.scanner.peekToken();
                throw new ParserException("while parsing a " + (bl ? "block" : "flow") + " node", mark, "expected the node content, but found '" + (Object)((Object)((Token)object).getTokenId()) + "'", ((Token)object).getStartMark());
            }
        }
        return nodeEvent;
    }

    private Event processEmptyScalar(Mark mark) {
        return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, DumperOptions.ScalarStyle.PLAIN);
    }

    static Production access$102(ParserImpl parserImpl, Production production) {
        parserImpl.state = production;
        return parserImpl.state;
    }

    static CommentEvent access$300(ParserImpl parserImpl, CommentToken commentToken) {
        return parserImpl.produceCommentEvent(commentToken);
    }

    static ArrayStack access$500(ParserImpl parserImpl) {
        return parserImpl.states;
    }

    static VersionTagsTuple access$800(ParserImpl parserImpl) {
        return parserImpl.processDirectives();
    }

    static ArrayStack access$1000(ParserImpl parserImpl) {
        return parserImpl.marks;
    }

    static Event access$1100(ParserImpl parserImpl, Mark mark) {
        return parserImpl.processEmptyScalar(mark);
    }

    static Event access$1200(ParserImpl parserImpl, boolean bl, boolean bl2) {
        return parserImpl.parseNode(bl, bl2);
    }

    static Event access$2100(ParserImpl parserImpl) {
        return parserImpl.parseBlockNodeOrIndentlessSequence();
    }

    static Production access$100(ParserImpl parserImpl) {
        return parserImpl.state;
    }

    static Event access$2400(ParserImpl parserImpl) {
        return parserImpl.parseFlowNode();
    }

    static {
        DEFAULT_TAGS.put("!", "!");
        DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
    }

    private class ParseFlowMappingEmptyValue
    implements Production {
        final ParserImpl this$0;

        private ParseFlowMappingEmptyValue(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            ParserImpl.access$102(this.this$0, new ParseFlowMappingKey(this.this$0, false));
            return ParserImpl.access$1100(this.this$0, this.this$0.scanner.peekToken().getStartMark());
        }

        ParseFlowMappingEmptyValue(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowMappingValue
    implements Production {
        final ParserImpl this$0;

        private ParseFlowMappingValue(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Value)) {
                Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseFlowMappingKey(this.this$0, false));
                    return ParserImpl.access$2400(this.this$0);
                }
                ParserImpl.access$102(this.this$0, new ParseFlowMappingKey(this.this$0, false));
                return ParserImpl.access$1100(this.this$0, token.getEndMark());
            }
            ParserImpl.access$102(this.this$0, new ParseFlowMappingKey(this.this$0, false));
            Token token = this.this$0.scanner.peekToken();
            return ParserImpl.access$1100(this.this$0, token.getStartMark());
        }

        ParseFlowMappingValue(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowMappingKey
    implements Production {
        private final boolean first;
        final ParserImpl this$0;

        public ParseFlowMappingKey(ParserImpl parserImpl, boolean bl) {
            this.this$0 = parserImpl;
            this.first = bl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseFlowMappingKey(this.this$0, this.first));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                if (!this.first) {
                    if (this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        this.this$0.scanner.getToken();
                        if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                            ParserImpl.access$102(this.this$0, new ParseFlowMappingKey(this.this$0, true));
                            return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
                        }
                    } else {
                        Token token = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.access$1000(this.this$0).pop(), "expected ',' or '}', but got " + (Object)((Object)token.getTokenId()), token.getStartMark());
                    }
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    Token token = this.this$0.scanner.getToken();
                    if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                        ParserImpl.access$500(this.this$0).push(new ParseFlowMappingValue(this.this$0, null));
                        return ParserImpl.access$2400(this.this$0);
                    }
                    ParserImpl.access$102(this.this$0, new ParseFlowMappingValue(this.this$0, null));
                    return ParserImpl.access$1100(this.this$0, token.getEndMark());
                }
                if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseFlowMappingEmptyValue(this.this$0, null));
                    return ParserImpl.access$2400(this.this$0);
                }
            }
            Token token = this.this$0.scanner.getToken();
            MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$1000(this.this$0).pop();
            if (!this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            } else {
                ParserImpl.access$102(this.this$0, new ParseFlowEndComment(this.this$0, null));
            }
            return mappingEndEvent;
        }
    }

    private class ParseFlowMappingFirstKey
    implements Production {
        final ParserImpl this$0;

        private ParseFlowMappingFirstKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token = this.this$0.scanner.getToken();
            ParserImpl.access$1000(this.this$0).push(token.getStartMark());
            return new ParseFlowMappingKey(this.this$0, true).produce();
        }

        ParseFlowMappingFirstKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowSequenceEntryMappingEnd
    implements Production {
        final ParserImpl this$0;

        private ParseFlowSequenceEntryMappingEnd(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntry(this.this$0, false));
            Token token = this.this$0.scanner.peekToken();
            return new MappingEndEvent(token.getStartMark(), token.getEndMark());
        }

        ParseFlowSequenceEntryMappingEnd(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowSequenceEntryMappingValue
    implements Production {
        final ParserImpl this$0;

        private ParseFlowSequenceEntryMappingValue(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Value)) {
                Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseFlowSequenceEntryMappingEnd(this.this$0, null));
                    return ParserImpl.access$2400(this.this$0);
                }
                ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntryMappingEnd(this.this$0, null));
                return ParserImpl.access$1100(this.this$0, token.getEndMark());
            }
            ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntryMappingEnd(this.this$0, null));
            Token token = this.this$0.scanner.peekToken();
            return ParserImpl.access$1100(this.this$0, token.getStartMark());
        }

        ParseFlowSequenceEntryMappingValue(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowSequenceEntryMappingKey
    implements Production {
        final ParserImpl this$0;

        private ParseFlowSequenceEntryMappingKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                ParserImpl.access$500(this.this$0).push(new ParseFlowSequenceEntryMappingValue(this.this$0, null));
                return ParserImpl.access$2400(this.this$0);
            }
            ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntryMappingValue(this.this$0, null));
            return ParserImpl.access$1100(this.this$0, token.getEndMark());
        }

        ParseFlowSequenceEntryMappingKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowEndComment
    implements Production {
        final ParserImpl this$0;

        private ParseFlowEndComment(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            CommentEvent commentEvent = ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            if (!this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            }
            return commentEvent;
        }

        ParseFlowEndComment(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseFlowSequenceEntry
    implements Production {
        private final boolean first;
        final ParserImpl this$0;

        public ParseFlowSequenceEntry(ParserImpl parserImpl, boolean bl) {
            this.this$0 = parserImpl;
            this.first = bl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntry(this.this$0, this.first));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                if (!this.first) {
                    if (this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        this.this$0.scanner.getToken();
                        if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                            ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntry(this.this$0, true));
                            return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
                        }
                    } else {
                        Token token = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.access$1000(this.this$0).pop(), "expected ',' or ']', but got " + (Object)((Object)token.getTokenId()), token.getStartMark());
                    }
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    Token token = this.this$0.scanner.peekToken();
                    MappingStartEvent mappingStartEvent = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), DumperOptions.FlowStyle.FLOW);
                    ParserImpl.access$102(this.this$0, new ParseFlowSequenceEntryMappingKey(this.this$0, null));
                    return mappingStartEvent;
                }
                if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseFlowSequenceEntry(this.this$0, false));
                    return ParserImpl.access$2400(this.this$0);
                }
            }
            Token token = this.this$0.scanner.getToken();
            SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            if (!this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            } else {
                ParserImpl.access$102(this.this$0, new ParseFlowEndComment(this.this$0, null));
            }
            ParserImpl.access$1000(this.this$0).pop();
            return sequenceEndEvent;
        }
    }

    private class ParseFlowSequenceFirstEntry
    implements Production {
        final ParserImpl this$0;

        private ParseFlowSequenceFirstEntry(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token = this.this$0.scanner.getToken();
            ParserImpl.access$1000(this.this$0).push(token.getStartMark());
            return new ParseFlowSequenceEntry(this.this$0, true).produce();
        }

        ParseFlowSequenceFirstEntry(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockMappingValueCommentList
    implements Production {
        List<CommentToken> tokens;
        final ParserImpl this$0;

        public ParseBlockMappingValueCommentList(ParserImpl parserImpl, List<CommentToken> list) {
            this.this$0 = parserImpl;
            this.tokens = list;
        }

        @Override
        public Event produce() {
            if (!this.tokens.isEmpty()) {
                return ParserImpl.access$300(this.this$0, this.tokens.remove(0));
            }
            return new ParseBlockMappingKey(this.this$0, null).produce();
        }
    }

    private class ParseBlockMappingValueComment
    implements Production {
        List<CommentToken> tokens;
        final ParserImpl this$0;

        private ParseBlockMappingValueComment(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
            this.tokens = new LinkedList<CommentToken>();
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                this.tokens.add((CommentToken)this.this$0.scanner.getToken());
                return this.produce();
            }
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                if (!this.tokens.isEmpty()) {
                    return ParserImpl.access$300(this.this$0, this.tokens.remove(0));
                }
                ParserImpl.access$500(this.this$0).push(new ParseBlockMappingKey(this.this$0, null));
                return ParserImpl.access$2100(this.this$0);
            }
            ParserImpl.access$102(this.this$0, new ParseBlockMappingValueCommentList(this.this$0, this.tokens));
            return ParserImpl.access$1100(this.this$0, this.this$0.scanner.peekToken().getStartMark());
        }

        ParseBlockMappingValueComment(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockMappingValue
    implements Production {
        final ParserImpl this$0;

        private ParseBlockMappingValue(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Value)) {
                Token token = this.this$0.scanner.getToken();
                if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                    ParserImpl.access$102(this.this$0, new ParseBlockMappingValueComment(this.this$0, null));
                    return ParserImpl.access$100(this.this$0).produce();
                }
                if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseBlockMappingKey(this.this$0, null));
                    return ParserImpl.access$2100(this.this$0);
                }
                ParserImpl.access$102(this.this$0, new ParseBlockMappingKey(this.this$0, null));
                return ParserImpl.access$1100(this.this$0, token.getEndMark());
            }
            if (this.this$0.scanner.checkToken(Token.ID.Scalar)) {
                ParserImpl.access$500(this.this$0).push(new ParseBlockMappingKey(this.this$0, null));
                return ParserImpl.access$2100(this.this$0);
            }
            ParserImpl.access$102(this.this$0, new ParseBlockMappingKey(this.this$0, null));
            Token token = this.this$0.scanner.peekToken();
            return ParserImpl.access$1100(this.this$0, token.getStartMark());
        }

        ParseBlockMappingValue(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockMappingKey
    implements Production {
        final ParserImpl this$0;

        private ParseBlockMappingKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseBlockMappingKey(this.this$0));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                    ParserImpl.access$500(this.this$0).push(new ParseBlockMappingValue(this.this$0, null));
                    return ParserImpl.access$2100(this.this$0);
                }
                ParserImpl.access$102(this.this$0, new ParseBlockMappingValue(this.this$0, null));
                return ParserImpl.access$1100(this.this$0, token.getEndMark());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                Token token = this.this$0.scanner.peekToken();
                throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.access$1000(this.this$0).pop(), "expected <block end>, but found '" + (Object)((Object)token.getTokenId()) + "'", token.getStartMark());
            }
            Token token = this.this$0.scanner.getToken();
            MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            ParserImpl.access$1000(this.this$0).pop();
            return mappingEndEvent;
        }

        ParseBlockMappingKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockMappingFirstKey
    implements Production {
        final ParserImpl this$0;

        private ParseBlockMappingFirstKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token = this.this$0.scanner.getToken();
            ParserImpl.access$1000(this.this$0).push(token.getStartMark());
            return new ParseBlockMappingKey(this.this$0, null).produce();
        }

        ParseBlockMappingFirstKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseIndentlessSequenceEntryValue
    implements Production {
        BlockEntryToken token;
        final ParserImpl this$0;

        public ParseIndentlessSequenceEntryValue(ParserImpl parserImpl, BlockEntryToken blockEntryToken) {
            this.this$0 = parserImpl;
            this.token = blockEntryToken;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseIndentlessSequenceEntryValue(this.this$0, this.token));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.access$500(this.this$0).push(new ParseIndentlessSequenceEntryKey(this.this$0, null));
                return new ParseBlockNode(this.this$0, null).produce();
            }
            ParserImpl.access$102(this.this$0, new ParseIndentlessSequenceEntryKey(this.this$0, null));
            return ParserImpl.access$1100(this.this$0, this.token.getEndMark());
        }
    }

    private class ParseIndentlessSequenceEntryKey
    implements Production {
        final ParserImpl this$0;

        private ParseIndentlessSequenceEntryKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseIndentlessSequenceEntryKey(this.this$0));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                BlockEntryToken blockEntryToken = (BlockEntryToken)this.this$0.scanner.getToken();
                return new ParseIndentlessSequenceEntryValue(this.this$0, blockEntryToken).produce();
            }
            Token token = this.this$0.scanner.peekToken();
            SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            return sequenceEndEvent;
        }

        ParseIndentlessSequenceEntryKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockSequenceEntryValue
    implements Production {
        BlockEntryToken token;
        final ParserImpl this$0;

        public ParseBlockSequenceEntryValue(ParserImpl parserImpl, BlockEntryToken blockEntryToken) {
            this.this$0 = parserImpl;
            this.token = blockEntryToken;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseBlockSequenceEntryValue(this.this$0, this.token));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
                ParserImpl.access$500(this.this$0).push(new ParseBlockSequenceEntryKey(this.this$0, null));
                return new ParseBlockNode(this.this$0, null).produce();
            }
            ParserImpl.access$102(this.this$0, new ParseBlockSequenceEntryKey(this.this$0, null));
            return ParserImpl.access$1100(this.this$0, this.token.getEndMark());
        }
    }

    private class ParseBlockSequenceEntryKey
    implements Production {
        final ParserImpl this$0;

        private ParseBlockSequenceEntryKey(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseBlockSequenceEntryKey(this.this$0));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                BlockEntryToken blockEntryToken = (BlockEntryToken)this.this$0.scanner.getToken();
                return new ParseBlockSequenceEntryValue(this.this$0, blockEntryToken).produce();
            }
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                Token token = this.this$0.scanner.peekToken();
                throw new ParserException("while parsing a block collection", (Mark)ParserImpl.access$1000(this.this$0).pop(), "expected <block end>, but found '" + (Object)((Object)token.getTokenId()) + "'", token.getStartMark());
            }
            Token token = this.this$0.scanner.getToken();
            SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
            ParserImpl.access$1000(this.this$0).pop();
            return sequenceEndEvent;
        }

        ParseBlockSequenceEntryKey(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockSequenceFirstEntry
    implements Production {
        final ParserImpl this$0;

        private ParseBlockSequenceFirstEntry(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token = this.this$0.scanner.getToken();
            ParserImpl.access$1000(this.this$0).push(token.getStartMark());
            return new ParseBlockSequenceEntryKey(this.this$0, null).produce();
        }

        ParseBlockSequenceFirstEntry(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseBlockNode
    implements Production {
        final ParserImpl this$0;

        private ParseBlockNode(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            return ParserImpl.access$1200(this.this$0, true, false);
        }

        ParseBlockNode(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseDocumentContent
    implements Production {
        final ParserImpl this$0;

        private ParseDocumentContent(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseDocumentContent(this.this$0));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
                Event event = ParserImpl.access$1100(this.this$0, this.this$0.scanner.peekToken().getStartMark());
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$500(this.this$0).pop());
                return event;
            }
            return new ParseBlockNode(this.this$0, null).produce();
        }

        ParseDocumentContent(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseDocumentEnd
    implements Production {
        final ParserImpl this$0;

        private ParseDocumentEnd(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Mark mark;
            Token token = this.this$0.scanner.peekToken();
            Mark mark2 = mark = token.getStartMark();
            boolean bl = false;
            if (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                token = this.this$0.scanner.getToken();
                mark2 = token.getEndMark();
                bl = true;
            }
            DocumentEndEvent documentEndEvent = new DocumentEndEvent(mark, mark2, bl);
            ParserImpl.access$102(this.this$0, new ParseDocumentStart(this.this$0, null));
            return documentEndEvent;
        }

        ParseDocumentEnd(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseDocumentStart
    implements Production {
        final ParserImpl this$0;

        private ParseDocumentStart(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            Token token;
            while (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                this.this$0.scanner.getToken();
            }
            if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
                this.this$0.scanner.resetDocumentIndex();
                token = this.this$0.scanner.peekToken();
                Mark mark = token.getStartMark();
                VersionTagsTuple versionTagsTuple = ParserImpl.access$800(this.this$0);
                while (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                    this.this$0.scanner.getToken();
                }
                if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
                    if (!this.this$0.scanner.checkToken(Token.ID.DocumentStart)) {
                        throw new ParserException(null, null, "expected '<document start>', but found '" + (Object)((Object)this.this$0.scanner.peekToken().getTokenId()) + "'", this.this$0.scanner.peekToken().getStartMark());
                    }
                    token = this.this$0.scanner.getToken();
                    Mark mark2 = token.getEndMark();
                    DocumentStartEvent documentStartEvent = new DocumentStartEvent(mark, mark2, true, versionTagsTuple.getVersion(), versionTagsTuple.getTags());
                    ParserImpl.access$500(this.this$0).push(new ParseDocumentEnd(this.this$0, null));
                    ParserImpl.access$102(this.this$0, new ParseDocumentContent(this.this$0, null));
                    return documentStartEvent;
                }
            }
            token = (StreamEndToken)this.this$0.scanner.getToken();
            StreamEndEvent streamEndEvent = new StreamEndEvent(token.getStartMark(), token.getEndMark());
            if (!ParserImpl.access$500(this.this$0).isEmpty()) {
                throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.access$500(this.this$0));
            }
            if (!ParserImpl.access$1000(this.this$0).isEmpty()) {
                throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.access$1000(this.this$0));
            }
            ParserImpl.access$102(this.this$0, null);
            return streamEndEvent;
        }

        ParseDocumentStart(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseImplicitDocumentStart
    implements Production {
        final ParserImpl this$0;

        private ParseImplicitDocumentStart(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.access$102(this.this$0, new ParseImplicitDocumentStart(this.this$0));
                return ParserImpl.access$300(this.this$0, (CommentToken)this.this$0.scanner.getToken());
            }
            if (!this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
                Mark mark;
                Token token = this.this$0.scanner.peekToken();
                Mark mark2 = mark = token.getStartMark();
                DocumentStartEvent documentStartEvent = new DocumentStartEvent(mark, mark2, false, null, null);
                ParserImpl.access$500(this.this$0).push(new ParseDocumentEnd(this.this$0, null));
                ParserImpl.access$102(this.this$0, new ParseBlockNode(this.this$0, null));
                return documentStartEvent;
            }
            return new ParseDocumentStart(this.this$0, null).produce();
        }

        ParseImplicitDocumentStart(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }

    private class ParseStreamStart
    implements Production {
        final ParserImpl this$0;

        private ParseStreamStart(ParserImpl parserImpl) {
            this.this$0 = parserImpl;
        }

        @Override
        public Event produce() {
            StreamStartToken streamStartToken = (StreamStartToken)this.this$0.scanner.getToken();
            StreamStartEvent streamStartEvent = new StreamStartEvent(streamStartToken.getStartMark(), streamStartToken.getEndMark());
            ParserImpl.access$102(this.this$0, new ParseImplicitDocumentStart(this.this$0, null));
            return streamStartEvent;
        }

        ParseStreamStart(ParserImpl parserImpl, 1 var2_2) {
            this(parserImpl);
        }
    }
}

