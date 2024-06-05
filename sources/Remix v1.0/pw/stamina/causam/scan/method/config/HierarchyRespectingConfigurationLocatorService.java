package pw.stamina.causam.scan.method.config;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.lang.annotation.*;
import pw.stamina.causam.scan.method.model.*;

public final class HierarchyRespectingConfigurationLocatorService implements ConfigurationLocatorService
{
    @Override
    public ListenerConfiguration locateConfiguration(final Method method) {
        final ListenerConfigurationBuilder configurationBuilder = null;
        final Class<?> declaringClass = method.getDeclaringClass();
        Arrays.stream(declaringClass.getAnnotations()).filter(this::isConfigurationAnnotation);
        return configurationBuilder.build();
    }
    
    private boolean isConfigurationAnnotation(final Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(Configuration.class);
    }
}
