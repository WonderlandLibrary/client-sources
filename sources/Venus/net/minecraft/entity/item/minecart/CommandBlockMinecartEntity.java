/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CommandBlockMinecartEntity
extends AbstractMinecartEntity {
    private static final DataParameter<String> COMMAND = EntityDataManager.createKey(CommandBlockMinecartEntity.class, DataSerializers.STRING);
    private static final DataParameter<ITextComponent> LAST_OUTPUT = EntityDataManager.createKey(CommandBlockMinecartEntity.class, DataSerializers.TEXT_COMPONENT);
    private final CommandBlockLogic commandBlockLogic = new MinecartCommandLogic(this);
    private int activatorRailCooldown;

    public CommandBlockMinecartEntity(EntityType<? extends CommandBlockMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public CommandBlockMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.COMMAND_BLOCK_MINECART, world, d, d2, d3);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(COMMAND, "");
        this.getDataManager().register(LAST_OUTPUT, StringTextComponent.EMPTY);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.commandBlockLogic.read(compoundNBT);
        this.getDataManager().set(COMMAND, this.getCommandBlockLogic().getCommand());
        this.getDataManager().set(LAST_OUTPUT, this.getCommandBlockLogic().getLastOutput());
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.commandBlockLogic.write(compoundNBT);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.COMMAND_BLOCK;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return Blocks.COMMAND_BLOCK.getDefaultState();
    }

    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }

    @Override
    public void onActivatorRailPass(int n, int n2, int n3, boolean bl) {
        if (bl && this.ticksExisted - this.activatorRailCooldown >= 4) {
            this.getCommandBlockLogic().trigger(this.world);
            this.activatorRailCooldown = this.ticksExisted;
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        return this.commandBlockLogic.tryOpenEditCommandBlock(playerEntity);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (LAST_OUTPUT.equals(dataParameter)) {
            try {
                this.commandBlockLogic.setLastOutput(this.getDataManager().get(LAST_OUTPUT));
            } catch (Throwable throwable) {}
        } else if (COMMAND.equals(dataParameter)) {
            this.commandBlockLogic.setCommand(this.getDataManager().get(COMMAND));
        }
    }

    @Override
    public boolean ignoreItemEntityData() {
        return false;
    }

    public class MinecartCommandLogic
    extends CommandBlockLogic {
        final CommandBlockMinecartEntity this$0;

        public MinecartCommandLogic(CommandBlockMinecartEntity commandBlockMinecartEntity) {
            this.this$0 = commandBlockMinecartEntity;
        }

        @Override
        public ServerWorld getWorld() {
            return (ServerWorld)this.this$0.world;
        }

        @Override
        public void updateCommand() {
            this.this$0.getDataManager().set(COMMAND, this.getCommand());
            this.this$0.getDataManager().set(LAST_OUTPUT, this.getLastOutput());
        }

        @Override
        public Vector3d getPositionVector() {
            return this.this$0.getPositionVec();
        }

        public CommandBlockMinecartEntity getMinecart() {
            return this.this$0;
        }

        @Override
        public CommandSource getCommandSource() {
            return new CommandSource(this, this.this$0.getPositionVec(), this.this$0.getPitchYaw(), this.getWorld(), 2, this.getName().getString(), this.this$0.getDisplayName(), this.getWorld().getServer(), this.this$0);
        }
    }
}

