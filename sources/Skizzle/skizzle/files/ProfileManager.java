/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package skizzle.files;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;
import skizzle.Client;
import skizzle.files.FileManager;
import skizzle.files.Profile;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.modules.profiles.ProfileModule;

public class ProfileManager {
    public static String homeDir;
    public static CopyOnWriteArrayList<Profile> profiles;
    public static Minecraft mc;
    public static FileManager fm;

    public ProfileManager() {
        ProfileManager Nigga;
    }

    public static boolean isProfile(String Nigga) {
        for (File Nigga2 : ProfileManager.getProfiles()) {
            if (!Nigga2.getName().equals(Nigga)) continue;
            return true;
        }
        return false;
    }

    public static void setProfile(String Nigga) {
        Client.currentProfile = Nigga;
        ModuleManager.reloadModules();
    }

    public static boolean deleteProfile(String Nigga) {
        try {
            FileUtils.deleteDirectory((File)new File(String.valueOf(homeDir) + File.separator + Client.name + File.separator + Qprot0.0("\u4aef\u71d9\u71bc\u50ee\u1309\ucf49\u8c2a\u2699") + File.separator + Nigga + File.separator));
            return true;
        }
        catch (IOException Nigga2) {
            return false;
        }
    }

    public static void updateProfiles() {
        for (Module module : Client.modules) {
            if (!(module instanceof ProfileModule)) continue;
            Client.modules.remove(module);
        }
        try {
            for (File file : ProfileManager.getProfiles()) {
                Client.modules.add(new ProfileModule(file.getName()));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveProfile(String Nigga) {
        fm.writeFile("", Qprot0.0("\u4aef\u71d9\u71bc\u8cfc\u4f1f\ucf49\u8c2a\u2699") + File.separator + Nigga, Qprot0.0("\u4aec\u71ce\u71a7\u8cee\u4f1f\ucf4b\u8c28\u2699"));
        fm.writeFile("", Qprot0.0("\u4aef\u71d9\u71bc\u8cfc\u4f1f\ucf49\u8c2a\u2699") + File.separator + Nigga, Qprot0.0("\u4af2\u71c4\u71b7\u8cef\u4f1a\ucf40\u8c3c"));
        Client.currentProfile = Nigga;
        fm.updateSettings();
        fm.updateActiveModules();
        ProfileManager.updateProfiles();
    }

    public static String getCurrentProfile() {
        for (Profile Nigga : profiles) {
            if (!Nigga.isActive) continue;
            return Nigga.name;
        }
        return Qprot0.0("\u4afb\u71ce\u71b5\u6371\u218d\ucf49\u8c3b");
    }

    public static File[] getProfiles() {
        File[] Nigga = new File(String.valueOf(homeDir) + File.separator + Client.name + File.separator + Qprot0.0("\u4aef\u71d9\u71bc\u4cf0\u0f6b\ucf49\u8c2a\u2699") + File.separator).listFiles(File::isDirectory);
        return Nigga;
    }

    static {
        fm = new FileManager();
        profiles = new CopyOnWriteArrayList();
        mc = Minecraft.getMinecraft();
        homeDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
    }
}

