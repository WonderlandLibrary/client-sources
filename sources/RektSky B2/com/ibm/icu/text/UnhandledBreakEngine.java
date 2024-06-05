package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

final class UnhandledBreakEngine implements LanguageBreakEngine
{
    volatile UnicodeSet fHandled;
    
    public UnhandledBreakEngine() {
        this.fHandled = new UnicodeSet();
    }
    
    @Override
    public boolean handles(final int c) {
        return this.fHandled.contains(c);
    }
    
    @Override
    public int findBreaks(final CharacterIterator text, final int startPos, final int endPos, final DictionaryBreakEngine.DequeI foundBreaks) {
        final UnicodeSet uniset = this.fHandled;
        for (int c = CharacterIteration.current32(text); text.getIndex() < endPos && uniset.contains(c); c = CharacterIteration.current32(text)) {
            CharacterIteration.next32(text);
        }
        return 0;
    }
    
    public void handleChar(final int c) {
        final UnicodeSet originalSet = this.fHandled;
        if (!originalSet.contains(c)) {
            final int script = UCharacter.getIntPropertyValue(c, 4106);
            final UnicodeSet newSet = new UnicodeSet();
            newSet.applyIntPropertyValue(4106, script);
            newSet.addAll(originalSet);
            this.fHandled = newSet;
        }
    }
}
