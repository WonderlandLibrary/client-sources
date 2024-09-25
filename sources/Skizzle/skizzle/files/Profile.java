/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package skizzle.files;

import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;
import skizzle.Client;

public class Profile {
    public String name;
    public String alias;
    public boolean isActive;

    public Profile(String Nigga, String Nigga2) {
        Profile Nigga3;
        Nigga3.name = Nigga;
        Nigga3.alias = Nigga2;
        Nigga3.isActive = true;
        Client.currentProfile = Nigga;
    }

    public boolean isModuleEnabled(String Nigga) throws IOException {
        Profile Nigga2;
        String Nigga3 = String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()) + File.separator + Qprot0.0("\uc5dd\u71c0\ufe8b\ua7a7\uf75e\u4058\u8c2a") + File.separator + Nigga2.name + File.separator + Qprot0.0("\uc5e3\u71c4\ufe86\ua7a8\uf748\u4051\u8c3c") + Qprot0.0("\uc5a0\u71df\ufe9a\ua7a9"));
        File Nigga4 = new File(Nigga3);
        String Nigga5 = FileUtils.readFileToString((File)Nigga4);
        return Nigga5.contains(Nigga);
    }

    public String readSettings() throws IOException {
        Profile Nigga;
        String Nigga2 = String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()) + File.separator + Qprot0.0("\uc5dd\u71c0\ufe8b\u2655\u7580\u4058\u8c2a") + File.separator + Nigga.name + File.separator + Qprot0.0("\uc5fd\u71ce\ufe96\u265b\u7593\u405a\u8c28\ua9a8") + Qprot0.0("\uc5a0\u71df\ufe9a\u265b"));
        File Nigga3 = new File(Nigga2);
        return FileUtils.readFileToString((File)Nigga3);
    }

    public static {
        throw throwable;
    }
}

