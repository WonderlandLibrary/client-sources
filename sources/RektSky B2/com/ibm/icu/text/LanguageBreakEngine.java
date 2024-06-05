package com.ibm.icu.text;

import java.text.*;

interface LanguageBreakEngine
{
    boolean handles(final int p0);
    
    int findBreaks(final CharacterIterator p0, final int p1, final int p2, final DictionaryBreakEngine.DequeI p3);
}
