/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.UTF16;
import java.text.CharacterIterator;

public final class CharacterIteration {
    public static final int DONE32 = Integer.MAX_VALUE;

    private CharacterIteration() {
    }

    public static int next32(CharacterIterator characterIterator) {
        int n = characterIterator.current();
        if (n >= 55296 && n <= 56319 && ((n = characterIterator.next()) < 56320 || n > 57343)) {
            characterIterator.previous();
        }
        if ((n = characterIterator.next()) >= 55296) {
            n = CharacterIteration.nextTrail32(characterIterator, n);
        }
        if (n >= 65536 && n != Integer.MAX_VALUE) {
            characterIterator.previous();
        }
        return n;
    }

    public static int nextTrail32(CharacterIterator characterIterator, int n) {
        if (n == 65535 && characterIterator.getIndex() >= characterIterator.getEndIndex()) {
            return 0;
        }
        int n2 = n;
        if (n <= 56319) {
            char c = characterIterator.next();
            if (UTF16.isTrailSurrogate(c)) {
                n2 = (n - 55296 << 10) + (c - 56320) + 65536;
            } else {
                characterIterator.previous();
            }
        }
        return n2;
    }

    public static int previous32(CharacterIterator characterIterator) {
        int n;
        if (characterIterator.getIndex() <= characterIterator.getBeginIndex()) {
            return 0;
        }
        int n2 = n = characterIterator.previous();
        if (UTF16.isTrailSurrogate((char)n) && characterIterator.getIndex() > characterIterator.getBeginIndex()) {
            char c = characterIterator.previous();
            if (UTF16.isLeadSurrogate(c)) {
                n2 = (c - 55296 << 10) + (n - 56320) + 65536;
            } else {
                characterIterator.next();
            }
        }
        return n2;
    }

    public static int current32(CharacterIterator characterIterator) {
        int n = characterIterator.current();
        int n2 = n;
        if (n2 < 55296) {
            return n2;
        }
        if (UTF16.isLeadSurrogate((char)n)) {
            char c = characterIterator.next();
            characterIterator.previous();
            if (UTF16.isTrailSurrogate(c)) {
                n2 = (n - 55296 << 10) + (c - 56320) + 65536;
            }
        } else if (n == 65535 && characterIterator.getIndex() >= characterIterator.getEndIndex()) {
            n2 = Integer.MAX_VALUE;
        }
        return n2;
    }
}

