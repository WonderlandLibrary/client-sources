/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.optifine.BetterGrass;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;

public class BlockModelCustomizer {
    private static final List<BakedQuad> NO_QUADS = ImmutableList.of();

    public static IBakedModel getRenderModel(IBakedModel iBakedModel, BlockState blockState, RenderEnv renderEnv) {
        if (renderEnv.isSmartLeaves()) {
            iBakedModel = SmartLeaves.getLeavesModel(iBakedModel, blockState);
        }
        return iBakedModel;
    }

    public static List<BakedQuad> getRenderQuads(List<BakedQuad> list, IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, Direction direction, RenderType renderType, long l, RenderEnv renderEnv) {
        if (direction != null) {
            if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(iBlockDisplayReader.getBlockState(blockPos.offset(direction)), blockState)) {
                return NO_QUADS;
            }
            if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                list = BetterGrass.getFaceQuads(iBlockDisplayReader, blockState, blockPos, direction, list);
            }
        }
        List<BakedQuad> list2 = renderEnv.getListQuadsCustomizer();
        list2.clear();
        for (int i = 0; i < list.size(); ++i) {
            BakedQuad bakedQuad = list.get(i);
            BakedQuad[] bakedQuadArray = BlockModelCustomizer.getRenderQuads(bakedQuad, iBlockDisplayReader, blockState, blockPos, direction, l, renderEnv);
            if (i == 0 && list.size() == 1 && bakedQuadArray.length == 1 && bakedQuadArray[0] == bakedQuad && bakedQuad.getQuadEmissive() == null) {
                return list;
            }
            for (int j = 0; j < bakedQuadArray.length; ++j) {
                BakedQuad bakedQuad2 = bakedQuadArray[j];
                list2.add(bakedQuad2);
                if (bakedQuad2.getQuadEmissive() == null) continue;
                renderEnv.getListQuadsOverlay(BlockModelCustomizer.getEmissiveLayer(renderType)).addQuad(bakedQuad2.getQuadEmissive(), blockState);
                renderEnv.setOverlaysRendered(false);
            }
        }
        return list2;
    }

    private static RenderType getEmissiveLayer(RenderType renderType) {
        return renderType != null && renderType != RenderTypes.SOLID ? renderType : RenderTypes.CUTOUT_MIPPED;
    }

    private static BakedQuad[] getRenderQuads(BakedQuad bakedQuad, IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, Direction direction, long l, RenderEnv renderEnv) {
        BakedQuad[] bakedQuadArray;
        if (renderEnv.isBreakingAnimation(bakedQuad)) {
            return renderEnv.getArrayQuadsCtm(bakedQuad);
        }
        BakedQuad bakedQuad2 = bakedQuad;
        if (Config.isConnectedTextures() && ((bakedQuadArray = ConnectedTextures.getConnectedTexture(iBlockDisplayReader, blockState, blockPos, bakedQuad, renderEnv)).length != 1 || bakedQuadArray[0] != bakedQuad)) {
            return bakedQuadArray;
        }
        if (Config.isNaturalTextures() && (bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad)) != bakedQuad2) {
            return renderEnv.getArrayQuadsCtm(bakedQuad);
        }
        return renderEnv.getArrayQuadsCtm(bakedQuad);
    }
}

