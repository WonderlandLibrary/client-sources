// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command;

import java.util.ArrayList;
import gg.childtrafficking.smokex.module.Module;
import java.util.Iterator;
import gg.childtrafficking.smokex.property.Property;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import gg.childtrafficking.smokex.SmokeXClient;
import java.util.Collection;
import java.util.Arrays;
import gg.childtrafficking.smokex.command.commands.FontCommand;
import gg.childtrafficking.smokex.command.commands.EnemyCommand;
import gg.childtrafficking.smokex.command.commands.LoginCommand;
import gg.childtrafficking.smokex.command.commands.ModulesCommand;
import gg.childtrafficking.smokex.command.commands.ConfigCommand;
import gg.childtrafficking.smokex.command.commands.FriendCommand;
import gg.childtrafficking.smokex.command.commands.ClientNameCommand;
import gg.childtrafficking.smokex.command.commands.BindCommand;
import gg.childtrafficking.smokex.command.commands.ToggleCommand;
import gg.childtrafficking.smokex.command.commands.HelpCommand;
import gg.childtrafficking.smokex.command.commands.HideCommand;
import gg.childtrafficking.smokex.command.commands.ProxyCommand;
import java.util.List;

public final class CommandManager
{
    private static final List<Command> COMMANDS;
    
    public CommandManager() {
        CommandManager.COMMANDS.addAll(Arrays.asList(new ProxyCommand(), new HideCommand(), new HelpCommand(), new ToggleCommand(), new BindCommand(), new ClientNameCommand(), new FriendCommand(), new ConfigCommand(), new ModulesCommand(), new LoginCommand(), new EnemyCommand(), new FontCommand()));
    }
    
    public List<Command> getCommands() {
        return CommandManager.COMMANDS;
    }
    
    public void parse(final String input) {
        final String[] args = input.substring(1).split(" ");
        for (final Command command : CommandManager.COMMANDS) {
            final Property<?> p;
            if (command.getName().equalsIgnoreCase(args[0]) || Arrays.stream(command.getAliases()).anyMatch(p -> p.equalsIgnoreCase(args[0]))) {
                command.execute(Arrays.copyOfRange(args, 1, args.length));
                return;
            }
        }
        final Module module = SmokeXClient.getInstance().getModuleManager().getModule(args[0]);
        if (module != null) {
            if (args.length > 1) {
                final Property<?> property = module.getProperty(args[1]);
                if (property != null) {
                    if (args.length > 2) {
                        if (property.setValueFromString(args[2])) {
                            if (args[2].equalsIgnoreCase("cfont")) {
                                ModuleManager.getInstance(HUDModule.class).toggle();
                                ModuleManager.getInstance(HUDModule.class).panic = true;
                            }
                            ChatUtils.addChatMessage("Set " + property.getDisplayName() + " to " + property.getValue());
                            SmokeXClient.getInstance().getConfigManager().getCurrentConfig().serialize();
                        }
                        else {
                            ChatUtils.addChatMessage("§4Invalid value for property.");
                        }
                    }
                    else if (property instanceof EnumProperty) {
                        ChatUtils.addChatMessage(property.getIdentifier() + " : " + Arrays.toString(((EnumProperty)property).getEnumConstants()));
                    }
                    else {
                        ChatUtils.addChatMessage(property.getIdentifier() + " : " + property.getValueAsString());
                    }
                }
                else {
                    ChatUtils.addChatMessage("§4Property Doesn't Exist. (Type \"." + module.getName() + "\" to get properties)");
                }
            }
            else {
                ChatUtils.addChatMessage("Properties of " + module.getName() + ": \n");
                for (final Property<?> p : module.getProperties()) {
                    ChatUtils.addChatMessage(p.getIdentifier() + " : " + p.getValue().toString());
                }
            }
            return;
        }
        ChatUtils.addChatMessage("§cThat command does not exist!");
    }
    
    static {
        COMMANDS = new ArrayList<Command>();
    }
}
