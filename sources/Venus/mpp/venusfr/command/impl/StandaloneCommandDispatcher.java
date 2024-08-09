/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mpp.venusfr.command.AdviceCommandFactory;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandDispatcher;
import mpp.venusfr.command.CommandProvider;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.MultiNamedCommand;
import mpp.venusfr.command.ParametersFactory;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.command.impl.DispatchResult;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.venusfr;
import net.minecraft.util.text.TextFormatting;

public class StandaloneCommandDispatcher
implements CommandDispatcher,
CommandProvider {
    private static final String DELIMITER = " ";
    private final Prefix prefix;
    private final ParametersFactory parametersFactory;
    private final Logger logger;
    private final Map<String, Command> aliasToCommandMap;

    public StandaloneCommandDispatcher(List<Command> list, AdviceCommandFactory adviceCommandFactory, Prefix prefix, ParametersFactory parametersFactory, Logger logger) {
        this.prefix = prefix;
        this.parametersFactory = parametersFactory;
        this.logger = logger;
        this.aliasToCommandMap = this.commandsToAliasToCommandMap(this.commandsWithAdviceCommand(adviceCommandFactory, list));
    }

    @Override
    public DispatchResult dispatch(String string) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        String string2 = this.prefix.get();
        if (!string.startsWith(string2)) {
            return DispatchResult.NOT_DISPATCHED;
        }
        String[] stringArray = string.split(DELIMITER);
        String string3 = stringArray[0].substring(string2.length());
        Command command = this.aliasToCommandMap.get(string3);
        try {
            String string4 = this.extractParametersFromMessage(string, stringArray);
            command.execute(this.parametersFactory.createParameters(string4, DELIMITER));
        } catch (Exception exception) {
            this.handleCommandException(exception, command);
        }
        return DispatchResult.DISPATCHED;
    }

    @Override
    public Command command(String string) {
        return this.aliasToCommandMap.get(string);
    }

    private Map<String, Command> commandsToAliasToCommandMap(List<Command> list) {
        return list.stream().flatMap(this::commandToWrappedCommandStream).collect(Collectors.toMap(FlatMapCommand::getAlias, FlatMapCommand::getCommand));
    }

    private Stream<FlatMapCommand> commandToWrappedCommandStream(Command command) {
        Stream<FlatMapCommand> stream = Stream.of(new FlatMapCommand(command.name(), command));
        if (command instanceof MultiNamedCommand) {
            MultiNamedCommand multiNamedCommand = (MultiNamedCommand)((Object)command);
            return Stream.concat(stream, multiNamedCommand.aliases().stream().map(arg_0 -> StandaloneCommandDispatcher.lambda$commandToWrappedCommandStream$0(command, arg_0)));
        }
        return stream;
    }

    private void handleCommandException(Exception exception, Command command) {
        if (exception instanceof CommandException) {
            this.logger.log(exception.getMessage());
        } else {
            this.logger.log("\u041f\u0440\u043e\u0438\u0437\u043e\u0448\u043b\u0430 \u043e\u0448\u0438\u0431\u043a\u0430 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0432\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u044b!");
            String string = "\u0414\u0435\u0442\u0430\u043b\u0438 \u043e\u0448\u0438\u0431\u043a\u0438: ";
            String string2 = exception instanceof NullPointerException ? "\u0422\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442." : exception.getMessage();
            this.logger.log(string.concat(string2));
        }
        if (command instanceof CommandWithAdvice) {
            this.logger.log(String.format(TextFormatting.GRAY + "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 %sadvice %s", this.prefix.get(), command.name()));
        }
    }

    private String extractParametersFromMessage(String string, String[] stringArray) {
        return string.substring((stringArray.length != 1 ? 1 : 0) + stringArray[0].length());
    }

    private List<Command> commandsWithAdviceCommand(AdviceCommandFactory adviceCommandFactory, List<Command> list) {
        ArrayList<Command> arrayList = new ArrayList<Command>(list);
        arrayList.add(adviceCommandFactory.adviceCommand(this));
        return arrayList;
    }

    private static FlatMapCommand lambda$commandToWrappedCommandStream$0(Command command, String string) {
        return new FlatMapCommand(string, command);
    }

    private static final class FlatMapCommand {
        private final String alias;
        private final Command command;

        public FlatMapCommand(String string, Command command) {
            this.alias = string;
            this.command = command;
        }

        public String getAlias() {
            return this.alias;
        }

        public Command getCommand() {
            return this.command;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof FlatMapCommand)) {
                return true;
            }
            FlatMapCommand flatMapCommand = (FlatMapCommand)object;
            String string = this.getAlias();
            String string2 = flatMapCommand.getAlias();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return true;
            }
            Command command = this.getCommand();
            Command command2 = flatMapCommand.getCommand();
            return command == null ? command2 != null : !command.equals(command2);
        }

        public int hashCode() {
            int n = 59;
            int n2 = 1;
            String string = this.getAlias();
            n2 = n2 * 59 + (string == null ? 43 : string.hashCode());
            Command command = this.getCommand();
            n2 = n2 * 59 + (command == null ? 43 : command.hashCode());
            return n2;
        }

        public String toString() {
            return "StandaloneCommandDispatcher.FlatMapCommand(alias=" + this.getAlias() + ", command=" + this.getCommand() + ")";
        }
    }
}

