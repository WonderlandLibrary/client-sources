package club.strifeclient.command;

import club.strifeclient.command.implementations.*;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import lombok.Getter;

import java.util.Arrays;

public class CommandManager {
    @Getter
    private final ClassToInstanceMap<Command> commands = MutableClassToInstanceMap.create();

    public void init() {
        add(new BindCommand(),
            new ToggleCommand(),
            new LoginCommand(),
            new ReloadCommand(),
            new UsernameCommand(),
            new EnemyCommand(),
            new FriendCommand(),
            new ConfigCommand()
        );
        System.out.println("Commands have been initialized.");
    }
    public void deInit() {
        commands.clear();
    }

    private void add(Command... cmds) {
        Arrays.stream(cmds).forEach(command -> commands.put(command.getClass(), command));
    }

    public <T extends Command> T getCommand(Class<T> cmd) {
        return (T) commands.getInstance(cmd);
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> T getCommand(String name) {
        return (T) commands.values().stream().filter(command -> command.getName().contains(name)).findFirst().orElse(null);
    }
}
