/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.util.registry.DynamicRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PropertyManager<T extends PropertyManager<T>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Properties serverProperties;

    public PropertyManager(Properties properties) {
        this.serverProperties = properties;
    }

    public static Properties load(Path path) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
            properties.load(inputStream);
        } catch (IOException iOException) {
            LOGGER.error("Failed to load properties from file: " + path);
        }
        return properties;
    }

    public void save(Path path) {
        try (OutputStream outputStream = Files.newOutputStream(path, new OpenOption[0]);){
            this.serverProperties.store(outputStream, "Minecraft server properties");
        } catch (IOException iOException) {
            LOGGER.error("Failed to store properties to file: " + path);
        }
    }

    private static <V extends Number> Function<String, V> safeParseNumber(Function<String, V> function) {
        return arg_0 -> PropertyManager.lambda$safeParseNumber$0(function, arg_0);
    }

    protected static <V> Function<String, V> enumConverter(IntFunction<V> intFunction, Function<String, V> function) {
        return arg_0 -> PropertyManager.lambda$enumConverter$1(intFunction, function, arg_0);
    }

    @Nullable
    private String getStringValue(String string) {
        return (String)this.serverProperties.get(string);
    }

    @Nullable
    protected <V> V func_218984_a(String string, Function<String, V> function) {
        String string2 = this.getStringValue(string);
        if (string2 == null) {
            return null;
        }
        this.serverProperties.remove(string);
        return function.apply(string2);
    }

    protected <V> V func_218983_a(String string, Function<String, V> function, Function<V, String> function2, V v) {
        String string2 = this.getStringValue(string);
        V v2 = MoreObjects.firstNonNull(string2 != null ? (Object)function.apply(string2) : null, v);
        this.serverProperties.put(string, function2.apply(v2));
        return v2;
    }

    protected <V> Property<V> func_218981_b(String string, Function<String, V> function, Function<V, String> function2, V v) {
        String string2 = this.getStringValue(string);
        Object v2 = MoreObjects.firstNonNull(string2 != null ? (Object)function.apply(string2) : null, v);
        this.serverProperties.put(string, function2.apply(v2));
        return new Property<Object>(this, string, v2, (Function<Object, String>)function2);
    }

    protected <V> V func_218977_a(String string, Function<String, V> function, UnaryOperator<V> unaryOperator, Function<V, String> function2, V v) {
        return (V)this.func_218983_a(string, arg_0 -> PropertyManager.lambda$func_218977_a$2(function, unaryOperator, arg_0), function2, v);
    }

    protected <V> V func_218979_a(String string, Function<String, V> function, V v) {
        return (V)this.func_218983_a(string, function, Objects::toString, v);
    }

    protected <V> Property<V> func_218965_b(String string, Function<String, V> function, V v) {
        return this.func_218981_b(string, function, Objects::toString, v);
    }

    protected String registerString(String string, String string2) {
        return this.func_218983_a(string, Function.identity(), Function.identity(), string2);
    }

    @Nullable
    protected String func_218980_a(String string) {
        return (String)this.func_218984_a(string, Function.identity());
    }

    protected int registerInt(String string, int n) {
        return this.func_218979_a(string, PropertyManager.safeParseNumber(Integer::parseInt), n);
    }

    protected Property<Integer> func_218974_b(String string, int n) {
        return this.func_218965_b(string, PropertyManager.safeParseNumber(Integer::parseInt), n);
    }

    protected int func_218962_a(String string, UnaryOperator<Integer> unaryOperator, int n) {
        return this.func_218977_a(string, PropertyManager.safeParseNumber(Integer::parseInt), unaryOperator, Objects::toString, n);
    }

    protected long func_218967_a(String string, long l) {
        return this.func_218979_a(string, PropertyManager.safeParseNumber(Long::parseLong), l);
    }

    protected boolean registerBool(String string, boolean bl) {
        return this.func_218979_a(string, Boolean::valueOf, bl);
    }

    protected Property<Boolean> func_218961_b(String string, boolean bl) {
        return this.func_218965_b(string, Boolean::valueOf, bl);
    }

    @Nullable
    protected Boolean func_218978_b(String string) {
        return this.func_218984_a(string, Boolean::valueOf);
    }

    protected Properties func_218966_a() {
        Properties properties = new Properties();
        properties.putAll(this.serverProperties);
        return properties;
    }

    protected abstract T func_241881_b(DynamicRegistries var1, Properties var2);

    private static Object lambda$func_218977_a$2(Function function, UnaryOperator unaryOperator, String string) {
        Object r = function.apply(string);
        return r != null ? unaryOperator.apply(r) : null;
    }

    private static Object lambda$enumConverter$1(IntFunction intFunction, Function function, String string) {
        try {
            return intFunction.apply(Integer.parseInt(string));
        } catch (NumberFormatException numberFormatException) {
            return function.apply(string);
        }
    }

    private static Number lambda$safeParseNumber$0(Function function, String string) {
        try {
            return (Number)function.apply(string);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    public class Property<V>
    implements Supplier<V> {
        private final String name;
        private final V field_219041_c;
        private final Function<V, String> field_219042_d;
        final PropertyManager this$0;

        private Property(PropertyManager propertyManager, String string, V v, Function<V, String> function) {
            this.this$0 = propertyManager;
            this.name = string;
            this.field_219041_c = v;
            this.field_219042_d = function;
        }

        @Override
        public V get() {
            return this.field_219041_c;
        }

        public T func_244381_a(DynamicRegistries dynamicRegistries, V v) {
            Properties properties = this.this$0.func_218966_a();
            properties.put(this.name, this.field_219042_d.apply(v));
            return this.this$0.func_241881_b(dynamicRegistries, properties);
        }
    }
}

