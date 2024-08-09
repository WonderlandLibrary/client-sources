/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.text;

import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.text.CollationElementIterator;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.text.RbnfLenientScannerProvider;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.util.ULocale;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RbnfScannerProviderImpl
implements RbnfLenientScannerProvider {
    private static final boolean DEBUG = ICUDebug.enabled("rbnf");
    private Map<String, RbnfLenientScanner> cache = new HashMap<String, RbnfLenientScanner>();

    @Deprecated
    public RbnfScannerProviderImpl() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Deprecated
    public RbnfLenientScanner get(ULocale uLocale, String string) {
        RbnfLenientScanner rbnfLenientScanner = null;
        String string2 = uLocale.toString() + "/" + string;
        Map<String, RbnfLenientScanner> map = this.cache;
        synchronized (map) {
            rbnfLenientScanner = this.cache.get(string2);
            if (rbnfLenientScanner != null) {
                return rbnfLenientScanner;
            }
        }
        rbnfLenientScanner = this.createScanner(uLocale, string);
        map = this.cache;
        synchronized (map) {
            this.cache.put(string2, rbnfLenientScanner);
        }
        return rbnfLenientScanner;
    }

    @Deprecated
    protected RbnfLenientScanner createScanner(ULocale uLocale, String string) {
        RuleBasedCollator ruleBasedCollator = null;
        try {
            ruleBasedCollator = (RuleBasedCollator)Collator.getInstance(uLocale.toLocale());
            if (string != null) {
                String string2 = ruleBasedCollator.getRules() + string;
                ruleBasedCollator = new RuleBasedCollator(string2);
            }
            ruleBasedCollator.setDecomposition(17);
        } catch (Exception exception) {
            if (DEBUG) {
                exception.printStackTrace();
                System.out.println("++++");
            }
            ruleBasedCollator = null;
        }
        return new RbnfLenientScannerImpl(ruleBasedCollator, null);
    }

    private static class RbnfLenientScannerImpl
    implements RbnfLenientScanner {
        private final RuleBasedCollator collator;

        private RbnfLenientScannerImpl(RuleBasedCollator ruleBasedCollator) {
            this.collator = ruleBasedCollator;
        }

        @Override
        public boolean allIgnorable(String string) {
            CollationElementIterator collationElementIterator = this.collator.getCollationElementIterator(string);
            int n = collationElementIterator.next();
            while (n != -1 && CollationElementIterator.primaryOrder(n) == 0) {
                n = collationElementIterator.next();
            }
            return n == -1;
        }

        @Override
        public int[] findText(String string, String string2, int n) {
            int n2 = 0;
            for (int i = n; i < string.length() && n2 == 0; ++i) {
                n2 = this.prefixLength(string.substring(i), string2);
                if (n2 == 0) continue;
                return new int[]{i, n2};
            }
            return new int[]{-1, 0};
        }

        public int[] findText2(String string, String string2, int n) {
            CollationElementIterator collationElementIterator = this.collator.getCollationElementIterator(string);
            CollationElementIterator collationElementIterator2 = this.collator.getCollationElementIterator(string2);
            int n2 = -1;
            collationElementIterator.setOffset(n);
            int n3 = collationElementIterator.next();
            int n4 = collationElementIterator2.next();
            while (n4 != -1) {
                while (n3 != -1 && CollationElementIterator.primaryOrder(n3) == 0) {
                    n3 = collationElementIterator.next();
                }
                while (n4 != -1 && CollationElementIterator.primaryOrder(n4) == 0) {
                    n4 = collationElementIterator2.next();
                }
                if (n3 == -1) {
                    return new int[]{-1, 0};
                }
                if (n4 == -1) break;
                if (CollationElementIterator.primaryOrder(n3) == CollationElementIterator.primaryOrder(n4)) {
                    n2 = collationElementIterator.getOffset();
                    n3 = collationElementIterator.next();
                    n4 = collationElementIterator2.next();
                    continue;
                }
                if (n2 != -1) {
                    n2 = -1;
                    collationElementIterator2.reset();
                    continue;
                }
                n3 = collationElementIterator.next();
            }
            return new int[]{n2, collationElementIterator.getOffset() - n2};
        }

        @Override
        public int prefixLength(String string, String string2) {
            CollationElementIterator collationElementIterator = this.collator.getCollationElementIterator(string);
            CollationElementIterator collationElementIterator2 = this.collator.getCollationElementIterator(string2);
            int n = collationElementIterator.next();
            int n2 = collationElementIterator2.next();
            while (n2 != -1) {
                while (CollationElementIterator.primaryOrder(n) == 0 && n != -1) {
                    n = collationElementIterator.next();
                }
                while (CollationElementIterator.primaryOrder(n2) == 0 && n2 != -1) {
                    n2 = collationElementIterator2.next();
                }
                if (n2 == -1) break;
                if (n == -1) {
                    return 1;
                }
                if (CollationElementIterator.primaryOrder(n) != CollationElementIterator.primaryOrder(n2)) {
                    return 1;
                }
                n = collationElementIterator.next();
                n2 = collationElementIterator2.next();
            }
            int n3 = collationElementIterator.getOffset();
            if (n != -1) {
                --n3;
            }
            return n3;
        }

        RbnfLenientScannerImpl(RuleBasedCollator ruleBasedCollator, 1 var2_2) {
            this(ruleBasedCollator);
        }
    }
}

