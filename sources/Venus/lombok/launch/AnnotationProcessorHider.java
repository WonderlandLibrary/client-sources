/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.lang.reflect.Field;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import lombok.launch.Main;
import sun.misc.Unsafe;

class AnnotationProcessorHider {
    AnnotationProcessorHider() {
    }

    public static class AnnotationProcessor
    extends AbstractProcessor {
        private final AbstractProcessor instance = AnnotationProcessor.createWrappedInstance();

        @Override
        public Set<String> getSupportedOptions() {
            return this.instance.getSupportedOptions();
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return this.instance.getSupportedAnnotationTypes();
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return this.instance.getSupportedSourceVersion();
        }

        @Override
        public void init(ProcessingEnvironment processingEnvironment) {
            this.disableJava9SillyWarning();
            AstModificationNotifierData.lombokInvoked = true;
            this.instance.init(processingEnvironment);
            super.init(processingEnvironment);
        }

        private void disableJava9SillyWarning() {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(false);
                Unsafe unsafe = (Unsafe)field.get(null);
                Class<?> clazz = Class.forName("jdk.internal.module.IllegalAccessLogger");
                Field field2 = clazz.getDeclaredField("logger");
                unsafe.putObjectVolatile(clazz, unsafe.staticFieldOffset(field2), null);
            } catch (Throwable throwable) {}
        }

        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
            return this.instance.process(set, roundEnvironment);
        }

        @Override
        public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String string) {
            return this.instance.getCompletions(element, annotationMirror, executableElement, string);
        }

        private static AbstractProcessor createWrappedInstance() {
            ClassLoader classLoader = Main.getShadowClassLoader();
            try {
                Class<?> clazz = classLoader.loadClass("lombok.core.AnnotationProcessor");
                return (AbstractProcessor)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable throwable) {
                if (throwable instanceof Error) {
                    throw (Error)throwable;
                }
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException)throwable;
                }
                throw new RuntimeException(throwable);
            }
        }
    }

    public static class AstModificationNotifierData {
        public static volatile boolean lombokInvoked = false;
    }

    @SupportedAnnotationTypes(value={"lombok.*"})
    public static class ClaimingProcessor
    extends AbstractProcessor {
        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }
    }
}

