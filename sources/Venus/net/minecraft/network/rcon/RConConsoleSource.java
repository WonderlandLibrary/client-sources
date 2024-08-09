/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import java.util.UUID;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class RConConsoleSource
implements ICommandSource {
    private static final StringTextComponent field_232647_b_ = new StringTextComponent("Rcon");
    private final StringBuffer buffer = new StringBuffer();
    private final MinecraftServer server;

    public RConConsoleSource(MinecraftServer minecraftServer) {
        this.server = minecraftServer;
    }

    public void resetLog() {
        this.buffer.setLength(0);
    }

    public String getLogContents() {
        return this.buffer.toString();
    }

    public CommandSource getCommandSource() {
        ServerWorld serverWorld = this.server.func_241755_D_();
        return new CommandSource(this, Vector3d.copy(serverWorld.getSpawnPoint()), Vector2f.ZERO, serverWorld, 4, "Rcon", field_232647_b_, this.server, null);
    }

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        this.buffer.append(iTextComponent.getString());
    }

    @Override
    public boolean shouldReceiveFeedback() {
        return false;
    }

    @Override
    public boolean shouldReceiveErrors() {
        return false;
    }

    @Override
    public boolean allowLogging() {
        return this.server.allowLoggingRcon();
    }
}

