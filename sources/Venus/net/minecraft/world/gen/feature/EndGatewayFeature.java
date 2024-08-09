/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndGatewayFeature
extends Feature<EndGatewayConfig> {
    public EndGatewayFeature(Codec<EndGatewayConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, EndGatewayConfig endGatewayConfig) {
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-1, -2, -1), blockPos.add(1, 2, 1))) {
            boolean bl;
            boolean bl2 = blockPos2.getX() == blockPos.getX();
            boolean bl3 = blockPos2.getY() == blockPos.getY();
            boolean bl4 = blockPos2.getZ() == blockPos.getZ();
            boolean bl5 = bl = Math.abs(blockPos2.getY() - blockPos.getY()) == 2;
            if (bl2 && bl3 && bl4) {
                BlockPos blockPos3 = blockPos2.toImmutable();
                this.setBlockState(iSeedReader, blockPos3, Blocks.END_GATEWAY.getDefaultState());
                endGatewayConfig.func_214700_b().ifPresent(arg_0 -> EndGatewayFeature.lambda$func_241855_a$0(iSeedReader, blockPos3, endGatewayConfig, arg_0));
                continue;
            }
            if (bl3) {
                this.setBlockState(iSeedReader, blockPos2, Blocks.AIR.getDefaultState());
                continue;
            }
            if (bl && bl2 && bl4) {
                this.setBlockState(iSeedReader, blockPos2, Blocks.BEDROCK.getDefaultState());
                continue;
            }
            if ((bl2 || bl4) && !bl) {
                this.setBlockState(iSeedReader, blockPos2, Blocks.BEDROCK.getDefaultState());
                continue;
            }
            this.setBlockState(iSeedReader, blockPos2, Blocks.AIR.getDefaultState());
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (EndGatewayConfig)iFeatureConfig);
    }

    private static void lambda$func_241855_a$0(ISeedReader iSeedReader, BlockPos blockPos, EndGatewayConfig endGatewayConfig, BlockPos blockPos2) {
        TileEntity tileEntity = iSeedReader.getTileEntity(blockPos);
        if (tileEntity instanceof EndGatewayTileEntity) {
            EndGatewayTileEntity endGatewayTileEntity = (EndGatewayTileEntity)tileEntity;
            endGatewayTileEntity.setExitPortal(blockPos2, endGatewayConfig.func_214701_c());
            tileEntity.markDirty();
        }
    }
}

