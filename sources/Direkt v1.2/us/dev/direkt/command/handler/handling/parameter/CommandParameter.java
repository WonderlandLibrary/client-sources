package us.dev.direkt.command.handler.handling.parameter;

import us.dev.api.interfaces.Labeled;

import java.lang.annotation.Annotation;

/**
 * @author Foundry
 */
public interface CommandParameter extends Labeled {
    <A extends Annotation> A getAnnotation(Class<A> annotationClass);

    Annotation[] getAnnotations();

    Class<?> getType();

    boolean isOptional();

    boolean isBoolean();

    class Transient implements CommandParameter {
        final Class<?> type;

        public Transient(Class<?> type) {
            this.type = type;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
            return null;
        }

        @Override
        public Annotation[] getAnnotations() {
            return new Annotation[0];
        }

        @Override
        public Class<?> getType() {
            return type;
        }

        @Override
        public boolean isOptional() {
            return false;
        }

        @Override
        public boolean isBoolean() {
            return false;
        }

        @Override
        public String getLabel() {
            return type.getName();
        }
    }
}
