package us.dev.direkt.command;

import us.dev.api.interfaces.Aliased;
import us.dev.api.interfaces.Labeled;
import us.dev.direkt.command.handler.CommandExecutor;
import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.command.handler.annotations.Flag;
import us.dev.direkt.command.handler.arguments.ArgumentStack;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.exceptions.UnsupportedParameterException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Foundry
 */
public abstract class Command implements Labeled, Aliased {
    protected final String label;
    protected final String[] aliases;
    protected final CommandManager commandManager;
    protected final Set<CommandExecutor> executors = new HashSet<>();

    protected Command(CommandManager commandManager, String label, String... aliases) {
        this.label = label.replaceAll(" ", "").toLowerCase();
        this.aliases = Stream.of(aliases).map(s -> s.replaceAll(" ", "").toLowerCase()).toArray(x -> aliases);
        this.commandManager = commandManager;
        for (Class<?> clazz = this.getClass(); clazz.getSuperclass() != null; clazz = clazz.getSuperclass()) {
            for (Method method : clazz.getDeclaredMethods()) {
                Class returnType;
                Executes handler = method.getAnnotation(Executes.class);
                if (handler == null || (returnType = method.getReturnType()) != Void.TYPE && returnType != String.class && returnType != String[].class)
                    continue;
                try {
                    this.executors.add(new CommandExecutor(this, method, handler, commandManager));
                } catch (UnsupportedParameterException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String execute(ArgumentStack argumentStack) {
        String output = "Invalid argument.";
        final ArgumentStack originalArgumentStack = argumentStack;
        executorBlock: for (CommandExecutor executor : this.executors) {
            CommandParameter parameter;
            argumentStack = originalArgumentStack.copy();
            subHandlerBlock : for (String sub : executor.getHandler().value()) {
                if (!argumentStack.hasNext()) continue executorBlock;
                final String argument = argumentStack.next();
                for (String part : sub.split("\\|")) {
                    if (!argument.equalsIgnoreCase(part)) {
                        continue;
                    }
                    argumentStack.remove();
                    continue subHandlerBlock;
                }
                continue executorBlock;
            }
            final CommandParameter[] parameters = executor.getParameters();
            final boolean[] presents = new boolean[parameters.length];
            final Object[] arguments = new Object[parameters.length];
            for (int index = 0; index < arguments.length; index++) {
                parameter = parameters[index];
                final Flag flag = parameter.getAnnotation(Flag.class);
                if (flag == null) continue;
                String input = parameter.isBoolean() ? "false" : flag.def();
                boolean present = false;
                argStackBlock : while (argumentStack.hasNext()) {
                    final String argument = argumentStack.next();
                    for (String id : flag.value()) {
                        if (!argument.equalsIgnoreCase("-" + id)) continue;
                        present = true;
                        argumentStack.remove();
                        if (parameter.isBoolean()) {
                            input = "true";
                            break argStackBlock;
                        }
                        if (!argumentStack.hasNext()) break argStackBlock;
                        input = argumentStack.next();
                        argumentStack.remove();
                        break argStackBlock;
                    }
                }
                if (!present && parameter.isOptional()) {
                    presents[index] = true;
                    arguments[index] = Optional.empty();
                    continue;
                }
                try {
                    presents[index] = true;
                    final Object parsed = this.commandManager.findArgumentHandler(parameter.getType())
                            .orElseThrow(() -> new UnsupportedParameterException("unsupported parameter type found while executing command")).parse(commandManager, parameter, input);
                    arguments[index] = parameter.isOptional() ? Optional.ofNullable(parsed) : parsed;
                } catch (ArgumentParseException e) {
                    if (parameter.isOptional()) {
                        presents[index] = true;
                        arguments[index] = Optional.empty();
                    }
                    output = String.format("Failed to parse argument for parameter %s. (%s)", index + 1, e.getMessage());
                    continue executorBlock;
                }
                argumentStack.setCursor(0);
            }
            for (int index = 0; index < arguments.length; index++) {
                if (presents[index]) continue;
                if (!argumentStack.hasNext()) {
                    while (index < arguments.length) {
                        parameter = parameters[index];
                        if (!parameter.isOptional()) continue executorBlock;
                        arguments[index++] = Optional.empty();
                    }
                    break;
                }
                try {
                    parameter = parameters[index];
                    final Object parsed = this.commandManager.findArgumentHandler(parameter.getType())
                            .orElseThrow(() -> new UnsupportedParameterException("unsupported parameter type found while executing command")).parse(commandManager, parameter, argumentStack.next());
                    arguments[index] = parameter.isOptional() ? Optional.ofNullable(parsed) : parsed;
                } catch (ArgumentParseException e) {
                    output = String.format("Failed to parse argument for parameter %s. (%s)", index + 1, e.getMessage());
                    continue executorBlock;
                }
            }
            if (argumentStack.hasNext()) continue;
            return executor.execute(arguments);
        }
        return output;
    }

    public String[] getUsage() {
        return getUsage(null);
    }

    public String[] getUsage(String nameBypass) {
        final String parsedLabel = nameBypass != null
                ? nameBypass
                : (label + (aliases.length > 0
                    ? "|" + String.join("|", aliases)
                    : ""));
        final String prefix = String.format("[%s]", parsedLabel);
        return executors.stream()
                .map(executor -> String.format("%s %s", prefix, executor.getSyntax()))
                .toArray(String[]::new);
    }

    public String[][] getUsageTree() {
        String[][] usageTree = new String[executors.size()][];
        int idx = 0;
        for (CommandExecutor executor : executors) {
            final List<String> executorSyntax = executor.getSyntaxTree();
            usageTree[idx++] = executorSyntax.toArray(new String[executorSyntax.size()]);
        }
        return usageTree;
    }

    public String getFormattedUsage(boolean multiLine) {
        return getFormattedUsage(null, multiLine);
    }

    public String getFormattedUsage(String nameBypass, boolean multiLine) {
        final String[] unparsedSyntax = getUsage(nameBypass);
        StringBuilder syntaxBuilder = new StringBuilder("Syntax: ").append(multiLine ? System.lineSeparator() : "");
        for (String element : unparsedSyntax) {
            syntaxBuilder.append(element).append(", ").append(multiLine ? System.lineSeparator() : "");
        }
        syntaxBuilder.setLength(syntaxBuilder.length() - (multiLine ? 3 : 2));
        return syntaxBuilder.toString();
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Set<CommandExecutor> getExecutors() {
        return this.executors;
    }
}

