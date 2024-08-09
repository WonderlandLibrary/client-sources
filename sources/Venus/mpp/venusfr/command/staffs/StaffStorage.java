/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.staffs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.HashSet;
import java.util.Set;
import mpp.venusfr.utils.client.IMinecraft;

public final class StaffStorage
implements IMinecraft {
    private static final Set<String> staffs = new HashSet<String>();
    private static final File file = new File(StaffStorage.mc.gameDir + File.separator + "venusfr" + File.separator + "staffs.cfg");

    public static void load() {
        if (file.exists()) {
            staffs.addAll(Files.readAllLines(file.toPath()));
        } else {
            file.createNewFile();
        }
    }

    public static void add(String string) {
        staffs.add(string);
        StaffStorage.save();
    }

    public static void remove(String string) {
        staffs.remove(string);
        StaffStorage.save();
    }

    public static void clear() {
        staffs.clear();
        StaffStorage.save();
    }

    public static boolean isStaff(String string) {
        return staffs.contains(string);
    }

    private static void save() {
        Files.write(file.toPath(), staffs, new OpenOption[0]);
    }

    private StaffStorage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Set<String> getStaffs() {
        return staffs;
    }
}

