/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.io.IOException;
import java.io.InputStream;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.OneArgFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class PackageLib
extends TwoArgFunction {
    public static final String DEFAULT_LUA_PATH;
    static final LuaString _LOADED;
    private static final LuaString _LOADLIB;
    static final LuaString _PRELOAD;
    static final LuaString _PATH;
    static final LuaString _SEARCHPATH;
    static final LuaString _SEARCHERS;
    Globals globals;
    LuaTable package_;
    public preload_searcher preload_searcher;
    public lua_searcher lua_searcher;
    public java_searcher java_searcher;
    private static final LuaString _SENTINEL;
    private static final String FILE_SEP;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        this.globals.set("require", (LuaValue)new require(this));
        this.package_ = new LuaTable();
        this.package_.set(_LOADED, (LuaValue)new LuaTable());
        this.package_.set(_PRELOAD, (LuaValue)new LuaTable());
        this.package_.set(_PATH, (LuaValue)LuaValue.valueOf(DEFAULT_LUA_PATH));
        this.package_.set(_LOADLIB, (LuaValue)new loadlib());
        this.package_.set(_SEARCHPATH, (LuaValue)new searchpath(this));
        LuaTable luaTable = new LuaTable();
        this.preload_searcher = new preload_searcher(this);
        luaTable.set(1, (LuaValue)this.preload_searcher);
        this.lua_searcher = new lua_searcher(this);
        luaTable.set(2, (LuaValue)this.lua_searcher);
        this.java_searcher = new java_searcher(this);
        luaTable.set(3, (LuaValue)this.java_searcher);
        this.package_.set(_SEARCHERS, (LuaValue)luaTable);
        this.package_.set("config", FILE_SEP + "\n;\n?\n!\n-\n");
        this.package_.get(_LOADED).set("package", (LuaValue)this.package_);
        luaValue2.set("package", (LuaValue)this.package_);
        this.globals.package_ = this;
        return luaValue2;
    }

    public void setIsLoaded(String string, LuaTable luaTable) {
        this.package_.get(_LOADED).set(string, (LuaValue)luaTable);
    }

    public void setLuaPath(String string) {
        this.package_.set(_PATH, (LuaValue)LuaValue.valueOf(string));
    }

    @Override
    public String tojstring() {
        return "package";
    }

    public static final String toClassname(String string) {
        int n;
        int n2 = n = string.length();
        if (string.endsWith(".lua")) {
            n2 -= 4;
        }
        for (int i = 0; i < n2; ++i) {
            int n3 = string.charAt(i);
            if (PackageLib.isClassnamePart((char)n3) && n3 != 47 && n3 != 92) continue;
            StringBuffer stringBuffer = new StringBuffer(n2);
            for (int j = 0; j < n2; ++j) {
                n3 = string.charAt(j);
                stringBuffer.append((char)(PackageLib.isClassnamePart((char)n3) ? n3 : (n3 == 47 || n3 == 92 ? 46 : 95)));
            }
            return stringBuffer.toString();
        }
        return n == n2 ? string : string.substring(0, n2);
    }

    private static final boolean isClassnamePart(char c) {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
            return false;
        }
        switch (c) {
            case '$': 
            case '.': 
            case '_': {
                return false;
            }
        }
        return true;
    }

    static {
        String string = null;
        try {
            string = System.getProperty("luaj.package.path");
        } catch (Exception exception) {
            System.out.println(exception);
        }
        if (string == null) {
            string = "?.lua";
        }
        DEFAULT_LUA_PATH = string;
        _LOADED = PackageLib.valueOf("loaded");
        _LOADLIB = PackageLib.valueOf("loadlib");
        _PRELOAD = PackageLib.valueOf("preload");
        _PATH = PackageLib.valueOf("path");
        _SEARCHPATH = PackageLib.valueOf("searchpath");
        _SEARCHERS = PackageLib.valueOf("searchers");
        _SENTINEL = PackageLib.valueOf("\u0001");
        FILE_SEP = System.getProperty("file.separator");
    }

    public class require
    extends OneArgFunction {
        final PackageLib this$0;

        public require(PackageLib packageLib) {
            this.this$0 = packageLib;
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaString luaString = luaValue.checkstring();
            LuaValue luaValue2 = this.this$0.package_.get(_LOADED);
            LuaValue luaValue3 = luaValue2.get(luaString);
            if (luaValue3.toboolean()) {
                if (luaValue3 == _SENTINEL) {
                    require.error("loop or previous error loading module '" + luaString + "'");
                }
                return luaValue3;
            }
            LuaTable luaTable = this.this$0.package_.get(_SEARCHERS).checktable();
            StringBuffer stringBuffer = new StringBuffer();
            Varargs varargs = null;
            int n = 1;
            while (true) {
                LuaValue luaValue4;
                if ((luaValue4 = luaTable.get(n)).isnil()) {
                    require.error("module '" + luaString + "' not found: " + luaString + stringBuffer);
                }
                if ((varargs = luaValue4.invoke(luaString)).isfunction(0)) break;
                if (varargs.isstring(0)) {
                    stringBuffer.append(varargs.tojstring(1));
                }
                ++n;
            }
            luaValue2.set(luaString, (LuaValue)_SENTINEL);
            luaValue3 = varargs.arg1().call(luaString, varargs.arg(2));
            if (!luaValue3.isnil()) {
                luaValue2.set(luaString, luaValue3);
            } else {
                luaValue3 = luaValue2.get(luaString);
                if (luaValue3 == _SENTINEL) {
                    luaValue3 = LuaValue.TRUE;
                    luaValue2.set(luaString, luaValue3);
                }
            }
            return luaValue3;
        }
    }

    public static class loadlib
    extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs varargs) {
            varargs.checkstring(1);
            return loadlib.varargsOf(NIL, loadlib.valueOf("dynamic libraries not enabled"), loadlib.valueOf("absent"));
        }
    }

    public class searchpath
    extends VarArgFunction {
        final PackageLib this$0;

        public searchpath(PackageLib packageLib) {
            this.this$0 = packageLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            String string = varargs.checkjstring(1);
            String string2 = varargs.checkjstring(2);
            String string3 = varargs.optjstring(3, ".");
            String string4 = varargs.optjstring(4, FILE_SEP);
            int n = -1;
            int n2 = string2.length();
            StringBuffer stringBuffer = null;
            string = string.replace(string3.charAt(0), string4.charAt(0));
            while (n < n2) {
                InputStream inputStream;
                int n3 = n + 1;
                if ((n = string2.indexOf(59, n3)) < 0) {
                    n = string2.length();
                }
                String string5 = string2.substring(n3, n);
                int n4 = string5.indexOf(63);
                Object object = string5;
                if (n4 >= 0) {
                    object = string5.substring(0, n4) + string + string5.substring(n4 + 1);
                }
                if ((inputStream = this.this$0.globals.finder.findResource((String)object)) != null) {
                    try {
                        inputStream.close();
                    } catch (IOException iOException) {
                        // empty catch block
                    }
                    return searchpath.valueOf((String)object);
                }
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.append("\n\t" + (String)object);
            }
            return searchpath.varargsOf(NIL, (Varargs)searchpath.valueOf(stringBuffer.toString()));
        }
    }

    public class preload_searcher
    extends VarArgFunction {
        final PackageLib this$0;

        public preload_searcher(PackageLib packageLib) {
            this.this$0 = packageLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            LuaValue luaValue = this.this$0.package_.get(_PRELOAD).get(luaString);
            return luaValue.isnil() ? mpp.venusfr.scripts.interpreter.lib.PackageLib$preload_searcher.valueOf("\n\tno field package.preload['" + luaString + "']") : luaValue;
        }
    }

    public class lua_searcher
    extends VarArgFunction {
        final PackageLib this$0;

        public lua_searcher(PackageLib packageLib) {
            this.this$0 = packageLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaString luaString = varargs.checkstring(1);
            LuaValue luaValue = this.this$0.package_.get(_PATH);
            if (!luaValue.isstring()) {
                return mpp.venusfr.scripts.interpreter.lib.PackageLib$lua_searcher.valueOf("package.path is not a string");
            }
            Varargs varargs2 = this.this$0.package_.get(_SEARCHPATH).invoke(mpp.venusfr.scripts.interpreter.lib.PackageLib$lua_searcher.varargsOf(luaString, (Varargs)luaValue));
            if (!varargs2.isstring(0)) {
                return varargs2.arg(2).tostring();
            }
            LuaString luaString2 = varargs2.arg1().strvalue();
            if ((varargs2 = this.this$0.globals.loadfile(luaString2.tojstring())).arg1().isfunction()) {
                return LuaValue.varargsOf(varargs2.arg1(), (Varargs)luaString2);
            }
            return mpp.venusfr.scripts.interpreter.lib.PackageLib$lua_searcher.varargsOf(NIL, (Varargs)mpp.venusfr.scripts.interpreter.lib.PackageLib$lua_searcher.valueOf("'" + luaString2 + "': " + varargs2.arg(2).tojstring()));
        }
    }

    public class java_searcher
    extends VarArgFunction {
        final PackageLib this$0;

        public java_searcher(PackageLib packageLib) {
            this.this$0 = packageLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            String string = varargs.checkjstring(1);
            String string2 = PackageLib.toClassname(string);
            Class<?> clazz = null;
            LuaValue luaValue = null;
            try {
                clazz = Class.forName(string2);
                luaValue = (LuaValue)clazz.newInstance();
                if (luaValue.isfunction()) {
                    luaValue.initupvalue1(this.this$0.globals);
                }
                return mpp.venusfr.scripts.interpreter.lib.PackageLib$java_searcher.varargsOf(luaValue, (Varargs)this.this$0.globals);
            } catch (ClassNotFoundException classNotFoundException) {
                return mpp.venusfr.scripts.interpreter.lib.PackageLib$java_searcher.valueOf("\n\tno class '" + string2 + "'");
            } catch (Exception exception) {
                return mpp.venusfr.scripts.interpreter.lib.PackageLib$java_searcher.valueOf("\n\tjava load failed on '" + string2 + "', " + exception);
            }
        }
    }
}

