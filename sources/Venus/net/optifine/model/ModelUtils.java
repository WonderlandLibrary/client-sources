/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.util.Direction;
import net.optifine.Config;

public class ModelUtils {
    private static final Random RANDOM = new Random(0L);

    public static void dbgModel(IBakedModel iBakedModel) {
        if (iBakedModel != null) {
            Config.dbg("Model: " + iBakedModel + ", ao: " + iBakedModel.isAmbientOcclusion() + ", gui3d: " + iBakedModel.isGui3d() + ", builtIn: " + iBakedModel.isBuiltInRenderer() + ", particle: " + iBakedModel.getParticleTexture());
            Direction[] directionArray = Direction.VALUES;
            for (int i = 0; i < directionArray.length; ++i) {
                Direction direction = directionArray[i];
                List<BakedQuad> list = iBakedModel.getQuads(null, direction, RANDOM);
                ModelUtils.dbgQuads(direction.getString(), list, "  ");
            }
            List<BakedQuad> list = iBakedModel.getQuads(null, null, RANDOM);
            ModelUtils.dbgQuads("General", list, "  ");
        }
    }

    private static void dbgQuads(String string, List<BakedQuad> list, String string2) {
        for (BakedQuad bakedQuad : list) {
            ModelUtils.dbgQuad(string, bakedQuad, string2);
        }
    }

    public static void dbgQuad(String string, BakedQuad bakedQuad, String string2) {
        Config.dbg(string2 + "Quad: " + bakedQuad.getClass().getName() + ", type: " + string + ", face: " + bakedQuad.getFace() + ", tint: " + bakedQuad.getTintIndex() + ", sprite: " + bakedQuad.getSprite());
        ModelUtils.dbgVertexData(bakedQuad.getVertexData(), "  " + string2);
    }

    public static void dbgVertexData(int[] nArray, String string) {
        int n = nArray.length / 4;
        Config.dbg(string + "Length: " + nArray.length + ", step: " + n);
        for (int i = 0; i < 4; ++i) {
            int n2 = i * n;
            float f = Float.intBitsToFloat(nArray[n2 + 0]);
            float f2 = Float.intBitsToFloat(nArray[n2 + 1]);
            float f3 = Float.intBitsToFloat(nArray[n2 + 2]);
            int n3 = nArray[n2 + 3];
            float f4 = Float.intBitsToFloat(nArray[n2 + 4]);
            float f5 = Float.intBitsToFloat(nArray[n2 + 5]);
            Config.dbg(string + i + " xyz: " + f + "," + f2 + "," + f3 + " col: " + n3 + " u,v: " + f4 + "," + f5);
        }
    }

    public static IBakedModel duplicateModel(IBakedModel iBakedModel) {
        List list = ModelUtils.duplicateQuadList(iBakedModel.getQuads(null, null, RANDOM));
        Direction[] directionArray = Direction.VALUES;
        HashMap<Direction, List<BakedQuad>> hashMap = new HashMap<Direction, List<BakedQuad>>();
        for (int i = 0; i < directionArray.length; ++i) {
            Direction direction = directionArray[i];
            List<BakedQuad> list2 = iBakedModel.getQuads(null, direction, RANDOM);
            List list3 = ModelUtils.duplicateQuadList(list2);
            hashMap.put(direction, list3);
        }
        return new SimpleBakedModel(list, hashMap, iBakedModel.isAmbientOcclusion(), iBakedModel.isGui3d(), true, iBakedModel.getParticleTexture(), iBakedModel.getItemCameraTransforms(), iBakedModel.getOverrides());
    }

    public static List duplicateQuadList(List<BakedQuad> list) {
        ArrayList<BakedQuad> arrayList = new ArrayList<BakedQuad>();
        for (BakedQuad bakedQuad : list) {
            BakedQuad bakedQuad2 = ModelUtils.duplicateQuad(bakedQuad);
            arrayList.add(bakedQuad2);
        }
        return arrayList;
    }

    public static BakedQuad duplicateQuad(BakedQuad bakedQuad) {
        return new BakedQuad((int[])bakedQuad.getVertexData().clone(), bakedQuad.getTintIndex(), bakedQuad.getFace(), bakedQuad.getSprite(), bakedQuad.applyDiffuseLighting());
    }
}

