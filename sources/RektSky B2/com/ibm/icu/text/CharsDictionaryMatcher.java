package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.util.*;

class CharsDictionaryMatcher extends DictionaryMatcher
{
    private CharSequence characters;
    
    public CharsDictionaryMatcher(final CharSequence chars) {
        this.characters = chars;
    }
    
    @Override
    public int matches(final CharacterIterator text_, final int maxLength, final int[] lengths, final int[] count_, final int limit, final int[] values) {
        final UCharacterIterator text = UCharacterIterator.getInstance(text_);
        final CharsTrie uct = new CharsTrie(this.characters, 0);
        int c = text.nextCodePoint();
        if (c == -1) {
            return 0;
        }
        BytesTrie.Result result = uct.firstForCodePoint(c);
        int numChars = 1;
        int count = 0;
        while (true) {
            if (result.hasValue()) {
                if (count < limit) {
                    if (values != null) {
                        values[count] = uct.getValue();
                    }
                    lengths[count] = numChars;
                    ++count;
                }
                if (result == BytesTrie.Result.FINAL_VALUE) {
                    break;
                }
            }
            else if (result == BytesTrie.Result.NO_MATCH) {
                break;
            }
            if (numChars >= maxLength) {
                break;
            }
            c = text.nextCodePoint();
            if (c == -1) {
                break;
            }
            ++numChars;
            result = uct.nextForCodePoint(c);
        }
        count_[0] = count;
        return numChars;
    }
    
    @Override
    public int getType() {
        return 1;
    }
}
