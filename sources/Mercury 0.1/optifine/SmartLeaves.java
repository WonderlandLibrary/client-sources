/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.ModelUtils;

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

    public static IBakedModel getLeavesModel(IBakedModel model) {
        if (!Config.isTreesSmart()) {
            return model;
        }
        List generalQuads = model.func_177550_a();
        return generalQuads == generalQuadsCullAcacia ? modelLeavesDoubleAcacia : (generalQuads == generalQuadsCullBirch ? modelLeavesDoubleBirch : (generalQuads == generalQuadsCullDarkOak ? modelLeavesDoubleDarkOak : (generalQuads == generalQuadsCullJungle ? modelLeavesDoubleJungle : (generalQuads == generalQuadsCullOak ? modelLeavesDoubleOak : (generalQuads == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : model)))));
    }

    public static void updateLeavesModels() {
        ArrayList updatedTypes = new ArrayList();
        modelLeavesCullAcacia = SmartLeaves.getModelCull("acacia", updatedTypes);
        modelLeavesCullBirch = SmartLeaves.getModelCull("birch", updatedTypes);
        modelLeavesCullDarkOak = SmartLeaves.getModelCull("dark_oak", updatedTypes);
        modelLeavesCullJungle = SmartLeaves.getModelCull("jungle", updatedTypes);
        modelLeavesCullOak = SmartLeaves.getModelCull("oak", updatedTypes);
        modelLeavesCullSpruce = SmartLeaves.getModelCull("spruce", updatedTypes);
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
        if (updatedTypes.size() > 0) {
            Config.dbg("Enable face culling: " + Config.arrayToString(updatedTypes.toArray()));
        }
    }

    private static List getGeneralQuadsSafe(IBakedModel model) {
        return model == null ? null : model.func_177550_a();
    }

    static IBakedModel getModelCull(String type, List updatedTypes) {
        ModelManager modelManager = Config.getModelManager();
        if (modelManager == null) {
            return null;
        }
        ResourceLocation locState = new ResourceLocation("blockstates/" + type + "_leaves.json");
        if (Config.getDefiningResourcePack(locState) != Config.getDefaultResourcePack()) {
            return null;
        }
        ResourceLocation locModel = new ResourceLocation("models/block/" + type + "_leaves.json");
        if (Config.getDefiningResourcePack(locModel) != Config.getDefaultResourcePack()) {
            return null;
        }
        ModelResourceLocation mrl = new ModelResourceLocation(String.valueOf(type) + "_leaves", "normal");
        IBakedModel model = modelManager.getModel(mrl);
        if (model != null && model != modelManager.getMissingModel()) {
            List listGeneral = model.func_177550_a();
            if (listGeneral.size() == 0) {
                return model;
            }
            if (listGeneral.size() != 6) {
                return null;
            }
            for (BakedQuad quad : listGeneral) {
                List listFace = model.func_177551_a(quad.getFace());
                if (listFace.size() > 0) {
                    return null;
                }
                listFace.add(quad);
            }
            listGeneral.clear();
            updatedTypes.add(String.valueOf(type) + "_leaves");
            return model;
        }
        return null;
    }

    private static IBakedModel getModelDoubleFace(IBakedModel model) {
        if (model == null) {
            return null;
        }
        if (model.func_177550_a().size() > 0) {
            Config.warn("SmartLeaves: Model is not cube, general quads: " + model.func_177550_a().size() + ", model: " + model);
            return model;
        }
        EnumFacing[] faces = EnumFacing.VALUES;
        for (int model2 = 0; model2 < faces.length; ++model2) {
            EnumFacing faceQuads = faces[model2];
            List i2 = model.func_177551_a(faceQuads);
            if (i2.size() == 1) continue;
            Config.warn("SmartLeaves: Model is not cube, side: " + faceQuads + ", quads: " + i2.size() + ", model: " + model);
            return model;
        }
        IBakedModel var12 = ModelUtils.duplicateModel(model);
        List[] var13 = new List[faces.length];
        for (int var14 = 0; var14 < faces.length; ++var14) {
            EnumFacing face = faces[var14];
            List quads = var12.func_177551_a(face);
            BakedQuad quad = (BakedQuad)quads.get(0);
            BakedQuad quad2 = new BakedQuad((int[])quad.func_178209_a().clone(), quad.func_178211_c(), quad.getFace(), quad.getSprite());
            int[] vd2 = quad2.func_178209_a();
            int[] vd22 = (int[])vd2.clone();
            int step = vd2.length / 4;
            System.arraycopy(vd2, 0 * step, vd22, 3 * step, step);
            System.arraycopy(vd2, 1 * step, vd22, 2 * step, step);
            System.arraycopy(vd2, 2 * step, vd22, 1 * step, step);
            System.arraycopy(vd2, 3 * step, vd22, 0 * step, step);
            System.arraycopy(vd22, 0, vd2, 0, vd22.length);
            quads.add(quad2);
        }
        return var12;
    }
}

