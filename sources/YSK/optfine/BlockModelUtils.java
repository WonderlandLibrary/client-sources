package optfine;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.util.vector.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;

public class BlockModelUtils
{
    private static final String[] I;
    
    public static IBakedModel makeModelCube(final String s, final int n) {
        return makeModelCube(Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(s), n);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("`", "CBMtm");
    }
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static BakedQuad makeBakedQuad(final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final int n) {
        final Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
        final Vector3f vector3f2 = new Vector3f(16.0f, 16.0f, 16.0f);
        final float[] array = new float[0x2C ^ 0x28];
        array["".length()] = 0.0f;
        array[" ".length()] = 0.0f;
        array["  ".length()] = 16.0f;
        array["   ".length()] = 16.0f;
        return new FaceBakery().makeBakedQuad(vector3f, vector3f2, new BlockPartFace(enumFacing, n, BlockModelUtils.I["".length()] + enumFacing.getName(), new BlockFaceUV(array, "".length())), textureAtlasSprite, enumFacing, ModelRotation.X0_Y0, null, "".length() != 0, " ".length() != 0);
    }
    
    public static IBakedModel makeModelCube(final TextureAtlasSprite textureAtlasSprite, final int n) {
        final ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
        final EnumFacing[] values = EnumFacing.values();
        final ArrayList list2 = new ArrayList<List<BakedQuad>>(values.length);
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < values.length) {
            final EnumFacing enumFacing = values[i];
            final ArrayList<BakedQuad> list3 = new ArrayList<BakedQuad>();
            list3.add(makeBakedQuad(enumFacing, textureAtlasSprite, n));
            list2.add(list3);
            ++i;
        }
        return new SimpleBakedModel(list, (List<List<BakedQuad>>)list2, " ".length() != 0, " ".length() != 0, textureAtlasSprite, ItemCameraTransforms.DEFAULT);
    }
    
    static {
        I();
    }
}
