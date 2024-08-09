/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentEventsCollector;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CollectionEndEvent;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.CommentEvent;
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

public final class Emitter
implements Emitable {
    public static final int MIN_INDENT = 1;
    public static final int MAX_INDENT = 10;
    private static final char[] SPACE = new char[]{' '};
    private static final Pattern SPACES_PATTERN = Pattern.compile("\\s");
    private static final Set<Character> INVALID_ANCHOR = new HashSet<Character>();
    private static final Map<Character, String> ESCAPE_REPLACEMENTS;
    private static final Map<String, String> DEFAULT_TAG_PREFIXES;
    private final Writer stream;
    private final ArrayStack<EmitterState> states;
    private EmitterState state;
    private final Queue<Event> events;
    private Event event;
    private final ArrayStack<Integer> indents;
    private Integer indent;
    private int flowLevel;
    private boolean rootContext;
    private boolean mappingContext;
    private boolean simpleKeyContext;
    private int column;
    private boolean whitespace;
    private boolean indention;
    private boolean openEnded;
    private final Boolean canonical;
    private final Boolean prettyFlow;
    private final boolean allowUnicode;
    private int bestIndent;
    private final int indicatorIndent;
    private final boolean indentWithIndicator;
    private int bestWidth;
    private final char[] bestLineBreak;
    private final boolean splitLines;
    private final int maxSimpleKeyLength;
    private final boolean emitComments;
    private Map<String, String> tagPrefixes;
    private String preparedAnchor;
    private String preparedTag;
    private ScalarAnalysis analysis;
    private DumperOptions.ScalarStyle style;
    private final CommentEventsCollector blockCommentsCollector;
    private final CommentEventsCollector inlineCommentsCollector;
    private static final Pattern HANDLE_FORMAT;

    public Emitter(Writer writer, DumperOptions dumperOptions) {
        if (writer == null) {
            throw new NullPointerException("Writer must be provided.");
        }
        if (dumperOptions == null) {
            throw new NullPointerException("DumperOptions must be provided.");
        }
        this.stream = writer;
        this.states = new ArrayStack(100);
        this.state = new ExpectStreamStart(this, null);
        this.events = new ArrayDeque<Event>(100);
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
        this.canonical = dumperOptions.isCanonical();
        this.prettyFlow = dumperOptions.isPrettyFlow();
        this.allowUnicode = dumperOptions.isAllowUnicode();
        this.bestIndent = 2;
        if (dumperOptions.getIndent() > 1 && dumperOptions.getIndent() < 10) {
            this.bestIndent = dumperOptions.getIndent();
        }
        this.indicatorIndent = dumperOptions.getIndicatorIndent();
        this.indentWithIndicator = dumperOptions.getIndentWithIndicator();
        this.bestWidth = 80;
        if (dumperOptions.getWidth() > this.bestIndent * 2) {
            this.bestWidth = dumperOptions.getWidth();
        }
        this.bestLineBreak = dumperOptions.getLineBreak().getString().toCharArray();
        this.splitLines = dumperOptions.getSplitLines();
        this.maxSimpleKeyLength = dumperOptions.getMaxSimpleKeyLength();
        this.emitComments = dumperOptions.isProcessComments();
        this.tagPrefixes = new LinkedHashMap<String, String>();
        this.preparedAnchor = null;
        this.preparedTag = null;
        this.analysis = null;
        this.style = null;
        this.blockCommentsCollector = new CommentEventsCollector(this.events, CommentType.BLANK_LINE, CommentType.BLOCK);
        this.inlineCommentsCollector = new CommentEventsCollector(this.events, CommentType.IN_LINE);
    }

    @Override
    public void emit(Event event) throws IOException {
        this.events.add(event);
        while (!this.needMoreEvents()) {
            this.event = this.events.poll();
            this.state.expect();
            this.event = null;
        }
    }

    private boolean needMoreEvents() {
        if (this.events.isEmpty()) {
            return false;
        }
        Iterator<Event> iterator2 = this.events.iterator();
        Event event = (Event)iterator2.next();
        while (event instanceof CommentEvent) {
            if (!iterator2.hasNext()) {
                return false;
            }
            event = (Event)iterator2.next();
        }
        if (event instanceof DocumentStartEvent) {
            return this.needEvents(iterator2, 1);
        }
        if (event instanceof SequenceStartEvent) {
            return this.needEvents(iterator2, 2);
        }
        if (event instanceof MappingStartEvent) {
            return this.needEvents(iterator2, 3);
        }
        if (event instanceof StreamStartEvent) {
            return this.needEvents(iterator2, 2);
        }
        if (event instanceof StreamEndEvent) {
            return true;
        }
        if (this.emitComments) {
            return this.needEvents(iterator2, 1);
        }
        return true;
    }

    private boolean needEvents(Iterator<Event> iterator2, int n) {
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            Event event = iterator2.next();
            if (event instanceof CommentEvent) continue;
            ++n3;
            if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) {
                ++n2;
            } else if (event instanceof DocumentEndEvent || event instanceof CollectionEndEvent) {
                --n2;
            } else if (event instanceof StreamEndEvent) {
                n2 = -1;
            }
            if (n2 >= 0) continue;
            return true;
        }
        return n3 < n;
    }

    private void increaseIndent(boolean bl, boolean bl2) {
        this.indents.push(this.indent);
        if (this.indent == null) {
            this.indent = bl ? Integer.valueOf(this.bestIndent) : Integer.valueOf(0);
        } else if (!bl2) {
            this.indent = this.indent + this.bestIndent;
        }
    }

    private void expectNode(boolean bl, boolean bl2, boolean bl3) throws IOException {
        this.rootContext = bl;
        this.mappingContext = bl2;
        this.simpleKeyContext = bl3;
        if (this.event instanceof AliasEvent) {
            this.expectAlias();
        } else if (this.event instanceof ScalarEvent || this.event instanceof CollectionStartEvent) {
            this.processAnchor("&");
            this.processTag();
            if (this.event instanceof ScalarEvent) {
                this.expectScalar();
            } else if (this.event instanceof SequenceStartEvent) {
                if (this.flowLevel != 0 || this.canonical.booleanValue() || ((SequenceStartEvent)this.event).isFlow() || this.checkEmptySequence()) {
                    this.expectFlowSequence();
                } else {
                    this.expectBlockSequence();
                }
            } else if (this.flowLevel != 0 || this.canonical.booleanValue() || ((MappingStartEvent)this.event).isFlow() || this.checkEmptyMapping()) {
                this.expectFlowMapping();
            } else {
                this.expectBlockMapping();
            }
        } else {
            throw new EmitterException("expected NodeEvent, but got " + this.event);
        }
    }

    private void expectAlias() throws IOException {
        if (!(this.event instanceof AliasEvent)) {
            throw new EmitterException("Alias must be provided");
        }
        this.processAnchor("*");
        this.state = this.states.pop();
    }

    private void expectScalar() throws IOException {
        this.increaseIndent(true, false);
        this.processScalar();
        this.indent = this.indents.pop();
        this.state = this.states.pop();
    }

    private void expectFlowSequence() throws IOException {
        this.writeIndicator("[", true, true, true);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow.booleanValue()) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowSequenceItem(this, null);
    }

    private void expectFlowMapping() throws IOException {
        this.writeIndicator("{", true, true, true);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow.booleanValue()) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowMappingKey(this, null);
    }

    private void expectBlockSequence() throws IOException {
        boolean bl = this.mappingContext && !this.indention;
        this.increaseIndent(false, bl);
        this.state = new ExpectFirstBlockSequenceItem(this, null);
    }

    private void expectBlockMapping() throws IOException {
        this.increaseIndent(false, false);
        this.state = new ExpectFirstBlockMappingKey(this, null);
    }

    private boolean isFoldedOrLiteral(Event event) {
        if (!event.is(Event.ID.Scalar)) {
            return true;
        }
        ScalarEvent scalarEvent = (ScalarEvent)event;
        DumperOptions.ScalarStyle scalarStyle = scalarEvent.getScalarStyle();
        return scalarStyle == DumperOptions.ScalarStyle.FOLDED || scalarStyle == DumperOptions.ScalarStyle.LITERAL;
    }

    private boolean checkEmptySequence() {
        return this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events.peek() instanceof SequenceEndEvent;
    }

    private boolean checkEmptyMapping() {
        return this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events.peek() instanceof MappingEndEvent;
    }

    private boolean checkEmptyDocument() {
        if (!(this.event instanceof DocumentStartEvent) || this.events.isEmpty()) {
            return true;
        }
        Event event = this.events.peek();
        if (event instanceof ScalarEvent) {
            ScalarEvent scalarEvent = (ScalarEvent)event;
            return scalarEvent.getAnchor() == null && scalarEvent.getTag() == null && scalarEvent.getImplicit() != null && scalarEvent.getValue().length() == 0;
        }
        return true;
    }

    private boolean checkSimpleKey() {
        int n = 0;
        if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
            if (this.preparedAnchor == null) {
                this.preparedAnchor = Emitter.prepareAnchor(((NodeEvent)this.event).getAnchor());
            }
            n += this.preparedAnchor.length();
        }
        String string = null;
        if (this.event instanceof ScalarEvent) {
            string = ((ScalarEvent)this.event).getTag();
        } else if (this.event instanceof CollectionStartEvent) {
            string = ((CollectionStartEvent)this.event).getTag();
        }
        if (string != null) {
            if (this.preparedTag == null) {
                this.preparedTag = this.prepareTag(string);
            }
            n += this.preparedTag.length();
        }
        if (this.event instanceof ScalarEvent) {
            if (this.analysis == null) {
                this.analysis = this.analyzeScalar(((ScalarEvent)this.event).getValue());
            }
            n += this.analysis.getScalar().length();
        }
        return n < this.maxSimpleKeyLength && (this.event instanceof AliasEvent || this.event instanceof ScalarEvent && !this.analysis.isEmpty() && !this.analysis.isMultiline() || this.checkEmptySequence() || this.checkEmptyMapping());
    }

    private void processAnchor(String string) throws IOException {
        NodeEvent nodeEvent = (NodeEvent)this.event;
        if (nodeEvent.getAnchor() == null) {
            this.preparedAnchor = null;
            return;
        }
        if (this.preparedAnchor == null) {
            this.preparedAnchor = Emitter.prepareAnchor(nodeEvent.getAnchor());
        }
        this.writeIndicator(string + this.preparedAnchor, true, false, true);
        this.preparedAnchor = null;
    }

    private void processTag() throws IOException {
        String string = null;
        if (this.event instanceof ScalarEvent) {
            ScalarEvent scalarEvent = (ScalarEvent)this.event;
            string = scalarEvent.getTag();
            if (this.style == null) {
                this.style = this.chooseScalarStyle();
            }
            if ((!this.canonical.booleanValue() || string == null) && (this.style == null && scalarEvent.getImplicit().canOmitTagInPlainScalar() || this.style != null && scalarEvent.getImplicit().canOmitTagInNonPlainScalar())) {
                this.preparedTag = null;
                return;
            }
            if (scalarEvent.getImplicit().canOmitTagInPlainScalar() && string == null) {
                string = "!";
                this.preparedTag = null;
            }
        } else {
            CollectionStartEvent collectionStartEvent = (CollectionStartEvent)this.event;
            string = collectionStartEvent.getTag();
            if ((!this.canonical.booleanValue() || string == null) && collectionStartEvent.getImplicit()) {
                this.preparedTag = null;
                return;
            }
        }
        if (string == null) {
            throw new EmitterException("tag is not specified");
        }
        if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(string);
        }
        this.writeIndicator(this.preparedTag, true, false, true);
        this.preparedTag = null;
    }

    private DumperOptions.ScalarStyle chooseScalarStyle() {
        ScalarEvent scalarEvent = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(scalarEvent.getValue());
        }
        if (!scalarEvent.isPlain() && scalarEvent.getScalarStyle() == DumperOptions.ScalarStyle.DOUBLE_QUOTED || this.canonical.booleanValue()) {
            return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        }
        if (scalarEvent.isPlain() && scalarEvent.getImplicit().canOmitTagInPlainScalar() && (!this.simpleKeyContext || !this.analysis.isEmpty() && !this.analysis.isMultiline()) && (this.flowLevel != 0 && this.analysis.isAllowFlowPlain() || this.flowLevel == 0 && this.analysis.isAllowBlockPlain())) {
            return null;
        }
        if (!(scalarEvent.isPlain() || scalarEvent.getScalarStyle() != DumperOptions.ScalarStyle.LITERAL && scalarEvent.getScalarStyle() != DumperOptions.ScalarStyle.FOLDED || this.flowLevel != 0 || this.simpleKeyContext || !this.analysis.isAllowBlock())) {
            return scalarEvent.getScalarStyle();
        }
        if (!(!scalarEvent.isPlain() && scalarEvent.getScalarStyle() != DumperOptions.ScalarStyle.SINGLE_QUOTED || !this.analysis.isAllowSingleQuoted() || this.simpleKeyContext && this.analysis.isMultiline())) {
            return DumperOptions.ScalarStyle.SINGLE_QUOTED;
        }
        return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
    }

    private void processScalar() throws IOException {
        boolean bl;
        ScalarEvent scalarEvent = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(scalarEvent.getValue());
        }
        if (this.style == null) {
            this.style = this.chooseScalarStyle();
        }
        boolean bl2 = bl = !this.simpleKeyContext && this.splitLines;
        if (this.style == null) {
            this.writePlain(this.analysis.getScalar(), bl);
        } else {
            switch (1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[this.style.ordinal()]) {
                case 1: {
                    this.writeDoubleQuoted(this.analysis.getScalar(), bl);
                    break;
                }
                case 2: {
                    this.writeSingleQuoted(this.analysis.getScalar(), bl);
                    break;
                }
                case 3: {
                    this.writeFolded(this.analysis.getScalar(), bl);
                    break;
                }
                case 4: {
                    this.writeLiteral(this.analysis.getScalar());
                    break;
                }
                default: {
                    throw new YAMLException("Unexpected style: " + (Object)((Object)this.style));
                }
            }
        }
        this.analysis = null;
        this.style = null;
    }

    private String prepareVersion(DumperOptions.Version version) {
        if (version.major() != 1) {
            throw new EmitterException("unsupported YAML version: " + (Object)((Object)version));
        }
        return version.getRepresentation();
    }

    private String prepareTagHandle(String string) {
        if (string.length() == 0) {
            throw new EmitterException("tag handle must not be empty");
        }
        if (string.charAt(0) != '!' || string.charAt(string.length() - 1) != '!') {
            throw new EmitterException("tag handle must start and end with '!': " + string);
        }
        if (!"!".equals(string) && !HANDLE_FORMAT.matcher(string).matches()) {
            throw new EmitterException("invalid character in the tag handle: " + string);
        }
        return string;
    }

    private String prepareTagPrefix(String string) {
        if (string.length() == 0) {
            throw new EmitterException("tag prefix must not be empty");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = 0;
        if (string.charAt(0) == '!') {
            n2 = 1;
        }
        while (n2 < string.length()) {
            ++n2;
        }
        if (n < n2) {
            stringBuilder.append(string, n, n2);
        }
        return stringBuilder.toString();
    }

    private String prepareTag(String string) {
        int n;
        String string22;
        if (string.length() == 0) {
            throw new EmitterException("tag must not be empty");
        }
        if ("!".equals(string)) {
            return string;
        }
        String string3 = null;
        String string4 = string;
        for (String string22 : this.tagPrefixes.keySet()) {
            if (!string.startsWith(string22) || !"!".equals(string22) && string22.length() >= string.length()) continue;
            string3 = string22;
        }
        if (string3 != null) {
            string4 = string.substring(string3.length());
            string3 = this.tagPrefixes.get(string3);
        }
        String string5 = string22 = (n = string4.length()) > 0 ? string4.substring(0, n) : "";
        if (string3 != null) {
            return string3 + string22;
        }
        return "!<" + string22 + ">";
    }

    static String prepareAnchor(String string) {
        if (string.length() == 0) {
            throw new EmitterException("anchor must not be empty");
        }
        for (Character c : INVALID_ANCHOR) {
            if (string.indexOf(c.charValue()) <= -1) continue;
            throw new EmitterException("Invalid character '" + c + "' in the anchor: " + string);
        }
        Matcher matcher = SPACES_PATTERN.matcher(string);
        if (matcher.find()) {
            throw new EmitterException("Anchor may not contain spaces: " + string);
        }
        return string;
    }

    private static boolean hasLeadingZero(String string) {
        if (string.length() > 1 && string.charAt(0) == '0') {
            for (int i = 1; i < string.length(); ++i) {
                boolean bl;
                char c = string.charAt(i);
                boolean bl2 = bl = c >= '0' && c <= '9' || c == '_';
                if (bl) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private ScalarAnalysis analyzeScalar(String string) {
        int n;
        int n2;
        int n3;
        if (string.length() == 0) {
            return new ScalarAnalysis(string, true, false, false, true, true, false);
        }
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = Emitter.hasLeadingZero(string);
        boolean bl6 = false;
        boolean bl7 = false;
        boolean bl8 = false;
        boolean bl9 = false;
        boolean bl10 = false;
        boolean bl11 = false;
        if (string.startsWith("---") || string.startsWith("...")) {
            bl = true;
            bl2 = true;
        }
        boolean bl12 = true;
        boolean bl13 = string.length() == 1 || Constant.NULL_BL_T_LINEBR.has(string.codePointAt(1));
        boolean bl14 = false;
        boolean bl15 = false;
        int n4 = 0;
        while (n4 < string.length()) {
            n3 = string.codePointAt(n4);
            if (n4 == 0) {
                if ("#,[]{}&*!|>'\"%@`".indexOf(n3) != -1) {
                    bl2 = true;
                    bl = true;
                }
                if (n3 == 63 || n3 == 58) {
                    bl2 = true;
                    if (bl13) {
                        bl = true;
                    }
                }
                if (n3 == 45 && bl13) {
                    bl2 = true;
                    bl = true;
                }
            } else {
                if (",?[]{}".indexOf(n3) != -1) {
                    bl2 = true;
                }
                if (n3 == 58) {
                    bl2 = true;
                    if (bl13) {
                        bl = true;
                    }
                }
                if (n3 == 35 && bl12) {
                    bl2 = true;
                    bl = true;
                }
            }
            if ((n2 = Constant.LINEBR.has(n3)) != 0) {
                bl3 = true;
            }
            if (n3 != 10 && (32 > n3 || n3 > 126)) {
                if (n3 == 133 || n3 >= 160 && n3 <= 55295 || n3 >= 57344 && n3 <= 65533 || n3 >= 65536 && n3 <= 0x10FFFF) {
                    if (!this.allowUnicode) {
                        bl4 = true;
                    }
                } else {
                    bl4 = true;
                }
            }
            if (n3 == 32) {
                if (n4 == 0) {
                    bl6 = true;
                }
                if (n4 == string.length() - 1) {
                    bl8 = true;
                }
                if (bl15) {
                    bl10 = true;
                }
                bl14 = true;
                bl15 = false;
            } else if (n2 != 0) {
                if (n4 == 0) {
                    bl7 = true;
                }
                if (n4 == string.length() - 1) {
                    bl9 = true;
                }
                if (bl14) {
                    bl11 = true;
                }
                bl14 = false;
                bl15 = true;
            } else {
                bl14 = false;
                bl15 = false;
            }
            bl12 = Constant.NULL_BL_T.has(n3) || n2 != 0;
            bl13 = true;
            if ((n4 += Character.charCount(n3)) + 1 >= string.length() || (n = n4 + Character.charCount(string.codePointAt(n4))) >= string.length()) continue;
            bl13 = Constant.NULL_BL_T.has(string.codePointAt(n)) || n2 != 0;
        }
        n3 = 1;
        n2 = 1;
        n = 1;
        boolean bl16 = true;
        if (bl6 || bl7 || bl8 || bl9 || bl5) {
            n2 = 0;
            n3 = 0;
        }
        if (bl8) {
            bl16 = false;
        }
        if (bl10) {
            n = 0;
            n2 = 0;
            n3 = 0;
        }
        if (bl11 || bl4) {
            bl16 = false;
            n = 0;
            n2 = 0;
            n3 = 0;
        }
        if (bl3) {
            n3 = 0;
        }
        if (bl2) {
            n3 = 0;
        }
        if (bl) {
            n2 = 0;
        }
        return new ScalarAnalysis(string, false, bl3, n3 != 0, n2 != 0, n != 0, bl16);
    }

    void flushStream() throws IOException {
        this.stream.flush();
    }

    void writeStreamStart() {
    }

    void writeStreamEnd() throws IOException {
        this.flushStream();
    }

    void writeIndicator(String string, boolean bl, boolean bl2, boolean bl3) throws IOException {
        if (!this.whitespace && bl) {
            ++this.column;
            this.stream.write(SPACE);
        }
        this.whitespace = bl2;
        this.indention = this.indention && bl3;
        this.column += string.length();
        this.openEnded = false;
        this.stream.write(string);
    }

    void writeIndent() throws IOException {
        int n = this.indent != null ? this.indent : 0;
        if (!this.indention || this.column > n || this.column == n && !this.whitespace) {
            this.writeLineBreak(null);
        }
        this.writeWhitespace(n - this.column);
    }

    private void writeWhitespace(int n) throws IOException {
        if (n <= 0) {
            return;
        }
        this.whitespace = true;
        char[] cArray = new char[n];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = 32;
        }
        this.column += n;
        this.stream.write(cArray);
    }

    private void writeLineBreak(String string) throws IOException {
        this.whitespace = true;
        this.indention = true;
        this.column = 0;
        if (string == null) {
            this.stream.write(this.bestLineBreak);
        } else {
            this.stream.write(string);
        }
    }

    void writeVersionDirective(String string) throws IOException {
        this.stream.write("%YAML ");
        this.stream.write(string);
        this.writeLineBreak(null);
    }

    void writeTagDirective(String string, String string2) throws IOException {
        this.stream.write("%TAG ");
        this.stream.write(string);
        this.stream.write(SPACE);
        this.stream.write(string2);
        this.writeLineBreak(null);
    }

    private void writeSingleQuoted(String string, boolean bl) throws IOException {
        this.writeIndicator("'", true, false, true);
        boolean bl2 = false;
        boolean bl3 = false;
        int n = 0;
        for (int i = 0; i <= string.length(); ++i) {
            int n2;
            char c = '\u0000';
            if (i < string.length()) {
                c = string.charAt(i);
            }
            if (bl2) {
                if (c == '\u0000' || c != ' ') {
                    if (n + 1 == i && this.column > this.bestWidth && bl && n != 0 && i != string.length()) {
                        this.writeIndent();
                    } else {
                        n2 = i - n;
                        this.column += n2;
                        this.stream.write(string, n, n2);
                    }
                    n = i;
                }
            } else if (bl3) {
                if (c == '\u0000' || Constant.LINEBR.hasNo(c)) {
                    if (string.charAt(n) == '\n') {
                        this.writeLineBreak(null);
                    }
                    String string2 = string.substring(n, i);
                    for (char c2 : string2.toCharArray()) {
                        if (c2 == '\n') {
                            this.writeLineBreak(null);
                            continue;
                        }
                        this.writeLineBreak(String.valueOf(c2));
                    }
                    this.writeIndent();
                    n = i;
                }
            } else if (Constant.LINEBR.has(c, "\u0000 '") && n < i) {
                n2 = i - n;
                this.column += n2;
                this.stream.write(string, n, n2);
                n = i;
            }
            if (c == '\'') {
                this.column += 2;
                this.stream.write("''");
                n = i + 1;
            }
            if (c == '\u0000') continue;
            bl2 = c == ' ';
            bl3 = Constant.LINEBR.has(c);
        }
        this.writeIndicator("'", false, false, true);
    }

    private void writeDoubleQuoted(String string, boolean bl) throws IOException {
        this.writeIndicator("\"", true, false, true);
        int n = 0;
        for (int i = 0; i <= string.length(); ++i) {
            Character c = null;
            if (i < string.length()) {
                c = Character.valueOf(string.charAt(i));
            }
            if (c == null || "\"\\\u0085\u2028\u2029\ufeff".indexOf(c.charValue()) != -1 || ' ' > c.charValue() || c.charValue() > '~') {
                if (n < i) {
                    int n2 = i - n;
                    this.column += n2;
                    this.stream.write(string, n, n2);
                    n = i;
                }
                if (c != null) {
                    String string2;
                    if (ESCAPE_REPLACEMENTS.containsKey(c)) {
                        string2 = "\\" + ESCAPE_REPLACEMENTS.get(c);
                    } else {
                        int n3;
                        if (Character.isHighSurrogate(c.charValue()) && i + 1 < string.length()) {
                            char c2 = string.charAt(i + 1);
                            n3 = Character.toCodePoint(c.charValue(), c2);
                        } else {
                            n3 = c.charValue();
                        }
                        if (this.allowUnicode && StreamReader.isPrintable(n3)) {
                            string2 = String.valueOf(Character.toChars(n3));
                            if (Character.charCount(n3) == 2) {
                                ++i;
                            }
                        } else if (c.charValue() <= '\u00ff') {
                            String string3 = "0" + Integer.toString(c.charValue(), 16);
                            string2 = "\\x" + string3.substring(string3.length() - 2);
                        } else if (Character.charCount(n3) == 2) {
                            ++i;
                            String string4 = "000" + Long.toHexString(n3);
                            string2 = "\\U" + string4.substring(string4.length() - 8);
                        } else {
                            String string5 = "000" + Integer.toString(c.charValue(), 16);
                            string2 = "\\u" + string5.substring(string5.length() - 4);
                        }
                    }
                    this.column += string2.length();
                    this.stream.write(string2);
                    n = i + 1;
                }
            }
            if (0 >= i || i >= string.length() - 1 || c.charValue() != ' ' && n < i || this.column + (i - n) <= this.bestWidth || !bl) continue;
            String string6 = n >= i ? "\\" : string.substring(n, i) + "\\";
            if (n < i) {
                n = i;
            }
            this.column += string6.length();
            this.stream.write(string6);
            this.writeIndent();
            this.whitespace = false;
            this.indention = false;
            if (string.charAt(n) != ' ') continue;
            string6 = "\\";
            this.column += string6.length();
            this.stream.write(string6);
        }
        this.writeIndicator("\"", false, false, true);
    }

    private boolean writeCommentLines(List<CommentLine> list) throws IOException {
        boolean bl = false;
        if (this.emitComments) {
            int n = 0;
            boolean bl2 = true;
            for (CommentLine commentLine : list) {
                if (commentLine.getCommentType() != CommentType.BLANK_LINE) {
                    if (bl2) {
                        bl2 = false;
                        this.writeIndicator("#", commentLine.getCommentType() == CommentType.IN_LINE, false, true);
                        n = this.column > 0 ? this.column - 1 : 0;
                    } else {
                        this.writeWhitespace(n);
                        this.writeIndicator("#", false, false, true);
                    }
                    this.stream.write(commentLine.getValue());
                    this.writeLineBreak(null);
                } else {
                    this.writeLineBreak(null);
                    this.writeIndent();
                }
                bl = true;
            }
        }
        return bl;
    }

    private void writeBlockComment() throws IOException {
        if (!this.blockCommentsCollector.isEmpty()) {
            this.writeIndent();
            this.writeCommentLines(this.blockCommentsCollector.consume());
        }
    }

    private boolean writeInlineComments() throws IOException {
        return this.writeCommentLines(this.inlineCommentsCollector.consume());
    }

    private String determineBlockHints(String string) {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        if (Constant.LINEBR.has(string.charAt(0), " ")) {
            stringBuilder.append(this.bestIndent);
        }
        if (Constant.LINEBR.hasNo(c = string.charAt(string.length() - 1))) {
            stringBuilder.append("-");
        } else if (string.length() == 1 || Constant.LINEBR.has(string.charAt(string.length() - 2))) {
            stringBuilder.append("+");
        }
        return stringBuilder.toString();
    }

    void writeFolded(String string, boolean bl) throws IOException {
        String string2 = this.determineBlockHints(string);
        this.writeIndicator(">" + string2, true, false, true);
        if (string2.length() > 0 && string2.charAt(string2.length() - 1) == '+') {
            this.openEnded = true;
        }
        if (!this.writeInlineComments()) {
            this.writeLineBreak(null);
        }
        boolean bl2 = true;
        boolean bl3 = false;
        boolean bl4 = true;
        int n = 0;
        for (int i = 0; i <= string.length(); ++i) {
            char c = '\u0000';
            if (i < string.length()) {
                c = string.charAt(i);
            }
            if (bl4) {
                if (c == '\u0000' || Constant.LINEBR.hasNo(c)) {
                    if (!bl2 && c != '\u0000' && c != ' ' && string.charAt(n) == '\n') {
                        this.writeLineBreak(null);
                    }
                    bl2 = c == ' ';
                    String string3 = string.substring(n, i);
                    for (char c2 : string3.toCharArray()) {
                        if (c2 == '\n') {
                            this.writeLineBreak(null);
                            continue;
                        }
                        this.writeLineBreak(String.valueOf(c2));
                    }
                    if (c != '\u0000') {
                        this.writeIndent();
                    }
                    n = i;
                }
            } else if (bl3) {
                if (c != ' ') {
                    if (n + 1 == i && this.column > this.bestWidth && bl) {
                        this.writeIndent();
                    } else {
                        int n2 = i - n;
                        this.column += n2;
                        this.stream.write(string, n, n2);
                    }
                    n = i;
                }
            } else if (Constant.LINEBR.has(c, "\u0000 ")) {
                int n3 = i - n;
                this.column += n3;
                this.stream.write(string, n, n3);
                if (c == '\u0000') {
                    this.writeLineBreak(null);
                }
                n = i;
            }
            if (c == '\u0000') continue;
            bl4 = Constant.LINEBR.has(c);
            bl3 = c == ' ';
        }
    }

    void writeLiteral(String string) throws IOException {
        String string2 = this.determineBlockHints(string);
        this.writeIndicator("|" + string2, true, false, true);
        if (string2.length() > 0 && string2.charAt(string2.length() - 1) == '+') {
            this.openEnded = true;
        }
        if (!this.writeInlineComments()) {
            this.writeLineBreak(null);
        }
        boolean bl = true;
        int n = 0;
        for (int i = 0; i <= string.length(); ++i) {
            char c = '\u0000';
            if (i < string.length()) {
                c = string.charAt(i);
            }
            if (bl) {
                if (c == '\u0000' || Constant.LINEBR.hasNo(c)) {
                    String string3 = string.substring(n, i);
                    for (char c2 : string3.toCharArray()) {
                        if (c2 == '\n') {
                            this.writeLineBreak(null);
                            continue;
                        }
                        this.writeLineBreak(String.valueOf(c2));
                    }
                    if (c != '\u0000') {
                        this.writeIndent();
                    }
                    n = i;
                }
            } else if (c == '\u0000' || Constant.LINEBR.has(c)) {
                this.stream.write(string, n, i - n);
                if (c == '\u0000') {
                    this.writeLineBreak(null);
                }
                n = i;
            }
            if (c == '\u0000') continue;
            bl = Constant.LINEBR.has(c);
        }
    }

    void writePlain(String string, boolean bl) throws IOException {
        if (this.rootContext) {
            this.openEnded = true;
        }
        if (string.length() == 0) {
            return;
        }
        if (!this.whitespace) {
            ++this.column;
            this.stream.write(SPACE);
        }
        this.whitespace = false;
        this.indention = false;
        boolean bl2 = false;
        boolean bl3 = false;
        int n = 0;
        for (int i = 0; i <= string.length(); ++i) {
            int n2;
            char c = '\u0000';
            if (i < string.length()) {
                c = string.charAt(i);
            }
            if (bl2) {
                if (c != ' ') {
                    if (n + 1 == i && this.column > this.bestWidth && bl) {
                        this.writeIndent();
                        this.whitespace = false;
                        this.indention = false;
                    } else {
                        n2 = i - n;
                        this.column += n2;
                        this.stream.write(string, n, n2);
                    }
                    n = i;
                }
            } else if (bl3) {
                if (Constant.LINEBR.hasNo(c)) {
                    if (string.charAt(n) == '\n') {
                        this.writeLineBreak(null);
                    }
                    String string2 = string.substring(n, i);
                    for (char c2 : string2.toCharArray()) {
                        if (c2 == '\n') {
                            this.writeLineBreak(null);
                            continue;
                        }
                        this.writeLineBreak(String.valueOf(c2));
                    }
                    this.writeIndent();
                    this.whitespace = false;
                    this.indention = false;
                    n = i;
                }
            } else if (Constant.LINEBR.has(c, "\u0000 ")) {
                n2 = i - n;
                this.column += n2;
                this.stream.write(string, n, n2);
                n = i;
            }
            if (c == '\u0000') continue;
            bl2 = c == ' ';
            bl3 = Constant.LINEBR.has(c);
        }
    }

    static Event access$100(Emitter emitter) {
        return emitter.event;
    }

    static EmitterState access$202(Emitter emitter, EmitterState emitterState) {
        emitter.state = emitterState;
        return emitter.state;
    }

    static boolean access$400(Emitter emitter) {
        return emitter.openEnded;
    }

    static String access$500(Emitter emitter, DumperOptions.Version version) {
        return emitter.prepareVersion(version);
    }

    static Map access$602(Emitter emitter, Map map) {
        emitter.tagPrefixes = map;
        return emitter.tagPrefixes;
    }

    static Map access$700() {
        return DEFAULT_TAG_PREFIXES;
    }

    static Map access$600(Emitter emitter) {
        return emitter.tagPrefixes;
    }

    static String access$800(Emitter emitter, String string) {
        return emitter.prepareTagHandle(string);
    }

    static String access$900(Emitter emitter, String string) {
        return emitter.prepareTagPrefix(string);
    }

    static Boolean access$1000(Emitter emitter) {
        return emitter.canonical;
    }

    static boolean access$1100(Emitter emitter) {
        return emitter.checkEmptyDocument();
    }

    static CommentEventsCollector access$1400(Emitter emitter) {
        return emitter.blockCommentsCollector;
    }

    static void access$1500(Emitter emitter) throws IOException {
        emitter.writeBlockComment();
    }

    static Event access$102(Emitter emitter, Event event) {
        emitter.event = event;
        return emitter.event;
    }

    static ArrayStack access$1700(Emitter emitter) {
        return emitter.states;
    }

    static void access$1800(Emitter emitter, boolean bl, boolean bl2, boolean bl3) throws IOException {
        emitter.expectNode(bl, bl2, bl3);
    }

    static Integer access$2002(Emitter emitter, Integer n) {
        emitter.indent = n;
        return emitter.indent;
    }

    static ArrayStack access$2100(Emitter emitter) {
        return emitter.indents;
    }

    static int access$2210(Emitter emitter) {
        return emitter.flowLevel--;
    }

    static CommentEventsCollector access$2300(Emitter emitter) {
        return emitter.inlineCommentsCollector;
    }

    static boolean access$2400(Emitter emitter) throws IOException {
        return emitter.writeInlineComments();
    }

    static int access$2500(Emitter emitter) {
        return emitter.column;
    }

    static int access$2600(Emitter emitter) {
        return emitter.bestWidth;
    }

    static boolean access$2700(Emitter emitter) {
        return emitter.splitLines;
    }

    static Boolean access$2800(Emitter emitter) {
        return emitter.prettyFlow;
    }

    static boolean access$3100(Emitter emitter) {
        return emitter.checkSimpleKey();
    }

    static boolean access$3600(Emitter emitter) {
        return emitter.indentWithIndicator;
    }

    static int access$3700(Emitter emitter) {
        return emitter.indicatorIndent;
    }

    static void access$3800(Emitter emitter, int n) throws IOException {
        emitter.writeWhitespace(n);
    }

    static Integer access$2000(Emitter emitter) {
        return emitter.indent;
    }

    static void access$3900(Emitter emitter, boolean bl, boolean bl2) {
        emitter.increaseIndent(bl, bl2);
    }

    static ScalarAnalysis access$4002(Emitter emitter, ScalarAnalysis scalarAnalysis) {
        emitter.analysis = scalarAnalysis;
        return emitter.analysis;
    }

    static ScalarAnalysis access$4100(Emitter emitter, String string) {
        return emitter.analyzeScalar(string);
    }

    static ScalarAnalysis access$4000(Emitter emitter) {
        return emitter.analysis;
    }

    static boolean access$4500(Emitter emitter, Event event) {
        return emitter.isFoldedOrLiteral(event);
    }

    static {
        INVALID_ANCHOR.add(Character.valueOf('['));
        INVALID_ANCHOR.add(Character.valueOf(']'));
        INVALID_ANCHOR.add(Character.valueOf('{'));
        INVALID_ANCHOR.add(Character.valueOf('}'));
        INVALID_ANCHOR.add(Character.valueOf(','));
        INVALID_ANCHOR.add(Character.valueOf('*'));
        INVALID_ANCHOR.add(Character.valueOf('&'));
        ESCAPE_REPLACEMENTS = new HashMap<Character, String>();
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u0000'), "0");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u0007'), "a");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\b'), "b");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\t'), "t");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\n'), "n");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u000b'), "v");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\f'), "f");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\r'), "r");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u001b'), "e");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\"'), "\"");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\\'), "\\");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u0085'), "N");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u00a0'), "_");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u2028'), "L");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\u2029'), "P");
        DEFAULT_TAG_PREFIXES = new LinkedHashMap<String, String>();
        DEFAULT_TAG_PREFIXES.put("!", "!");
        DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
        HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
    }

    private class ExpectBlockMappingValue
    implements EmitterState {
        final Emitter this$0;

        private ExpectBlockMappingValue(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            this.this$0.writeIndent();
            this.this$0.writeIndicator(":", true, false, false);
            Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$2400(this.this$0);
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$1500(this.this$0);
            Emitter.access$1700(this.this$0).push(new ExpectBlockMappingKey(this.this$0, false));
            Emitter.access$1800(this.this$0, false, true, false);
            Emitter.access$2300(this.this$0).collectEvents(Emitter.access$100(this.this$0));
            Emitter.access$2400(this.this$0);
        }

        ExpectBlockMappingValue(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectBlockMappingSimpleValue
    implements EmitterState {
        final Emitter this$0;

        private ExpectBlockMappingSimpleValue(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, true);
            Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            if (!Emitter.access$4500(this.this$0, Emitter.access$100(this.this$0)) && Emitter.access$2400(this.this$0)) {
                Emitter.access$3900(this.this$0, true, false);
                this.this$0.writeIndent();
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
            }
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            if (!Emitter.access$1400(this.this$0).isEmpty()) {
                Emitter.access$3900(this.this$0, true, false);
                Emitter.access$1500(this.this$0);
                this.this$0.writeIndent();
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
            }
            Emitter.access$1700(this.this$0).push(new ExpectBlockMappingKey(this.this$0, false));
            Emitter.access$1800(this.this$0, false, true, false);
            Emitter.access$2300(this.this$0).collectEvents();
            Emitter.access$2400(this.this$0);
        }

        ExpectBlockMappingSimpleValue(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectBlockMappingKey
    implements EmitterState {
        private final boolean first;
        final Emitter this$0;

        public ExpectBlockMappingKey(Emitter emitter, boolean bl) {
            this.this$0 = emitter;
            this.first = bl;
        }

        @Override
        public void expect() throws IOException {
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$1500(this.this$0);
            if (!this.first && Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else {
                this.this$0.writeIndent();
                if (Emitter.access$3100(this.this$0)) {
                    Emitter.access$1700(this.this$0).push(new ExpectBlockMappingSimpleValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, true);
                } else {
                    this.this$0.writeIndicator("?", true, false, false);
                    Emitter.access$1700(this.this$0).push(new ExpectBlockMappingValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, false);
                }
            }
        }
    }

    private class ExpectFirstBlockMappingKey
    implements EmitterState {
        final Emitter this$0;

        private ExpectFirstBlockMappingKey(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            new ExpectBlockMappingKey(this.this$0, true).expect();
        }

        ExpectFirstBlockMappingKey(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectBlockSequenceItem
    implements EmitterState {
        private final boolean first;
        final Emitter this$0;

        public ExpectBlockSequenceItem(Emitter emitter, boolean bl) {
            this.this$0 = emitter;
            this.first = bl;
        }

        @Override
        public void expect() throws IOException {
            if (!this.first && Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else if (Emitter.access$100(this.this$0) instanceof CommentEvent) {
                Emitter.access$1400(this.this$0).collectEvents(Emitter.access$100(this.this$0));
            } else {
                this.this$0.writeIndent();
                if (!Emitter.access$3600(this.this$0) || this.first) {
                    Emitter.access$3800(this.this$0, Emitter.access$3700(this.this$0));
                }
                this.this$0.writeIndicator("-", true, false, false);
                if (Emitter.access$3600(this.this$0) && this.first) {
                    Emitter.access$2002(this.this$0, Emitter.access$2000(this.this$0) + Emitter.access$3700(this.this$0));
                }
                if (!Emitter.access$1400(this.this$0).isEmpty()) {
                    Emitter.access$3900(this.this$0, false, false);
                    Emitter.access$1500(this.this$0);
                    if (Emitter.access$100(this.this$0) instanceof ScalarEvent) {
                        Emitter.access$4002(this.this$0, Emitter.access$4100(this.this$0, ((ScalarEvent)Emitter.access$100(this.this$0)).getValue()));
                        if (!Emitter.access$4000(this.this$0).isEmpty()) {
                            this.this$0.writeIndent();
                        }
                    }
                    Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                }
                Emitter.access$1700(this.this$0).push(new ExpectBlockSequenceItem(this.this$0, false));
                Emitter.access$1800(this.this$0, false, false, false);
                Emitter.access$2300(this.this$0).collectEvents();
                Emitter.access$2400(this.this$0);
            }
        }
    }

    private class ExpectFirstBlockSequenceItem
    implements EmitterState {
        final Emitter this$0;

        private ExpectFirstBlockSequenceItem(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            new ExpectBlockSequenceItem(this.this$0, true).expect();
        }

        ExpectFirstBlockSequenceItem(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFlowMappingValue
    implements EmitterState {
        final Emitter this$0;

        private ExpectFlowMappingValue(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.access$1000(this.this$0).booleanValue() || Emitter.access$2500(this.this$0) > Emitter.access$2600(this.this$0) || Emitter.access$2800(this.this$0).booleanValue()) {
                this.this$0.writeIndent();
            }
            this.this$0.writeIndicator(":", true, false, true);
            Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$2400(this.this$0);
            Emitter.access$1700(this.this$0).push(new ExpectFlowMappingKey(this.this$0, null));
            Emitter.access$1800(this.this$0, false, true, false);
            Emitter.access$2300(this.this$0).collectEvents(Emitter.access$100(this.this$0));
            Emitter.access$2400(this.this$0);
        }

        ExpectFlowMappingValue(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFlowMappingSimpleValue
    implements EmitterState {
        final Emitter this$0;

        private ExpectFlowMappingSimpleValue(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, true);
            Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$2400(this.this$0);
            Emitter.access$1700(this.this$0).push(new ExpectFlowMappingKey(this.this$0, null));
            Emitter.access$1800(this.this$0, false, true, false);
            Emitter.access$2300(this.this$0).collectEvents(Emitter.access$100(this.this$0));
            Emitter.access$2400(this.this$0);
        }

        ExpectFlowMappingSimpleValue(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFlowMappingKey
    implements EmitterState {
        final Emitter this$0;

        private ExpectFlowMappingKey(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$2210(this.this$0);
                if (Emitter.access$1000(this.this$0).booleanValue()) {
                    this.this$0.writeIndicator(",", false, false, true);
                    this.this$0.writeIndent();
                }
                if (Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("}", false, false, true);
                Emitter.access$2300(this.this$0).collectEvents();
                Emitter.access$2400(this.this$0);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else {
                this.this$0.writeIndicator(",", false, false, true);
                Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
                Emitter.access$1500(this.this$0);
                if (Emitter.access$1000(this.this$0).booleanValue() || Emitter.access$2500(this.this$0) > Emitter.access$2600(this.this$0) && Emitter.access$2700(this.this$0) || Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                if (!Emitter.access$1000(this.this$0).booleanValue() && Emitter.access$3100(this.this$0)) {
                    Emitter.access$1700(this.this$0).push(new ExpectFlowMappingSimpleValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, true);
                } else {
                    this.this$0.writeIndicator("?", true, false, true);
                    Emitter.access$1700(this.this$0).push(new ExpectFlowMappingValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, false);
                }
            }
        }

        ExpectFlowMappingKey(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFirstFlowMappingKey
    implements EmitterState {
        final Emitter this$0;

        private ExpectFirstFlowMappingKey(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$1500(this.this$0);
            if (Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$2210(this.this$0);
                this.this$0.writeIndicator("}", false, false, true);
                Emitter.access$2300(this.this$0).collectEvents();
                Emitter.access$2400(this.this$0);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else {
                if (Emitter.access$1000(this.this$0).booleanValue() || Emitter.access$2500(this.this$0) > Emitter.access$2600(this.this$0) && Emitter.access$2700(this.this$0) || Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                if (!Emitter.access$1000(this.this$0).booleanValue() && Emitter.access$3100(this.this$0)) {
                    Emitter.access$1700(this.this$0).push(new ExpectFlowMappingSimpleValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, true);
                } else {
                    this.this$0.writeIndicator("?", true, false, true);
                    Emitter.access$1700(this.this$0).push(new ExpectFlowMappingValue(this.this$0, null));
                    Emitter.access$1800(this.this$0, false, true, false);
                }
            }
        }

        ExpectFirstFlowMappingKey(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFlowSequenceItem
    implements EmitterState {
        final Emitter this$0;

        private ExpectFlowSequenceItem(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$2210(this.this$0);
                if (Emitter.access$1000(this.this$0).booleanValue()) {
                    this.this$0.writeIndicator(",", false, false, true);
                    this.this$0.writeIndent();
                } else if (Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("]", false, false, true);
                Emitter.access$2300(this.this$0).collectEvents();
                Emitter.access$2400(this.this$0);
                if (Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else if (Emitter.access$100(this.this$0) instanceof CommentEvent) {
                Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEvents(Emitter.access$100(this.this$0)));
            } else {
                this.this$0.writeIndicator(",", false, false, true);
                Emitter.access$1500(this.this$0);
                if (Emitter.access$1000(this.this$0).booleanValue() || Emitter.access$2500(this.this$0) > Emitter.access$2600(this.this$0) && Emitter.access$2700(this.this$0) || Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                Emitter.access$1700(this.this$0).push(new ExpectFlowSequenceItem(this.this$0));
                Emitter.access$1800(this.this$0, false, false, false);
                Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEvents(Emitter.access$100(this.this$0)));
                Emitter.access$2400(this.this$0);
            }
        }

        ExpectFlowSequenceItem(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectFirstFlowSequenceItem
    implements EmitterState {
        final Emitter this$0;

        private ExpectFirstFlowSequenceItem(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$2002(this.this$0, (Integer)Emitter.access$2100(this.this$0).pop());
                Emitter.access$2210(this.this$0);
                this.this$0.writeIndicator("]", false, false, true);
                Emitter.access$2300(this.this$0).collectEvents();
                Emitter.access$2400(this.this$0);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1700(this.this$0).pop());
            } else if (Emitter.access$100(this.this$0) instanceof CommentEvent) {
                Emitter.access$1400(this.this$0).collectEvents(Emitter.access$100(this.this$0));
                Emitter.access$1500(this.this$0);
            } else {
                if (Emitter.access$1000(this.this$0).booleanValue() || Emitter.access$2500(this.this$0) > Emitter.access$2600(this.this$0) && Emitter.access$2700(this.this$0) || Emitter.access$2800(this.this$0).booleanValue()) {
                    this.this$0.writeIndent();
                }
                Emitter.access$1700(this.this$0).push(new ExpectFlowSequenceItem(this.this$0, null));
                Emitter.access$1800(this.this$0, false, false, false);
                Emitter.access$102(this.this$0, Emitter.access$2300(this.this$0).collectEvents(Emitter.access$100(this.this$0)));
                Emitter.access$2400(this.this$0);
            }
        }

        ExpectFirstFlowSequenceItem(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectDocumentRoot
    implements EmitterState {
        final Emitter this$0;

        private ExpectDocumentRoot(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            if (!Emitter.access$1400(this.this$0).isEmpty()) {
                Emitter.access$1500(this.this$0);
                if (Emitter.access$100(this.this$0) instanceof DocumentEndEvent) {
                    new ExpectDocumentEnd(this.this$0, null).expect();
                    return;
                }
            }
            Emitter.access$1700(this.this$0).push(new ExpectDocumentEnd(this.this$0, null));
            Emitter.access$1800(this.this$0, true, false, false);
        }

        ExpectDocumentRoot(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectDocumentEnd
    implements EmitterState {
        final Emitter this$0;

        private ExpectDocumentEnd(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            Emitter.access$102(this.this$0, Emitter.access$1400(this.this$0).collectEventsAndPoll(Emitter.access$100(this.this$0)));
            Emitter.access$1500(this.this$0);
            if (Emitter.access$100(this.this$0) instanceof DocumentEndEvent) {
                this.this$0.writeIndent();
                if (((DocumentEndEvent)Emitter.access$100(this.this$0)).getExplicit()) {
                    this.this$0.writeIndicator("...", true, false, true);
                    this.this$0.writeIndent();
                }
            } else {
                throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.access$100(this.this$0));
            }
            this.this$0.flushStream();
            Emitter.access$202(this.this$0, new ExpectDocumentStart(this.this$0, false));
        }

        ExpectDocumentEnd(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectDocumentStart
    implements EmitterState {
        private final boolean first;
        final Emitter this$0;

        public ExpectDocumentStart(Emitter emitter, boolean bl) {
            this.this$0 = emitter;
            this.first = bl;
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof DocumentStartEvent) {
                boolean bl;
                Object object;
                DocumentStartEvent documentStartEvent = (DocumentStartEvent)Emitter.access$100(this.this$0);
                if ((documentStartEvent.getVersion() != null || documentStartEvent.getTags() != null) && Emitter.access$400(this.this$0)) {
                    this.this$0.writeIndicator("...", true, false, true);
                    this.this$0.writeIndent();
                }
                if (documentStartEvent.getVersion() != null) {
                    object = Emitter.access$500(this.this$0, documentStartEvent.getVersion());
                    this.this$0.writeVersionDirective((String)object);
                }
                Emitter.access$602(this.this$0, new LinkedHashMap(Emitter.access$700()));
                if (documentStartEvent.getTags() != null) {
                    object = new TreeSet<String>(documentStartEvent.getTags().keySet());
                    Iterator iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        String string = (String)iterator2.next();
                        String string2 = documentStartEvent.getTags().get(string);
                        Emitter.access$600(this.this$0).put(string2, string);
                        String string3 = Emitter.access$800(this.this$0, string);
                        String string4 = Emitter.access$900(this.this$0, string2);
                        this.this$0.writeTagDirective(string3, string4);
                    }
                }
                boolean bl2 = bl = this.first && !documentStartEvent.getExplicit() && Emitter.access$1000(this.this$0) == false && documentStartEvent.getVersion() == null && (documentStartEvent.getTags() == null || documentStartEvent.getTags().isEmpty()) && !Emitter.access$1100(this.this$0);
                if (!bl) {
                    this.this$0.writeIndent();
                    this.this$0.writeIndicator("---", true, false, true);
                    if (Emitter.access$1000(this.this$0).booleanValue()) {
                        this.this$0.writeIndent();
                    }
                }
                Emitter.access$202(this.this$0, new ExpectDocumentRoot(this.this$0, null));
            } else if (Emitter.access$100(this.this$0) instanceof StreamEndEvent) {
                this.this$0.writeStreamEnd();
                Emitter.access$202(this.this$0, new ExpectNothing(this.this$0, null));
            } else if (Emitter.access$100(this.this$0) instanceof CommentEvent) {
                Emitter.access$1400(this.this$0).collectEvents(Emitter.access$100(this.this$0));
                Emitter.access$1500(this.this$0);
            } else {
                throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.access$100(this.this$0));
            }
        }
    }

    private class ExpectFirstDocumentStart
    implements EmitterState {
        final Emitter this$0;

        private ExpectFirstDocumentStart(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            new ExpectDocumentStart(this.this$0, true).expect();
        }

        ExpectFirstDocumentStart(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectNothing
    implements EmitterState {
        final Emitter this$0;

        private ExpectNothing(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            throw new EmitterException("expecting nothing, but got " + Emitter.access$100(this.this$0));
        }

        ExpectNothing(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }

    private class ExpectStreamStart
    implements EmitterState {
        final Emitter this$0;

        private ExpectStreamStart(Emitter emitter) {
            this.this$0 = emitter;
        }

        @Override
        public void expect() throws IOException {
            if (!(Emitter.access$100(this.this$0) instanceof StreamStartEvent)) {
                throw new EmitterException("expected StreamStartEvent, but got " + Emitter.access$100(this.this$0));
            }
            this.this$0.writeStreamStart();
            Emitter.access$202(this.this$0, new ExpectFirstDocumentStart(this.this$0, null));
        }

        ExpectStreamStart(Emitter emitter, 1 var2_2) {
            this(emitter);
        }
    }
}

