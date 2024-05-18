package us.dev.direkt.command.handler.handling.handlers;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Optional;

/**
 * @author Foundry
 */
public class ArrayArgumentHandler implements ArgumentHandler<Object> {
    @Override
    public Object parse(CommandManager commandManager, CommandParameter parameter, String input) throws ArgumentParseException {
        Object resultArray = Array.newInstance(parameter.getType().getComponentType(), Integer.valueOf(input.substring(input.indexOf('}') + 1)));
        String[] arrayComponents = input.substring(1, input.indexOf('}')).split(",");

        @SuppressWarnings("unchecked")
        Optional<ArgumentHandler<?>> handlerLookup = commandManager.findArgumentHandler((Class) parameter.getType().getComponentType());
        if (handlerLookup.isPresent()) {
            for (int i = 0; i < Array.getLength(resultArray); i++) {
                Array.set(resultArray, i, handlerLookup.get().parse(commandManager,
                        new ArrayProxyCommandParameter(parameter),
                        arrayComponents[i]));
            }
        }
        return resultArray;
    }

    @Override
    public String getSyntax(CommandParameter parameter) {
        return parameter.getLabel() + " - " + "Array of " + parameter.getType().getComponentType().getSimpleName();
    }

    @Override
    public Class[] getHandledTypes() {
        return new Class[] {Object[].class};
    }

    private static class ArrayProxyCommandParameter implements CommandParameter {
        private final CommandParameter backingParameter;

        ArrayProxyCommandParameter(CommandParameter backingParameter) {
            this.backingParameter = backingParameter;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
            return backingParameter.getAnnotation(annotationClass);
        }

        @Override
        public Annotation[] getAnnotations() {
            return backingParameter.getAnnotations();
        }

        @Override
        public Class<?> getType() {
            return backingParameter.getType().getComponentType();
        }

        @Override
        public boolean isOptional() {
            return backingParameter.isOptional();
        }

        @Override
        public boolean isBoolean() {
            return backingParameter.isBoolean();
        }

        @Override
        public String getLabel() {
            return backingParameter.getLabel();
        }
    }
}
