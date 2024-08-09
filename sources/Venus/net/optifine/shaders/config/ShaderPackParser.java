/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.expr.ExpressionFloatArrayCached;
import net.optifine.expr.ExpressionFloatCached;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.expr.ParseException;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Program;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.RenderScale;
import net.optifine.shaders.config.ScreenShaderOptions;
import net.optifine.shaders.config.ShaderMacro;
import net.optifine.shaders.config.ShaderMacros;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionResolver;
import net.optifine.shaders.config.ShaderOptionRest;
import net.optifine.shaders.config.ShaderOptionScreen;
import net.optifine.shaders.config.ShaderOptionSwitch;
import net.optifine.shaders.config.ShaderOptionSwitchConst;
import net.optifine.shaders.config.ShaderOptionVariable;
import net.optifine.shaders.config.ShaderOptionVariableConst;
import net.optifine.shaders.config.ShaderParser;
import net.optifine.shaders.config.ShaderProfile;
import net.optifine.shaders.uniform.CustomUniform;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderExpressionResolver;
import net.optifine.shaders.uniform.UniformType;
import net.optifine.util.DynamicDimension;
import net.optifine.util.LineBuffer;
import net.optifine.util.StrUtils;

public class ShaderPackParser {
    private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
    private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
    private static final Set<String> setConstNames = ShaderPackParser.makeSetConstNames();
    private static final Map<String, Integer> mapAlphaFuncs = ShaderPackParser.makeMapAlphaFuncs();
    private static final Map<String, Integer> mapBlendFactors = ShaderPackParser.makeMapBlendFactors();

    public static ShaderOption[] parseShaderPackOptions(IShaderPack iShaderPack, String[] stringArray, List<Integer> list) {
        Object object;
        if (iShaderPack == null) {
            return new ShaderOption[0];
        }
        HashMap<String, ShaderOption> hashMap = new HashMap<String, ShaderOption>();
        ShaderPackParser.collectShaderOptions(iShaderPack, "/shaders", stringArray, hashMap);
        Object object2 = list.iterator();
        while (object2.hasNext()) {
            int n = object2.next();
            object = "/shaders/world" + n;
            ShaderPackParser.collectShaderOptions(iShaderPack, (String)object, stringArray, hashMap);
        }
        object2 = hashMap.values();
        ShaderOption[] shaderOptionArray = object2.toArray(new ShaderOption[object2.size()]);
        object = new Comparator<ShaderOption>(){

            @Override
            public int compare(ShaderOption shaderOption, ShaderOption shaderOption2) {
                return shaderOption.getName().compareToIgnoreCase(shaderOption2.getName());
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((ShaderOption)object, (ShaderOption)object2);
            }
        };
        Arrays.sort(shaderOptionArray, object);
        return shaderOptionArray;
    }

    private static void collectShaderOptions(IShaderPack iShaderPack, String string, String[] stringArray, Map<String, ShaderOption> map) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (string2.equals("")) continue;
            String string3 = string + "/" + string2 + ".csh";
            String string4 = string + "/" + string2 + ".vsh";
            String string5 = string + "/" + string2 + ".gsh";
            String string6 = string + "/" + string2 + ".fsh";
            ShaderPackParser.collectShaderOptions(iShaderPack, string3, map);
            ShaderPackParser.collectShaderOptions(iShaderPack, string4, map);
            ShaderPackParser.collectShaderOptions(iShaderPack, string5, map);
            ShaderPackParser.collectShaderOptions(iShaderPack, string6, map);
        }
    }

    private static void collectShaderOptions(IShaderPack iShaderPack, String string, Map<String, ShaderOption> map) {
        String[] stringArray = ShaderPackParser.getLines(iShaderPack, string);
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            ShaderOption shaderOption = ShaderPackParser.getShaderOption(string2, string);
            if (shaderOption == null || shaderOption.getName().startsWith(ShaderMacros.getPrefixMacro()) || shaderOption.checkUsed() && !ShaderPackParser.isOptionUsed(shaderOption, stringArray)) continue;
            String string3 = shaderOption.getName();
            ShaderOption shaderOption2 = map.get(string3);
            if (shaderOption2 != null) {
                if (!Config.equals(shaderOption2.getValueDefault(), shaderOption.getValueDefault())) {
                    if (shaderOption2.isEnabled()) {
                        Config.warn("Ambiguous shader option: " + shaderOption.getName());
                        Config.warn(" - in " + Config.arrayToString(shaderOption2.getPaths()) + ": " + shaderOption2.getValueDefault());
                        Config.warn(" - in " + Config.arrayToString(shaderOption.getPaths()) + ": " + shaderOption.getValueDefault());
                    }
                    shaderOption2.setEnabled(true);
                }
                if (shaderOption2.getDescription() == null || shaderOption2.getDescription().length() <= 0) {
                    shaderOption2.setDescription(shaderOption.getDescription());
                }
                shaderOption2.addPaths(shaderOption.getPaths());
                continue;
            }
            map.put(string3, shaderOption);
        }
    }

    private static boolean isOptionUsed(ShaderOption shaderOption, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (!shaderOption.isUsedInLine(string)) continue;
            return false;
        }
        return true;
    }

    private static String[] getLines(IShaderPack iShaderPack, String string) {
        try {
            ArrayList<String> arrayList = new ArrayList<String>();
            LineBuffer lineBuffer = ShaderPackParser.loadFile(string, iShaderPack, 0, arrayList, 0);
            return lineBuffer == null ? new String[]{} : lineBuffer.getLines();
        } catch (IOException iOException) {
            Config.dbg(iOException.getClass().getName() + ": " + iOException.getMessage());
            return new String[0];
        }
    }

    private static ShaderOption getShaderOption(String string, String string2) {
        ShaderOption shaderOption = null;
        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitch.parseOption(string, string2);
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionVariable.parseOption(string, string2);
        }
        if (shaderOption != null) {
            return shaderOption;
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitchConst.parseOption(string, string2);
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionVariableConst.parseOption(string, string2);
        }
        return shaderOption != null && setConstNames.contains(shaderOption.getName()) ? shaderOption : null;
    }

    private static Set<String> makeSetConstNames() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("shadowMapResolution");
        hashSet.add("shadowMapFov");
        hashSet.add("shadowDistance");
        hashSet.add("shadowDistanceRenderMul");
        hashSet.add("shadowIntervalSize");
        hashSet.add("generateShadowMipmap");
        hashSet.add("generateShadowColorMipmap");
        hashSet.add("shadowHardwareFiltering");
        hashSet.add("shadowHardwareFiltering0");
        hashSet.add("shadowHardwareFiltering1");
        hashSet.add("shadowtex0Mipmap");
        hashSet.add("shadowtexMipmap");
        hashSet.add("shadowtex1Mipmap");
        hashSet.add("shadowcolor0Mipmap");
        hashSet.add("shadowColor0Mipmap");
        hashSet.add("shadowcolor1Mipmap");
        hashSet.add("shadowColor1Mipmap");
        hashSet.add("shadowtex0Nearest");
        hashSet.add("shadowtexNearest");
        hashSet.add("shadow0MinMagNearest");
        hashSet.add("shadowtex1Nearest");
        hashSet.add("shadow1MinMagNearest");
        hashSet.add("shadowcolor0Nearest");
        hashSet.add("shadowColor0Nearest");
        hashSet.add("shadowColor0MinMagNearest");
        hashSet.add("shadowcolor1Nearest");
        hashSet.add("shadowColor1Nearest");
        hashSet.add("shadowColor1MinMagNearest");
        hashSet.add("wetnessHalflife");
        hashSet.add("drynessHalflife");
        hashSet.add("eyeBrightnessHalflife");
        hashSet.add("centerDepthHalflife");
        hashSet.add("sunPathRotation");
        hashSet.add("ambientOcclusionLevel");
        hashSet.add("superSamplingLevel");
        hashSet.add("noiseTextureResolution");
        return hashSet;
    }

    public static ShaderProfile[] parseProfiles(Properties properties, ShaderOption[] shaderOptionArray) {
        String string = "profile.";
        ArrayList<ShaderProfile> arrayList = new ArrayList<ShaderProfile>();
        for (String string2 : properties.keySet()) {
            if (!string2.startsWith(string)) continue;
            String string3 = string2.substring(string.length());
            properties.getProperty(string2);
            HashSet<String> hashSet = new HashSet<String>();
            ShaderProfile shaderProfile = ShaderPackParser.parseProfile(string3, properties, hashSet, shaderOptionArray);
            if (shaderProfile == null) continue;
            arrayList.add(shaderProfile);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        ShaderProfile[] shaderProfileArray = arrayList.toArray(new ShaderProfile[arrayList.size()]);
        return shaderProfileArray;
    }

    public static Map<String, IExpressionBool> parseProgramConditions(Properties properties, ShaderOption[] shaderOptionArray) {
        String string = "program.";
        Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
        HashMap<String, IExpressionBool> hashMap = new HashMap<String, IExpressionBool>();
        for (String string2 : properties.keySet()) {
            Matcher matcher = pattern.matcher(string2);
            if (!matcher.matches()) continue;
            String string3 = matcher.group(1);
            String string4 = properties.getProperty(string2).trim();
            IExpressionBool iExpressionBool = ShaderPackParser.parseOptionExpression(string4, shaderOptionArray);
            if (iExpressionBool == null) {
                SMCLog.severe("Error parsing program condition: " + string2);
                continue;
            }
            hashMap.put(string3, iExpressionBool);
        }
        return hashMap;
    }

    private static IExpressionBool parseOptionExpression(String string, ShaderOption[] shaderOptionArray) {
        try {
            ShaderOptionResolver shaderOptionResolver = new ShaderOptionResolver(shaderOptionArray);
            ExpressionParser expressionParser = new ExpressionParser(shaderOptionResolver);
            return expressionParser.parseBool(string);
        } catch (ParseException parseException) {
            SMCLog.warning(parseException.getClass().getName() + ": " + parseException.getMessage());
            return null;
        }
    }

    public static Set<String> parseOptionSliders(Properties properties, ShaderOption[] shaderOptionArray) {
        HashSet<String> hashSet = new HashSet<String>();
        String string = properties.getProperty("sliders");
        if (string == null) {
            return hashSet;
        }
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            ShaderOption shaderOption = ShaderUtils.getShaderOption(string2, shaderOptionArray);
            if (shaderOption == null) {
                Config.warn("Invalid shader option: " + string2);
                continue;
            }
            hashSet.add(string2);
        }
        return hashSet;
    }

    private static ShaderProfile parseProfile(String string, Properties properties, Set<String> set, ShaderOption[] shaderOptionArray) {
        String string2 = "profile.";
        String string3 = string2 + string;
        if (set.contains(string3)) {
            Config.warn("[Shaders] Profile already parsed: " + string);
            return null;
        }
        set.add(string);
        ShaderProfile shaderProfile = new ShaderProfile(string);
        String string4 = properties.getProperty(string3);
        String[] stringArray = Config.tokenize(string4, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            Object object;
            Object object2;
            Object object3;
            String string5 = stringArray[i];
            if (string5.startsWith(string2)) {
                object3 = string5.substring(string2.length());
                object2 = ShaderPackParser.parseProfile((String)object3, properties, set, shaderOptionArray);
                if (shaderProfile == null) continue;
                shaderProfile.addOptionValues((ShaderProfile)object2);
                shaderProfile.addDisabledPrograms(((ShaderProfile)object2).getDisabledPrograms());
                continue;
            }
            object3 = Config.tokenize(string5, ":=");
            if (((String[])object3).length == 1) {
                Object object4;
                object2 = object3[0];
                boolean bl = true;
                if (((String)object2).startsWith("!")) {
                    bl = false;
                    object2 = ((String)object2).substring(1);
                }
                if (((String)object2).startsWith((String)(object = "program."))) {
                    object4 = ((String)object2).substring(((String)object).length());
                    if (!Shaders.isProgramPath((String)object4)) {
                        Config.warn("Invalid program: " + (String)object4 + " in profile: " + shaderProfile.getName());
                        continue;
                    }
                    if (bl) {
                        shaderProfile.removeDisabledProgram((String)object4);
                        continue;
                    }
                    shaderProfile.addDisabledProgram((String)object4);
                    continue;
                }
                object4 = ShaderUtils.getShaderOption((String)object2, shaderOptionArray);
                if (!(object4 instanceof ShaderOptionSwitch)) {
                    Config.warn("[Shaders] Invalid option: " + (String)object2);
                    continue;
                }
                shaderProfile.addOptionValue((String)object2, String.valueOf(bl));
                ((ShaderOption)object4).setVisible(false);
                continue;
            }
            if (((String[])object3).length != 2) {
                Config.warn("[Shaders] Invalid option value: " + string5);
                continue;
            }
            object2 = object3[0];
            String string6 = object3[5];
            object = ShaderUtils.getShaderOption((String)object2, shaderOptionArray);
            if (object == null) {
                Config.warn("[Shaders] Invalid option: " + string5);
                continue;
            }
            if (!((ShaderOption)object).isValidValue(string6)) {
                Config.warn("[Shaders] Invalid value: " + string5);
                continue;
            }
            ((ShaderOption)object).setVisible(false);
            shaderProfile.addOptionValue((String)object2, string6);
        }
        return shaderProfile;
    }

    public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties properties, ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray) {
        HashMap<String, ScreenShaderOptions> hashMap = new HashMap<String, ScreenShaderOptions>();
        ShaderPackParser.parseGuiScreen("screen", properties, hashMap, shaderProfileArray, shaderOptionArray);
        return hashMap.isEmpty() ? null : hashMap;
    }

    private static boolean parseGuiScreen(String string, Properties properties, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray) {
        String string2;
        String string3 = properties.getProperty(string);
        if (string3 == null) {
            return true;
        }
        String string4 = string + "$parent$";
        if (map.containsKey(string4)) {
            Config.warn("[Shaders] Screen circular reference: " + string + " = " + string3);
            return true;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        HashSet<String> hashSet = new HashSet<String>();
        String[] stringArray = Config.tokenize(string3, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            Object object;
            string2 = stringArray[i];
            if (string2.equals("<empty>")) {
                arrayList.add(null);
                continue;
            }
            if (hashSet.contains(string2)) {
                Config.warn("[Shaders] Duplicate option: " + string2 + ", key: " + string);
                continue;
            }
            hashSet.add(string2);
            if (string2.equals("<profile>")) {
                if (shaderProfileArray == null) {
                    Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + string2 + ", key: " + string);
                    continue;
                }
                object = new ShaderOptionProfile(shaderProfileArray, shaderOptionArray);
                arrayList.add(object);
                continue;
            }
            if (string2.equals("*")) {
                object = new ShaderOptionRest("<rest>");
                arrayList.add(object);
                continue;
            }
            if (string2.startsWith("[") && string2.endsWith("]")) {
                object = StrUtils.removePrefixSuffix(string2, "[", "]");
                if (!((String)object).matches("^[a-zA-Z0-9_]+$")) {
                    Config.warn("[Shaders] Invalid screen: " + string2 + ", key: " + string);
                    continue;
                }
                map.put(string4, null);
                boolean bl = ShaderPackParser.parseGuiScreen("screen." + (String)object, properties, map, shaderProfileArray, shaderOptionArray);
                map.remove(string4);
                if (!bl) {
                    Config.warn("[Shaders] Invalid screen: " + string2 + ", key: " + string);
                    continue;
                }
                ShaderOptionScreen shaderOptionScreen = new ShaderOptionScreen((String)object);
                arrayList.add(shaderOptionScreen);
                continue;
            }
            object = ShaderUtils.getShaderOption(string2, shaderOptionArray);
            if (object == null) {
                Config.warn("[Shaders] Invalid option: " + string2 + ", key: " + string);
                arrayList.add(null);
                continue;
            }
            ((ShaderOption)object).setVisible(false);
            arrayList.add(object);
        }
        ShaderOption[] shaderOptionArray2 = arrayList.toArray(new ShaderOption[arrayList.size()]);
        string2 = properties.getProperty(string + ".columns");
        int n = Config.parseInt(string2, 2);
        ScreenShaderOptions screenShaderOptions = new ScreenShaderOptions(string, shaderOptionArray2, n);
        map.put(string, screenShaderOptions);
        return false;
    }

    public static LineBuffer resolveIncludes(LineBuffer lineBuffer, String string, IShaderPack iShaderPack, int n, List<String> list, int n2) throws IOException {
        Object object;
        Object object2;
        String string2 = "/";
        int n3 = string.lastIndexOf("/");
        if (n3 >= 0) {
            string2 = string.substring(0, n3);
        }
        LineBuffer lineBuffer2 = new LineBuffer();
        int n4 = -1;
        int n5 = 0;
        ShaderMacro[] shaderMacroArray = lineBuffer.iterator();
        while (shaderMacroArray.hasNext()) {
            Matcher matcher;
            object2 = shaderMacroArray.next();
            ++n5;
            if (n4 < 0 && (matcher = PATTERN_VERSION.matcher((CharSequence)object2)).matches()) {
                lineBuffer2.add((String)object2);
                object = ShaderMacros.getHeaderMacroLines();
                lineBuffer2.add((String[])object);
                n4 = lineBuffer2.size();
                String string3 = "#line " + (n5 + 1) + " " + n;
                lineBuffer2.add(string3);
                continue;
            }
            matcher = PATTERN_INCLUDE.matcher((CharSequence)object2);
            if (matcher.matches()) {
                int n6;
                LineBuffer lineBuffer3;
                String string4;
                object = matcher.group(1);
                boolean bl = ((String)object).startsWith("/");
                String string5 = string4 = bl ? "/shaders" + (String)object : string2 + "/" + (String)object;
                if (!list.contains(string4)) {
                    list.add(string4);
                }
                if ((lineBuffer3 = ShaderPackParser.loadFile(string4, iShaderPack, n6 = list.indexOf(string4) + 1, list, n2)) == null) {
                    throw new IOException("Included file not found: " + string);
                }
                if (lineBuffer3.indexMatch(PATTERN_VERSION) < 0) {
                    lineBuffer2.add("#line 1 " + n6);
                }
                lineBuffer2.add(lineBuffer3.getLines());
                lineBuffer2.add("#line " + (n5 + 1) + " " + n);
                continue;
            }
            lineBuffer2.add((String)object2);
        }
        if (n4 >= 0 && (shaderMacroArray = ShaderPackParser.getCustomMacros(lineBuffer2, n4)).length > 0) {
            object2 = new LineBuffer();
            for (int i = 0; i < shaderMacroArray.length; ++i) {
                object = shaderMacroArray[i];
                ((LineBuffer)object2).add(((ShaderMacro)object).getSourceLine());
            }
            lineBuffer2.insert(n4, ((LineBuffer)object2).getLines());
        }
        return lineBuffer2;
    }

    private static ShaderMacro[] getCustomMacros(LineBuffer lineBuffer, int n) {
        LinkedHashSet<ShaderMacro> linkedHashSet = new LinkedHashSet<ShaderMacro>();
        for (int i = n; i < lineBuffer.size(); ++i) {
            String string = lineBuffer.get(i);
            if (!string.contains(ShaderMacros.getPrefixMacro())) continue;
            ShaderMacro[] shaderMacroArray = ShaderPackParser.findMacros(string, ShaderMacros.getExtensions());
            linkedHashSet.addAll(Arrays.asList(shaderMacroArray));
            ShaderMacro[] shaderMacroArray2 = ShaderPackParser.findMacros(string, ShaderMacros.getConstantMacros());
            linkedHashSet.addAll(Arrays.asList(shaderMacroArray2));
        }
        return linkedHashSet.toArray(new ShaderMacro[linkedHashSet.size()]);
    }

    public static LineBuffer remapTextureUnits(LineBuffer lineBuffer) throws IOException {
        if (!Shaders.isRemapLightmap()) {
            return lineBuffer;
        }
        LineBuffer lineBuffer2 = new LineBuffer();
        for (Object object : lineBuffer) {
            Object object2 = ((String)object).replace("gl_TextureMatrix[1]", "gl_TextureMatrix[2]");
            if (!((String)(object2 = ((String)object2).replace("gl_MultiTexCoord1", "gl_MultiTexCoord2"))).equals(object)) {
                object = object2 = (String)object2 + " // Legacy fix, replaced TU 1 with 2";
            }
            lineBuffer2.add((String)object);
        }
        return lineBuffer2;
    }

    private static ShaderMacro[] findMacros(String string, ShaderMacro[] shaderMacroArray) {
        ArrayList<ShaderMacro> arrayList = new ArrayList<ShaderMacro>();
        for (int i = 0; i < shaderMacroArray.length; ++i) {
            ShaderMacro shaderMacro = shaderMacroArray[i];
            if (!string.contains(shaderMacro.getName())) continue;
            arrayList.add(shaderMacro);
        }
        return arrayList.toArray(new ShaderMacro[arrayList.size()]);
    }

    private static LineBuffer loadFile(String string, IShaderPack iShaderPack, int n, List<String> list, int n2) throws IOException {
        if (n2 >= 10) {
            throw new IOException("#include depth exceeded: " + n2 + ", file: " + string);
        }
        ++n2;
        InputStream inputStream = iShaderPack.getResourceAsStream(string);
        if (inputStream == null) {
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ASCII");
        LineBuffer lineBuffer = LineBuffer.readAll(inputStreamReader);
        return ShaderPackParser.resolveIncludes(lineBuffer, string, iShaderPack, n, list, n2);
    }

    public static CustomUniforms parseCustomUniforms(Properties properties) {
        String string = "uniform";
        String string2 = "variable";
        String string3 = string + ".";
        String string4 = string2 + ".";
        HashMap<String, IExpression> hashMap = new HashMap<String, IExpression>();
        ArrayList<CustomUniform> arrayList = new ArrayList<CustomUniform>();
        for (String string5 : properties.keySet()) {
            String[] stringArray = Config.tokenize(string5, ".");
            if (stringArray.length != 3) continue;
            String string6 = stringArray[0];
            String string7 = stringArray[5];
            String string8 = stringArray[5];
            String string9 = properties.getProperty(string5).trim();
            if (hashMap.containsKey(string8)) {
                SMCLog.warning("Expression already defined: " + string8);
                continue;
            }
            if (!string6.equals(string) && !string6.equals(string2)) continue;
            SMCLog.info("Custom " + string6 + ": " + string8);
            CustomUniform customUniform = ShaderPackParser.parseCustomUniform(string6, string8, string7, string9, hashMap);
            if (customUniform == null) continue;
            hashMap.put(string8, customUniform.getExpression());
            if (string6.equals(string2)) continue;
            arrayList.add(customUniform);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        CustomUniform[] customUniformArray = arrayList.toArray(new CustomUniform[arrayList.size()]);
        return new CustomUniforms(customUniformArray, hashMap);
    }

    private static CustomUniform parseCustomUniform(String string, String string2, String string3, String string4, Map<String, IExpression> map) {
        try {
            UniformType uniformType = UniformType.parse(string3);
            if (uniformType == null) {
                SMCLog.warning("Unknown " + string + " type: " + uniformType);
                return null;
            }
            ShaderExpressionResolver shaderExpressionResolver = new ShaderExpressionResolver(map);
            ExpressionParser expressionParser = new ExpressionParser(shaderExpressionResolver);
            IExpression iExpression = expressionParser.parse(string4);
            ExpressionType expressionType = iExpression.getExpressionType();
            if (!uniformType.matchesExpressionType(expressionType)) {
                SMCLog.warning("Expression type does not match " + string + " type, expression: " + expressionType + ", " + string + ": " + uniformType + " " + string2);
                return null;
            }
            iExpression = ShaderPackParser.makeExpressionCached(iExpression);
            return new CustomUniform(string2, uniformType, iExpression);
        } catch (ParseException parseException) {
            SMCLog.warning(parseException.getClass().getName() + ": " + parseException.getMessage());
            return null;
        }
    }

    private static IExpression makeExpressionCached(IExpression iExpression) {
        if (iExpression instanceof IExpressionFloat) {
            return new ExpressionFloatCached((IExpressionFloat)iExpression);
        }
        return iExpression instanceof IExpressionFloatArray ? new ExpressionFloatArrayCached((IExpressionFloatArray)iExpression) : iExpression;
    }

    public static void parseAlphaStates(Properties properties) {
        for (String string : properties.keySet()) {
            String[] stringArray = Config.tokenize(string, ".");
            if (stringArray.length != 2) continue;
            String string2 = stringArray[0];
            String string3 = stringArray[5];
            if (!string2.equals("alphaTest")) continue;
            Program program = Shaders.getProgram(string3);
            if (program == null) {
                SMCLog.severe("Invalid program name: " + string3);
                continue;
            }
            String string4 = properties.getProperty(string).trim();
            GlAlphaState glAlphaState = ShaderPackParser.parseAlphaState(string4);
            if (glAlphaState == null) continue;
            program.setAlphaState(glAlphaState);
        }
    }

    public static GlAlphaState parseAlphaState(String string) {
        if (string == null) {
            return null;
        }
        String[] stringArray = Config.tokenize(string, " ");
        if (stringArray.length == 1) {
            String string2 = stringArray[0];
            if (string2.equals("off") || string2.equals("false")) {
                return new GlAlphaState(false);
            }
        } else if (stringArray.length == 2) {
            String string3 = stringArray[0];
            String string4 = stringArray[5];
            Integer n = mapAlphaFuncs.get(string3);
            float f = Config.parseFloat(string4, -1.0f);
            if (n != null && f >= 0.0f) {
                return new GlAlphaState(true, n, f);
            }
        }
        SMCLog.severe("Invalid alpha test: " + string);
        return null;
    }

    public static void parseBlendStates(Properties properties) {
        for (String string : properties.keySet()) {
            String string2;
            String[] stringArray = Config.tokenize(string, ".");
            if (stringArray.length < 2 || stringArray.length > 3) continue;
            String string3 = stringArray[0];
            String string4 = stringArray[5];
            String string5 = string2 = stringArray.length == 3 ? stringArray[5] : null;
            if (!string3.equals("blend")) continue;
            Program program = Shaders.getProgram(string4);
            if (program == null) {
                SMCLog.severe("Invalid program name: " + string4);
                continue;
            }
            String string6 = properties.getProperty(string).trim();
            GlBlendState glBlendState = ShaderPackParser.parseBlendState(string6);
            if (glBlendState == null) continue;
            if (string2 != null) {
                int n;
                int n2 = program.getProgramStage().isAnyShadow() ? ShaderParser.getShadowColorIndex(string2) : Shaders.getBufferIndex(string2);
                int n3 = n = program.getProgramStage().isAnyShadow() ? 2 : 16;
                if (n2 >= 0 && n2 < n) {
                    program.setBlendStateColorIndexed(n2, glBlendState);
                    SMCLog.info("Blend " + string4 + "." + string2 + "=" + string6);
                    continue;
                }
                SMCLog.severe("Invalid buffer name: " + string2);
                continue;
            }
            program.setBlendState(glBlendState);
        }
    }

    public static GlBlendState parseBlendState(String string) {
        if (string == null) {
            return null;
        }
        String[] stringArray = Config.tokenize(string, " ");
        if (stringArray.length == 1) {
            String string2 = stringArray[0];
            if (string2.equals("off") || string2.equals("false")) {
                return new GlBlendState(false);
            }
        } else if (stringArray.length == 2 || stringArray.length == 4) {
            String string3 = stringArray[0];
            String string4 = stringArray[5];
            String string5 = string3;
            String string6 = string4;
            if (stringArray.length == 4) {
                string5 = stringArray[5];
                string6 = stringArray[5];
            }
            Integer n = mapBlendFactors.get(string3);
            Integer n2 = mapBlendFactors.get(string4);
            Integer n3 = mapBlendFactors.get(string5);
            Integer n4 = mapBlendFactors.get(string6);
            if (n != null && n2 != null && n3 != null && n4 != null) {
                return new GlBlendState(true, n, n2, n3, n4);
            }
        }
        SMCLog.severe("Invalid blend mode: " + string);
        return null;
    }

    public static void parseRenderScales(Properties properties) {
        for (String string : properties.keySet()) {
            String[] stringArray = Config.tokenize(string, ".");
            if (stringArray.length != 2) continue;
            String string2 = stringArray[0];
            String string3 = stringArray[5];
            if (!string2.equals("scale")) continue;
            Program program = Shaders.getProgram(string3);
            if (program == null) {
                SMCLog.severe("Invalid program name: " + string3);
                continue;
            }
            String string4 = properties.getProperty(string).trim();
            RenderScale renderScale = ShaderPackParser.parseRenderScale(string4);
            if (renderScale == null) continue;
            program.setRenderScale(renderScale);
        }
    }

    private static RenderScale parseRenderScale(String string) {
        if (string == null) {
            return null;
        }
        String[] stringArray = Config.tokenize(string, " ");
        float f = Config.parseFloat(stringArray[0], -1.0f);
        float f2 = 0.0f;
        float f3 = 0.0f;
        if (stringArray.length > 1) {
            if (stringArray.length != 3) {
                SMCLog.severe("Invalid render scale: " + string);
                return null;
            }
            f2 = Config.parseFloat(stringArray[5], -1.0f);
            f3 = Config.parseFloat(stringArray[5], -1.0f);
        }
        if (Config.between(f, 0.0f, 1.0f) && Config.between(f2, 0.0f, 1.0f) && Config.between(f3, 0.0f, 1.0f)) {
            return new RenderScale(f, f2, f3);
        }
        SMCLog.severe("Invalid render scale: " + string);
        return null;
    }

    public static void parseBuffersFlip(Properties properties) {
        for (String string : properties.keySet()) {
            String[] stringArray = Config.tokenize(string, ".");
            if (stringArray.length != 3) continue;
            String string2 = stringArray[0];
            String string3 = stringArray[5];
            String string4 = stringArray[5];
            if (!string2.equals("flip")) continue;
            Program program = Shaders.getProgram(string3);
            if (program == null) {
                SMCLog.severe("Invalid program name: " + string3);
                continue;
            }
            Boolean[] booleanArray = program.getBuffersFlip();
            int n = Shaders.getBufferIndex(string4);
            if (n >= 0 && n < booleanArray.length) {
                String string5 = properties.getProperty(string).trim();
                Boolean bl = Config.parseBoolean(string5, null);
                if (bl == null) {
                    SMCLog.severe("Invalid boolean value: " + string5);
                    continue;
                }
                booleanArray[n] = bl;
                continue;
            }
            SMCLog.severe("Invalid buffer name: " + string4);
        }
    }

    private static Map<String, Integer> makeMapAlphaFuncs() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("NEVER", new Integer(512));
        hashMap.put("LESS", new Integer(513));
        hashMap.put("EQUAL", new Integer(514));
        hashMap.put("LEQUAL", new Integer(515));
        hashMap.put("GREATER", new Integer(516));
        hashMap.put("NOTEQUAL", new Integer(517));
        hashMap.put("GEQUAL", new Integer(518));
        hashMap.put("ALWAYS", new Integer(519));
        return Collections.unmodifiableMap(hashMap);
    }

    private static Map<String, Integer> makeMapBlendFactors() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("ZERO", new Integer(0));
        hashMap.put("ONE", new Integer(1));
        hashMap.put("SRC_COLOR", new Integer(768));
        hashMap.put("ONE_MINUS_SRC_COLOR", new Integer(769));
        hashMap.put("DST_COLOR", new Integer(774));
        hashMap.put("ONE_MINUS_DST_COLOR", new Integer(775));
        hashMap.put("SRC_ALPHA", new Integer(770));
        hashMap.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
        hashMap.put("DST_ALPHA", new Integer(772));
        hashMap.put("ONE_MINUS_DST_ALPHA", new Integer(773));
        hashMap.put("CONSTANT_COLOR", new Integer(32769));
        hashMap.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
        hashMap.put("CONSTANT_ALPHA", new Integer(32771));
        hashMap.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
        hashMap.put("SRC_ALPHA_SATURATE", new Integer(776));
        return Collections.unmodifiableMap(hashMap);
    }

    public static DynamicDimension[] parseBufferSizes(Properties properties, int n) {
        DynamicDimension[] dynamicDimensionArray = new DynamicDimension[n];
        for (Object k : properties.keySet()) {
            String[] stringArray;
            String string = (String)k;
            if (!string.startsWith("size.buffer.") || (stringArray = Config.tokenize(string, ".")).length != 3) continue;
            String string2 = stringArray[0];
            int n2 = Shaders.getBufferIndex(string2);
            if (n2 >= 0 && n2 < dynamicDimensionArray.length) {
                String string3 = properties.getProperty(string).trim();
                DynamicDimension dynamicDimension = ShaderPackParser.parseDynamicDimension(string3);
                if (dynamicDimension == null) {
                    SMCLog.severe("Invalid buffer size: " + string + "=" + string3);
                    continue;
                }
                dynamicDimensionArray[n2] = dynamicDimension;
                if (dynamicDimension.isRelative()) {
                    SMCLog.info("Relative size " + string2 + ": " + dynamicDimension.getWidth() + " " + dynamicDimension.getHeight());
                    continue;
                }
                SMCLog.info("Fixed size " + string2 + ": " + (int)dynamicDimension.getWidth() + " " + (int)dynamicDimension.getHeight());
                continue;
            }
            SMCLog.severe("Invalid buffer name: " + string);
        }
        return dynamicDimensionArray;
    }

    private static DynamicDimension parseDynamicDimension(String string) {
        if (string == null) {
            return null;
        }
        String[] stringArray = Config.tokenize(string, " ");
        if (stringArray.length != 2) {
            return null;
        }
        int n = Config.parseInt(stringArray[0], -1);
        int n2 = Config.parseInt(stringArray[5], -1);
        if (n >= 0 && n2 >= 0) {
            return new DynamicDimension(false, n, n2);
        }
        float f = Config.parseFloat(stringArray[0], -1.0f);
        float f2 = Config.parseFloat(stringArray[5], -1.0f);
        return f >= 0.0f && f2 >= 0.0f ? new DynamicDimension(true, f, f2) : null;
    }
}

