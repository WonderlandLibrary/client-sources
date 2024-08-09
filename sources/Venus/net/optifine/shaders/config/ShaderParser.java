/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.math.vector.Vector3i;
import net.optifine.Config;
import net.optifine.shaders.config.ShaderLine;

public class ShaderParser {
    public static Pattern PATTERN_UNIFORM = Pattern.compile("[\\w\\s(,=)]*uniform\\s+\\w+\\s+(\\w+).*");
    public static Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
    public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
    public static Pattern PATTERN_CONST_IVEC3 = Pattern.compile("\\s*const\\s+ivec3\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
    public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
    public static Pattern PATTERN_CONST_VEC2 = Pattern.compile("\\s*const\\s+vec2\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
    public static Pattern PATTERN_CONST_VEC4 = Pattern.compile("\\s*const\\s+vec4\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
    public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
    public static Pattern PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*([\\w.,]+)\\s*(\\*/.*|\\s*)");
    public static Pattern PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)\\s*:\\s*(\\w+).*");
    public static Pattern PATTERN_LAYOUT = Pattern.compile("\\s*layout\\s*\\((.*)\\)\\s*(\\w+).*");
    public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-9N]+");
    public static Pattern PATTERN_RENDER_TARGETS = Pattern.compile("[0-9N,]+");

    public static ShaderLine parseLine(String string) {
        Matcher matcher = PATTERN_UNIFORM.matcher(string);
        if (matcher.matches()) {
            return new ShaderLine(ShaderLine.Type.UNIFORM, matcher.group(1), "", string);
        }
        Matcher matcher2 = PATTERN_ATTRIBUTE.matcher(string);
        if (matcher2.matches()) {
            return new ShaderLine(ShaderLine.Type.ATTRIBUTE, matcher2.group(1), "", string);
        }
        Matcher matcher3 = PATTERN_CONST_INT.matcher(string);
        if (matcher3.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_INT, matcher3.group(1), matcher3.group(2), string);
        }
        Matcher matcher4 = PATTERN_CONST_IVEC3.matcher(string);
        if (matcher4.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_IVEC3, matcher4.group(1), matcher4.group(2), string);
        }
        Matcher matcher5 = PATTERN_CONST_FLOAT.matcher(string);
        if (matcher5.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_FLOAT, matcher5.group(1), matcher5.group(2), string);
        }
        Matcher matcher6 = PATTERN_CONST_VEC2.matcher(string);
        if (matcher6.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_VEC2, matcher6.group(1), matcher6.group(2), string);
        }
        Matcher matcher7 = PATTERN_CONST_VEC4.matcher(string);
        if (matcher7.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_VEC4, matcher7.group(1), matcher7.group(2), string);
        }
        Matcher matcher8 = PATTERN_CONST_BOOL.matcher(string);
        if (matcher8.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_BOOL, matcher8.group(1), matcher8.group(2), string);
        }
        Matcher matcher9 = PATTERN_PROPERTY.matcher(string);
        if (matcher9.matches()) {
            return new ShaderLine(ShaderLine.Type.PROPERTY, matcher9.group(2), matcher9.group(3), string);
        }
        Matcher matcher10 = PATTERN_EXTENSION.matcher(string);
        if (matcher10.matches()) {
            return new ShaderLine(ShaderLine.Type.EXTENSION, matcher10.group(1), matcher10.group(2), string);
        }
        Matcher matcher11 = PATTERN_LAYOUT.matcher(string);
        return matcher11.matches() ? new ShaderLine(ShaderLine.Type.LAYOUT, matcher11.group(2), matcher11.group(1), string) : null;
    }

    public static int getIndex(String string, String string2, int n, int n2) {
        if (!string.startsWith(string2)) {
            return 1;
        }
        String string3 = string.substring(string2.length());
        int n3 = Config.parseInt(string3, -1);
        return n3 >= n && n3 <= n2 ? n3 : -1;
    }

    public static int getShadowDepthIndex(String string) {
        int n = -1;
        switch (string.hashCode()) {
            case -903579360: {
                if (!string.equals("shadow")) break;
                n = 0;
                break;
            }
            case 1235669239: {
                if (!string.equals("watershadow")) break;
                n = 1;
            }
        }
        switch (n) {
            case 0: {
                return 1;
            }
            case 1: {
                return 0;
            }
        }
        return ShaderParser.getIndex(string, "shadowtex", 0, 1);
    }

    public static int getShadowColorIndex(String string) {
        int n = -1;
        switch (string.hashCode()) {
            case -1560188349: {
                if (!string.equals("shadowcolor")) break;
                n = 0;
            }
        }
        switch (n) {
            case 0: {
                return 1;
            }
        }
        return ShaderParser.getIndex(string, "shadowcolor", 0, 1);
    }

    public static int getShadowColorImageIndex(String string) {
        return ShaderParser.getIndex(string, "shadowcolorimg", 0, 1);
    }

    public static int getDepthIndex(String string) {
        return ShaderParser.getIndex(string, "depthtex", 0, 2);
    }

    public static int getColorIndex(String string) {
        int n = ShaderParser.getIndex(string, "gaux", 1, 4);
        return n > 0 ? n + 3 : ShaderParser.getIndex(string, "colortex", 0, 15);
    }

    public static int getColorImageIndex(String string) {
        return ShaderParser.getIndex(string, "colorimg", 0, 15);
    }

    public static String[] parseDrawBuffers(String string) {
        if (!PATTERN_DRAW_BUFFERS.matcher(string).matches()) {
            return null;
        }
        string = string.trim();
        String[] stringArray = new String[string.length()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = String.valueOf(string.charAt(i));
        }
        return stringArray;
    }

    public static String[] parseRenderTargets(String string) {
        if (!PATTERN_RENDER_TARGETS.matcher(string).matches()) {
            return null;
        }
        string = string.trim();
        return Config.tokenize(string, ",");
    }

    public static Vector3i parseLocalSize(String string) {
        int n = 1;
        int n2 = 1;
        int n3 = 1;
        String[] stringArray = Config.tokenize(string, ",");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            String[] stringArray2 = Config.tokenize(string2, "=");
            if (stringArray2.length != 2) continue;
            String string3 = stringArray2[0].trim();
            String string4 = stringArray2[5].trim();
            int n4 = Config.parseInt(string4, -1);
            if (n4 < 1) {
                return null;
            }
            if (string3.equals("local_size_x")) {
                n = n4;
            }
            if (string3.equals("local_size_y")) {
                n2 = n4;
            }
            if (!string3.equals("local_size_z")) continue;
            n3 = n4;
        }
        return n == 1 && n2 == 1 && n3 == 1 ? null : new Vector3i(n, n2, n3);
    }
}

