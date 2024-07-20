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

    public static NameAbbreviator getAbbreviator(String pattern) {
        if (pattern.length() > 0) {
            int i;
            String number;
            boolean isNegativeNumber;
            String trimmed = pattern.trim();
            if (trimmed.isEmpty()) {
                return DEFAULT;
            }
            if (trimmed.length() > 1 && trimmed.charAt(0) == '-') {
                isNegativeNumber = true;
                number = trimmed.substring(1);
            } else {
                isNegativeNumber = false;
                number = trimmed;
            }
            for (i = 0; i < number.length() && number.charAt(i) >= '0' && number.charAt(i) <= '9'; ++i) {
            }
            if (i == number.length()) {
                return new MaxElementAbbreviator(Integer.parseInt(number), isNegativeNumber ? MaxElementAbbreviator.Strategy.DROP : MaxElementAbbreviator.Strategy.RETAIN);
            }
            ArrayList<PatternAbbreviatorFragment> fragments = new ArrayList<PatternAbbreviatorFragment>(5);
            for (int pos = 0; pos < trimmed.length() && pos >= 0; ++pos) {
                int charCount;
                int ellipsisPos = pos;
                if (trimmed.charAt(pos) == '*') {
                    charCount = Integer.MAX_VALUE;
                    ++ellipsisPos;
                } else if (trimmed.charAt(pos) >= '0' && trimmed.charAt(pos) <= '9') {
                    charCount = trimmed.charAt(pos) - 48;
                    ++ellipsisPos;
                } else {
                    charCount = 0;
                }
                char ellipsis = '\u0000';
                if (ellipsisPos < trimmed.length() && (ellipsis = trimmed.charAt(ellipsisPos)) == '.') {
                    ellipsis = '\u0000';
                }
                fragments.add(new PatternAbbreviatorFragment(charCount, ellipsis));
                pos = trimmed.indexOf(46, pos);
                if (pos == -1) break;
            }
            return new PatternAbbreviator(fragments);
        }
        return DEFAULT;
    }

    public static NameAbbreviator getDefaultAbbreviator() {
        return DEFAULT;
    }

    public abstract void abbreviate(String var1, StringBuilder var2);

    private static class PatternAbbreviator
    extends NameAbbreviator {
        private final PatternAbbreviatorFragment[] fragments;

        public PatternAbbreviator(List<PatternAbbreviatorFragment> fragments) {
            if (fragments.isEmpty()) {
                throw new IllegalArgumentException("fragments must have at least one element");
            }
            this.fragments = new PatternAbbreviatorFragment[fragments.size()];
            fragments.toArray(this.fragments);
        }

        @Override
        public void abbreviate(String original, StringBuilder destination) {
            int pos = destination.length();
            int max = pos + original.length();
            StringBuilder sb = destination.append(original);
            for (int i = 0; i < this.fragments.length - 1 && pos < original.length(); ++i) {
                pos = this.fragments[i].abbreviate(sb, pos);
            }
            PatternAbbreviatorFragment terminalFragment = this.fragments[this.fragments.length - 1];
            while (pos < max && pos >= 0) {
                pos = terminalFragment.abbreviate(sb, pos);
            }
        }
    }

    private static class PatternAbbreviatorFragment {
        private final int charCount;
        private final char ellipsis;

        public PatternAbbreviatorFragment(int charCount, char ellipsis) {
            this.charCount = charCount;
            this.ellipsis = ellipsis;
        }

        public int abbreviate(StringBuilder buf, int startPos) {
            int start = startPos < 0 ? 0 : startPos;
            int max = buf.length();
            int nextDot = -1;
            for (int i = start; i < max; ++i) {
                if (buf.charAt(i) != '.') continue;
                nextDot = i;
                break;
            }
            if (nextDot != -1) {
                if (nextDot - startPos > this.charCount) {
                    buf.delete(startPos + this.charCount, nextDot);
                    nextDot = startPos + this.charCount;
                    if (this.ellipsis != '\u0000') {
                        buf.insert(nextDot, this.ellipsis);
                        ++nextDot;
                    }
                }
                ++nextDot;
            }
            return nextDot;
        }
    }

    private static class MaxElementAbbreviator
    extends NameAbbreviator {
        private final int count;
        private final Strategy strategy;

        public MaxElementAbbreviator(int count, Strategy strategy) {
            this.count = Math.max(count, strategy.minCount);
            this.strategy = strategy;
        }

        @Override
        public void abbreviate(String original, StringBuilder destination) {
            this.strategy.abbreviate(this.count, original, destination);
        }

        private static enum Strategy {
            DROP(0){

                @Override
                void abbreviate(int count, String original, StringBuilder destination) {
                    int start = 0;
                    for (int i = 0; i < count; ++i) {
                        int nextStart = original.indexOf(46, start);
                        if (nextStart == -1) {
                            destination.append(original);
                            return;
                        }
                        start = nextStart + 1;
                    }
                    destination.append(original, start, original.length());
                }
            }
            ,
            RETAIN(1){

                @Override
                void abbreviate(int count, String original, StringBuilder destination) {
                    int end = original.length() - 1;
                    for (int i = count; i > 0; --i) {
                        if ((end = original.lastIndexOf(46, end - 1)) != -1) continue;
                        destination.append(original);
                        return;
                    }
                    destination.append(original, end + 1, original.length());
                }
            };

            final int minCount;

            private Strategy(int minCount) {
                this.minCount = minCount;
            }

            abstract void abbreviate(int var1, String var2, StringBuilder var3);
        }
    }

    private static class NOPAbbreviator
    extends NameAbbreviator {
        @Override
        public void abbreviate(String original, StringBuilder destination) {
            destination.append(original);
        }
    }
}

