/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class CommandBlockTileEntity
extends TileEntity {
    private boolean powered;
    private boolean auto;
    private boolean conditionMet;
    private boolean sendToClient;
    private final CommandBlockLogic commandBlockLogic = new CommandBlockLogic(this){
        final CommandBlockTileEntity this$0;
        {
            this.this$0 = commandBlockTileEntity;
        }

        @Override
        public void setCommand(String string) {
            super.setCommand(string);
            this.this$0.markDirty();
        }

        @Override
        public ServerWorld getWorld() {
            return (ServerWorld)this.this$0.world;
        }

        @Override
        public void updateCommand() {
            BlockState blockState = this.this$0.world.getBlockState(this.this$0.pos);
            this.getWorld().notifyBlockUpdate(this.this$0.pos, blockState, blockState, 3);
        }

        @Override
        public Vector3d getPositionVector() {
            return Vector3d.copyCentered(this.this$0.pos);
        }

        @Override
        public CommandSource getCommandSource() {
            return new CommandSource(this, Vector3d.copyCentered(this.this$0.pos), Vector2f.ZERO, this.getWorld(), 2, this.getName().getString(), this.getName(), this.getWorld().getServer(), null);
        }
    };

    public CommandBlockTileEntity() {
        super(TileEntityType.COMMAND_BLOCK);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        this.commandBlockLogic.write(compoundNBT);
        compoundNBT.putBoolean("powered", this.isPowered());
        compoundNBT.putBoolean("conditionMet", this.isConditionMet());
        compoundNBT.putBoolean("auto", this.isAuto());
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.commandBlockLogic.read(compoundNBT);
        this.powered = compoundNBT.getBoolean("powered");
        this.conditionMet = compoundNBT.getBoolean("conditionMet");
        this.setAuto(compoundNBT.getBoolean("auto"));
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        if (this.isSendToClient()) {
            this.setSendToClient(true);
            CompoundNBT compoundNBT = this.write(new CompoundNBT());
            return new SUpdateTileEntityPacket(this.pos, 2, compoundNBT);
        }
        return null;
    }

    @Override
    public boolean onlyOpsCanSetNbt() {
        return false;
    }

    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }

    public void setPowered(boolean bl) {
        this.powered = bl;
    }

    public boolean isPowered() {
        return this.powered;
    }

    public boolean isAuto() {
        return this.auto;
    }

    public void setAuto(boolean bl) {
        boolean bl2 = this.auto;
        this.auto = bl;
        if (!bl2 && bl && !this.powered && this.world != null && this.getMode() != Mode.SEQUENCE) {
            this.func_226988_y_();
        }
    }

    public void func_226987_h_() {
        Mode mode = this.getMode();
        if (mode == Mode.AUTO && (this.powered || this.auto) && this.world != null) {
            this.func_226988_y_();
        }
    }

    private void func_226988_y_() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof CommandBlockBlock) {
            this.setConditionMet();
            this.world.getPendingBlockTicks().scheduleTick(this.pos, block, 1);
        }
    }

    public boolean isConditionMet() {
        return this.conditionMet;
    }

    public boolean setConditionMet() {
        this.conditionMet = true;
        if (this.isConditional()) {
            TileEntity tileEntity;
            BlockPos blockPos = this.pos.offset(this.world.getBlockState(this.pos).get(CommandBlockBlock.FACING).getOpposite());
            this.conditionMet = this.world.getBlockState(blockPos).getBlock() instanceof CommandBlockBlock ? (tileEntity = this.world.getTileEntity(blockPos)) instanceof CommandBlockTileEntity && ((CommandBlockTileEntity)tileEntity).getCommandBlockLogic().getSuccessCount() > 0 : false;
        }
        return this.conditionMet;
    }

    public boolean isSendToClient() {
        return this.sendToClient;
    }

    public void setSendToClient(boolean bl) {
        this.sendToClient = bl;
    }

    public Mode getMode() {
        BlockState blockState = this.getBlockState();
        if (blockState.isIn(Blocks.COMMAND_BLOCK)) {
            return Mode.REDSTONE;
        }
        if (blockState.isIn(Blocks.REPEATING_COMMAND_BLOCK)) {
            return Mode.AUTO;
        }
        return blockState.isIn(Blocks.CHAIN_COMMAND_BLOCK) ? Mode.SEQUENCE : Mode.REDSTONE;
    }

    public boolean isConditional() {
        BlockState blockState = this.world.getBlockState(this.getPos());
        return blockState.getBlock() instanceof CommandBlockBlock ? blockState.get(CommandBlockBlock.CONDITIONAL) : false;
    }

    @Override
    public void validate() {
        this.updateContainingBlockInfo();
        super.validate();
    }

    public static enum Mode {
        SEQUENCE,
        AUTO,
        REDSTONE;

    }
}

