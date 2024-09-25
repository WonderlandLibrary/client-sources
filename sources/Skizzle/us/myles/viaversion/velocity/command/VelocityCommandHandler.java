/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.command.Command
 *  com.velocitypowered.api.command.CommandSource
 *  org.checkerframework.checker.nullness.qual.NonNull
 */
package us.myles.ViaVersion.velocity.command;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import us.myles.ViaVersion.commands.ViaCommandHandler;
import us.myles.ViaVersion.velocity.command.VelocityCommandSender;
import us.myles.ViaVersion.velocity.command.subs.ProbeSubCmd;

public class VelocityCommandHandler
extends ViaCommandHandler
implements Command {
    public VelocityCommandHandler() {
        try {
            this.registerSubCommand(new ProbeSubCmd());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(@NonNull CommandSource source, String[] args) {
        this.onCommand(new VelocityCommandSender(source), args);
    }

    public List<String> suggest(@NonNull CommandSource source, String[] currentArgs) {
        return this.onTabComplete(new VelocityCommandSender(source), currentArgs);
    }
}

