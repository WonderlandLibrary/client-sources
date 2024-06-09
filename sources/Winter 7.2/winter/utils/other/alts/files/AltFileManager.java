/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.alts.files;

import java.util.ArrayList;
import winter.utils.other.alts.files.Alts;
import winter.utils.other.alts.files.CustomFile;

public class AltFileManager {
    public static ArrayList<CustomFile> Files = new ArrayList();

    public AltFileManager() {
        Files.add(new Alts());
    }

    public CustomFile getFile(Class<? extends CustomFile> clazz) {
        for (CustomFile file : Files) {
            if (file.getClass() != clazz) continue;
            return file;
        }
        return null;
    }
}

