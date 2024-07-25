package club.bluezenith.command;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.commands.*;
import club.bluezenith.events.impl.SentMessageEvent;
import club.bluezenith.module.ModuleCommand;
import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.util.MinecraftInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager {
    public String commandPrefix = ".";
    public List<Command> commands = new ArrayList<>();

    public CommandManager() {
        /*new Reflections("club.bluezenith.command.commands").getSubTypesOf(Command.class).forEach(cmd -> {
            try {
                commands.add(cmd.getDeclaredConstructor().newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });*/

        try {
            //lev r u retarded
            commands.add(BindCommand.class.getDeclaredConstructor().newInstance());
            commands.add(ClientnameCommand.class.getDeclaredConstructor().newInstance());
            commands.add(ConfigCommand.class.getDeclaredConstructor().newInstance());
            commands.add(FontCommand.class.getDeclaredConstructor().newInstance());
            commands.add(ForCommand.class.getDeclaredConstructor().newInstance());
            commands.add(FriendCommand.class.getDeclaredConstructor().newInstance());
            commands.add(HelpCommand.class.getDeclaredConstructor().newInstance());
            commands.add(HideCommand.class.getDeclaredConstructor().newInstance());
            commands.add(IgnCommand.class.getDeclaredConstructor().newInstance());
            commands.add(PanicCommand.class.getDeclaredConstructor().newInstance());
            commands.add(RenameCommand.class.getDeclaredConstructor().newInstance());
          //  commands.add(ScriptsCommand.class.getDeclaredConstructor().newInstance());
            commands.add(TargetCommand.class.getDeclaredConstructor().newInstance());
            commands.add(ToggleCommand.class.getDeclaredConstructor().newInstance());
            commands.add(VClipCommand.class.getDeclaredConstructor().newInstance());
            commands.add(new KillsultsCommand()); //do it like this
            commands.add(new HClipCommand());
            commands.add(new IgnoreErrorsCommand());
            commands.add(new AntiSpamCommand());


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        BlueZenith.getBlueZenith().getModuleManager().getModules().forEach(mod -> commands.add(new ModuleCommand(mod, mod.getName())));

    }

    public void dispatch(SentMessageEvent event) {
        if (event.message.startsWith(commandPrefix)) {
            if(event.sendToChat){
                MinecraftInstance.mc.ingameGUI.getChatGUI().addToSentMessages(event.message);
            }
            event.cancel();
            String[] args = event.message.substring(commandPrefix.length()).split(" ");
            for (Command command : commands) {
                if (command.name.equalsIgnoreCase(args[0])) {
                    command.execute(args);
                    return;
                }
                for (String alias : command.pref) {
                    if (alias.equalsIgnoreCase(args[0])) {
                        command.execute(args);
                        return;
                    }
                }

            }
            ClientUtils.fancyMessage("Couldn't find that command.");
        }
    }

    public Command getCommand(String name) {
        for(Command c : commands) {
            if(c.name.equalsIgnoreCase(name)) return c;
        }
        return null;
    }

    public void unloadScripts() {
        List<Command> list = new CopyOnWriteArrayList<>(commands);
       // List<ScriptCommand> scriptCommands = new CopyOnWriteArrayList<>(ScriptManager.getScriptManager().scriptCommands);
        for(Command c : list) {
            if(c.isScript/* && c instanceof ScriptCommand*/) {
                //scriptCommands.remove(c);
                list.remove(c);
            }
        }
        commands = list;
        //ScriptManager.getScriptManager().scriptCommands = scriptCommands;
    }
}
