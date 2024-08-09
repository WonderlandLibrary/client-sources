/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ParserConfiguration {
    public static final int UNDEFINED_MAXIMUM_NESTING_DEPTH = -1;
    public static final int DEFAULT_MAXIMUM_NESTING_DEPTH = 512;
    protected boolean keepStrings;
    protected int maxNestingDepth;

    public ParserConfiguration() {
        this.keepStrings = false;
        this.maxNestingDepth = 512;
    }

    protected ParserConfiguration(boolean bl, int n) {
        this.keepStrings = bl;
        this.maxNestingDepth = n;
    }

    protected ParserConfiguration clone() {
        return new ParserConfiguration(this.keepStrings, this.maxNestingDepth);
    }

    public boolean isKeepStrings() {
        return this.keepStrings;
    }

    public <T extends ParserConfiguration> T withKeepStrings(boolean bl) {
        ParserConfiguration parserConfiguration = this.clone();
        parserConfiguration.keepStrings = bl;
        return (T)parserConfiguration;
    }

    public int getMaxNestingDepth() {
        return this.maxNestingDepth;
    }

    public <T extends ParserConfiguration> T withMaxNestingDepth(int n) {
        ParserConfiguration parserConfiguration = this.clone();
        parserConfiguration.maxNestingDepth = n > -1 ? n : -1;
        return (T)parserConfiguration;
    }

    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

