/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.util.StrUtils;

public class ShaderOptionVariable
extends ShaderOption {
    private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");

    public ShaderOptionVariable(String string, String string2, String string3, String[] stringArray, String string4) {
        super(string, string2, string3, stringArray, string3, string4);
        this.setVisible(this.getValues().length > 1);
    }

    @Override
    public String getSourceLine() {
        return "#define " + this.getName() + " " + this.getValue() + " // Shader option " + this.getValue();
    }

    @Override
    public String getValueText(String string) {
        String string2 = Shaders.translate("prefix." + this.getName(), "");
        String string3 = super.getValueText(string);
        String string4 = Shaders.translate("suffix." + this.getName(), "");
        return string2 + string3 + string4;
    }

    @Override
    public String getValueColor(String string) {
        String string2 = string.toLowerCase();
        return !string2.equals("false") && !string2.equals("off") ? "\u00a7a" : "\u00a7c";
    }

    @Override
    public boolean matchesLine(String string) {
        Matcher matcher = PATTERN_VARIABLE.matcher(string);
        if (!matcher.matches()) {
            return true;
        }
        String string2 = matcher.group(1);
        return string2.matches(this.getName());
    }

    public static ShaderOption parseOption(String string, String string2) {
        Matcher matcher = PATTERN_VARIABLE.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string3 = matcher.group(1);
        String string4 = matcher.group(2);
        String string5 = matcher.group(3);
        String string6 = StrUtils.getSegment(string5, "[", "]");
        if (string6 != null && string6.length() > 0) {
            string5 = string5.replace(string6, "").trim();
        }
        String[] stringArray = ShaderOptionVariable.parseValues(string4, string6);
        if (string3 != null && string3.length() > 0) {
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            ShaderOptionVariable shaderOptionVariable = new ShaderOptionVariable(string3, string5, string4, stringArray, string2);
            return shaderOptionVariable;
        }
        return null;
    }

    public static String[] parseValues(String string, String string2) {
        String[] stringArray = new String[]{string};
        if (string2 == null) {
            return stringArray;
        }
        string2 = string2.trim();
        string2 = StrUtils.removePrefix(string2, "[");
        string2 = StrUtils.removeSuffix(string2, "]");
        if ((string2 = string2.trim()).length() <= 0) {
            return stringArray;
        }
        Object[] objectArray = Config.tokenize(string2, " ");
        if (objectArray.length <= 0) {
            return stringArray;
        }
        if (!Arrays.asList(objectArray).contains(string)) {
            objectArray = (String[])Config.addObjectToArray(objectArray, string, 0);
        }
        return objectArray;
    }
}

