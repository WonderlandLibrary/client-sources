/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.ShaderMacro;
import net.optifine.shaders.config.ShaderMacros;
import net.optifine.shaders.config.ShaderOption;

public class MacroProcessor {
    public static InputStream process(InputStream inputStream, String string, boolean bl) throws IOException {
        Object object;
        Object object2 = Config.readInputStream(inputStream, "ASCII");
        String string2 = MacroProcessor.getMacroHeader((String)object2, bl);
        if (!string2.isEmpty()) {
            object2 = string2 + (String)object2;
            if (Shaders.saveFinalShaders) {
                object = string.replace(':', '/') + ".pre";
                Shaders.saveShader((String)object, (String)object2);
            }
            object2 = MacroProcessor.process((String)object2);
        }
        if (Shaders.saveFinalShaders) {
            object = string.replace(':', '/');
            Shaders.saveShader((String)object, (String)object2);
        }
        object = ((String)object2).getBytes("ASCII");
        return new ByteArrayInputStream((byte[])object);
    }

    public static String process(String string) throws IOException {
        StringReader stringReader = new StringReader(string);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        MacroState macroState = new MacroState();
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String string2;
            if ((string2 = bufferedReader.readLine()) == null) {
                string2 = stringBuilder.toString();
                return string2;
            }
            if (!macroState.processLine(string2) || MacroState.isMacroLine(string2)) continue;
            stringBuilder.append(string2);
            stringBuilder.append("\n");
        }
    }

    /*
     * Unable to fully structure code
     */
    private static String getMacroHeader(String var0, boolean var1_1) throws IOException {
        var2_2 = new StringBuilder();
        var3_3 = null;
        var4_4 = null;
        var5_5 = new StringReader(var0);
        var6_6 = new BufferedReader(var5_5);
        block0: while (true) {
            if ((var7_7 = var6_6.readLine()) == null) {
                return var2_2.toString();
            }
            if (!MacroState.isMacroLine(var7_7)) continue;
            if (var2_2.length() == 0) {
                var2_2.append(ShaderMacros.getFixedMacroLines());
            }
            if (var1_1) {
                if (var3_3 == null) {
                    var3_3 = MacroProcessor.getMacroOptions();
                }
                var8_8 = var3_3.iterator();
                while (var8_8.hasNext()) {
                    var9_9 = var8_8.next();
                    if (!var7_7.contains(var9_9.getName())) continue;
                    var2_2.append(var9_9.getSourceLine());
                    var2_2.append("\n");
                    var8_8.remove();
                }
            }
            if (var4_4 == null) {
                var4_4 = new ArrayList<ShaderMacro>(Arrays.asList(ShaderMacros.getExtensions()));
            }
            var8_8 = var4_4.iterator();
            while (true) {
                if (var8_8.hasNext()) ** break;
                continue block0;
                var9_9 = (ShaderMacro)var8_8.next();
                if (!var7_7.contains(var9_9.getName())) continue;
                var2_2.append(var9_9.getSourceLine());
                var2_2.append("\n");
                var8_8.remove();
            }
            break;
        }
    }

    private static List<ShaderOption> getMacroOptions() {
        ArrayList<ShaderOption> arrayList = new ArrayList<ShaderOption>();
        ShaderOption[] shaderOptionArray = Shaders.getShaderPackOptions();
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            String string = shaderOption.getSourceLine();
            if (string == null || !string.startsWith("#")) continue;
            arrayList.add(shaderOption);
        }
        return arrayList;
    }
}

