/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;
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
    private Map<String, String> tagPrefixes;
    private String preparedAnchor;
    private String preparedTag;
    private ScalarAnalysis analysis;
    private DumperOptions.ScalarStyle style;
    private static final Pattern HANDLE_FORMAT;

    public Emitter(Writer stream, DumperOptions opts) {
        this.stream = stream;
        this.states = new ArrayStack(100);
        this.state = new ExpectStreamStart();
        this.events = new ArrayBlockingQueue<Event>(100);
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
        this.canonical = opts.isCanonical();
        this.prettyFlow = opts.isPrettyFlow();
        this.allowUnicode = opts.isAllowUnicode();
        this.bestIndent = 2;
        if (opts.getIndent() > 1 && opts.getIndent() < 10) {
            this.bestIndent = opts.getIndent();
        }
        this.indicatorIndent = opts.getIndicatorIndent();
        this.indentWithIndicator = opts.getIndentWithIndicator();
        this.bestWidth = 80;
        if (opts.getWidth() > this.bestIndent * 2) {
            this.bestWidth = opts.getWidth();
        }
        this.bestLineBreak = opts.getLineBreak().getString().toCharArray();
        this.splitLines = opts.getSplitLines();
        this.maxSimpleKeyLength = opts.getMaxSimpleKeyLength();
        this.tagPrefixes = new LinkedHashMap<String, String>();
        this.preparedAnchor = null;
        this.preparedTag = null;
        this.analysis = null;
        this.style = null;
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
            return true;
        }
        Event event = this.events.peek();
        if (event instanceof DocumentStartEvent) {
            return this.needEvents(1);
        }
        if (event instanceof SequenceStartEvent) {
            return this.needEvents(2);
        }
        if (!(event instanceof MappingStartEvent)) return false;
        return this.needEvents(3);
    }

    private boolean needEvents(int count) {
        int level = 0;
        Iterator iter = this.events.iterator();
        iter.next();
        while (iter.hasNext()) {
            Event event = (Event)iter.next();
            if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) {
                ++level;
            } else if (event instanceof DocumentEndEvent || event instanceof CollectionEndEvent) {
                --level;
            } else if (event instanceof StreamEndEvent) {
                return false;
            }
            if (level >= 0) continue;
            return false;
        }
        if (this.events.size() >= count + 1) return false;
        return true;
    }

    private void increaseIndent(boolean flow, boolean indentless) {
        this.indents.push(this.indent);
        if (this.indent != null) {
            if (indentless) return;
            Emitter emitter = this;
            emitter.indent = emitter.indent + this.bestIndent;
            return;
        }
        if (flow) {
            this.indent = this.bestIndent;
            return;
        }
        this.indent = 0;
    }

    private void expectNode(boolean root, boolean mapping, boolean simpleKey) throws IOException {
        this.rootContext = root;
        this.mappingContext = mapping;
        this.simpleKeyContext = simpleKey;
        if (this.event instanceof AliasEvent) {
            this.expectAlias();
            return;
        }
        if (!(this.event instanceof ScalarEvent)) {
            if (!(this.event instanceof CollectionStartEvent)) throw new EmitterException("expected NodeEvent, but got " + this.event);
        }
        this.processAnchor("&");
        this.processTag();
        if (this.event instanceof ScalarEvent) {
            this.expectScalar();
            return;
        }
        if (this.event instanceof SequenceStartEvent) {
            if (!(this.flowLevel != 0 || this.canonical.booleanValue() || ((SequenceStartEvent)this.event).isFlow() || this.checkEmptySequence())) {
                this.expectBlockSequence();
                return;
            }
            this.expectFlowSequence();
            return;
        }
        if (!(this.flowLevel != 0 || this.canonical.booleanValue() || ((MappingStartEvent)this.event).isFlow() || this.checkEmptyMapping())) {
            this.expectBlockMapping();
            return;
        }
        this.expectFlowMapping();
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
        this.writeIndicator("[", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow.booleanValue()) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowSequenceItem();
    }

    private void expectFlowMapping() throws IOException {
        this.writeIndicator("{", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow.booleanValue()) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowMappingKey();
    }

    private void expectBlockSequence() throws IOException {
        boolean indentless = this.mappingContext && !this.indention;
        this.increaseIndent(false, indentless);
        this.state = new ExpectFirstBlockSequenceItem();
    }

    private void expectBlockMapping() throws IOException {
        this.increaseIndent(false, false);
        this.state = new ExpectFirstBlockMappingKey();
    }

    private boolean checkEmptySequence() {
        if (!(this.event instanceof SequenceStartEvent)) return false;
        if (this.events.isEmpty()) return false;
        if (!(this.events.peek() instanceof SequenceEndEvent)) return false;
        return true;
    }

    private boolean checkEmptyMapping() {
        if (!(this.event instanceof MappingStartEvent)) return false;
        if (this.events.isEmpty()) return false;
        if (!(this.events.peek() instanceof MappingEndEvent)) return false;
        return true;
    }

    private boolean checkEmptyDocument() {
        if (!(this.event instanceof DocumentStartEvent)) return false;
        if (this.events.isEmpty()) {
            return false;
        }
        Event event = this.events.peek();
        if (!(event instanceof ScalarEvent)) return false;
        ScalarEvent e = (ScalarEvent)event;
        if (e.getAnchor() != null) return false;
        if (e.getTag() != null) return false;
        if (e.getImplicit() == null) return false;
        if (e.getValue().length() != 0) return false;
        return true;
    }

    private boolean checkSimpleKey() {
        int length = 0;
        if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
            if (this.preparedAnchor == null) {
                this.preparedAnchor = Emitter.prepareAnchor(((NodeEvent)this.event).getAnchor());
            }
            length += this.preparedAnchor.length();
        }
        String tag = null;
        if (this.event instanceof ScalarEvent) {
            tag = ((ScalarEvent)this.event).getTag();
        } else if (this.event instanceof CollectionStartEvent) {
            tag = ((CollectionStartEvent)this.event).getTag();
        }
        if (tag != null) {
            if (this.preparedTag == null) {
                this.preparedTag = this.prepareTag(tag);
            }
            length += this.preparedTag.length();
        }
        if (this.event instanceof ScalarEvent) {
            if (this.analysis == null) {
                this.analysis = this.analyzeScalar(((ScalarEvent)this.event).getValue());
            }
            length += this.analysis.getScalar().length();
        }
        if (length >= this.maxSimpleKeyLength) return false;
        if (this.event instanceof AliasEvent) return true;
        if (this.event instanceof ScalarEvent && !this.analysis.isEmpty()) {
            if (!this.analysis.isMultiline()) return true;
        }
        if (this.checkEmptySequence()) return true;
        if (!this.checkEmptyMapping()) return false;
        return true;
    }

    private void processAnchor(String indicator) throws IOException {
        NodeEvent ev = (NodeEvent)this.event;
        if (ev.getAnchor() == null) {
            this.preparedAnchor = null;
            return;
        }
        if (this.preparedAnchor == null) {
            this.preparedAnchor = Emitter.prepareAnchor(ev.getAnchor());
        }
        this.writeIndicator(indicator + this.preparedAnchor, true, false, false);
        this.preparedAnchor = null;
    }

    private void processTag() throws IOException {
        String tag = null;
        if (this.event instanceof ScalarEvent) {
            ScalarEvent ev = (ScalarEvent)this.event;
            tag = ev.getTag();
            if (this.style == null) {
                this.style = this.chooseScalarStyle();
            }
            if ((!this.canonical.booleanValue() || tag == null) && (this.style == null && ev.getImplicit().canOmitTagInPlainScalar() || this.style != null && ev.getImplicit().canOmitTagInNonPlainScalar())) {
                this.preparedTag = null;
                return;
            }
            if (ev.getImplicit().canOmitTagInPlainScalar() && tag == null) {
                tag = "!";
                this.preparedTag = null;
            }
        } else {
            CollectionStartEvent ev = (CollectionStartEvent)this.event;
            tag = ev.getTag();
            if ((!this.canonical.booleanValue() || tag == null) && ev.getImplicit()) {
                this.preparedTag = null;
                return;
            }
        }
        if (tag == null) {
            throw new EmitterException("tag is not specified");
        }
        if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(tag);
        }
        this.writeIndicator(this.preparedTag, true, false, false);
        this.preparedTag = null;
    }

    private DumperOptions.ScalarStyle chooseScalarStyle() {
        ScalarEvent ev = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if (!ev.isPlain()) {
            if (ev.getScalarStyle() == DumperOptions.ScalarStyle.DOUBLE_QUOTED) return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        }
        if (this.canonical.booleanValue()) {
            return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        }
        if (ev.isPlain() && ev.getImplicit().canOmitTagInPlainScalar() && (!this.simpleKeyContext || !this.analysis.isEmpty() && !this.analysis.isMultiline())) {
            if (this.flowLevel != 0) {
                if (this.analysis.isAllowFlowPlain()) return null;
            }
            if (this.flowLevel == 0 && this.analysis.isAllowBlockPlain()) {
                return null;
            }
        }
        if (!(ev.isPlain() || ev.getScalarStyle() != DumperOptions.ScalarStyle.LITERAL && ev.getScalarStyle() != DumperOptions.ScalarStyle.FOLDED || this.flowLevel != 0 || this.simpleKeyContext || !this.analysis.isAllowBlock())) {
            return ev.getScalarStyle();
        }
        if (!ev.isPlain()) {
            if (ev.getScalarStyle() != DumperOptions.ScalarStyle.SINGLE_QUOTED) return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        }
        if (!this.analysis.isAllowSingleQuoted()) return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        if (!this.simpleKeyContext) return DumperOptions.ScalarStyle.SINGLE_QUOTED;
        if (this.analysis.isMultiline()) return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        return DumperOptions.ScalarStyle.SINGLE_QUOTED;
    }

    private void processScalar() throws IOException {
        boolean split;
        ScalarEvent ev = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if (this.style == null) {
            this.style = this.chooseScalarStyle();
        }
        boolean bl = split = !this.simpleKeyContext && this.splitLines;
        if (this.style == null) {
            this.writePlain(this.analysis.getScalar(), split);
        } else {
            switch (1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[this.style.ordinal()]) {
                case 1: {
                    this.writeDoubleQuoted(this.analysis.getScalar(), split);
                    break;
                }
                case 2: {
                    this.writeSingleQuoted(this.analysis.getScalar(), split);
                    break;
                }
                case 3: {
                    this.writeFolded(this.analysis.getScalar(), split);
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
        if (version.major() == 1) return version.getRepresentation();
        throw new EmitterException("unsupported YAML version: " + (Object)((Object)version));
    }

    private String prepareTagHandle(String handle) {
        if (handle.length() == 0) {
            throw new EmitterException("tag handle must not be empty");
        }
        if (handle.charAt(0) != '!') throw new EmitterException("tag handle must start and end with '!': " + handle);
        if (handle.charAt(handle.length() - 1) != '!') {
            throw new EmitterException("tag handle must start and end with '!': " + handle);
        }
        if ("!".equals(handle)) return handle;
        if (HANDLE_FORMAT.matcher(handle).matches()) return handle;
        throw new EmitterException("invalid character in the tag handle: " + handle);
    }

    private String prepareTagPrefix(String prefix) {
        if (prefix.length() == 0) {
            throw new EmitterException("tag prefix must not be empty");
        }
        StringBuilder chunks = new StringBuilder();
        int start = 0;
        int end = 0;
        if (prefix.charAt(0) == '!') {
            end = 1;
        }
        while (true) {
            if (end >= prefix.length()) {
                if (start >= end) return chunks.toString();
                chunks.append(prefix, start, end);
                return chunks.toString();
            }
            ++end;
        }
    }

    private String prepareTag(String tag) {
        int end;
        if (tag.length() == 0) {
            throw new EmitterException("tag must not be empty");
        }
        if ("!".equals(tag)) {
            return tag;
        }
        String handle = null;
        String suffix = tag;
        for (String prefix : this.tagPrefixes.keySet()) {
            if (!tag.startsWith(prefix) || !"!".equals(prefix) && prefix.length() >= tag.length()) continue;
            handle = prefix;
        }
        if (handle != null) {
            suffix = tag.substring(handle.length());
            handle = this.tagPrefixes.get(handle);
        }
        String suffixText = (end = suffix.length()) > 0 ? suffix.substring(0, end) : "";
        if (handle == null) return "!<" + suffixText + ">";
        return handle + suffixText;
    }

    static String prepareAnchor(String anchor) {
        Character invalid;
        if (anchor.length() == 0) {
            throw new EmitterException("anchor must not be empty");
        }
        Iterator<Character> i$ = INVALID_ANCHOR.iterator();
        do {
            if (i$.hasNext()) continue;
            Matcher matcher = SPACES_PATTERN.matcher(anchor);
            if (!matcher.find()) return anchor;
            throw new EmitterException("Anchor may not contain spaces: " + anchor);
        } while (anchor.indexOf((invalid = i$.next()).charValue()) <= -1);
        throw new EmitterException("Invalid character '" + invalid + "' in the anchor: " + anchor);
    }

    private ScalarAnalysis analyzeScalar(String scalar) {
        if (scalar.length() == 0) {
            return new ScalarAnalysis(scalar, true, false, false, true, true, false);
        }
        boolean blockIndicators = false;
        boolean flowIndicators = false;
        boolean lineBreaks = false;
        boolean specialCharacters = false;
        boolean leadingSpace = false;
        boolean leadingBreak = false;
        boolean trailingSpace = false;
        boolean trailingBreak = false;
        boolean breakSpace = false;
        boolean spaceBreak = false;
        if (scalar.startsWith("---") || scalar.startsWith("...")) {
            blockIndicators = true;
            flowIndicators = true;
        }
        boolean preceededByWhitespace = true;
        boolean followedByWhitespace = scalar.length() == 1 || Constant.NULL_BL_T_LINEBR.has(scalar.codePointAt(1));
        boolean previousSpace = false;
        boolean previousBreak = false;
        int index = 0;
        while (index < scalar.length()) {
            int nextIndex;
            boolean isLineBreak;
            int c = scalar.codePointAt(index);
            if (index == 0) {
                if ("#,[]{}&*!|>'\"%@`".indexOf(c) != -1) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
                if (c == 63 || c == 58) {
                    flowIndicators = true;
                    if (followedByWhitespace) {
                        blockIndicators = true;
                    }
                }
                if (c == 45 && followedByWhitespace) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            } else {
                if (",?[]{}".indexOf(c) != -1) {
                    flowIndicators = true;
                }
                if (c == 58) {
                    flowIndicators = true;
                    if (followedByWhitespace) {
                        blockIndicators = true;
                    }
                }
                if (c == 35 && preceededByWhitespace) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            }
            if (isLineBreak = Constant.LINEBR.has(c)) {
                lineBreaks = true;
            }
            if (c != 10 && (32 > c || c > 126)) {
                if (c == 133 || c >= 160 && c <= 55295 || c >= 57344 && c <= 65533 || c >= 65536 && c <= 0x10FFFF) {
                    if (!this.allowUnicode) {
                        specialCharacters = true;
                    }
                } else {
                    specialCharacters = true;
                }
            }
            if (c == 32) {
                if (index == 0) {
                    leadingSpace = true;
                }
                if (index == scalar.length() - 1) {
                    trailingSpace = true;
                }
                if (previousBreak) {
                    breakSpace = true;
                }
                previousSpace = true;
                previousBreak = false;
            } else if (isLineBreak) {
                if (index == 0) {
                    leadingBreak = true;
                }
                if (index == scalar.length() - 1) {
                    trailingBreak = true;
                }
                if (previousSpace) {
                    spaceBreak = true;
                }
                previousSpace = false;
                previousBreak = true;
            } else {
                previousSpace = false;
                previousBreak = false;
            }
            preceededByWhitespace = Constant.NULL_BL_T.has(c) || isLineBreak;
            followedByWhitespace = true;
            if ((index += Character.charCount(c)) + 1 >= scalar.length() || (nextIndex = index + Character.charCount(scalar.codePointAt(index))) >= scalar.length()) continue;
            followedByWhitespace = Constant.NULL_BL_T.has(scalar.codePointAt(nextIndex)) || isLineBreak;
        }
        boolean allowFlowPlain = true;
        boolean allowBlockPlain = true;
        boolean allowSingleQuoted = true;
        boolean allowBlock = true;
        if (leadingSpace || leadingBreak || trailingSpace || trailingBreak) {
            allowBlockPlain = false;
            allowFlowPlain = false;
        }
        if (trailingSpace) {
            allowBlock = false;
        }
        if (breakSpace) {
            allowSingleQuoted = false;
            allowBlockPlain = false;
            allowFlowPlain = false;
        }
        if (spaceBreak || specialCharacters) {
            allowBlock = false;
            allowSingleQuoted = false;
            allowBlockPlain = false;
            allowFlowPlain = false;
        }
        if (lineBreaks) {
            allowFlowPlain = false;
        }
        if (flowIndicators) {
            allowFlowPlain = false;
        }
        if (!blockIndicators) return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
        allowBlockPlain = false;
        return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
    }

    void flushStream() throws IOException {
        this.stream.flush();
    }

    void writeStreamStart() {
    }

    void writeStreamEnd() throws IOException {
        this.flushStream();
    }

    void writeIndicator(String indicator, boolean needWhitespace, boolean whitespace, boolean indentation) throws IOException {
        if (!this.whitespace && needWhitespace) {
            ++this.column;
            this.stream.write(SPACE);
        }
        this.whitespace = whitespace;
        this.indention = this.indention && indentation;
        this.column += indicator.length();
        this.openEnded = false;
        this.stream.write(indicator);
    }

    void writeIndent() throws IOException {
        int indent = this.indent != null ? this.indent : 0;
        if (!this.indention || this.column > indent || this.column == indent && !this.whitespace) {
            this.writeLineBreak(null);
        }
        this.writeWhitespace(indent - this.column);
    }

    private void writeWhitespace(int length) throws IOException {
        if (length <= 0) {
            return;
        }
        this.whitespace = true;
        char[] data = new char[length];
        int i = 0;
        while (true) {
            if (i >= data.length) {
                this.column += length;
                this.stream.write(data);
                return;
            }
            data[i] = 32;
            ++i;
        }
    }

    private void writeLineBreak(String data) throws IOException {
        this.whitespace = true;
        this.indention = true;
        this.column = 0;
        if (data == null) {
            this.stream.write(this.bestLineBreak);
            return;
        }
        this.stream.write(data);
    }

    void writeVersionDirective(String versionText) throws IOException {
        this.stream.write("%YAML ");
        this.stream.write(versionText);
        this.writeLineBreak(null);
    }

    void writeTagDirective(String handleText, String prefixText) throws IOException {
        this.stream.write("%TAG ");
        this.stream.write(handleText);
        this.stream.write(SPACE);
        this.stream.write(prefixText);
        this.writeLineBreak(null);
    }

    private void writeSingleQuoted(String text, boolean split) throws IOException {
        this.writeIndicator("'", true, false, false);
        boolean spaces = false;
        boolean breaks = false;
        int start = 0;
        int end = 0;
        while (true) {
            char ch;
            block13: {
                int len$;
                char[] arr$;
                block15: {
                    int len;
                    block14: {
                        block12: {
                            if (end > text.length()) {
                                this.writeIndicator("'", false, false, false);
                                return;
                            }
                            ch = '\u0000';
                            if (end < text.length()) {
                                ch = text.charAt(end);
                            }
                            if (!spaces) break block12;
                            if (ch == '\u0000' || ch != ' ') {
                                if (start + 1 == end && this.column > this.bestWidth && split && start != 0 && end != text.length()) {
                                    this.writeIndent();
                                } else {
                                    len = end - start;
                                    this.column += len;
                                    this.stream.write(text, start, len);
                                }
                                start = end;
                            }
                            break block13;
                        }
                        if (!breaks) break block14;
                        if (ch != '\u0000' && !Constant.LINEBR.hasNo(ch)) break block13;
                        if (text.charAt(start) == '\n') {
                            this.writeLineBreak(null);
                        }
                        String data = text.substring(start, end);
                        arr$ = data.toCharArray();
                        len$ = arr$.length;
                        break block15;
                    }
                    if (Constant.LINEBR.has(ch, "\u0000 '") && start < end) {
                        len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                        start = end;
                    }
                    break block13;
                }
                for (int i$ = 0; i$ < len$; ++i$) {
                    char br = arr$[i$];
                    if (br == '\n') {
                        this.writeLineBreak(null);
                        continue;
                    }
                    this.writeLineBreak(String.valueOf(br));
                }
                this.writeIndent();
                start = end;
            }
            if (ch == '\'') {
                this.column += 2;
                this.stream.write("''");
                start = end + 1;
            }
            if (ch != '\u0000') {
                spaces = ch == ' ';
                breaks = Constant.LINEBR.has(ch);
            }
            ++end;
        }
    }

    private void writeDoubleQuoted(String text, boolean split) throws IOException {
        this.writeIndicator("\"", true, false, false);
        int start = 0;
        int end = 0;
        while (true) {
            if (end > text.length()) {
                this.writeIndicator("\"", false, false, false);
                return;
            }
            Character ch = null;
            if (end < text.length()) {
                ch = Character.valueOf(text.charAt(end));
            }
            if (ch == null || "\"\\\u0085\u2028\u2029\ufeff".indexOf(ch.charValue()) != -1 || ' ' > ch.charValue() || ch.charValue() > '~') {
                if (start < end) {
                    int len = end - start;
                    this.column += len;
                    this.stream.write(text, start, len);
                    start = end;
                }
                if (ch != null) {
                    String data;
                    if (ESCAPE_REPLACEMENTS.containsKey(ch)) {
                        data = "\\" + ESCAPE_REPLACEMENTS.get(ch);
                    } else if (!this.allowUnicode || !StreamReader.isPrintable(ch.charValue())) {
                        String s;
                        if (ch.charValue() <= '\u00ff') {
                            s = "0" + Integer.toString(ch.charValue(), 16);
                            data = "\\x" + s.substring(s.length() - 2);
                        } else if (ch.charValue() >= '\ud800' && ch.charValue() <= '\udbff') {
                            if (end + 1 < text.length()) {
                                Character ch2 = Character.valueOf(text.charAt(++end));
                                String s2 = "000" + Long.toHexString(Character.toCodePoint(ch.charValue(), ch2.charValue()));
                                data = "\\U" + s2.substring(s2.length() - 8);
                            } else {
                                s = "000" + Integer.toString(ch.charValue(), 16);
                                data = "\\u" + s.substring(s.length() - 4);
                            }
                        } else {
                            s = "000" + Integer.toString(ch.charValue(), 16);
                            data = "\\u" + s.substring(s.length() - 4);
                        }
                    } else {
                        data = String.valueOf(ch);
                    }
                    this.column += data.length();
                    this.stream.write(data);
                    start = end + 1;
                }
            }
            if (0 < end && end < text.length() - 1 && (ch.charValue() == ' ' || start >= end) && this.column + (end - start) > this.bestWidth && split) {
                String data = start >= end ? "\\" : text.substring(start, end) + "\\";
                if (start < end) {
                    start = end;
                }
                this.column += data.length();
                this.stream.write(data);
                this.writeIndent();
                this.whitespace = false;
                this.indention = false;
                if (text.charAt(start) == ' ') {
                    data = "\\";
                    this.column += data.length();
                    this.stream.write(data);
                }
            }
            ++end;
        }
    }

    private String determineBlockHints(String text) {
        char ch1;
        StringBuilder hints = new StringBuilder();
        if (Constant.LINEBR.has(text.charAt(0), " ")) {
            hints.append(this.bestIndent);
        }
        if (Constant.LINEBR.hasNo(ch1 = text.charAt(text.length() - 1))) {
            hints.append("-");
            return hints.toString();
        }
        if (text.length() != 1) {
            if (!Constant.LINEBR.has(text.charAt(text.length() - 2))) return hints.toString();
        }
        hints.append("+");
        return hints.toString();
    }

    void writeFolded(String text, boolean split) throws IOException {
        String hints = this.determineBlockHints(text);
        this.writeIndicator(">" + hints, true, false, false);
        if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean leadingSpace = true;
        boolean spaces = false;
        boolean breaks = true;
        int start = 0;
        int end = 0;
        while (end <= text.length()) {
            char ch;
            block16: {
                int len$;
                char[] arr$;
                block17: {
                    block15: {
                        ch = '\u0000';
                        if (end < text.length()) {
                            ch = text.charAt(end);
                        }
                        if (!breaks) break block15;
                        if (ch != '\u0000' && !Constant.LINEBR.hasNo(ch)) break block16;
                        if (!leadingSpace && ch != '\u0000' && ch != ' ' && text.charAt(start) == '\n') {
                            this.writeLineBreak(null);
                        }
                        leadingSpace = ch == ' ';
                        String data = text.substring(start, end);
                        arr$ = data.toCharArray();
                        len$ = arr$.length;
                        break block17;
                    }
                    if (spaces) {
                        if (ch != ' ') {
                            if (start + 1 == end && this.column > this.bestWidth && split) {
                                this.writeIndent();
                            } else {
                                int len = end - start;
                                this.column += len;
                                this.stream.write(text, start, len);
                            }
                            start = end;
                        }
                    } else if (Constant.LINEBR.has(ch, "\u0000 ")) {
                        int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                        if (ch == '\u0000') {
                            this.writeLineBreak(null);
                        }
                        start = end;
                    }
                    break block16;
                }
                for (int i$ = 0; i$ < len$; ++i$) {
                    char br = arr$[i$];
                    if (br == '\n') {
                        this.writeLineBreak(null);
                        continue;
                    }
                    this.writeLineBreak(String.valueOf(br));
                }
                if (ch != '\u0000') {
                    this.writeIndent();
                }
                start = end;
            }
            if (ch != '\u0000') {
                breaks = Constant.LINEBR.has(ch);
                spaces = ch == ' ';
            }
            ++end;
        }
    }

    void writeLiteral(String text) throws IOException {
        String hints = this.determineBlockHints(text);
        this.writeIndicator("|" + hints, true, false, false);
        if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean breaks = true;
        int start = 0;
        int end = 0;
        while (end <= text.length()) {
            char ch;
            block10: {
                int len$;
                char[] arr$;
                block11: {
                    block9: {
                        ch = '\u0000';
                        if (end < text.length()) {
                            ch = text.charAt(end);
                        }
                        if (!breaks) break block9;
                        if (ch != '\u0000' && !Constant.LINEBR.hasNo(ch)) break block10;
                        String data = text.substring(start, end);
                        arr$ = data.toCharArray();
                        len$ = arr$.length;
                        break block11;
                    }
                    if (ch == '\u0000' || Constant.LINEBR.has(ch)) {
                        this.stream.write(text, start, end - start);
                        if (ch == '\u0000') {
                            this.writeLineBreak(null);
                        }
                        start = end;
                    }
                    break block10;
                }
                for (int i$ = 0; i$ < len$; ++i$) {
                    char br = arr$[i$];
                    if (br == '\n') {
                        this.writeLineBreak(null);
                        continue;
                    }
                    this.writeLineBreak(String.valueOf(br));
                }
                if (ch != '\u0000') {
                    this.writeIndent();
                }
                start = end;
            }
            if (ch != '\u0000') {
                breaks = Constant.LINEBR.has(ch);
            }
            ++end;
        }
    }

    void writePlain(String text, boolean split) throws IOException {
        if (this.rootContext) {
            this.openEnded = true;
        }
        if (text.length() == 0) {
            return;
        }
        if (!this.whitespace) {
            ++this.column;
            this.stream.write(SPACE);
        }
        this.whitespace = false;
        this.indention = false;
        boolean spaces = false;
        boolean breaks = false;
        int start = 0;
        int end = 0;
        while (end <= text.length()) {
            char ch;
            block14: {
                int len$;
                char[] arr$;
                block16: {
                    int len;
                    block15: {
                        block13: {
                            ch = '\u0000';
                            if (end < text.length()) {
                                ch = text.charAt(end);
                            }
                            if (!spaces) break block13;
                            if (ch != ' ') {
                                if (start + 1 == end && this.column > this.bestWidth && split) {
                                    this.writeIndent();
                                    this.whitespace = false;
                                    this.indention = false;
                                } else {
                                    len = end - start;
                                    this.column += len;
                                    this.stream.write(text, start, len);
                                }
                                start = end;
                            }
                            break block14;
                        }
                        if (!breaks) break block15;
                        if (!Constant.LINEBR.hasNo(ch)) break block14;
                        if (text.charAt(start) == '\n') {
                            this.writeLineBreak(null);
                        }
                        String data = text.substring(start, end);
                        arr$ = data.toCharArray();
                        len$ = arr$.length;
                        break block16;
                    }
                    if (Constant.LINEBR.has(ch, "\u0000 ")) {
                        len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                        start = end;
                    }
                    break block14;
                }
                for (int i$ = 0; i$ < len$; ++i$) {
                    char br = arr$[i$];
                    if (br == '\n') {
                        this.writeLineBreak(null);
                        continue;
                    }
                    this.writeLineBreak(String.valueOf(br));
                }
                this.writeIndent();
                this.whitespace = false;
                this.indention = false;
                start = end;
            }
            if (ch != '\u0000') {
                spaces = ch == ' ';
                breaks = Constant.LINEBR.has(ch);
            }
            ++end;
        }
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
        private ExpectBlockMappingValue() {
        }

        @Override
        public void expect() throws IOException {
            Emitter.this.writeIndent();
            Emitter.this.writeIndicator(":", true, false, true);
            Emitter.this.states.push(new ExpectBlockMappingKey(false));
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectBlockMappingSimpleValue
    implements EmitterState {
        private ExpectBlockMappingSimpleValue() {
        }

        @Override
        public void expect() throws IOException {
            Emitter.this.writeIndicator(":", false, false, false);
            Emitter.this.states.push(new ExpectBlockMappingKey(false));
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectBlockMappingKey
    implements EmitterState {
        private final boolean first;

        public ExpectBlockMappingKey(boolean first) {
            this.first = first;
        }

        @Override
        public void expect() throws IOException {
            if (!this.first && Emitter.this.event instanceof MappingEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            Emitter.this.writeIndent();
            if (Emitter.this.checkSimpleKey()) {
                Emitter.this.states.push(new ExpectBlockMappingSimpleValue());
                Emitter.this.expectNode(false, true, true);
                return;
            }
            Emitter.this.writeIndicator("?", true, false, true);
            Emitter.this.states.push(new ExpectBlockMappingValue());
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectFirstBlockMappingKey
    implements EmitterState {
        private ExpectFirstBlockMappingKey() {
        }

        @Override
        public void expect() throws IOException {
            new ExpectBlockMappingKey(true).expect();
        }
    }

    private class ExpectBlockSequenceItem
    implements EmitterState {
        private final boolean first;

        public ExpectBlockSequenceItem(boolean first) {
            this.first = first;
        }

        @Override
        public void expect() throws IOException {
            if (!this.first && Emitter.this.event instanceof SequenceEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            Emitter.this.writeIndent();
            if (!Emitter.this.indentWithIndicator || this.first) {
                Emitter.this.writeWhitespace(Emitter.this.indicatorIndent);
            }
            Emitter.this.writeIndicator("-", true, false, true);
            if (Emitter.this.indentWithIndicator && this.first) {
                Emitter.this.indent = Emitter.this.indent + Emitter.this.indicatorIndent;
            }
            Emitter.this.states.push(new ExpectBlockSequenceItem(false));
            Emitter.this.expectNode(false, false, false);
        }
    }

    private class ExpectFirstBlockSequenceItem
    implements EmitterState {
        private ExpectFirstBlockSequenceItem() {
        }

        @Override
        public void expect() throws IOException {
            new ExpectBlockSequenceItem(true).expect();
        }
    }

    private class ExpectFlowMappingValue
    implements EmitterState {
        private ExpectFlowMappingValue() {
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.writeIndicator(":", true, false, false);
            Emitter.this.states.push(new ExpectFlowMappingKey());
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectFlowMappingSimpleValue
    implements EmitterState {
        private ExpectFlowMappingSimpleValue() {
        }

        @Override
        public void expect() throws IOException {
            Emitter.this.writeIndicator(":", false, false, false);
            Emitter.this.states.push(new ExpectFlowMappingKey());
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectFlowMappingKey
    implements EmitterState {
        private ExpectFlowMappingKey() {
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.this.event instanceof MappingEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.flowLevel--;
                if (Emitter.this.canonical.booleanValue()) {
                    Emitter.this.writeIndicator(",", false, false, false);
                    Emitter.this.writeIndent();
                }
                if (Emitter.this.prettyFlow.booleanValue()) {
                    Emitter.this.writeIndent();
                }
                Emitter.this.writeIndicator("}", false, false, false);
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            Emitter.this.writeIndicator(",", false, false, false);
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) {
                Emitter.this.states.push(new ExpectFlowMappingSimpleValue());
                Emitter.this.expectNode(false, true, true);
                return;
            }
            Emitter.this.writeIndicator("?", true, false, false);
            Emitter.this.states.push(new ExpectFlowMappingValue());
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectFirstFlowMappingKey
    implements EmitterState {
        private ExpectFirstFlowMappingKey() {
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.this.event instanceof MappingEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.flowLevel--;
                Emitter.this.writeIndicator("}", false, false, false);
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) {
                Emitter.this.states.push(new ExpectFlowMappingSimpleValue());
                Emitter.this.expectNode(false, true, true);
                return;
            }
            Emitter.this.writeIndicator("?", true, false, false);
            Emitter.this.states.push(new ExpectFlowMappingValue());
            Emitter.this.expectNode(false, true, false);
        }
    }

    private class ExpectFlowSequenceItem
    implements EmitterState {
        private ExpectFlowSequenceItem() {
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.this.event instanceof SequenceEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.flowLevel--;
                if (Emitter.this.canonical.booleanValue()) {
                    Emitter.this.writeIndicator(",", false, false, false);
                    Emitter.this.writeIndent();
                }
                Emitter.this.writeIndicator("]", false, false, false);
                if (Emitter.this.prettyFlow.booleanValue()) {
                    Emitter.this.writeIndent();
                }
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            Emitter.this.writeIndicator(",", false, false, false);
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.states.push(new ExpectFlowSequenceItem());
            Emitter.this.expectNode(false, false, false);
        }
    }

    private class ExpectFirstFlowSequenceItem
    implements EmitterState {
        private ExpectFirstFlowSequenceItem() {
        }

        @Override
        public void expect() throws IOException {
            if (Emitter.this.event instanceof SequenceEndEvent) {
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
                Emitter.this.flowLevel--;
                Emitter.this.writeIndicator("]", false, false, false);
                Emitter.this.state = (EmitterState)Emitter.this.states.pop();
                return;
            }
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.states.push(new ExpectFlowSequenceItem());
            Emitter.this.expectNode(false, false, false);
        }
    }

    private class ExpectDocumentRoot
    implements EmitterState {
        private ExpectDocumentRoot() {
        }

        @Override
        public void expect() throws IOException {
            Emitter.this.states.push(new ExpectDocumentEnd());
            Emitter.this.expectNode(true, false, false);
        }
    }

    private class ExpectDocumentEnd
    implements EmitterState {
        private ExpectDocumentEnd() {
        }

        @Override
        public void expect() throws IOException {
            if (!(Emitter.this.event instanceof DocumentEndEvent)) throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.this.event);
            Emitter.this.writeIndent();
            if (((DocumentEndEvent)Emitter.this.event).getExplicit()) {
                Emitter.this.writeIndicator("...", true, false, false);
                Emitter.this.writeIndent();
            }
            Emitter.this.flushStream();
            Emitter.this.state = new ExpectDocumentStart(false);
        }
    }

    private class ExpectDocumentStart
    implements EmitterState {
        private final boolean first;

        public ExpectDocumentStart(boolean first) {
            this.first = first;
        }

        @Override
        public void expect() throws IOException {
            boolean implicit;
            if (!(Emitter.this.event instanceof DocumentStartEvent)) {
                if (!(Emitter.this.event instanceof StreamEndEvent)) throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.this.event);
                Emitter.this.writeStreamEnd();
                Emitter.this.state = new ExpectNothing();
                return;
            }
            DocumentStartEvent ev = (DocumentStartEvent)Emitter.this.event;
            if ((ev.getVersion() != null || ev.getTags() != null) && Emitter.this.openEnded) {
                Emitter.this.writeIndicator("...", true, false, false);
                Emitter.this.writeIndent();
            }
            if (ev.getVersion() != null) {
                String versionText = Emitter.this.prepareVersion(ev.getVersion());
                Emitter.this.writeVersionDirective(versionText);
            }
            Emitter.this.tagPrefixes = new LinkedHashMap(DEFAULT_TAG_PREFIXES);
            if (ev.getTags() != null) {
                TreeSet<String> handles = new TreeSet<String>(ev.getTags().keySet());
                for (String handle : handles) {
                    String prefix = ev.getTags().get(handle);
                    Emitter.this.tagPrefixes.put(prefix, handle);
                    String handleText = Emitter.this.prepareTagHandle(handle);
                    String prefixText = Emitter.this.prepareTagPrefix(prefix);
                    Emitter.this.writeTagDirective(handleText, prefixText);
                }
            }
            boolean bl = implicit = this.first && !ev.getExplicit() && Emitter.this.canonical == false && ev.getVersion() == null && (ev.getTags() == null || ev.getTags().isEmpty()) && !Emitter.this.checkEmptyDocument();
            if (!implicit) {
                Emitter.this.writeIndent();
                Emitter.this.writeIndicator("---", true, false, false);
                if (Emitter.this.canonical.booleanValue()) {
                    Emitter.this.writeIndent();
                }
            }
            Emitter.this.state = new ExpectDocumentRoot();
        }
    }

    private class ExpectFirstDocumentStart
    implements EmitterState {
        private ExpectFirstDocumentStart() {
        }

        @Override
        public void expect() throws IOException {
            new ExpectDocumentStart(true).expect();
        }
    }

    private class ExpectNothing
    implements EmitterState {
        private ExpectNothing() {
        }

        @Override
        public void expect() throws IOException {
            throw new EmitterException("expecting nothing, but got " + Emitter.this.event);
        }
    }

    private class ExpectStreamStart
    implements EmitterState {
        private ExpectStreamStart() {
        }

        @Override
        public void expect() throws IOException {
            if (!(Emitter.this.event instanceof StreamStartEvent)) throw new EmitterException("expected StreamStartEvent, but got " + Emitter.this.event);
            Emitter.this.writeStreamStart();
            Emitter.this.state = new ExpectFirstDocumentStart();
        }
    }
}

