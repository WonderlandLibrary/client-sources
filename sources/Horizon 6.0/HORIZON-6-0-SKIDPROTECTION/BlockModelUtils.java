package HORIZON-6-0-SKIDPROTECTION;

import javax.vecmath.Vector3f;
import java.util.List;
import java.util.ArrayList;

public class BlockModelUtils
{
    public static IBakedModel HorizonCode_Horizon_È(final String spriteName, final int tintIndex) {
        final TextureAtlasSprite sprite = Config.È().áŠ().HorizonCode_Horizon_È(spriteName);
        return HorizonCode_Horizon_È(sprite, tintIndex);
    }
    
    public static IBakedModel HorizonCode_Horizon_È(final TextureAtlasSprite sprite, final int tintIndex) {
        final ArrayList generalQuads = new ArrayList();
        final EnumFacing[] facings = EnumFacing.values();
        final ArrayList faceQuads = new ArrayList(facings.length);
        for (int bakedModel = 0; bakedModel < facings.length; ++bakedModel) {
            final EnumFacing facing = facings[bakedModel];
            final ArrayList quads = new ArrayList();
            quads.add(HorizonCode_Horizon_È(facing, sprite, tintIndex));
            faceQuads.add(quads);
        }
        final SimpleBakedModel var8 = new SimpleBakedModel(generalQuads, faceQuads, true, true, sprite, ItemCameraTransforms.HorizonCode_Horizon_È);
        return var8;
    }
    
    private static BakedQuad HorizonCode_Horizon_È(final EnumFacing facing, final TextureAtlasSprite sprite, final int tintIndex) {
        final Vector3f posFrom = new Vector3f(0.0f, 0.0f, 0.0f);
        final Vector3f posTo = new Vector3f(16.0f, 16.0f, 16.0f);
        final BlockFaceUV uv = new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0);
        final BlockPartFace face = new BlockPartFace(facing, tintIndex, "#" + facing.HorizonCode_Horizon_È(), uv);
        final ModelRotation modelRotation = ModelRotation.HorizonCode_Horizon_È;
        final Object partRotation = null;
        final boolean uvLocked = false;
        final boolean shade = true;
        final FaceBakery faceBakery = new FaceBakery();
        final BakedQuad quad = faceBakery.HorizonCode_Horizon_È(posFrom, posTo, face, sprite, facing, modelRotation, (BlockPartRotation)partRotation, uvLocked, shade);
        return quad;
    }
}
