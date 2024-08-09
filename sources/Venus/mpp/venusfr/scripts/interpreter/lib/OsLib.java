/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class OsLib
extends TwoArgFunction {
    public static final String TMP_PREFIX = ".luaj";
    public static final String TMP_SUFFIX = "tmp";
    private static final int CLOCK = 0;
    private static final int DATE = 1;
    private static final int DIFFTIME = 2;
    private static final int EXECUTE = 3;
    private static final int EXIT = 4;
    private static final int GETENV = 5;
    private static final int REMOVE = 6;
    private static final int RENAME = 7;
    private static final int SETLOCALE = 8;
    private static final int TIME = 9;
    private static final int TMPNAME = 10;
    private static final String[] NAMES = new String[]{"clock", "date", "difftime", "execute", "exit", "getenv", "remove", "rename", "setlocale", "time", "tmpname"};
    private static final long t0;
    private static long tmpnames;
    protected Globals globals;
    private static final String[] WeekdayNameAbbrev;
    private static final String[] WeekdayName;
    private static final String[] MonthNameAbbrev;
    private static final String[] MonthName;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        LuaTable luaTable = new LuaTable();
        for (int i = 0; i < NAMES.length; ++i) {
            luaTable.set(NAMES[i], (LuaValue)new OsLibFunc(this, i, NAMES[i]));
        }
        luaValue2.set("os", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("os", (LuaValue)luaTable);
        }
        return luaTable;
    }

    protected double clock() {
        return (double)(System.currentTimeMillis() - t0) / 1000.0;
    }

    protected double difftime(double d, double d2) {
        return d - d2;
    }

    public String date(String string, double d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)(d * 1000.0)));
        if (string.startsWith("!")) {
            calendar.setTime(new Date((long)((d -= (double)this.timeZoneOffset(calendar)) * 1000.0)));
            string = string.substring(1);
        }
        byte[] byArray = string.getBytes();
        int n = byArray.length;
        Buffer buffer = new Buffer(n);
        int n2 = 0;
        block28: while (n2 < n) {
            int n3;
            byte by = byArray[n2++];
            switch (by) {
                case 10: {
                    buffer.append("\n");
                    continue block28;
                }
                default: {
                    buffer.append(by);
                    continue block28;
                }
                case 37: 
            }
            if (n2 >= n) continue;
            by = byArray[n2++];
            switch (by) {
                default: {
                    LuaValue.argerror(1, "invalid conversion specifier '%" + by + "'");
                    continue block28;
                }
                case 37: {
                    buffer.append((byte)37);
                    continue block28;
                }
                case 97: {
                    buffer.append(WeekdayNameAbbrev[calendar.get(7) - 1]);
                    continue block28;
                }
                case 65: {
                    buffer.append(WeekdayName[calendar.get(7) - 1]);
                    continue block28;
                }
                case 98: {
                    buffer.append(MonthNameAbbrev[calendar.get(2)]);
                    continue block28;
                }
                case 66: {
                    buffer.append(MonthName[calendar.get(2)]);
                    continue block28;
                }
                case 99: {
                    buffer.append(this.date("%a %b %d %H:%M:%S %Y", d));
                    continue block28;
                }
                case 100: {
                    buffer.append(String.valueOf(100 + calendar.get(5)).substring(1));
                    continue block28;
                }
                case 72: {
                    buffer.append(String.valueOf(100 + calendar.get(11)).substring(1));
                    continue block28;
                }
                case 73: {
                    buffer.append(String.valueOf(100 + calendar.get(11) % 12).substring(1));
                    continue block28;
                }
                case 106: {
                    Calendar calendar2 = this.beginningOfYear(calendar);
                    n3 = (int)((calendar.getTime().getTime() - calendar2.getTime().getTime()) / 86400000L);
                    buffer.append(String.valueOf(1001 + n3).substring(1));
                    continue block28;
                }
                case 109: {
                    buffer.append(String.valueOf(101 + calendar.get(2)).substring(1));
                    continue block28;
                }
                case 77: {
                    buffer.append(String.valueOf(100 + calendar.get(12)).substring(1));
                    continue block28;
                }
                case 112: {
                    buffer.append(calendar.get(11) < 12 ? "AM" : "PM");
                    continue block28;
                }
                case 83: {
                    buffer.append(String.valueOf(100 + calendar.get(13)).substring(1));
                    continue block28;
                }
                case 85: {
                    buffer.append(String.valueOf(this.weekNumber(calendar, 0)));
                    continue block28;
                }
                case 119: {
                    buffer.append(String.valueOf((calendar.get(7) + 6) % 7));
                    continue block28;
                }
                case 87: {
                    buffer.append(String.valueOf(this.weekNumber(calendar, 1)));
                    continue block28;
                }
                case 120: {
                    buffer.append(this.date("%m/%d/%y", d));
                    continue block28;
                }
                case 88: {
                    buffer.append(this.date("%H:%M:%S", d));
                    continue block28;
                }
                case 121: {
                    buffer.append(String.valueOf(calendar.get(1)).substring(2));
                    continue block28;
                }
                case 89: {
                    buffer.append(String.valueOf(calendar.get(1)));
                    continue block28;
                }
                case 122: 
            }
            int n4 = this.timeZoneOffset(calendar) / 60;
            n3 = Math.abs(n4);
            String string2 = String.valueOf(100 + n3 / 60).substring(1);
            String string3 = String.valueOf(100 + n3 % 60).substring(1);
            buffer.append((n4 >= 0 ? "+" : "-") + string2 + string3);
        }
        return buffer.tojstring();
    }

    private Calendar beginningOfYear(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        calendar2.set(2, 0);
        calendar2.set(5, 1);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2;
    }

    private int weekNumber(Calendar calendar, int n) {
        Calendar calendar2 = this.beginningOfYear(calendar);
        calendar2.set(5, 1 + (n + 8 - calendar2.get(7)) % 7);
        if (calendar2.after(calendar)) {
            calendar2.set(1, calendar2.get(1) - 1);
            calendar2.set(5, 1 + (n + 8 - calendar2.get(7)) % 7);
        }
        long l = calendar.getTime().getTime() - calendar2.getTime().getTime();
        return 1 + (int)(l / 604800000L);
    }

    private int timeZoneOffset(Calendar calendar) {
        int n = (calendar.get(11) * 3600 + calendar.get(12) * 60 + calendar.get(13)) * 1000;
        return calendar.getTimeZone().getOffset(1, calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), n) / 1000;
    }

    private boolean isDaylightSavingsTime(Calendar calendar) {
        return this.timeZoneOffset(calendar) != calendar.getTimeZone().getRawOffset() / 1000;
    }

    protected Varargs execute(String string) {
        return OsLib.varargsOf(NIL, OsLib.valueOf("exit"), ONE);
    }

    protected void exit(int n) {
        System.exit(n);
    }

    protected String getenv(String string) {
        return System.getProperty(string);
    }

    protected void remove(String string) throws IOException {
        throw new IOException("not implemented");
    }

    protected void rename(String string, String string2) throws IOException {
        throw new IOException("not implemented");
    }

    protected String setlocale(String string, String string2) {
        return "C";
    }

    protected double time(LuaTable luaTable) {
        Date date;
        if (luaTable == null) {
            date = new Date();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, luaTable.get("year").checkint());
            calendar.set(2, luaTable.get("month").checkint() - 1);
            calendar.set(5, luaTable.get("day").checkint());
            calendar.set(11, luaTable.get("hour").optint(12));
            calendar.set(12, luaTable.get("min").optint(0));
            calendar.set(13, luaTable.get("sec").optint(0));
            calendar.set(14, 0);
            date = calendar.getTime();
        }
        return (double)date.getTime() / 1000.0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String tmpname() {
        Class<OsLib> clazz = OsLib.class;
        synchronized (OsLib.class) {
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return TMP_PREFIX + tmpnames++ + TMP_SUFFIX;
        }
    }

    static {
        tmpnames = t0 = System.currentTimeMillis();
        WeekdayNameAbbrev = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        WeekdayName = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        MonthNameAbbrev = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        MonthName = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    class OsLibFunc
    extends VarArgFunction {
        final OsLib this$0;

        public OsLibFunc(OsLib osLib, int n, String string) {
            this.this$0 = osLib;
            this.opcode = n;
            this.name = string;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                switch (this.opcode) {
                    case 0: {
                        return OsLibFunc.valueOf(this.this$0.clock());
                    }
                    case 1: {
                        double d;
                        String string = varargs.optjstring(1, "%c");
                        double d2 = d = varargs.isnumber(1) ? varargs.todouble(2) : this.this$0.time(null);
                        if (string.equals("*t")) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date((long)(d * 1000.0)));
                            LuaTable luaTable = LuaValue.tableOf();
                            luaTable.set("year", (LuaValue)LuaValue.valueOf(calendar.get(1)));
                            luaTable.set("month", (LuaValue)LuaValue.valueOf(calendar.get(2) + 1));
                            luaTable.set("day", (LuaValue)LuaValue.valueOf(calendar.get(5)));
                            luaTable.set("hour", (LuaValue)LuaValue.valueOf(calendar.get(11)));
                            luaTable.set("min", (LuaValue)LuaValue.valueOf(calendar.get(12)));
                            luaTable.set("sec", (LuaValue)LuaValue.valueOf(calendar.get(13)));
                            luaTable.set("wday", (LuaValue)LuaValue.valueOf(calendar.get(7)));
                            luaTable.set("yday", (LuaValue)LuaValue.valueOf(calendar.get(6)));
                            luaTable.set("isdst", (LuaValue)LuaValue.valueOf(this.this$0.isDaylightSavingsTime(calendar)));
                            return luaTable;
                        }
                        return OsLibFunc.valueOf(this.this$0.date(string, d == -1.0 ? this.this$0.time(null) : d));
                    }
                    case 2: {
                        return OsLibFunc.valueOf(this.this$0.difftime(varargs.checkdouble(1), varargs.checkdouble(2)));
                    }
                    case 3: {
                        return this.this$0.execute(varargs.optjstring(1, null));
                    }
                    case 4: {
                        this.this$0.exit(varargs.optint(1, 0));
                        return NONE;
                    }
                    case 5: {
                        String string = this.this$0.getenv(varargs.checkjstring(1));
                        return string != null ? OsLibFunc.valueOf(string) : NIL;
                    }
                    case 6: {
                        this.this$0.remove(varargs.checkjstring(1));
                        return LuaValue.TRUE;
                    }
                    case 7: {
                        this.this$0.rename(varargs.checkjstring(1), varargs.checkjstring(2));
                        return LuaValue.TRUE;
                    }
                    case 8: {
                        String string = this.this$0.setlocale(varargs.optjstring(1, null), varargs.optjstring(2, "all"));
                        return string != null ? OsLibFunc.valueOf(string) : NIL;
                    }
                    case 9: {
                        return OsLibFunc.valueOf(this.this$0.time(varargs.opttable(1, null)));
                    }
                    case 10: {
                        return OsLibFunc.valueOf(this.this$0.tmpname());
                    }
                }
                return NONE;
            } catch (IOException iOException) {
                return OsLibFunc.varargsOf(NIL, (Varargs)OsLibFunc.valueOf(iOException.getMessage()));
            }
        }
    }
}

