/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.RuleBasedTimeZone;
import com.ibm.icu.util.TimeArrayTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VTimeZone
extends BasicTimeZone {
    private static final long serialVersionUID = -6851467294127795902L;
    private BasicTimeZone tz;
    private List<String> vtzlines;
    private String olsonzid = null;
    private String tzurl = null;
    private Date lastmod = null;
    private static String ICU_TZVERSION;
    private static final String ICU_TZINFO_PROP = "X-TZINFO";
    private static final int DEF_DSTSAVINGS = 3600000;
    private static final long DEF_TZSTARTTIME = 0L;
    private static final long MIN_TIME = Long.MIN_VALUE;
    private static final long MAX_TIME = Long.MAX_VALUE;
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final String EQUALS_SIGN = "=";
    private static final String COMMA = ",";
    private static final String NEWLINE = "\r\n";
    private static final String ICAL_BEGIN_VTIMEZONE = "BEGIN:VTIMEZONE";
    private static final String ICAL_END_VTIMEZONE = "END:VTIMEZONE";
    private static final String ICAL_BEGIN = "BEGIN";
    private static final String ICAL_END = "END";
    private static final String ICAL_VTIMEZONE = "VTIMEZONE";
    private static final String ICAL_TZID = "TZID";
    private static final String ICAL_STANDARD = "STANDARD";
    private static final String ICAL_DAYLIGHT = "DAYLIGHT";
    private static final String ICAL_DTSTART = "DTSTART";
    private static final String ICAL_TZOFFSETFROM = "TZOFFSETFROM";
    private static final String ICAL_TZOFFSETTO = "TZOFFSETTO";
    private static final String ICAL_RDATE = "RDATE";
    private static final String ICAL_RRULE = "RRULE";
    private static final String ICAL_TZNAME = "TZNAME";
    private static final String ICAL_TZURL = "TZURL";
    private static final String ICAL_LASTMOD = "LAST-MODIFIED";
    private static final String ICAL_FREQ = "FREQ";
    private static final String ICAL_UNTIL = "UNTIL";
    private static final String ICAL_YEARLY = "YEARLY";
    private static final String ICAL_BYMONTH = "BYMONTH";
    private static final String ICAL_BYDAY = "BYDAY";
    private static final String ICAL_BYMONTHDAY = "BYMONTHDAY";
    private static final String[] ICAL_DOW_NAMES;
    private static final int[] MONTHLENGTH;
    private static final int INI = 0;
    private static final int VTZ = 1;
    private static final int TZI = 2;
    private static final int ERR = 3;
    private volatile transient boolean isFrozen = false;
    static final boolean $assertionsDisabled;

    public static VTimeZone create(String string) {
        BasicTimeZone basicTimeZone = TimeZone.getFrozenICUTimeZone(string, true);
        if (basicTimeZone == null) {
            return null;
        }
        VTimeZone vTimeZone = new VTimeZone(string);
        vTimeZone.tz = (BasicTimeZone)basicTimeZone.cloneAsThawed();
        vTimeZone.olsonzid = vTimeZone.tz.getID();
        return vTimeZone;
    }

    public static VTimeZone create(Reader reader) {
        VTimeZone vTimeZone = new VTimeZone();
        if (vTimeZone.load(reader)) {
            return vTimeZone;
        }
        return null;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        return this.tz.getOffset(n, n2, n3, n4, n5, n6);
    }

    @Override
    public void getOffset(long l, boolean bl, int[] nArray) {
        this.tz.getOffset(l, bl, nArray);
    }

    @Override
    @Deprecated
    public void getOffsetFromLocal(long l, int n, int n2, int[] nArray) {
        this.tz.getOffsetFromLocal(l, n, n2, nArray);
    }

    @Override
    public int getRawOffset() {
        return this.tz.getRawOffset();
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return this.tz.inDaylightTime(date);
    }

    @Override
    public void setRawOffset(int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tz.setRawOffset(n);
    }

    @Override
    public boolean useDaylightTime() {
        return this.tz.useDaylightTime();
    }

    @Override
    public boolean observesDaylightTime() {
        return this.tz.observesDaylightTime();
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return false;
        }
        if (timeZone instanceof VTimeZone) {
            return this.tz.hasSameRules(((VTimeZone)timeZone).tz);
        }
        return this.tz.hasSameRules(timeZone);
    }

    public String getTZURL() {
        return this.tzurl;
    }

    public void setTZURL(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tzurl = string;
    }

    public Date getLastModified() {
        return this.lastmod;
    }

    public void setLastModified(Date date) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.lastmod = date;
    }

    public void write(Writer writer) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        if (this.vtzlines != null) {
            for (String string : this.vtzlines) {
                if (string.startsWith("TZURL:")) {
                    if (this.tzurl == null) continue;
                    bufferedWriter.write(ICAL_TZURL);
                    bufferedWriter.write(COLON);
                    bufferedWriter.write(this.tzurl);
                    bufferedWriter.write(NEWLINE);
                    continue;
                }
                if (string.startsWith("LAST-MODIFIED:")) {
                    if (this.lastmod == null) continue;
                    bufferedWriter.write(ICAL_LASTMOD);
                    bufferedWriter.write(COLON);
                    bufferedWriter.write(VTimeZone.getUTCDateTimeString(this.lastmod.getTime()));
                    bufferedWriter.write(NEWLINE);
                    continue;
                }
                bufferedWriter.write(string);
                bufferedWriter.write(NEWLINE);
            }
            bufferedWriter.flush();
        } else {
            String[] stringArray = null;
            if (this.olsonzid != null && ICU_TZVERSION != null) {
                stringArray = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "]"};
            }
            this.writeZone(writer, this.tz, stringArray);
        }
    }

    public void write(Writer writer, long l) throws IOException {
        TimeZoneRule[] timeZoneRuleArray = this.tz.getTimeZoneRules(l);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)timeZoneRuleArray[0]);
        for (int i = 1; i < timeZoneRuleArray.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(timeZoneRuleArray[i]);
        }
        String[] stringArray = null;
        if (this.olsonzid != null && ICU_TZVERSION != null) {
            stringArray = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "/Partial@" + l + "]"};
        }
        this.writeZone(writer, ruleBasedTimeZone, stringArray);
    }

    public void writeSimple(Writer writer, long l) throws IOException {
        TimeZoneRule[] timeZoneRuleArray = this.tz.getSimpleTimeZoneRulesNear(l);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)timeZoneRuleArray[0]);
        for (int i = 1; i < timeZoneRuleArray.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(timeZoneRuleArray[i]);
        }
        String[] stringArray = null;
        if (this.olsonzid != null && ICU_TZVERSION != null) {
            stringArray = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "/Simple@" + l + "]"};
        }
        this.writeZone(writer, ruleBasedTimeZone, stringArray);
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        return this.tz.getNextTransition(l, bl);
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        return this.tz.getPreviousTransition(l, bl);
    }

    @Override
    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2) {
        if (this == timeZone) {
            return false;
        }
        return this.tz.hasEquivalentTransitions(timeZone, l, l2);
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        return this.tz.getTimeZoneRules();
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules(long l) {
        return this.tz.getTimeZoneRules(l);
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    private VTimeZone() {
    }

    private VTimeZone(String string) {
        super(string);
    }

    private boolean load(Reader reader) {
        try {
            this.vtzlines = new LinkedList<String>();
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                int n;
                if ((n = reader.read()) == -1) {
                    if (!bl2 || !stringBuilder.toString().startsWith(ICAL_END_VTIMEZONE)) break;
                    this.vtzlines.add(stringBuilder.toString());
                    bl3 = true;
                    break;
                }
                if (n == 13) continue;
                if (bl) {
                    if (n != 9 && n != 32) {
                        if (bl2 && stringBuilder.length() > 0) {
                            this.vtzlines.add(stringBuilder.toString());
                        }
                        stringBuilder.setLength(0);
                        if (n != 10) {
                            stringBuilder.append((char)n);
                        }
                    }
                    bl = false;
                    continue;
                }
                if (n == 10) {
                    bl = true;
                    if (bl2) {
                        if (!stringBuilder.toString().startsWith(ICAL_END_VTIMEZONE)) continue;
                        this.vtzlines.add(stringBuilder.toString());
                        bl3 = true;
                        break;
                    }
                    if (!stringBuilder.toString().startsWith(ICAL_BEGIN_VTIMEZONE)) continue;
                    this.vtzlines.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    bl2 = true;
                    bl = false;
                    continue;
                }
                stringBuilder.append((char)n);
            }
            if (!bl3) {
                return false;
            }
        } catch (IOException iOException) {
            return true;
        }
        return this.parse();
    }

    private boolean parse() {
        int timeZoneRule3;
        int n;
        if (this.vtzlines == null || this.vtzlines.size() == 0) {
            return true;
        }
        String string = null;
        int n3 = 0;
        boolean bl = false;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        boolean bl2 = false;
        LinkedList<String> linkedList = null;
        ArrayList<TimeZoneRule> arrayList = new ArrayList<TimeZoneRule>();
        int n4 = 0;
        int n5 = 0;
        long l = Long.MAX_VALUE;
        for (String object2 : this.vtzlines) {
            n = object2.indexOf(COLON);
            if (n < 0) continue;
            String n9 = object2.substring(0, n);
            String i = object2.substring(n + 1);
            switch (n3) {
                case 0: {
                    if (!n9.equals(ICAL_BEGIN) || !i.equals(ICAL_VTIMEZONE)) break;
                    n3 = 1;
                    break;
                }
                case 1: {
                    if (n9.equals(ICAL_TZID)) {
                        string = i;
                        break;
                    }
                    if (n9.equals(ICAL_TZURL)) {
                        this.tzurl = i;
                        break;
                    }
                    if (n9.equals(ICAL_LASTMOD)) {
                        this.lastmod = new Date(VTimeZone.parseDateTimeString(i, 0));
                        break;
                    }
                    if (n9.equals(ICAL_BEGIN)) {
                        boolean stringTokenizer = i.equals(ICAL_DAYLIGHT);
                        if (i.equals(ICAL_STANDARD) || stringTokenizer) {
                            if (string == null) {
                                n3 = 3;
                                break;
                            }
                            linkedList = null;
                            bl2 = false;
                            string2 = null;
                            string3 = null;
                            string4 = null;
                            bl = stringTokenizer;
                            n3 = 2;
                            break;
                        }
                        n3 = 3;
                        break;
                    }
                    if (!n9.equals(ICAL_END)) break;
                    break;
                }
                case 2: {
                    if (n9.equals(ICAL_DTSTART)) {
                        string5 = i;
                        break;
                    }
                    if (n9.equals(ICAL_TZNAME)) {
                        string4 = i;
                        break;
                    }
                    if (n9.equals(ICAL_TZOFFSETFROM)) {
                        string2 = i;
                        break;
                    }
                    if (n9.equals(ICAL_TZOFFSETTO)) {
                        string3 = i;
                        break;
                    }
                    if (n9.equals(ICAL_RDATE)) {
                        if (bl2) {
                            n3 = 3;
                            break;
                        }
                        if (linkedList == null) {
                            linkedList = new LinkedList<String>();
                        }
                        StringTokenizer timeZoneRule = new StringTokenizer(i, COMMA);
                        while (timeZoneRule.hasMoreTokens()) {
                            String n6 = timeZoneRule.nextToken();
                            linkedList.add(n6);
                        }
                        break;
                    }
                    if (n9.equals(ICAL_RRULE)) {
                        if (!bl2 && linkedList != null) {
                            n3 = 3;
                            break;
                        }
                        if (linkedList == null) {
                            linkedList = new LinkedList();
                        }
                        bl2 = true;
                        linkedList.add(i);
                        break;
                    }
                    if (!n9.equals(ICAL_END)) break;
                    if (string5 == null || string2 == null || string3 == null) {
                        n3 = 3;
                        break;
                    }
                    if (string4 == null) {
                        string4 = VTimeZone.getDefaultTZName(string, bl);
                    }
                    TimeZoneRule timeZoneRule = null;
                    int n11 = 0;
                    int date = 0;
                    int date2 = 0;
                    timeZoneRule3 = 0;
                    long l2 = 0L;
                    try {
                        n11 = VTimeZone.offsetStrToMillis(string2);
                        date = VTimeZone.offsetStrToMillis(string3);
                        if (bl) {
                            if (date - n11 > 0) {
                                date2 = n11;
                                timeZoneRule3 = date - n11;
                            } else {
                                date2 = date - 3600000;
                                timeZoneRule3 = 3600000;
                            }
                        } else {
                            date2 = date;
                            timeZoneRule3 = 0;
                        }
                        l2 = VTimeZone.parseDateTimeString(string5, n11);
                        Date illegalArgumentException = null;
                        timeZoneRule = bl2 ? VTimeZone.createRuleByRRULE(string4, date2, timeZoneRule3, l2, linkedList, n11) : VTimeZone.createRuleByRDATE(string4, date2, timeZoneRule3, l2, linkedList, n11);
                        if (timeZoneRule != null && (illegalArgumentException = timeZoneRule.getFirstStart(n11, 0)).getTime() < l) {
                            l = illegalArgumentException.getTime();
                            if (timeZoneRule3 > 0) {
                                n4 = n11;
                                n5 = 0;
                            } else if (n11 - date == 3600000) {
                                n4 = n11 - 3600000;
                                n5 = 3600000;
                            } else {
                                n4 = n11;
                                n5 = 0;
                            }
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        // empty catch block
                    }
                    if (timeZoneRule == null) {
                        n3 = 3;
                        break;
                    }
                    arrayList.add(timeZoneRule);
                    n3 = 1;
                }
            }
            if (n3 != 3) continue;
            this.vtzlines = null;
            return true;
        }
        if (arrayList.size() == 0) {
            return true;
        }
        InitialTimeZoneRule initialTimeZoneRule = new InitialTimeZoneRule(VTimeZone.getDefaultTZName(string, false), n4, n5);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(string, initialTimeZoneRule);
        n = -1;
        int n2 = 0;
        for (int annualTimeZoneRule = 0; annualTimeZoneRule < arrayList.size(); ++annualTimeZoneRule) {
            TimeZoneRule n10 = (TimeZoneRule)arrayList.get(annualTimeZoneRule);
            if (!(n10 instanceof AnnualTimeZoneRule) || ((AnnualTimeZoneRule)n10).getEndYear() != Integer.MAX_VALUE) continue;
            ++n2;
            n = annualTimeZoneRule;
        }
        if (n2 > 2) {
            return true;
        }
        if (n2 == 1) {
            if (arrayList.size() == 1) {
                arrayList.clear();
            } else {
                TimeZoneRule timeZoneRule;
                Date date;
                AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)arrayList.get(n);
                int timeZoneRule2 = annualTimeZoneRule.getRawOffset();
                int n6 = annualTimeZoneRule.getDSTSavings();
                Date date2 = date = annualTimeZoneRule.getFirstStart(n4, n5);
                for (timeZoneRule3 = 0; timeZoneRule3 < arrayList.size(); ++timeZoneRule3) {
                    TimeZoneRule nArray;
                    Date date3;
                    if (n == timeZoneRule3 || !(date3 = (nArray = (TimeZoneRule)arrayList.get(timeZoneRule3)).getFinalStart(timeZoneRule2, n6)).after(date2)) continue;
                    date2 = annualTimeZoneRule.getNextStart(date3.getTime(), nArray.getRawOffset(), nArray.getDSTSavings(), true);
                }
                if (date2 == date) {
                    timeZoneRule = new TimeArrayTimeZoneRule(annualTimeZoneRule.getName(), annualTimeZoneRule.getRawOffset(), annualTimeZoneRule.getDSTSavings(), new long[]{date.getTime()}, 2);
                } else {
                    int[] nArray = Grego.timeToFields(date2.getTime(), null);
                    timeZoneRule = new AnnualTimeZoneRule(annualTimeZoneRule.getName(), annualTimeZoneRule.getRawOffset(), annualTimeZoneRule.getDSTSavings(), annualTimeZoneRule.getRule(), annualTimeZoneRule.getStartYear(), nArray[0]);
                }
                arrayList.set(n, timeZoneRule);
            }
        }
        for (TimeZoneRule timeZoneRule : arrayList) {
            ruleBasedTimeZone.addTransitionRule(timeZoneRule);
        }
        this.tz = ruleBasedTimeZone;
        this.setID(string);
        return false;
    }

    private static String getDefaultTZName(String string, boolean bl) {
        if (bl) {
            return string + "(DST)";
        }
        return string + "(STD)";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static TimeZoneRule createRuleByRRULE(String string, int n, int n2, long l, List<String> list, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        long[] lArray;
        if (list == null || list.size() == 0) {
            return null;
        }
        String string2 = list.get(0);
        int[] nArray = VTimeZone.parseRRULE(string2, lArray = new long[1]);
        if (nArray == null) {
            return null;
        }
        int n8 = nArray[0];
        int n9 = nArray[1];
        int n10 = nArray[2];
        int n11 = nArray[3];
        if (list.size() == 1) {
            if (nArray.length > 4) {
                if (nArray.length != 10 || n8 == -1 || n9 == 0) {
                    return null;
                }
                n7 = 31;
                int[] nArray2 = new int[7];
                for (n6 = 0; n6 < 7; ++n6) {
                    nArray2[n6] = nArray[3 + n6];
                    nArray2[n6] = nArray2[n6] > 0 ? nArray2[n6] : MONTHLENGTH[n8] + nArray2[n6] + 1;
                    n7 = nArray2[n6] < n7 ? nArray2[n6] : n7;
                }
                for (n6 = 1; n6 < 7; ++n6) {
                    n5 = 0;
                    for (n4 = 0; n4 < 7; ++n4) {
                        if (nArray2[n4] != n7 + n6) continue;
                        n5 = 1;
                        break;
                    }
                    if (n5 != 0) continue;
                    return null;
                }
                n11 = n7;
            }
        } else {
            if (n8 == -1 || n9 == 0 || n11 == 0) {
                return null;
            }
            if (list.size() > 7) {
                return null;
            }
            n7 = n8;
            int n12 = nArray.length - 3;
            n6 = 31;
            for (n5 = 0; n5 < n12; ++n5) {
                n4 = nArray[3 + n5];
                n4 = n4 > 0 ? n4 : MONTHLENGTH[n8] + n4 + 1;
                n6 = n4 < n6 ? n4 : n6;
            }
            n5 = -1;
            for (n4 = 1; n4 < list.size(); ++n4) {
                int n13;
                string2 = list.get(n4);
                long[] lArray2 = new long[1];
                int[] nArray3 = VTimeZone.parseRRULE(string2, lArray2);
                if (lArray2[0] > lArray[0]) {
                    lArray = lArray2;
                }
                if (nArray3[0] == -1 || nArray3[1] == 0 || nArray3[3] == 0) {
                    return null;
                }
                int n14 = nArray3.length - 3;
                if (n12 + n14 > 7) {
                    return null;
                }
                if (nArray3[1] != n9) {
                    return null;
                }
                if (nArray3[0] != n8) {
                    if (n5 == -1) {
                        n13 = nArray3[0] - n8;
                        if (n13 == -11 || n13 == -1) {
                            n7 = n5 = nArray3[0];
                            n6 = 31;
                        } else {
                            if (n13 != 11 && n13 != 1) return null;
                            n5 = nArray3[0];
                        }
                    } else if (nArray3[0] != n8 && nArray3[0] != n5) {
                        return null;
                    }
                }
                if (nArray3[0] == n7) {
                    for (n13 = 0; n13 < n14; ++n13) {
                        int n15 = nArray3[3 + n13];
                        n15 = n15 > 0 ? n15 : MONTHLENGTH[nArray3[0]] + n15 + 1;
                        n6 = n15 < n6 ? n15 : n6;
                    }
                }
                n12 += n14;
            }
            if (n12 != 7) {
                return null;
            }
            n8 = n7;
            n11 = n6;
        }
        int[] nArray4 = Grego.timeToFields(l + (long)n3, null);
        int n16 = nArray4[0];
        if (n8 == -1) {
            n8 = nArray4[1];
        }
        if (n9 == 0 && n10 == 0 && n11 == 0) {
            n11 = nArray4[2];
        }
        n6 = nArray4[5];
        n5 = Integer.MAX_VALUE;
        if (lArray[0] != Long.MIN_VALUE) {
            Grego.timeToFields(lArray[0], nArray4);
            n5 = nArray4[0];
        }
        DateTimeRule dateTimeRule = null;
        if (n9 == 0 && n10 == 0 && n11 != 0) {
            dateTimeRule = new DateTimeRule(n8, n11, n6, 0);
            return new AnnualTimeZoneRule(string, n, n2, dateTimeRule, n16, n5);
        } else if (n9 != 0 && n10 != 0 && n11 == 0) {
            dateTimeRule = new DateTimeRule(n8, n10, n9, n6, 0);
            return new AnnualTimeZoneRule(string, n, n2, dateTimeRule, n16, n5);
        } else {
            if (n9 == 0 || n10 != 0 || n11 == 0) return null;
            dateTimeRule = new DateTimeRule(n8, n11, n9, true, n6, 0);
        }
        return new AnnualTimeZoneRule(string, n, n2, dateTimeRule, n16, n5);
    }

    private static int[] parseRRULE(String string, long[] lArray) {
        Object object;
        int n = -1;
        int n2 = 0;
        int n3 = 0;
        int[] nArray = null;
        long l = Long.MIN_VALUE;
        boolean bl = false;
        boolean bl2 = false;
        StringTokenizer stringTokenizer = new StringTokenizer(string, SEMICOLON);
        block8: while (stringTokenizer.hasMoreTokens()) {
            int n4;
            int n5;
            String string2 = stringTokenizer.nextToken();
            int n6 = string2.indexOf(EQUALS_SIGN);
            if (n6 == -1) {
                bl2 = true;
                break;
            }
            object = string2.substring(0, n6);
            String string3 = string2.substring(n6 + 1);
            if (((String)object).equals(ICAL_FREQ)) {
                if (string3.equals(ICAL_YEARLY)) {
                    bl = true;
                    continue;
                }
                bl2 = true;
                break;
            }
            if (((String)object).equals(ICAL_UNTIL)) {
                try {
                    l = VTimeZone.parseDateTimeString(string3, 0);
                    continue;
                } catch (IllegalArgumentException illegalArgumentException) {
                    bl2 = true;
                    break;
                }
            }
            if (((String)object).equals(ICAL_BYMONTH)) {
                if (string3.length() > 2) {
                    bl2 = true;
                    break;
                }
                try {
                    n = Integer.parseInt(string3) - 1;
                    if (n >= 0 && n < 12) continue;
                    bl2 = true;
                } catch (NumberFormatException numberFormatException) {
                    bl2 = true;
                }
                break;
            }
            if (((String)object).equals(ICAL_BYDAY)) {
                int n7 = string3.length();
                if (n7 < 2 || n7 > 4) {
                    bl2 = true;
                    break;
                }
                if (n7 > 2) {
                    n5 = 1;
                    if (string3.charAt(0) == '+') {
                        n5 = 1;
                    } else if (string3.charAt(0) == '-') {
                        n5 = -1;
                    } else if (n7 == 4) {
                        bl2 = true;
                        break;
                    }
                    try {
                        n4 = Integer.parseInt(string3.substring(n7 - 3, n7 - 2));
                        if (n4 == 0 || n4 > 4) {
                            bl2 = true;
                            break;
                        }
                        n3 = n4 * n5;
                    } catch (NumberFormatException numberFormatException) {
                        bl2 = true;
                        break;
                    }
                    string3 = string3.substring(n7 - 2);
                }
                for (n5 = 0; n5 < ICAL_DOW_NAMES.length && !string3.equals(ICAL_DOW_NAMES[n5]); ++n5) {
                }
                if (n5 < ICAL_DOW_NAMES.length) {
                    n2 = n5 + 1;
                    continue;
                }
                bl2 = true;
                break;
            }
            if (!((String)object).equals(ICAL_BYMONTHDAY)) continue;
            StringTokenizer stringTokenizer2 = new StringTokenizer(string3, COMMA);
            n5 = stringTokenizer2.countTokens();
            nArray = new int[n5];
            n4 = 0;
            while (stringTokenizer2.hasMoreTokens()) {
                try {
                    nArray[n4++] = Integer.parseInt(stringTokenizer2.nextToken());
                } catch (NumberFormatException numberFormatException) {
                    bl2 = true;
                    continue block8;
                }
            }
        }
        if (bl2) {
            return null;
        }
        if (!bl) {
            return null;
        }
        lArray[0] = l;
        if (nArray == null) {
            object = new int[4];
            object[3] = 0;
        } else {
            object = new int[3 + nArray.length];
            for (int i = 0; i < nArray.length; ++i) {
                object[3 + i] = nArray[i];
            }
        }
        object[0] = n;
        object[1] = n2;
        object[2] = n3;
        return object;
    }

    private static TimeZoneRule createRuleByRDATE(String string, int n, int n2, long l, List<String> list, int n3) {
        long[] lArray;
        if (list == null || list.size() == 0) {
            lArray = new long[]{l};
        } else {
            lArray = new long[list.size()];
            int n4 = 0;
            try {
                for (String string2 : list) {
                    lArray[n4++] = VTimeZone.parseDateTimeString(string2, n3);
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return new TimeArrayTimeZoneRule(string, n, n2, lArray, 2);
    }

    private void writeZone(Writer writer, BasicTimeZone basicTimeZone, String[] stringArray) throws IOException {
        Object object;
        this.writeHeader(writer);
        if (stringArray != null && stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; ++i) {
                if (stringArray[i] == null) continue;
                writer.write(stringArray[i]);
                writer.write(NEWLINE);
            }
        }
        long l = Long.MIN_VALUE;
        String string = null;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        long l2 = 0L;
        long l3 = 0L;
        int n9 = 0;
        AnnualTimeZoneRule annualTimeZoneRule = null;
        String string2 = null;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = 0;
        long l4 = 0L;
        long l5 = 0L;
        int n18 = 0;
        AnnualTimeZoneRule annualTimeZoneRule2 = null;
        int[] nArray = new int[6];
        boolean bl = false;
        while ((object = basicTimeZone.getNextTransition(l, true)) != null) {
            bl = true;
            l = ((TimeZoneTransition)object).getTime();
            String string3 = ((TimeZoneTransition)object).getTo().getName();
            boolean bl2 = ((TimeZoneTransition)object).getTo().getDSTSavings() != 0;
            int n19 = ((TimeZoneTransition)object).getFrom().getRawOffset() + ((TimeZoneTransition)object).getFrom().getDSTSavings();
            int n20 = ((TimeZoneTransition)object).getFrom().getDSTSavings();
            int n21 = ((TimeZoneTransition)object).getTo().getRawOffset() + ((TimeZoneTransition)object).getTo().getDSTSavings();
            Grego.timeToFields(((TimeZoneTransition)object).getTime() + (long)n19, nArray);
            int n22 = Grego.getDayOfWeekInMonth(nArray[0], nArray[1], nArray[2]);
            int n23 = nArray[0];
            boolean bl3 = false;
            if (bl2) {
                if (annualTimeZoneRule == null && ((TimeZoneTransition)object).getTo() instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)((TimeZoneTransition)object).getTo()).getEndYear() == Integer.MAX_VALUE) {
                    annualTimeZoneRule = (AnnualTimeZoneRule)((TimeZoneTransition)object).getTo();
                }
                if (n9 > 0) {
                    if (n23 == n4 + n9 && string3.equals(string) && n == n19 && n3 == n21 && n5 == nArray[1] && n6 == nArray[3] && n7 == n22 && n8 == nArray[5]) {
                        l3 = l;
                        ++n9;
                        bl3 = true;
                    }
                    if (!bl3) {
                        if (n9 == 1) {
                            VTimeZone.writeZonePropsByTime(writer, true, string, n, n3, l2, true);
                        } else {
                            VTimeZone.writeZonePropsByDOW(writer, true, string, n, n3, n5, n7, n6, l2, l3);
                        }
                    }
                }
                if (!bl3) {
                    string = string3;
                    n = n19;
                    n2 = n20;
                    n3 = n21;
                    n4 = n23;
                    n5 = nArray[1];
                    n6 = nArray[3];
                    n7 = n22;
                    n8 = nArray[5];
                    l2 = l3 = l;
                    n9 = 1;
                }
                if (annualTimeZoneRule2 == null || annualTimeZoneRule == null) continue;
                break;
            }
            if (annualTimeZoneRule2 == null && ((TimeZoneTransition)object).getTo() instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)((TimeZoneTransition)object).getTo()).getEndYear() == Integer.MAX_VALUE) {
                annualTimeZoneRule2 = (AnnualTimeZoneRule)((TimeZoneTransition)object).getTo();
            }
            if (n18 > 0) {
                if (n23 == n13 + n18 && string3.equals(string2) && n10 == n19 && n12 == n21 && n14 == nArray[1] && n15 == nArray[3] && n16 == n22 && n17 == nArray[5]) {
                    l5 = l;
                    ++n18;
                    bl3 = true;
                }
                if (!bl3) {
                    if (n18 == 1) {
                        VTimeZone.writeZonePropsByTime(writer, false, string2, n10, n12, l4, true);
                    } else {
                        VTimeZone.writeZonePropsByDOW(writer, false, string2, n10, n12, n14, n16, n15, l4, l5);
                    }
                }
            }
            if (!bl3) {
                string2 = string3;
                n10 = n19;
                n11 = n20;
                n12 = n21;
                n13 = n23;
                n14 = nArray[1];
                n15 = nArray[3];
                n16 = n22;
                n17 = nArray[5];
                l4 = l5 = l;
                n18 = 1;
            }
            if (annualTimeZoneRule2 == null || annualTimeZoneRule == null) continue;
            break;
        }
        if (!bl) {
            int n24 = basicTimeZone.getOffset(0L);
            boolean bl4 = n24 != basicTimeZone.getRawOffset();
            VTimeZone.writeZonePropsByTime(writer, bl4, VTimeZone.getDefaultTZName(basicTimeZone.getID(), bl4), n24, n24, 0L - (long)n24, false);
        } else {
            if (n9 > 0) {
                if (annualTimeZoneRule == null) {
                    if (n9 == 1) {
                        VTimeZone.writeZonePropsByTime(writer, true, string, n, n3, l2, true);
                    } else {
                        VTimeZone.writeZonePropsByDOW(writer, true, string, n, n3, n5, n7, n6, l2, l3);
                    }
                } else if (n9 == 1) {
                    VTimeZone.writeFinalRule(writer, true, annualTimeZoneRule, n - n2, n2, l2);
                } else if (VTimeZone.isEquivalentDateRule(n5, n7, n6, annualTimeZoneRule.getRule())) {
                    VTimeZone.writeZonePropsByDOW(writer, true, string, n, n3, n5, n7, n6, l2, Long.MAX_VALUE);
                } else {
                    VTimeZone.writeZonePropsByDOW(writer, true, string, n, n3, n5, n7, n6, l2, l3);
                    object = annualTimeZoneRule.getNextStart(l3, n - n2, n2, true);
                    if (!$assertionsDisabled && object == null) {
                        throw new AssertionError();
                    }
                    if (object != null) {
                        VTimeZone.writeFinalRule(writer, true, annualTimeZoneRule, n - n2, n2, ((Date)object).getTime());
                    }
                }
            }
            if (n18 > 0) {
                if (annualTimeZoneRule2 == null) {
                    if (n18 == 1) {
                        VTimeZone.writeZonePropsByTime(writer, false, string2, n10, n12, l4, true);
                    } else {
                        VTimeZone.writeZonePropsByDOW(writer, false, string2, n10, n12, n14, n16, n15, l4, l5);
                    }
                } else if (n18 == 1) {
                    VTimeZone.writeFinalRule(writer, false, annualTimeZoneRule2, n10 - n11, n11, l4);
                } else if (VTimeZone.isEquivalentDateRule(n14, n16, n15, annualTimeZoneRule2.getRule())) {
                    VTimeZone.writeZonePropsByDOW(writer, false, string2, n10, n12, n14, n16, n15, l4, Long.MAX_VALUE);
                } else {
                    VTimeZone.writeZonePropsByDOW(writer, false, string2, n10, n12, n14, n16, n15, l4, l5);
                    object = annualTimeZoneRule2.getNextStart(l5, n10 - n11, n11, true);
                    if (!$assertionsDisabled && object == null) {
                        throw new AssertionError();
                    }
                    if (object != null) {
                        VTimeZone.writeFinalRule(writer, false, annualTimeZoneRule2, n10 - n11, n11, ((Date)object).getTime());
                    }
                }
            }
        }
        VTimeZone.writeFooter(writer);
    }

    private static boolean isEquivalentDateRule(int n, int n2, int n3, DateTimeRule dateTimeRule) {
        if (n != dateTimeRule.getRuleMonth() || n3 != dateTimeRule.getRuleDayOfWeek()) {
            return true;
        }
        if (dateTimeRule.getTimeRuleType() != 0) {
            return true;
        }
        if (dateTimeRule.getDateRuleType() == 1 && dateTimeRule.getRuleWeekInMonth() == n2) {
            return false;
        }
        int n4 = dateTimeRule.getRuleDayOfMonth();
        if (dateTimeRule.getDateRuleType() == 2) {
            if (n4 % 7 == 1 && (n4 + 6) / 7 == n2) {
                return false;
            }
            if (n != 1 && (MONTHLENGTH[n] - n4) % 7 == 6 && n2 == -1 * ((MONTHLENGTH[n] - n4 + 1) / 7)) {
                return false;
            }
        }
        if (dateTimeRule.getDateRuleType() == 3) {
            if (n4 % 7 == 0 && n4 / 7 == n2) {
                return false;
            }
            if (n != 1 && (MONTHLENGTH[n] - n4) % 7 == 0 && n2 == -1 * ((MONTHLENGTH[n] - n4) / 7 + 1)) {
                return false;
            }
        }
        return true;
    }

    private static void writeZonePropsByTime(Writer writer, boolean bl, String string, int n, int n2, long l, boolean bl2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        if (bl2) {
            writer.write(ICAL_RDATE);
            writer.write(COLON);
            writer.write(VTimeZone.getDateTimeString(l + (long)n));
            writer.write(NEWLINE);
        }
        VTimeZone.endZoneProps(writer, bl);
    }

    private static void writeZonePropsByDOM(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, long l, long l2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        VTimeZone.beginRRULE(writer, n3);
        writer.write(ICAL_BYMONTHDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n4));
        if (l2 != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString(l2 + (long)n));
        }
        writer.write(NEWLINE);
        VTimeZone.endZoneProps(writer, bl);
    }

    private static void writeZonePropsByDOW(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, int n5, long l, long l2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        VTimeZone.beginRRULE(writer, n3);
        writer.write(ICAL_BYDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n4));
        writer.write(ICAL_DOW_NAMES[n5 - 1]);
        if (l2 != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString(l2 + (long)n));
        }
        writer.write(NEWLINE);
        VTimeZone.endZoneProps(writer, bl);
    }

    private static void writeZonePropsByDOW_GEQ_DOM(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, int n5, long l, long l2) throws IOException {
        if (n4 % 7 == 1) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, (n4 + 6) / 7, n5, l, l2);
        } else if (n3 != 1 && (MONTHLENGTH[n3] - n4) % 7 == 6) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, -1 * ((MONTHLENGTH[n3] - n4 + 1) / 7), n5, l, l2);
        } else {
            VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
            int n6 = n4;
            int n7 = 7;
            if (n4 <= 0) {
                int n8 = 1 - n4;
                n7 -= n8;
                int n9 = n3 - 1 < 0 ? 11 : n3 - 1;
                VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(writer, n9, -n8, n5, n8, Long.MAX_VALUE, n);
                n6 = 1;
            } else if (n4 + 6 > MONTHLENGTH[n3]) {
                int n10 = n4 + 6 - MONTHLENGTH[n3];
                n7 -= n10;
                int n11 = n3 + 1 > 11 ? 0 : n3 + 1;
                VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(writer, n11, 1, n5, n10, Long.MAX_VALUE, n);
            }
            VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(writer, n3, n6, n5, n7, l2, n);
            VTimeZone.endZoneProps(writer, bl);
        }
    }

    private static void writeZonePropsByDOW_GEQ_DOM_sub(Writer writer, int n, int n2, int n3, int n4, long l, int n5) throws IOException {
        boolean bl;
        int n6 = n2;
        boolean bl2 = bl = n == 1;
        if (n2 < 0 && !bl) {
            n6 = MONTHLENGTH[n] + n2 + 1;
        }
        VTimeZone.beginRRULE(writer, n);
        writer.write(ICAL_BYDAY);
        writer.write(EQUALS_SIGN);
        writer.write(ICAL_DOW_NAMES[n3 - 1]);
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTHDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n6));
        for (int i = 1; i < n4; ++i) {
            writer.write(COMMA);
            writer.write(Integer.toString(n6 + i));
        }
        if (l != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString(l + (long)n5));
        }
        writer.write(NEWLINE);
    }

    private static void writeZonePropsByDOW_LEQ_DOM(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, int n5, long l, long l2) throws IOException {
        if (n4 % 7 == 0) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, n4 / 7, n5, l, l2);
        } else if (n3 != 1 && (MONTHLENGTH[n3] - n4) % 7 == 0) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, -1 * ((MONTHLENGTH[n3] - n4) / 7 + 1), n5, l, l2);
        } else if (n3 == 1 && n4 == 29) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, 1, -1, n5, l, l2);
        } else {
            VTimeZone.writeZonePropsByDOW_GEQ_DOM(writer, bl, string, n, n2, n3, n4 - 6, n5, l, l2);
        }
    }

    private static void writeFinalRule(Writer writer, boolean bl, AnnualTimeZoneRule annualTimeZoneRule, int n, int n2, long l) throws IOException {
        DateTimeRule dateTimeRule = VTimeZone.toWallTimeRule(annualTimeZoneRule.getRule(), n, n2);
        int n3 = dateTimeRule.getRuleMillisInDay();
        if (n3 < 0) {
            l += (long)(0 - n3);
        } else if (n3 >= 86400000) {
            l -= (long)(n3 - 86399999);
        }
        int n4 = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
        switch (dateTimeRule.getDateRuleType()) {
            case 0: {
                VTimeZone.writeZonePropsByDOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), l, Long.MAX_VALUE);
                break;
            }
            case 1: {
                VTimeZone.writeZonePropsByDOW(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleWeekInMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
                break;
            }
            case 2: {
                VTimeZone.writeZonePropsByDOW_GEQ_DOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
                break;
            }
            case 3: {
                VTimeZone.writeZonePropsByDOW_LEQ_DOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
            }
        }
    }

    private static DateTimeRule toWallTimeRule(DateTimeRule dateTimeRule, int n, int n2) {
        if (dateTimeRule.getTimeRuleType() == 0) {
            return dateTimeRule;
        }
        int n3 = dateTimeRule.getRuleMillisInDay();
        if (dateTimeRule.getTimeRuleType() == 2) {
            n3 += n + n2;
        } else if (dateTimeRule.getTimeRuleType() == 1) {
            n3 += n2;
        }
        int n4 = -1;
        int n5 = 0;
        int n6 = 0;
        int n7 = -1;
        int n8 = 0;
        if (n3 < 0) {
            n8 = -1;
            n3 += 86400000;
        } else if (n3 >= 86400000) {
            n8 = 1;
            n3 -= 86400000;
        }
        n4 = dateTimeRule.getRuleMonth();
        n5 = dateTimeRule.getRuleDayOfMonth();
        n6 = dateTimeRule.getRuleDayOfWeek();
        n7 = dateTimeRule.getDateRuleType();
        if (n8 != 0) {
            if (n7 == 1) {
                int n9 = dateTimeRule.getRuleWeekInMonth();
                if (n9 > 0) {
                    n7 = 2;
                    n5 = 7 * (n9 - 1) + 1;
                } else {
                    n7 = 3;
                    n5 = MONTHLENGTH[n4] + 7 * (n9 + 1);
                }
            }
            if ((n5 += n8) == 0) {
                n4 = --n4 < 0 ? 11 : n4;
                n5 = MONTHLENGTH[n4];
            } else if (n5 > MONTHLENGTH[n4]) {
                n4 = ++n4 > 11 ? 0 : n4;
                n5 = 1;
            }
            if (n7 != 0) {
                if ((n6 += n8) < 1) {
                    n6 = 7;
                } else if (n6 > 7) {
                    n6 = 1;
                }
            }
        }
        DateTimeRule dateTimeRule2 = n7 == 0 ? new DateTimeRule(n4, n5, n3, 0) : new DateTimeRule(n4, n5, n6, n7 == 2, n3, 0);
        return dateTimeRule2;
    }

    private static void beginZoneProps(Writer writer, boolean bl, String string, int n, int n2, long l) throws IOException {
        writer.write(ICAL_BEGIN);
        writer.write(COLON);
        if (bl) {
            writer.write(ICAL_DAYLIGHT);
        } else {
            writer.write(ICAL_STANDARD);
        }
        writer.write(NEWLINE);
        writer.write(ICAL_TZOFFSETTO);
        writer.write(COLON);
        writer.write(VTimeZone.millisToOffset(n2));
        writer.write(NEWLINE);
        writer.write(ICAL_TZOFFSETFROM);
        writer.write(COLON);
        writer.write(VTimeZone.millisToOffset(n));
        writer.write(NEWLINE);
        writer.write(ICAL_TZNAME);
        writer.write(COLON);
        writer.write(string);
        writer.write(NEWLINE);
        writer.write(ICAL_DTSTART);
        writer.write(COLON);
        writer.write(VTimeZone.getDateTimeString(l + (long)n));
        writer.write(NEWLINE);
    }

    private static void endZoneProps(Writer writer, boolean bl) throws IOException {
        writer.write(ICAL_END);
        writer.write(COLON);
        if (bl) {
            writer.write(ICAL_DAYLIGHT);
        } else {
            writer.write(ICAL_STANDARD);
        }
        writer.write(NEWLINE);
    }

    private static void beginRRULE(Writer writer, int n) throws IOException {
        writer.write(ICAL_RRULE);
        writer.write(COLON);
        writer.write(ICAL_FREQ);
        writer.write(EQUALS_SIGN);
        writer.write(ICAL_YEARLY);
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTH);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n + 1));
        writer.write(SEMICOLON);
    }

    private static void appendUNTIL(Writer writer, String string) throws IOException {
        if (string != null) {
            writer.write(SEMICOLON);
            writer.write(ICAL_UNTIL);
            writer.write(EQUALS_SIGN);
            writer.write(string);
        }
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write(ICAL_BEGIN);
        writer.write(COLON);
        writer.write(ICAL_VTIMEZONE);
        writer.write(NEWLINE);
        writer.write(ICAL_TZID);
        writer.write(COLON);
        writer.write(this.tz.getID());
        writer.write(NEWLINE);
        if (this.tzurl != null) {
            writer.write(ICAL_TZURL);
            writer.write(COLON);
            writer.write(this.tzurl);
            writer.write(NEWLINE);
        }
        if (this.lastmod != null) {
            writer.write(ICAL_LASTMOD);
            writer.write(COLON);
            writer.write(VTimeZone.getUTCDateTimeString(this.lastmod.getTime()));
            writer.write(NEWLINE);
        }
    }

    private static void writeFooter(Writer writer) throws IOException {
        writer.write(ICAL_END);
        writer.write(COLON);
        writer.write(ICAL_VTIMEZONE);
        writer.write(NEWLINE);
    }

    private static String getDateTimeString(long l) {
        int[] nArray = Grego.timeToFields(l, null);
        StringBuilder stringBuilder = new StringBuilder(15);
        stringBuilder.append(VTimeZone.numToString(nArray[0], 4));
        stringBuilder.append(VTimeZone.numToString(nArray[1] + 1, 2));
        stringBuilder.append(VTimeZone.numToString(nArray[2], 2));
        stringBuilder.append('T');
        int n = nArray[5];
        int n2 = n / 3600000;
        int n3 = (n %= 3600000) / 60000;
        int n4 = (n %= 60000) / 1000;
        stringBuilder.append(VTimeZone.numToString(n2, 2));
        stringBuilder.append(VTimeZone.numToString(n3, 2));
        stringBuilder.append(VTimeZone.numToString(n4, 2));
        return stringBuilder.toString();
    }

    private static String getUTCDateTimeString(long l) {
        return VTimeZone.getDateTimeString(l) + "Z";
    }

    private static long parseDateTimeString(String string, int n) {
        boolean bl;
        boolean bl2;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        block6: {
            block7: {
                int n8;
                n7 = 0;
                n6 = 0;
                n5 = 0;
                n4 = 0;
                n3 = 0;
                n2 = 0;
                bl2 = false;
                bl = false;
                if (string == null || (n8 = string.length()) != 15 && n8 != 16 || string.charAt(8) != 'T') break block6;
                if (n8 != 16) break block7;
                if (string.charAt(15) != 'Z') break block6;
                bl2 = true;
            }
            try {
                n7 = Integer.parseInt(string.substring(0, 4));
                n6 = Integer.parseInt(string.substring(4, 6)) - 1;
                n5 = Integer.parseInt(string.substring(6, 8));
                n4 = Integer.parseInt(string.substring(9, 11));
                n3 = Integer.parseInt(string.substring(11, 13));
                n2 = Integer.parseInt(string.substring(13, 15));
            } catch (NumberFormatException numberFormatException) {
                break block6;
            }
            int n9 = Grego.monthLength(n7, n6);
            if (n7 >= 0 && n6 >= 0 && n6 <= 11 && n5 >= 1 && n5 <= n9 && n4 >= 0 && n4 < 24 && n3 >= 0 && n3 < 60 && n2 >= 0 && n2 < 60) {
                bl = true;
            }
        }
        if (!bl) {
            throw new IllegalArgumentException("Invalid date time string format");
        }
        long l = Grego.fieldsToDay(n7, n6, n5) * 86400000L;
        l += (long)(n4 * 3600000 + n3 * 60000 + n2 * 1000);
        if (!bl2) {
            l -= (long)n;
        }
        return l;
    }

    private static int offsetStrToMillis(String string) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        boolean bl;
        block5: {
            block4: {
                block7: {
                    char c;
                    block6: {
                        bl = false;
                        n5 = 0;
                        n4 = 0;
                        n3 = 0;
                        n2 = 0;
                        if (string == null || (n = string.length()) != 5 && n != 7) break block5;
                        c = string.charAt(0);
                        if (c != '+') break block6;
                        n5 = 1;
                        break block7;
                    }
                    if (c != '-') break block5;
                    n5 = -1;
                }
                try {
                    n4 = Integer.parseInt(string.substring(1, 3));
                    n3 = Integer.parseInt(string.substring(3, 5));
                    if (n != 7) break block4;
                    n2 = Integer.parseInt(string.substring(5, 7));
                } catch (NumberFormatException numberFormatException) {
                    break block5;
                }
            }
            bl = true;
        }
        if (!bl) {
            throw new IllegalArgumentException("Bad offset string");
        }
        n = n5 * ((n4 * 60 + n3) * 60 + n2) * 1000;
        return n;
    }

    private static String millisToOffset(int n) {
        StringBuilder stringBuilder = new StringBuilder(7);
        if (n >= 0) {
            stringBuilder.append('+');
        } else {
            stringBuilder.append('-');
            n = -n;
        }
        int n2 = n / 1000;
        int n3 = n2 % 60;
        n2 = (n2 - n3) / 60;
        int n4 = n2 % 60;
        int n5 = n2 / 60;
        stringBuilder.append(VTimeZone.numToString(n5, 2));
        stringBuilder.append(VTimeZone.numToString(n4, 2));
        stringBuilder.append(VTimeZone.numToString(n3, 2));
        return stringBuilder.toString();
    }

    private static String numToString(int n, int n2) {
        String string = Integer.toString(n);
        int n3 = string.length();
        if (n3 >= n2) {
            return string.substring(n3 - n2, n3);
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        for (int i = n3; i < n2; ++i) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override
    public TimeZone cloneAsThawed() {
        VTimeZone vTimeZone = (VTimeZone)super.cloneAsThawed();
        vTimeZone.tz = (BasicTimeZone)this.tz.cloneAsThawed();
        vTimeZone.isFrozen = false;
        return vTimeZone;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static {
        $assertionsDisabled = !VTimeZone.class.desiredAssertionStatus();
        ICAL_DOW_NAMES = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
        MONTHLENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        try {
            ICU_TZVERSION = TimeZone.getTZDataVersion();
        } catch (MissingResourceException missingResourceException) {
            ICU_TZVERSION = null;
        }
    }
}

