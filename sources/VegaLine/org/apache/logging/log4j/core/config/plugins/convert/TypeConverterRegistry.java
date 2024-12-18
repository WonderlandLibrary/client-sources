/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UnknownFormatConversionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.EnumConverter;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverter;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.core.util.TypeUtil;
import org.apache.logging.log4j.status.StatusLogger;

public class TypeConverterRegistry {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static volatile TypeConverterRegistry INSTANCE;
    private static final Object INSTANCE_LOCK;
    private final ConcurrentMap<Type, TypeConverter<?>> registry = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TypeConverterRegistry getInstance() {
        TypeConverterRegistry result = INSTANCE;
        if (result == null) {
            Object object = INSTANCE_LOCK;
            synchronized (object) {
                result = INSTANCE;
                if (result == null) {
                    INSTANCE = result = new TypeConverterRegistry();
                }
            }
        }
        return result;
    }

    public TypeConverter<?> findCompatibleConverter(Type type2) {
        Class clazz;
        Objects.requireNonNull(type2, "No type was provided");
        TypeConverter primary = (TypeConverter)this.registry.get(type2);
        if (primary != null) {
            return primary;
        }
        if (type2 instanceof Class && (clazz = (Class)type2).isEnum()) {
            EnumConverter<Enum> converter = new EnumConverter<Enum>(clazz.asSubclass(Enum.class));
            this.registry.putIfAbsent(type2, converter);
            return converter;
        }
        for (Map.Entry entry : this.registry.entrySet()) {
            Type key = (Type)entry.getKey();
            if (!TypeUtil.isAssignable(type2, key)) continue;
            LOGGER.debug("Found compatible TypeConverter<{}> for type [{}].", (Object)key, (Object)type2);
            TypeConverter value = (TypeConverter)entry.getValue();
            this.registry.putIfAbsent(type2, value);
            return value;
        }
        throw new UnknownFormatConversionException(type2.toString());
    }

    private TypeConverterRegistry() {
        LOGGER.trace("TypeConverterRegistry initializing.");
        PluginManager manager = new PluginManager("TypeConverter");
        manager.collectPlugins();
        this.loadKnownTypeConverters(manager.getPlugins().values());
        this.registerPrimitiveTypes();
    }

    private void loadKnownTypeConverters(Collection<PluginType<?>> knownTypes) {
        for (PluginType<?> knownType : knownTypes) {
            TypeConverter converter;
            Class<TypeConverter> pluginClass;
            Type conversionType;
            Class<?> clazz = knownType.getPluginClass();
            if (!TypeConverter.class.isAssignableFrom(clazz) || this.registry.putIfAbsent(conversionType = TypeConverterRegistry.getTypeConverterSupportedType(pluginClass = clazz.asSubclass(TypeConverter.class)), converter = ReflectionUtil.instantiate(pluginClass)) == null) continue;
            LOGGER.warn("Found a TypeConverter [{}] for type [{}] that already exists.", (Object)converter, (Object)conversionType);
        }
    }

    private static Type getTypeConverterSupportedType(Class<? extends TypeConverter> typeConverterClass) {
        for (Type type2 : typeConverterClass.getGenericInterfaces()) {
            ParameterizedType pType;
            if (!(type2 instanceof ParameterizedType) || !TypeConverter.class.equals((Object)(pType = (ParameterizedType)type2).getRawType())) continue;
            return pType.getActualTypeArguments()[0];
        }
        return Void.TYPE;
    }

    private void registerPrimitiveTypes() {
        this.registerTypeAlias((Type)((Object)Boolean.class), Boolean.TYPE);
        this.registerTypeAlias((Type)((Object)Byte.class), Byte.TYPE);
        this.registerTypeAlias((Type)((Object)Character.class), Character.TYPE);
        this.registerTypeAlias((Type)((Object)Double.class), Double.TYPE);
        this.registerTypeAlias((Type)((Object)Float.class), Float.TYPE);
        this.registerTypeAlias((Type)((Object)Integer.class), Integer.TYPE);
        this.registerTypeAlias((Type)((Object)Long.class), Long.TYPE);
        this.registerTypeAlias((Type)((Object)Short.class), Short.TYPE);
    }

    private void registerTypeAlias(Type knownType, Type aliasType) {
        this.registry.putIfAbsent(aliasType, (TypeConverter<?>)this.registry.get(knownType));
    }

    static {
        INSTANCE_LOCK = new Object();
    }
}

