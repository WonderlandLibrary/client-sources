/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import com.ibm.icu.text.LanguageBreakEngine;
import com.ibm.icu.text.UnicodeSet;
import java.text.CharacterIterator;

final class UnhandledBreakEngine
implements LanguageBreakEngine {
    volatile UnicodeSet fHandled = new UnicodeSet();

    @Override
    public boolean handles(int n) {
        return this.fHandled.contains(n);
    }

    @Override
    public int findBreaks(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI dequeI) {
        UnicodeSet unicodeSet = this.fHandled;
        int n3 = CharacterIteration.current32(characterIterator);
        while (characterIterator.getIndex() < n2 && unicodeSet.contains(n3)) {
            CharacterIteration.next32(characterIterator);
            n3 = CharacterIteration.current32(characterIterator);
        }
        return 1;
    }

    public void handleChar(int n) {
        UnicodeSet unicodeSet = this.fHandled;
        if (!unicodeSet.contains(n)) {
            int n2 = UCharacter.getIntPropertyValue(n, 4106);
            UnicodeSet unicodeSet2 = new UnicodeSet();
            unicodeSet2.applyIntPropertyValue(4106, n2);
            unicodeSet2.addAll(unicodeSet);
            this.fHandled = unicodeSet2;
        }
    }
}

