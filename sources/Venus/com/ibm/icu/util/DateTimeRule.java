/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import java.io.Serializable;

public class DateTimeRule
implements Serializable {
    private static final long serialVersionUID = 2183055795738051443L;
    public static final int DOM = 0;
    public static final int DOW = 1;
    public static final int DOW_GEQ_DOM = 2;
    public static final int DOW_LEQ_DOM = 3;
    public static final int WALL_TIME = 0;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    private final int dateRuleType;
    private final int month;
    private final int dayOfMonth;
    private final int dayOfWeek;
    private final int weekInMonth;
    private final int timeRuleType;
    private final int millisInDay;
    private static final String[] DOWSTR = new String[]{"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] MONSTR = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DateTimeRule(int n, int n2, int n3, int n4) {
        this.dateRuleType = 0;
        this.month = n;
        this.dayOfMonth = n2;
        this.millisInDay = n3;
        this.timeRuleType = n4;
        this.dayOfWeek = 0;
        this.weekInMonth = 0;
    }

    public DateTimeRule(int n, int n2, int n3, int n4, int n5) {
        this.dateRuleType = 1;
        this.month = n;
        this.weekInMonth = n2;
        this.dayOfWeek = n3;
        this.millisInDay = n4;
        this.timeRuleType = n5;
        this.dayOfMonth = 0;
    }

    public DateTimeRule(int n, int n2, int n3, boolean bl, int n4, int n5) {
        this.dateRuleType = bl ? 2 : 3;
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = n3;
        this.millisInDay = n4;
        this.timeRuleType = n5;
        this.weekInMonth = 0;
    }

    public int getDateRuleType() {
        return this.dateRuleType;
    }

    public int getRuleMonth() {
        return this.month;
    }

    public int getRuleDayOfMonth() {
        return this.dayOfMonth;
    }

    public int getRuleDayOfWeek() {
        return this.dayOfWeek;
    }

    public int getRuleWeekInMonth() {
        return this.weekInMonth;
    }

    public int getTimeRuleType() {
        return this.timeRuleType;
    }

    public int getRuleMillisInDay() {
        return this.millisInDay;
    }

    public String toString() {
        String string = null;
        String string2 = null;
        switch (this.dateRuleType) {
            case 0: {
                string = Integer.toString(this.dayOfMonth);
                break;
            }
            case 1: {
                string = Integer.toString(this.weekInMonth) + DOWSTR[this.dayOfWeek];
                break;
            }
            case 2: {
                string = DOWSTR[this.dayOfWeek] + ">=" + Integer.toString(this.dayOfMonth);
                break;
            }
            case 3: {
                string = DOWSTR[this.dayOfWeek] + "<=" + Integer.toString(this.dayOfMonth);
            }
        }
        switch (this.timeRuleType) {
            case 0: {
                string2 = "WALL";
                break;
            }
            case 1: {
                string2 = "STD";
                break;
            }
            case 2: {
                string2 = "UTC";
            }
        }
        int n = this.millisInDay;
        int n2 = n % 1000;
        int n3 = (n /= 1000) % 60;
        int n4 = (n /= 60) % 60;
        int n5 = n / 60;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("month=");
        stringBuilder.append(MONSTR[this.month]);
        stringBuilder.append(", date=");
        stringBuilder.append(string);
        stringBuilder.append(", time=");
        stringBuilder.append(n5);
        stringBuilder.append(":");
        stringBuilder.append(n4 / 10);
        stringBuilder.append(n4 % 10);
        stringBuilder.append(":");
        stringBuilder.append(n3 / 10);
        stringBuilder.append(n3 % 10);
        stringBuilder.append(".");
        stringBuilder.append(n2 / 100);
        stringBuilder.append(n2 / 10 % 10);
        stringBuilder.append(n2 % 10);
        stringBuilder.append("(");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

