/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;
import optifine.Config;

public class ModelUtils {
    public static void dbgModel(IBakedModel model) {
        if (model != null) {
            Config.dbg("Model: " + model + ", ao: " + model.isGui3d() + ", gui3d: " + model.isAmbientOcclusionEnabled() + ", builtIn: " + model.isBuiltInRenderer() + ", particle: " + model.getTexture());
            EnumFacing[] faces = EnumFacing.VALUES;
            for (int generalQuads = 0; generalQuads < faces.length; ++generalQuads) {
                EnumFacing face = faces[generalQuads];
                List faceQuads = model.func_177551_a(face);
                ModelUtils.dbgQuads(face.getName(), faceQuads, "  ");
            }
            List var5 = model.func_177550_a();
            ModelUtils.dbgQuads("General", var5, "  ");
        }
    }

    private static void dbgQuads(String name, List quads, String prefix) {
        for (BakedQuad quad : quads) {
            ModelUtils.dbgQuad(name, quad, prefix);
        }
    }

    public static void dbgQuad(String name, BakedQuad quad, String prefix) {
        Config.dbg(String.valueOf(prefix) + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.getFace() + ", tint: " + quad.func_178211_c() + ", sprite: " + quad.getSprite());
        ModelUtils.dbgVertexData(quad.func_178209_a(), "  " + prefix);
    }

    public static void dbgVertexData(int[] vd2, String prefix) {
        int step = vd2.length / 4;
        Config.dbg(String.valueOf(prefix) + "Length: " + vd2.length + ", step: " + step);
        for (int i2 = 0; i2 < 4; ++i2) {
            int pos = i2 * step;
            float x2 = Float.intBitsToFloat(vd2[pos + 0]);
            float y2 = Float.intBitsToFloat(vd2[pos + 1]);
            float z2 = Float.intBitsToFloat(vd2[pos + 2]);
            int col = vd2[pos + 3];
            float u2 = Float.intBitsToFloat(vd2[pos + 4]);
            float v2 = Float.intBitsToFloat(vd2[pos + 5]);
            Config.dbg(String.valueOf(prefix) + i2 + " xyz: " + x2 + "," + y2 + "," + z2 + " col: " + col + " u,v: " + u2 + "," + v2);
        }
    }

    public static IBakedModel duplicateModel(IBakedModel model) {
        List generalQuads2 = ModelUtils.duplicateQuadList(model.func_177550_a());
        EnumFacing[] faces = EnumFacing.VALUES;
        ArrayList<List> faceQuads2 = new ArrayList<List>();
        for (int model2 = 0; model2 < faces.length; ++model2) {
            EnumFacing face = faces[model2];
            List quads = model.func_177551_a(face);
            List quads2 = ModelUtils.duplicateQuadList(quads);
            faceQuads2.add(quads2);
        }
        SimpleBakedModel var8 = new SimpleBakedModel(generalQuads2, faceQuads2, model.isGui3d(), model.isAmbientOcclusionEnabled(), model.getTexture(), model.getItemCameraTransforms());
        return var8;
    }

    public static List duplicateQuadList(List list) {
        ArrayList<BakedQuad> list2 = new ArrayList<BakedQuad>();
        for (BakedQuad quad : list) {
            BakedQuad quad2 = ModelUtils.duplicateQuad(quad);
            list2.add(quad2);
        }
        return list2;
    }

    public static BakedQuad duplicateQuad(BakedQuad quad) {
        BakedQuad quad2 = new BakedQuad((int[])quad.func_178209_a().clone(), quad.func_178211_c(), quad.getFace(), quad.getSprite());
        return quad2;
    }
}

