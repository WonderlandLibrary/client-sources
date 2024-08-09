/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.mojang.brigadier.context.CommandContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class CommandBlockLogic
implements ICommandSource {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final ITextComponent field_226655_c_ = new StringTextComponent("@");
    private long lastExecution = -1L;
    private boolean updateLastExecution = true;
    private int successCount;
    private boolean trackOutput = true;
    @Nullable
    private ITextComponent lastOutput;
    private String commandStored = "";
    private ITextComponent customName = field_226655_c_;

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int n) {
        this.successCount = n;
    }

    public ITextComponent getLastOutput() {
        return this.lastOutput == null ? StringTextComponent.EMPTY : this.lastOutput;
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putString("Command", this.commandStored);
        compoundNBT.putInt("SuccessCount", this.successCount);
        compoundNBT.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        compoundNBT.putBoolean("TrackOutput", this.trackOutput);
        if (this.lastOutput != null && this.trackOutput) {
            compoundNBT.putString("LastOutput", ITextComponent.Serializer.toJson(this.lastOutput));
        }
        compoundNBT.putBoolean("UpdateLastExecution", this.updateLastExecution);
        if (this.updateLastExecution && this.lastExecution > 0L) {
            compoundNBT.putLong("LastExecution", this.lastExecution);
        }
        return compoundNBT;
    }

    public void read(CompoundNBT compoundNBT) {
        this.commandStored = compoundNBT.getString("Command");
        this.successCount = compoundNBT.getInt("SuccessCount");
        if (compoundNBT.contains("CustomName", 1)) {
            this.setName(ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("CustomName")));
        }
        if (compoundNBT.contains("TrackOutput", 0)) {
            this.trackOutput = compoundNBT.getBoolean("TrackOutput");
        }
        if (compoundNBT.contains("LastOutput", 1) && this.trackOutput) {
            try {
                this.lastOutput = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("LastOutput"));
            } catch (Throwable throwable) {
                this.lastOutput = new StringTextComponent(throwable.getMessage());
            }
        } else {
            this.lastOutput = null;
        }
        if (compoundNBT.contains("UpdateLastExecution")) {
            this.updateLastExecution = compoundNBT.getBoolean("UpdateLastExecution");
        }
        this.lastExecution = this.updateLastExecution && compoundNBT.contains("LastExecution") ? compoundNBT.getLong("LastExecution") : -1L;
    }

    public void setCommand(String string) {
        this.commandStored = string;
        this.successCount = 0;
    }

    public String getCommand() {
        return this.commandStored;
    }

    public boolean trigger(World world) {
        if (!world.isRemote && world.getGameTime() != this.lastExecution) {
            if ("Searge".equalsIgnoreCase(this.commandStored)) {
                this.lastOutput = new StringTextComponent("#itzlipofutzli");
                this.successCount = 1;
                return false;
            }
            this.successCount = 0;
            MinecraftServer minecraftServer = this.getWorld().getServer();
            if (minecraftServer.isCommandBlockEnabled() && !StringUtils.isNullOrEmpty(this.commandStored)) {
                try {
                    this.lastOutput = null;
                    CommandSource commandSource = this.getCommandSource().withResultConsumer(this::lambda$trigger$0);
                    minecraftServer.getCommandManager().handleCommand(commandSource, this.commandStored);
                } catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Executing command block");
                    CrashReportCategory crashReportCategory = crashReport.makeCategory("Command to be executed");
                    crashReportCategory.addDetail("Command", this::getCommand);
                    crashReportCategory.addDetail("Name", this::lambda$trigger$1);
                    throw new ReportedException(crashReport);
                }
            }
            this.lastExecution = this.updateLastExecution ? world.getGameTime() : -1L;
            return false;
        }
        return true;
    }

    public ITextComponent getName() {
        return this.customName;
    }

    public void setName(@Nullable ITextComponent iTextComponent) {
        this.customName = iTextComponent != null ? iTextComponent : field_226655_c_;
    }

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        if (this.trackOutput) {
            this.lastOutput = new StringTextComponent("[" + TIMESTAMP_FORMAT.format(new Date()) + "] ").append(iTextComponent);
            this.updateCommand();
        }
    }

    public abstract ServerWorld getWorld();

    public abstract void updateCommand();

    public void setLastOutput(@Nullable ITextComponent iTextComponent) {
        this.lastOutput = iTextComponent;
    }

    public void setTrackOutput(boolean bl) {
        this.trackOutput = bl;
    }

    public boolean shouldTrackOutput() {
        return this.trackOutput;
    }

    public ActionResultType tryOpenEditCommandBlock(PlayerEntity playerEntity) {
        if (!playerEntity.canUseCommandBlock()) {
            return ActionResultType.PASS;
        }
        if (playerEntity.getEntityWorld().isRemote) {
            playerEntity.openMinecartCommandBlock(this);
        }
        return ActionResultType.func_233537_a_(playerEntity.world.isRemote);
    }

    public abstract Vector3d getPositionVector();

    public abstract CommandSource getCommandSource();

    @Override
    public boolean shouldReceiveFeedback() {
        return this.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK) && this.trackOutput;
    }

    @Override
    public boolean shouldReceiveErrors() {
        return this.trackOutput;
    }

    @Override
    public boolean allowLogging() {
        return this.getWorld().getGameRules().getBoolean(GameRules.COMMAND_BLOCK_OUTPUT);
    }

    private String lambda$trigger$1() throws Exception {
        return this.getName().getString();
    }

    private void lambda$trigger$0(CommandContext commandContext, boolean bl, int n) {
        if (bl) {
            ++this.successCount;
        }
    }
}

