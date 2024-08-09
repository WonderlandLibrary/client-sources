/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.DictionaryBreakEngine;
import java.text.CharacterIterator;

interface LanguageBreakEngine {
    public boolean handles(int var1);

    public int findBreaks(CharacterIterator var1, int var2, int var3, DictionaryBreakEngine.DequeI var4);
}

