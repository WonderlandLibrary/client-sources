/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ShaderLine {
    private Type type;
    private String name;
    private String value;
    private String line;

    public ShaderLine(Type type, String string, String string2, String string3) {
        this.type = type;
        this.name = string;
        this.value = string2;
        this.line = string3;
    }

    public Type getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isUniform() {
        return this.type == Type.UNIFORM;
    }

    public boolean isUniform(String string) {
        return this.isUniform() && string.equals(this.name);
    }

    public boolean isAttribute() {
        return this.type == Type.ATTRIBUTE;
    }

    public boolean isAttribute(String string) {
        return this.isAttribute() && string.equals(this.name);
    }

    public boolean isProperty() {
        return this.type == Type.PROPERTY;
    }

    public boolean isConstInt() {
        return this.type == Type.CONST_INT;
    }

    public boolean isConstFloat() {
        return this.type == Type.CONST_FLOAT;
    }

    public boolean isConstBool() {
        return this.type == Type.CONST_BOOL;
    }

    public boolean isExtension() {
        return this.type == Type.EXTENSION;
    }

    public boolean isConstVec2() {
        return this.type == Type.CONST_VEC2;
    }

    public boolean isConstVec4() {
        return this.type == Type.CONST_VEC4;
    }

    public boolean isConstIVec3() {
        return this.type == Type.CONST_IVEC3;
    }

    public boolean isLayout() {
        return this.type == Type.LAYOUT;
    }

    public boolean isLayout(String string) {
        return this.isLayout() && string.equals(this.name);
    }

    public boolean isProperty(String string) {
        return this.isProperty() && string.equals(this.name);
    }

    public boolean isProperty(String string, String string2) {
        return this.isProperty(string) && string2.equals(this.value);
    }

    public boolean isConstInt(String string) {
        return this.isConstInt() && string.equals(this.name);
    }

    public boolean isConstIntSuffix(String string) {
        return this.isConstInt() && this.name.endsWith(string);
    }

    public boolean isConstIVec3(String string) {
        return this.isConstIVec3() && string.equals(this.name);
    }

    public boolean isConstFloat(String string) {
        return this.isConstFloat() && string.equals(this.name);
    }

    public boolean isConstBool(String string) {
        return this.isConstBool() && string.equals(this.name);
    }

    public boolean isExtension(String string) {
        return this.isExtension() && string.equals(this.name);
    }

    public boolean isConstBoolSuffix(String string) {
        return this.isConstBool() && this.name.endsWith(string);
    }

    public boolean isConstBoolSuffix(String string, boolean bl) {
        return this.isConstBoolSuffix(string) && this.getValueBool() == bl;
    }

    public boolean isConstBool(String string, String string2) {
        return this.isConstBool(string) || this.isConstBool(string2);
    }

    public boolean isConstBool(String string, String string2, String string3) {
        return this.isConstBool(string) || this.isConstBool(string2) || this.isConstBool(string3);
    }

    public boolean isConstBool(String string, boolean bl) {
        return this.isConstBool(string) && this.getValueBool() == bl;
    }

    public boolean isConstBool(String string, String string2, boolean bl) {
        return this.isConstBool(string, string2) && this.getValueBool() == bl;
    }

    public boolean isConstBool(String string, String string2, String string3, boolean bl) {
        return this.isConstBool(string, string2, string3) && this.getValueBool() == bl;
    }

    public boolean isConstVec2(String string) {
        return this.isConstVec2() && string.equals(this.name);
    }

    public boolean isConstVec4Suffix(String string) {
        return this.isConstVec4() && this.name.endsWith(string);
    }

    public int getValueInt() {
        try {
            return Integer.parseInt(this.value);
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
        }
    }

    public float getValueFloat() {
        try {
            return Float.parseFloat(this.value);
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
        }
    }

    public Vector3i getValueIVec3() {
        if (this.value == null) {
            return null;
        }
        String string = this.value.trim();
        string = StrUtils.removePrefix(string, "ivec3");
        String[] stringArray = Config.tokenize(string = StrUtils.trim(string, " ()"), ", ");
        if (stringArray.length != 3) {
            return null;
        }
        int[] nArray = new int[3];
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            int n = Config.parseInt(string2, Integer.MIN_VALUE);
            if (n == Integer.MIN_VALUE) {
                return null;
            }
            nArray[i] = n;
        }
        return new Vector3i(nArray[0], nArray[1], nArray[2]);
    }

    public Vector2f getValueVec2() {
        if (this.value == null) {
            return null;
        }
        String string = this.value.trim();
        string = StrUtils.removePrefix(string, "vec2");
        String[] stringArray = Config.tokenize(string = StrUtils.trim(string, " ()"), ", ");
        if (stringArray.length != 2) {
            return null;
        }
        float[] fArray = new float[2];
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            float f = Config.parseFloat(string2 = StrUtils.removeSuffix(string2, new String[]{"F", "f"}), Float.MAX_VALUE);
            if (f == Float.MAX_VALUE) {
                return null;
            }
            fArray[i] = f;
        }
        return new Vector2f(fArray[0], fArray[1]);
    }

    public Vector4f getValueVec4() {
        if (this.value == null) {
            return null;
        }
        String string = this.value.trim();
        string = StrUtils.removePrefix(string, "vec4");
        String[] stringArray = Config.tokenize(string = StrUtils.trim(string, " ()"), ", ");
        if (stringArray.length != 4) {
            return null;
        }
        float[] fArray = new float[4];
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            float f = Config.parseFloat(string2 = StrUtils.removeSuffix(string2, new String[]{"F", "f"}), Float.MAX_VALUE);
            if (f == Float.MAX_VALUE) {
                return null;
            }
            fArray[i] = f;
        }
        return new Vector4f(fArray[0], fArray[1], fArray[2], fArray[3]);
    }

    public boolean getValueBool() {
        String string = this.value.toLowerCase();
        if (!string.equals("true") && !string.equals("false")) {
            throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
        }
        return Boolean.valueOf(this.value);
    }

    public String toString() {
        return this.line;
    }

    public static enum Type {
        UNIFORM,
        ATTRIBUTE,
        CONST_INT,
        CONST_IVEC3,
        CONST_FLOAT,
        CONST_VEC2,
        CONST_VEC4,
        CONST_BOOL,
        PROPERTY,
        EXTENSION,
        LAYOUT;

    }
}

