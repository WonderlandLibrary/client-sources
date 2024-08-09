/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.BaseLib;

public class JseBaseLib
extends BaseLib {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        super.call(luaValue, luaValue2);
        luaValue2.checkglobals().STDIN = System.in;
        return luaValue2;
    }

    @Override
    public InputStream findResource(String string) {
        File file = new File(string);
        if (!file.exists()) {
            return super.findResource(string);
        }
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException iOException) {
            return null;
        }
    }
}

