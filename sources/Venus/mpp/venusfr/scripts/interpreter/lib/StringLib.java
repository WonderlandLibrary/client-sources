/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.DumpState;
import mpp.venusfr.scripts.interpreter.lib.OneArgFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class StringLib
extends TwoArgFunction {
    private static final String FLAGS = "-+ #0";
    private static final int L_ESC = 37;
    private static final LuaString SPECIALS = StringLib.valueOf("^$*+?.([%-");
    private static final int MAX_CAPTURES = 32;
    private static final int MAXCCALLS = 200;
    private static final int CAP_UNFINISHED = -1;
    private static final int CAP_POSITION = -2;
    private static final byte MASK_ALPHA = 1;
    private static final byte MASK_LOWERCASE = 2;
    private static final byte MASK_UPPERCASE = 4;
    private static final byte MASK_DIGIT = 8;
    private static final byte MASK_PUNCT = 16;
    private static final byte MASK_SPACE = 32;
    private static final byte MASK_CONTROL = 64;
    private static final byte MASK_HEXDIGIT = -128;
    static final byte[] CHAR_TABLE = new byte[256];

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable();
        luaTable.set("byte", (LuaValue)new _byte());
        luaTable.set("char", (LuaValue)new _char());
        luaTable.set("dump", (LuaValue)new dump());
        luaTable.set("find", (LuaValue)new find());
        luaTable.set("format", (LuaValue)new format(this));
        luaTable.set("gmatch", (LuaValue)new gmatch());
        luaTable.set("gsub", (LuaValue)new gsub());
        luaTable.set("len", (LuaValue)new len());
        luaTable.set("lower", (LuaValue)new lower());
        luaTable.set("match", (LuaValue)new match());
        luaTable.set("rep", (LuaValue)new rep());
        luaTable.set("reverse", (LuaValue)new reverse());
        luaTable.set("sub", (LuaValue)new sub());
        luaTable.set("upper", (LuaValue)new upper());
        luaValue2.set("string", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("string", (LuaValue)luaTable);
        }
        if (LuaString.s_metatable == null) {
            LuaString.s_metatable = LuaValue.tableOf(new LuaValue[]{INDEX, luaTable});
        }
        return luaTable;
    }

    static void addquoted(Buffer buffer, LuaString luaString) {
        buffer.append((byte)34);
        int n = luaString.length();
        block3: for (int i = 0; i < n; ++i) {
            int n2 = luaString.luaByte(i);
            switch (n2) {
                case 10: 
                case 34: 
                case 92: {
                    buffer.append((byte)92);
                    buffer.append((byte)n2);
                    continue block3;
                }
                default: {
                    if (n2 <= 31 || n2 == 127) {
                        buffer.append((byte)92);
                        if (i + 1 == n || luaString.luaByte(i + 1) < 48 || luaString.luaByte(i + 1) > 57) {
                            buffer.append(Integer.toString(n2));
                            continue block3;
                        }
                        buffer.append((byte)48);
                        buffer.append((byte)(48 + n2 / 10));
                        buffer.append((byte)(48 + n2 % 10));
                        continue block3;
                    }
                    buffer.append((byte)n2);
                }
            }
        }
        buffer.append((byte)34);
    }

    protected String format(String string, double d) {
        return String.valueOf(d);
    }

    static Varargs str_find_aux(Varargs varargs, boolean bl) {
        boolean bl2;
        LuaString luaString = varargs.checkstring(1);
        LuaString luaString2 = varargs.checkstring(2);
        int n = varargs.optint(3, 1);
        if (n > 0) {
            n = Math.min(n - 1, luaString.length());
        } else if (n < 0) {
            n = Math.max(0, luaString.length() + n);
        }
        boolean bl3 = bl2 = bl && (varargs.arg(4).toboolean() || luaString2.indexOfAny(SPECIALS) == -1);
        if (bl2) {
            int n2 = luaString.indexOf(luaString2, n);
            if (n2 != -1) {
                return StringLib.varargsOf(StringLib.valueOf(n2 + 1), (Varargs)StringLib.valueOf(n2 + luaString2.length()));
            }
        } else {
            MatchState matchState = new MatchState(varargs, luaString, luaString2);
            boolean bl4 = false;
            int n3 = 0;
            if (luaString2.length() > 0 && luaString2.luaByte(0) == 94) {
                bl4 = true;
                n3 = 1;
            }
            int n4 = n;
            do {
                matchState.reset();
                int n5 = matchState.match(n4, n3);
                if (n5 == -1) continue;
                if (bl) {
                    return StringLib.varargsOf(StringLib.valueOf(n4 + 1), StringLib.valueOf(n5), matchState.push_captures(false, n4, n5));
                }
                return matchState.push_captures(true, n4, n5);
            } while (n4++ < luaString.length() && !bl4);
        }
        return NIL;
    }

    static int posrelat(int n, int n2) {
        return n >= 0 ? n : n2 + n + 1;
    }

    static {
        for (int i = 0; i < 128; ++i) {
            char c = (char)i;
            StringLib.CHAR_TABLE[i] = (byte)((Character.isDigit(c) ? 8 : 0) | (Character.isLowerCase(c) ? 2 : 0) | (Character.isUpperCase(c) ? 4 : 0) | (c < ' ' || c == '\u007f' ? 64 : 0));
            if (c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= '0' && c <= '9') {
                int n = i;
                CHAR_TABLE[n] = (byte)(CHAR_TABLE[n] | 0xFFFFFF80);
            }
            if (c >= '!' && c <= '/' || c >= ':' && c <= '@' || c >= '[' && c <= '`' || c >= '{' && c <= '~') {
                int n = i;
                CHAR_TABLE[n] = (byte)(CHAR_TABLE[n] | 0x10);
            }
            if ((CHAR_TABLE[i] & 6) == 0) continue;
            int n = i;
            CHAR_TABLE[n] = (byte)(CHAR_TABLE[n] | 1);
        }
        StringLib.CHAR_TABLE[32] = 32;
        CHAR_TABLE[13] = (byte)(CHAR_TABLE[13] | 0x20);
        CHAR_TABLE[10] = (byte)(CHAR_TABLE[10] | 0x20);
        CHAR_TABLE[9] = (byte)(CHAR_TABLE[9] | 0x20);
        CHAR_TABLE[11] = (byte)(CHAR_TABLE[11] | 0x20);
        CHAR_TABLE[12] = (byte)(CHAR_TABLE[12] | 0x20);
    }

    static final class _byte
    extends VarArgFunction {
        _byte() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            int n = luaString.m_length;
            int n2 = StringLib.posrelat(varargs.optint(2, 1), n);
            int n3 = StringLib.posrelat(varargs.optint(3, n2), n);
            if (n2 <= 0) {
                n2 = 1;
            }
            if (n3 > n) {
                n3 = n;
            }
            if (n2 > n3) {
                return NONE;
            }
            int n4 = n3 - n2 + 1;
            if (n2 + n4 <= n3) {
                _byte.error("string slice too long");
            }
            LuaValue[] luaValueArray = new LuaValue[n4];
            for (int i = 0; i < n4; ++i) {
                luaValueArray[i] = _byte.valueOf(luaString.luaByte(n2 + i - 1));
            }
            return _byte.varargsOf(luaValueArray);
        }
    }

    static final class _char
    extends VarArgFunction {
        _char() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            int n = varargs.narg();
            byte[] byArray = new byte[n];
            int n2 = 0;
            int n3 = 1;
            while (n2 < n) {
                int n4 = varargs.checkint(n3);
                if (n4 < 0 || n4 >= 256) {
                    _char.argerror(n3, "invalid value for string.char [0; 255]: " + n4);
                }
                byArray[n2] = (byte)n4;
                ++n2;
                ++n3;
            }
            return LuaString.valueUsing(byArray);
        }
    }

    static final class dump
    extends VarArgFunction {
        dump() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaFunction luaFunction = varargs.checkfunction(1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                DumpState.dump(((LuaClosure)luaFunction).p, byteArrayOutputStream, varargs.optboolean(2, false));
                return LuaString.valueUsing(byteArrayOutputStream.toByteArray());
            } catch (IOException iOException) {
                return dump.error(iOException.getMessage());
            }
        }
    }

    static final class find
    extends VarArgFunction {
        find() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            return StringLib.str_find_aux(varargs, true);
        }
    }

    final class format
    extends VarArgFunction {
        final StringLib this$0;

        format(StringLib stringLib) {
            this.this$0 = stringLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            int n = luaString.length();
            Buffer buffer = new Buffer(n);
            int n2 = 1;
            int n3 = 0;
            block12: while (n3 < n) {
                int n4 = luaString.luaByte(n3++);
                switch (n4) {
                    case 10: {
                        buffer.append("\n");
                        continue block12;
                    }
                    default: {
                        buffer.append((byte)n4);
                        continue block12;
                    }
                    case 37: 
                }
                if (n3 >= n) continue;
                n4 = luaString.luaByte(n3);
                if (n4 == 37) {
                    ++n3;
                    buffer.append((byte)37);
                    continue;
                }
                ++n2;
                FormatDesc formatDesc = new FormatDesc(this.this$0, varargs, luaString, n3);
                n3 += formatDesc.length;
                switch (formatDesc.conversion) {
                    case 99: {
                        formatDesc.format(buffer, (byte)varargs.checkint(n2));
                        break;
                    }
                    case 100: 
                    case 105: {
                        formatDesc.format(buffer, varargs.checklong(n2));
                        break;
                    }
                    case 88: 
                    case 111: 
                    case 117: 
                    case 120: {
                        formatDesc.format(buffer, varargs.checklong(n2));
                        break;
                    }
                    case 69: 
                    case 71: 
                    case 101: 
                    case 102: 
                    case 103: {
                        formatDesc.format(buffer, varargs.checkdouble(n2));
                        break;
                    }
                    case 113: {
                        StringLib.addquoted(buffer, varargs.checkstring(n2));
                        break;
                    }
                    case 115: {
                        LuaString luaString2 = varargs.checkstring(n2);
                        if (formatDesc.precision == -1 && luaString2.length() >= 100) {
                            buffer.append(luaString2);
                            break;
                        }
                        formatDesc.format(buffer, luaString2);
                        break;
                    }
                    default: {
                        format.error("invalid option '%" + (char)formatDesc.conversion + "' to 'format'");
                    }
                }
            }
            return buffer.tostring();
        }
    }

    static final class gmatch
    extends VarArgFunction {
        gmatch() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            LuaString luaString2 = varargs.checkstring(2);
            return new GMatchAux(varargs, luaString, luaString2);
        }
    }

    static final class gsub
    extends VarArgFunction {
        gsub() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            int n = luaString.length();
            LuaString luaString2 = varargs.checkstring(2);
            int n2 = -1;
            LuaValue luaValue = varargs.arg(3);
            int n3 = varargs.optint(4, n + 1);
            boolean bl = luaString2.length() > 0 && luaString2.charAt(0) == 94;
            Buffer buffer = new Buffer(n);
            MatchState matchState = new MatchState(varargs, luaString, luaString2);
            int n4 = 0;
            int n5 = 0;
            while (n5 < n3) {
                matchState.reset();
                int n6 = matchState.match(n4, bl ? 1 : 0);
                if (n6 != -1 && n6 != n2) {
                    ++n5;
                    matchState.add_value(buffer, n4, n6, luaValue);
                    n4 = n2 = n6;
                } else {
                    if (n4 >= n) break;
                    buffer.append((byte)luaString.luaByte(n4++));
                }
                if (!bl) continue;
                break;
            }
            buffer.append(luaString.substring(n4, n));
            return gsub.varargsOf(buffer.tostring(), (Varargs)gsub.valueOf(n5));
        }
    }

    static final class len
    extends OneArgFunction {
        len() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return luaValue.checkstring().len();
        }
    }

    static final class lower
    extends OneArgFunction {
        lower() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return lower.valueOf(luaValue.checkjstring().toLowerCase());
        }
    }

    static final class match
    extends VarArgFunction {
        match() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            return StringLib.str_find_aux(varargs, false);
        }
    }

    static final class rep
    extends VarArgFunction {
        rep() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            int n = varargs.checkint(2);
            byte[] byArray = new byte[luaString.length() * n];
            int n2 = luaString.length();
            for (int i = 0; i < byArray.length; i += n2) {
                luaString.copyInto(0, byArray, i, n2);
            }
            return LuaString.valueUsing(byArray);
        }
    }

    static final class reverse
    extends OneArgFunction {
        reverse() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaString luaString = luaValue.checkstring();
            int n = luaString.length();
            byte[] byArray = new byte[n];
            int n2 = 0;
            int n3 = n - 1;
            while (n2 < n) {
                byArray[n3] = (byte)luaString.luaByte(n2);
                ++n2;
                --n3;
            }
            return LuaString.valueUsing(byArray);
        }
    }

    static final class sub
    extends VarArgFunction {
        sub() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            int n = luaString.length();
            int n2 = StringLib.posrelat(varargs.checkint(2), n);
            int n3 = StringLib.posrelat(varargs.optint(3, -1), n);
            if (n2 < 1) {
                n2 = 1;
            }
            if (n3 > n) {
                n3 = n;
            }
            if (n2 <= n3) {
                return luaString.substring(n2 - 1, n3);
            }
            return EMPTYSTRING;
        }
    }

    static final class upper
    extends OneArgFunction {
        upper() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return upper.valueOf(luaValue.checkjstring().toUpperCase());
        }
    }

    static class MatchState {
        int matchdepth;
        final LuaString s;
        final LuaString p;
        final Varargs args;
        int level;
        int[] cinit;
        int[] clen;

        MatchState(Varargs varargs, LuaString luaString, LuaString luaString2) {
            this.s = luaString;
            this.p = luaString2;
            this.args = varargs;
            this.level = 0;
            this.cinit = new int[32];
            this.clen = new int[32];
            this.matchdepth = 200;
        }

        void reset() {
            this.level = 0;
            this.matchdepth = 200;
        }

        private void add_s(Buffer buffer, LuaString luaString, int n, int n2) {
            int n3 = luaString.length();
            for (int i = 0; i < n3; ++i) {
                byte by = (byte)luaString.luaByte(i);
                if (by != 37) {
                    buffer.append(by);
                    continue;
                }
                if (!Character.isDigit((char)(by = (byte)(++i < n3 ? luaString.luaByte(i) : 0)))) {
                    if (by != 37) {
                        LuaValue.error("invalid use of '%' in replacement string: after '%' must be '0'-'9' or '%', but found " + (String)(i < n3 ? "symbol '" + (char)by + "' with code " + by + " at pos " + (i + 1) : "end of string"));
                    }
                    buffer.append(by);
                    continue;
                }
                if (by == 48) {
                    buffer.append(this.s.substring(n, n2));
                    continue;
                }
                buffer.append(this.push_onecapture(by - 49, n, n2).strvalue());
            }
        }

        public void add_value(Buffer buffer, int n, int n2, LuaValue luaValue) {
            switch (luaValue.type()) {
                case 3: 
                case 4: {
                    this.add_s(buffer, luaValue.strvalue(), n, n2);
                    return;
                }
                case 6: {
                    luaValue = luaValue.invoke(this.push_captures(true, n, n2)).arg1();
                    break;
                }
                case 5: {
                    luaValue = luaValue.get(this.push_onecapture(0, n, n2));
                    break;
                }
                default: {
                    LuaValue.error("bad argument: string/function/table expected");
                    return;
                }
            }
            if (!luaValue.toboolean()) {
                luaValue = this.s.substring(n, n2);
            } else if (!luaValue.isstring()) {
                LuaValue.error("invalid replacement value (a " + luaValue.typename() + ")");
            }
            buffer.append(luaValue.strvalue());
        }

        Varargs push_captures(boolean bl, int n, int n2) {
            int n3 = this.level == 0 && bl ? 1 : this.level;
            switch (n3) {
                case 0: {
                    return LuaValue.NONE;
                }
                case 1: {
                    return this.push_onecapture(0, n, n2);
                }
            }
            LuaValue[] luaValueArray = new LuaValue[n3];
            for (int i = 0; i < n3; ++i) {
                luaValueArray[i] = this.push_onecapture(i, n, n2);
            }
            return LuaValue.varargsOf(luaValueArray);
        }

        private LuaValue push_onecapture(int n, int n2, int n3) {
            if (n >= this.level) {
                if (n == 0) {
                    return this.s.substring(n2, n3);
                }
                return LuaValue.error("invalid capture index %" + (n + 1));
            }
            int n4 = this.clen[n];
            if (n4 == -1) {
                return LuaValue.error("unfinished capture");
            }
            if (n4 == -2) {
                return LuaValue.valueOf(this.cinit[n] + 1);
            }
            int n5 = this.cinit[n];
            return this.s.substring(n5, n5 + n4);
        }

        private int check_capture(int n) {
            if ((n -= 49) < 0 || n >= this.level || this.clen[n] == -1) {
                LuaValue.error("invalid capture index %" + (n + 1));
            }
            return n;
        }

        private int capture_to_close() {
            int n = this.level;
            --n;
            while (n >= 0) {
                if (this.clen[n] == -1) {
                    return n;
                }
                --n;
            }
            LuaValue.error("invalid pattern capture");
            return 1;
        }

        int classend(int n) {
            switch (this.p.luaByte(n++)) {
                case 37: {
                    if (n == this.p.length()) {
                        LuaValue.error("malformed pattern (ends with '%')");
                    }
                    return n + 1;
                }
                case 91: {
                    if (n != this.p.length() && this.p.luaByte(n) == 94) {
                        ++n;
                    }
                    do {
                        if (n == this.p.length()) {
                            LuaValue.error("malformed pattern (missing ']')");
                        }
                        if (this.p.luaByte(n++) != 37 || n >= this.p.length()) continue;
                        ++n;
                    } while (n == this.p.length() || this.p.luaByte(n) != 93);
                    return n + 1;
                }
            }
            return n;
        }

        static boolean match_class(int n, int n2) {
            boolean bl;
            char c = Character.toLowerCase((char)n2);
            byte by = CHAR_TABLE[n];
            switch (c) {
                case 'a': {
                    bl = (by & 1) != 0;
                    break;
                }
                case 'd': {
                    bl = (by & 8) != 0;
                    break;
                }
                case 'l': {
                    bl = (by & 2) != 0;
                    break;
                }
                case 'u': {
                    bl = (by & 4) != 0;
                    break;
                }
                case 'c': {
                    bl = (by & 0x40) != 0;
                    break;
                }
                case 'p': {
                    bl = (by & 0x10) != 0;
                    break;
                }
                case 's': {
                    bl = (by & 0x20) != 0;
                    break;
                }
                case 'g': {
                    bl = (by & 0x19) != 0;
                    break;
                }
                case 'w': {
                    bl = (by & 9) != 0;
                    break;
                }
                case 'x': {
                    bl = (by & 0xFFFFFF80) != 0;
                    break;
                }
                case 'z': {
                    bl = n == 0;
                    break;
                }
                default: {
                    return n2 == n;
                }
            }
            return c == n2 == bl;
        }

        boolean matchbracketclass(int n, int n2, int n3) {
            boolean bl = true;
            if (this.p.luaByte(n2 + 1) == 94) {
                bl = false;
                ++n2;
            }
            while (++n2 < n3) {
                if (!(this.p.luaByte(n2) == 37 ? MatchState.match_class(n, this.p.luaByte(++n2)) : (this.p.luaByte(n2 + 1) == 45 && n2 + 2 < n3 ? this.p.luaByte((n2 += 2) - 2) <= n && n <= this.p.luaByte(n2) : this.p.luaByte(n2) == n))) continue;
                return bl;
            }
            return !bl;
        }

        boolean singlematch(int n, int n2, int n3) {
            switch (this.p.luaByte(n2)) {
                case 46: {
                    return false;
                }
                case 37: {
                    return MatchState.match_class(n, this.p.luaByte(n2 + 1));
                }
                case 91: {
                    return this.matchbracketclass(n, n2, n3 - 1);
                }
            }
            return this.p.luaByte(n2) == n;
        }

        int match(int n, int n2) {
            if (this.matchdepth-- == 0) {
                LuaValue.error("pattern too complex");
            }
            try {
                block33: while (true) {
                    int n3;
                    int n4;
                    int n5;
                    int n6;
                    if (n2 == this.p.length()) {
                        n6 = n;
                        return n6;
                    }
                    switch (this.p.luaByte(n2)) {
                        case 40: {
                            if (++n2 < this.p.length() && this.p.luaByte(n2) == 41) {
                                n6 = this.start_capture(n, n2 + 1, -2);
                                return n6;
                            }
                            n6 = this.start_capture(n, n2, -1);
                            return n6;
                        }
                        case 41: {
                            n6 = this.end_capture(n, n2 + 1);
                            return n6;
                        }
                        case 37: {
                            if (n2 + 1 == this.p.length()) {
                                LuaValue.error("malformed pattern (ends with '%')");
                            }
                            switch (this.p.luaByte(n2 + 1)) {
                                case 98: {
                                    n = this.matchbalance(n, n2 + 2);
                                    if (n == -1) {
                                        n6 = -1;
                                        return n6;
                                    }
                                    n2 += 4;
                                    continue block33;
                                }
                                case 102: {
                                    if ((n2 += 2) == this.p.length() || this.p.luaByte(n2) != 91) {
                                        LuaValue.error("missing '[' after '%f' in pattern");
                                    }
                                    n6 = this.classend(n2);
                                    n5 = n == 0 ? 0 : this.s.luaByte(n - 1);
                                    int n7 = n4 = n == this.s.length() ? 0 : this.s.luaByte(n);
                                    if (this.matchbracketclass(n5, n2, n6 - 1) || !this.matchbracketclass(n4, n2, n6 - 1)) {
                                        n3 = -1;
                                        return n3;
                                    }
                                    n2 = n6;
                                    continue block33;
                                }
                            }
                            n6 = this.p.luaByte(n2 + 1);
                            if (Character.isDigit((char)n6)) {
                                if ((n = this.match_capture(n, n6)) == -1) {
                                    n5 = -1;
                                    return n5;
                                }
                                n5 = this.match(n, n2 + 2);
                                return n5;
                            }
                        }
                        case 36: {
                            if (n2 + 1 != this.p.length()) break;
                            n6 = n == this.s.length() ? n : -1;
                            return n6;
                        }
                    }
                    n6 = this.classend(n2);
                    n5 = n < this.s.length() && this.singlematch(this.s.luaByte(n), n2, n6) ? 1 : 0;
                    n4 = n6 < this.p.length() ? this.p.luaByte(n6) : 0;
                    switch (n4) {
                        case 63: {
                            if (n5 != 0 && (n3 = this.match(n + 1, n6 + 1)) != -1) {
                                int n8 = n3;
                                return n8;
                            }
                            n2 = n6 + 1;
                            continue block33;
                        }
                        case 42: {
                            int n9 = this.max_expand(n, n2, n6);
                            return n9;
                        }
                        case 43: {
                            int n10 = n5 != 0 ? this.max_expand(n + 1, n2, n6) : -1;
                            return n10;
                        }
                        case 45: {
                            int n11 = this.min_expand(n, n2, n6);
                            return n11;
                        }
                    }
                    if (n5 == 0) {
                        int n12 = -1;
                        return n12;
                    }
                    ++n;
                    n2 = n6;
                }
            } finally {
                ++this.matchdepth;
            }
        }

        int max_expand(int n, int n2, int n3) {
            int n4 = 0;
            while (n + n4 < this.s.length() && this.singlematch(this.s.luaByte(n + n4), n2, n3)) {
                ++n4;
            }
            while (n4 >= 0) {
                int n5 = this.match(n + n4, n3 + 1);
                if (n5 != -1) {
                    return n5;
                }
                --n4;
            }
            return 1;
        }

        int min_expand(int n, int n2, int n3) {
            while (true) {
                int n4;
                if ((n4 = this.match(n, n3 + 1)) != -1) {
                    return n4;
                }
                if (n >= this.s.length() || !this.singlematch(this.s.luaByte(n), n2, n3)) break;
                ++n;
            }
            return 1;
        }

        int start_capture(int n, int n2, int n3) {
            int n4 = this.level;
            if (n4 >= 32) {
                LuaValue.error("too many captures");
            }
            this.cinit[n4] = n;
            this.clen[n4] = n3;
            this.level = n4 + 1;
            int n5 = this.match(n, n2);
            if (n5 == -1) {
                --this.level;
            }
            return n5;
        }

        int end_capture(int n, int n2) {
            int n3 = this.capture_to_close();
            this.clen[n3] = n - this.cinit[n3];
            int n4 = this.match(n, n2);
            if (n4 == -1) {
                this.clen[n3] = -1;
            }
            return n4;
        }

        int match_capture(int n, int n2) {
            n2 = this.check_capture(n2);
            int n3 = this.clen[n2];
            if (this.s.length() - n >= n3 && LuaString.equals(this.s, this.cinit[n2], this.s, n, n3)) {
                return n + n3;
            }
            return 1;
        }

        int matchbalance(int n, int n2) {
            int n3;
            int n4 = this.p.length();
            if (n2 == n4 || n2 + 1 == n4) {
                LuaValue.error("malformed pattern (missing arguments to '%b')");
            }
            if (n >= (n3 = this.s.length())) {
                return 1;
            }
            int n5 = this.p.luaByte(n2);
            if (this.s.luaByte(n) != n5) {
                return 1;
            }
            int n6 = this.p.luaByte(n2 + 1);
            int n7 = 1;
            while (++n < n3) {
                if (this.s.luaByte(n) == n6) {
                    if (--n7 != 0) continue;
                    return n + 1;
                }
                if (this.s.luaByte(n) != n5) continue;
                ++n7;
            }
            return 1;
        }
    }

    static class GMatchAux
    extends VarArgFunction {
        private final int srclen;
        private final MatchState ms;
        private int soffset;
        private int lastmatch;

        public GMatchAux(Varargs varargs, LuaString luaString, LuaString luaString2) {
            this.srclen = luaString.length();
            this.ms = new MatchState(varargs, luaString, luaString2);
            this.soffset = 0;
            this.lastmatch = -1;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            while (this.soffset <= this.srclen) {
                this.ms.reset();
                int n = this.ms.match(this.soffset, 0);
                if (n >= 0 && n != this.lastmatch) {
                    int n2 = this.soffset;
                    this.lastmatch = this.soffset = n;
                    return this.ms.push_captures(true, n2, n);
                }
                ++this.soffset;
            }
            return NIL;
        }
    }

    class FormatDesc {
        private boolean leftAdjust;
        private boolean zeroPad;
        private boolean explicitPlus;
        private boolean space;
        private boolean alternateForm;
        private static final int MAX_FLAGS = 5;
        private int width;
        int precision;
        public final int conversion;
        public final int length;
        public final String src;
        final StringLib this$0;

        public FormatDesc(StringLib stringLib, Varargs varargs, LuaString luaString, int n) {
            this.this$0 = stringLib;
            int n2 = n;
            int n3 = luaString.length();
            int n4 = 0;
            boolean bl = true;
            block7: while (bl) {
                n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                switch (n4) {
                    case 45: {
                        this.leftAdjust = true;
                        continue block7;
                    }
                    case 43: {
                        this.explicitPlus = true;
                        continue block7;
                    }
                    case 32: {
                        this.space = true;
                        continue block7;
                    }
                    case 35: {
                        this.alternateForm = true;
                        continue block7;
                    }
                    case 48: {
                        this.zeroPad = true;
                        continue block7;
                    }
                }
                bl = false;
            }
            if (n2 - n > 5) {
                LuaValue.error("invalid format (repeated flags)");
            }
            this.width = -1;
            if (Character.isDigit((char)n4)) {
                this.width = n4 - 48;
                int n5 = n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                if (Character.isDigit((char)n4)) {
                    this.width = this.width * 10 + (n4 - 48);
                    n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                }
            }
            this.precision = -1;
            if (n4 == 46) {
                int n6 = n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                if (Character.isDigit((char)n4)) {
                    this.precision = n4 - 48;
                    int n7 = n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                    if (Character.isDigit((char)n4)) {
                        this.precision = this.precision * 10 + (n4 - 48);
                        int n8 = n4 = n2 < n3 ? luaString.luaByte(n2++) : 0;
                    }
                }
            }
            if (Character.isDigit((char)n4)) {
                LuaValue.error("invalid format (width or precision too long)");
            }
            this.zeroPad &= !this.leftAdjust;
            this.conversion = n4;
            this.length = n2 - n;
            this.src = luaString.substring(n - 1, n2).tojstring();
        }

        public void format(Buffer buffer, byte by) {
            buffer.append(by);
        }

        public void format(Buffer buffer, long l) {
            int n;
            int n2;
            String string;
            if (l == 0L && this.precision == 0) {
                string = "";
            } else {
                switch (this.conversion) {
                    case 88: 
                    case 120: {
                        n2 = 16;
                        break;
                    }
                    case 111: {
                        n2 = 8;
                        break;
                    }
                    default: {
                        n2 = 10;
                    }
                }
                string = Long.toString(l, n2);
                if (this.conversion == 88) {
                    string = string.toUpperCase();
                }
            }
            int n3 = n2 = string.length();
            if (l < 0L) {
                --n3;
            } else if (this.explicitPlus || this.space) {
                ++n2;
            }
            int n4 = this.precision > n3 ? this.precision - n3 : (this.precision == -1 && this.zeroPad && this.width > n2 ? this.width - n2 : 0);
            int n5 = n = this.width > (n2 += n4) ? this.width - n2 : 0;
            if (!this.leftAdjust) {
                this.pad(buffer, ' ', n);
            }
            if (l < 0L) {
                if (n4 > 0) {
                    buffer.append((byte)45);
                    string = string.substring(1);
                }
            } else if (this.explicitPlus) {
                buffer.append((byte)43);
            } else if (this.space) {
                buffer.append((byte)32);
            }
            if (n4 > 0) {
                this.pad(buffer, '0', n4);
            }
            buffer.append(string);
            if (this.leftAdjust) {
                this.pad(buffer, ' ', n);
            }
        }

        public void format(Buffer buffer, double d) {
            buffer.append(this.this$0.format(this.src, d));
        }

        public void format(Buffer buffer, LuaString luaString) {
            int n = luaString.indexOf((byte)0, 0);
            if (n != -1) {
                luaString = luaString.substring(0, n);
            }
            buffer.append(luaString);
        }

        public final void pad(Buffer buffer, char c, int n) {
            byte by = (byte)c;
            while (n-- > 0) {
                buffer.append(by);
            }
        }
    }
}

