/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public abstract class NameAbbreviator {
    private static final NameAbbreviator DEFAULT = new NOPAbbreviator();

    public static NameAbbreviator getAbbreviator(String string) {
        if (string.length() > 0) {
            int n;
            String string2;
            boolean bl;
            String string3 = string.trim();
            if (string3.isEmpty()) {
                return DEFAULT;
            }
            if (string3.length() > 1 && string3.charAt(0) == '-') {
                bl = true;
                string2 = string3.substring(1);
            } else {
                bl = false;
                string2 = string3;
            }
            for (n = 0; n < string2.length() && string2.charAt(n) >= '0' && string2.charAt(n) <= '9'; ++n) {
            }
            if (n == string2.length()) {
                return new MaxElementAbbreviator(Integer.parseInt(string2), bl ? MaxElementAbbreviator.Strategy.DROP : MaxElementAbbreviator.Strategy.RETAIN);
            }
            ArrayList<PatternAbbreviatorFragment> arrayList = new ArrayList<PatternAbbreviatorFragment>(5);
            for (int i = 0; i < string3.length() && i >= 0; ++i) {
                int n2;
                int n3 = i;
                if (string3.charAt(i) == '*') {
                    n2 = Integer.MAX_VALUE;
                    ++n3;
                } else if (string3.charAt(i) >= '0' && string3.charAt(i) <= '9') {
                    n2 = string3.charAt(i) - 48;
                    ++n3;
                } else {
                    n2 = 0;
                }
                char c = '\u0000';
                if (n3 < string3.length() && (c = string3.charAt(n3)) == '.') {
                    c = '\u0000';
                }
                arrayList.add(new PatternAbbreviatorFragment(n2, c));
                i = string3.indexOf(46, i);
                if (i == -1) break;
            }
            return new PatternAbbreviator(arrayList);
        }
        return DEFAULT;
    }

    public static NameAbbreviator getDefaultAbbreviator() {
        return DEFAULT;
    }

    public abstract void abbreviate(String var1, StringBuilder var2);

    static class 1 {
    }

    private static class PatternAbbreviator
    extends NameAbbreviator {
        private final PatternAbbreviatorFragment[] fragments;

        public PatternAbbreviator(List<PatternAbbreviatorFragment> list) {
            if (list.isEmpty()) {
                throw new IllegalArgumentException("fragments must have at least one element");
            }
            this.fragments = new PatternAbbreviatorFragment[list.size()];
            list.toArray(this.fragments);
        }

        @Override
        public void abbreviate(String string, StringBuilder stringBuilder) {
            int n = stringBuilder.length();
            int n2 = n + string.length();
            StringBuilder stringBuilder2 = stringBuilder.append(string);
            for (int i = 0; i < this.fragments.length - 1 && n < string.length(); ++i) {
                n = this.fragments[i].abbreviate(stringBuilder2, n);
            }
            PatternAbbreviatorFragment patternAbbreviatorFragment = this.fragments[this.fragments.length - 1];
            while (n < n2 && n >= 0) {
                n = patternAbbreviatorFragment.abbreviate(stringBuilder2, n);
            }
        }
    }

    private static class PatternAbbreviatorFragment {
        private final int charCount;
        private final char ellipsis;

        public PatternAbbreviatorFragment(int n, char c) {
            this.charCount = n;
            this.ellipsis = c;
        }

        public int abbreviate(StringBuilder stringBuilder, int n) {
            int n2 = n < 0 ? 0 : n;
            int n3 = stringBuilder.length();
            int n4 = -1;
            for (int i = n2; i < n3; ++i) {
                if (stringBuilder.charAt(i) != '.') continue;
                n4 = i;
                break;
            }
            if (n4 != -1) {
                if (n4 - n > this.charCount) {
                    stringBuilder.delete(n + this.charCount, n4);
                    n4 = n + this.charCount;
                    if (this.ellipsis != '\u0000') {
                        stringBuilder.insert(n4, this.ellipsis);
                        ++n4;
                    }
                }
                ++n4;
            }
            return n4;
        }
    }

    private static class MaxElementAbbreviator
    extends NameAbbreviator {
        private final int count;
        private final Strategy strategy;

        public MaxElementAbbreviator(int n, Strategy strategy) {
            this.count = Math.max(n, strategy.minCount);
            this.strategy = strategy;
        }

        @Override
        public void abbreviate(String string, StringBuilder stringBuilder) {
            this.strategy.abbreviate(this.count, string, stringBuilder);
        }

        private static enum Strategy {
            DROP(0){

                @Override
                void abbreviate(int n, String string, StringBuilder stringBuilder) {
                    int n2 = 0;
                    for (int i = 0; i < n; ++i) {
                        int n3 = string.indexOf(46, n2);
                        if (n3 == -1) {
                            stringBuilder.append(string);
                            return;
                        }
                        n2 = n3 + 1;
                    }
                    stringBuilder.append(string, n2, string.length());
                }
            }
            ,
            RETAIN(1){

                @Override
                void abbreviate(int n, String string, StringBuilder stringBuilder) {
                    int n2 = string.length() - 1;
                    for (int i = n; i > 0; --i) {
                        if ((n2 = string.lastIndexOf(46, n2 - 1)) != -1) continue;
                        stringBuilder.append(string);
                        return;
                    }
                    stringBuilder.append(string, n2 + 1, string.length());
                }
            };

            final int minCount;

            private Strategy(int n2) {
                this.minCount = n2;
            }

            abstract void abbreviate(int var1, String var2, StringBuilder var3);

            Strategy(int n2, 1 var4_4) {
                this(n2);
            }
        }
    }

    private static class NOPAbbreviator
    extends NameAbbreviator {
        @Override
        public void abbreviate(String string, StringBuilder stringBuilder) {
            stringBuilder.append(string);
        }
    }
}

