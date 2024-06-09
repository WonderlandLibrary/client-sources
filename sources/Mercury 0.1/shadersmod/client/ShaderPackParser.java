/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import optifine.Config;
import optifine.StrUtils;
import shadersmod.client.IShaderPack;
import shadersmod.client.ShaderOption;
import shadersmod.client.ShaderOptionProfile;
import shadersmod.client.ShaderOptionRest;
import shadersmod.client.ShaderOptionScreen;
import shadersmod.client.ShaderOptionSwitch;
import shadersmod.client.ShaderOptionSwitchConst;
import shadersmod.client.ShaderOptionVariable;
import shadersmod.client.ShaderOptionVariableConst;
import shadersmod.client.ShaderProfile;
import shadersmod.client.ShaderUtils;
import shadersmod.client.Shaders;

public class ShaderPackParser {
    private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
    private static final Set<String> setConstNames = ShaderPackParser.makeSetConstNames();

    public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
        if (shaderPack == null) {
            return new ShaderOption[0];
        }
        HashMap<String, ShaderOption> mapOptions = new HashMap<String, ShaderOption>();
        ShaderPackParser.collectShaderOptions(shaderPack, "/shaders", programNames, mapOptions);
        for (int sos : listDimensions) {
            String comp = "/shaders/world" + sos;
            ShaderPackParser.collectShaderOptions(shaderPack, comp, programNames, mapOptions);
        }
        Collection<ShaderOption> options1 = mapOptions.values();
        ShaderOption[] sos1 = options1.toArray(new ShaderOption[options1.size()]);
        Comparator comp1 = new Comparator(){

            public int compare(ShaderOption o1, ShaderOption o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

            public int compare(Object x0, Object x1) {
                return this.compare((ShaderOption)x0, (ShaderOption)x1);
            }
        };
        Arrays.sort(sos1, comp1);
        return sos1;
    }

    private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
        for (int i2 = 0; i2 < programNames.length; ++i2) {
            String programName = programNames[i2];
            if (programName.equals("")) continue;
            String vsh = String.valueOf(dir) + "/" + programName + ".vsh";
            String fsh = String.valueOf(dir) + "/" + programName + ".fsh";
            ShaderPackParser.collectShaderOptions(shaderPack, vsh, mapOptions);
            ShaderPackParser.collectShaderOptions(shaderPack, fsh, mapOptions);
        }
    }

    private static void collectShaderOptions(IShaderPack sp2, String path, Map<String, ShaderOption> mapOptions) {
        String[] lines = ShaderPackParser.getLines(sp2, path);
        for (int i2 = 0; i2 < lines.length; ++i2) {
            String line = lines[i2];
            ShaderOption so2 = ShaderPackParser.getShaderOption(line, path);
            if (so2 == null || so2.checkUsed() && !ShaderPackParser.isOptionUsed(so2, lines)) continue;
            String key = so2.getName();
            ShaderOption so22 = mapOptions.get(key);
            if (so22 != null) {
                if (!Config.equals(so22.getValueDefault(), so2.getValueDefault())) {
                    Config.warn("Ambiguous shader option: " + so2.getName());
                    Config.warn(" - in " + Config.arrayToString(so22.getPaths()) + ": " + so22.getValueDefault());
                    Config.warn(" - in " + Config.arrayToString(so2.getPaths()) + ": " + so2.getValueDefault());
                    so22.setEnabled(false);
                }
                if (so22.getDescription() == null || so22.getDescription().length() <= 0) {
                    so22.setDescription(so2.getDescription());
                }
                so22.addPaths(so2.getPaths());
                continue;
            }
            mapOptions.put(key, so2);
        }
    }

    private static boolean isOptionUsed(ShaderOption so2, String[] lines) {
        for (int i2 = 0; i2 < lines.length; ++i2) {
            String line = lines[i2];
            if (!so2.isUsedInLine(line)) continue;
            return true;
        }
        return false;
    }

    private static String[] getLines(IShaderPack sp2, String path) {
        try {
            String e2 = ShaderPackParser.loadFile(path, sp2, 0);
            if (e2 == null) {
                return new String[0];
            }
            ByteArrayInputStream is2 = new ByteArrayInputStream(e2.getBytes());
            String[] lines = Config.readLines(is2);
            return lines;
        }
        catch (IOException var5) {
            Config.dbg(String.valueOf(var5.getClass().getName()) + ": " + var5.getMessage());
            return new String[0];
        }
    }

    private static ShaderOption getShaderOption(String line, String path) {
        ShaderOption so2 = null;
        if (so2 == null) {
            so2 = ShaderOptionSwitch.parseOption(line, path);
        }
        if (so2 == null) {
            so2 = ShaderOptionVariable.parseOption(line, path);
        }
        if (so2 != null) {
            return so2;
        }
        if (so2 == null) {
            so2 = ShaderOptionSwitchConst.parseOption(line, path);
        }
        if (so2 == null) {
            so2 = ShaderOptionVariableConst.parseOption(line, path);
        }
        return so2 != null && setConstNames.contains(so2.getName()) ? so2 : null;
    }

    private static Set<String> makeSetConstNames() {
        HashSet<String> set = new HashSet<String>();
        set.add("shadowMapResolution");
        set.add("shadowDistance");
        set.add("shadowIntervalSize");
        set.add("generateShadowMipmap");
        set.add("generateShadowColorMipmap");
        set.add("shadowHardwareFiltering");
        set.add("shadowHardwareFiltering0");
        set.add("shadowHardwareFiltering1");
        set.add("shadowtex0Mipmap");
        set.add("shadowtexMipmap");
        set.add("shadowtex1Mipmap");
        set.add("shadowcolor0Mipmap");
        set.add("shadowColor0Mipmap");
        set.add("shadowcolor1Mipmap");
        set.add("shadowColor1Mipmap");
        set.add("shadowtex0Nearest");
        set.add("shadowtexNearest");
        set.add("shadow0MinMagNearest");
        set.add("shadowtex1Nearest");
        set.add("shadow1MinMagNearest");
        set.add("shadowcolor0Nearest");
        set.add("shadowColor0Nearest");
        set.add("shadowColor0MinMagNearest");
        set.add("shadowcolor1Nearest");
        set.add("shadowColor1Nearest");
        set.add("shadowColor1MinMagNearest");
        set.add("wetnessHalflife");
        set.add("drynessHalflife");
        set.add("eyeBrightnessHalflife");
        set.add("centerDepthHalflife");
        set.add("sunPathRotation");
        set.add("ambientOcclusionLevel");
        set.add("superSamplingLevel");
        set.add("noiseTextureResolution");
        return set;
    }

    public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
        String PREFIX_PROFILE = "profile.";
        ArrayList<ShaderProfile> list = new ArrayList<ShaderProfile>();
        Set<Object> keys = props.keySet();
        for (String key : keys) {
            if (!key.startsWith(PREFIX_PROFILE)) continue;
            String name = key.substring(PREFIX_PROFILE.length());
            props.getProperty(key);
            HashSet<String> parsedProfiles = new HashSet<String>();
            ShaderProfile p2 = ShaderPackParser.parseProfile(name, props, parsedProfiles, shaderOptions);
            if (p2 == null) continue;
            list.add(p2);
        }
        if (list.size() <= 0) {
            return null;
        }
        ShaderProfile[] profs1 = list.toArray(new ShaderProfile[list.size()]);
        return profs1;
    }

    private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
        String PREFIX_PROFILE = "profile.";
        String key = String.valueOf(PREFIX_PROFILE) + name;
        if (parsedProfiles.contains(key)) {
            Config.warn("[Shaders] Profile already parsed: " + name);
            return null;
        }
        parsedProfiles.add(name);
        ShaderProfile prof = new ShaderProfile(name);
        String val = props.getProperty(key);
        String[] parts = Config.tokenize(val, " ");
        for (int i2 = 0; i2 < parts.length; ++i2) {
            String var17;
            String part = parts[i2];
            if (part.startsWith(PREFIX_PROFILE)) {
                String tokens = part.substring(PREFIX_PROFILE.length());
                ShaderProfile option = ShaderPackParser.parseProfile(tokens, props, parsedProfiles, shaderOptions);
                if (prof == null) continue;
                prof.addOptionValues(option);
                prof.addDisabledPrograms(option.getDisabledPrograms());
                continue;
            }
            String[] var16 = Config.tokenize(part, ":=");
            if (var16.length == 1) {
                var17 = var16[0];
                boolean value = true;
                if (var17.startsWith("!")) {
                    value = false;
                    var17 = var17.substring(1);
                }
                String so2 = "program.";
                if (!value && var17.startsWith("program.")) {
                    String var20 = var17.substring(so2.length());
                    if (!Shaders.isProgramPath(var20)) {
                        Config.warn("Invalid program: " + var20 + " in profile: " + prof.getName());
                        continue;
                    }
                    prof.addDisabledProgram(var20);
                    continue;
                }
                ShaderOption so1 = ShaderUtils.getShaderOption(var17, shaderOptions);
                if (!(so1 instanceof ShaderOptionSwitch)) {
                    Config.warn("[Shaders] Invalid option: " + var17);
                    continue;
                }
                prof.addOptionValue(var17, String.valueOf(value));
                so1.setVisible(true);
                continue;
            }
            if (var16.length != 2) {
                Config.warn("[Shaders] Invalid option value: " + part);
                continue;
            }
            var17 = var16[0];
            String var18 = var16[1];
            ShaderOption var19 = ShaderUtils.getShaderOption(var17, shaderOptions);
            if (var19 == null) {
                Config.warn("[Shaders] Invalid option: " + part);
                continue;
            }
            if (!var19.isValidValue(var18)) {
                Config.warn("[Shaders] Invalid value: " + part);
                continue;
            }
            var19.setVisible(true);
            prof.addOptionValue(var17, var18);
        }
        return prof;
    }

    public static Map<String, ShaderOption[]> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
        HashMap<String, ShaderOption[]> map = new HashMap<String, ShaderOption[]>();
        ShaderPackParser.parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
        return map.isEmpty() ? null : map;
    }

    private static boolean parseGuiScreen(String key, Properties props, Map<String, ShaderOption[]> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
        String val = props.getProperty(key);
        if (val == null) {
            return false;
        }
        ArrayList<ShaderOption> list = new ArrayList<ShaderOption>();
        HashSet<String> setNames = new HashSet<String>();
        String[] opNames = Config.tokenize(val, " ");
        for (int scrOps = 0; scrOps < opNames.length; ++scrOps) {
            String opName = opNames[scrOps];
            if (opName.equals("<empty>")) {
                list.add(null);
                continue;
            }
            if (setNames.contains(opName)) {
                Config.warn("[Shaders] Duplicate option: " + opName + ", key: " + key);
                continue;
            }
            setNames.add(opName);
            if (opName.equals("<profile>")) {
                if (shaderProfiles == null) {
                    Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + opName + ", key: " + key);
                    continue;
                }
                ShaderOptionProfile so2 = new ShaderOptionProfile(shaderProfiles, shaderOptions);
                list.add(so2);
                continue;
            }
            if (opName.equals("*")) {
                ShaderOptionRest var14 = new ShaderOptionRest("<rest>");
                list.add(var14);
                continue;
            }
            if (opName.startsWith("[") && opName.endsWith("]")) {
                String var16 = StrUtils.removePrefixSuffix(opName, "[", "]");
                if (!var16.matches("^[a-zA-Z0-9_]+$")) {
                    Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
                    continue;
                }
                if (!ShaderPackParser.parseGuiScreen("screen." + var16, props, map, shaderProfiles, shaderOptions)) {
                    Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
                    continue;
                }
                ShaderOptionScreen optionScreen = new ShaderOptionScreen(var16);
                list.add(optionScreen);
                continue;
            }
            ShaderOption var15 = ShaderUtils.getShaderOption(opName, shaderOptions);
            if (var15 == null) {
                Config.warn("[Shaders] Invalid option: " + opName + ", key: " + key);
                list.add(null);
                continue;
            }
            var15.setVisible(true);
            list.add(var15);
        }
        ShaderOption[] var13 = list.toArray(new ShaderOption[list.size()]);
        map.put(key, var13);
        return true;
    }

    public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int includeLevel) throws IOException {
        String fileDir = "/";
        int pos = filePath.lastIndexOf("/");
        if (pos >= 0) {
            fileDir = filePath.substring(0, pos);
        }
        CharArrayWriter caw = new CharArrayWriter();
        do {
            String filePathInc;
            String fileInc;
            String car;
            boolean absolute;
            if ((car = reader.readLine()) == null) {
                CharArrayReader car1 = new CharArrayReader(caw.toCharArray());
                return new BufferedReader(car1);
            }
            Matcher m2 = PATTERN_INCLUDE.matcher(car);
            if (m2.matches() && (car = ShaderPackParser.loadFile(filePathInc = (absolute = (fileInc = m2.group(1)).startsWith("/")) ? "/shaders" + fileInc : String.valueOf(fileDir) + "/" + fileInc, shaderPack, includeLevel)) == null) {
                throw new IOException("Included file not found: " + filePath);
            }
            caw.write(car);
            caw.write("\n");
        } while (true);
    }

    private static String loadFile(String filePath, IShaderPack shaderPack, int includeLevel) throws IOException {
        if (includeLevel >= 10) {
            throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
        }
        ++includeLevel;
        InputStream in2 = shaderPack.getResourceAsStream(filePath);
        if (in2 == null) {
            return null;
        }
        InputStreamReader isr = new InputStreamReader(in2, "ASCII");
        BufferedReader br2 = new BufferedReader(isr);
        br2 = ShaderPackParser.resolveIncludes(br2, filePath, shaderPack, includeLevel);
        CharArrayWriter caw = new CharArrayWriter();
        String line;
        while ((line = br2.readLine()) != null) {
            caw.write(line);
            caw.write("\n");
        }
        return caw.toString();
    }

}

