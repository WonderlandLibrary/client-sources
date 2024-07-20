/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.DateRule;
import java.util.Date;

class Range {
    public Date start;
    public DateRule rule;

    public Range(Date start, DateRule rule) {
        this.start = start;
        this.rule = rule;
    }
}

