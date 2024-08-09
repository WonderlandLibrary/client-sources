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
        TypeConverterRegistry typeConverterRegistry = INSTANCE;
        if (typeConverterRegistry == null) {
            Object object = INSTANCE_LOCK;
            synchronized (object) {
                typeConverterRegistry = INSTANCE;
                if (typeConverterRegistry == null) {
                    INSTANCE = typeConverterRegistry = new TypeConverterRegistry();
                }
            }
        }
        return typeConverterRegistry;
    }

    public TypeConverter<?> findCompatibleConverter(Type type) {
        Object object;
        Objects.requireNonNull(type, "No type was provided");
        TypeConverter typeConverter = (TypeConverter)this.registry.get(type);
        if (typeConverter != null) {
            return typeConverter;
        }
        if (type instanceof Class && ((Class)(object = (Class)type)).isEnum()) {
            EnumConverter<Enum> enumConverter = new EnumConverter<Enum>(((Class)object).asSubclass(Enum.class));
            this.registry.putIfAbsent(type, enumConverter);
            return enumConverter;
        }
        for (Map.Entry entry : this.registry.entrySet()) {
            Type type2 = (Type)entry.getKey();
            if (!TypeUtil.isAssignable(type, type2)) continue;
            LOGGER.debug("Found compatible TypeConverter<{}> for type [{}].", (Object)type2, (Object)type);
            TypeConverter typeConverter2 = (TypeConverter)entry.getValue();
            this.registry.putIfAbsent(type, typeConverter2);
            return typeConverter2;
        }
        throw new UnknownFormatConversionException(type.toString());
    }

    private TypeConverterRegistry() {
        LOGGER.trace("TypeConverterRegistry initializing.");
        PluginManager pluginManager = new PluginManager("TypeConverter");
        pluginManager.collectPlugins();
        this.loadKnownTypeConverters(pluginManager.getPlugins().values());
        this.registerPrimitiveTypes();
    }

    private void loadKnownTypeConverters(Collection<PluginType<?>> collection) {
        for (PluginType<?> pluginType : collection) {
            TypeConverter typeConverter;
            Class<TypeConverter> clazz;
            Type type;
            Class<?> clazz2 = pluginType.getPluginClass();
            if (!TypeConverter.class.isAssignableFrom(clazz2) || this.registry.putIfAbsent(type = TypeConverterRegistry.getTypeConverterSupportedType(clazz = clazz2.asSubclass(TypeConverter.class)), typeConverter = ReflectionUtil.instantiate(clazz)) == null) continue;
            LOGGER.warn("Found a TypeConverter [{}] for type [{}] that already exists.", (Object)typeConverter, (Object)type);
        }
    }

    private static Type getTypeConverterSupportedType(Class<? extends TypeConverter> clazz) {
        for (Type type : clazz.getGenericInterfaces()) {
            ParameterizedType parameterizedType;
            if (!(type instanceof ParameterizedType) || !TypeConverter.class.equals((Object)(parameterizedType = (ParameterizedType)type).getRawType())) continue;
            return parameterizedType.getActualTypeArguments()[0];
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

    private void registerTypeAlias(Type type, Type type2) {
        this.registry.putIfAbsent(type2, (TypeConverter<?>)this.registry.get(type));
    }

    static {
        INSTANCE_LOCK = new Object();
    }
}

