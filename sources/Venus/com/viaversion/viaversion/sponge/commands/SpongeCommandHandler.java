/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.kyori.adventure.text.Component
 *  org.spongepowered.api.command.Command$Raw
 *  org.spongepowered.api.command.CommandCause
 *  org.spongepowered.api.command.CommandCompletion
 *  org.spongepowered.api.command.CommandResult
 *  org.spongepowered.api.command.parameter.ArgumentReader$Mutable
 */
package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.sponge.commands.SpongeCommandSender;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.ArgumentReader;

public class SpongeCommandHandler
extends ViaCommandHandler
implements Command.Raw {
    public CommandResult process(CommandCause commandCause, ArgumentReader.Mutable mutable) {
        String[] stringArray = mutable.input().length() > 0 ? mutable.input().split(" ") : new String[]{};
        this.onCommand(new SpongeCommandSender(commandCause), stringArray);
        return CommandResult.success();
    }

    public List<CommandCompletion> complete(CommandCause commandCause, ArgumentReader.Mutable mutable) {
        String[] stringArray = mutable.input().split(" ", -1);
        return this.onTabComplete(new SpongeCommandSender(commandCause), stringArray).stream().map(CommandCompletion::of).collect(Collectors.toList());
    }

    public boolean canExecute(CommandCause commandCause) {
        return commandCause.hasPermission("viaversion.admin");
    }

    public Optional<Component> shortDescription(CommandCause commandCause) {
        return Optional.of(Component.text((String)"Shows ViaVersion Version and more."));
    }

    public Optional<Component> extendedDescription(CommandCause commandCause) {
        return this.shortDescription(commandCause);
    }

    public Optional<Component> help(@NotNull CommandCause commandCause) {
        return Optional.empty();
    }

    public Component usage(CommandCause commandCause) {
        return Component.text((String)"Usage /viaversion");
    }
}

