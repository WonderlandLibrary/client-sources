/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RuleBasedTimeZone
extends BasicTimeZone {
    private static final long serialVersionUID = 7580833058949327935L;
    private final InitialTimeZoneRule initialRule;
    private List<TimeZoneRule> historicRules;
    private AnnualTimeZoneRule[] finalRules;
    private transient List<TimeZoneTransition> historicTransitions;
    private transient boolean upToDate;
    private volatile transient boolean isFrozen = false;

    public RuleBasedTimeZone(String string, InitialTimeZoneRule initialTimeZoneRule) {
        super(string);
        this.initialRule = initialTimeZoneRule;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void addTransitionRule(TimeZoneRule timeZoneRule) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen RuleBasedTimeZone instance.");
        }
        if (!timeZoneRule.isTransitionRule()) {
            throw new IllegalArgumentException("Rule must be a transition rule");
        }
        if (timeZoneRule instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)timeZoneRule).getEndYear() == Integer.MAX_VALUE) {
            if (this.finalRules == null) {
                this.finalRules = new AnnualTimeZoneRule[2];
                this.finalRules[0] = (AnnualTimeZoneRule)timeZoneRule;
            } else {
                if (this.finalRules[1] != null) throw new IllegalStateException("Too many final rules");
                this.finalRules[1] = (AnnualTimeZoneRule)timeZoneRule;
            }
        } else {
            if (this.historicRules == null) {
                this.historicRules = new ArrayList<TimeZoneRule>();
            }
            this.historicRules.add(timeZoneRule);
        }
        this.upToDate = false;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n == 0) {
            n2 = 1 - n2;
        }
        long l = Grego.fieldsToDay(n2, n3, n4) * 86400000L + (long)n6;
        int[] nArray = new int[2];
        this.getOffset(l, true, 3, 1, nArray);
        return nArray[0] + nArray[1];
    }

    @Override
    public void getOffset(long l, boolean bl, int[] nArray) {
        this.getOffset(l, bl, 4, 12, nArray);
    }

    @Override
    @Deprecated
    public void getOffsetFromLocal(long l, int n, int n2, int[] nArray) {
        this.getOffset(l, true, n, n2, nArray);
    }

    @Override
    public int getRawOffset() {
        long l = System.currentTimeMillis();
        int[] nArray = new int[2];
        this.getOffset(l, false, nArray);
        return nArray[0];
    }

    @Override
    public boolean inDaylightTime(Date date) {
        int[] nArray = new int[2];
        this.getOffset(date.getTime(), false, nArray);
        return nArray[1] != 0;
    }

    @Override
    public void setRawOffset(int n) {
        throw new UnsupportedOperationException("setRawOffset in RuleBasedTimeZone is not supported.");
    }

    @Override
    public boolean useDaylightTime() {
        long l = System.currentTimeMillis();
        int[] nArray = new int[2];
        this.getOffset(l, false, nArray);
        if (nArray[1] != 0) {
            return false;
        }
        TimeZoneTransition timeZoneTransition = this.getNextTransition(l, true);
        return timeZoneTransition == null || timeZoneTransition.getTo().getDSTSavings() == 0;
    }

    @Override
    public boolean observesDaylightTime() {
        TimeZoneTransition timeZoneTransition;
        BitSet bitSet;
        long l = System.currentTimeMillis();
        int[] nArray = new int[2];
        this.getOffset(l, false, nArray);
        if (nArray[1] != 0) {
            return false;
        }
        BitSet bitSet2 = bitSet = this.finalRules == null ? null : new BitSet(this.finalRules.length);
        while ((timeZoneTransition = this.getNextTransition(l, true)) != null) {
            TimeZoneRule timeZoneRule = timeZoneTransition.getTo();
            if (timeZoneRule.getDSTSavings() != 0) {
                return false;
            }
            if (bitSet != null) {
                for (int i = 0; i < this.finalRules.length; ++i) {
                    if (!this.finalRules[i].equals(timeZoneRule)) continue;
                    bitSet.set(i);
                }
                if (bitSet.cardinality() == this.finalRules.length) break;
            }
            l = timeZoneTransition.getTime();
        }
        return true;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return false;
        }
        if (!(timeZone instanceof RuleBasedTimeZone)) {
            return true;
        }
        RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone)timeZone;
        if (!this.initialRule.isEquivalentTo(ruleBasedTimeZone.initialRule)) {
            return true;
        }
        if (this.finalRules != null && ruleBasedTimeZone.finalRules != null) {
            for (int i = 0; i < this.finalRules.length; ++i) {
                if (this.finalRules[i] == null && ruleBasedTimeZone.finalRules[i] == null || this.finalRules[i] != null && ruleBasedTimeZone.finalRules[i] != null && this.finalRules[i].isEquivalentTo(ruleBasedTimeZone.finalRules[i])) continue;
                return true;
            }
        } else if (this.finalRules != null || ruleBasedTimeZone.finalRules != null) {
            return true;
        }
        if (this.historicRules != null && ruleBasedTimeZone.historicRules != null) {
            if (this.historicRules.size() != ruleBasedTimeZone.historicRules.size()) {
                return true;
            }
            for (TimeZoneRule timeZoneRule : this.historicRules) {
                boolean bl = false;
                for (TimeZoneRule timeZoneRule2 : ruleBasedTimeZone.historicRules) {
                    if (!timeZoneRule.isEquivalentTo(timeZoneRule2)) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
                return true;
            }
        } else if (this.historicRules != null || ruleBasedTimeZone.historicRules != null) {
            return true;
        }
        return false;
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        int n = 1;
        if (this.historicRules != null) {
            n += this.historicRules.size();
        }
        if (this.finalRules != null) {
            n = this.finalRules[1] != null ? (n += 2) : ++n;
        }
        TimeZoneRule[] timeZoneRuleArray = new TimeZoneRule[n];
        timeZoneRuleArray[0] = this.initialRule;
        if (this.historicRules != null) {
            for (int i = 1; i < this.historicRules.size() + 1; ++i) {
                timeZoneRuleArray[i] = this.historicRules.get(i - 1);
            }
        }
        if (this.finalRules != null) {
            timeZoneRuleArray[i++] = this.finalRules[0];
            if (this.finalRules[1] != null) {
                timeZoneRuleArray[i] = this.finalRules[1];
            }
        }
        return timeZoneRuleArray;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        Object object;
        Object object2;
        this.complete();
        if (this.historicTransitions == null) {
            return null;
        }
        boolean bl2 = false;
        TimeZoneTransition timeZoneTransition = this.historicTransitions.get(0);
        long l2 = timeZoneTransition.getTime();
        if (l2 > l || bl && l2 == l) {
            object2 = timeZoneTransition;
        } else {
            int n = this.historicTransitions.size() - 1;
            timeZoneTransition = this.historicTransitions.get(n);
            l2 = timeZoneTransition.getTime();
            if (bl && l2 == l) {
                object2 = timeZoneTransition;
            } else if (l2 <= l) {
                if (this.finalRules == null) return null;
                object = this.finalRules[0].getNextStart(l, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), bl);
                Date date = this.finalRules[1].getNextStart(l, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), bl);
                timeZoneTransition = date.after((Date)object) ? new TimeZoneTransition(((Date)object).getTime(), this.finalRules[1], this.finalRules[0]) : new TimeZoneTransition(date.getTime(), this.finalRules[0], this.finalRules[1]);
                object2 = timeZoneTransition;
                bl2 = true;
            } else {
                --n;
                object = timeZoneTransition;
                while (n > 0 && (l2 = (timeZoneTransition = this.historicTransitions.get(n)).getTime()) >= l && (bl || l2 != l)) {
                    --n;
                    object = timeZoneTransition;
                }
                object2 = object;
            }
        }
        TimeZoneRule timeZoneRule = ((TimeZoneTransition)object2).getFrom();
        object = ((TimeZoneTransition)object2).getTo();
        if (timeZoneRule.getRawOffset() != ((TimeZoneRule)object).getRawOffset()) return object2;
        if (timeZoneRule.getDSTSavings() != ((TimeZoneRule)object).getDSTSavings()) return object2;
        if (!bl2) return this.getNextTransition(((TimeZoneTransition)object2).getTime(), true);
        return null;
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        Serializable serializable;
        TimeZoneTransition timeZoneTransition;
        this.complete();
        if (this.historicTransitions == null) {
            return null;
        }
        TimeZoneTransition timeZoneTransition2 = this.historicTransitions.get(0);
        long l2 = timeZoneTransition2.getTime();
        if (bl && l2 == l) {
            timeZoneTransition = timeZoneTransition2;
        } else {
            if (l2 >= l) {
                return null;
            }
            int n = this.historicTransitions.size() - 1;
            timeZoneTransition2 = this.historicTransitions.get(n);
            l2 = timeZoneTransition2.getTime();
            if (bl && l2 == l) {
                timeZoneTransition = timeZoneTransition2;
            } else if (l2 < l) {
                if (this.finalRules != null) {
                    serializable = this.finalRules[0].getPreviousStart(l, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), bl);
                    Date date = this.finalRules[1].getPreviousStart(l, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), bl);
                    timeZoneTransition2 = date.before((Date)serializable) ? new TimeZoneTransition(((Date)serializable).getTime(), this.finalRules[1], this.finalRules[0]) : new TimeZoneTransition(date.getTime(), this.finalRules[0], this.finalRules[1]);
                }
                timeZoneTransition = timeZoneTransition2;
            } else {
                --n;
                while (!(n < 0 || (l2 = (timeZoneTransition2 = this.historicTransitions.get(n)).getTime()) < l || bl && l2 == l)) {
                    --n;
                }
                timeZoneTransition = timeZoneTransition2;
            }
        }
        TimeZoneRule timeZoneRule = timeZoneTransition.getFrom();
        serializable = timeZoneTransition.getTo();
        if (timeZoneRule.getRawOffset() == ((TimeZoneRule)serializable).getRawOffset() && timeZoneRule.getDSTSavings() == ((TimeZoneRule)serializable).getDSTSavings()) {
            timeZoneTransition = this.getPreviousTransition(timeZoneTransition.getTime(), true);
        }
        return timeZoneTransition;
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    private void complete() {
        if (this.upToDate) {
            return;
        }
        if (this.finalRules != null && this.finalRules[1] == null) {
            throw new IllegalStateException("Incomplete final rules");
        }
        if (this.historicRules != null || this.finalRules != null) {
            Cloneable cloneable;
            InitialTimeZoneRule initialTimeZoneRule = this.initialRule;
            long l = -184303902528000000L;
            if (this.historicRules != null) {
                cloneable = new BitSet(this.historicRules.size());
                while (true) {
                    long l2;
                    Date date;
                    int n;
                    int n2 = initialTimeZoneRule.getRawOffset();
                    int n3 = initialTimeZoneRule.getDSTSavings();
                    long l3 = 183882168921600000L;
                    TimeZoneRule timeZoneRule = null;
                    for (n = 0; n < this.historicRules.size(); ++n) {
                        if (((BitSet)cloneable).get(n)) continue;
                        TimeZoneRule timeZoneRule2 = this.historicRules.get(n);
                        date = timeZoneRule2.getNextStart(l, n2, n3, true);
                        if (date == null) {
                            ((BitSet)cloneable).set(n);
                            continue;
                        }
                        if (timeZoneRule2 == initialTimeZoneRule || timeZoneRule2.getName().equals(initialTimeZoneRule.getName()) && timeZoneRule2.getRawOffset() == initialTimeZoneRule.getRawOffset() && timeZoneRule2.getDSTSavings() == initialTimeZoneRule.getDSTSavings() || (l2 = date.getTime()) >= l3) continue;
                        l3 = l2;
                        timeZoneRule = timeZoneRule2;
                    }
                    if (timeZoneRule == null) {
                        n = 1;
                        for (int i = 0; i < this.historicRules.size(); ++i) {
                            if (((BitSet)cloneable).get(i)) continue;
                            n = 0;
                            break;
                        }
                        if (n != 0) break;
                    }
                    if (this.finalRules != null) {
                        for (n = 0; n < 2; ++n) {
                            if (this.finalRules[n] == initialTimeZoneRule || (date = this.finalRules[n].getNextStart(l, n2, n3, true)) == null || (l2 = date.getTime()) >= l3) continue;
                            l3 = l2;
                            timeZoneRule = this.finalRules[n];
                        }
                    }
                    if (timeZoneRule == null) break;
                    if (this.historicTransitions == null) {
                        this.historicTransitions = new ArrayList<TimeZoneTransition>();
                    }
                    this.historicTransitions.add(new TimeZoneTransition(l3, initialTimeZoneRule, timeZoneRule));
                    l = l3;
                    initialTimeZoneRule = timeZoneRule;
                }
            }
            if (this.finalRules != null) {
                if (this.historicTransitions == null) {
                    this.historicTransitions = new ArrayList<TimeZoneTransition>();
                }
                cloneable = this.finalRules[0].getNextStart(l, initialTimeZoneRule.getRawOffset(), initialTimeZoneRule.getDSTSavings(), true);
                Date date = this.finalRules[1].getNextStart(l, initialTimeZoneRule.getRawOffset(), initialTimeZoneRule.getDSTSavings(), true);
                if (date.after((Date)cloneable)) {
                    this.historicTransitions.add(new TimeZoneTransition(((Date)cloneable).getTime(), initialTimeZoneRule, this.finalRules[0]));
                    date = this.finalRules[1].getNextStart(((Date)cloneable).getTime(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), true);
                    this.historicTransitions.add(new TimeZoneTransition(date.getTime(), this.finalRules[0], this.finalRules[1]));
                } else {
                    this.historicTransitions.add(new TimeZoneTransition(date.getTime(), initialTimeZoneRule, this.finalRules[1]));
                    cloneable = this.finalRules[0].getNextStart(date.getTime(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), true);
                    this.historicTransitions.add(new TimeZoneTransition(((Date)cloneable).getTime(), this.finalRules[1], this.finalRules[0]));
                }
            }
        }
        this.upToDate = true;
    }

    private void getOffset(long l, boolean bl, int n, int n2, int[] nArray) {
        this.complete();
        TimeZoneRule timeZoneRule = null;
        if (this.historicTransitions == null) {
            timeZoneRule = this.initialRule;
        } else {
            long l2 = RuleBasedTimeZone.getTransitionTime(this.historicTransitions.get(0), bl, n, n2);
            if (l < l2) {
                timeZoneRule = this.initialRule;
            } else {
                int n3 = this.historicTransitions.size() - 1;
                long l3 = RuleBasedTimeZone.getTransitionTime(this.historicTransitions.get(n3), bl, n, n2);
                if (l > l3) {
                    if (this.finalRules != null) {
                        timeZoneRule = this.findRuleInFinal(l, bl, n, n2);
                    }
                    if (timeZoneRule == null) {
                        timeZoneRule = this.historicTransitions.get(n3).getTo();
                    }
                } else {
                    while (n3 >= 0 && l < RuleBasedTimeZone.getTransitionTime(this.historicTransitions.get(n3), bl, n, n2)) {
                        --n3;
                    }
                    timeZoneRule = this.historicTransitions.get(n3).getTo();
                }
            }
        }
        nArray[0] = timeZoneRule.getRawOffset();
        nArray[1] = timeZoneRule.getDSTSavings();
    }

    private TimeZoneRule findRuleInFinal(long l, boolean bl, int n, int n2) {
        int n3;
        if (this.finalRules == null || this.finalRules.length != 2 || this.finalRules[0] == null || this.finalRules[1] == null) {
            return null;
        }
        long l2 = l;
        if (bl) {
            n3 = RuleBasedTimeZone.getLocalDelta(this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), n, n2);
            l2 -= (long)n3;
        }
        Date date = this.finalRules[0].getPreviousStart(l2, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), false);
        l2 = l;
        if (bl) {
            n3 = RuleBasedTimeZone.getLocalDelta(this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), n, n2);
            l2 -= (long)n3;
        }
        Date date2 = this.finalRules[1].getPreviousStart(l2, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), false);
        if (date == null || date2 == null) {
            if (date != null) {
                return this.finalRules[0];
            }
            if (date2 != null) {
                return this.finalRules[1];
            }
            return null;
        }
        return date.after(date2) ? this.finalRules[0] : this.finalRules[1];
    }

    private static long getTransitionTime(TimeZoneTransition timeZoneTransition, boolean bl, int n, int n2) {
        long l = timeZoneTransition.getTime();
        if (bl) {
            l += (long)RuleBasedTimeZone.getLocalDelta(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings(), n, n2);
        }
        return l;
    }

    private static int getLocalDelta(int n, int n2, int n3, int n4, int n5, int n6) {
        boolean bl;
        int n7 = 0;
        int n8 = n + n2;
        int n9 = n3 + n4;
        boolean bl2 = n2 != 0 && n4 == 0;
        boolean bl3 = bl = n2 == 0 && n4 != 0;
        n7 = n9 - n8 >= 0 ? ((n5 & 3) == 1 && bl2 || (n5 & 3) == 3 && bl ? n8 : ((n5 & 3) == 1 && bl || (n5 & 3) == 3 && bl2 ? n9 : ((n5 & 0xC) == 12 ? n8 : n9))) : ((n6 & 3) == 1 && bl2 || (n6 & 3) == 3 && bl ? n9 : ((n6 & 3) == 1 && bl || (n6 & 3) == 3 && bl2 ? n8 : ((n6 & 0xC) == 4 ? n8 : n9)));
        return n7;
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public TimeZone freeze() {
        this.complete();
        this.isFrozen = true;
        return this;
    }

    @Override
    public TimeZone cloneAsThawed() {
        RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone)super.cloneAsThawed();
        if (this.historicRules != null) {
            ruleBasedTimeZone.historicRules = new ArrayList<TimeZoneRule>(this.historicRules);
        }
        if (this.finalRules != null) {
            ruleBasedTimeZone.finalRules = (AnnualTimeZoneRule[])this.finalRules.clone();
        }
        ruleBasedTimeZone.isFrozen = false;
        return ruleBasedTimeZone;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }
}

