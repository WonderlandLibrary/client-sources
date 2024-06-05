package com.ibm.icu.impl.text;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;

@Deprecated
public class RbnfScannerProviderImpl implements RbnfLenientScannerProvider
{
    private static final boolean DEBUG;
    private Map<String, RbnfLenientScanner> cache;
    
    @Deprecated
    public RbnfScannerProviderImpl() {
        this.cache = new HashMap<String, RbnfLenientScanner>();
    }
    
    @Deprecated
    @Override
    public RbnfLenientScanner get(final ULocale locale, final String extras) {
        RbnfLenientScanner result = null;
        final String key = locale.toString() + "/" + extras;
        synchronized (this.cache) {
            result = this.cache.get(key);
            if (result != null) {
                return result;
            }
        }
        result = this.createScanner(locale, extras);
        synchronized (this.cache) {
            this.cache.put(key, result);
        }
        return result;
    }
    
    @Deprecated
    protected RbnfLenientScanner createScanner(final ULocale locale, final String extras) {
        RuleBasedCollator collator = null;
        try {
            collator = (RuleBasedCollator)Collator.getInstance(locale.toLocale());
            if (extras != null) {
                final String rules = collator.getRules() + extras;
                collator = new RuleBasedCollator(rules);
            }
            collator.setDecomposition(17);
        }
        catch (Exception e) {
            if (RbnfScannerProviderImpl.DEBUG) {
                e.printStackTrace();
                System.out.println("++++");
            }
            collator = null;
        }
        return new RbnfLenientScannerImpl(collator);
    }
    
    static {
        DEBUG = ICUDebug.enabled("rbnf");
    }
    
    private static class RbnfLenientScannerImpl implements RbnfLenientScanner
    {
        private final RuleBasedCollator collator;
        
        private RbnfLenientScannerImpl(final RuleBasedCollator rbc) {
            this.collator = rbc;
        }
        
        @Override
        public boolean allIgnorable(final String s) {
            CollationElementIterator iter;
            int o;
            for (iter = this.collator.getCollationElementIterator(s), o = iter.next(); o != -1 && CollationElementIterator.primaryOrder(o) == 0; o = iter.next()) {}
            return o == -1;
        }
        
        @Override
        public int[] findText(final String str, final String key, final int startingAt) {
            for (int p = startingAt, keyLen = 0; p < str.length() && keyLen == 0; ++p) {
                keyLen = this.prefixLength(str.substring(p), key);
                if (keyLen != 0) {
                    return new int[] { p, keyLen };
                }
            }
            return new int[] { -1, 0 };
        }
        
        public int[] findText2(final String str, final String key, final int startingAt) {
            final CollationElementIterator strIter = this.collator.getCollationElementIterator(str);
            final CollationElementIterator keyIter = this.collator.getCollationElementIterator(key);
            int keyStart = -1;
            strIter.setOffset(startingAt);
            int oStr = strIter.next();
            int oKey = keyIter.next();
            while (oKey != -1) {
                while (oStr != -1 && CollationElementIterator.primaryOrder(oStr) == 0) {
                    oStr = strIter.next();
                }
                while (oKey != -1 && CollationElementIterator.primaryOrder(oKey) == 0) {
                    oKey = keyIter.next();
                }
                if (oStr == -1) {
                    return new int[] { -1, 0 };
                }
                if (oKey == -1) {
                    break;
                }
                if (CollationElementIterator.primaryOrder(oStr) == CollationElementIterator.primaryOrder(oKey)) {
                    keyStart = strIter.getOffset();
                    oStr = strIter.next();
                    oKey = keyIter.next();
                }
                else if (keyStart != -1) {
                    keyStart = -1;
                    keyIter.reset();
                }
                else {
                    oStr = strIter.next();
                }
            }
            return new int[] { keyStart, strIter.getOffset() - keyStart };
        }
        
        @Override
        public int prefixLength(final String str, final String prefix) {
            final CollationElementIterator strIter = this.collator.getCollationElementIterator(str);
            final CollationElementIterator prefixIter = this.collator.getCollationElementIterator(prefix);
            int oStr = strIter.next();
            for (int oPrefix = prefixIter.next(); oPrefix != -1; oPrefix = prefixIter.next()) {
                while (CollationElementIterator.primaryOrder(oStr) == 0 && oStr != -1) {
                    oStr = strIter.next();
                }
                while (CollationElementIterator.primaryOrder(oPrefix) == 0 && oPrefix != -1) {
                    oPrefix = prefixIter.next();
                }
                if (oPrefix == -1) {
                    break;
                }
                if (oStr == -1) {
                    return 0;
                }
                if (CollationElementIterator.primaryOrder(oStr) != CollationElementIterator.primaryOrder(oPrefix)) {
                    return 0;
                }
                oStr = strIter.next();
            }
            int result = strIter.getOffset();
            if (oStr != -1) {
                --result;
            }
            return result;
        }
    }
}
