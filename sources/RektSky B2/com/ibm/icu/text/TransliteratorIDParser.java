package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.text.*;
import java.util.*;

class TransliteratorIDParser
{
    private static final char ID_DELIM = ';';
    private static final char TARGET_SEP = '-';
    private static final char VARIANT_SEP = '/';
    private static final char OPEN_REV = '(';
    private static final char CLOSE_REV = ')';
    private static final String ANY = "Any";
    private static final int FORWARD = 0;
    private static final int REVERSE = 1;
    private static final Map<CaseInsensitiveString, String> SPECIAL_INVERSES;
    
    public static SingleID parseFilterID(final String id, final int[] pos) {
        final int start = pos[0];
        final Specs specs = parseFilterID(id, pos, true);
        if (specs == null) {
            pos[0] = start;
            return null;
        }
        final SingleID single = specsToID(specs, 0);
        single.filter = specs.filter;
        return single;
    }
    
    public static SingleID parseSingleID(final String id, final int[] pos, final int dir) {
        final int start = pos[0];
        Specs specsA = null;
        Specs specsB = null;
        boolean sawParen = false;
        int pass = 1;
        while (pass <= 2) {
            if (pass == 2) {
                specsA = parseFilterID(id, pos, true);
                if (specsA == null) {
                    pos[0] = start;
                    return null;
                }
            }
            if (Utility.parseChar(id, pos, '(')) {
                sawParen = true;
                if (Utility.parseChar(id, pos, ')')) {
                    break;
                }
                specsB = parseFilterID(id, pos, true);
                if (specsB == null || !Utility.parseChar(id, pos, ')')) {
                    pos[0] = start;
                    return null;
                }
                break;
            }
            else {
                ++pass;
            }
        }
        SingleID single;
        if (sawParen) {
            if (dir == 0) {
                single = specsToID(specsA, 0);
                single.canonID = single.canonID + '(' + specsToID(specsB, 0).canonID + ')';
                if (specsA != null) {
                    single.filter = specsA.filter;
                }
            }
            else {
                single = specsToID(specsB, 0);
                single.canonID = single.canonID + '(' + specsToID(specsA, 0).canonID + ')';
                if (specsB != null) {
                    single.filter = specsB.filter;
                }
            }
        }
        else {
            if (dir == 0) {
                single = specsToID(specsA, 0);
            }
            else {
                single = specsToSpecialInverse(specsA);
                if (single == null) {
                    single = specsToID(specsA, 1);
                }
            }
            single.filter = specsA.filter;
        }
        return single;
    }
    
    public static UnicodeSet parseGlobalFilter(final String id, final int[] pos, final int dir, final int[] withParens, final StringBuffer canonID) {
        UnicodeSet filter = null;
        final int start = pos[0];
        if (withParens[0] == -1) {
            withParens[0] = (Utility.parseChar(id, pos, '(') ? 1 : 0);
        }
        else if (withParens[0] == 1 && !Utility.parseChar(id, pos, '(')) {
            pos[0] = start;
            return null;
        }
        pos[0] = PatternProps.skipWhiteSpace(id, pos[0]);
        if (UnicodeSet.resemblesPattern(id, pos[0])) {
            final ParsePosition ppos = new ParsePosition(pos[0]);
            try {
                filter = new UnicodeSet(id, ppos, null);
            }
            catch (IllegalArgumentException e) {
                pos[0] = start;
                return null;
            }
            String pattern = id.substring(pos[0], ppos.getIndex());
            pos[0] = ppos.getIndex();
            if (withParens[0] == 1 && !Utility.parseChar(id, pos, ')')) {
                pos[0] = start;
                return null;
            }
            if (canonID != null) {
                if (dir == 0) {
                    if (withParens[0] == 1) {
                        pattern = String.valueOf('(') + pattern + ')';
                    }
                    canonID.append(pattern + ';');
                }
                else {
                    if (withParens[0] == 0) {
                        pattern = String.valueOf('(') + pattern + ')';
                    }
                    canonID.insert(0, pattern + ';');
                }
            }
        }
        return filter;
    }
    
    public static boolean parseCompoundID(final String id, final int dir, final StringBuffer canonID, final List<SingleID> list, final UnicodeSet[] globalFilter) {
        final int[] pos = { 0 };
        final int[] withParens = { 0 };
        list.clear();
        globalFilter[0] = null;
        canonID.setLength(0);
        withParens[0] = 0;
        UnicodeSet filter = parseGlobalFilter(id, pos, dir, withParens, canonID);
        if (filter != null) {
            if (!Utility.parseChar(id, pos, ';')) {
                canonID.setLength(0);
                pos[0] = 0;
            }
            if (dir == 0) {
                globalFilter[0] = filter;
            }
        }
        boolean sawDelimiter = true;
        while (true) {
            final SingleID single = parseSingleID(id, pos, dir);
            if (single == null) {
                break;
            }
            if (dir == 0) {
                list.add(single);
            }
            else {
                list.add(0, single);
            }
            if (!Utility.parseChar(id, pos, ';')) {
                sawDelimiter = false;
                break;
            }
        }
        if (list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            final SingleID single2 = list.get(i);
            canonID.append(single2.canonID);
            if (i != list.size() - 1) {
                canonID.append(';');
            }
        }
        if (sawDelimiter) {
            withParens[0] = 1;
            filter = parseGlobalFilter(id, pos, dir, withParens, canonID);
            if (filter != null) {
                Utility.parseChar(id, pos, ';');
                if (dir == 1) {
                    globalFilter[0] = filter;
                }
            }
        }
        pos[0] = PatternProps.skipWhiteSpace(id, pos[0]);
        return pos[0] == id.length();
    }
    
    static List<Transliterator> instantiateList(final List<SingleID> ids) {
        final List<Transliterator> translits = new ArrayList<Transliterator>();
        for (final SingleID single : ids) {
            if (single.basicID.length() == 0) {
                continue;
            }
            final Transliterator t = single.getInstance();
            if (t == null) {
                throw new IllegalArgumentException("Illegal ID " + single.canonID);
            }
            translits.add(t);
        }
        if (translits.size() == 0) {
            final Transliterator t = Transliterator.getBasicInstance("Any-Null", null);
            if (t == null) {
                throw new IllegalArgumentException("Internal error; cannot instantiate Any-Null");
            }
            translits.add(t);
        }
        return translits;
    }
    
    public static String[] IDtoSTV(final String id) {
        String source = "Any";
        String target = null;
        String variant = "";
        int sep = id.indexOf(45);
        int var = id.indexOf(47);
        if (var < 0) {
            var = id.length();
        }
        boolean isSourcePresent = false;
        if (sep < 0) {
            target = id.substring(0, var);
            variant = id.substring(var);
        }
        else if (sep < var) {
            if (sep > 0) {
                source = id.substring(0, sep);
                isSourcePresent = true;
            }
            target = id.substring(++sep, var);
            variant = id.substring(var);
        }
        else {
            if (var > 0) {
                source = id.substring(0, var);
                isSourcePresent = true;
            }
            variant = id.substring(var, sep++);
            target = id.substring(sep);
        }
        if (variant.length() > 0) {
            variant = variant.substring(1);
        }
        return new String[] { source, target, variant, isSourcePresent ? "" : null };
    }
    
    public static String STVtoID(final String source, final String target, final String variant) {
        final StringBuilder id = new StringBuilder(source);
        if (id.length() == 0) {
            id.append("Any");
        }
        id.append('-').append(target);
        if (variant != null && variant.length() != 0) {
            id.append('/').append(variant);
        }
        return id.toString();
    }
    
    public static void registerSpecialInverse(final String target, final String inverseTarget, final boolean bidirectional) {
        TransliteratorIDParser.SPECIAL_INVERSES.put(new CaseInsensitiveString(target), inverseTarget);
        if (bidirectional && !target.equalsIgnoreCase(inverseTarget)) {
            TransliteratorIDParser.SPECIAL_INVERSES.put(new CaseInsensitiveString(inverseTarget), target);
        }
    }
    
    private static Specs parseFilterID(final String id, final int[] pos, final boolean allowFilter) {
        String first = null;
        String source = null;
        String target = null;
        String variant = null;
        String filter = null;
        char delimiter = '\0';
        int specCount = 0;
        final int start = pos[0];
        while (true) {
            pos[0] = PatternProps.skipWhiteSpace(id, pos[0]);
            if (pos[0] == id.length()) {
                break;
            }
            if (allowFilter && filter == null && UnicodeSet.resemblesPattern(id, pos[0])) {
                final ParsePosition ppos = new ParsePosition(pos[0]);
                new UnicodeSet(id, ppos, null);
                filter = id.substring(pos[0], ppos.getIndex());
                pos[0] = ppos.getIndex();
            }
            else {
                if (delimiter == '\0') {
                    final char c = id.charAt(pos[0]);
                    if ((c == '-' && target == null) || (c == '/' && variant == null)) {
                        delimiter = c;
                        final int n = 0;
                        ++pos[n];
                        continue;
                    }
                }
                if (delimiter == '\0' && specCount > 0) {
                    break;
                }
                final String spec = Utility.parseUnicodeIdentifier(id, pos);
                if (spec == null) {
                    break;
                }
                switch (delimiter) {
                    case '\0': {
                        first = spec;
                        break;
                    }
                    case '-': {
                        target = spec;
                        break;
                    }
                    case '/': {
                        variant = spec;
                        break;
                    }
                }
                ++specCount;
                delimiter = '\0';
            }
        }
        if (first != null) {
            if (target == null) {
                target = first;
            }
            else {
                source = first;
            }
        }
        if (source == null && target == null) {
            pos[0] = start;
            return null;
        }
        boolean sawSource = true;
        if (source == null) {
            source = "Any";
            sawSource = false;
        }
        if (target == null) {
            target = "Any";
        }
        return new Specs(source, target, variant, sawSource, filter);
    }
    
    private static SingleID specsToID(final Specs specs, final int dir) {
        String canonID = "";
        String basicID = "";
        String basicPrefix = "";
        if (specs != null) {
            final StringBuilder buf = new StringBuilder();
            if (dir == 0) {
                if (specs.sawSource) {
                    buf.append(specs.source).append('-');
                }
                else {
                    basicPrefix = specs.source + '-';
                }
                buf.append(specs.target);
            }
            else {
                buf.append(specs.target).append('-').append(specs.source);
            }
            if (specs.variant != null) {
                buf.append('/').append(specs.variant);
            }
            basicID = basicPrefix + buf.toString();
            if (specs.filter != null) {
                buf.insert(0, specs.filter);
            }
            canonID = buf.toString();
        }
        return new SingleID(canonID, basicID);
    }
    
    private static SingleID specsToSpecialInverse(final Specs specs) {
        if (!specs.source.equalsIgnoreCase("Any")) {
            return null;
        }
        final String inverseTarget = TransliteratorIDParser.SPECIAL_INVERSES.get(new CaseInsensitiveString(specs.target));
        if (inverseTarget != null) {
            final StringBuilder buf = new StringBuilder();
            if (specs.filter != null) {
                buf.append(specs.filter);
            }
            if (specs.sawSource) {
                buf.append("Any").append('-');
            }
            buf.append(inverseTarget);
            String basicID = "Any-" + inverseTarget;
            if (specs.variant != null) {
                buf.append('/').append(specs.variant);
                basicID = basicID + '/' + specs.variant;
            }
            return new SingleID(buf.toString(), basicID);
        }
        return null;
    }
    
    static {
        SPECIAL_INVERSES = Collections.synchronizedMap(new HashMap<CaseInsensitiveString, String>());
    }
    
    private static class Specs
    {
        public String source;
        public String target;
        public String variant;
        public String filter;
        public boolean sawSource;
        
        Specs(final String s, final String t, final String v, final boolean sawS, final String f) {
            this.source = s;
            this.target = t;
            this.variant = v;
            this.sawSource = sawS;
            this.filter = f;
        }
    }
    
    static class SingleID
    {
        public String canonID;
        public String basicID;
        public String filter;
        
        SingleID(final String c, final String b, final String f) {
            this.canonID = c;
            this.basicID = b;
            this.filter = f;
        }
        
        SingleID(final String c, final String b) {
            this(c, b, null);
        }
        
        Transliterator getInstance() {
            Transliterator t;
            if (this.basicID == null || this.basicID.length() == 0) {
                t = Transliterator.getBasicInstance("Any-Null", this.canonID);
            }
            else {
                t = Transliterator.getBasicInstance(this.basicID, this.canonID);
            }
            if (t != null && this.filter != null) {
                t.setFilter(new UnicodeSet(this.filter));
            }
            return t;
        }
    }
}
