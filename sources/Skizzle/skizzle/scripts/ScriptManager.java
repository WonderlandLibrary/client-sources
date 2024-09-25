/*
 * Decompiled with CFR 0.150.
 */
package skizzle.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import net.minecraft.client.Minecraft;
import skizzle.Client;
import skizzle.modules.Module;
import skizzle.scripts.Script;
import skizzle.util.MoveUtil;

public class ScriptManager {
    public static {
        throw throwable;
    }

    public static void addScripts() {
        File[] Nigga;
        for (Module module : Client.modules) {
            if (!module.category.equals((Object)Module.Category.SCRIPTS)) continue;
            Client.modules.remove(module);
        }
        File file = new File(String.valueOf(Client.skizzlePath) + Qprot0.0("\ueab3\u71c8\ud1c6\u04b8\ua792\u6f0e\u8c3c"));
        boolean Nigga3 = file.exists();
        if (!Nigga3) {
            file.mkdir();
        }
        if ((Nigga = file.listFiles()) != null) {
            File[] arrfile = Nigga;
            int n = Nigga.length;
            for (int i = 0; i < n; ++i) {
                File Nigga4 = arrfile[i];
                try {
                    ScriptEngine Nigga5 = new ScriptEngineManager().getEngineByName(Qprot0.0("\ueaae\u71ca\ud1c7\u04b9\ua78d\u6f08\u8c21"));
                    String Nigga6 = ScriptManager.readScriptFile(String.valueOf(Client.skizzlePath) + Qprot0.0("\ueab3\u71c8\ud1c6\u04b8\ua792\u6f0e\u8c3c\u86d1") + Nigga4.getName());
                    String Nigga7 = String.format(Qprot0.0("\ueab6\u71ca\ud1c6\u04f1\ua78f\u6f15\u8c39\u86e8\uf417\u0c95\ue577\uaf26\u6837\ud10e\ud3ba\u76eb\u42fd\u5cd6\ub445\ub8c6\u132e\u0188\ufab8\u4b2a\u6067\u57ee\u2f45\u3fec"), MoveUtil.class.getCanonicalName());
                    Nigga6 = String.valueOf(Nigga7) + Nigga6;
                    Client.modules.add(new Script(Nigga4.getName().replace(Qprot0.0("\ueaee\u71c1\ud1c7"), ""), Nigga5.eval(Nigga6), Nigga5));
                    continue;
                }
                catch (Exception Nigga8) {
                    if (Minecraft.getMinecraft().thePlayer != null) {
                        Minecraft.getMinecraft().thePlayer.skizzleMessage(String.format(Qprot0.0("\ueae0\u718b\ud113\u04b2\ua7a4\u6f1b\u8c26\u86e1\uf452\u0ccc\ue577\uaf18\u6839\ud158\ud3b7\u76aa\u42e8\u5ccb\ub415\ub884\u1323\u01d9\ufa3a\u4b3a\u6062\u57fd\u2f5e\u3fc3\ue117"), Nigga4.getName().replace(Qprot0.0("\ueaee\u71c1\ud1c7"), ""), Nigga8));
                    }
                    Nigga8.printStackTrace();
                }
            }
        }
    }

    public ScriptManager() {
        ScriptManager Nigga;
    }

    public static void toggle(String Nigga) {
        for (Module Nigga2 : Client.modules) {
            if (!Nigga2.name.equals(Nigga)) continue;
            Nigga2.toggle();
        }
    }

    public static String readScriptFile(String Nigga) {
        StringBuilder Nigga2 = new StringBuilder();
        try {
            Throwable throwable = null;
            Object var3_5 = null;
            try (Stream<String> Nigga3 = Files.lines(Paths.get(Nigga, new String[0]), StandardCharsets.UTF_8);){
                Nigga3.forEach(arg_0 -> ScriptManager.lambda$0(Nigga2, arg_0));
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException Nigga4) {
            Nigga4.printStackTrace();
        }
        return Nigga2.toString();
    }

    public static void lambda$0(StringBuilder stringBuilder, String Nigga) {
        StringBuilder stringBuilder2 = stringBuilder.append(Nigga).append("\n");
    }
}

