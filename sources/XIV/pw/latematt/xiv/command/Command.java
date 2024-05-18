package pw.latematt.xiv.command;

import pw.latematt.xiv.XIV;

/**
 * @author Matthew
 */
public class Command {
    protected final String cmd, description;
    protected final String[] arguments, aliases;
    protected final CommandHandler handler;

    private Command(Builder builder) {
        this.cmd = builder.cmd;
        this.description = builder.description;
        this.arguments = builder.arguments;
        this.aliases = builder.aliases;
        this.handler = builder.handler;
        XIV.getInstance().getCommandManager().getContents().add(this);
    }

    public String getCmd() {
        return cmd;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String[] getArguments() {
        return arguments;
    }

    public CommandHandler getHandler() {
        return handler;
    }

    public static Builder newCommand() {
        return new Builder();
    }

    public static final class Builder {
        private String cmd, description;
        private String[] arguments, aliases;
        private CommandHandler handler;

        private Builder() {
        }

        public Command build() {
            return new Command(this);
        }

        public Builder cmd(String cmd) {
            this.cmd = cmd;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder aliases(String... aliases) {
            this.aliases = aliases;
            return this;
        }

        public Builder arguments(String... arguments) {
            this.arguments = arguments;
            return this;
        }

        public Builder handler(CommandHandler handler) {
            this.handler = handler;
            return this;
        }
    }
}
