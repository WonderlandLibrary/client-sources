/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;

public class FluidBlockRenderer {
    private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
    private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
    private TextureAtlasSprite atlasSpriteWaterOverlay;

    protected void initAtlasSprites() {
        this.atlasSpritesLava[0] = Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(Blocks.LAVA.getDefaultState()).getParticleTexture();
        this.atlasSpritesLava[1] = ModelBakery.LOCATION_LAVA_FLOW.getSprite();
        this.atlasSpritesWater[0] = Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(Blocks.WATER.getDefaultState()).getParticleTexture();
        this.atlasSpritesWater[1] = ModelBakery.LOCATION_WATER_FLOW.getSprite();
        this.atlasSpriteWaterOverlay = ModelBakery.LOCATION_WATER_OVERLAY.getSprite();
    }

    private static boolean isAdjacentFluidSameAs(IBlockReader iBlockReader, BlockPos blockPos, Direction direction, FluidState fluidState) {
        BlockPos blockPos2 = blockPos.offset(direction);
        FluidState fluidState2 = iBlockReader.getFluidState(blockPos2);
        return fluidState2.getFluid().isEquivalentTo(fluidState.getFluid());
    }

    private static boolean func_239284_a_(IBlockReader iBlockReader, Direction direction, float f, BlockPos blockPos, BlockState blockState) {
        if (blockState.isSolid()) {
            VoxelShape voxelShape = VoxelShapes.create(0.0, 0.0, 0.0, 1.0, f, 1.0);
            VoxelShape voxelShape2 = blockState.getRenderShapeTrue(iBlockReader, blockPos);
            return VoxelShapes.isCubeSideCovered(voxelShape, voxelShape2, direction);
        }
        return true;
    }

    private static boolean func_239283_a_(IBlockReader iBlockReader, BlockPos blockPos, Direction direction, float f) {
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState = iBlockReader.getBlockState(blockPos2);
        return FluidBlockRenderer.func_239284_a_(iBlockReader, direction, f, blockPos2, blockState);
    }

    private static boolean func_239282_a_(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Direction direction) {
        return FluidBlockRenderer.func_239284_a_(iBlockReader, direction.getOpposite(), 1.0f, blockPos, blockState);
    }

    public static boolean func_239281_a_(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction direction) {
        return !FluidBlockRenderer.func_239282_a_(iBlockDisplayReader, blockPos, blockState, direction) && !FluidBlockRenderer.isAdjacentFluidSameAs(iBlockDisplayReader, blockPos, direction, fluidState);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean render(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, IVertexBuilder iVertexBuilder, FluidState fluidState) {
        boolean bl;
        BlockState blockState = fluidState.getBlockState();
        try {
            Object object;
            TextureAtlasSprite[] textureAtlasSpriteArray;
            boolean bl2;
            if (Config.isShaders()) {
                SVertexBuilder.pushEntity(blockState, iVertexBuilder);
            }
            TextureAtlasSprite[] textureAtlasSpriteArray2 = (bl2 = fluidState.isTagged(FluidTags.LAVA)) ? this.atlasSpritesLava : this.atlasSpritesWater;
            BlockState blockState2 = iBlockDisplayReader.getBlockState(blockPos);
            if (Reflector.ForgeHooksClient_getFluidSprites.exists() && (textureAtlasSpriteArray = (TextureAtlasSprite[])Reflector.call(Reflector.ForgeHooksClient_getFluidSprites, iBlockDisplayReader, blockPos, fluidState)) != null) {
                textureAtlasSpriteArray2 = textureAtlasSpriteArray;
            }
            textureAtlasSpriteArray = iVertexBuilder.getRenderEnv(blockState, blockPos);
            int n = -1;
            float f = 1.0f;
            if (Reflector.IForgeFluid_getAttributes.exists() && (object = Reflector.call(fluidState.getFluid(), Reflector.IForgeFluid_getAttributes, new Object[0])) != null && Reflector.FluidAttributes_getColor.exists()) {
                n = Reflector.callInt(object, Reflector.FluidAttributes_getColor, iBlockDisplayReader, blockPos);
                f = (float)(n >> 24 & 0xFF) / 255.0f;
            }
            boolean bl3 = !FluidBlockRenderer.isAdjacentFluidSameAs(iBlockDisplayReader, blockPos, Direction.UP, fluidState);
            boolean bl4 = FluidBlockRenderer.func_239281_a_(iBlockDisplayReader, blockPos, fluidState, blockState2, Direction.DOWN) && !FluidBlockRenderer.func_239283_a_(iBlockDisplayReader, blockPos, Direction.DOWN, 0.8888889f);
            boolean bl5 = FluidBlockRenderer.func_239281_a_(iBlockDisplayReader, blockPos, fluidState, blockState2, Direction.NORTH);
            boolean bl6 = FluidBlockRenderer.func_239281_a_(iBlockDisplayReader, blockPos, fluidState, blockState2, Direction.SOUTH);
            boolean bl7 = FluidBlockRenderer.func_239281_a_(iBlockDisplayReader, blockPos, fluidState, blockState2, Direction.WEST);
            boolean bl8 = FluidBlockRenderer.func_239281_a_(iBlockDisplayReader, blockPos, fluidState, blockState2, Direction.EAST);
            if (bl3 || bl4 || bl8 || bl7 || bl5 || bl6) {
                float f2;
                float f3;
                float f4;
                float f5;
                float f6;
                float f7;
                int n2;
                int n3;
                if (n < 0) {
                    n = CustomColors.getFluidColor(iBlockDisplayReader, blockState, blockPos, (RenderEnv)textureAtlasSpriteArray);
                }
                float f8 = (float)(n >> 16 & 0xFF) / 255.0f;
                float f9 = (float)(n >> 8 & 0xFF) / 255.0f;
                float f10 = (float)(n & 0xFF) / 255.0f;
                int n4 = 0;
                float f11 = iBlockDisplayReader.func_230487_a_(Direction.DOWN, true);
                float f12 = iBlockDisplayReader.func_230487_a_(Direction.UP, true);
                float f13 = iBlockDisplayReader.func_230487_a_(Direction.NORTH, true);
                float f14 = iBlockDisplayReader.func_230487_a_(Direction.WEST, true);
                float f15 = this.getFluidHeight(iBlockDisplayReader, blockPos, fluidState.getFluid());
                float f16 = this.getFluidHeight(iBlockDisplayReader, blockPos.south(), fluidState.getFluid());
                float f17 = this.getFluidHeight(iBlockDisplayReader, blockPos.east().south(), fluidState.getFluid());
                float f18 = this.getFluidHeight(iBlockDisplayReader, blockPos.east(), fluidState.getFluid());
                double d = blockPos.getX() & 0xF;
                double d2 = blockPos.getY() & 0xF;
                double d3 = blockPos.getZ() & 0xF;
                if (Config.isRenderRegions()) {
                    int n5 = blockPos.getX() >> 4 << 4;
                    int n6 = blockPos.getY() >> 4 << 4;
                    n3 = blockPos.getZ() >> 4 << 4;
                    int n7 = 8;
                    int n8 = n5 >> n7 << n7;
                    int n9 = n3 >> n7 << n7;
                    n2 = n5 - n8;
                    int n10 = n3 - n9;
                    d += (double)n2;
                    d2 += (double)n6;
                    d3 += (double)n10;
                }
                if (Config.isShaders() && Shaders.useMidBlockAttrib) {
                    iVertexBuilder.setMidBlock((float)(d + 0.5), (float)(d2 + 0.5), (float)(d3 + 0.5));
                }
                float f19 = 0.001f;
                float f20 = f7 = bl4 ? 0.001f : 0.0f;
                if (bl3 && !FluidBlockRenderer.func_239283_a_(iBlockDisplayReader, blockPos, Direction.UP, Math.min(Math.min(f15, f16), Math.min(f17, f18)))) {
                    float f21;
                    float f22;
                    float f23;
                    float f24;
                    float f25;
                    float f26;
                    float f27;
                    float f28;
                    float f29;
                    n4 = 1;
                    f15 -= 0.001f;
                    f16 -= 0.001f;
                    f17 -= 0.001f;
                    f18 -= 0.001f;
                    Vector3d vector3d = fluidState.getFlow(iBlockDisplayReader, blockPos);
                    if (vector3d.x == 0.0 && vector3d.z == 0.0) {
                        var47_60 = textureAtlasSpriteArray2[0];
                        iVertexBuilder.setSprite(var47_60);
                        f29 = var47_60.getInterpolatedU(0.0);
                        f28 = var47_60.getInterpolatedV(0.0);
                        f27 = f29;
                        f6 = var47_60.getInterpolatedV(16.0);
                        f5 = var47_60.getInterpolatedU(16.0);
                        f26 = f6;
                        f4 = f5;
                        f25 = f28;
                    } else {
                        var47_60 = textureAtlasSpriteArray2[5];
                        iVertexBuilder.setSprite(var47_60);
                        f24 = (float)MathHelper.atan2(vector3d.z, vector3d.x) - 1.5707964f;
                        f23 = MathHelper.sin(f24) * 0.25f;
                        f22 = MathHelper.cos(f24) * 0.25f;
                        f21 = 8.0f;
                        f29 = var47_60.getInterpolatedU(8.0f + (-f22 - f23) * 16.0f);
                        f28 = var47_60.getInterpolatedV(8.0f + (-f22 + f23) * 16.0f);
                        f27 = var47_60.getInterpolatedU(8.0f + (-f22 + f23) * 16.0f);
                        f6 = var47_60.getInterpolatedV(8.0f + (f22 + f23) * 16.0f);
                        f5 = var47_60.getInterpolatedU(8.0f + (f22 + f23) * 16.0f);
                        f26 = var47_60.getInterpolatedV(8.0f + (f22 - f23) * 16.0f);
                        f4 = var47_60.getInterpolatedU(8.0f + (f22 - f23) * 16.0f);
                        f25 = var47_60.getInterpolatedV(8.0f + (-f22 - f23) * 16.0f);
                    }
                    float f30 = (f29 + f27 + f5 + f4) / 4.0f;
                    f24 = (f28 + f6 + f26 + f25) / 4.0f;
                    f23 = (float)textureAtlasSpriteArray2[0].getWidth() / (textureAtlasSpriteArray2[0].getMaxU() - textureAtlasSpriteArray2[0].getMinU());
                    f22 = (float)textureAtlasSpriteArray2[0].getHeight() / (textureAtlasSpriteArray2[0].getMaxV() - textureAtlasSpriteArray2[0].getMinV());
                    f21 = 4.0f / Math.max(f22, f23);
                    f29 = MathHelper.lerp(f21, f29, f30);
                    f27 = MathHelper.lerp(f21, f27, f30);
                    f5 = MathHelper.lerp(f21, f5, f30);
                    f4 = MathHelper.lerp(f21, f4, f30);
                    f28 = MathHelper.lerp(f21, f28, f24);
                    f6 = MathHelper.lerp(f21, f6, f24);
                    f26 = MathHelper.lerp(f21, f26, f24);
                    f25 = MathHelper.lerp(f21, f25, f24);
                    int n11 = this.getCombinedAverageLight(iBlockDisplayReader, blockPos);
                    f3 = f12 * f8;
                    f2 = f12 * f9;
                    float f31 = f12 * f10;
                    this.vertexVanilla(iVertexBuilder, d + 0.0, d2 + (double)f15, d3 + 0.0, f3, f2, f31, f, f29, f28, n11);
                    this.vertexVanilla(iVertexBuilder, d + 0.0, d2 + (double)f16, d3 + 1.0, f3, f2, f31, f, f27, f6, n11);
                    this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f17, d3 + 1.0, f3, f2, f31, f, f5, f26, n11);
                    this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f18, d3 + 0.0, f3, f2, f31, f, f4, f25, n11);
                    if (fluidState.shouldRenderSides(iBlockDisplayReader, blockPos.up())) {
                        this.vertexVanilla(iVertexBuilder, d + 0.0, d2 + (double)f15, d3 + 0.0, f3, f2, f31, f, f29, f28, n11);
                        this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f18, d3 + 0.0, f3, f2, f31, f, f4, f25, n11);
                        this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f17, d3 + 1.0, f3, f2, f31, f, f5, f26, n11);
                        this.vertexVanilla(iVertexBuilder, d + 0.0, d2 + (double)f16, d3 + 1.0, f3, f2, f31, f, f27, f6, n11);
                    }
                }
                if (bl4) {
                    iVertexBuilder.setSprite(textureAtlasSpriteArray2[0]);
                    float f32 = textureAtlasSpriteArray2[0].getMinU();
                    float f33 = textureAtlasSpriteArray2[0].getMaxU();
                    float f34 = textureAtlasSpriteArray2[0].getMinV();
                    float f35 = textureAtlasSpriteArray2[0].getMaxV();
                    n2 = this.getCombinedAverageLight(iBlockDisplayReader, blockPos.down());
                    float f36 = iBlockDisplayReader.func_230487_a_(Direction.DOWN, true);
                    f5 = f36 * f8;
                    f4 = f36 * f9;
                    f6 = f36 * f10;
                    this.vertexVanilla(iVertexBuilder, d, d2 + (double)f7, d3 + 1.0, f5, f4, f6, f, f32, f35, n2);
                    this.vertexVanilla(iVertexBuilder, d, d2 + (double)f7, d3, f5, f4, f6, f, f32, f34, n2);
                    this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f7, d3, f5, f4, f6, f, f33, f34, n2);
                    this.vertexVanilla(iVertexBuilder, d + 1.0, d2 + (double)f7, d3 + 1.0, f5, f4, f6, f, f33, f35, n2);
                    n4 = 1;
                }
                for (n3 = 0; n3 < 4; ++n3) {
                    boolean bl9;
                    boolean bl10;
                    Direction direction;
                    double d4;
                    double d5;
                    double d6;
                    double d7;
                    float f37;
                    float f38;
                    if (n3 == 0) {
                        f38 = f15;
                        f37 = f18;
                        d7 = d;
                        d6 = d + 1.0;
                        d5 = d3 + (double)0.001f;
                        d4 = d3 + (double)0.001f;
                        direction = Direction.NORTH;
                        bl10 = bl5;
                    } else if (n3 == 1) {
                        f38 = f17;
                        f37 = f16;
                        d7 = d + 1.0;
                        d6 = d;
                        d5 = d3 + 1.0 - (double)0.001f;
                        d4 = d3 + 1.0 - (double)0.001f;
                        direction = Direction.SOUTH;
                        bl10 = bl6;
                    } else if (n3 == 2) {
                        f38 = f16;
                        f37 = f15;
                        d7 = d + (double)0.001f;
                        d6 = d + (double)0.001f;
                        d5 = d3 + 1.0;
                        d4 = d3;
                        direction = Direction.WEST;
                        bl10 = bl7;
                    } else {
                        f38 = f18;
                        f37 = f17;
                        d7 = d + 1.0 - (double)0.001f;
                        d6 = d + 1.0 - (double)0.001f;
                        d5 = d3;
                        d4 = d3 + 1.0;
                        direction = Direction.EAST;
                        bl10 = bl8;
                    }
                    if (!bl10 || FluidBlockRenderer.func_239283_a_(iBlockDisplayReader, blockPos, direction, Math.max(f38, f37))) continue;
                    n4 = 1;
                    BlockPos blockPos2 = blockPos.offset(direction);
                    TextureAtlasSprite textureAtlasSprite = textureAtlasSpriteArray2[5];
                    f3 = 0.0f;
                    f2 = 0.0f;
                    boolean bl11 = bl9 = !bl2;
                    if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                        boolean bl12 = bl9 = textureAtlasSpriteArray2[5] != null;
                    }
                    if (bl9) {
                        BlockState blockState3 = iBlockDisplayReader.getBlockState(blockPos2);
                        Block block = blockState3.getBlock();
                        boolean bl13 = false;
                        if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                            bl13 = Reflector.callBoolean(blockState3, Reflector.IForgeBlockState_shouldDisplayFluidOverlay, iBlockDisplayReader, blockPos2, fluidState);
                        }
                        if (bl13 || block instanceof BreakableBlock || block instanceof LeavesBlock || block == Blocks.BEACON) {
                            textureAtlasSprite = this.atlasSpriteWaterOverlay;
                        }
                        if (block == Blocks.FARMLAND || block == Blocks.GRASS_PATH) {
                            f3 = 0.9375f;
                            f2 = 0.9375f;
                        }
                        if (block instanceof SlabBlock) {
                            SlabBlock slabBlock = (SlabBlock)block;
                            if (blockState3.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                                f3 = 0.5f;
                                f2 = 0.5f;
                            }
                        }
                    }
                    iVertexBuilder.setSprite(textureAtlasSprite);
                    if (f38 <= f3 && f37 <= f2) continue;
                    f3 = Math.min(f3, f38);
                    f2 = Math.min(f2, f37);
                    if (f3 > f19) {
                        f3 -= f19;
                    }
                    if (f2 > f19) {
                        f2 -= f19;
                    }
                    float f39 = textureAtlasSprite.getInterpolatedV((1.0f - f3) * 16.0f * 0.5f);
                    float f40 = textureAtlasSprite.getInterpolatedV((1.0f - f2) * 16.0f * 0.5f);
                    float f41 = textureAtlasSprite.getInterpolatedU(0.0);
                    float f42 = textureAtlasSprite.getInterpolatedU(8.0);
                    float f43 = textureAtlasSprite.getInterpolatedV((1.0f - f38) * 16.0f * 0.5f);
                    float f44 = textureAtlasSprite.getInterpolatedV((1.0f - f37) * 16.0f * 0.5f);
                    float f45 = textureAtlasSprite.getInterpolatedV(8.0);
                    int n12 = this.getCombinedAverageLight(iBlockDisplayReader, blockPos2);
                    float f46 = n3 < 2 ? iBlockDisplayReader.func_230487_a_(Direction.NORTH, true) : iBlockDisplayReader.func_230487_a_(Direction.WEST, true);
                    float f47 = 1.0f * f46 * f8;
                    float f48 = 1.0f * f46 * f9;
                    float f49 = 1.0f * f46 * f10;
                    this.vertexVanilla(iVertexBuilder, d7, d2 + (double)f38, d5, f47, f48, f49, f, f41, f43, n12);
                    this.vertexVanilla(iVertexBuilder, d6, d2 + (double)f37, d4, f47, f48, f49, f, f42, f44, n12);
                    this.vertexVanilla(iVertexBuilder, d6, d2 + (double)f7, d4, f47, f48, f49, f, f42, f40, n12);
                    this.vertexVanilla(iVertexBuilder, d7, d2 + (double)f7, d5, f47, f48, f49, f, f41, f39, n12);
                    if (textureAtlasSprite == this.atlasSpriteWaterOverlay) continue;
                    this.vertexVanilla(iVertexBuilder, d7, d2 + (double)f7, d5, f47, f48, f49, f, f41, f39, n12);
                    this.vertexVanilla(iVertexBuilder, d6, d2 + (double)f7, d4, f47, f48, f49, f, f42, f40, n12);
                    this.vertexVanilla(iVertexBuilder, d6, d2 + (double)f37, d4, f47, f48, f49, f, f42, f44, n12);
                    this.vertexVanilla(iVertexBuilder, d7, d2 + (double)f38, d5, f47, f48, f49, f, f41, f43, n12);
                }
                iVertexBuilder.setSprite(null);
                n3 = n4;
                return n3 != 0;
            }
            bl = false;
        } finally {
            if (Config.isShaders()) {
                SVertexBuilder.popEntity(iVertexBuilder);
            }
        }
        return bl;
    }

    private void vertexVanilla(IVertexBuilder iVertexBuilder, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, int n) {
        iVertexBuilder.pos(d, d2, d3).color(f, f2, f3, 1.0f).tex(f4, f5).lightmap(n).normal(0.0f, 1.0f, 0.0f).endVertex();
    }

    private void vertexVanilla(IVertexBuilder iVertexBuilder, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, int n) {
        iVertexBuilder.pos(d, d2, d3).color(f, f2, f3, f4).tex(f5, f6).lightmap(n).normal(0.0f, 1.0f, 0.0f).endVertex();
    }

    private int getCombinedAverageLight(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        int n = WorldRenderer.getCombinedLight(iBlockDisplayReader, blockPos);
        int n2 = WorldRenderer.getCombinedLight(iBlockDisplayReader, blockPos.up());
        int n3 = n & 0xFF;
        int n4 = n2 & 0xFF;
        int n5 = n >> 16 & 0xFF;
        int n6 = n2 >> 16 & 0xFF;
        return (n3 > n4 ? n3 : n4) | (n5 > n6 ? n5 : n6) << 16;
    }

    private float getFluidHeight(IBlockReader iBlockReader, BlockPos blockPos, Fluid fluid) {
        int n = 0;
        float f = 0.0f;
        for (int i = 0; i < 4; ++i) {
            BlockPos blockPos2 = blockPos.add(-(i & 1), 0, -(i >> 1 & 1));
            if (iBlockReader.getFluidState(blockPos2.up()).getFluid().isEquivalentTo(fluid)) {
                return 1.0f;
            }
            FluidState fluidState = iBlockReader.getFluidState(blockPos2);
            if (fluidState.getFluid().isEquivalentTo(fluid)) {
                float f2 = fluidState.getActualHeight(iBlockReader, blockPos2);
                if (f2 >= 0.8f) {
                    f += f2 * 10.0f;
                    n += 10;
                    continue;
                }
                f += f2;
                ++n;
                continue;
            }
            if (iBlockReader.getBlockState(blockPos2).getMaterial().isSolid()) continue;
            ++n;
        }
        return f / (float)n;
    }
}

