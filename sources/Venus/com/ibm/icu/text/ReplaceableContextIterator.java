/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UTF16;

class ReplaceableContextIterator
implements UCaseProps.ContextIterator {
    protected Replaceable rep = null;
    protected int index = 0;
    protected int limit = 0;
    protected int cpStart = 0;
    protected int cpLimit = 0;
    protected int contextStart = 0;
    protected int contextLimit = 0;
    protected int dir = 0;
    protected boolean reachedLimit = false;

    ReplaceableContextIterator() {
    }

    public void setText(Replaceable replaceable) {
        this.rep = replaceable;
        this.limit = this.contextLimit = replaceable.length();
        this.contextStart = 0;
        this.index = 0;
        this.cpLimit = 0;
        this.cpStart = 0;
        this.dir = 0;
        this.reachedLimit = false;
    }

    public void setIndex(int n) {
        this.cpStart = this.cpLimit = n;
        this.index = 0;
        this.dir = 0;
        this.reachedLimit = false;
    }

    public int getCaseMapCPStart() {
        return this.cpStart;
    }

    public void setLimit(int n) {
        this.limit = 0 <= n && n <= this.rep.length() ? n : this.rep.length();
        this.reachedLimit = false;
    }

    public void setContextLimits(int n, int n2) {
        this.contextStart = n < 0 ? 0 : (n <= this.rep.length() ? n : this.rep.length());
        this.contextLimit = n2 < this.contextStart ? this.contextStart : (n2 <= this.rep.length() ? n2 : this.rep.length());
        this.reachedLimit = false;
    }

    public int nextCaseMapCP() {
        if (this.cpLimit < this.limit) {
            this.cpStart = this.cpLimit;
            int n = this.rep.char32At(this.cpLimit);
            this.cpLimit += UTF16.getCharCount(n);
            return n;
        }
        return 1;
    }

    public int replace(String string) {
        int n = string.length() - (this.cpLimit - this.cpStart);
        this.rep.replace(this.cpStart, this.cpLimit, string);
        this.cpLimit += n;
        this.limit += n;
        this.contextLimit += n;
        return n;
    }

    public boolean didReachLimit() {
        return this.reachedLimit;
    }

    @Override
    public void reset(int n) {
        if (n > 0) {
            this.dir = 1;
            this.index = this.cpLimit;
        } else if (n < 0) {
            this.dir = -1;
            this.index = this.cpStart;
        } else {
            this.dir = 0;
            this.index = 0;
        }
        this.reachedLimit = false;
    }

    @Override
    public int next() {
        if (this.dir > 0) {
            if (this.index < this.contextLimit) {
                int n = this.rep.char32At(this.index);
                this.index += UTF16.getCharCount(n);
                return n;
            }
            this.reachedLimit = true;
        } else if (this.dir < 0 && this.index > this.contextStart) {
            int n = this.rep.char32At(this.index - 1);
            this.index -= UTF16.getCharCount(n);
            return n;
        }
        return 1;
    }
}

