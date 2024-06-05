package optfine;

import net.minecraft.client.resources.*;
import java.io.*;

public class ResourceUtils
{
    private static ReflectorClass ForgeAbstractResourcePack;
    private static ReflectorField ForgeAbstractResourcePack_resourcePackFile;
    private static boolean directAccessValid;
    
    public static File getResourcePackFile(final AbstractResourcePack p_getResourcePackFile_0_) {
        if (ResourceUtils.directAccessValid) {
            try {
                return p_getResourcePackFile_0_.resourcePackFile;
            }
            catch (IllegalAccessError illegalaccesserror) {
                ResourceUtils.directAccessValid = false;
                if (!ResourceUtils.ForgeAbstractResourcePack_resourcePackFile.exists()) {
                    throw illegalaccesserror;
                }
            }
        }
        return (File)Reflector.getFieldValue(p_getResourcePackFile_0_, ResourceUtils.ForgeAbstractResourcePack_resourcePackFile);
    }
    
    static {
        ResourceUtils.ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
        ResourceUtils.ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ResourceUtils.ForgeAbstractResourcePack, "resourcePackFile");
        ResourceUtils.directAccessValid = true;
    }
}
