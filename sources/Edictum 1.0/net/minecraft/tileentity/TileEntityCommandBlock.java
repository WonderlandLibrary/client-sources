package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity
{
    private boolean powered;
    private boolean auto;
    private boolean conditionMet;
    private boolean sendToClient;
    private final CommandBlockBaseLogic commandBlockLogic = new CommandBlockBaseLogic()
    {
        public BlockPos getPosition()
        {
            return TileEntityCommandBlock.this.pos;
        }
        public Vec3d getPositionVector()
        {
            return new Vec3d((double)TileEntityCommandBlock.this.pos.getX() + 0.5D, (double)TileEntityCommandBlock.this.pos.getY() + 0.5D, (double)TileEntityCommandBlock.this.pos.getZ() + 0.5D);
        }
        public World getEntityWorld()
        {
            return TileEntityCommandBlock.this.getWorld();
        }
        public void setCommand(String command)
        {
            super.setCommand(command);
            TileEntityCommandBlock.this.markDirty();
        }
        public void updateCommand()
        {
            IBlockState iblockstate = TileEntityCommandBlock.this.worldObj.getBlockState(TileEntityCommandBlock.this.pos);
            TileEntityCommandBlock.this.getWorld().notifyBlockUpdate(TileEntityCommandBlock.this.pos, iblockstate, iblockstate, 3);
        }
        public int getCommandBlockType()
        {
            return 0;
        }
        public void fillInInfo(ByteBuf buf)
        {
            buf.writeInt(TileEntityCommandBlock.this.pos.getX());
            buf.writeInt(TileEntityCommandBlock.this.pos.getY());
            buf.writeInt(TileEntityCommandBlock.this.pos.getZ());
        }
        public Entity getCommandSenderEntity()
        {
            return null;
        }
        public MinecraftServer getServer()
        {
            return TileEntityCommandBlock.this.worldObj.getMinecraftServer();
        }
    };

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        this.commandBlockLogic.func_189510_a(p_189515_1_);
        p_189515_1_.setBoolean("powered", this.isPowered());
        p_189515_1_.setBoolean("conditionMet", this.isConditionMet());
        p_189515_1_.setBoolean("auto", this.isAuto());
        return p_189515_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.commandBlockLogic.readDataFromNBT(compound);
        this.setPowered(compound.getBoolean("powered"));
        this.setConditionMet(compound.getBoolean("conditionMet"));
        this.setAuto(compound.getBoolean("auto"));
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        if (this.isSendToClient())
        {
            this.setSendToClient(false);
            NBTTagCompound nbttagcompound = this.func_189515_b(new NBTTagCompound());
            return new SPacketUpdateTileEntity(this.pos, 2, nbttagcompound);
        }
        else
        {
            return null;
        }
    }

    public boolean onlyOpsCanSetNbt()
    {
        return true;
    }

    public CommandBlockBaseLogic getCommandBlockLogic()
    {
        return this.commandBlockLogic;
    }

    public CommandResultStats getCommandResultStats()
    {
        return this.commandBlockLogic.getCommandResultStats();
    }

    public void setPowered(boolean poweredIn)
    {
        this.powered = poweredIn;
    }

    public boolean isPowered()
    {
        return this.powered;
    }

    public boolean isAuto()
    {
        return this.auto;
    }

    public void setAuto(boolean autoIn)
    {
        boolean flag = this.auto;
        this.auto = autoIn;

        if (!flag && autoIn && !this.powered && this.worldObj != null && this.getMode() != TileEntityCommandBlock.Mode.SEQUENCE)
        {
            Block block = this.getBlockType();

            if (block instanceof BlockCommandBlock)
            {
                BlockPos blockpos = this.getPos();
                BlockCommandBlock blockcommandblock = (BlockCommandBlock)block;
                this.conditionMet = !this.isConditional() || blockcommandblock.isNextToSuccessfulCommandBlock(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos));
                this.worldObj.scheduleUpdate(blockpos, block, block.tickRate(this.worldObj));

                if (this.conditionMet)
                {
                    blockcommandblock.propagateUpdate(this.worldObj, blockpos);
                }
            }
        }
    }

    public boolean isConditionMet()
    {
        return this.conditionMet;
    }

    public void setConditionMet(boolean conditionMetIn)
    {
        this.conditionMet = conditionMetIn;
    }

    public boolean isSendToClient()
    {
        return this.sendToClient;
    }

    public void setSendToClient(boolean p_184252_1_)
    {
        this.sendToClient = p_184252_1_;
    }

    public TileEntityCommandBlock.Mode getMode()
    {
        Block block = this.getBlockType();
        return block == Blocks.COMMAND_BLOCK ? TileEntityCommandBlock.Mode.REDSTONE : (block == Blocks.REPEATING_COMMAND_BLOCK ? TileEntityCommandBlock.Mode.AUTO : (block == Blocks.CHAIN_COMMAND_BLOCK ? TileEntityCommandBlock.Mode.SEQUENCE : TileEntityCommandBlock.Mode.REDSTONE));
    }

    public boolean isConditional()
    {
        IBlockState iblockstate = this.worldObj.getBlockState(this.getPos());
        return iblockstate.getBlock() instanceof BlockCommandBlock ? ((Boolean)iblockstate.getValue(BlockCommandBlock.CONDITIONAL)).booleanValue() : false;
    }

    /**
     * validates a tile entity
     */
    public void validate()
    {
        this.blockType = null;
        super.validate();
    }

    public static enum Mode
    {
        SEQUENCE,
        AUTO,
        REDSTONE;
    }
}
