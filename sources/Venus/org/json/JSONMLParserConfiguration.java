/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import org.json.ParserConfiguration;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class JSONMLParserConfiguration
extends ParserConfiguration {
    public static final int DEFAULT_MAXIMUM_NESTING_DEPTH = 512;
    public static final JSONMLParserConfiguration ORIGINAL = new JSONMLParserConfiguration();
    public static final JSONMLParserConfiguration KEEP_STRINGS = new JSONMLParserConfiguration().withKeepStrings(false);

    public JSONMLParserConfiguration() {
        this.maxNestingDepth = 512;
    }

    protected JSONMLParserConfiguration(boolean bl, int n) {
        super(bl, n);
    }

    @Override
    protected JSONMLParserConfiguration clone() {
        return new JSONMLParserConfiguration(this.keepStrings, this.maxNestingDepth);
    }

    public JSONMLParserConfiguration withKeepStrings(boolean bl) {
        return (JSONMLParserConfiguration)super.withKeepStrings(bl);
    }

    public JSONMLParserConfiguration withMaxNestingDepth(int n) {
        return (JSONMLParserConfiguration)super.withMaxNestingDepth(n);
    }

    public ParserConfiguration withMaxNestingDepth(int n) {
        return this.withMaxNestingDepth(n);
    }

    public ParserConfiguration withKeepStrings(boolean bl) {
        return this.withKeepStrings(bl);
    }

    @Override
    protected ParserConfiguration clone() {
        return this.clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

