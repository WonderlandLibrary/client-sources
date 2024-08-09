/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatSitOnBlockGoal
extends MoveToBlockGoal {
    private final CatEntity cat;

    public CatSitOnBlockGoal(CatEntity catEntity, double d) {
        super(catEntity, d, 8);
        this.cat = catEntity;
    }

    @Override
    public boolean shouldExecute() {
        return this.cat.isTamed() && !this.cat.isSitting() && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.cat.setSleeping(true);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.cat.setSleeping(true);
    }

    @Override
    public void tick() {
        super.tick();
        this.cat.setSleeping(this.getIsAboveDestination());
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
        if (!iWorldReader.isAirBlock(blockPos.up())) {
            return true;
        }
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        if (blockState.isIn(Blocks.CHEST)) {
            return ChestTileEntity.getPlayersUsing(iWorldReader, blockPos) < 1;
        }
        return blockState.isIn(Blocks.FURNACE) && blockState.get(FurnaceBlock.LIT) != false ? true : blockState.isInAndMatches(BlockTags.BEDS, CatSitOnBlockGoal::lambda$shouldMoveTo$1);
    }

    private static boolean lambda$shouldMoveTo$1(AbstractBlock.AbstractBlockState abstractBlockState) {
        return abstractBlockState.func_235903_d_(BedBlock.PART).map(CatSitOnBlockGoal::lambda$shouldMoveTo$0).orElse(true);
    }

    private static Boolean lambda$shouldMoveTo$0(BedPart bedPart) {
        return bedPart != BedPart.HEAD;
    }
}

