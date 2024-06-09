package us.dev.direkt.command.handler;

import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.command.handler.annotations.Flag;
import us.dev.direkt.command.handler.exceptions.UnsupportedParameterException;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Foundry
 */
public final class CommandExecutor {
    private final Command source;
    private final Method method;
    private final CommandParameter[] parameters;
    private final Executes handler;
    private final CommandManager commandManager;

    public CommandExecutor(Command source, Method method, Executes handler, CommandManager commandManager) throws UnsupportedParameterException {
        this.source = source;
        this.method = method;
        this.handler = handler;
        this.commandManager = commandManager;
        int[] index = new int[1];
        this.parameters = Arrays.stream(method.getParameters()).map(parameter -> {
            Class<?> type = parameter.getType();
            if (type == Optional.class) {
                Type generic = method.getGenericParameterTypes()[index[0]];
                if (generic instanceof ParameterizedType) {
                    type = (Class<?>) ((ParameterizedType) generic).getActualTypeArguments()[0];
                } else {
                    throw new UnsupportedParameterException("failed to resolve argument type");
                }
            }
            index[0]++;
            if (commandManager.findArgumentHandler(type) == null) {
                throw new UnsupportedParameterException(type);
            }
            return new DeclaredCommandParameter(parameter, type);
        }
        ).toArray(DeclaredCommandParameter[]::new);
    }

    public String execute(Object ... arguments) {
        try {
            this.method.setAccessible(true);
            Object output = this.method.invoke(this.source, arguments);
            if (output instanceof String[]) {
                output = String.join(System.lineSeparator(), (String[]) output);
            }
            return (String)output;
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return "An error occurred while executing the command.";
        }
    }

    public String getSyntax() {
        return String.join(" ", getSyntaxTree());
    }

    public List<String> getSyntaxTree() {
        final List<String> syntaxBuilder = new ArrayList<>(this.handler.value().length + this.parameters.length);
        for (String sub : this.handler.value()) {
            syntaxBuilder.add(String.format("[%s]", sub));
        }
        for (CommandParameter parameter : this.parameters) {
            String syntax = this.commandManager.findArgumentHandler(parameter.getType())
                    .orElseThrow(() -> new UnsupportedParameterException("unsupported parameter type found while creating syntax tree")).getSyntax(parameter);
            Flag flag = parameter.getAnnotation(Flag.class);
            if (flag != null) {
                syntaxBuilder.add(String.format("(-[%s]%s)", String.join("|", flag.value()), parameter.isBoolean() ? "" : " [" + syntax + "]"));
                continue;
            }
            syntax = parameter.isOptional() ? String.format("<%s>", syntax) : String.format("[%s]", syntax);
            syntaxBuilder.add(syntax);
        }
        return syntaxBuilder;
    }

    public CommandParameter[] getParameters() {
        return this.parameters;
    }

    public Executes getHandler() {
        return this.handler;
    }

    private static final class DeclaredCommandParameter implements CommandParameter {
        private final Parameter parameter;
        private final Class type;

        DeclaredCommandParameter(Parameter parameter, Class type) {
            this.parameter = parameter;
            this.type = type;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
            return this.parameter.getAnnotation(annotationClass);
        }

        @Override
        public Annotation[] getAnnotations() {
            return this.parameter.getAnnotations();
        }

        @Override
        public Class<?> getType() {
            return this.type;
        }

        @Override
        public boolean isOptional() {
            return this.parameter.getType() == Optional.class;
        }

        @Override
        public boolean isBoolean() {
            return this.parameter.getType() == Boolean.class && this.parameter.getType() == Boolean.TYPE;
        }

        @Override
        public String getLabel() {
            return parameter.getName();
        }
    }

}

