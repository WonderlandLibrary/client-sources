/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidators;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitor;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitors;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.core.util.TypeUtil;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilders;

public class PluginBuilder
implements Builder<Object> {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final PluginType<?> pluginType;
    private final Class<?> clazz;
    private Configuration configuration;
    private Node node;
    private LogEvent event;

    public PluginBuilder(PluginType<?> pluginType) {
        this.pluginType = pluginType;
        this.clazz = pluginType.getPluginClass();
    }

    public PluginBuilder withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public PluginBuilder withConfigurationNode(Node node) {
        this.node = node;
        return this;
    }

    public PluginBuilder forLogEvent(LogEvent logEvent) {
        this.event = logEvent;
        return this;
    }

    @Override
    public Object build() {
        Object object;
        this.verify();
        try {
            LOGGER.debug("Building Plugin[name={}, class={}].", (Object)this.pluginType.getElementName(), (Object)this.pluginType.getPluginClass().getName());
            object = PluginBuilder.createBuilder(this.clazz);
            if (object != null) {
                this.injectFields((Builder<?>)object);
                return object.build();
            }
        } catch (Exception exception) {
            LOGGER.error("Unable to inject fields into builder class for plugin type {}, element {}.", (Object)this.clazz, (Object)this.node.getName(), (Object)exception);
        }
        try {
            object = PluginBuilder.findFactoryMethod(this.clazz);
            Object[] objectArray = this.generateParameters((Method)object);
            return ((Method)object).invoke(null, objectArray);
        } catch (Exception exception) {
            LOGGER.error("Unable to invoke factory method in class {} for element {}.", (Object)this.clazz, (Object)this.node.getName(), (Object)exception);
            return null;
        }
    }

    private void verify() {
        Objects.requireNonNull(this.configuration, "No Configuration object was set.");
        Objects.requireNonNull(this.node, "No Node object was set.");
    }

    private static Builder<?> createBuilder(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(PluginBuilderFactory.class) || !Modifier.isStatic(method.getModifiers()) || !TypeUtil.isAssignable(Builder.class, method.getReturnType())) continue;
            ReflectionUtil.makeAccessible(method);
            return (Builder)method.invoke(null, new Object[0]);
        }
        return null;
    }

    private void injectFields(Builder<?> builder) throws IllegalAccessException {
        List<Field> list = TypeUtil.getAllDeclaredFields(builder.getClass());
        AccessibleObject.setAccessible(list.toArray(new Field[0]), true);
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (Field field : list) {
            stringBuilder.append(stringBuilder.length() == 0 ? PluginBuilder.simpleName(builder) + "(" : ", ");
            Annotation[] annotationArray = field.getDeclaredAnnotations();
            String[] stringArray = PluginBuilder.extractPluginAliases(annotationArray);
            for (Annotation annotation : annotationArray) {
                Object object;
                PluginVisitor<? extends Annotation> pluginVisitor;
                if (annotation instanceof PluginAliases || (pluginVisitor = PluginVisitors.findVisitor(annotation.annotationType())) == null || (object = pluginVisitor.setAliases(stringArray).setAnnotation(annotation).setConversionType(field.getType()).setStrSubstitutor(this.configuration.getStrSubstitutor()).setMember(field).visit(this.configuration, this.node, this.event, stringBuilder)) == null) continue;
                field.set(builder, object);
            }
            Collection<ConstraintValidator<?>> collection = ConstraintValidators.findValidators(annotationArray);
            Object object = field.get(builder);
            Iterator iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                ConstraintValidator constraintValidator = (ConstraintValidator)iterator2.next();
                if (constraintValidator.isValid(field.getName(), object)) continue;
                bl = true;
            }
        }
        stringBuilder.append(stringBuilder.length() == 0 ? builder.getClass().getSimpleName() + "()" : ")");
        LOGGER.debug(stringBuilder.toString());
        if (bl) {
            throw new ConfigurationException("Arguments given for element " + this.node.getName() + " are invalid");
        }
        this.checkForRemainingAttributes();
        this.verifyNodeChildrenUsed();
    }

    private static String simpleName(Object object) {
        if (object == null) {
            return "null";
        }
        String string = object.getClass().getName();
        int n = string.lastIndexOf(46);
        return n < 0 ? string : string.substring(n + 1);
    }

    private static Method findFactoryMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(PluginFactory.class) || !Modifier.isStatic(method.getModifiers())) continue;
            ReflectionUtil.makeAccessible(method);
            return method;
        }
        throw new IllegalStateException("No factory method found for class " + clazz.getName());
    }

    private Object[] generateParameters(Method method) {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?>[] classArray = method.getParameterTypes();
        Annotation[][] annotationArray = method.getParameterAnnotations();
        Object[] objectArray = new Object[annotationArray.length];
        boolean bl = false;
        for (int i = 0; i < annotationArray.length; ++i) {
            Object object;
            stringBuilder.append(stringBuilder.length() == 0 ? method.getName() + "(" : ", ");
            String[] stringArray = PluginBuilder.extractPluginAliases(annotationArray[i]);
            for (Annotation annotation : annotationArray[i]) {
                Object object2;
                if (annotation instanceof PluginAliases || (object = PluginVisitors.findVisitor(annotation.annotationType())) == null || (object2 = object.setAliases(stringArray).setAnnotation(annotation).setConversionType(classArray[i]).setStrSubstitutor(this.configuration.getStrSubstitutor()).setMember(method).visit(this.configuration, this.node, this.event, stringBuilder)) == null) continue;
                objectArray[i] = object2;
            }
            Collection<ConstraintValidator<?>> collection = ConstraintValidators.findValidators(annotationArray[i]);
            Object object4 = objectArray[i];
            String string = "arg[" + i + "](" + PluginBuilder.simpleName(object4) + ")";
            Iterator object22 = collection.iterator();
            while (object22.hasNext()) {
                object = (ConstraintValidator)object22.next();
                if (object.isValid(string, object4)) continue;
                bl = true;
            }
        }
        stringBuilder.append(stringBuilder.length() == 0 ? method.getName() + "()" : ")");
        this.checkForRemainingAttributes();
        this.verifyNodeChildrenUsed();
        LOGGER.debug(stringBuilder.toString());
        if (bl) {
            throw new ConfigurationException("Arguments given for element " + this.node.getName() + " are invalid");
        }
        return objectArray;
    }

    private static String[] extractPluginAliases(Annotation ... annotationArray) {
        String[] stringArray = null;
        for (Annotation annotation : annotationArray) {
            if (!(annotation instanceof PluginAliases)) continue;
            stringArray = ((PluginAliases)annotation).value();
        }
        return stringArray;
    }

    private void checkForRemainingAttributes() {
        Map<String, String> map = this.node.getAttributes();
        if (!map.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String string : map.keySet()) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(this.node.getName());
                    stringBuilder.append(" contains ");
                    if (map.size() == 1) {
                        stringBuilder.append("an invalid element or attribute ");
                    } else {
                        stringBuilder.append("invalid attributes ");
                    }
                } else {
                    stringBuilder.append(", ");
                }
                StringBuilders.appendDqValue(stringBuilder, string);
            }
            LOGGER.error(stringBuilder.toString());
        }
    }

    private void verifyNodeChildrenUsed() {
        List<Node> list = this.node.getChildren();
        if (!this.pluginType.isDeferChildren() && !list.isEmpty()) {
            for (Node node : list) {
                String string = this.node.getType().getElementName();
                String string2 = string.equals(this.node.getName()) ? this.node.getName() : string + ' ' + this.node.getName();
                LOGGER.error("{} has no parameter that matches element {}", (Object)string2, (Object)node.getName());
            }
        }
    }
}

