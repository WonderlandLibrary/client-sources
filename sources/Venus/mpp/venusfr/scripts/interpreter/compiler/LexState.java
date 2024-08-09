/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.Lua;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.compiler.Constants;
import mpp.venusfr.scripts.interpreter.compiler.FuncState;
import mpp.venusfr.scripts.interpreter.compiler.InstructionPtr;
import mpp.venusfr.scripts.interpreter.compiler.IntPtr;
import mpp.venusfr.scripts.interpreter.compiler.LuaC;
import mpp.venusfr.scripts.interpreter.lib.MathLib;

public class LexState
extends Constants {
    protected static final String RESERVED_LOCAL_VAR_FOR_CONTROL = "(for control)";
    protected static final String RESERVED_LOCAL_VAR_FOR_STATE = "(for state)";
    protected static final String RESERVED_LOCAL_VAR_FOR_GENERATOR = "(for generator)";
    protected static final String RESERVED_LOCAL_VAR_FOR_STEP = "(for step)";
    protected static final String RESERVED_LOCAL_VAR_FOR_LIMIT = "(for limit)";
    protected static final String RESERVED_LOCAL_VAR_FOR_INDEX = "(for index)";
    protected static final String[] RESERVED_LOCAL_VAR_KEYWORDS;
    private static final Hashtable RESERVED_LOCAL_VAR_KEYWORDS_TABLE;
    private static final int EOZ = -1;
    private static final int MAX_INT = 0x7FFFFFFD;
    private static final int UCHAR_MAX = 255;
    private static final int LUAI_MAXCCALLS = 200;
    private static final int LUA_COMPAT_LSTR = 1;
    private static final boolean LUA_COMPAT_VARARG = true;
    static final int NO_JUMP = -1;
    static final int OPR_ADD = 0;
    static final int OPR_SUB = 1;
    static final int OPR_MUL = 2;
    static final int OPR_DIV = 3;
    static final int OPR_MOD = 4;
    static final int OPR_POW = 5;
    static final int OPR_CONCAT = 6;
    static final int OPR_NE = 7;
    static final int OPR_EQ = 8;
    static final int OPR_LT = 9;
    static final int OPR_LE = 10;
    static final int OPR_GT = 11;
    static final int OPR_GE = 12;
    static final int OPR_AND = 13;
    static final int OPR_OR = 14;
    static final int OPR_NOBINOPR = 15;
    static final int OPR_MINUS = 0;
    static final int OPR_NOT = 1;
    static final int OPR_LEN = 2;
    static final int OPR_NOUNOPR = 3;
    static final int VVOID = 0;
    static final int VNIL = 1;
    static final int VTRUE = 2;
    static final int VFALSE = 3;
    static final int VK = 4;
    static final int VKNUM = 5;
    static final int VNONRELOC = 6;
    static final int VLOCAL = 7;
    static final int VUPVAL = 8;
    static final int VINDEXED = 9;
    static final int VJMP = 10;
    static final int VRELOCABLE = 11;
    static final int VCALL = 12;
    static final int VVARARG = 13;
    int current;
    int linenumber;
    int lastline;
    final Token t = new Token();
    final Token lookahead = new Token();
    FuncState fs;
    LuaC.CompileState L;
    InputStream z;
    char[] buff;
    int nbuff;
    Dyndata dyd = new Dyndata();
    LuaString source;
    LuaString envn;
    byte decpoint;
    static final String[] luaX_tokens;
    static final int TK_AND = 257;
    static final int TK_BREAK = 258;
    static final int TK_DO = 259;
    static final int TK_ELSE = 260;
    static final int TK_ELSEIF = 261;
    static final int TK_END = 262;
    static final int TK_FALSE = 263;
    static final int TK_FOR = 264;
    static final int TK_FUNCTION = 265;
    static final int TK_GOTO = 266;
    static final int TK_IF = 267;
    static final int TK_IN = 268;
    static final int TK_LOCAL = 269;
    static final int TK_NIL = 270;
    static final int TK_NOT = 271;
    static final int TK_OR = 272;
    static final int TK_REPEAT = 273;
    static final int TK_RETURN = 274;
    static final int TK_THEN = 275;
    static final int TK_TRUE = 276;
    static final int TK_UNTIL = 277;
    static final int TK_WHILE = 278;
    static final int TK_CONCAT = 279;
    static final int TK_DOTS = 280;
    static final int TK_EQ = 281;
    static final int TK_GE = 282;
    static final int TK_LE = 283;
    static final int TK_NE = 284;
    static final int TK_DBCOLON = 285;
    static final int TK_EOS = 286;
    static final int TK_NUMBER = 287;
    static final int TK_NAME = 288;
    static final int TK_STRING = 289;
    static final int FIRST_RESERVED = 257;
    static final int NUM_RESERVED = 22;
    static final Hashtable RESERVED;
    static Priority[] priority;
    static final int UNARY_PRIORITY = 8;

    private static final String LUA_QS(String string) {
        return "'" + string + "'";
    }

    private static final String LUA_QL(Object object) {
        return LexState.LUA_QS(String.valueOf(object));
    }

    public static boolean isReservedKeyword(String string) {
        return RESERVED_LOCAL_VAR_KEYWORDS_TABLE.containsKey(string);
    }

    private boolean isalnum(int n) {
        return n >= 48 && n <= 57 || n >= 97 && n <= 122 || n >= 65 && n <= 90 || n == 95;
    }

    private boolean isalpha(int n) {
        return n >= 97 && n <= 122 || n >= 65 && n <= 90;
    }

    private boolean isdigit(int n) {
        return n >= 48 && n <= 57;
    }

    private boolean isxdigit(int n) {
        return n >= 48 && n <= 57 || n >= 97 && n <= 102 || n >= 65 && n <= 70;
    }

    private boolean isspace(int n) {
        return n >= 0 && n <= 32;
    }

    public LexState(LuaC.CompileState compileState, InputStream inputStream) {
        this.z = inputStream;
        this.buff = new char[32];
        this.L = compileState;
    }

    void nextChar() {
        try {
            this.current = this.z.read();
        } catch (IOException iOException) {
            iOException.printStackTrace();
            this.current = -1;
        }
    }

    boolean currIsNewline() {
        return this.current == 10 || this.current == 13;
    }

    void save_and_next() {
        this.save(this.current);
        this.nextChar();
    }

    void save(int n) {
        if (this.buff == null || this.nbuff + 1 > this.buff.length) {
            this.buff = LexState.realloc(this.buff, this.nbuff * 2 + 1);
        }
        this.buff[this.nbuff++] = (char)n;
    }

    String token2str(int n) {
        if (n < 257) {
            return LexState.iscntrl(n) ? this.L.pushfstring("char(" + n + ")") : this.L.pushfstring(String.valueOf((char)n));
        }
        return luaX_tokens[n - 257];
    }

    private static boolean iscntrl(int n) {
        return n < 32;
    }

    String txtToken(int n) {
        switch (n) {
            case 287: 
            case 288: 
            case 289: {
                return new String(this.buff, 0, this.nbuff);
            }
        }
        return this.token2str(n);
    }

    void lexerror(String string, int n) {
        String string2 = Lua.chunkid(this.source.tojstring());
        this.L.pushfstring(string2 + ":" + this.linenumber + ": " + string);
        if (n != 0) {
            this.L.pushfstring("syntax error: " + string + " near " + this.txtToken(n));
        }
        throw new LuaError(string2 + ":" + this.linenumber + ": " + string, this.linenumber, false);
    }

    void syntaxerror(String string) {
        this.lexerror(string, this.t.token);
    }

    LuaString newstring(String string) {
        return this.L.newTString(string);
    }

    LuaString newstring(char[] cArray, int n, int n2) {
        return this.L.newTString(new String(cArray, n, n2));
    }

    void inclinenumber() {
        int n = this.current;
        LexState._assert(this.currIsNewline());
        this.nextChar();
        if (this.currIsNewline() && this.current != n) {
            this.nextChar();
        }
        if (++this.linenumber >= 0x7FFFFFFD) {
            this.syntaxerror("chunk has too many lines");
        }
    }

    void setinput(LuaC.CompileState compileState, int n, InputStream inputStream, LuaString luaString) {
        this.decpoint = (byte)46;
        this.L = compileState;
        this.lookahead.token = 286;
        this.z = inputStream;
        this.fs = null;
        this.linenumber = 1;
        this.lastline = 1;
        this.source = luaString;
        this.envn = LuaValue.ENV;
        this.nbuff = 0;
        this.current = n;
        this.skipShebang();
    }

    private void skipShebang() {
        if (this.current == 35) {
            while (!this.currIsNewline() && this.current != -1) {
                this.nextChar();
            }
        }
    }

    boolean check_next(String string) {
        if (string.indexOf(this.current) < 0) {
            return true;
        }
        this.save_and_next();
        return false;
    }

    void buffreplace(char c, char c2) {
        int n = this.nbuff;
        char[] cArray = this.buff;
        while (--n >= 0) {
            if (cArray[n] != c) continue;
            cArray[n] = c2;
        }
    }

    LuaValue strx2number(String string, SemInfo semInfo) {
        int n;
        char[] cArray = string.toCharArray();
        for (n = 0; n < cArray.length && this.isspace(cArray[n]); ++n) {
        }
        double d = 1.0;
        if (n < cArray.length && cArray[n] == '-') {
            d = -1.0;
            ++n;
        }
        if (n + 2 >= cArray.length) {
            return LuaValue.ZERO;
        }
        if (cArray[n++] != '0') {
            return LuaValue.ZERO;
        }
        if (cArray[n] != 'x' && cArray[n] != 'X') {
            return LuaValue.ZERO;
        }
        ++n;
        double d2 = 0.0;
        int n2 = 0;
        while (n < cArray.length && this.isxdigit(cArray[n])) {
            d2 = d2 * 16.0 + (double)this.hexvalue(cArray[n++]);
        }
        if (n < cArray.length && cArray[n] == '.') {
            ++n;
            while (n < cArray.length && this.isxdigit(cArray[n])) {
                d2 = d2 * 16.0 + (double)this.hexvalue(cArray[n++]);
                n2 -= 4;
            }
        }
        if (n < cArray.length && (cArray[n] == 'p' || cArray[n] == 'P')) {
            int n3 = 0;
            boolean bl = false;
            if (++n < cArray.length && cArray[n] == '-') {
                bl = true;
                ++n;
            }
            while (n < cArray.length && this.isdigit(cArray[n])) {
                n3 = n3 * 10 + cArray[n++] - 48;
            }
            if (bl) {
                n3 = -n3;
            }
            n2 += n3;
        }
        return LuaValue.valueOf(d * d2 * MathLib.dpow_d(2.0, n2));
    }

    boolean str2d(String string, SemInfo semInfo) {
        if (string.indexOf(110) >= 0 || string.indexOf(78) >= 0) {
            semInfo.r = LuaValue.ZERO;
        } else if (string.indexOf(120) >= 0 || string.indexOf(88) >= 0) {
            semInfo.r = this.strx2number(string, semInfo);
        } else {
            try {
                semInfo.r = LuaValue.valueOf(Double.parseDouble(string.trim()));
            } catch (NumberFormatException numberFormatException) {
                this.lexerror("malformed number (" + numberFormatException.getMessage() + ")", 287);
            }
        }
        return false;
    }

    void read_numeral(SemInfo semInfo) {
        String string = "Ee";
        int n = this.current;
        LexState._assert(this.isdigit(this.current));
        this.save_and_next();
        if (n == 48 && this.check_next("Xx")) {
            string = "Pp";
        }
        while (true) {
            if (this.check_next(string)) {
                this.check_next("+-");
            }
            if (!this.isxdigit(this.current) && this.current != 46) break;
            this.save_and_next();
        }
        String string2 = new String(this.buff, 0, this.nbuff);
        this.str2d(string2, semInfo);
    }

    int skip_sep() {
        int n = 0;
        int n2 = this.current;
        LexState._assert(n2 == 91 || n2 == 93);
        this.save_and_next();
        while (this.current == 61) {
            this.save_and_next();
            ++n;
        }
        return this.current == n2 ? n : -n - 1;
    }

    void read_long_string(SemInfo semInfo, int n) {
        int n2 = 0;
        this.save_and_next();
        if (this.currIsNewline()) {
            this.inclinenumber();
        }
        boolean bl = false;
        block6: while (!bl) {
            switch (this.current) {
                case -1: {
                    this.lexerror(semInfo != null ? "unfinished long string" : "unfinished long comment", 286);
                    continue block6;
                }
                case 91: {
                    if (this.skip_sep() != n) continue block6;
                    this.save_and_next();
                    ++n2;
                    if (n != 0) continue block6;
                    this.lexerror("nesting of [[...]] is deprecated", 91);
                    continue block6;
                }
                case 93: {
                    if (this.skip_sep() != n) continue block6;
                    this.save_and_next();
                    bl = true;
                    continue block6;
                }
                case 10: 
                case 13: {
                    this.save(10);
                    this.inclinenumber();
                    if (semInfo != null) continue block6;
                    this.nbuff = 0;
                    continue block6;
                }
            }
            if (semInfo != null) {
                this.save_and_next();
                continue;
            }
            this.nextChar();
        }
        if (semInfo != null) {
            semInfo.ts = this.L.newTString(LuaString.valueOf(this.buff, 2 + n, this.nbuff - 2 * (2 + n)));
        }
    }

    int hexvalue(int n) {
        return n <= 57 ? n - 48 : (n <= 70 ? n + 10 - 65 : n + 10 - 97);
    }

    int readhexaesc() {
        this.nextChar();
        int n = this.current;
        this.nextChar();
        int n2 = this.current;
        if (!this.isxdigit(n) || !this.isxdigit(n2)) {
            this.lexerror("hexadecimal digit expected 'x" + (char)n + (char)n2, 289);
        }
        return (this.hexvalue(n) << 4) + this.hexvalue(n2);
    }

    void read_string(int n, SemInfo semInfo) {
        this.save_and_next();
        block18: while (this.current != n) {
            switch (this.current) {
                case -1: {
                    this.lexerror("unfinished string", 286);
                    continue block18;
                }
                case 10: 
                case 13: {
                    this.lexerror("unfinished string", 289);
                    continue block18;
                }
                case 92: {
                    int n2;
                    this.nextChar();
                    switch (this.current) {
                        case 97: {
                            n2 = 7;
                            break;
                        }
                        case 98: {
                            n2 = 8;
                            break;
                        }
                        case 102: {
                            n2 = 12;
                            break;
                        }
                        case 110: {
                            n2 = 10;
                            break;
                        }
                        case 114: {
                            n2 = 13;
                            break;
                        }
                        case 116: {
                            n2 = 9;
                            break;
                        }
                        case 118: {
                            n2 = 11;
                            break;
                        }
                        case 120: {
                            n2 = this.readhexaesc();
                            break;
                        }
                        case 10: 
                        case 13: {
                            this.save(10);
                            this.inclinenumber();
                            continue block18;
                        }
                        case -1: {
                            continue block18;
                        }
                        case 122: {
                            this.nextChar();
                            while (this.isspace(this.current)) {
                                if (this.currIsNewline()) {
                                    this.inclinenumber();
                                    continue;
                                }
                                this.nextChar();
                            }
                            continue block18;
                        }
                        default: {
                            if (!this.isdigit(this.current)) {
                                this.save_and_next();
                                continue block18;
                            }
                            int n3 = 0;
                            n2 = 0;
                            do {
                                n2 = 10 * n2 + (this.current - 48);
                                this.nextChar();
                            } while (++n3 < 3 && this.isdigit(this.current));
                            if (n2 > 255) {
                                this.lexerror("escape sequence too large", 289);
                            }
                            this.save(n2);
                            continue block18;
                        }
                    }
                    this.save(n2);
                    this.nextChar();
                    continue block18;
                }
            }
            this.save_and_next();
        }
        this.save_and_next();
        semInfo.ts = this.L.newTString(LuaString.valueOf(this.buff, 1, this.nbuff - 2));
    }

    /*
     * Unable to fully structure code
     */
    int llex(SemInfo var1_1) {
        this.nbuff = 0;
        block15: while (true) {
            switch (this.current) {
                case 10: 
                case 13: {
                    this.inclinenumber();
                    continue block15;
                }
                case 9: 
                case 11: 
                case 12: 
                case 32: {
                    this.nextChar();
                    continue block15;
                }
                case 45: {
                    this.nextChar();
                    if (this.current != 45) {
                        return 0;
                    }
                    this.nextChar();
                    if (this.current == 91) {
                        var2_2 = this.skip_sep();
                        this.nbuff = 0;
                        if (var2_2 >= 0) {
                            this.read_long_string(null, var2_2);
                            this.nbuff = 0;
                            continue block15;
                        }
                    }
                    while (true) {
                        if (!this.currIsNewline() && this.current != -1) ** break;
                        continue block15;
                        this.nextChar();
                    }
                }
                case 91: {
                    var2_2 = this.skip_sep();
                    if (var2_2 >= 0) {
                        this.read_long_string(var1_1, var2_2);
                        return 0;
                    }
                    if (var2_2 == -1) {
                        return 0;
                    }
                    this.lexerror("invalid long string delimiter", 289);
                }
                case 61: {
                    this.nextChar();
                    if (this.current != 61) {
                        return 0;
                    }
                    this.nextChar();
                    return 0;
                }
                case 60: {
                    this.nextChar();
                    if (this.current != 61) {
                        return 1;
                    }
                    this.nextChar();
                    return 0;
                }
                case 62: {
                    this.nextChar();
                    if (this.current != 61) {
                        return 1;
                    }
                    this.nextChar();
                    return 1;
                }
                case 126: {
                    this.nextChar();
                    if (this.current != 61) {
                        return 1;
                    }
                    this.nextChar();
                    return 1;
                }
                case 58: {
                    this.nextChar();
                    if (this.current != 58) {
                        return 1;
                    }
                    this.nextChar();
                    return 0;
                }
                case 34: 
                case 39: {
                    this.read_string(this.current, var1_1);
                    return 0;
                }
                case 46: {
                    this.save_and_next();
                    if (this.check_next(".")) {
                        if (this.check_next(".")) {
                            return 1;
                        }
                        return 0;
                    }
                    if (!this.isdigit(this.current)) {
                        return 1;
                    }
                    this.read_numeral(var1_1);
                    return 0;
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    this.read_numeral(var1_1);
                    return 0;
                }
                case -1: {
                    return 1;
                }
            }
            break;
        }
        if (this.isalpha(this.current) || this.current == 95) {
            do {
                this.save_and_next();
            } while (this.isalnum(this.current));
            var2_3 = this.newstring(this.buff, 0, this.nbuff);
            if (LexState.RESERVED.containsKey(var2_3)) {
                return (Integer)LexState.RESERVED.get(var2_3);
            }
            var1_1.ts = var2_3;
            return 1;
        }
        var2_2 = this.current;
        this.nextChar();
        return var2_2;
    }

    void next() {
        this.lastline = this.linenumber;
        if (this.lookahead.token != 286) {
            this.t.set(this.lookahead);
            this.lookahead.token = 286;
        } else {
            this.t.token = this.llex(this.t.seminfo);
        }
    }

    void lookahead() {
        LexState._assert(this.lookahead.token == 286);
        this.lookahead.token = this.llex(this.lookahead.seminfo);
    }

    static final boolean vkisvar(int n) {
        return 7 <= n && n <= 9;
    }

    static final boolean vkisinreg(int n) {
        return n == 6 || n == 7;
    }

    boolean hasmultret(int n) {
        return n == 12 || n == 13;
    }

    void anchor_token() {
        LexState._assert(this.fs != null || this.t.token == 286);
        if (this.t.token == 288 || this.t.token == 289) {
            LuaString luaString = this.t.seminfo.ts;
            this.L.cachedLuaString(this.t.seminfo.ts);
        }
    }

    void semerror(String string) {
        this.t.token = 0;
        this.syntaxerror(string);
    }

    void error_expected(int n) {
        this.syntaxerror(this.L.pushfstring(LexState.LUA_QS(this.token2str(n)) + " expected"));
    }

    boolean testnext(int n) {
        if (this.t.token == n) {
            this.next();
            return false;
        }
        return true;
    }

    void check(int n) {
        if (this.t.token != n) {
            this.error_expected(n);
        }
    }

    void checknext(int n) {
        this.check(n);
        this.next();
    }

    void check_condition(boolean bl, String string) {
        if (!bl) {
            this.syntaxerror(string);
        }
    }

    void check_match(int n, int n2, int n3) {
        if (!this.testnext(n)) {
            if (n3 == this.linenumber) {
                this.error_expected(n);
            } else {
                this.syntaxerror(this.L.pushfstring(LexState.LUA_QS(this.token2str(n)) + " expected (to close " + LexState.LUA_QS(this.token2str(n2)) + " at line " + n3 + ")"));
            }
        }
    }

    LuaString str_checkname() {
        this.check(288);
        LuaString luaString = this.t.seminfo.ts;
        this.next();
        return luaString;
    }

    void codestring(expdesc expdesc2, LuaString luaString) {
        expdesc2.init(4, this.fs.stringK(luaString));
    }

    void checkname(expdesc expdesc2) {
        this.codestring(expdesc2, this.str_checkname());
    }

    int registerlocalvar(LuaString luaString) {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        if (prototype.locvars == null || funcState.nlocvars + 1 > prototype.locvars.length) {
            prototype.locvars = LexState.realloc(prototype.locvars, funcState.nlocvars * 2 + 1);
        }
        prototype.locvars[funcState.nlocvars] = new LocVars(luaString, 0, 0);
        short s = funcState.nlocvars;
        funcState.nlocvars = (short)(s + 1);
        return s;
    }

    void new_localvar(LuaString luaString) {
        int n = this.registerlocalvar(luaString);
        this.fs.checklimit(this.dyd.n_actvar + 1, 200, "local variables");
        if (this.dyd.actvar == null || this.dyd.n_actvar + 1 > this.dyd.actvar.length) {
            this.dyd.actvar = LexState.realloc(this.dyd.actvar, Math.max(1, this.dyd.n_actvar * 2));
        }
        this.dyd.actvar[this.dyd.n_actvar++] = new Vardesc(n);
    }

    void new_localvarliteral(String string) {
        LuaString luaString = this.newstring(string);
        this.new_localvar(luaString);
    }

    void adjustlocalvars(int n) {
        FuncState funcState = this.fs;
        funcState.nactvar = (short)(funcState.nactvar + n);
        while (n > 0) {
            funcState.getlocvar((int)(funcState.nactvar - n)).startpc = funcState.pc;
            --n;
        }
    }

    void removevars(int n) {
        FuncState funcState = this.fs;
        while (funcState.nactvar > n) {
            funcState.nactvar = (short)(funcState.nactvar - 1);
            funcState.getlocvar((int)((short)(funcState.nactvar - 1))).endpc = funcState.pc;
        }
    }

    void singlevar(expdesc expdesc2) {
        FuncState funcState = this.fs;
        LuaString luaString = this.str_checkname();
        if (FuncState.singlevaraux(funcState, luaString, expdesc2, 1) == 0) {
            expdesc expdesc3 = new expdesc();
            FuncState.singlevaraux(funcState, this.envn, expdesc2, 1);
            LexState._assert(expdesc2.k == 7 || expdesc2.k == 8);
            this.codestring(expdesc3, luaString);
            funcState.indexed(expdesc2, expdesc3);
        }
    }

    void adjust_assign(int n, int n2, expdesc expdesc2) {
        FuncState funcState = this.fs;
        int n3 = n - n2;
        if (this.hasmultret(expdesc2.k)) {
            if (++n3 < 0) {
                n3 = 0;
            }
            funcState.setreturns(expdesc2, n3);
            if (n3 > 1) {
                funcState.reserveregs(n3 - 1);
            }
        } else {
            if (expdesc2.k != 0) {
                funcState.exp2nextreg(expdesc2);
            }
            if (n3 > 0) {
                short s = funcState.freereg;
                funcState.reserveregs(n3);
                funcState.nil(s, n3);
            }
        }
    }

    void enterlevel() {
        if (++this.L.nCcalls > 200) {
            this.lexerror("chunk has too many syntax levels", 0);
        }
    }

    void leavelevel() {
        --this.L.nCcalls;
    }

    void closegoto(int n, Labeldesc labeldesc) {
        FuncState funcState = this.fs;
        Labeldesc[] labeldescArray = this.dyd.gt;
        Labeldesc labeldesc2 = labeldescArray[n];
        LexState._assert(labeldesc2.name.eq_b(labeldesc.name));
        if (labeldesc2.nactvar < labeldesc.nactvar) {
            LuaString luaString = funcState.getlocvar((int)labeldesc2.nactvar).varname;
            String string = this.L.pushfstring("<goto " + labeldesc2.name + "> at line " + labeldesc2.line + " jumps into the scope of local '" + luaString.tojstring() + "'");
            this.semerror(string);
        }
        funcState.patchlist(labeldesc2.pc, labeldesc.pc);
        System.arraycopy(labeldescArray, n + 1, labeldescArray, n, this.dyd.n_gt - n - 1);
        labeldescArray[--this.dyd.n_gt] = null;
    }

    boolean findlabel(int n) {
        FuncState.BlockCnt blockCnt = this.fs.bl;
        Dyndata dyndata = this.dyd;
        Labeldesc labeldesc = dyndata.gt[n];
        for (int i = blockCnt.firstlabel; i < dyndata.n_label; ++i) {
            Labeldesc labeldesc2 = dyndata.label[i];
            if (!labeldesc2.name.eq_b(labeldesc.name)) continue;
            if (labeldesc.nactvar > labeldesc2.nactvar && (blockCnt.upval || dyndata.n_label > blockCnt.firstlabel)) {
                this.fs.patchclose(labeldesc.pc, labeldesc2.nactvar);
            }
            this.closegoto(n, labeldesc2);
            return false;
        }
        return true;
    }

    int newlabelentry(Labeldesc[] labeldescArray, int n, LuaString luaString, int n2, int n3) {
        labeldescArray[n] = new Labeldesc(luaString, n3, n2, this.fs.nactvar);
        return n;
    }

    void findgotos(Labeldesc labeldesc) {
        Labeldesc[] labeldescArray = this.dyd.gt;
        int n = this.fs.bl.firstgoto;
        while (n < this.dyd.n_gt) {
            if (labeldescArray[n].name.eq_b(labeldesc.name)) {
                this.closegoto(n, labeldesc);
                continue;
            }
            ++n;
        }
    }

    void breaklabel() {
        LuaString luaString = LuaString.valueOf("break");
        this.dyd.label = LexState.grow(this.dyd.label, this.dyd.n_label + 1);
        int n = this.newlabelentry(this.dyd.label, this.dyd.n_label++, luaString, 0, this.fs.pc);
        this.findgotos(this.dyd.label[n]);
    }

    void undefgoto(Labeldesc labeldesc) {
        String string = this.L.pushfstring(LexState.isReservedKeyword(labeldesc.name.tojstring()) ? "<" + labeldesc.name + "> at line " + labeldesc.line + " not inside a loop" : "no visible label '" + labeldesc.name + "' for <goto> at line " + labeldesc.line);
        this.semerror(string);
    }

    Prototype addprototype() {
        Prototype prototype;
        Prototype prototype2 = this.fs.f;
        if (prototype2.p == null || this.fs.np >= prototype2.p.length) {
            prototype2.p = LexState.realloc(prototype2.p, Math.max(1, this.fs.np * 2));
        }
        prototype2.p[this.fs.np++] = prototype = new Prototype();
        return prototype;
    }

    void codeclosure(expdesc expdesc2) {
        FuncState funcState = this.fs.prev;
        expdesc2.init(11, funcState.codeABx(37, 0, funcState.np - 1));
        funcState.exp2nextreg(expdesc2);
    }

    void open_func(FuncState funcState, FuncState.BlockCnt blockCnt) {
        funcState.prev = this.fs;
        funcState.ls = this;
        this.fs = funcState;
        funcState.pc = 0;
        funcState.lasttarget = -1;
        funcState.jpc = new IntPtr(-1);
        funcState.freereg = 0;
        funcState.nk = 0;
        funcState.np = 0;
        funcState.nups = 0;
        funcState.nlocvars = 0;
        funcState.nactvar = 0;
        funcState.firstlocal = this.dyd.n_actvar;
        funcState.bl = null;
        funcState.f.source = this.source;
        funcState.f.maxstacksize = 2;
        funcState.enterblock(blockCnt, true);
    }

    void close_func() {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        funcState.ret(0, 0);
        funcState.leaveblock();
        prototype.code = LexState.realloc(prototype.code, funcState.pc);
        prototype.lineinfo = LexState.realloc(prototype.lineinfo, funcState.pc);
        prototype.k = LexState.realloc(prototype.k, funcState.nk);
        prototype.p = LexState.realloc(prototype.p, funcState.np);
        prototype.locvars = LexState.realloc(prototype.locvars, (int)funcState.nlocvars);
        prototype.upvalues = LexState.realloc(prototype.upvalues, (int)funcState.nups);
        LexState._assert(funcState.bl == null);
        this.fs = funcState.prev;
    }

    void fieldsel(expdesc expdesc2) {
        FuncState funcState = this.fs;
        expdesc expdesc3 = new expdesc();
        funcState.exp2anyregup(expdesc2);
        this.next();
        this.checkname(expdesc3);
        funcState.indexed(expdesc2, expdesc3);
    }

    void yindex(expdesc expdesc2) {
        this.next();
        this.expr(expdesc2);
        this.fs.exp2val(expdesc2);
        this.checknext(93);
    }

    void recfield(ConsControl consControl) {
        FuncState funcState = this.fs;
        short s = this.fs.freereg;
        expdesc expdesc2 = new expdesc();
        expdesc expdesc3 = new expdesc();
        if (this.t.token == 288) {
            funcState.checklimit(consControl.nh, 0x7FFFFFFD, "items in a constructor");
            this.checkname(expdesc2);
        } else {
            this.yindex(expdesc2);
        }
        ++consControl.nh;
        this.checknext(61);
        int n = funcState.exp2RK(expdesc2);
        this.expr(expdesc3);
        funcState.codeABC(10, consControl.t.u.info, n, funcState.exp2RK(expdesc3));
        funcState.freereg = s;
    }

    void listfield(ConsControl consControl) {
        this.expr(consControl.v);
        this.fs.checklimit(consControl.na, 0x7FFFFFFD, "items in a constructor");
        ++consControl.na;
        ++consControl.tostore;
    }

    void constructor(expdesc expdesc2) {
        FuncState funcState = this.fs;
        int n = this.linenumber;
        int n2 = funcState.codeABC(11, 0, 0, 0);
        ConsControl consControl = new ConsControl();
        consControl.tostore = 0;
        consControl.nh = 0;
        consControl.na = 0;
        consControl.t = expdesc2;
        expdesc2.init(11, n2);
        consControl.v.init(0, 0);
        funcState.exp2nextreg(expdesc2);
        this.checknext(123);
        do {
            LexState._assert(consControl.v.k == 0 || consControl.tostore > 0);
            if (this.t.token == 125) break;
            funcState.closelistfield(consControl);
            switch (this.t.token) {
                case 288: {
                    this.lookahead();
                    if (this.lookahead.token != 61) {
                        this.listfield(consControl);
                        break;
                    }
                    this.recfield(consControl);
                    break;
                }
                case 91: {
                    this.recfield(consControl);
                    break;
                }
                default: {
                    this.listfield(consControl);
                }
            }
        } while (this.testnext(1) || this.testnext(0));
        this.check_match(125, 123, n);
        funcState.lastlistfield(consControl);
        InstructionPtr instructionPtr = new InstructionPtr(funcState.f.code, n2);
        LexState.SETARG_B(instructionPtr, LexState.luaO_int2fb(consControl.na));
        LexState.SETARG_C(instructionPtr, LexState.luaO_int2fb(consControl.nh));
    }

    static int luaO_int2fb(int n) {
        int n2 = 0;
        while (n >= 16) {
            n = n + 1 >> 1;
            ++n2;
        }
        if (n < 8) {
            return n;
        }
        return n2 + 1 << 3 | n - 8;
    }

    void parlist() {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        int n = 0;
        prototype.is_vararg = 0;
        if (this.t.token != 41) {
            do {
                switch (this.t.token) {
                    case 288: {
                        this.new_localvar(this.str_checkname());
                        ++n;
                        break;
                    }
                    case 280: {
                        this.next();
                        prototype.is_vararg = 1;
                        break;
                    }
                    default: {
                        this.syntaxerror("<name> or " + LexState.LUA_QL("...") + " expected");
                    }
                }
            } while (prototype.is_vararg == 0 && this.testnext(1));
        }
        this.adjustlocalvars(n);
        prototype.numparams = funcState.nactvar;
        funcState.reserveregs(funcState.nactvar);
    }

    void body(expdesc expdesc2, boolean bl, int n) {
        FuncState funcState = new FuncState();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        funcState.f = this.addprototype();
        funcState.f.linedefined = n;
        this.open_func(funcState, blockCnt);
        this.checknext(40);
        if (bl) {
            this.new_localvarliteral("self");
            this.adjustlocalvars(1);
        }
        this.parlist();
        this.checknext(41);
        this.statlist();
        funcState.f.lastlinedefined = this.linenumber;
        this.check_match(262, 265, n);
        this.codeclosure(expdesc2);
        this.close_func();
    }

    int explist(expdesc expdesc2) {
        int n = 1;
        this.expr(expdesc2);
        while (this.testnext(1)) {
            this.fs.exp2nextreg(expdesc2);
            this.expr(expdesc2);
            ++n;
        }
        return n;
    }

    void funcargs(expdesc expdesc2, int n) {
        int n2;
        FuncState funcState = this.fs;
        expdesc expdesc3 = new expdesc();
        switch (this.t.token) {
            case 40: {
                this.next();
                if (this.t.token == 41) {
                    expdesc3.k = 0;
                } else {
                    this.explist(expdesc3);
                    funcState.setmultret(expdesc3);
                }
                this.check_match(41, 40, n);
                break;
            }
            case 123: {
                this.constructor(expdesc3);
                break;
            }
            case 289: {
                this.codestring(expdesc3, this.t.seminfo.ts);
                this.next();
                break;
            }
            default: {
                this.syntaxerror("function arguments expected");
                return;
            }
        }
        LexState._assert(expdesc2.k == 6);
        int n3 = expdesc2.u.info;
        if (this.hasmultret(expdesc3.k)) {
            n2 = -1;
        } else {
            if (expdesc3.k != 0) {
                funcState.exp2nextreg(expdesc3);
            }
            n2 = funcState.freereg - (n3 + 1);
        }
        expdesc2.init(12, funcState.codeABC(29, n3, n2 + 1, 2));
        funcState.fixline(n);
        funcState.freereg = (short)(n3 + 1);
    }

    void primaryexp(expdesc expdesc2) {
        switch (this.t.token) {
            case 40: {
                int n = this.linenumber;
                this.next();
                this.expr(expdesc2);
                this.check_match(41, 40, n);
                this.fs.dischargevars(expdesc2);
                return;
            }
            case 288: {
                this.singlevar(expdesc2);
                return;
            }
        }
        this.syntaxerror("unexpected symbol " + this.t.token + " (" + (char)this.t.token + ")");
    }

    void suffixedexp(expdesc expdesc2) {
        int n = this.linenumber;
        this.primaryexp(expdesc2);
        block6: while (true) {
            switch (this.t.token) {
                case 46: {
                    this.fieldsel(expdesc2);
                    continue block6;
                }
                case 91: {
                    expdesc expdesc3 = new expdesc();
                    this.fs.exp2anyregup(expdesc2);
                    this.yindex(expdesc3);
                    this.fs.indexed(expdesc2, expdesc3);
                    continue block6;
                }
                case 58: {
                    expdesc expdesc3 = new expdesc();
                    this.next();
                    this.checkname(expdesc3);
                    this.fs.self(expdesc2, expdesc3);
                    this.funcargs(expdesc2, n);
                    continue block6;
                }
                case 40: 
                case 123: 
                case 289: {
                    this.fs.exp2nextreg(expdesc2);
                    this.funcargs(expdesc2, n);
                    continue block6;
                }
            }
            break;
        }
    }

    void simpleexp(expdesc expdesc2) {
        switch (this.t.token) {
            case 287: {
                expdesc2.init(5, 0);
                expdesc2.u.setNval(this.t.seminfo.r);
                break;
            }
            case 289: {
                this.codestring(expdesc2, this.t.seminfo.ts);
                break;
            }
            case 270: {
                expdesc2.init(1, 0);
                break;
            }
            case 276: {
                expdesc2.init(2, 0);
                break;
            }
            case 263: {
                expdesc2.init(3, 0);
                break;
            }
            case 280: {
                FuncState funcState = this.fs;
                this.check_condition(funcState.f.is_vararg != 0, "cannot use " + LexState.LUA_QL("...") + " outside a vararg function");
                expdesc2.init(13, funcState.codeABC(38, 0, 1, 0));
                break;
            }
            case 123: {
                this.constructor(expdesc2);
                return;
            }
            case 265: {
                this.next();
                this.body(expdesc2, false, this.linenumber);
                return;
            }
            default: {
                this.suffixedexp(expdesc2);
                return;
            }
        }
        this.next();
    }

    int getunopr(int n) {
        switch (n) {
            case 271: {
                return 0;
            }
            case 45: {
                return 1;
            }
            case 35: {
                return 1;
            }
        }
        return 0;
    }

    int getbinopr(int n) {
        switch (n) {
            case 43: {
                return 1;
            }
            case 45: {
                return 0;
            }
            case 42: {
                return 1;
            }
            case 47: {
                return 0;
            }
            case 37: {
                return 1;
            }
            case 94: {
                return 0;
            }
            case 279: {
                return 1;
            }
            case 284: {
                return 0;
            }
            case 281: {
                return 1;
            }
            case 60: {
                return 0;
            }
            case 283: {
                return 1;
            }
            case 62: {
                return 0;
            }
            case 282: {
                return 1;
            }
            case 257: {
                return 0;
            }
            case 272: {
                return 1;
            }
        }
        return 0;
    }

    int subexpr(expdesc expdesc2, int n) {
        this.enterlevel();
        int n2 = this.getunopr(this.t.token);
        if (n2 != 3) {
            int n3 = this.linenumber;
            this.next();
            this.subexpr(expdesc2, 8);
            this.fs.prefix(n2, expdesc2, n3);
        } else {
            this.simpleexp(expdesc2);
        }
        int n4 = this.getbinopr(this.t.token);
        while (n4 != 15 && LexState.priority[n4].left > n) {
            expdesc expdesc3 = new expdesc();
            int n5 = this.linenumber;
            this.next();
            this.fs.infix(n4, expdesc2);
            int n6 = this.subexpr(expdesc3, LexState.priority[n4].right);
            this.fs.posfix(n4, expdesc2, expdesc3, n5);
            n4 = n6;
        }
        this.leavelevel();
        return n4;
    }

    void expr(expdesc expdesc2) {
        this.subexpr(expdesc2, 0);
    }

    boolean block_follow(boolean bl) {
        switch (this.t.token) {
            case 260: 
            case 261: 
            case 262: 
            case 286: {
                return false;
            }
            case 277: {
                return bl;
            }
        }
        return true;
    }

    void block() {
        FuncState funcState = this.fs;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        funcState.enterblock(blockCnt, true);
        this.statlist();
        funcState.leaveblock();
    }

    void check_conflict(LHS_assign lHS_assign, expdesc expdesc2) {
        FuncState funcState = this.fs;
        short s = funcState.freereg;
        boolean bl = false;
        while (lHS_assign != null) {
            if (lHS_assign.v.k == 9) {
                if (lHS_assign.v.u.ind_vt == expdesc2.k && lHS_assign.v.u.ind_t == expdesc2.u.info) {
                    bl = true;
                    lHS_assign.v.u.ind_vt = (short)7;
                    lHS_assign.v.u.ind_t = s;
                }
                if (expdesc2.k == 7 && lHS_assign.v.u.ind_idx == expdesc2.u.info) {
                    bl = true;
                    lHS_assign.v.u.ind_idx = s;
                }
            }
            lHS_assign = lHS_assign.prev;
        }
        if (bl) {
            int n = expdesc2.k == 7 ? 0 : 5;
            funcState.codeABC(n, s, expdesc2.u.info, 0);
            funcState.reserveregs(1);
        }
    }

    void assignment(LHS_assign lHS_assign, int n) {
        expdesc expdesc2 = new expdesc();
        this.check_condition(7 <= lHS_assign.v.k && lHS_assign.v.k <= 9, "syntax error");
        if (this.testnext(1)) {
            LHS_assign lHS_assign2 = new LHS_assign();
            lHS_assign2.prev = lHS_assign;
            this.suffixedexp(lHS_assign2.v);
            if (lHS_assign2.v.k != 9) {
                this.check_conflict(lHS_assign, lHS_assign2.v);
            }
            this.assignment(lHS_assign2, n + 1);
        } else {
            this.checknext(61);
            int n2 = this.explist(expdesc2);
            if (n2 != n) {
                this.adjust_assign(n, n2, expdesc2);
                if (n2 > n) {
                    this.fs.freereg = (short)(this.fs.freereg - (n2 - n));
                }
            } else {
                this.fs.setoneret(expdesc2);
                this.fs.storevar(lHS_assign.v, expdesc2);
                return;
            }
        }
        expdesc2.init(6, this.fs.freereg - 1);
        this.fs.storevar(lHS_assign.v, expdesc2);
    }

    int cond() {
        expdesc expdesc2 = new expdesc();
        this.expr(expdesc2);
        if (expdesc2.k == 1) {
            expdesc2.k = 3;
        }
        this.fs.goiftrue(expdesc2);
        return expdesc2.f.i;
    }

    void gotostat(int n) {
        LuaString luaString;
        int n2 = this.linenumber;
        if (this.testnext(1)) {
            luaString = this.str_checkname();
        } else {
            this.next();
            luaString = LuaString.valueOf("break");
        }
        this.dyd.gt = LexState.grow(this.dyd.gt, this.dyd.n_gt + 1);
        int n3 = this.newlabelentry(this.dyd.gt, this.dyd.n_gt++, luaString, n2, n);
        this.findlabel(n3);
    }

    void skipnoopstat() {
        while (this.t.token == 59 || this.t.token == 285) {
            this.statement();
        }
    }

    void labelstat(LuaString luaString, int n) {
        this.fs.checkrepeated(this.dyd.label, this.dyd.n_label, luaString);
        this.checknext(285);
        this.dyd.label = LexState.grow(this.dyd.label, this.dyd.n_label + 1);
        int n2 = this.newlabelentry(this.dyd.label, this.dyd.n_label++, luaString, n, this.fs.getlabel());
        this.skipnoopstat();
        if (this.block_follow(true)) {
            this.dyd.label[n2].nactvar = this.fs.bl.nactvar;
        }
        this.findgotos(this.dyd.label[n2]);
    }

    void whilestat(int n) {
        FuncState funcState = this.fs;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        this.next();
        int n2 = funcState.getlabel();
        int n3 = this.cond();
        funcState.enterblock(blockCnt, false);
        this.checknext(259);
        this.block();
        funcState.patchlist(funcState.jump(), n2);
        this.check_match(262, 278, n);
        funcState.leaveblock();
        funcState.patchtohere(n3);
    }

    void repeatstat(int n) {
        FuncState funcState = this.fs;
        int n2 = funcState.getlabel();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        FuncState.BlockCnt blockCnt2 = new FuncState.BlockCnt();
        funcState.enterblock(blockCnt, false);
        funcState.enterblock(blockCnt2, true);
        this.next();
        this.statlist();
        this.check_match(277, 273, n);
        int n3 = this.cond();
        if (blockCnt2.upval) {
            funcState.patchclose(n3, blockCnt2.nactvar);
        }
        funcState.leaveblock();
        funcState.patchlist(n3, n2);
        funcState.leaveblock();
    }

    int exp1() {
        expdesc expdesc2 = new expdesc();
        this.expr(expdesc2);
        int n = expdesc2.k;
        this.fs.exp2nextreg(expdesc2);
        return n;
    }

    void forbody(int n, int n2, int n3, boolean bl) {
        int n4;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        FuncState funcState = this.fs;
        this.adjustlocalvars(3);
        this.checknext(259);
        int n5 = bl ? funcState.codeAsBx(33, n, -1) : funcState.jump();
        funcState.enterblock(blockCnt, true);
        this.adjustlocalvars(n3);
        funcState.reserveregs(n3);
        this.block();
        funcState.leaveblock();
        funcState.patchtohere(n5);
        if (bl) {
            n4 = funcState.codeAsBx(32, n, -1);
        } else {
            funcState.codeABC(34, n, 0, n3);
            funcState.fixline(n2);
            n4 = funcState.codeAsBx(35, n + 2, -1);
        }
        funcState.patchlist(n4, n5 + 1);
        funcState.fixline(n2);
    }

    void fornum(LuaString luaString, int n) {
        FuncState funcState = this.fs;
        short s = funcState.freereg;
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_INDEX);
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_LIMIT);
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_STEP);
        this.new_localvar(luaString);
        this.checknext(61);
        this.exp1();
        this.checknext(44);
        this.exp1();
        if (this.testnext(1)) {
            this.exp1();
        } else {
            funcState.codeK(funcState.freereg, funcState.numberK(LuaInteger.valueOf(1)));
            funcState.reserveregs(1);
        }
        this.forbody(s, n, 1, false);
    }

    void forlist(LuaString luaString) {
        FuncState funcState = this.fs;
        expdesc expdesc2 = new expdesc();
        int n = 4;
        short s = funcState.freereg;
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_GENERATOR);
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_STATE);
        this.new_localvarliteral(RESERVED_LOCAL_VAR_FOR_CONTROL);
        this.new_localvar(luaString);
        while (this.testnext(1)) {
            this.new_localvar(this.str_checkname());
            ++n;
        }
        this.checknext(268);
        int n2 = this.linenumber;
        this.adjust_assign(3, this.explist(expdesc2), expdesc2);
        funcState.checkstack(3);
        this.forbody(s, n2, n - 3, true);
    }

    void forstat(int n) {
        FuncState funcState = this.fs;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        funcState.enterblock(blockCnt, false);
        this.next();
        LuaString luaString = this.str_checkname();
        switch (this.t.token) {
            case 61: {
                this.fornum(luaString, n);
                break;
            }
            case 44: 
            case 268: {
                this.forlist(luaString);
                break;
            }
            default: {
                this.syntaxerror(LexState.LUA_QL("=") + " or " + LexState.LUA_QL("in") + " expected");
            }
        }
        this.check_match(262, 264, n);
        funcState.leaveblock();
    }

    void test_then_block(IntPtr intPtr) {
        int n;
        expdesc expdesc2 = new expdesc();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        this.next();
        this.expr(expdesc2);
        this.checknext(275);
        if (this.t.token == 266 || this.t.token == 258) {
            this.fs.goiffalse(expdesc2);
            this.fs.enterblock(blockCnt, true);
            this.gotostat(expdesc2.t.i);
            this.skipnoopstat();
            if (this.block_follow(true)) {
                this.fs.leaveblock();
                return;
            }
            n = this.fs.jump();
        } else {
            this.fs.goiftrue(expdesc2);
            this.fs.enterblock(blockCnt, true);
            n = expdesc2.f.i;
        }
        this.statlist();
        this.fs.leaveblock();
        if (this.t.token == 260 || this.t.token == 261) {
            this.fs.concat(intPtr, this.fs.jump());
        }
        this.fs.patchtohere(n);
    }

    void ifstat(int n) {
        IntPtr intPtr = new IntPtr(-1);
        this.test_then_block(intPtr);
        while (this.t.token == 261) {
            this.test_then_block(intPtr);
        }
        if (this.testnext(1)) {
            this.block();
        }
        this.check_match(262, 267, n);
        this.fs.patchtohere(intPtr.i);
    }

    void localfunc() {
        expdesc expdesc2 = new expdesc();
        FuncState funcState = this.fs;
        this.new_localvar(this.str_checkname());
        this.adjustlocalvars(1);
        this.body(expdesc2, false, this.linenumber);
        funcState.getlocvar((int)(funcState.nactvar - 1)).startpc = funcState.pc;
    }

    void localstat() {
        int n;
        int n2 = 0;
        expdesc expdesc2 = new expdesc();
        do {
            this.new_localvar(this.str_checkname());
            ++n2;
        } while (this.testnext(1));
        if (this.testnext(0)) {
            n = this.explist(expdesc2);
        } else {
            expdesc2.k = 0;
            n = 0;
        }
        this.adjust_assign(n2, n, expdesc2);
        this.adjustlocalvars(n2);
    }

    boolean funcname(expdesc expdesc2) {
        boolean bl = false;
        this.singlevar(expdesc2);
        while (this.t.token == 46) {
            this.fieldsel(expdesc2);
        }
        if (this.t.token == 58) {
            bl = true;
            this.fieldsel(expdesc2);
        }
        return bl;
    }

    void funcstat(int n) {
        expdesc expdesc2 = new expdesc();
        expdesc expdesc3 = new expdesc();
        this.next();
        boolean bl = this.funcname(expdesc2);
        this.body(expdesc3, bl, n);
        this.fs.storevar(expdesc2, expdesc3);
        this.fs.fixline(n);
    }

    void exprstat() {
        FuncState funcState = this.fs;
        LHS_assign lHS_assign = new LHS_assign();
        this.suffixedexp(lHS_assign.v);
        if (this.t.token == 61 || this.t.token == 44) {
            lHS_assign.prev = null;
            this.assignment(lHS_assign, 1);
        } else {
            this.check_condition(lHS_assign.v.k == 12, "syntax error");
            LexState.SETARG_C(funcState.getcodePtr(lHS_assign.v), 1);
        }
    }

    void retstat() {
        int n;
        int n2;
        FuncState funcState = this.fs;
        expdesc expdesc2 = new expdesc();
        if (this.block_follow(false) || this.t.token == 59) {
            n2 = 0;
            n = 0;
        } else {
            n2 = this.explist(expdesc2);
            if (this.hasmultret(expdesc2.k)) {
                funcState.setmultret(expdesc2);
                if (expdesc2.k == 12 && n2 == 1) {
                    LexState.SET_OPCODE(funcState.getcodePtr(expdesc2), 30);
                    LexState._assert(Lua.GETARG_A(funcState.getcode(expdesc2)) == funcState.nactvar);
                }
                n = funcState.nactvar;
                n2 = -1;
            } else if (n2 == 1) {
                n = funcState.exp2anyreg(expdesc2);
            } else {
                funcState.exp2nextreg(expdesc2);
                n = funcState.nactvar;
                LexState._assert(n2 == funcState.freereg - n);
            }
        }
        funcState.ret(n, n2);
        this.testnext(0);
    }

    void statement() {
        int n = this.linenumber;
        this.enterlevel();
        switch (this.t.token) {
            case 59: {
                this.next();
                break;
            }
            case 267: {
                this.ifstat(n);
                break;
            }
            case 278: {
                this.whilestat(n);
                break;
            }
            case 259: {
                this.next();
                this.block();
                this.check_match(262, 259, n);
                break;
            }
            case 264: {
                this.forstat(n);
                break;
            }
            case 273: {
                this.repeatstat(n);
                break;
            }
            case 265: {
                this.funcstat(n);
                break;
            }
            case 269: {
                this.next();
                if (this.testnext(0)) {
                    this.localfunc();
                    break;
                }
                this.localstat();
                break;
            }
            case 285: {
                this.next();
                this.labelstat(this.str_checkname(), n);
                break;
            }
            case 274: {
                this.next();
                this.retstat();
                break;
            }
            case 258: 
            case 266: {
                this.gotostat(this.fs.jump());
                break;
            }
            default: {
                this.exprstat();
            }
        }
        LexState._assert(this.fs.f.maxstacksize >= this.fs.freereg && this.fs.freereg >= this.fs.nactvar);
        this.fs.freereg = this.fs.nactvar;
        this.leavelevel();
    }

    void statlist() {
        while (!this.block_follow(false)) {
            if (this.t.token == 274) {
                this.statement();
                return;
            }
            this.statement();
        }
    }

    public void mainfunc(FuncState funcState) {
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        this.open_func(funcState, blockCnt);
        this.fs.f.is_vararg = 1;
        expdesc expdesc2 = new expdesc();
        expdesc2.init(7, 0);
        this.fs.newupvalue(this.envn, expdesc2);
        this.next();
        this.statlist();
        this.check(286);
        this.close_func();
    }

    static {
        int n;
        RESERVED_LOCAL_VAR_KEYWORDS = new String[]{RESERVED_LOCAL_VAR_FOR_CONTROL, RESERVED_LOCAL_VAR_FOR_GENERATOR, RESERVED_LOCAL_VAR_FOR_INDEX, RESERVED_LOCAL_VAR_FOR_LIMIT, RESERVED_LOCAL_VAR_FOR_STATE, RESERVED_LOCAL_VAR_FOR_STEP};
        RESERVED_LOCAL_VAR_KEYWORDS_TABLE = new Hashtable();
        for (n = 0; n < RESERVED_LOCAL_VAR_KEYWORDS.length; ++n) {
            RESERVED_LOCAL_VAR_KEYWORDS_TABLE.put(RESERVED_LOCAL_VAR_KEYWORDS[n], Boolean.TRUE);
        }
        luaX_tokens = new String[]{"and", "break", "do", "else", "elseif", "end", "false", "for", "function", "goto", "if", "in", "local", "nil", "not", "or", "repeat", "return", "then", "true", "until", "while", "..", "...", "==", ">=", "<=", "~=", "::", "<eos>", "<number>", "<name>", "<string>", "<eof>"};
        RESERVED = new Hashtable();
        for (n = 0; n < 22; ++n) {
            LuaString luaString = LuaValue.valueOf(luaX_tokens[n]);
            RESERVED.put(luaString, 257 + n);
        }
        priority = new Priority[]{new Priority(6, 6), new Priority(6, 6), new Priority(7, 7), new Priority(7, 7), new Priority(7, 7), new Priority(10, 9), new Priority(5, 4), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(2, 2), new Priority(1, 1)};
    }

    private static class Token {
        int token;
        final SemInfo seminfo = new SemInfo();

        private Token() {
        }

        public void set(Token token) {
            this.token = token.token;
            this.seminfo.r = token.seminfo.r;
            this.seminfo.ts = token.seminfo.ts;
        }
    }

    static class Dyndata {
        Vardesc[] actvar;
        int n_actvar = 0;
        Labeldesc[] gt;
        int n_gt = 0;
        Labeldesc[] label;
        int n_label = 0;

        Dyndata() {
        }
    }

    private static class SemInfo {
        LuaValue r;
        LuaString ts;

        private SemInfo() {
        }
    }

    static class expdesc {
        int k;
        final U u = new U();
        final IntPtr t = new IntPtr();
        final IntPtr f = new IntPtr();

        expdesc() {
        }

        void init(int n, int n2) {
            this.f.i = -1;
            this.t.i = -1;
            this.k = n;
            this.u.info = n2;
        }

        boolean hasjumps() {
            return this.t.i != this.f.i;
        }

        boolean isnumeral() {
            return this.k == 5 && this.t.i == -1 && this.f.i == -1;
        }

        public void setvalue(expdesc expdesc2) {
            this.f.i = expdesc2.f.i;
            this.k = expdesc2.k;
            this.t.i = expdesc2.t.i;
            this.u._nval = expdesc2.u._nval;
            this.u.ind_idx = expdesc2.u.ind_idx;
            this.u.ind_t = expdesc2.u.ind_t;
            this.u.ind_vt = expdesc2.u.ind_vt;
            this.u.info = expdesc2.u.info;
        }

        static class U {
            short ind_idx;
            short ind_t;
            short ind_vt;
            private LuaValue _nval;
            int info;

            U() {
            }

            public void setNval(LuaValue luaValue) {
                this._nval = luaValue;
            }

            public LuaValue nval() {
                return this._nval == null ? LuaInteger.valueOf(this.info) : this._nval;
            }
        }
    }

    static class Vardesc {
        final short idx;

        Vardesc(int n) {
            this.idx = (short)n;
        }
    }

    static class Labeldesc {
        LuaString name;
        int pc;
        int line;
        short nactvar;

        public Labeldesc(LuaString luaString, int n, int n2, short s) {
            this.name = luaString;
            this.pc = n;
            this.line = n2;
            this.nactvar = s;
        }
    }

    static class ConsControl {
        expdesc v = new expdesc();
        expdesc t;
        int nh;
        int na;
        int tostore;

        ConsControl() {
        }
    }

    static class Priority {
        final byte left;
        final byte right;

        public Priority(int n, int n2) {
            this.left = (byte)n;
            this.right = (byte)n2;
        }
    }

    static class LHS_assign {
        LHS_assign prev;
        expdesc v = new expdesc();

        LHS_assign() {
        }
    }
}

