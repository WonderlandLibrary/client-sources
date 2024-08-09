/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.Arrays;
import java.util.List;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;

public abstract class ShaderOption {
    private String name = null;
    private String description = null;
    private String value = null;
    private String[] values = null;
    private String valueDefault = null;
    private String[] paths = null;
    private boolean enabled = true;
    private boolean visible = true;
    public static final String COLOR_GREEN = "\u00a7a";
    public static final String COLOR_RED = "\u00a7c";
    public static final String COLOR_BLUE = "\u00a79";

    public ShaderOption(String string, String string2, String string3, String[] stringArray, String string4, String string5) {
        this.name = string;
        this.description = string2;
        this.value = string3;
        this.values = stringArray;
        this.valueDefault = string4;
        if (string5 != null) {
            this.paths = new String[]{string5};
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionText() {
        String string = Config.normalize(this.description);
        string = StrUtils.removePrefix(string, "//");
        return Shaders.translate("option." + this.getName() + ".comment", string);
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public String getValue() {
        return this.value;
    }

    public boolean setValue(String string) {
        int n = ShaderOption.getIndex(string, this.values);
        if (n < 0) {
            return true;
        }
        this.value = string;
        return false;
    }

    public String getValueDefault() {
        return this.valueDefault;
    }

    public void resetValue() {
        this.value = this.valueDefault;
    }

    public void nextValue() {
        int n = ShaderOption.getIndex(this.value, this.values);
        if (n >= 0) {
            n = (n + 1) % this.values.length;
            this.value = this.values[n];
        }
    }

    public void prevValue() {
        int n = ShaderOption.getIndex(this.value, this.values);
        if (n >= 0) {
            n = (n - 1 + this.values.length) % this.values.length;
            this.value = this.values[n];
        }
    }

    private static int getIndex(String string, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (!string2.equals(string)) continue;
            return i;
        }
        return 1;
    }

    public String[] getPaths() {
        return this.paths;
    }

    public void addPaths(String[] stringArray) {
        List<String> list = Arrays.asList(this.paths);
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (list.contains(string)) continue;
            this.paths = (String[])Config.addObjectToArray(this.paths, string);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    public boolean isChanged() {
        return !Config.equals(this.value, this.valueDefault);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    public boolean isValidValue(String string) {
        return ShaderOption.getIndex(string, this.values) >= 0;
    }

    public String getNameText() {
        return Shaders.translate("option." + this.name, this.name);
    }

    public String getValueText(String string) {
        return Shaders.translate("value." + this.name + "." + string, string);
    }

    public String getValueColor(String string) {
        return "";
    }

    public boolean matchesLine(String string) {
        return true;
    }

    public boolean checkUsed() {
        return true;
    }

    public boolean isUsedInLine(String string) {
        return true;
    }

    public String getSourceLine() {
        return null;
    }

    public String[] getValues() {
        return (String[])this.values.clone();
    }

    public float getIndexNormalized() {
        if (this.values.length <= 1) {
            return 0.0f;
        }
        int n = ShaderOption.getIndex(this.value, this.values);
        return n < 0 ? 0.0f : 1.0f * (float)n / ((float)this.values.length - 1.0f);
    }

    public void setIndexNormalized(float f) {
        if (this.values.length > 1) {
            f = Config.limit(f, 0.0f, 1.0f);
            int n = Math.round(f * (float)(this.values.length - 1));
            this.value = this.values[n];
        }
    }

    public String toString() {
        return this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString(this.paths);
    }
}

