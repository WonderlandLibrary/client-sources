/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.BlockPartRotation;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.ModelUtils;

public class BlockModelUtils {
    private static final float VERTEX_COORD_ACCURACY = 1.0E-6f;
    private static final Random RANDOM = new Random(0L);

    public static IBakedModel makeModelCube(String string, int n) {
        TextureAtlasSprite textureAtlasSprite = Config.getTextureMap().getUploadedSprite(string);
        return BlockModelUtils.makeModelCube(textureAtlasSprite, n);
    }

    public static IBakedModel makeModelCube(TextureAtlasSprite textureAtlasSprite, int n) {
        Object object;
        ArrayList<BakedQuad> arrayList = new ArrayList<BakedQuad>();
        Direction[] directionArray = Direction.VALUES;
        HashMap<Direction, List<BakedQuad>> hashMap = new HashMap<Direction, List<BakedQuad>>();
        for (int i = 0; i < directionArray.length; ++i) {
            object = directionArray[i];
            ArrayList<BakedQuad> arrayList2 = new ArrayList<BakedQuad>();
            arrayList2.add(BlockModelUtils.makeBakedQuad((Direction)object, textureAtlasSprite, n));
            hashMap.put((Direction)object, arrayList2);
        }
        ItemOverrideList itemOverrideList = ItemOverrideList.EMPTY;
        object = new SimpleBakedModel(arrayList, hashMap, true, true, true, textureAtlasSprite, ItemCameraTransforms.DEFAULT, itemOverrideList);
        return object;
    }

    public static IBakedModel joinModelsCube(IBakedModel iBakedModel, IBakedModel iBakedModel2) {
        Object object;
        int n;
        ArrayList<BakedQuad> arrayList = new ArrayList<BakedQuad>();
        arrayList.addAll(iBakedModel.getQuads(null, null, RANDOM));
        arrayList.addAll(iBakedModel2.getQuads(null, null, RANDOM));
        Direction[] directionArray = Direction.VALUES;
        HashMap<Direction, List<BakedQuad>> hashMap = new HashMap<Direction, List<BakedQuad>>();
        for (n = 0; n < directionArray.length; ++n) {
            Direction direction = directionArray[n];
            object = new ArrayList<BakedQuad>();
            object.addAll(iBakedModel.getQuads(null, direction, RANDOM));
            object.addAll(iBakedModel2.getQuads(null, direction, RANDOM));
            hashMap.put(direction, (List<BakedQuad>)object);
        }
        n = iBakedModel.isAmbientOcclusion() ? 1 : 0;
        boolean bl = iBakedModel.isBuiltInRenderer();
        object = iBakedModel.getParticleTexture();
        ItemCameraTransforms itemCameraTransforms = iBakedModel.getItemCameraTransforms();
        ItemOverrideList itemOverrideList = iBakedModel.getOverrides();
        SimpleBakedModel simpleBakedModel = new SimpleBakedModel(arrayList, hashMap, n != 0, bl, true, (TextureAtlasSprite)object, itemCameraTransforms, itemOverrideList);
        return simpleBakedModel;
    }

    public static BakedQuad makeBakedQuad(Direction direction, TextureAtlasSprite textureAtlasSprite, int n) {
        Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f vector3f2 = new Vector3f(16.0f, 16.0f, 16.0f);
        BlockFaceUV blockFaceUV = new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0);
        BlockPartFace blockPartFace = new BlockPartFace(direction, n, "#" + direction.getString(), blockFaceUV);
        ModelRotation modelRotation = ModelRotation.X0_Y0;
        BlockPartRotation blockPartRotation = null;
        boolean bl = true;
        ResourceLocation resourceLocation = textureAtlasSprite.getName();
        FaceBakery faceBakery = new FaceBakery();
        return faceBakery.bakeQuad(vector3f, vector3f2, blockPartFace, textureAtlasSprite, direction, modelRotation, blockPartRotation, bl, resourceLocation);
    }

    public static IBakedModel makeModel(String string, String string2, String string3) {
        AtlasTexture atlasTexture = Config.getTextureMap();
        TextureAtlasSprite textureAtlasSprite = atlasTexture.getUploadedSprite(string2);
        TextureAtlasSprite textureAtlasSprite2 = atlasTexture.getUploadedSprite(string3);
        return BlockModelUtils.makeModel(string, textureAtlasSprite, textureAtlasSprite2);
    }

    public static IBakedModel makeModel(String string, TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite textureAtlasSprite2) {
        if (textureAtlasSprite != null && textureAtlasSprite2 != null) {
            ModelManager modelManager = Config.getModelManager();
            if (modelManager == null) {
                return null;
            }
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(string, "");
            IBakedModel iBakedModel = modelManager.getModel(modelResourceLocation);
            if (iBakedModel != null && iBakedModel != modelManager.getMissingModel()) {
                IBakedModel iBakedModel2 = ModelUtils.duplicateModel(iBakedModel);
                Direction[] directionArray = Direction.VALUES;
                for (int i = 0; i < directionArray.length; ++i) {
                    Direction direction = directionArray[i];
                    List<BakedQuad> list = iBakedModel2.getQuads(null, direction, RANDOM);
                    BlockModelUtils.replaceTexture(list, textureAtlasSprite, textureAtlasSprite2);
                }
                List<BakedQuad> list = iBakedModel2.getQuads(null, null, RANDOM);
                BlockModelUtils.replaceTexture(list, textureAtlasSprite, textureAtlasSprite2);
                return iBakedModel2;
            }
            return null;
        }
        return null;
    }

    private static void replaceTexture(List<BakedQuad> list, TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite textureAtlasSprite2) {
        ArrayList<BakedQuad> arrayList = new ArrayList<BakedQuad>();
        for (BakedQuad bakedQuad : list) {
            if (bakedQuad.getSprite() == textureAtlasSprite) {
                bakedQuad = new BakedQuadRetextured(bakedQuad, textureAtlasSprite2);
            }
            arrayList.add(bakedQuad);
        }
        list.clear();
        list.addAll(arrayList);
    }

    public static void snapVertexPosition(Vector3f vector3f) {
        vector3f.set(BlockModelUtils.snapVertexCoord(vector3f.getX()), BlockModelUtils.snapVertexCoord(vector3f.getY()), BlockModelUtils.snapVertexCoord(vector3f.getZ()));
    }

    private static float snapVertexCoord(float f) {
        if (f > -1.0E-6f && f < 1.0E-6f) {
            return 0.0f;
        }
        return f > 0.999999f && f < 1.000001f ? 1.0f : f;
    }

    public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB axisAlignedBB, AbstractBlock.OffsetType offsetType, BlockPos blockPos) {
        int n = blockPos.getX();
        int n2 = blockPos.getZ();
        long l = (long)(n * 3129871) ^ (long)n2 * 116129781L;
        l = l * l * 42317861L + l * 11L;
        double d = ((double)((float)(l >> 16 & 0xFL) / 15.0f) - 0.5) * 0.5;
        double d2 = ((double)((float)(l >> 24 & 0xFL) / 15.0f) - 0.5) * 0.5;
        double d3 = 0.0;
        if (offsetType == AbstractBlock.OffsetType.XYZ) {
            d3 = ((double)((float)(l >> 20 & 0xFL) / 15.0f) - 1.0) * 0.2;
        }
        return axisAlignedBB.offset(d, d3, d2);
    }
}

