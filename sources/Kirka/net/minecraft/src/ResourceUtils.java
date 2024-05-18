/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.io.File;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorField;

public class ResourceUtils {
    private static ReflectorClass ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
    private static ReflectorField ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ForgeAbstractResourcePack, "resourcePackFile");
    private static boolean directAccessValid = true;

    public static File getResourcePackFile(AbstractResourcePack arp) {
        block3 : {
            if (directAccessValid) {
                try {
                    return arp.resourcePackFile;
                }
                catch (IllegalAccessError var2) {
                    directAccessValid = false;
                    if (ForgeAbstractResourcePack_resourcePackFile.exists()) break block3;
                    throw var2;
                }
            }
        }
        return (File)Reflector.getFieldValue(arp, ForgeAbstractResourcePack_resourcePackFile);
    }
}

