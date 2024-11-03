package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.Command.Raw;
import org.spongepowered.api.command.parameter.ArgumentReader.Mutable;

public class SpongeCommandHandler extends ViaCommandHandler implements Raw {
   public CommandResult process(CommandCause cause, Mutable arguments) {
      String[] args = arguments.input().length() > 0 ? arguments.input().split(" ") : new String[0];
      this.onCommand(new SpongeCommandSender(cause), args);
      return CommandResult.success();
   }

   public List<CommandCompletion> complete(CommandCause cause, Mutable arguments) {
      String[] args = arguments.input().split(" ", -1);
      return this.onTabComplete(new SpongeCommandSender(cause), args).stream().<CommandCompletion>map(CommandCompletion::of).collect(Collectors.toList());
   }

   public boolean canExecute(CommandCause cause) {
      return cause.hasPermission("viaversion.command");
   }

   public Optional<Component> shortDescription(CommandCause cause) {
      return Optional.of(Component.text("Shows ViaVersion Version and more."));
   }

   public Optional<Component> extendedDescription(CommandCause cause) {
      return this.shortDescription(cause);
   }

   public Optional<Component> help(@NotNull CommandCause cause) {
      return Optional.empty();
   }

   public Component usage(CommandCause cause) {
      return Component.text("Usage /viaversion");
   }
}
