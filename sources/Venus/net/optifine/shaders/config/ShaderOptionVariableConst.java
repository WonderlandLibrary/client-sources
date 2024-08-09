/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionVariable;
import net.optifine.util.StrUtils;

public class ShaderOptionVariableConst
extends ShaderOptionVariable {
    private String type = null;
    private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");

    public ShaderOptionVariableConst(String string, String string2, String string3, String string4, String[] stringArray, String string5) {
        super(string, string3, string4, stringArray, string5);
        this.type = string2;
    }

    @Override
    public String getSourceLine() {
        return "const " + this.type + " " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
    }

    @Override
    public boolean matchesLine(String string) {
        Matcher matcher = PATTERN_CONST.matcher(string);
        if (!matcher.matches()) {
            return true;
        }
        String string2 = matcher.group(2);
        return string2.matches(this.getName());
    }

    public static ShaderOption parseOption(String string, String string2) {
        Matcher matcher = PATTERN_CONST.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string3 = matcher.group(1);
        String string4 = matcher.group(2);
        String string5 = matcher.group(3);
        String string6 = matcher.group(4);
        String string7 = StrUtils.getSegment(string6, "[", "]");
        if (string7 != null && string7.length() > 0) {
            string6 = string6.replace(string7, "").trim();
        }
        String[] stringArray = ShaderOptionVariableConst.parseValues(string5, string7);
        if (string4 != null && string4.length() > 0) {
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            ShaderOptionVariableConst shaderOptionVariableConst = new ShaderOptionVariableConst(string4, string3, string6, string5, stringArray, string2);
            return shaderOptionVariableConst;
        }
        return null;
    }
}

