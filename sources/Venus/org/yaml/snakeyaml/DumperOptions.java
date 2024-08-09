/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml;

import java.util.Map;
import java.util.TimeZone;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import org.yaml.snakeyaml.serializer.NumberAnchorGenerator;

public class DumperOptions {
    private ScalarStyle defaultStyle = ScalarStyle.PLAIN;
    private FlowStyle defaultFlowStyle = FlowStyle.AUTO;
    private boolean canonical = false;
    private boolean allowUnicode = true;
    private boolean allowReadOnlyProperties = false;
    private int indent = 2;
    private int indicatorIndent = 0;
    private boolean indentWithIndicator = false;
    private int bestWidth = 80;
    private boolean splitLines = true;
    private LineBreak lineBreak = LineBreak.UNIX;
    private boolean explicitStart = false;
    private boolean explicitEnd = false;
    private TimeZone timeZone = null;
    private int maxSimpleKeyLength = 128;
    private boolean processComments = false;
    private NonPrintableStyle nonPrintableStyle = NonPrintableStyle.BINARY;
    private Version version = null;
    private Map<String, String> tags = null;
    private Boolean prettyFlow = false;
    private AnchorGenerator anchorGenerator = new NumberAnchorGenerator(0);

    public boolean isAllowUnicode() {
        return this.allowUnicode;
    }

    public void setAllowUnicode(boolean bl) {
        this.allowUnicode = bl;
    }

    public ScalarStyle getDefaultScalarStyle() {
        return this.defaultStyle;
    }

    public void setDefaultScalarStyle(ScalarStyle scalarStyle) {
        if (scalarStyle == null) {
            throw new NullPointerException("Use ScalarStyle enum.");
        }
        this.defaultStyle = scalarStyle;
    }

    public void setIndent(int n) {
        if (n < 1) {
            throw new YAMLException("Indent must be at least 1");
        }
        if (n > 10) {
            throw new YAMLException("Indent must be at most 10");
        }
        this.indent = n;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setIndicatorIndent(int n) {
        if (n < 0) {
            throw new YAMLException("Indicator indent must be non-negative.");
        }
        if (n > 9) {
            throw new YAMLException("Indicator indent must be at most Emitter.MAX_INDENT-1: 9");
        }
        this.indicatorIndent = n;
    }

    public int getIndicatorIndent() {
        return this.indicatorIndent;
    }

    public boolean getIndentWithIndicator() {
        return this.indentWithIndicator;
    }

    public void setIndentWithIndicator(boolean bl) {
        this.indentWithIndicator = bl;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setCanonical(boolean bl) {
        this.canonical = bl;
    }

    public boolean isCanonical() {
        return this.canonical;
    }

    public void setPrettyFlow(boolean bl) {
        this.prettyFlow = bl;
    }

    public boolean isPrettyFlow() {
        return this.prettyFlow;
    }

    public void setWidth(int n) {
        this.bestWidth = n;
    }

    public int getWidth() {
        return this.bestWidth;
    }

    public void setSplitLines(boolean bl) {
        this.splitLines = bl;
    }

    public boolean getSplitLines() {
        return this.splitLines;
    }

    public LineBreak getLineBreak() {
        return this.lineBreak;
    }

    public void setDefaultFlowStyle(FlowStyle flowStyle) {
        if (flowStyle == null) {
            throw new NullPointerException("Use FlowStyle enum.");
        }
        this.defaultFlowStyle = flowStyle;
    }

    public FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }

    public void setLineBreak(LineBreak lineBreak) {
        if (lineBreak == null) {
            throw new NullPointerException("Specify line break.");
        }
        this.lineBreak = lineBreak;
    }

    public boolean isExplicitStart() {
        return this.explicitStart;
    }

    public void setExplicitStart(boolean bl) {
        this.explicitStart = bl;
    }

    public boolean isExplicitEnd() {
        return this.explicitEnd;
    }

    public void setExplicitEnd(boolean bl) {
        this.explicitEnd = bl;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> map) {
        this.tags = map;
    }

    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }

    public void setAllowReadOnlyProperties(boolean bl) {
        this.allowReadOnlyProperties = bl;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public AnchorGenerator getAnchorGenerator() {
        return this.anchorGenerator;
    }

    public void setAnchorGenerator(AnchorGenerator anchorGenerator) {
        this.anchorGenerator = anchorGenerator;
    }

    public int getMaxSimpleKeyLength() {
        return this.maxSimpleKeyLength;
    }

    public void setMaxSimpleKeyLength(int n) {
        if (n > 1024) {
            throw new YAMLException("The simple key must not span more than 1024 stream characters. See https://yaml.org/spec/1.1/#id934537");
        }
        this.maxSimpleKeyLength = n;
    }

    public void setProcessComments(boolean bl) {
        this.processComments = bl;
    }

    public boolean isProcessComments() {
        return this.processComments;
    }

    public NonPrintableStyle getNonPrintableStyle() {
        return this.nonPrintableStyle;
    }

    public void setNonPrintableStyle(NonPrintableStyle nonPrintableStyle) {
        this.nonPrintableStyle = nonPrintableStyle;
    }

    public static enum NonPrintableStyle {
        BINARY,
        ESCAPE;

    }

    public static enum Version {
        V1_0(new Integer[]{1, 0}),
        V1_1(new Integer[]{1, 1});

        private final Integer[] version;

        private Version(Integer[] integerArray) {
            this.version = integerArray;
        }

        public int major() {
            return this.version[0];
        }

        public int minor() {
            return this.version[1];
        }

        public String getRepresentation() {
            return this.version[0] + "." + this.version[1];
        }

        public String toString() {
            return "Version: " + this.getRepresentation();
        }
    }

    public static enum LineBreak {
        WIN("\r\n"),
        MAC("\r"),
        UNIX("\n");

        private final String lineBreak;

        private LineBreak(String string2) {
            this.lineBreak = string2;
        }

        public String getString() {
            return this.lineBreak;
        }

        public String toString() {
            return "Line break: " + this.name();
        }

        public static LineBreak getPlatformLineBreak() {
            String string = System.getProperty("line.separator");
            for (LineBreak lineBreak : LineBreak.values()) {
                if (!lineBreak.lineBreak.equals(string)) continue;
                return lineBreak;
            }
            return UNIX;
        }
    }

    public static enum FlowStyle {
        FLOW(Boolean.TRUE),
        BLOCK(Boolean.FALSE),
        AUTO(null);

        private final Boolean styleBoolean;

        private FlowStyle(Boolean bl) {
            this.styleBoolean = bl;
        }

        public String toString() {
            return "Flow style: '" + this.styleBoolean + "'";
        }
    }

    public static enum ScalarStyle {
        DOUBLE_QUOTED(Character.valueOf('\"')),
        SINGLE_QUOTED(Character.valueOf('\'')),
        LITERAL(Character.valueOf('|')),
        FOLDED(Character.valueOf('>')),
        PLAIN(null);

        private final Character styleChar;

        private ScalarStyle(Character c) {
            this.styleChar = c;
        }

        public Character getChar() {
            return this.styleChar;
        }

        public String toString() {
            return "Scalar style: '" + this.styleChar + "'";
        }

        public static ScalarStyle createStyle(Character c) {
            if (c == null) {
                return PLAIN;
            }
            switch (c.charValue()) {
                case '\"': {
                    return DOUBLE_QUOTED;
                }
                case '\'': {
                    return SINGLE_QUOTED;
                }
                case '|': {
                    return LITERAL;
                }
                case '>': {
                    return FOLDED;
                }
            }
            throw new YAMLException("Unknown scalar style character: " + c);
        }
    }
}

