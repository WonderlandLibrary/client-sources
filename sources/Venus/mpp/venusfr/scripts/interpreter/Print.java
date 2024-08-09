/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import mpp.venusfr.scripts.interpreter.Lua;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Upvaldesc;
import mpp.venusfr.scripts.interpreter.Varargs;

public class Print
extends Lua {
    private static final String STRING_FOR_NULL = "null";
    public static PrintStream ps = System.out;
    public static final String[] OPNAMES = new String[]{"MOVE", "LOADK", "LOADKX", "LOADBOOL", "LOADNIL", "GETUPVAL", "GETTABUP", "GETTABLE", "SETTABUP", "SETUPVAL", "SETTABLE", "NEWTABLE", "SELF", "ADD", "SUB", "MUL", "DIV", "MOD", "POW", "UNM", "NOT", "LEN", "CONCAT", "JMP", "EQ", "LT", "LE", "TEST", "TESTSET", "CALL", "TAILCALL", "RETURN", "FORLOOP", "FORPREP", "TFORCALL", "TFORLOOP", "SETLIST", "CLOSURE", "VARARG", "EXTRAARG", null};

    static void printString(PrintStream printStream, LuaString luaString) {
        printStream.print('\"');
        int n = luaString.m_length;
        block11: for (int i = 0; i < n; ++i) {
            byte by = luaString.m_bytes[luaString.m_offset + i];
            if (by >= 32 && by <= 126 && by != 34 && by != 92) {
                printStream.print((char)by);
                continue;
            }
            switch (by) {
                case 34: {
                    printStream.print("\\\"");
                    continue block11;
                }
                case 92: {
                    printStream.print("\\\\");
                    continue block11;
                }
                case 7: {
                    printStream.print("\\a");
                    continue block11;
                }
                case 8: {
                    printStream.print("\\b");
                    continue block11;
                }
                case 12: {
                    printStream.print("\\f");
                    continue block11;
                }
                case 9: {
                    printStream.print("\\t");
                    continue block11;
                }
                case 13: {
                    printStream.print("\\r");
                    continue block11;
                }
                case 10: {
                    printStream.print("\\n");
                    continue block11;
                }
                case 11: {
                    printStream.print("\\v");
                    continue block11;
                }
                default: {
                    printStream.print('\\');
                    printStream.print(Integer.toString(0x4E7 & by).substring(1));
                }
            }
        }
        printStream.print('\"');
    }

    static void printValue(PrintStream printStream, LuaValue luaValue) {
        if (luaValue == null) {
            printStream.print(STRING_FOR_NULL);
            return;
        }
        switch (luaValue.type()) {
            case 4: {
                Print.printString(printStream, (LuaString)luaValue);
                break;
            }
            default: {
                printStream.print(luaValue.tojstring());
            }
        }
    }

    static void printConstant(PrintStream printStream, Prototype prototype, int n) {
        Print.printValue(printStream, n < prototype.k.length ? prototype.k[n] : LuaValue.valueOf("UNKNOWN_CONST_" + n));
    }

    static void printUpvalue(PrintStream printStream, Upvaldesc upvaldesc) {
        printStream.print(upvaldesc.idx + " ");
        Print.printValue(printStream, upvaldesc.name);
    }

    public static void printCode(Prototype prototype) {
        int[] nArray = prototype.code;
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            i = Print.printOpCode(prototype, i);
            ps.println();
        }
    }

    public static int printOpCode(Prototype prototype, int n) {
        return Print.printOpCode(ps, prototype, n);
    }

    public static int printOpCode(PrintStream printStream, Prototype prototype, int n) {
        int[] nArray = prototype.code;
        int n2 = nArray[n];
        int n3 = Print.GET_OPCODE(n2);
        int n4 = Print.GETARG_A(n2);
        int n5 = Print.GETARG_B(n2);
        int n6 = Print.GETARG_C(n2);
        int n7 = Print.GETARG_Bx(n2);
        int n8 = Print.GETARG_sBx(n2);
        int n9 = Print.getline(prototype, n);
        printStream.print("  " + (n + 1) + "  ");
        if (n9 > 0) {
            printStream.print("[" + n9 + "]  ");
        } else {
            printStream.print("[-]  ");
        }
        if (n3 >= OPNAMES.length - 1) {
            printStream.print("UNKNOWN_OP_" + n3 + "  ");
        } else {
            printStream.print(OPNAMES[n3] + "  ");
            switch (Print.getOpMode(n3)) {
                case 0: {
                    printStream.print(n4);
                    if (Print.getBMode(n3) != 0) {
                        printStream.print(" " + (Print.ISK(n5) ? -1 - Print.INDEXK(n5) : n5));
                    }
                    if (Print.getCMode(n3) == 0) break;
                    printStream.print(" " + (Print.ISK(n6) ? -1 - Print.INDEXK(n6) : n6));
                    break;
                }
                case 1: {
                    if (Print.getBMode(n3) == 3) {
                        printStream.print(n4 + " " + (-1 - n7));
                        break;
                    }
                    printStream.print(n4 + " " + n7);
                    break;
                }
                case 2: {
                    if (n3 == 23) {
                        printStream.print(n8);
                        break;
                    }
                    printStream.print(n4 + " " + n8);
                }
            }
            switch (n3) {
                case 1: {
                    printStream.print("  ; ");
                    Print.printConstant(printStream, prototype, n7);
                    break;
                }
                case 5: 
                case 9: {
                    printStream.print("  ; ");
                    if (n5 < prototype.upvalues.length) {
                        Print.printUpvalue(printStream, prototype.upvalues[n5]);
                        break;
                    }
                    printStream.print("UNKNOWN_UPVALUE_" + n5);
                    break;
                }
                case 6: {
                    printStream.print("  ; ");
                    if (n5 < prototype.upvalues.length) {
                        Print.printUpvalue(printStream, prototype.upvalues[n5]);
                    } else {
                        printStream.print("UNKNOWN_UPVALUE_" + n5);
                    }
                    printStream.print(" ");
                    if (Print.ISK(n6)) {
                        Print.printConstant(printStream, prototype, Print.INDEXK(n6));
                        break;
                    }
                    printStream.print("-");
                    break;
                }
                case 8: {
                    printStream.print("  ; ");
                    if (n4 < prototype.upvalues.length) {
                        Print.printUpvalue(printStream, prototype.upvalues[n4]);
                    } else {
                        printStream.print("UNKNOWN_UPVALUE_" + n4);
                    }
                    printStream.print(" ");
                    if (Print.ISK(n5)) {
                        Print.printConstant(printStream, prototype, Print.INDEXK(n5));
                    } else {
                        printStream.print("-");
                    }
                    printStream.print(" ");
                    if (Print.ISK(n6)) {
                        Print.printConstant(printStream, prototype, Print.INDEXK(n6));
                        break;
                    }
                    printStream.print("-");
                    break;
                }
                case 7: 
                case 12: {
                    if (!Print.ISK(n6)) break;
                    printStream.print("  ; ");
                    Print.printConstant(printStream, prototype, Print.INDEXK(n6));
                    break;
                }
                case 10: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 18: 
                case 24: 
                case 25: 
                case 26: {
                    if (!Print.ISK(n5) && !Print.ISK(n6)) break;
                    printStream.print("  ; ");
                    if (Print.ISK(n5)) {
                        Print.printConstant(printStream, prototype, Print.INDEXK(n5));
                    } else {
                        printStream.print("-");
                    }
                    printStream.print(" ");
                    if (Print.ISK(n6)) {
                        Print.printConstant(printStream, prototype, Print.INDEXK(n6));
                        break;
                    }
                    printStream.print("-");
                    break;
                }
                case 23: 
                case 32: 
                case 33: {
                    printStream.print("  ; to " + (n8 + n + 2));
                    break;
                }
                case 37: {
                    if (n7 < prototype.p.length) {
                        printStream.print("  ; " + prototype.p[n7].getClass().getName());
                        break;
                    }
                    printStream.print("  ; UNKNOWN_PROTYPE_" + n7);
                    break;
                }
                case 36: {
                    if (n6 == 0) {
                        printStream.print("  ; " + nArray[++n] + " (stored in the next OP)");
                        break;
                    }
                    printStream.print("  ; " + n6);
                    break;
                }
                case 38: {
                    printStream.print("  ; is_vararg=" + prototype.is_vararg);
                    break;
                }
            }
        }
        return n;
    }

    private static int getline(Prototype prototype, int n) {
        return n > 0 && prototype.lineinfo != null && n < prototype.lineinfo.length ? prototype.lineinfo[n] : -1;
    }

    static void printHeader(Prototype prototype) {
        String string = String.valueOf(prototype.source);
        string = string.startsWith("@") || string.startsWith("=") ? string.substring(1) : ("\u001bLua".equals(string) ? "(bstring)" : "(string)");
        String string2 = prototype.linedefined == 0 ? "main" : "function";
        ps.print("\n%" + string2 + " <" + string + ":" + prototype.linedefined + "," + prototype.lastlinedefined + "> (" + prototype.code.length + " instructions, " + prototype.code.length * 4 + " bytes at " + Print.id(prototype) + ")\n");
        ps.print(prototype.numparams + " param, " + prototype.maxstacksize + " slot, " + prototype.upvalues.length + " upvalue, ");
        ps.print(prototype.locvars.length + " local, " + prototype.k.length + " constant, " + prototype.p.length + " function\n");
    }

    static void printConstants(Prototype prototype) {
        int n = prototype.k.length;
        ps.print("constants (" + n + ") for " + Print.id(prototype) + ":\n");
        for (int i = 0; i < n; ++i) {
            ps.print("  " + (i + 1) + "  ");
            Print.printValue(ps, prototype.k[i]);
            ps.print("\n");
        }
    }

    static void printLocals(Prototype prototype) {
        int n = prototype.locvars.length;
        ps.print("locals (" + n + ") for " + Print.id(prototype) + ":\n");
        for (int i = 0; i < n; ++i) {
            ps.println("  " + i + "  " + prototype.locvars[i].varname + " " + (prototype.locvars[i].startpc + 1) + " " + (prototype.locvars[i].endpc + 1));
        }
    }

    static void printUpValues(Prototype prototype) {
        int n = prototype.upvalues.length;
        ps.print("upvalues (" + n + ") for " + Print.id(prototype) + ":\n");
        for (int i = 0; i < n; ++i) {
            ps.print("  " + i + "  " + prototype.upvalues[i] + "\n");
        }
    }

    public static void print(Prototype prototype) {
        Print.printFunction(prototype, true);
    }

    public static void printFunction(Prototype prototype, boolean bl) {
        int n = prototype.p.length;
        Print.printHeader(prototype);
        Print.printCode(prototype);
        if (bl) {
            Print.printConstants(prototype);
            Print.printLocals(prototype);
            Print.printUpValues(prototype);
        }
        for (int i = 0; i < n; ++i) {
            Print.printFunction(prototype.p[i], bl);
        }
    }

    private static void format(String string, int n) {
        int n2 = string.length();
        if (n2 > n) {
            ps.print(string.substring(0, n));
        } else {
            ps.print(string);
            int n3 = n - n2;
            while (--n3 >= 0) {
                ps.print(' ');
            }
        }
    }

    private static String id(Prototype prototype) {
        return "Proto";
    }

    private void _assert(boolean bl) {
        if (!bl) {
            throw new NullPointerException("_assert failed");
        }
    }

    public static void printState(LuaClosure luaClosure, int n, LuaValue[] luaValueArray, int n2, Varargs varargs) {
        PrintStream printStream = ps;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        Print.printOpCode(luaClosure.p, n);
        ps.flush();
        ps.close();
        ps = printStream;
        Print.format(byteArrayOutputStream.toString(), 50);
        Print.printStack(luaValueArray, n2, varargs);
        ps.println();
    }

    public static void printStack(LuaValue[] luaValueArray, int n, Varargs varargs) {
        ps.print('[');
        for (int i = 0; i < luaValueArray.length; ++i) {
            LuaValue luaValue = luaValueArray[i];
            if (luaValue == null) {
                ps.print(STRING_FOR_NULL);
            } else {
                switch (luaValue.type()) {
                    case 4: {
                        LuaString luaString = luaValue.checkstring();
                        ps.print((String)(luaString.length() < 48 ? luaString.tojstring() : luaString.substring(0, 32).tojstring() + "...+" + (luaString.length() - 32) + "b"));
                        break;
                    }
                    case 6: {
                        ps.print(luaValue.tojstring());
                        break;
                    }
                    case 7: {
                        Object object = luaValue.touserdata();
                        if (object != null) {
                            String string = object.getClass().getName();
                            string = string.substring(string.lastIndexOf(46) + 1);
                            ps.print(string + ": " + Integer.toHexString(object.hashCode()));
                            break;
                        }
                        ps.print(luaValue);
                        break;
                    }
                    default: {
                        ps.print(luaValue.tojstring());
                    }
                }
            }
            if (i + 1 == n) {
                ps.print(']');
            }
            ps.print(" | ");
        }
        ps.print(varargs);
    }
}

