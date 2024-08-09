/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitch
extends ShaderOption {
    private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
    private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");

    public ShaderOptionSwitch(String string, String string2, String string3, String string4) {
        super(string, string2, string3, new String[]{"false", "true"}, string3, string4);
    }

    @Override
    public String getSourceLine() {
        return ShaderOptionSwitch.isTrue(this.getValue()) ? "#define " + this.getName() + " // Shader option ON" : "//#define " + this.getName() + " // Shader option OFF";
    }

    @Override
    public String getValueText(String string) {
        String string2 = super.getValueText(string);
        if (string2 != string) {
            return string2;
        }
        return ShaderOptionSwitch.isTrue(string) ? Lang.getOn() : Lang.getOff();
    }

    @Override
    public String getValueColor(String string) {
        return ShaderOptionSwitch.isTrue(string) ? "\u00a7a" : "\u00a7c";
    }

    public static ShaderOption parseOption(String string, String string2) {
        Matcher matcher = PATTERN_DEFINE.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string3 = matcher.group(1);
        String string4 = matcher.group(2);
        String string5 = matcher.group(3);
        if (string4 != null && string4.length() > 0) {
            boolean bl = Config.equals(string3, "//");
            boolean bl2 = !bl;
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            ShaderOptionSwitch shaderOptionSwitch = new ShaderOptionSwitch(string4, string5, String.valueOf(bl2), string2);
            return shaderOptionSwitch;
        }
        return null;
    }

    @Override
    public boolean matchesLine(String string) {
        Matcher matcher = PATTERN_DEFINE.matcher(string);
        if (!matcher.matches()) {
            return true;
        }
        String string2 = matcher.group(2);
        return string2.matches(this.getName());
    }

    @Override
    public boolean checkUsed() {
        return false;
    }

    @Override
    public boolean isUsedInLine(String string) {
        String string2;
        Matcher matcher = PATTERN_IFDEF.matcher(string);
        return !matcher.matches() || !(string2 = matcher.group(2)).equals(this.getName());
    }

    public static boolean isTrue(String string) {
        return Boolean.valueOf(string);
    }
}

