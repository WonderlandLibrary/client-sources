/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionSwitch;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitchConst
extends ShaderOptionSwitch {
    private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");

    public ShaderOptionSwitchConst(String string, String string2, String string3, String string4) {
        super(string, string2, string3, string4);
    }

    @Override
    public String getSourceLine() {
        return "const bool " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
    }

    public static ShaderOption parseOption(String string, String string2) {
        Matcher matcher = PATTERN_CONST.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string3 = matcher.group(1);
        String string4 = matcher.group(2);
        String string5 = matcher.group(3);
        if (string3 != null && string3.length() > 0) {
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            ShaderOptionSwitchConst shaderOptionSwitchConst = new ShaderOptionSwitchConst(string3, string5, string4, string2);
            shaderOptionSwitchConst.setVisible(true);
            return shaderOptionSwitchConst;
        }
        return null;
    }

    @Override
    public boolean matchesLine(String string) {
        Matcher matcher = PATTERN_CONST.matcher(string);
        if (!matcher.matches()) {
            return true;
        }
        String string2 = matcher.group(1);
        return string2.matches(this.getName());
    }

    @Override
    public boolean checkUsed() {
        return true;
    }
}

