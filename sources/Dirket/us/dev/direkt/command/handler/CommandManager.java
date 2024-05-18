package us.dev.direkt.command.handler;

import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.arguments.ArgumentStack;
import us.dev.direkt.command.handler.handling.ArgumentHandler;
import us.dev.direkt.command.handler.handling.handlers.*;
import us.dev.direkt.command.handler.utils.PrimitiveArrays;
import us.dev.direkt.command.handler.utils.Primitives;
import us.dev.direkt.command.internal.core.*;
import us.dev.direkt.command.internal.misc.*;
import us.dev.direkt.command.internal.movement.Jump;
import us.dev.direkt.command.internal.movement.TP;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Foundry
 */
public final class CommandManager {
    private Map<String, Command> registry = new HashMap<>();
    private final Set<ArgumentHandler> argumentHandlers = new HashSet<>();

    public CommandManager() {
        this.registerHandler(new StringArgumentHandler());
        this.registerHandler(new BooleanArgumentHandler());
        this.registerHandler(new NumberArgumentHandler());
        this.registerHandler(new ArrayArgumentHandler());
        this.registerHandler(new EnumArgumentHandler());
    }

    public CommandManager makeLoaded() {
        registry = new CommandMapBuilder()
                /* Core */
                .put(new Bind())
                .put(new Commands())
                .put(new Help())
                .put(new Lock())
                .put(new Modules())
                .put(new Toggle())

                /* Misc */
                .put(new Crash())
                .put(new Damage())
                .put(new Drop())
                .put(new GameModeDiscovery())
                .put(new Give())
                .put(new PluginDetection())
                .put(new Say())
                .put(new ServerInfo())

                /* Movement */
                .put(new Jump())
                .put(new TP())
                .build();
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<ArgumentHandler<T>> findArgumentHandler(Class<T> type) {
        if (type.isPrimitive()) {
            type = Primitives.wrap(type);
        } else if (type.isArray() && type.getComponentType().isPrimitive()) {
            type = PrimitiveArrays.wrap(type);
        }
        for (ArgumentHandler argumentHandler : this.argumentHandlers) {
            for (Class handledType : argumentHandler.getHandledTypes()) {
                if (handledType.isAssignableFrom(type)) {
                    return Optional.of(argumentHandler);
                }
            }
        }
        return Optional.empty();
    }

    private void registerHandler(ArgumentHandler argumentHandler) {
        this.argumentHandlers.add(argumentHandler);
    }

    public boolean register(Command command) {
        /*if (this.registry.containsKey(command.getLabel()) || this.registry.containsValue(command)) {
            return false;
        }*/
        Command previous = this.registry.put(command.getLabel().toLowerCase(), command);
        if (previous != null) {
            this.registry.values().removeIf(c -> c.equals(previous));
        }
        for (String alias : command.getAliases()) {
            this.registry.put(alias.toLowerCase(), command);
        }
        return true;
    }

    public Optional<Command> find(String identifier) {
        return Optional.ofNullable(this.registry.get(identifier.toLowerCase()));
    }

    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(registry.values());
    }

    public Optional<String> execute(String input) {
        if (input == null) {
            return Optional.of("Invalid input. (input cannot be null)");
        } else if (input.length() == 0) {
            return Optional.of("Invalid input. (input cannot be empty)");
        }

        final ArgumentStack argumentStack = new ArgumentStack(parse(input.toCharArray()));
        return Optional.ofNullable(this.find(argumentStack.next())
                .<Supplier<String>>map(c -> () -> {
                    argumentStack.remove();
                    return c.execute(argumentStack);
                }).orElseGet(() -> () -> "Command not found.").get());
    }

    private static String[] parse(char[] input) {
        boolean quoted = false, inArray = false;
        int arrayElementCount = -1;
        ArrayList<String> arguments = new ArrayList<>();
        StringBuilder argument = new StringBuilder();
        parserBlock : for (int index = 0; index < input.length; index++) {
            switch (input[index]) {
                case '{': {
                    if (!quoted && !inArray) {
                        inArray = true;
                        arrayElementCount = 1;
                    }
                    argument.append(input[index]);
                    continue parserBlock;
                }
                case '\"': {
                    if (index > 0 && input[index - 1] == '\\') {
                        argument.setCharAt(argument.length() - 1, '\"');
                    } else {
                        quoted = !quoted;
                    }
                    continue parserBlock;
                }
                case ',': {
                    argument.append(input[index]);
                    if (!quoted && inArray) {
                        while (input[index+1] == ' ') {
                            index++;
                        }
                        arrayElementCount++;
                    }
                    continue parserBlock;
                }
                case '}': {
                    if (!quoted && inArray) {
                        inArray = false;
                        argument.append(input[index]).append(arrayElementCount);
                        arrayElementCount = 0;
                        arguments.add(argument.toString());
                        argument.setLength(0);
                    } else {
                        argument.append(input[index]);
                    }
                    continue parserBlock;
                }
                case ' ': {
                    if (!quoted && !inArray) {
                        if (argument.length() <= 0) continue parserBlock;
                        arguments.add(argument.toString());
                        argument.setLength(0);
                        continue parserBlock;
                    }
                }
                default: {
                    argument.append(input[index]);
                }
            }
        }
        if (argument.length() > 0) arguments.add(argument.toString());
        return arguments.toArray(new String[arguments.size()]);
    }

    private static class CommandMapBuilder {
        private Map<String, Command> builder = new HashMap<>();

        public CommandMapBuilder put(Command command) {
            builder.put(command.getLabel().toLowerCase(), command);
            for (String alias : command.getAliases()) {
                builder.put(alias.toLowerCase(), command);
            }
            return this;
        }

        public Map<String, Command> build() {
            return builder;
        }
    }
}

