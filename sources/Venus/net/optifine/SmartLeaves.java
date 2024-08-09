/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.model.ModelUtils;

public class SmartLeaves {
    private static IBakedModel modelLeavesCullAcacia = null;
    private static IBakedModel modelLeavesCullBirch = null;
    private static IBakedModel modelLeavesCullDarkOak = null;
    private static IBakedModel modelLeavesCullJungle = null;
    private static IBakedModel modelLeavesCullOak = null;
    private static IBakedModel modelLeavesCullSpruce = null;
    private static List generalQuadsCullAcacia = null;
    private static List generalQuadsCullBirch = null;
    private static List generalQuadsCullDarkOak = null;
    private static List generalQuadsCullJungle = null;
    private static List generalQuadsCullOak = null;
    private static List generalQuadsCullSpruce = null;
    private static IBakedModel modelLeavesDoubleAcacia = null;
    private static IBakedModel modelLeavesDoubleBirch = null;
    private static IBakedModel modelLeavesDoubleDarkOak = null;
    private static IBakedModel modelLeavesDoubleJungle = null;
    private static IBakedModel modelLeavesDoubleOak = null;
    private static IBakedModel modelLeavesDoubleSpruce = null;
    private static final Random RANDOM = new Random();

    public static IBakedModel getLeavesModel(IBakedModel iBakedModel, BlockState blockState) {
        if (!Config.isTreesSmart()) {
            return iBakedModel;
        }
        List<BakedQuad> list = iBakedModel.getQuads(blockState, null, RANDOM);
        if (list == generalQuadsCullAcacia) {
            return modelLeavesDoubleAcacia;
        }
        if (list == generalQuadsCullBirch) {
            return modelLeavesDoubleBirch;
        }
        if (list == generalQuadsCullDarkOak) {
            return modelLeavesDoubleDarkOak;
        }
        if (list == generalQuadsCullJungle) {
            return modelLeavesDoubleJungle;
        }
        if (list == generalQuadsCullOak) {
            return modelLeavesDoubleOak;
        }
        return list == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : iBakedModel;
    }

    public static boolean isSameLeaves(BlockState blockState, BlockState blockState2) {
        Block block;
        if (blockState == blockState2) {
            return false;
        }
        Block block2 = blockState.getBlock();
        return block2 == (block = blockState2.getBlock());
    }

    public static void updateLeavesModels() {
        ArrayList arrayList = new ArrayList();
        modelLeavesCullAcacia = SmartLeaves.getModelCull("acacia", arrayList);
        modelLeavesCullBirch = SmartLeaves.getModelCull("birch", arrayList);
        modelLeavesCullDarkOak = SmartLeaves.getModelCull("dark_oak", arrayList);
        modelLeavesCullJungle = SmartLeaves.getModelCull("jungle", arrayList);
        modelLeavesCullOak = SmartLeaves.getModelCull("oak", arrayList);
        modelLeavesCullSpruce = SmartLeaves.getModelCull("spruce", arrayList);
        generalQuadsCullAcacia = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullAcacia);
        generalQuadsCullBirch = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullBirch);
        generalQuadsCullDarkOak = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullDarkOak);
        generalQuadsCullJungle = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullJungle);
        generalQuadsCullOak = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullOak);
        generalQuadsCullSpruce = SmartLeaves.getGeneralQuadsSafe(modelLeavesCullSpruce);
        modelLeavesDoubleAcacia = SmartLeaves.getModelDoubleFace(modelLeavesCullAcacia);
        modelLeavesDoubleBirch = SmartLeaves.getModelDoubleFace(modelLeavesCullBirch);
        modelLeavesDoubleDarkOak = SmartLeaves.getModelDoubleFace(modelLeavesCullDarkOak);
        modelLeavesDoubleJungle = SmartLeaves.getModelDoubleFace(modelLeavesCullJungle);
        modelLeavesDoubleOak = SmartLeaves.getModelDoubleFace(modelLeavesCullOak);
        modelLeavesDoubleSpruce = SmartLeaves.getModelDoubleFace(modelLeavesCullSpruce);
        if (arrayList.size() > 0) {
            Config.dbg("Enable face culling: " + Config.arrayToString(arrayList.toArray()));
        }
    }

    private static List getGeneralQuadsSafe(IBakedModel iBakedModel) {
        return iBakedModel == null ? null : iBakedModel.getQuads(null, null, RANDOM);
    }

    static IBakedModel getModelCull(String string, List list) {
        ModelManager modelManager = Config.getModelManager();
        if (modelManager == null) {
            return null;
        }
        ResourceLocation resourceLocation = new ResourceLocation("blockstates/" + string + "_leaves.json");
        if (!Config.isFromDefaultResourcePack(resourceLocation)) {
            return null;
        }
        ResourceLocation resourceLocation2 = new ResourceLocation("models/block/" + string + "_leaves.json");
        if (!Config.isFromDefaultResourcePack(resourceLocation2)) {
            return null;
        }
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(string + "_leaves", "normal");
        IBakedModel iBakedModel = modelManager.getModel(modelResourceLocation);
        if (iBakedModel != null && iBakedModel != modelManager.getMissingModel()) {
            List<BakedQuad> list2 = iBakedModel.getQuads(null, null, RANDOM);
            if (list2.size() == 0) {
                return iBakedModel;
            }
            if (list2.size() != 6) {
                return null;
            }
            for (BakedQuad bakedQuad : list2) {
                List<BakedQuad> list3 = iBakedModel.getQuads(null, bakedQuad.getFace(), RANDOM);
                if (list3.size() > 0) {
                    return null;
                }
                list3.add(bakedQuad);
            }
            list2.clear();
            list.add(string + "_leaves");
            return iBakedModel;
        }
        return null;
    }

    private static IBakedModel getModelDoubleFace(IBakedModel iBakedModel) {
        List[] listArray;
        if (iBakedModel == null) {
            return null;
        }
        if (iBakedModel.getQuads(null, null, RANDOM).size() > 0) {
            Config.warn("SmartLeaves: Model is not cube, general quads: " + iBakedModel.getQuads(null, null, RANDOM).size() + ", model: " + iBakedModel);
            return iBakedModel;
        }
        Direction[] directionArray = Direction.VALUES;
        for (int i = 0; i < directionArray.length; ++i) {
            listArray = directionArray[i];
            List<BakedQuad> list = iBakedModel.getQuads(null, (Direction)listArray, RANDOM);
            if (list.size() == 1) continue;
            Config.warn("SmartLeaves: Model is not cube, side: " + (Direction)listArray + ", quads: " + list.size() + ", model: " + iBakedModel);
            return iBakedModel;
        }
        IBakedModel iBakedModel2 = ModelUtils.duplicateModel(iBakedModel);
        listArray = new List[directionArray.length];
        for (int i = 0; i < directionArray.length; ++i) {
            Direction direction = directionArray[i];
            List<BakedQuad> list = iBakedModel2.getQuads(null, direction, RANDOM);
            BakedQuad bakedQuad = list.get(0);
            BakedQuad bakedQuad2 = new BakedQuad((int[])bakedQuad.getVertexData().clone(), bakedQuad.getTintIndex(), bakedQuad.getFace(), bakedQuad.getSprite(), bakedQuad.applyDiffuseLighting());
            int[] nArray = bakedQuad2.getVertexData();
            int[] nArray2 = (int[])nArray.clone();
            int n = nArray.length / 4;
            System.arraycopy(nArray, 0 * n, nArray2, 3 * n, n);
            System.arraycopy(nArray, 1 * n, nArray2, 2 * n, n);
            System.arraycopy(nArray, 2 * n, nArray2, 1 * n, n);
            System.arraycopy(nArray, 3 * n, nArray2, 0 * n, n);
            System.arraycopy(nArray2, 0, nArray, 0, nArray2.length);
            list.add(bakedQuad2);
        }
        return iBakedModel2;
    }
}

