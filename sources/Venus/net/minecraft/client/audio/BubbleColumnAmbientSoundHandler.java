/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.audio.IAmbientSoundHandler;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class BubbleColumnAmbientSoundHandler
implements IAmbientSoundHandler {
    private final ClientPlayerEntity player;
    private boolean prevTickInColumn;
    private boolean firstTick = true;

    public BubbleColumnAmbientSoundHandler(ClientPlayerEntity clientPlayerEntity) {
        this.player = clientPlayerEntity;
    }

    @Override
    public void tick() {
        World world = this.player.world;
        BlockState blockState = world.getStatesInArea(this.player.getBoundingBox().grow(0.0, -0.4f, 0.0).shrink(0.001)).filter(BubbleColumnAmbientSoundHandler::lambda$tick$0).findFirst().orElse(null);
        if (blockState != null) {
            if (!this.prevTickInColumn && !this.firstTick && blockState.isIn(Blocks.BUBBLE_COLUMN) && !this.player.isSpectator()) {
                boolean bl = blockState.get(BubbleColumnBlock.DRAG);
                if (bl) {
                    this.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0f, 1.0f);
                } else {
                    this.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0f, 1.0f);
                }
            }
            this.prevTickInColumn = true;
        } else {
            this.prevTickInColumn = false;
        }
        this.firstTick = false;
    }

    private static boolean lambda$tick$0(BlockState blockState) {
        return blockState.isIn(Blocks.BUBBLE_COLUMN);
    }
}

