package optfine;

import net.minecraft.client.resources.*;
import java.io.*;

public class ResourceUtils
{
    private static ReflectorClass ForgeAbstractResourcePack;
    private static ReflectorField ForgeAbstractResourcePack_resourcePackFile;
    private static boolean directAccessValid;
    private static final String[] I;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        ResourceUtils.ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
        ResourceUtils.ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ResourceUtils.ForgeAbstractResourcePack, ResourceUtils.I["".length()]);
        ResourceUtils.directAccessValid = (" ".length() != 0);
    }
    
    public static File getResourcePackFile(final AbstractResourcePack abstractResourcePack) {
        if (ResourceUtils.directAccessValid) {
            try {
                return abstractResourcePack.resourcePackFile;
            }
            catch (IllegalAccessError illegalAccessError) {
                ResourceUtils.directAccessValid = ("".length() != 0);
                if (!ResourceUtils.ForgeAbstractResourcePack_resourcePackFile.exists()) {
                    throw illegalAccessError;
                }
            }
        }
        return (File)Reflector.getFieldValue(abstractResourcePack, ResourceUtils.ForgeAbstractResourcePack_resourcePackFile);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("!\t&\u0018\u0011!\u000f0'\u00050\u0007\u0013\u001e\b6", "SlUwd");
    }
}
