/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.TimeArrayTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Date;
import java.util.LinkedList;

public abstract class BasicTimeZone
extends TimeZone {
    private static final long serialVersionUID = -3204278532246180932L;
    private static final long MILLIS_PER_YEAR = 31536000000L;
    @Deprecated
    public static final int LOCAL_STD = 1;
    @Deprecated
    public static final int LOCAL_DST = 3;
    @Deprecated
    public static final int LOCAL_FORMER = 4;
    @Deprecated
    public static final int LOCAL_LATTER = 12;
    @Deprecated
    protected static final int STD_DST_MASK = 3;
    @Deprecated
    protected static final int FORMER_LATTER_MASK = 12;

    public abstract TimeZoneTransition getNextTransition(long var1, boolean var3);

    public abstract TimeZoneTransition getPreviousTransition(long var1, boolean var3);

    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2) {
        return this.hasEquivalentTransitions(timeZone, l, l2, true);
    }

    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2, boolean bl) {
        if (this == timeZone) {
            return false;
        }
        if (!(timeZone instanceof BasicTimeZone)) {
            return true;
        }
        int[] nArray = new int[2];
        int[] nArray2 = new int[2];
        this.getOffset(l, false, nArray);
        timeZone.getOffset(l, false, nArray2);
        if (bl ? nArray[0] + nArray[1] != nArray2[0] + nArray2[1] || nArray[1] != 0 && nArray2[1] == 0 || nArray[1] == 0 && nArray2[1] != 0 : nArray[0] != nArray2[0] || nArray[1] != nArray2[1]) {
            return true;
        }
        long l3 = l;
        while (true) {
            TimeZoneTransition timeZoneTransition = this.getNextTransition(l3, true);
            TimeZoneTransition timeZoneTransition2 = ((BasicTimeZone)timeZone).getNextTransition(l3, true);
            if (bl) {
                while (timeZoneTransition != null && timeZoneTransition.getTime() <= l2 && timeZoneTransition.getFrom().getRawOffset() + timeZoneTransition.getFrom().getDSTSavings() == timeZoneTransition.getTo().getRawOffset() + timeZoneTransition.getTo().getDSTSavings() && timeZoneTransition.getFrom().getDSTSavings() != 0 && timeZoneTransition.getTo().getDSTSavings() != 0) {
                    timeZoneTransition = this.getNextTransition(timeZoneTransition.getTime(), true);
                }
                while (timeZoneTransition2 != null && timeZoneTransition2.getTime() <= l2 && timeZoneTransition2.getFrom().getRawOffset() + timeZoneTransition2.getFrom().getDSTSavings() == timeZoneTransition2.getTo().getRawOffset() + timeZoneTransition2.getTo().getDSTSavings() && timeZoneTransition2.getFrom().getDSTSavings() != 0 && timeZoneTransition2.getTo().getDSTSavings() != 0) {
                    timeZoneTransition2 = ((BasicTimeZone)timeZone).getNextTransition(timeZoneTransition2.getTime(), true);
                }
            }
            boolean bl2 = false;
            boolean bl3 = false;
            if (timeZoneTransition != null && timeZoneTransition.getTime() <= l2) {
                bl2 = true;
            }
            if (timeZoneTransition2 != null && timeZoneTransition2.getTime() <= l2) {
                bl3 = true;
            }
            if (!bl2 && !bl3) break;
            if (!bl2 || !bl3) {
                return true;
            }
            if (timeZoneTransition.getTime() != timeZoneTransition2.getTime()) {
                return true;
            }
            if (bl ? timeZoneTransition.getTo().getRawOffset() + timeZoneTransition.getTo().getDSTSavings() != timeZoneTransition2.getTo().getRawOffset() + timeZoneTransition2.getTo().getDSTSavings() || timeZoneTransition.getTo().getDSTSavings() != 0 && timeZoneTransition2.getTo().getDSTSavings() == 0 || timeZoneTransition.getTo().getDSTSavings() == 0 && timeZoneTransition2.getTo().getDSTSavings() != 0 : timeZoneTransition.getTo().getRawOffset() != timeZoneTransition2.getTo().getRawOffset() || timeZoneTransition.getTo().getDSTSavings() != timeZoneTransition2.getTo().getDSTSavings()) {
                return true;
            }
            l3 = timeZoneTransition.getTime();
        }
        return false;
    }

    public abstract TimeZoneRule[] getTimeZoneRules();

    public TimeZoneRule[] getTimeZoneRules(long l) {
        Object object;
        TimeZoneRule[] timeZoneRuleArray = this.getTimeZoneRules();
        TimeZoneTransition timeZoneTransition = this.getPreviousTransition(l, false);
        if (timeZoneTransition == null) {
            return timeZoneRuleArray;
        }
        BitSet bitSet = new BitSet(timeZoneRuleArray.length);
        LinkedList<Serializable> linkedList = new LinkedList<Serializable>();
        InitialTimeZoneRule initialTimeZoneRule = new InitialTimeZoneRule(timeZoneTransition.getTo().getName(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings());
        linkedList.add(initialTimeZoneRule);
        bitSet.set(0);
        for (int i = 1; i < timeZoneRuleArray.length; ++i) {
            Date date = timeZoneRuleArray[i].getNextStart(l, initialTimeZoneRule.getRawOffset(), initialTimeZoneRule.getDSTSavings(), true);
            if (date != null) continue;
            bitSet.set(i);
        }
        long l2 = l;
        boolean bl = false;
        boolean bl2 = false;
        while (!(bl && bl2 || (timeZoneTransition = this.getNextTransition(l2, true)) == null)) {
            Serializable serializable;
            TimeZoneRule timeZoneRule;
            int n;
            l2 = timeZoneTransition.getTime();
            object = timeZoneTransition.getTo();
            for (n = 1; n < timeZoneRuleArray.length && !timeZoneRuleArray[n].equals(object); ++n) {
            }
            if (n >= timeZoneRuleArray.length) {
                throw new IllegalStateException("The rule was not found");
            }
            if (bitSet.get(n)) continue;
            if (object instanceof TimeArrayTimeZoneRule) {
                timeZoneRule = (TimeArrayTimeZoneRule)object;
                long l3 = l;
                while ((timeZoneTransition = this.getNextTransition(l3, true)) != null && !timeZoneTransition.getTo().equals(timeZoneRule)) {
                    l3 = timeZoneTransition.getTime();
                }
                if (timeZoneTransition != null) {
                    serializable = ((TimeArrayTimeZoneRule)timeZoneRule).getFirstStart(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings());
                    if (serializable.getTime() > l) {
                        linkedList.add(timeZoneRule);
                    } else {
                        int n2;
                        int n3;
                        long[] lArray = ((TimeArrayTimeZoneRule)timeZoneRule).getStartTimes();
                        int n4 = ((TimeArrayTimeZoneRule)timeZoneRule).getTimeType();
                        for (n3 = 0; n3 < lArray.length; ++n3) {
                            l3 = lArray[n3];
                            if (n4 == 1) {
                                l3 -= (long)timeZoneTransition.getFrom().getRawOffset();
                            }
                            if (n4 == 0) {
                                l3 -= (long)timeZoneTransition.getFrom().getDSTSavings();
                            }
                            if (l3 > l) break;
                        }
                        if ((n2 = lArray.length - n3) > 0) {
                            long[] lArray2 = new long[n2];
                            System.arraycopy(lArray, n3, lArray2, 0, n2);
                            TimeArrayTimeZoneRule timeArrayTimeZoneRule = new TimeArrayTimeZoneRule(timeZoneRule.getName(), timeZoneRule.getRawOffset(), timeZoneRule.getDSTSavings(), lArray2, ((TimeArrayTimeZoneRule)timeZoneRule).getTimeType());
                            linkedList.add(timeArrayTimeZoneRule);
                        }
                    }
                }
            } else if (object instanceof AnnualTimeZoneRule) {
                timeZoneRule = (AnnualTimeZoneRule)object;
                Date date = ((AnnualTimeZoneRule)timeZoneRule).getFirstStart(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings());
                if (date.getTime() == timeZoneTransition.getTime()) {
                    linkedList.add(timeZoneRule);
                } else {
                    int[] nArray = new int[6];
                    Grego.timeToFields(timeZoneTransition.getTime(), nArray);
                    serializable = new AnnualTimeZoneRule(timeZoneRule.getName(), timeZoneRule.getRawOffset(), timeZoneRule.getDSTSavings(), ((AnnualTimeZoneRule)timeZoneRule).getRule(), nArray[0], ((AnnualTimeZoneRule)timeZoneRule).getEndYear());
                    linkedList.add(serializable);
                }
                if (((AnnualTimeZoneRule)timeZoneRule).getEndYear() == Integer.MAX_VALUE) {
                    if (timeZoneRule.getDSTSavings() == 0) {
                        bl = true;
                    } else {
                        bl2 = true;
                    }
                }
            }
            bitSet.set(n);
        }
        object = linkedList.toArray(new TimeZoneRule[linkedList.size()]);
        return object;
    }

    public TimeZoneRule[] getSimpleTimeZoneRulesNear(long l) {
        Object object;
        AnnualTimeZoneRule[] annualTimeZoneRuleArray = null;
        InitialTimeZoneRule initialTimeZoneRule = null;
        TimeZoneTransition timeZoneTransition = this.getNextTransition(l, true);
        if (timeZoneTransition != null) {
            object = timeZoneTransition.getFrom().getName();
            int n = timeZoneTransition.getFrom().getRawOffset();
            int n2 = timeZoneTransition.getFrom().getDSTSavings();
            long l2 = timeZoneTransition.getTime();
            if ((timeZoneTransition.getFrom().getDSTSavings() == 0 && timeZoneTransition.getTo().getDSTSavings() != 0 || timeZoneTransition.getFrom().getDSTSavings() != 0 && timeZoneTransition.getTo().getDSTSavings() == 0) && l + 31536000000L > l2) {
                Date date;
                annualTimeZoneRuleArray = new AnnualTimeZoneRule[2];
                int[] nArray = Grego.timeToFields(l2 + (long)timeZoneTransition.getFrom().getRawOffset() + (long)timeZoneTransition.getFrom().getDSTSavings(), null);
                int n3 = Grego.getDayOfWeekInMonth(nArray[0], nArray[1], nArray[2]);
                DateTimeRule dateTimeRule = new DateTimeRule(nArray[1], n3, nArray[3], nArray[5], 0);
                AnnualTimeZoneRule annualTimeZoneRule = null;
                annualTimeZoneRuleArray[0] = new AnnualTimeZoneRule(timeZoneTransition.getTo().getName(), n, timeZoneTransition.getTo().getDSTSavings(), dateTimeRule, nArray[0], Integer.MAX_VALUE);
                if (timeZoneTransition.getTo().getRawOffset() == n && (timeZoneTransition = this.getNextTransition(l2, true)) != null && (timeZoneTransition.getFrom().getDSTSavings() == 0 && timeZoneTransition.getTo().getDSTSavings() != 0 || timeZoneTransition.getFrom().getDSTSavings() != 0 && timeZoneTransition.getTo().getDSTSavings() == 0) && l2 + 31536000000L > timeZoneTransition.getTime()) {
                    nArray = Grego.timeToFields(timeZoneTransition.getTime() + (long)timeZoneTransition.getFrom().getRawOffset() + (long)timeZoneTransition.getFrom().getDSTSavings(), nArray);
                    n3 = Grego.getDayOfWeekInMonth(nArray[0], nArray[1], nArray[2]);
                    dateTimeRule = new DateTimeRule(nArray[1], n3, nArray[3], nArray[5], 0);
                    annualTimeZoneRule = new AnnualTimeZoneRule(timeZoneTransition.getTo().getName(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings(), dateTimeRule, nArray[0] - 1, Integer.MAX_VALUE);
                    date = annualTimeZoneRule.getPreviousStart(l, timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), false);
                    if (date != null && date.getTime() <= l && n == timeZoneTransition.getTo().getRawOffset() && n2 == timeZoneTransition.getTo().getDSTSavings()) {
                        annualTimeZoneRuleArray[1] = annualTimeZoneRule;
                    }
                }
                if (annualTimeZoneRuleArray[0] == null && (timeZoneTransition = this.getPreviousTransition(l, false)) != null && (timeZoneTransition.getFrom().getDSTSavings() == 0 && timeZoneTransition.getTo().getDSTSavings() != 0 || timeZoneTransition.getFrom().getDSTSavings() != 0 && timeZoneTransition.getTo().getDSTSavings() == 0)) {
                    nArray = Grego.timeToFields(timeZoneTransition.getTime() + (long)timeZoneTransition.getFrom().getRawOffset() + (long)timeZoneTransition.getFrom().getDSTSavings(), nArray);
                    n3 = Grego.getDayOfWeekInMonth(nArray[0], nArray[1], nArray[2]);
                    dateTimeRule = new DateTimeRule(nArray[1], n3, nArray[3], nArray[5], 0);
                    annualTimeZoneRule = new AnnualTimeZoneRule(timeZoneTransition.getTo().getName(), n, n2, dateTimeRule, annualTimeZoneRuleArray[5].getStartYear() - 1, Integer.MAX_VALUE);
                    date = annualTimeZoneRule.getNextStart(l, timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), true);
                    if (date.getTime() > l2) {
                        annualTimeZoneRuleArray[1] = annualTimeZoneRule;
                    }
                }
                if (annualTimeZoneRuleArray[0] == null) {
                    annualTimeZoneRuleArray = null;
                } else {
                    object = annualTimeZoneRuleArray[5].getName();
                    n = annualTimeZoneRuleArray[5].getRawOffset();
                    n2 = annualTimeZoneRuleArray[5].getDSTSavings();
                }
            }
            initialTimeZoneRule = new InitialTimeZoneRule((String)object, n, n2);
        } else {
            timeZoneTransition = this.getPreviousTransition(l, false);
            if (timeZoneTransition != null) {
                initialTimeZoneRule = new InitialTimeZoneRule(timeZoneTransition.getTo().getName(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings());
            } else {
                object = new int[2];
                this.getOffset(l, false, (int[])object);
                initialTimeZoneRule = new InitialTimeZoneRule(this.getID(), (int)object[0], (int)object[1]);
            }
        }
        object = null;
        object = annualTimeZoneRuleArray == null ? new TimeZoneRule[]{initialTimeZoneRule} : new TimeZoneRule[]{initialTimeZoneRule, annualTimeZoneRuleArray[5], annualTimeZoneRuleArray[0]};
        return object;
    }

    @Deprecated
    public void getOffsetFromLocal(long l, int n, int n2, int[] nArray) {
        throw new IllegalStateException("Not implemented");
    }

    protected BasicTimeZone() {
    }

    @Deprecated
    protected BasicTimeZone(String string) {
        super(string);
    }
}

