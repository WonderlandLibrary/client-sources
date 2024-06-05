package com.ibm.icu.text;

import java.util.*;

class CompoundTransliterator extends Transliterator
{
    private Transliterator[] trans;
    private int numAnonymousRBTs;
    
    CompoundTransliterator(final List<Transliterator> list) {
        this(list, 0);
    }
    
    CompoundTransliterator(final List<Transliterator> list, final int numAnonymousRBTs) {
        super("", null);
        this.numAnonymousRBTs = 0;
        this.trans = null;
        this.init(list, 0, false);
        this.numAnonymousRBTs = numAnonymousRBTs;
    }
    
    CompoundTransliterator(final String id, final UnicodeFilter filter2, final Transliterator[] trans2, final int numAnonymousRBTs2) {
        super(id, filter2);
        this.numAnonymousRBTs = 0;
        this.trans = trans2;
        this.numAnonymousRBTs = numAnonymousRBTs2;
    }
    
    private void init(final List<Transliterator> list, final int direction, final boolean fixReverseID) {
        final int count = list.size();
        this.trans = new Transliterator[count];
        for (int i = 0; i < count; ++i) {
            final int j = (direction == 0) ? i : (count - 1 - i);
            this.trans[i] = list.get(j);
        }
        if (direction == 1 && fixReverseID) {
            final StringBuilder newID = new StringBuilder();
            for (int i = 0; i < count; ++i) {
                if (i > 0) {
                    newID.append(';');
                }
                newID.append(this.trans[i].getID());
            }
            this.setID(newID.toString());
        }
        this.computeMaximumContextLength();
    }
    
    public int getCount() {
        return this.trans.length;
    }
    
    public Transliterator getTransliterator(final int index) {
        return this.trans[index];
    }
    
    private static void _smartAppend(final StringBuilder buf, final char c) {
        if (buf.length() != 0 && buf.charAt(buf.length() - 1) != c) {
            buf.append(c);
        }
    }
    
    @Override
    public String toRules(final boolean escapeUnprintable) {
        final StringBuilder rulesSource = new StringBuilder();
        if (this.numAnonymousRBTs >= 1 && this.getFilter() != null) {
            rulesSource.append("::").append(this.getFilter().toPattern(escapeUnprintable)).append(';');
        }
        for (int i = 0; i < this.trans.length; ++i) {
            String rule;
            if (this.trans[i].getID().startsWith("%Pass")) {
                rule = this.trans[i].toRules(escapeUnprintable);
                if (this.numAnonymousRBTs > 1 && i > 0 && this.trans[i - 1].getID().startsWith("%Pass")) {
                    rule = "::Null;" + rule;
                }
            }
            else if (this.trans[i].getID().indexOf(59) >= 0) {
                rule = this.trans[i].toRules(escapeUnprintable);
            }
            else {
                rule = this.trans[i].baseToRules(escapeUnprintable);
            }
            _smartAppend(rulesSource, '\n');
            rulesSource.append(rule);
            _smartAppend(rulesSource, ';');
        }
        return rulesSource.toString();
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet filter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = new UnicodeSet(this.getFilterAsUnicodeSet(filter));
        final UnicodeSet tempTargetSet = new UnicodeSet();
        for (int i = 0; i < this.trans.length; ++i) {
            tempTargetSet.clear();
            this.trans[i].addSourceTargetSet(myFilter, sourceSet, tempTargetSet);
            targetSet.addAll(tempTargetSet);
            myFilter.addAll(tempTargetSet);
        }
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position index, final boolean incremental) {
        if (this.trans.length < 1) {
            index.start = index.limit;
            return;
        }
        int compoundLimit = index.limit;
        final int compoundStart = index.start;
        int delta = 0;
        final StringBuffer log = null;
        for (int i = 0; i < this.trans.length; ++i) {
            index.start = compoundStart;
            final int limit = index.limit;
            if (index.start == index.limit) {
                break;
            }
            this.trans[i].filteredTransliterate(text, index, incremental);
            if (!incremental && index.start != index.limit) {
                throw new RuntimeException("ERROR: Incomplete non-incremental transliteration by " + this.trans[i].getID());
            }
            delta += index.limit - limit;
            if (incremental) {
                index.limit = index.start;
            }
        }
        compoundLimit += delta;
        index.limit = compoundLimit;
    }
    
    private void computeMaximumContextLength() {
        int max = 0;
        for (int i = 0; i < this.trans.length; ++i) {
            final int len = this.trans[i].getMaximumContextLength();
            if (len > max) {
                max = len;
            }
        }
        this.setMaximumContextLength(max);
    }
    
    public Transliterator safeClone() {
        UnicodeFilter filter = this.getFilter();
        if (filter != null && filter instanceof UnicodeSet) {
            filter = new UnicodeSet((UnicodeSet)filter);
        }
        return new CompoundTransliterator(this.getID(), filter, this.trans, this.numAnonymousRBTs);
    }
}
