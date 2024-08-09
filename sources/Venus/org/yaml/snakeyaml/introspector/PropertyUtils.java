/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.FieldProperty;
import org.yaml.snakeyaml.introspector.MethodProperty;
import org.yaml.snakeyaml.introspector.MissingProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.util.PlatformFeatureDetector;

public class PropertyUtils {
    private final Map<Class<?>, Map<String, Property>> propertiesCache = new HashMap();
    private final Map<Class<?>, Set<Property>> readableProperties = new HashMap();
    private BeanAccess beanAccess = BeanAccess.DEFAULT;
    private boolean allowReadOnlyProperties = false;
    private boolean skipMissingProperties = false;
    private final PlatformFeatureDetector platformFeatureDetector;
    private static final String TRANSIENT = "transient";

    public PropertyUtils() {
        this(new PlatformFeatureDetector());
    }

    PropertyUtils(PlatformFeatureDetector platformFeatureDetector) {
        this.platformFeatureDetector = platformFeatureDetector;
        if (platformFeatureDetector.isRunningOnAndroid()) {
            this.beanAccess = BeanAccess.FIELD;
        }
    }

    protected Map<String, Property> getPropertiesMap(Class<?> clazz, BeanAccess beanAccess) {
        if (this.propertiesCache.containsKey(clazz)) {
            return this.propertiesCache.get(clazz);
        }
        LinkedHashMap<String, Property> linkedHashMap = new LinkedHashMap<String, Property>();
        boolean bl = false;
        if (beanAccess == BeanAccess.FIELD) {
            for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
                for (Field field : clazz2.getDeclaredFields()) {
                    int n = field.getModifiers();
                    if (Modifier.isStatic(n) || Modifier.isTransient(n) || linkedHashMap.containsKey(field.getName())) continue;
                    linkedHashMap.put(field.getName(), new FieldProperty(field));
                }
            }
        } else {
            Object object;
            try {
                object = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
                int n = ((PropertyDescriptor[])object).length;
                for (int i = 0; i < n; ++i) {
                    PropertyDescriptor propertyDescriptor = object[i];
                    Method accessibleObject = propertyDescriptor.getReadMethod();
                    if (accessibleObject != null && accessibleObject.getName().equals("getClass") || this.isTransient(propertyDescriptor)) continue;
                    linkedHashMap.put(propertyDescriptor.getName(), new MethodProperty(propertyDescriptor));
                }
            } catch (IntrospectionException introspectionException) {
                throw new YAMLException(introspectionException);
            }
            for (object = clazz; object != null; object = ((Class)object).getSuperclass()) {
                for (Field field : ((Class)object).getDeclaredFields()) {
                    int n = field.getModifiers();
                    if (Modifier.isStatic(n) || Modifier.isTransient(n)) continue;
                    if (Modifier.isPublic(n)) {
                        linkedHashMap.put(field.getName(), new FieldProperty(field));
                        continue;
                    }
                    bl = true;
                }
            }
        }
        if (linkedHashMap.isEmpty() && bl) {
            throw new YAMLException("No JavaBean properties found in " + clazz.getName());
        }
        this.propertiesCache.put(clazz, linkedHashMap);
        return linkedHashMap;
    }

    private boolean isTransient(FeatureDescriptor featureDescriptor) {
        return Boolean.TRUE.equals(featureDescriptor.getValue(TRANSIENT));
    }

    public Set<Property> getProperties(Class<? extends Object> clazz) {
        return this.getProperties(clazz, this.beanAccess);
    }

    public Set<Property> getProperties(Class<? extends Object> clazz, BeanAccess beanAccess) {
        if (this.readableProperties.containsKey(clazz)) {
            return this.readableProperties.get(clazz);
        }
        Set<Property> set = this.createPropertySet(clazz, beanAccess);
        this.readableProperties.put(clazz, set);
        return set;
    }

    protected Set<Property> createPropertySet(Class<? extends Object> clazz, BeanAccess beanAccess) {
        TreeSet<Property> treeSet = new TreeSet<Property>();
        Collection<Property> collection = this.getPropertiesMap(clazz, beanAccess).values();
        for (Property property : collection) {
            if (!property.isReadable() || !this.allowReadOnlyProperties && !property.isWritable()) continue;
            treeSet.add(property);
        }
        return treeSet;
    }

    public Property getProperty(Class<? extends Object> clazz, String string) {
        return this.getProperty(clazz, string, this.beanAccess);
    }

    public Property getProperty(Class<? extends Object> clazz, String string, BeanAccess beanAccess) {
        Map<String, Property> map = this.getPropertiesMap(clazz, beanAccess);
        Property property = map.get(string);
        if (property == null && this.skipMissingProperties) {
            property = new MissingProperty(string);
        }
        if (property == null) {
            throw new YAMLException("Unable to find property '" + string + "' on class: " + clazz.getName());
        }
        return property;
    }

    public void setBeanAccess(BeanAccess beanAccess) {
        if (this.platformFeatureDetector.isRunningOnAndroid() && beanAccess != BeanAccess.FIELD) {
            throw new IllegalArgumentException("JVM is Android - only BeanAccess.FIELD is available");
        }
        if (this.beanAccess != beanAccess) {
            this.beanAccess = beanAccess;
            this.propertiesCache.clear();
            this.readableProperties.clear();
        }
    }

    public void setAllowReadOnlyProperties(boolean bl) {
        if (this.allowReadOnlyProperties != bl) {
            this.allowReadOnlyProperties = bl;
            this.readableProperties.clear();
        }
    }

    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }

    public void setSkipMissingProperties(boolean bl) {
        if (this.skipMissingProperties != bl) {
            this.skipMissingProperties = bl;
            this.readableProperties.clear();
        }
    }

    public boolean isSkipMissingProperties() {
        return this.skipMissingProperties;
    }
}

